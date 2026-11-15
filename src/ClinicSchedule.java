import java.util.ArrayList;

/**
 * Stores the simulated current day and the fixed daily clinic schedule.
 */
public final class ClinicSchedule {
    /** Monday. */
    public static final String MONDAY = "MONDAY";
    /** Tuesday. */
    public static final String TUESDAY = "TUESDAY";
    /** Wednesday. */
    public static final String WEDNESDAY = "WEDNESDAY";
    /** Thursday. */
    public static final String THURSDAY = "THURSDAY";
    /** Friday. */
    public static final String FRIDAY = "FRIDAY";
    /** Saturday. */
    public static final String SATURDAY = "SATURDAY";
    /** Sunday. */
    public static final String SUNDAY = "SUNDAY";

    /** The simulated current clinic day used by this console application. */
    public static final String CURRENT_DAY = MONDAY;

    private static final int START_MINUTES = 8 * 60;
    private static final int END_MINUTES = 16 * 60;
    private static final int SLOT_MINUTES = 30;
    private static final int MINUTES_PER_HOUR = 60;

    private static final ArrayList<String> VALID_DAYS = createValidDays();
    private static final ArrayList<TimeSlot> DAILY_SLOTS = createDailySlots();

    private ClinicSchedule() {
        // This class provides constants and utility methods only.
    }

    /**
     * Returns the simulated current clinic day.
     *
     * @return the current day name
     */
    public static String getCurrentDay() {
        return CURRENT_DAY;
    }

    /**
     * Returns a copy of the valid day names.
     *
     * @return a new list containing every valid day
     */
    public static ArrayList<String> getValidDays() {
        return new ArrayList<String>(VALID_DAYS);
    }

    /**
     * Returns a copy of the bookable daily time slots.
     *
     * @return a new list containing 08:00 through 15:30
     */
    public static ArrayList<TimeSlot> getDailySlots() {
        return new ArrayList<TimeSlot>(DAILY_SLOTS);
    }

    /**
     * Checks whether a day name is valid.
     *
     * @param day the day name to validate
     * @return true when the trimmed name is a recognised day
     */
    public static boolean isValidDay(String day) {
        return day != null && VALID_DAYS.contains(day.trim().toUpperCase());
    }

    /**
     * Checks whether a time is part of the clinic schedule.
     *
     * @param timeSlot the time to validate
     * @return true when the time is a bookable clinic slot
     */
    public static boolean isScheduledSlot(TimeSlot timeSlot) {
        return timeSlot != null && DAILY_SLOTS.contains(timeSlot);
    }

    /**
     * Checks whether a time falls inside the clinic's opening hours.
     * The opening time is inclusive and the closing time is exclusive.
     *
     * @param timeSlot the time to validate
     * @return true when the time is a valid half-hour time before closing
     */
    public static boolean isWithinOpeningHours(TimeSlot timeSlot) {
        if (timeSlot == null) {
            return false;
        }
        int minuteOfDay = timeSlot.getMinuteOfDay();
        return minuteOfDay >= START_MINUTES && minuteOfDay < END_MINUTES;
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

    private static ArrayList<TimeSlot> createDailySlots() {
        ArrayList<TimeSlot> slots = new ArrayList<TimeSlot>();
        for (int minutes = START_MINUTES; minutes < END_MINUTES;
                minutes += SLOT_MINUTES) {
            int hour = minutes / MINUTES_PER_HOUR;
            int minute = minutes % MINUTES_PER_HOUR;
            slots.add(new TimeSlot(hour, minute));
        }
        return slots;
    }
}
