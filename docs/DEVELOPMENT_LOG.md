# Development Log

Chronological record of how this project was built: what was done in each step,
why, and how it was verified. Oldest entries first.

## Documentation rules

Agreed by the developer during project planning and restated in the Step 0
session.

1. `DEVELOPMENT_LOG.md` and `AI_USAGE.md` are updated in the same commit as the changes they describe.
2. `DEVELOPMENT_LOG.md` is chronological.
3. `AI_USAGE.md` includes the prompts, summaries of the AI output, and the developer's decisions.
4. Verification is never claimed unless the actual command was run.
5. Developer review is never claimed unless the developer explicitly confirmed it.
6. These labels are used consistently in both documents:

| Label | Meaning |
|-------|---------|
| **AI-suggested** | Proposed by the AI; no developer decision recorded yet. |
| **Accepted by developer** | The developer explicitly accepted the suggestion as proposed. |
| **Modified by developer** | The developer changed or corrected the suggestion. |
| **Rejected by developer** | The developer explicitly declined the suggestion. |
| **Verified by AI** | The AI ran the stated command in the session and observed the stated result. |
| **Verified by developer** | The developer explicitly confirmed they ran or checked it themselves. |
| **Developer review: pending** | No explicit developer confirmation has been recorded. |

### Evidence types used in this log

- **Conversation-recorded** — a decision or event reported by the developer from
  the planning conversations. It was not observed by any command in this
  repository's sessions, and no AI output or verification is attributed to it.
- **Command-verified** — a result observed from a command actually run in a
  session; always carries a *Verified by AI* or *Verified by developer* label
  and names the command.

### Standing constraints

- Nothing is committed or pushed until the developer approves the proposed change. Work stays on a feature branch and is not merged into `main` without approval.
- No credentials, secrets, or unnecessary personal data in code, prompts, or these records.
- Strict ERPX isolation: this assessment belongs to Keerthi Joshi. ERPX code, files, credentials, and architecture are never accessed, reused, modified, or integrated.
- Scope follows the assessment brief. No database, frontend, Docker, or extra dependencies without a decision recorded here.

## Log

### 2026-11-15 to 2026-11-16 — Project planning (historical backfill)

**Evidence: Conversation-recorded.** These events took place in planning
conversations before the Step 0 session. They are listed as reported by the
developer on request during the Step 0 session. The planning conversations
themselves were not available to that session, so no AI outputs, exact times,
or verification results are recorded for them, and the detailed content of each
decision is not reproduced here.

| # | Event | Evidence |
|---|-------|----------|
| 1 | Assessment requirements and scope reviewed. | Conversation-recorded |
| 2 | Keerthi Joshi's repository identified (`keejoshi/course-waitlist`). | Conversation-recorded |
| 3 | Initial repository found empty. | Conversation-recorded (independently command-verified later; see *Environment verification*) |
| 4 | Architecture and implementation approach discussed. | Conversation-recorded |
| 5 | Stack finalized: Spring Boot 3.5.x, Java 17 compatibility, Maven, REST, in-memory state. | Conversation-recorded |
| 6 | Enrollment, duplicate-enrollment, FIFO waitlist, and prerequisite policies agreed. | Conversation-recorded |
| 7 | Strict ERPX isolation established. | Conversation-recorded |

Open item from this entry: the specific policy definitions agreed in event 6
(including whether an ineligible learner is skipped or retained) are not yet
written in this repository. They are to be recorded in the design
specification before the code that relies on them.

Events 8 and 9 of the developer's backfill list (cloud environment created and
verified; Step 0 skeleton generated and tests run) have command evidence and
are the next two entries.

### 2026-11-15 — New Claude Code Cloud environment created and verified

Timestamps observed in the container are UTC.

**Goal:** Confirm the working environment before creating anything.

**Evidence: Command-verified — Verified by AI.**

| Check | Command | Observed result |
|-------|---------|-----------------|
| Repository | `git remote -v` | `origin` is `https://github.com/keejoshi/course-waitlist` (fetch and push). |
| Branch | `git status -sb` | `claude/kind-carson-dtyk4z`, no commits yet. |
| Repository empty | `ls -la`, `git ls-remote origin` | Working directory held only `.git`; the remote returned no refs. |
| Cloud, not local Windows | `uname -a`, `/etc/os-release` | Ubuntu 24.04.5 LTS, Linux x86_64. |
| Java 17+ | `java -version` | OpenJDK 21.0.12. |
| Maven | `mvn -version` | Apache Maven 3.9.11. |
| Maven Central reachable | `curl -I` on a Maven Central URL | HTTP 200. |

**Not verified:** permission to push. `git push --dry-run` could not run with
no commits; this will only be known at the first real push.

**Changes:** none.

**Developer decision:** Accepted by developer — the developer replied
"Approved. Proceed with Step 0."

### 2026-11-15 — Step 0: Initial project setup

**Goal:** Minimal Spring Boot skeleton, documentation scaffolding, and a
startup test. No business logic.

**Inputs supplied by the developer:** a locally generated Spring Initializr zip
(Spring Boot 4.0.9, group `com.assesssent`, Web MVC + Validation) and the
assessment brief PDF.

**What changed:**

- `pom.xml` — Spring Boot 3.5.16 parent, Java 17, three starters (web, validation, test).
- `CourseWaitlistApplication` — application entry point in `com.assessment.coursewaitlist`.
- `CourseWaitlistApplicationTests` — startup test.
- `application.yaml` — application name only.
- Maven wrapper (`mvnw`, `mvnw.cmd`, `.mvn/wrapper/maven-wrapper.properties`), `.gitignore`, `.gitattributes`.
- `README.md`, `docs/DEVELOPMENT_LOG.md`, `docs/AI_USAGE.md`.

**Decisions (all AI-suggested):**

| Decision | Reason |
|----------|--------|
| Spring Boot 3.5.16 instead of the supplied zip's 4.0.9 | The agreed stack is Spring Boot 3.5.x; 3.5.16 was the latest 3.5 release in Maven Central metadata on this date. |
| Package `com.assessment.coursewaitlist` instead of the zip's `com.assesssent.coursewaitlist` | Required package name; the zip's group contained a typo. |
| `spring-boot-starter-web` and `spring-boot-starter-test` instead of the zip's `-webmvc`, `-webmvc-test`, `-validation-test` | Those artifact names exist only in Spring Boot 4; the 3.5 equivalents are used. |
| Wrapper, `.gitignore`, `.gitattributes` kept from the zip unchanged | Standard Initializr output, independent of the Boot version. |
| Initializr `HELP.md` and the empty `<licenses>/<developers>/<scm>` POM overrides dropped | Boilerplate with no content. |
| Java release level 17, built on JDK 21 | Requirement is Java 17 compatibility; `java.version=17` sets the compiler target. |
| Startup test asserts the context and the application bean exist | An empty `contextLoads()` asserts nothing explicit. |

**Developer decision:** Accepted by developer — the developer stated "Step 0
looks good" after the AI's report. Commit and push approval was withheld
pending the documentation corrections in the next entry.

**Verification — Verified by AI:**

| Command | Observed result |
|---------|-----------------|
| `mvn -B test` (Maven 3.9.11, OpenJDK 21.0.12) | `Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`; BUILD SUCCESS; log shows Spring Boot v3.5.16. |
| `./mvnw -B test` (wrapper; `./mvnw -v` reports Maven 3.10.0) | `Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`; BUILD SUCCESS. |
| `mvn -B dependency:tree` | Direct dependencies are exactly the three starters, all 3.5.16. |

**Verified by developer:** none recorded.

**Not verified at this point:** a build on an actual JDK 17 (the container only
has JDK 21); `./mvnw spring-boot:run` (verified in the next entry); `mvnw.cmd`
on Windows.

### 2026-11-15 — Step 0 documentation corrections

**Goal:** Apply the developer's corrections before the Step 0 commit.

**What changed:**

- `docs/DEVELOPMENT_LOG.md` — the AI's draft documentation rules replaced with the agreed rules and labels; backfill rewritten as a chronological entry from the developer's event list; conversation-recorded events separated from command-verified results; labels applied throughout.
- `docs/AI_USAGE.md` — restructured to prompt / output summary / developer decision / verification, using the agreed labels.
- `README.md` — Maven wrapper commands documented with Windows equivalents.

**Developer decision:** Modified by developer — the developer supplied the
rules, the labels, and the backfill event list, replacing the AI's drafts.

**Verification — Verified by AI:**

| Command | Observed result |
|---------|-----------------|
| `./mvnw -B test` after the documentation changes | `Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`; BUILD SUCCESS. |
| `./mvnw -B spring-boot:run`, then `curl http://localhost:8080/` | Log shows `Tomcat started on port 8080` and `Started CourseWaitlistApplication`; `GET /` returned HTTP 404, as expected with no endpoints. Process stopped afterwards. |

**Not verified:** the Windows `mvnw.cmd` commands (no Windows machine in the session); a build on an actual JDK 17.

**Verified by developer:** none recorded.

**Developer decision on the result:** Accepted by developer — "I approve Step 0,
including the project skeleton, README, and documentation scaffolding." The
developer approved the commit with the message "Step 0: Spring Boot 3.5 project
skeleton and documentation scaffolding".

**Developer review: pending** for push approval.

### 2026-11-15 — Step 0 commit, author amendment, and push

**Goal:** Commit Step 0, set the commit author, and publish the branch, each
on the developer's explicit approval.

**Sequence:**

1. The developer approved the commit. Before committing, the AI recorded that approval in this log and in `AI_USAGE.md`, re-ran the tests, and committed the 12 files as `1caa0bd`.
2. The commit's author and committer were the cloud container's default git identity ("Claude", an Anthropic no-reply address). The AI reported this and set out the options without changing anything.
3. The developer supplied Keerthi Joshi's GitHub no-reply address and asked for the author to be changed, keeping the AI co-author disclosure. The AI amended the author only. The commit became `1c709c3b792c25363bba9550bf92b73f0c90ae7e`. The committer and the environment's automatic commit signature were left as they were, because only the author change was requested.
4. The developer approved pushing that commit to the remote branch `feature/initial-setup`. The AI pushed it and nothing else.

**Developer decision:** Accepted by developer at each of steps 1, 3, and 4, by
explicit instruction in the session.

**Verification — Verified by AI:**

| Command | Observed result |
|---------|-----------------|
| `./mvnw -B test` before the commit | `Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`; BUILD SUCCESS. |
| `git rev-parse HEAD^{tree}` before and after the amend | Identical tree hash: no file content changed. |
| `git log -1` after the amend | Author is Keerthi Joshi; message and `Co-Authored-By` trailer unchanged. |
| `git push origin 1c709c3…:refs/heads/feature/initial-setup` | New branch created. Git printed "push negotiation failed; proceeding anyway", then succeeded. |
| `git ls-remote origin` after the push | One ref: `refs/heads/feature/initial-setup` at `1c709c3b792c25363bba9550bf92b73f0c90ae7e`. No `main`. |

This also closes the Step 0 open item "confirm push permission at the first
push".

**Verified by developer:** none recorded.

**Not verified:** how GitHub displays the author, committer, and signature on
the commit page. Because `feature/initial-setup` is the only remote branch,
GitHub treats it as the default branch; that setting was not touched.

### 2026-11-15 — Step 1 design review: domain model

**Goal:** Agree the domain design before writing code.

**Inspection first (Verified by AI):** `git fetch` and `git ls-files` showed the
local tree identical to `feature/initial-setup` at `1c709c3` and containing only
the Step 0 skeleton. There was no starter data model, interface, persistence
layer, or test convention to conform to, so the brief's minimal-service
fallback applies. The reasoning is in `DESIGN.md` section 1.

**What the AI proposed (AI-suggested):** `Course` (immutable), `Learner`
(mutable completions), `CourseOffering` (aggregate owning enrolled list and
waitlist), result types with outcomes as values, insertion-ordered waitlist,
one lock per offering, and a domain test plan. It raised four open questions.

**Developer decisions:**

| # | Question | Decision | Label |
|---|----------|----------|-------|
| 1 | Open seat with waiters when a new request arrives | Eligible waitlisted learners take the seat before a new requester. | Accepted by developer (the AI's recommended option) |
| 2 | Drop by a waitlisted learner | Must be allowed, with a distinct outcome `DROPPED_FROM_WAITLIST`, affecting no enrolled learner and triggering no promotion. | Rejected by developer (the AI had recommended treating it as `NOT_ENROLLED`); the developer's alternative was adopted |
| 3 | Duplicate request while waitlisted | `ALREADY_WAITLISTED`, HTTP 409, state unchanged; same for `ALREADY_ENROLLED`. | Accepted by developer |
| 4 | `revokeCompletion` on `Learner` | Approved. Enrolled learners are not removed when a prerequisite is later revoked. | Accepted by developer, with the non-removal rule added by the developer |
| — | String ids, immutable `Course`, `CourseOffering` owning state, FIFO order, skip-and-retain, vacant seat when no eligible waiter, `docs/DESIGN.md` | Approved. | Accepted by developer |

**Changes:** none to files; design only.

### 2026-11-15 — Requirements checklist and traceability rules

**Goal:** Make coverage of the brief explicit before implementation.

**Developer instruction:** every acceptance criterion and test scenario in the
brief must be covered; ids for every requirement and test; a traceability
matrix; nothing marked covered without an actual test; nothing marked passed
without execution; gaps reported as BLOCKED or NOT COVERED; a coverage review
before every commit; scope and tests never reduced without asking.

**What the AI produced (AI-suggested):** a master checklist extracted from the
brief: requirements, domain tests, concurrency tests, provisional API tests,
and review checks. It flagged that documentation and scope requirements cannot
be meaningfully unit-tested, that the rubric file was not supplied, that the
concurrency clause is conditional, and that the brief names only six test
scenarios so the rest are derived.

**Developer decisions:**

| Item | Decision | Label |
|------|----------|-------|
| Review evidence instead of artificial unit tests for documentation and scope requirements | Approved, provided every behavioural criterion has an executed test. | Accepted by developer |
| TC-A-08 and TC-A-10 | Keep provisionally; review against the API design; no registries added just for them; propose an equivalent before changing the checklist. | Modified by developer |
| TC-D-17 | Must use an offering with exactly one remaining seat. | Modified by developer |
| REQ-26 (rubric) | Stays BLOCKED until the file is provided; nothing invented. | Accepted by developer |
| Labelling | Brief text preserved verbatim; brief, developer, and AI-derived items kept distinct. | Modified by developer |
| Additional tests | Keep the waitlist-priority test and add the regained-eligibility scenario; document the open-seat policy precisely. | Modified by developer |
| Additional review checks | Starter inspection and fallback justification; scope confirmation; independent AI review. | Modified by developer |
| Timebox | Guidance, not an acceptance criterion. | Accepted by developer (developer's own ruling) |

**Result:** `docs/REQUIREMENTS_TRACEABILITY.md` — 35 requirements, 50 planned
automated tests, 12 review checks. The AI added one item beyond those the
developer listed: REQ-35 / RV-12 (submit all deliverables together), taken from
the brief's candidate expectations. Developer review: pending for that addition.

### 2026-11-15 — Step 1: Domain model implementation

**Goal:** Implement the approved domain classes and domain tests. No REST
layer, no services, no persistence.

**What changed:**

- New package `com.assessment.coursewaitlist.domain`: `Course`, `Learner`, `CourseOffering`, `EnrollmentResult`, `DropResult`, and a package-private argument-check helper `Require`.
- New tests: `CourseOfferingTest` (31 tests), `CourseOfferingConcurrencyTest` (4), `LearnerTest` (1), `CourseTest` (1).
- New documents: `docs/DESIGN.md`, `docs/REQUIREMENTS_TRACEABILITY.md`.
- Updated: this log, `docs/AI_USAGE.md`, `README.md` (status, structure, document links).
- Unchanged: `pom.xml`, `application.yaml`, the application class, the Step 0 test. No dependency was added.

**Implementation decisions (AI-suggested; Developer review: pending):**

| Decision | Reason |
|----------|--------|
| One promotion pass used by both `drop` and `enroll` | A single implementation of skip-and-retain guarantees the policy is applied consistently in both places. |
| The pass promotes until seats run out, not just once | Needed when a seat was left vacant earlier and waiters have since regained eligibility (TC-D-33). |
| `enroll` runs the pass only after the duplicate and prerequisite checks | Duplicate and rejected requests must leave the state unchanged. Consequence recorded as limitation L1 in `DESIGN.md`. |
| Both result types carry `promotedLearnerIds` as a list | An enrollment request can cause a promotion, and a pass can promote more than one learner. |
| Malformed calls throw `IllegalArgumentException`; expected outcomes are enum values | Keeps normal results distinct from programming errors. |
| `synchronized` on a private lock object | Callers cannot interfere by locking the offering themselves. |
| One test method per checklist id, named `tcD01_…`, with the id in the display name | Makes the traceability matrix checkable against the test report. |
| Concurrency scenarios repeat 25 rounds behind a start gate | A single round can pass by luck. |

**Verification — Verified by AI:**

| Command | Observed result |
|---------|-----------------|
| `./mvnw -B test` (OpenJDK 21.0.12, wrapper Maven 3.10.0) | `Tests run: 38, Failures: 0, Errors: 0, Skipped: 0`; BUILD SUCCESS. By class: `CourseOfferingTest` 31, `CourseOfferingConcurrencyTest` 4, `LearnerTest` 1, `CourseTest` 1, `CourseWaitlistApplicationTests` 1. |
| Fault injection 1, in a scratch copy outside the repository: lock replaced with a fresh object per call, then the concurrency tests run | 3 of 4 concurrency tests failed (TC-C-01, 02, 04). TC-C-03 still passed in that run. |
| Fault injection 2, scratch copy: promotion ignores eligibility | 8 tests failed: TC-D-13, 14, 15, 16, 17, 18, 32, 33. |
| Fault injection 3, scratch copy: no promotion pass before a new enrollment | 1 test failed: TC-D-17. |
| `git status` after the fault injections | Repository working tree unaffected; the faults existed only in the scratch copy. |
| `git diff --stat HEAD -- pom.xml` | Empty: no dependency change. |
| `grep` for Spring or Jakarta imports in the domain package | None. |

The fault injections show that the tests detect the faults they target. They
are not a full mutation analysis. TC-C-03 not failing without the lock in one
run means that test alone is weak evidence of the lock; TC-C-01, 02, and 04
are the ones that demonstrated it.

**Verified by developer:** none recorded.

**Not verified:** a build on an actual JDK 17; `mvnw.cmd` on Windows.

**Coverage at this point** (nothing was committed): 38 of 50 planned tests
implemented, executed, and passed; 12 API tests not implemented; REQ-26
blocked; REQ-29 not covered. These figures were superseded by the corrections
recorded two entries below.

**Developer review: pending** for the implementation, the documents, and
commit approval.

### 2026-11-15 — Step 1 code-quality review

**Goal:** At the developer's request, review the uncommitted Step 1
implementation before approval. No code or document was changed during the
review.

**Method (Verified by AI):** read each domain class and test; cross-checked
every requirement-to-test mapping in `REQUIREMENTS_TRACEABILITY.md` in both
directions with a script; matched every domain and concurrency test id to a
method that ran and passed; ran the concurrency test class 20 times in a row
(20 of 20 passed); re-ran the full suite (38 passed). Finding F1 came from
reading the code, not from an observed failure.

**Result:** no defect found in the enroll, drop, or promotion logic. The AI
recommended corrections before accepting Step 1 and reported these findings:

| Id | Finding | Severity |
|----|---------|----------|
| F1 | No atomic view of the whole offering: the three read methods each took the lock separately, so two calls could disagree. | Medium |
| F2 | The eligibility check was not atomic across several prerequisites. | Low |
| F3 | The result types accepted contradictory values. | Low |
| F4 | `Course` validated the caller's set before copying it. | Very low |
| T1 | TC-D-21 could not detect a wrongly triggered promotion, because the offering was full. | Medium |
| T2 | TC-C-03 still passed with the lock disabled. | Medium |
| T3 | No test for an enrollment where a waiter is promoted and the requester also gets a seat. | Low |
| T4 | Check order untested for a waitlisted learner who has become ineligible. | Low |
| T5 | TC-D-24 never ran a promotion after the revocation. | Low |
| T6 | No direct tests for the result types. | Low |
| T7 | A comment in the concurrency test said all tasks ran at once; only 16 of up to 40 started together. | Cosmetic |
| D1-D5 | Traceability: REQ-13 and REQ-20 did not name their tests; Table A.1 and section C.1 disagreed; TC-D-29 mapped to the wrong requirement; TC-D-30 loosely mapped; two approved decisions had no requirement id. | Documentation |
| A1-A5 | Records: stop-hook declines misrecorded; one decision labelled differently in two files; an overstated fault-injection claim; missing basis quote; stale entry range. | Documentation |

**Developer decision:** Accepted by developer — "I approve fixing all findings
before accepting Step 1." The developer set the required fix for each finding
and added: record the review as an AI activity; state precisely which tests
failed and passed under fault injection; keep developer verification pending.

### 2026-11-15 — Step 1 corrections after the code-quality review

**Goal:** Fix every review finding. No API work.

**Code changes:**

| Finding | Change |
|---------|--------|
| F1 | New `OfferingSnapshot` record and `CourseOffering.snapshot()`, which captures the enrolled list, the waitlist, and the free-seat count under one lock acquisition. The single-value accessors stay, documented as not atomic with each other. |
| F2 | Fixed, not argued away. `Learner` now guards its completions with a private lock instead of a lock-free concurrent set, so an eligibility check sees one consistent state. Lock order is offering, then learner; documented in `DESIGN.md` section 6. |
| F3 | `EnrollmentResult` and `DropResult` reject a promoted list on outcomes that cannot promote, and reject null, blank, or repeated ids. |
| F4 | `Course` copies the prerequisite collection first and validates the copy. |

**Test changes:**

| Finding | Change |
|---------|--------|
| T1 | TC-D-21 gained a second part with a vacant seat and an eligible waiter; another waiter leaving must promote nobody. |
| T2 | TC-C-03 rewritten: 8 enrolled learners each dropped by 4 threads at once, with 12 waiting. Asserts one drop per learner, one promotion per drop, exactly the first 8 waiters promoted, FIFO order kept for the rest. |
| T3 | New TC-D-34. |
| T4 | New TC-D-35. |
| T5 | TC-D-24 now uses two seats and runs a promotion after a drop and before a new enrollment; the revoked learner must stay enrolled. |
| T6 | New `ResultTypesTest`: TC-D-36 and TC-D-37. |
| T7 | The test runner now gives every task its own thread behind the start gate, so all tasks really are released together, and the comment says so. |
| F1 | New TC-D-38 (snapshot content, immutability, validation) and TC-C-06 (snapshots stay consistent during concurrent enrolls and drops). |
| F2 | New TC-C-05 (eligibility never reported for a state the learner was never in). |
| F4 | TC-D-29 extended with a null prerequisite element. |

**Documentation changes:** `REQUIREMENTS_TRACEABILITY.md` — REQ-36 and REQ-37
added, all mappings regenerated from one table and totals recalculated
(D1-D5); `DESIGN.md` — snapshot, result validation, learner lock and lock
order, invariant I8, limitations L6 rewritten and L7 added; `AI_USAGE.md` —
A1, A2, A4 corrected, entries 8 and 9 added; this log — A2 label corrected and
the "at this commit" wording in the previous Step 1 entry corrected, since
nothing had been committed.

**Verification — Verified by AI:**

| Command | Observed result |
|---------|-----------------|
| `./mvnw -B test` (OpenJDK 21.0.12) | `Tests run: 45, Failures: 0, Errors: 0, Skipped: 0`; BUILD SUCCESS. By class: `CourseOfferingTest` 34, `CourseOfferingConcurrencyTest` 6, `ResultTypesTest` 2, `LearnerTest` 1, `CourseTest` 1, `CourseWaitlistApplicationTests` 1. |
| Concurrency class run 15 times in a row | 15 of 15 passed. |
| Mapping cross-check script over `REQUIREMENTS_TRACEABILITY.md` | No mismatch in either direction; every domain and concurrency id matched to a passed test method. |
| `git diff --stat HEAD -- pom.xml` | Empty: no dependency change. |

**Fault injection — Verified by AI.** Each fault was applied to a scratch copy
outside the repository and the relevant tests were run. Concurrency faults
were run five times each.

| Fault | Tests that failed | Tests that passed despite the fault |
|-------|-------------------|-------------------------------------|
| M1 Offering lock disabled (5 runs) | TC-C-02, TC-C-04, TC-C-06: 5 of 5 runs. TC-C-01: 4 of 5. TC-C-03: 4 of 5. | TC-C-01 once, TC-C-03 once. TC-C-05 in all 5, as expected: it does not use the offering lock. |
| M2 Promotion ignores eligibility | TC-D-13, 14, 15, 16, 17, 18, 21, 32, 33, 34 | — |
| M3 No promotion pass before a new enrollment | TC-D-17, 34 | — |
| M4 Leaving the waitlist runs a promotion pass | TC-D-21 | — |
| M5 Snapshot built from three separate lock acquisitions (5 runs) | TC-C-06: 5 of 5 | The other concurrency tests, as expected: they read a snapshot only after all threads finish. |
| M6 Learner eligibility checked one prerequisite at a time (5 runs) | TC-C-05: 5 of 5 | The other concurrency tests, as expected: they use a single prerequisite. |
| M7 Prerequisite check placed before the duplicate check | TC-D-24, 35 | — |
| M8 Promotion evicts enrolled learners who are no longer eligible | TC-D-24 | — |
| M9 Result types accept contradictory values | TC-D-36, 37 | — |

Before the corrections TC-C-03 passed with the lock disabled; it now fails in
4 of 5 such runs. It is still not conclusive on its own, and neither is
TC-C-01. In every run with the offering lock disabled at least four
concurrency tests failed.

**Verified by developer:** none recorded. All verification above is the AI
checking its own work and does not stand in for the developer's review.

**Not verified:** a build on an actual JDK 17; `mvnw.cmd` on Windows.

**Developer review: pending** for the corrected Step 1 and for commit
approval. Nothing has been committed or pushed.

### 2026-11-15 — Step 1 acceptance review

**Goal:** At the developer's request, verify the uncommitted Step 1 change
before final acceptance. Files to be changed only for a factual documentation
discrepancy.

**Checks — Verified by AI:**

| Check | Command | Observed result |
|-------|---------|-----------------|
| HEAD, branch, remote | `git rev-parse HEAD`, `git branch --show-current`, `git ls-remote origin` | HEAD `1c709c3`; local branch `claude/kind-carson-dtyk4z`; remote has one branch, `feature/initial-setup`, at `1c709c3`. No `main` anywhere. |
| Working tree | `git status --short -uall` | 3 modified, 14 untracked, nothing staged. |
| Tests against the current files | `./mvnw -B test` | `Tests run: 45, Failures: 0, Errors: 0, Skipped: 0`; BUILD SUCCESS. Reports are newer than every Java source file. |
| Diff against Step 0 | `git diff --name-status HEAD`, `git diff --check HEAD` | 17 files, all Step 1 code, tests, or documents; no whitespace errors; `pom.xml` and `application.yaml` unchanged. No unrelated or accidental change found. |
| No API work | search of `src` for controller, service, and mapping annotations | None found. |

**Documentation discrepancies found and corrected** (Markdown only; no code or
test changed):

1. `AI_USAGE.md`: the stop-hook table was missing the fifth message, received after the corrections report. Row added.
2. `AI_USAGE.md` and `REQUIREMENTS_TRACEABILITY.md`: RV-01 claimed entries 1-9 cover every AI activity; this review is another. Entry 10 and this log entry added; RV-01 now says 1-10.
3. `README.md`: the `domain` package description omitted `OfferingSnapshot`. One line changed.

**Verified by developer:** none recorded.

**Developer review: pending** for final acceptance and commit approval.

### 2026-11-15 — Step 1 accepted, committed, and pushed

Recorded on 2026-11-16, at the start of Step 2. The commit itself could not
carry this entry: the developer's commit instruction was to commit the 17
reviewed files with no additional changes, so the Step 1 commit still shows
"Developer review: pending" in the two entries above. Those entries are left
as written; this entry records what followed.

**Sequence:**

1. After the acceptance report, the developer wrote: "I explicitly authorize you to stage and commit the accepted Step 1 changes only", with the conditions: only the 17 reviewed files, Keerthi Joshi as author, Claude as co-author, the approved message, no push, no further changes.
2. The AI staged exactly those 17 files and committed them as `2cc6d95709b15f13169dec5e5a0f8c7abbf71232`, parent `1c709c3`. Author: Keerthi Joshi (GitHub no-reply address). Committer: the container's default identity, as for Step 0. The message carries the `Co-Authored-By` trailer.
3. The developer then wrote: "I explicitly authorize pushing the existing Step 1 commit `2cc6d95…` to the remote branch `feature/initial-setup`", with the conditions: this exact commit only, no amend, no `main`, no merge.
4. The AI pushed that commit and nothing else.
5. At the start of Step 2 the developer stated: "Step 1 is accepted, committed, and pushed."

**Developer decision:** Accepted by developer — Step 1 acceptance, the commit,
and the push, each by the explicit statements quoted above.

**Verification — Verified by AI:**

| Command | Observed result |
|---------|-----------------|
| `git diff --cached --name-only` before the commit | 17 files staged; nothing else in the working tree. |
| `git log -1` after the commit | Hash `2cc6d95709b15f13169dec5e5a0f8c7abbf71232`; author Keerthi Joshi; approved message and co-author trailer. |
| `git status` after the commit | Clean working tree. |
| `git push origin 2cc6d95…:refs/heads/feature/initial-setup` | Fast-forward `1c709c3..2cc6d95`; no errors or warnings. |
| `git ls-remote origin` after the push | One branch, `feature/initial-setup`, at `2cc6d95709b15f13169dec5e5a0f8c7abbf71232`. No `main`. |

**Verified by developer:** none recorded. The developer accepted Step 1 on the
basis of the AI's reports; no statement that the developer personally ran the
tests or inspected the code has been made, so review check RV-11 stays
PARTIAL.

### 2026-11-16 — Step 2 design review: service layer and REST API

**Goal:** Agree the service and API design before writing code.

**Inspection first (Verified by AI):** `git status` and `git ls-remote origin`
showed the local tree and `feature/initial-setup` both at the Step 1 commit
`2cc6d95`, with a clean working tree.

**What the AI proposed (AI-suggested):** three packages (`config`, `service`,
`api`) beside the unchanged domain; one configured offering and no offering
id; an in-memory learner directory; six endpoints; `ProblemDetail` errors with
a `code`; 18 new tests; three new requirement ids; and eight decisions for the
developer. It recommended no change to the accepted domain code.

**Developer decisions:**

| # | Question | Decision | Label |
|---|----------|----------|-------|
| 1 | How learners reach the service | In-memory `LearnerDirectory` and learner endpoints. | Accepted by developer (the AI's recommended option) |
| 2 | Offering configuration | The single offering is configured in `application.yaml`. | Accepted by developer |
| 3 | Offering id and TC-A-10 | No offering id in paths and no registry. TC-A-10 is kept as a design/review check; no artificial endpoint or test. | Modified by developer (the AI had proposed a replacement automated test) |
| 4 | Success codes for enroll | 200 for both `ENROLLED` and `WAITLISTED`, distinguished by the `status` field. | Modified by developer (the AI had recommended 201 and 202, with 201 for both as the alternative) |
| 5 | Prerequisite failure | 422. | Accepted by developer |
| 6 | Drop when not enrolled | 409 `NOT_ENROLLED` for a registered learner; 404 `LEARNER_NOT_FOUND` for an unknown one. | Rejected by developer for the first part (the AI had recommended 404; the alternative it listed, 409, was chosen); accepted for the second |
| 7 | Limitation L1 | Unchanged. | Accepted by developer |
| 8 | New ids | REQ-38, REQ-39, REQ-40 and the proposed additional tests. | Accepted by developer |

Further instructions from the developer: immutable response types and no
serialized domain objects; `CourseOffering.snapshot()` for the state query;
preserve the domain invariants and locking; update test status only after
implementation and execution; correct RV-01's stale entry reference; record
Step 1 acceptance and the seventh stop-hook message.

**Changes:** none to code; the Step 1 acceptance entry above and its
counterpart in `AI_USAGE.md` were written in this session (Phase A).

### 2026-11-16 — Step 2: Service layer and REST API implementation

**Goal:** Implement the approved design. The domain package is untouched.

**What changed:**

- `config`: `OfferingProperties`, `OfferingConfiguration`; the offering's data in `application.yaml`.
- `service`: `LearnerDirectory`, `EnrollmentService`, `OfferingView`, `LearnerView`, `LearnerNotFoundException`, `LearnerAlreadyExistsException`.
- `api`: `OfferingController`, `LearnerController`, `ApiExceptionHandler`, request records (`EnrollRequest`, `RegisterLearnerRequest`), response records (`EnrollmentResponse`, `DropResponse`, `OfferingResponse`, `LearnerResponse`), `RequestRejectedException`, `ApiRules`.
- Tests: `OfferingApiTest` (14), `LearnerApiTest` (2), `EnrollmentApiConcurrencyTest` (1), `EnrollmentServiceTest` (5), `OfferingConfigurationTest` (1).
- Documents: `DESIGN.md` section 7 and limitations L8-L11; `REQUIREMENTS_TRACEABILITY.md`; `README.md`; this log; `AI_USAGE.md`.
- Unchanged: `pom.xml` (no dependency added) and every file in the `domain` package.

**Implementation decisions (AI-suggested; Developer review: pending):**

| Decision | Reason |
|----------|--------|
| One `EnrollmentService` facade for both controllers | The whole feature is six operations; a second service would add a class without separating anything. |
| The service returns the domain's immutable result records and its own immutable views; controllers map them to response records | No mutable domain object leaves the service. The `api` package imports only `EnrollmentResult` and `DropResult` from the domain. |
| Controllers turn a rejected outcome into a `RequestRejectedException` carrying status and code; one `@RestControllerAdvice` renders every error | A single place produces `ProblemDetail`, so framework errors and domain rejections share one shape. |
| The advice extends Spring's `ResponseEntityExceptionHandler` and adds `code` to framework errors | Malformed JSON, unknown paths, and wrong methods then need no separate handling. |
| `LearnerDirectory.register` uses `putIfAbsent` | Of two simultaneous registrations of one id, exactly one wins, and the directory never holds two objects for one learner. |
| Each API test class starts every test from a fresh application context | The offering is a singleton with state; this isolates tests without adding a reset operation to production code. |
| API tests assert the response and then the offering state through `GET /api/offering` | A correct status with corrupted state would otherwise pass. |

**Verification — Verified by AI:**

| Command | Observed result |
|---------|-----------------|
| `./mvnw -B test` (OpenJDK 21.0.12) | `Tests run: 68, Failures: 0, Errors: 0, Skipped: 0`; BUILD SUCCESS. By class: `CourseOfferingTest` 34, `OfferingApiTest` 14, `CourseOfferingConcurrencyTest` 6, `EnrollmentServiceTest` 5, `LearnerApiTest` 2, `ResultTypesTest` 2, and 1 each in `EnrollmentApiConcurrencyTest`, `OfferingConfigurationTest`, `LearnerTest`, `CourseTest`, `CourseWaitlistApplicationTests`. |
| Both concurrency classes run 8 times in a row | 8 of 8 passed. |
| `./mvnw spring-boot:run`, then the README walk-through and error cases with `curl` | Responses matched the design: 201 on registration; 200 `ENROLLED` and `WAITLISTED`; 409 `ALREADY_ENROLLED`; 422; 404 `LEARNER_NOT_FOUND`; 400; 200 `DROPPED` with the promoted learner; 409 `NOT_ENROLLED`; 204 on revoke; errors as `application/problem+json`. No error in the server log. |
| Mapping cross-check script over `REQUIREMENTS_TRACEABILITY.md` | No mismatch in either direction; 67 test ids matched to passed test methods, plus `contextLoads`; 68 of 68 test cases in the reports passed. |
| `git diff --stat HEAD -- pom.xml src/main/java/.../domain` | Empty: no dependency change and no domain change. |

**Fault injection — Verified by AI.** Each fault was applied to a scratch copy
outside the repository and the service and API test classes were run.

| Fault | Tests that failed |
|-------|-------------------|
| N1 Duplicate enrollment answered 200 | TC-A-03, 18 |
| N2 Service registers an unknown learner on the fly | TC-A-08, 16, 17, 18; TC-S-02 |
| N3 Directory replaces an existing learner on re-registration | TC-A-16, 18; TC-S-03 |
| N5 Prerequisite failure answered 409 | TC-A-05, 16, 17, 18 |
| N6 Validation removed from the enroll request | TC-A-09, 18 |
| N7 Drop by a learner not enrolled answered 200 | TC-A-07, 11, 18 |
| N8 No handler for an unknown learner (falls through to 500) | TC-A-08, 16, 17, 18 |
| N10 Configured capacity ignored | TC-S-06 |
| N11 Unexpected failure reports its exception text | TC-A-18 |
| N12 Enrollment answered 201 instead of 200 | TC-A-01, 02, 03, 15, 16, 17 |
| N9 Offering lock disabled, TC-A-19 only (5 runs) | First version of TC-A-19: 0 of 5 runs. Strengthened version: 5 of 5. |
| N4 State query built from three separate reads, TC-A-19 only (5 runs) | First version: 0 of 5. Strengthened version: 3 of 5; passed in the other 2. |

The first version of TC-A-19 (one round of 24 concurrent enrollments) detected
neither concurrency fault, so it was rewritten: 12 rounds of 32 concurrent
enrollments followed by 32 concurrent drops, with 4 readers checking every
state read. It remains a weaker detector than the domain concurrency tests and
is documented as limitation L10.

**Self-review findings (AI reviewing its own Step 2 work):**

| Id | Finding | Outcome |
|----|---------|---------|
| S2-1 | TC-A-19 as first written passed with the offering lock disabled and with a non-atomic state read. | Fixed: strengthened as above; residual weakness recorded as L10. |
| S2-2 | An unknown path answered with the framework's wording "No static resource ...". | Fixed: neutral message; TC-A-18 asserts the old wording is absent. |
| S2-3 | The 500 path had no test, since no request can trigger it. | Fixed: TC-A-18 calls the handler directly and asserts the cause is not revealed. |
| S2-4 | A misleading comment in TC-A-15. | Fixed. |
| S2-5 | Learners cannot be removed and the directory is unbounded. | Not changed; recorded as L8. |
| S2-6 | No authentication: any caller can act for any learner. | Not changed; out of scope by the brief; recorded as L9. |
| S2-7 | A JSON number is accepted as a learner id and read as a string. | Not changed; recorded as L11. |
| S2-8 | Two simultaneous registrations of the same id are handled by `putIfAbsent` but no test runs them concurrently. | Not changed; open for the developer's decision. |
| S2-9 | Each API test restarts the application context, about 5 seconds in total. | Not changed; accepted cost of test isolation. |

**Verified by developer:** none recorded. All verification above, including
the self-review, is the AI checking its own work.

**Not verified:** a build on an actual JDK 17; `mvnw.cmd` on Windows.

**Coverage after this step:** 40 requirements; all 25 behavioural requirements
have executed, passing tests; 68 of 68 planned automated tests implemented and
passed; REQ-26 blocked; REQ-24 and REQ-34 partial; REQ-35 open. Details in
`REQUIREMENTS_TRACEABILITY.md` section E.

**Developer review: pending** for the Step 2 implementation, tests, and
documents, and for commit approval. Nothing has been committed or pushed.

### 2026-11-16 — Step 2 review corrections

**Goal:** Apply the developer's corrections from the Step 2 review before
acceptance preparation.

**Developer decisions (from the review):**

| Item | Decision | Label |
|------|----------|-------|
| S2-8 (no concurrent-registration test) | Add one: many simultaneous registrations of one id; exactly one succeeds; the rest get the duplicate result; exactly one learner remains; the winner's data is preserved; no unexpected exception; bounded timeout; repeated for stability. | Modified by developer (the AI had left this open for decision) |
| S2-7 / L11 (JSON number read as a learner id) | Reject it instead of converting it, with an executable API test, unless the fix is disproportionately complex. | Rejected by developer as a limitation (the AI had recorded it as an accepted limitation); to be fixed |

**What changed:**

- `LearnerDirectory.size()` added: a read-only count, needed to assert that exactly one learner remains.
- New `api/JsonConfiguration`: one Jackson setting that makes reading strict for text fields, so a JSON number or boolean is not converted to a string. Such a request becomes a malformed-body error and takes the existing 400 `INVALID_REQUEST` path. The fix is a single configuration class, so it was not judged disproportionate, and L11 is recorded as resolved.
- New tests: TC-S-07 (`EnrollmentServiceTest`), TC-A-20 (`EnrollmentApiConcurrencyTest`), TC-A-21 (`LearnerApiTest`). TC-A-21 also checks that ids containing `.`, `_`, and `-` work in request bodies and in paths, which no earlier test did.
- Documents: `REQUIREMENTS_TRACEABILITY.md` (three rows, mappings, totals), `DESIGN.md` (validation rule, L10, L11), `README.md` (one line), this log, `AI_USAGE.md` (Entry 14, stop-hook message 8).
- Unchanged: `pom.xml` and the `domain` package.

**Verification — Verified by AI:**

| Command | Observed result |
|---------|-----------------|
| `./mvnw -B test` (OpenJDK 21.0.12) | `Tests run: 71, Failures: 0, Errors: 0, Skipped: 0`; BUILD SUCCESS. `EnrollmentServiceTest` 6, `LearnerApiTest` 3, `EnrollmentApiConcurrencyTest` 2; other classes as before. |
| The three classes containing concurrency tests, run 10 times in a row, twice | 10 of 10 passed both times. |
| Mapping cross-check script | No mismatch in either direction; 70 test ids matched to passed methods, plus `contextLoads`; 71 of 71 test cases in the reports passed. |
| Compile of main and test code with `-Xlint:all`, in a scratch copy | No warnings. |
| `git diff --check HEAD`; `git diff --stat HEAD -- pom.xml` and the `domain` packages | No whitespace errors; no change to `pom.xml` or to any domain source or test file. |

**Fault injection — Verified by AI**, in a scratch copy outside the repository:

| Fault | Result |
|-------|--------|
| R1 Registration is check-then-put, not atomic | TC-S-07 at 200 rounds: failed in 3 of 5 runs. The test was then raised to 2,000 rounds: failed in 10 of 10 runs. TC-A-20: failed in 1 of 5 runs. |
| R2 Strict JSON setting switched off | TC-A-21 failed. |
| R3 Strict JSON covers integers only | TC-A-21 failed. |
| N3 Re-registration replaces the learner | TC-A-16, 18, 20; TC-S-03, 07 failed. |
| N2 Unknown learner registered on the fly | TC-A-08, 16, 17, 18, 21; TC-S-02 failed. |
| N6 Enroll request validation removed | TC-A-09, 18 failed. |
| N9 Offering lock disabled, HTTP concurrency class (3 more runs) | TC-A-19 failed in 2 of 3; with the 5 of 5 recorded earlier, 7 of 8 overall. |

**Review of the full Step 2 diff — remaining issues found:**

| Id | Finding | Outcome |
|----|---------|---------|
| S2-10 | TC-S-07 as first written (200 rounds) missed a non-atomic registration in 2 of 5 runs. | Fixed: 2,000 rounds, 10 of 10. It adds about 2 seconds to the suite. |
| S2-11 | TC-A-20 is a weak detector of a non-atomic registration (1 of 5). | Not changed; it remains as the HTTP-level check that the right status codes are returned under concurrency. Recorded in L10. |
| S2-12 | `RegisterLearnerRequest` keeps the list instance created by the JSON reader without copying it. | Not changed. The request object is used once and discarded, and `LearnerDirectory` copies each code into the learner. Open for the developer's decision. |
| S2-13 | `LearnerDirectory.size()` exists only to support a test assertion. | Kept; it is read-only and one line. Open for the developer's decision. |

No other issue was found: no compiler lint warnings, no unused imports, no
debug output, no change outside the Step 2 packages, `application.yaml`,
`README.md`, and `docs/`.

**Verified by developer:** none recorded.

**Not verified:** a build on an actual JDK 17; `mvnw.cmd` on Windows.

**Coverage after the corrections:** 40 requirements; 25 of 25 behavioural
requirements covered; 71 of 71 planned automated tests implemented and passed.

**Developer review: pending** for the corrected Step 2 and for commit
approval. Nothing has been committed or pushed. Step 3 has not been started.

### 2026-11-16 — Step 2 review decisions and final cleanup

**Goal:** Apply the developer's decisions on the two findings left open by the
previous entry. No other change.

**Developer decisions:**

| Finding | Decision | Label |
|---------|----------|-------|
| S2-12 `RegisterLearnerRequest` kept the caller's list | Copy it defensively at construction; keep null handling consistent with validation; add a focused test; no unrelated DTO changes. | Modified by developer (the AI had left it unchanged and open) |
| S2-13 `LearnerDirectory.size()` | Keep it if harmless; prefer package-private if it is only test infrastructure and that is straightforward; no reflection. | Modified by developer (the AI had kept it public) |

**What changed:**

- `RegisterLearnerRequest`: the course list is now an unmodifiable copy. The copy is made with a plain `ArrayList`, not `List.copyOf`, because `List.copyOf` throws on a null element; a null element must stay in place so that request validation reports it as a 400, as before. An omitted list is still an empty list.
- `LearnerDirectory.size()`: now package-private. Its only caller is `EnrollmentServiceTest`, which is in the same package, so no reflection or other test infrastructure was needed. It is kept because it is a one-line read-only count.
- New test TC-A-22 (`RegisterLearnerRequestTest`, plain JUnit).
- Documents: `REQUIREMENTS_TRACEABILITY.md` (one row, REQ-39 mapping and wording, totals), this log, `AI_USAGE.md` (Entry 15, stop-hook message 9). `DESIGN.md` and `README.md` did not need changing.
- Unchanged: `pom.xml`, the `domain` package, and every other request and response type.

**Verification — Verified by AI:**

| Command | Observed result |
|---------|-----------------|
| `./mvnw -B test` (OpenJDK 21.0.12) | `Tests run: 72, Failures: 0, Errors: 0, Skipped: 0`; BUILD SUCCESS. |
| The three classes containing concurrency tests, run 10 times in a row | 10 of 10 passed. |
| Mapping cross-check script | No mismatch in either direction; 71 test ids matched to passed methods, plus `contextLoads`; 72 of 72 test cases in the reports passed. |
| Fault injection in a scratch copy: defensive copy removed | TC-A-22 failed. |
| Fault injection in a scratch copy: copy made with `List.copyOf` | TC-A-22 failed (a null element was rejected at construction instead of being left for validation). |
| `git diff --check HEAD`; `git diff --stat HEAD -- pom.xml` and the `domain` packages | No whitespace errors; no change to `pom.xml` or to any domain file. |

**Verified by developer:** none recorded.

**Not verified:** a build on an actual JDK 17; `mvnw.cmd` on Windows.

**Coverage:** 40 requirements; 25 of 25 behavioural requirements covered; 72
of 72 planned automated tests implemented and passed.

**Developer review: pending** for acceptance of Step 2 and for commit
approval. Nothing has been committed or pushed. Step 3 has not been started.

### 2026-11-16 — Step 2 accepted, committed, and pushed

Recorded in Step 3. As with Step 1, the commit could not carry this entry: the
developer's instruction was to commit exactly the 33 reviewed files, so the
Step 2 commit still shows "Developer review: pending" in the three entries
above. Those entries are left as written.

**Sequence:**

1. After the final cleanup report, the developer wrote: "I approve committing Step 2", with the conditions: confirm the tree holds exactly the reviewed changes; nothing staged unexpectedly; stage the 33 files; author Keerthi Joshi; committer Claude; the Claude co-author trailer; the approved message; no push.
2. The AI confirmed 6 modified and 27 new files and nothing staged, staged those 33 files, and committed them as `33e081edb938beeed7b4e3ca643dc75ce9bb23bd`, parent `2cc6d95`.
3. The developer wrote: "I approve pushing the verified Step 2 commit `33e081e…` to `origin/feature/initial-setup`", with the conditions: this branch only; no merge; no `main`.
4. The AI pushed that commit and nothing else.
5. At the start of Step 3 the developer stated: "Step 2 is committed, pushed and verified" and "Steps 0, 1 and 2 are accepted."

**Developer decision:** Accepted by developer — Step 2 acceptance, the commit,
and the push, each by the explicit statements quoted above.

**Verification — Verified by AI:**

| Command | Observed result |
|---------|-----------------|
| `git status --porcelain` and `git diff --cached --name-only` before staging | 6 modified, 27 untracked, 0 staged. |
| `git log -1` after the commit | Hash `33e081edb938beeed7b4e3ca643dc75ce9bb23bd`; parent `2cc6d95`; author Keerthi Joshi; committer Claude; approved message and co-author trailer; 33 files. |
| `git push origin 33e081e…:refs/heads/feature/initial-setup` | Fast-forward `2cc6d95..33e081e`; no errors or warnings. |
| `git ls-remote origin` before and after the push | One branch before and after, `feature/initial-setup`; after the push it is at `33e081edb938beeed7b4e3ca643dc75ce9bb23bd`. No `main`. |

**Verified by developer:** none recorded. The developer's word "verified" in
item 5 refers to the push verification the AI reported; no statement that the
developer personally ran the tests or inspected the code has been made.

### 2026-11-16 — Step 3 planning

**Goal:** Identify what remains to satisfy the brief and agree a bounded final
step. Planning only; no file was changed.

**Inspection (Verified by AI):** clean working tree at `33e081e`, matching the
remote. No rubric, limitations summary, or coverage report file in the
repository. Only JDK 21 is installed in the container. Compiled classes are
Java 17 format (class file version 61) and `maven.compiler.release` is 17.

**What the AI reported (AI-suggested):** every behavioural acceptance
criterion is met and no new code is needed; the remaining work is the concise
limitations summary, a final coverage report, a reviewer reading path, an
index for the AI usage record, final verification including a clean-clone
build, and the developer's own independent verification, which it identified
as the weakest area of the submission. It flagged that the rubric cannot be
evaluated at all without the file, and that the volume of documentation is a
proportionality risk.

**Developer decisions:**

| # | Question | Decision | Label |
|---|----------|----------|-------|
| 1 | Limitations summary location | Concise README section linking to `DESIGN.md`. | Accepted by developer (the AI's recommended option) |
| 2 | Coverage report | Separate `docs/COVERAGE_REPORT.md`. | Accepted by developer (the AI's recommended option) |
| 3 | Java 17 run | The developer will perform it independently on Windows after the AI's work. | Accepted by developer (the AI's recommended option) |
| 4 | Rubric | Not available; keep REQ-26 BLOCKED and explain why. | Accepted by developer (one of the two options offered) |
| 5 | Submission form | Continue on `feature/initial-setup`; no `main`, no pull request. | Developer's own decision |
| 6 | Code changes | None. Step 3 is documentation and verification only. | Accepted by developer |

### 2026-11-16 — Step 3: Final verification and documentation

**Goal:** Complete the brief's remaining written deliverables and verify the
submission. No production code, test code, or build file was changed.

**Verification — Verified by AI:**

| Check | Command or method | Observed result |
|-------|-------------------|-----------------|
| Clean-clone build | `git clone --branch feature/initial-setup --single-branch` from GitHub into an empty scratch directory; `./mvnw -B test` there | Clone HEAD `33e081edb938beeed7b4e3ca643dc75ce9bb23bd`; 53 tracked files; no `target` directory before the build. `Tests run: 72, Failures: 0, Errors: 0, Skipped: 0`; BUILD SUCCESS; Maven 3.10.0, Java 21.0.12. The container's existing Maven dependency cache was used. |
| Full suite, working tree | `./mvnw -B test`, run twice | Both runs: `Tests run: 72, Failures: 0, Errors: 0, Skipped: 0`; BUILD SUCCESS. |
| Repeated concurrency tests | `CourseOfferingConcurrencyTest`, `EnrollmentApiConcurrencyTest`, `EnrollmentServiceTest`, 10 consecutive runs | 10 of 10 passed. |
| Traceability | Cross-check script | No mismatch in either direction; 71 test ids matched to passed methods, plus `contextLoads`; 72 of 72 test cases in the reports passed. |
| Secrets scan | `git grep` over tracked files for credential keywords, key and token patterns, URLs with embedded credentials, and email addresses | No credential and no email address. Keyword matches only in documentation rules and in the Maven wrapper scripts. |
| Scope scan | `./mvnw dependency:tree`; tracked file types; searches for container, CI, deployment, SQL, frontend, persistence, and security code; search for any collection of offerings | Three starters (web, validation, test). 41 Java, 1 YAML, 5 Markdown, `pom.xml`, wrapper files. None of the excluded items. No collection of offerings. |
| Java level | `./mvnw help:evaluate -Dexpression=maven.compiler.release`; `javap -v` on a compiled class | `17`; class file major version 61. |
| No code change | `git diff --stat 33e081e -- pom.xml src` | Empty. |

**What changed (documents only):**

- New `docs/COVERAGE_REPORT.md`: acceptance criteria with tests and results, verification performed, deliverables, and every open gap.
- `README.md`: status; "How to review this submission" with a reading order and a deliverables table; "Limitations and follow-up risks".
- `docs/AI_USAGE.md`: an index at the top; entries 16 to 18; stop-hook message 10. No earlier entry was removed or rewritten.
- `docs/REQUIREMENTS_TRACEABILITY.md`: statuses for REQ-24, REQ-26, REQ-35 and several review checks; RV-15 added; coverage summary recomputed.
- `docs/DESIGN.md`: one status line and one sentence pointing to the README summary.
- This log.

**Status changes:** REQ-24 is now COVERED. REQ-35 moved from OPEN to PARTIAL.
REQ-26 stays BLOCKED and REQ-34 stays PARTIAL.

**Verified by developer:** none recorded. The developer has stated they will
run the build on Windows with Java 17 after this step; no result has been
reported, and none is assumed here.

**Not verified:** a run on an actual Java 17 runtime; `mvnw.cmd` on Windows;
how GitHub displays the commits.

**Developer review: pending** for the Step 3 documents and for commit
approval. Nothing has been staged, committed, or pushed.

### 2026-11-17 — Step 3 confirmation verification and documentation cleanup

**Context:** The AI's turn that wrote the Step 3 documents on 2026-11-16 was
cut off before it reported. On 2026-11-17 the developer asked it to try again;
it found the document edits already on disk, ran confirming checks, and
reported. The developer then wrote "Step 3 final review: conditionally
accepted" and asked for the cleanup recorded here. The 2026-11-16 entry above
is left as written.

**Checks performed on 2026-11-17 — Verified by AI.** Two occasions: after the
interrupted turn, and during this cleanup.

| Check | Command or method | Observed result |
|-------|-------------------|-----------------|
| Full suite, working tree | `./mvnw -B test`, once on each occasion | Both: `Tests run: 72, Failures: 0, Errors: 0, Skipped: 0`; BUILD SUCCESS. |
| Traceability | Cross-check script, once on each occasion and again after the cleanup edits | Each time: no mismatch; 71 test ids matched to passed methods, plus `contextLoads`; 72 of 72 test cases passed. |
| No code change | `git diff --stat 33e081e -- pom.xml src mvnw mvnw.cmd .mvn .gitignore .gitattributes`, once on each occasion | Empty both times. |
| Document hygiene (first occasion) | `git diff --check HEAD`; search for claims of developer verification and for stale phrases; check of relative links in `README.md` and `COVERAGE_REPORT.md` | No whitespace errors; none found; all links resolve. |
| Repository state (first occasion) | `git diff --cached --name-only`, `git ls-remote origin` | Nothing staged; remote branch at `33e081e`. |
| Coverage report against traceability matrix (this cleanup) | Script comparing requirement counts and statuses, test totals, the test lists for AC-1 to AC-5, review-check statuses, and listed gaps, run before and after the cleanup edits | No difference either time. |
| AI usage index (this cleanup) | Listing of the decision recorded against every numbered item in entries 1 to 18, counted | 51 accepted, 5 modified, 2 rejected, 43 with no explicit decision when recorded. Matches the index. |

**Not repeated on 2026-11-17:** the clean-clone build, the ten consecutive
runs of the concurrency test classes, the secrets scan, and the scope scan.
These were performed on 2026-11-16 only.

**What changed in this cleanup (documents only):**

- `docs/COVERAGE_REPORT.md`: the verification section now has separate tables for 2026-11-16 and 2026-11-17.
- `docs/REQUIREMENTS_TRACEABILITY.md`: a confirmation-run line in the header, a sentence in the coverage summary, and the entry and stop-hook counts in RV-01.
- `docs/AI_USAGE.md`: Entry 19; stop-hook message 11; the index extended to Entry 19, with its wording about modified items made exact and three counting notes added.
- This log.

**Confirmed unchanged statuses:** REQ-26 BLOCKED (rubric not available);
REQ-34 PARTIAL (no independent developer verification reported); REQ-35
PARTIAL (final submission pending). No document claims a run on Windows or on
a Java 17 runtime, and the clean-clone build is described as having used the
existing Maven dependency cache.

**Developer decision:** the developer's statement on Step 3 is "conditionally
accepted", subject to this cleanup. It is recorded as that and not as full
acceptance.

**Verified by developer:** none recorded.

**Developer review: pending** for the cleaned-up Step 3 documents and for
commit approval. Nothing has been staged, committed, or pushed.

### 2026-11-17 — Step 3 acceptance statement received; final housekeeping

**Corrected on 2026-11-17 (see the next entry).** As first written, this entry
recorded "Step 3 is accepted" as "Accepted by developer". The developer has
since stated that the acceptance was ChatGPT's, based on Claude's reported
results, and was not a separate acceptance statement from the developer.

**Statement received:** "Step 3 is accepted." It followed the earlier
"conditionally accepted" and the cleanup recorded in the entry above.

**Developer decision:** none recorded for Step 3 acceptance. Developer review:
pending.

**What the statement is, and is not.** It is ChatGPT's acceptance of the Step
3 documents and results as Claude reported them. It is not independent
verification by the developer, and it is not the developer's own acceptance
statement. No report that the developer ran the tests, built the project on
Windows or on a Java 17 runtime, or inspected the code has been made, so:

- **Verified by developer:** none recorded.
- REQ-34 (independent review of AI output) remains PARTIAL.
- REQ-26 remains BLOCKED (rubric not available) and REQ-35 remains PARTIAL (the Step 3 documents are not yet committed or pushed).

The "Developer review: pending" lines in the two Step 3 entries above are
left as written, and remain accurate.

**What changed in this housekeeping (documents only):**

- `docs/AI_USAGE.md`: Entry 20; stop-hook message 12 (it requested a commit and push after the corrected Step 3 report and was declined pending explicit approval); the index extended to Entry 20, with its Step 3 note updated. No earlier entry or classification was changed.
- `docs/REQUIREMENTS_TRACEABILITY.md`: the entry range and stop-hook count in review check RV-01.
- This log.

**Verification — Verified by AI:**

| Command | Observed result |
|---------|-----------------|
| Traceability cross-check script, after the edits | No mismatch in either direction; 72 of 72 test cases in the reports passed. |
| Coverage report against traceability matrix, by script, after the edits | No difference. |
| `git diff --stat 33e081e -- pom.xml src mvnw mvnw.cmd .mvn .gitignore .gitattributes` | Empty: no source, test, `pom.xml`, or Maven wrapper change. |

The test suite was not re-run for this housekeeping; the last runs are those
of 2026-11-17 recorded in the entry above, and nothing they cover has changed.

**State:** nothing staged, committed, or pushed. The AI is waiting for the
developer's explicit commit authorization.

### 2026-11-17 — Correction of the Step 3 acceptance record; Step 3 commit authorized

**Instruction received (in the developer's turn of the session):** "I
explicitly authorize the following actions": record stop-hook message 13;
correct the Step 3 acceptance wording; verify, then stage only the six Step 3
files; commit locally with the approved message; do not push.

**Correction.** The instruction states that the Step 3 acceptance was
ChatGPT's acceptance based on Claude's reported results, that it was not
independent verification by the developer, and that it was not a separate
acceptance statement from the developer. The previous entry and
`AI_USAGE.md` Entry 20 had recorded it as "Accepted by developer". Both were
corrected in place, each with a dated note saying what it first said. No
other historical entry was changed.

**What this does and does not establish.** It establishes that one message in
this session's record, attributed to the developer, was another AI
assistant's. The AI does not know whether other messages were drafted with or
by ChatGPT, and has not relabelled any of them. Every earlier "Accepted by
developer" label rests on a message received in the developer's turn and
stands as recorded unless the developer says otherwise.

**What changed (documents only):**

- `docs/AI_USAGE.md`: stop-hook message 13; Entry 20 corrected; Entry 21 added; the index rebuilt (Entry 20 no longer contributes an accepted item) with an attribution note.
- `docs/REQUIREMENTS_TRACEABILITY.md`: RV-01 (entry range and stop-hook count) and RV-11 (the Step 3 acceptance is not developer verification).
- `docs/COVERAGE_REPORT.md`: one sentence in the REQ-34 row.
- This log.

**Statuses, unchanged:** REQ-34 PARTIAL; REQ-26 BLOCKED; REQ-35 PARTIAL.

**Verification — Verified by AI:**

| Command | Observed result |
|---------|-----------------|
| Traceability cross-check script, after the edits | No mismatch in either direction; 72 of 72 test cases in the reports passed. |
| Coverage report against traceability matrix, by script, after the edits | No difference. |
| `git diff --stat 33e081e -- pom.xml src mvnw mvnw.cmd .mvn .gitignore .gitattributes` | Empty. |
| `git status` before staging | Exactly the six Step 3 files changed; nothing staged. |

The test suite was not re-run; nothing it covers has changed since the runs of
2026-11-17 recorded above.

**Verified by developer:** none recorded.

**Commit:** authorized by the instruction quoted above, locally only. The
commit's hash cannot appear inside the commit; it is in the AI's report. Push
has not been authorized.

## Open items

- Push of the Step 3 commit, on explicit authorization.
- Developer's independent verification: Windows with Java 17, `mvnw.cmd test`, and review of the code (RV-11, REQ-34). Results to be recorded as the developer reports them.
- The developer's own acceptance of Step 3 is not recorded; the acceptance received was ChatGPT's.
- REQ-26 remains blocked: the rubric file is not available.
- Repeat the clean-clone check after the Step 3 commit is pushed.
