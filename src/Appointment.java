/**
 * Links one patient to one professional at a bookable clinic time.
 */
public class Appointment implements Comparable<Appointment> {
    private final Patient patient;
    private final HealthProfessional healthProfessional;
    private final TimeSlot timeSlot;

    /**
     * Creates a complete and validated appointment.
     *
     * @param patient the booked patient
     * @param healthProfessional the chosen health professional
     * @param timeSlot a time that belongs to the fixed clinic schedule
     */
    public Appointment(Patient patient, HealthProfessional healthProfessional,
            TimeSlot timeSlot) {
        if (patient == null || healthProfessional == null || timeSlot == null) {
            throw new IllegalArgumentException(
                    "Patient, health professional, and time slot are required.");
        }
        if (!ClinicSchedule.isWithinOpeningHours(timeSlot)) {
            throw new IllegalArgumentException(
                    "The selected time is outside the clinic's opening hours.");
        }

        this.patient = patient;
        this.healthProfessional = healthProfessional;
        this.timeSlot = timeSlot;
    }

    /**
     * Returns the appointment patient.
     *
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Returns the appointment health professional.
     *
     * @return the selected health professional
     */
    public HealthProfessional getHealthProfessional() {
        return healthProfessional;
    }

    /**
     * Returns the appointment time.
     *
     * @return the bookable clinic time
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * Compares appointments by time and then by professional ID.
     *
     * @param other the appointment to compare with
     * @return a negative value, zero, or a positive value based on order
     */
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

    /**
     * Returns a concise appointment summary.
     *
     * @return the time, patient, and professional summary
     */
    @Override
    public String toString() {
        return "Appointment{time=" + timeSlot
                + ", patient='" + patient.getName()
                + "', mobile='" + patient.getMobile()
                + "', professional='" + healthProfessional.getRoleDescription()
                + "', professionalId='" + healthProfessional.getId()
                + "', professionalName='" + healthProfessional.getName() + "'}";
    }
}
