package aTerolGroup_OOP_FinalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AttendanceManagementSystemGUI extends JFrame {
    private JLabel nameLabel;
    private JTextField nameField;
    private JButton submitButton;
    private JTextArea attendanceTextArea;
    private List<String> attendanceList;

    public AttendanceManagementSystemGUI() {
        setTitle("Attendance Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new FlowLayout());

        nameLabel = new JLabel("Enter Name:");
        nameField = new JTextField(15);
        submitButton = new JButton("Submit");
        attendanceTextArea = new JTextArea(10, 30);
        attendanceList = new ArrayList<>();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                attendanceList.add(name);
                updateAttendanceTextArea();
                nameField.setText("");
            }
        });

        add(nameLabel);
        add(nameField);
        add(submitButton);
        add(new JScrollPane(attendanceTextArea));

        setVisible(true);
    }

    private void updateAttendanceTextArea() {
        StringBuilder sb = new StringBuilder();
        for (String name : attendanceList) {
            sb.append(name).append("\n");
        }
        attendanceTextArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AttendanceManagementSystemGUI();
            }
        });
    }
}