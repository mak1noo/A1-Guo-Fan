import java.util.ArrayList;

/**
 * Represents a general practitioner.
 */
public class GeneralPractitioner extends HealthProfessional {
    private final String medicalLicenceNumber;

    /**
     * Creates a GP with the default Monday, Wednesday, and Friday schedule.
     *
     * @param id the numeric professional ID
     * @param name the professional's name
     * @param medicalLicenceNumber the GP's licence number
     */
    public GeneralPractitioner(String id, String name, String medicalLicenceNumber) {
        this(id, name, medicalLicenceNumber, createMondayWednesdayFriday());
    }

    /**
     * Creates a GP with a custom working day list.
     *
     * @param id the numeric professional ID
     * @param name the professional's name
     * @param medicalLicenceNumber the GP's licence number
     * @param availableDays the GP's working days
     */
    public GeneralPractitioner(String id, String name, String medicalLicenceNumber,
            ArrayList<String> availableDays) {
        super(id, name, availableDays);
        this.medicalLicenceNumber = requireText(medicalLicenceNumber, "Medical licence number");
    }

    /**
     * Returns the medical licence number.
     *
     * @return the licence number
     */
    public String getMedicalLicenceNumber() {
        return medicalLicenceNumber;
    }

    /**
     * Returns the GP role.
     *
     * @return the role name
     */
    @Override
    public String getRoleDescription() {
        return "General Practitioner";
    }

    /**
     * Returns the GP duty.
     *
     * @return the duty description
     */
    @Override
    public String performDuty() {
        return "Provides primary care and coordinates patient treatment.";
    }

    /**
     * Creates a prescription message for a patient.
     *
     * @param patientName the patient's name
     * @param medication the prescribed medication
     * @return a readable prescription message
     */
    public String prescribeDrug(String patientName, String medication) {
        String patient = requireText(patientName, "Patient name");
        String medicine = requireText(medication, "Medication");
        return getName() + " prescribed " + medicine + " for " + patient + ".";
    }

    /**
     * Returns the common and GP-specific details.
     *
     * @return the formatted details
     */
    @Override
    public String toString() {
        return super.toString() + " | medicalLicenceNumber='" + medicalLicenceNumber + "'";
    }
}
