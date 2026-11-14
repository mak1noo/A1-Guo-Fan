import java.util.ArrayList;

/**
 * Stores the fixed clinic days and half-hour appointment slots.
 */
public final class ClinicSchedule {
    public static final String MONDAY = "Monday";
    public static final String TUESDAY = "Tuesday";
    public static final String WEDNESDAY = "Wednesday";
    public static final String THURSDAY = "Thursday";
    public static final String FRIDAY = "Friday";
    public static final String SATURDAY = "Saturday";
    public static final String SUNDAY = "Sunday";

    public static final String SLOT_08_00 = "08:00";
    public static final String SLOT_08_30 = "08:30";
    public static final String SLOT_09_00 = "09:00";
    public static final String SLOT_09_30 = "09:30";
    public static final String SLOT_10_00 = "10:00";
    public static final String SLOT_10_30 = "10:30";
    public static final String SLOT_11_00 = "11:00";
    public static final String SLOT_11_30 = "11:30";
    public static final String SLOT_12_00 = "12:00";
    public static final String SLOT_12_30 = "12:30";
    public static final String SLOT_13_00 = "13:00";
    public static final String SLOT_13_30 = "13:30";
    public static final String SLOT_14_00 = "14:00";
    public static final String SLOT_14_30 = "14:30";
    public static final String SLOT_15_00 = "15:00";
    public static final String SLOT_15_30 = "15:30";
    public static final String SLOT_16_00 = "16:00";

    private static final ArrayList<String> VALID_DAYS = createValidDays();
    private static final ArrayList<String> DAILY_SLOTS = createDailySlots();

    private ClinicSchedule() {
        // Utility class; instances are not required.
    }

    public static ArrayList<String> getValidDays() {
        return new ArrayList<String>(VALID_DAYS);
    }

    public static ArrayList<String> getDailySlots() {
        return new ArrayList<String>(DAILY_SLOTS);
    }

    public static boolean isValidDay(String day) {
        return day != null && VALID_DAYS.contains(day.trim());
    }

    public static boolean isScheduledSlot(String timeSlot) {
        return timeSlot != null && DAILY_SLOTS.contains(timeSlot.trim());
    }

    private static ArrayList<String> createValidDays() {
        ArrayList<String> days = new ArrayList<String>();
        days.add(MONDAY);
        days.add(TUESDAY);
        days.add(WEDNESDAY);
        days.add(THURSDAY);
        days.add(FRIDAY);
        days.add(SATURDAY);
        days.add(SUNDAY);
        return days;
    }

    private static ArrayList<String> createDailySlots() {
        ArrayList<String> slots = new ArrayList<String>();
        slots.add(SLOT_08_00);
        slots.add(SLOT_08_30);
        slots.add(SLOT_09_00);
        slots.add(SLOT_09_30);
        slots.add(SLOT_10_00);
        slots.add(SLOT_10_30);
        slots.add(SLOT_11_00);
        slots.add(SLOT_11_30);
        slots.add(SLOT_12_00);
        slots.add(SLOT_12_30);
        slots.add(SLOT_13_00);
        slots.add(SLOT_13_30);
        slots.add(SLOT_14_00);
        slots.add(SLOT_14_30);
        slots.add(SLOT_15_00);
        slots.add(SLOT_15_30);
        slots.add(SLOT_16_00);
        return slots;
    }
}
