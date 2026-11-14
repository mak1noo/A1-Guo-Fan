import java.util.ArrayList;

/**
 * Represents a surgeon who can schedule operations.
 */
public class Surgeon extends HealthProfessional {
    private final String operatingTheatre;

    public Surgeon(String id, String name, String operatingTheatre) {
        this(id, name, operatingTheatre, createMondayThursdayFriday());
    }

    public Surgeon(String id, String name, String operatingTheatre,
            ArrayList<String> availableDays) {
        super(id, name, availableDays);
        this.operatingTheatre = requireText(operatingTheatre, "Operating theatre");
    }

    public String getOperatingTheatre() {
        return operatingTheatre;
    }

    @Override
    public String getRoleDescription() {
        return "Surgeon (" + operatingTheatre + ")";
    }

    @Override
    public String performDuty() {
        return "Performs surgical procedures and reviews post-operative care.";
    }

    public String scheduleOperation(String patientName, String procedure) {
        String patient = requireText(patientName, "Patient name");
        String operation = requireText(procedure, "Procedure");
        return getName() + " scheduled " + operation + " for " + patient + ".";
    }

    @Override
    public String toString() {
        return super.toString() + " | operatingTheatre='" + operatingTheatre + "'";
    }
}
