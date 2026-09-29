public class Player {
    public int week = 1;
    public int turnInWeek = 1; 
    
    public int assignments = 0;
    public int sleepDebt = 0;
    public int stress = 0;
    
    // NERF 1 & 2: Better Starting Stats
    public int money = 3500;           // Increased from 2000
    public int academicPoints = 70;    // Increased from 50 (gives a nice buffer before failing)

    public boolean isFailing() {

        return assignments >= 20 || sleepDebt >= 40 || stress >= 30;
    }
}