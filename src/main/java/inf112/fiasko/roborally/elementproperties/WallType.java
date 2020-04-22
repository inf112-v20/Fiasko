package inf112.fiasko.roborally.elementproperties;

/**
 * This enum represents all possible wall types
 */
public enum WallType {
    /**
     * A normal wall
     */
    WALL_NORMAL(1),
    /**
     * A wall which consists of two connected walls
     */
    WALL_CORNER(2),
    /**
     * A wall with a laser
     */
    WALL_LASER_SINGLE(3),
    /**
     * A wall with two lasers
     */
    WALL_LASER_DOUBLE(4);

    private final int wallTypeID;

    /**
     * Constructor to let a wall type be represented by a numerical identifier
     *
     * @param wallTypeID The numerical identifier assigned to the wall type
     */
    WallType(int wallTypeID) {
        this.wallTypeID = wallTypeID;
    }

    /**
     * Gets a wall type value from its numerical representation
     *
     * @param wallTypeID The numerical representation of a wall type
     * @return The enum value representing the wall type, or null if the id is invalid
     */
    public static WallType getWallTypeFromID(int wallTypeID) {
        for (WallType type : WallType.values()) {
            if (type.wallTypeID == wallTypeID) {
                return type;
            }
        }
        return null;
    }

    /**
     * Gets the numerical id used for alternate identification of a wall type
     *
     * @return The numerical id of the wall type
     */
    public int getWallTypeID() {
        return this.wallTypeID;
    }
}
