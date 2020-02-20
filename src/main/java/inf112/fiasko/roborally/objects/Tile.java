package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Direction;
import inf112.fiasko.roborally.element_properties.TileType;

public class Tile {

    /**
     * tileType stores the type of the specific tile.
     */
    private TileType tileType;
    /**
     * direction stores the direction of the specific tile.
     */
    private Direction direction;

    /**
     *
     * @param tileType sets the type of the tile.
     * @param direction sets the direction the tile is facing.
     */
    public Tile(TileType tileType, Direction direction) {
        this.tileType = tileType;
        this.direction = direction;
    }

    /**
     *
     * @return the type of the specific tile.
     */
    public TileType getTileType() {
        return tileType;
    }

    /**
     *
     * @return the direction of the specific tile.
     */
    public Direction getDirection() {
        return direction;
    }
}
