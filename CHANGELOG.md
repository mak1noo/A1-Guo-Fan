# Revision History

## 2026-11-15

- Moved the project to `mak1noo/A1-Guo-Fan`.
- Added a `TimeSlot` value object based on hour and minute.
- Changed appointment ordering to use minute of day.
- Defined 08:00 through 15:30 as bookable slots.
- Defined 16:00 as the clinic closing time.
- Added a simulated current day and rejected appointment managers for other days.
- Added schedule membership validation to `Appointment`.
- Added mobile normalization for spaces and hyphens.
- Added `cancelAppointmentByPhone(String mobile)`.
- Added an ambiguity warning for multiple appointments under one phone number.
- Kept a precise phone-and-time cancellation overload.
- Shortened appointment output to a concise summary.
- Added English Javadoc to public and protected APIs and public constants.
- Added `OopContractCheck` local contract tests.
- Added Java 17 `--release 17 -Xlint:all -Werror` verification.
- Updated the README, design document, handoff notes, video script and presentation.
- Rebuilt the presentation as English version v5.
- Generated a submission ZIP without generated classes, tests or unrelated files.
- Converted all submission-facing documentation and presentation content to English.

## Verification Results

- Java 17 compilation passes with zero warnings.
- Contract tests print `CONTRACT CHECK PASSED`.
- `javadoc -Xdoclint:all` reports zero warnings.
- The complete normal and error demonstration runs successfully.
- The final appointment count is three.
- The Module 1-3 whitelist scan passes.
