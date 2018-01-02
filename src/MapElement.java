import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;

/**
 * Master class for map elements
 *
 * @author Alastair Watt 16001346
 */
public class MapElement extends JLabel {

    String elementName;
    String elementAsText;
    Color elementColour;

    public String getElementName() {
        return elementName;
    }

    public String getElementAsText() {
        return elementAsText;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(elementColour);
        g.fillRect(0, 0, 20, 20);
    }

}
