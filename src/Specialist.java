import java.util.ArrayList;

/**
 * Represents a specialist who may refer patients for further care.
 */
public class Specialist extends HealthProfessional {
    private final String specialty;

    /**
     * Creates a specialist with the default Monday-to-Friday schedule.
     *
     * @param id the numeric professional ID
     * @param name the professional's name
     * @param specialty the specialist's medical specialty
     */
    public Specialist(String id, String name, String specialty) {
        this(id, name, specialty, createWeekdays());
    }

    /**
     * Creates a specialist with a custom working day list.
     *
     * @param id the numeric professional ID
     * @param name the professional's name
     * @param specialty the specialist's medical specialty
     * @param availableDays the specialist's working days
     */
    public Specialist(String id, String name, String specialty, ArrayList<String> availableDays) {
        super(id, name, availableDays);
        this.specialty = requireText(specialty, "Specialty");
    }

    /**
     * Returns the medical specialty.
     *
     * @return the specialty name
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Returns the specialist role.
     *
     * @return the role description
     */
    @Override
    public String getRoleDescription() {
        return "Specialist (" + specialty + ")";
    }

    /**
     * Returns the specialist duty.
     *
     * @return the duty description
     */
    @Override
    public String performDuty() {
        return "Assesses complex conditions and provides specialist care.";
    }

    /**
     * Creates a referral message for a patient.
     *
     * @param patientName the patient's name
     * @param referredService the service receiving the referral
     * @return a readable referral message
     */
    public String referTo(String patientName, String referredService) {
        String patient = requireText(patientName, "Patient name");
        String service = requireText(referredService, "Referred service");
        return getName() + " referred " + patient + " to " + service + ".";
    }

    /**
     * Returns the common and specialist-specific details.
     *
     * @return the formatted details
     */
    @Override
    public String toString() {
        return super.toString() + " | specialty='" + specialty + "'";
    }
}
