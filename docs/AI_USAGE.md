# AI Prompt and Use Record

Record of every AI-assisted engineering activity in this project, as required
by the assessment brief (acceptance criterion 7).

**Tool:** Claude Code (cloud session), working directly in this repository.

Each entry gives the prompt, a summary of the AI output, the developer's
decision, and the verification performed. Prompts are condensed with nothing
material removed. No credentials or secrets appear in any prompt.

## Labels

| Label | Meaning |
|-------|---------|
| **AI-suggested** | Proposed by the AI; no developer decision recorded yet. |
| **Accepted by developer** | The developer explicitly accepted the suggestion as proposed. |
| **Modified by developer** | The developer changed or corrected the suggestion. |
| **Rejected by developer** | The developer explicitly declined the suggestion. |
| **Verified by AI** | The AI ran the stated command in the session and observed the stated result. |
| **Verified by developer** | The developer explicitly confirmed they ran or checked it themselves. |
| **Developer review: pending** | No explicit developer confirmation has been recorded. |

## Planning conversations, 2026-11-15 to 2026-11-16 (not recorded here)

AI was used in project planning conversations before the first session in this
repository. Their outcomes are listed in
[DEVELOPMENT_LOG.md](DEVELOPMENT_LOG.md) as conversation-recorded events, as
reported by the developer. The prompts and AI outputs from those conversations
were not available when this record was written, so none are reproduced or
reconstructed here.

---

## Entry 1 — Environment verification

**Date:** 2026-11-15
**Activity:** Environment and repository checks before any work.

**Prompt:**

> We are starting a fresh Claude Code Cloud session. Before implementing
> anything, verify the environment: (1) confirm the GitHub repository is
> `keejoshi/course-waitlist`; (2) confirm the current branch and Git remote;
> (3) confirm this is a cloud environment, not my local Windows machine;
> (4) confirm Java 17+ is available and Maven can run; (5) check whether the
> repository is empty or contains existing files; (6) confirm you have
> permission to create and push a feature branch. Do NOT create files,
> generate a Spring Boot project, commit, or push anything yet. Report the
> results of these checks and wait for my approval before proceeding to
> Step 0. Strict isolation: this assessment belongs to Keerthi Joshi. Never
> access, reuse, modify, or integrate ERPX code, files, credentials, or
> architecture.

**AI output summary:** Ran read-only checks and reported five of six
confirmed. Push permission was reported as not verifiable without a real push.
The AI also noted that the container has JDK 21 rather than 17, suggested
targeting Java 17 in the POM, and noted that the first branch pushed to an
empty remote becomes GitHub's default branch.

**Developer decision:** Accepted by developer — "Approved. Proceed with Step 0."

**Verification:** Verified by AI — `git remote -v`, `git status -sb`, `ls -la`,
`git ls-remote origin`, `uname -a`, `java -version`, `mvn -version`; results
are listed in the development log. Verified by developer: none recorded.

**Files/decisions informed:** no files changed; informed the Java 17 compiler level in `pom.xml`.

---

## Entry 2 — Step 0: project skeleton

**Date:** 2026-11-15
**Activity:** Project scaffolding and documentation setup.

**Prompt:**

> Approved. Proceed with Step 0: Initial project setup. Create the minimal
> Spring Boot project skeleton in the current GitHub repository. Requirements:
> Spring Boot 3.5.x; Java 17 compatibility; Maven; package
> `com.assessment.coursewaitlist`; dependencies Spring Web, Validation, Spring
> Boot Test; generate a proper Maven project structure; create a meaningful
> README.md with project overview and initial setup instructions; create
> docs/DEVELOPMENT_LOG.md and docs/AI_USAGE.md; include the previously agreed
> documentation rules and historical backfill; add a basic application startup
> test. Do not implement enrollment, waitlist, or business logic yet. Do not
> add a database, frontend, Docker, or unnecessary dependencies. Do not merge
> into main. Do not push anything until I approve the proposed commit and
> push. After implementation: show the complete file tree, explain every
> dependency added, run Maven tests and report actual results, show the
> proposed Git changes, and wait for my review before committing or pushing.

Supplied with the prompt: a locally generated Spring Initializr zip and,
mid-task, the assessment brief PDF.

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | Used the supplied zip as the base but rewrote `pom.xml` for Spring Boot 3.5.16, because the zip was Boot 4.0.9. | Accepted by developer |
| 2 | Corrected the package from the zip's `com.assesssent` to `com.assessment`. | Accepted by developer |
| 3 | Replaced the Boot 4 starter names (`-webmvc`, `-webmvc-test`, `-validation-test`) with the Boot 3.5 equivalents (`-web`, `-test`). | Accepted by developer |
| 4 | Kept the zip's Maven wrapper, `.gitignore`, and `.gitattributes` unchanged; dropped `HELP.md` and the empty POM metadata overrides. | Accepted by developer |
| 5 | Named the artifact `course-waitlist` and the main class `CourseWaitlistApplication`. | Accepted by developer |
| 6 | Wrote a startup test asserting that the application context and the application bean are present. | Accepted by developer |
| 7 | Wrote `README.md` with overview, scope from the brief, and setup instructions. | Modified by developer — see Entry 3 |
| 8 | Drafted documentation rules and a backfill table from the brief and the session only, flagging that the previously agreed versions were not available to the AI. | Modified by developer — see Entry 3 |
| 9 | Declined an automated stop-hook message asking it to commit and push, because the developer had not approved. | Accepted by developer |

Basis for "Accepted by developer": the developer wrote "Step 0 looks good, and
I appreciate you respecting the commit/push approval boundary" after the AI's
report. This records acceptance of the Step 0 result as reported; it is not a
claim that the developer ran any command, and commit and push approval was
not given at that point.

**Verification:**

- Verified by AI — `mvn -B test`: `Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`, BUILD SUCCESS, Spring Boot v3.5.16 in the log.
- Verified by AI — `./mvnw -B test` (wrapper, Maven 3.10.0): same result.
- Verified by AI — `mvn -B dependency:tree`: direct dependencies are the three starters only.
- Verified by AI — Maven Central metadata query: 3.5.16 was the latest 3.5.x release on 2026-11-15.
- Verified by developer: none recorded.
- Not verified at this point: build on a real JDK 17; `./mvnw spring-boot:run` (verified in Entry 3); `mvnw.cmd` on Windows.

**Files informed:** `pom.xml`, `CourseWaitlistApplication.java`,
`CourseWaitlistApplicationTests.java`, `application.yaml`, `README.md`,
`docs/DEVELOPMENT_LOG.md`, `docs/AI_USAGE.md`, and the wrapper and git
configuration files copied from the zip.

---

## Entry 3 — Step 0 documentation corrections

**Date:** 2026-11-15
**Activity:** Documentation corrections before the Step 0 commit.

**Prompt:**

> Step 0 looks good. Before I approve the commit, make the following
> documentation corrections. (1) Apply the agreed documentation rules:
> DEVELOPMENT_LOG.md and AI_USAGE.md must be updated in the same commit as the
> changes they describe; DEVELOPMENT_LOG.md must be chronological; AI_USAGE.md
> must include prompts, output summaries, and developer decisions; never claim
> verification unless the actual command was run; never claim developer review
> unless explicitly confirmed; use these labels consistently: AI-suggested,
> Accepted by developer, Modified by developer, Rejected by developer,
> Verified by AI, Verified by developer, Developer review: pending.
> (2) Backfill the historical events from our project planning conversations
> dated November 15–16, 2026: assessment requirements and scope reviewed;
> Keerthi Joshi's repository identified; initial repository found empty;
> architecture and implementation approach discussed; stack finalized (Spring
> Boot 3.5.x, Java 17 compatibility, Maven, REST, in-memory state);
> enrollment, duplicate enrollment, FIFO waitlist and prerequisite policies
> agreed; strict ERPX isolation established; new Claude Code Cloud environment
> created and verified; Step 0 skeleton generated and tests executed
> successfully. Distinguish conversation-recorded decisions from actual
> command verification. Do not invent historical AI outputs or developer
> verification. (3) Update the README to document the Maven wrapper commands
> `./mvnw test` and `./mvnw spring-boot`, mentioning the Windows wrapper
> equivalents where appropriate. (4) Re-run the tests after documentation
> changes and show the diff. Do not commit or push yet. Wait for my review.

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | Replaced the AI-drafted rules in `DEVELOPMENT_LOG.md` with the developer's rules and label set; applied the labels in both documents. | Accepted by developer |
| 2 | Rewrote the backfill as a chronological log entry. Events 1–7 are marked conversation-recorded with no AI output or verification attributed; events 8–9 are the command-verified environment and Step 0 entries. | Accepted by developer |
| 3 | Did not write out the content of the agreed enrollment, waitlist, and prerequisite policies, because only the fact of agreement was supplied; left this as an open item for the design specification. | Accepted by developer |
| 4 | Documented the run command in `README.md` as `./mvnw spring-boot:run`. The prompt's `./mvnw spring-boot` is a plugin prefix without a goal, which Maven does not accept. | Accepted by developer |
| 5 | Added Windows equivalents (`mvnw.cmd test`, `mvnw.cmd spring-boot:run`) to `README.md`. | Accepted by developer |
| 6 | Recorded Step 0 suggestions 1–6 as "Accepted by developer" on the basis of the developer's "Step 0 looks good" statement. | Accepted by developer |

Basis for "Accepted by developer": after reviewing the AI's report of these
corrections, the developer wrote "I approve Step 0, including the project
skeleton, README, and documentation scaffolding" and approved the commit.
Push approval was not given at that point. This is not a claim that the
developer ran any command.

**Verification:**

- Verified by AI — `./mvnw -B test` after the documentation changes: `Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`, BUILD SUCCESS.
- Verified by developer: none recorded.
- Verified by AI — `./mvnw -B spring-boot:run`: log shows `Tomcat started on port 8080` and `Started CourseWaitlistApplication`; `curl http://localhost:8080/` returned HTTP 404, as expected with no endpoints.
- Not verified: the Windows `mvnw.cmd` commands were not run (no Windows machine in the session); build on a real JDK 17.

**Files informed:** `README.md`, `docs/DEVELOPMENT_LOG.md`, `docs/AI_USAGE.md`.

---

## Entry 4 — Step 0 commit, author amendment, and push

**Date:** 2026-11-15
**Activity:** Version control operations, each on explicit developer approval.

**Prompts (four messages, condensed):**

> (a) I approve Step 0, including the project skeleton, README, and
> documentation scaffolding. Before committing: confirm all 12 expected files
> are present; confirm there are no unrelated changes; confirm the
> documentation contains no fabricated verification or developer approvals;
> show the final staged changes summary. Then commit with the approved message
> "Step 0: Spring Boot 3.5 project skeleton and documentation scaffolding". Do
> not push yet. Report the commit hash and working tree status.
>
> (b) Step 0 commit is approved. Do not push yet. First, show me the configured
> Git author name and email, and explain how the current commit author
> appears. Do not amend the commit or change Git identity without my approval.
>
> (c) Amend the existing Step 0 commit so that the author is Keerthi Joshi with
> the supplied GitHub noreply email. Retain Claude's AI co-author disclosure.
> Do not change any project files. Do not push anything. Do not merge into
> main.
>
> (d) I approve pushing the Step 0 commit. Push the current commit
> `1c709c3b792c25363bba9550bf92b73f0c90ae7e` to the remote branch
> `feature/initial-setup`. Push only this commit. Do not push to main, create
> or merge into main, modify project files, amend, or push any other branch.
> Verify the remote branch points to the exact approved commit. If the push
> fails, stop and report the error.

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | Before committing, updated the two documentation files to record the developer's approval (they still said "pending"), and reported that edit. Committed 12 files as `1caa0bd`. | Accepted by developer — "Step 0 commit is approved." |
| 2 | Reported that author and committer were the container's default identity ("Claude") and that the environment signs commits automatically. Set out three options and recommended re-authoring as the developer while keeping the AI co-author trailer. | Accepted by developer — chose that option |
| 3 | Amended the author only; left the committer and signature unchanged and said so. New hash `1c709c3b792c25363bba9550bf92b73f0c90ae7e`. | Accepted by developer — approved pushing this commit |
| 4 | Pushed that commit to `feature/initial-setup` only, and noted that this branch is now GitHub's default because it is the only one. | Accepted by developer — "Step 0 is approved, committed, and successfully pushed." |

**Verification:**

- Verified by AI — `./mvnw -B test` before the commit: 1 run, 0 failures.
- Verified by AI — tree hash identical before and after the amend (`git rev-parse HEAD^{tree}`), so no file content changed.
- Verified by AI — `git ls-remote origin` after the push: one ref, `refs/heads/feature/initial-setup`, at the approved hash.
- Verified by developer: none recorded.
- Not verified: how GitHub displays the author, committer, and signature.

**Files/decisions informed:** commit `1c709c3`; remote branch `feature/initial-setup`.

---

## Entry 5 — Step 1 domain design

**Date:** 2026-11-15
**Activity:** Design proposal and review, before any code.

**Prompt (condensed):**

> We are starting Step 1: Domain Model and Core Enrollment Design. First,
> inspect the existing project and assessment brief. Before implementing:
> (1) propose the domain model for Learner, Course, and CourseOffering;
> (2) explain responsibilities and invariants of CourseOffering; (3) explain
> how completed prerequisites are represented; (4) explain how enrollment and
> waitlist membership will be represented; (5) explain how FIFO ordering will
> be preserved; (6) explain how thread safety will be handled; (7) propose
> domain-level tests. Do not implement anything yet. Keep all previously
> approved policies: duplicate enrollment returns HTTP 409; ineligible
> waitlisted learners are skipped during promotion but remain on the waitlist;
> prerequisites are checked on initial enrollment and promotion; one offering
> only, in-memory; no frontend or database.

**AI output summary:** Proposed the model, invariants, FIFO and locking
approach, and a test plan, and raised four questions. It pointed out a
consequence of the approved policies: because ineligible learners are rejected
at enrollment, an ineligible learner can only be on the waitlist if a
completion is revoked after they joined, so `Learner` needs a way to revoke.

| # | AI suggestion | Developer decision |
|---|---------------|--------------------|
| 1 | `Course` immutable; `Learner` with a set of completed course codes; `CourseOffering` as the aggregate owning enrolled list and waitlist; plain string ids. | Accepted by developer |
| 2 | Outcomes as result values (`EnrollmentResult`, `DropResult`), exceptions only for malformed calls. | Accepted by developer |
| 3 | Insertion-ordered waitlist; promotion walks from the head and skips ineligible learners in place. | Accepted by developer |
| 4 | One lock per offering; immutable snapshots; concurrent set for completions. | Accepted by developer |
| 5 | When a seat is open, offer it to eligible waiters before a new requester (recommended over the simpler alternative). | Accepted by developer |
| 6 | Treat a drop by a waitlisted learner as `NOT_ENROLLED`, to stay in scope. | Rejected by developer — waitlisted learners must be able to leave, with a distinct outcome `DROPPED_FROM_WAITLIST` and no promotion |
| 7 | A repeat request from a waitlisted learner returns 409 with a distinct reason. | Accepted by developer |
| 8 | Add `revokeCompletion` to `Learner`. | Accepted by developer, who added that enrolled learners are not removed on revocation |
| 9 | Put the design specification in `docs/DESIGN.md`. | Accepted by developer |

Basis for the "Accepted by developer" labels: the developer wrote "I have
reviewed your domain design. The overall design is approved, subject to the
following decisions and clarifications", then listed the four decisions and
explicit approvals for string ids, immutable `Course`, `CourseOffering` owning
the state, FIFO ordering, skip-and-retain, the vacant-seat rule, and
`docs/DESIGN.md`. Rows 2 and 4 (result values; locking) were not named
individually and rest on the overall approval.

**Verification:** Verified by AI — `git fetch` and `git ls-files` confirmed the
local tree matched the pushed branch and held only the Step 0 skeleton. No
code existed to test at this point.

**Files/decisions informed:** `docs/DESIGN.md` sections 2-6; all domain classes.

---

## Entry 6 — Requirements checklist and traceability

**Date:** 2026-11-15
**Activity:** Requirements analysis and test planning.

**Prompts (three messages, condensed):**

> (a) [Design decisions as recorded in Entry 5.] One critical requirement:
> EVERY test case and acceptance criterion in the assessment brief MUST be
> covered. Extract every acceptance criterion and test scenario into a master
> checklist; assign a unique ID to every requirement and test case; maintain a
> traceability matrix mapping requirement, implementation component, test case
> ID, and test result; every requirement must have at least one corresponding
> test; do not mark a requirement covered merely because related code exists;
> do not mark a test passed unless it was actually executed; report anything
> that cannot be implemented or tested as BLOCKED or NOT COVERED; before every
> commit perform a requirements coverage review; the final submission must
> include a complete coverage report. Do not reduce scope or skip tests
> without asking. Show me the complete master test checklist before continuing.
>
> (b) I approve the separation between automated behavioural tests and
> documentation/scope review checks, with corrections: every behavioural
> criterion must have an executable, actually-run test; keep TC-A-08 and
> TC-A-10 provisionally and do not add registries just for them; TC-D-17 must
> use an offering with exactly one remaining seat; explain the conditional
> concurrency requirement; keep REQ-26 BLOCKED until the rubric file is
> provided; preserve the brief's acceptance criteria exactly and distinguish
> brief requirements from developer-approved requirements and AI-derived
> scenarios; no unverified claims.
>
> (c) The checklist is approved, subject to: retain a separate explicit test
> for the waitlist-priority decision; keep the regained-eligibility scenario
> as an additional test and document the open-seat policy precisely; add
> review checks for starter inspection and the fallback justification, for
> scope, and for independent AI review; treat the 7-8 hour timebox as
> guidance. Then proceed with Step 1 only.

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | Master checklist: 31 requirements, 48 automated tests, 8 review checks. | Modified by developer — corrections in (b) and (c) |
| 2 | Proposed review evidence, not unit tests, for documentation and scope requirements, and flagged that this departed from "every requirement has a test". | Accepted by developer, with the condition that every behavioural criterion has an executed test |
| 3 | Flagged REQ-26 as blocked because the rubric file was not supplied. | Accepted by developer |
| 4 | Flagged that the concurrency clause is conditional and not triggered, and proposed concurrency tests anyway. | Accepted by developer, who asked for the reasoning to be written down (`DESIGN.md` section 6) |
| 5 | Rewrote TC-D-17 with exactly one open seat. | Accepted by developer — checklist approved |
| 6 | Kept TC-A-08 and TC-A-10 provisional, with reasoning for each. | Accepted by developer |
| 7 | Final checklist after (c): 35 requirements, 50 automated tests, 12 review checks. Added TC-D-32 and TC-D-33 for the open-seat policy, REQ-32 to REQ-34 and RV-09 to RV-11 for the developer's three review checks. | Developer review: pending |
| 8 | Added REQ-35 / RV-12 ("submit the implementation and all listed deliverables together"), which the developer had not listed, from the brief's candidate expectations. | AI-suggested; Developer review: pending |

**Verification:** The checklist was produced by reading the brief; there is no
command to run. The requirement and test counts in
`docs/REQUIREMENTS_TRACEABILITY.md` were recounted by the AI against its
tables. Verified by developer: none recorded.

**Files/decisions informed:** `docs/REQUIREMENTS_TRACEABILITY.md`; the test
method names in the domain test classes.

---

## Entry 7 — Step 1 implementation

**Date:** 2026-11-15
**Activity:** Code generation, test generation, and documentation.

**Prompt (condensed):**

> Update the checklist and traceability totals accordingly, then proceed with
> Step 1 only. Implement the approved domain classes and domain tests. Update
> docs/DESIGN.md, docs/REQUIREMENTS_TRACEABILITY.md, docs/DEVELOPMENT_LOG.md
> and docs/AI_USAGE.md. Run actual tests and report changed files, test totals
> and actual execution results, requirement coverage status, remaining gaps
> and blocked items, and a suggested commit message. Do not commit, push,
> merge or modify main without my explicit approval.

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | `Course`, `Learner`, `CourseOffering`, `EnrollmentResult`, `DropResult`, and a package-private `Require` helper, in `com.assessment.coursewaitlist.domain`. No framework imports; no new dependency. | Developer review: pending |
| 2 | A single promotion pass shared by `drop` and `enroll`, which fills every open seat in FIFO order and skips ineligible learners in place. | AI-suggested; Developer review: pending |
| 3 | `enroll` runs the promotion pass only after the duplicate and prerequisite checks, so rejected requests change nothing. Consequence: limitation L1 (a waiter who regains eligibility while a seat is vacant waits for the next drop or valid new request). | AI-suggested; Developer review: pending |
| 4 | Both result types carry a list of promoted learner ids. | AI-suggested; Developer review: pending |
| 5 | 37 new tests, one method per checklist id: 33 domain, 4 concurrency. | Developer review: pending |
| 6 | `docs/DESIGN.md` and `docs/REQUIREMENTS_TRACEABILITY.md`; updates to the development log, this record, and `README.md`. The README update was not in the developer's list; it was changed so its status and structure sections were not left stale. | AI-suggested; Developer review: pending |

**Verification:**

- Verified by AI — `./mvnw -B test`: `Tests run: 38, Failures: 0, Errors: 0, Skipped: 0`, BUILD SUCCESS. The tests passed on their first run; no test was changed to make it pass.
- Verified by AI — fault injection in a scratch copy outside the repository, to check that the tests can fail:
  - lock made ineffective: TC-C-01, TC-C-02, TC-C-04 failed; TC-C-03 passed in that run;
  - promotion ignoring eligibility: TC-D-13, 14, 15, 16, 17, 18, 32, 33 failed;
  - no promotion pass before a new enrollment: TC-D-17 failed.
- Verified by AI — `git status` after the fault injections: the repository working tree was unaffected.
- Verified by AI — `git diff --stat HEAD -- pom.xml`: empty.
- Verified by developer: none recorded.
- Not verified: a build on an actual JDK 17; `mvnw.cmd` on Windows.

**Candid notes on the AI's contribution:** all Step 1 code, tests, and document
text were written by the AI. The policies they implement were set by the
developer (Entries 5 and 6); the implementation choices in rows 2-4 above were
the AI's and have not yet been reviewed. The fault injections are evidence
that the tests are sensitive to the targeted faults, not a proof of
correctness, and they are the AI checking its own work: they do not replace
the developer's independent review.

**Files informed:** the six domain source files, the four domain test files,
`docs/DESIGN.md`, `docs/REQUIREMENTS_TRACEABILITY.md`,
`docs/DEVELOPMENT_LOG.md`, `README.md`.

---

## Entry 8 — Step 1 code-quality review

**Date:** 2026-11-15
**Activity:** Code review of the AI's own uncommitted Step 1 work, at the developer's request.

**Prompt (condensed):**

> Step 1 report received. Do not commit or push. Before I approve Step 1,
> perform a focused code-quality review of the actual implementation. Review:
> (1) CourseOffering.java — concurrency, lock boundaries, enrollment/drop
> sequencing, FIFO promotion, eligibility, and state invariants; (2) all
> domain tests — meaningful assertions, coverage of each approved scenario,
> and whether any test can pass despite incorrect state;
> (3) CourseOfferingConcurrencyTest.java — synchronization reliability and
> flakiness; (4) Learner.java and Course.java — immutability, validation, and
> thread-safety; (5) EnrollmentResult.java and DropResult.java — completeness
> and correctness; (6) docs/REQUIREMENTS_TRACEABILITY.md — validate every test
> mapping and status; (7) docs/AI_USAGE.md and docs/DEVELOPMENT_LOG.md —
> verify factual accuracy and consistency. Do not change code during this
> review. Report any bugs or correctness risks; weak, redundant, or missing
> tests; any mismatch between implementation, approved decisions, and
> documentation; exact file/line references; and whether you recommend
> accepting Step 1 as-is or making corrections.

**AI output summary:** Changed nothing. Reported no defect in the enroll,
drop, or promotion logic, and 21 findings elsewhere, with file and line
references, and recommended corrections before acceptance:

| Group | Findings |
|-------|----------|
| Code | F1 no atomic view of the whole offering (medium); F2 eligibility not atomic across several prerequisites (low); F3 result types accept contradictory values (low); F4 `Course` validates before copying (very low). |
| Tests | T1 TC-D-21 could not detect a wrongly triggered promotion (medium); T2 TC-C-03 passed with the lock disabled (medium); T3 enrolled-with-promotion untested; T4 check order half tested; T5 TC-D-24 thin; T6 no direct result-type tests; T7 misleading comment. |
| Traceability | D1 REQ-13 did not list its tests; D2 Table A.1 and section C.1 disagreed; D3 TC-D-29 mapped to the wrong requirement and two approved decisions had no id; D4 REQ-20 did not name TC-A-01; D5 TC-D-30 loosely mapped. |
| Records | A1 stop-hook declines misrecorded and over-labelled; A2 one decision labelled "Rejected" here and "Modified" in the log; A3 overstated fault-injection claim; A4 missing basis quote in Entry 5; A5 stale entry range. |

Several of these were errors in the AI's own earlier output: the weak test
(T1), the mis-mapped rows (D1-D5), and the inaccurate records (A1-A3).

**Developer decision:** Accepted by developer — "I approve fixing all findings
before accepting Step 1." The developer specified the fix for each finding
(Entry 9).

**Verification:**

- Verified by AI — mapping cross-check script over `REQUIREMENTS_TRACEABILITY.md`: it reported the REQ-13 and REQ-20 mismatches (D1, D4).
- Verified by AI — concurrency test class run 20 times in a row: 20 of 20 passed.
- Verified by AI — `./mvnw -B test`: 38 run, 0 failures; `git status`: no file changed by the review.
- F1 was found by reading the code; it was not observed as a failure at review time. It was later demonstrated by fault injection M5 (Entry 9).
- Verified by developer: none recorded.

**Files/decisions informed:** every change in Entry 9.

---

## Entry 9 — Step 1 corrections after the review

**Date:** 2026-11-15
**Activity:** Code, test, and documentation corrections.

**Prompt (condensed):**

> I approve fixing all findings before accepting Step 1. Code — F1: add an
> atomic offering snapshot containing enrolled learners, waitlisted learners
> and available seats, captured under one lock; use it for the upcoming API
> state query. F2: make prerequisite eligibility checks atomic across multiple
> prerequisites, or provide a concrete justification if you conclude this is
> unnecessary. F3: validate EnrollmentResult and DropResult constructors so
> contradictory outcomes cannot be created. F4: copy Course prerequisite
> collections before validation, then validate the copied collection.
> Tests — T1: strengthen TC-D-21 using a vacant seat and an eligible waiter.
> T2: strengthen TC-C-03 or explicitly document its limitations and ensure the
> concurrency suite provides reliable evidence of lock correctness. T3: add a
> test where a waiter is promoted and the new requester also gets a seat.
> T4: add a test that an already-waitlisted learner receives
> ALREADY_WAITLISTED even when their prerequisite has since been revoked.
> T5: extend TC-D-24 so a later promotion cannot evict an enrolled learner
> whose prerequisite was revoked. T6: add direct tests for result-type
> validation and collection immutability. T7: correct the concurrency test
> comment. Traceability — D1-D5: correct every mapping and classification;
> add a requirement ID for the Course immutability and non-blank identifier
> decisions; recalculate all totals. Documentation — A1-A5: correct the
> records; record this review as a new AI activity; state precisely which
> tests failed and which passed under mutation; ensure developer verification
> remains pending until I personally review and confirm it. Run the complete
> suite; re-run relevant mutation checks including the improved TC-C-03. Do
> not commit, push, merge or modify main. Do not begin API implementation.

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | F1: `OfferingSnapshot` record and `CourseOffering.snapshot()`. | Developer review: pending |
| 2 | F2: fixed, with no justification offered for leaving it. `Learner` completions moved from a lock-free concurrent set to a set guarded by a private lock; lock order offering then learner. | AI-suggested (choice of fix); Developer review: pending |
| 3 | F3: both result constructors reject contradictory outcome/promotion combinations and null, blank, or repeated ids. The repeated-id check goes slightly beyond what was asked. | Developer review: pending |
| 4 | F4: `Course` copies, then validates the copy. | Developer review: pending |
| 5 | T1, T5: TC-D-21 and TC-D-24 extended. T3, T4, T6: new TC-D-34, 35, 36, 37. | Developer review: pending |
| 6 | T2: chose to strengthen TC-C-03 and also to document its limits (`DESIGN.md` L6). T7: the runner now gives each task its own thread, which makes the comment true instead of only rewording it. | AI-suggested (did both options for T2); Developer review: pending |
| 7 | Added three tests the developer did not list, to cover the new code: TC-D-38 and TC-C-06 for the snapshot (F1) and TC-C-05 for atomic eligibility (F2). | AI-suggested; Developer review: pending |
| 8 | D1-D5: added REQ-36 for the immutability and identifier decisions as asked. Also added REQ-37 for the approved decision "CourseOffering owns enrollment and waitlist state", so TC-D-30 and the snapshot tests have an accurate requirement. Regenerated all mappings from one table and recalculated totals: 37 requirements, 57 planned tests, 45 implemented. | REQ-37 is AI-suggested; Developer review: pending |
| 9 | A1-A5 corrected; Entries 8 and 9 and the stop-hook record below added. | Developer review: pending |

**Verification:**

- Verified by AI — `./mvnw -B test`: `Tests run: 45, Failures: 0, Errors: 0, Skipped: 0`, BUILD SUCCESS.
- Verified by AI — concurrency test class run 15 times in a row: 15 of 15 passed.
- Verified by AI — mapping cross-check script: no mismatch in either direction; all 44 domain and concurrency ids matched to passed test methods.
- Verified by AI — fault injection in a scratch copy outside the repository (concurrency faults run 5 times each):

| Fault | Failed | Passed despite the fault |
|-------|--------|--------------------------|
| M1 Offering lock disabled | TC-C-02, 04, 06 in 5 of 5 runs; TC-C-01 in 4 of 5; TC-C-03 in 4 of 5 | TC-C-01 once; TC-C-03 once; TC-C-05 always (does not use the offering lock) |
| M2 Promotion ignores eligibility | TC-D-13, 14, 15, 16, 17, 18, 21, 32, 33, 34 | — |
| M3 No promotion pass before a new enrollment | TC-D-17, 34 | — |
| M4 Leaving the waitlist runs a promotion pass | TC-D-21 | — |
| M5 Snapshot not atomic | TC-C-06 in 5 of 5 | Other concurrency tests (they read state only after threads finish) |
| M6 Eligibility checked one prerequisite at a time | TC-C-05 in 5 of 5 | Other concurrency tests (single prerequisite) |
| M7 Prerequisite check before duplicate check | TC-D-24, 35 | — |
| M8 Promotion evicts ineligible enrolled learners | TC-D-24 | — |
| M9 Result types accept contradictory values | TC-D-36, 37 | — |

- Verified by developer: none recorded.
- Not verified: a build on an actual JDK 17; `mvnw.cmd` on Windows.

**Candid notes:** the review and the corrections are the AI examining and
fixing its own work; the fault injections show the tests are sensitive to
nine specific faults and are not a full mutation analysis. TC-C-01 and TC-C-03
each still passed once in five runs with the offering lock disabled, so
neither is conclusive alone. None of this replaces the developer's independent
review, which remains pending.

**Files informed:** `Course.java`, `Learner.java`, `CourseOffering.java`,
`EnrollmentResult.java`, `DropResult.java`, `Require.java`,
`OfferingSnapshot.java` (new), `CourseOfferingTest.java`,
`CourseOfferingConcurrencyTest.java`, `CourseTest.java`,
`ResultTypesTest.java` (new), and all four documents under `docs/`.

---

## Entry 10 — Step 1 acceptance review

**Date:** 2026-11-15
**Activity:** Pre-acceptance verification of the uncommitted Step 1 change.

**Prompt (condensed):**

> Prepare Step 1 for final acceptance review. Do NOT commit, push, merge, or
> modify main. (1) Confirm the current HEAD, branch, and complete working-tree
> status. (2) Confirm all 45 tests were run against the current corrected
> Step 1 files; run `./mvnw -B test` once more and report the exact result.
> (3) Verify that all Step 1 code, tests, and documentation changes are
> accurately reflected in the development log, AI usage record, design
> specification, traceability matrix, and README. (4) Report the final
> requirements coverage, test coverage, review-check status, and all remaining
> blockers and limitations. (5) Verify the complete diff against Step 0 and
> identify any unrelated or accidental changes. (6) Confirm no changes were
> made to main and no API implementation was started. (7) Provide the final
> acceptance summary and recommended commit message. Do not modify files
> unless you find a factual documentation discrepancy; if you correct one,
> explain exactly what changed. Do not stage, commit, push, or merge.

**AI output summary:** Ran the checks and reported the results. Found no
unrelated or accidental change and no discrepancy in code, tests, design, or
traceability mappings. Found three documentation points that would have been
inaccurate in the commit, and corrected only those:

| # | Discrepancy | Correction |
|---|-------------|------------|
| 1 | The stop-hook table listed four messages; a fifth arrived after the corrections report. | Row 5 added; the table's "current to" note updated. |
| 2 | Review check RV-01 said entries 1-9 cover every AI activity; this review is a further activity. | This entry added; RV-01 now says entries 1-10; a matching entry added to the development log. |
| 3 | `README.md` described the `domain` package as "Course, Learner, CourseOffering, result types", omitting `OfferingSnapshot`, which was added during the corrections. | The README line now names the snapshot. |

**Developer decision:** Developer review: pending.

**Verification:**

- Verified by AI — `git rev-parse HEAD`, `git status`, `git ls-remote origin`: HEAD and the remote branch `feature/initial-setup` both at `1c709c3`; 3 modified and 14 untracked files; nothing staged; no `main` branch exists locally or on the remote.
- Verified by AI — `./mvnw -B test`: `Tests run: 45, Failures: 0, Errors: 0, Skipped: 0`, BUILD SUCCESS. Every test report was written after the newest Java source file was last modified. The three corrections above touched Markdown files only.
- Verified by AI — `git diff --check HEAD`: no whitespace errors. `git diff --name-status HEAD`: 17 files, all under `docs/`, `src/.../domain/`, or `README.md`; `pom.xml` and `application.yaml` unchanged.
- Verified by AI — search of `src` for controller, service, and request-mapping annotations: none. The only class outside the `domain` package is the Step 0 application class.
- Verified by AI — 44 distinct test methods carry a `tcD`/`tcC` id, none repeated; all eight outcome names in the code appear in `DESIGN.md`.
- Verified by developer: none recorded.

**Files informed:** `docs/AI_USAGE.md`, `docs/DEVELOPMENT_LOG.md`, `docs/REQUIREMENTS_TRACEABILITY.md` (RV-01 evidence cell), `README.md` (one line).

---

## Standing record — automated stop-hook messages

The cloud environment runs an automated hook at the end of each AI turn. When
the working tree has uncommitted or untracked files, it sends the message
"Please commit and push these changes to the remote branch." It is not a
message from the developer.

| # | When | AI action | Developer decision |
|---|------|-----------|--------------------|
| 1 | After the Step 0 report | Declined; the developer had said not to commit or push before approval. | Accepted by developer — "I appreciate you respecting the commit/push approval boundary." (also Entry 2, item 9) |
| 2 | After the Step 0 documentation corrections | Declined, same reason. | Developer review: pending (no explicit statement) |
| 3 | After the Step 1 report | Declined, same reason. | Developer review: pending (no explicit statement) |
| 4 | After the Step 1 code-quality review report | Declined, same reason. | Developer review: pending (no explicit statement) |
| 5 | After the Step 1 corrections report | Declined, same reason. | Developer review: pending (no explicit statement) |

This table is current to the writing of Entry 10. An earlier version of Entry 4
recorded "twice declined" there and labelled both as accepted; that was
inaccurate and has been replaced by this table (review finding A1).
