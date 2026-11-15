import java.util.ArrayList;

/**
 * Represents a nurse who can collect clinical samples.
 */
public class Nurse extends HealthProfessional {
    private final String nursingUnit;

    /**
     * Creates a nurse with the default Monday-to-Friday schedule.
     *
     * @param id the numeric professional ID
     * @param name the nurse's name
     * @param nursingUnit the nurse's unit or ward
     */
    public Nurse(String id, String name, String nursingUnit) {
        this(id, name, nursingUnit, createWeekdays());
    }

    /**
     * Creates a nurse with a custom working day list.
     *
     * @param id the numeric professional ID
     * @param name the nurse's name
     * @param nursingUnit the nurse's unit or ward
     * @param availableDays the nurse's working days
     */
    public Nurse(String id, String name, String nursingUnit, ArrayList<String> availableDays) {
        super(id, name, availableDays);
        this.nursingUnit = requireText(nursingUnit, "Nursing unit");
    }

    /**
     * Returns the nursing unit.
     *
     * @return the nursing unit name
     */
    public String getNursingUnit() {
        return nursingUnit;
    }

    /**
     * Returns the nurse role.
     *
     * @return the role description
     */
    @Override
    public String getRoleDescription() {
        return "Nurse (" + nursingUnit + ")";
    }

    /**
     * Returns the nurse duty.
     *
     * @return the duty description
     */
    @Override
    public String performDuty() {
        return "Monitors patients and supports clinical procedures.";
    }

    /**
     * Creates a blood sample collection message.
     *
     * @param patientName the patient's name
     * @return a readable sample collection message
     */
    public String takeBloodSample(String patientName) {
        String patient = requireText(patientName, "Patient name");
        return getName() + " collected a blood sample from " + patient + ".";
    }

    /**
     * Returns the common and nurse-specific details.
     *
     * @return the formatted details
     */
    @Override
    public String toString() {
        return super.toString() + " | nursingUnit='" + nursingUnit + "'";
    }
}
