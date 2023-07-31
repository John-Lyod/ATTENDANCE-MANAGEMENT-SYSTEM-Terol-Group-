package aTerolGroup_OOP_FinalProject;
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

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

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

public class AMS_UserInterface extends JFrame {
    private JPasswordField confirmpassField;
    private JFrame confirmpassFrame;
    private JPanel userMainPanel;
    private JLabel userHeaderTitle;
    private JPanel roomPanel;
    private JTabbedPane mainTabs;
    private JPanel accountPanel;
    private JPanel aboutPanel;
    public JLabel userNameLabel;
    public JTextField userFirstnameField;
    public JTextField userLastnameField;
    public JTextField userAgeField;
    public JTextField userEmailField;
    public JPasswordField userPasswordField;
    private JCheckBox showPasswordCheckBox;
    private JButton userEditButton;
    public JComboBox userSexSelection;
    private JButton updateMyDataButton;
    private JButton logOutButton;
    private JTable displayMyRoomTable;
    private JButton enterRoomButton;
    private JButton createRoomButton;
    private JButton deleteRoomButton;
    private JTextField roomNameField;
    private JTextField roomIDField;
    private JButton refreshRoomFieldButton;
    private JPanel editAccountPanel;
    public JLabel userHostIDLabel;
    private JTable recentRoomTable;
    private JButton searchRoomButton;
    private JScrollPane myRoomListScrollPane;
    private JScrollPane recentRoomListScrollPane;
    private JPanel roomInterfacePanel;
    private JTable tableAttendanceRoom1;
    public JPanel roomHostAttendancePanel;
    private JButton backButton1;
    public JPanel roomMemberAttendancePanel;
    private JTable tableAttendanceRoom2;
    private JButton backButton2;
    private JLabel labelRoomName1;
    private JLabel labelRoomName2;
    private JLabel labelRoomID1;
    private JLabel labelRoomID2;
    private JLabel labelMyRoomKey;
    private JLabel labelHostname;
    private JTextField fieldMemberName1;
    private JTextField fieldMemberName2;
    private JLabel labelMemberID2;
    private JLabel labelMemberID1;
    private JButton buttonMarkMe;
    private JButton buttonPresent;
    private JButton buttonAbsent;
    private JButton buttonExcuse;
    private JPanel panelDate1;
    private JPanel panelDate2;
    private JLabel viewListMember1;
    private JLabel viewListMember2;
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
    private JPanel panelEnrolledPeople;
    private JTable tableEnrolledPeople;
    private JTextField fieldPeopleId;
    private JDateChooser dateChooser1, dateChooser2;
    private JTextFieldDateEditor dateEditor1, dateEditor2;
    private boolean mouseClick = false;
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //extended frames
    public AMS_UserInterface() {
        setTitle("User Dashboard");
        getContentPane().add(userMainPanel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);//display frame to center
//        setResizable(false);
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

        //Room panel setup
        mainRoomPanel();
        calendar1();
        calendar2();
        hideSomeSwing();
        moreOption();
        logo();

        //table click action
        displayMyRoomTable.addMouseListener(new MouseAdapter() {//display the selected row name to field name
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = displayMyRoomTable.getSelectedRow();
                TableModel model = displayMyRoomTable.getModel();
                roomNameField.setText(model.getValueAt(i, 0).toString());
                roomIDField.setText(model.getValueAt(i, 1).toString());
                disableRoomFields();
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
        //border style
        Border b1 = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        refreshRoomFieldButton.setBorderPainted(false);
        refreshRoomFieldButton.setOpaque(false);
        //back button customize
        backButton1.setOpaque(false);
        backButton1.setBorder(b1);
        backButton2.setOpaque(false);
        backButton2.setBorder(b1);
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //Account panel
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    userPasswordField.setEchoChar('\u0000');
                } else {
                    userPasswordField.setEchoChar('\u2023');
                }
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //perform edit one's own data
        userEditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmPass();
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //update one's own data
        updateMyDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fname = userFirstnameField.getText();
                String lname = userLastnameField.getText();
                String age_s = userAgeField.getText();
                String sex = userSexSelection.getSelectedItem().toString();
                String n_email = userEmailField.getText();
                String upass = userPasswordField.getText();
                String s_char1 ="^[ A-Za-z]+$";//valid characters to be input
                String s_char2 ="^[A-Za-z]+$";//valid characters to be input

                //statements and conditions
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
                }else if(!isEmailValid(n_email)){//if email is not valid character or not valid format
                    JOptionPane.showMessageDialog(null, "Invalid email address", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (upass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Can't process some field(s) is/are empty.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        int age = Integer.parseInt(age_s);//convert string to integer
                        boolean isValid = AccountsDataHandler.updateMyData(Integer.parseInt(userHostIDLabel.getText()),fname, lname, age, sex, n_email, upass);//update data function
                        if (isValid) {//if data is valid to be update
                            updateMyDataButton.setVisible(false);
                            userEditButton.setVisible(true);
                            uneditMyData();
                        }
                    } catch (NumberFormatException ex) {//some fields must contains numbers only
                        JOptionPane.showMessageDialog(null, "Some field must contain number(s) only.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //log out button action perform
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm logout", JOptionPane.YES_NO_OPTION);//logging out confirmation
                if (confirm == JOptionPane.YES_OPTION) {//if logging out confirmation is yes
                    JOptionPane.showMessageDialog(null, "Thank you for using our system!");
                    setVisible(false);//hide or log out from dashboard
                    new Sign_in().setVisible(true);//go to log in form
                }
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //refresh action perform
        refreshRoomFieldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearRoomField();
                recentJoinedRoom(Integer.parseInt(userHostIDLabel.getText()));
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //create room button action perform
        createRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearRoomField();
                disableRoomFields();
                String host_name = userNameLabel.getText();
                String s_char = "^[A-Za-z0-9_]+$";//valid characters to be input
                String room_name = JOptionPane.showInputDialog(null, "", "New_Room");
                if (room_name == null) {//if cancel the room creation
                    System.out.println("Create cancelled...");
                } else {
                    if (room_name.isEmpty()) {//if room name was empty
                        JOptionPane.showMessageDialog(null, "Can't process room name was empty", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (room_name.matches(".*\\s+.*")) {
                        JOptionPane.showMessageDialog(null, "Input cannot contain spaces.", "Error", JOptionPane.ERROR_MESSAGE);
                    }else if(!room_name.matches(s_char)){
                        JOptionPane.showMessageDialog(null, "Input can't special character(s)", "Error", JOptionPane.ERROR_MESSAGE);
                    }else if(room_name.length() < 4){//if room name length was less than 4
                        JOptionPane.showMessageDialog(null,"Room Name is too short!");
                    }else if(room_name.length() > 15){//if room name length exceed 15 characters
                        JOptionPane.showMessageDialog(null,"Room Name is too long!");
                    } else {
                        boolean isRoomExist = RoomHandler.isRoomExist(room_name, Integer.parseInt(userHostIDLabel.getText()));//check if room exist
                        if (isRoomExist) {//if room exist
                            JOptionPane.showMessageDialog(null, "Room was already exist.");
                        } else {
                            String room_key = JOptionPane.showInputDialog(null, "Enter Your Room password");//give a room key
                            if (room_key == null) {//if room key input is cancel
                                System.out.println("Create cancelled...");
                            } else {
                                if (room_key.isEmpty()) {//if room key was empty input
                                    JOptionPane.showMessageDialog(null, "Can't process input was empty", "Error", JOptionPane.ERROR_MESSAGE);
                                } else if (room_key.matches(".*\\s+.*")) {//room key can't contain spaces
                                    JOptionPane.showMessageDialog(null, "Input cannot contain spaces.", "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    RoomHandler.recordRoom(room_name, room_key, host_name, Integer.parseInt(userHostIDLabel.getText()));//record the created room
                                    userRoomList(Integer.parseInt(userHostIDLabel.getText()));//display the latest room list
                                    JOptionPane.showMessageDialog(null, "Room created successfully.");
                                }
                            }
                        }
                    }
                }
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //recent room table click action perform
        recentRoomTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = recentRoomTable.getSelectedRow();
                TableModel model = recentRoomTable.getModel();
                roomNameField.setText(model.getValueAt(i, 0).toString());//set to text field where selected row
                roomIDField.setText(model.getValueAt(i, 1).toString());//set to text field where selected row
                //disable editable fields
                roomNameField.setEditable(false);
                roomIDField.setEditable(false);
                //enable some fields
                enterRoomButton.setEnabled(true);
                deleteRoomButton.setEnabled(true);
                enterRoomButton.setOpaque(true);
                deleteRoomButton.setOpaque(true);
                enterRoomButton.setForeground(Color.WHITE);
                deleteRoomButton.setForeground(Color.WHITE);
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //search room action perform
        searchRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearRoomField();
                disableRoomFields();
                int user_id = Integer.parseInt(userHostIDLabel.getText());
                String room_id_s = JOptionPane.showInputDialog("Enter the Room id");
                if (room_id_s == null) {//if room search was cancel
                    System.out.println("Search cancelled");
                } else {
                    if (room_id_s.isEmpty()) {//if room id input was empty
                        JOptionPane.showMessageDialog(null, "Can't process no input(s)", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (room_id_s.matches(".*\\s+.*")) {//invalid spaces input
                        JOptionPane.showMessageDialog(null, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            int room_id = Integer.parseInt(room_id_s);//convert string to integer
                            if (RoomHandler.isSearchRoomHost(room_id, user_id)) {//check if room host
                                JOptionPane.showMessageDialog(null, "You are the host in that Room");
                                userRoomList(user_id);
                            } else {
                                boolean isRoomExist = RoomHandler.isSearchRoomExist(room_id);//check if room is exist
                                if (isRoomExist) {//if room was exist
                                    displaySearchRoom(room_id);//display room if exist
                                } else {
                                    displaySearchRoom(room_id);//display no existing room
                                    JOptionPane.showMessageDialog(null, "No room found");
                                }
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Input must contain number/s only", "Error", JOptionPane.ERROR_MESSAGE);
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
                int my_id = Integer.parseInt(userHostIDLabel.getText());
                boolean isHostEnter = RoomHandler.isSearchRoomHost(room_id, my_id);
                if (isHostEnter) {
                    myRoomStatus(Integer.parseInt(roomIDField.getText()));
                    hostPanel();
                } else {
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
                int my_id = Integer.parseInt(userHostIDLabel.getText());
                boolean isHost = RoomHandler.isSearchRoomHost(room_id, my_id);
                if (!isHost) {
                    deleteRoomButton.setEnabled(false);
                    deleteRoomButton.setOpaque(false);
                    deleteRoomButton.setForeground(Color.GRAY);
                    JOptionPane.showMessageDialog(null, "You're not the host of this room");
                } else {
                    int confirmDelete = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + room_name + "?", "Delete Room?", JOptionPane.YES_NO_OPTION);
                    if (confirmDelete == JOptionPane.YES_OPTION) {
                        RoomHandler.deleteRoom(room_name, room_id, my_id);
                        userRoomList(my_id);
                        enterRoomButton.setEnabled(false);
                        enterRoomButton.setOpaque(false);
                        enterRoomButton.setForeground(Color.GRAY);
                        deleteRoomButton.setEnabled(false);
                        deleteRoomButton.setOpaque(false);
                        deleteRoomButton.setForeground(Color.GRAY);
                        System.out.println(room_name + " id no. " + room_id + " is deleted.");
                    } else {
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
                if (e.getClickCount() == 2) {
                    String room_key = JOptionPane.showInputDialog("Room Key", labelMyRoomKey.getText());
                    if (room_key == null) {
                    } else {
                        if (room_key.equals("")) {
                            JOptionPane.showMessageDialog(null, "Input was empty!", "Error", JOptionPane.ERROR_MESSAGE);
                        } else if (room_key.matches(".*\\s+.*")) {
                            JOptionPane.showMessageDialog(null, "Input cannot contain spaces", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure want to save changed?", "Confirm Change", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
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
                fieldMemberName1.setText(model.getValueAt(i, 0).toString());
                labelMemberID1.setText(model.getValueAt(i, 2).toString());
                enableBtnAtHostPanel();
            }
        });
        tableAttendanceRoom2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tableAttendanceRoom2.getSelectedRow();
                TableModel model = tableAttendanceRoom2.getModel();
                fieldMemberName2.setText(model.getValueAt(i, 0).toString());
                labelMemberID2.setText(model.getValueAt(i, 2).toString());
                enableBtnAtMemberPanel();
            }
        });
        viewListMember1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (dateChooser1.getDate() == null) {
                    JOptionPane.showMessageDialog(null, "Please select a date first.");
                } else {
                    String date = dateEditor1.getText().toString();
                    boolean isDateSet = searchSetDate(labelRoomName1.getText(), Integer.parseInt(userHostIDLabel.getText()), date);
                    if (isDateSet) {
                        displayPeopleEnrolled(labelRoomName1.getText(), Integer.parseInt(userHostIDLabel.getText()), date);
                    } else {
                        int confirm = JOptionPane.showConfirmDialog(null, "Selected date is not yet set. \nSet a date (" + date + ") now?", "Message", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            setDatePeople(labelRoomName1.getText(), Integer.parseInt(userHostIDLabel.getText()), date);
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
                    boolean isDateSet = setMeThatDate(labelRoomName2.getText(),Integer.parseInt(labelhostID2.getText()), Integer.parseInt(userHostIDLabel.getText()), date);
                    if(isDateSet){
                        displayMeEnrolled(labelRoomName2.getText(),Integer.parseInt(labelhostID2.getText()), Integer.parseInt(userHostIDLabel.getText()), date);
                    }else {
                        displayMeEnrolled(labelRoomName2.getText(),Integer.parseInt(labelhostID2.getText()), Integer.parseInt(userHostIDLabel.getText()), date);
                        JOptionPane.showMessageDialog(null,"Selected date is not yet set.");
                    }
                }
            }
        });
        labelRoomName1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String old_room_name = labelRoomName1.getText();
                    String s_char = "^[A-Za-z0-9_]+$";
                    String new_room_name = JOptionPane.showInputDialog("Room Name", labelRoomName1.getText());
                    if (new_room_name == null) {
                    } else {
                        if (new_room_name.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Input was empty!", "Error", JOptionPane.ERROR_MESSAGE);
                        } else if (new_room_name.matches(".*\\s+.*")) {
                            JOptionPane.showMessageDialog(null, "Input cannot contain spaces", "Error", JOptionPane.ERROR_MESSAGE);
                        }else if(!new_room_name.matches(s_char)){
                            JOptionPane.showMessageDialog(null, "You entered invalid character(s)", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else if(new_room_name.length() < 4){
                            JOptionPane.showMessageDialog(null,"Room Name is too short!");
                        }
                        else if(new_room_name.length() > 15){
                            JOptionPane.showMessageDialog(null,"Room Name is too long!");
                        }
                        else {
                            updateRoomName(old_room_name, new_room_name, Integer.parseInt(labelRoomID1.getText()), Integer.parseInt(userHostIDLabel.getText()));
                        }
                    }
                }
            }
        });
        buttonPresent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String attendance_status = "Present";
                RoomHandler.markPeopleAttendance(labelRoomName1.getText(), Integer.parseInt(userHostIDLabel.getText()), Integer.parseInt(labelMemberID1.getText()), attendance_status, String.valueOf(dateEditor1.getText()));
                displayPeopleEnrolled(labelRoomName1.getText(), Integer.parseInt(userHostIDLabel.getText()), String.valueOf(dateEditor1.getText()));
            }
        });
        buttonAbsent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String attendance_status = "Absent";
                RoomHandler.markPeopleAttendance(labelRoomName1.getText(), Integer.parseInt(userHostIDLabel.getText()), Integer.parseInt(labelMemberID1.getText()), attendance_status, String.valueOf(dateEditor1.getText()));
                displayPeopleEnrolled(labelRoomName1.getText(), Integer.parseInt(userHostIDLabel.getText()), String.valueOf(dateEditor1.getText()));
            }
        });
        buttonExcuse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String attendance_status = "Excused";
                RoomHandler.markPeopleAttendance(labelRoomName1.getText(), Integer.parseInt(userHostIDLabel.getText()), Integer.parseInt(labelMemberID1.getText()), attendance_status, String.valueOf(dateEditor1.getText()));
                displayPeopleEnrolled(labelRoomName1.getText(), Integer.parseInt(userHostIDLabel.getText()), String.valueOf(dateEditor1.getText()));
            }
        });
        buttonMarkMe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String attendance_status = "Present";
                RoomHandler.markMeAttendance(labelRoomName2.getText(), Integer.parseInt(labelhostID2.getText()), Integer.parseInt(labelMemberID2.getText()), attendance_status, String.valueOf(dateEditor2.getText()));
                displayMeEnrolled(labelRoomName2.getText(), Integer.parseInt(labelhostID2.getText()), Integer.parseInt(userHostIDLabel.getText()), String.valueOf(dateEditor2.getText()));
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
                    boolean isDateSet = searchSetDate(labelRoomName1.getText(), Integer.parseInt(userHostIDLabel.getText()), date);
                    if (isDateSet) {
                        displayPeopleEnrolled(labelRoomName1.getText(), Integer.parseInt(userHostIDLabel.getText()), date);
                    } else {
                        int confirm = JOptionPane.showConfirmDialog(null, "Selected date is not yet set. \nSet a date (" + date + ") now?", "Message", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            setDatePeople(labelRoomName1.getText(), Integer.parseInt(userHostIDLabel.getText()), date);
                            displayPeopleEnrolled(labelRoomName1.getText(), Integer.parseInt(userHostIDLabel.getText()), date);
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
                    boolean isDateSet = setMeThatDate(labelRoomName2.getText(),Integer.parseInt(labelhostID2.getText()), Integer.parseInt(userHostIDLabel.getText()), date);
                    if(isDateSet){
                        displayMeEnrolled(labelRoomName2.getText(),Integer.parseInt(labelhostID2.getText()), Integer.parseInt(userHostIDLabel.getText()), date);
                    }else {
                        displayMeEnrolled(labelRoomName2.getText(),Integer.parseInt(labelhostID2.getText()), Integer.parseInt(userHostIDLabel.getText()), date);
                        JOptionPane.showMessageDialog(null,"The date you selected is not yet set.");
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
                        RoomHandler.removePersonInMyRoom(labelRoomName1.getText(),Integer.parseInt(userHostIDLabel.getText()),Integer.parseInt(fieldPeopleId.getText()));
                        displayPeopleEnrolled2(labelRoomName1.getText(),Integer.parseInt(userHostIDLabel.getText()));
                        displayPeopleEnrolled(roomNameField.getText(), Integer.parseInt(userHostIDLabel.getText()), dateEditor1.getText().toString());
                        mouseClick = false;
                        removePeople.setForeground(Color.GRAY);
                    }else if(confirm == JOptionPane.NO_OPTION){
                        displayPeopleEnrolled2(labelRoomName1.getText(),Integer.parseInt(userHostIDLabel.getText()));
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
                    RoomHandler.removeMeInThatRoom(labelRoomName2.getText(),Integer.parseInt(labelRoomID2.getText()),Integer.parseInt(userHostIDLabel.getText()));
                    mainRoomPanel();
                    clearRoomField();
                    recentJoinedRoom(Integer.parseInt(userHostIDLabel.getText()));
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
                displayPeopleEnrolled2(labelRoomName1.getText(),Integer.parseInt(userHostIDLabel.getText()));
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
        String userEmail = userEmailField.getText();
        System.out.println("Accessing " + userEmail + ".....");
        String confirmPass = JOptionPane.showInputDialog(null, "Enter your password: ");
        if (confirmPass == null) {
            System.out.println(">>> Edit cancelled <<<");
        } else {
            if (confirmPass.equals("")) {
                JOptionPane.showMessageDialog(null, "Can't process input was empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean isValid = AccountsDataHandler.confirmEdit(userEmail, confirmPass);
                if (isValid) {
                    System.out.println(userEmail + " has been accessed.");
                    userEditButton.setVisible(false);
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
        userSexSelection.setEnabled(true);
        userFirstnameField.setEditable(true);
        userLastnameField.setEditable(true);
        userAgeField.setEditable(true);
        userPasswordField.setEditable(true);
        showPasswordCheckBox.setEnabled(true);
        userPasswordField.setEchoChar('\u2023');
        userEmailField.setEditable(true);
    }

    private void uneditMyData() {
        userFirstnameField.setEditable(false);
        userLastnameField.setEditable(false);
        userAgeField.setEditable(false);
        userSexSelection.setEnabled(false);
        showPasswordCheckBox.setEnabled(false);
        userPasswordField.setEditable(false);
        userPasswordField.setEchoChar('\u2023');
        userEmailField.setEditable(false);
    }

    public void userRoomList(int host) {
        Object[][] data = null;
        String[] columnNames = {"My Room", "Id"};
        DefaultTableModel displayModel = new DefaultTableModel(data, columnNames) {
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
                System.out.println(room_name + ", " + room_id + ", " + host_name + ", " + host);
                while (rs.next()) {
                    String room_id2 = String.valueOf(rs.getInt("room_id"));
                    String room_name2 = rs.getString("room_name");
                    String host_name2 = rs.getString("host_name");
                    String list2[] = {room_name2, room_id2};
                    DefaultTableModel tblModel2 = (DefaultTableModel) displayMyRoomTable.getModel();
                    tblModel2.addRow(list2);
                    System.out.println(room_name2 + ", " + room_id2 + ", " + host_name2 + ", " + host);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
    }

    boolean displaySearchRoom(int room_id_input) {
        Object[][] data = null;
        String[] columnNames = {"Room Name", "Room id", "Host Name", "Host id", "Room Status"};
        DefaultTableModel displayModel = new DefaultTableModel(data, columnNames) {
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
            } else {
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

    void clearRoomField() {
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
        userRoomList(Integer.parseInt(userHostIDLabel.getText()));
    }

    void disableRoomFields() {
        roomNameField.setEditable(false);
        roomIDField.setEditable(false);
    }

    void enableRoomFields() {
        roomNameField.setEditable(true);
        roomIDField.setEditable(true);
    }

    public void hostPanel() {
        roomInterfacePanel.setVisible(false);
        roomHostAttendancePanel.setVisible(true);
        labelRoomName1.setText(roomNameField.getText());
        labelRoomID1.setText(String.valueOf(roomIDField.getText()));
        HostnameAndRoomKey(Integer.parseInt(roomIDField.getText()));
        displayPeopleEnrolled(roomNameField.getText(), Integer.parseInt(userHostIDLabel.getText()), dateEditor1.getText().toString());
        displayPeopleEnrolled2(roomNameField.getText(), Integer.parseInt(userHostIDLabel.getText()));
    }

    public void memberPanel() {
        roomInterfacePanel.setVisible(false);
        roomMemberAttendancePanel.setVisible(true);
        labelRoomName2.setText(roomNameField.getText());
        labelRoomID2.setText(String.valueOf(roomIDField.getText()));
        HostnameAndRoomKey(Integer.parseInt(roomIDField.getText()));
        displayMeEnrolled(roomNameField.getText(), Integer.parseInt(labelhostID2.getText()), Integer.parseInt(userHostIDLabel.getText()), dateEditor2.getText().toString());
    }

    public void mainRoomPanel() {
        roomHostAttendancePanel.setVisible(false);
        roomMemberAttendancePanel.setVisible(false);
        roomInterfacePanel.setVisible(true);
    }

    private void defaultT() {
        labelMemberID1.setText(null);
        labelMemberID2.setText(null);
        fieldMemberName1.setText(null);
        fieldMemberName2.setText(null);
    }

    private boolean roomStatus(int room_id, int my_id) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String query = "SELECT * FROM public.room_list WHERE room_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, room_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String status = rs.getString("status");
                if (status.equals("Close")) {
                    JOptionPane.showMessageDialog(null, "Room is Closed.");
                } else if (status.equals("Open")) {
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

    private void updateRoomKey(int room_id, String room_key) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            PreparedStatement updateStmt = conn.prepareStatement("UPDATE public.room_list SET room_password = ? WHERE room_id = ?");
            updateStmt.setString(1, room_key);
            updateStmt.setInt(2, room_id);
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
    private boolean isImEnrolled(int room_id2, String room_name, int host_id, int my_id){
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String sql = "SELECT * FROM rooms."+room_name+"_"+host_id+" WHERE ams_acc_id = "+my_id;
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
                    String room_key = JOptionPane.showInputDialog("Enter the room key");
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
        String[] columnNames= {"You","Email","Account Id","Attendance Status","Date"};
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
                String list[] = {people_name, people_email, ams_id, mark,date};
                DefaultTableModel tblModel = (DefaultTableModel) tableAttendanceRoom2.getModel();
                tblModel.addRow(list);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
    }
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
        userSexSelection.setEnabled(false);
        mainTabs.remove(aboutPanel);

        //uneditable some swing temporary
        userFirstnameField.setEditable(false);
        userLastnameField.setEditable(false);
        userAgeField.setEditable(false);
        userEmailField.setEditable(false);
        userPasswordField.setEditable(false);
        roomNameField.setEditable(false);
        roomIDField.setEditable(false);

        //modify some swing style
        userPasswordField.setEchoChar('\u2023');
    }
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    void logo(){
        //Insert background logo
//        JLabel logo = new JLabel();
//        logo.setSize(680,380);
//        logo.setIcon(new ImageIcon("C:\\Users\\limch\\OneDrive\\Documents\\JL_LIM\\AttendanceManagementSystem\\src\\main\\java\\Pictures\\about-logo.png"));
////        panelAboutDesign.setLayout((LayoutManager)null);
//        panelAboutDesign.add(logo);
    }
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AMS_UserInterface().setVisible(true);
        });
    }
}
