package inf112.fiasko.roborally.elementproperties;

/**
 * This enum represents all possible wall types
 */
public enum WallType {
    WALL_NORMAL (1),
    WALL_CORNER (2),
    WALL_LASER_SINGLE (3),
    WALL_LASER_DOUBLE (4);

    private final int wallTypeID;

    /**
     * Constructor to let a wall type be represented by a numerical identifier
     * @param wallTypeID <p>The numerical identifier assigned to the wall type</p>
     */
    WallType(int wallTypeID) {
        this.wallTypeID = wallTypeID;
    }

    /**
     * Gets the numerical id used for alternate identification of a wall type
     * @return <p>The numerical id of the wall type</p>
     */
    public int getWallTypeID() {
        return this.wallTypeID;
    }

    /**
     * Gets a wall type value from its numerical representation
     * @param wallTypeID <p>The numerical representation of a wall type</p>
     * @return <p>The enum value representing the wall type, or null if the id is invalid</p>
     */
    public static WallType getWallTypeFromID(int wallTypeID) {
        for (WallType type : WallType.values()) {
            if (type.wallTypeID == wallTypeID) {
                return type;
            }
        }
        return null;
    }
}
