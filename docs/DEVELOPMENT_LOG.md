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

## Open items

- Record the agreed enrollment, duplicate-enrollment, FIFO waitlist, and prerequisite policies in a design specification.
- Confirm push permission at the first push.
- Build once on a real JDK 17.
- Run the `mvnw.cmd` commands once on Windows.
