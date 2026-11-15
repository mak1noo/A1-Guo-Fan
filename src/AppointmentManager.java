import java.util.ArrayList;
import java.util.Collections;

/**
 * Manages appointments for the simulated current clinic day.
 */
public class AppointmentManager {
    private final ArrayList<Appointment> appointments;
    private final ArrayList<TimeSlot> schedule;
    private final String clinicDay;

    /**
     * Creates a manager for the simulated current day.
     */
    public AppointmentManager() {
        this(ClinicSchedule.getCurrentDay(), ClinicSchedule.getDailySlots());
    }

    /**
     * Creates a manager only when the supplied day is the simulated current day.
     *
     * @param clinicDay the requested clinic day
     */
    public AppointmentManager(String clinicDay) {
        this(clinicDay, ClinicSchedule.getDailySlots());
    }

    /**
     * Creates a current-day manager with a custom clinic schedule.
     *
     * @param schedule the configured time slots for the current day
     */
    public AppointmentManager(ArrayList<TimeSlot> schedule) {
        this(ClinicSchedule.getCurrentDay(), schedule);
    }

    /**
     * Creates a manager with a custom schedule for the simulated current day.
     *
     * @param clinicDay the requested clinic day
     * @param schedule the configured time slots for the current day
     */
    public AppointmentManager(String clinicDay, ArrayList<TimeSlot> schedule) {
        if (!ClinicSchedule.isValidDay(clinicDay)) {
            throw new IllegalArgumentException("A valid clinic day is required.");
        }
        if (!ClinicSchedule.getCurrentDay().equalsIgnoreCase(clinicDay.trim())) {
            throw new IllegalArgumentException(
                    "Appointments can only be made for the current clinic day.");
        }

        this.clinicDay = ClinicSchedule.getCurrentDay();
        this.schedule = copyAndValidateSchedule(schedule);
        this.appointments = new ArrayList<Appointment>();
    }

    /**
     * Returns the manager's current clinic day.
     *
     * @return the simulated current day
     */
    public String getClinicDay() {
        return clinicDay;
    }

    /**
     * Returns a defensive copy of the configured clinic schedule.
     *
     * @return the manager's ordered clinic time slots
     */
    public ArrayList<TimeSlot> getSchedule() {
        return new ArrayList<TimeSlot>(schedule);
    }

    /**
     * Returns the number of active appointments.
     *
     * @return the appointment count
     */
    public int getAppointmentCount() {
        return appointments.size();
    }

    /**
     * Returns a defensive copy of all appointments.
     *
     * @return a new list containing the current appointments
     */
    public ArrayList<Appointment> getAppointments() {
        return new ArrayList<Appointment>(appointments);
    }

    /**
     * Adds an appointment after checking the schedule, availability, and conflicts.
     *
     * @param patient the patient to book
     * @param professional the chosen health professional
     * @param timeSlot the requested clinic time
     * @return a success or warning message
     */
    public String addAppointment(Patient patient, HealthProfessional professional,
            TimeSlot timeSlot) {
        if (patient == null || professional == null || timeSlot == null) {
            return "Booking failed: patient, health professional, and time slot are required.";
        }
        if (!schedule.contains(timeSlot)) {
            return "Booking failed: " + timeSlot + " is not in the daily schedule.";
        }
        if (!professional.worksOn(clinicDay)) {
            return "Booking failed: " + professional.getName()
                    + " does not work on " + clinicDay + ".";
        }
        if (hasBookingConflict(professional, timeSlot)) {
            return "Booking failed: " + professional.getName()
                    + " already has an appointment at " + timeSlot + ".";
        }

        appointments.add(new Appointment(patient, professional, timeSlot));
        return "SUCCESS: Appointment booked for " + patient.getName()
                + " with " + professional.getName() + " at " + timeSlot + ".";
    }

    /**
     * Cancels the first appointment matching a patient name and time.
     *
     * @param patientName the patient's name
     * @param timeSlot the appointment time
     * @return a success or warning message
     */
    public String cancelAppointment(String patientName, TimeSlot timeSlot) {
        if (patientName == null || patientName.trim().isEmpty()
                || timeSlot == null) {
            return "Cancellation failed: patient name and time slot are required.";
        }

        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getPatient().getName().equalsIgnoreCase(patientName.trim())
                    && appointment.getTimeSlot().equals(timeSlot)) {
                appointments.remove(i);
                return "SUCCESS: Appointment cancelled for " + patientName.trim()
                        + " at " + timeSlot + ".";
            }
        }
        return "Cancellation failed: no appointment found for "
                + patientName.trim() + " at " + timeSlot + ".";
    }

    /**
     * Cancels a patient's appointment using only the patient's mobile number.
     * A warning is returned when the phone belongs to multiple appointments.
     *
     * @param mobile the patient's mobile number
     * @return a success or warning message
     */
    public String cancelAppointmentByPhone(String mobile) {
        String normalizedMobile = Patient.normalizeMobile(mobile);
        if (normalizedMobile.isEmpty()) {
            return "Cancellation failed: mobile number is required.";
        }

        int matchCount = 0;
        int matchIndex = -1;
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getPatient().getMobile()
                    .equals(normalizedMobile)) {
                matchCount++;
                matchIndex = i;
            }
        }

        if (matchCount == 0) {
            return "Cancellation failed: no appointment found for mobile "
                    + normalizedMobile + ".";
        }
        if (matchCount > 1) {
            return "Cancellation failed: multiple appointments found for mobile "
                    + normalizedMobile + ". Please provide the appointment time.";
        }

        Appointment removed = appointments.remove(matchIndex);
        return "SUCCESS: Appointment cancelled for " + removed.getPatient().getName()
                + " at " + removed.getTimeSlot() + ".";
    }

    /**
     * Cancels a patient's appointment using a mobile number and exact time.
     *
     * @param mobile the patient's mobile number
     * @param timeSlot the appointment time
     * @return a success or warning message
     */
    public String cancelAppointmentByPhone(String mobile, TimeSlot timeSlot) {
        String normalizedMobile = Patient.normalizeMobile(mobile);
        if (normalizedMobile.isEmpty() || timeSlot == null) {
            return "Cancellation failed: mobile number and time slot are required.";
        }

        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getPatient().getMobile().equals(normalizedMobile)
                    && appointment.getTimeSlot().equals(timeSlot)) {
                appointments.remove(i);
                return "SUCCESS: Appointment cancelled for mobile "
                        + normalizedMobile + " at " + timeSlot + ".";
            }
        }
        return "Cancellation failed: no appointment found for mobile "
                + normalizedMobile + " at " + timeSlot + ".";
    }

    /**
     * Cancels an appointment using a professional ID and exact time.
     *
     * @param professionalId the professional's numeric ID
     * @param timeSlot the appointment time
     * @return a success or warning message
     */
    public String cancelAppointmentByProfessional(String professionalId,
            TimeSlot timeSlot) {
        if (professionalId == null || professionalId.trim().isEmpty()
                || timeSlot == null) {
            return "Cancellation failed: professional ID and time slot are required.";
        }

        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getHealthProfessional().getId()
                    .equals(professionalId.trim())
                    && appointment.getTimeSlot().equals(timeSlot)) {
                appointments.remove(i);
                return "SUCCESS: Appointment cancelled for professional ID "
                        + professionalId.trim() + " at " + timeSlot + ".";
            }
        }
        return "Cancellation failed: no appointment found for professional ID "
                + professionalId.trim() + " at " + timeSlot + ".";
    }

    /**
     * Displays every appointment in insertion order.
     */
    public void displayAll() {
        System.out.println("All appointments for " + clinicDay + ":");
        printAppointments(appointments, "all appointments");
    }

    /**
     * Displays appointments for one professional ID.
     *
     * @param professionalId the professional ID to filter by
     */
    public void displayByProfessional(String professionalId) {
        ArrayList<Appointment> matches =
                findAppointmentsByProfessional(professionalId);
        System.out.println("Appointments for professional ID " + professionalId + ":");
        printAppointments(matches, "professional ID " + professionalId);
    }

    /**
     * Displays a professional's appointments sorted by time.
     *
     * @param professionalId the professional ID to filter by
     */
    public void displayByProfessionalSorted(String professionalId) {
        ArrayList<Appointment> matches =
                findAppointmentsByProfessional(professionalId);
        Collections.sort(matches);
        System.out.println("Sorted appointments for professional ID "
                + professionalId + ":");
        printAppointments(matches, "professional ID " + professionalId);
    }

    /**
     * Displays appointments for one patient mobile number.
     *
     * @param mobile the mobile number to filter by
     */
    public void displayByPatientPhone(String mobile) {
        ArrayList<Appointment> matches = findAppointmentsByPhone(mobile);
        System.out.println("Appointments for patient mobile " + mobile + ":");
        printAppointments(matches, "mobile " + mobile);
    }

    /**
     * Displays a patient's appointments sorted by time.
     *
     * @param mobile the mobile number to filter by
     */
    public void displayByPatientPhoneSorted(String mobile) {
        ArrayList<Appointment> matches = findAppointmentsByPhone(mobile);
        Collections.sort(matches);
        System.out.println("Sorted appointments for patient mobile " + mobile + ":");
        printAppointments(matches, "mobile " + mobile);
    }

    /**
     * Displays all appointments sorted by time.
     */
    public void displaySortedByTime() {
        ArrayList<Appointment> sortedAppointments =
                new ArrayList<Appointment>(appointments);
        Collections.sort(sortedAppointments);
        System.out.println("All appointments sorted by time:");
        printAppointments(sortedAppointments, "all appointments");
    }

    private boolean hasBookingConflict(HealthProfessional professional,
            TimeSlot timeSlot) {
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getHealthProfessional().equals(professional)
                    && appointment.getTimeSlot().equals(timeSlot)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Appointment> findAppointmentsByProfessional(
            String professionalId) {
        ArrayList<Appointment> matches = new ArrayList<Appointment>();
        if (professionalId == null || professionalId.trim().isEmpty()) {
            return matches;
        }

        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getHealthProfessional().getId()
                    .equals(professionalId.trim())) {
                matches.add(appointment);
            }
        }
        return matches;
    }

    private static ArrayList<TimeSlot> copyAndValidateSchedule(
            ArrayList<TimeSlot> suppliedSchedule) {
        if (suppliedSchedule == null || suppliedSchedule.isEmpty()) {
            throw new IllegalArgumentException(
                    "The daily schedule must contain at least one time slot.");
        }

        ArrayList<TimeSlot> validatedSchedule = new ArrayList<TimeSlot>();
        for (int i = 0; i < suppliedSchedule.size(); i++) {
            TimeSlot timeSlot = suppliedSchedule.get(i);
            if (timeSlot == null) {
                throw new IllegalArgumentException(
                        "The daily schedule cannot contain null.");
            }
            if (!ClinicSchedule.isWithinOpeningHours(timeSlot)) {
                throw new IllegalArgumentException(
                        "Every schedule time must be inside clinic opening hours.");
            }
            if (validatedSchedule.contains(timeSlot)) {
                throw new IllegalArgumentException(
                        "The daily schedule cannot contain duplicate times.");
            }
            validatedSchedule.add(timeSlot);
        }
        Collections.sort(validatedSchedule);
        return validatedSchedule;
    }

    private ArrayList<Appointment> findAppointmentsByPhone(String mobile) {
        ArrayList<Appointment> matches = new ArrayList<Appointment>();
        String normalizedMobile = Patient.normalizeMobile(mobile);
        if (normalizedMobile.isEmpty()) {
            return matches;
        }

        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getPatient().getMobile().equals(normalizedMobile)) {
                matches.add(appointment);
            }
        }
        return matches;
    }

    private void printAppointments(ArrayList<Appointment> appointmentsToPrint,
            String scope) {
        if (appointmentsToPrint.isEmpty()) {
            System.out.println("No appointments found for " + scope + ".");
            return;
        }
        for (int i = 0; i < appointmentsToPrint.size(); i++) {
            System.out.println((i + 1) + ". " + appointmentsToPrint.get(i));
        }
    }
}
