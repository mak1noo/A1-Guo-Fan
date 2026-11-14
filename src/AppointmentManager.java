import java.util.ArrayList;
import java.util.Collections;

/**
 * Manages appointments for one current clinic day.
 */
public class AppointmentManager {
    private final ArrayList<Appointment> appointments;
    private final String clinicDay;

    public AppointmentManager(String clinicDay) {
        if (!ClinicSchedule.isValidDay(clinicDay)) {
            throw new IllegalArgumentException("A valid clinic day is required.");
        }
        this.clinicDay = clinicDay.trim();
        this.appointments = new ArrayList<Appointment>();
    }

    public String getClinicDay() {
        return clinicDay;
    }

    public int getAppointmentCount() {
        return appointments.size();
    }

    /**
     * Returns a copy to keep the manager in control of its collection.
     */
    public ArrayList<Appointment> getAppointments() {
        return new ArrayList<Appointment>(appointments);
    }

    public String addAppointment(Patient patient, HealthProfessional professional,
            String timeSlot) {
        if (patient == null || professional == null || timeSlot == null) {
            return "Booking failed: patient, health professional, and time slot are required.";
        }
        if (!ClinicSchedule.isScheduledSlot(timeSlot)) {
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

    public String cancelAppointment(String patientName, String timeSlot) {
        if (patientName == null || patientName.trim().isEmpty() || timeSlot == null) {
            return "Cancellation failed: patient name and time slot are required.";
        }

        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getPatient().getName().equalsIgnoreCase(patientName.trim())
                    && appointment.getTimeSlot().equals(timeSlot.trim())) {
                appointments.remove(i);
                return "SUCCESS: Appointment cancelled for " + patientName.trim()
                        + " at " + timeSlot.trim() + ".";
            }
        }
        return "Cancellation failed: no appointment found for "
                + patientName.trim() + " at " + timeSlot.trim() + ".";
    }

    public String cancelAppointmentByPhone(String mobile, String timeSlot) {
        if (mobile == null || mobile.trim().isEmpty() || timeSlot == null) {
            return "Cancellation failed: mobile number and time slot are required.";
        }

        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getPatient().getMobile().equals(mobile.trim())
                    && appointment.getTimeSlot().equals(timeSlot.trim())) {
                appointments.remove(i);
                return "SUCCESS: Appointment cancelled for mobile "
                        + mobile.trim() + " at " + timeSlot.trim() + ".";
            }
        }
        return "Cancellation failed: no appointment found for mobile "
                + mobile.trim() + " at " + timeSlot.trim() + ".";
    }

    public String cancelAppointmentByProfessional(String professionalId, String timeSlot) {
        if (professionalId == null || professionalId.trim().isEmpty() || timeSlot == null) {
            return "Cancellation failed: professional ID and time slot are required.";
        }

        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getHealthProfessional().getId().equals(professionalId.trim())
                    && appointment.getTimeSlot().equals(timeSlot.trim())) {
                appointments.remove(i);
                return "SUCCESS: Appointment cancelled for professional ID "
                        + professionalId.trim() + " at " + timeSlot.trim() + ".";
            }
        }
        return "Cancellation failed: no appointment found for professional ID "
                + professionalId.trim() + " at " + timeSlot.trim() + ".";
    }

    public void displayAll() {
        System.out.println("All appointments for " + clinicDay + ":");
        printAppointments(appointments, "all appointments");
    }

    public void displayByProfessional(String professionalId) {
        ArrayList<Appointment> matches = findAppointmentsByProfessional(professionalId);
        System.out.println("Appointments for professional ID " + professionalId + ":");
        printAppointments(matches, "professional ID " + professionalId);
    }

    public void displayByProfessionalSorted(String professionalId) {
        ArrayList<Appointment> matches = findAppointmentsByProfessional(professionalId);
        Collections.sort(matches);
        System.out.println("Sorted appointments for professional ID " + professionalId + ":");
        printAppointments(matches, "professional ID " + professionalId);
    }

    public void displayByPatientPhone(String mobile) {
        ArrayList<Appointment> matches = findAppointmentsByPhone(mobile);
        System.out.println("Appointments for patient mobile " + mobile + ":");
        printAppointments(matches, "mobile " + mobile);
    }

    public void displayByPatientPhoneSorted(String mobile) {
        ArrayList<Appointment> matches = findAppointmentsByPhone(mobile);
        Collections.sort(matches);
        System.out.println("Sorted appointments for patient mobile " + mobile + ":");
        printAppointments(matches, "mobile " + mobile);
    }

    public void displaySortedByTime() {
        ArrayList<Appointment> sortedAppointments =
                new ArrayList<Appointment>(appointments);
        Collections.sort(sortedAppointments);
        System.out.println("All appointments sorted by time:");
        printAppointments(sortedAppointments, "all appointments");
    }

    private boolean hasBookingConflict(HealthProfessional professional, String timeSlot) {
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getHealthProfessional().equals(professional)
                    && appointment.getTimeSlot().equals(timeSlot.trim())) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Appointment> findAppointmentsByProfessional(String professionalId) {
        ArrayList<Appointment> matches = new ArrayList<Appointment>();
        if (professionalId == null || professionalId.trim().isEmpty()) {
            return matches;
        }
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getHealthProfessional().getId().equals(professionalId.trim())) {
                matches.add(appointment);
            }
        }
        return matches;
    }

    private ArrayList<Appointment> findAppointmentsByPhone(String mobile) {
        ArrayList<Appointment> matches = new ArrayList<Appointment>();
        if (mobile == null || mobile.trim().isEmpty()) {
            return matches;
        }
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getPatient().getMobile().equals(mobile.trim())) {
                matches.add(appointment);
            }
        }
        return matches;
    }

    private void printAppointments(ArrayList<Appointment> appointmentsToPrint, String scope) {
        if (appointmentsToPrint.isEmpty()) {
            System.out.println("No appointments found for " + scope + ".");
            return;
        }
        for (int i = 0; i < appointmentsToPrint.size(); i++) {
            System.out.println((i + 1) + ". " + appointmentsToPrint.get(i));
        }
    }
}
