
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Level class populates the arrays from map files and handles all movement and
 * collision detection within the arrays.
 *
 * @author Alastair Watt 16001346
 */
public class Level extends JComponent implements ActionListener {

    MapElement map[][];                 //2D Array of stationary MapElements
    WarehouseKeeper warehouseKeeper;    //WarehouseKeeper object
    Crate crates[];                     //1D Array of all crates
    int numberOfMoves;                  //Move counter
    JLabel numberOfMovesLabel;          //Label to show number of moves
    JButton restartLevelButton;         //Restart button
    JButton nextLevelButton;
    int levelNumber;

    int levelWidth;
    int levelHeight;
    int numberOfCrates;

    JButton moveUpButton;
    JButton moveLeftButton;
    JButton moveDownButton;
    JButton moveRightButton;

    Level(int levelNumber) {
        try {
            loadMap(levelNumber);
        } catch (FileNotFoundException ex) {
            System.out.println("Level not found message:" + ex.getMessage());
        }
    }

    void loadUI() {

        numberOfMovesLabel = new JLabel("Number of Moves : " + numberOfMoves);
        add(numberOfMovesLabel);
        numberOfMovesLabel.setForeground(Color.red);
        numberOfMovesLabel.setBounds(730, 950, 150, 40);
        numberOfMovesLabel.setVisible(true);

        restartLevelButton = new JButton("Restart");
        add(restartLevelButton);
        restartLevelButton.setBounds(750, 850, 80, 40);
        restartLevelButton.setVisible(true);
        restartLevelButton.addActionListener(this);

        moveUpButton = new JButton("Up");
        add(moveUpButton);
        moveUpButton.setBounds(500, 850, 80, 40);
        moveUpButton.setVisible(true);
        moveUpButton.addActionListener(this);

        moveDownButton = new JButton("Down");
        add(moveDownButton);
        moveDownButton.setBounds(500, 950, 80, 40);
        moveDownButton.setVisible(true);
        moveDownButton.addActionListener(this);

        moveLeftButton = new JButton("Left");
        add(moveLeftButton);
        moveLeftButton.setBounds(455, 900, 80, 40);
        moveLeftButton.setVisible(true);
        moveLeftButton.addActionListener(this);

        moveRightButton = new JButton("Right");
        add(moveRightButton);
        moveRightButton.setBounds(545, 900, 80, 40);
        moveRightButton.setVisible(true);
        moveRightButton.addActionListener(this);

        nextLevelButton = new JButton("Well Done! Click for Next Level");
        add(nextLevelButton);
        nextLevelButton.setBounds(660, 900, 260, 40);
        nextLevelButton.addActionListener(this);
        nextLevelButton.setEnabled(false);
        nextLevelButton.setVisible(false);
    }

    public void loadMap(int levelNumber) throws FileNotFoundException {

        loadUI();

        String levelLocation = "res/SokobanMaps/level" + levelNumber + ".txt";
//        String levelLocation = "res/SokobanMaps/test" + levelNumber + ".txt";

        File levelFile = new File(levelLocation);

        Scanner levelScanner = new Scanner(levelFile);

        numberOfMoves = 0;

        levelWidth = levelScanner.nextInt();
        levelHeight = levelScanner.nextInt();
        numberOfCrates = levelScanner.nextInt();

        levelScanner.nextLine();

        map = new MapElement[levelHeight][levelWidth];
        crates = new Crate[numberOfCrates];

        this.setBounds(5, 5, 1430, 1070);
        this.setVisible(true);

        int row = 0;
        int cratesAdded = 0;
        while (row < levelHeight) {
            String currentLine = levelScanner.nextLine();
            char character[] = currentLine.toCharArray();
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
                    map[row][col] = new Floor();
                    warehouseKeeper = new WarehouseKeeper(col, row);
                    this.add(warehouseKeeper);
                    warehouseKeeper.setStartingPosition(col, row);
//                    System.out.println("Starting position is " + warehouseKeeper.getX() +" , " + warehouseKeeper.getY());
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
                map[row][col].setBounds(col * 64, row * 64, 64, 64);
                col++;
            }
            row++;
        }
    }

    public void restartLevel() {
        int i = 0;
        while (i < crates.length) {
            crates[i].resetPosition();
            i++;
        }
        warehouseKeeper.resetPosition();
        numberOfMoves = 0;
        numberOfMovesLabel.setText("Number of Moves : " + numberOfMoves);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.moveUpButton) {
//            System.out.println("You pressed the up button");
            moveElement(warehouseKeeper, "up");
        } else if (e.getSource() == this.moveDownButton) {
//            System.out.println("You pressed the down button");
            moveElement(warehouseKeeper, "down");
        } else if (e.getSource() == this.moveLeftButton) {
//           System.out.println("You pressed the left button");
            moveElement(warehouseKeeper, "left");
        } else if (e.getSource() == this.moveRightButton) {
//            System.out.println("You pressed the right button");
            moveElement(warehouseKeeper, "right");
        } else if (e.getSource() == this.restartLevelButton) {
            restartLevel();
        } else if (e.getSource() == this.nextLevelButton) {
            numberOfMoves = 0;
            numberOfMovesLabel.setText("Number of Moves : " + numberOfMoves);
            removeAll();
            repaint();
            levelNumber++;
            try {
                loadMap(levelNumber);
            } catch (FileNotFoundException ex) {
                System.out.println("Level not found message:" + ex.getMessage());
            }
        }
    }

    public boolean moveElement(WarehouseKeeper wk, String direction) {
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

        if (map[newPosition.getY()][newPosition.getX()].getElementName() == "Wall") {
//            System.out.println("In spite of your best efforts, the wall doesn't budge");
            canMove = false;
        } else {
            for (int i = 0; i < numberOfCrates; i++) {
                if (crates[i].getXPosition() == newPosition.getX() && crates[i].getYPosition() == newPosition.getY()) {
//                   System.out.println("warehouse keper pushed crate");
                    canMove = moveElement(i, direction);
                }
            }
        }
        if (canMove == true) {
            warehouseKeeper.setCurrentPosition(newPosition);
//            System.out.println("New position is " + wk.getX() +" , " + wk.getY());
            numberOfMoves++;
            numberOfMovesLabel.setText("Number of Moves : " + numberOfMoves);
        }
        return canMove;
    }

    public boolean moveElement(int c, String direction) {
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

        if (map[newPosition.getY()][newPosition.getX()].getElementName() == "Wall") {
//            System.out.println("Crates can't move through walls");
            canMove = false;
        } else {
            for (int i = 0; i < numberOfCrates; i++) {
                if (crates[i].getXPosition() == crates[c].getX() && crates[i].getYPosition() == crates[c].getY()) {

                } else if (crates[i].getXPosition() == newPosition.getX() && crates[i].getYPosition() == newPosition.getY()) {
//                    System.out.println("Crates can't move through other crates");
                    canMove = false;
                }
            }
        }
        if (canMove == true) {
            crates[c].setCurrentPosition(newPosition);
            checkForWin();
//            System.out.println(checkForWin());
        }
        return canMove;
    }

    public boolean checkForWin() {
        boolean win = true;
        int i = 0;
        while (i < crates.length) {
            if (map[crates[i].getYPosition()][crates[i].getXPosition()].getElementAsText() != ".") {
                win = false;
            }
            i++;
        }
        if (win == true) {
            nextLevelButton.setEnabled(true);
            nextLevelButton.setVisible(true);
        }
        return win;
    }

}
