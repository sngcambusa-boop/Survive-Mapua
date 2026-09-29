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
        this(new Player());
    }

    public GameFrame(Player startingPlayer) {
        // 1. Setup Main Window
        setTitle("Mapua Sim: The Quadsem Survival Game");
        setSize(900, 560);
        setMinimumSize(new Dimension(850, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(138, 21, 56));

        player = startingPlayer;

        // 2. Top Panel (Status & Turn)
        JPanel northPanel = new JPanel(new BorderLayout(15, 0));
        northPanel.setBackground(new Color(138, 21, 56));
        northPanel.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        JLabel titleLabel = new JLabel("MAPÚA SIM");
        titleLabel.setForeground(new Color(255, 204, 0));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        northPanel.add(titleLabel, BorderLayout.WEST);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 18, 0));
        statusPanel.setOpaque(false);

        lblTurn = new JLabel("Week: 1 / 10");
        lblTurn.setForeground(Color.WHITE);
        lblTurn.setFont(new Font("Arial", Font.BOLD, 15));

        lblMoney = new JLabel("💰 Baon: ₱" + player.money);
        lblMoney.setForeground(new Color(255, 220, 110));
        lblMoney.setFont(new Font("Arial", Font.BOLD, 15));

        lblGrades = new JLabel("🎓 Academic Points: " + player.academicPoints);
        lblGrades.setForeground(Color.WHITE);
        lblGrades.setFont(new Font("Arial", Font.BOLD, 15));

        statusPanel.add(lblTurn);
        statusPanel.add(lblMoney);
        statusPanel.add(lblGrades);

        JButton btnSave = new JButton("Save");
        btnSave.setFocusPainted(false);
        btnSave.setForeground(new Color(138, 21, 56));
        btnSave.setBackground(Color.WHITE);
        btnSave.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 204, 0), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        btnSave.addActionListener(e -> {
            if (DBConnection.saveGame(player)) {
                JOptionPane.showMessageDialog(this, "Game saved successfully.", "Save Game", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Could not save the game. Check the database connection and save table.", "Save Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        statusPanel.add(btnSave);
        northPanel.add(statusPanel, BorderLayout.EAST);
        add(northPanel, BorderLayout.NORTH);

        // 3. Left Panel (Hazard Stats)
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setBackground(Color.WHITE);
        westPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 204, 0), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        westPanel.setPreferredSize(new Dimension(240, 0));

        JLabel hazardTitle = new JLabel("Hazard Stats");
        hazardTitle.setFont(new Font("Arial", Font.BOLD, 16));
        hazardTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        hazardTitle.setForeground(new Color(138, 21, 56));
        westPanel.add(hazardTitle);
        westPanel.add(Box.createVerticalStrut(12));

        addStatPanel(westPanel, "Assignments (Max " + Player.ASSIGNMENTS_LIMIT + ")", Player.ASSIGNMENTS_LIMIT, true);
        addStatPanel(westPanel, "Sleep Debt (Max " + Player.SLEEP_DEBT_LIMIT + ")", Player.SLEEP_DEBT_LIMIT, false);
        addStatPanel(westPanel, "Stress (Max " + Player.STRESS_LIMIT + ")", Player.STRESS_LIMIT, true);

        add(westPanel, BorderLayout.WEST);

        // 4. Center Panel (Narrative Text)
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 204, 0), 2),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        txtEventDescription = new JTextArea("Loading event...");
        txtEventDescription.setFont(new Font("Arial", Font.PLAIN, 16));
        txtEventDescription.setLineWrap(true);
        txtEventDescription.setWrapStyleWord(true);
        txtEventDescription.setEditable(false);
        txtEventDescription.setBackground(new Color(255, 255, 255));
        txtEventDescription.setForeground(new Color(33, 33, 33));
        txtEventDescription.setMargin(new Insets(8, 8, 8, 8));

        centerPanel.add(txtEventDescription, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // 5. Bottom Panel (Choices)
        JPanel southPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        southPanel.setBackground(new Color(138, 21, 56));
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        btnChoiceA = new JButton("Choice A");
        btnChoiceB = new JButton("Choice B");

        styleButton(btnChoiceA);
        styleButton(btnChoiceB);

        btnChoiceA.addActionListener(e -> resolveChoice(true));
        btnChoiceB.addActionListener(e -> resolveChoice(false));

        southPanel.add(btnChoiceA);
        southPanel.add(btnChoiceB);
        add(southPanel, BorderLayout.SOUTH);

        // 6. Start the Game!
        nextTurn();
    }

    private void addStatPanel(JPanel panel, String labelText, int maxValue, boolean isPrimary) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setForeground(new Color(44, 62, 80));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(4));

        JProgressBar bar = new JProgressBar(0, maxValue);
        bar.setStringPainted(true);
        bar.setFont(new Font("Arial", Font.BOLD, 11));
        bar.setBackground(new Color(239, 232, 233));
        bar.setForeground(isPrimary ? new Color(138, 21, 56) : new Color(218, 166, 0));
        bar.setBorder(BorderFactory.createLineBorder(new Color(216, 203, 205)));
        bar.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (labelText.startsWith("Assignments")) {
            barAssignments = bar;
        } else if (labelText.startsWith("Sleep")) {
            barSleep = bar;
        } else {
            barStress = bar;
        }

        panel.add(bar);
        panel.add(Box.createVerticalStrut(12));
    }

    private void styleButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 17));
        btn.setForeground(new Color(138, 21, 56));
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 204, 0), 2),
                BorderFactory.createEmptyBorder(12, 18, 12, 18)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
    }

    // --- GAME LOGIC ---

    private void nextTurn() {
        updateUIStats();

        // Check fail state
        if (player.isFailing()) {
            triggerGameOver("You failed to maintain your hazard stats. Burnout reached critical levels. Game Over.");
            return;
        }

        // Check win state (survived past week 10)
        if (player.week > 10) {
            triggerEnding();
            return;
        }

        // --- NEW EVENT SCHEDULER ---
        if (player.week == 10 && player.turnInWeek == 3) {
            // Very last turn of the game: Hell Week Finals
            currentEvent = DBConnection.getEventById(999);
        }
        else if ((player.week == 4 || player.week == 8) && player.turnInWeek == 3) {
            // Week 4 and Week 8, Turn 3: Summative Exams!
            currentEvent = DBConnection.getEventById(904); // ID 904 will be your Exam Event
        }
        else {
            // Standard turns: Random Event filtered by week
            currentEvent = DBConnection.getRandomEvent(player.stress, player.week);
        }

        if (currentEvent == null) {
            txtEventDescription.setText("DATABASE ERROR: Could not load event.");
            btnChoiceA.setEnabled(false);
            btnChoiceB.setEnabled(false);
            return;
        }

        lblTurn.setText("Week: " + player.week + " / 10 (Turn: " + player.turnInWeek + "/3)");
        txtEventDescription.setText("EVENT: " + currentEvent.name + "\n\n" + currentEvent.description);

        setupButton(btnChoiceA, currentEvent.choiceAText, currentEvent.costA);

        if (currentEvent.choiceBText == null || currentEvent.choiceBText.trim().isEmpty() || currentEvent.choiceBText.equalsIgnoreCase("none")) {
            btnChoiceB.setVisible(false); // Hide the second button
        } else {
            btnChoiceB.setVisible(true);  // Show it for normal events
            setupButton(btnChoiceB, currentEvent.choiceBText, currentEvent.costB);
        }
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

        // NEW TIME LOGIC: 3 Turns per week
        player.turnInWeek++;
        if (player.turnInWeek > 3) {
            player.turnInWeek = 1;
            player.week++;
        }

        nextTurn();
    }

    private void updateUIStats() {
        lblMoney.setText(" Baon: ₱" + player.money);
        lblGrades.setText(" Academic Points: " + player.academicPoints);
        
        barAssignments.setValue(player.assignments);
        barSleep.setValue(player.sleepDebt);
        barStress.setValue(player.stress);
    }

    private void triggerGameOver(String message) {
        txtEventDescription.setText(message);
        btnChoiceB.setVisible(false);

        for (java.awt.event.ActionListener al : btnChoiceA.getActionListeners()) {
            btnChoiceA.removeActionListener(al);
        }

        btnChoiceA.setText("Return to Main Menu");
        btnChoiceA.setVisible(true);
        btnChoiceA.setEnabled(true);
        btnChoiceA.addActionListener(e -> {
            new MainMenu().setVisible(true);
            this.dispose();
        });
    }

    private void triggerEnding() {
        String finalGrade;
        if (player.academicPoints >= 90) finalGrade = "President's Lister! (1.00 - 1.25)";
        else if (player.academicPoints >= 75) finalGrade = "Dean's Lister! (1.50 - 1.75)";
        else if (player.academicPoints >= 40) finalGrade = "Passed. (2.00 - 3.00)";
        else finalGrade = "Failed / Singko (5.00)";

        txtEventDescription.setText("SEMESTER COMPLETE!\n\nYou survived the 10-week quadsem.\nFinal Evaluation: " + finalGrade);
        btnChoiceB.setVisible(false);

        for (java.awt.event.ActionListener al : btnChoiceA.getActionListeners()) {
            btnChoiceA.removeActionListener(al);
        }

        btnChoiceA.setText("Return to Main Menu");
        btnChoiceA.setVisible(true);
        btnChoiceA.setEnabled(true);
        btnChoiceA.addActionListener(e -> {
            new MainMenu().setVisible(true);
            this.dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GameFrame().setVisible(true);
        });
    }
}