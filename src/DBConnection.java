import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:8889/mapua_sim_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static GameEvent getRandomEvent(int currentStress) {
        // Exclude 999 (Hell Week) and 904 (Summative Exam) from the random pool
        String query = "SELECT * FROM events_pool WHERE req_stress_limit >= ? AND event_id NOT IN (999, 904) ORDER BY RAND() LIMIT 1";
        return fetchEvent(query, currentStress);
    }

    public static GameEvent getEventById(int id) {
        String query = "SELECT * FROM events_pool WHERE event_id = ?";
        return fetchEvent(query, id);
    }

    public static void saveGame(Player p) {
        String query = "REPLACE INTO player_saves (id, week, turn_in_week, money, stress, sleep_debt, assignments, academic_points) VALUES (1, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, p.week);
            pstmt.setInt(2, p.turnInWeek);
            pstmt.setInt(3, p.money);
            pstmt.setInt(4, p.stress);
            pstmt.setInt(5, p.sleepDebt);
            pstmt.setInt(6, p.assignments);
            pstmt.setInt(7, p.academicPoints);
            pstmt.executeUpdate();
            System.out.println("Game saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Player loadGame() {
        String query = "SELECT * FROM player_saves WHERE id = 1";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                Player p = new Player();
                p.week = rs.getInt("week");
                p.turnInWeek = rs.getInt("turn_in_week");
                p.money = rs.getInt("money");
                p.stress = rs.getInt("stress");
                p.sleepDebt = rs.getInt("sleep_debt");
                p.assignments = rs.getInt("assignments");
                p.academicPoints = rs.getInt("academic_points");
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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