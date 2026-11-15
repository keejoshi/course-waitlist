# Course Waitlist

A small Spring Boot service for one bounded course-registration feature:
enrollment, dropping, and FIFO waitlist promotion for a single course offering.

This repository is the submission for the *Focused AI-Assisted Engineering
Assessment: Course Waitlist*. It is deliberately a reliable service slice, not a
full registration platform.

## Status

**Step 0 — project skeleton.** The application starts and has a startup test.
No enrollment, drop, or waitlist logic is implemented yet.

| Step | Scope | State |
|------|-------|-------|
| 0 | Maven/Spring Boot skeleton, documentation scaffolding, startup test | Done |
| 1+ | Design specification, enrollment, drop, waitlist promotion, tests | Not started |

## Planned scope

From the assessment brief:

- A learner may enroll only once in the offering.
- Enrollment succeeds while capacity remains and the learner meets the prerequisite rule.
- When the offering is full, an eligible learner joins the waitlist exactly once.
- Dropping an enrolled learner opens a seat and promotes the earliest eligible waitlisted learner.
- An ineligible learner is never enrolled or promoted; the skip-or-retain policy will be documented and applied consistently.
- Invalid requests and repeated drops return clear results without corrupting state.

Out of scope: frontend, course catalog, payments, identity integration,
multi-term registration, deployment, and a database. State will be held in
memory, as the brief allows when no starter data model is supplied.

## Tech stack

- Java 17 (source/target level; also builds on newer JDKs)
- Spring Boot 3.5.16
- Maven (wrapper included)
- Spring Web, Bean Validation, Spring Boot Test

## Prerequisites

- JDK 17 or newer on the `PATH` (`java -version`)
- No local Maven install is required; the wrapper downloads Maven on first use

## Setup and run

All commands use the Maven wrapper included in the repository and are run from
the project root.

Run the tests:

```bash
./mvnw test
```

Start the application:

```bash
./mvnw spring-boot:run
```

On Windows (Command Prompt or PowerShell), use the wrapper script `mvnw.cmd`:

```bat
mvnw.cmd test
```

```bat
mvnw.cmd spring-boot:run
```

In PowerShell, prefix the script with `.\` (for example `.\mvnw.cmd test`).

The application starts on port 8080 and is stopped with `Ctrl+C`. It exposes
no endpoints yet.

## Project structure

```
.
├── pom.xml
├── mvnw, mvnw.cmd, .mvn/        Maven wrapper
├── docs/
│   ├── DEVELOPMENT_LOG.md       Step-by-step record of what was done and verified
│   └── AI_USAGE.md              Prompts, AI output summaries, accept/edit/reject decisions
└── src/
    ├── main/java/com/assessment/coursewaitlist/
    │   └── CourseWaitlistApplication.java
    ├── main/resources/application.yaml
    └── test/java/com/assessment/coursewaitlist/
        └── CourseWaitlistApplicationTests.java
```

## Documentation

- [docs/DEVELOPMENT_LOG.md](docs/DEVELOPMENT_LOG.md) — chronological development record and documentation rules
- [docs/AI_USAGE.md](docs/AI_USAGE.md) — AI prompt and use record

The design specification and the limitations summary will be added in later steps.
