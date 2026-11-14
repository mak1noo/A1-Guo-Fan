import java.util.ArrayList;

/**
 * Represents a general practitioner.
 */
public class GeneralPractitioner extends HealthProfessional {
    private final String medicalLicenceNumber;

    public GeneralPractitioner(String id, String name, String medicalLicenceNumber) {
        this(id, name, medicalLicenceNumber, createMondayWednesdayFriday());
    }

    public GeneralPractitioner(String id, String name, String medicalLicenceNumber,
            ArrayList<String> availableDays) {
        super(id, name, availableDays);
        this.medicalLicenceNumber = requireText(medicalLicenceNumber, "Medical licence number");
    }

    public String getMedicalLicenceNumber() {
        return medicalLicenceNumber;
    }

    @Override
    public String getRoleDescription() {
        return "General Practitioner";
    }

    @Override
    public String performDuty() {
        return "Provides primary care and coordinates patient treatment.";
    }

    public String prescribeDrug(String patientName, String medication) {
        String patient = requireText(patientName, "Patient name");
        String medicine = requireText(medication, "Medication");
        return getName() + " prescribed " + medicine + " for " + patient + ".";
    }

    @Override
    public String toString() {
        return super.toString() + " | medicalLicenceNumber='" + medicalLicenceNumber + "'";
    }
}
