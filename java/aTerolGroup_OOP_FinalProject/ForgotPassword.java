package aTerolGroup_OOP_FinalProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotPassword extends JFrame {
    private JPanel wholePanel;
    private JLabel titleLabel;
    private JTable tableFoundAcc;
    private JTextField fieldID;
    private JButton yesButton;
    private JButton noButton;
    private JPanel panelFoundAcc;
    private JPanel panelFillForm;
    private JPanel panelFoundAcc2;
    private JPanel panelFillForm2;
    private JButton buttonFindMyAcc;
    private JTextField fieldEmail;
    private JLabel buttonLogin;
    private JLabel labelID;
    private JLabel labelEmail;

    public ForgotPassword(){
        setTitle("Forgot password?");
        getContentPane().add(wholePanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(400, 250);
        setLocationRelativeTo(null);
        this.setResizable(false);
        panelFillForm.setBackground(Color.LIGHT_GRAY);
        this.getRootPane().setDefaultButton(buttonFindMyAcc);


        hideSomeComponent();
        buttonFindMyAcc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s_id = fieldID.getText();
                String email = fieldEmail.getText();
                if(s_id.isEmpty() && email.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Can't process fields was empty!","Warning",JOptionPane.ERROR_MESSAGE);
                }
                else if(s_id.contains(" ")){
                    JOptionPane.showMessageDialog(null,"Input can't contain spaces!","Warning",JOptionPane.ERROR_MESSAGE);
                }
                else if(s_id.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Can't process some fields was empty!","Warning",JOptionPane.ERROR_MESSAGE);

                }
                else if(email.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Can't process fields was empty!","Warning",JOptionPane.ERROR_MESSAGE);

                }else if(email.contains(" ")){
                    JOptionPane.showMessageDialog(null,"Input can't contain spaces!","Warning",JOptionPane.ERROR_MESSAGE);

                }
                else {
                    try {
                        int id = Integer.parseInt(s_id);
                        if(displayFoundData(id, email)){
                            panelFillForm.setVisible(false);
                            panelFoundAcc.setVisible(true);
                        }else{
                            JOptionPane.showMessageDialog(null,"The data you provide was not found or \n not matched on the existing accounts.","Warning",JOptionPane.ERROR_MESSAGE);
                        }
                    }catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(null,"Some field must contain number(s) only.","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelFoundAcc.setVisible(false);
                panelFillForm.setVisible(true);
            }
        });
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s_id = fieldID.getText();
                String email = fieldEmail.getText();
                String new_password = JOptionPane.showInputDialog("Enter a new password");
                if(new_password == null){
                }else{
                    if(new_password.isEmpty()){
                        JOptionPane.showMessageDialog(null,"No Input!","Warning!",JOptionPane.ERROR_MESSAGE);
                    }else if(new_password.matches(".*\\s+.*")){
                        JOptionPane.showMessageDialog(null,"Input can't contain spaces,","Warning",JOptionPane.ERROR_MESSAGE);
                    }else{
                        int id = Integer.parseInt(s_id);
                        updateMyPassword(id, email, new_password);
                        JOptionPane.showMessageDialog(null,"Password Updated.");
                        setVisible(false);
                        new Sign_in().setVisible(true);
                    }
                }
            }
        });
        buttonLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 1){
                    setVisible(false);
                    new Sign_in().setVisible(true);
                }
            }
        });
    }
    private void hideSomeComponent(){
        panelFoundAcc.setVisible(false);
    }
    private boolean displayFoundData(int id, String email) {
        Object[][] data = null;
        String[] columnNames = {"id", "Fullname", "Age", "Gender", "Email"};
        DefaultTableModel displayModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable row editing for all cells
            }
        };
        tableFoundAcc.getTableHeader().setResizingAllowed(false);
        tableFoundAcc.getTableHeader().setReorderingAllowed(false);
        tableFoundAcc.setModel(displayModel);
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String query = "SELECT * FROM public.ams_acc WHERE id = ? AND email = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setString(2, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                String list[] = {String.valueOf(rs.getInt("id")), (rs.getString("lastname")) +", "+ (rs.getString("firstname")), String.valueOf(rs.getInt("age")), (rs.getString("gender")), (rs.getString("email"))};
                DefaultTableModel tblModel = (DefaultTableModel) tableFoundAcc.getModel();
                tblModel.addRow(list);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    private void updateMyPassword(int id, String email, String new_password){
        try (Connection conn = AMS_DB_connector.getConnection()) {
            PreparedStatement locate = conn.prepareStatement("UPDATE public.ams_acc SET password = ? WHERE id = ? AND email = ?");
            locate.setString(1, new_password);
            locate.setInt(2, id);
            locate.setString(3, email);
            locate.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ForgotPassword().setVisible(true);;
            }
        });
    }
}
