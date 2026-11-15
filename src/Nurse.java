import java.util.ArrayList;

/**
 * Represents a nurse who can collect clinical samples.
 */
public class Nurse extends HealthProfessional {
    private final String nursingUnit;

    public Nurse(String id, String name, String nursingUnit) {
        this(id, name, nursingUnit, createWeekdays());
    }

    public Nurse(String id, String name, String nursingUnit, ArrayList<String> availableDays) {
        super(id, name, availableDays);
        this.nursingUnit = requireText(nursingUnit, "Nursing unit");
    }

    public String getNursingUnit() {
        return nursingUnit;
    }

    @Override
    public String getRoleDescription() {
        return "Nurse (" + nursingUnit + ")";
    }

    @Override
    public String performDuty() {
        return "Monitors patients and supports clinical procedures.";
    }

    public String takeBloodSample(String patientName) {
        String patient = requireText(patientName, "Patient name");
        return getName() + " collected a blood sample from " + patient + ".";
    }

    @Override
    public String toString() {
        return super.toString() + " | nursingUnit='" + nursingUnit + "'";
    }
}
