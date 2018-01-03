
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author Alastair Watt 16001346
 */
public class SokobanGame extends JComponent {

    private int currentLevelNum;
    private final JFrame mainWindow;
    private StartMenu mainMenu;

    SokobanGame() {
        mainWindow = new JFrame();
        mainWindow.setSize(500, 580);
        mainWindow.setLayout(null);
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        StartMenu startMenu = new StartMenu();
        loadLevel(0);
    }

    public void loadLevel(int levelNumber) {
        Level currentLevel = new Level(levelNumber);
        mainWindow.add(currentLevel);
        currentLevel.repaint();

    }

}
