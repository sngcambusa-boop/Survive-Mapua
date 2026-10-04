import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    // Backend Objects
    private Player player;
    private GameEvent currentEvent;

    // UI Components
    private JLabel lblTurn, lblMoney, lblGrades;
    private JLabel lblEventName;
    private JProgressBar barAssignments, barSleep, barStress;
    private JTextArea txtEventDescription;
    private JButton btnChoiceA, btnChoiceB;
    private JPanel choicePanel;

    public GameFrame() {
        this(new Player());
    }

    public GameFrame(Player startingPlayer) {
        // 1. Setup Main Window
        setTitle("Mapua Sim: The Quadsem Survival Game");
        setSize(1000, 650);
        setMinimumSize(new Dimension(880, 560));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(138, 21, 56));

        player = startingPlayer;

        JPanel northPanel = new JPanel(new BorderLayout(15, 0));
        northPanel.setBackground(new Color(138, 21, 56));
        northPanel.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        JPanel brandPanel = new JPanel();
        brandPanel.setLayout(new BoxLayout(brandPanel, BoxLayout.Y_AXIS));
        brandPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("MAPÚA SIM");
        titleLabel.setForeground(new Color(255, 204, 0));
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 22));
        JLabel subtitleLabel = new JLabel("QUADSEM SURVIVAL");
        subtitleLabel.setForeground(new Color(245, 225, 229));
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 10));
        brandPanel.add(titleLabel);
        brandPanel.add(Box.createVerticalStrut(3));
        brandPanel.add(subtitleLabel);
        northPanel.add(brandPanel, BorderLayout.WEST);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 18, 0));
        statusPanel.setOpaque(false);

        lblTurn = new JLabel("Week: 1 / 10");
        lblTurn.setForeground(Color.WHITE);
        lblTurn.setFont(new Font("Arial", Font.BOLD, 13));

        lblMoney = new JLabel("Baon: ₱" + player.money);
        lblMoney.setForeground(new Color(255, 220, 110));
        lblMoney.setFont(new Font("Arial", Font.BOLD, 13));

        lblGrades = new JLabel("Academic Points: " + player.academicPoints);
        lblGrades.setForeground(Color.WHITE);
        lblGrades.setFont(new Font("Arial", Font.BOLD, 13));

        statusPanel.add(lblTurn);
        statusPanel.add(lblMoney);
        statusPanel.add(lblGrades);

        JButton btnPause = new JButton("Pause Menu");
        btnPause.setFocusPainted(false);
        btnPause.setForeground(new Color(138, 21, 56));
        btnPause.setBackground(Color.WHITE);
        btnPause.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 204, 0), 2),
                BorderFactory.createEmptyBorder(7, 14, 7, 14)
        ));
        btnPause.setFont(new Font("Arial", Font.BOLD, 12));
        btnPause.setPreferredSize(new Dimension(118, 34));
        btnPause.setMinimumSize(new Dimension(118, 34));
        btnPause.setOpaque(true);
        btnPause.setContentAreaFilled(true);
        btnPause.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPause.addActionListener(e -> triggerPauseMenu());
        statusPanel.add(btnPause);
        northPanel.add(statusPanel, BorderLayout.EAST);
        add(northPanel, BorderLayout.NORTH);

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

        addStatPanel(westPanel, "Assignments", Player.ASSIGNMENTS_LIMIT);
        addStatPanel(westPanel, "Sleep Debt", Player.SLEEP_DEBT_LIMIT);
        addStatPanel(westPanel, "Stress", Player.STRESS_LIMIT);

        westPanel.add(Box.createVerticalStrut(20));
        JButton btnRelief = new JButton("Take a Break (Shop)");
        btnRelief.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnRelief.setPreferredSize(new Dimension(204, 42));
        btnRelief.setMinimumSize(new Dimension(204, 42));
        btnRelief.setMaximumSize(new Dimension(204, 42));
        btnRelief.setFocusPainted(false);
        btnRelief.setForeground(Color.WHITE);
        btnRelief.setBackground(new Color(138, 21, 56));
        btnRelief.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        btnRelief.setFont(new Font("Arial", Font.BOLD, 11));
        btnRelief.setOpaque(true);
        btnRelief.setContentAreaFilled(true);
        btnRelief.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRelief.setToolTipText("Spend Baon to reduce assignments, sleep debt, or stress.");
        btnRelief.addActionListener(e -> openReliefShop());
        westPanel.add(btnRelief);

        JLabel shopDescription = new JLabel("Spend Baon to lower your hazards.");
        shopDescription.setFont(new Font("Arial", Font.PLAIN, 11));
        shopDescription.setForeground(new Color(90, 75, 75));
        shopDescription.setAlignmentX(Component.LEFT_ALIGNMENT);
        westPanel.add(Box.createVerticalStrut(6));
        westPanel.add(shopDescription);

        add(westPanel, BorderLayout.WEST);

        // 4. Center Panel (Narrative Text)
        JPanel centerPanel = new JPanel(new BorderLayout(0, 14));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 204, 0), 2),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        JPanel eventHeading = new JPanel();
        eventHeading.setLayout(new BoxLayout(eventHeading, BoxLayout.Y_AXIS));
        eventHeading.setOpaque(false);

        JLabel eventEyebrow = new JLabel("CURRENT EVENT");
        eventEyebrow.setFont(new Font("Arial", Font.BOLD, 11));
        eventEyebrow.setForeground(new Color(174, 116, 0));
        lblEventName = new JLabel("Loading event...");
        lblEventName.setFont(new Font("Georgia", Font.BOLD, 24));
        lblEventName.setForeground(new Color(138, 21, 56));
        lblEventName.setAlignmentX(Component.LEFT_ALIGNMENT);
        eventHeading.add(eventEyebrow);
        eventHeading.add(Box.createVerticalStrut(6));
        eventHeading.add(lblEventName);
        eventHeading.add(Box.createVerticalStrut(12));
        JSeparator eventRule = new JSeparator();
        eventRule.setForeground(new Color(255, 204, 0));
        eventRule.setBackground(new Color(255, 204, 0));
        eventHeading.add(eventRule);
        centerPanel.add(eventHeading, BorderLayout.NORTH);

        txtEventDescription = new JTextArea("Loading event...");
        txtEventDescription.setFont(new Font("Arial", Font.PLAIN, 16));
        txtEventDescription.setLineWrap(true);
        txtEventDescription.setWrapStyleWord(true);
        txtEventDescription.setEditable(false);
        txtEventDescription.setBackground(new Color(255, 255, 255));
        txtEventDescription.setForeground(new Color(33, 33, 33));
        txtEventDescription.setMargin(new Insets(8, 8, 8, 8));

        JScrollPane eventScroll = new JScrollPane(txtEventDescription);
        eventScroll.setBorder(BorderFactory.createEmptyBorder());
        eventScroll.getViewport().setBackground(Color.WHITE);
        centerPanel.add(eventScroll, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // 5. Bottom Panel (Choices)
        choicePanel = new JPanel(new GridLayout(1, 2, 12, 0));
        choicePanel.setBackground(new Color(138, 21, 56));
        choicePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        btnChoiceA = new JButton("Choice A");
        btnChoiceB = new JButton("Choice B");

        styleButton(btnChoiceA);
        styleButton(btnChoiceB);

        btnChoiceA.addActionListener(e -> resolveChoice(true));
        btnChoiceB.addActionListener(e -> resolveChoice(false));

        choicePanel.add(btnChoiceA);
        choicePanel.add(btnChoiceB);
        add(choicePanel, BorderLayout.SOUTH);

        // 6. Start the Game!
        nextTurn();
    }

    private void addStatPanel(JPanel panel, String labelText, int maxValue) {
        JLabel label = new JLabel(labelText + " (Max " + maxValue + ")");
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setForeground(new Color(44, 62, 80));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(4));

        JProgressBar bar = new JProgressBar(0, maxValue);
        bar.setStringPainted(true);
        bar.setFont(new Font("Arial", Font.BOLD, 11));
        bar.setBackground(new Color(239, 232, 233));
        bar.setForeground(new Color(49, 130, 91));
        bar.setBorder(BorderFactory.createLineBorder(new Color(216, 203, 205)));
        bar.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (labelText.equals("Assignments")) {
            barAssignments = bar;
        } else if (labelText.equals("Sleep Debt")) {
            barSleep = bar;
        } else {
            barStress = bar;
        }

        panel.add(bar);
        panel.add(Box.createVerticalStrut(12));
    }

    private void styleButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setForeground(new Color(138, 21, 56));
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 204, 0), 2),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setPreferredSize(new Dimension(0, 58));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event) {
                if (btn.isEnabled()) {
                    btn.setBackground(new Color(255, 244, 204));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent event) {
                btn.setBackground(Color.WHITE);
            }
        });
    }

    private void triggerPauseMenu() {
        String[] options = {"Resume", "Save Game", "Restart", "Exit to Desktop"};
        int choice = JOptionPane.showOptionDialog(this, "Game Paused", "Pause Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice == 1) {
            if (DBConnection.saveGame(player)) {
                JOptionPane.showMessageDialog(this, "Progress saved to database!", "Save Game", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Could not save the game. Check the database connection and save table.", "Save Failed", JOptionPane.ERROR_MESSAGE);
            }
        } else if (choice == 2) {
            new GameFrame().setVisible(true);
            this.dispose();
        } else if (choice == 3) {
            System.exit(0);
        }
    }

    private void openReliefShop() {
        String[] options = {
                "Hire a Coding Tutor (₱300 = -5 Assignments)",
                "Venti Energy Drink (₱150 = -5 Sleep Debt)",
                "Play The PS5 (₱200 = -8 Stress)",
                "Cancel"
        };

        int choice = JOptionPane.showOptionDialog(this,
                "Emergency Relief Shop\nCurrent Baon: ₱" + player.money + "\nWhat do you want to buy?",
                "Take a Break", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[3]);

        if (choice < 0 || choice == 3) {
            return;
        }

        int cost;
        if (choice == 0) {
            cost = 300;
        } else if (choice == 1) {
            cost = 150;
        } else {
            cost = 200;
        }

        if (player.money < cost) {
            JOptionPane.showMessageDialog(this, "You don't have enough Baon for that!", "Broke", JOptionPane.WARNING_MESSAGE);
            return;
        }

        player.money -= cost;
        if (choice == 0) {
            player.assignments = Math.max(0, player.assignments - 5);
        } else if (choice == 1) {
            player.sleepDebt = Math.max(0, player.sleepDebt - 5);
        } else {
            player.stress = Math.max(0, player.stress - 8);
        }

        updateUIStats();
        if (currentEvent != null) {
            setupButton(btnChoiceA, currentEvent.choiceAText, currentEvent.costA);
            if (currentEvent.choiceBText != null && !currentEvent.choiceBText.trim().isEmpty()
                    && !currentEvent.choiceBText.equalsIgnoreCase("none")) {
                setupButton(btnChoiceB, currentEvent.choiceBText, currentEvent.costB);
            }
        }
    }


    private void nextTurn() {
        updateUIStats();

        // Check fail state
        if (player.isFailing()) {
            triggerGameOver("You failed to maintain your hazard stats. Burnout reached critical levels. Game Over.");
            return;
        }

        // Check win state after completing week 11
        if (player.week > 11) {
            JOptionPane.showMessageDialog(this,
                    "Congratulations! You survived the quadsem and passed the Departmental Exams!",
                    "Semester Complete", JOptionPane.INFORMATION_MESSAGE);
            triggerEnding();
            return;
        }

        if (player.week == 11 && player.turnInWeek == 4) {
            currentEvent = DBConnection.getEventById(1100);
        }
        else if (player.week == 10 && player.turnInWeek == 3) {
            // Very last turn of the game: Hell Week Finals
            currentEvent = DBConnection.getEventById(999);
        }
        else if ((player.week == 4 || player.week == 8) && player.turnInWeek == 3) {
            // Week 4 and Week 8, Turn 3: Summative Exams!
            currentEvent = DBConnection.getEventById(904); // ID 904 will be your Exam Event
        }
        else {
            currentEvent = DBConnection.getRandomEvent(player.stress, player.week);
        }

        if (currentEvent == null) {
            lblEventName.setText("Event unavailable");
            txtEventDescription.setText("DATABASE ERROR: Could not load event.");
            btnChoiceA.setEnabled(false);
            btnChoiceB.setEnabled(false);
            return;
        }

        if (currentEvent.id == 1100) {
            lblTurn.setText("Week: 11 / 11 (Departmental Exam)");
        } else {
            lblTurn.setText("Week: " + player.week + " / 11 (Turn: " + player.turnInWeek + "/3)");
        }
        String eventTitle = currentEvent.name;
        if (eventTitle == null || eventTitle.trim().isEmpty()) {
            eventTitle = currentEvent.id == 1100 ? "Departmental Exams" : "Campus Event";
        }
        lblEventName.setText(eventTitle);
        txtEventDescription.setText(currentEvent.description);

        setupButton(btnChoiceA, currentEvent.choiceAText, currentEvent.costA);

        if (currentEvent.choiceBText == null || currentEvent.choiceBText.trim().isEmpty() || currentEvent.choiceBText.equalsIgnoreCase("none")) {
            btnChoiceB.setVisible(false);
            choicePanel.setLayout(new GridLayout(1, 1));
        } else {
            btnChoiceB.setVisible(true);
            choicePanel.setLayout(new GridLayout(1, 2, 12, 0));
            setupButton(btnChoiceB, currentEvent.choiceBText, currentEvent.costB);
        }
        choicePanel.revalidate();
        choicePanel.repaint();
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
        AudioPlayer.playSFX("assets/click.wav");

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

        if (player.stress < 0) player.stress = 0;
        if (player.sleepDebt < 0) player.sleepDebt = 0;
        if (player.assignments < 0) player.assignments = 0;

        if (currentEvent.id == 1100) {
            player.week = 12;
            player.turnInWeek = 1;
        } else if (player.week == 11 && player.turnInWeek == 3) {
            player.turnInWeek = 4;
        } else {
            player.turnInWeek++;
            if (player.turnInWeek > 3) {
                player.turnInWeek = 1;
                player.week++;

                player.money += 500;
                JOptionPane.showMessageDialog(this,
                        "It's a new week! You received your ₱500 allowance.",
                        "Payday", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        nextTurn();
    }

    private void updateUIStats() {
        lblMoney.setText("Baon: ₱" + player.money);
        lblGrades.setText("Academic Points: " + player.academicPoints);

        updateHazardBar(barAssignments, player.assignments);
        updateHazardBar(barSleep, player.sleepDebt);
        updateHazardBar(barStress, player.stress);
    }

    private void updateHazardBar(JProgressBar bar, int value) {
        bar.setValue(value);
        double ratio = (double) value / bar.getMaximum();
        if (ratio >= 0.75) {
            bar.setForeground(new Color(138, 21, 56));
        } else if (ratio >= 0.5) {
            bar.setForeground(new Color(218, 166, 0));
        } else {
            bar.setForeground(new Color(49, 130, 91));
        }
    }

    private void triggerGameOver(String message) {
        lblEventName.setText("Game Over");
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

        lblEventName.setText("Semester Complete");
        txtEventDescription.setText("You survived the 11-week quadsem.\n\nFinal Evaluation: " + finalGrade);
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