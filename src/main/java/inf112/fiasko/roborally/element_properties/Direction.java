package inf112.fiasko.roborally.element_properties;

/**
 * This enum represents all possible directions for an element on the board
 */
public enum Direction {
    NORTH (1),
    NORTH_EAST (2),
    EAST (3),
    SOUTH_EAST (4),
    SOUTH (5),
    SOUTH_WEST (6),
    WEST (7),
    NORTH_WEST (8);

    private final int directionID;

    /**
     * Constructor to let a direction to be represented by a numerical identifier
     * @param directionID <p>The numerical identifier assigned to the direction</p>
     */
    Direction(int directionID) {
        this.directionID = directionID;
    }

    /**
     * Gets the numerical identifier used for alternate identification of a direction
     * @return <p>The numerical id of the direction</p>
     */
    public int getDirectionID() {
        return this.directionID;
    }

    /**
     * Gets a direction from its numerical id
     * @param directionID <p>The numerical representation of a direction</p>
     * @return <p>The enum value representing the direction, or null if the id is invalid</p>
     */
    public static Direction getDirectionFromID(int directionID) {
        for (Direction direction : Direction.values()) {
            if (direction.directionID == directionID) {
                return direction;
            }
        }
        return null;
    }
}