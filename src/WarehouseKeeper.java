
import javax.swing.ImageIcon;

/**
 * WarehouseKeeper class for player character
 *
 * @author Alastair Watt 16001346
 */
public class WarehouseKeeper extends MovableMapElement {

    WarehouseKeeper(int newX, int newY) {
        super(newX, newY);
        elementName = "warehouseKeeper";
        elementAsText = "@";
        imgFileName = "res/SokobanAssets/WK.png";
        this.setIcon(new ImageIcon(imgFileName));
       }
}
