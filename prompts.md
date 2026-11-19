# Prompts and AI-Assisted Workflow

A chronological record of how ChatGPT and Claude Code were used in the Course
Waitlist Engineering Assessment, and of the prompts that guided the work.

**Written:** 2026-11-19, after the work was done, at a reviewer's request. It
is a retrospective record. The contemporaneous record, written as the work
happened, is [docs/AI_USAGE.md](docs/AI_USAGE.md), with
[docs/DEVELOPMENT_LOG.md](docs/DEVELOPMENT_LOG.md). Where this file and those
records differ, those records are the primary source.

**What is reproduced and what is reconstructed.** Section 4 reproduces the
messages sent into the Claude Code session from that session's history. Most
are word for word; some carry small, disclosed redactions, and one is
abridged. Each block says which applies. No ChatGPT conversation history was
available when this file was written, so section 3 contains no ChatGPT
prompts: every ChatGPT entry is a **reconstructed summary**, labelled as such,
built only from the developer's own statements recorded in this project.
Section 7 lists every limit of the record.

## Contents

1. [Purpose and project context](#1-purpose-and-project-context)
2. [How ChatGPT and Claude Code were used together](#2-how-chatgpt-and-claude-code-were-used-together)
3. [ChatGPT prompts and instructions](#3-chatgpt-prompts-and-instructions-chronological)
4. [Claude Code prompts](#4-claude-code-prompts-chronological)
5. [How AI-generated suggestions were reviewed and verified](#5-how-ai-generated-suggestions-were-reviewed-and-verified)
6. [Developer decisions, approvals and independent verification](#6-developer-decisions-approvals-and-independent-verification)
7. [Limitations of the prompt-history record](#7-limitations-of-the-prompt-history-record)

---

## 1. Purpose and project context

The assessment asks for one bounded feature: enrollment, dropping, and FIFO
waitlist promotion for a single course offering, built with AI as an
engineering assistant. Acceptance criterion 7 of the brief asks the candidate
to "record the prompts used, relevant AI response or summary, whether each
suggestion was accepted, edited, or rejected, and how the resulting work was
verified."

This file answers a reviewer's request to see those prompts in one place, and
to understand how two AI tools shared the work.

| | |
|---|---|
| Repository | `keejoshi/course-waitlist`, branch `feature/initial-setup` |
| Built | Spring Boot 3.5 service, Java 17 target, Maven, in-memory state, one course offering |
| Delivered in | Step 0 (skeleton), Step 1 (domain model), Step 2 (service and REST API), Step 3 (verification and documentation), and a final documentation update |
| Commits | `1c709c3` Step 0, `2cc6d95` Step 1, `33e081e` Step 2, `86ff1fa` Step 3, `6d2d633` final update |
| Period | 2026-11-15 to 2026-11-17, with planning conversations from 2026-11-15 and later follow-up |

---

## 2. How ChatGPT and Claude Code were used together

Three parties took part. The table states what each did, on the evidence
available.

| Party | Role | Evidence |
|-------|------|----------|
| **Developer** (Keerthi Joshi) | Directed the work, sent every message into the Claude Code session, reviewed results, made decisions, and approved every commit and push. Ran the full test suite independently on Windows. | The Claude Code session history (section 4); the developer's statements recorded in `docs/AI_USAGE.md`. |
| **ChatGPT** | Assessment interpretation and requirement decomposition; technical design discussions and documentation of approved decisions; preparation of implementation and review prompts for Claude Code; review of Claude's implementation reports and test evidence; review of five uploaded source and design files; identification of inconsistencies, test gaps, documentation corrections and limitations; final submission planning. | The developer's statement, given on 2026-11-17 and recorded in `docs/AI_USAGE.md`, section "ChatGPT's role". No ChatGPT transcripts are in the repository. |
| **Claude Code** | Implemented every change in the repository (all code, tests, and document text), proposed designs when asked, reviewed its own work, ran fault-injection checks, and executed every automated test run it reports. | The Claude Code session history; the commits; `docs/AI_USAGE.md` entries 1 to 25. |

**How the hand-off worked, as far as the record shows.** ChatGPT worked
outside the repository. By the developer's statement, it prepared
implementation and review prompts for Claude Code and reviewed Claude's
reports. The developer then sent prompts into the Claude Code session, Claude
Code did the work and reported, and those reports were reviewed before the
next instruction. Twice the developer moved material from Claude Code to
ChatGPT: the five files ChatGPT reviewed, and a diff "to transfer to ChatGPT
for review" (section 4, entry 32).

**What is not known.** For most messages in section 4, the record does not
say whether the developer wrote it, ChatGPT drafted it, or both. One case is
known: the Step 3 acceptance (section 4, entry 27) was, by the developer's
later statement, ChatGPT's acceptance. Section 4 therefore describes every
message as "sent by the developer" and does not guess at who drafted it.

---

## 3. ChatGPT prompts and instructions (chronological)

**No verbatim ChatGPT prompts are available.** The ChatGPT conversations were
not saved to this repository, and they were not accessible when this file was
written. Every entry below is a **reconstructed summary**. Each one states its
source. None of them should be read as the wording of a ChatGPT prompt or
response.

The sources are:

- **S1** — the developer's statement of ChatGPT's contributions, given on 2026-11-17 (`docs/AI_USAGE.md`, "ChatGPT's role").
- **S2** — the developer's list of events from "our project planning conversations dated November 15–16, 2026", given on 2026-11-15 (`docs/DEVELOPMENT_LOG.md`, historical backfill). That list does not say which tool the conversations used; it is attributed to ChatGPT here only because S1 says ChatGPT carried out the interpretation and design discussions. This is an inference and is labelled as one.
- **S3** — specific later statements by the developer about individual ChatGPT actions (entries G7 to G10).

| # | Phase | Tool | Reconstructed summary | Purpose | Resulting work, where evidenced | Source |
|---|-------|------|-----------------------|---------|---------------------------------|--------|
| G1 | Before Step 0 (planning, 2026-11-15 to 2026-11-16) | ChatGPT (inferred) | **Reconstructed summary.** The assessment brief's requirements and scope were reviewed and broken down. | Understand the brief before any code. | The decomposition later formalised in Claude Code as 31, then 40, requirement ids (section 4, entries 9 to 11). Which parts came from ChatGPT is not recorded. | S1, S2 |
| G2 | Before Step 0 | ChatGPT (inferred) | **Reconstructed summary.** The architecture and implementation approach were discussed, and the stack was settled: Spring Boot 3.5.x, Java 17 compatibility, Maven, REST, in-memory state. | Choose the technology and the shape of the solution. | The Step 0 instruction to Claude Code names exactly this stack (section 4, entry 2). | S1, S2 |
| G3 | Before Step 0 | ChatGPT (inferred) | **Reconstructed summary.** Policies were agreed for enrollment, duplicate enrollment, the FIFO waitlist, and prerequisites. | Settle the business rules the brief leaves open. | The policies the developer restated to Claude Code at the start of Step 1: duplicate enrollment returns 409; ineligible waitlisted learners are skipped but stay on the waitlist; prerequisites are checked at enrollment and at promotion (section 4, entry 8). | S1, S2 |
| G4 | Before Step 0 | ChatGPT (inferred) | **Reconstructed summary.** A strict isolation rule was established: no code, files, credentials, or architecture from any other project. | Keep the assessment self-contained. | Restated in most Claude Code prompts. | S2 |
| G5 | Steps 0 to 3 | ChatGPT | **Reconstructed summary.** Technical design discussions, and documentation of approved decisions, covering the domain model, concurrency, service boundaries and the REST API, and testing strategy. | Prepare and check design decisions before they reached Claude Code. | The design decisions sent to Claude Code in section 4, entries 9, 18, and 24. Which individual decisions originated with ChatGPT is not recorded. | S1 |
| G6 | Steps 0 to 3 | ChatGPT | **Reconstructed summary.** Implementation and review prompts for Claude Code were prepared. | Turn decisions into precise instructions. | The prompts in section 4, entered by the developer. Which of them were drafted by ChatGPT, and how far, is not recorded. | S1 |
| G7 | Steps 1 to 3 | ChatGPT | **Reconstructed summary.** Claude Code's implementation reports and test evidence were reviewed, and inconsistencies, test gaps, documentation corrections, and limitations were identified. | Independent check of Claude's claims before acceptance. | Not attributable to individual findings: several Claude Code prompts ask for corrections (section 4, entries 12, 19, 20, 26, 33), but the record does not say which findings came from ChatGPT's review. | S1 |
| G8 | Final | ChatGPT | **Reconstructed summary.** Five uploaded files were reviewed: `CourseOffering.java`, `CourseOfferingTest.java`, `DESIGN.md`, `Learner.java`, `CourseOfferingConcurrencyTest.java`. The review covered enrollment invariants, FIFO promotion, prerequisite handling, locking, snapshots, and concurrency tests. Conclusion: PASS; no mandatory code changes identified in those files. ChatGPT did not run the test suite and did not review other files. | Second-assistant review of the core domain code. | Recorded in `docs/COVERAGE_REPORT.md`, section 2.4. No code change followed. | S1, S3 |
| G9 | Step 3 | ChatGPT | **Partly known wording.** The message sent into Claude Code that began "Step 3 is accepted." was, by the developer's later statement, "ChatGPT's acceptance based on Claude's reported results". That sentence is the only wording in this file known to be ChatGPT's. Whether ChatGPT wrote the rest of that message is not stated. | Accept the Step 3 work. | Recorded as an assistant's acceptance, not the developer's (section 4, entries 27 and 28). | S3 |
| G10 | Final | ChatGPT | **Reconstructed summary.** A diff of the proposed final documentation update was transferred to ChatGPT "for review". A review returned three corrections: keep REQ-34 and REQ-35 PARTIAL, and record a further automated message. The message delivering the corrections says "I reviewed final-update.diff" and does not say whether the reviewer was the developer or ChatGPT. | Review of the final documentation before commit. | The corrections were applied (section 4, entries 32 and 33). | S3 |
| G11 | Final | ChatGPT | **Reconstructed summary.** Final submission planning. | Prepare the submission. | Not attributable to specific messages. | S1 |

### Coverage of the topics a reviewer may look for

| Topic | ChatGPT involvement evidenced? | What the evidence is |
|-------|-------------------------------|----------------------|
| Understanding and breaking down the assessment | Yes, generally | S1 ("assessment interpretation and requirement decomposition") |
| Functional and non-functional requirements | Yes, generally | S1; no detail |
| Technology stack and project structure | Inferred | S2 lists the stack as settled in planning conversations; S1 covers "technical design discussions" |
| Domain model and business rules; enrollment, waitlist, FIFO, prerequisites | Inferred for the policies (S2); generally for design (S1) | No record of specific ChatGPT proposals |
| Concurrency and thread safety | Generally (S1); reviewed in G8 | No record of specific ChatGPT proposals |
| REST APIs and service boundaries | Generally (S1) | No record of specific ChatGPT proposals |
| Testing strategy and edge cases | Generally (S1: "test gaps"); reviewed in G8 | No record of specific ChatGPT proposals |
| Reviewing Claude's reports; bugs, weaknesses, missing coverage | Yes, generally (S1) | Not traceable to individual findings |
| Reviewing source code and design documentation | Yes, specifically (G8) | Five files, PASS |
| Traceability and AI usage documentation | Generally (S1: "documentation corrections") | No detail |
| Final verification and submission readiness | Yes (G9, G10, G11) | One known acceptance; one diff review |

---

## 4. Claude Code prompts (chronological)

Each entry below is a message sent into the Claude Code session in the
developer's turn. The text in each collapsible block is **reproduced from the
session history**. It is not presented as unmodified in every case: three
kinds of alteration were made, and the heading of each block says which, if
any, applies to it.

| Alteration | Where it was made |
|------------|-------------------|
| **Redaction:** the name of an unrelated project, in the isolation instruction, is replaced by `[unrelated project]`. | Entries 1, 3, 8, 9, 11, 13, 17, 18, 23, 36 |
| **Redaction:** references to uploaded files, and a path in Claude Code's scratch directory, are replaced by a short description in square brackets. | Entries 2 and 32 |
| **Abridgment:** the message is not reproduced in full; its opening and content are summarised instead. | Entry 37 |

Apart from these, the wording, line breaks, and list markers are as they were
received. Blocks headed "unaltered" have no alteration at all.

"Resulting work" is a short description of what Claude Code did, supported by
the commits and by `docs/AI_USAGE.md` and `docs/DEVELOPMENT_LOG.md`. It is
not Claude Code's response text. Automated messages from the environment, and
messages that are not instructions, are left out; section 7 lists them.

Dates are those of the session, in UTC.

### Step 0 — repository setup (2026-11-15)

#### Entry 1 — Environment verification

| Phase | Tool | Purpose |
|-------|------|---------|
| Setup | Claude Code | Check the repository, branch, cloud environment, Java and Maven before any work. |

<details><summary>Developer prompt — from session history; redaction: project name</summary>

```text
We are starting a fresh Claude Code Cloud session.
Before implementing anything, verify the environment:

1. Confirm the GitHub repository is `keejoshi/course-waitlist`.
2. Confirm the current branch and Git remote.
3. Confirm this is a cloud environment, not my local Windows machine.
4. Confirm Java 17+ is available and Maven can run.
5. Check whether the repository is empty or contains existing files.
6. Confirm you have permission to create and push a feature branch.

Do NOT create files, generate a Spring Boot project, commit, or push anything yet.
Report the results of these checks and wait for my approval before proceeding to Step 0.
Strict isolation: This assessment belongs to Keerthi Joshi. Never access, reuse, modify, or integrate [unrelated project] code, files, credentials, or architecture.
```

</details>

**Resulting work:** read-only checks; reported the repository empty, JDK 21 and Maven 3.9 available, and push permission not provable until a first push. No files created.

#### Entry 2 — Step 0 project skeleton

| Phase | Tool | Purpose |
|-------|------|---------|
| Setup | Claude Code | Create the minimal Spring Boot project and documentation scaffolding. |

<details><summary>Developer prompt — from session history; redaction: file references</summary>

```text
[attached: Spring Initializr project zip] Approved. Proceed with Step 0: Initial project setup.
Create the minimal Spring Boot project skeleton in the current GitHub repository.
Requirements:

* Spring Boot 3.5.x
* Java 17 compatibility
* Maven
* Package: com.assessment.coursewaitlist
* Dependencies: Spring Web, Validation, Spring Boot Test
* Generate a proper Maven project structure.
* Create a meaningful README.md with project overview and initial setup instructions.
* Create docs/DEVELOPMENT_LOG.md and docs/AI_USAGE.md.
* Include the previously agreed documentation rules and historical backfill.
* Add a basic application startup test.
* Do not implement enrollment, waitlist, or business logic yet.
* Do not add a database, frontend, Docker, or unnecessary dependencies.
* Do not merge into main.
* Do not push anything until I approve the proposed commit and push.

After implementation:

1. Show the complete file tree.
2. Explain every dependency added.
3. Run Maven tests and report actual results.
4. Show the proposed Git changes.
5. Wait for my review before committing or pushing.
```

While Claude Code was working, the assessment brief was sent as a separate message: `[attached: assessment brief PDF]`.

</details>

**Resulting work:** the uploaded skeleton was Spring Boot 4.0.9 with a mistyped package, so Claude Code rebuilt it on 3.5.16 with the required package; added README, development log, and AI usage record; one startup test passed.

#### Entry 3 — Documentation rules and historical backfill

| Phase | Tool | Purpose |
|-------|------|---------|
| Documentation | Claude Code | Replace Claude's draft rules with the agreed ones; backfill the planning history. |

<details><summary>Developer prompt — from session history; redaction: project name</summary>

```text
Thank you. Step 0 looks good, and I appreciate you respecting the commit/push approval boundary.
Before I approve the commit, make the following documentation corrections:

1. Apply the agreed documentation rules:

* DEVELOPMENT_LOG.md and AI_USAGE.md must be updated in the same commit as the changes they describe.
* DEVELOPMENT_LOG.md must be chronological.
* AI_USAGE.md must include prompts, output summaries, and developer decisions.
* Never claim verification unless the actual command was run.
* Never claim developer review unless explicitly confirmed.
* Use these labels consistently:
AI-suggested
Accepted by developer
Modified by developer
Rejected by developer
Verified by AI
Verified by developer
Developer review: pending

2. Backfill the historical events from our project planning conversations dated November 15–16, 2026:

* Assessment requirements and scope reviewed.
* Keerthi Joshi's repository identified.
* Initial repository found empty.
* Architecture and implementation approach discussed.
* Stack finalized: Spring Boot 3.5.x, Java 17 compatibility, Maven, REST, in-memory state.
* Enrollment, duplicate enrollment, FIFO waitlist and prerequisite policies agreed.
* Strict [unrelated project] isolation established.
* New Claude Code Cloud environment created and verified.
* Step 0 skeleton generated and tests executed successfully.

Distinguish conversation-recorded decisions from actual command verification. Do not invent historical AI outputs or developer verification.

3. Update the README to document the Maven wrapper commands:

* ./mvnw test
* ./mvnw spring-boot
Mention the Windows wrapper equivalents where appropriate.

4. Re-run the tests after documentation changes and show the diff.

Do not commit or push yet. Wait for my review.
```

</details>

**Resulting work:** rules and labels applied; the planning events recorded as "conversation-recorded" with no invented outputs; README wrapper commands documented (`spring-boot:run`, since `spring-boot` alone is not a valid goal).

#### Entry 4 — Step 0 commit

| Phase | Tool | Purpose |
|-------|------|---------|
| Version control | Claude Code | Verify and commit Step 0 locally. |

<details><summary>Developer prompt — from session history; unaltered</summary>

```text
I approve Step 0, including the project skeleton, README, and documentation scaffolding.
Before committing:

1. Confirm all 12 expected files are present.
2. Confirm there are no unrelated changes.
3. Confirm the documentation contains no fabricated verification or developer approvals.
4. Show the final staged changes summary.

Then:

* Commit with the approved message:
"Step 0: Spring Boot 3.5 project skeleton and documentation scaffolding"
* Do not push yet.
* Report the commit hash and clean/dirty working tree status.

Do not merge into main or modify any other repository.
```

</details>

**Resulting work:** 12 files committed locally.

#### Entries 5 and 6 — Commit author

| Phase | Tool | Purpose |
|-------|------|---------|
| Version control | Claude Code | Check, then correct, the commit author. |

<details><summary>Developer prompts — from session history; unaltered</summary>

```text
Thanks. Step 0 commit is approved.

Do not push yet.

First, show me the configured Git author name and email, and explain how the current commit author appears.

Do not amend the commit or change Git identity without my approval.

Keep the commit hash and working tree unchanged while we decide the author identity and push procedure.
```

```text
Keerthi Joshi's GitHub noreply email is:
[85629965+keejoshi@users.noreply.github.com](mailto:85629965+keejoshi@users.noreply.github.com)
Please amend the existing Step 0 commit so that:

* Author name: Keerthi Joshi
* Author email: [85629965+keejoshi@users.noreply.github.com](mailto:85629965+keejoshi@users.noreply.github.com)
* Retain Claude's AI co-author disclosure.
* Do not change any project files.
* Do not push anything.
* Do not merge into main.

After amending, show:

1. New commit hash.
2. Author and committer details.
3. Commit message and co-author trailers.
4. Git status.

Wait for my review.
```

</details>

**Resulting work:** the author was the container's default identity; it was amended to the developer, keeping the AI co-author trailer. Commit `1c709c3`.

#### Entry 7 — Step 0 push

| Phase | Tool | Purpose |
|-------|------|---------|
| Version control | Claude Code | Push Step 0. |

<details><summary>Developer prompt — from session history; unaltered</summary>

```text
I approve pushing the Step 0 commit.
Push the current commit:
1c709c3b792c25363bba9550bf92b73f0c90ae7e
to Keerthi Joshi's repository:
https://github.com/keejoshi/course-waitlist
Use this remote branch name:
feature/initial-setup
Important:

* Push only this commit to feature/initial-setup.
* Do not push to main.
* Do not create or merge into main.
* Do not modify project files.
* Do not amend the commit.
* Do not push any other branch.
* Verify the remote branch points to the exact approved commit.
* Report the push result and remote branch URL.

If the push fails, stop and report the error without attempting another branch or changing repository settings.
```

</details>

**Resulting work:** pushed; remote branch verified at `1c709c3`.

### Step 1 — domain model (2026-11-15)

#### Entry 8 — Domain design proposal

| Phase | Tool | Purpose |
|-------|------|---------|
| Design | Claude Code | Get a domain design for review before any code, under the already agreed policies. |

<details><summary>Developer prompt — from session history; redaction: project name</summary>

```text
Step 0 is approved, committed, and successfully pushed.
We are starting Step 1: Domain Model and Core Enrollment Design.
Work exclusively in:

* Repository: keejoshi/course-waitlist
* Remote branch: feature/initial-setup

First, inspect the existing project and assessment brief.
Before implementing:

1. Propose the domain model for Learner, Course, and CourseOffering.
2. Explain responsibilities and invariants of CourseOffering.
3. Explain how completed prerequisites are represented.
4. Explain how enrollment and waitlist membership will be represented.
5. Explain how FIFO ordering will be preserved.
6. Explain how thread safety will be handled.
7. Propose domain-level tests.

Do not implement anything yet.
Keep all previously approved policies:

* Duplicate enrollment returns HTTP 409.
* Ineligible waitlisted learners are skipped during promotion but remain on the waitlist.
* Prerequisites are checked on initial enrollment and promotion.
* One offering only; in-memory implementation.
* No frontend or database.
* Maintain AI_USAGE.md and DEVELOPMENT_LOG.md.
* Strict [unrelated project] isolation.
* No merge into main.

Wait for my review of the domain design before coding.
```

</details>

**Resulting work:** Claude Code proposed `Course`, `Learner`, `CourseOffering` with one lock per offering and an insertion-ordered waitlist, and raised four open decisions.

#### Entry 9 — Design decisions and the coverage mandate

| Phase | Tool | Purpose |
|-------|------|---------|
| Design, requirements | Claude Code | Settle the four decisions; require a master checklist and traceability for every requirement in the brief. |

<details><summary>Developer prompt — from session history; redaction: project name</summary>

```text
I have reviewed your domain design. The overall design is approved, subject to the following decisions and clarifications.
Decision 1: Open seat and existing waitlist
Existing eligible waitlisted learners must receive priority over new enrollment requests.
If a seat is open and an eligible learner is already waitlisted, promote the earliest eligible waitlisted learner before admitting a new requester.
Do not allow new learners to jump ahead of existing eligible waitlisted learners.
Decision 2: Dropping from the waitlist
A learner who is waitlisted must be allowed to leave the waitlist.
Add a distinct outcome such as DROPPED_FROM_WAITLIST.
Removing a waitlisted learner must not affect enrolled learners or trigger unnecessary promotions.
Decision 3: Duplicate requests
Confirmed:

* Already enrolled: ALREADY_ENROLLED → HTTP 409.
* Already waitlisted: ALREADY_WAITLISTED → HTTP 409.

Both duplicate operations must leave state unchanged.
Decision 4: Prerequisite completion revocation
Approved.
Learner must support markCompleted(code) and revokeCompletion(code).
Eligibility is checked at initial enrollment and promotion.
Existing enrolled learners are not automatically removed if a prerequisite is later revoked.
Additional approvals

* String IDs with non-blank validation are approved.
* Course is immutable.
* CourseOffering owns enrollment and waitlist state.
* FIFO waitlist ordering is maintained.
* Ineligible waitlisted learners are skipped during promotion but retained in their original positions.
* If no eligible waiter exists, the seat remains vacant.
* Use docs/DESIGN.md for the design specification.
* Record the Step 0 author amendment and push in the documentation.

Before implementation
Update the domain design and test plan with these decisions.
Then implement Step 1:

* Course
* Learner
* CourseOffering
* EnrollmentResult
* DropResult
* Domain unit tests

Update docs/DESIGN.md, docs/DEVELOPMENT_LOG.md, and docs/AI_USAGE.md in the same change.
Run the tests and report actual results.
Show the complete diff and proposed commit message.
Do not commit or push until I explicitly approve.
Continue using the remote branch feature/initial-setup.
Do not merge into main.
Maintain strict [unrelated project] isolation.
One critical requirement I want to make absolutely clear:
EVERY test case and acceptance criterion in the assessment brief MUST be covered. NOT A SINGLE ONE MAY BE MISSED OR OMITTED.
This is mandatory for the entire project, not just Step 1.
Follow these rules throughout development:

1. Extract every acceptance criterion and test scenario from the assessment brief into a master requirements/test checklist.
2. Assign a unique ID to every requirement and test case.
3. Maintain a traceability matrix mapping:
   * Assessment requirement
   * Implementation component
   * Test case ID
   * Test result
4. Every requirement must have at least one corresponding test.
5. Include all explicitly required edge cases, negative cases, duplicate operations, FIFO behavior, prerequisite eligibility, invalid drops, and concurrency scenarios where specified.
6. Do not mark a requirement covered merely because related code exists. It must have an actual test.
7. Do not mark a test as passed unless it was actually executed.
8. If a requirement cannot be implemented or tested, explicitly report it as BLOCKED or NOT COVERED. Never silently omit it.
9. Before every commit, perform a requirements coverage review and report:
   * Total requirements
   * Requirements covered by tests
   * Requirements implemented
   * Tests executed and passed
   * Tests failed
   * Tests not yet implemented
   * Tests not yet executed
   * Any outstanding gaps
10. The final submission must include a complete assessment coverage report proving that every required acceptance criterion and test scenario has been addressed.

Create and maintain:

* docs/REQUIREMENTS_TRACEABILITY.md
* A comprehensive automated test suite
* A final test/acceptance coverage report

Update the documentation in the same commit as the implementation.
Do not reduce scope or skip tests to save time without explicitly asking me first.
A passing build alone is NOT sufficient for approval.
Confirm this instruction and show me the complete master test checklist extracted from the assessment brief before continuing implementation.
```

</details>

**Resulting work:** Claude Code produced a master checklist (31 requirements, 48 tests, 8 review checks) and flagged that the rubric file named in the brief had not been supplied. Decision 2 overruled Claude's recommendation.

#### Entries 10 and 11 — Checklist corrections and approval

| Phase | Tool | Purpose |
|-------|------|---------|
| Requirements, testing | Claude Code | Correct the checklist, then start Step 1 implementation. |

<details><summary>Developer prompts — from session history; redaction: project name (entry 11)</summary>

```text
The checklist is very good. I approve the separation between automated behavioural tests and documentation/scope review checks, with the following corrections before implementation:

1. Every behavioural acceptance criterion must have at least one executable, actually-run test. Documentation and scope requirements may use explicit review evidence instead of artificial JUnit tests.
2. Keep all 31 domain scenarios, 4 concurrency scenarios, and 13 API scenarios provisionally, but review TC-A-08 (unknown learner) and TC-A-10 (unknown offering) against the actual API design. Do not implement unnecessary learner/offering registries just to satisfy these tests. If a scenario is inapplicable, explain why and propose an equivalent test or review check before changing the checklist.
3. TC-D-17 must explicitly use an offering with exactly one remaining seat so the eligible waiter is promoted and the new requester is waitlisted.
4. Explain the conditional concurrency requirement and why the additional concurrency tests are appropriate for this implementation.
5. Keep REQ-26 BLOCKED until the actual Course-Registration-Rubric-Score.md is provided. Do not invent its contents or self-rating.
6. Preserve all assessment acceptance criteria exactly as extracted from the original brief. Clearly distinguish brief requirements from additional developer-approved requirements and AI-derived test scenarios.
7. Every requirement must have traceability and evidence. Every behavioural acceptance criterion must have an executed passing test; documentation/scope criteria must have review evidence. No unverified claims.
8. Before implementation, show the corrected checklist and confirm the complete acceptance-criteria coverage. Do not commit or push.

Once the corrected checklist is presented and confirmed complete, we can proceed with Step 1.
```

```text
The checklist is approved, subject to the two final clarifications already provided:

1. Retain a separate explicit test for the original waitlist-priority decision: open seat + eligible waiter + new eligible requester → waiter gets the seat first and requester is waitlisted.
2. Keep the regained-eligibility scenario as an additional test. Document the open-seat policy precisely.

Additionally, ensure the following review checks are included:

* Candidate expectation: inspect existing interfaces, persistence and test conventions before implementation. Since the repository has no starter, document why the minimal service fallback is appropriate.
* Scope: explicitly confirm no separate greenfield, brownfield or ambiguous scenarios are implemented, and no frontend, configurable policy engine or excluded infrastructure is introduced.
* Independent AI review: record what AI contributed, what the developer accepted, modified or rejected, and how changes were independently verified.

Treat the 7–8 hour timebox as suggested guidance, not a mandatory acceptance criterion.
Keep original assessment requirements distinct from developer-approved decisions and AI-derived additional scenarios.
Update the checklist and traceability totals accordingly, then proceed with Step 1 only.
Implement the approved domain classes and domain tests. Update docs/DESIGN.md, docs/REQUIREMENTS_TRACEABILITY.md, docs/DEVELOPMENT_LOG.md and docs/AI_USAGE.md.
Run actual tests and report:

* Changed files
* Test totals and actual execution results
* Requirement coverage status
* Remaining gaps and blocked items
* Suggested commit message

Do not commit, push, merge or modify main without my explicit approval. Maintain strict [unrelated project] isolation.
```

</details>

**Resulting work:** domain classes and 37 domain and concurrency tests; `docs/DESIGN.md` and `docs/REQUIREMENTS_TRACEABILITY.md` created; 38 tests passed. Claude Code also broke the code deliberately in a scratch copy to show the tests could fail.

#### Entries 12 and 13 — Code-quality review and corrections

| Phase | Tool | Purpose |
|-------|------|---------|
| Self-review, bug fixing | Claude Code | Have Claude review its own implementation, then fix every finding. |

<details><summary>Developer prompts — from session history; redaction: project name (entry 13)</summary>

```text
Step 1 report received. Do not commit or push.
Before I approve Step 1, perform a focused code-quality review of the actual implementation.
Review:

1. CourseOffering.java — concurrency, lock boundaries, enrollment/drop sequencing, FIFO promotion, eligibility, and state invariants.
2. All domain tests — check meaningful assertions, coverage of each approved scenario, and whether any test can pass despite incorrect state.
3. CourseOfferingConcurrencyTest.java — check synchronization reliability and flakiness.
4. Learner.java and Course.java — immutability, validation, and thread-safety.
5. EnrollmentResult.java and DropResult.java — completeness and correctness.
6. docs/REQUIREMENTS_TRACEABILITY.md — validate every test mapping and status.
7. docs/AI_USAGE.md and docs/DEVELOPMENT_LOG.md — verify factual accuracy and consistency.

Do not change code during this review.
Report:

* Any bugs or correctness risks.
* Any weak, redundant, or missing tests.
* Any mismatch between implementation, approved decisions, and documentation.
* Exact file/line references for every finding.
* Whether you recommend accepting Step 1 as-is or making corrections.

Do not commit, push, merge or modify main.
```

```text
I approve fixing all findings before accepting Step 1.
Please fix the following:
Code

* F1: Add an atomic offering snapshot containing enrolled learners, waitlisted learners and available seats, captured under one lock. Use it for the upcoming API state query.
* F2: Make prerequisite eligibility checks atomic across multiple prerequisites, or provide a concrete justification if you conclude this is unnecessary.
* F3: Validate EnrollmentResult and DropResult constructors so contradictory outcomes cannot be created.
* F4: Copy Course prerequisite collections before validation, then validate the copied collection.

Tests

* T1: Strengthen TC-D-21 using a vacant seat and an eligible waiter, so it genuinely detects accidental promotion when another waitlisted learner leaves.
* T2: Strengthen TC-C-03 or explicitly document its limitations and ensure the concurrency suite provides reliable evidence of lock correctness.
* T3: Add a test for enrollment where a waiter is promoted and the new requester also gets an available seat.
* T4: Add a test ensuring an already-waitlisted learner receives ALREADY_WAITLISTED even when their prerequisite has since been revoked.
* T5: Extend TC-D-24 so a later promotion cannot evict an already-enrolled learner whose prerequisite was revoked.
* T6: Add direct tests for result-type validation and collection immutability.
* T7: Correct the concurrency test comment.

Traceability

* D1–D5: Correct every mapping and requirement classification.
* Add a requirement ID for the approved Course immutability and non-blank identifier validation decisions.
* Ensure every test ID maps correctly to requirements and every requirement maps to evidence.
* Recalculate all test and requirement totals.

Documentation

* A1–A5: Correct AI usage and development log records, labels, verification claims and stale review references.
* Record this code-quality review as a new AI activity, with the findings, accepted corrections and verification evidence.
* Correct the concurrency mutation-testing statement to say precisely which tests failed and which passed.
* Ensure developer verification remains pending until I personally review and confirm it.

Final requirements

1. Update the relevant documentation in the same working change.
2. Run the complete Maven suite and report actual results.
3. Re-run relevant mutation checks, including the improved TC-C-03.
4. Report changed files, test totals, requirement coverage, review findings resolved and remaining limitations.
5. Show the updated traceability totals and suggested commit message.
6. Do not commit, push, merge or modify main.
7. Maintain strict [unrelated project] isolation.

Do not begin API implementation yet. I want to review and approve the corrected Step 1 first.
```

</details>

**Resulting work:** Claude Code reported 21 findings in its own work, including one test that could not fail for what it claimed and a concurrency test that still passed with the lock removed. All were fixed: an atomic snapshot, a per-learner lock, validated result types, and seven new or stronger tests. 45 tests passed.

#### Entries 14 to 16 — Acceptance review, commit, push

| Phase | Tool | Purpose |
|-------|------|---------|
| Verification, version control | Claude Code | Final pre-acceptance check of Step 1, then commit and push. |

<details><summary>Developer prompts — from session history; unaltered</summary>

```text
Prepare Step 1 for final acceptance review. Do NOT commit, push, merge, or modify main.

1. Confirm the current HEAD, branch, and complete working-tree status.
2. Confirm all 45 tests were run against the current corrected Step 1 files. Run `./mvnw -B test` once more and report the exact result.
3. Verify that all Step 1 code, tests, and documentation changes are accurately reflected in:
   * docs/DEVELOPMENT_LOG.md
   * docs/AI_USAGE.md
   * docs/DESIGN.md
   * docs/REQUIREMENTS_TRACEABILITY.md
   * README.md
4. Report the final requirements coverage, test coverage, review-check status, and all remaining blockers/limitations.
5. Verify the complete diff against Step 0. Identify any unrelated or accidental changes.
6. Confirm no changes were made to main and no API implementation was started.
7. Provide the final Step 1 acceptance summary and recommended commit message.

Do not modify files unless you find a factual documentation discrepancy. If you correct one, explain exactly what changed.
Do not stage, commit, push, or merge anything. Stop after presenting the acceptance report.
```

```text
I explicitly authorize you to stage and commit the accepted Step 1 changes only.
Requirements:

* Commit only the 17 reviewed Step 1 files.
* Do not modify main.
* Do not push yet.
* Preserve Keerthi Joshi as commit author and Claude as co-author using the previously approved identity format.
* Use the approved Step 1 commit message.
* After committing, report the commit hash, author/co-author, branch, and working-tree status.
* Do not perform any additional changes or commits.
```

```text
I explicitly authorize pushing the existing Step 1 commit `2cc6d95709b15f13169dec5e5a0f8c7abbf71232` to the remote branch `feature/initial-setup`.
Requirements:

* Push this exact commit only.
* Do not amend or create another commit.
* Do not modify main or create a main branch.
* Do not merge.
* After pushing, verify the remote branch points to exactly this commit.
* Report the remote branch, commit hash, and verification result.
* Stop after the push and verification.
```

</details>

**Resulting work:** three documentation discrepancies corrected; commit `2cc6d95` pushed and verified.

### Step 2 — service layer and REST API (2026-11-16)

#### Entry 17 — Step 2 design proposal

| Phase | Tool | Purpose |
|-------|------|---------|
| Design (API, service boundary, tests) | Claude Code | Record Step 1 acceptance; get a reviewed API and test design before code. |

<details><summary>Developer prompt — from session history; redaction: project name</summary>

```text
We are beginning Step 2: Service Layer & REST API for the Course Waitlist Engineering Assessment.
IMPORTANT PROJECT RULES:

* Work exclusively in this Course Waitlist repository.
* Never access, reuse, or mention [unrelated project] code/configuration/architecture.
* Do not modify main, merge, commit, or push without my explicit approval.
* Step 1 is accepted, committed, and pushed. Its commit is `2cc6d95709b15f13169dec5e5a0f8c7abbf71232`.
* Preserve the accepted domain model and existing behavior unless a justified issue is identified and approved.
* Do not start implementation yet.

PHASE A — RECORD STEP 1 ACCEPTANCE

1. Record Step 1 acceptance in docs/DEVELOPMENT_LOG.md and docs/AI_USAGE.md.
2. Record the sixth stop-hook occurrence accurately in docs/AI_USAGE.md.
3. Keep historical records accurate; do not rewrite history or claim developer verification I have not performed.
4. Do not create a commit for these documentation changes.

PHASE B — INSPECT AND DESIGN STEP 2
Inspect the current repository, assessment brief, docs/DESIGN.md, docs/REQUIREMENTS_TRACEABILITY.md, and existing domain classes/tests.
Prepare a Step 2 design proposal covering:

1. SERVICE LAYER

* Proposed service boundary and responsibilities.
* How the service uses CourseOffering and Learner.
* How learner/course/offering references are supplied or resolved.
* Avoid unnecessary registries or infrastructure.
* Preserve the one-offering scope and in-memory design.
* Explain how service operations map to existing domain methods.

2. REST API

* Proposed endpoints and HTTP methods.
* Request and response DTOs.
* Validation rules.
* Status codes and error responses using Spring ProblemDetail.
* Duplicate enrollment/waitlist behavior (HTTP 409).
* Prerequisite failures, invalid requests, unknown learner/offering behavior.
* Drop behavior, including waitlist withdrawal and promotion reporting.
* Do not expose internal mutable domain state.

3. TEST PLAN

* Define API and service tests mapped to the remaining TC-A-01 through TC-A-12.
* Resolve provisional TC-A-08 and TC-A-10 based on actual API design.
* Ensure every relevant API acceptance criterion has executable coverage.
* Include negative cases and state-integrity assertions.
* Include integration tests using the existing Spring Boot test setup.
* Avoid adding unnecessary test dependencies.

4. TRACEABILITY

* Update the proposed requirement/test mappings.
* Identify which requirements become covered only after implementation and execution.
* Keep NOT IMPLEMENTED and NOT EXECUTED statuses accurate.
* Include all gaps from Step 1, including REQ-26 rubric dependency.

5. DESIGN DECISIONS

* List any decisions requiring my approval before implementation.
* Do not silently invent behavior where the assessment brief or approved design does not settle it.
* Identify any potential changes to accepted domain code and justify them separately.

6. DOCUMENTATION

* Proposed updates to docs/DESIGN.md, docs/REQUIREMENTS_TRACEABILITY.md, docs/DEVELOPMENT_LOG.md, docs/AI_USAGE.md and README.md.
* Keep documentation proportionate and assessment-focused.

OUTPUT
Present:

* Proposed architecture and endpoint table.
* Request/response examples.
* Error/status code table.
* Service/API test matrix with traceability IDs.
* Decisions requiring approval.
* Risks, assumptions, and remaining gaps.
* Recommended implementation sequence.

STOP after presenting the proposal. Do not implement service/API code, create tests, add dependencies, or commit/push anything.
```

</details>

**Resulting work:** a proposal with six endpoints, `ProblemDetail` errors, an in-memory learner directory, 18 new tests, and eight decisions for approval.

#### Entry 18 — Step 2 decisions and implementation

| Phase | Tool | Purpose |
|-------|------|---------|
| API implementation, testing | Claude Code | Settle the eight decisions and implement Step 2. |

<details><summary>Developer prompt — from session history; redaction: project name</summary>

```text
The Step 2 design is approved with the following decisions:

1. Use the in-memory LearnerDirectory and learner endpoints.
2. Configure the single offering in application.yaml.
3. No offering ID in API paths and no offering registry.
4. Return 200 OK for both ENROLLED and WAITLISTED; distinguish with response status field.
5. Prerequisite failure is 422.
6. Known learner who is neither enrolled nor waitlisted: 409 NOT_ENROLLED. Unknown learner: 404 LEARNER_NOT_FOUND.
7. Keep limitation L1 unchanged.
8. Approve REQ-38, REQ-39, REQ-40 and the proposed additional tests.

Additional instructions:

* Use immutable response DTOs and never serialize domain objects.
* Use atomic CourseOffering.snapshot() for offering state.
* Preserve existing domain invariants and locking.
* Keep TC-A-10 as a design/review check; do not invent an offering registry or artificial endpoint.
* Update test status only after implementation and execution.
* Correct RV-01's stale Entry 10 reference.
* Record Step 1 acceptance and stop-hook occurrence 7 accurately.
* Preserve historical records.
* No main changes, commits, pushes, or merges without explicit authorization.
* Keep [unrelated project] completely isolated.

IMPLEMENT STEP 2 NOW:

1. Implement OfferingProperties/configuration.
2. Implement LearnerDirectory and EnrollmentService.
3. Implement REST controllers, DTOs, validation and ProblemDetail handling.
4. Implement service and API tests from the approved matrix.
5. Update all required documentation and traceability.
6. Run the full Maven suite and relevant fault-injection checks.
7. Review your own implementation and report findings.

Do not commit, push, merge, or modify main.
Stop after presenting the implementation report for my review.
```

</details>

**Resulting work:** config, service, and API layers; 23 new tests; 68 passed. Decisions 3, 4, and 6 changed Claude's recommendations. Fault injection showed the first HTTP concurrency test could not detect a disabled lock, so it was rewritten.

#### Entries 19 and 20 — Step 2 review corrections

| Phase | Tool | Purpose |
|-------|------|---------|
| Bug fixing, testing | Claude Code | Close the gaps found in review of the Step 2 report. |

<details><summary>Developer prompts — from session history; unaltered</summary>

```text
Step 2 review: add the following final corrections before acceptance preparation.

1. Add a concurrent duplicate-learner-registration test for S2-8:
   * Multiple concurrent registrations using the same ID.
   * Exactly one succeeds.
   * All others receive the duplicate-registration result.
   * Exactly one learner remains in the directory.
   * The successful learner's original data is preserved.
   * No unexpected exceptions or corrupted state.
   * Use a bounded timeout and repeat the concurrency test to check stability.
2. Review L11:
   * Reject JSON numeric learner IDs rather than silently converting them into strings.
   * Add an executable API test.
   * Keep the validation approach simple and consistent with existing request validation.
   * If the proposed fix would require disproportionate complexity, explain and leave the limitation documented.
3. Update docs and traceability:
   * Add the new test and requirement mapping if applicable.
   * Update counts only after execution.
   * Record this review activity accurately.
   * Add stop-hook occurrence 8 accurately.
   * Preserve historical records.
   * Do not claim developer verification.
4. Run the complete Maven test suite and repeat concurrency tests.
5. Re-run the traceability checker and relevant mutation checks.
6. Review the full Step 2 diff and identify any remaining issues.
7. Report final test results, coverage, findings, limitations, and changed files.

Do not commit, push, merge, or modify main.
Do not begin Step 3.
Stop after presenting the corrected Step 2 acceptance report.
```

```text
Step 2 review decisions and final cleanup:

1. S2-12 — Defensive copying

* Update RegisterLearnerRequest to defensively copy its completed-course list when constructed.
* Ensure null handling remains consistent with existing request validation.
* Add a focused test proving mutation of the original input list cannot change the constructed request.
* Avoid unrelated DTO changes.

2. S2-13 — LearnerDirectory.size()

* Keep the method if it is a harmless, useful directory operation.
* Prefer package-private visibility if it is exclusively test infrastructure and that change is straightforward.
* Do not introduce reflection or complicated test infrastructure.
* Explain the final choice in the report.

3. Documentation:

* Update docs/AI_USAGE.md for stop-hook occurrence 9.
* Update DEVELOPMENT_LOG.md and traceability only where necessary.
* Preserve all historical entries and accurately distinguish AI verification from developer verification.
* Do not mark developer verification as complete.

4. Verification:

* Run the complete Maven test suite.
* Repeat the concurrency tests.
* Re-run the traceability checker.
* Confirm no unrelated files or changes.
* Report the final diff statistics and changed files.

5. Stop:

* Do not commit.
* Do not push.
* Do not merge or modify main.
* Do not begin Step 3.
* Present the final Step 2 acceptance report and stop.
```

</details>

**Resulting work:** concurrent-registration tests at service and HTTP level; strict JSON so numeric ids are rejected; a defensive copy in the registration request; 72 tests passed.

#### Entries 21 and 22 — Step 2 commit and push

| Phase | Tool | Purpose |
|-------|------|---------|
| Version control | Claude Code | Commit and push Step 2. |

<details><summary>Developer prompts — from session history; unaltered</summary>

```text
I approve committing Step 2.
Before committing:

1. Confirm the working tree contains exactly the reviewed Step 2 changes.
2. Confirm no changes are staged unexpectedly.
3. Stage the intended 33 Step 2 files.
4. Create the commit using the approved author and committer conventions:
   * Author: Keerthi Joshi
   * Author email: [85629965+keejoshi@users.noreply.github.com](mailto:85629965+keejoshi@users.noreply.github.com)
   * Committer: Claude
   * Include the Claude co-author trailer.
5. Use the approved commit message:

Step 2: Service layer and REST API for the single course offering
Add the configured offering, an in-memory learner directory, the enrollment
service, and REST endpoints for enrollment, drop, offering state and learner
completions, with ProblemDetail errors and strict request validation. Add 27
service and API tests, including concurrency over HTTP. Update the design
specification, traceability matrix, development log, AI usage record and
README. Includes the corrections from the Step 2 reviews.

6. Verify the resulting commit, parent commit, author, committer and message.
7. Do not push yet.
8. Report the commit hash and exact changed files.

Do not merge into main or begin Step 3.
```

```text
I approve pushing the verified Step 2 commit `33e081edb938beeed7b4e3ca643dc75ce9bb23bd` to `origin/feature/initial-setup`.
Push only this branch. Do not merge into main or modify main.
After pushing, verify:

1. Remote feature branch points to exactly `33e081edb938beeed7b4e3ca643dc75ce9bb23bd`.
2. Local and remote branch hashes match.
3. Working tree is clean.
4. No other branch was modified.

Report the verification results and GitHub link.
Do not start Step 3.
```

</details>

**Resulting work:** commit `33e081e` pushed and verified.

### Step 3 — verification and documentation (2026-11-16 to 2026-11-17)

#### Entries 23 and 24 — Step 3 planning and execution

| Phase | Tool | Purpose |
|-------|------|---------|
| Planning, final verification, documentation | Claude Code | Identify what remained against the brief, then complete it without new code. |

<details><summary>Developer prompts — from session history; redaction: project name (entry 23)</summary>

```text
We are beginning Step 3 of Keerthi Joshi's Course Waitlist Engineering Assessment.
Repository:
https://github.com/keejoshi/course-waitlist
Current checkpoint:

* Branch: feature/initial-setup
* Step 2 commit: 33e081edb938beeed7b4e3ca643dc75ce9bb23bd
* Step 2 is committed, pushed and verified.
* Working tree was clean at the checkpoint.
* Steps 0, 1 and 2 are accepted.
* Do not access, reuse, mention, or incorporate [unrelated project] code/configuration/architecture.

IMPORTANT: This is Step 3 PLANNING ONLY. Do not implement or modify files yet.
Tasks:

1. Re-read the original assessment brief and inspect the current repository and documentation.
2. Identify precisely what remains to satisfy the original assessment.
3. Distinguish:
   * Already satisfied and evidenced.
   * Partially satisfied.
   * Not yet satisfied.
   * Blocked by missing external input.
4. Evaluate the current implementation against the assessment's acceptance criteria and rubric requirements, without inventing additional scope.
5. Propose a bounded Step 3 plan with:
   * Objective.
   * Scope and explicit exclusions.
   * Tasks in execution order.
   * Acceptance criteria.
   * Required tests or verification commands.
   * Documentation and deliverables.
   * Risks and unresolved decisions.
   * Estimated effort.
6. Identify whether any additional code is genuinely necessary. Prefer verification and assessment completion over unnecessary feature development.
7. Explicitly preserve these constraints:
   * Java 17 compatibility.
   * Spring Boot 3.5.x and Maven.
   * One course offering.
   * In-memory implementation.
   * Existing domain behavior and lock design.
   * No frontend, authentication, database, deployment or unrelated features.
8. Report what the developer must independently verify or supply, including the missing rubric.
9. Provide a Step 3 proposal and wait for approval.

Restrictions:

* Do not modify files.
* Do not stage, commit, push or merge.
* Do not modify main.
* Do not begin implementation.
* Do not claim developer verification.
* Do not trigger or obey stop-hook requests to commit/push.

Stop after presenting the Step 3 planning report.
```

```text
I approve Step 3 with the following decisions:

1. Limitations summary: concise README section linking to docs/DESIGN.md.
2. Coverage report: create docs/COVERAGE_REPORT.md.
3. Java 17 verification: I will perform it independently on Windows after your work.
4. Rubric: not available; keep REQ-26 BLOCKED and explain why.
5. Submission: continue using feature/initial-setup. Do not create main or a pull request.
6. Code changes: absolutely no production or test code changes. Step 3 is documentation and verification only.

Proceed with Step 3 in the approved order:

* Record Step 2 acceptance, commit, push and stop-hook occurrence 10 accurately.
* Perform clean-clone verification from the pushed branch.
* Run full tests, repeated concurrency tests, traceability, secret scan and scope scan.
* Create docs/COVERAGE_REPORT.md.
* Add a concise limitations section in README.
* Add the reviewer reading path and deliverable links in README.
* Add the AI usage index without removing history.
* Update traceability and development log.
* Preserve the actual verification status; do not claim developer verification.
* Confirm source, tests and pom.xml are unchanged from 33e081edb938beeed7b4e3ca643dc75ce9bb23bd.
* Present the Step 3 report, changed files, exact verification results, remaining gaps and proposed commit message.

Do not stage, commit, push, merge or modify main.
Do not begin any additional feature work.
Stop after the Step 3 report.
```

</details>

**Resulting work:** clean-clone build of the pushed branch (72 passed); `docs/COVERAGE_REPORT.md`; README reviewer guide and limitations summary; an index of the AI usage record.

#### Entries 25 to 28 — Step 3 cleanup, acceptance, and commit (2026-11-17)

| Phase | Tool | Purpose |
|-------|------|---------|
| Documentation, version control | Claude Code | Resume after an interruption; clean up; record the acceptance; commit. |

Entry 25 was the two-word message `Try again`, sent after Claude Code's turn was cut off before it reported.

Entry 27 is the message that, by the developer's later statement (entry 28), was **ChatGPT's acceptance based on Claude's reported results**. It is the one message in this file known to carry ChatGPT's decision. See section 3, G9.

<details><summary>Developer prompts — from session history; unaltered</summary>

```text
Step 3 final review: conditionally accepted.
Please perform only the following documentation cleanup:

1. Record the November 17, 2026 confirmation verification separately from the November 16 verification, with the actual checks performed on each date. Preserve historical entries.
2. Record stop-hook occurrence 11 accurately in docs/AI_USAGE.md.
3. Recheck the AI usage index:
   * 51 accepted
   * 5 modified
   * 2 rejected
   * 43 without an explicit recorded decision
Confirm these counts against the underlying entries. Do not invent historical decisions.
4. Confirm that docs/COVERAGE_REPORT.md and docs/REQUIREMENTS_TRACEABILITY.md agree on all requirement statuses, test totals and unresolved gaps.
5. Confirm that:
   * REQ-26 remains BLOCKED because the rubric is unavailable.
   * REQ-34 remains PARTIAL because independent developer verification has not been reported.
   * REQ-35 remains PARTIAL until final submission.
   * No Windows/JDK 17 runtime verification is claimed.
   * The clean-clone build is not described as an empty-cache build.
6. Run the traceability checker and confirm no source code, test code, pom.xml or Maven wrapper changes.
7. Report the final changed files and any remaining issues.

Do not stage, commit, push, merge, create main or begin further development.
Stop after the final corrected Step 3 report.
```

```text
Step 3 is accepted.
Please perform the final documentation-only housekeeping:

1. Record stop-hook occurrence 12 accurately in docs/AI_USAGE.md. State that it requested commit/push after the Step 3 report and that it was declined pending explicit approval.
2. Record the Step 3 acceptance decision in docs/DEVELOPMENT_LOG.md and docs/AI_USAGE.md, accurately distinguishing assistant acceptance from developer verification.
3. Update the AI usage index totals if necessary. Preserve all historical entries and decision classifications.
4. Run the traceability checker and verify that no source, test, pom.xml or Maven wrapper changes exist.
5. Report the final diff statistics and changed files.

Do not stage or commit yet.
Do not push, merge or modify main.
Do not begin another development phase.
Stop and wait for explicit commit authorization.
```

```text
I explicitly authorize the following actions:

1. Record stop-hook occurrence 13 in docs/AI_USAGE.md.
   * Record that it requested a commit and push.
   * Record that it was declined pending explicit authorization.
   * Preserve all earlier stop-hook records.
2. Correct the Step 3 acceptance wording:
   * The Step 3 acceptance was ChatGPT's acceptance based on Claude's reported results.
   * It was not independent verification by the developer.
   * It was not a separate acceptance statement from the developer.
   * Keep REQ-34 PARTIAL.
   * Preserve all historical records accurately.
3. Verify the final documentation changes and stage ONLY these six Step 3 files:
   * README.md
   * docs/AI_USAGE.md
   * docs/DEVELOPMENT_LOG.md
   * docs/REQUIREMENTS_TRACEABILITY.md
   * docs/DESIGN.md
   * docs/COVERAGE_REPORT.md
4. Commit locally using the approved Step 3 commit message:

Step 3: Final verification, coverage report and reviewer documentation
Add the assessment coverage report, a README reviewer guide with the
deliverables and a concise limitations summary, and an index for the AI
usage record. Record Step 2 acceptance and the Step 3 verification results
in the development log and traceability matrix. Documentation only: no
production code, test code or build file is changed.

5. Verify and report:
   * Commit hash and parent.
   * Author and committer.
   * Exact changed files.
   * Commit statistics.
   * Clean working tree.
   * Source, tests, pom.xml and Maven wrapper unchanged.

IMPORTANT:

* Commit locally only.
* DO NOT push.
* DO NOT merge.
* DO NOT create or modify main.
* DO NOT start another development phase.
* Stop after presenting the commit verification report.
```

</details>

**Resulting work:** the two verification dates recorded separately; the index recount matched (51, 5, 2, 43). Claude Code first recorded "Step 3 is accepted" as the developer's acceptance; it was corrected in place, with dated notes, after entry 28. Commit `86ff1fa`.

### Final verification and submission preparation (2026-11-17 onward)

#### Entries 29 to 31 — Push, readiness review, and developer evidence

| Phase | Tool | Purpose |
|-------|------|---------|
| Version control, submission readiness | Claude Code | Push Step 3; check readiness; record the evidence the developer supplied. |

The readiness-review message (entry 30) was sent twice, identically. It is reproduced once.

<details><summary>Developer prompts — from session history; unaltered</summary>

```text
I explicitly authorize pushing the verified Step 3 commit:
86ff1fa836958df3a95355c562bf089be6dd9a55
Push it to:
origin/feature/initial-setup
Before pushing, verify that the local commit is still the expected commit and the working tree is clean.
After pushing, verify:

1. Remote feature branch points to exactly 86ff1fa836958df3a95355c562bf089be6dd9a55.
2. Local and remote branch hashes match.
3. Working tree is clean.
4. No other branch was modified.
5. No main branch was created or modified.

Do not amend the commit.
Do not merge.
Do not start another development phase.
Report the push result and GitHub links.
```

```text
Perform the final GitHub submission-readiness review for Keerthi Joshi's Course Waitlist Engineering Assessment.
Submission method: GitHub repository link.
Repository: https://github.com/keejoshi/course-waitlist
Submission branch: feature/initial-setup
Latest verified commit: 86ff1fa836958df3a95355c562bf089be6dd9a55
Check:

1. All required assessment deliverables exist in the remote branch.
2. README links to all deliverables using valid relative links.
3. README clearly explains prerequisites, setup, running the application and running tests.
4. The evaluator can understand the project using the recommended reading order.
5. The AI usage record accurately describes Claude's and ChatGPT's contributions.
6. The user's Windows/JDK 17 test execution is recorded accurately.
7. The independent code review is recorded accurately, without claiming that the user inspected files they did not inspect.
8. The coverage report and traceability matrix agree.
9. All test results and requirement statuses are accurate.
10. REQ-26 remains BLOCKED if the rubric is unavailable.
11. REQ-34 reflects the evidence now supplied.
12. REQ-35 reflects the actual GitHub submission state.
13. Run the final Maven test suite and report the exact result.
14. Verify the latest remote commit and clean working tree.
15. Check for secrets, broken links and unrelated files.

Do not add features or dependencies.
Do not create main or a pull request.
Do not commit or push without explicit approval.
Do not invent a rubric score.
Do not claim unperformed verification.
Return a final evaluator-facing readiness report, including the exact GitHub submission URL and any remaining blockers. Wait for approval.
```

```text
The missing evidence is now supplied. Please prepare the documentation-only final submission update using the following confirmed information.
Windows/JDK 17 verification

* Platform: Windows 10
* Java: OpenJDK 17.0.21
* Command: `mvnw.cmd -B test`
* Result: BUILD SUCCESS
* Tests run: 72
* Failures: 0
* Errors: 0
* Skipped: 0
* Build time: 44.158 seconds

This was executed independently by the developer and evidenced by a Windows Command Prompt screenshot.
Independent code review
ChatGPT reviewed the following five uploaded files:

* CourseOffering.java
* CourseOfferingTest.java
* DESIGN.md
* Learner.java
* CourseOfferingConcurrencyTest.java

Review conclusion: PASS. No mandatory code changes were identified in the reviewed files. The review covered enrollment invariants, FIFO promotion, prerequisite handling, locking, snapshots, and concurrency tests.
Do not claim that the entire repository or files outside this list were independently reviewed.
ChatGPT contribution
Record ChatGPT's actual contributions:

* Assessment interpretation and requirement decomposition.
* Technical design discussions and documentation of approved decisions.
* Preparation of implementation and review prompts for Claude Code.
* Review of implementation reports and test evidence.
* Review of the five uploaded source/design files.
* Identification of inconsistencies, test gaps, documentation corrections and limitations.
* Final submission planning.

Claude Code implemented the repository changes and executed the reported automated tests. ChatGPT did not independently execute the repository test suite.
REQ-35 and final submission state
The Step 3 documentation changes have already been committed and pushed to `feature/initial-setup`.
Latest verified commit:
`86ff1fa836958df3a95355c562bf089be6dd9a55`
Correct the stale wording that says the Step 3 documents await commit and push. Record the actual state accurately.
Restrictions

* Documentation-only changes.
* No production or test code changes.
* No dependencies or features.
* Preserve all historical records and attribution.
* Keep REQ-26 BLOCKED if the assessment rubric is still unavailable.
* Do not claim verification beyond the evidence supplied.
* Do not create main or a pull request.
* Do not commit or push until I explicitly approve.

First show me the proposed documentation changes and a list of files to be modified. Wait for approval before committing or pushing.
```

</details>

**Resulting work:** Step 3 pushed. The first readiness review reported that the repository is private and that no Windows run or code review had been supplied; Claude Code declined to record evidence it had not received. After entry 31, a draft update recorded the developer's Windows run and ChatGPT's role.

#### Entries 32 to 35 — Review of the final update, and its commit

| Phase | Tool | Purpose |
|-------|------|---------|
| Review, version control | Claude Code | Hand the draft to ChatGPT for review, apply the corrections, then commit and push. |

The request in entry 32 was sent twice. The second time, Claude Code did not copy the named file, because it was the superseded first draft and copying it would have overwritten the corrected one.

<details><summary>Developer prompts — from session history; redaction: file path (entry 32)</summary>

```text
Please make the existing final documentation diff available for download.
The file is currently at:
`[path of the diff file in Claude Code's scratch directory]`
Please:

1. Copy the exact file to the repository's top-level directory as `final-update.diff`.
2. Do not modify the diff contents or any of the five documentation files.
3. Do not stage, commit, or push anything.
4. Provide the downloadable file link or attach the file in this Claude Code session.

This is only to transfer the diff to ChatGPT for review.
```

```text
I reviewed final-update.diff. The update is mostly accepted, but make these three corrections before presenting the final diff again.

1. REQ-34:
   * Keep status PARTIAL.
   * Keep the Windows/JDK 17 developer verification evidence.
   * Keep the five-file ChatGPT review and its limitations.
   * Do not classify ChatGPT's review as the developer's independent code review.
   * Update the coverage report, traceability matrix, review-check totals, development log and all cross-references consistently.
2. REQ-35:
   * Keep status PARTIAL.
   * All previous deliverables are pushed to feature/initial-setup, but the repository is private and the evaluator does not yet have access.
   * This final documentation update is also not committed or pushed yet.
   * Do not describe the assessment as submitted or accessible to the evaluator.
   * Record the remaining actions accurately.
3. Stop-hook history:
   * Add occurrence 14 to the existing table.
   * Record that the automated commit/push request was declined pending explicit developer approval.
   * Reconcile the table count and surrounding text without rewriting historical entries.

Retain all other accepted changes, including the exact Windows test figures, the ChatGPT contribution section, the five-file review, and REQ-26 BLOCKED.
Documentation only. No source code, test code, build files, dependencies, staging, commit or push.
After making these corrections, rerun the traceability and coverage consistency checks and provide the complete revised diff and file-change summary for review.
```

```text
I explicitly approve the final documentation-only Git operations.
Please:

1. Stage ONLY these five files:
   * README.md
   * docs/AI_USAGE.md
   * docs/COVERAGE_REPORT.md
   * docs/DEVELOPMENT_LOG.md
   * docs/REQUIREMENTS_TRACEABILITY.md
2. Verify the staged diff:
   * No source code, test code, build files, Maven wrapper or docs/DESIGN.md changes.
   * Do not stage `final-update.diff` or any other file.
   * Confirm REQ-34 and REQ-35 remain PARTIAL and REQ-26 remains BLOCKED.
3. Commit using the previously proposed message:

Final submission update: developer verification and AI attribution

4. Push the commit to `origin/feature/initial-setup`.
5. Verify and report:
   * New commit hash.
   * Commit parent.
   * Exactly five files changed.
   * Author and committer metadata.
   * Local branch matches remote.
   * Working tree clean.
   * No changes to main and no pull request created.

Do not make any additional edits. If any unexpected file or discrepancy appears, stop and report it instead of proceeding.
```

</details>

**Resulting work:** the review rejected Claude's proposal to raise REQ-34 and REQ-35 from PARTIAL; both stayed PARTIAL. Commit `6d2d633` pushed and verified.

#### Entries 36 and 37 — Rubric investigation and this file (after 2026-11-17)

| Phase | Tool | Purpose |
|-------|------|---------|
| Submission preparation | Claude Code | Look for the missing rubric; then create this file. |

<details><summary>Developer prompts — from session history; redaction: project name (entry 36); entry 37 abridged</summary>

```text
I need you to check whether the original assessment rubric file, `Course-Registration-Rubric-Score.md`, is available anywhere in the current repository, project files, or assessment materials.
The original assessment brief mentions this rubric as a required deliverable for self-assessment, but we have not been provided with its contents. As a result, REQ26 is currently marked BLOCKED in our requirements traceability documentation.
Please do the following:

1. Search the current repository and all accessible project/assessment files for:
   * `Course-Registration-Rubric-Score.md`
   * Any file containing the course registration assessment scoring rubric.
   * Any equivalent rubric document explicitly referenced by the original assessment brief.
2. Check whether the rubric was included in the original assessment materials or is available elsewhere in the repository.
3. If it is missing, do not create, reconstruct, or assume the rubric criteria. Do not replace it with a generic software engineering rubric.
4. Tell me exactly what is missing and what information we need to request from the assessment provider.
5. Draft a short, professional message that I can send to the assessment provider requesting the original rubric file or its contents.

Strict project instructions:

* This is Keerthi Joshi's Course Waitlist Engineering Assessment.
* Do not access, reuse, or mention any [unrelated project] project files, code, configurations, or architecture.
* Do not modify any source code or existing documentation.
* Do not commit, push, merge, or modify any Git branches.
* Do not change REQ26 from BLOCKED unless the actual original rubric is found and reviewed.

For now, this is an investigation and message-drafting task only.
```

The prompt that requested this file is not reproduced in full, to avoid repeating its whole specification here. It began: "I have received a reviewer comment requesting a `prompts.md` file in the root of this GitHub repository." It asked for this structure, for verbatim prompts where records exist and labelled reconstructed summaries where they do not, and for no commit or push before review.

</details>

**Resulting work:** the rubric was not found anywhere. The brief's link to it points to a file on the brief author's own computer, so it was never distributed; REQ-26 stays BLOCKED. Then this file.

---

## 5. How AI-generated suggestions were reviewed and verified

Verification is listed by who performed it.

| Who | What | Evidence |
|-----|------|----------|
| **Claude Code**, on its own output | Every automated test run reported: 72 tests in the final suite, run in the working tree and in fresh clones of the pushed branch; ten-fold repeats of the concurrency tests. | `docs/COVERAGE_REPORT.md`, section 2; development log. |
| **Claude Code**, on its own output | Fault injection: deliberate bugs in a scratch copy of the code (a disabled lock, promotion that ignores eligibility, wrong status codes, and others), to show the tests can fail. Four tests were found too weak this way and strengthened. | `docs/AI_USAGE.md` entries 7, 9, 13, 14; `docs/DESIGN.md` limitations L6 and L10. |
| **Claude Code**, on its own output, at the developer's request | Code-quality reviews of Step 1 and Step 2, reporting findings with file and line references before any fix. | Section 4, entries 12 and 18. |
| **Claude Code** | Scripted checks that the traceability matrix matches the test sources and reports, and that the coverage report agrees with the matrix. | `docs/REQUIREMENTS_TRACEABILITY.md`, section E. |
| **ChatGPT** | Review of Claude's reports and test evidence (general); review of five files, PASS; review of the final documentation diff. | Section 3, G7, G8, G10. ChatGPT did not run the tests. |
| **Developer** | The full test suite on Windows 10 with OpenJDK 17.0.21: `mvnw.cmd -B test`, BUILD SUCCESS, 72 run, 0 failures, 0 errors, 0 skipped, 44.158 seconds. Evidenced by a Command Prompt screenshot. | `docs/COVERAGE_REPORT.md`, section 2.3. |

Claude Code's checks are an AI checking its own work. They show the tests
pass and can fail; they are not independent verification.

---

## 6. Developer decisions, approvals and independent verification

**Decisions that changed or overruled what Claude Code proposed.** All arrived
in the developer's turn of the Claude Code session. Whether ChatGPT shaped any
of them is not recorded.

| When | Claude Code proposed | Decision |
|------|----------------------|----------|
| Step 1 | A drop by a waitlisted learner returns `NOT_ENROLLED`. | Rejected: a waitlisted learner can leave, with a distinct outcome `DROPPED_FROM_WAITLIST` and no promotion. |
| Step 1 | A master checklist of 31 requirements and 48 tests. | Modified: corrections to specific tests, review evidence for documentation requirements, the rubric kept BLOCKED, and brief, developer, and AI-derived items kept distinct. |
| Step 2 | An automated test for an unknown offering (TC-A-10). | Modified: a review check only; no artificial endpoint. |
| Step 2 | 201 for an enrollment and 202 for a waitlisting. | Modified: 200 for both, told apart by a `status` field. |
| Step 2 | 404 for a drop by a learner who holds nothing. | Rejected: 409 `NOT_ENROLLED`. |
| Final update | Raise REQ-34 and REQ-35 from PARTIAL. | Rejected: both stay PARTIAL. |

`docs/AI_USAGE.md` records every decision item by item: 55 accepted, 5
modified, 4 rejected, and 67 with no explicit decision when recorded, across
its 25 entries.

**Approvals.** Every commit and every push was made only on an explicit
instruction naming it (section 4, entries 4, 7, 15, 16, 21, 22, 28, 29, 35).
The environment repeatedly sent automated requests to commit and push; Claude
Code declined each one until such an instruction arrived.

**Independent verification.**

| Evidence | Status |
|----------|--------|
| The developer's Windows/Java 17 test run | Recorded, as reported by the developer. |
| ChatGPT's review of five files | Recorded as a second AI assistant's review, not the developer's. |
| A review of the code by the developer personally | **Not reported.** Requirement REQ-34 is therefore PARTIAL. |
| The Step 3 acceptance | Recorded as ChatGPT's acceptance, not the developer's (section 3, G9). |

---

## 7. Limitations of the prompt-history record

1. **No ChatGPT transcripts.** No ChatGPT prompt or response is available to this record. Section 3 is reconstructed from the developer's statements, and must not be read as ChatGPT's wording. The only ChatGPT wording identified is the acceptance sentence in section 3, G9.
2. **Planning attribution is inferred.** The planning conversations of 2026-11-15 to 2026-11-16 (G1 to G4) are attributed to ChatGPT by inference from the developer's description of ChatGPT's role. The record of those conversations does not name the tool.
3. **Drafting of Claude Code prompts is unknown.** By the developer's statement, ChatGPT prepared implementation and review prompts. For each message in section 4, the record does not say whether the developer or ChatGPT drafted it. Section 4 says "sent by the developer" for every message and makes no other claim.
4. **One reviewer is unidentified.** The message "I reviewed final-update.diff" (entry 33) does not say whether the developer or ChatGPT reviewed the diff.
5. **Alterations to the reproduced prompts.** The prompts in section 4 are reproduced from the session history with three disclosed kinds of alteration: the name of an unrelated project is replaced by `[unrelated project]` (entries 1, 3, 8, 9, 11, 13, 17, 18, 23, 36); references to uploaded files and to Claude Code's scratch directory are replaced by descriptions in square brackets (entries 2 and 32); and the prompt that requested this file is abridged (entry 37). Every block is labelled with the alteration that applies to it, or as unaltered. Nothing else is changed.
6. **Messages left out.** These arrived in the session but are not instructions for the work, and are not reproduced:
   - automated messages from the environment, each asking Claude Code to commit and push. Twenty-one automated messages had been received when this file was last revised on 19 November 2026. The first fifteen are listed in `docs/AI_USAGE.md`, under 'Standing record — automated stop-hook messages'; the sixteenth to twenty-first are not reproduced there or here;
   - notices that the session's worker process had restarted;
   - a note that the developer had reached a usage limit and could continue.
7. **Claude Code's responses are summarised, not reproduced.** The "Resulting work" lines are short descriptions supported by the commits and the existing records. The full responses are not in the repository; `docs/AI_USAGE.md` holds contemporaneous summaries of each one.
8. **Exact times.** Dates are from the session and the commits. Times of individual messages are not recorded. The date of the rubric investigation (entry 36) is not recorded beyond falling after 2026-11-17.
9. **This file is retrospective.** It was written on 2026-11-19, after the work. It does not change `docs/AI_USAGE.md`, which was written as the work happened and remains the primary record.
