import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestConnection {
    
    private static final String URL = "jdbc:mysql://localhost:8889/mapua_sim_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; 

    public static void main(String[] args) {
        System.out.println("Attempting to connect to MAMP...");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("SUCCESS: Connected to Mapua Sim Database!");
            
            // Test fetching one random event to prove data flows
            String query = "SELECT event_name FROM events_pool ORDER BY RAND() LIMIT 1";
            try (PreparedStatement pstmt = conn.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                
                if (rs.next()) {
                    System.out.println("Data Pull Test: Random Event Found -> " + rs.getString("event_name"));
                } else {
                    System.out.println("Data Pull Test FAILED: The events_pool table is empty.");
                }
            }
            
        } catch (SQLException e) {
            System.out.println("FAILED: Could not connect to the database.");
            System.out.println("Check if MAMP is running, and double-check your MySQL Port in MAMP settings.");
            e.printStackTrace();
        }
    }
}