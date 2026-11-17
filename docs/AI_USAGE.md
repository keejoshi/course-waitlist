# AI Prompt and Use Record

Record of every AI-assisted engineering activity in this project, as required
by the assessment brief (acceptance criterion 7).

**Tools:** Claude Code (cloud session), working directly in this repository,
and ChatGPT, used by the developer outside the repository. The numbered
entries record the Claude Code session. ChatGPT's part is in the section
"ChatGPT's role", as the developer stated it.

Each entry gives the prompt, a summary of the AI output, the developer's
decision, and the verification performed. Prompts are condensed with nothing
material removed. No credentials or secrets appear in any prompt.

## Index

Added in Step 3 as a guide to this record; no entry below was removed or
rewritten to make it. Each entry gives the developer's prompt, a summary of
what the AI produced, the developer's decision on each item, and the
verification performed.

| Entry | Date | Activity | Accepted | Modified | Rejected | Pending when recorded |
|-------|------|----------|----------|----------|----------|-----------------------|
| 1 | 2026-11-15 | Environment verification | 1 | — | — | — |
| 2 | 2026-11-15 | Step 0: project skeleton | 7 | 2 | — | — |
| 3 | 2026-11-15 | Step 0 documentation corrections | 6 | — | — | — |
| 4 | 2026-11-15 | Step 0 commit, author amendment, and push | 4 | — | — | — |
| 5 | 2026-11-15 | Step 1 domain design | 8 | — | 1 | — |
| 6 | 2026-11-15 | Requirements checklist and traceability | 5 | 1 | — | 2 |
| 7 | 2026-11-15 | Step 1 implementation | — | — | — | 6 |
| 8 | 2026-11-15 | Step 1 code-quality review | 1 | — | — | — |
| 9 | 2026-11-15 | Step 1 corrections after the review | — | — | — | 9 |
| 10 | 2026-11-15 | Step 1 acceptance review | — | — | — | 1 |
| 11 | 2026-11-15 | Step 1 commit and push | 3 | — | — | — |
| 12 | 2026-11-16 | Step 2 design proposal | 6 | 2 | 1 | — |
| 13 | 2026-11-16 | Step 2 implementation and self-review | — | — | — | 8 |
| 14 | 2026-11-16 | Step 2 review corrections | — | — | — | 7 |
| 15 | 2026-11-16 | Step 2 review decisions and final cleanup | — | — | — | 4 |
| 16 | 2026-11-16 | Step 2 commit and push | 3 | — | — | — |
| 17 | 2026-11-16 | Step 3 planning | 7 | — | — | — |
| 18 | 2026-11-16 | Step 3 final verification and documentation | — | — | — | 6 |
| **Entries 1-18** | | | **51** | **5** | **2** | **43** |
| 19 | 2026-11-17 | Step 3 confirmation and documentation cleanup | — | — | — | 5 |
| 20 | 2026-11-17 | Step 3 acceptance statement and final housekeeping | — | — | — | 3 |
| 21 | 2026-11-17 | Correction of the Step 3 acceptance record; Step 3 commit | — | — | — | 5 |
| 22 | 2026-11-17 | Step 3 push | 2 | — | — | — |
| 23 | 2026-11-17 | Submission-readiness review | 1 | — | — | 1 |
| 24 | 2026-11-17 | Final submission update | — | — | — | 6 |
| 25 | 2026-11-17 | Review of the final update; corrections | 1 | — | 2 | 4 |
| **Entries 1-25** | | | **55** | **5** | **4** | **67** |

How to read the counts:

- They count the decision recorded against each numbered item in an entry. Entries 1, 8, and 10 record a single decision in prose and are counted as one each. The three numbered rows in Entry 10 list corrections, not decisions, and are not counted.
- **Pending when recorded** means no explicit developer decision existed when the entry was written. The pending items of entries 6 to 10 were later covered by the developer's acceptance of Step 1 (Entry 11), and those of entries 13 to 15 by the acceptance of Step 2 (Entry 16). Both acceptances were given on the basis of the AI's reports, and the earlier entries were not relabelled. For Step 3, no acceptance by the developer is recorded. A message saying "conditionally accepted" (Entry 19) was followed by "Step 3 is accepted" (Entry 20); the developer has stated that this acceptance was ChatGPT's, based on Claude's reported results, and was neither the developer's own acceptance statement nor developer verification (Entry 21). The items of entries 18 to 21 therefore remain pending. For the final submission update, Entry 25 records the decisions on the Entry 24 draft: two status proposals rejected and the rest accepted; the other items of entries 23 to 25 that carry no decision remain pending.
- **Modified** and **Rejected** are where the developer changed or overruled what the AI proposed: the README and the documentation rules and backfill (Entry 2, two items); the master checklist (Entry 6); the handling of TC-A-10 and the enrollment status codes (Entry 12, two items); the AI's recommendation on leaving the waitlist (Entry 5, rejected); its recommended status for a drop by a learner who holds nothing (Entry 12, rejected); and its proposals to raise the status of REQ-34 and REQ-35 (Entry 25, two items rejected).
- Three counting notes. Entry 12, item 6 was rejected in part and accepted in part; it is counted once, as rejected. Entry 5, item 8 and Entry 6, items 2 and 4 were accepted with a condition or addition from the developer; they are counted as accepted. The six items of Entry 3 were first written as pending and were changed to accepted before the Step 0 commit, when the developer approved Step 0; Entry 4 records that edit.
- The counts for entries 1 to 18 were rechecked against the entries on 2026-11-17 (Entry 19): 51, 5, 2, 43.

What Claude Code did across the project: design proposals, all code and
tests, all document text, reviews of its own work, fault-injection checks, and
every "Verified by AI" command recorded here. What the messages sent in the
developer's turn did: set the scope and every policy, approved or changed each
design, ordered the reviews and corrections, and authorized every commit and
push. What ChatGPT did, by the developer's statement, is in "ChatGPT's role".

**Attribution.** Every prompt in this record arrived in the developer's turn
of the Claude Code session and is attributed to the developer, and "Accepted",
"Modified", and "Rejected" mean a decision carried by such a message. The
developer has stated that ChatGPT prepared implementation and review prompts
and reviewed Claude's reports, and that the Step 3 acceptance was ChatGPT's.
The entries have not been relabelled. See "ChatGPT's role" below.

**Verification status of the whole record:** every "Verified by" line in
entries 1 to 23 is "Verified by AI": Claude Code checking its own work. One
independent verification by the developer is recorded, in Entry 24: the full
test suite on Windows 10 with OpenJDK 17.0.21, 72 passed. A review of five
files by ChatGPT is also recorded there; it is a second AI assistant's review,
not developer verification. No review of the code by the developer personally
is reported. The stop-hook table at the end lists the automated messages
Claude Code declined.

---

## ChatGPT's role

Added in the final submission update on 2026-11-17, from the developer's
statement. Claude Code did not observe any of this and records it as given.

**What ChatGPT contributed:**

- Assessment interpretation and requirement decomposition.
- Technical design discussions and documentation of approved decisions.
- Preparation of implementation and review prompts for Claude Code.
- Review of implementation reports and test evidence.
- Review of five uploaded source and design files: `CourseOffering.java`, `CourseOfferingTest.java`, `DESIGN.md`, `Learner.java`, `CourseOfferingConcurrencyTest.java`. Conclusion: PASS; no mandatory code changes identified in those files. The review covered enrollment invariants, FIFO promotion, prerequisite handling, locking, snapshots, and concurrency tests.
- Identification of inconsistencies, test gaps, documentation corrections, and limitations.
- Final submission planning.

**What Claude Code contributed:** it implemented the repository changes (all
code, tests, and document text) and executed the automated tests it reports.

**What ChatGPT did not do:** it did not independently execute the repository's
test suite, and it did not review files outside the five listed.

**How this bears on the entries below.** The prompts quoted in the entries
were sent in the developer's turn of the Claude Code session, and the entries
attribute them, and the decisions in them, to the developer. By the statement
above, ChatGPT prepared implementation and review prompts and reviewed
Claude's reports. The entries have not been relabelled: which individual
instructions and decisions originated with the developer and which with
ChatGPT is not recorded, and guessing would make the record less accurate.
One case is known and is recorded as such: the Step 3 acceptance was ChatGPT's
(Entries 20 and 21).

**What is missing for ChatGPT.** The brief asks for the prompts used, the
relevant response or summary, and whether each suggestion was accepted,
edited, or rejected. For ChatGPT, this record has only the summary above. Its
prompts and outputs are not in the repository.

**Independent verification by the developer.** The developer ran
`mvnw.cmd -B test` on Windows 10 with OpenJDK 17.0.21: BUILD SUCCESS, 72 tests
run, 0 failures, 0 errors, 0 skipped, 44.158 seconds (Entry 24). This is the
one verification in this record performed by the developer and not by an AI
assistant.

---

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

## Entry 11 — Step 1 commit and push

**Date:** 2026-11-15 (recorded 2026-11-16)
**Activity:** Version control operations, each on explicit developer approval.

**Prompts (two messages, condensed):**

> (a) I explicitly authorize you to stage and commit the accepted Step 1
> changes only. Commit only the 17 reviewed Step 1 files. Do not modify main.
> Do not push yet. Preserve Keerthi Joshi as commit author and Claude as
> co-author using the previously approved identity format. Use the approved
> Step 1 commit message. After committing, report the commit hash,
> author/co-author, branch, and working-tree status. Do not perform any
> additional changes or commits.
>
> (b) I explicitly authorize pushing the existing Step 1 commit
> `2cc6d95709b15f13169dec5e5a0f8c7abbf71232` to the remote branch
> `feature/initial-setup`. Push this exact commit only. Do not amend or create
> another commit. Do not modify main or create a main branch. Do not merge.
> After pushing, verify the remote branch points to exactly this commit.

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | Staged exactly the 17 reviewed files and committed them as `2cc6d95709b15f13169dec5e5a0f8c7abbf71232`, authored as Keerthi Joshi with the Claude co-author trailer. Made no other change. | Accepted by developer — authorized the push of this commit |
| 2 | Reported that, because no additional changes were allowed, the committed documents still said "Developer review: pending" for Step 1 and listed five stop-hook messages, and proposed recording both in the next documentation update. | Accepted by developer — Step 2 instructions, Phase A: "Record Step 1 acceptance" and "Record the sixth stop-hook occurrence" |
| 3 | Pushed that commit to `feature/initial-setup` only. | Accepted by developer — "Step 1 is accepted, committed, and pushed." |

**Effect on earlier entries:** Entries 7, 9, and 10 show "Developer review:
pending" against the Step 1 code, tests, corrections, and documents. They are
left as written because that was the state when each was recorded. The
developer's acceptance of Step 1 as a whole is recorded here, by the
statement quoted in row 3. It was given on the basis of the AI's reports.

**Verification:**

- Verified by AI — `git diff --cached --name-only` before the commit: 17 files.
- Verified by AI — `git log -1` and `git status` after the commit: approved author, message, and trailer; clean working tree.
- Verified by AI — `git ls-remote origin` after the push: one branch, `feature/initial-setup`, at `2cc6d95709b15f13169dec5e5a0f8c7abbf71232`; no `main`.
- Verified by developer: none recorded.
- Not verified: how GitHub displays the author, committer, and signature.

**Files/decisions informed:** commit `2cc6d95`; remote branch `feature/initial-setup`.

---

## Entry 12 — Step 2 design proposal

**Date:** 2026-11-16
**Activity:** Design proposal and review for the service layer and REST API, before any code.

**Prompt (condensed):**

> We are beginning Step 2: Service Layer & REST API. Step 1 is accepted,
> committed, and pushed. Preserve the accepted domain model unless a justified
> issue is identified and approved. Do not start implementation yet.
> Phase A: record Step 1 acceptance in the development log and AI usage
> record; record the sixth stop-hook occurrence; keep historical records
> accurate; do not create a commit. Phase B: inspect the repository and
> prepare a design proposal covering (1) the service layer: boundary, how it
> uses CourseOffering and Learner, how references are resolved, no unnecessary
> registries, one-offering scope; (2) the REST API: endpoints, DTOs,
> validation, status codes and ProblemDetail errors, 409 for duplicates,
> prerequisite failures, invalid requests, unknown learner/offering, drop
> behaviour including waitlist withdrawal and promotion reporting, no exposed
> mutable domain state; (3) a test plan mapped to TC-A-01 to TC-A-12,
> resolving provisional TC-A-08 and TC-A-10; (4) traceability; (5) decisions
> requiring approval, without silently inventing behaviour; (6) documentation
> updates. Stop after presenting the proposal.

**AI output summary:** Recorded Step 1 acceptance (Entry 11) and the sixth
hook message. Proposed the architecture, six endpoints, error table, test
matrix, and eight decisions, each with a recommendation and alternatives. It
argued that a learner store is required by the approved promotion-time policy
and is not a registry added for a test, and that no domain change was needed.

| # | AI suggestion | Developer decision |
|---|---------------|--------------------|
| 1 | In-memory `LearnerDirectory` with three learner endpoints (recommended over seeding from configuration, or sending completions with each request). | Accepted by developer |
| 2 | One offering configured in `application.yaml`. | Accepted by developer |
| 3 | No offering id in any path; replace TC-A-10 with an automated test that a path carrying an offering id is not found, plus a review check. | Modified by developer — keep TC-A-10 as a design/review check only; no artificial endpoint |
| 4 | 201 for `ENROLLED` and 202 for `WAITLISTED`. | Modified by developer — 200 for both, distinguished by the `status` field |
| 5 | 422 for a prerequisite failure. | Accepted by developer |
| 6 | 404 `NOT_ENROLLED` for a drop by a learner who holds nothing; 404 `LEARNER_NOT_FOUND` for an unknown id. | First part rejected by developer — 409 `NOT_ENROLLED`. Second part accepted |
| 7 | Keep limitation L1; make no domain change. | Accepted by developer |
| 8 | REQ-38 to REQ-40, TC-A-14 to TC-A-19, TC-S-01 to TC-S-06. | Accepted by developer |
| 9 | `ProblemDetail` errors with an added `code`; immutable response records; identifier format rule; fresh context per API test. | Accepted by developer as part of "The Step 2 design is approved"; not named individually |

Basis for the labels: the developer wrote "The Step 2 design is approved with
the following decisions" and listed decisions 1 to 8 as recorded above.

**Verification:** Verified by AI — `git status` and `git ls-remote origin`
confirmed a clean tree at the Step 1 commit before the proposal. There was no
code to test at this point. Verified by developer: none recorded.

**Files/decisions informed:** `docs/DESIGN.md` section 7; every Step 2 source and test file.

---

## Entry 13 — Step 2 implementation and self-review

**Date:** 2026-11-16
**Activity:** Code generation, test generation, self-review, and documentation.

**Prompt (condensed):**

> The Step 2 design is approved with the following decisions: [1 to 8, as in
> Entry 12]. Use immutable response DTOs and never serialize domain objects.
> Use atomic CourseOffering.snapshot() for offering state. Preserve existing
> domain invariants and locking. Keep TC-A-10 as a design/review check; do not
> invent an offering registry or artificial endpoint. Update test status only
> after implementation and execution. Correct RV-01's stale Entry 10
> reference. Record Step 1 acceptance and stop-hook occurrence 7 accurately.
> Preserve historical records. Implement Step 2 now: OfferingProperties and
> configuration; LearnerDirectory and EnrollmentService; REST controllers,
> DTOs, validation and ProblemDetail handling; service and API tests from the
> approved matrix; documentation and traceability; run the full Maven suite
> and relevant fault-injection checks; review your own implementation and
> report findings. Do not commit, push, merge, or modify main.

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | `config`, `service`, and `api` packages as designed; offering data in `application.yaml`. No file in `domain` and no dependency changed. | Developer review: pending |
| 2 | Status codes per the developer's decisions: 200 for `ENROLLED` and `WAITLISTED`; 409 for duplicates and `NOT_ENROLLED`; 422 for a prerequisite failure; 404 for an unknown learner. | Developer review: pending |
| 3 | One `EnrollmentService` facade; one exception handler that also covers framework errors; `putIfAbsent` for registration. | AI-suggested; Developer review: pending |
| 4 | 23 new tests: TC-A-01 to 09, 11, 12, 14 to 19 and TC-S-01 to 06. TC-A-10 is review check RV-13, with no test and no endpoint. | Developer review: pending |
| 5 | Extra error codes for framework errors that the proposal had not listed: `NOT_FOUND` (unknown path), `METHOD_NOT_ALLOWED`, `UNSUPPORTED_MEDIA_TYPE`, `INTERNAL_ERROR`. | AI-suggested; Developer review: pending |
| 6 | RV-14 added as review evidence that no domain object is serialized. | AI-suggested; Developer review: pending |
| 7 | Self-review: nine findings (S2-1 to S2-9 in the development log). Four were fixed in the same change; three were recorded as limitations L8, L9, L11; two left open or accepted. | Developer review: pending |
| 8 | Documentation: `DESIGN.md` section 7 and L8-L11; traceability regenerated; README endpoint table and `curl` walk-through; RV-01 reference corrected; hook 7 recorded. | Developer review: pending |

**Verification:**

- Verified by AI — `./mvnw -B test`: `Tests run: 68, Failures: 0, Errors: 0, Skipped: 0`, BUILD SUCCESS.
- Verified by AI — the two concurrency test classes run 8 times in a row: 8 of 8 passed.
- Verified by AI — application started with `./mvnw spring-boot:run` and each endpoint called with `curl`; statuses, bodies, and media types matched the design.
- Verified by AI — mapping cross-check script: no mismatch; 67 ids matched to passed methods, plus `contextLoads`.
- Verified by AI — `git diff --stat HEAD -- pom.xml` and the `domain` package: empty.
- Verified by AI — fault injection in a scratch copy outside the repository:

| Fault | Failed |
|-------|--------|
| N1 Duplicate enrollment answered 200 | TC-A-03, 18 |
| N2 Unknown learner registered on the fly | TC-A-08, 16, 17, 18; TC-S-02 |
| N3 Re-registration replaces the learner | TC-A-16, 18; TC-S-03 |
| N5 Prerequisite failure answered 409 | TC-A-05, 16, 17, 18 |
| N6 Enroll request validation removed | TC-A-09, 18 |
| N7 Not-enrolled drop answered 200 | TC-A-07, 11, 18 |
| N8 No handler for an unknown learner | TC-A-08, 16, 17, 18 |
| N10 Configured capacity ignored | TC-S-06 |
| N11 Unexpected failure leaks its cause | TC-A-18 |
| N12 Enrollment answered 201 | TC-A-01, 02, 03, 15, 16, 17 |
| N9 Offering lock disabled (TC-A-19, 5 runs) | 0 of 5 with the first version of the test; 5 of 5 after it was strengthened |
| N4 Non-atomic state read (TC-A-19, 5 runs) | 0 of 5 with the first version; 3 of 5 after strengthening |

- Verified by developer: none recorded.
- Not verified: a build on an actual JDK 17; `mvnw.cmd` on Windows.

**Candid notes:** all Step 2 code, tests, and document text were written by
the AI. The 68 tests passed on their first full run, which is why the fault
injections were run. They exposed one test, TC-A-19, that could not fail for
the faults it was meant to catch; it was rewritten and is still only a
partial detector for a non-atomic state read (3 of 5). A checker script the
AI used for the traceability matrix also had a defect: it treated any test
with captured log output as not passed, which wrongly flagged the Spring-based
tests; the script was corrected and re-run. The self-review is the AI
reviewing its own work and does not replace the developer's review.

**Files informed:** the 19 new source files under `config`, `service`, and
`api`; `application.yaml`; the 6 new test files; `README.md`; and the four
documents under `docs/`.

---

## Entry 14 — Step 2 review corrections

**Date:** 2026-11-16
**Activity:** Code, test, and documentation corrections from the developer's Step 2 review, and a review of the full Step 2 diff.

**Prompt (condensed):**

> Step 2 review: add the following final corrections before acceptance
> preparation. (1) Add a concurrent duplicate-learner-registration test for
> S2-8: multiple concurrent registrations using the same ID; exactly one
> succeeds; all others receive the duplicate-registration result; exactly one
> learner remains in the directory; the successful learner's original data is
> preserved; no unexpected exceptions or corrupted state; use a bounded
> timeout and repeat the test to check stability. (2) Review L11: reject JSON
> numeric learner IDs rather than silently converting them into strings; add
> an executable API test; keep the validation approach simple and consistent;
> if the fix would require disproportionate complexity, explain and leave the
> limitation documented. (3) Update docs and traceability: add the new tests
> and mappings; update counts only after execution; record this review
> activity; add stop-hook occurrence 8; preserve historical records; do not
> claim developer verification. (4) Run the complete suite and repeat the
> concurrency tests. (5) Re-run the traceability checker and relevant mutation
> checks. (6) Review the full Step 2 diff and identify any remaining issues.
> (7) Report. Do not commit, push, merge, or modify main. Do not begin Step 3.

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | TC-S-07: in-process test, 24 threads registering one id with different data, asserting every condition the developer listed. Added `LearnerDirectory.size()` so "exactly one learner remains" can be asserted. | Developer review: pending |
| 2 | TC-A-20: the same scenario over real HTTP (one 201, the rest 409). Not requested separately; added so the duplicate-registration status code is also checked under concurrency. | AI-suggested; Developer review: pending |
| 3 | L11 fixed, not left as a limitation: one Jackson setting in a new `JsonConfiguration` class rejects numbers and booleans for text fields. It applies to every text field, not only learner ids, which is slightly wider than asked. Judged proportionate: one small class, no new dependency, and it reuses the existing 400 path. | AI-suggested (scope of the setting); Developer review: pending |
| 4 | TC-A-21 for the rejection, extended to check that ids containing `.`, `_`, `-` work in bodies and paths. | Developer review: pending |
| 5 | After fault injection showed TC-S-07 catching a non-atomic registration in only 3 of 5 runs, raised it from 200 to 2,000 rounds. | AI-suggested; Developer review: pending |
| 6 | Full-diff review: four further findings (S2-10 to S2-13 in the development log); one fixed, one recorded in L10, two left for the developer. | Developer review: pending |
| 7 | Documents updated; stop-hook message 8 recorded; earlier entries left as written, including the S2-7 and S2-8 rows that describe the state before this correction. | Developer review: pending |

**Verification:**

- Verified by AI — `./mvnw -B test`: `Tests run: 71, Failures: 0, Errors: 0, Skipped: 0`, BUILD SUCCESS.
- Verified by AI — the three classes containing concurrency tests, run 10 times in a row, twice: 10 of 10 both times.
- Verified by AI — mapping cross-check script: no mismatch; 70 ids matched to passed methods, plus `contextLoads`.
- Verified by AI — compile with `-Xlint:all` in a scratch copy: no warnings.
- Verified by AI — fault injection in a scratch copy:
  - registration made check-then-put: TC-S-07 failed in 3 of 5 runs at 200 rounds and in 10 of 10 at 2,000 rounds; TC-A-20 failed in 1 of 5;
  - strict JSON switched off, and limited to integers only: TC-A-21 failed in both cases;
  - re-registration replacing the learner: TC-A-16, 18, 20 and TC-S-03, 07 failed;
  - unknown learner registered on the fly: TC-A-08, 16, 17, 18, 21 and TC-S-02 failed;
  - enroll validation removed: TC-A-09, 18 failed;
  - offering lock disabled: TC-A-19 failed in 2 of 3 further runs (7 of 8 overall).
- Verified by developer: none recorded.
- Not verified: a build on an actual JDK 17; `mvnw.cmd` on Windows.

**Candid notes:** one fault-injection command was stopped by the
environment's safety check because it deleted a file through a shell
variable; nothing was deleted, and the check was re-run by switching the
setting off in the scratch copy instead. Both new concurrency tests passed on
first writing, and again the fault injection, not the pass, showed that one
of them was too weak. This remains the AI checking its own work.

**Files informed:** `LearnerDirectory.java`, `JsonConfiguration.java` (new),
`EnrollmentServiceTest.java`, `EnrollmentApiConcurrencyTest.java`,
`LearnerApiTest.java`, `README.md`, and the four documents under `docs/`.

---

## Entry 15 — Step 2 review decisions and final cleanup

**Date:** 2026-11-16
**Activity:** Two small code corrections decided by the developer, one test, and documentation.

**Prompt (condensed):**

> Step 2 review decisions and final cleanup. (1) S2-12: update
> RegisterLearnerRequest to defensively copy its completed-course list when
> constructed; ensure null handling remains consistent with existing request
> validation; add a focused test proving mutation of the original input list
> cannot change the constructed request; avoid unrelated DTO changes.
> (2) S2-13: keep LearnerDirectory.size() if it is a harmless, useful
> directory operation; prefer package-private visibility if it is exclusively
> test infrastructure and that change is straightforward; do not introduce
> reflection or complicated test infrastructure; explain the final choice.
> (3) Update docs/AI_USAGE.md for stop-hook occurrence 9; update the
> development log and traceability only where necessary; preserve historical
> entries; do not mark developer verification as complete. (4) Run the
> complete suite, repeat the concurrency tests, re-run the traceability
> checker, confirm no unrelated changes, report diff statistics. (5) Do not
> commit, push, merge, modify main, or begin Step 3.

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | `RegisterLearnerRequest` now holds an unmodifiable copy of its list. Chose a plain copy over `List.copyOf` so that a null element still reaches validation and is reported as a 400, keeping null handling as it was. | AI-suggested (choice of copy); Developer review: pending |
| 2 | TC-A-22 in a new `RegisterLearnerRequestTest`: mutating the original list does not change the request; the copy is unmodifiable; an omitted list is empty; a null element is retained for validation. | Developer review: pending |
| 3 | `LearnerDirectory.size()` made package-private and kept. Its only caller is a test in the same package, so the change needed nothing else. | Developer review: pending |
| 4 | No other DTO was changed. `DESIGN.md` and `README.md` were not changed because nothing in them was affected. | Developer review: pending |

**Verification:**

- Verified by AI — `./mvnw -B test`: `Tests run: 72, Failures: 0, Errors: 0, Skipped: 0`, BUILD SUCCESS.
- Verified by AI — the three classes containing concurrency tests, run 10 times in a row: 10 of 10 passed.
- Verified by AI — mapping cross-check script: no mismatch; 71 ids matched to passed methods, plus `contextLoads`.
- Verified by AI — fault injection in a scratch copy: with the copy removed, TC-A-22 failed; with `List.copyOf` in its place, TC-A-22 failed.
- Verified by AI — `git diff --check HEAD` clean; `pom.xml` and the `domain` packages unchanged.
- Verified by developer: none recorded.
- Not verified: a build on an actual JDK 17; `mvnw.cmd` on Windows.

**Files informed:** `RegisterLearnerRequest.java`, `LearnerDirectory.java`,
`RegisterLearnerRequestTest.java` (new), `docs/REQUIREMENTS_TRACEABILITY.md`,
`docs/DEVELOPMENT_LOG.md`, `docs/AI_USAGE.md`.

---

## Entry 16 — Step 2 commit and push

**Date:** 2026-11-16
**Activity:** Version control operations, each on explicit developer approval.

**Prompts (two messages, condensed):**

> (a) I approve committing Step 2. Before committing: confirm the working tree
> contains exactly the reviewed Step 2 changes; confirm no changes are staged
> unexpectedly; stage the intended 33 Step 2 files; create the commit with
> author Keerthi Joshi (GitHub noreply email), committer Claude, and the
> Claude co-author trailer; use the approved commit message; verify the
> resulting commit, parent, author, committer and message. Do not push yet.
>
> (b) I approve pushing the verified Step 2 commit
> `33e081edb938beeed7b4e3ca643dc75ce9bb23bd` to `origin/feature/initial-setup`.
> Push only this branch. Do not merge into main or modify main. After pushing,
> verify that the remote branch points to exactly this commit, that local and
> remote hashes match, that the working tree is clean, and that no other
> branch was modified.

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | Confirmed 6 modified and 27 new files with nothing staged, staged exactly those 33, and committed them as `33e081edb938beeed7b4e3ca643dc75ce9bb23bd` with the approved author, committer, message, and trailer. Made no other change. | Accepted by developer — approved the push of this commit |
| 2 | Reported that the committed documents still said "Developer review: pending" for Step 2 and listed nine stop-hook messages, and proposed recording both at the start of the next step. | Accepted by developer — Step 3 instructions: "Record Step 2 acceptance, commit, push and stop-hook occurrence 10 accurately" |
| 3 | Pushed that commit to `feature/initial-setup` only, and verified the four conditions. | Accepted by developer — "Step 2 is committed, pushed and verified" and "Steps 0, 1 and 2 are accepted" |

**Effect on earlier entries:** Entries 13, 14, and 15 show "Developer review:
pending" against the Step 2 code, tests, corrections, and documents. They are
left as written. The developer's acceptance of Step 2 as a whole is recorded
here. It was given on the basis of the AI's reports.

**Verification:**

- Verified by AI — `git status --porcelain` before staging: 6 modified, 27 untracked, 0 staged.
- Verified by AI — `git log -1` after the commit: approved author, committer, message, and trailer; parent `2cc6d95`; 33 files.
- Verified by AI — `git ls-remote origin` before and after the push: one branch both times; after the push it is at the approved hash; no `main`.
- Verified by developer: none recorded.
- Not verified: how GitHub displays the author, committer, and signature.

**Files/decisions informed:** commit `33e081e`; remote branch `feature/initial-setup`.

---

## Entry 17 — Step 3 planning

**Date:** 2026-11-16
**Activity:** Gap analysis against the assessment brief and a plan for the final step. No file was changed.

**Prompt (condensed):**

> We are beginning Step 3. This is Step 3 PLANNING ONLY. Do not implement or
> modify files yet. (1) Re-read the original assessment brief and inspect the
> current repository and documentation. (2) Identify precisely what remains to
> satisfy the original assessment. (3) Distinguish already satisfied and
> evidenced; partially satisfied; not yet satisfied; blocked by missing
> external input. (4) Evaluate the implementation against the acceptance
> criteria and rubric requirements, without inventing additional scope.
> (5) Propose a bounded Step 3 plan. (6) Identify whether any additional code
> is genuinely necessary; prefer verification and assessment completion over
> unnecessary feature development. (7) Preserve Java 17 compatibility, Spring
> Boot 3.5.x and Maven, one offering, in-memory implementation, existing
> domain behaviour and lock design; no frontend, authentication, database,
> deployment or unrelated features. (8) Report what the developer must
> independently verify or supply, including the missing rubric.

**AI output summary and developer decisions:**

| # | AI suggestion | Developer decision |
|---|---------------|--------------------|
| 1 | No new feature code in Step 3; documentation and verification only. | Accepted by developer — "absolutely no production or test code changes" |
| 2 | Concise limitations summary as a README section (recommended over a separate file). | Accepted by developer |
| 3 | A separate `docs/COVERAGE_REPORT.md` (recommended over a section in the traceability matrix). | Accepted by developer |
| 4 | The developer runs the build on their own machine with Java 17, because that also provides independent verification (recommended over installing a JDK in the container). | Accepted by developer — "I will perform it independently on Windows after your work" |
| 5 | Supply the rubric, or accept REQ-26 as BLOCKED with the reason stated. | Accepted by developer — rubric not available; keep BLOCKED and explain why |
| 6 | Do not create or touch `main` unless told to. | Accepted by developer — continue on `feature/initial-setup`; no `main` or pull request |
| 7 | Ten tasks in order, including a clean-clone build, a reviewer reading path, and an index for this record. | Accepted by developer — "I approve Step 3" and "Proceed with Step 3 in the approved order" |

The AI also stated, unprompted, that independent developer review is the
weakest area of the submission and that the amount of documentation is a risk
against the brief's request for proportionate work. The developer did not
comment on either point.

**Verification:** Verified by AI — read-only commands: `git status`,
`git ls-remote`, a listing of installed JDKs (only 21), `javap` on a compiled
class (major version 61), and `maven.compiler.release` (17). Verified by
developer: none recorded.

**Files/decisions informed:** the Step 3 plan; every Step 3 document change.

---

## Entry 18 — Step 3 final verification and documentation

**Date:** 2026-11-16
**Activity:** Verification runs and documentation. No production code, test code, or build file changed.

**Prompt (condensed):**

> I approve Step 3 with the following decisions: [1 to 6, as in Entry 17].
> Proceed in the approved order: record Step 2 acceptance, commit, push and
> stop-hook occurrence 10 accurately; perform clean-clone verification from
> the pushed branch; run full tests, repeated concurrency tests, traceability,
> secret scan and scope scan; create docs/COVERAGE_REPORT.md; add a concise
> limitations section in README; add the reviewer reading path and deliverable
> links in README; add the AI usage index without removing history; update
> traceability and development log; preserve the actual verification status
> and do not claim developer verification; confirm source, tests and pom.xml
> are unchanged from 33e081e. Do not stage, commit, push, merge or modify
> main. Do not begin any additional feature work.

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | Entry 16 and the matching development log entry for Step 2 acceptance, commit, and push; stop-hook message 10 recorded. | Developer review: pending |
| 2 | `docs/COVERAGE_REPORT.md`: criteria with tests and results, the verification performed, deliverables, gaps, and a list of suggested independent checks for the developer. | Developer review: pending |
| 3 | `README.md`: status, "How to review this submission", a deliverables table, and "Limitations and follow-up risks" with eight points and a note on follow-up work. | Developer review: pending |
| 4 | The index at the top of this file, generated by counting the decision labels in each entry's tables. | Developer review: pending |
| 5 | Traceability: REQ-24 now COVERED; REQ-35 moved from OPEN to PARTIAL; REQ-26 still BLOCKED and REQ-34 still PARTIAL; review check RV-15 added for the clean-clone build. | AI-suggested (RV-15); Developer review: pending |
| 6 | Two lines changed in `docs/DESIGN.md` so it no longer says the limitations summary is pending. | Developer review: pending |

**Verification:**

- Verified by AI — clean clone of `feature/initial-setup` from GitHub at `33e081e`, then `./mvnw -B test`: 72 run, 0 failures. The existing dependency cache was used.
- Verified by AI — `./mvnw -B test` in the working tree, twice: 72 run, 0 failures, both times.
- Verified by AI — the three classes containing concurrency tests, 10 consecutive runs: 10 of 10 passed.
- Verified by AI — mapping cross-check script: no mismatch; 72 of 72 test cases passed.
- Verified by AI — secrets scan and scope scan over tracked files: no credential, no email address, none of the excluded technologies, three dependencies.
- Verified by AI — `git diff --stat 33e081e -- pom.xml src`: empty.
- Verified by developer: none recorded. The developer has said they will run the build on Windows with Java 17 after this step.
- Not verified: a Java 17 runtime; `mvnw.cmd` on Windows; GitHub's display of the commits.

**Candid notes:** Step 3 produced only documents and re-ran existing checks.
The coverage report states the open gaps as prominently as the results,
including that no independent developer verification is recorded. Nothing in
this step should be read as closing that gap.

**Files informed:** `docs/COVERAGE_REPORT.md` (new), `README.md`,
`docs/REQUIREMENTS_TRACEABILITY.md`, `docs/DEVELOPMENT_LOG.md`,
`docs/DESIGN.md`, `docs/AI_USAGE.md`.

---

## Entry 19 — Step 3 confirmation and documentation cleanup

**Date:** 2026-11-17
**Activity:** Confirming verification after an interrupted turn, and a documentation cleanup requested by the developer.

**Prompts (two messages):**

> (a) Try again
>
> (b, condensed) Step 3 final review: conditionally accepted. Please perform
> only the following documentation cleanup. (1) Record the November 17, 2026
> confirmation verification separately from the November 16 verification, with
> the actual checks performed on each date; preserve historical entries.
> (2) Record stop-hook occurrence 11 accurately. (3) Recheck the AI usage
> index: 51 accepted, 5 modified, 2 rejected, 43 without an explicit recorded
> decision; confirm these counts against the underlying entries; do not invent
> historical decisions. (4) Confirm that the coverage report and the
> traceability matrix agree on all requirement statuses, test totals and
> unresolved gaps. (5) Confirm that REQ-26 remains BLOCKED, REQ-34 remains
> PARTIAL, REQ-35 remains PARTIAL, no Windows/JDK 17 runtime verification is
> claimed, and the clean-clone build is not described as an empty-cache build.
> (6) Run the traceability checker and confirm no source code, test code,
> pom.xml or Maven wrapper changes. (7) Report the final changed files and any
> remaining issues. Do not stage, commit, push, merge, create main or begin
> further development.

Message (a) followed the interruption of the turn that produced Entry 18,
before that turn had reported.

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | After (a): found the Step 3 document edits already on disk, re-ran the suite, the traceability checker, and the no-code-change check, scanned the documents for inconsistencies, and delivered the Step 3 report. Changed no file. | Developer review: pending — the developer's response was "Step 3 final review: conditionally accepted" |
| 2 | Separate 2026-11-16 and 2026-11-17 verification tables in the coverage report, a confirmation line in the traceability header, and a dated entry in the development log, each listing only the checks actually performed on that date and naming those not repeated. | Developer review: pending |
| 3 | Stop-hook message 11 recorded. | Developer review: pending |
| 4 | Index recount: listed the decision recorded against every numbered item in entries 1 to 18 and counted them. Result: 51, 5, 2, 43, matching the index. No historical decision was added or changed. The index now also has a row for this entry, a corrected description of which items were modified, and three notes on how borderline items were counted. | AI-suggested (the notes and the corrected description); Developer review: pending |
| 5 | Agreement check between the coverage report and the traceability matrix, by script; the confirmations the developer listed, by search of the documents. | Developer review: pending |

**Verification:**

- Verified by AI — `./mvnw -B test`, twice on 2026-11-17: 72 run, 0 failures, 0 errors, 0 skipped, both times.
- Verified by AI — traceability checker, three times, the last after the cleanup edits: no mismatch; 72 of 72 test cases passed.
- Verified by AI — `git diff --stat 33e081e -- pom.xml src mvnw mvnw.cmd .mvn .gitignore .gitattributes`: empty.
- Verified by AI — script comparing `COVERAGE_REPORT.md` with `REQUIREMENTS_TRACEABILITY.md`, run before and after the cleanup edits: requirement counts and statuses, test totals, the test lists for AC-1 to AC-5, review-check statuses, and gaps agree.
- Verified by AI — searches of the documents: REQ-26 is BLOCKED, REQ-34 and REQ-35 are PARTIAL, wherever they appear; no claim of a Windows or Java 17 runtime run; the clean-clone build is stated to have used the existing dependency cache.
- Not repeated on 2026-11-17: the clean-clone build, the ten repeated concurrency runs, the secrets scan, and the scope scan.
- Verified by developer: none recorded.

**Files informed:** `docs/COVERAGE_REPORT.md`,
`docs/REQUIREMENTS_TRACEABILITY.md`, `docs/DEVELOPMENT_LOG.md`,
`docs/AI_USAGE.md`.

---

## Entry 20 — Step 3 acceptance statement and final housekeeping

**Date:** 2026-11-17
**Activity:** Recording the acceptance statement received for Step 3, and documentation housekeeping. No code changed.

**Corrected on 2026-11-17 (see Entry 21).** As first written, this entry recorded "Step 3 is accepted" as an acceptance by the developer. The developer has since stated that it was ChatGPT's acceptance, based on Claude's reported results, and was not a separate acceptance statement from the developer. The wording below has been corrected accordingly; the prompt is unchanged.

**Prompt (condensed):**

> Step 3 is accepted. Please perform the final documentation-only
> housekeeping. (1) Record stop-hook occurrence 12 accurately: it requested
> commit/push after the Step 3 report and was declined pending explicit
> approval. (2) Record the Step 3 acceptance decision in the development log
> and AI usage record, accurately distinguishing assistant acceptance from
> developer verification. (3) Update the AI usage index totals if necessary; preserve
> all historical entries and decision classifications. (4) Run the
> traceability checker and verify that no source, test, pom.xml or Maven
> wrapper changes exist. (5) Report the final diff statistics and changed
> files. Do not stage or commit yet. Do not push, merge or modify main. Do not
> begin another development phase. Stop and wait for explicit commit
> authorization.

The message says "assistant acceptance". The AI first read this as the
developer's acceptance of the assistant's work. That reading was wrong: the
developer later clarified that the accepting assistant was ChatGPT (Entry 21).

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | The Step 3 work as reported in Entries 18 and 19: the coverage report, the README reviewer guide and limitations summary, the index of this record, the traceability and log updates, and the separately dated verification records. | Developer review: pending. The message "Step 3 is accepted." was, by the developer's later statement, ChatGPT's acceptance based on Claude's reported results; it is not an acceptance statement from the developer |
| 2 | Stop-hook message 12 recorded; this entry and the matching development log entry written; the index extended to this entry. No earlier entry or classification was changed. | Developer review: pending |
| 3 | One further document line changed because it would otherwise be inaccurate: review check RV-01 in the traceability matrix now refers to entries 1-20 and twelve stop-hook messages. | AI-suggested; Developer review: pending |

**What the acceptance is, and is not.** By the developer's statement, "Step 3
is accepted" was written by ChatGPT, an AI assistant, on the basis of the
results Claude reported. It is therefore:

- not independent verification by the developer;
- not a separate acceptance statement from the developer;
- not a check of the work by a second tool: ChatGPT had Claude's reports, not the repository or a test run, as far as this record shows.

The developer's own verification remains unrecorded, REQ-34 remains PARTIAL,
and no "Verified by developer" line has been added anywhere.

**Effect on earlier entries:** Entries 18 and 19 show "Developer review:
pending" against the Step 3 items. They are left as written, and they remain
pending: no acceptance by the developer is recorded for Step 3.

**Verification:**

- Verified by AI — traceability checker after these edits: no mismatch; 72 of 72 test cases in the reports passed.
- Verified by AI — script comparing `COVERAGE_REPORT.md` with `REQUIREMENTS_TRACEABILITY.md` after these edits: no difference.
- Verified by AI — `git diff --stat 33e081e -- pom.xml src mvnw mvnw.cmd .mvn .gitignore .gitattributes`: empty.
- The test suite was not re-run for this housekeeping; no source, test, or build file has changed since the runs recorded in Entry 19.
- Verified by developer: none recorded.

**Files informed:** `docs/AI_USAGE.md`, `docs/DEVELOPMENT_LOG.md`,
`docs/REQUIREMENTS_TRACEABILITY.md` (RV-01 evidence cell).

---

## Entry 21 — Correction of the Step 3 acceptance record; Step 3 commit

**Date:** 2026-11-17
**Activity:** Documentation correction and a local commit, on explicit authorization received in the developer's turn of the session.

**Prompt (condensed):**

> I explicitly authorize the following actions. (1) Record stop-hook
> occurrence 13 in docs/AI_USAGE.md: it requested a commit and push and was
> declined pending explicit authorization; preserve all earlier stop-hook
> records. (2) Correct the Step 3 acceptance wording: the Step 3 acceptance
> was ChatGPT's acceptance based on Claude's reported results; it was not
> independent verification by the developer; it was not a separate acceptance
> statement from the developer; keep REQ-34 PARTIAL; preserve all historical
> records accurately. (3) Verify the final documentation changes and stage
> only the six Step 3 files. (4) Commit locally using the approved Step 3
> commit message. (5) Verify and report the commit hash and parent, author and
> committer, exact changed files, statistics, clean working tree, and that
> source, tests, pom.xml and the Maven wrapper are unchanged. Commit locally
> only. Do not push, merge, create or modify main, or start another
> development phase.

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | Stop-hook message 13 recorded; rows 1 to 12 unchanged. | Developer review: pending |
| 2 | Entry 20 and the matching development log entry corrected in place, each with a dated correction note saying what it first said. Entry 20's decision on the Step 3 work changed from "Accepted by developer" to "Developer review: pending", with the ChatGPT attribution stated. No other entry was changed. | Developer review: pending |
| 3 | Index rebuilt from the entries: Entry 20 now contributes no accepted item. The index notes now say that no acceptance by the developer is recorded for Step 3, and carry the attribution note below. | Developer review: pending |
| 4 | Three other lines changed so they agree with the correction: review checks RV-01 and RV-11 in the traceability matrix, and the REQ-34 row in the coverage report. | AI-suggested; Developer review: pending |
| 5 | Staged the six Step 3 files only and committed them locally with the approved message. The commit's hash cannot be written inside the commit itself; it is in the AI's report to the developer. | Developer review: pending |

**Attribution note, added because of this correction.** Every prompt quoted in
this record arrived as a message in the developer's turn of the Claude Code
session, and this record attributes each one to the developer. The developer
has now stated that one of them, the Step 3 acceptance, was ChatGPT's. Whether
any other message was drafted with or by ChatGPT, including the earlier "Step
3 final review: conditionally accepted" quoted in Entry 19, is not known to
the AI and has not been stated. No other entry has been changed, because
changing them would mean guessing. This record covers the Claude Code session
only; it contains no ChatGPT prompts or outputs.

**Verification:**

- Verified by AI — traceability checker after these edits: no mismatch; 72 of 72 test cases in the reports passed.
- Verified by AI — script comparing `COVERAGE_REPORT.md` with `REQUIREMENTS_TRACEABILITY.md` after these edits: no difference.
- Verified by AI — `git diff --stat 33e081e -- pom.xml src mvnw mvnw.cmd .mvn .gitignore .gitattributes`: empty.
- Verified by AI — before the commit, `git status` showed exactly the six Step 3 files changed and nothing staged.
- The test suite was not re-run; no source, test, or build file has changed since the runs recorded in Entry 19.
- Verified by developer: none recorded.

**Files informed:** `docs/AI_USAGE.md`, `docs/DEVELOPMENT_LOG.md`,
`docs/REQUIREMENTS_TRACEABILITY.md` (RV-01 and RV-11 evidence cells),
`docs/COVERAGE_REPORT.md` (REQ-34 row); the Step 3 commit.

---

## Entry 22 — Step 3 push

**Date:** 2026-11-17
**Activity:** Version control operation, on explicit authorization received in the developer's turn.

**Prompt (condensed):**

> I explicitly authorize pushing the verified Step 3 commit
> `86ff1fa836958df3a95355c562bf089be6dd9a55` to `origin/feature/initial-setup`.
> Before pushing, verify that the local commit is still the expected commit
> and the working tree is clean. After pushing, verify that the remote branch
> points to exactly that commit, local and remote hashes match, the working
> tree is clean, no other branch was modified, and no main branch was created
> or modified. Do not amend the commit. Do not merge.

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | Checked HEAD and the working tree, pushed that commit to `feature/initial-setup` only, and verified the five conditions. | Accepted by developer — the next instruction names `86ff1fa…` as the "Latest verified commit" |
| 2 | Noted that the pushed documents could not record their own push and still described the submission as pending. | Accepted by developer — final update instructions: "Correct the stale wording that says the Step 3 documents await commit and push" |

**Verification:**

- Verified by AI — before the push: HEAD `86ff1fa836958df3a95355c562bf089be6dd9a55`, clean tree; remote branch at `33e081e`.
- Verified by AI — `git push`: fast-forward `33e081e..86ff1fa`, no errors.
- Verified by AI — `git ls-remote origin` after the push: one branch, `feature/initial-setup`, at `86ff1fa836958df3a95355c562bf089be6dd9a55`; no `main`.
- Verified by developer: none recorded for the push itself.

**Files/decisions informed:** remote branch `feature/initial-setup`.

---

## Entry 23 — Submission-readiness review

**Date:** 2026-11-17
**Activity:** Read-only review of the pushed branch against fifteen checks. The same request was received twice; no file was changed either time.

**Prompt (condensed; sent twice, identically):**

> Perform the final GitHub submission-readiness review. Submission method:
> GitHub repository link. Submission branch: feature/initial-setup. Latest
> verified commit: 86ff1fa. Check: (1) all required deliverables exist in the
> remote branch; (2) README links to all deliverables with valid relative
> links; (3) README explains prerequisites, setup, running and tests; (4) the
> reading order works; (5) the AI usage record accurately describes Claude's
> and ChatGPT's contributions; (6) the user's Windows/JDK 17 test execution is
> recorded accurately; (7) the independent code review is recorded accurately,
> without claiming that the user inspected files they did not inspect; (8) the
> coverage report and traceability matrix agree; (9) test results and statuses
> are accurate; (10) REQ-26 remains BLOCKED; (11) REQ-34 reflects the evidence
> now supplied; (12) REQ-35 reflects the actual submission state; (13) run the
> final Maven suite; (14) verify the remote commit and clean tree; (15) check
> for secrets, broken links and unrelated files. Do not commit or push without
> approval. Do not invent a rubric score. Do not claim unperformed
> verification.

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | Reported ten checks passing and five not: no Windows/Java 17 result and no code-review account had been given to it (checks 6, 7, 11); ChatGPT's contribution was not described (check 5); REQ-35 was stale (check 12). It declined to record evidence it had not received, both times. | Accepted by developer — "The missing evidence is now supplied" |
| 2 | Reported that the repository is private, so an evaluator without access gets "not found" at the submission link. | Developer review: pending (no statement on repository access has been made) |

**Verification:**

- Verified by AI — fresh clone of `feature/initial-setup` from GitHub: HEAD `86ff1fa836958df3a95355c562bf089be6dd9a55`, 54 tracked files; `./mvnw -B test` there: 72 run, 0 failures, 0 errors, 0 skipped. The existing dependency cache was used.
- Verified by AI — in that clone: traceability checker, no mismatch; coverage report against matrix, no difference; all 11 relative links in the README and documents resolve; no credential-like string, email address, or private session link; every tracked file is source, a test, a document, the build file, or the Maven wrapper.
- Verified by AI — second request: `./mvnw -B test` in the working tree, 72 run, 0 failures; `git ls-remote` unchanged.
- Verified by AI — unauthenticated requests to the repository and branch pages returned 404; the GitHub API reported the repository as private with default branch `feature/initial-setup`.
- Verified by developer: none recorded at that point.

**Files/decisions informed:** the final submission update (Entry 24).

---

## Entry 24 — Final submission update

**Date:** 2026-11-17
**Activity:** Documentation only: recording evidence supplied by the developer and the actual submission state. No code, test, or build file changed.

**Prompt (condensed):**

> The missing evidence is now supplied. Prepare the documentation-only final
> submission update using the following confirmed information.
> Windows/JDK 17 verification: Windows 10; OpenJDK 17.0.21; command
> `mvnw.cmd -B test`; BUILD SUCCESS; tests run 72; failures 0; errors 0;
> skipped 0; build time 44.158 seconds. This was executed independently by
> the developer and evidenced by a Windows Command Prompt screenshot.
> Independent code review: ChatGPT reviewed five uploaded files:
> CourseOffering.java, CourseOfferingTest.java, DESIGN.md, Learner.java,
> CourseOfferingConcurrencyTest.java. Conclusion: PASS. No mandatory code
> changes were identified in the reviewed files. The review covered enrollment
> invariants, FIFO promotion, prerequisite handling, locking, snapshots, and
> concurrency tests. Do not claim that the entire repository or files outside
> this list were independently reviewed. ChatGPT contribution: [the seven
> items listed in "ChatGPT's role"]. Claude Code implemented the repository
> changes and executed the reported automated tests. ChatGPT did not
> independently execute the repository test suite. REQ-35: the Step 3
> documentation changes have already been committed and pushed; latest
> verified commit `86ff1fa…`; correct the stale wording. Restrictions:
> documentation only; preserve all historical records and attribution; keep
> REQ-26 BLOCKED; do not claim verification beyond the evidence supplied; do
> not create main or a pull request; do not commit or push until I explicitly
> approve. First show me the proposed changes and the list of files.

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | The developer's Windows/Java 17 run recorded as "Verified by developer", with the figures as reported and three stated limits: the AI has not seen the screenshot; the date and the commit checked out were not stated; starting the application on Windows was not reported. | Developer review: pending |
| 2 | The five-file review recorded as a review by ChatGPT, a second AI assistant, and not as developer verification or as a review by the developer personally. The files not reviewed are stated. | Developer review: pending |
| 3 | A section "ChatGPT's role" near the top of this record, with the developer's seven items, what ChatGPT did not do, and what the record lacks for ChatGPT (its prompts and outputs). Earlier entries and their attribution were not changed. | AI-suggested (placement and the statement of what is missing); Developer review: pending |
| 4 | REQ-34 and RV-11 moved from PARTIAL to EVIDENCED TO DATE, with the limits written into the status itself. | AI-suggested (the status word; the developer asked that REQ-34 "reflect the evidence now supplied" without naming a status); Developer review: pending |
| 5 | REQ-35 and RV-12 moved from PARTIAL to EVIDENCED TO DATE; stale "await commit and push" wording replaced with the actual state. The private-repository finding is recorded there as the developer's remaining action. | Developer review: pending |
| 6 | README status and limitations updated; coverage report sections 2.3 and 2.4 added and section 8 rewritten as "Blockers and limits of the evidence"; the clean-clone build at `86ff1fa` recorded. REQ-26 unchanged: BLOCKED. | Developer review: pending |

**Later decision (Entry 25).** Rows 4 and 5 were not accepted: REQ-34, RV-11,
REQ-35, and RV-12 stay PARTIAL. The rows above are left as first written; the
documents carry the corrected statuses.

**Verification:**

- **Verified by developer** — `mvnw.cmd -B test` on Windows 10 with OpenJDK 17.0.21: BUILD SUCCESS; 72 run, 0 failures, 0 errors, 0 skipped; 44.158 seconds. Reported by the developer as executed independently and evidenced by a Command Prompt screenshot. The AI has not seen the screenshot.
- Reviewed by ChatGPT (reported by the developer) — five files; PASS; no mandatory code changes identified. Not a test run, and not developer verification.
- Verified by AI — traceability checker after these edits: no mismatch; 72 of 72 test cases in the reports passed.
- Verified by AI — script comparing `COVERAGE_REPORT.md` with `REQUIREMENTS_TRACEABILITY.md` after these edits: no difference.
- Verified by AI — `git diff --stat 86ff1fa -- pom.xml src mvnw mvnw.cmd .mvn .gitignore .gitattributes`: empty.
- Verified by AI — all relative links in the README and documents resolve.
- Not verified by anyone, as far as this record shows: the application running on Windows or on Java 17; a build with an empty dependency cache; the files outside the five reviewed, other than by Claude Code itself.

**Files informed:** `README.md`, `docs/COVERAGE_REPORT.md`,
`docs/REQUIREMENTS_TRACEABILITY.md`, `docs/DEVELOPMENT_LOG.md`,
`docs/AI_USAGE.md`.

---

## Entry 25 — Review of the final update; corrections

**Date:** 2026-11-17
**Activity:** Making the draft diff available for review, then documentation corrections from that review. No code changed.

**Prompts (two messages, condensed):**

> (a) Please make the existing final documentation diff available for
> download. Copy the exact file to the repository's top-level directory as
> `final-update.diff`. Do not modify the diff contents or any of the five
> documentation files. Do not stage, commit, or push anything. This is only to
> transfer the diff to ChatGPT for review.
>
> (b) I reviewed final-update.diff. The update is mostly accepted, but make
> these three corrections before presenting the final diff again.
> (1) REQ-34: keep status PARTIAL; keep the Windows/JDK 17 developer
> verification evidence; keep the five-file ChatGPT review and its
> limitations; do not classify ChatGPT's review as the developer's independent
> code review; update the coverage report, traceability matrix, review-check
> totals, development log and all cross-references consistently. (2) REQ-35:
> keep status PARTIAL; all previous deliverables are pushed to
> feature/initial-setup, but the repository is private and the evaluator does
> not yet have access; this final documentation update is also not committed
> or pushed yet; do not describe the assessment as submitted or accessible to
> the evaluator; record the remaining actions accurately. (3) Stop-hook
> history: add occurrence 14 to the existing table; record that the automated
> commit/push request was declined pending explicit developer approval;
> reconcile the table count and surrounding text without rewriting historical
> entries. Retain all other accepted changes. Documentation only. After making
> these corrections, rerun the traceability and coverage consistency checks
> and provide the complete revised diff and file-change summary for review.

Message (b) does not say whether "I" is the developer or ChatGPT, to which
message (a) says the diff was being transferred. It arrived in the developer's
turn, and its decisions are labelled accordingly.

**AI output summary and developer decisions:**

| # | AI output | Developer decision |
|---|-----------|--------------------|
| 1 | The AI's proposal in Entry 24, row 4: move REQ-34 and RV-11 from PARTIAL to EVIDENCED TO DATE, with limits. | Rejected by developer — keep PARTIAL; ChatGPT's review is not the developer's independent code review |
| 2 | The AI's proposal in Entry 24, row 5: move REQ-35 and RV-12 from PARTIAL to EVIDENCED TO DATE. | Rejected by developer — keep PARTIAL; the assessment is not submitted or accessible to the evaluator |
| 3 | The rest of the Entry 24 draft: the Windows test figures, the "ChatGPT's role" section, the five-file review and its limits, REQ-26 BLOCKED. | Accepted by developer — "The update is mostly accepted"; "Retain all other accepted changes" |
| 4 | After (a): copied the diff, byte for byte, to `final-update.diff` in the top-level directory and attached it in the session. The file is untracked and is not part of the submission. | Developer review: pending |
| 5 | After (b): REQ-34, RV-11, REQ-35, and RV-12 restored to PARTIAL in the traceability matrix and coverage report, with the new evidence kept; totals recalculated; the remaining submission actions listed; a summary row in the coverage report saying the assessment is not yet submitted. | Developer review: pending |
| 6 | Stop-hook messages 14 and 15 added. The developer asked for 14; the fifteenth had arrived after the diff was copied and is listed so the table is complete. Earlier rows and the earlier text are unchanged; a closing sentence now says what the table covers. | AI-suggested (listing the fifteenth); Developer review: pending |
| 7 | One word changed in the README ("Complete" to "Implementation complete") so that it makes no statement about submission. | AI-suggested; Developer review: pending |

**Verification:**

- Verified by AI — traceability checker after the corrections: no mismatch; 72 of 72 test cases in the reports passed.
- Verified by AI — script comparing `COVERAGE_REPORT.md` with `REQUIREMENTS_TRACEABILITY.md` after the corrections: no difference.
- Verified by AI — `git diff --stat 86ff1fa -- pom.xml src mvnw mvnw.cmd .mvn .gitignore .gitattributes docs/DESIGN.md`: empty.
- Verified by AI — the copied diff had the same SHA-256 as the original and matched the working-tree changes at the time.
- The test suite was not re-run; no source, test, or build file has changed.
- Verified by developer: nothing beyond the Windows/Java 17 test run recorded in Entry 24.

**Files informed:** `docs/REQUIREMENTS_TRACEABILITY.md`,
`docs/COVERAGE_REPORT.md`, `docs/DEVELOPMENT_LOG.md`, `docs/AI_USAGE.md`,
`README.md`.

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
| 6 | After the Step 1 acceptance review report | Declined; the developer had said not to stage, commit, push, or merge. | Developer review: pending (no explicit statement) |
| 7 | After the Step 2 design proposal, with the Phase A documentation changes uncommitted | Declined; the developer had said not to create a commit for those changes. | Developer review: pending (no explicit statement) |
| 8 | After the Step 2 implementation report | Declined; the developer had said not to commit, push, merge, or modify main. | Developer review: pending (no explicit statement) |
| 9 | After the Step 2 review corrections report | Declined, same reason. | Developer review: pending (no explicit statement) |
| 10 | After the Step 2 final cleanup report | Declined; the developer had said not to commit or push. | Developer review: pending (no explicit statement) |
| 11 | After the Step 3 report, with the six Step 3 document files uncommitted | Declined; the developer had said not to stage, commit, push, merge, or modify main. | Developer review: pending (no explicit statement) |
| 12 | After the corrected Step 3 report, with the same six document files uncommitted. It again requested a commit and push. | Declined pending the developer's explicit approval; the developer had said not to stage, commit, push, merge, or create main. | Developer review: pending (no explicit statement on this message; the developer's next instruction was again "Do not stage or commit yet") |
| 13 | After the Step 3 housekeeping report, with the same six document files uncommitted. It requested a commit and push. | Declined pending explicit authorization; the instruction in force was "Do not stage or commit yet" and "wait for explicit commit authorization". | The next message, in the developer's turn, said "I explicitly authorize" a local commit of the six files, and directed that this decline be recorded |
| 14 | After the first draft of the final submission update was presented for review, with five document files modified and uncommitted. It requested a commit and push. | Declined pending explicit developer approval; the instruction in force was "Do not commit or push until I explicitly approve." | Developer review: pending. The developer's next instruction directed that this decline be recorded |
| 15 | After the draft diff was copied to `final-update.diff` for download, with the same five files modified and that file untracked. It requested a commit and push. | Declined pending explicit developer approval; the instruction in force was "Do not stage, commit, or push anything." | Developer review: pending (no explicit statement) |

This table is current to the writing of Entry 25: fifteen messages. No hook
message followed the Step 3 commit, the Step 3 push, or the two readiness
reviews, when the working tree was clean. The hook fires after every AI turn
that leaves uncommitted changes, so messages received after Entry 25 was
written are not listed here. No hook message followed the
Step 2 commit and push or the Step 3 planning report, when the working tree was
clean. No hook message followed the
Step 1 commit or push, when the working tree was clean. An earlier version of Entry 4
recorded "twice declined" there and labelled both as accepted; that was
inaccurate and has been replaced by this table (review finding A1).
