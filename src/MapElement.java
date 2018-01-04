
import javax.swing.JLabel;

/**
 * Master class for map elements
 *
 * @author Alastair Watt 16001346
 */
public class MapElement extends JLabel {

    public String elementName;
    public String elementAsText;

    public String imgFileName;
    

    public String getElementName() {
        return elementName;
    }

    public String getElementAsText() {
        return elementAsText;
    }

}