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
            String trimmedDay = day.trim();
            if (!ClinicSchedule.isValidDay(trimmedDay)) {
                throw new IllegalArgumentException("Invalid working day: " + day);
            }
            if (!this.availableDays.contains(trimmedDay)) {
                this.availableDays.add(trimmedDay);
            }
        }
    }

    /**
     * Creates a professional with the default Monday-to-Friday schedule.
     */
    protected HealthProfessional(String id, String name) {
        this(id, name, createWeekdays());
    }

    public final String getId() {
        return id;
    }

    public final String getName() {
        return name;
    }

    /**
     * Returns a copy to protect the internal availability collection.
     */
    public final ArrayList<String> getAvailableDays() {
        return new ArrayList<String>(availableDays);
    }

    public final boolean worksOn(String day) {
        return day != null && availableDays.contains(day.trim());
    }

    public abstract String getRoleDescription();

    public abstract String performDuty();

    @Override
    public int compareTo(HealthProfessional other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot compare with null.");
        }
        return id.compareTo(other.id);
    }

    /**
     * The ID is the identity of a professional, so equality is ID-based.
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

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "HealthProfessional{id='" + id
                + "', name='" + name
                + "', role='" + getRoleDescription()
                + "', availableDays=" + availableDays + "}";
    }

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

    protected static ArrayList<String> createWeekdays() {
        ArrayList<String> days = new ArrayList<String>();
        days.add(ClinicSchedule.MONDAY);
        days.add(ClinicSchedule.TUESDAY);
        days.add(ClinicSchedule.WEDNESDAY);
        days.add(ClinicSchedule.THURSDAY);
        days.add(ClinicSchedule.FRIDAY);
        return days;
    }

    protected static ArrayList<String> createMondayWednesdayFriday() {
        ArrayList<String> days = new ArrayList<String>();
        days.add(ClinicSchedule.MONDAY);
        days.add(ClinicSchedule.WEDNESDAY);
        days.add(ClinicSchedule.FRIDAY);
        return days;
    }

    protected static ArrayList<String> createMondayThursdayFriday() {
        ArrayList<String> days = new ArrayList<String>();
        days.add(ClinicSchedule.MONDAY);
        days.add(ClinicSchedule.THURSDAY);
        days.add(ClinicSchedule.FRIDAY);
        return days;
    }
}
