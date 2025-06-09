import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewPatient extends JPanel {
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private JFrame mainFrame; // Reference to main frame

    public ViewPatient(JFrame mainFrame, JPanel firsPanel) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Create table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Age");
        tableModel.addColumn("Contact");
        tableModel.addColumn("Medical History");

        refreshTable();

        // Table setup
        patientTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(patientTable);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back to Main Menu");
        JButton refreshButton = new JButton("Refresh Data");
        
        refreshButton.addActionListener(e -> refreshTable());
        backButton.addActionListener(e -> returnToMainMenu( firsPanel));

        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        // Add components into viewPatient Panel
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshTable() {
        tableModel.setRowCount(0); // Clear existing data
        
        for (PatientClass patient : PatientClass.patientList) {
            tableModel.addRow(new Object[]{
                patient.getPatientID(),
                patient.getPatientName(),
                patient.getPatientAge(),
                patient.getPatient_contactInfo(),
                patient.getMedicalHistory()
            });
        }
    }

    private void returnToMainMenu(JPanel firstPanel) {
        mainFrame.getContentPane().removeAll();
        mainFrame.add(BorderLayout.SOUTH, firstPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}