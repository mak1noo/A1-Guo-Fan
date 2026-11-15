import java.util.ArrayList;

/**
 * Local contract checks for the final Assessment 1 implementation.
 * This file is used for verification and is excluded from the submission ZIP.
 */
public class OopContractCheck {
    public static void main(String[] args) {
        checkScheduleContract();
        checkPatientNormalization();
        checkAppointmentScheduleValidation();
        checkManagerContracts();
        System.out.println("CONTRACT CHECK PASSED");
    }

    private static void checkScheduleContract() {
        ArrayList<TimeSlot> slots = ClinicSchedule.getDailySlots();
        check(slots.size() == 16, "The schedule must contain 16 half-hour slots.");
        check(new TimeSlot(8, 0).equals(slots.get(0)),
                "The first slot must be 08:00.");
        check(new TimeSlot(15, 30).equals(slots.get(15)),
                "The last bookable slot must be 15:30.");
        check(!ClinicSchedule.isScheduledSlot(new TimeSlot(16, 0)),
                "16:00 must be treated as the closing time.");
        check("MONDAY".equals(ClinicSchedule.getCurrentDay()),
                "The simulated current day must be Monday.");
    }

    private static void checkPatientNormalization() {
        Patient patient = new Patient("Alex Morgan", " 0412 345-678 ");
        check("0412345678".equals(patient.getMobile()),
                "Spaces and hyphens must be removed from the stored mobile number.");
    }

    private static void checkAppointmentScheduleValidation() {
        GeneralPractitioner gp = new GeneralPractitioner(
                "1001", "Alice Brown", "GP-1001");
        Patient patient = new Patient("Alex Morgan", "0412345678");

        boolean rejected = false;
        try {
            new Appointment(patient, gp, new TimeSlot(16, 0));
        } catch (IllegalArgumentException exception) {
            rejected = true;
        }
        check(rejected, "An Appointment outside the clinic schedule must be rejected.");
    }

    private static void checkManagerContracts() {
        GeneralPractitioner gp = new GeneralPractitioner(
                "1001", "Alice Brown", "GP-1001");
        Nurse nurse = new Nurse("3001", "Emily Davis", "General Ward");
        Patient single = new Patient("Ava Martin", "0423456789");
        Patient multiple = new Patient("Mia Turner", "0412345678");

        AppointmentManager manager = new AppointmentManager();
        check("MONDAY".equals(manager.getClinicDay()),
                "The default manager must use the simulated current day.");

        check(manager.addAppointment(single, gp, new TimeSlot(9, 0))
                .startsWith("SUCCESS"), "A valid appointment must be added.");
        check(manager.cancelAppointmentByPhone("0423456789")
                .startsWith("SUCCESS"), "A single phone match must cancel directly.");

        check(manager.addAppointment(multiple, gp, new TimeSlot(10, 0))
                .startsWith("SUCCESS"), "The first multi-booking appointment must be added.");
        check(manager.addAppointment(multiple, nurse, new TimeSlot(11, 0))
                .startsWith("SUCCESS"), "The second multi-booking appointment must be added.");
        check(manager.cancelAppointmentByPhone("0412345678")
                .contains("multiple appointments"),
                "Multiple phone matches must return an ambiguity warning.");
        check(manager.cancelAppointmentByPhone("0412345678", new TimeSlot(10, 0))
                .startsWith("SUCCESS"),
                "The precise phone-and-time overload must resolve ambiguity.");

        boolean rejectedDay = false;
        try {
            new AppointmentManager("TUESDAY");
        } catch (IllegalArgumentException exception) {
            rejectedDay = true;
        }
        check(rejectedDay, "A manager for a day other than the current day must be rejected.");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
}
