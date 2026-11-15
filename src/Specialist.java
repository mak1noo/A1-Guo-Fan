import java.util.ArrayList;

/**
 * Represents a specialist who may refer patients for further care.
 */
public class Specialist extends HealthProfessional {
    private final String specialty;

    public Specialist(String id, String name, String specialty) {
        this(id, name, specialty, createWeekdays());
    }

    public Specialist(String id, String name, String specialty, ArrayList<String> availableDays) {
        super(id, name, availableDays);
        this.specialty = requireText(specialty, "Specialty");
    }

    public String getSpecialty() {
        return specialty;
    }

    @Override
    public String getRoleDescription() {
        return "Specialist (" + specialty + ")";
    }

    @Override
    public String performDuty() {
        return "Assesses complex conditions and provides specialist care.";
    }

    public String referTo(String patientName, String referredService) {
        String patient = requireText(patientName, "Patient name");
        String service = requireText(referredService, "Referred service");
        return getName() + " referred " + patient + " to " + service + ".";
    }

    @Override
    public String toString() {
        return super.toString() + " | specialty='" + specialty + "'";
    }
}
