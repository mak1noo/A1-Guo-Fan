# PROG2004 Assessment 1 - Hospital Appointment System

## Project Structure

```text
A1-mak1noo/
|-- .gitignore
|-- CHANGELOG.md
|-- DESIGN.md
|-- HANDOFF.md
|-- README.md
|-- presentation/
|   |-- VIDEO_SCRIPT.md
|   `-- output/
|       `-- PROG2004-A1-OOP-Presentation-v5.pptx
|-- src/
|   |-- Appointment.java
|   |-- AppointmentManager.java
|   |-- AssignmentOne.java
|   |-- ClinicSchedule.java
|   |-- GeneralPractitioner.java
|   |-- HealthProfessional.java
|   |-- Nurse.java
|   |-- Patient.java
|   |-- Specialist.java
|   |-- Surgeon.java
|   `-- TimeSlot.java
`-- test/
    `-- OopContractCheck.java
```

## Compile and Run

Run the following commands from the project directory:

```powershell
New-Item -ItemType Directory -Force -Path .\build | Out-Null
javac --release 17 -Xlint:all -Werror -d .\build (Get-ChildItem -LiteralPath .\src -Filter *.java | ForEach-Object { $_.FullName })
java -cp .\build AssignmentOne
```

Run the local contract checks:

```powershell
javac --release 17 -Xlint:all -Werror -d .\build (Get-ChildItem -LiteralPath .\src -Filter *.java | ForEach-Object { $_.FullName }) .\test\OopContractCheck.java
java -cp .\build OopContractCheck
```

## Current Day and Time Slots

- `ClinicSchedule.CURRENT_DAY` defines the simulated current day, `MONDAY`.
- `AppointmentManager` uses the current day by default.
- Any attempt to create a manager for another day is rejected.
- The clinic accepts bookings from 08:00 to 16:00.
- Time slots use 30-minute intervals.
- The final bookable slot starts at 15:30.
- 16:00 is the closing time and not an appointment start time.
- `TimeSlot` stores an hour and a minute and orders slots by minute of day.

## Patient Mobile Numbers

The system first removes spaces and hyphens from a supplied mobile number.
It then checks that the value contains 10 digits and starts with `04`.
The stored value uses continuous digits:

```text
" 0412 345-678 " -> "0412345678"
```

## Phone Cancellation Rules

- `cancelAppointmentByPhone(mobile)` cancels using the mobile number only.
- A single phone match is cancelled directly.
- Multiple phone matches return a warning that asks for the appointment time.
- `cancelAppointmentByPhone(mobile, timeSlot)` resolves the ambiguity.

## Javadoc

Public classes, public methods, protected methods, and public constants use
short English Javadoc comments. Validate the documentation with:

```powershell
javadoc -Xdoclint:all -quiet -d .\build\docs (Get-ChildItem -LiteralPath .\src -Filter *.java | ForEach-Object { $_.FullName })
```

## Private Repository

```text
https://github.com/mak1noo/A1-Guo-Fan
```

## Academic Integrity

The assessment brief does not permit GenAI to write the submitted solution
code, code comments, or video explanation. This repository is a reference
implementation. Before submission, follow the unit rules, understand every
line of code, rewrite the work as required, and complete the GenAI declaration
accurately.
