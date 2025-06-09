
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.Border;

public class Main {
   
    public static void main(String[] args){
        JFrame mainFrame = new JFrame("Hospital Management System");
        mainFrame.setSize(600,700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcomeText = new JLabel("WELCOME TO OUR HOSPITAL");
        JLabel  welcome_2 = new JLabel("MAIN MENU");
        welcomeText.setFont(new Font("Ariel", Font.ITALIC, 30));
        welcomeText.setHorizontalAlignment(SwingConstants.CENTER);
        welcome_2.setHorizontalAlignment(SwingConstants.CENTER);

        

        JPanel mainPanel = new JPanel();     // the first container 
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 100,300, 100));

        //Buttons

        JButton addPatientButton = new JButton("Add Patient");
        JButton viewPatientButton = new JButton("View Patients List");
        JButton addDoctorButton = new JButton("Add Doctor");
        JButton viewDoctorsList = new JButton("View Doctors List");
        JButton appoitButton = new JButton("Make Appointment");
        JButton viewAppointButton = new JButton("View Appointment Lists");
        JButton locationButton = new JButton("Get nearest hospital ");

// add all the buttons into the main Frame
        mainPanel.add(addPatientButton);
        mainPanel.add(viewPatientButton);
        mainPanel.add(addDoctorButton);
        mainPanel.add(viewDoctorsList);
        mainPanel.add(appoitButton);
        mainPanel.add(viewAppointButton);
        mainPanel.add(locationButton);

        mainPanel.setLayout(new GridLayout(7,1,50,5));
        
        mainFrame.add(BorderLayout.NORTH, welcomeText);
        mainFrame.add(BorderLayout.CENTER, welcome_2);
        mainFrame.add(BorderLayout.SOUTH,mainPanel);
// add patient Listener
        addPatientButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                // call the static method using its class name
                PatientClass.showAddPatientForm(mainFrame,mainPanel);
                
                
            }
        });
// view patient list listener
        viewPatientButton.addActionListener(e -> {
            
            int attempt = 1;
            while(attempt < 6){
                String password = JOptionPane.showInputDialog("Admin Password");
            
                if (password != null && password.equals("Wogenie@2127")) {
                    // Clear main frame and show patient view
                    mainFrame.getContentPane().removeAll();
                    mainFrame.add(new ViewPatient(mainFrame, mainPanel)); // Pass mainFrame reference
                    mainFrame.revalidate();
                    mainFrame.repaint(); 
                    break;            
                }
                attempt++;

            }
            if(attempt > 5){
                JOptionPane.showMessageDialog(mainFrame, "Warning", "You finished your Trial", JOptionPane.ERROR_MESSAGE);
            }
        });
// doctor button Listener
        addDoctorButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                DoctorClass.showAddDoctorForm(mainFrame, mainPanel);
            }
        });
// view doct List listener
        viewDoctorsList.addActionListener(e -> {
            
            int attempt = 1;
            while(attempt < 6){
                String password = JOptionPane.showInputDialog("Admin Password");
            
                if (password != null && password.equals("Wogenie@2127")) {
                    // Clear main frame and show patient view
                    mainFrame.getContentPane().removeAll();
                    mainFrame.add(new ViewDoctorList(mainFrame, mainPanel)); // Pass mainFrame reference
                    mainFrame.revalidate();
                    mainFrame.repaint(); 
                    break;            
                }
                attempt++;

            }
            if(attempt > 5){
                JOptionPane.showMessageDialog(mainFrame, "Warning", "You finished your Trial", JOptionPane.ERROR_MESSAGE);
            }
        });
// appointment Listener      
        appoitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                AppointmentClass.makeAppointment(mainFrame, mainPanel);
            }
                });

            viewAppointButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    mainFrame.getContentPane().removeAll();
                    mainFrame.add(new viewAppointment(mainFrame, mainPanel)); // Temporary fix
                    mainFrame.revalidate();
                    mainFrame.repaint();
                }
            });
        
// location button cliked
        locationButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                HospitalFinder findHospitalObject = new HospitalFinder();
                //findHospital.methodName
                findHospitalObject.findHospital(mainFrame, mainPanel);
               
            }
        });




        
        
        mainFrame.setVisible(true);

    }
}
