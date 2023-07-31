import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("\nId | Username | Phone | Password");
        try (Connection conn = DatabaseConnector.getConnection()) {
            String sql = "SELECT * FROM public.user_acc ORDER BY user_id ASC";
//            System.out.println("Database Success!");
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
// Process the results
                    String columnId = rs.getString("user_id");
                    String columnName = rs.getString("user_name");
                    String columnPhone = rs.getString("user_phone");
                    String columnPass = rs.getString("user_password");
                    System.out.println(columnId + ". " + columnName + ", " + columnPhone + ", " + columnPass);
// Do something with the data
//                    JOptionPane.showMessageDialog(null,"The data are " + columnName);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " +
                    e.getMessage());
        }
    }
}
