import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    private static final Color CARDINAL_RED = new Color(138, 21, 56);
    private static final Color MAPUA_GOLD = new Color(255, 204, 0);

    public MainMenu() {
        setTitle("Mapua Sim: The Quadsem Survival Game");
        setSize(680, 560);
        setMinimumSize(new Dimension(600, 520));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        AudioPlayer.playBGM("assets/bgm.wav");

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(CARDINAL_RED);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));

        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setOpaque(false);
        
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = GridBagConstraints.RELATIVE;
        gbcTitle.anchor = GridBagConstraints.CENTER;
        gbcTitle.insets = new Insets(5, 0, 5, 0); // Default vertical spacing

        JLabel titleLabel = new JLabel("MAPÚA SIM");
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 54));
        titleLabel.setForeground(MAPUA_GOLD);

        JLabel subtitleLabel = new JLabel("The Quadsem Survival Game");
        subtitleLabel.setFont(new Font("Georgia", Font.ITALIC, 20));
        subtitleLabel.setForeground(Color.WHITE);

        JLabel descriptionLabel = new JLabel("<html><div style='text-align:center; width:400px;'>Survive the 10-week quadsem by balancing your workload, rest, stress, and Baon.</div></html>");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        descriptionLabel.setForeground(new Color(255, 239, 224));

        JSeparator goldRule = new JSeparator();
        goldRule.setForeground(MAPUA_GOLD);
        goldRule.setBackground(MAPUA_GOLD);
        goldRule.setPreferredSize(new Dimension(200, 2));

        titlePanel.add(titleLabel, gbcTitle);
        titlePanel.add(subtitleLabel, gbcTitle);
        gbcTitle.insets = new Insets(15, 0, 15, 0); 
        titlePanel.add(descriptionLabel, gbcTitle);
        gbcTitle.insets = new Insets(5, 0, 15, 0);
        titlePanel.add(goldRule, gbcTitle);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setOpaque(false);
        
        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.gridx = 0;
        gbcBtn.gridy = GridBagConstraints.RELATIVE;
        gbcBtn.insets = new Insets(8, 0, 8, 0); 
        gbcBtn.anchor = GridBagConstraints.CENTER;

        JButton btnNewGame = createMenuButton("New Game", true);
        JButton btnLoadGame = createMenuButton("Load Game", false);
        JButton btnExit = createMenuButton("Exit Game", false);

        btnNewGame.setToolTipText("Start a fresh 10-week semester.");
        btnLoadGame.setToolTipText("Continue your saved semester.");

        btnNewGame.addActionListener(e -> {
            new GameFrame().setVisible(true);
            this.dispose();
        });

        btnLoadGame.addActionListener(e -> {
            Player savedPlayer = DBConnection.loadGame();
            if (savedPlayer == null) {
                JOptionPane.showMessageDialog(this,
                        "No saved game was found, or the database is unavailable.",
                        "Unable to Load Game",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            new GameFrame(savedPlayer).setVisible(true);
            this.dispose();
        });

        btnExit.addActionListener(e -> System.exit(0));

        buttonsPanel.add(btnNewGame, gbcBtn);
        buttonsPanel.add(btnLoadGame, gbcBtn);
        buttonsPanel.add(btnExit, gbcBtn);

        mainPanel.add(buttonsPanel, BorderLayout.CENTER);

        JLabel rhythmLabel = new JLabel("11 WEEKS  |  3 TURNS PER WEEK");
        rhythmLabel.setFont(new Font("Arial", Font.BOLD, 11));
        rhythmLabel.setForeground(new Color(255, 224, 174));
        rhythmLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        mainPanel.add(rhythmLabel, BorderLayout.SOUTH);

        add(mainPanel);
        getRootPane().setDefaultButton(btnNewGame);
    }

    private JButton createMenuButton(String text, boolean primary) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(300, 52));
        btn.setFocusPainted(false);
        Color baseBackground = primary ? MAPUA_GOLD : Color.WHITE;
        Color baseForeground = CARDINAL_RED;
        btn.setBackground(baseBackground);
        btn.setForeground(baseForeground);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MAPUA_GOLD, 2),
                BorderFactory.createEmptyBorder(10, 18, 10, 18)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event) {
                btn.setBackground(MAPUA_GOLD);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent event) {
                btn.setBackground(baseBackground);
            }
        });
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenu().setVisible(true);
        });
    }
}