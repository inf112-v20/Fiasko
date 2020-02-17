package inf112.fiasko.roborally.abstractions;

/**
 * This enum represents all possible tile types
 */
public enum TileType {
    TILE (1),
    HOLE (2),
    COGWHEEL_RIGHT (3),
    COGWHEEL_LEFT (4),
    TRANSPORT_BAND_SLOW (5),
    TRANSPORT_BAND_SLOW_RIGHT (6),
    TRANSPORT_BAND_SLOW_LEFT (7),
    TRANSPORT_BAND_SLOW_SIDE_ENTRANCES (8),
    TRANSPORT_BAND_SLOW_SIDE_ENTRANCE_LEFT (9),
    TRANSPORT_BAND_SLOW_SIDE_ENTRANCE_RIGHT (10),
    TRANSPORT_BAND_FAST (11),
    TRANSPORT_BAND_FAST_RIGHT (12),
    TRANSPORT_BAND_FAST_LEFT (13),
    TRANSPORT_BAND_FAST_SIDE_ENTRANCES (14),
    TRANSPORT_BAND_FAST_SIDE_ENTRANCE_LEFT (15),
    TRANSPORT_BAND_FAST_SIDE_ENTRANCE_RIGHT (16),
    FLAG_1 (17),
    FLAG_2 (18),
    FLAG_3 (19),
    FLAG_4 (20),
    WRENCH (21),
    WRENCH_AND_HAMMER (22),
    DEATH_TILE (23);

    private final int tileTypeID;

    /**
     * Constructor to let a tile type be represented by a numerical identifier
     * @param tileTypeID <p>The numerical identifier assigned to the tile type</p>
     */
    TileType(int tileTypeID) {
        this.tileTypeID = tileTypeID;
    }

    /**
     * Gets the numerical id used for alternate identification of a tile type
     * @return <p>The numerical id of the tile</p>
     */
    public int getTileTypeID() {
        return this.tileTypeID;
    }

    /**
     * Gets a tile type value from its numerical representation
     * @param tileTypeID <p>The numerical representation of a tile tpye</p>
     * @return <p>The enum value representing the tile type</p>
     */
    public static TileType getTileTypeFromID(int tileTypeID) {
        for (TileType type : TileType.values()) {
            if (type.tileTypeID == tileTypeID) {
                return type;
            }
        }
        return null;
    }
}
