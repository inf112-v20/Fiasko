package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.elementproperties.Direction;
import inf112.fiasko.roborally.elementproperties.TileType;

/**
 * This class represents a simple tile
 */
public class Tile {

    private TileType tileType;
    private Direction direction;

    /**
     * Instantiates a new tile
     *
     * @param tileType  The type of the tile
     * @param direction The direction of the tile
     */
    public Tile(TileType tileType, Direction direction) {
        if (direction.getDirectionID() % 2 == 0) {
            throw new IllegalArgumentException("Invalid direction for tile submitted");
        }
        this.tileType = tileType;
        this.direction = direction;
    }

    /**
     * Gets the tile type of the tile
     *
     * @return The tile's tile type
     */
    public TileType getTileType() {
        return tileType;
    }

    /**
     * Gets the direction of the tile
     *
     * @return The tile's direction
     */
    public Direction getDirection() {
        return direction;
    }
}
