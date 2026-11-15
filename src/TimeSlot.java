import java.util.Objects;

/**
 * Represents one bookable half-hour clinic time.
 */
public class TimeSlot implements Comparable<TimeSlot> {
    private final int hour;
    private final int minute;

    /**
     * Creates a validated half-hour time.
     *
     * @param hour the hour in 24-hour time, from 0 to 23
     * @param minute the minute value, either 0 or 30
     */
    public TimeSlot(int hour, int minute) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hour must be between 0 and 23.");
        }
        if (minute != 0 && minute != 30) {
            throw new IllegalArgumentException("Minute must be 0 or 30.");
        }
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Returns the hour value.
     *
     * @return the hour in 24-hour time
     */
    public int getHour() {
        return hour;
    }

    /**
     * Returns the minute value.
     *
     * @return either 0 or 30
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Converts this slot to minutes after midnight for reliable ordering.
     *
     * @return minutes after midnight
     */
    public int getMinuteOfDay() {
        return hour * 60 + minute;
    }

    /**
     * Compares this slot with another slot by time.
     *
     * @param other the slot to compare with
     * @return a negative value, zero, or a positive value based on time order
     */
    @Override
    public int compareTo(TimeSlot other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot compare with null.");
        }
        return Integer.compare(getMinuteOfDay(), other.getMinuteOfDay());
    }

    /**
     * Compares two time slots by hour and minute.
     *
     * @param other the object to compare with
     * @return true when both hour and minute match
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        TimeSlot slot = (TimeSlot) other;
        return hour == slot.hour && minute == slot.minute;
    }

    /**
     * Returns a hash code consistent with equals.
     *
     * @return the hash code for the hour and minute
     */
    @Override
    public int hashCode() {
        return Objects.hash(hour, minute);
    }

    /**
     * Returns the time in HH:mm format.
     *
     * @return the formatted time
     */
    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }
}
