# A1-Guo Fan

## PROG2004 Assessment 1 - Hospital Appointment System

## Student

MySCU username: Guo Fan

## Project structure

```text
A1-Guo Fan/
|-- .gitignore
|-- README.md
`-- src/
    |-- Appointment.java
    |-- AppointmentManager.java
    |-- AssignmentOne.java
    |-- ClinicSchedule.java
    |-- GeneralPractitioner.java
    |-- HealthProfessional.java
    |-- Nurse.java
    |-- Patient.java
    |-- Specialist.java
    |-- Surgeon.java
    `-- TimeSlot.java
```

## Compile and run

Run these commands from the project directory:

```powershell
New-Item -ItemType Directory -Force -Path .\build | Out-Null
javac -Xlint:all -d .\build (Get-ChildItem -LiteralPath .\src -Filter *.java | ForEach-Object { $_.FullName })
java -cp .\build AssignmentOne
```

## Schedule

The clinic day is simulated as Monday. Bookable appointments run from `08:00`
to `15:30` in 30-minute intervals. `16:00` is treated as the clinic closing
time and is not a bookable slot.

## Implementation notes

- `HealthProfessional` provides the shared abstract professional model.
- Four concrete professional types demonstrate inheritance and polymorphism.
- `Patient` validates and normalises mobile numbers.
- `Appointment` combines a patient, professional, and validated `TimeSlot`.
- `AppointmentManager` handles booking, conflicts, filtering, sorting, and cancellation.
- `Comparable` and `Collections.sort()` provide ordering.
