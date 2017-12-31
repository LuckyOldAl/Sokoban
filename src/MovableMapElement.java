/**
 * Master class for movable map elements
 * @author Alastair Watt 16001346
 */

public class MovableMapElement extends MapElement {
    
    Coordinate currentPosition;
    Coordinate startingPosition;
    
    MovableMapElement(int newX, int newY) {
        currentPosition = new Coordinate(newX, newY);
        startingPosition = new Coordinate(newX, newY);
    }
    
}