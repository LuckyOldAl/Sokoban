
/**
 * Master class for movable map elements
 *
 * @author Alastair Watt 16001346
 */

public class MovableMapElement extends MapElement {

    Coordinate currentPosition;
    Coordinate startingPosition;

    public MovableMapElement(int newX, int newY) {
        
        currentPosition = new Coordinate(newX, newY);
        startingPosition = new Coordinate(newX, newY);
    }

    public void setCurrentPosition(Coordinate c) {
        
        currentPosition.setX(c.getX());
        currentPosition.setY(c.getY());
        setBounds(c.getX() * 48, c.getY() * 48, 48, 48);
    }

    public int getXPosition() {
        
        return currentPosition.getX();
    }

    public int getYPosition() {
        
        return currentPosition.getY();
    }

    public void setStartingPosition(int newX, int newY) {
        
        startingPosition.setX(newX);
        startingPosition.setY(newY);
        setBounds(newX * 48, newY * 48, 48, 48);
    }

    public Coordinate getStartingPosition() {
        
        return startingPosition;
    }

    public void resetPosition() {
        
        currentPosition.setX(startingPosition.getX());
        currentPosition.setY(startingPosition.getY());
        setBounds(startingPosition.getX() * 48, startingPosition.getY() * 48, 48, 48);
    }

}
