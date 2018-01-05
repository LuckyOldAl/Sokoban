
import javax.swing.ImageIcon;

/**
 * Wall class for map elements
 *
 * @author Alastair Watt 16001346
 */
public class Wall extends MapElement {

    public Wall() {
        
        elementName = "Wall";
        elementAsText = "X";
        imgFileName = "res/SokobanAssets/Wall.png";
        this.setIcon(new ImageIcon(imgFileName));
    }

}
