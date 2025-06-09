
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;



public class PatientClass {                  //implements ActionListener
    // variabels
    static ArrayList<PatientClass> patientList = new ArrayList<>();

    private String patientName;
    private int patientAge;
    private String patientID;
    private String medicalHistory;
    private String patient_contactInfo;

    public PatientClass(String patientName,int patientAge,String patientID,String medicalHistory,String patient_contactInfo){
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientID = patientID;
        this.medicalHistory = medicalHistory;
        this.patient_contactInfo = patient_contactInfo;
    }


    public String getPatientName() { return patientName; }
    public void setPatientName(String name) {this.patientName = name;}

    public int getPatientAge() {return patientAge; }
    public void setPatientAge(int age) {this.patientAge = age;}

    public String getPatientID() {return patientID;}
    public void setPatientID(String id) {this.patientID = id;}

    public String getMedicalHistory() {return medicalHistory;}
    public void setMedicalHistory(String medicalHistory) {this.medicalHistory = medicalHistory;}

    public String getPatient_contactInfo() {return patient_contactInfo;}
    public void setPatient_contactInfo(String contact_number) {this.patient_contactInfo = contact_number;}


    public static void showAddPatientForm(JFrame mainFrame, JPanel firstPanel){
        //JFrame patientFrame = new JFrame();

        JPanel PatientPanel = new JPanel();
        PatientPanel.setLayout(new GridLayout(7, 2, 10,20)); // 5 rows, 2 columns (label + input)
        PatientPanel.setBorder(BorderFactory.createEmptyBorder(50, 70, 10, 70));
        //PatientPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        JLabel nameLabel = new JLabel("Patient Name:");
        JTextField nameField = new JTextField();

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();

        JLabel medicalHistoryLabel = new JLabel("Medical History:");
        JTextArea medicalHistoryField = new JTextArea();

        JLabel contactLabel = new JLabel("Contact Number:");
        JTextField contactField = new JTextField();

        // add all the components into the 
        PatientPanel.add(nameLabel);
        PatientPanel.add(nameField);

        PatientPanel.add(ageLabel);
        PatientPanel.add(ageField);
/*
        PatientPanel.add(genderLabel);
        PatientPanel.add(genderButton);
        PatientPanel.add(femaleButton);
*/

        PatientPanel.add(idLabel);
        PatientPanel.add(idField);

        PatientPanel.add(medicalHistoryLabel);
        PatientPanel.add(medicalHistoryField);

        PatientPanel.add(contactLabel);
        PatientPanel.add(contactField);

        
        
        JPanel navigationPanel = new JPanel();                 // Navigation panel
        navigationPanel.setLayout(new GridLayout(1,3,50,10));

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(Color.GREEN);
        submitButton.setOpaque(true);    // back ground color to show required

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.RED);
        cancelButton.setOpaque(true);

        JButton backButton = new JButton("Back");
        navigationPanel.add(backButton);
        navigationPanel.add(submitButton);
        navigationPanel.add(cancelButton);

        // replacing the old frame with the new one

        mainFrame.getContentPane().removeAll();
        mainFrame.add(BorderLayout.NORTH,PatientPanel);
        mainFrame.add(BorderLayout.SOUTH, navigationPanel);



        mainFrame.revalidate();
        mainFrame.repaint();

        submitButton.addActionListener(new ActionListener(){      // adding actionListener to the sibmitButtton
            @Override
            public void actionPerformed( ActionEvent e){    

                String name = nameField.getText();                
                int age = Integer.parseInt(ageField.getText());
                String id = idField.getText();
                String medicalHistory = medicalHistoryField.getText();
                String contact = contactField.getText();

                // Create a new patient
                PatientClass newPatient = new PatientClass(name, age, id, medicalHistory, contact);

                // Add to patient list
                patientList.add(newPatient);


                JOptionPane.showMessageDialog(null, "submission completed!", "Title", JOptionPane.INFORMATION_MESSAGE);
                // Clear form
                nameField.setText("");
                ageField.setText("");
                idField.setText("");
                medicalHistoryField.setText("");
                contactField.setText("");
                }

            });

        cancelButton.addActionListener( new ActionListener() {          // cancle listener
            @Override
            public void actionPerformed(ActionEvent e){
            JOptionPane.showMessageDialog(null, "Felege Gion Hospital \nThanks for using our Hospital", "By", JOptionPane.INFORMATION_MESSAGE);          
            System.exit(0);
            }
        });
// back to te main frame
        backButton.addActionListener(new ActionListener() {    
            @Override
            public void actionPerformed(ActionEvent e){

                mainFrame.getContentPane().removeAll();/*
                mainFrame.add(BorderLayout.NORTH, welcomeText);
                mainFrame.add(BorderLayout.CENTER, welcome_2); */
                mainFrame.add(BorderLayout.SOUTH,firstPanel);
                

                mainFrame.repaint();
                mainFrame.revalidate();

            }
        });
    }    
}
