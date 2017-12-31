import java.awt.Color;

/**
 * Crate class for map elements
 * @author Alastair Watt 16001346
 */

public class Crate extends MovableMapElement{   
    
    public Crate(int newX, int newY) {
        super(newX, newY);
        elementName = "Crate";
        elementAsText = "*";
        elementColour = Color.red;
    }
    
}