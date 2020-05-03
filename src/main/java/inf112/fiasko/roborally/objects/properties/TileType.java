package inf112.fiasko.roborally.objects.properties;

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
    /**
     * The first flag a robot has to visit to win the game
     */
    FLAG_1(17),
    /**
     * The second flag a robot has to visit to win the game
     */
    FLAG_2(18),
    /**
     * The third flag a robot has to visit to win the game
     */
    FLAG_3(19),
    /**
     * The fourth flag a robot has to visit to win the game
     */
    FLAG_4(20),
    /**
     * A wrench which repairs a robot
     */
    WRENCH(21),
    /**
     * A wrench and hammer which repairs a robot
     */
    WRENCH_AND_HAMMER(22),
    /**
     * The spawn location belonging to the first robot
     */
    ROBOT_SPAWN_1(23),
    /**
     * The spawn location belonging to the second robot
     */
    ROBOT_SPAWN_2(24),
    /**
     * The spawn location belonging to the third robot
     */
    ROBOT_SPAWN_3(25),
    /**
     * The spawn location belonging to the fourth robot
     */
    ROBOT_SPAWN_4(26),
    /**
     * The spawn location belonging to the fifth robot
     */
    ROBOT_SPAWN_5(27),
    /**
     * The spawn location belonging to the sixth robot
     */
    ROBOT_SPAWN_6(28),
    /**
     * The spawn location belonging to the seventh robot
     */
    ROBOT_SPAWN_7(29),
    /**
     * The spawn location belonging to the eight robot
     */
    ROBOT_SPAWN_8(30),
    /**
     * A pit without edges a robot may fall into
     */
    PIT_EMPTY(31),
    /**
     * A pit with all edges a robot may fall into
     */
    PIT_FULL(32),
    /**
     * A pit with one edge a robot may fall into
     */
    PIT_NORMAL(33),
    /**
     * A pit with two connected edges the robot may fall into
     */
    PIT_CORNER(34),
    /**
     * A pit with three edges the robot may fall into
     */
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
