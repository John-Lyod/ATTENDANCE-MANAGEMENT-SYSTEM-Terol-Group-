package aTerolGroup_OOP_FinalProject;
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

public class AMS_AccountsList extends JFrame {
    private JTable accTable;
    private JButton addButton;
    private JTextField firstnameField;
    private JTextField lastnameField;
    private JTextField ageField;
    private JComboBox genderSelection;
    private JTextField emailField;
    private JTextField passwordField;
    private JComboBox roleSelection;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JPanel wholePanel;
    private JComboBox idSelection;
    private JPanel accountsPanel;
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JLabel firstnameLabel;
    private JLabel ageLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel titleLabel;
    private JLabel lastnameLabel;
    private JLabel genderLabel;
    private JLabel roleLabel;
    private JLabel idLabel;

/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //extended frame
    public AMS_AccountsList() {;
        displayAllData();
        setTitle("List of Accounts");
        getContentPane().add(wholePanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

        //id's selection
        idSelection.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clearFields();//clear fields when clicking id selection
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

        //when clicking table
        accTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = accTable.getSelectedRow();
                TableModel model = accTable.getModel();
                idSelection.setSelectedItem(model.getValueAt(i,0).toString());
                firstnameField.setText(model.getValueAt(i,1).toString());
                lastnameField.setText(model.getValueAt(i,2).toString());
                ageField.setText(model.getValueAt(i,3).toString());
                genderSelection.setSelectedItem(model.getValueAt(i,4).toString());
                emailField.setText(model.getValueAt(i,5).toString());
                passwordField.setText(model.getValueAt(i,6).toString());
                roleSelection.setSelectedItem(model.getValueAt(i,7).toString());
                firstnameField.requestFocus();
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //add function button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstname = firstnameField.getText();
                String lastname = lastnameField.getText();
                String age_s = ageField.getText();
                String gender = genderSelection.getSelectedItem().toString();
                String email = emailField.getText();
                String password = passwordField.getText();
                String role = roleSelection.getSelectedItem().toString();
                String s_char1 ="^[ A-Za-z]+$";//valid characters to input
                String s_char2 ="^[A-Za-z]+$";//valid characters to input

                //statements and condition on adding function
                if(firstname.isEmpty() && lastname.isEmpty() && age_s.isEmpty() && email.isEmpty() && password.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field was empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(firstname.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field was empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(!firstname.matches(s_char1)){
                    JOptionPane.showMessageDialog(null, "Invalid character/s at Firstname","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(lastname.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field was empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(!lastname.matches(s_char2)){
                    JOptionPane.showMessageDialog(null, "Invalid character/s at Lastname","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(age_s.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field was empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(email.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field was empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(!isEmailValid(email)){
                    JOptionPane.showMessageDialog(null, "Invalid email address","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(password.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field was empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    try {
                        int age = Integer.parseInt(age_s);//try to convert string to integer if age input is numbers
                        boolean isSameData = AccountsDataHandler.ifSameDataOnAdding(firstname, lastname, age, gender, email, password, role);
                        if(isSameData){//if data provided was exist already
                            JOptionPane.showMessageDialog(null,"Data is already exist.");
                            clearFields();
                        }
                        else {
                            boolean isEmailExist = AccountsDataHandler.addData(firstname, lastname, age, gender, email, password, role);//check email if already taken
                            if (isEmailExist) {//if email is taken
                                JOptionPane.showMessageDialog(null, "Email is already used!");
                            } else {//if email is not yet taken then perform adding data
                                AccountsDataHandler.showData();//show the new list on print statement
                                displayAllData();//refresh the display table
                                JOptionPane.showMessageDialog(null, "Data Added...");
                                clearFields();
                            }
                        }
                    }catch (NumberFormatException ex){//when input is not numbers on some fields
                        JOptionPane.showMessageDialog(null,"Some field must contain number(s) only","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //delete function button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstname = firstnameField.getText();
                String lastname = lastnameField.getText();
                String age_s = ageField.getText();
                String gender = genderSelection.getSelectedItem().toString();
                String email = emailField.getText();
                String password = passwordField.getText();
                String role = roleSelection.getSelectedItem().toString();
                String id_s = idSelection.getSelectedItem().toString();

                //statements and condition on deleting
                if(firstname.isEmpty() && lastname.isEmpty() && age_s.isEmpty() && email.isEmpty() && password.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field was empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(firstname.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field was empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(lastname.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field was empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(age_s.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field was empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(email.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field was empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(password.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field was empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else {
                    boolean isDataExist = AccountsDataHandler.isEmailExist(email);//check if email is registered or exist
                    if (!isDataExist) {//if email is not registered or not exist
                        JOptionPane.showMessageDialog(null, "Can't process email is not exist .","Error",JOptionPane.ERROR_MESSAGE);
                    }else {
                        int id = Integer.parseInt(id_s);//convert string to integer
                        try {
                            int age = Integer.parseInt(age_s);//convert string to integer
                            boolean isExist = AccountsDataHandler.ifDataMatched(id, firstname, lastname, age, gender, email, password, role);//check if data to be delete is matched on existing data
                            if (isExist) {//if data is matched
                                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this data?", "Confirmation", JOptionPane.YES_NO_OPTION);
                                if (confirm == JOptionPane.YES_OPTION) {//if delete confirmation is yes
                                    AccountsDataHandler.deleteData(id, firstname, lastname, age, gender, email, password, role);//delete the data
                                    AccountsDataHandler.showData();//display latest list
                                    RoomHandler.deleteMyRooms(id);//delete rooms and logs of this person to be drop or remove
                                    displayAllData();//refresh the table display
                                    JOptionPane.showMessageDialog(null, "Data deleted");
                                    clearFields();
                                } else {//if delete confirmation is no
                                    JOptionPane.showMessageDialog(null, "Delete cancelled.");
                                    System.out.println(">>> Delete cancelled <<<");
                                }
                            } else {//if some data to be deleted is missing
                                JOptionPane.showMessageDialog(null, "Some data requirement(s) is/are not matched!","Error",JOptionPane.ERROR_MESSAGE);
                                System.out.println(">>> Delete cancelled <<<");
                            }
                        } catch (NumberFormatException ex) {//some fields must contain numbers only
                            JOptionPane.showMessageDialog(null, "Some field must contain numbers only","Error",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //update function button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstname = firstnameField.getText();
                String lastname = lastnameField.getText();
                String age_s = ageField.getText();
                String gender = genderSelection.getSelectedItem().toString();
                String email = emailField.getText();
                String password = passwordField.getText();
                String role = roleSelection.getSelectedItem().toString();
                String id_s = idSelection.getSelectedItem().toString();
                String s_char1 ="^[ A-Za-z]+$";//valid characters to input
                String s_char2 ="^[A-Za-z]+$";//valid characters to input

                //statements and condition on adding function
                if(firstname.isEmpty() && lastname.isEmpty() && age_s.isEmpty() && email.isEmpty() && password.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field was empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(firstname.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field was empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(!firstname.matches(s_char1)){
                    JOptionPane.showMessageDialog(null, "Invalid character/s at Firstname","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(lastname.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field was empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(!lastname.matches(s_char2)){
                    JOptionPane.showMessageDialog(null, "Invalid character/s at Lastname","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(age_s.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field was empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(email.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field was empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(!isEmailValid(email)){
                    JOptionPane.showMessageDialog(null, "Invalid email address","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if(password.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Can't process some field was empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else {
                    int id = Integer.parseInt(id_s);//convert string to integer
                    try {
                        int age = Integer.parseInt(age_s);//try to convert string to integer if age input is numbers
                        boolean isNothingChange = AccountsDataHandler.ifDataMatched(id, firstname, lastname, age, gender, email, password, role);
                        if (isNothingChange) {//if data provided is not changed
                            JOptionPane.showMessageDialog(null, "There's no changes of data.");
                        } else {
                            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to save changed?", "Confirmation", JOptionPane.YES_NO_OPTION);//update confirmation
                            if (confirm == JOptionPane.YES_OPTION) {//if update confirmation is yes
                                AccountsDataHandler.updateData(id, firstname, lastname, age, gender, email, password, role);//update the data
                                AccountsDataHandler.showData();//display latest list on print statements
                                searchEmail(email);//display the update data in a row
                                JOptionPane.showMessageDialog(null, "Data Updated...");
                                clearFields();
                            } else {//if update confirmation was no
                                JOptionPane.showMessageDialog(null, "Update cancelled.");
                                System.out.println(">>> Update cancelled <<<");
                            }
                        }
                    } catch (NumberFormatException ex) {//some fields must contain numbers only
                        JOptionPane.showMessageDialog(null, "Some field must contain number(s) only","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //search and display the data from selected id
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
                String id_s = idSelection.getSelectedItem().toString();
                if(idSelection.getSelectedItem().equals("<all>")){
                    displayAllData();
                }
                else{
                    int id = Integer.parseInt(id_s);
                    searchID(id);
                }
            }
        });
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    //when email is valid
    private boolean isEmailValid(String input) {
        // Regular expression to validate the email address
        String regex = "^(?!.*[_.]{2})[a-zA-Z0-9._]+@[a-zA-Z0-9]+\\.[a-zA-Z]+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    //view all data
    void displayAllData(){
        Object[][] data=null;//default data
        String[] columnNames= {"id","firstname","lastname","age","sex","email","password","role"};//create column header
        DefaultTableModel displayModel = new DefaultTableModel(data,columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable row editing for all cells
            }
        };
        accTable.getTableHeader().setResizingAllowed(false);
        accTable.getTableHeader().setReorderingAllowed(false);
        accTable.setModel(displayModel);
        idSelection.removeAllItems();
        idSelection.addItem("<all>");
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String sql = "SELECT * FROM public.ams_acc ORDER BY id ASC";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String id = String.valueOf(rs.getInt("id"));
                    String firstname = rs.getString("firstname");
                    String lastname = rs.getString("lastname");
                    String age = String.valueOf(rs.getInt("age"));
                    String gender = rs.getString("gender");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String role = rs.getString("role");
                    String list[] = {id,firstname,lastname,age,gender,email,password,role};
                    DefaultTableModel tblModel = (DefaultTableModel)accTable.getModel();
                    tblModel.addRow(list);
                    idSelection.addItem(id);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //display searched data in a row
    private boolean searchID(int id) {
        Object[][] rowData=null;//default data
        String[] columnNames= {"id","firstname","lastname","age","sex","email","password","role"};//create table header
        DefaultTableModel searchModel = new DefaultTableModel(rowData,columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable row editing for all cells
            }
        };
        accTable.setModel(searchModel);
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String query = "SELECT firstname, lastname, age, gender, email, password, role FROM public.ams_acc WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String firstname = rs.getString(1);
                String lastname = rs.getString(2);
                int age = rs.getInt(3);
                String gender = rs.getString(4);
                String email = rs.getString(5);
                String password = rs.getString(6);
                String role = rs.getString(7);

                //set data to row
                String list[] = {String.valueOf(id),firstname,lastname, String.valueOf(age),gender,email,password,role};
                DefaultTableModel tblModel = (DefaultTableModel)accTable.getModel();
                tblModel.addRow(list);
                firstnameField.setText(firstname);
                lastnameField.setText(lastname);
                ageField.setText(String.valueOf(age));
                genderSelection.setSelectedItem(gender);
                emailField.setText(email);
                passwordField.setText(password);
                roleSelection.setSelectedItem(role);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // No data found with the provided id
        return false;
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //display data in a row where selected email
    private boolean searchEmail(String mail) {
        Object[][] rowData=null;//default data
        String[] columnNames= {"id","firstname","lastname","age","gender","email","password","role"};//create table header
        DefaultTableModel searchModel = new DefaultTableModel(rowData,columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable row editing for all cells
            }
        };
        accTable.setModel(searchModel);
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String query = "SELECT id, firstname, lastname, age, gender, email, password, role FROM public.ams_acc WHERE email=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1,mail);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt(1);
                String firstname = rs.getString(2);
                String lastname = rs.getString(3);
                int age = rs.getInt(4);
                String gender = rs.getString(5);
                String email = rs.getString(6);
                String password = rs.getString(7);
                String role = rs.getString(8);

                //set data to row
                String list[] = {String.valueOf(id), firstname,lastname, String.valueOf(age),gender,email,password,role};
                DefaultTableModel tblModel = (DefaultTableModel)accTable.getModel();
                tblModel.addRow(list);
                idSelection.setSelectedItem(String.valueOf(id));
                firstnameField.setText(firstname);
                lastnameField.setText(lastname);
                ageField.setText(String.valueOf(age));
                genderSelection.setSelectedItem(gender);
                emailField.setText(email);
                passwordField.setText(password);
                roleSelection.setSelectedItem(role);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // No data found with the provided id
        return false;
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //clear some fields
    public void clearFields(){
        firstnameField.setText("");
        lastnameField.setText("");
        ageField.setText("");
        emailField.setText("");
        passwordField.setText("");
        genderSelection.setSelectedItem("unidentified");//default selection
        roleSelection.setSelectedItem("user");//default selection
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //enable runnable frame AMS_AccountsList
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AMS_AccountsList();
            }
        });
    }
}
