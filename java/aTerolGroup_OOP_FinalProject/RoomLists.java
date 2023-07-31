package aTerolGroup_OOP_FinalProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomLists extends JFrame {
    private JLabel roomTitleLabel;
    private JScrollPane roomListScrollPane;
    private JTable displayRoomTable;
    private JPanel mainPanel;

    RoomLists(){
        setTitle("Room List");
        getContentPane().add(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        roomList();
        setVisible(true);
    }
    void roomList(){
        Object[][] data=null;
        String[] columnNames= {"Room", "Host Id", "Password"};
        DefaultTableModel displayModel = new DefaultTableModel(data,columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable row editing for all cells
            }
        };
        displayRoomTable.getTableHeader().setResizingAllowed(false);
        displayRoomTable.getTableHeader().setReorderingAllowed(false);
        displayRoomTable.setModel(displayModel);
        System.out.println("#Rooms List\nRoom_name | Room_id | Room_password");
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String sql = "SELECT * FROM public.room_list ORDER BY room_id ASC ";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String room = rs.getString("room_name");
                    String id = String.valueOf(rs.getInt("room_id"));
                    String password = rs.getString("room_password");
                    String host = rs.getString("host_id");
                    String list[] = {room,id,password};
                    DefaultTableModel tblModel = (DefaultTableModel)displayRoomTable.getModel();
                    tblModel.addRow(list);
                    System.out.println(room +" - "+ id +" - "+ password +" - "+ host);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
    }
    public void myRoomList(String room_name, int room_id, int host_id){
        Object[][] data=null;
        String[] columnNames= {"Room","Password", "Host Id"};
        DefaultTableModel displayModel = new DefaultTableModel(data,columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable row editing for all cells
            }
        };
        displayRoomTable.getTableHeader().setResizingAllowed(false);
        displayRoomTable.getTableHeader().setReorderingAllowed(false);
        displayRoomTable.setModel(displayModel);
        System.out.println("#Rooms List\nRoom_name | Room_id | Room_password");
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String sql = "SELECT * FROM rooms."+room_name+"_"+host_id+" WHERE room_id = ? ";
            PreparedStatement stmt = conn.prepareStatement(sql);
             stmt.setInt(1, room_id);
             ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String room = rs.getString("room_name");
                String id = String.valueOf(rs.getInt("room_id"));
                String password = rs.getString("room_password");
                String host = rs.getString("host_id");
                String list[] = {room,id,password};
                DefaultTableModel tblModel = (DefaultTableModel)displayRoomTable.getModel();
                tblModel.addRow(list);
                System.out.println(room +" - "+ id +" - "+ password +" - "+ host);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RoomLists();
            }
        });
    }
}
