# Hospital Appointment System: Final Class Design

> The assessment brief does not permit GenAI to write the submitted solution
> code, code comments, or video explanation. This document is a design
> reference. Before submission, follow the unit rules and ensure that you can
> explain every implementation decision independently.

## 1. Class Responsibilities

| Class | Responsibility | Assessment Part |
|---|---|---|
| `AssignmentOne` | Runs the Part 3 and Part 6 console demonstration | 3, 6 |
| `HealthProfessional` | Abstract base class, common validation, equality and ordering | 1, 2 |
| `GeneralPractitioner` | GP-specific fields and prescription behaviour | 2 |
| `Specialist` | Specialist fields and referral behaviour | 2 |
| `Nurse` | Nursing unit and blood sample behaviour | 2 |
| `Surgeon` | Operating theatre and operation scheduling behaviour | 2 |
| `Patient` | Patient name, mobile normalization and validation | 4 |
| `TimeSlot` | Immutable hour/minute value and time ordering | 4 |
| `ClinicSchedule` | Simulated current day and fixed daily schedule | 4, 5 |
| `Appointment` | Composition of patient, professional and valid time | 4 |
| `AppointmentManager` | Adding, conflict checks, filtering, sorting and cancellation | 5, 6 |

## 2. Text Class Diagram

```text
HealthProfessional
|-- GeneralPractitioner
|-- Specialist
|-- Nurse
`-- Surgeon

HealthProfessional ..|> Comparable<HealthProfessional>
HealthProfessional has-a ArrayList<String> availableDays

Appointment has-a Patient
Appointment has-a HealthProfessional
Appointment has-a TimeSlot
Appointment ..|> Comparable<Appointment>

TimeSlot ..|> Comparable<TimeSlot>
ClinicSchedule has-a ArrayList<TimeSlot> dailySlots
AppointmentManager has-a ArrayList<Appointment>
AppointmentManager has-a currentDay

AssignmentOne ..> AppointmentManager
AssignmentOne ..> all concrete professional types
```

## 3. Key Design Decisions

### HealthProfessional

- `id`, `name` and `availableDays` use `private final` fields.
- The ID must contain digits only, and the name cannot be blank.
- Working days are trimmed, converted to uppercase, validated and de-duplicated.
- `getAvailableDays()` returns a defensive copy.
- Two protected constructors support a custom schedule and a default
  Monday-to-Friday schedule.
- Two abstract methods force every concrete type to provide a role and duty.
- `equals()` and `hashCode()` use the professional ID consistently.
- `Comparable<HealthProfessional>` orders professionals by ID.

### TimeSlot

- The design avoids advanced time APIs that are outside the unit scope.
- `hour` and `minute` use `private final` fields.
- Hours range from 0 to 23, and minutes accept only 0 or 30.
- `compareTo()` uses minutes after midnight rather than string order.
- `equals()` and `hashCode()` use hour and minute.
- `toString()` uses `String.format()` to produce `HH:mm`.

### ClinicSchedule

- `CURRENT_DAY` defines the simulated current day as `MONDAY`.
- The schedule generates 08:00 through 15:30 at 30-minute intervals.
- 16:00 is the closing time and is not a bookable slot.
- The schedule contains 16 bookable time slots.
- `getDailySlots()` returns a defensive copy.
- `Appointment` and `AppointmentManager` share the same schedule validation.

### Patient

- The patient name must not be blank.
- Mobile normalization removes spaces and hyphens before validation.
- The normalized value must contain 10 digits and start with `04`.
- The validated continuous-digit value is stored in a final field.
- `normalizeMobile()` is reused by filtering and cancellation so that the
  same input format works consistently.

### Appointment

- The three association fields are final and cannot be null.
- The `TimeSlot` must belong to `ClinicSchedule`.
- `compareTo()` orders by time and then by professional ID.
- `toString()` returns a concise summary containing the time, patient,
  professional role, ID and name.

### AppointmentManager

- The no-argument constructor uses the simulated current day.
- The overloaded constructor rejects any day other than the current day.
- The manager stores an `ArrayList<TimeSlot>` schedule.
- The default schedule comes from `ClinicSchedule.getDailySlots()`.
- A custom-schedule constructor accepts a reduced or irregular list of slots.
- Custom schedules reject null, duplicate, outside-hours and empty values.
- `getSchedule()` returns a defensive copy.
- `addAppointment()` checks null values, the fixed schedule, availability and
  double booking.
- Filtering and sorting use copies of the appointment list.
- `cancelAppointmentByPhone(mobile)` implements the brief's phone-only
  cancellation requirement.
- Multiple appointments for one phone number return an ambiguity warning.
- `cancelAppointmentByPhone(mobile, timeSlot)` resolves the ambiguity.
- `cancelAppointmentByProfessional(id, timeSlot)` implements the second
  required cancellation method.
- `cancelAppointment(name, timeSlot)` is an additional convenience method.
- Every add and cancel operation returns an explicit success or failure
  message.

## 4. Concept Mapping

| Concept | Location |
|---|---|
| Encapsulation | Private final fields, getters and defensive copies |
| Constructor validation | Professionals, patient, time, appointment and manager |
| Constructor overloading | `HealthProfessional`, four subclasses, manager |
| `this(...)` | Subclass constructors and manager constructor delegation |
| `super(...)` | Subclass initialization of common fields |
| Abstract class and methods | `HealthProfessional` |
| Inheritance | Four concrete professional subclasses |
| Polymorphism | `ArrayList<HealthProfessional>` traversal |
| Dynamic binding | `getRoleDescription()` and `performDuty()` |
| `@Override` | `compareTo()`, `equals()`, `hashCode()` and `toString()` |
| `super.toString()` | Four subclasses reuse common output |
| `equals()` and `hashCode()` | Professional ID and `TimeSlot` value |
| `Comparable` | Professionals, appointments and time slots |
| `Collections.sort()` | Professional lists and appointment lists |
| `ArrayList` | Working days, professionals, slots, appointments and results |
| `static final` | Current day, weekdays, schedule and fixed fields |
| `String.format()` | TimeSlot `HH:mm` output |
| `if`, loops and `switch` | Validation, conflicts, filtering and schedule generation |

## 5. Part 3 Demonstration

1. Create three GPs.
2. Create two specialists.
3. Create two nurses.
4. Create one surgeon.
5. Add all eight objects to `ArrayList<HealthProfessional>`.
6. Sort the list with `Collections.sort()`.
7. Use a normal `for` loop to print each object, role and duty.
8. Call prescription, referral, blood sample and operation methods.
9. Compare two GP objects with the same ID to verify `equals()` and
   `hashCode()`.

## 6. Part 6 Demonstration

| Scenario | Expected Result |
|---|---|
| Current day | `Clinic day: MONDAY` |
| Schedule | 16 slots from 08:00 through 15:30 |
| Add appointment | `SUCCESS: Appointment booked...` |
| Display all | Complete appointment summaries |
| Filter | Professional ID and patient phone |
| Sort | All and filtered lists ordered by `TimeSlot` |
| Configurable schedule | A custom slot accepts a booking while an unconfigured slot is rejected |
| Phone single match | Direct cancellation success |
| Phone multiple matches | `Cancellation failed: multiple appointments...` |
| Phone and time | Exact cancellation success |
| Professional and time | Required cancellation success |
| Name and time | Additional cancellation success |
| Double booking | Conflict warning |
| Missing cancellation | Three clear no-appointment warnings |
| Non-working day | Professional unavailable warning |
| 16:00 | `Booking failed: 16:00 is not in the daily schedule.` |
| Final count | Three remaining appointments |

## 7. HD Checklist

| Rubric Item | Evidence |
|---|---|
| Part 1, 10% | Abstract base, numeric ID, controlled access, overloaded constructors, comparison and equality |
| Part 2, 20% | GP, Specialist, Nurse and Surgeon with distinct fields and behaviour |
| Part 3, 10% | Parent-type collection, polymorphic traversal and concrete behaviours |
| Part 4, 15% | Patient, TimeSlot, Appointment and schedule membership validation |
| Part 5, 15% | Current-day rule, fixed schedule, conflicts, filters, sorting and required cancellation methods |
| Part 6, 10% | Normal cases, cancellation variants, duplicate booking, non-working day, 16:00 and missing records |
| Video, 10% | Four pillars linked to specific code and design reasons |
| Professional practice, 10% | Javadoc with zero warnings, `-Xlint:all -Werror`, contract tests, GitHub and WakaTime |

## 8. Suggested GitHub Commit Topics

1. Set up the project structure
2. Add the abstract professional base class
3. Implement four concrete professional types
4. Complete the Part 3 polymorphism demonstration
5. Add Patient and TimeSlot
6. Implement Appointment
7. Add booking and conflict checks
8. Add filters and sorting
9. Add required cancellation methods
10. Add contract tests
11. Complete Javadoc
12. Perform final cleanup and ZIP validation

## 9. Video Reference Points

### Encapsulation

- Private final fields and public getters.
- Defensive copies returned from collection getters.
- Constructor validation.

### Abstraction

- Abstract `HealthProfessional` class.
- Two abstract methods.
- Private manager helpers for current-day rules and conflicts.

### Inheritance

- `extends HealthProfessional`.
- `super(...)` calls.
- `super.toString()` reuse.

### Polymorphism

- `ArrayList<HealthProfessional>`.
- Dynamic binding of `getRoleDescription()` and `performDuty()`.
- Parent-type professional field in `Appointment`.

## 10. Common Errors

| Error | Check |
|---|---|
| `equals()` conflicts with `hashCode()` | Equal IDs must produce equal hash codes |
| `super(...)` is not the first statement | Inspect every subclass constructor |
| Time uses text order | Confirm `TimeSlot.compareTo()` uses minutes |
| 16:00 is treated as bookable | Confirm the loop uses `< END_MINUTES` |
| Phone formats differ across operations | Normalize before validation, filtering and cancellation |
| Multiple phone matches are deleted directly | Return an ambiguity warning |
| Manager accepts another day | Inspect the overloaded constructor |
| Getter exposes internal collections | Return defensive copies |
