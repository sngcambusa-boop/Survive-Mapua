public class Player {
    public static final int ASSIGNMENTS_LIMIT = 20;
    public static final int SLEEP_DEBT_LIMIT = 40;
    public static final int STRESS_LIMIT = 30;

    public int week = 1;
    public int turnInWeek = 1; 
    
    public int assignments = 0;
    public int sleepDebt = 0;
    public int stress = 0;
    
    // NERF 1 & 2: Better Starting Stats mb for making the game hard lol
    public int money = 3500;          //Game so hard I gott make it easier lol
    public int academicPoints = 60;    

    public boolean isFailing() {
        return assignments >= ASSIGNMENTS_LIMIT
                || sleepDebt >= SLEEP_DEBT_LIMIT
                || stress >= STRESS_LIMIT;
    }
}