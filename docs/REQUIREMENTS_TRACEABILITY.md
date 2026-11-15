# Requirements Traceability

Master checklist of every requirement and test for this assessment, and the
matrix linking requirement, implementation, test, and result.

**Last updated:** Step 1 (domain model) after the code-quality review corrections, 2026-11-15.
**Last test run recorded here:** `./mvnw -B test` on 2026-11-15, OpenJDK 21.0.12 —
45 run, 0 failed, 0 errors, 0 skipped. Verified by AI. **Verified by developer:
none recorded.**

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
| Tests | NOT IMPLEMENTED, NOT EXECUTED, PASSED, FAILED |
| Review checks | OPEN, EVIDENCED TO DATE (true at this commit, re-checked at each commit), PARTIAL, BLOCKED |
| Requirements | NOT COVERED, PARTIAL, COVERED, BLOCKED |

The brief's suggested timebox of 7-8 hours is treated as guidance, not as a
requirement, by the developer's decision. It has no row here.

## A. Acceptance criteria (BRIEF, verbatim)

| ID | Text from the brief | Type | Tests / evidence | Status |
|----|---------------------|------|------------------|--------|
| AC-1 | "Enrollment succeeds when capacity remains and the learner satisfies the stated prerequisite rule." | Behavioural | TC-D-01, 02, 03, 31; TC-C-01; TC-A-01 | COVERED at domain level; API test pending |
| AC-2 | "When full, an eligible learner joins the waitlist once; duplicate enrollment requests do not create duplicate records." | Behavioural | TC-D-04, 05, 06, 25, 31, 35; TC-C-01, 02; TC-A-02, 03, 04 | COVERED at domain level; API tests pending |
| AC-3 | "Dropping an enrolled learner opens a seat and promotes the earliest eligible waitlisted learner according to the documented eligibility policy." | Behavioural | TC-D-10, 11, 12, 23, 33; TC-C-03; TC-A-06, 12 | COVERED at domain level; API tests pending |
| AC-4 | "An ineligible learner is not enrolled or promoted. The documented policy for skipping or retaining that learner is applied consistently." | Behavioural | TC-D-07, 08, 09, 13, 14, 15, 16, 18; TC-A-05 | COVERED at domain level; API test pending |
| AC-5 | "Invalid requests and duplicate drops return clear results without corrupting enrollment, capacity, or waitlist state." | Behavioural | TC-D-19, 20, 22, 26, 27, 32, 36, 37; TC-C-03, 04; TC-A-07, 08, 09, 10 | COVERED at domain level; API tests pending |
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
| Concurrency (conditional clause, not triggered: no starter) | TC-C-01, 02, 03, 04, 05, 06 | PASSED; added by design decision, see DESIGN.md section 6 |

## B. Requirements

REQ-01 to REQ-14 decompose AC-1 to AC-6 into single testable statements; the
verbatim text in section A is the authority.

Component abbreviations: `CO` = `CourseOffering`, `L` = `Learner`, `C` = `Course`.

### B.1 Behavioural requirements

| ID | Origin | Requirement | Component | Tests | Status |
|----|--------|-------------|-----------|-------|--------|
| REQ-01 | BRIEF AC-1 | Enrollment succeeds when capacity remains and the learner satisfies the prerequisite rule. | `CO.enroll`, `L.isEligibleFor` | TC-D-01, 02, 03, 31; TC-C-01; TC-A-01 | COVERED (domain) |
| REQ-02 | BRIEF problem statement | A learner may enroll only once. | `CO.enroll` | TC-D-05, 23; TC-C-02 | COVERED (domain) |
| REQ-03 | BRIEF AC-2 | When the offering is full, an eligible learner joins the waitlist. | `CO.enroll` | TC-D-04; TC-C-01; TC-A-02 | COVERED (domain) |
| REQ-04 | BRIEF AC-2 | A learner joins the waitlist only once. | `CO.enroll` | TC-D-06, 35; TC-C-02; TC-A-04 | COVERED (domain) |
| REQ-05 | BRIEF AC-2 | Duplicate enrollment requests do not create duplicate records. | `CO.enroll` | TC-D-05, 06, 25, 31, 35; TC-C-02; TC-A-03 | COVERED (domain) |
| REQ-06 | BRIEF AC-3 | Dropping an enrolled learner opens a seat. | `CO.drop` | TC-D-10, 11; TC-A-06 | COVERED (domain) |
| REQ-07 | BRIEF AC-3 | A drop promotes the earliest eligible waitlisted learner (FIFO). | `CO.drop`, promotion pass | TC-D-11, 12, 23, 25, 33; TC-C-03; TC-A-06, 12 | COVERED (domain) |
| REQ-08 | BRIEF AC-4 | An ineligible learner is not enrolled, and is not waitlisted. | `CO.enroll`, `L.isEligibleFor` | TC-D-07, 08, 09; TC-A-05 | COVERED (domain) |
| REQ-09 | BRIEF AC-4 | An ineligible learner is not promoted. | promotion pass | TC-D-13, 15 | COVERED |
| REQ-10 | BRIEF AC-4, problem statement | The documented policy (skipped, retained in original position) is applied consistently. | promotion pass | TC-D-13, 14, 16, 18 | COVERED |
| REQ-11 | BRIEF AC-5 | Invalid requests return clear results without corrupting enrollment, capacity, or waitlist state. | `CO`, `Require`, result types; API validation (pending) | TC-D-20, 26, 27, 32, 36, 37; TC-C-04; TC-A-05, 08, 09, 10 | COVERED (domain) |
| REQ-12 | BRIEF AC-5 | Duplicate drops return clear results without corrupting state. | `CO.drop`, `DropResult` | TC-D-19, 22, 37; TC-C-03; TC-A-07 | COVERED (domain) |
| REQ-13 | BRIEF AC-6 | Tests cover the six named scenarios. | Test suite | TC-D-01, 02, 03, 04, 05, 06, 07, 08, 09, 11, 12, 19, 20, 22; summarised by scenario in Table A.1; RV-07 | COVERED |
| REQ-14 | BRIEF AC-6 (conditional) | Preserve the starter's locking pattern and add a focused concurrency test where practical. Condition not triggered (no starter); concurrency tests added by design decision. | `CO` lock, `L` lock | TC-C-01, 02, 03, 04, 05, 06 | COVERED |
| REQ-20 | BRIEF deliverables | Runnable code for the scoped feature and focused automated tests. | Whole project | TC-A-01, 13; supported by every other passing test | PARTIAL: domain built and tested; feature not yet reachable through the running service |
| REQ-27 | DEV | Eligible waitlisted learners take an open seat before any new requester. | `CO.enroll`, promotion pass | TC-D-17, 18, 32, 33, 34 | COVERED |
| REQ-28 | DEV | A waitlisted learner can leave the waitlist without affecting enrolled learners or triggering promotion. | `CO.drop` | TC-D-21, 22; TC-A-11 | COVERED (domain) |
| REQ-29 | DEV | Duplicate enrollment and duplicate waitlist requests return HTTP 409. | REST layer (pending) | TC-A-03, 04 | NOT COVERED: API step |
| REQ-30 | DEV | Prerequisites are checked at enrollment and at promotion; revocation does not remove enrolled learners. | `CO`, `L` | TC-D-16, 24, 28; TC-C-05 | COVERED |
| REQ-31 | DEV | If no eligible waiter exists, the seat stays vacant. | promotion pass | TC-D-15 | COVERED |
| REQ-36 | DEV | `Course` is immutable, and identifiers are plain strings validated as non-blank. | `C`, `L`, `CO`, `Require` | TC-D-26, 27, 28, 29 | COVERED |
| REQ-37 | DEV | `CourseOffering` owns the enrollment and waitlist state: it changes only through `enroll` and `drop`, and can be read as one consistent, immutable snapshot. | `CO.snapshot`, `OfferingSnapshot` | TC-D-30, 38; TC-C-06; TC-A-12 | COVERED (domain) |

"COVERED (domain)" means at least one executed, passing domain test covers the
requirement and one or more API tests for it are still NOT IMPLEMENTED.

### B.2 Documentation and scope requirements

| ID | Origin | Requirement | Review check | Status |
|----|--------|-------------|--------------|--------|
| REQ-15 | BRIEF AC-7 | AI is used for at least one activity, with prompts, response summaries, accept/edit/reject decisions, and verification recorded. | RV-01 | EVIDENCED TO DATE |
| REQ-16 | BRIEF problem statement, candidate expectations | The prerequisite and waitlist assumptions are stated, including what happens when an ineligible learner reaches the front of the queue. | RV-02 | COVERED |
| REQ-17 | BRIEF problem statement, scope | One course offering only, with an in-memory store. | RV-05 | EVIDENCED TO DATE |
| REQ-18 | BRIEF scope | No frontend, course catalog, payments, identity integrations, multi-term registration, deployment, or new database. | RV-05, RV-10 | EVIDENCED TO DATE |
| REQ-19 | BRIEF scope | No configurable policy engine. | RV-10 | EVIDENCED TO DATE |
| REQ-21 | BRIEF deliverables | Design specification covering service boundary, data/API design, enrollment and capacity invariants, waitlist-promotion rule, concurrency or persistence approach, and assumptions or tradeoffs. | RV-02 | PARTIAL: API design section pending |
| REQ-22 | BRIEF deliverables | AI record includes the files or decisions informed and verification evidence. | RV-01 | EVIDENCED TO DATE |
| REQ-23 | BRIEF deliverables | Setup and run instructions, including the commands used to execute the tests. | RV-03 | COVERED |
| REQ-24 | BRIEF deliverables | Concise summary of limitations or follow-up risks. | RV-04 | PARTIAL: limitations listed in DESIGN.md section 8; final summary pending |
| REQ-25 | BRIEF candidate expectations | No credentials, secrets, or unnecessary sensitive data in prompts or prompt records. | RV-06 | EVIDENCED TO DATE |
| REQ-26 | BRIEF timebox paragraph | Use the AI-Assisted scorecard in `Course-Registration-Rubric-Score.md`. | RV-08 | **BLOCKED**: the rubric file has not been provided |
| REQ-32 | BRIEF candidate expectations | Start by understanding the supplied project and its existing interfaces, persistence, and test conventions; if no starter is supplied, use the minimal service or CLI fallback. | RV-09 | COVERED |
| REQ-33 | BRIEF scope | Do not implement separate greenfield, brownfield, and ambiguous scenarios. | RV-10 | EVIDENCED TO DATE |
| REQ-34 | BRIEF candidate expectations | Independently review and verify AI-generated suggestions; be candid about what AI contributed and what was changed or rejected. | RV-11 | PARTIAL: no independent developer verification is recorded yet |
| REQ-35 | BRIEF candidate expectations | Submit the implementation and all listed deliverables together. | RV-12 | OPEN: final submission |

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

### C.3 API tests (later step; provisional)

Ids are fixed. Endpoints and status codes other than 409 are settled when the
API design is approved.

| ID | Scenario | Requirements | Status |
|----|----------|--------------|--------|
| TC-A-01 | Enroll request with capacity available returns success. | REQ-01, 20 | NOT IMPLEMENTED |
| TC-A-02 | Enroll request when full returns a waitlisted result. | REQ-03 | NOT IMPLEMENTED |
| TC-A-03 | Duplicate enrollment returns HTTP 409. | REQ-05, 29 | NOT IMPLEMENTED |
| TC-A-04 | Duplicate waitlist request returns HTTP 409. | REQ-04, 29 | NOT IMPLEMENTED |
| TC-A-05 | Prerequisite failure returns a clear error; state unchanged. | REQ-08, 11 | NOT IMPLEMENTED |
| TC-A-06 | Drop returns success and reports the promoted learner. | REQ-06, 07 | NOT IMPLEMENTED |
| TC-A-07 | Repeated drop returns a clear error; state unchanged. | REQ-12 | NOT IMPLEMENTED |
| TC-A-08 | Unknown learner returns a clear error. *Provisional: expected to apply, because promotion-time checks require the service to hold learner state; to be confirmed against the API design.* | REQ-11 | NOT IMPLEMENTED |
| TC-A-09 | Malformed or blank request returns a clear validation error. | REQ-11 | NOT IMPLEMENTED |
| TC-A-10 | Unknown offering returns a clear error. *Provisional: may not apply if the single-offering API carries no offering id (REQ-17). No registry will be added for this test; a replacement review check will be proposed to the developer before this row changes.* | REQ-11 | NOT IMPLEMENTED |
| TC-A-11 | Waitlisted learner leaves the waitlist through the API. | REQ-28 | NOT IMPLEMENTED |
| TC-A-12 | State query shows enrolled learners and the waitlist in FIFO order, read from one atomic snapshot. | REQ-07, 37 | NOT IMPLEMENTED |
| TC-A-13 | Application context starts. (`CourseWaitlistApplicationTests.contextLoads`) | REQ-20 | PASSED |

## D. Review checks (AI-DERIVED)

Evidence is recorded by the AI unless stated. "Verified by developer" appears
only where the developer has explicitly confirmed a check.

| ID | Check | Requirements | Status | Evidence at this commit |
|----|-------|--------------|--------|-------------------------|
| RV-01 | `docs/AI_USAGE.md` has prompts, output summaries, developer decisions, files informed, and verification for every AI activity. | REQ-15, 22 | EVIDENCED TO DATE | `docs/AI_USAGE.md` entries 1-10 cover every session activity to this point, including the code-quality review, its corrections, and the acceptance review. |
| RV-02 | `docs/DESIGN.md` covers every topic the brief lists and states the assumptions. | REQ-16, 21 | PARTIAL | Sections 1-6 and 8 written and updated after the code-quality review; assumptions and the ineligible-at-front policy are in section 2 (W3, W4). Section 7 (API) pending. |
| RV-03 | README setup and test commands are present and were actually run. | REQ-23 | COVERED | `README.md` "Setup and run". `./mvnw -B test` and `./mvnw -B spring-boot:run` were run (Verified by AI). `mvnw.cmd` not run on Windows. |
| RV-04 | Limitations and follow-up risks are written up. | REQ-24 | PARTIAL | `docs/DESIGN.md` section 8 (L1-L7). Final summary pending. |
| RV-05 | Dependencies and source tree hold one offering, in-memory state, and none of the excluded items. | REQ-17, 18 | EVIDENCED TO DATE | `git diff --stat HEAD -- pom.xml` is empty: dependencies unchanged since Step 0. `./mvnw dependency:tree` shows only the web, validation, and test starters. New source files are Java classes in the `domain` package only. |
| RV-06 | No credentials or secrets in the repository or prompt records. | REQ-25 | EVIDENCED TO DATE | Keyword search (`password`, `secret`, `api key`, `token`, `bearer`, `private key`) over the working tree: the only matches are the Maven wrapper scripts' own optional `MVNW_PASSWORD` handling and the words used in documentation rules. No values found. |
| RV-07 | Every scenario named in AC-6 maps to at least one executed, passing test. | REQ-13 | COVERED | Table A.1; surefire reports from the run recorded at the top of this file. |
| RV-08 | Rubric self-rating completed. | REQ-26 | BLOCKED | `Course-Registration-Rubric-Score.md` has not been provided. No rating has been written or guessed. |
| RV-09 | The existing project was inspected before implementation; the minimal-service fallback is justified. | REQ-32 | COVERED | Repository inspected by command before Step 0 (empty) and before Step 1 (Step 0 skeleton only); see the development log. Fallback justification: `docs/DESIGN.md` section 1. |
| RV-10 | No separate greenfield, brownfield, or ambiguous scenarios; no frontend, policy engine, or excluded infrastructure. | REQ-18, 19, 33 | EVIDENCED TO DATE | One scenario is implemented: the single-offering slice. Source tree contains only Java and `application.yaml`. Policies are fixed in `CourseOffering` and `Learner`; there is no rule configuration, strategy interface, or policy type. No Docker, CI, or deployment files. |
| RV-11 | Independent AI review: what AI contributed, what the developer accepted, modified, or rejected, and how changes were independently verified. | REQ-34 | PARTIAL | AI contributions and developer decisions are recorded per entry in `docs/AI_USAGE.md`. Verification so far is "Verified by AI": commands run in session, the AI's code-quality review of its own work (entry 8), and fault-injection checks (entries 7 and 9). These are the AI checking its own output and do not count as independent review. **Verified by developer: none recorded. This stays PARTIAL until the developer personally reviews and confirms.** |
| RV-12 | Implementation and all deliverables are submitted together. | REQ-35 | OPEN | Final submission. |

## E. Coverage summary

Recomputed after the code-quality review corrections, for the proposed Step 1
commit.

| Measure | Count |
|---------|-------|
| Total requirements | 37 (22 behavioural, 15 documentation/scope) |
| Requirements by origin | 30 BRIEF, 7 DEV |
| Behavioural requirements covered by executed passing tests | 20 of 22 |
| Behavioural requirements partial | 1 (REQ-20) |
| Behavioural requirements not covered | 1 (REQ-29, HTTP 409 mapping: API step) |
| Documentation/scope requirements covered or evidenced to date | 10 of 15 |
| Documentation/scope requirements partial | 3 (REQ-21, 24, 34) |
| Documentation/scope requirements open | 1 (REQ-35) |
| Documentation/scope requirements blocked | 1 (REQ-26) |
| Requirements implemented in code (behavioural) | 20 fully; REQ-20 partly; REQ-29 not yet |
| Automated tests planned | 57 (38 domain, 6 concurrency, 13 API) |
| Tests implemented | 45 |
| Tests executed and passed | 45 |
| Tests failed | 0 |
| Tests not yet implemented | 12 (TC-A-01 to TC-A-12) |
| Tests implemented but not executed | 0 |
| Review checks | 12: 3 covered, 4 evidenced to date, 3 partial, 1 open, 1 blocked |

Every mapping in sections B.1 and C was checked in both directions by script:
each test listed by a requirement lists that requirement, and the reverse.
Each of the 44 domain and concurrency ids was matched to a test method that
appears as passed in the surefire report of the run recorded at the top.

### Changes since the first Step 1 checklist

| Change | Reason |
|--------|--------|
| REQ-36 added (DEV) | The approved decisions "Course is immutable" and "string ids with non-blank validation" had no id. TC-D-29 was wrongly mapped to REQ-17. |
| REQ-37 added (DEV) | The approved decision "CourseOffering owns enrollment and waitlist state" had no id. Covers the atomic snapshot; TC-D-30 was loosely mapped to REQ-11. |
| TC-D-34 to TC-D-38, TC-C-05, TC-C-06 added | Code-quality review findings F1, F2, F3, T3, T4, T6. |
| TC-D-21, TC-D-24, TC-C-03 strengthened | Review findings T1, T5, T2. |
| REQ-13 and REQ-20 now name their tests; TC-D-02, 03, 08, 09 now cite REQ-13 | Review findings D1, D2, D4. |

### Acceptance criteria

| AC | Status |
|----|--------|
| AC-1 to AC-5 | Each has executed, passing domain tests. API-level tests pending. |
| AC-6 | All six named scenarios have executed, passing tests. Concurrency tests added although the clause is not triggered. |
| AC-7 | Evidenced to date by review (RV-01); developer verification not yet recorded (RV-11). |

### Outstanding gaps

- **BLOCKED:** REQ-26 — rubric file not provided.
- **NOT COVERED:** REQ-29 — HTTP 409 mapping; needs the REST layer.
- **PARTIAL:** REQ-20 (service not yet exposing the feature), REQ-21 (API design section), REQ-24 (final limitations summary), REQ-34 (developer verification not recorded).
- **OPEN:** REQ-35 — final submission.
- **Provisional rows:** TC-A-08 and TC-A-10, to be confirmed against the API design.
- **Not verified:** a build on an actual JDK 17; `mvnw.cmd` on Windows.
