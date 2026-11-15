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

## Open items

- REST API design and implementation, with API tests TC-A-01 to TC-A-12.
- Confirm or replace the provisional rows TC-A-08 and TC-A-10 against the API design.
- REQ-26 is blocked until `Course-Registration-Rubric-Score.md` is provided.
- Developer's independent verification is not yet recorded (RV-11).
- Final limitations summary and final coverage report.
- Build once on a real JDK 17.
- Run the `mvnw.cmd` commands once on Windows.
