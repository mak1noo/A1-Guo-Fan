import java.util.ArrayList;
import java.util.Collections;

/**
 * Demonstrates the hospital appointment class model.
 */
public class AssignmentOne {
    private AssignmentOne() {
        // This class is used through its static main method.
    }

    /**
     * Runs the Part 3 and Part 6 console demonstration.
     *
     * @param args command-line arguments, which this application does not use
     */
    public static void main(String[] args) {
        System.out.println("PROG2004 Assessment 1 - Hospital Appointment System");

        GeneralPractitioner gpAlice = new GeneralPractitioner(
                "1001", "Alice Brown", "GP-LIC-1001");
        GeneralPractitioner gpChen = new GeneralPractitioner(
                "1002", "Chen Wei", "GP-LIC-1002");
        GeneralPractitioner gpPriya = new GeneralPractitioner(
                "1003", "Priya Shah", "GP-LIC-1003");

        ArrayList<String> cardiologyDays = new ArrayList<String>();
        cardiologyDays.add("MONDAY");
        cardiologyDays.add("WEDNESDAY");
        cardiologyDays.add("THURSDAY");
        Specialist specialistOlivia = new Specialist(
                "2001", "Olivia Reed", "Cardiology", cardiologyDays);

        ArrayList<String> neurologyDays = new ArrayList<String>();
        neurologyDays.add("TUESDAY");
        neurologyDays.add("THURSDAY");
        Specialist specialistMarcus = new Specialist(
                "2002", "Marcus Lee", "Neurology", neurologyDays);

        ArrayList<String> nurseEmilyDays = new ArrayList<String>();
        nurseEmilyDays.add("MONDAY");
        nurseEmilyDays.add("FRIDAY");
        Nurse nurseEmily = new Nurse(
                "3001", "Emily Davis", "General Ward", nurseEmilyDays);

        ArrayList<String> nurseDanielDays = new ArrayList<String>();
        nurseDanielDays.add("MONDAY");
        nurseDanielDays.add("TUESDAY");
        Nurse nurseDaniel = new Nurse(
                "3002", "Daniel Kim", "Triage", nurseDanielDays);

        Surgeon surgeonSarah = new Surgeon("4001", "Sarah Patel", "Theatre 2");

        ArrayList<HealthProfessional> professionals =
                new ArrayList<HealthProfessional>();
        professionals.add(gpAlice);
        professionals.add(gpChen);
        professionals.add(gpPriya);
        professionals.add(specialistOlivia);
        professionals.add(specialistMarcus);
        professionals.add(nurseEmily);
        professionals.add(nurseDaniel);
        professionals.add(surgeonSarah);

        printHeading("PART 3 - HEALTH PROFESSIONALS THROUGH THE BASE TYPE");
        Collections.sort(professionals);
        for (int i = 0; i < professionals.size(); i++) {
            HealthProfessional professional = professionals.get(i);
            System.out.println((i + 1) + ". " + professional);
            System.out.println("   Role: " + professional.getRoleDescription());
            System.out.println("   Duty: " + professional.performDuty());
        }

        printHeading("PART 3 - TYPE-SPECIFIC BEHAVIOUR");
        System.out.println(gpAlice.prescribeDrug("Mia Turner", "Amoxicillin"));
        System.out.println(specialistOlivia.referTo(
                "Noah Wilson", "Physiotherapy"));
        System.out.println(nurseEmily.takeBloodSample("Ava Martin"));
        System.out.println(surgeonSarah.scheduleOperation(
                "Leo Chen", "Appendectomy"));

        GeneralPractitioner sameIdentityReference = new GeneralPractitioner(
                "1001", "Alice Brown", "GP-REFERENCE-ONLY");
        System.out.println("ID equality check: "
                + gpAlice.equals(sameIdentityReference)
                + ", equal hash codes: "
                + (gpAlice.hashCode() == sameIdentityReference.hashCode()));

        Patient patientMia = new Patient("Mia Turner", "0412345678");
        Patient patientNoah = new Patient("Noah Wilson", "0498765432");
        Patient patientAva = new Patient("Ava Martin", "0423456789");
        Patient patientLeo = new Patient("Leo Chen", "0434567890");
        Patient patientZoe = new Patient("Zoe Taylor", "0445678901");

        AppointmentManager manager = new AppointmentManager();
        printHeading("PART 6 - CURRENT CLINIC DAY AND SCHEDULE");
        System.out.println("Clinic day: " + manager.getClinicDay());
        System.out.println("Daily schedule: " + ClinicSchedule.getDailySlots());

        printHeading("PART 6 - CREATE APPOINTMENTS");
        System.out.println(manager.addAppointment(
                patientMia, nurseEmily, new TimeSlot(11, 0)));
        System.out.println(manager.addAppointment(
                patientNoah, gpAlice, new TimeSlot(9, 0)));
        System.out.println(manager.addAppointment(
                patientAva, specialistOlivia, new TimeSlot(10, 30)));
        System.out.println(manager.addAppointment(
                patientLeo, gpChen, new TimeSlot(9, 30)));
        System.out.println(manager.addAppointment(
                patientZoe, surgeonSarah, new TimeSlot(10, 0)));
        System.out.println(manager.addAppointment(
                patientMia, gpChen, new TimeSlot(9, 0)));
        System.out.println(manager.addAppointment(
                patientNoah, nurseEmily, new TimeSlot(15, 30)));

        printHeading("PART 6 - DISPLAY ALL APPOINTMENTS");
        manager.displayAll();

        printHeading("PART 6 - FILTER BY PROFESSIONAL ID AND PATIENT PHONE");
        manager.displayByProfessional("1001");
        manager.displayByPatientPhone("0412345678");
        manager.displayByPatientPhoneSorted("0412345678");
        manager.displaySortedByTime();
        manager.displayByProfessionalSorted("1002");

        printHeading("PART 6 - CANCEL USING PHONE AND OTHER SEARCH KEYS");
        System.out.println(manager.cancelAppointmentByPhone("0423456789"));
        System.out.println(manager.cancelAppointmentByPhone("0412345678"));
        System.out.println(manager.cancelAppointmentByPhone(
                "0412345678", new TimeSlot(11, 0)));
        System.out.println(manager.cancelAppointmentByProfessional(
                "1002", new TimeSlot(9, 30)));
        System.out.println(manager.cancelAppointment(
                "Zoe Taylor", new TimeSlot(10, 0)));
        manager.displayAll();

        printHeading("PART 6 - ERROR CASE: DOUBLE BOOKING");
        System.out.println(manager.addAppointment(
                patientNoah, gpAlice, new TimeSlot(9, 0)));

        printHeading("PART 6 - ERROR CASE: CANCEL APPOINTMENT THAT DOES NOT EXIST");
        System.out.println(manager.cancelAppointment(
                "Noah Wilson", new TimeSlot(13, 0)));
        System.out.println(manager.cancelAppointmentByPhone("0499999999"));
        System.out.println(manager.cancelAppointmentByProfessional(
                "9999", new TimeSlot(10, 0)));

        printHeading("PART 6 - ERROR CASE: PROFESSIONAL NOT WORKING ON CLINIC DAY");
        System.out.println(manager.addAppointment(
                patientZoe, specialistMarcus, new TimeSlot(12, 0)));

        printHeading("PART 6 - ERROR CASE: 16:00 IS THE CLOSING TIME");
        System.out.println(manager.addAppointment(
                patientZoe, gpAlice, new TimeSlot(16, 0)));

        printHeading("PART 6 - FINAL APPOINTMENT COLLECTION");
        manager.displaySortedByTime();
        System.out.println("Total appointments remaining: "
                + manager.getAppointmentCount());
    }

    private static void printHeading(String text) {
        System.out.println();
        System.out.println("=== " + text + " ===");
    }
}
