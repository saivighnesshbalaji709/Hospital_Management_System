package dao;
import java.util.*;
import entity.*;
import myexceptions.PatientNumberNotFoundException;
public interface IHospitalService {
	public Appointment getAppointmentById(int appointmentId);
	public HashMap<Integer, Appointment> getAppointmentsForPatient(int patientId) throws PatientNumberNotFoundException;
	public HashMap<Integer, Appointment> getAppointmentsForDoctor(int doctorId);
	public boolean scheduleAppointment(Appointment appointment);
	public boolean updateAppointment(Appointment appointment);
	public boolean CancelAppointment(int appointmentId);
}
