# Requirements Traceability

Master checklist of every requirement and test for this assessment, and the
matrix linking requirement, implementation, test, and result.

**Last updated:** final submission update, 2026-11-17.
**Last test run by the AI:** `./mvnw -B test` on 2026-11-17, OpenJDK 21.0.12, Linux —
72 run, 0 failed, 0 errors, 0 skipped, in the working tree and in a fresh clone
of the pushed branch at `86ff1fa`. Verified by AI.
**Verified by developer:** `mvnw.cmd -B test` on Windows 10 with OpenJDK 17.0.21 —
BUILD SUCCESS; 72 run, 0 failures, 0 errors, 0 skipped; 44.158 seconds. Reported
by the developer on 2026-11-17 as run independently and evidenced by a Command
Prompt screenshot, which the AI has not seen.
**Reviewed by ChatGPT** (reported by the developer): five files, conclusion PASS.
This is a second AI assistant's review, not developer verification.

The summary of this matrix for a reader is [COVERAGE_REPORT.md](COVERAGE_REPORT.md).

## Rules

1. Every requirement and test has a unique id.
2. A **behavioural** requirement is covered only by an automated test that was actually run and passed.
3. A **documentation** or **scope** requirement is covered by explicit review evidence: the file and section checked, or the command run. No artificial unit tests are written for these.
4. A requirement is never marked covered because related code exists.
5. A test is never marked passed unless it was executed.
6. Anything that cannot be implemented or tested is marked BLOCKED or NOT COVERED, never omitted.
7. The coverage summary at the end is recomputed before every commit.

### Origin labels

| Label | Meaning |
|-------|---------|
| **BRIEF** | Stated by the assessment brief. Acceptance criteria are quoted verbatim. |
| **DEV** | Additional requirement decided by the developer. |
| **AI-DERIVED** | Test scenario or review check derived by the AI from the requirements. All TC and RV rows are AI-derived and were reviewed by the developer as a checklist. |

### Status values

| For | Values |
|-----|--------|
| Tests | NOT IMPLEMENTED, NOT EXECUTED, PASSED, FAILED; NOT AN AUTOMATED TEST for a row converted to a review check |
| Review checks | OPEN, EVIDENCED TO DATE (true at this commit, re-checked at each commit), PARTIAL, COVERED, BLOCKED |
| Requirements | NOT COVERED, PARTIAL, COVERED, BLOCKED |

The brief's suggested timebox of 7-8 hours is treated as guidance, not as a
requirement, by the developer's decision. It has no row here.

## A. Acceptance criteria (BRIEF, verbatim)

| ID | Text from the brief | Type | Tests / evidence | Status |
|----|---------------------|------|------------------|--------|
| AC-1 | "Enrollment succeeds when capacity remains and the learner satisfies the stated prerequisite rule." | Behavioural | TC-D-01, 02, 03, 31; TC-C-01; TC-A-01, 19 | COVERED: domain and API tests passed |
| AC-2 | "When full, an eligible learner joins the waitlist once; duplicate enrollment requests do not create duplicate records." | Behavioural | TC-D-04, 05, 06, 25, 31, 35; TC-C-01, 02; TC-A-02, 03, 04, 19 | COVERED: domain and API tests passed |
| AC-3 | "Dropping an enrolled learner opens a seat and promotes the earliest eligible waitlisted learner according to the documented eligibility policy." | Behavioural | TC-D-10, 11, 12, 23, 33; TC-C-03; TC-A-06, 12; TC-S-01 | COVERED: domain and API tests passed |
| AC-4 | "An ineligible learner is not enrolled or promoted. The documented policy for skipping or retaining that learner is applied consistently." | Behavioural | TC-D-07, 08, 09, 13, 14, 15, 16, 18; TC-A-05, 14; TC-S-04 | COVERED: domain and API tests passed |
| AC-5 | "Invalid requests and duplicate drops return clear results without corrupting enrollment, capacity, or waitlist state." | Behavioural | TC-D-19, 20, 22, 26, 27, 32, 36, 37; TC-C-03, 04; TC-A-07, 08, 09, 18; TC-S-02 | COVERED: domain and API tests passed |
| AC-6 | "Tests cover available capacity, full capacity, duplicate requests, FIFO promotion, prerequisite failure, and invalid or repeated drops. If the starter uses concurrent requests, preserve its transaction or locking pattern and add a focused concurrency test where practical." | Behavioural (tests must exist and run) | Table A.1; RV-07 | COVERED: all six named scenarios have passing tests |
| AC-7 | "Use AI for at least one engineering activity. Record the prompts used, relevant AI response or summary, whether each suggestion was accepted, edited, or rejected, and how the resulting work was verified." | Documentation | RV-01, RV-11 | EVIDENCED TO DATE |

### A.1 Scenarios named in AC-6

| Scenario | Tests | Status |
|----------|-------|--------|
| Available capacity | TC-D-01, 02, 03 | PASSED |
| Full capacity | TC-D-04 | PASSED |
| Duplicate requests | TC-D-05, 06 | PASSED |
| FIFO promotion | TC-D-11, 12 | PASSED |
| Prerequisite failure | TC-D-07, 08, 09 | PASSED |
| Invalid or repeated drops | TC-D-19, 20, 22 | PASSED |
| Concurrency (conditional clause, not triggered: no starter) | TC-C-01, 02, 03, 04, 05, 06; TC-A-19, 20; TC-S-07 | PASSED; added by design decision, see DESIGN.md section 6 |

## B. Requirements

REQ-01 to REQ-14 decompose AC-1 to AC-6 into single testable statements; the
verbatim text in section A is the authority.

Component abbreviations: `CO` = `CourseOffering`, `L` = `Learner`, `C` = `Course`.
Test id prefixes: `TC-D` domain, `TC-C` concurrency, `TC-A` API, `TC-S` service.

### B.1 Behavioural requirements

| ID | Origin | Requirement | Component | Tests | Status |
|----|--------|-------------|-----------|-------|--------|
| REQ-01 | BRIEF AC-1 | Enrollment succeeds when capacity remains and the learner satisfies the prerequisite rule. | `CO.enroll`, `L.isEligibleFor`; `OfferingController.enroll` | TC-D-01, 02, 03, 31; TC-C-01; TC-A-01, 19 | COVERED |
| REQ-02 | BRIEF problem statement | A learner may enroll only once. | `CO.enroll` | TC-D-05, 23; TC-C-02 | COVERED |
| REQ-03 | BRIEF AC-2 | When the offering is full, an eligible learner joins the waitlist. | `CO.enroll` | TC-D-04; TC-C-01; TC-A-02, 19 | COVERED |
| REQ-04 | BRIEF AC-2 | A learner joins the waitlist only once. | `CO.enroll` | TC-D-06, 35; TC-C-02; TC-A-04 | COVERED |
| REQ-05 | BRIEF AC-2 | Duplicate enrollment requests do not create duplicate records. | `CO.enroll` | TC-D-05, 06, 25, 31, 35; TC-C-02; TC-A-03 | COVERED |
| REQ-06 | BRIEF AC-3 | Dropping an enrolled learner opens a seat. | `CO.drop` | TC-D-10, 11; TC-A-06 | COVERED |
| REQ-07 | BRIEF AC-3 | A drop promotes the earliest eligible waitlisted learner (FIFO). | `CO.drop`, promotion pass | TC-D-11, 12, 23, 25, 33; TC-C-03; TC-A-06, 12 | COVERED |
| REQ-08 | BRIEF AC-4 | An ineligible learner is not enrolled, and is not waitlisted. | `CO.enroll`, `L.isEligibleFor` | TC-D-07, 08, 09; TC-A-05 | COVERED |
| REQ-09 | BRIEF AC-4 | An ineligible learner is not promoted. | promotion pass | TC-D-13, 15; TC-A-14 | COVERED |
| REQ-10 | BRIEF AC-4, problem statement | The documented policy (skipped, retained in original position) is applied consistently. | promotion pass | TC-D-13, 14, 16, 18; TC-A-14 | COVERED |
| REQ-11 | BRIEF AC-5 | Invalid requests return clear results without corrupting enrollment, capacity, or waitlist state. | `CO`, `Require`, result types; request validation and `ApiExceptionHandler` | TC-D-20, 26, 27, 32, 36, 37; TC-C-04; TC-A-05, 08, 09, 16, 18, 21; TC-S-02 | COVERED |
| REQ-12 | BRIEF AC-5 | Duplicate drops return clear results without corrupting state. | `CO.drop`, `DropResult`; `OfferingController.drop` | TC-D-19, 22, 37; TC-C-03; TC-A-07 | COVERED |
| REQ-13 | BRIEF AC-6 | Tests cover the six named scenarios. | Test suite | TC-D-01, 02, 03, 04, 05, 06, 07, 08, 09, 11, 12, 19, 20, 22; summarised by scenario in Table A.1; RV-07 | COVERED |
| REQ-14 | BRIEF AC-6 (conditional) | Preserve the starter's locking pattern and add a focused concurrency test where practical. Condition not triggered (no starter); concurrency tests added by design decision. | `CO` lock, `L` lock; no shared state added by the service or API | TC-C-01, 02, 03, 04, 05, 06; TC-A-19, 20; TC-S-07 | COVERED |
| REQ-20 | BRIEF deliverables | Runnable code for the scoped feature and focused automated tests. | Whole project | TC-A-01, 13; TC-S-01; supported by every other passing test and a live run of the application | COVERED |
| REQ-27 | DEV | Eligible waitlisted learners take an open seat before any new requester. | `CO.enroll`, promotion pass | TC-D-17, 18, 32, 33, 34; TC-A-15 | COVERED |
| REQ-28 | DEV | A waitlisted learner can leave the waitlist without affecting enrolled learners or triggering promotion. | `CO.drop`; `OfferingController.drop` | TC-D-21, 22; TC-A-11 | COVERED |
| REQ-29 | DEV | Duplicate enrollment and duplicate waitlist requests return HTTP 409. | `OfferingController`, `ApiExceptionHandler` | TC-A-03, 04 | COVERED |
| REQ-30 | DEV | Prerequisites are checked at enrollment and at promotion; revocation does not remove enrolled learners. | `CO`, `L` | TC-D-16, 24, 28; TC-C-05; TC-A-14, 17; TC-S-04 | COVERED |
| REQ-31 | DEV | If no eligible waiter exists, the seat stays vacant. | promotion pass | TC-D-15 | COVERED |
| REQ-36 | DEV | `Course` is immutable, and identifiers are plain strings validated as non-blank. | `C`, `L`, `CO`, `Require` | TC-D-26, 27, 28, 29; TC-S-06 | COVERED |
| REQ-37 | DEV | `CourseOffering` owns the enrollment and waitlist state: it changes only through `enroll` and `drop`, and can be read as one consistent, immutable snapshot. | `CO.snapshot`, `OfferingSnapshot`; `EnrollmentService.offeringState` | TC-D-30, 38; TC-C-06; TC-A-12; TC-S-03, 05 | COVERED |
| REQ-38 | DEV | Error responses use Spring `ProblemDetail` (`application/problem+json`) with a stable `code`. | `ApiExceptionHandler` | TC-A-09, 18, 21 | COVERED |
| REQ-39 | DEV | The API uses immutable request and response types and never serializes a domain object or exposes internal mutable state. | Request and response records in `api`; `OfferingView`, `LearnerView` | TC-A-12, 16, 18, 22; TC-S-05 | COVERED |
| REQ-40 | DEV | Learners and their completed courses can be registered and changed through the API, held in an in-memory directory with one object per learner. | `LearnerDirectory`, `EnrollmentService`, `LearnerController` | TC-A-16, 17, 20; TC-S-03, 04, 07 | COVERED |

After Step 2 every behavioural requirement is COVERED: each has executed,
passing tests, at the API level as well as the domain level where both apply.

### B.2 Documentation and scope requirements

| ID | Origin | Requirement | Review check | Status |
|----|--------|-------------|--------------|--------|
| REQ-15 | BRIEF AC-7 | AI is used for at least one activity, with prompts, response summaries, accept/edit/reject decisions, and verification recorded. | RV-01 | EVIDENCED TO DATE |
| REQ-16 | BRIEF problem statement, candidate expectations | The prerequisite and waitlist assumptions are stated, including what happens when an ineligible learner reaches the front of the queue. | RV-02 | COVERED |
| REQ-17 | BRIEF problem statement, scope | One course offering only, with an in-memory store. | RV-05, RV-13 | EVIDENCED TO DATE |
| REQ-18 | BRIEF scope | No frontend, course catalog, payments, identity integrations, multi-term registration, deployment, or new database. | RV-05, RV-10 | EVIDENCED TO DATE |
| REQ-19 | BRIEF scope | No configurable policy engine. | RV-10 | EVIDENCED TO DATE |
| REQ-21 | BRIEF deliverables | Design specification covering service boundary, data/API design, enrollment and capacity invariants, waitlist-promotion rule, concurrency or persistence approach, and assumptions or tradeoffs. | RV-02 | COVERED |
| REQ-22 | BRIEF deliverables | AI record includes the files or decisions informed and verification evidence. | RV-01 | EVIDENCED TO DATE |
| REQ-23 | BRIEF deliverables | Setup and run instructions, including the commands used to execute the tests. | RV-03, RV-15 | COVERED |
| REQ-24 | BRIEF deliverables | Concise summary of limitations or follow-up risks. | RV-04 | COVERED |
| REQ-25 | BRIEF candidate expectations | No credentials, secrets, or unnecessary sensitive data in prompts or prompt records. | RV-06 | EVIDENCED TO DATE |
| REQ-26 | BRIEF timebox paragraph | Use the AI-Assisted scorecard in `Course-Registration-Rubric-Score.md`. | RV-08 | **BLOCKED**: the rubric file was not supplied with the brief and the developer has confirmed it is not available |
| REQ-32 | BRIEF candidate expectations | Start by understanding the supplied project and its existing interfaces, persistence, and test conventions; if no starter is supplied, use the minimal service or CLI fallback. | RV-09 | COVERED |
| REQ-33 | BRIEF scope | Do not implement separate greenfield, brownfield, and ambiguous scenarios. | RV-10 | EVIDENCED TO DATE |
| REQ-34 | BRIEF candidate expectations | Independently review and verify AI-generated suggestions; be candid about what AI contributed and what was changed or rejected. | RV-11 | PARTIAL: verification is evidenced (the developer ran the full suite independently on Windows 10 with OpenJDK 17.0.21, 72 passed); independent code review by the developer is not (the only review beyond Claude Code's own is ChatGPT's review of five files, which is not the developer's review, and no review by the developer personally is reported) |
| REQ-35 | BRIEF candidate expectations | Submit the implementation and all listed deliverables together. | RV-12, RV-15 | PARTIAL: not yet submitted. Steps 0 to 3 are pushed to `feature/initial-setup` (latest verified commit `86ff1fa`), but the repository is private and the evaluator does not yet have access, and this final documentation update is not yet committed or pushed |

## C. Automated tests (AI-DERIVED)

### C.1 Domain tests

Test class `CourseOfferingTest` unless stated (`LearnerTest`, `CourseTest`, `ResultTypesTest`). The method name begins with the
id, for example `tcD01_...`.

| ID | Scenario | Requirements | Status |
|----|----------|--------------|--------|
| TC-D-01 | Eligible learner enrolls while seats remain. | REQ-01, 13 | PASSED |
| TC-D-02 | Course with no prerequisites admits any learner. | REQ-01, 13 | PASSED |
| TC-D-03 | Enrollment fills exactly to capacity; the last seat is granted. | REQ-01, 13 | PASSED |
| TC-D-04 | Full offering: eligible learner is waitlisted; enrolled count unchanged. | REQ-03, 13 | PASSED |
| TC-D-05 | Enrolled learner enrolls again: `ALREADY_ENROLLED`, state unchanged. | REQ-02, 05, 13 | PASSED |
| TC-D-06 | Waitlisted learner enrolls again: `ALREADY_WAITLISTED`, position and size unchanged. | REQ-04, 05, 13 | PASSED |
| TC-D-07 | Ineligible learner with a seat free: rejected, not enrolled, not waitlisted. | REQ-08, 13 | PASSED |
| TC-D-08 | Ineligible learner when full: rejected, not waitlisted. | REQ-08, 13 | PASSED |
| TC-D-09 | Learner with only some of several prerequisites is ineligible. | REQ-08, 13 | PASSED |
| TC-D-10 | Drop with an empty waitlist: `DROPPED`, one seat free. | REQ-06 | PASSED |
| TC-D-11 | Drop promotes the head of the waitlist; result names the promoted learner. | REQ-06, 07, 13 | PASSED |
| TC-D-12 | A, B, C waitlisted in order; successive drops promote A, then B, then C. | REQ-07, 13 | PASSED |
| TC-D-13 | Ineligible head is skipped; next eligible learner promoted; skipped learner keeps position. | REQ-09, 10 | PASSED |
| TC-D-14 | Several ineligible learners skipped; order of all remaining waiters preserved. | REQ-10 | PASSED |
| TC-D-15 | All waiters ineligible: seat stays vacant, waitlist unchanged. | REQ-09, 31 | PASSED |
| TC-D-16 | Regained eligibility: a skipped learner whose prerequisite is restored is promoted on the next drop, ahead of later arrivals. | REQ-10, 30 | PASSED |
| TC-D-17 | Waitlist priority: offering with exactly one open seat, an eligible waiter, and a new eligible requester. The waiter gets the seat; the requester is waitlisted; the result names the promoted waiter. | REQ-27 | PASSED |
| TC-D-18 | Open seat, only ineligible waiters, new eligible requester: requester enrolled; waiters retained in order. | REQ-10, 27 | PASSED |
| TC-D-19 | Repeated drop: `NOT_ENROLLED`, state unchanged. | REQ-12, 13 | PASSED |
| TC-D-20 | Drop by a learner never enrolled or waitlisted: `NOT_ENROLLED`, state unchanged. | REQ-11, 13 | PASSED |
| TC-D-21 | Waitlisted learner drops: `DROPPED_FROM_WAITLIST`; enrolled unchanged; remaining order kept. Second part: with a vacant seat and an eligible waiter, another waiter leaving promotes nobody. | REQ-28 | PASSED |
| TC-D-22 | Repeated waitlist drop: `NOT_ENROLLED`, state unchanged. | REQ-12, 13, 28 | PASSED |
| TC-D-23 | Learner who dropped can enroll again; joins the tail when the offering is full. | REQ-02, 07 | PASSED |
| TC-D-24 | Prerequisite revoked for an enrolled learner: learner stays enrolled, including after a later promotion pass triggered by a drop and by a new enrollment. | REQ-30 | PASSED |
| TC-D-25 | A promoted learner is removed from the waitlist and never appears in both collections. | REQ-05, 07 | PASSED |
| TC-D-26 | Invalid arguments (null learner, null or blank id): rejected, state unchanged. | REQ-11, 36 | PASSED |
| TC-D-27 | Construction rejects capacity below 1, null course, and blank ids. | REQ-11, 36 | PASSED |
| TC-D-28 | `Learner`: mark and revoke completions; eligible only with all prerequisites. (`LearnerTest`) | REQ-30, 36 | PASSED |
| TC-D-29 | `Course` is immutable and rejects blank codes. (`CourseTest`) | REQ-36 | PASSED |
| TC-D-30 | State snapshots cannot be used to modify the offering. | REQ-37 | PASSED |
| TC-D-31 | After each step of a mixed sequence, enrolled never exceeds capacity and the two collections are disjoint. | REQ-01, 05 | PASSED |
| TC-D-32 | Open-seat policy: duplicate requests, rejected requests, and invalid drops do not fill an open seat; a waiter who regained eligibility keeps waiting. | REQ-11, 27 | PASSED |
| TC-D-33 | Open-seat policy: a drop fills every open seat from the waitlist in FIFO order, including a seat left vacant earlier. | REQ-07, 27 | PASSED |
| TC-D-34 | Two open seats, one eligible waiter, new eligible requester: the waiter is promoted and the requester is also enrolled; the result reports the promotion. (Review finding T3) | REQ-27 | PASSED |
| TC-D-35 | Waitlisted learner whose prerequisite was revoked requests again: `ALREADY_WAITLISTED`, not a prerequisite failure; position kept. (Review finding T4) | REQ-04, 05 | PASSED |
| TC-D-36 | `EnrollmentResult` rejects contradictory values (promotions with an outcome that changes nothing) and malformed values; its list is immutable. (`ResultTypesTest`; review findings F3, T6) | REQ-11 | PASSED |
| TC-D-37 | `DropResult` rejects contradictory values (promotions without `DROPPED`) and malformed values; its list is immutable. (`ResultTypesTest`; review findings F3, T6) | REQ-11, 12 | PASSED |
| TC-D-38 | `snapshot()` returns enrolled, waitlist, and free seats together; it is immutable, does not follow later changes, and cannot be built in a contradictory state. (Review finding F1) | REQ-37 | PASSED |

### C.2 Concurrency tests

Test class `CourseOfferingConcurrencyTest`. Every task has its own thread and
all are released together from a start gate. TC-C-01 to TC-C-04 run 25 rounds
each, TC-C-06 runs 5 longer rounds, and TC-C-05 is one long run. With correct
code these tests always pass; their power to detect a missing lock is
probabilistic and is reported in `DESIGN.md`, limitation L6.

| ID | Scenario | Requirements | Status |
|----|----------|--------------|--------|
| TC-C-01 | 40 distinct learners enroll at once into 5 seats: exactly 5 enrolled, 35 waitlisted, nobody twice. | REQ-01, 03, 14 | PASSED |
| TC-C-02 | 32 simultaneous requests for the same learner: exactly one succeeds (enrolled case and waitlisted case), the rest get the duplicate outcome. | REQ-02, 04, 05, 14 | PASSED |
| TC-C-03 | 8 enrolled learners are each dropped by 4 threads at once, with 12 waiting: each is dropped exactly once, each drop promotes exactly one waiter, exactly the first 8 waiters are promoted, and the other 4 keep their FIFO order. (Strengthened: review finding T2) | REQ-07, 12, 14 | PASSED |
| TC-C-04 | 40 learners enroll at once and half of them drop: invariants hold, exactly the others remain, no seat is vacant while anyone waits. | REQ-11, 14 | PASSED |
| TC-C-05 | Eligibility is checked against one consistent set of completions: while a writer moves a learner between two prerequisites without ever holding both, 800,000 concurrent checks never report the learner eligible. (Review finding F2) | REQ-14, 30 | PASSED |
| TC-C-06 | While 12 writers enroll and drop repeatedly, every snapshot taken by 4 readers is internally consistent: no learner in both lists, seat count agrees with the enrolled list, nobody waiting while a seat is free. (Review finding F1) | REQ-14, 37 | PASSED |

### C.3 API tests

Test class `OfferingApiTest` unless stated. They run through the full Spring
MVC stack with MockMvc, and each test starts from a fresh application context
(empty offering, no learners). Every test asserts the response and then the
offering state through `GET /api/offering`. TC-A-19 uses a real HTTP port.

| ID | Scenario | Requirements | Status |
|----|----------|--------------|--------|
| TC-A-01 | Enroll with a seat free: 200, status `ENROLLED`; the state query shows the learner. | REQ-01, 20 | PASSED |
| TC-A-02 | Enroll when full: 200, status `WAITLISTED`; enrolled list unchanged. | REQ-03 | PASSED |
| TC-A-03 | Enrolled learner enrolls again: 409 `ALREADY_ENROLLED`; state unchanged. | REQ-05, 29 | PASSED |
| TC-A-04 | Waitlisted learner enrolls again: 409 `ALREADY_WAITLISTED`; position unchanged. | REQ-04, 29 | PASSED |
| TC-A-05 | Ineligible learner, with a seat free and when full: 422 `PREREQUISITES_NOT_MET`; not enrolled, not waitlisted. | REQ-08, 11 | PASSED |
| TC-A-06 | Drop: 200 `DROPPED`, reports the promoted learner; the state shows the promotion; a drop with nobody waiting reports none. | REQ-06, 07 | PASSED |
| TC-A-07 | Repeated drop, and drop by a registered learner never enrolled: 409 `NOT_ENROLLED`; state unchanged. | REQ-12 | PASSED |
| TC-A-08 | Unknown learner on enroll and on drop: 404 `LEARNER_NOT_FOUND`; state unchanged; the learner is not registered as a side effect. (Resolved: applies, because the service holds learner state.) | REQ-11 | PASSED |
| TC-A-09 | Missing body, missing, null, blank or malformed id, over-long id, malformed JSON, invalid path id: 400 `INVALID_REQUEST`; wrong content type: 415; state unchanged. | REQ-11, 38 | PASSED |
| TC-A-10 | *Converted to review check RV-13 by developer decision.* Originally "unknown offering returns a clear error". The API has one configured offering and no offering id, so there is no such request to test, and no registry or artificial endpoint was added for it. | REQ-17 (via RV-13) | NOT AN AUTOMATED TEST |
| TC-A-11 | Waitlisted learner leaves through the API: 200 `DROPPED_FROM_WAITLIST`, nobody promoted, enrolled unchanged; leaving twice is 409. | REQ-28 | PASSED |
| TC-A-12 | State query returns the configured offering, FIFO order after a sequence, and exactly the documented fields. | REQ-07, 37, 39 | PASSED |
| TC-A-13 | Application context starts. (`CourseWaitlistApplicationTests.contextLoads`) | REQ-20 | PASSED |
| TC-A-14 | Skip-and-retain through the API: revoke the head waiter's completion, drop, the next learner is promoted and the skipped learner keeps position, then is promoted after the completion is restored. Revoking an enrolled learner's completion does not remove them. | REQ-09, 10, 30 | PASSED |
| TC-A-15 | Waitlist priority through the API: with one open seat and an eligible waiter, a new request is waitlisted and reports the promoted waiter. | REQ-27 | PASSED |
| TC-A-16 | Learner registration: 201 with the documented fields; duplicate id 409 and the original learner intact; invalid input 400 and nothing registered. (`LearnerApiTest`) | REQ-11, 39, 40 | PASSED |
| TC-A-17 | Mark and revoke completion: 204, repeatable, and they change eligibility; unknown learner 404; invalid values 400. (`LearnerApiTest`) | REQ-30, 40 | PASSED |
| TC-A-18 | Every error, including framework errors and an unexpected failure, is an `application/problem+json` document with status, title, detail and `code`, and reveals no class name, stack trace, or internal message. | REQ-11, 38, 39 | PASSED |
| TC-A-19 | Over real HTTP, 12 rounds: 32 learners enroll at once (exactly 2 enrolled, 30 waitlisted, responses agree with the state), then all drop at once (every drop succeeds once, offering ends empty), while 4 readers check every state read for consistency. (`EnrollmentApiConcurrencyTest`) | REQ-01, 03, 14 | PASSED |
| TC-A-20 | Over real HTTP, 15 rounds: 24 simultaneous registrations of the same learner id. Exactly one is 201, the other 23 are 409 `LEARNER_ALREADY_EXISTS`, nothing else is returned, and the learner that exists has the winner's completions (shown by whether it can enroll). (`EnrollmentApiConcurrencyTest`; review finding S2-8) | REQ-14, 40 | PASSED |
| TC-A-21 | A JSON number, boolean, array, or object where text is expected (learner id, name, course codes) is rejected with 400 `INVALID_REQUEST` and registers or enrolls nothing; the string id `"5"` still works; ids containing `.`, `_`, `-` work in bodies and paths. (`LearnerApiTest`; review finding S2-7) | REQ-11, 38 | PASSED |
| TC-A-22 | `RegisterLearnerRequest` copies its course list: changing the original list cannot change the request, the copy is unmodifiable, an omitted list is empty, and a null element is kept for validation to reject. (`RegisterLearnerRequestTest`, plain JUnit; review finding S2-12) | REQ-39 | PASSED |

### C.4 Service tests

Test class `EnrollmentServiceTest` unless stated; plain JUnit, no Spring
context.

| ID | Scenario | Requirements | Status |
|----|----------|--------------|--------|
| TC-S-01 | Service enroll and drop resolve the registered learner and return the domain result. | REQ-20 | PASSED |
| TC-S-02 | An unknown learner is rejected by every service operation; state unchanged; nothing registered as a side effect. | REQ-11 | PASSED |
| TC-S-03 | Registering an id twice is rejected and the original `Learner` object, name, and completions are kept. | REQ-37, 40 | PASSED |
| TC-S-04 | A completion change made through the service is seen at the next promotion; marking a completion does not itself fill a seat (L1). | REQ-30, 40 | PASSED |
| TC-S-05 | The offering view matches the domain snapshot, is immutable, and does not follow later changes. | REQ-37, 39 | PASSED |
| TC-S-06 | The single offering is built from configuration; capacity below 1, missing configuration, or a self-prerequisite stops startup. (`OfferingConfigurationTest`) | REQ-36 | PASSED |
| TC-S-07 | 2,000 rounds: 24 threads register the same learner id at once with different data. Exactly one succeeds, 23 get the duplicate result, no other exception occurs, the directory holds exactly one learner, and it carries the winner's name and completions unmixed. Bounded by 10-second timeouts. (Review finding S2-8) | REQ-14, 40 | PASSED |

## D. Review checks (AI-DERIVED)

Evidence is recorded by the AI unless stated. "Verified by developer" appears
only where the developer has explicitly confirmed a check.

| ID | Check | Requirements | Status | Evidence at this commit |
|----|-------|--------------|--------|-------------------------|
| RV-01 | `docs/AI_USAGE.md` has prompts, output summaries, developer decisions, files informed, and verification for every AI activity. | REQ-15, 22 | EVIDENCED TO DATE | `docs/AI_USAGE.md` entries 1-25 cover every Claude Code session activity to this point, with an index at the top and a stop-hook table listing the fifteen automated messages received up to Entry 25. A section "ChatGPT's role" records the developer's statement of what ChatGPT contributed. The record contains no ChatGPT prompts or outputs, so for ChatGPT the brief's "prompts used" and "response or summary" are not available here. |
| RV-02 | `docs/DESIGN.md` covers every topic the brief lists and states the assumptions. | REQ-16, 21 | COVERED | `docs/DESIGN.md` sections 1-8: service boundary (1, 7.1), data and API design (3, 7), invariants (4), promotion rule (5), concurrency and persistence (6), assumptions and tradeoffs (2, 7.5, 8). The ineligible-at-front policy is W3 and W4 in section 2. |
| RV-03 | README setup and test commands are present and were actually run. | REQ-23 | COVERED | `README.md` "Setup and run" and "Using the API". Verified by AI: `./mvnw -B test` (72 passed, also from fresh clones) and `./mvnw -B spring-boot:run` with the README's `curl` calls, on Linux with JDK 21. Verified by developer: `mvnw.cmd -B test` on Windows 10 with OpenJDK 17.0.21, 72 passed. Not reported: `mvnw.cmd spring-boot:run` on Windows. |
| RV-04 | Limitations and follow-up risks are written up. | REQ-24 | COVERED | Concise summary in `README.md`, "Limitations and follow-up risks"; full list in `docs/DESIGN.md` section 8 (L1-L11; L11 recorded as resolved). |
| RV-05 | Dependencies and source tree hold one offering, in-memory state, and none of the excluded items. | REQ-17, 18 | EVIDENCED TO DATE | `git diff --stat 33e081e -- pom.xml src` is empty and `./mvnw dependency:tree` shows only the web, validation, and test starters, unchanged since Step 0. State is held in `CourseOffering` and `LearnerDirectory` objects; a search of `src` and `pom.xml` for JDBC, JPA, data-source, cache, and security code found none. |
| RV-06 | No credentials or secrets in the repository or prompt records. | REQ-25 | EVIDENCED TO DATE | Step 3 scan of all tracked files for credential keywords, key and token patterns, URLs with embedded credentials, and email addresses: no credential and no email address. The only keyword matches are the words used in documentation rules and in this table, and the Maven wrapper scripts' own optional `MVNW_PASSWORD` handling. |
| RV-07 | Every scenario named in AC-6 maps to at least one executed, passing test. | REQ-13 | COVERED | Table A.1; surefire reports from the run recorded at the top of this file. |
| RV-08 | Rubric self-rating completed. | REQ-26 | BLOCKED | `Course-Registration-Rubric-Score.md` was not supplied with the brief, and the developer confirmed in Step 3 that it is not available. No rating has been written and the rubric's criteria have not been guessed. |
| RV-09 | The existing project was inspected before implementation; the minimal-service fallback is justified. | REQ-32 | COVERED | Repository inspected by command before Step 0 (empty) and before Step 1 (Step 0 skeleton only); see the development log. Fallback justification: `docs/DESIGN.md` section 1. |
| RV-10 | No separate greenfield, brownfield, or ambiguous scenarios; no frontend, policy engine, or excluded infrastructure. | REQ-18, 19, 33 | EVIDENCED TO DATE | One scenario is implemented: the single-offering slice. Tracked files are 41 Java files, one `application.yaml`, five Markdown documents, `pom.xml`, and the Maven wrapper: no frontend, container, CI, deployment, or SQL files (Step 3 scan). Policies are fixed in `CourseOffering` and `Learner`; the only configuration is the offering's own data, not its rules. The learner endpoints are an in-memory store needed for promotion-time eligibility, not an identity integration or a course catalog. Step 3 added no code. |
| RV-11 | Independent AI review: what AI contributed, what the developer accepted, modified, or rejected, and how changes were independently verified. | REQ-34 | PARTIAL | What AI contributed and what was accepted, modified, or rejected is recorded per entry in `docs/AI_USAGE.md`; seven AI recommendations were modified or rejected. **Verified by developer:** the full test suite on Windows 10 with OpenJDK 17.0.21, 72 passed, run independently (reported 2026-11-17; screenshot not seen by the AI). **Reviewed by ChatGPT** (reported by the developer): `CourseOffering.java`, `CourseOfferingTest.java`, `DESIGN.md`, `Learner.java`, `CourseOfferingConcurrencyTest.java`; conclusion PASS, no mandatory code change; ChatGPT did not run the tests. ChatGPT's review is not classified as the developer's independent code review. **Limits, and the reason this stays PARTIAL:** no review of the code by the developer personally is reported; files outside these five were not independently reviewed; the Step 3 acceptance was ChatGPT's, based on Claude's reports. All other verification in the record is Claude Code checking its own work. |
| RV-12 | Implementation and all deliverables are submitted together. | REQ-35 | PARTIAL | Not yet submitted, as of 2026-11-17. Every deliverable the brief lists is linked from `README.md`, "How to review this submission", except the rubric self-rating (blocked). Steps 0 to 3 are pushed to `feature/initial-setup`: `git ls-remote` and a fresh clone on 2026-11-17 showed the branch at `86ff1fa836958df3a95355c562bf089be6dd9a55`. Submission is to be by a link to that branch, by developer decision; no `main` branch or pull request. **Remaining actions, each on the developer's decision:** (1) approve, commit, and push the final documentation update that contains this row; (2) give the evaluator access to the repository, which was private on 2026-11-17, or make it public; (3) send the evaluator the link. |
| RV-13 | Single offering: the API has one configured offering, no offering id, and no offering registry. (Replaces TC-A-10 by developer decision.) | REQ-17 | COVERED | No path in `OfferingController` or `LearnerController` carries an offering id. One `CourseOffering` bean is created in `OfferingConfiguration`; TC-S-06 asserts the context has a single such bean. TC-A-12 asserts `GET /api/offering` returns the configured offering. TC-A-18 asserts that paths carrying an offering id (`/api/offerings/CS201-A/...`) are not found. No class holds a collection of offerings. |
| RV-14 | The API never serializes a domain object. | REQ-39 | COVERED | Every controller method returns a record from the `api` package or nothing. The `api` package imports only `EnrollmentResult` and `DropResult` from the domain, both immutable records, and only to copy their values into responses; it does not reference `Learner`, `Course`, `CourseOffering`, or `OfferingSnapshot` (checked by search). TC-A-12 and TC-A-16 assert the exact set of JSON fields. |
| RV-15 | The submission builds and passes from a clean clone of the pushed branch. | REQ-23, 35 | COVERED | `git clone --branch feature/initial-setup` from GitHub into an empty directory, then `./mvnw -B test`: at `33e081e` on 2026-11-16 (53 files) and again at `86ff1fa` on 2026-11-17 after the Step 3 push (54 files): 72 run, 0 failures, both times. The existing Maven dependency cache was used both times; this is not an empty-cache build. |

## E. Coverage summary

Recomputed for the final submission update. No code or test has changed since
Step 2; the test figures are those of Step 2, re-run by the AI on 2026-11-16
and 2026-11-17 and run by the developer on Windows with Java 17.

| Measure | Count |
|---------|-------|
| Total requirements | 40 (25 behavioural, 15 documentation/scope) |
| Requirements by origin | 30 BRIEF, 10 DEV |
| Behavioural requirements covered by executed passing tests | 25 of 25 |
| Behavioural requirements partial or not covered | 0 |
| Documentation/scope requirements covered or evidenced to date | 12 of 15 |
| Documentation/scope requirements partial | 2 (REQ-34, 35) |
| Documentation/scope requirements blocked | 1 (REQ-26) |
| Requirements implemented in code (behavioural) | 25 of 25 |
| Automated tests planned | 72 (38 domain, 6 concurrency, 21 API, 7 service) |
| Tests implemented | 72 |
| Tests executed and passed | 72 |
| Tests failed | 0 |
| Tests not yet implemented | 0 |
| Tests implemented but not executed | 0 |
| Review checks | 15: 8 covered, 4 evidenced to date, 2 partial, 1 blocked |

TC-A-10 is not counted as an automated test: by developer decision it is
review check RV-13.

Every mapping in sections B.1 and C was checked in both directions by script:
each test listed by a requirement lists that requirement, and the reverse.
Each of the 71 test ids other than TC-A-13 was matched to a test method that
appears as passed in the surefire report of the AI's run recorded at the top;
TC-A-13 is `contextLoads`, which also passed.

### Changes in the final submission update

| Change | Reason |
|--------|--------|
| REQ-34 and RV-11: status stays PARTIAL; evidence added | The developer reported an independent Windows/Java 17 run of the full suite, which evidences verification. The five-file review was ChatGPT's and is recorded as that; it is not the developer's independent code review. A first draft of this update proposed EVIDENCED TO DATE; that was not accepted. |
| REQ-35 and RV-12: status stays PARTIAL; wording corrected | Steps 0 to 3 are now pushed, so the earlier "await commit and push" wording was stale. The assessment is still not submitted: the repository is private, the evaluator has no access, and this update is not yet committed or pushed. A first draft proposed EVIDENCED TO DATE; that was not accepted. |
| RV-03 and RV-15 evidence extended | The developer's Windows run of the test command; the clean-clone build repeated at `86ff1fa`. |
| RV-01 evidence | Entries 1-25; ChatGPT's role recorded as stated by the developer. |
| REQ-26 and RV-08 | Unchanged: BLOCKED. |
| No test or requirement added or removed; no status changed | Documentation only. |

Earlier changes are recorded in `DEVELOPMENT_LOG.md` and in the git history
of this file.

### Acceptance criteria

| AC | Status |
|----|--------|
| AC-1 to AC-5 | Each has executed, passing tests at both the domain and the API level. |
| AC-6 | All six named scenarios have executed, passing tests. Concurrency tests added although the clause is not triggered. |
| AC-7 | Evidenced by review (RV-01) for Claude Code. ChatGPT's contribution is recorded as the developer stated it, without its prompts or outputs. |

### Outstanding gaps

- **BLOCKED:** REQ-26 — the rubric file `Course-Registration-Rubric-Score.md` is not available. No self-rating has been written.
- **PARTIAL:** REQ-34 — the developer's test run is recorded; an independent code review by the developer is not. ChatGPT reviewed five files.
- **PARTIAL:** REQ-35 — not yet submitted. Remaining: commit and push this update; evaluator access to the private repository; sending the link.
- **Limits of the evidence, not failed checks:**
  - ChatGPT's prompts and outputs are not in the repository.
  - `mvnw.cmd spring-boot:run` on Windows and the application on a Java 17 runtime were not reported.
  - No build with an empty Maven dependency cache.
