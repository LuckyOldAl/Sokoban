
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
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

    public final JFrame mainWindow;        //Main window variables will not change once initialised so can be assigned final
    private JButton startGameButton;
    private JButton exitButton;
    private JLabel startupLabel;
    private JLabel creditsLabel;
    private JLabel instructionsLabel;
    public JButton restartLevelButton;         //Restart button
    public JButton moveUpButton;               //Up button
    public JButton moveDownButton;             //Down button
    public JButton moveLeftButton;             //Left button
    public JButton moveRightButton;            //Right button
    public static JButton nextLevelButton;     //Win message and next level button. Static as it needs to be accessed from Level class
    public static JLabel numberOfMovesLabel;   //Label to show number of moves. Static as it needs to be accessed from Level class
    public int numberOfMoves;                  //Move counter
    public int levelNumber;
    public Level firstLevel;
  
    //Creates main window and adds start menu
    public SokobanGame() {
        
        mainWindow = new JFrame();
        mainWindow.setSize(1090, 850);
        mainWindow.getContentPane().setBackground(Color.black);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setLayout(null);
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startMenu();
    }

    //Start menu contains information labels and buttons to launch or exit the game
    private void startMenu() {

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
        
        firstLevel = new Level(levelNumber);
        mainWindow.add(firstLevel);
        firstLevel.repaint();
        loadUI();

    }
    
    //Draw UI score counter and control buttons, each of which has an ActionListner
    public void loadUI() {

        numberOfMovesLabel = new JLabel("Number of Moves : " + numberOfMoves);  //Set label text
        add(numberOfMovesLabel);                                                //Add to level
        numberOfMovesLabel.setFont(new Font("Serif", Font.BOLD, 18));           //Set font
        numberOfMovesLabel.setForeground(Color.red);                            //Set text colour
        numberOfMovesLabel.setBounds(575, 735, 190, 40);                        //Set size and position
        numberOfMovesLabel.setVisible(true);                                    //Set visibility

        restartLevelButton = new JButton("Restart");                            //Set button name
        add(restartLevelButton);
        restartLevelButton.setFont(new Font("Serif", Font.BOLD, 18));
        restartLevelButton.setBackground(Color.red);                            //Set button background colour
        restartLevelButton.setBounds(600, 635, 120, 40);
        restartLevelButton.setVisible(true);
        restartLevelButton.addActionListener(this);                             //Add ActionListner to button

        moveUpButton = new JButton("Up");
        add(moveUpButton);
        moveUpButton.setFont(new Font("Serif", Font.BOLD, 18));
        moveUpButton.setBackground(Color.red);
        moveUpButton.setBounds(355, 635, 80, 40);
        moveUpButton.setVisible(true);
        moveUpButton.addActionListener(this);

        moveDownButton = new JButton("Down");
        add(moveDownButton);
        moveDownButton.setFont(new Font("Serif", Font.BOLD, 18));
        moveDownButton.setBackground(Color.red);
        moveDownButton.setBounds(355, 735, 80, 40);
        moveDownButton.setVisible(true);
        moveDownButton.addActionListener(this);

        moveLeftButton = new JButton("Left");
        add(moveLeftButton);
        moveLeftButton.setFont(new Font("Serif", Font.BOLD, 18));
        moveLeftButton.setBackground(Color.red);
        moveLeftButton.setBounds(310, 685, 80, 40);        
        moveLeftButton.setVisible(true);
        moveLeftButton.addActionListener(this);

        moveRightButton = new JButton("Right");
        add(moveRightButton);
        moveRightButton.setFont(new Font("Serif", Font.BOLD, 18));
        moveRightButton.setBackground(Color.red);
        moveRightButton.setBounds(400, 685, 80, 40);
        moveRightButton.setVisible(true);
        moveRightButton.addActionListener(this);

        nextLevelButton = new JButton("Well Done! Click for Next Level");
        add(nextLevelButton);
        nextLevelButton.setFont(new Font("Serif", Font.BOLD, 16));
        nextLevelButton.setBackground(Color.red);
        nextLevelButton.setBounds(530, 685, 260, 40);
        nextLevelButton.addActionListener(this);
        nextLevelButton.setEnabled(false);                                      //Button only enabled when level is complete
        nextLevelButton.setVisible(false);                                      //Button only visible when level is complete
    }  
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.startGameButton)) {                       //If source of action event is startGameButton in this class
            removeAll();                                                        //To remove start menu components
            repaint();                                                          //Make mainWindow blank again
            loadLevel(0);
        } else if (e.getSource().equals(this.exitButton)) {
            System.exit(0);
        } else if (e.getSource().equals(moveUpButton)) {
            firstLevel.moveElement(firstLevel.warehouseKeeper, "up");
        } else if (e.getSource().equals(moveDownButton)) {
            firstLevel.moveElement(firstLevel.warehouseKeeper, "down");
        } else if (e.getSource().equals(moveLeftButton)) {
            firstLevel.moveElement(firstLevel.warehouseKeeper, "left");
        } else if (e.getSource().equals(moveRightButton)) {
            firstLevel.moveElement(firstLevel.warehouseKeeper, "right");
        } else if (e.getSource().equals(restartLevelButton)) {
            firstLevel.restartLevel();
        } else if (e.getSource().equals(nextLevelButton)) {
            nextLevelButton.setEnabled(false);       
            nextLevelButton.setVisible(false);
            levelNumber++;
            numberOfMoves = 0;
            numberOfMovesLabel.setText("Number of Moves : " + numberOfMoves);
            firstLevel.removeAll();                                                    //To remove previous level from window
            firstLevel.repaint();
            try {
                firstLevel.loadMap(levelNumber);
            } catch (FileNotFoundException ex) {
                System.out.println("Level not found message:" + ex.getMessage());
            }
    }
}
}
