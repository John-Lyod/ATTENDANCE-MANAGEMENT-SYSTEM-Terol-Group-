package aTerolGroup_OOP_FinalProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AMS_DB_connector {
    private static final String DB_URL = "jdbc:postgresql://localhost:5433/attendance-db";
    private static final String USER = "postgres";
    private static final String PASS = "zhask.04";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
