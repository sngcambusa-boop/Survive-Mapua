import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:8889/mapua_sim_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static GameEvent getRandomEvent(int currentStress) {
        String query = "SELECT * FROM events_pool WHERE req_stress_limit >= ? AND event_id != 999 ORDER BY RAND() LIMIT 1";
        return fetchEvent(query, currentStress);
    }

    public static GameEvent getEventById(int id) {
        String query = "SELECT * FROM events_pool WHERE event_id = ?";
        return fetchEvent(query, id);
    }

    private static GameEvent fetchEvent(String query, int parameter) {
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, parameter);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                GameEvent event = new GameEvent();
                event.id = rs.getInt("event_id");
                event.name = rs.getString("event_name");
                event.description = rs.getString("event_description");
                
                event.choiceAText = rs.getString("choice_A_text");
                event.costA = rs.getInt("choice_A_money_cost");
                event.stressA = rs.getInt("choice_A_stress_mod");
                event.sleepA = rs.getInt("choice_A_sleep_mod");
                event.assignA = rs.getInt("choice_A_assign_mod");
                event.gradeA = rs.getInt("choice_A_grade_mod");
                event.moneyA = rs.getInt("choice_A_money_mod");
                
                event.choiceBText = rs.getString("choice_B_text");
                event.costB = rs.getInt("choice_B_money_cost");
                event.stressB = rs.getInt("choice_B_stress_mod");
                event.sleepB = rs.getInt("choice_B_sleep_mod");
                event.assignB = rs.getInt("choice_B_assign_mod");
                event.gradeB = rs.getInt("choice_B_grade_mod");
                event.moneyB = rs.getInt("choice_B_money_mod");
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}