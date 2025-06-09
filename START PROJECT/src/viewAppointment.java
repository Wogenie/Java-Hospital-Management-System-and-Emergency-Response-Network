import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class viewAppointment extends JPanel {
    private JTable appointTable;
    private DefaultTableModel tableModel;
    private JFrame mainFrame;

    public viewAppointment(JFrame mainFrame, JPanel mainPanel) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Create table model for APPOINTMENTS
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Patient Name");
        tableModel.addColumn("Patient ID");
        tableModel.addColumn("Doctor Name");
        tableModel.addColumn("Doctor ID");
        tableModel.addColumn("Date");
        tableModel.addColumn("Time");

        loadAppointments(); // Load data once when panel opens

        // Table setup
        appointTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(appointTable);

        // Only a back button now
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> returnToMainMenu(mainPanel));

    // Add components to panel
        add(scrollPane, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

    // Replace main frame content
        mainFrame.getContentPane().removeAll();
        mainFrame.add(this, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void loadAppointments() {
        tableModel.setRowCount(0); // Clear existing data

        // Load all appointments
        for (AppointmentClass appointment : AppointmentClass.getAppointmentList()) {
            tableModel.addRow(new Object[]{
                appointment.getPatientName(),
                appointment.getPatientID(),
                appointment.getDoctorName(),
                appointment.getDoctID(),
                appointment.getDate(),
                appointment.getTime()
            });
        }
    }

    private void returnToMainMenu(JPanel mainPanel) {
        mainFrame.getContentPane().removeAll();
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}