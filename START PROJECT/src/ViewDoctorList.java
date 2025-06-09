import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewDoctorList extends JPanel {
    private JTable doctorTable;
    private DefaultTableModel tableModel;
    private JFrame mainFrame;
    private JLabel statusLabel; // Add status label

    public ViewDoctorList(JFrame mainFrame, JPanel firstPanel) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Create table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Age");
        tableModel.addColumn("Specialization");
        tableModel.addColumn("Contact");

        // Table setup
        doctorTable = new JTable(tableModel);
        doctorTable.setPreferredScrollableViewportSize(new Dimension(800, 400)); // Set preferred size
        doctorTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(doctorTable);

        // Status label
        statusLabel = new JLabel("No doctors found");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back to Main Menu");
        JButton refreshButton = new JButton("Refresh Data");

        refreshButton.addActionListener(e -> refreshTable());
        backButton.addActionListener(e -> returnToMainMenu(firstPanel));

        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        // Add components
        add(scrollPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Initial refresh
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0); // Clear existing data
        
        if (DoctorClass.doctorList.isEmpty()) {
            statusLabel.setText("No doctors found in the system");
            return;
        }

        for (DoctorClass doctor : DoctorClass.doctorList) {
            tableModel.addRow(new Object[]{
                doctor.getDoctID(),
                doctor.getDoctName(),
                doctor.getDoctAge(),
                doctor.getSpecialization(),
                doctor.getDoctContactInfo()
            });
        }
        
        statusLabel.setText("Found " + DoctorClass.doctorList.size() + " doctors");
    }

    private void returnToMainMenu(JPanel firstPanel) {
        mainFrame.getContentPane().removeAll();
        mainFrame.add(firstPanel, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
