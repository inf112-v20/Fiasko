package inf112.fiasko.roborally.elementproperties;

/**
 * This enum represents all possible tile types
 */
public enum TileType {
    /**
     * The generic tile without functionality
     */
    TILE(1),
    /**
     * A hole which robots might fall into
     */
    HOLE(2),
    /**
     * A cogwheel rotating to the right
     */
    COGWHEEL_RIGHT(3),
    /**
     * A cogwheel rotating to the left
     */
    COGWHEEL_LEFT(4),
    /**
     * A slow and straight conveyor belt
     */
    CONVEYOR_BELT_SLOW(5),
    /**
     * A slow conveyor belt with a rightward bend
     */
    CONVEYOR_BELT_SLOW_RIGHT(6),
    /**
     * A slow conveyor belt with a leftward bend
     */
    CONVEYOR_BELT_SLOW_LEFT(7),
    /**
     * A slow conveyor belt with entrances both to the left and to the right
     */
    CONVEYOR_BELT_SLOW_SIDE_ENTRANCES(8),
    /**
     * A slow conveyor belt with one entrance on the left and one from behind
     */
    CONVEYOR_BELT_SLOW_SIDE_ENTRANCE_LEFT(9),
    /**
     * A slow conveyor belt with one entrance on the right and one from behind
     */
    CONVEYOR_BELT_SLOW_SIDE_ENTRANCE_RIGHT(10),
    /**
     * A fast and straight conveyor belt
     */
    CONVEYOR_BELT_FAST(11),
    /**
     * A fast conveyor belt with a rightward bend
     */
    CONVEYOR_BELT_FAST_RIGHT(12),
    /**
     * A fast conveyor belt with a leftward bend
     */
    CONVEYOR_BELT_FAST_LEFT(13),
    /**
     * A fast conveyor belt with one entrance on the left and one from behind
     */
    CONVEYOR_BELT_FAST_SIDE_ENTRANCES(14),
    /**
     * A fast conveyor belt with one entrance on the left and one from behind
     */
    CONVEYOR_BELT_FAST_SIDE_ENTRANCE_LEFT(15),
    /**
     * A fast conveyor belt with one entrance on the right and one from behind
     */
    CONVEYOR_BELT_FAST_SIDE_ENTRANCE_RIGHT(16),
    FLAG_1(17),
    FLAG_2(18),
    FLAG_3(19),
    FLAG_4(20),
    WRENCH(21),
    WRENCH_AND_HAMMER(22),
    ROBOT_SPAWN_1(23),
    ROBOT_SPAWN_2(24),
    ROBOT_SPAWN_3(25),
    ROBOT_SPAWN_4(26),
    ROBOT_SPAWN_5(27),
    ROBOT_SPAWN_6(28),
    ROBOT_SPAWN_7(29),
    ROBOT_SPAWN_8(30),
    PIT_EMPTY(31),
    PIT_FULL(32),
    PIT_NORMAL(33),
    PIT_CORNER(34),
    PIT_U(35);

    private final int tileTypeID;

    /**
     * Constructor to let a tile type be represented by a numerical identifier
     *
     * @param tileTypeID The numerical identifier assigned to the tile type
     */
    TileType(int tileTypeID) {
        this.tileTypeID = tileTypeID;
    }

    /**
     * Gets a tile type value from its numerical representation
     *
     * @param tileTypeID The numerical representation of a tile type
     * @return The enum value representing the tile type, or null if the id is invalid
     */
    public static TileType getTileTypeFromID(int tileTypeID) {
        for (TileType type : TileType.values()) {
            if (type.tileTypeID == tileTypeID) {
                return type;
            }
        }
        return null;
    }

    /**
     * Gets the numerical id used for alternate identification of a tile type
     *
     * @return The numerical id of the tile type
     */
    public int getTileTypeID() {
        return this.tileTypeID;
    }
}
