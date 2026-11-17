# Assessment Coverage Report

Final test and acceptance coverage for the *Focused AI-Assisted Engineering
Assessment: Course Waitlist*. This report summarises the evidence; the
row-by-row matrix is in [REQUIREMENTS_TRACEABILITY.md](REQUIREMENTS_TRACEABILITY.md).

**Prepared:** 2026-11-16, in Step 3. **Confirmed:** 2026-11-17 (section 2.2).
**Code state reported on:** commit `33e081edb938beeed7b4e3ca643dc75ce9bb23bd`
on branch `feature/initial-setup`. Step 3 changed documents only; `pom.xml`
and everything under `src/` are identical to that commit.

## 1. Summary

| Question | Answer |
|----------|--------|
| Are all behavioural acceptance criteria (AC-1 to AC-6) covered by tests that were run and passed? | Yes. |
| How many automated tests exist, and how many pass? | 72 exist; 72 passed; 0 failed; 0 skipped. |
| Is the AI use record (AC-7) complete? | Yes for what the AI did and what the developer decided. |
| Has the developer independently verified the work? | **Not recorded.** Every verification in this report was performed by the AI. See section 8. |
| Is anything blocked? | Yes: the rubric self-rating. The rubric file is not available. |

## 2. How this was verified

Everything in this section was run by the AI in a Linux cloud container with
OpenJDK 21.0.12 and the Maven wrapper (Maven 3.10.0). None of it has been
repeated by the developer yet. The two dates are recorded separately because
different checks were performed on each.

### 2.1 Verification on 2026-11-16

| Check | Command or method | Observed result |
|-------|-------------------|-----------------|
| Clean-clone build | `git clone --branch feature/initial-setup` from GitHub into an empty directory, then `./mvnw -B test` | Clone at `33e081e`, 53 files, no pre-built output. `Tests run: 72, Failures: 0, Errors: 0, Skipped: 0`; BUILD SUCCESS. The machine's existing Maven dependency cache was used, so this proves the repository is self-contained, not that it builds on a machine with no cache. |
| Full suite in the working tree | `./mvnw -B test` | `Tests run: 72, Failures: 0, Errors: 0, Skipped: 0`; BUILD SUCCESS. |
| Stability of concurrency tests | The three test classes containing concurrency tests, run 10 times in a row | 10 of 10 runs passed. |
| Traceability consistency | Script comparing the matrix with the test sources and the surefire reports | No mismatch in either direction between requirements and tests; every test id matches a test method that passed. |
| Secrets scan | Keyword and pattern search over all tracked files | No credential, key, token, or email address. The only keyword matches are the documentation rules themselves and the Maven wrapper scripts' optional `MVNW_PASSWORD` handling. |
| Scope scan | Dependency tree, file types, and searches for excluded technology | Three dependencies (web, validation, test), unchanged since Step 0. 41 Java files, one YAML file, five Markdown files, and the Maven wrapper. No frontend, container, CI, deployment, SQL, persistence, or security code. No collection of offerings anywhere. |
| Java level | `maven.compiler.release` and `javap` on a compiled class | Release 17; class file major version 61 (Java 17). The build has **not** been run on a Java 17 runtime. |
| No code change in Step 3 | `git diff --stat 33e081e -- pom.xml src` | Empty. |

### 2.2 Confirmation on 2026-11-17

Performed after the Step 3 documents were written, and again after the
documentation cleanup the developer requested.

| Check | Command or method | Observed result |
|-------|-------------------|-----------------|
| Full suite in the working tree | `./mvnw -B test`, run twice | Both runs: `Tests run: 72, Failures: 0, Errors: 0, Skipped: 0`; BUILD SUCCESS. |
| Traceability consistency | The same script as on 2026-11-16, run three times, the last after the cleanup edits | No mismatch in either direction each time; 72 of 72 test cases passed. |
| No code change in Step 3 | `git diff --stat 33e081e -- pom.xml src mvnw mvnw.cmd .mvn .gitignore .gitattributes` | Empty: source, tests, `pom.xml`, and the Maven wrapper are unchanged. |
| Document hygiene | `git diff --check HEAD`; search of the documents for claims of developer verification; check that relative links in the README and this report resolve | No whitespace errors; no claim of developer verification; all links resolve. |
| Repository state | `git status`, `git ls-remote origin` | Nothing staged; remote branch `feature/initial-setup` still at `33e081e`; no `main`. |
| Report and matrix agreement | Script comparing this report with the traceability matrix, run before and after the cleanup edits | Requirement counts and statuses, test totals, the test lists for AC-1 to AC-5, review-check statuses, and unresolved gaps agree. |
| AI usage index | Recount of the decision recorded against every item in entries 1 to 18 | 51 accepted, 5 modified, 2 rejected, 43 with no explicit decision when recorded: matches the index. |

**Not repeated on 2026-11-17:** the clean-clone build, the ten repeated runs
of the concurrency tests, the secrets scan, and the scope scan. Their
2026-11-16 results stand; no source, test, or build file has changed since.

## 3. Acceptance criteria

Text is quoted from the assessment brief.

| AC | Criterion | Tests | Result |
|----|-----------|-------|--------|
| AC-1 | "Enrollment succeeds when capacity remains and the learner satisfies the stated prerequisite rule." | TC-D-01, 02, 03, 31; TC-C-01; TC-A-01, 19 | Passed |
| AC-2 | "When full, an eligible learner joins the waitlist once; duplicate enrollment requests do not create duplicate records." | TC-D-04, 05, 06, 25, 31, 35; TC-C-01, 02; TC-A-02, 03, 04, 19 | Passed |
| AC-3 | "Dropping an enrolled learner opens a seat and promotes the earliest eligible waitlisted learner according to the documented eligibility policy." | TC-D-10, 11, 12, 23, 33; TC-C-03; TC-A-06, 12; TC-S-01 | Passed |
| AC-4 | "An ineligible learner is not enrolled or promoted. The documented policy for skipping or retaining that learner is applied consistently." | TC-D-07, 08, 09, 13, 14, 15, 16, 18; TC-A-05, 14; TC-S-04 | Passed |
| AC-5 | "Invalid requests and duplicate drops return clear results without corrupting enrollment, capacity, or waitlist state." | TC-D-19, 20, 22, 26, 27, 32, 36, 37; TC-C-03, 04; TC-A-07, 08, 09, 18; TC-S-02 | Passed |
| AC-6 | "Tests cover available capacity, full capacity, duplicate requests, FIFO promotion, prerequisite failure, and invalid or repeated drops. If the starter uses concurrent requests, preserve its transaction or locking pattern and add a focused concurrency test where practical." | Table below | Passed |
| AC-7 | "Use AI for at least one engineering activity. Record the prompts used, relevant AI response or summary, whether each suggestion was accepted, edited, or rejected, and how the resulting work was verified." | Review of [AI_USAGE.md](AI_USAGE.md) | Evidenced by review; see section 8 for what it does not yet contain |

Scenarios named in AC-6:

| Scenario | Tests | Result |
|----------|-------|--------|
| Available capacity | TC-D-01, 02, 03 | Passed |
| Full capacity | TC-D-04 | Passed |
| Duplicate requests | TC-D-05, 06 | Passed |
| FIFO promotion | TC-D-11, 12 | Passed |
| Prerequisite failure | TC-D-07, 08, 09 | Passed |
| Invalid or repeated drops | TC-D-19, 20, 22 | Passed |
| Concurrency | TC-C-01 to 06; TC-A-19, 20; TC-S-07 | Passed. The brief's clause applies only "if the starter uses concurrent requests"; no starter was supplied, so these tests were added by design decision, not because the clause required them. |

The documented policy tested under AC-3 and AC-4: an ineligible waitlisted
learner is skipped at promotion and keeps their position; if nobody waiting is
eligible the seat stays vacant
([DESIGN.md](DESIGN.md), section 2, W3 and W4).

## 4. Requirements

| Group | Count | Status |
|-------|-------|--------|
| All requirements | 40 | 30 from the brief, 10 decided by the developer |
| Behavioural | 25 | 25 covered by tests that ran and passed |
| Documentation and scope | 15 | 12 covered or evidenced by review; 2 partial (REQ-34, REQ-35); 1 blocked (REQ-26) |

## 5. Tests

| Test class | Level | Tests | Result |
|------------|-------|-------|--------|
| `CourseOfferingTest` | Domain | 34 | Passed |
| `CourseOfferingConcurrencyTest` | Domain, concurrent | 6 | Passed |
| `ResultTypesTest` | Domain | 2 | Passed |
| `LearnerTest` | Domain | 1 | Passed |
| `CourseTest` | Domain | 1 | Passed |
| `EnrollmentServiceTest` | Service | 6 | Passed |
| `OfferingConfigurationTest` | Configuration | 1 | Passed |
| `OfferingApiTest` | API | 14 | Passed |
| `LearnerApiTest` | API | 3 | Passed |
| `RegisterLearnerRequestTest` | API type | 1 | Passed |
| `EnrollmentApiConcurrencyTest` | API over real HTTP, concurrent | 2 | Passed |
| `CourseWaitlistApplicationTests` | Application start | 1 | Passed |
| **Total** | | **72** | **72 passed** |

Every test method carries its checklist id in its name, for example
`tcD17_...`, so the matrix can be checked against the test report.

**Do the tests detect faults?** Because most tests passed the first time they
were run, the AI injected deliberate faults into scratch copies of the code
during Steps 1 and 2 and re-ran the tests: 9 domain faults, 12 service and API
faults, and 5 more after the Step 2 reviews. Each fault was detected by at
least one test, though some concurrency faults were not detected in every
run. Four tests were found too weak, by review or by these checks, and were
strengthened: TC-D-21, TC-C-03, TC-A-19, and TC-S-07. The concurrency tests
remain probabilistic
detectors; the measured detection rates are in [DESIGN.md](DESIGN.md),
limitations L6 and L10. This is not a full mutation analysis.

## 6. Review checks

| Check | Subject | Status |
|-------|---------|--------|
| RV-01 | AI use record complete | Evidenced to date |
| RV-02 | Design specification covers the brief's topics | Covered |
| RV-03 | Setup, run, and test instructions present and run | Covered on Linux; `mvnw.cmd` not run on Windows |
| RV-04 | Limitations written up | Covered |
| RV-05 | One offering, in-memory, no excluded items | Evidenced to date |
| RV-06 | No credentials or secrets | Evidenced to date |
| RV-07 | Every AC-6 scenario has a passing test | Covered |
| RV-08 | Rubric self-rating | **Blocked** |
| RV-09 | Starter inspection and fallback justification | Covered |
| RV-10 | No extra scenarios, frontend, policy engine, or infrastructure | Evidenced to date |
| RV-11 | Independent review of AI output | **Partial** |
| RV-12 | Deliverables submitted together | **Partial** |
| RV-13 | Single offering, no offering id, no registry | Covered |
| RV-14 | No domain object serialized by the API | Covered |
| RV-15 | Builds and passes from a clean clone | Covered for commit `33e081e` |

## 7. Deliverables required by the brief

| Deliverable | Where | State |
|-------------|-------|-------|
| Runnable code and focused automated tests | `src/` | Present; 72 tests pass |
| Design specification | [DESIGN.md](DESIGN.md) | Present |
| AI prompt and use record | [AI_USAGE.md](AI_USAGE.md) | Present |
| Setup and run instructions, with test commands | [README.md](../README.md) | Present |
| Summary of limitations and follow-up risks | [README.md](../README.md), "Limitations and follow-up risks"; detail in [DESIGN.md](DESIGN.md) section 8 | Present |
| Rubric self-rating | — | **Blocked**: rubric file not available |

## 8. Gaps, blockers, and what has not been verified

| Item | Status | Detail |
|------|--------|--------|
| Rubric self-rating (REQ-26) | **Blocked** | The brief refers to an AI-Assisted scorecard in `Course-Registration-Rubric-Score.md`. The file was not supplied with the brief and the developer has confirmed it is not available. No rating has been written, and the rubric's criteria have not been guessed. |
| Independent developer verification (REQ-34) | **Partial** | The brief asks the candidate to "independently review and verify any AI-generated suggestion before relying on it". The record shows the developer directing the work, deciding every policy, rejecting or modifying AI recommendations, and ordering reviews and corrections. It does not yet contain any statement that the developer personally ran the tests or inspected the code. All test runs, reviews, and fault injections were done by the AI on its own output. The acceptance statement received for Step 3 was, by the developer's statement, written by ChatGPT on the basis of Claude's reported results; it is not developer verification and not the developer's own acceptance. |
| Java 17 runtime | Not verified | Compiled for release 17, but only ever run on JDK 21. The developer has stated they will run it on Windows with Java 17 after Step 3. |
| Windows wrapper | Not verified | `mvnw.cmd` is documented but has not been run. Same planned check. |
| Final submission (REQ-35) | **Partial** | All deliverables are in the repository. The Step 3 documents still have to be committed and pushed on the developer's approval. |
| GitHub display of commits | Not verified | Commits are authored as the developer with the AI as committer and co-author; how GitHub renders this has not been checked. |

Known limitations of the implementation itself are summarised in the README
and listed in full in [DESIGN.md](DESIGN.md), section 8.

### Suggested independent checks for the developer

These would close the open items above. Results should be recorded only as
the developer reports them.

1. On Windows with JDK 17: `java -version`, then `mvnw.cmd test`. Expected: 72 tests, 0 failures.
2. `mvnw.cmd spring-boot:run`, then the `curl` walk-through in the README.
3. Read `CourseOffering.java` (about 190 lines) against the policies in `DESIGN.md` section 2.
4. Read one test class, for example `CourseOfferingTest`, and confirm the tests assert what their names say.
5. Open the two commits' pages on GitHub and confirm the author shown.
