
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

    private MapElement map[][];                 //2D Array of stationary MapElements. Private as it will not be accessed from outside this class
    public WarehouseKeeper warehouseKeeper;     //WarehouseKeeper object. Public as it will be accessed from the SokobanGame class
    private Crate crates[];                     //Array of all crates
    public int numberOfMoves;                   //Move counter
    private int levelNumber;                    //Identifier for loadMap method
    private int levelWidth;                     //For setting width of 2D array
    private int levelHeight;                    //For setting height of 2D array
    private int numberOfCrates;                 //For setting length of crate array
    public JLabel numberOfMovesLabel;           //Label to show number of moves
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
  
    //loadMap uses scanner to read text files and populates arrays accordingly
    public void loadMap(int levelNumber) throws FileNotFoundException {
        
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

        this.setBounds(5, 5, 1080, 620);
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
                map[row][col].setBounds(col * 48, row * 48, 48, 48);    //All map elements are 48 pixel squares so x and y values must be multiplied by 64
                col++;
            }
            row++;
        }
    }

    //Resets movable object positions to starting positions
    public void restartLevel() {
        
        int i = 0;
        while (i < crates.length) {
            crates[i].crateOffDiamond();    //Changes any crates on diamonds back to original colour
            crates[i].resetPosition();
            i++;
        }
        warehouseKeeper.resetPosition();
        numberOfMoves = 0;                                                              //Reset number of moves
        SokobanGame.numberOfMovesLabel.setText("Number of Moves : " + numberOfMoves);   //Reset text on moves label
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        

         if (e.getSource().equals(this.exitButton)) {
            System.exit(0);
        }
    }
    
    //Method for moving warehousekeeper by adding or subtracting 1 from either x or y coordinate
    public boolean moveElement(WarehouseKeeper wk, String direction) {
        
        boolean canMove = true;
        Coordinate newPosition;
        
        switch (direction) {
            case "up":
                newPosition = new Coordinate(wk.getXPosition(), wk.getYPosition() - 1);
                break;
            case "down":
                newPosition = new Coordinate(wk.getXPosition(), wk.getYPosition() + 1);
                break;
            case "left":
                newPosition = new Coordinate(wk.getXPosition() - 1, wk.getYPosition());
                break;
            default:
                newPosition = new Coordinate(wk.getXPosition() + 1, wk.getYPosition());
                break;
        }

        //Checks if there is a wall at the position the warehousekeeper is trying to move into
        if (map[newPosition.getY()][newPosition.getX()].getElementName().equals("Wall")) {
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
            SokobanGame.numberOfMovesLabel.setText("Number of Moves : " + numberOfMoves);
        }
        return canMove;
    }

    //Method for moving crates by adding or subtracting 1 from either x or y coordinate
    public boolean moveElement(int c, String direction) {
        
        boolean canMove = true;
        Coordinate newPosition;
        
        switch (direction) {
            case "up":
                newPosition = new Coordinate(crates[c].getXPosition(), crates[c].getYPosition() - 1);
                break;
            case "down":
                newPosition = new Coordinate(crates[c].getXPosition(), crates[c].getYPosition() + 1);
                break;
            case "left":
                newPosition = new Coordinate(crates[c].getXPosition() - 1, crates[c].getYPosition());
                break;
            default:
                newPosition = new Coordinate(crates[c].getXPosition() + 1, crates[c].getYPosition());
                break;
        }

         //Checks if there is a wall at the position the crate is trying to move into
        if (map[newPosition.getY()][newPosition.getX()].getElementName().equals("Wall")) {
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
             //Checks if there is a diamond at the position the crate is moving into
            if (map[newPosition.getY()][newPosition.getX()].getElementName().equals("Diamond")) {
                crates[c].crateOnDiamond();     //Changes crate colour to show it is on diamond
            } else {
                crates[c].crateOffDiamond();    //In case crate moved onto diamond then off again
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
            if (!"Diamond".equals(map[crates[i].getYPosition()][crates[i].getXPosition()].getElementName())) {
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
                gameCompletedLabel.setFont(new Font("Serif", Font.BOLD, 200));
                gameCompletedLabel.setForeground(Color.red);
                gameCompletedLabel.setBounds(0, 0, 1080, 420);
                gameCompletedLabel.setVisible(true);

                exitButton = new JButton("Exit");
                exitButton.setFont(new Font("Serif", Font.BOLD, 54));
                exitButton.setBackground(Color.red);
                add(exitButton);
                exitButton.setBounds(380, 380, 300, 140);
                exitButton.addActionListener(this);
                exitButton.setVisible(true);
            } else {
                SokobanGame.nextLevelButton.setEnabled(true);       //Enable next level button
                SokobanGame.nextLevelButton.setVisible(true);       //Show next level button with level complete dialog
            }
        }
        return win;
    }

}
