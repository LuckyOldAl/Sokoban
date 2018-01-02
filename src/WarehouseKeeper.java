
import java.awt.Color;

/**
 * WarehouseKeeper class for player character
 *
 * @author Alastair Watt 16001346
 */
public class WarehouseKeeper extends MovableMapElement {

    private MapElement[][] map;

    WarehouseKeeper(int newX, int newY) {
        super(newX, newY);
        elementName = "warehouseKeeper";
        elementAsText = "@";
        elementColour = Color.black;

    }
}
