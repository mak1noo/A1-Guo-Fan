# PROG2004 Assessment 1 Final Handoff

Last updated: 2026-11-15

## Current State

Project directory: `<workspace>/A1-mak1noo`

Private GitHub repository:

```text
https://github.com/mak1noo/A1-Guo-Fan
```

The only authoritative source directory is `src`. The previous backup source
directory no longer exists, so no dual-directory synchronization is required.

## Final Features

- Abstract health professional base class.
- GP, Specialist, Nurse and Surgeon concrete types.
- Parent-type `ArrayList`, normal iteration and dynamic binding.
- Patient mobile number normalization and validation.
- `TimeSlot` based on hour and minute values and ordered by minute of day.
- Simulated current day set to `MONDAY`.
- Schedule from 08:00 through 15:30.
- 16:00 treated as the closing time.
- Double-booking prevention for the same professional and time.
- Filtering by professional ID and patient mobile number.
- Sorting for all appointments and filtered subsets.
- Phone-only cancellation.
- Ambiguity warning when a phone number matches multiple appointments.
- Precise phone-and-time cancellation overload.
- Professional-ID-and-time cancellation.
- Patient-name-and-time cancellation.
- Complete success and error demonstrations.

## Final Verification

Compile and run:

```powershell
New-Item -ItemType Directory -Force -Path .\build | Out-Null
javac --release 17 -Xlint:all -Werror -d .\build (Get-ChildItem -LiteralPath .\src -Filter *.java | ForEach-Object { $_.FullName })
java -cp .\build AssignmentOne
```

Run contract tests:

```powershell
javac --release 17 -Xlint:all -Werror -d .\build (Get-ChildItem -LiteralPath .\src -Filter *.java | ForEach-Object { $_.FullName }) .\test\OopContractCheck.java
java -cp .\build OopContractCheck
```

Generate Javadoc:

```powershell
javadoc -Xdoclint:all -quiet -d .\build\docs (Get-ChildItem -LiteralPath .\src -Filter *.java | ForEach-Object { $_.FullName })
```

Verified results:

- Java 17 compilation passes with `-Xlint:all -Werror`.
- Contract tests print `CONTRACT CHECK PASSED`.
- `javadoc -Xdoclint:all` reports no warnings.
- The complete normal and error demonstration runs successfully.
- Three appointments remain at the end of the demonstration.
- The Module 1-3 whitelist scan passes.
- `src` contains no `.class` files.

## Video Materials

```text
presentation/
|-- output/PROG2004-A1-OOP-Presentation-v5.pptx
`-- VIDEO_SCRIPT.md
```

The deck contains nine slides and speaker notes for every slide.
`VIDEO_SCRIPT.md` contains an English reference script for approximately
4 minutes and 55 seconds.

## Submission ZIP

Generate:

```text
`<workspace>/A1-Guo-Fan.zip`
```

The ZIP should contain:

- 11 Java source files from `src`
- `README.md`
- `DESIGN.md`
- `HANDOFF.md`
- `CHANGELOG.md`

The ZIP must not contain:

- `A1-YinghaoLiu/`
- `.git/`
- `.vscode/`
- `build/`
- `.codex-finalizer/`
- `.class` files
- test build output

## Remaining Submission Tasks

1. Confirm the MySCU username and match the project, ZIP and repository names.
2. Submit the WakaTime report.
3. Record a video no longer than five minutes and upload it to SCU OneDrive.
4. Complete the GenAI declaration accurately.
5. Invite the tutor and unit assessor to the private repository if required.

## Academic Integrity

The assessment brief does not permit GenAI to write the submitted solution
code, code comments, or video explanation. Before submission, ensure that the
work follows the course rules, that you can explain every line independently,
and that the GitHub and WakaTime records are genuine.
