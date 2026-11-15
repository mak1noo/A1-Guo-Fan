/**
 * Stores validated patient contact details for an appointment.
 */
public class Patient {
    private final String name;
    private final String mobile;

    /**
     * Creates a patient with a validated name and mobile number.
     *
     * @param name the patient's full name
     * @param mobile a 10-digit Australian mobile number, spaces and hyphens allowed
     */
    public Patient(String name, String mobile) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient name is required.");
        }

        String normalizedMobile = normalizeMobile(mobile);
        if (!isValidAustralianMobile(normalizedMobile)) {
            throw new IllegalArgumentException(
                    "Mobile number must contain 10 digits and start with 04.");
        }

        this.name = name.trim();
        this.mobile = normalizedMobile;
    }

    /**
     * Returns the patient's name.
     *
     * @return the stored name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the normalized patient mobile number.
     *
     * @return a mobile number without spaces or hyphens
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Removes spaces and hyphens from a mobile number.
     *
     * @param mobile the raw mobile number
     * @return a normalized value, or an empty string for null input
     */
    public static String normalizeMobile(String mobile) {
        if (mobile == null) {
            return "";
        }

        String result = "";
        for (int i = 0; i < mobile.length(); i++) {
            char character = mobile.charAt(i);
            if (character != ' ' && character != '-') {
                result += character;
            }
        }
        return result.trim();
    }

    /**
     * Returns a readable patient summary.
     *
     * @return the patient name and normalized mobile number
     */
    @Override
    public String toString() {
        return "Patient{name='" + name + "', mobile='" + mobile + "'}";
    }

    private static boolean isValidAustralianMobile(String mobile) {
        if (mobile.length() != 10 || !mobile.startsWith("04")) {
            return false;
        }
        for (int i = 0; i < mobile.length(); i++) {
            char character = mobile.charAt(i);
            if (character < '0' || character > '9') {
                return false;
            }
        }
        return true;
    }
}
