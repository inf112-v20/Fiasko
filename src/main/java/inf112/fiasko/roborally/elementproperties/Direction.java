package inf112.fiasko.roborally.elementproperties;

/**
 * This enum represents all possible directions for an element on the board
 */
public enum Direction {
    NORTH(1),
    NORTH_EAST(2),
    EAST(3),
    SOUTH_EAST(4),
    SOUTH(5),
    SOUTH_WEST(6),
    WEST(7),
    NORTH_WEST(8);

    private final int directionID;

    /**
     * Constructor to let a direction to be represented by a numerical identifier
     *
     * @param directionID The numerical identifier assigned to the direction
     */
    Direction(int directionID) {
        this.directionID = directionID;
    }

    /**
     * Gets a direction from its numerical id
     *
     * @param directionID The numerical representation of a direction
     * @return The enum value representing the direction, or null if the id is invalid
     */
    public static Direction getDirectionFromID(int directionID) {
        for (Direction direction : Direction.values()) {
            if (direction.directionID == directionID) {
                return direction;
            }
        }
        return null;
    }

    /**
     * Checks whether two directions are perpendicular
     *
     * @param direction1 The first direction
     * @param direction2 The second direction
     * @return True if the directions are perpendicular
     */
    public static boolean arePerpendicular(Direction direction1, Direction direction2) {
        return direction1.equals(getLeftRotatedDirection(direction2)) ||
                direction1.equals(getRightRotatedDirection(direction2));
    }

    /**
     * Gets the reverse of a direction
     *
     * @param direction A direction
     * @return The reverse direction
     */
    public static Direction getReverseDirection(Direction direction) {
        return getDirectionFromID((((direction.getDirectionID() + 3) % 8) + 1));
    }

    /**
     * Gets the direction if something rotated to the left
     *
     * <p>A rotation is assumed to be a ninety degrees rotation, so NORTH would become WEST and so on.</p>
     *
     * @param direction A direction
     * @return The left rotated direction
     */
    public static Direction getLeftRotatedDirection(Direction direction) {
        return getDirectionFromID(((((direction.getDirectionID() - 3) + 8) % 8) + 1));
    }

    /**
     * Gets the direction if something rotated to the right
     *
     * <p>A rotation is assumed to be a ninety degrees rotation, so NORTH would become EAST and so on.</p>
     *
     * @param direction A direction
     * @return The left rotated direction
     */
    public static Direction getRightRotatedDirection(Direction direction) {
        return getDirectionFromID((((direction.getDirectionID() + 1) % 8) + 1));
    }

    /**
     * Gets the numerical identifier used for alternate identification of a direction
     *
     * @return The numerical id of the direction
     */
    public int getDirectionID() {
        return this.directionID;
    }
}