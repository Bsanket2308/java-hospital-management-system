import java.sql.Timestamp;

public class Appointment {
    int id;
    int patientId;
    int doctorId;
    Timestamp appointmentTime;
    String status;

    public Appointment(int id, int patientId, int doctorId, Timestamp appointmentTime, String status) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    public String toString() {
        return "Appointment #" + id + " | Patient: " + patientId + " | Doctor: " + doctorId +
                " | Time: " + appointmentTime + " | Status: " + status;
    }
}