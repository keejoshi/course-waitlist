# Course Waitlist

A small Spring Boot service for one bounded course-registration feature:
enrollment, dropping, and FIFO waitlist promotion for a single course offering.

This repository is the submission for the *Focused AI-Assisted Engineering
Assessment: Course Waitlist*. It is deliberately a reliable service slice, not a
full registration platform.

## Status

**Complete, pending the developer's own verification.** The enrollment, drop,
and waitlist rules are implemented as plain Java domain classes and exposed
through a small REST API. All state is in memory. 72 automated tests pass.

| Step | Scope | State |
|------|-------|-------|
| 0 | Maven/Spring Boot skeleton, documentation scaffolding, startup test | Done |
| 1 | Domain model (`Course`, `Learner`, `CourseOffering`), domain and concurrency tests, design specification, traceability matrix | Done |
| 2 | Service layer, REST API, service and API tests | Done |
| 3 | Final verification, coverage report, limitations summary, reviewer guide | Done by the AI; the developer's independent verification is not yet recorded |

Not available: the rubric self-rating. The scorecard file the brief refers to
was not supplied. See the coverage report.

## How to review this submission

Suggested reading order, shortest path first:

1. This README: what the service does, how to run it, and its limitations.
2. [docs/COVERAGE_REPORT.md](docs/COVERAGE_REPORT.md): each acceptance criterion, the tests that cover it, the actual results, and every open gap.
3. [docs/DESIGN.md](docs/DESIGN.md), sections 2 and 5: the prerequisite and waitlist policies, including what happens to an ineligible learner at the front of the queue.
4. `src/main/java/com/assessment/coursewaitlist/domain/CourseOffering.java`: all enrollment rules and the lock, in one class.
5. `src/test/java/com/assessment/coursewaitlist/domain/CourseOfferingTest.java` and `api/OfferingApiTest.java`: the behaviour tests, each named after its checklist id.
6. [docs/AI_USAGE.md](docs/AI_USAGE.md): start with the index at the top.

Deliverables asked for by the assessment brief:

| Deliverable | Location |
|-------------|----------|
| Runnable code and automated tests | `src/` |
| Design specification | [docs/DESIGN.md](docs/DESIGN.md) |
| AI prompt and use record | [docs/AI_USAGE.md](docs/AI_USAGE.md) |
| Setup and run instructions, with test commands | This README, "Setup and run" |
| Summary of limitations and follow-up risks | This README, "Limitations and follow-up risks" |
| Requirement-to-test traceability and coverage | [docs/REQUIREMENTS_TRACEABILITY.md](docs/REQUIREMENTS_TRACEABILITY.md), [docs/COVERAGE_REPORT.md](docs/COVERAGE_REPORT.md) |
| Rubric self-rating | Not available: the rubric file was not supplied |

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

## Limitations and follow-up risks

A concise summary. The full list, with consequences, is in
[docs/DESIGN.md](docs/DESIGN.md), section 8.

- **State is in memory.** Enrollments, the waitlist, and learners are lost on restart, and the service cannot run as more than one instance.
- **A waiting learner who becomes eligible again is not seated immediately.** Marking a completion does not itself fill a vacant seat; the learner is promoted at the next drop or the next valid new enrollment request.
- **Revoking a prerequisite does not remove an enrolled learner.** This is the stated policy, not a defect.
- **No authentication or authorization.** Any caller can register learners, change completions, and enroll or drop anyone. Identity integration is outside the brief's scope.
- **Learners cannot be removed**, and the learner directory has no size limit.
- **Promotion scans the waitlist from the front**, so its cost grows with the number of ineligible learners ahead of the first eligible one.
- **Concurrency tests are probabilistic.** They always pass on correct code, and they failed when locks were deliberately removed, but passing cannot prove the absence of every race. The tests over real HTTP are weaker detectors than the in-process ones.
- **Not yet verified on Java 17 or on Windows.** The code is compiled for Java 17 but has only been run on JDK 21 on Linux.

Follow-up work a production version would need: persistence with database
transactions in place of the in-memory lock, authentication, removal of
learners, and a trigger that offers vacant seats when a waiting learner's
completions change.

## Project structure

```
.
├── pom.xml
├── mvnw, mvnw.cmd, .mvn/        Maven wrapper
├── docs/
│   ├── COVERAGE_REPORT.md             Final acceptance and test coverage report
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

- [docs/COVERAGE_REPORT.md](docs/COVERAGE_REPORT.md) — final acceptance and test coverage report, with every open gap
- [docs/DESIGN.md](docs/DESIGN.md) — design specification: boundary, assumptions and policies, domain model, invariants, promotion rule, concurrency, service layer and REST API, limitations
- [docs/REQUIREMENTS_TRACEABILITY.md](docs/REQUIREMENTS_TRACEABILITY.md) — every requirement and test with its status, and the coverage summary
- [docs/DEVELOPMENT_LOG.md](docs/DEVELOPMENT_LOG.md) — chronological development record and documentation rules
- [docs/AI_USAGE.md](docs/AI_USAGE.md) — AI prompt and use record
