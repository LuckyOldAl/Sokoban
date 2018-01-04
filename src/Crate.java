
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

}
