import java.util.ArrayList;

/**
 * Represents a surgeon who can schedule operations.
 */
public class Surgeon extends HealthProfessional {
    private final String operatingTheatre;

    /**
     * Creates a surgeon with the default Monday, Thursday, and Friday schedule.
     *
     * @param id the numeric professional ID
     * @param name the surgeon's name
     * @param operatingTheatre the surgeon's operating theatre
     */
    public Surgeon(String id, String name, String operatingTheatre) {
        this(id, name, operatingTheatre, createMondayThursdayFriday());
    }

    /**
     * Creates a surgeon with a custom working day list.
     *
     * @param id the numeric professional ID
     * @param name the surgeon's name
     * @param operatingTheatre the surgeon's operating theatre
     * @param availableDays the surgeon's working days
     */
    public Surgeon(String id, String name, String operatingTheatre,
            ArrayList<String> availableDays) {
        super(id, name, availableDays);
        this.operatingTheatre = requireText(operatingTheatre, "Operating theatre");
    }

    /**
     * Returns the operating theatre.
     *
     * @return the operating theatre name
     */
    public String getOperatingTheatre() {
        return operatingTheatre;
    }

    /**
     * Returns the surgeon role.
     *
     * @return the role description
     */
    @Override
    public String getRoleDescription() {
        return "Surgeon (" + operatingTheatre + ")";
    }

    /**
     * Returns the surgeon duty.
     *
     * @return the duty description
     */
    @Override
    public String performDuty() {
        return "Performs surgical procedures and reviews post-operative care.";
    }

    /**
     * Creates an operation scheduling message.
     *
     * @param patientName the patient's name
     * @param procedure the scheduled procedure
     * @return a readable operation message
     */
    public String scheduleOperation(String patientName, String procedure) {
        String patient = requireText(patientName, "Patient name");
        String operation = requireText(procedure, "Procedure");
        return getName() + " scheduled " + operation + " for " + patient + ".";
    }

    /**
     * Returns the common and surgeon-specific details.
     *
     * @return the formatted details
     */
    @Override
    public String toString() {
        return super.toString() + " | operatingTheatre='" + operatingTheatre + "'";
    }
}
