# Course Waitlist

A small Spring Boot service for one bounded course-registration feature:
enrollment, dropping, and FIFO waitlist promotion for a single course offering.

This repository is the submission for the *Focused AI-Assisted Engineering
Assessment: Course Waitlist*. It is deliberately a reliable service slice, not a
full registration platform.

## Status

**Step 2 — service layer and REST API.** The enrollment, drop, and waitlist
rules are implemented as plain Java domain classes and exposed through a small
REST API. All state is in memory.

| Step | Scope | State |
|------|-------|-------|
| 0 | Maven/Spring Boot skeleton, documentation scaffolding, startup test | Done |
| 1 | Domain model (`Course`, `Learner`, `CourseOffering`), domain and concurrency tests, design specification, traceability matrix | Done |
| 2 | Service layer, REST API, service and API tests | Implemented; awaiting review |
| 3 | Final limitations summary, coverage report, rubric self-rating | Not started |

## Scope

From the assessment brief:

- A learner may enroll only once in the offering.
- Enrollment succeeds while capacity remains and the learner meets the prerequisite rule.
- When the offering is full, an eligible learner joins the waitlist exactly once.
- Dropping an enrolled learner opens a seat and promotes the earliest eligible waitlisted learner.
- An ineligible learner is never enrolled or promoted. An ineligible learner on the waitlist is skipped at promotion and keeps their position.
- Invalid requests and repeated drops return clear results without corrupting state.

Out of scope: frontend, course catalog, payments, identity integration,
multi-term registration, deployment, and a database. State is held in
memory, as the brief allows when no starter data model is supplied, and is
lost when the application stops.

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

The application starts on port 8080 and is stopped with `Ctrl+C`.

## Configuration

There is exactly one course offering. It is defined in
`src/main/resources/application.yaml`:

```yaml
course-waitlist:
  offering:
    id: CS201-A
    course-code: CS201
    course-title: Data Structures
    prerequisite-codes:
      - CS101
    capacity: 2
```

## Using the API

| Method and path | Purpose |
|-----------------|---------|
| `POST /api/learners` | Register a learner with the courses they have completed |
| `PUT /api/learners/{learnerId}/completed-courses/{courseCode}` | Mark a course completed |
| `DELETE /api/learners/{learnerId}/completed-courses/{courseCode}` | Revoke a completion |
| `GET /api/offering` | The offering, its enrolled learners, waitlist, and free seats |
| `POST /api/offering/enrollments` | Request a seat |
| `DELETE /api/offering/enrollments/{learnerId}` | Give up a seat, or leave the waitlist |

A short walk-through with the default configuration (two seats, prerequisite
`CS101`). Start the application, then in another terminal register three
eligible learners:

```bash
curl -X POST localhost:8080/api/learners -H 'Content-Type: application/json' -d '{"id":"L1","name":"Asha","completedCourseCodes":["CS101"]}'
```

```bash
curl -X POST localhost:8080/api/learners -H 'Content-Type: application/json' -d '{"id":"L2","name":"Ben","completedCourseCodes":["CS101"]}'
```

```bash
curl -X POST localhost:8080/api/learners -H 'Content-Type: application/json' -d '{"id":"L3","name":"Cy","completedCourseCodes":["CS101"]}'
```

Enroll all three. The first two are `ENROLLED`; the third is `WAITLISTED`:

```bash
curl -X POST localhost:8080/api/offering/enrollments -H 'Content-Type: application/json' -d '{"learnerId":"L1"}'
```

```bash
curl -X POST localhost:8080/api/offering/enrollments -H 'Content-Type: application/json' -d '{"learnerId":"L2"}'
```

```bash
curl -X POST localhost:8080/api/offering/enrollments -H 'Content-Type: application/json' -d '{"learnerId":"L3"}'
```

Look at the offering:

```bash
curl localhost:8080/api/offering
```

Drop the first learner. The response reports that `L3` was promoted:

```bash
curl -X DELETE localhost:8080/api/offering/enrollments/L1
```

Responses:

- Enroll returns 200 with `"status": "ENROLLED"` or `"WAITLISTED"`.
- Drop returns 200 with `"status": "DROPPED"` or `"DROPPED_FROM_WAITLIST"` and the learners promoted, if any.
- Errors are `application/problem+json` documents with a `code` field: 409 `ALREADY_ENROLLED`, `ALREADY_WAITLISTED`, `NOT_ENROLLED`, or `LEARNER_ALREADY_EXISTS`; 422 `PREREQUISITES_NOT_MET`; 404 `LEARNER_NOT_FOUND`; 400 `INVALID_REQUEST`.
- Ids and names must be JSON strings; a number such as `{"learnerId": 5}` is rejected with 400.

The full endpoint, body, and error reference is in
[docs/DESIGN.md](docs/DESIGN.md), section 7.

## Project structure

```
.
├── pom.xml
├── mvnw, mvnw.cmd, .mvn/        Maven wrapper
├── docs/
│   ├── DESIGN.md                      Design specification, policies, limitations
│   ├── REQUIREMENTS_TRACEABILITY.md   Requirement and test checklist, coverage summary
│   ├── DEVELOPMENT_LOG.md             Step-by-step record of what was done and verified
│   └── AI_USAGE.md                    Prompts, AI output summaries, developer decisions
└── src/
    ├── main/java/com/assessment/coursewaitlist/
    │   ├── CourseWaitlistApplication.java
    │   ├── domain/                    Course, Learner, CourseOffering, snapshot and result types
    │   ├── config/                    The single offering, built from application.yaml
    │   ├── service/                   LearnerDirectory, EnrollmentService
    │   └── api/                       Controllers, request/response records, error handling
    ├── main/resources/application.yaml
    └── test/java/com/assessment/coursewaitlist/
        ├── CourseWaitlistApplicationTests.java
        ├── domain/                    Domain and concurrency tests
        ├── config/                    Configuration test
        ├── service/                   Service tests
        └── api/                       API tests, including concurrency over HTTP
```

## Documentation

- [docs/DESIGN.md](docs/DESIGN.md) — design specification: boundary, assumptions and policies, domain model, invariants, promotion rule, concurrency, service layer and REST API, limitations
- [docs/REQUIREMENTS_TRACEABILITY.md](docs/REQUIREMENTS_TRACEABILITY.md) — every requirement and test with its status, and the coverage summary
- [docs/DEVELOPMENT_LOG.md](docs/DEVELOPMENT_LOG.md) — chronological development record and documentation rules
- [docs/AI_USAGE.md](docs/AI_USAGE.md) — AI prompt and use record
