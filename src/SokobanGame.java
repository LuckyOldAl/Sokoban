
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
 * Game class loads the start menu and launches the game.
 *
 * @author Alastair Watt 16001346
 */
public class SokobanGame extends JComponent implements ActionListener {

    private final JFrame mainWindow;        //Main window variables will not change so can be assigned final
    private JButton startGameButton;
    private JButton exitButton;
    private JLabel startupLabel;
    private JLabel creditsLabel;
    private JLabel instructionsLabel;

    //Creates main window and adds start menu
    public SokobanGame() {
        
        mainWindow = new JFrame();
        mainWindow.setSize(1090, 850);
        mainWindow.getContentPane().setBackground(Color.black);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setLayout(null);
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        StartMenu();
    }

    //Start menu contains information labels and buttons to launch or exit the game
    private void StartMenu() {

        startupLabel = new JLabel("Sokoban", SwingConstants.CENTER);
        startupLabel.setFont(new Font("Serif", Font.BOLD, 270));
        startupLabel.setForeground(Color.red);
        add(startupLabel);
        startupLabel.setBounds(0, 20, 1090, 220);
        startupLabel.setVisible(true);

        creditsLabel = new JLabel("<html>Created by Hiroyuki Imabayashi 1981<br><br>Graphics by 1001.com 2014<br><br>Badly Coded by Alastair Watt 2018</html>", SwingConstants.CENTER);
        creditsLabel.setFont(new Font("Serif", Font.BOLD, 20));
        creditsLabel.setForeground(Color.red);
        add(creditsLabel);
        creditsLabel.setBounds(0, 260, 1090, 180);
        creditsLabel.setVisible(true);

        instructionsLabel = new JLabel("<html>Instructions:<br>Push all the crates onto the diamonds.</html>", SwingConstants.CENTER);
        instructionsLabel.setFont(new Font("Serif", Font.BOLD, 56));
        instructionsLabel.setForeground(Color.red);
        add(instructionsLabel);
        instructionsLabel.setBounds(0, 470, 1090, 120);
        instructionsLabel.setVisible(true);

        startGameButton = new JButton("Start Game");
        startGameButton.setFont(new Font("Serif", Font.BOLD, 54));
        startGameButton.setBackground(Color.red);
        add(startGameButton);
        startGameButton.setBounds(10, 645, 300, 140);
        startGameButton.setVisible(true);
        startGameButton.addActionListener(this);

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Serif", Font.BOLD, 54));
        exitButton.setBackground(Color.red);
        add(exitButton);
        exitButton.setBounds(760, 645, 300, 140);
        exitButton.setVisible(true);
        exitButton.addActionListener(this);

        mainWindow.add(this);
        this.setBounds(0, 0, 1090, 800);
        this.setVisible(true);
    }

    //Method to load first level
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
