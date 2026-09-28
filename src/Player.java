public class Player {
    // Keeps compatibility with both the original turn-based game and a week/tracking model.
    public int turn = 1;
    public int week = 1;
    public int turnInWeek = 1; // Tracks turn 1, 2, or 3 within the week

    public int assignments = 0;
    public int sleepDebt = 0;
    public int stress = 0;
    public int money = 2000;
    public int academicPoints = 50;

    public boolean isFailing() {
        return assignments >= 15 || sleepDebt >= 30 || stress >= 20;
    }
}