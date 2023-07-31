package aTerolGroup_OOP_FinalProject;
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
import javax.swing.*;
import java.sql.*;
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
public class RoomHandler {
    //use on when creating a room
    public static void createRoom(String room_name, int host_id) throws SQLException {
        Connection conn = AMS_DB_connector.getConnection();
        String sql = "CREATE TABLE rooms." + room_name + "_" + host_id + "\n" +
                "                (\n" +
                "                    people_name character varying(255) COLLATE pg_catalog.default NOT NULL,\n" +
                "                    people_email character varying(255) COLLATE pg_catalog.default NOT NULL,\n" +
                "                    ams_acc_id integer NOT NULL,\n" +
                "                    mark_as character varying(10) COLLATE pg_catalog.default NOT NULL, \n" +
                "                    date character varying(10) COLLATE pg_catalog.default NOT NULL \n" +
                "                );\n" +
                "                ALTER TABLE rooms." + room_name + "_" + host_id + "\n" +
                "                    OWNER to postgres;";
        Statement createStmt = conn.createStatement();
        createStmt.executeUpdate(sql);
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //record the created room
    public static void recordRoom(String room_name, String room_key, String host_name, int host_id) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String insertQuery = "INSERT INTO public.room_list(room_name, room_password, host_name, host_id, status) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, room_name.toLowerCase());
            insertStmt.setString(2, room_key);
            insertStmt.setString(3, host_name);
            insertStmt.setInt(4, host_id);
            insertStmt.setString(5, "Open");
            insertStmt.executeUpdate();
            createRoom(room_name, host_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //record the recent join room name and its host
    public static void recordJoinRoom(int room_id, String room_name, int host_id, int member_id) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String insertQuery = "INSERT INTO public.room_join_log(room_id, room_name, host_id, member_id) VALUES (?, ?, ?, ?);";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setInt(1, room_id);
            insertStmt.setString(2, room_name);
            insertStmt.setInt(3, host_id);
            insertStmt.setInt(4, member_id);
            insertStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //check if room was exist from room list where room name and host id condition
    public static boolean isRoomExist(String room_name, int host_id) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String query = "SELECT * FROM public.room_list WHERE room_name = ? AND host_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, room_name.toLowerCase());
            pstmt.setInt(2, host_id);
            ResultSet rs = pstmt.executeQuery();
            return (rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //check if room was exist from room list where room id condition
    public static boolean isSearchRoomExist(int room_id_input) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String sql = "SELECT * FROM public.room_list WHERE room_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, room_id_input);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
        return false;
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //check if it was the host who enter the selected room
    public static boolean isSearchRoomHost(int room_id_input, int host_id2) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String sql = "SELECT * FROM public.room_list WHERE room_id = ? AND host_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, room_id_input);
            stmt.setInt(2, host_id2);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
        return false;
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //to delete the room where the room name and room id is located and selected
    public static boolean deleteRoom(String room_name, int room_id, int host_id2) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String sql = "DROP TABLE rooms." + room_name + "_" + host_id2 + ";\n" +
                    " DELETE FROM public.room_list WHERE room_id = " + room_id + ";\n"+
            " DELETE FROM public.room_join_log WHERE room_id = " + room_id + ";";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
        return true;
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //delete one's own room
    public static void deleteMyRooms(int host_id) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            PreparedStatement check = conn.prepareStatement("SELECT * FROM public.room_list WHERE host_id = ?");
            check.setInt(1,host_id);
            ResultSet rs = check.executeQuery();
            if(rs.next()){
                int room_id = rs.getInt("room_id");
                String room_name = rs.getString("room_name");
                String sql = "DROP TABLE rooms." + room_name + "_" + host_id + ";\n" +
                        " DELETE FROM public.room_list WHERE room_id = " + room_id + ";\n"+
                        " DELETE FROM public.room_join_log WHERE room_id = " + room_id + ";\n"+
                        " DELETE FROM public.room_join_log WHERE member_id = " + host_id + ";";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
                while(rs.next()){
                    int room_ids = rs.getInt("room_id");
                    String room_names = rs.getString("room_name");
                    String sqls = "DROP TABLE rooms." + room_names + "_" + host_id + ";\n" +
                            " DELETE FROM public.room_list WHERE room_id = " + room_ids + ";\n"+
                            " DELETE FROM public.room_join_log WHERE room_id = " + room_ids + ";\n"+
                            " DELETE FROM public.room_join_log WHERE member_id = " + host_id + ";";
                    PreparedStatement stmts = conn.prepareStatement(sqls);
                    stmts.executeUpdate();

                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //set the updated data when entering the room
    public static void isMyDataUpdate(String room_name, int host_id, String people_name, String people_email, int ams_acc_id) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            // Check if data is already exists to be updating
            String checkQuery = "SELECT * FROM rooms." + room_name + "_" + host_id + " WHERE ams_acc_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, ams_acc_id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE rooms." + room_name + "_" + host_id + " SET people_name=?, people_email=? \n" +
                        " WHERE ams_acc_id=?");
                updateStmt.setString(1, people_name);
                updateStmt.setString(2, people_email);
                updateStmt.setInt(3, ams_acc_id);
                updateStmt.executeUpdate();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //check if the room key is correct before entering or joining the room
    public static boolean isRoomKeyCorrect(int room_id, String room_name, String room_password, int my_id) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String sql = "SELECT * FROM public.room_list WHERE room_id = ? AND room_name = ? AND room_password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, room_id);
            stmt.setString(2, room_name);
            stmt.setString(3, room_password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String sql2 = "SELECT * FROM public.ams_acc WHERE id = " + my_id;
                PreparedStatement stmt2 = conn.prepareStatement(sql2);
                ResultSet rs2 = stmt2.executeQuery();
                if (rs2.next()) {
                    String my_name = rs2.getString("lastname") + ", " + rs2.getString("firstname");
                    String my_email = rs2.getString("email");
                    int host_id = rs.getInt("host_id");
                    enrollRoom(room_name, my_name, my_email, my_id, host_id);
                    recordJoinRoom(room_id, room_name, host_id, my_id);

                }
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "You entered a wrong key.\nContact the host for a key request.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
        return false;
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //enroll to a selected room
    private static void enrollRoom(String room_name, String my_name, String my_email, int my_id, int host_id) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String date = "_";
            String mark_as = "_";
            String insertQuery = "INSERT INTO rooms." + room_name + "_" + host_id + "(people_name, people_email, ams_acc_id, date, mark_as) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, my_name);
            insertStmt.setString(2, my_email);
            insertStmt.setInt(3, my_id);
            insertStmt.setString(4, date);
            insertStmt.setString(5, mark_as);
            insertStmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Joined Successfully\nWelcome to " + room_name);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //marking attendance status
    public static void markPeopleAttendance(String room_name, int host_id, int member_id, String attendance_status, String dateSelected){
        String roomName = room_name+"_"+host_id;
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String sql = "UPDATE rooms." + roomName + " SET mark_as = ? WHERE ams_acc_id = ? AND date = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,attendance_status);
            stmt.setInt(2,member_id);
            stmt.setString(3, dateSelected);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //self marking attendance status
    public static void markMeAttendance(String room_name, int host_id, int member_id, String attendance_status, String dateSelected){
        String roomName = room_name+"_"+host_id;
        try (Connection conn = AMS_DB_connector.getConnection()) {
            PreparedStatement check = conn.prepareStatement("SELECT * FROM rooms."+roomName+" WHERE ams_acc_id = ? AND date = ?");
            check.setInt(1,member_id);
            check.setString(2, dateSelected);
            ResultSet rs = check.executeQuery();
            if(rs.next()){
                String attendance_status_check = rs.getString("mark_as");
                if(attendance_status_check.equals("_")) {
                    String sql = "UPDATE rooms." + roomName + " SET mark_as = ? WHERE ams_acc_id = ? AND date = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, attendance_status);
                    stmt.setInt(2, member_id);
                    stmt.setString(3, dateSelected);
                    stmt.executeUpdate();
                }else{
                    JOptionPane.showMessageDialog(null,"Your attendance status is already record!");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //to update the room status
    public static void updateMyRoomStatus(int room_id, String room_status) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            PreparedStatement updateRoomStatus = conn.prepareStatement("UPDATE public.room_list SET status = ? WHERE room_id = ?");
            updateRoomStatus.setString(1, room_status);
            updateRoomStatus.setInt(2, room_id);
            updateRoomStatus.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean removePersonInMyRoom(String room_name, int host_id, int member_id) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            String room = room_name+"_"+host_id;
            String sql = " DELETE FROM public.room_join_log WHERE member_id = " + member_id + ";"+
                    " DELETE FROM rooms."+room+" WHERE ams_acc_id = " + member_id + ";";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
        return true;
    }

    public static boolean removeMeInThatRoom(String room_name, int room_id, int my_id) {
        try (Connection conn = AMS_DB_connector.getConnection()) {
            PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM public.room_list WHERE room_id = ?");
            stmt2.setInt(1,room_id);
            ResultSet rs = stmt2.executeQuery();
            if(rs.next()){
                int host_id = rs.getInt("host_id");
                String room = room_name+"_"+host_id;
                String sql = " DELETE FROM public.room_join_log WHERE member_id = " + my_id + ";"+
                        " DELETE FROM rooms."+room+" WHERE ams_acc_id = " + my_id + ";";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
                while(rs.next()){
                    String sql2 = " DELETE FROM public.room_join_log WHERE member_id = " + my_id + ";"+
                            " DELETE FROM rooms."+room+" ams_acc_id = " + my_id + ";";
                    PreparedStatement stmt3 = conn.prepareStatement(sql2);
                    stmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
        return true;
    }
}
