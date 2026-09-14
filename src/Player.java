public class Player {
    public int turn = 1;
    public int assignments = 0;
    public int sleepDebt = 0;
    public int stress = 0;
    public int money = 2000;
    public int academicPoints = 50;

    public boolean isFailing() {
        return assignments >= 15 || sleepDebt >= 30 || stress >= 20;
    }
}
