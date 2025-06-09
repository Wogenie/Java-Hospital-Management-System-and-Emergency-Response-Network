import javax.swing.*;
import java.awt.*;
//import javax.swing.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AppointmentClass {

    private String patientName;
    private String patientID;
    private String doctorName;
    private String doctID;
    private String date;
    private String time;
    private static ArrayList<AppointmentClass> appointmentList = new ArrayList<>();

    public AppointmentClass(String patientName, String patientID, String doctorName, String doctID, String date, String time) {
        this.patientName = patientName;
        this.patientID = patientID;
        this.doctorName = doctorName;
        this.doctID = doctID;
        this.date = date;
        this.time = time;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientID() {
        return patientID;
    }
    public String getDoctorName() {
        return doctorName;
    }
    public String getDoctID() {
        return doctID;
    }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public static ArrayList<AppointmentClass> getAppointmentList() {
        return appointmentList;
    }

    public static void makeAppointment(JFrame mainFrame, JPanel mainPanel) {
        JLabel welcomeLabel = new JLabel("Welcome to the Appointment Environment!");
        welcomeLabel.setFont(new Font("Arial", Font.ITALIC, 30));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel appointMainPanel = new JPanel();      // To hold the two containers
        JPanel appointLeftPanel = new JPanel();      // To hold data related to doctor
        JPanel appointRightPanel = new JPanel();     // To hold data related to patient

        JPanel navigationPanel = new JPanel();            // 
        navigationPanel.setLayout(new GridLayout(2,2));

        appointMainPanel.setLayout(new GridLayout(1,2));
        appointMainPanel.setBorder(BorderFactory.createEmptyBorder(200, 10,200, 10));
        appointLeftPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 200, 50));
        appointRightPanel.setBorder(BorderFactory.createEmptyBorder(50,50,200,50));

        mainFrame.setBackground(Color.black);
        appointMainPanel.setOpaque(true);

        appointLeftPanel.setLayout(new GridLayout(6,1));
        appointRightPanel.setLayout(new GridLayout(6,1));

        // Doctor Side
        JLabel doctorNameLabel = new JLabel("Enter the name of doctor: ");
        JTextField searchDoctorName = new JTextField();
        JLabel doctorIDLabel = new JLabel("Enter Doctor ID: ");
        JTextField searchDoctorID = new JTextField();
        
        JButton searchDoctorButton = new JButton("Search");
        JButton backButton = new JButton("Back to Main Frame");
        JButton resetButton = new JButton("Reset Data");

        // Adding components to the doctor panel
        appointLeftPanel.add(doctorNameLabel);
        appointLeftPanel.add(searchDoctorName);
        appointLeftPanel.add(doctorIDLabel);
        appointLeftPanel.add(searchDoctorID);

        // Patient Side
        JLabel patientNameLabel = new JLabel("Enter Patient Name: ");
        JTextField searchPatientName = new JTextField();
        JLabel patientIDLabel = new JLabel("Enter Patient ID: ");
        JTextField searchPatientID = new JTextField();
        JLabel appointmentDateLabel = new JLabel("Select Date (YYYY-MM-DD): ");
        JTextField appointmentDateField = new JTextField();
        JLabel appointmentTimeLabel = new JLabel("Select Time (HH:MM): ");
        JTextField appointmentTimeField = new JTextField();
        JButton makeAppointmentButton = new JButton("Add Appointment");

        // Adding components to the patient panel
        appointRightPanel.add(patientNameLabel);
        appointRightPanel.add(searchPatientName);
        appointRightPanel.add(patientIDLabel);
        appointRightPanel.add(searchPatientID);

        appointRightPanel.add(appointmentDateLabel);
        appointRightPanel.add(appointmentDateField);
        appointRightPanel.add(appointmentTimeLabel);
        appointRightPanel.add(appointmentTimeField);
        appointRightPanel.add(makeAppointmentButton);

        navigationPanel.add(makeAppointmentButton);
        navigationPanel.add(searchDoctorButton);
        navigationPanel.add(backButton);
        navigationPanel.add(resetButton);
    

        // Add action listener for making appointment
        makeAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String patientName = searchPatientName.getText();
                String patientID = searchPatientID.getText();
                String doctorName = searchDoctorName.getText();
                String doctorID = searchDoctorID.getText();
                String date = appointmentDateField.getText();
                String time = appointmentTimeField.getText();

                if (patientName.isEmpty() || patientID.isEmpty() || 
                    doctorName.isEmpty() || doctorID.isEmpty() || 
                    date.isEmpty() || time.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "Please fill in all fields.");
                } else {

                    AppointmentClass newAppointment = new AppointmentClass(patientName, patientID, doctorName, doctorID, date, time);
                    appointmentList.add(newAppointment);
                    JOptionPane.showMessageDialog(mainFrame, "Appointment made successfully!\n" +"Patient: " + patientName + "\n" +"Doctor: " + doctorName + "\n" +"Date: " + date + " at " + time);
                    
                    // Clear fields after successful appointment
                    searchPatientName.setText("");
                    searchPatientID.setText("");
                    appointmentDateField.setText("");
                    appointmentTimeField.setText("");
                }
            }
        });
//backButton.addActionListener(e -> returnToMainMenu(firstPanel));
        
// action listener to searching doctor       
        searchDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String doctorID = searchDoctorID.getText();
                String doctorName = searchDoctorName.getText();
                boolean found = false;

                for (AppointmentClass appointment : appointmentList) {
                    if (doctorID.equals(appointment.getDoctID()) && doctorName.equals(appointment.getDoctorName())) {
                        JOptionPane.showMessageDialog(mainFrame, "Doctor " + appointment.getDoctorName() +
                                " (ID: " + doctorID + ") has an appointment with " + appointment.getPatientName());
                        found = true;
                        break; // Found match, no need to continue
                    }
                    }

                    if (!found) {
                        JOptionPane.showMessageDialog(mainFrame, "No appointment found for Doctor " + doctorName + " (ID: " + doctorID + ").");
                    }
                }
            });
// action listener to the back botton
        backButton.addActionListener(new ActionListener() {    
            @Override
            public void actionPerformed(ActionEvent e){

                mainFrame.getContentPane().removeAll();
                mainFrame.add(BorderLayout.SOUTH,mainPanel);
                

                mainFrame.repaint();
                mainFrame.revalidate();

            }
        });
// resat action listeneer
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPatientName.setText("");
                searchPatientID.setText("");
                searchDoctorName.setText("");
                searchDoctorID.setText("");
                appointmentDateField.setText("");
                appointmentTimeField.setText("");
            }
        });


        appointMainPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        appointLeftPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        appointRightPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        navigationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        // Final setup of the main panel
        appointMainPanel.add(appointLeftPanel);
        appointMainPanel.add(appointRightPanel); 
        

        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(welcomeLabel, BorderLayout.NORTH);
        mainFrame.getContentPane().add(appointMainPanel, BorderLayout.CENTER);
        mainFrame.add(navigationPanel,BorderLayout.SOUTH);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}