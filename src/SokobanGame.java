
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *Game class loads the start menu and launches the game.
 * 
 * @author Alastair Watt 16001346
 */
public class SokobanGame extends JComponent implements ActionListener {

    private final JFrame mainWindow;
    JButton startGameButton;
    JButton exitButton;
    JLabel startupLabel;
    JLabel creditsLabel;
    JLabel instructionsLabel;

    SokobanGame() {
        mainWindow = new JFrame();
        mainWindow.setSize(500, 580);
        mainWindow.setLayout(null);
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        StartMenu();
    }

    private void StartMenu() {

        startupLabel = new JLabel("Sokoban");
        startupLabel.setFont(new Font("Serif", Font.BOLD, 120));
        startupLabel.setForeground(Color.red);
        add(startupLabel);
        startupLabel.setBounds(10, 20, 500, 100);
        startupLabel.setVisible(true);

        creditsLabel = new JLabel("<html>Created by:<br>Hiroyuki Imabayashi 1981<br><br>Badly Coded by:<br>Alastair Watt 2018</html>", SwingConstants.CENTER);
        creditsLabel.setFont(new Font("Serif", Font.BOLD, 24));
        creditsLabel.setForeground(Color.blue);
        add(creditsLabel);
        creditsLabel.setBounds(0, 140, 500, 180);
        creditsLabel.setVisible(true);

        instructionsLabel = new JLabel("<html>Instructions:<br>Push all the crates onto the diamonds.</html>", SwingConstants.CENTER);
        instructionsLabel.setFont(new Font("Serif", Font.BOLD, 26));
        instructionsLabel.setForeground(Color.red);
        add(instructionsLabel);
        instructionsLabel.setBounds(0, 340, 500, 100);
        instructionsLabel.setVisible(true);

        startGameButton = new JButton("Start");
        add(startGameButton);
        startGameButton.setBounds(50, 450, 80, 40);
        startGameButton.setVisible(true);
        startGameButton.addActionListener(this);

        exitButton = new JButton("Exit");
        add(exitButton);
        exitButton.setBounds(350, 450, 80, 40);
        exitButton.setVisible(true);
        exitButton.addActionListener(this);

        mainWindow.add(this);
        this.setBounds(0, 0, 500, 580);
        this.setVisible(true);
    }

    public void loadLevel(int levelNumber) {
        Level firstLevel = new Level(levelNumber);
        mainWindow.add(firstLevel);
        firstLevel.repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.startGameButton) {
            removeAll();
            repaint();
            loadLevel(0);
        } else if (e.getSource() == this.exitButton) {
            System.exit(0);
        }
    }
}
