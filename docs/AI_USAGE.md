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
