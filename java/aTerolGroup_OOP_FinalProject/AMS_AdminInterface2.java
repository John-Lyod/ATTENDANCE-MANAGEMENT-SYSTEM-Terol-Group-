package aTerolGroup_OOP_FinalProject;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AMS_AdminInterface2 extends JFrame {
    private JPanel adminMainPanel;
    private JLabel adminHeaderTitle;
    private JTabbedPane mainTabs;
    private JPanel roomPanel;
    private JPanel accountPanel;
    private JPanel aboutPanel;
    private JButton listButton;
    public JLabel adminNameLabel;
    public JLabel adminHostIDLabel;
    public JTextField adminFirstnameField;
    public JTextField adminLastnameField;
    public JTextField adminAgeField;
    public JTextField adminEmailField;
    public JPasswordField adminPasswordField;
    private JCheckBox showPasswordCheckBox;
    private JButton adminEditButton;
    public JComboBox adminSexSelection;
    private JButton updateMyDataButton;
    private JButton logOutButton;
    private JTextField roomNameField;
    private JButton enterRoomButton;
    private JButton createRoomButton;
    private JButton deleteRoomButton;
    private JTextField roomIDField;
    private JTable displayMyRoomTable;
    private JButton refreshRoomFieldButton;
    private JTable recentRoomTable;
    private JButton searchRoomButton;
    private JPanel roomInterfacePanel;
    private JScrollPane myRoomListScrollPane;
    private JScrollPane recentRoomListScrollPane;
    private JPanel roomHostAttendancePanel;
    private JTable tableAttendanceRoom1;
    private JButton backButton1;
    private JLabel labelRoomName1;
    private JPanel roomMemberAttendancePanel;
    private JTable tableAttendanceRoom2;
    private JButton backButton2;
    private JLabel labelRoomName2;
    private JLabel labelRoomID1;
    private JLabel labelRoomID2;
    private JLabel labelMyRoomKey;
    private JLabel labelHostname;
    private JTextField fieldMemberName2;
    private JLabel labelMemberID2;
    private JTextField fieldMemberName1;
    private JLabel labelMemberID1;
    private JButton buttonPresent;
    private JButton buttonAbsent;
    private JButton buttonExcuse;
    private JButton buttonMarkMe;
    private JPanel panelDate2;
    private JPanel panelDate1;
    private JLabel viewListMember2;
    private JLabel viewListMember1;
    private JLabel labelhostID2;
    private JLabel labelRoomID;
    private JButton closeRoomButton;
    private JButton openRoomButton;
    private JPanel panelRoomDesign;
    private JPanel panelRecentDesign;
    private JPanel panelAboutDesign;
    private JPanel panelMyData;
    private JLabel removePeople;
    private JLabel outMe;
    private JButton outButton;
    private JTable tableEnrolledPeople;
    private JPanel panelEnrolledPeople;
    private JTextField fieldPeopleId;
    boolean isAccountsListOpen;
    private JDateChooser dateChooser1, dateChooser2;
    private JTextFieldDateEditor dateEditor1, dateEditor2;
    private boolean mouseClick = false;

    private void openListOfAccounts() {
        isAccountsListOpen = true;
        AMS_AccountsList actionFrame = new AMS_AccountsList();
        actionFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                isAccountsListOpen = false;
            }
        });
    }

    public AMS_AdminInterface2() {
        setTitle("Admin Dashboard");
        getContentPane().add(adminMainPanel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
//        setResizable(false);
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAccountsListOpen) {
                    openListOfAccounts();
                }
            }
        });

        //Room panel
        mainRoomPanel();
        calendar1();
        calendar2();
        hideSomeSwing();
        moreOption();
        logo();

        displayMyRoomTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = displayMyRoomTable.getSelectedRow();
                TableModel model = displayMyRoomTable.getModel();
                roomNameField.setText(model.getValueAt(i, 0).toString());
                roomIDField.setText(model.getValueAt(i, 1).toString());
                roomNameField.setEditable(false);
                roomIDField.setEditable(false);
                enterRoomButton.setEnabled(true);
                deleteRoomButton.setEnabled(true);
                enterRoomButton.setOpaque(true);
                deleteRoomButton.setOpaque(true);
                enterRoomButton.setForeground(Color.WHITE);
                deleteRoomButton.setForeground(Color.WHITE);
            }
        });
        Border b1 = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        refreshRoomFieldButton.setBorderPainted(false);
        refreshRoomFieldButton.setOpaque(false);

        backButton1.setOpaque(false);
        backButton1.setBorder(b1);

        backButton2.setOpaque(false);
        backButton2.setBorder(b1);

        //Accounts panel
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    adminPasswordField.setEchoChar('\u0000');
                } else {
                    adminPasswordField.setEchoChar('\u2023');
                }
            }
        });
        adminEditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmPass();
            }
        });
        updateMyDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fname = adminFirstnameField.getText();
                String lname = adminLastnameField.getText();
                String age_s = adminAgeField.getText();
                String sex = adminSexSelection.getSelectedItem().toString();
                String n_email = adminEmailField.getText();
                String upass = adminPasswordField.getText();
                String s_char1 ="^[ A-Za-z]+$";
                String s_char2 ="^[A-Za-z]+$";
                if (fname.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Can't process some field(s) is/are empty.", "Error", JOptionPane.ERROR_MESSAGE);
                }else if(!fname.matches(s_char1)){
                    JOptionPane.showMessageDialog(null, "Invalid character/s at Firstname", "Error", JOptionPane.ERROR_MESSAGE);
                }else if (lname.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Can't process some field(s) is/are empty.", "Error", JOptionPane.ERROR_MESSAGE);
                }else if(!lname.matches(s_char2)){
                    JOptionPane.showMessageDialog(null, "Invalid character/s at Lastname", "Error", JOptionPane.ERROR_MESSAGE);
                }else if (age_s.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Can't process some field(s) is/are empty.", "Error", JOptionPane.ERROR_MESSAGE);
                }else if(n_email.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field(s) is/are empty.", "Error", JOptionPane.ERROR_MESSAGE);
                }else if(!isEmailValid(n_email)){
                    JOptionPane.showMessageDialog(null, "Invalid email address", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (upass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Can't process some field(s) is/are empty.", "Error", JOptionPane.ERROR_MESSAGE);
                }else {
                    try {
                        int age = Integer.parseInt(age_s);
                        boolean isValid = AccountsDataHandler.updateMyData(Integer.parseInt(adminHostIDLabel.getText()),fname, lname, age, sex, n_email, upass);
                        if (isValid) {
                            updateMyDataButton.setVisible(false);
                            adminEditButton.setVisible(true);
                            uneditMyData();
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Some field must contain number(s) only.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm logout", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Thank you for using this system.");
                    setVisible(false);
                    new Sign_in().setVisible(true);                }
            }
        });
        refreshRoomFieldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearRoomField();
                recentJoinedRoom(Integer.parseInt(adminHostIDLabel.getText()));
            }
        });
        createRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearRoomField();
                disableRoomFields();
                String host_name = adminNameLabel.getText();
                String s_char = "^[A-Za-z0-9_]+$";
                String room_name = JOptionPane.showInputDialog(null, "", "New_Room");
                if (room_name == null) {
                    System.out.println("Create cancelled...");
                } else {
                    if (room_name.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Can't process room name was empty", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (room_name.matches(".*\\s+.*")) {
                        JOptionPane.showMessageDialog(null, "Input cannot contain spaces.", "Error", JOptionPane.ERROR_MESSAGE);
                    }else if(!room_name.matches(s_char)){
                        JOptionPane.showMessageDialog(null, "Input can't special character(s)", "Error", JOptionPane.ERROR_MESSAGE);
                    }else if(room_name.length() < 4){
                        JOptionPane.showMessageDialog(null,"Room Name is too short!");
                    }else if(room_name.length() > 15){
                        JOptionPane.showMessageDialog(null,"Room Name is too long!");
                    }
                    else {
                        boolean isRoomExist = RoomHandler.isRoomExist(room_name, Integer.parseInt(adminHostIDLabel.getText()));
                        if (isRoomExist) {
                            JOptionPane.showMessageDialog(null,"Room was already exist.");
                        }
                        else {
                            String room_key = JOptionPane.showInputDialog(null, "Enter Room password");
                            if (room_key == null) {
                                System.out.println("Create cancelled...");
                            } else {
                                if (room_key.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Can't process input was empty", "Error", JOptionPane.ERROR_MESSAGE);
                                } else if (room_key.matches(".*\\s+.*")) {
                                    JOptionPane.showMessageDialog(null, "Input cannot contain spaces.", "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    RoomHandler.recordRoom(room_name, room_key, host_name, Integer.parseInt(adminHostIDLabel.getText()));
                                    adminRoomList(Integer.parseInt(adminHostIDLabel.getText()));
                                    JOptionPane.showMessageDialog(null, "Room created successfully.");
                                }
                            }
                        }
                    }
                }
            }
        });
        recentRoomTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = recentRoomTable.getSelectedRow();
                TableModel model = recentRoomTable.getModel();
                roomNameField.setText(model.getValueAt(i,0).toString());
                roomIDField.setText(model.getValueAt(i,1).toString());
                roomNameField.setEditable(false);
                roomIDField.setEditable(false);
                enterRoomButton.setEnabled(true);
                deleteRoomButton.setEnabled(true);
                enterRoomButton.setOpaque(true);
                deleteRoomButton.setOpaque(true);
                enterRoomButton.setForeground(Color.WHITE);
                deleteRoomButton.setForeground(Color.WHITE);
            }
        });
        searchRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearRoomField();
                disableRoomFields();
                int admin_id = Integer.parseInt(adminHostIDLabel.getText());
                String room_id_s = JOptionPane.showInputDialog("Enter the Room id");
                if(room_id_s == null){
                    System.out.println("Search cancelled");
                }else {
                    if (room_id_s.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Can't process no input(s)");
                    } else if (room_id_s.matches(".*\\s+.*")) {
                        JOptionPane.showMessageDialog(null, "Input cannot contain spaces.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            int room_id = Integer.parseInt(room_id_s);
                            if(RoomHandler.isSearchRoomHost(room_id, admin_id)){
                                JOptionPane.showMessageDialog(null, "You are the host in that Room");
                                adminRoomList(admin_id);
                            }else {
                                boolean isRoomExist = RoomHandler.isSearchRoomExist(room_id);
                                if (isRoomExist) {
                                    displaySearchRoom(room_id);
                                } else {
                                    displaySearchRoom(room_id);
                                    JOptionPane.showMessageDialog(null, "No room found");
                                }
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Input must contain number/s only");
                        }
                    }
                }
            }
        });
        enterRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defaultT();
                disableBtnAtHostPanel();
                disableBtnAtMemberPanel();
                int room_id = Integer.parseInt(roomIDField.getText());
                int my_id = Integer.parseInt(adminHostIDLabel.getText());
                boolean isHostEnter = RoomHandler.isSearchRoomHost(room_id, my_id);
                if(isHostEnter){
                    myRoomStatus(Integer.parseInt(roomIDField.getText()));
                    hostPanel();
                } else{
                    roomStatus(room_id, my_id);
                    recentJoinedRoom(my_id);
                }
                clearDate();
            }
        });
        deleteRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String room_name = roomNameField.getText();
                int room_id = Integer.parseInt(roomIDField.getText());
                int my_id = Integer.parseInt(adminHostIDLabel.getText());
                boolean isHost = RoomHandler.isSearchRoomHost(room_id, my_id);
                if(!isHost){
                    deleteRoomButton.setEnabled(false);
                    deleteRoomButton.setForeground(Color.WHITE);
                    deleteRoomButton.setOpaque(false);
                    JOptionPane.showMessageDialog(null,"You're not the host of this room");
                }else{
                    int confirmDelete = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "+room_name+"?","Delete Room?",JOptionPane.YES_NO_OPTION);
                    if(confirmDelete == JOptionPane.YES_OPTION){
                        RoomHandler.deleteRoom(room_name,room_id,my_id);
                        adminRoomList(my_id);
                        enterRoomButton.setEnabled(false);
                        enterRoomButton.setOpaque(false);
                        enterRoomButton.setForeground(Color.GRAY);
                        deleteRoomButton.setEnabled(false);
                        deleteRoomButton.setOpaque(false);
                        deleteRoomButton.setForeground(Color.GRAY);
                        System.out.println(room_name+" id no. "+room_id+" is deleted.");
                    }else{
                        enterRoomButton.setEnabled(false);
                        enterRoomButton.setOpaque(false);
                        enterRoomButton.setForeground(Color.GRAY);
                        deleteRoomButton.setEnabled(false);
                        deleteRoomButton.setOpaque(false);
                        deleteRoomButton.setForeground(Color.GRAY);
                        System.out.println("Delete room cancelled.");
                    }
                }
            }
        });
        backButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainRoomPanel();
                clearRoomField();
            }
        });
        backButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainRoomPanel();
                clearRoomField();
            }
        });
        labelMyRoomKey.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    String room_key = JOptionPane.showInputDialog("Room Key",labelMyRoomKey.getText());
                    if(room_key == null){
                    }else{
                        if(room_key.equals("")){
                        }else if(room_key.matches(".*\\s+.*")){
                            JOptionPane.showMessageDialog(null,"Input must not contain spaces");
                        }else{
                            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure want to save changed?","Confirmation",JOptionPane.YES_NO_OPTION);
                            if(confirm == JOptionPane.YES_OPTION) {
                                updateRoomKey(Integer.parseInt(labelRoomID1.getText()), room_key);
                                labelMyRoomKey.setText(room_key);
                            }
                        }
                    }
                }
            }
        });
        tableAttendanceRoom1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tableAttendanceRoom1.getSelectedRow();
                TableModel model = tableAttendanceRoom1.getModel();
                fieldMemberName1.setText(model.getValueAt(i,0).toString());
                labelMemberID1.setText(model.getValueAt(i,2).toString());
                enableBtnAtHostPanel();
            }
        });
        tableAttendanceRoom2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tableAttendanceRoom2.getSelectedRow();
                TableModel model = tableAttendanceRoom2.getModel();
                fieldMemberName2.setText(model.getValueAt(i,0).toString());
                labelMemberID2.setText(model.getValueAt(i,2).toString());
                enableBtnAtMemberPanel();
            }
        });
        viewListMember1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(dateChooser1.getDate() == null){
                    JOptionPane.showMessageDialog(null,"Please select a date first.");
                }else{
                    String date = dateEditor1.getText().toString();
                    boolean isDateSet = searchSetDate(labelRoomName1.getText(),Integer.parseInt(adminHostIDLabel.getText()), date);
                    if(isDateSet){
                        displayPeopleEnrolled(labelRoomName1.getText(),Integer.parseInt(adminHostIDLabel.getText()), date);
                    }else{
                        int confirm = JOptionPane.showConfirmDialog(null,"Selected data is not yet set. \nSet a date ("+date+") now?","Message",JOptionPane.YES_NO_OPTION);
                        if(confirm == JOptionPane.YES_OPTION) {
                            setDatePeople(labelRoomName1.getText(), Integer.parseInt(adminHostIDLabel.getText()), date);
                        }
                    }
                }
            }
        });
        viewListMember2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(dateChooser2.getDate() == null){
                    JOptionPane.showMessageDialog(null,"Please select a date first.");
                }else{
                    String date = dateEditor2.getText().toString();
                    boolean isDateSet = setMeThatDate(labelRoomName2.getText(),Integer.parseInt(labelhostID2.getText()), Integer.parseInt(adminHostIDLabel.getText()), date);
                    if(isDateSet){
                        displayMeEnrolled(labelRoomName2.getText(),Integer.parseInt(labelhostID2.getText()), Integer.parseInt(adminHostIDLabel.getText()), date);
                    }else {
                        displayMeEnrolled(labelRoomName2.getText(),Integer.parseInt(labelhostID2.getText()), Integer.parseInt(adminHostIDLabel.getText()), date);
                        JOptionPane.showMessageDialog(null,"Selected date is not yet set.");
                    }
                }
            }
        });
        labelRoomName1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    String old_room_name = labelRoomName1.getText();
                    String s_char = "^[A-Za-z0-9_]+$";
                    String new_room_name = JOptionPane.showInputDialog("Room Name",labelRoomName1.getText());
                    if(new_room_name == null){
                    }else{
                        if(new_room_name.equals("")){
                            JOptionPane.showMessageDialog(null,"Input was empty!", "Error", JOptionPane.ERROR_MESSAGE);
                        }else if(new_room_name.matches(".*\\s+.*")){
                            JOptionPane.showMessageDialog(null,"Input cannot contain spaces", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else if(!new_room_name.matches(s_char)){
                            JOptionPane.showMessageDialog(null, "You entered invalid character(s)", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else if(new_room_name.length() < 4){
                            JOptionPane.showMessageDialog(null,"Room Name is too short!");
                        }
                        else if(new_room_name.length() > 15){
                            JOptionPane.showMessageDialog(null,"Room Name is too long!");
                        }
                        else{
                            updateRoomName(old_room_name, new_room_name, Integer.parseInt(labelRoomID1.getText()), Integer.parseInt(adminHostIDLabel.getText()));
                        }
                    }
                }
            }
        });
        buttonPresent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String attendance_status = "Present";
                RoomHandler.markPeopleAttendance(labelRoomName1.getText(), Integer.parseInt(adminHostIDLabel.getText()), Integer.parseInt(labelMemberID1.getText()), attendance_status, String.valueOf(dateEditor1.getText()));
                displayPeopleEnrolled(labelRoomName1.getText(), Integer.parseInt(adminHostIDLabel.getText()), String.valueOf(dateEditor1.getText()));
            }
        });
        buttonAbsent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String attendance_status = "Absent";
                RoomHandler.markPeopleAttendance(labelRoomName1.getText(), Integer.parseInt(adminHostIDLabel.getText()), Integer.parseInt(labelMemberID1.getText()), attendance_status, String.valueOf(dateEditor1.getText()));
                displayPeopleEnrolled(labelRoomName1.getText(), Integer.parseInt(adminHostIDLabel.getText()), String.valueOf(dateEditor1.getText()));
            }
        });
        buttonExcuse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String attendance_status = "Excused";
                RoomHandler.markPeopleAttendance(labelRoomName1.getText(), Integer.parseInt(adminHostIDLabel.getText()), Integer.parseInt(labelMemberID1.getText()), attendance_status, String.valueOf(dateEditor1.getText()));
                displayPeopleEnrolled(labelRoomName1.getText(), Integer.parseInt(adminHostIDLabel.getText()), String.valueOf(dateEditor1.getText()));
            }
        });
        buttonMarkMe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String attendance_status = "Present";
                RoomHandler.markMeAttendance(labelRoomName2.getText(), Integer.parseInt(labelhostID2.getText()), Integer.parseInt(labelMemberID2.getText()), attendance_status, String.valueOf(dateEditor2.getText()));
                displayMeEnrolled(labelRoomName2.getText(), Integer.parseInt(labelhostID2.getText()), Integer.parseInt(adminHostIDLabel.getText()), String.valueOf(dateEditor2.getText()));
            }
        });
        dateChooser1.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                disableBtnAtHostPanel();
                if(dateChooser1.getDate() == null){
//                    JOptionPane.showMessageDialog(null,"Please select a date first.");
                }else {
                    String date = dateEditor1.getText().toString();
                    boolean isDateSet = searchSetDate(labelRoomName1.getText(), Integer.parseInt(adminHostIDLabel.getText()), date);
                    if (isDateSet) {
                        displayPeopleEnrolled(labelRoomName1.getText(), Integer.parseInt(adminHostIDLabel.getText()), date);
                    } else {
                        int confirm = JOptionPane.showConfirmDialog(null, "Selected date is not yet set. \nSet a date (" + date + ") now?", "Message", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            setDatePeople(labelRoomName1.getText(), Integer.parseInt(adminHostIDLabel.getText()), date);
                        }
                    }
                }
            }
        });
        dateChooser2.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                disableBtnAtMemberPanel();
                if(dateChooser2.getDate() == null){
//                    JOptionPane.showMessageDialog(null,"Please select a date first.");
                }else{
                    String date = dateEditor2.getText().toString();
                    boolean isDateSet = setMeThatDate(labelRoomName2.getText(),Integer.parseInt(labelhostID2.getText()), Integer.parseInt(adminHostIDLabel.getText()), date);
                    if(isDateSet){
                        displayMeEnrolled(labelRoomName2.getText(),Integer.parseInt(labelhostID2.getText()), Integer.parseInt(adminHostIDLabel.getText()), date);
                    }else {
                        displayMeEnrolled(labelRoomName2.getText(),Integer.parseInt(labelhostID2.getText()), Integer.parseInt(adminHostIDLabel.getText()), date);
                        JOptionPane.showMessageDialog(null,"Selected date is not yet set.");
                    }
                }
            }
        });
        closeRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RoomHandler.updateMyRoomStatus(Integer.parseInt(labelRoomID1.getText()),closeRoomButton.getText());
                closeRoomButton.setEnabled(false);
                closeRoomButton.setOpaque(false);
                closeRoomButton.setForeground(Color.GRAY);
                openRoomButton.setEnabled(true);
                openRoomButton.setOpaque(true);
                openRoomButton.setForeground(Color.WHITE);
                openRoomButton.setBackground(Color.GREEN);
                System.out.println("Room was close");
            }
        });
        openRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RoomHandler.updateMyRoomStatus(Integer.parseInt(labelRoomID1.getText()),openRoomButton.getText());
                openRoomButton.setEnabled(false);
                openRoomButton.setOpaque(false);
                openRoomButton.setForeground(Color.GRAY);
                closeRoomButton.setEnabled(true);
                closeRoomButton.setOpaque(true);
                closeRoomButton.setForeground(Color.WHITE);
                closeRoomButton.setBackground(Color.ORANGE);
                System.out.println("Room was open");
            }
        });
        removePeople.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(mouseClick){
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this data?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if(confirm == JOptionPane.YES_OPTION){
                        RoomHandler.removePersonInMyRoom(labelRoomName1.getText(),Integer.parseInt(adminHostIDLabel.getText()),Integer.parseInt(fieldPeopleId.getText()));
                        displayPeopleEnrolled2(labelRoomName1.getText(),Integer.parseInt(adminHostIDLabel.getText()));
                        displayPeopleEnrolled(roomNameField.getText(), Integer.parseInt(adminHostIDLabel.getText()), dateEditor1.getText().toString());
                        mouseClick = false;
                        removePeople.setForeground(Color.GRAY);
                    }else if(confirm == JOptionPane.NO_OPTION){
                        displayPeopleEnrolled2(labelRoomName1.getText(),Integer.parseInt(adminHostIDLabel.getText()));
                        removePeople.setForeground(Color.GRAY);
                    }
                }
            }
        });
        outButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to leave this room?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION){
                    RoomHandler.removeMeInThatRoom(labelRoomName2.getText(),Integer.parseInt(labelRoomID2.getText()),Integer.parseInt(adminHostIDLabel.getText()));
                    mainRoomPanel();
                    clearRoomField();
                    recentJoinedRoom(Integer.parseInt(adminHostIDLabel.getText()));
                }
            }
        });
        tableEnrolledPeople.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tableEnrolledPeople.getSelectedRow();
                TableModel model = tableEnrolledPeople.getModel();
                fieldPeopleId.setText(model.getValueAt(i, 2).toString());
                disableBtnAtHostPanel();
                mouseClick = true;
                removePeople.setForeground(Color.BLUE);
            }
        });

        tableEnrolledPeople.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                mouseClick = false;
                removePeople.setForeground(Color.GRAY);
                displayPeopleEnrolled2(labelRoomName1.getText(),Integer.parseInt(adminHostIDLabel.getText()));
            }
        });
    }

    private boolean isEmailValid(String input) {
        // Regular expression to validate the email address
        String regex = "^(?!.*[_.]{2})[a-zA-Z0-9._]+@[a-zA-Z0-9]+\\.[a-zA-Z]+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    private void confirmPass() {
        String adminEmail = adminEmailField.getText();
        System.out.println("Accessing " + adminEmail + ".....");
        String confirmPass = JOptionPane.showInputDialog(null, "Enter your password: ");
        if (confirmPass == null) {
            System.out.println(">>> Edit cancelled <<<");
        } else {
            if (confirmPass.equals("")) {
                JOptionPane.showMessageDialog(null, "Can't process input was empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean isValid = AccountsDataHandler.confirmEdit(adminEmail, confirmPass);
                if (isValid) {
                    System.out.println(adminEmail + " has been accessed.");
                    adminEditButton.setVisible(false);
                    updateMyDataButton.setVisible(true);
                    editMyData();
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong password.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("ACCESS DENIED.");
                }
            }
        }
    }

    private void editMyData() {
        adminSexSelection.setEnabled(true);
        adminFirstnameField.setEditable(true);
        adminLastnameField.setEditable(true);
        adminAgeField.setEditable(true);
        adminPasswordField.setEditable(true);
        showPasswordCheckBox.setEnabled(true);
        adminPasswordField.setEchoChar('\u2023');
        listButton.setEnabled(false);
        adminEmailField.setEditable(true);
    }

    private void uneditMyData() {
        adminFirstnameField.setEditable(false);
        adminLastnameField.setEditable(false);
        adminAgeField.setEditable(false);
        adminSexSelection.setEnabled(false);
        showPasswordCheckBox.setEnabled(false);
        adminPasswordField.setEditable(false);
        adminPasswordField.setEchoChar('\u2023');
        listButton.setEnabled(true);
        adminEmailField.setEditable(false);
    }

    public void adminRoomList(int host){
        Object[][] data=null;
        String[] columnNames= {"My Room","Id"};
        DefaultTableModel displayModel = new DefaultTableModel(data,columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable row editing for all cells
            }
        };
        displayMyRoomTable.getTableHeader().setResizingAllowed(false);
        displayMyRoomTable.getTableHeader().setReorderingAllowed(false);
        displayMyRoomTable.setModel(displayModel);
        System.out.println("#Rooms List\nroom_name | room_id | host_name | host_id");
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String sql = "SELECT * FROM public.room_list WHERE host_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
             stmt.setInt(1, host);
             ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String room_id = String.valueOf(rs.getInt("room_id"));
                String room_name = rs.getString("room_name");
                String host_name = rs.getString("host_name");
                String list[] = {room_name, room_id};
                DefaultTableModel tblModel = (DefaultTableModel) displayMyRoomTable.getModel();
                tblModel.addRow(list);
                System.out.println(room_name +", "+ room_id + ", " + host_name + ", " + host);
                while(rs.next()){
                    String room_id2 = String.valueOf(rs.getInt("room_id"));
                    String room_name2 = rs.getString("room_name");
                    String host_name2 = rs.getString("host_name");
                    String list2[] = {room_name2, room_id2};
                    DefaultTableModel tblModel2 = (DefaultTableModel) displayMyRoomTable.getModel();
                    tblModel2.addRow(list2);
                    System.out.println(room_name2 +", "+ room_id2 + ", " + host_name2 + ", " + host);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
    }
    private boolean displaySearchRoom(int room_id_input){
        Object[][] data=null;
        String[] columnNames= {"Room Name","Room id","Host Name","Host id", "Room Status"};
        DefaultTableModel displayModel = new DefaultTableModel(data,columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable row editing for all cells
            }
        };
        displayMyRoomTable.getTableHeader().setResizingAllowed(false);
        displayMyRoomTable.getTableHeader().setReorderingAllowed(false);
        displayMyRoomTable.setModel(displayModel);
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String sql = "SELECT * FROM public.room_list WHERE room_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, room_id_input);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String room_id = String.valueOf(rs.getInt("room_id"));
                String room_name = rs.getString("room_name");
                String host_name = rs.getString("host_name");
                int host_id = rs.getInt("host_id");
                String status = rs.getString("status");
                String list[] = {room_name, room_id, host_name, String.valueOf(host_id), status};
                DefaultTableModel tblModel = (DefaultTableModel) displayMyRoomTable.getModel();
                tblModel.addRow(list);
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
        return false;
    }

    public boolean recentJoinedRoom(int member_id) {
        Object[][] data = null;
        String[] columnNames = {"Room Name", "Room id", "Host Name", "Host id", "Room Status"};
        DefaultTableModel displayModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable row editing for all cells
            }
        };
        recentRoomTable.getTableHeader().setResizingAllowed(false);
        recentRoomTable.getTableHeader().setReorderingAllowed(false);
        recentRoomTable.setModel(displayModel);
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String sql = "SELECT * FROM public.room_join_log WHERE member_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, member_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int room_id = rs.getInt("room_id");
                String room_name = rs.getString("room_name");
                int host_id = rs.getInt("host_id");
                PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM public.room_list WHERE room_id = ? AND room_name = ? AND host_id = ?");
                stmt2.setInt(1, room_id);
                stmt2.setString(2, room_name);
                stmt2.setInt(3, host_id);
                ResultSet rs2 = stmt2.executeQuery();
                if (rs2.next()) {
                    String host_name = rs2.getString("host_name");
                    String status = rs2.getString("status");
                    String list[] = {room_name, String.valueOf(room_id), host_name, String.valueOf(host_id), status};
                    DefaultTableModel tblModel = (DefaultTableModel) recentRoomTable.getModel();
                    tblModel.addRow(list);
                }
                while (rs.next()) {
                    int room_id2 = rs.getInt("room_id");
                    String room_name2 = rs.getString("room_name");
                    int host_id2 = rs.getInt("host_id");
                    PreparedStatement stmt22 = conn.prepareStatement("SELECT * FROM public.room_list WHERE room_id = ? AND room_name = ? AND host_id = ?");
                    stmt22.setInt(1, room_id2);
                    stmt22.setString(2, room_name2);
                    stmt22.setInt(3, host_id2);
                    ResultSet rs22 = stmt22.executeQuery();
                    while (rs22.next()) {
                        String host_name = rs22.getString("host_name");
                        String status = rs22.getString("status");
                        String list[] = {room_name2, String.valueOf(room_id2), host_name, String.valueOf(host_id2), status};
                        DefaultTableModel tblModel = (DefaultTableModel) recentRoomTable.getModel();
                        tblModel.addRow(list);
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
        return false;
    }

    void clearRoomField(){
        roomNameField.setText(null);
        roomIDField.setText(null);
        enterRoomButton.setEnabled(false);
        deleteRoomButton.setEnabled(false);
        enterRoomButton.setEnabled(false);
        enterRoomButton.setOpaque(false);
        enterRoomButton.setForeground(Color.GRAY);
        deleteRoomButton.setEnabled(false);
        deleteRoomButton.setOpaque(false);
        deleteRoomButton.setForeground(Color.GRAY);
        adminRoomList(Integer.parseInt(adminHostIDLabel.getText()));
    }
    void disableRoomFields(){
        roomNameField.setEditable(false);
        roomIDField.setEditable(false);
    }
    void enableRoomFields(){
        roomNameField.setEditable(true);
        roomIDField.setEditable(true);
    }
    public void hostPanel(){
        roomInterfacePanel.setVisible(false);
        roomHostAttendancePanel.setVisible(true);
        labelRoomName1.setText(roomNameField.getText());
        labelRoomID1.setText(String.valueOf(roomIDField.getText()));
        HostnameAndRoomKey(Integer.parseInt(roomIDField.getText()));
        displayPeopleEnrolled(roomNameField.getText(),Integer.parseInt(adminHostIDLabel.getText()), dateEditor1.getText().toString());
        displayPeopleEnrolled2(roomNameField.getText(), Integer.parseInt(adminHostIDLabel.getText()));
    }
    public void memberPanel(){
        roomInterfacePanel.setVisible(false);
        roomMemberAttendancePanel.setVisible(true);
        labelRoomName2.setText(roomNameField.getText());
        labelRoomID2.setText(String.valueOf(roomIDField.getText()));
        HostnameAndRoomKey(Integer.parseInt(roomIDField.getText()));
        displayMeEnrolled(roomNameField.getText(),Integer.parseInt(labelhostID2.getText()), Integer.parseInt(adminHostIDLabel.getText()), dateEditor2.getText().toString());
    }
    public void mainRoomPanel(){
        roomHostAttendancePanel.setVisible(false);
        roomMemberAttendancePanel.setVisible(false);
        roomInterfacePanel.setVisible(true);
    }

    private void defaultT(){
        labelMemberID1.setText(null);
        labelMemberID2.setText(null);
        fieldMemberName1.setText(null);
        fieldMemberName2.setText(null);
    }

    private void updateRoomKey(int room_id, String room_key) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            PreparedStatement updateStmt = conn.prepareStatement("UPDATE public.room_list SET room_password = ? WHERE room_id = ?");
            updateStmt.setString(1,room_key);
            updateStmt.setInt(2,room_id);
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void updateRoomName(String old_room, String new_room, int room_id, int host_id) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                String oldRoomName = old_room+"_"+host_id;
                String newRoomName = new_room+"_"+host_id;
                try (Connection conn = AMS_DB_connector.getConnection()) {
                    PreparedStatement checkQuery = conn.prepareStatement("SELECT * FROM public.room_list WHERE room_name = ? AND room_id = ?");
                    checkQuery.setString(1, new_room);
                    checkQuery.setInt(2, room_id);
                    ResultSet rs = checkQuery.executeQuery();
                    if(rs.next()){
                        JOptionPane.showMessageDialog(null,"Room name is already in used.\n Try another one.");
                    }else {
                        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure want to save changed?","Confirm Change?",JOptionPane.YES_NO_OPTION);
                        if(confirm == JOptionPane.YES_OPTION) {
                            PreparedStatement sql1 = conn.prepareStatement("UPDATE public.room_list SET room_name = ? WHERE room_id = ?");
                            sql1.setString(1,new_room.toLowerCase());
                            sql1.setInt(2,room_id);
                            sql1.executeUpdate();
                            PreparedStatement sql2 = conn.prepareStatement("UPDATE public.room_join_log SET room_name = ? WHERE room_id = ?");
                            sql2.setString(1,new_room.toLowerCase());
                            sql2.setInt(2,room_id);
                            sql2.executeUpdate();
                            Statement sql3 = conn.createStatement();
                            sql3.executeUpdate("ALTER TABLE rooms."+ oldRoomName + " RENAME TO " + newRoomName);
                            labelRoomName1.setText(new_room);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        worker.execute();
    }
    private boolean roomStatus(int room_id, int my_id) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String query = "SELECT * FROM public.room_list WHERE room_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, room_id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                String status = rs.getString("status");
                if(status.equals("Close")){
                    JOptionPane.showMessageDialog(null,"Room is Closed.");
                }else if(status.equals("Open")){
                    int host_id = rs.getInt("host_id");
                    String room_name = rs.getString("room_name");
                    isImEnrolled(room_id, room_name, host_id, my_id);
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private void myRoomStatus(int room_id) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String query = "SELECT * FROM public.room_list WHERE room_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, room_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String status = rs.getString("status");
                if (status.equals("Close")) {
                    closeRoomButton.setEnabled(false);
                    closeRoomButton.setOpaque(false);
                    closeRoomButton.setForeground(Color.GRAY);
                    openRoomButton.setEnabled(true);
                    openRoomButton.setBackground(Color.GREEN);
                    openRoomButton.setForeground(Color.WHITE);
                } else if (status.equals("Open")){
                    openRoomButton.setEnabled(false);
                    openRoomButton.setOpaque(false);
                    openRoomButton.setForeground(Color.GRAY);
                    closeRoomButton.setEnabled(true);
                    closeRoomButton.setBackground(Color.ORANGE);
                    closeRoomButton.setForeground(Color.WHITE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private boolean isImEnrolled(int room_id2, String room_name, int host_id, int my_id){
        String sql = "SELECT * FROM rooms."+room_name+"_"+host_id+" WHERE ams_acc_id = "+my_id;
        try (Connection conn = AMS_DB_connector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM public.ams_acc WHERE id = " + my_id);
                ResultSet rs2 = stmt2.executeQuery();
                if (rs2.next()) {
                    String firstname = rs2.getString("firstname");
                    String lastname = rs2.getString("lastname");
                    String people_name = lastname + ", " + firstname;
                    String people_email = rs2.getString("email");
                    RoomHandler.isMyDataUpdate(room_name, host_id, people_name, people_email, my_id);
                }
                memberPanel();
                return true;
            }
            else{
                int confirmJoin = JOptionPane.showConfirmDialog(null,"You're not enrolled in this room, want to join?","Join Confirmation",JOptionPane.YES_NO_OPTION);
                if(confirmJoin == JOptionPane.NO_OPTION){
                    System.out.println("Cancelled join.");
                }else if(confirmJoin == JOptionPane.YES_OPTION) {
                    String room_key = JOptionPane.showInputDialog("Enter room key");
                    if(room_key == null){
                        System.out.println("Join cancelled.");
                    }else {
                        if(room_key.equals("")){
                            JOptionPane.showMessageDialog(null,"Can't process input was empty", "Error", JOptionPane.ERROR_MESSAGE);
                        }else if(room_key.matches(".*\\s+.*")){
                            JOptionPane.showMessageDialog(null, "Input cannot contain spaces.", "Error", JOptionPane.ERROR_MESSAGE);
                        }else {
                            int room_id = room_id2;
                            RoomHandler.isRoomKeyCorrect(room_id, room_name, room_key, my_id);
                        }
                    }
                }
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
        return false;
    }

    private void HostnameAndRoomKey(int room_id){
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String sql = "SELECT * FROM public.room_list WHERE room_id = " + room_id;
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                labelMyRoomKey.setText(String.valueOf(rs.getString("room_password")));
                labelHostname.setText(String.valueOf(rs.getString("host_name")));
                labelhostID2.setText(String.valueOf(rs.getInt("host_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean searchSetDate(String room_name, int host_id, String dateSelected){
        displayPeopleEnrolled(room_name,host_id, dateSelected);
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String roomName = room_name+"_"+host_id;
            String sql = "SELECT * FROM rooms." + roomName+ " WHERE date = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, dateSelected);
            ResultSet rs = stmt.executeQuery();
            return (rs.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private boolean setMeThatDate(String room_name, int host_id, int my_id, String dateSelected){
        displayPeopleEnrolled(room_name,host_id, dateSelected);
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String roomName = room_name+"_"+host_id;
            String sql = "SELECT * FROM rooms." + roomName+ " WHERE ams_acc_id = ? AND date = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,my_id);
            stmt.setString(2, dateSelected);
            ResultSet rs = stmt.executeQuery();
            return (rs.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setDatePeople(String room_name, int host_id, String dateSelected){
        String roomName = room_name+"_"+host_id;
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String sql = "SELECT * FROM rooms."+roomName+ " WHERE date = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,"_");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String people_name = rs.getString("people_name");
                String people_email = rs.getString("people_email");
                int ams_id = rs.getInt("ams_acc_id");
                PreparedStatement insertUpdate = conn.prepareStatement("INSERT INTO rooms."+roomName+"(people_name, people_email, ams_acc_id, mark_as, date) VALUES (?,?,?,?,?)");
                insertUpdate.setString(1,people_name);
                insertUpdate.setString(2,people_email);
                insertUpdate.setInt(3,ams_id);
                insertUpdate.setString(4,"_");
                insertUpdate.setString(5,dateSelected);
                insertUpdate.executeUpdate();
                while (rs.next()) {
                    String people_name2 = rs.getString("people_name");
                    String people_email2 = rs.getString("people_email");
                    int ams_id2 = rs.getInt("ams_acc_id");
                    PreparedStatement insertUpdate2 = conn.prepareStatement("INSERT INTO rooms."+roomName+"(people_name, people_email, ams_acc_id, mark_as, date) VALUES (?,?,?,?,?)");
                    insertUpdate2.setString(1,people_name2);
                    insertUpdate2.setString(2,people_email2);
                    insertUpdate2.setInt(3,ams_id2);
                    insertUpdate2.setString(4,"_");
                    insertUpdate2.setString(5,dateSelected);
                    insertUpdate2.executeUpdate();
                }
            }else{
                JOptionPane.showMessageDialog(null,"There's no people in your room.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
        displayPeopleEnrolled(room_name,host_id, dateSelected);
    }

    private void displayPeopleEnrolled(String room_name, int host_id, String dateSelected){
        String roomName = room_name+"_"+host_id;
        Object[][] data=null;
        String[] columnNames= {"People Name","Email","Account Id","Attendance Status","Date"};
        DefaultTableModel displayModel = new DefaultTableModel(data,columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable row editing for all cells
            }
        };
        tableAttendanceRoom1.getTableHeader().setResizingAllowed(false);
        tableAttendanceRoom1.getTableHeader().setReorderingAllowed(false);
        tableAttendanceRoom1.setModel(displayModel);
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String sql = "SELECT * FROM rooms."+roomName+ " WHERE date = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, dateSelected);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String people_name = rs.getString("people_name");
                String people_email = rs.getString("people_email");
                String ams_id = String.valueOf(rs.getInt("ams_acc_id"));
                String mark = rs.getString("mark_as");
                String date = rs.getString("date");
                String list[] = {people_name, people_email, ams_id, mark,date};
                DefaultTableModel tblModel = (DefaultTableModel) tableAttendanceRoom1.getModel();
                tblModel.addRow(list);
                while (rs.next()) {
                    String people_name2 = rs.getString("people_name");
                    String people_email2 = rs.getString("people_email");
                    String ams_id2 = String.valueOf(rs.getInt("ams_acc_id"));
                    String mark2 = rs.getString("mark_as");
                    String date2 = rs.getString("date");
                    String list2[] = {people_name2, people_email2, ams_id2, mark2,date};
                    DefaultTableModel tblModel2 = (DefaultTableModel) tableAttendanceRoom1.getModel();
                    tblModel2.addRow(list2);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
    }

    private void displayPeopleEnrolled2(String room_name, int host_id){
        String roomName = room_name+"_"+host_id;
        Object[][] data=null;
        String[] columnNames= {"People Name","Email","Account Id"};
        DefaultTableModel displayModel = new DefaultTableModel(data,columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable row editing for all cells
            }
        };
        tableEnrolledPeople.getTableHeader().setResizingAllowed(false);
        tableEnrolledPeople.getTableHeader().setReorderingAllowed(false);
        tableEnrolledPeople.setModel(displayModel);
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String sql = "SELECT * FROM rooms."+roomName+ " WHERE date = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "_");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String people_name = rs.getString("people_name");
                String people_email = rs.getString("people_email");
                String ams_id = String.valueOf(rs.getInt("ams_acc_id"));

                String list[] = {people_name, people_email, ams_id};
                DefaultTableModel tblModel = (DefaultTableModel) tableEnrolledPeople.getModel();
                tblModel.addRow(list);
                while (rs.next()) {
                    String people_name2 = rs.getString("people_name");
                    String people_email2 = rs.getString("people_email");
                    String ams_id2 = String.valueOf(rs.getInt("ams_acc_id"));

                    String list2[] = {people_name2, people_email2, ams_id2};
                    DefaultTableModel tblModel2 = (DefaultTableModel) tableEnrolledPeople.getModel();
                    tblModel2.addRow(list2);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
    }

    private void displayMeEnrolled(String room_name, int host_id, int my_id, String dateSelected){
        Object[][] data=null;
        String[] columnNames= {"You","Email","Account Id","Attendance Status", "Date"};
        DefaultTableModel displayModel = new DefaultTableModel(data,columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable row editing for all cells
            }
        };
        tableAttendanceRoom2.getTableHeader().setResizingAllowed(false);
        tableAttendanceRoom2.getTableHeader().setReorderingAllowed(false);
        tableAttendanceRoom2.setModel(displayModel);
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String roomName = room_name+"_"+host_id;
            PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM rooms."+roomName+" WHERE ams_acc_id = ? AND date = ?");
            stmt2.setInt(1,my_id);
            stmt2.setString(2,dateSelected);
            ResultSet rs2 = stmt2.executeQuery();
            if (rs2.next()) {
                String people_name = rs2.getString("people_name");
                String people_email = rs2.getString("people_email");
                String ams_id = String.valueOf(rs2.getInt("ams_acc_id"));
                String mark = rs2.getString("mark_as");
                String date = rs2.getString("date");
                String list[] = {people_name, people_email, ams_id, mark, date};
                DefaultTableModel tblModel = (DefaultTableModel) tableAttendanceRoom2.getModel();
                tblModel.addRow(list);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
    }

//    private void displayRooms() {
//        Object[][] data=null;
//        String[] columnNames= {"Room List"};
//        DefaultTableModel displayModel = new DefaultTableModel(data,columnNames) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false; // Disable row editing for all cells
//            }
//        };
//        displayRoomTable.getTableHeader().setResizingAllowed(false);
//        displayRoomTable.getTableHeader().setReorderingAllowed(false);
//        displayRoomTable.setModel(displayModel);
//        try (Connection conn = AMS_DB_connector.getConnection()) {
//            Statement statement = conn.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname='rooms';");
//            while (resultSet.next()) {
//                String tableName[] = {resultSet.getString("tablename")};
//                DefaultTableModel tblModel = (DefaultTableModel)displayRoomTable.getModel();
//                tblModel.addRow(tableName);
//                System.out.println(tableName);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    void disableBtnAtHostPanel(){
        buttonPresent.setEnabled(false);
        buttonAbsent.setEnabled(false);
        buttonExcuse.setEnabled(false);
        fieldMemberName1.setText(null);
        labelMemberID1.setText(null);

        //button style
        buttonPresent.setOpaque(false);
        buttonPresent.setForeground(Color.GRAY);
        buttonAbsent.setOpaque(false);
        buttonAbsent.setForeground(Color.GRAY);
        buttonExcuse.setOpaque(false);
        buttonExcuse.setForeground(Color.GRAY);
    }
    void enableBtnAtHostPanel(){
        buttonPresent.setEnabled(true);
        buttonAbsent.setEnabled(true);
        buttonExcuse.setEnabled(true);

        //button style
        buttonPresent.setOpaque(true);
        buttonPresent.setForeground(Color.WHITE);
        buttonPresent.setBackground(Color.GREEN);
        buttonAbsent.setOpaque(true);
        buttonAbsent.setForeground(Color.WHITE);
        buttonAbsent.setBackground(Color.RED);
        buttonExcuse.setOpaque(true);
        buttonExcuse.setForeground(Color.WHITE);
        buttonExcuse.setBackground(Color.ORANGE);

    }
    void disableBtnAtMemberPanel(){
        buttonMarkMe.setEnabled(false);
        buttonMarkMe.setOpaque(false);
        buttonMarkMe.setForeground(Color.GRAY);
        fieldMemberName2.setText(null);
        labelMemberID2.setText(null);
        mouseClick = true;
    }
    void enableBtnAtMemberPanel(){
        buttonMarkMe.setEnabled(true);
        buttonMarkMe.setOpaque(true);
        buttonMarkMe.setForeground(Color.WHITE);
        buttonMarkMe.setBackground(Color.BLUE);
        mouseClick = true;
    }
    void calendar1(){
        dateChooser1 = new JDateChooser();
        dateChooser1.setDateFormatString("dd-MM-yyyy");
        dateEditor1 = (JTextFieldDateEditor) dateChooser1.getDateEditor();
        dateEditor1.setEditable(false);
        panelDate1.add(dateChooser1);
    }
    void calendar2(){
        dateChooser2 = new JDateChooser();
        dateChooser2.setDateFormatString("dd-MM-yyyy");
        dateEditor2 = (JTextFieldDateEditor) dateChooser2.getDateEditor();
        dateEditor2.setEditable(false);
        panelDate2.add(dateChooser2);
    }
    void clearDate(){
        dateChooser1.setDate(null);
        dateChooser2.setDate(null);
    }
    void hideSomeSwing(){
        roomNameField.setVisible(false);
        roomIDField.setVisible(false);
        labelRoomID.setVisible(false);
        viewListMember1.setVisible(false);
        viewListMember2.setVisible(false);
        updateMyDataButton.setVisible(false);
    }
    void moreOption(){
        //disable some swing temporary
        showPasswordCheckBox.setEnabled(false);
        enterRoomButton.setEnabled(false);
        enterRoomButton.setOpaque(false);
        enterRoomButton.setForeground(Color.GRAY);
        deleteRoomButton.setEnabled(false);
        deleteRoomButton.setOpaque(false);
        deleteRoomButton.setForeground(Color.GRAY);
        adminSexSelection.setEnabled(false);
        mainTabs.remove(aboutPanel);

        //uneditable some swing temporary
        adminFirstnameField.setEditable(false);
        adminLastnameField.setEditable(false);
        adminAgeField.setEditable(false);
        adminEmailField.setEditable(false);
        adminPasswordField.setEditable(false);
        roomNameField.setEditable(false);
        roomIDField.setEditable(false);

        //modify some swing style
        adminPasswordField.setEchoChar('\u2023');
    }
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    void logo(){
        //Insert background logo
//        JLabel logo = new JLabel();
//        logo.setBounds(20,420,550,400);
//        logo.setIcon(new ImageIcon("C:\\Users\\limch\\OneDrive\\Documents\\JL_LIM\\AttendanceManagementSystem\\src\\main\\java\\Pictures\\about-logo.png"));
//        accountPanel.setLayout((LayoutManager)null);
//        accountPanel.add(logo);
    }
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AMS_AdminInterface2().setVisible(true);
            }
        });
    }
}
