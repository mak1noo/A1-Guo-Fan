# PROG2004 Assessment 1 Video Script

Target duration: 4 minutes 55 seconds  
Recommended visuals: alternate between the presentation and VS Code  
Recommended recording: 1440p or 1080p with editor text at least 18pt

> Academic integrity notice: the assessment brief does not permit GenAI to
> write the submitted video explanation. This document is a structure and
> wording reference. Rewrite it in your own language before recording and make
> sure you can explain every line independently.

## Before Recording

1. Open `AssignmentOne.java`, `HealthProfessional.java`,
   `GeneralPractitioner.java`, `ClinicSchedule.java`, `TimeSlot.java`,
   `Appointment.java` and `AppointmentManager.java`.
2. Run `AssignmentOne` once and confirm the console output is complete.
3. Start with the presentation and switch to the editor when discussing code.
4. Keep a natural pace. If the recording approaches five minutes, shorten
   slides 7 and 8 first.

## 0:00-0:15 | Title

Visual: Presentation slide 1.

> Hello, my name is mak1noo. This assignment builds a Java class model for a
> hospital appointment system. I will identify encapsulation, abstraction,
> inheritance and polymorphism in the code and explain why each design choice
> was made.

## 0:15-0:45 | System Structure

Visual: Presentation slide 2.

> `HealthProfessional` is the abstract parent type. It has four concrete
> subtypes: General Practitioner, Specialist, Nurse and Surgeon.
> `Appointment` combines a patient, any health professional and a valid
> `TimeSlot`. `AppointmentManager` stores appointments in an `ArrayList` and
> applies booking rules. The appointment field uses the parent type, so one
> appointment can refer to any professional subtype.

## 0:45-1:25 | Encapsulation

Visual: Presentation slide 3, then the fields and `getAvailableDays()` method in
`HealthProfessional.java`.

> The first pillar is encapsulation. The ID, name and working days are private
> final fields. External code can read them only through getters. The
> constructor validates the numeric ID, non-empty name and valid working days,
> so a successfully created object is in a valid state. `getAvailableDays()`
> returns a new `ArrayList`, so callers receive a copy and cannot modify the
> internal collection. I placed validation inside the object instead of
> relying on `main` to remember every check.

## 1:25-2:00 | Abstraction

Visual: Presentation slide 4, then the abstract declaration and abstract
methods in `HealthProfessional.java`.

> The second pillar is abstraction. `HealthProfessional` is an abstract class
> and cannot be instantiated directly. It declares `getRoleDescription()` and
> `performDuty()`, which force every concrete subtype to describe its role and
> duty. Shared fields and common behaviour remain in the base class, while
> subtype-specific behaviour belongs to the subclasses. This prevents an
> object that has no real professional role.

## 2:00-2:35 | Inheritance

Visual: Presentation slide 5, then `GeneralPractitioner.java`.

> The third pillar is inheritance. All four concrete types extend
> `HealthProfessional` and reuse the ID, name, working days, validation and
> common methods. Their constructors call `super(...)` to initialize the parent
> fields before setting licence, specialty, nursing unit or theatre details.
> Each subclass also calls `super.toString()` before appending its own fields,
> so the common output format has only one implementation.

## 2:35-3:20 | Polymorphism

Visual: Presentation slide 6, then the parent-type collection and loop in
`AssignmentOne.java`.

> The fourth pillar is polymorphism. An `ArrayList<HealthProfessional>` stores
> all four concrete types. The loop variable also uses the parent type. Calls to
> `getRoleDescription()` and `performDuty()` use dynamic binding and execute
> the method from the real object type. Adding another professional type does
> not require changes to this traversal logic. `Collections.sort()` also uses
> `Comparable` consistently.

## 3:20-4:00 | Appointment Model and Manager

Visual: Presentation slide 7, then `Appointment.java`, `TimeSlot.java`,
`ClinicSchedule.java` and `AppointmentManager.java`.

> `Appointment` holds a `Patient`, a parent-type `HealthProfessional` and a
> `TimeSlot`. `TimeSlot` stores an hour and a minute and orders values by
> minute of day. `ClinicSchedule` defines the simulated current day and creates
> slots from 08:00 through 15:30. It treats 16:00 as closing time.
> `AppointmentManager` checks the current day, schedule, availability, duplicate
> bookings, filtering, sorting and cancellation. It also accepts a custom
> `ArrayList<TimeSlot>` schedule while keeping the default schedule available.

## 4:00-4:35 | Demonstration and Error Cases

Visual: Presentation slide 8, then the running `AssignmentOne` console.

> The demonstration creates appointments across several professional types,
> displays all records, filters by professional and phone number, and sorts the
> results. A phone-only cancellation succeeds when it matches one record. When
> it matches multiple appointments, the manager returns an ambiguity warning
> and the precise phone-and-time overload resolves it. The demonstration also
> covers duplicate booking, missing appointments, a non-working day and 16:00
> outside the bookable schedule.

## 4:35-4:55 | Four-Pillar Summary

Visual: Presentation slide 9.

> To summarise, encapsulation protects object state, abstraction defines a
> shared contract, inheritance reuses common code, and polymorphism lets a
> caller process different subtypes through one parent type. These four
> pillars work together in the same appointment model. Thank you for watching.

## Recording Checklist

- Keep the video under five minutes.
- Name every pillar and connect it to a code location.
- Explain the design reason for each example.
- Show at least one successful run.
- Be ready to explain `super`, `@Override`, `Comparable`, the duplicate-booking
  rule, configurable schedules and defensive copies.
- Upload the video to SCU OneDrive and confirm the sharing permission.
