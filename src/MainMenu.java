import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        // 1. Setup Window
        setTitle("Mapua Sim: The Quadsem Survival Game");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the window
        
        // 2. Main Panel setup with Mapúa Colors
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(138, 21, 56)); // Mapúa Cardinal Red

        mainPanel.add(Box.createRigidArea(new Dimension(0, 70))); // Top spacing

        // 3. Title Text
        JLabel titleLabel = new JLabel("MAPÚA SIM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 56));
        titleLabel.setForeground(new Color(255, 204, 0)); // Mapúa Gold
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("The Quadsem Survival Game");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(subtitleLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 60))); // Spacing before buttons

        // 4. Create Buttons
        JButton btnNewGame = createMenuButton("New Game");
        JButton btnLoadGame = createMenuButton("Load Game");
        JButton btnExit = createMenuButton("Drop Out (Exit)");

        // 5. Button Logic
        btnNewGame.addActionListener(e -> {
            new GameFrame().setVisible(true); // Launch the actual game window
            this.dispose(); // Close this main menu window
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

        btnExit.addActionListener(e -> System.exit(0)); // Kills the Java program

        // 6. Add Buttons to Panel
        mainPanel.add(btnNewGame);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between buttons
        mainPanel.add(btnLoadGame);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(btnExit);

        add(mainPanel);
    }

    // Helper method to keep buttons uniformly styled
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(250, 50));
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(138, 21, 56)); // Red text on white buttons
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenu().setVisible(true);
        });
    }
}