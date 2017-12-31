import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Level class populates the arrays from map files
 * @author Alastair Watt 16001346
 */

public class Level extends JComponent {
    
    MapElement map[][];                 //Array of stationary MapElements
    WarehouseKeeper warehouseKeeper;    //WarehouseKeeper object
    Crate crates[];                     //Array of all crates
    int numberOfMoves;
    JLabel numberOfMovesLabel;
    JButton restartLevelButton;
    
    int levelWidth;     
    int levelHeight;    
    int numberOfCrates; 
    
    Level(int levelNum) {
        try {
            loadMap(levelNum);
        } catch (FileNotFoundException ex){
            System.out.println("Level not found message:" + ex.getMessage());
        }
             
    }
    
    public void loadMap(int levelNum) throws FileNotFoundException {
        String levelLocation = "res/SokobanMaps/level" + levelNum + ".txt";
        
        File levelFile = new File(levelLocation);

        Scanner levelScanner = new Scanner(levelFile);
        
        levelWidth = levelScanner.nextInt();
        levelHeight = levelScanner.nextInt();
        numberOfCrates = levelScanner.nextInt();
        
        System.out.println(levelWidth);
        System.out.println(levelHeight);
        System.out.println(numberOfCrates);
        
        levelScanner.nextLine();  
        
        map = new MapElement[levelHeight][levelWidth];
        crates = new Crate[numberOfCrates];
         
        this.setBounds(50,50,levelWidth*20,levelHeight*20);
        this.setVisible(true);
        

        int i = 0; 
        int cratesAdded = 0;
        while (i < levelHeight) {            
            String currentLine = levelScanner.nextLine();
            char character[] = currentLine.toCharArray();
            int j = 0;
            while (j < character.length) {                            
                if (character[j] == ' ') {
                    map[i][j] = new Floor();
                } else if (character[j] == 'X') {
                    map[i][j] = new Wall();
                } else if (character[j] == '.') {
                    map[i][j] = new Diamond();
                } else if (character[j] == '*') {
                    map[i][j] = new Floor();                    
                    if (cratesAdded > numberOfCrates){ 
                        System.out.println("Too many crates");
                    } else {
                        crates[cratesAdded] = new Crate(i,j);
                        this.add(crates[cratesAdded]);
                        crates[cratesAdded].setBounds(j*10,i*10,10,10);
                        cratesAdded++;
                    }
                } else if (character[j] == '@') {
                    map[i][j] = new Floor();
                    warehouseKeeper = new WarehouseKeeper(i,j);
                    this.add(warehouseKeeper);
                    warehouseKeeper.setBounds(j*10,i*10,10,10);
                }
                j++;                               
            }
             i++;           
        }
        
        i = 0;
        while (i < levelHeight) {
            int j = 0;
            while (j < levelWidth) {             
                this.add(map[i][j]);
                map[i][j].setBounds(j*10,i*10,10,10);
                j++; 
            }
            i++; 
        }
    }
    
    public boolean checkForWin() {
        boolean win = true;
        int i = 0;
        while (i < crates.length) {
            if (map[crates[i].currentPosition.getX()][crates[i].currentPosition.getY()].elementAsText != ".") {
                win = false;
            }
            i++;
        }
        return win;
    }
    
    public void restartLevel() {
        int i = 0;
        while (i < crates.length) {
            crates[i].currentPosition.setX(crates[i].startingPosition.getX());
            crates[i].currentPosition.setY(crates[i].startingPosition.getY());
            i++;
        }
        warehouseKeeper.currentPosition.setX(warehouseKeeper.startingPosition.getX());
        warehouseKeeper.currentPosition.setY(warehouseKeeper.startingPosition.getY());  
    }

}