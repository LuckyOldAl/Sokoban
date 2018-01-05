
import javax.swing.ImageIcon;

/**
 * Floor class for map elements
 *
 * @author Alastair Watt 16001346
 */
public class Floor extends MapElement {

    public Floor() {
        
        elementName = "Floor";
        elementAsText = " ";
        imgFileName = "res/SokobanAssets/Floor.png";
        this.setIcon(new ImageIcon(imgFileName));
    }

}
