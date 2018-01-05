
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Level class populates the arrays from map files and handles all movement and
 * collision detection within the arrays.
 *
 * @author Alastair Watt 16001346
 */

public class Level extends JComponent implements ActionListener {

    private MapElement map[][];                 //2D Array of stationary MapElements
    private WarehouseKeeper warehouseKeeper;    //WarehouseKeeper object
    private Crate crates[];                     //Array of all crates
    private int numberOfMoves;                  //Move counter
    private int levelNumber;                    //Identifier for loadMap method
    private int levelWidth;                     //For setting width of 2D array
    private int levelHeight;                    //For setting height of 2D array
    private int numberOfCrates;                 //For setting length of crate array

    private JButton restartLevelButton;         //Restart button
    private JButton moveUpButton;               //Up button
    private JButton moveDownButton;             //Down button
    private JButton moveLeftButton;             //Left button
    private JButton moveRightButton;            //Right button
    private JButton nextLevelButton;            //Win message and next level button
    private JLabel numberOfMovesLabel;          //Label to show number of moves
    private JLabel gameCompletedLabel;          //Label appears if all levels completed
    private JButton exitButton;                 //Button appears if all levels completed

    //Constructor tries loadMap method and catches FileNotFoundException
    public Level(int levelNumber) {
        
        try {
            loadMap(levelNumber);
        } catch (FileNotFoundException ex) {
            System.out.println("Level not found message:" + ex.getMessage());
        }
    }
    
    //Draw UI score counter and control buttons, each of which has an ActionListner
    private void loadUI() {

        numberOfMovesLabel = new JLabel("Number of Moves : " + numberOfMoves);  //Set label text
        add(numberOfMovesLabel);                                                //Add to level
        numberOfMovesLabel.setFont(new Font("Serif", Font.BOLD, 18));           //Set font
        numberOfMovesLabel.setForeground(Color.red);                            //Set text colour
        numberOfMovesLabel.setBounds(705, 950, 190, 40);                        //Set size and position
        numberOfMovesLabel.setVisible(true);                                    //Set visibility

        restartLevelButton = new JButton("Restart");                            //Set button name
        add(restartLevelButton);
        restartLevelButton.setFont(new Font("Serif", Font.BOLD, 18));
        restartLevelButton.setBackground(Color.red);                            //Set button background colour
        restartLevelButton.setBounds(730, 850, 120, 40);
        restartLevelButton.setVisible(true);
        restartLevelButton.addActionListener(this);                             //Add ActionListner to button

        moveUpButton = new JButton("Up");
        add(moveUpButton);
        moveUpButton.setFont(new Font("Serif", Font.BOLD, 18));
        moveUpButton.setBackground(Color.red);
        moveUpButton.setBounds(500, 850, 80, 40);
        moveUpButton.setVisible(true);
        moveUpButton.addActionListener(this);

        moveDownButton = new JButton("Down");
        add(moveDownButton);
        moveDownButton.setFont(new Font("Serif", Font.BOLD, 18));
        moveDownButton.setBackground(Color.red);
        moveDownButton.setBounds(500, 950, 80, 40);
        moveDownButton.setVisible(true);
        moveDownButton.addActionListener(this);

        moveLeftButton = new JButton("Left");
        add(moveLeftButton);
        moveLeftButton.setFont(new Font("Serif", Font.BOLD, 18));
        moveLeftButton.setBackground(Color.red);
        moveLeftButton.setBounds(455, 900, 80, 40);
        moveLeftButton.setVisible(true);
        moveLeftButton.addActionListener(this);

        moveRightButton = new JButton("Right");
        add(moveRightButton);
        moveRightButton.setFont(new Font("Serif", Font.BOLD, 18));
        moveRightButton.setBackground(Color.red);
        moveRightButton.setBounds(545, 900, 80, 40);
        moveRightButton.setVisible(true);
        moveRightButton.addActionListener(this);

        nextLevelButton = new JButton("Well Done! Click for Next Level");
        add(nextLevelButton);
        nextLevelButton.setFont(new Font("Serif", Font.BOLD, 16));
        nextLevelButton.setBackground(Color.red);
        nextLevelButton.setBounds(660, 900, 260, 40);
        nextLevelButton.addActionListener(this);
        nextLevelButton.setEnabled(false);                                      //Button only enabled when level is complete
        nextLevelButton.setVisible(false);                                      //Button only visible when level is complete
    }

    //loadMap uses scanner to read text files and populates arrays accordingly
    public void loadMap(int levelNumber) throws FileNotFoundException {

        loadUI();                                                               //UI reloaded each time a new level is started as it is removed by next level button
        String levelLocation = "res/SokobanMaps/level" + levelNumber + ".txt";  //Filepath for level file
        File levelFile = new File(levelLocation);                               //Make File object using filepath
        Scanner levelScanner = new Scanner(levelFile);                          //Scanner reads text input from File

        numberOfMoves = 0;                          
        
        levelWidth = levelScanner.nextInt();        //Reads first number found in text file and uses it to set levelWidth
        levelHeight = levelScanner.nextInt();       //Reads next number found in text file and uses it to set levelHeight
        numberOfCrates = levelScanner.nextInt();    //Reads next number found in text file and uses it to set numberOfCrates

        levelScanner.nextLine();                    //To continue to read the next line of the text file

        map = new MapElement[levelHeight][levelWidth];  //Create 2D array of MapElement objects
        crates = new Crate[numberOfCrates];             //Create array of Crate objects

        this.setBounds(5, 5, 1430, 1070);
        this.setVisible(true);

        int row = 0;
        int cratesAdded = 0;
        while (row < levelHeight) {                         //Nested while loops to read each character of the text file and place corresponding object in correct position in array
            String currentLine = levelScanner.nextLine();   //To move onto reading next line
            char character[] = currentLine.toCharArray();   //Scanner places all characters on current line into chracter array
            int col = 0;
            while (col < character.length) {
                if (character[col] == ' ') {
                    map[row][col] = new Floor();
                } else if (character[col] == 'X') {
                    map[row][col] = new Wall();
                } else if (character[col] == '.') {
                    map[row][col] = new Diamond();
                } else if (character[col] == '*') {
                    map[row][col] = new Floor();
                    crates[cratesAdded] = new Crate(col, row);
                    this.add(crates[cratesAdded]);
                    crates[cratesAdded].setStartingPosition(col, row);
                    cratesAdded++;
                } else if (character[col] == '@') {
                    map[row][col] = new Floor();            //Place floor object in same position as warehousekeeper
                    warehouseKeeper = new WarehouseKeeper(col, row);
                    this.add(warehouseKeeper);
                    warehouseKeeper.setStartingPosition(col, row);
                }
                col++;
            }
            row++;
        }

        row = 0;
        while (row < levelHeight) {
            int col = 0;
            while (col < levelWidth) {
                this.add(map[row][col]);
                map[row][col].setBounds(col * 64, row * 64, 64, 64);    //All map elements are 64 pixel squares so x and y values must be multiplied by 64
                col++;
            }
            row++;
        }
    }

    //Resets movable object positions to starting positions
    private void restartLevel() {
        
        int i = 0;
        while (i < crates.length) {
            crates[i].CrateOffDiamond();    //Changes any crates on diamonds back to original colour
            crates[i].resetPosition();
            i++;
        }
        warehouseKeeper.resetPosition();
        numberOfMoves = 0;                                                  //Reset number of moves
        numberOfMovesLabel.setText("Number of Moves : " + numberOfMoves);   //Reset text on moves label
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == this.moveUpButton) {
            moveElement(warehouseKeeper, "up");
        } else if (e.getSource() == this.moveDownButton) {
            moveElement(warehouseKeeper, "down");
        } else if (e.getSource() == this.moveLeftButton) {
            moveElement(warehouseKeeper, "left");
        } else if (e.getSource() == this.moveRightButton) {
            moveElement(warehouseKeeper, "right");
        } else if (e.getSource() == this.restartLevelButton) {
            restartLevel();
        } else if (e.getSource() == this.nextLevelButton) {
            numberOfMoves = 0;
            numberOfMovesLabel.setText("Number of Moves : " + numberOfMoves);
            removeAll();                                                    //To remove previous level from window
            repaint();
            try {
                loadMap(levelNumber);
            } catch (FileNotFoundException ex) {
                System.out.println("Level not found message:" + ex.getMessage());
            }
        } else if (e.getSource() == this.exitButton) {
            System.exit(0);
        }
    }
    
    //Method for moving warehousekeeper
    private boolean moveElement(WarehouseKeeper wk, String direction) {
        
        boolean canMove = true;
        Coordinate newPosition;
        
        if (direction == "up") {
            newPosition = new Coordinate(wk.getXPosition(), wk.getYPosition() - 1);
        } else if (direction == "down") {
            newPosition = new Coordinate(wk.getXPosition(), wk.getYPosition() + 1);
        } else if (direction == "left") {
            newPosition = new Coordinate(wk.getXPosition() - 1, wk.getYPosition());
        } else {
            newPosition = new Coordinate(wk.getXPosition() + 1, wk.getYPosition());
        }

        //Checks if there is a wall at the position the warehousekeeper is trying to move into
        if (map[newPosition.getY()][newPosition.getX()].getElementName() == "Wall") {
            canMove = false;
        } else {
            //Checks if there is a crate at the position the warehousekeeper is trying to move into
            for (int i = 0; i < numberOfCrates; i++) {
                if (crates[i].getXPosition() == newPosition.getX() && crates[i].getYPosition() == newPosition.getY()) {
                    canMove = moveElement(i, direction);    //Ability to move depends on whether the crate can move
                }
            }
        }
        if (canMove == true) {
            warehouseKeeper.setCurrentPosition(newPosition);
            numberOfMoves++;
            numberOfMovesLabel.setText("Number of Moves : " + numberOfMoves);
        }
        return canMove;
    }

    //Method for moving crates
    private boolean moveElement(int c, String direction) {
        
        boolean canMove = true;
        Coordinate newPosition;
        if (direction == "up") {
            newPosition = new Coordinate(crates[c].getXPosition(), crates[c].getYPosition() - 1);
        } else if (direction == "down") {
            newPosition = new Coordinate(crates[c].getXPosition(), crates[c].getYPosition() + 1);
        } else if (direction == "left") {
            newPosition = new Coordinate(crates[c].getXPosition() - 1, crates[c].getYPosition());
        } else {
            newPosition = new Coordinate(crates[c].getXPosition() + 1, crates[c].getYPosition());
        }

         //Checks if there is a wall at the position the crate is trying to move into
        if (map[newPosition.getY()][newPosition.getX()].getElementName() == "Wall") {
            canMove = false;
        } else {
             //Checks if there is a crate at the position the crate is trying to move into
            for (int i = 0; i < numberOfCrates; i++) {
                if (crates[i].getXPosition() == newPosition.getX() && crates[i].getYPosition() == newPosition.getY()) {
                    canMove = false;
                }
            }
        }
        if (canMove == true) {
             //Checks if there is a diamond at the position the crate is trying to move into
            if (map[newPosition.getY()][newPosition.getX()].getElementName() == "Diamond") {
                crates[c].CrateOnDiamond();     //Changes crate colour to show it is on diamond
            }
            crates[c].setCurrentPosition(newPosition);
            checkForWin();                      //Only check win condition if crate moves onto diamond
        }
        return canMove;
    }

    //Method to check if win condition is satisfied
    private boolean checkForWin() {
        
        boolean win = true;
        int i = 0;
        while (i < crates.length) {
            //Checks all crate positions against all diamond positions. If they match the level is complete
            if (map[crates[i].getYPosition()][crates[i].getXPosition()].getElementName() != "Diamond") {
                win = false;
            }
            i++;
        }
        if (win == true) {
            levelNumber++;
            //Checks if the completed level was the last level and displays game win dialog if it was
            if (levelNumber > 5) {
                removeAll();
                repaint();
                gameCompletedLabel = new JLabel("You Win!", SwingConstants.CENTER);
                add(gameCompletedLabel);
                gameCompletedLabel.setFont(new Font("Serif", Font.BOLD, 320));
                gameCompletedLabel.setForeground(Color.red);
                gameCompletedLabel.setBounds(0, 0, 1430, 900);
                gameCompletedLabel.setVisible(true);

                exitButton = new JButton("Exit");
                exitButton.setFont(new Font("Serif", Font.BOLD, 54));
                exitButton.setBackground(Color.red);
                add(exitButton);
                exitButton.setBounds(900, 800, 300, 140);
                exitButton.addActionListener(this);
                exitButton.setVisible(true);
            } else {
                nextLevelButton.setEnabled(true);       //Enable next level button
                nextLevelButton.setVisible(true);       //Show next level button with level complete dialog
            }
        }
        return win;
    }

}
