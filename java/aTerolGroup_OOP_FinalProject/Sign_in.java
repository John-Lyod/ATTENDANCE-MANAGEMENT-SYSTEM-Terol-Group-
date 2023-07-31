package aTerolGroup_OOP_FinalProject;
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;
import java.util.Timer;

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
public class Sign_in extends JFrame {
    private JPanel wholePanel;
    private JPanel panelLogin;
    private JPanel panelLogo;
    private JLabel labelReporter;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckbox;
    private JButton buttonSign_in;
    private JLabel newUserBtn;
    private JLabel forgotPassBtn;
    private JLabel labelCountDownMessage;
    private JLabel labelCountDownTimer;
    private JLabel lgoinHeader;
    private int login_attempt;
    private boolean mouseClickEnabled = true;
    private Timer timer;
    private int count;
    private JLabel lock;
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
   //extended frame
    public Sign_in() {
        setTitle("Login");
        getContentPane().add(wholePanel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        this.setResizable(false);
        this.getRootPane().setDefaultButton(buttonSign_in);//Press Enter instead of clicking the sign
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?","Confirm exit",JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
        moreOptions();

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //Insert background logo
        JLabel logo = new JLabel();
        logo.setSize(495,300);
        logo.setIcon(new ImageIcon("C:\\Users\\limch\\OneDrive\\Documents\\JL_LIM\\AttendanceManagementSystem\\src\\main\\java\\Pictures\\logo.jpg"));
        panelLogo.setLayout((LayoutManager)null);
        panelLogo.add(logo);
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //for email field style
        emailField.setText("Email");
        addPlaceholderStyle(emailField);

        //Email TextField focus gained and focus lost
        emailField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(mouseClickEnabled) {
                    if (emailField.getText().equals("Email")) {
                        emailField.requestFocus();
                        emailField.setText(null);
                        labelReporter.setText(" ");
                        removePlaceholderStyle(emailField);
                    } else {
                        labelReporter.setText(" ");
                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(emailField.getText().equals("")){
                    emailField.setText("Email");
                    addPlaceholderStyle(emailField);
                    labelReporter.setText(" ");
                }else{
                    labelReporter.setText(" ");
                }
            }
        });
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //For password field style
        passwordField.setText("Password");
        passwordField.setEchoChar('\u0000');
        addPlaceholderStyle(passwordField);
        //Password TextField focus gained and focus lost
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(mouseClickEnabled) {
                    if (passwordField.getText().equals("Password")) {
                        passwordField.setText("");
                        labelReporter.setText(" ");
                        passwordField.requestFocus();
                        removePlaceholderStyle(passwordField);
                        passwordField.setEchoChar('\u2022');
                    } else {
                        labelReporter.setText(" ");
                    }
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(passwordField.getText().length()==0) {
                    passwordField.setText("Password");
                    passwordField.setEchoChar((char) 0);
                    addPlaceholderStyle(passwordField);
                    labelReporter.setText(" ");
                }else{
                    labelReporter.setText(" ");
                }
            }
        });
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //sign in perform
        buttonSign_in.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                //statements and condition
                if(email.isEmpty()){
                    labelReporter.setText("Fields must not be empty!");
                }else if(email.contains(" ")){
                    labelReporter.setText("Invalid input at email");
                }
                else if(email.equals("Email")){
                    labelReporter.setText("Fields must not be empty!");
                }
                else if(password.isEmpty()){
                    labelReporter.setText("Fields must not be empty!");
                }
                else if(password.contains(" ")){
                    labelReporter.setText("Invalid input at password");
                }
                else if(password.equals("Password")){
                    labelReporter.setText("Fields must not be empty!");
                }
                else {
                    labelReporter.setText(" ");
                    boolean isValid = AccountsDataHandler.loginCheck(email, password);//check if accounts is register
                    if (isValid) {//if account is registered then sign in
                        clearFields();//clear some fields
                        setVisible(false);
                    } else {
                        login_attempt ++;//add attempts if failed login
                        System.out.println("Login Attempt: "+login_attempt);
                        labelReporter.setText("Wrong email or password.");
                        if(login_attempt == 3){//maximum attempts
                            labelReporter.setText(" ");
                            disableForm();//temporary disable the login form
                            System.out.println("Reach maximum attempt. Login failed!");
                            JOptionPane.showMessageDialog(null,"Reach maximum attempt\nLogin failed!","Message!",JOptionPane.ERROR_MESSAGE);
//                            System.out.println("Login Attempt: "+login_attempt);
                        }
                    }
                }
            }
        });
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //show password check box
        showPasswordCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckbox.isSelected()) {//if check the box
                    passwordField.setEchoChar((char) 0);//visible text password
                    labelReporter.setText(" ");
                    passwordField.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            passwordField.setEchoChar('\u0000');//visible text password
                        }
                    });
                } else {//if uncheck the box
                    passwordField.setEchoChar('\u2022');//dotted text password
                    labelReporter.setText(" ");
                    passwordField.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            passwordField.setEchoChar('\u2022');//dotted text password
                        }
                    });
                }
            }
        });
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //When user's forgot there password
        forgotPassBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(mouseClickEnabled) {
                    if (e.getClickCount() == 1) {
                        setVisible(false);//close login form
                        new ForgotPassword().setVisible(true);//visible the forgot password frame
                    }
                }
            }
        });
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //Perform to RegistrationForm GUI
        newUserBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(mouseClickEnabled) {
                    if (e.getClickCount() == 1) {
                        setVisible(false);//close login form
                        new Registration().setVisible(true);//visible the registration form
                    }
                }
            }
        });
    }
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //Placeholder Styles
    public void addPlaceholderStyle(JTextField textField){
        Font font = textField.getFont();
        font = font.deriveFont(Font.ITALIC);
        textField.setFont(font);
        textField.setForeground(Color.gray);
    }
    public void removePlaceholderStyle(JTextField textField){
        Font font = textField.getFont();
        font = font.deriveFont(Font.PLAIN|Font.BOLD);
        textField.setFont(font);
        textField.setForeground(Color.black);
    }
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //clear Fields on login fields
    void clearFields(){
        emailField.setText("Email");
        addPlaceholderStyle(emailField);
        passwordField.setText("Password");
        passwordField.setEchoChar('\u0000');
        addPlaceholderStyle(passwordField);
    }
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //Disable a login form when user reach maximum attempt on login
    void disableForm(){
        clearFields();
        emailField.setEditable(false);
        passwordField.setEditable(false);
        showPasswordCheckbox.setEnabled(false);
        buttonSign_in.setEnabled(false);
        mouseClickEnabled = false;
        buttonSign_in.requestFocus();
        countDown();
        viewSomeSwing();
    }
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //Enable a login form after countdown is over
    void enableForm(){
        clearFields();
        emailField.setEditable(true);
        passwordField.setEditable(true);
        showPasswordCheckbox.setEnabled(true);
        buttonSign_in.setEnabled(true);
        mouseClickEnabled = true;

    }
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //countdown timer
    void countDown(){
        count = 10;//amount of time to lock form
        timer = new java.util.Timer();
        // Create a TimerTask that will decrement the totalSeconds variable
        TimerTask task = new TimerTask() {
            public void run() {
                if (count > 0) {
                    int minutes = count / 60;
                    int seconds = count % 60;
                    labelCountDownMessage.setText("Try again in");
                    labelCountDownTimer.setText(minutes + ":" + seconds);
                    count--;
                } else {
                    System.out.println("\nTime's up! You can now login again.");
                    timer.cancel(); // Stop the timer when time's up
                    login_attempt = 0;//refresh login attempt to 0
                    enableForm();//enable form after stopping countdown
                    hideSomeSwing();//hide some swing again
                    showPasswordCheckbox.requestFocus();
                }
            }
        };
        // Schedule the TimerTask to run every 1 second (1000 milliseconds)
        timer.scheduleAtFixedRate(task, 0, 1000);
    }
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    void moreOptions(){
        showPasswordCheckbox.requestFocus();
        hideSomeSwing();
//        buttonSign_in.setPreferredSize(new Dimension(120,40));
    }
    void hideSomeSwing(){
        labelCountDownMessage.setVisible(false);
        labelCountDownMessage.setText(null);
        labelCountDownTimer.setVisible(false);
        labelCountDownTimer.setText(null);

    }
    void viewSomeSwing(){
        labelCountDownMessage.setVisible(true);
        labelCountDownTimer.setVisible(true);
    }

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //enable runnable login form
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Sign_in().setVisible(true);;
            }
        });
    }
}
