/**
 * Links one patient to one health professional and one clinic time slot.
 */
public class Appointment implements Comparable<Appointment> {
    private final Patient patient;
    private final HealthProfessional healthProfessional;
    private final String timeSlot;

    public Appointment(Patient patient, HealthProfessional healthProfessional,
            String timeSlot) {
        if (patient == null || healthProfessional == null || timeSlot == null) {
            throw new IllegalArgumentException(
                    "Patient, health professional, and time slot are required.");
        }
        if (!ClinicSchedule.isScheduledSlot(timeSlot)) {
            throw new IllegalArgumentException("The selected time is not in the clinic schedule.");
        }
        this.patient = patient;
        this.healthProfessional = healthProfessional;
        this.timeSlot = timeSlot.trim();
    }

    public Patient getPatient() {
        return patient;
    }

    public HealthProfessional getHealthProfessional() {
        return healthProfessional;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    @Override
    public int compareTo(Appointment other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot compare with null.");
        }
        int timeOrder = timeSlot.compareTo(other.timeSlot);
        if (timeOrder != 0) {
            return timeOrder;
        }
        return healthProfessional.compareTo(other.healthProfessional);
    }

    @Override
    public String toString() {
        return "Appointment{patient=" + patient
                + ", healthProfessional=" + healthProfessional
                + ", timeSlot=" + timeSlot + "}";
    }
}

