import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    // Backend Objects
    private Player player;
    private GameEvent currentEvent;

    // UI Components
    private JLabel lblTurn, lblMoney, lblGrades;
    private JProgressBar barAssignments, barSleep, barStress;
    private JTextArea txtEventDescription;
    private JButton btnChoiceA, btnChoiceB;

    public GameFrame() {
        // 1. Setup Main Window
        setTitle("Mapua Sim: The Quadsem Survival Game");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null); 

        player = new Player();

        // 2. Top Panel (Status & Turn)
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        lblTurn = new JLabel("Week: 1 / 10");
        lblTurn.setFont(new Font("Arial", Font.BOLD, 16));
        lblMoney = new JLabel("💰 Baon: ₱" + player.money);
        lblGrades = new JLabel("🎓 Academic Points: " + player.academicPoints);
        
        northPanel.add(lblTurn);
        northPanel.add(lblMoney);
        northPanel.add(lblGrades);
        add(northPanel, BorderLayout.NORTH);

        // 3. Left Panel (Hazard Stats)
        JPanel westPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        westPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        westPanel.add(new JLabel("Assignments (Max 15):"));
        barAssignments = new JProgressBar(0, 15);
        barAssignments.setStringPainted(true);
        westPanel.add(barAssignments);

        westPanel.add(new JLabel("Sleep Debt (Max 30):"));
        barSleep = new JProgressBar(0, 30);
        barSleep.setStringPainted(true);
        westPanel.add(barSleep);

        westPanel.add(new JLabel("Stress (Max 20):"));
        barStress = new JProgressBar(0, 20);
        barStress.setStringPainted(true);
        westPanel.add(barStress);
        
        add(westPanel, BorderLayout.WEST);

        // 4. Center Panel (Narrative Text)
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        txtEventDescription = new JTextArea("Loading event...");
        txtEventDescription.setFont(new Font("Arial", Font.PLAIN, 16));
        txtEventDescription.setLineWrap(true);
        txtEventDescription.setWrapStyleWord(true);
        txtEventDescription.setEditable(false);
        txtEventDescription.setBackground(new Color(240, 240, 240)); 
        
        centerPanel.add(txtEventDescription, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // 5. Bottom Panel (Choices)
        JPanel southPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        southPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        btnChoiceA = new JButton("Choice A");
        btnChoiceB = new JButton("Choice B");
        
        // Button Listeners
        btnChoiceA.addActionListener(e -> resolveChoice(true));
        btnChoiceB.addActionListener(e -> resolveChoice(false));
        
        southPanel.add(btnChoiceA);
        southPanel.add(btnChoiceB);
        add(southPanel, BorderLayout.SOUTH);

        // 6. Start the Game!
        nextTurn();
    }

    // --- GAME LOGIC ---

    private void nextTurn() {
        updateUIStats();

        // Check fail state
        if (player.isFailing()) {
            triggerGameOver("You failed to maintain your hazard stats. Burnout reached critical levels. Game Over.");
            return;
        }

        // Check win state
        if (player.turn > 10) {
            triggerEnding();
            return;
        } 
        
        // Fetch event from database
        if (player.turn == 10) {
            currentEvent = DBConnection.getEventById(999); // Hell Week
        } else {
            currentEvent = DBConnection.getRandomEvent(player.stress);
        }

        // Failsafe
        if (currentEvent == null) {
            txtEventDescription.setText("DATABASE ERROR: Could not load event.");
            btnChoiceA.setEnabled(false);
            btnChoiceB.setEnabled(false);
            return;
        }

        // Update Screen
        lblTurn.setText("Week: " + player.turn + " / 10");
        txtEventDescription.setText("EVENT: " + currentEvent.name + "\n\n" + currentEvent.description);
        
        setupButton(btnChoiceA, currentEvent.choiceAText, currentEvent.costA);
        setupButton(btnChoiceB, currentEvent.choiceBText, currentEvent.costB);
    }

    private void setupButton(JButton btn, String text, int cost) {
        if (player.money >= cost) {
            btn.setEnabled(true);
            btn.setText(cost > 0 ? text + " (₱" + cost + ")" : text);
        } else {
            btn.setEnabled(false);
            btn.setText(text + " (Need ₱" + cost + ")");
        }
    }

    private void resolveChoice(boolean isChoiceA) {
        if (isChoiceA) {
            player.money += (currentEvent.moneyA - currentEvent.costA);
            player.stress += currentEvent.stressA;
            player.sleepDebt += currentEvent.sleepA;
            player.assignments += currentEvent.assignA;
            player.academicPoints += currentEvent.gradeA;
        } else {
            player.money += (currentEvent.moneyB - currentEvent.costB);
            player.stress += currentEvent.stressB;
            player.sleepDebt += currentEvent.sleepB;
            player.assignments += currentEvent.assignB;
            player.academicPoints += currentEvent.gradeB;
        }

        // Prevent negative hazards
        if (player.stress < 0) player.stress = 0;
        if (player.sleepDebt < 0) player.sleepDebt = 0;
        if (player.assignments < 0) player.assignments = 0;

        player.turn++;
        nextTurn();
    }

    private void updateUIStats() {
        lblMoney.setText("💰 Baon: ₱" + player.money);
        lblGrades.setText("🎓 Academic Points: " + player.academicPoints);
        
        barAssignments.setValue(player.assignments);
        barSleep.setValue(player.sleepDebt);
        barStress.setValue(player.stress);
    }

    private void triggerGameOver(String message) {
        txtEventDescription.setText(message);
        btnChoiceA.setVisible(false);
        btnChoiceB.setVisible(false);
    }

    private void triggerEnding() {
        String finalGrade;
        if (player.academicPoints >= 90) finalGrade = "President's Lister! (1.00 - 1.25)";
        else if (player.academicPoints >= 75) finalGrade = "Dean's Lister! (1.50 - 1.75)";
        else if (player.academicPoints >= 40) finalGrade = "Passed. (2.00 - 3.00)";
        else finalGrade = "Failed / Singko (5.00)";

        txtEventDescription.setText("SEMESTER COMPLETE!\n\nYou survived the 10-week quadsem.\nFinal Evaluation: " + finalGrade);
        btnChoiceA.setVisible(false);
        btnChoiceB.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GameFrame().setVisible(true);
        });
    }
}