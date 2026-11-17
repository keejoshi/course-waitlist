# Assessment Coverage Report

Final test and acceptance coverage for the *Focused AI-Assisted Engineering
Assessment: Course Waitlist*. This report summarises the evidence; the
row-by-row matrix is in [REQUIREMENTS_TRACEABILITY.md](REQUIREMENTS_TRACEABILITY.md).

**Prepared:** 2026-11-16, in Step 3. **Confirmed:** 2026-11-17 (section 2.2).
**Final update:** 2026-11-17, adding the developer's Windows/Java 17 run and
the second-assistant review (sections 2.3 and 2.4).
**Code state reported on:** commit `33e081edb938beeed7b4e3ca643dc75ce9bb23bd`
on branch `feature/initial-setup`. Step 3 and the final update changed
documents only; `pom.xml` and everything under `src/` are identical to that
commit. The latest pushed commit when this update was written is
`86ff1fa836958df3a95355c562bf089be6dd9a55`.

## 1. Summary

| Question | Answer |
|----------|--------|
| Are all behavioural acceptance criteria (AC-1 to AC-6) covered by tests that were run and passed? | Yes. |
| How many automated tests exist, and how many pass? | 72 exist; 72 passed; 0 failed; 0 skipped. |
| Is the AI use record (AC-7) complete? | Complete for Claude Code: prompts, output summaries, decisions, and verification. For ChatGPT it records the developer's statement of what ChatGPT contributed, but no ChatGPT prompts or outputs. |
| Has the work been independently verified? | In part. The developer ran the full test suite on Windows 10 with OpenJDK 17.0.21: 72 passed. ChatGPT, a second AI assistant, reviewed five files and identified no mandatory code change. No review of the code by the developer personally is reported, and the rest of the repository was not independently reviewed. See sections 2.3, 2.4, and 8. |
| Is anything blocked? | Yes: the rubric self-rating. The rubric file is not available. |
| Has the assessment been submitted? | Not yet, as of 2026-11-17. Steps 0 to 3 are pushed, but the repository is private, the evaluator has no access yet, and this final update is not yet committed or pushed. See section 8. |

## 2. How this was verified

Sections 2.1 and 2.2 were run by Claude Code in a Linux cloud container with
OpenJDK 21.0.12 and the Maven wrapper (Maven 3.10.0). The two dates are
recorded separately because different checks were performed on each. Section
2.3 is the developer's own run, and section 2.4 is a review by a second AI
assistant; both are recorded as the developer reported them.

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

**Repeated later on 2026-11-17, after the Step 3 commit was pushed:** a fresh
clone of `feature/initial-setup` from GitHub at
`86ff1fa836958df3a95355c562bf089be6dd9a55` (54 tracked files), then
`./mvnw -B test`: `Tests run: 72, Failures: 0, Errors: 0, Skipped: 0`; BUILD
SUCCESS. The existing Maven dependency cache was used. In that clone: the
traceability check found no mismatch, all 11 relative links in the README and
documents resolved, and a scan found no credential-like string, no email
address, and no file other than source, tests, documents, the build file, and
the Maven wrapper.

### 2.3 Verification by the developer: Windows and Java 17

Reported by the developer on 2026-11-17. Label: **Verified by developer.**

| Item | As reported |
|------|-------------|
| Platform | Windows 10 |
| Java | OpenJDK 17.0.21 |
| Command | `mvnw.cmd -B test` |
| Result | BUILD SUCCESS |
| Tests run | 72 |
| Failures | 0 |
| Errors | 0 |
| Skipped | 0 |
| Build time | 44.158 seconds |

The developer states that this was executed independently by the developer
and is evidenced by a Windows Command Prompt screenshot. Claude Code has not
seen the screenshot and records the figures as given. The date of the run and
the commit that was checked out were not stated; the test count of 72 matches
commits `33e081e` and `86ff1fa`, which have identical code.

What this covers: the build and the full automated suite on a Java 17 runtime,
through the Windows wrapper script. What it does not cover: starting the
application on Windows, or the `curl` walk-through there; neither was
reported.

### 2.4 Review by a second AI assistant

Reported by the developer on 2026-11-17. This is a review by ChatGPT, not by
the developer personally, and not by Claude Code.

| Item | As reported |
|------|-------------|
| Reviewer | ChatGPT |
| Files reviewed (five, uploaded to it) | `CourseOffering.java`, `CourseOfferingTest.java`, `DESIGN.md`, `Learner.java`, `CourseOfferingConcurrencyTest.java` |
| Areas covered | Enrollment invariants, FIFO promotion, prerequisite handling, locking, snapshots, and concurrency tests |
| Conclusion | PASS. No mandatory code changes were identified in the reviewed files. |

Limits, as stated by the developer or following directly from the above:

- Only these five files were reviewed. The other 37 Java files, including the whole service and API layer and their tests, the README and the other four documents, and the build file were not independently reviewed.
- ChatGPT did not execute the repository's test suite.
- The version of the files uploaded was not stated.
- No statement has been made that the developer personally inspected these or any other files.

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
| AC-7 | "Use AI for at least one engineering activity. Record the prompts used, relevant AI response or summary, whether each suggestion was accepted, edited, or rejected, and how the resulting work was verified." | Review of [AI_USAGE.md](AI_USAGE.md) | Evidenced by review for Claude Code. ChatGPT's contribution is recorded as the developer described it, without its prompts or outputs; see section 8 |

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
| RV-03 | Setup, run, and test instructions present and run | Covered. Tests run on Linux by Claude Code and on Windows by the developer; the application was started on Linux only |
| RV-04 | Limitations written up | Covered |
| RV-05 | One offering, in-memory, no excluded items | Evidenced to date |
| RV-06 | No credentials or secrets | Evidenced to date |
| RV-07 | Every AC-6 scenario has a passing test | Covered |
| RV-08 | Rubric self-rating | **Blocked** |
| RV-09 | Starter inspection and fallback justification | Covered |
| RV-10 | No extra scenarios, frontend, policy engine, or infrastructure | Evidenced to date |
| RV-11 | Independent review of AI output | **Partial**. The developer's test run and ChatGPT's five-file review are recorded; no code review by the developer is reported |
| RV-12 | Deliverables submitted together | **Partial**. Not yet submitted: see section 8 |
| RV-13 | Single offering, no offering id, no registry | Covered |
| RV-14 | No domain object serialized by the API | Covered |
| RV-15 | Builds and passes from a clean clone | Covered for commits `33e081e` and `86ff1fa` |

## 7. Deliverables required by the brief

| Deliverable | Where | State |
|-------------|-------|-------|
| Runnable code and focused automated tests | `src/` | Present; 72 tests pass |
| Design specification | [DESIGN.md](DESIGN.md) | Present |
| AI prompt and use record | [AI_USAGE.md](AI_USAGE.md) | Present |
| Setup and run instructions, with test commands | [README.md](../README.md) | Present |
| Summary of limitations and follow-up risks | [README.md](../README.md), "Limitations and follow-up risks"; detail in [DESIGN.md](DESIGN.md) section 8 | Present |
| Rubric self-rating | — | **Blocked**: rubric file not available |

## 8. Blockers, partial items, and limits of the evidence

State as of 2026-11-17, when this section was written.

### Blocked

| Item | Status | Detail |
|------|--------|--------|
| Rubric self-rating (REQ-26) | **Blocked** | The brief refers to an AI-Assisted scorecard in `Course-Registration-Rubric-Score.md`. The file was not supplied with the brief and the developer has confirmed it is not available. No rating has been written, and the rubric's criteria have not been guessed. |

### Partial

| Item | Status | Detail |
|------|--------|--------|
| Independent review and verification (REQ-34) | **Partial** | The brief asks the candidate to "independently review and verify any AI-generated suggestion before relying on it". **Verification is evidenced:** the developer ran the full test suite independently on Windows 10 with OpenJDK 17.0.21, 72 passed (section 2.3). **Review is not:** the only review of the code other than Claude Code's own is ChatGPT's review of five files (section 2.4). That is a second AI assistant's review. It is not the developer's independent code review, and no review of the code by the developer personally is reported. The other files were not independently reviewed at all. |
| Final submission (REQ-35) | **Partial** | Not yet submitted. All deliverables up to and including Step 3 are pushed to `feature/initial-setup` (latest verified commit `86ff1fa836958df3a95355c562bf089be6dd9a55`). But the repository is private and the evaluator does not yet have access, and this final documentation update is not yet committed or pushed. Remaining actions, all on the developer's decision: approve, commit, and push this update; give the evaluator access to the repository or make it public; send the evaluator the link to the branch. |

### Limits of the evidence

Nothing below is a failed check. Each row says how far a piece of evidence
reaches.

| Subject | What is evidenced | What is not |
|---------|-------------------|-------------|
| Attribution of decisions | Every prompt in the AI usage record was sent in the developer's turn of the Claude Code session. The developer states that ChatGPT prepared implementation and review prompts and reviewed the reports. | Which individual decisions were the developer's own and which were proposed by ChatGPT is not recorded. ChatGPT's prompts and outputs are not in the repository. |
| ChatGPT's review | Five files reviewed; conclusion PASS; no mandatory code change identified. | ChatGPT did not run the test suite. The version of the files it was given was not stated. |
| Java 17 | Compiled for release 17 (class file version 61). Full suite passed on OpenJDK 17.0.21 on Windows 10, run by the developer. | The running application on Java 17. |
| Windows | `mvnw.cmd -B test` passed, run by the developer. | `mvnw.cmd spring-boot:run` and the `curl` walk-through on Windows. |
| Clean build | The pushed branch builds and passes from a fresh clone, at `33e081e` and at `86ff1fa`. | A build on a machine with an empty Maven dependency cache. |
| GitHub display of commits | Commits are authored as the developer with Claude as committer and co-author. | How GitHub renders this has not been checked. |

Known limitations of the implementation itself are summarised in the README
and listed in full in [DESIGN.md](DESIGN.md), section 8.

### Checks suggested earlier, and their outcome

Step 3 listed five checks for the developer. Outcome as reported:

| Suggested check | Outcome |
|-----------------|---------|
| Windows with JDK 17: `mvnw.cmd test` | Done by the developer: 72 run, 0 failures (section 2.3). |
| `mvnw.cmd spring-boot:run` and the `curl` walk-through | Not reported. |
| Read `CourseOffering.java` against the policies in `DESIGN.md` | Reviewed by ChatGPT; not reported as read by the developer (section 2.4). |
| Read one test class | `CourseOfferingTest` and `CourseOfferingConcurrencyTest` were reviewed by ChatGPT; not reported as read by the developer (section 2.4). |
| Check the commit pages on GitHub | Not reported. |
