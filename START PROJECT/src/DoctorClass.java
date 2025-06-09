import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

public class DoctorClass {
    private String doctName;
    private String doctAge;
    private String doctID;
    private String specialization;
    private String doctContactInfo;

    static ArrayList<DoctorClass> doctorList = new ArrayList<>();

    public DoctorClass(String doctName, String doctAge, String doctID, 
        String specialization, String doctContactInfo) {
        this.doctName = doctName;
        this.doctAge = doctAge;
        this.doctID = doctID;
        this.specialization = specialization;
        this.doctContactInfo = doctContactInfo;
    }

    // Getters
    public String getDoctName() { return doctName; }
    public String getDoctAge() { return doctAge; }
    public String getDoctID() { return doctID; }
    public String getSpecialization() { return specialization; }
    public String getDoctContactInfo() { return doctContactInfo; }

    // Input validation method
    private static boolean validateInput(String name, String age, String id, String specialization, String contact) {
        if (name.isEmpty() || age.isEmpty() || id.isEmpty() || specialization.isEmpty() || contact.isEmpty()) {
            return false;
        }
        
        try {
            Integer.parseInt(age); // Check if age is numeric
        } catch (NumberFormatException e) {
            return false;
        }
        
        // Add more validation as needed
        return true;
    }

    public static void showAddDoctorForm(JFrame mainFrame, JPanel mainPanel) {
        JPanel doctorPanel = new JPanel();
        doctorPanel.setLayout(new GridLayout(6, 2, 5, 5));
        doctorPanel.setBorder(BorderFactory.createEmptyBorder(100, 20, 300, 20));

        // Form fields
        JLabel nameLabel = new JLabel("DOCTOR NAME:");
        JTextField nameField = new JTextField(100);

        JLabel ageLabel = new JLabel("AGE:");
        JTextField ageField = new JTextField(20);

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(20);

        JLabel specialzationLabel = new JLabel("SPECIALIZATION:");
        JTextField specializationField = new JTextField(20);

        JLabel contactLabel = new JLabel("CONTACT:");
        JTextField contactField = new JTextField(20);

        // Add labels and fields
        doctorPanel.add(nameLabel);
        doctorPanel.add(nameField);
        doctorPanel.add(ageLabel);
        doctorPanel.add(ageField);
        doctorPanel.add(idLabel);
        doctorPanel.add(idField);
        doctorPanel.add(specialzationLabel);
        doctorPanel.add(specializationField);
        doctorPanel.add(contactLabel);
        doctorPanel.add(contactField);

        // Submit button
        JButton submitButton = new JButton("Add Doctor");
        submitButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String age = ageField.getText().trim();
            String id = idField.getText().trim();
            String specialization = specializationField.getText().trim();
            String contactInfo = contactField.getText().trim();

            if (!validateInput(name, age, id, specialization, contactInfo)) {
                JOptionPane.showMessageDialog(mainFrame, 
                    "Please fill all fields correctly!\nAge must be a number.", 
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DoctorClass newDoctor = new DoctorClass(name, age, id, specialization, contactInfo);
            doctorList.add(newDoctor);

            JOptionPane.showMessageDialog(mainFrame, "Doctor added successfully!");
            clearFields(nameField, ageField, idField, specializationField, contactField);
            
            // Return to main menu which will then allow fresh viewing of doctor list
            mainFrame.getContentPane().removeAll();
            mainFrame.add(mainPanel, BorderLayout.CENTER);
            mainFrame.revalidate();
            mainFrame.repaint();
        });
        // Back button
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> {
            mainFrame.getContentPane().removeAll();
            mainFrame.add(mainPanel, BorderLayout.CENTER);
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);

        // Main container
        JPanel doctorContainer = new JPanel(new BorderLayout());
        doctorContainer.add(doctorPanel, BorderLayout.CENTER);
        doctorContainer.add(buttonPanel, BorderLayout.SOUTH);

        // Update main frame
        mainFrame.getContentPane().removeAll();
        mainFrame.add(doctorContainer);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private static void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
}