package mainmod;
import dao.*;
import entity.*;
import java.sql.Date;
import java.util.*;

public class MainModule {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        IHospitalService service = null;
        try {
            service = new HospitalServiceImpl();
            boolean running = true;
            while (running) {
                System.out.println("Welcome to Hospital Management System. Choose any one option to continue");
                System.out.println("1. Get Appointment By ID");
                System.out.println("2. View all the Appointments of Patient");
                System.out.println("3. View all the Appointments of Doctor");
                System.out.println("4. Schedule your Appointment");
                System.out.println("5. Update your Appointment");
                System.out.println("6. Cancel your Appointment");
                System.out.println("7. Exit");
                System.out.print("Kindly enter your option: ");
                int key = scan.nextInt();
                switch (key) {
                    case 1 -> {
                        try {
                            System.out.print("Enter appointment ID: ");
                            int id = scan.nextInt();
                            Appointment app = service.getAppointmentById(id);
                            if (app != null) {
                                System.out.println(app);
                            } else {
                                System.out.println("Appointment not found.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid appointment ID format.");
                            scan.nextLine();
                        }
                    }
                    case 2 -> {
                        try {
                            System.out.print("Enter patient ID: ");
                            int pid = scan.nextInt();
                            HashMap<Integer, Appointment> hm=service.getAppointmentsForPatient(pid);
                            Collection<Appointment> apps = hm.values();
                            System.out.println("Appointments of patient:");
                            for (Appointment a : apps) {
                                System.out.println(a);
                            }
                        } catch (Exception e) {
                            System.out.println("Couldn’t ftech the patient appointments.");
                            e.printStackTrace();
                        }
                    }
                    case 3 -> {
                        try {
                            System.out.print("Enter doctor ID: ");
                            int did = scan.nextInt();
                            HashMap<Integer, Appointment> hm = service.getAppointmentsForDoctor(did);
                            Collection<Appointment> apps = hm.values();
                            System.out.println("Appointments for doctor:");
                            for (Appointment a : apps) {
                                System.out.println(a);
                            }
                        } catch (Exception e) {
                            System.out.println("Couldn’t fetch doctor appointments.");
                            e.printStackTrace();
                        }
                    }
                    case 4 -> {
                        try {
                            System.out.print("Enter appointment ID: ");
                            int id = scan.nextInt();
                            System.out.print("Enter patient ID: ");
                            int pid = scan.nextInt();
                            System.out.print("Enter doctor ID: ");
                            int did = scan.nextInt();
                            scan.nextLine();
                            System.out.print("Enter appointment date (yyyy-mm-dd): ");
                            Date date = Date.valueOf(scan.nextLine());
                            System.out.print("Enter description: ");
                            String desc = scan.nextLine();
                            Appointment a = new Appointment(id, pid, did, date, desc);
                            if (service.scheduleAppointment(a)) {
                                System.out.println("Appointment has scheduled successfully.");
                            } else {
                                System.out.println("Failed to schedule an appointment.");
                            }
                        } catch (Exception e) {
                            System.out.println("Something went wrong while scheduling.");
                            e.printStackTrace();
                        }
                    }
                    case 5 -> {
                        try {
                            System.out.print("Enter appointment ID to update: ");
                            int id = scan.nextInt();
                            System.out.print("Enter patient ID: ");
                            int pid = scan.nextInt();
                            System.out.print("Enter doctor ID: ");
                            int did = scan.nextInt();
                            scan.nextLine();
                            System.out.print("Enter new appointment date (yyyy-mm-dd): ");
                            Date date = Date.valueOf(scan.nextLine());
                            System.out.print("Enter new description: ");
                            String desc = scan.nextLine();
                            Appointment a = new Appointment(id, pid, did, date, desc);
                            if (service.updateAppointment(a)) {
                                System.out.println("Appointment has been updated successfully.");
                            } else {
                                System.out.println("Update failed.");
                            }
                        } catch (Exception e) {
                            System.out.println("Could not update your appointment.");
                            e.printStackTrace();
                        }
                    }
                    case 6 -> {
                        try {
                            System.out.print("Enter appointment ID to cancel: ");
                            int id = scan.nextInt();
                            if (service.CancelAppointment(id)) {
                                System.out.println("Appointment cancelled successfully.");
                            } else {
                                System.out.println("Could not cancel appointment.");
                            }
                        } catch (Exception e) {
                            System.out.println("Couldn’t cancelling appointment.");
                            e.printStackTrace();
                        }
                    }
                    case 7 -> {
                        System.out.println("Thank you!");
                        running = false;
                    }
                    default -> {
                        System.out.println("Your option is invalid. Please try again with another option.");
                        scan.nextLine();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong. Try again");
            e.printStackTrace();
        } finally {
            scan.close();
        }
    }
}
