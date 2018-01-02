
/**
 * Master class for movable map elements
 *
 * @author Alastair Watt 16001346
 */
public class MovableMapElement extends MapElement {

    Coordinate currentPosition;
    Coordinate startingPosition;

    MovableMapElement(int newX, int newY) {
        currentPosition = new Coordinate(newX, newY);
        startingPosition = new Coordinate(newX, newY);
    }

    public void setCurrentPosition(int newX, int newY) {
        currentPosition.setX(newX);
        currentPosition.setY(newY);
        setBounds(newX * 20, newY * 20, 20, 20);
    }

    public void setCurrentPosition(Coordinate c) {
        currentPosition.setX(c.getX());
        currentPosition.setY(c.getY());
        setBounds(c.getX() * 20, c.getY() * 20, 20, 20);
    }

    public Coordinate getCurrentPosition() {
        return currentPosition;
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
        setBounds(newX * 20, newY * 20, 20, 20);
    }

    public void setStartingPosition(Coordinate c) {
        startingPosition.setX(c.getX());
        startingPosition.setY(c.getY());
        setBounds(c.getX() * 20, c.getY() * 20, 20, 20);
    }

    public Coordinate getStartingPosition() {
        return startingPosition;
    }

    public int getStartingXPosition() {
        return startingPosition.getX();
    }

    public int getStartingYPosition() {
        return startingPosition.getY();
    }

    public void resetPosition() {
        currentPosition.setX(startingPosition.getX());
        currentPosition.setY(startingPosition.getY());
        setBounds(startingPosition.getX() * 20, startingPosition.getY() * 20, 20, 20);
    }

}
