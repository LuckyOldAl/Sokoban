
/**
 * Simple class to store x and y values together without any unnecessary extra
 * methods.
 *
 * @author Alastair Watt 16001346
 */

public class Coordinate {

    int x;
    int y;

    Coordinate(int newX, int newY) {
        x = newX;
        y = newY;
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
