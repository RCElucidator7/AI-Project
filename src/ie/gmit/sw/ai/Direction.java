package ie.gmit.sw.ai;

/**
 * Direction - enum to determine what direction to take
 * @author Ryan Conway
 *
 */

public enum Direction {
	UP (0), 
	DOWN (1), 
	LEFT (2), 
	RIGHT (3);

	private final int orientation;

    private Direction(int orientation) {
        this.orientation = orientation;
    }
    
    public int getOrientation() {
        return this.orientation;
    }
}