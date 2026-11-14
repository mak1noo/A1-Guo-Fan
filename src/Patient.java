/**
 * Represents a patient who books an appointment.
 */
public class Patient {
    private final String name;
    private final String mobile;

    public Patient(String name, String mobile) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient name is required.");
        }
        if (mobile == null) {
            throw new IllegalArgumentException(
                    "Mobile number must contain 10 digits and start with 04.");
        }
        String trimmedMobile = mobile.trim();
        if (!isValidAustralianMobile(trimmedMobile)) {
            throw new IllegalArgumentException(
                    "Mobile number must contain 10 digits and start with 04.");
        }
        this.name = name.trim();
        this.mobile = trimmedMobile;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    private static boolean isValidAustralianMobile(String mobile) {
        if (mobile == null || mobile.length() != 10 || !mobile.startsWith("04")) {
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

    @Override
    public String toString() {
        return "Patient{name='" + name + "', mobile='" + mobile + "'}";
    }
}
