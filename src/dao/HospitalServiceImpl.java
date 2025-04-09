package dao;
import entity.*;
import myexceptions.PatientNumberNotFoundException;
import util.DBConnection;
import java.sql.*;
import java.util.*;

public class HospitalServiceImpl implements IHospitalService {
    private Connection con;
    public HospitalServiceImpl() throws SQLException {
        con = DBConnection.getConnection();
    }
    @Override
    public Appointment getAppointmentById(int id) {
        Appointment a = null;
        try {
            PreparedStatement ps = con.prepareStatement("select * from appointment where appointmentid = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                a = new Appointment(
                    rs.getInt("appointmentid"),
                    rs.getInt("patientid"),
                    rs.getInt("doctorid"),
                    rs.getDate("appointmentdate"),
                    rs.getString("description")
                );
            }
        } catch (Exception e) {
            System.out.println("Problem getting appointment");
            e.printStackTrace();
        }
        return a;
    }
    @Override
    public HashMap<Integer, Appointment> getAppointmentsForPatient(int pid) throws PatientNumberNotFoundException {
        HashMap<Integer, Appointment> hm = new HashMap<>();
        try {
            PreparedStatement ps = con.prepareStatement("select * from appointment where patientid = ?");
            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment a = new Appointment(
                    rs.getInt("appointmentid"),
                    rs.getInt("patientid"),
                    rs.getInt("doctorid"),
                    rs.getDate("appointmentdate"),
                    rs.getString("description")
                );
                hm.put(a.getAppointmentId(), a);
            }
        } catch (Exception e) {
            System.out.println("Problem getting patient appointments");
            e.printStackTrace();
        }
        return hm;
    }
    @Override
    public HashMap<Integer, Appointment> getAppointmentsForDoctor(int did) {
        HashMap<Integer, Appointment> hm = new HashMap<>();
        try {
            PreparedStatement ps = con.prepareStatement("select * from appointment where doctorid = ?");
            ps.setInt(1, did);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment a = new Appointment(
                    rs.getInt("appointmentid"),
                    rs.getInt("patientid"),
                    rs.getInt("doctorid"),
                    rs.getDate("appointmentdate"),
                    rs.getString("description")
                );
                hm.put(a.getAppointmentId(), a);
            }
        } catch (Exception e) {
            System.out.println("Problem getting doctor appointments");
            e.printStackTrace();
        }
        return hm;
    }
    @Override
    public boolean scheduleAppointment(Appointment a) {
        boolean done = false;
        try {
            PreparedStatement ps = con.prepareStatement("insert into appointment values (?, ?, ?, ?, ?)");
            ps.setInt(1, a.getAppointmentId());
            ps.setInt(2, a.getPatientId());
            ps.setInt(3, a.getDoctorId());
            ps.setDate(4, new java.sql.Date(a.getAppointmentDate().getTime()));
            ps.setString(5, a.getDescription());
            ps.executeUpdate();
            done = true;
        } catch (Exception e) {
            System.out.println("Problem scheduling appointment");
            e.printStackTrace();
        }
        return done;
    }
    @Override
    public boolean updateAppointment(Appointment a) {
        boolean updated = false;
        try {
            PreparedStatement ps = con.prepareStatement("update appointment set patientid = ?, doctorid = ?, appointmentdate = ?, description = ? where appointmentid = ?");
            ps.setInt(1, a.getPatientId());
            ps.setInt(2, a.getDoctorId());
            ps.setDate(3, new java.sql.Date(a.getAppointmentDate().getTime()));
            ps.setString(4, a.getDescription());
            ps.setInt(5, a.getAppointmentId());
            ps.executeUpdate();
            updated = true;
        } catch (Exception e) {
            System.out.println("Couldn't updating appointment");
            e.printStackTrace();
        }
        return updated;
    }
    @Override
    public boolean CancelAppointment(int id) {
        boolean deleted = false;
        try {
            PreparedStatement ps = con.prepareStatement("delete from appointment where appointmentid = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            deleted = true;
        } catch (Exception e) {
            System.out.println("Couldn't cancel your appointment!");
            e.printStackTrace();
        }
        return deleted;
    }
    public void close() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
            System.out.println("Problem closing connection");
            e.printStackTrace();
        }
    }
}
