package aTerolGroup_OOP_FinalProject;
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
public class Registration extends JFrame {
    private JPanel panelOverall;
    private JPanel panelForm;
    private JCheckBox showCheckBox;
    private JButton buttonClear;
    private JPasswordField fieldConfirmPassword;
    private JButton buttonSubmit;
    private JPasswordField fieldPassword;
    private JTextField fieldEmail;
    private JTextField fieldFirstname;
    private JTextField fieldLastname;
    private JTextField fieldAge;
    private JComboBox comboBoxGender;
    private JLabel labelReporter;
    private JLabel labelToLogin;
    private JPanel panelLogo;
    private JPanel panelBorder;

    // Extended frame
    public Registration(){
        setTitle("Registration");
        getContentPane().add(panelOverall);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//Do nothing when pressing X bar
        pack();
        setLocationRelativeTo(null);//visible form at center
        this.getRootPane().setDefaultButton(buttonSubmit);//Press Enter instead of clicking the submit
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //Insert background logo
        panelLogo.setOpaque(false);
        String image_path = "C:\\Users\\limch\\OneDrive\\Documents\\JL_LIM\\AttendanceManagementSystem\\src\\main\\java\\Pictures\\paper-1.gif";
        JLabel logo = new JLabel();
        logo.setBounds(1,2,748,498);
        logo.setIcon(new ImageIcon(image_path));
        panelOverall.setLayout((LayoutManager)null);
        panelOverall.add(logo);
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //perform submit
        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstname = fieldFirstname.getText();
                String lastname = fieldLastname.getText();
                String s_age = fieldAge.getText();
                String gender = comboBoxGender.getSelectedItem().toString();
                String email = fieldEmail.getText();
                String password = fieldPassword.getText();
                String confirm_password = fieldConfirmPassword.getText();
                String role = "user";
                String s_char1 ="^[ A-Za-z]+$";
                String s_char2 ="^[A-Za-z]+$";

                if(firstname.isEmpty() && lastname.isEmpty() && s_age.isEmpty() && email.isEmpty() && password.isEmpty()){
                    labelReporter.setText("Fields must not be empty");
                }
                else if(firstname.isEmpty()){
                    labelReporter.setText("Fields must not be empty");
                }
                else if(!Character.isUpperCase(firstname.charAt(0))){
                    labelReporter.setText("Firstname must start with a capital letter");
                }
                else if(!firstname.matches(s_char1)){
                    labelReporter.setText("Invalid character at firstname");
                }
                else if(lastname.isEmpty()){
                    labelReporter.setText("Fields must not be empty");
                }
                else if(!Character.isUpperCase(lastname.charAt(0))){
                    labelReporter.setText("Lastname must start with a capital letter");
                }
                else if(!lastname.matches(s_char2)){
                    labelReporter.setText("Invalid character at lastname");
                }
                else if(lastname.equals(".*\\s+.*")){
                    labelReporter.setText("Input/s can't contain spaces.");
                }
                else if(s_age.isEmpty()){
                    labelReporter.setText("Fields must not be empty");
                }
                else if(s_age.contains(".")){
                    labelReporter.setText("Illegal characters at age");
                }
                else if(s_age.equals(".*\\s+.*")){
                    labelReporter.setText("Input/s can't contain spaces.");
                }
                else if(email.isEmpty()){
                    labelReporter.setText("Fields must not be empty");
                }
                else if(email.equals(".*\\s+.*")){
                    labelReporter.setText("Input/s can't contain spaces.");
                }
                else if(password.isEmpty()){
                    labelReporter.setText("Fields must not be empty");
                }
                else if(password.equals(".*\\s+.*")){
                    labelReporter.setText("Fields must not be empty");
                }
                else if(confirm_password.isEmpty()){
                    labelReporter.setText("Please re-enter password");
                }
                else if(confirm_password.equals(".*\\s+.*")){
                    labelReporter.setText("Input/s can't contain spaces.");
                }
                else if(!confirm_password.equals(password)){
                    labelReporter.setText("Password does not match");
                }
                else if(!isEmailValid(email)) {
                    labelReporter.setText("Invalid email address");
                }
                else {
                    try {
                        int age = Integer.parseInt(s_age);//Convert string to float numbers
                        labelReporter.setText(" ");
                        boolean isExist = AccountsDataHandler.registerAccount(firstname, lastname, age, gender, email, password, role);
                        if (isExist) {
                            labelReporter.setText("Email is already used");
                        } else {
                            AccountsDataHandler.registerAccount(firstname, lastname, age, gender, email, password, role);
                            AccountsDataHandler.showData();
                            JOptionPane.showMessageDialog(null, "Registered Successfully");
                            clearFields();
                        }
                    } catch (NumberFormatException ex) {//if user enter letters it will print
                        labelReporter.setBounds(75, 70, 190, 30);
                        labelReporter.setText("Some field must contain numbers only");
                    }

                }
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //perform clearing fields
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //Gender selection
        comboBoxGender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showCheckBox.isSelected()) {//when want to show passwords
                    fieldPassword.setEchoChar((char) 0);//visible password placeholder
                    fieldConfirmPassword.setEchoChar((char) 0);//visible confirm-password placeholder
                    labelReporter.setText(" ");
                } else {//hide passwords
                    fieldPassword.setEchoChar('\u2022');//dotted password placeholder
                    fieldConfirmPassword.setEchoChar('\u2022');//dotted confirm-password placeholder
                    labelReporter.setText(" ");
                }
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //open the login form
        labelToLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 1){
                    setVisible(false);
                    new Sign_in().setVisible(true);
                }
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //focus gained and focus lost to hide red message
        fieldFirstname.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                labelReporter.setText(" ");
            }

            @Override
            public void focusLost(FocusEvent e) {
                labelReporter.setText(" ");
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //focus gained and focus lost to hide red message
        fieldLastname.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                labelReporter.setText(" ");
            }

            @Override
            public void focusLost(FocusEvent e) {
                labelReporter.setText(" ");
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //focus gained and focus lost to hide red message
        fieldAge.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                labelReporter.setText(" ");
            }

            @Override
            public void focusLost(FocusEvent e) {
                labelReporter.setText(" ");
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //focus gained and focus lost to hide red message
        comboBoxGender.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                labelReporter.setText(" ");
            }

            @Override
            public void focusLost(FocusEvent e) {
                labelReporter.setText(" ");
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //focus gained and focus lost to hide red message
        fieldEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                labelReporter.setText(" ");
            }

            @Override
            public void focusLost(FocusEvent e) {
                labelReporter.setText(" ");
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //focus gained and focus lost to hide red message
        fieldPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                labelReporter.setText(" ");
            }

            @Override
            public void focusLost(FocusEvent e) {
                labelReporter.setText(" ");
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //focus gained and focus lost to hide red message
        fieldConfirmPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                labelReporter.setText(" ");
            }

            @Override
            public void focusLost(FocusEvent e) {
                labelReporter.setText(" ");
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //focus gained and focus lost to hide red message
        comboBoxGender.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                labelReporter.setText(" ");
            }

            @Override
            public void focusLost(FocusEvent e) {
                labelReporter.setText(" ");
            }
        });
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //show password
        showCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showCheckBox.isSelected()) {//check the box
                    fieldPassword.setEchoChar((char) 0);//visible password text
                    fieldConfirmPassword.setEchoChar((char) 0);//visible confirm-password text
                    labelReporter.setText(" ");
                } else {
                    fieldPassword.setEchoChar('\u2022');//dotted password text
                    fieldConfirmPassword.setEchoChar('\u2022');//dotted confirm-password text
                    labelReporter.setText(" ");
                }
            }
        });
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    void clearFields(){
        //setting text to null
        fieldFirstname.setText(null);
        fieldLastname.setText(null);
        fieldAge.setText(null);
        fieldEmail.setText(null);
        fieldPassword.setText(null);
        fieldConfirmPassword.setText(null);
        labelReporter.setText("");

        //default gender selected item
        comboBoxGender.setSelectedItem("unidentified");

        //field texts style color
        fieldFirstname.setForeground(Color.BLACK);
        fieldLastname.setForeground(Color.BLACK);
        fieldAge.setForeground(Color.BLACK);
        fieldEmail.setForeground(Color.BLACK);
        fieldPassword.setForeground(Color.BLACK);
        fieldAge.setForeground(Color.BLACK);
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    private boolean isEmailValid(String input) {
        // Regular expression to validate the email address
        String regex = "^(?!.*[_.]{2})[a-zA-Z0-9._]+@[a-zA-Z0-9]+\\.[a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //enable runnable registration form
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Registration().setVisible(true);;
            }
        });
    }
}
