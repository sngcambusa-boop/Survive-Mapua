public class Player {
    public static final int ASSIGNMENTS_LIMIT = 20;
    public static final int SLEEP_DEBT_LIMIT = 40;
    public static final int STRESS_LIMIT = 30;

    public int week = 1;
    public int turnInWeek = 1; 
    
    public int assignments = 0;
    public int sleepDebt = 0;
    public int stress = 0;
    
    // NERF 1 & 2: Better Starting Stats
    public int money = 3500;           // Increased from 2000
    public int academicPoints = 70;    // Increased from 50 (gives a nice buffer before failing)

    public boolean isFailing() {
        return assignments >= ASSIGNMENTS_LIMIT
                || sleepDebt >= SLEEP_DEBT_LIMIT
                || stress >= STRESS_LIMIT;
    }
}