package aTerolGroup_OOP_FinalProject;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;
import java.util.Timer;

public class SystemLauncher extends JFrame{
    private JPanel wholePanel;
    private JPanel panel2;
    private JPanel panel1;
    private JLabel labelWaiting,logo,logo2;
    private int count;
    private Timer timer;

    public SystemLauncher(){
        getContentPane().add(wholePanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
        countDown();
        labelWaiting.setVisible(false);
        panel2.setLayout((LayoutManager)null);

        //Insert background logo
//        JLabel logo = new JLabel();
////        logo.setBounds(48,14,90,70);
//        logo.setBounds(65,50,90,70);
//        logo.setIcon(new ImageIcon("C:\\Users\\limch\\OneDrive\\Documents\\JL_LIM\\AttendanceManagementSystem\\src\\main\\java\\Pictures\\loading-20x20.gif"));
//        panel2.setLayout((LayoutManager)null);
//        panel2.add(logo);


    }

    void countDown(){
        count = 4;
        timer = new java.util.Timer();
        // Create a TimerTask that will decrement the totalSeconds variable
        TimerTask task = new TimerTask() {
            public void run() {
                if (count > 0) {
                    count--;


                    if(count >= 3){
                        logo2 = new JLabel();
                        logo2.setBounds(4,3,292,142);
                        logo2.setIcon(new ImageIcon("C:\\Users\\limch\\OneDrive\\Documents\\JL_LIM\\AttendanceManagementSystem\\src\\main\\java\\Pictures\\jjj-295x150.jpg"));
                        panel2.add(logo2);
                    }
                    else if(count == 2){
                        logo = new JLabel();
                        logo2.setVisible(false);
                        labelWaiting.setVisible(true);
                        logo.setBounds(4,3,292,142);
                        logo.setIcon(new ImageIcon("C:\\Users\\limch\\OneDrive\\Documents\\JL_LIM\\AttendanceManagementSystem\\src\\main\\java\\Pictures\\cycle-295x150.gif"));
                        panel2.add(logo);
                        labelWaiting.setText("Loading");
                    }else if(count == 1){
                        labelWaiting.setText("Loading.....");
                    }else if(count == 0){
                        labelWaiting.setText("Launching");
                    }

                } else {
                    System.out.println("\nWelcome to login page.");
                    timer.cancel(); // Stop the timer when time's up
                    setVisible(false);
                    new Sign_in().setVisible(true);
                }
            }
        };

        // Schedule the TimerTask to run every 1 second (1000 milliseconds)
        timer.scheduleAtFixedRate(task, 0, 1000);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SystemLauncher().setVisible(true);
        });
    }
}

