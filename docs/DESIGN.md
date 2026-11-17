# Design Specification

Design of the course enrollment and waitlist slice. This document grows with
the implementation; sections marked *pending* are completed in the step that
builds them.

| Section | State |
|---------|-------|
| Context and service boundary | Written (Step 1) |
| Assumptions and policies | Written (Step 1) |
| Domain model | Written and implemented (Step 1) |
| Invariants | Written and tested (Step 1) |
| Waitlist promotion rule and open-seat policy | Written and tested (Step 1) |
| Concurrency and persistence | Written and tested (Step 1) |
| Service layer and REST API | Written, implemented, and tested (Step 2) |
| Limitations and follow-up risks | Written (Steps 1 and 2); concise summary in the README (Step 3) |

## 1. Context and service boundary

The assessment asks for one bounded feature: enrollment, dropping, and FIFO
waitlist promotion for a single course offering.

**No starter project was supplied.** The repository was empty when work began
(command-verified; see the development log), so there were no existing
interfaces, persistence, or test conventions to conform to. The brief's
fallback applies: "a small service or command-line prototype with an in-memory
store is sufficient." The service form was chosen over a CLI because the
developer's agreed stack is Spring Boot with REST, and a long-running service
is where the concurrency behaviour the brief cares about actually occurs.

Inside the boundary:

- one course offering, its enrolled list, and its waitlist;
- the learners known to the service and their completed courses;
- the prerequisite check.

Outside the boundary, and not built: frontend, course catalog, payments,
identity integration, multi-term registration, deployment, any database, and
any configurable policy engine. The rules below are fixed in code.

The domain model in section 3 has no framework dependencies. The service and
REST layers in section 7 translate HTTP requests into calls on it and
translate its result values into HTTP responses. They add no enrollment rules.

## 2. Assumptions and policies

Each item is marked with its origin: **Brief** (stated by the assessment
brief), **Developer** (decided by the developer), or **AI-suggested**
(proposed by the AI and accepted by the developer during design review).

### Prerequisite rule

| # | Policy | Origin |
|---|--------|--------|
| P1 | A course lists zero or more prerequisite course codes. A learner is eligible when they have completed **every** one of them. An empty list means everyone is eligible. | AI-suggested |
| P2 | There are no grades, in-progress courses, or transitive prerequisite chains: only the offering's own course's direct prerequisites are checked. | AI-suggested |
| P3 | Eligibility is checked at initial enrollment and again at promotion. It is evaluated at that moment and never cached. | Developer |
| P4 | A learner's completions can be added and revoked. Revoking a prerequisite does **not** remove a learner who is already enrolled. | Developer |

### Enrollment

| # | Policy | Origin |
|---|--------|--------|
| E1 | A learner may be enrolled at most once. | Brief |
| E2 | An eligible learner is enrolled while a seat is free. | Brief |
| E3 | An ineligible learner is rejected. They are not enrolled and not waitlisted, whether or not the offering is full. | Brief ("eligible learners join the waitlist") |
| E4 | A request from a learner who is already enrolled returns `ALREADY_ENROLLED`; from one already waitlisted, `ALREADY_WAITLISTED`. Both change nothing and will map to HTTP 409. | Developer |
| E5 | Checks run in the order duplicate, prerequisites, capacity. | AI-suggested |

### Waitlist

| # | Policy | Origin |
|---|--------|--------|
| W1 | When the offering is full, an eligible learner joins the end of the waitlist, once. | Brief |
| W2 | The waitlist is strictly first-in, first-out by arrival. | Brief |
| W3 | **Skip and retain:** when a seat opens, the earliest *eligible* waitlisted learner is promoted. An ineligible learner ahead of them is skipped and stays on the waitlist in their original position. They are promoted later if they are eligible when a seat is next offered. | Developer |
| W4 | If nobody on the waitlist is eligible, the seat stays vacant. | Developer |
| W5 | Waitlisted learners have priority over new requesters: a new requester never takes a seat that an eligible waitlisted learner could take. | Developer |
| W6 | A waitlisted learner may leave the waitlist. This affects no enrolled learner and promotes nobody. | Developer |
| W7 | A learner who drops and enrolls again is a new arrival and joins the end of the waitlist if the offering is full. | AI-suggested |
| W8 | The waitlist has no size limit. | AI-suggested |

### Drop

| # | Policy | Origin |
|---|--------|--------|
| D1 | Dropping an enrolled learner opens a seat and triggers promotion. | Brief |
| D2 | A drop by a learner who is neither enrolled nor waitlisted, including a repeated drop, returns `NOT_ENROLLED` and changes nothing. | Brief (clear result) / AI-suggested (name) |

## 3. Domain model

Package `com.assessment.coursewaitlist.domain`. Plain Java; no Spring types.

| Type | Kind | State | Role |
|------|------|-------|------|
| `Course` | Immutable record | `code`, `title`, `prerequisiteCodes` | Catalog data. Copies its prerequisite set first and validates the copy; rejects null or blank codes and a course that lists itself. |
| `Learner` | Class; identity is `id` | `id`, `name`, completed course codes | `markCompleted`, `revokeCompletion`, `hasCompleted`, `isEligibleFor(course)`. Knows nothing about offerings. |
| `CourseOffering` | Class; the aggregate | `id`, `course`, `capacity`, enrolled, waitlist | Owns all enrollment and waitlist state. `enroll(learner)`, `drop(learnerId)`, and `snapshot()`. |
| `OfferingSnapshot` | Immutable record | `enrolledLearnerIds`, `waitlistedLearnerIds`, `availableSeats` | One consistent view of the offering, captured under its lock in a single step. For the API state query. |
| `EnrollmentResult` | Immutable record | `outcome`, `promotedLearnerIds` | Outcomes: `ENROLLED`, `WAITLISTED`, `ALREADY_ENROLLED`, `ALREADY_WAITLISTED`, `PREREQUISITES_NOT_MET`. |
| `DropResult` | Immutable record | `outcome`, `promotedLearnerIds` | Outcomes: `DROPPED`, `DROPPED_FROM_WAITLIST`, `NOT_ENROLLED`. |

Design choices:

- **Ids are plain strings**, validated as non-blank. Wrapper types were judged unnecessary for a model this small.
- **Membership is the status.** A learner is enrolled because they are in the enrolled collection, and waitlisted because they are in the waitlist. There is no separate enrollment record with a status field that could disagree with the collections.
- **Expected outcomes are return values, not exceptions.** A duplicate, a prerequisite failure, and a repeated drop are normal results the caller must handle, so they are enum values. `IllegalArgumentException` is reserved for malformed calls: a null learner, a null or blank id, a capacity below 1.
- **Results report promotions.** Both result types list the learners promoted during the operation, in order, so a caller can tell who received a seat.
- **Results cannot contradict themselves.** The result constructors reject a promoted list on any outcome that cannot promote: only `ENROLLED`, `WAITLISTED`, and `DROPPED` may carry one. Null, blank, or repeated ids are rejected too.
- **State is read as one snapshot.** `snapshot()` returns the enrolled list, the waitlist, and the free-seat count together under one lock acquisition. The single-value accessors remain for convenience, but two separate calls are not atomic with each other, so anything that needs more than one value must use the snapshot.
- **The waitlist holds `Learner` references**, not only ids, so promotion can read each learner's current completions without a lookup service. This relies on one in-memory `Learner` object per learner, which holds for an in-memory design and is noted under limitations.

## 4. Invariants

`CourseOffering` guarantees, after every operation:

| # | Invariant |
|---|-----------|
| I1 | The number of enrolled learners never exceeds the capacity. Capacity is fixed at construction and is at least 1. |
| I2 | A learner is enrolled, waitlisted, or neither: never both, and never twice in either. |
| I3 | Every learner was eligible at the moment they were enrolled or promoted. |
| I4 | Nobody joins the waitlist while a seat is free. |
| I5 | A duplicate, rejected, or invalid request leaves the state exactly as it was. |
| I6 | Promotion never changes the relative order of the learners who remain waiting. |
| I7 | Callers cannot change the state through anything the offering returns: snapshots are immutable copies. |
| I8 | A snapshot is internally consistent: no learner appears in both lists, and the free-seat count equals capacity minus the enrolled count. |

Note on I4 and W4: a seat can be vacant while the waitlist is non-empty, but
only when every waitlisted learner was ineligible the last time seats were
offered.

## 5. Waitlist promotion rule and open-seat policy

**Promotion pass.** Walk the waitlist from the earliest arrival. Promote each
learner who is eligible right now, until no seat is free or the end of the
waitlist is reached. Skip ineligible learners without moving them.

**When the pass runs.** Open seats are offered to the waitlist at exactly two
moments:

1. immediately after an enrolled learner drops; and
2. immediately before a valid new enrollment request is placed, that is, a
   request that has already passed the duplicate and prerequisite checks.

After the pass in case 2, the requester takes a seat if one is still free and
otherwise joins the end of the waitlist. This is what enforces W5.

**When the pass does not run.** Duplicate requests, prerequisite failures,
leaving the waitlist, drops by learners who are not enrolled, and changes to a
learner's completions do not offer seats. The first four must leave the state
unchanged (I5, W6).

Normally a pass promotes at most one learner, because one drop opens one seat.
It can promote more when earlier seats were left vacant under W4 and waitlisted
learners have since regained eligibility.

**Worked example** (capacity 1):

| Step | Action | Enrolled | Waitlist | Note |
|------|--------|----------|----------|------|
| 1 | A enrolls | A | | |
| 2 | W enrolls | A | W | Full, W waitlisted |
| 3 | W's prerequisite is revoked | A | W | W stays on the waitlist |
| 4 | A drops | | W | W is ineligible and skipped; the seat stays vacant |
| 5 | W's prerequisite is restored | | W | No seat is offered by this change |
| 6 | New learner N enrolls | W | N | W is promoted first; N is waitlisted |

## 6. Concurrency and persistence

**Persistence.** All state is in memory and is lost on restart. The brief
allows this when no starter data model is supplied, and excludes adding a
database.

**Why concurrency matters here.** The brief's concurrency clause is
conditional: "If the starter uses concurrent requests, preserve its transaction
or locking pattern and add a focused concurrency test where practical." There
is no starter, so there is no existing pattern to preserve and the clause is
not triggered. The design handles concurrency anyway, because the service will
run in an embedded web server that handles requests on several threads against
shared in-memory state, with no database to arbitrate. Two simultaneous
requests for the last seat, or two simultaneous drops of the same learner, are
exactly how capacity or the waitlist would be corrupted.

**Approach.** One private lock per `CourseOffering` guards both collections.
`enroll`, `drop`, and `snapshot` hold it for their whole duration, so each
sequence of checks and the change that follows is atomic.

- A single lock was chosen over concurrent collections because the operations
  are compound: check duplicate, check eligibility, check capacity, mutate,
  promote. Concurrent collections make single calls safe, not sequences.
- Operations are short and there is one offering, so contention is not a
  concern.
- Each `Learner` has its own private lock guarding its completions. An
  eligibility check holds it for the whole check, so a course with several
  prerequisites is always judged against one consistent set of completions. An
  earlier version used a lock-free concurrent set, which could report a learner
  eligible for a combination of completions they never held at one instant;
  the code-quality review found this and it was replaced.
- **Lock order.** The offering takes a learner's lock while holding its own
  (during eligibility checks). The order is always offering first, learner
  second. `Learner` never calls into an offering and never holds two learner
  locks, so the order cannot be reversed and there is no deadlock.
- Eligibility is a point-in-time read. A completion revoked just after a
  promotion does not undo the promotion, consistent with P4.

## 7. Service layer and REST API

### 7.1 Layers

| Package | Types | Responsibility |
|---------|-------|----------------|
| `config` | `OfferingProperties`, `OfferingConfiguration` | Reads the one offering from `course-waitlist.offering.*` in `application.yaml` and creates the single `CourseOffering` bean. An invalid value (capacity below 1, missing id or course) stops startup. |
| `service` | `LearnerDirectory`, `EnrollmentService`, `OfferingView`, `LearnerView`, two exceptions | Holds the learners in memory, resolves a learner id to its `Learner` object, and delegates to the domain. Returns immutable results and views. |
| `api` | `OfferingController`, `LearnerController`, request and response records, `ApiExceptionHandler` | Validates input, calls the service, and maps results to HTTP. |

How service operations map to the domain:

| `EnrollmentService` | Domain call |
|---------------------|-------------|
| `enroll(learnerId)` | `LearnerDirectory.require`, then `CourseOffering.enroll(learner)` |
| `drop(learnerId)` | `LearnerDirectory.require`, then `CourseOffering.drop(learnerId)` |
| `offeringState()` | `CourseOffering.snapshot()`, combined with the offering's fixed details |
| `registerLearner(...)` | `LearnerDirectory.register`: creates one `Learner` |
| `markCompleted`, `revokeCompletion` | `Learner.markCompleted`, `Learner.revokeCompletion` |

**Why a learner directory exists.** Prerequisites are re-checked at promotion
(P3) and completions can be revoked (P4), so a learner's completions must
persist between requests and be changeable. The directory is an in-memory map
that guarantees one `Learner` object per id, which the domain relies on (L3).
It is not an identity system or a course catalog: completions are bare course
codes and there is no list of courses.

**Why there is no offering registry.** The scope is one offering. It is a
single bean built from configuration, and no API path carries an offering id.

**Locking.** The service and controllers hold no lock and no mutable state of
their own apart from the directory's concurrent map. Every enrollment rule and
the lock that makes each operation atomic stay in `CourseOffering`; the
accepted domain code was not changed in Step 2.

### 7.2 Endpoints

| Method and path | Purpose | Success |
|-----------------|---------|---------|
| `GET /api/offering` | The offering and its state, from one atomic snapshot | 200 |
| `POST /api/offering/enrollments` | Request a seat | 200, body `status` is `ENROLLED` or `WAITLISTED` |
| `DELETE /api/offering/enrollments/{learnerId}` | Give up a seat, or leave the waitlist | 200, body `status` is `DROPPED` or `DROPPED_FROM_WAITLIST` |
| `POST /api/learners` | Register a learner with initial completions | 201 |
| `PUT /api/learners/{learnerId}/completed-courses/{courseCode}` | Mark a course completed | 204 |
| `DELETE /api/learners/{learnerId}/completed-courses/{courseCode}` | Revoke a completion | 204 |

Enrollment returns 200 for both outcomes, by developer decision; the client
tells them apart by the `status` field. Drop returns 200 with a body, not
204, because it must report who was promoted.

### 7.3 Request and response bodies

All bodies are JSON. Responses are immutable records built from ids and
copies; no domain object is serialized.

```
POST /api/offering/enrollments          {"learnerId": "L1"}
200  {"learnerId": "L1", "status": "ENROLLED", "promotedLearnerIds": []}

DELETE /api/offering/enrollments/L1
200  {"learnerId": "L1", "status": "DROPPED", "promotedLearnerIds": ["L3"]}

GET /api/offering
200  {"offeringId": "CS201-A",
      "course": {"code": "CS201", "title": "Data Structures", "prerequisiteCodes": ["CS101"]},
      "capacity": 2, "availableSeats": 0,
      "enrolledLearnerIds": ["L1", "L2"], "waitlistedLearnerIds": ["L3"]}

POST /api/learners    {"id": "L1", "name": "Asha", "completedCourseCodes": ["CS101"]}
201  {"id": "L1", "name": "Asha", "completedCourseCodes": ["CS101"]}
```

`promotedLearnerIds` on an enrollment response lists waitlisted learners who
were seated before the request was placed (W5); it is usually empty.

Validation:

| Field | Rule |
|-------|------|
| Learner id, course code (body or path) | Required; 1 to 64 characters from letters, digits, `.`, `_`, `-` |
| Learner name | Required, not blank, at most 100 characters |
| `completedCourseCodes` | Optional; at most 100 entries, each a valid course code |
| Any text field | Must be a JSON string. A JSON number or boolean is not converted to text; the request is rejected with 400 |

### 7.4 Errors

Every error is an RFC 9457 problem document (Spring `ProblemDetail`, media
type `application/problem+json`) with one added member, `code`, a stable
machine-readable reason. Framework errors use the same shape.

```
409  {"type": "about:blank", "title": "Conflict", "status": 409,
      "detail": "Learner L1 is already enrolled.",
      "instance": "/api/offering/enrollments", "code": "ALREADY_ENROLLED"}
```

| Situation | Status | `code` |
|-----------|--------|--------|
| Learner already enrolled | 409 | `ALREADY_ENROLLED` |
| Learner already on the waitlist | 409 | `ALREADY_WAITLISTED` |
| Prerequisites not met | 422 | `PREREQUISITES_NOT_MET` |
| Drop by a registered learner who is neither enrolled nor waitlisted, including a repeated drop | 409 | `NOT_ENROLLED` |
| Learner id not registered (enroll, drop, or completion change) | 404 | `LEARNER_NOT_FOUND` |
| Learner id already registered | 409 | `LEARNER_ALREADY_EXISTS` |
| Missing, blank, or malformed field; malformed JSON | 400 | `INVALID_REQUEST` |
| Unknown path | 404 | `NOT_FOUND` |
| Wrong HTTP method / content type | 405 / 415 | `METHOD_NOT_ALLOWED` / `UNSUPPORTED_MEDIA_TYPE` |
| Unexpected failure | 500 | `INTERNAL_ERROR` |

A rejected request never changes the offering. An unexpected failure is logged
on the server and reported to the client without its cause; no response
carries a stack trace or class name.

### 7.5 API decisions and tradeoffs

| # | Decision | Origin |
|---|----------|--------|
| A1 | In-memory `LearnerDirectory` with three learner endpoints, so skip-and-retain can be exercised over HTTP. | Developer (AI-recommended option) |
| A2 | The single offering is configured in `application.yaml`. | Developer (AI-recommended option) |
| A3 | No offering id in any path and no offering registry. The "unknown offering" scenario is a review check, not a test. | Developer |
| A4 | 200 for both `ENROLLED` and `WAITLISTED`. | Developer (the AI had recommended 201 and 202) |
| A5 | 422 for a prerequisite failure. | Developer (AI-recommended option) |
| A6 | 409 `NOT_ENROLLED` for a drop by a registered learner who holds nothing; 404 `LEARNER_NOT_FOUND` for an unregistered id. | Developer (the AI had recommended 404 for the first) |
| A7 | Marking a completion does not itself fill a vacant seat (L1 kept). | Developer |
| A8 | The enrollment response does not include a waitlist position; a client reads the order from `GET /api/offering`. Including it atomically would need a domain change. | AI-suggested |
| A9 | Identifier format: 1 to 64 characters from letters, digits, `.`, `_`, `-`. | AI-suggested |

## 8. Limitations and follow-up risks

The full list. A concise summary is in the README, "Limitations and follow-up
risks".

| # | Limitation | Consequence |
|---|------------|-------------|
| L1 | A waitlisted learner who regains eligibility while a seat is vacant is not promoted immediately. This applies to the API too: `PUT .../completed-courses/...` does not seat anyone. | They wait until the next drop or the next valid new enrollment request. Their own repeat request returns `ALREADY_WAITLISTED` and does not promote them. A "re-check open seats" trigger on completion changes would close this gap. |
| L2 | State is in memory only. | Enrollments and the waitlist are lost on restart; the service cannot run as more than one instance. |
| L3 | The waitlist holds `Learner` object references. | Correct only while each learner has a single in-memory object. A persistent implementation would store ids and load learners at promotion. |
| L4 | Promotion scans the waitlist from the front. | Cost grows with the number of ineligible learners ahead of the first eligible one. Acceptable for one offering. |
| L5 | Revoking a prerequisite does not remove an enrolled learner (P4). | An enrolled learner may no longer meet the prerequisite. This is the developer's stated policy, recorded here so it is not mistaken for a defect. |
| L6 | The concurrency tests are probabilistic detectors. | With correct code they always pass (the class passed 15 of 15 consecutive runs after the corrections). Their ability to catch a missing lock was measured by disabling each lock in a scratch copy and running the class five times. Offering lock disabled: TC-C-02, TC-C-04, and TC-C-06 failed in 5 of 5 runs; TC-C-01 and TC-C-03 failed in 4 of 5 and passed once each; TC-C-05 passed in all 5, as expected, because it does not exercise the offering lock. Snapshot made non-atomic: TC-C-06 failed in 5 of 5. Learner eligibility check made non-atomic: TC-C-05 failed in 5 of 5. No single test is conclusive; the suite as a whole detected each fault in every run. Passing cannot prove the absence of every race. |
| L7 | The single-value accessors (`enrolledLearnerIds`, `waitlistedLearnerIds`, `availableSeats`) are not atomic with each other. | A caller that combines two of them can see an inconsistent pair. `snapshot()` exists for that purpose and the API state query must use it. |
| L8 | Learners can be registered but not removed, and the directory has no size limit. | Memory grows with registrations for the life of the process. Acceptable for an in-memory prototype. |
| L9 | There is no authentication or authorization. | Any caller can register learners, change completions, and enroll or drop any learner. Identity integration is excluded by the brief; a real deployment would need it in front of this API. |
| L10 | The HTTP-level concurrency tests (TC-A-19, TC-A-20) are weaker detectors than the in-process tests. | Network and JSON handling spread requests out. TC-A-19's first version (one round of 24 enrollments) did not detect a disabled offering lock or a non-atomic state read in 5 of 5 runs each. After strengthening (12 rounds of 32 concurrent enrollments and 32 concurrent drops with 4 readers) it detected the disabled lock in 7 of 8 runs and the non-atomic state read in 3 of 5. TC-A-20 detected a non-atomic registration in 1 of 5 runs; the in-process TC-S-07 detected it in 10 of 10 after being raised to 2,000 rounds (3 of 5 at 200 rounds). The evidence for the locks and for atomic registration is the in-process tests (L6, TC-S-07); the HTTP tests show the web layer adds no shared state of its own and reports results faithfully under load. |
| L11 | **Resolved in the Step 2 review corrections.** Originally: a JSON number sent as a learner id was read as a string, so `{"learnerId": 5}` meant the id `"5"`. | JSON reading is now strict for text fields: a number or boolean where text is expected is rejected with 400 `INVALID_REQUEST` (`JsonConfiguration`, tested by TC-A-21). Kept in this table so the history is visible. |
