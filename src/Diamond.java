
import javax.swing.ImageIcon;

/**
 * Diamond class for map elements
 *
 * @author Alastair Watt 16001346
 */
public class Diamond extends MapElement {

    public Diamond() {
        
        elementName = "Diamond";
        elementAsText = ".";
        imgFileName = "res/SokobanAssets/Diamond.png";
        this.setIcon(new ImageIcon(imgFileName));
    }
 
}
