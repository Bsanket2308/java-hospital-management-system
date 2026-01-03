import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class HospitalManagementSystem extends JFrame {

    Connection con = DBConnection.getConnection();

    JTextField nameField, ageField, genderField, doctorNameField, specializationField;
    JButton addPatientBtn, viewPatientsBtn, bookAppointmentBtn, viewAppointmentsBtn;
    JButton addDoctorBtn, viewDoctorsBtn, cancelAppointmentBtn;
    JTextArea displayArea;

    public HospitalManagementSystem() {
        setTitle("Hospital Appointment Booking System");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // --- Labels & Input Fields ---
        JLabel nameLabel = new JLabel("Patient Name:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel genderLabel = new JLabel("Gender:");

        nameField = new JTextField(10);
        ageField = new JTextField(5);
        genderField = new JTextField(8);

        JLabel doctorNameLabel = new JLabel("Doctor Name:");
        JLabel specializationLabel = new JLabel("Specialization:");

        doctorNameField = new JTextField(10);
        specializationField = new JTextField(10);

        // --- Buttons ---
        addPatientBtn = new JButton("Add Patient");
        viewPatientsBtn = new JButton("View Patients");
        addDoctorBtn = new JButton("Add Doctor");
        viewDoctorsBtn = new JButton("View Doctors");
        bookAppointmentBtn = new JButton("Book Appointment");
        viewAppointmentsBtn = new JButton("View Appointments");
        cancelAppointmentBtn = new JButton("Cancel Appointment");

        // --- Display Area ---
        displayArea = new JTextArea(18, 60);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Consolas", Font.PLAIN, 13));

        // --- Add Components ---
        add(nameLabel);
        add(nameField);
        add(ageLabel);
        add(ageField);
        add(genderLabel);
        add(genderField);
        add(addPatientBtn);
        add(viewPatientsBtn);

        add(new JLabel("------ Doctor Section ------"));
        add(doctorNameLabel);
        add(doctorNameField);
        add(specializationLabel);
        add(specializationField);
        add(addDoctorBtn);
        add(viewDoctorsBtn);

        add(bookAppointmentBtn);
        add(viewAppointmentsBtn);
        add(cancelAppointmentBtn);
        add(new JScrollPane(displayArea));

        // --- Background Design ---
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // --- Button Colors ---
        addPatientBtn.setBackground(Color.GREEN);
        viewPatientsBtn.setBackground(Color.CYAN);
        addDoctorBtn.setBackground(Color.YELLOW);
        viewDoctorsBtn.setBackground(Color.ORANGE);
        bookAppointmentBtn.setBackground(Color.PINK);
        viewAppointmentsBtn.setBackground(Color.MAGENTA);
        cancelAppointmentBtn.setBackground(Color.RED);
        cancelAppointmentBtn.setForeground(Color.WHITE);

        // --- Button Actions ---
        addPatientBtn.addActionListener(e -> addPatient());
        viewPatientsBtn.addActionListener(e -> viewPatients());
        addDoctorBtn.addActionListener(e -> addDoctor());
        viewDoctorsBtn.addActionListener(e -> viewDoctors());
        bookAppointmentBtn.addActionListener(e -> bookAppointment());
        viewAppointmentsBtn.addActionListener(e -> viewAppointments());
        cancelAppointmentBtn.addActionListener(e -> cancelAppointment());
    }

    // --- Add Patient ---
    void addPatient() {
        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = genderField.getText();

            PreparedStatement pst = con.prepareStatement("INSERT INTO patients(name, age, gender) VALUES (?, ?, ?)");
            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3, gender);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Patient Added Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- View Patients ---
    void viewPatients() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patients");
            displayArea.setText("=== Patient List ===\n");
            while (rs.next()) {
                displayArea.append("ID: " + rs.getInt("id") + " | Name: " + rs.getString("name")
                        + " | Age: " + rs.getInt("age") + " | Gender: " + rs.getString("gender") + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Add Doctor ---
    void addDoctor() {
        try {
            String name = doctorNameField.getText();
            String specialization = specializationField.getText();

            PreparedStatement pst = con.prepareStatement("INSERT INTO doctors(name, specialization) VALUES (?, ?)");
            pst.setString(1, name);
            pst.setString(2, specialization);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Doctor Added Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- View Doctors ---
    void viewDoctors() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM doctors");
            displayArea.setText("=== Doctor List ===\n");
            while (rs.next()) {
                displayArea.append("ID: " + rs.getInt("id") + " | Name: " + rs.getString("name")
                        + " | Specialization: " + rs.getString("specialization") + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Book Appointment ---
    void bookAppointment() {
        try {
            String pid = JOptionPane.showInputDialog(this, "Enter Patient ID:");
            String did = JOptionPane.showInputDialog(this, "Enter Doctor ID:");
            PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO appointments(patient_id, doctor_id, appointment_time, status) VALUES (?, ?, NOW(), 'Booked')");
            pst.setInt(1, Integer.parseInt(pid));
            pst.setInt(2, Integer.parseInt(did));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "✅ Appointment Booked!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- View Appointments ---
    void viewAppointments() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM appointments");
            displayArea.setText("=== Appointment List ===\n");
            while (rs.next()) {
                displayArea.append("Appointment #" + rs.getInt("id")
                        + " | Patient ID: " + rs.getInt("patient_id")
                        + " | Doctor ID: " + rs.getInt("doctor_id")
                        + " | Time: " + rs.getTimestamp("appointment_time")
                        + " | Status: " + rs.getString("status") + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Cancel Appointment ---
    void cancelAppointment() {
        try {
            String aid = JOptionPane.showInputDialog(this, "Enter Appointment ID to Cancel:");
            PreparedStatement pst = con.prepareStatement("UPDATE appointments SET status='Cancelled' WHERE id=?");
            pst.setInt(1, Integer.parseInt(aid));
            int updated = pst.executeUpdate();

            if (updated > 0)
                JOptionPane.showMessageDialog(this, "❌ Appointment Cancelled!");
            else
                JOptionPane.showMessageDialog(this, "⚠️ No Appointment Found with that ID!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HospitalManagementSystem().setVisible(true));
    }
}

