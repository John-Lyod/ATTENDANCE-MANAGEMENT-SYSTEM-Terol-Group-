package aTerolGroup_OOP_FinalProject;
import javax.swing.*;
import java.sql.*;

public class AccountsDataHandler {
//    public static ArrayList<None> getAllData(){
//        ArrayList<None> userList = new ArrayList<>();
//        try (Connection conn = AMS_DB_connector.getConnection()) {
//            String sql = "SELECT * FROM public.ams_acc ORDER BY id ASC";
//            try (PreparedStatement stmt = conn.prepareStatement(sql);
//                 ResultSet rs = stmt.executeQuery()) {
//                None user;
//                while (rs.next()) {
//                    user = new None(rs.getInt("id"),rs.getString("firstname"),rs.getString("lastname"),rs.getInt("age"),rs.getString("gender"),rs.getString("email"),rs.getString("password"),rs.getString("role"));
//                    userList.add(user);
//                }
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
//        }
//        return userList;
//    }

    public static boolean loginCheck(String email, String pass) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String query = "SELECT * FROM ams_acc WHERE email = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, pass);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int columnID = rs.getInt("id");
                String columnFname = rs.getString("firstname");
                String columnLname = rs.getString("lastname");
                int columnAge = rs.getInt("age");
                String columnGender = rs.getString("gender");
                String columnEmail = rs.getString("email");
                String columnPass = rs.getString("password");
                String columnRole = rs.getString("role");
                System.out.println(columnLname + ", " + columnFname +" ("+ columnRole + ")" + " is login...");
                System.out.println("Login Success!");
                JOptionPane.showMessageDialog(null, "Welcome " + columnRole,"Login success",JOptionPane.INFORMATION_MESSAGE);
                // None exists with the provided username and password
                if(columnRole.equals("admin")){
                    AMS_AdminInterface2 admin = new AMS_AdminInterface2();
                    admin.adminNameLabel.setText(columnLname + ", "+ columnFname);
                    admin.adminHostIDLabel.setText(String.valueOf(columnID));
                    admin.adminFirstnameField.setText(columnFname);
                    admin.adminLastnameField.setText(columnLname);
                    admin.adminAgeField.setText(String.valueOf(columnAge));
                    admin.adminSexSelection.setSelectedItem(columnGender);
                    admin.adminEmailField.setText(columnEmail);
                    admin.adminPasswordField.setText(columnPass);
                    admin.adminRoomList(columnID);
                    admin.recentJoinedRoom(columnID);
                    admin.setVisible(true);
                }else if(columnRole.equals("user")){
                    AMS_UserInterface user = new AMS_UserInterface();
                    user.userNameLabel.setText(columnLname + ", "+ columnFname);
                    user.userHostIDLabel.setText(String.valueOf(columnID));
                    user.userFirstnameField.setText(columnFname);
                    user.userLastnameField.setText(columnLname);
                    user.userAgeField.setText(String.valueOf(columnAge));
                    user.userSexSelection.setSelectedItem(columnGender);
                    user.userEmailField.setText(columnEmail);
                    user.userPasswordField.setText(columnPass);
                    user.userRoomList(columnID);
                    user.recentJoinedRoom(columnID);
                    user.setVisible(true);
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // No user found with the provided username and password
        return false;
    }

    public static boolean findAccount(String firstname, String lastname, int age, String gender, String email) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String query = "SELECT * FROM ams_acc WHERE firstname = ? AND lastname = ? AND age = ? AND email = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setInt(3, age);
            pstmt.setString(4, gender);
            pstmt.setString(5, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // No user found with the provided username and password
        return false;
    }

    public static String myFirstname(String email) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String query = "SELECT firstname FROM ams_acc WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String fname = rs.getString(1);
                return fname;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    //Create None
    public static boolean registerAccount(String firstname, String lastname, int age1, String gender, String email, String pass, String role) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            // Check if username exists
            String checkQuery = "SELECT * FROM public.ams_acc WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("It seems the email is already used.");
                return true;
            } else {
                // Insert new user
                String insertQuery = "INSERT INTO public.ams_acc(firstname, lastname, age, gender, email, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, firstname);
                insertStmt.setString(2, lastname);
                insertStmt.setInt(3, age1);
                insertStmt.setString(4, gender);
                insertStmt.setString(5, email);
                insertStmt.setString(6, pass);
                insertStmt.setString(7, role);
                insertStmt.executeUpdate();
                System.out.println("New user has been registered!");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean showData() {
        System.out.println("\n#DATA LIST\nId | firstname | lastname | age | gender | email | Password | Role");
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String sql = "SELECT * FROM public.ams_acc ORDER BY id ASC";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
// Process the results
                    String columnId = rs.getString("id");
                    String columnName = rs.getString("firstname");
                    String columnLast = rs.getString("lastname");
                    int columnAge = rs.getInt("age");
                    String columnGender = rs.getString("gender");
                    String columnEmail = rs.getString("email");
                    String columnPass = rs.getString("password");
                    String columnRole = rs.getString("role");
                    System.out.println(columnId + ". " + columnName + ", " + columnLast + ", " + columnAge + ", " + columnGender + ", " + columnEmail + ", " + columnPass + ", " + columnRole);
// Do something with the data;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " +
                    e.getMessage());
        }
        return false;
    }

    public static boolean addData(String firstname, String lastname, int age, String gender, String email, String password, String role) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            // Check if data exists, applying on adding
            String checkQuery = "SELECT * FROM public.ams_acc WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                // Insert new user
                String insertQuery = "INSERT INTO public.ams_acc(firstname, lastname, age, gender, email, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, firstname);
                insertStmt.setString(2, lastname);
                insertStmt.setInt(3, age);
                insertStmt.setString(4, gender);
                insertStmt.setString(5, email);
                insertStmt.setString(6, password);
                insertStmt.setString(7, role);
                insertStmt.executeUpdate();
//                displayAllData();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean updateData(int id, String firstname, String lastname, int age, String gender, String email, String password, String role) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            // Check if data is already exists to be updating
            String checkQuery = "SELECT * FROM public.ams_acc WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                String host_name = lastname+", "+firstname;
                // Update the current data
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE public.ams_acc SET firstname=?, lastname=?, age=?, gender=?, email=?, password=?, role=? WHERE id=?");
                updateStmt.setString(1, firstname);
                updateStmt.setString(2, lastname);
                updateStmt.setInt(3, age);
                updateStmt.setString(4, gender);
                updateStmt.setString(5, email);
                updateStmt.setString(6, password);
                updateStmt.setString(7, role);
                updateStmt.setInt(8, id);
                updateHost(host_name, id);
                updateStmt.executeUpdate();
//                displayAllData();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error: " + e.getMessage());
            return false;
        }
    }


    public static boolean ifDataMatched(int id, String firstname, String lastname, int age, String gender, String email, String password, String role) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            // Check if data is matched, used on updating/deleting the specified row,
            String checkQuery = "SELECT * FROM public.ams_acc WHERE id = ? AND firstname = ? AND lastname = ? AND age = ? AND gender = ? AND email = ? AND password = ? AND role = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, id);
            checkStmt.setString(2, firstname);
            checkStmt.setString(3, lastname);
            checkStmt.setInt(4, age);
            checkStmt.setString(5, gender);
            checkStmt.setString(6, email);
            checkStmt.setString(7, password);
            checkStmt.setString(8, role);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return true;
            }
            else{
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error: "+ ex.getMessage());
        }
        return false;
    }
    public static boolean deleteData(int id, String firstname, String lastname, int age, String gender, String email, String password, String role) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            // Check if data exists to be deleted
            String checkQuery = "SELECT * FROM public.ams_acc WHERE id = ? AND firstname = ? AND lastname = ? " +
                    "AND age = ? AND gender = ? AND email = ? AND password = ? AND role = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, id);
            checkStmt.setString(2, firstname);
            checkStmt.setString(3, lastname);
            checkStmt.setInt(4, age);
            checkStmt.setString(5, gender);
            checkStmt.setString(6, email);
            checkStmt.setString(7, password);
            checkStmt.setString(8, role);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                String deleteQuery = "DELETE FROM public.ams_acc WHERE id = ? ";
                PreparedStatement checkSt = conn.prepareStatement(deleteQuery);
                checkSt.setInt(1, id);
                checkSt.executeUpdate();
//                displayAllData();
                return true;
            }
            else{
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error: "+ ex.getMessage());
        }
        return false;
    }

    public static boolean newEmail(String nmail,String omail) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            // Check if email exists
            String checkQuery = "SELECT * FROM public.ams_acc WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, nmail);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                // Update the email only
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE public.ams_acc SET email=? WHERE email=?");
                updateStmt.setString(1, nmail);
                updateStmt.setString(2, omail);
                updateStmt.executeUpdate();
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error: " + e.getMessage());
            return false;
        }
    }
    public static boolean isEmailExist(String oldmail) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            // Check if email exists
            String checkQuery = "SELECT * FROM public.ams_acc WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, oldmail);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error: " + e.getMessage());
            return false;
        }
    }

    public static boolean ifSameDataOnAdding(String firstname, String lastname, int age, String gender, String email, String password, String role) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            // Check if data already exists on adding button
            String checkQuery = "SELECT * FROM public.ams_acc WHERE firstname = ? AND lastname = ? AND age = ? AND gender = ? AND email = ? AND password = ? AND role = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, firstname);
            checkStmt.setString(2, lastname);
            checkStmt.setInt(3, age);
            checkStmt.setString(4, gender);
            checkStmt.setString(5, email);
            checkStmt.setString(6, password);
            checkStmt.setString(7, role);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error: "+ ex.getMessage());
        }
        return false;
    }
    public static boolean checkSameEmail(int id, String firstname, String lastname, int age, String gender, String email, String password, String role) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            // Check if email exists on more options on list of accounts
            String checkQuery = "SELECT * FROM public.ams_acc WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error: "+ ex.getMessage());
        }
        return false;
    }

    public static boolean confirmEdit(String email, String pass) {
        //access editing acc by password to perform changes
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String query = "SELECT * FROM ams_acc WHERE email = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, pass);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateMyData(int id,String firstname, String lastname, int age, String gender, String email, String password) {
        System.out.println("\n#MY DATA\nId | firstname | lastname | age | gender | email | Password | Role");
        try (Connection conn = AMS_DB_connector.getConnection()) {
            // Check if data is already exists to be updating
            String checkQuery = "SELECT * FROM public.ams_acc WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                String host_name = lastname+", "+firstname;
                // Update the current data
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE public.ams_acc SET firstname=?, lastname=?, " +
                        "age=?, gender=?, email=?, password=? WHERE id=?");
                updateStmt.setString(1, firstname);
                updateStmt.setString(2, lastname);
                updateStmt.setInt(3, age);
                updateStmt.setString(4, gender);
                updateStmt.setString(5, email);
                updateStmt.setString(6, password);
                updateStmt.setInt(7, id);
                int host_id = rs.getInt("id");
                updateHost(host_name, host_id);
                updateStmt.executeUpdate();
                String columnId = rs.getString("id");
                String columnRole = rs.getString("role");
                System.out.println(columnId + ". " + firstname + ", " + lastname + ", " + age + ", " + gender + ", " + email + ", " + password + ", " + columnRole);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error: " + e.getMessage());
            return false;
        }
    }

    public static void updateHost(String host_name, int host_id) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            // Check if data is already exists to be updating
            String checkQuery = "SELECT * FROM public.room_list WHERE host_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, host_id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE public.room_list SET host_name=? \n" +
                        " WHERE host_id=?");
                updateStmt.setString(1, host_name);
                updateStmt.setInt(2, host_id);
                updateStmt.executeUpdate();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static boolean searchEmail(String mail) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String query = "SELECT firstname, lastname, age, gender, email, password FROM public.ams_acc WHERE email=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1,mail);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String firstname = rs.getString(1);
                String lastname = rs.getString(2);
                int age = rs.getInt(3);
                String gender = rs.getString(4);
                String email = rs.getString(5);
                String password = rs.getString(6);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
