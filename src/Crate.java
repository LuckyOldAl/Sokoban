
import javax.swing.ImageIcon;

/**
 * Crate class for map elements
 *
 * @author Alastair Watt 16001346
 */
public class Crate extends MovableMapElement {

    public Crate(int newX, int newY) {
        
        super(newX, newY);
        elementName = "Crate";
        elementAsText = "*";
        imgFileName = "res/SokobanAssets/Crate.png";
        this.setIcon(new ImageIcon(imgFileName));
    }

    //Method to change colour of crate when it is on a diamond
    public void CrateOnDiamond() {
        
        imgFileName = "res/SokobanAssets/CrateOnDiamond.png";
        this.setIcon(new ImageIcon(imgFileName));
    }

    //Method to change crate colour back to original colour
    public void CrateOffDiamond() {
        
        imgFileName = "res/SokobanAssets/Crate.png";
        this.setIcon(new ImageIcon(imgFileName));
    }
}
