import java.util.ArrayList;
import java.util.Objects;

/**
 * Defines the common state and behaviour of every health professional.
 */
public abstract class HealthProfessional implements Comparable<HealthProfessional> {
    private final String id;
    private final String name;
    private final ArrayList<String> availableDays;

    /**
     * Creates a professional with an explicit weekly availability list.
     *
     * @param id the numeric professional ID
     * @param name the professional's full name
     * @param availableDays the days when this professional works
     */
    protected HealthProfessional(String id, String name, ArrayList<String> availableDays) {
        this.id = requireNumericId(id);
        this.name = requireText(name, "Name");

        if (availableDays == null || availableDays.isEmpty()) {
            throw new IllegalArgumentException("At least one available day is required.");
        }

        this.availableDays = new ArrayList<String>();
        for (int i = 0; i < availableDays.size(); i++) {
            String day = availableDays.get(i);
            if (day == null) {
                throw new IllegalArgumentException("Available days cannot contain null.");
            }
            String normalizedDay = day.trim().toUpperCase();
            if (!ClinicSchedule.isValidDay(normalizedDay)) {
                throw new IllegalArgumentException("Invalid working day: " + day);
            }
            if (!this.availableDays.contains(normalizedDay)) {
                this.availableDays.add(normalizedDay);
            }
        }
    }

    /**
     * Creates a professional with the default Monday-to-Friday schedule.
     *
     * @param id the numeric professional ID
     * @param name the professional's full name
     */
    protected HealthProfessional(String id, String name) {
        this(id, name, createWeekdays());
    }

    /**
     * Returns the professional ID.
     *
     * @return the numeric ID
     */
    public final String getId() {
        return id;
    }

    /**
     * Returns the professional name.
     *
     * @return the stored name
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns a copy to protect the internal availability collection.
     *
     * @return a new list containing the working days
     */
    public final ArrayList<String> getAvailableDays() {
        return new ArrayList<String>(availableDays);
    }

    /**
     * Checks whether the professional works on a supplied day.
     *
     * @param day the day to check
     * @return true when the professional works that day
     */
    public final boolean worksOn(String day) {
        return day != null && availableDays.contains(day.trim().toUpperCase());
    }

    /**
     * Returns the professional's role description.
     *
     * @return the role shown to users
     */
    public abstract String getRoleDescription();

    /**
     * Returns a description of the professional's daily duty.
     *
     * @return the duty description
     */
    public abstract String performDuty();

    /**
     * Compares professionals by ID.
     *
     * @param other the professional to compare with
     * @return the ID comparison result
     */
    @Override
    public int compareTo(HealthProfessional other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot compare with null.");
        }
        return id.compareTo(other.id);
    }

    /**
     * The ID is the identity of a professional, so equality is ID-based.
     *
     * @param other the object to compare with
     * @return true when both objects have the same professional ID
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !(other instanceof HealthProfessional)) {
            return false;
        }
        HealthProfessional professional = (HealthProfessional) other;
        return id.equals(professional.id);
    }

    /**
     * Returns a hash code consistent with equals.
     *
     * @return the ID hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returns a readable summary of common professional details.
     *
     * @return the formatted professional details
     */
    @Override
    public String toString() {
        return "HealthProfessional{id='" + id
                + "', name='" + name
                + "', role='" + getRoleDescription()
                + "', availableDays=" + availableDays + "}";
    }

    /**
     * Validates and trims required text.
     *
     * @param value the supplied text
     * @param fieldName the field name used in the error message
     * @return the trimmed text
     */
    protected static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
        return value.trim();
    }

    private static String requireNumericId(String id) {
        String validId = requireText(id, "ID");
        for (int i = 0; i < validId.length(); i++) {
            char character = validId.charAt(i);
            if (character < '0' || character > '9') {
                throw new IllegalArgumentException("ID must contain digits only.");
            }
        }
        return validId;
    }

    /**
     * Returns a new Monday-to-Friday working day list.
     *
     * @return a new default working day list
     */
    protected static ArrayList<String> createWeekdays() {
        ArrayList<String> days = new ArrayList<String>();
        days.add(ClinicSchedule.MONDAY);
        days.add(ClinicSchedule.TUESDAY);
        days.add(ClinicSchedule.WEDNESDAY);
        days.add(ClinicSchedule.THURSDAY);
        days.add(ClinicSchedule.FRIDAY);
        return days;
    }

    /**
     * Returns a new Monday, Wednesday, and Friday working day list.
     *
     * @return a new working day list
     */
    protected static ArrayList<String> createMondayWednesdayFriday() {
        ArrayList<String> days = new ArrayList<String>();
        days.add(ClinicSchedule.MONDAY);
        days.add(ClinicSchedule.WEDNESDAY);
        days.add(ClinicSchedule.FRIDAY);
        return days;
    }

    /**
     * Returns a new Monday, Thursday, and Friday working day list.
     *
     * @return a new working day list
     */
    protected static ArrayList<String> createMondayThursdayFriday() {
        ArrayList<String> days = new ArrayList<String>();
        days.add(ClinicSchedule.MONDAY);
        days.add(ClinicSchedule.THURSDAY);
        days.add(ClinicSchedule.FRIDAY);
        return days;
    }
}
