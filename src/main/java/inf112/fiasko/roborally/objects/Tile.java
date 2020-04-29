package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.elementproperties.Direction;
import inf112.fiasko.roborally.elementproperties.TileType;
import inf112.fiasko.roborally.utility.StringUtil;

/**
 * This class represents a simple tile
 */
public class Tile implements BoardElement<TileType> {

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

    @Override
    public TileType getType() {
        return tileType;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction newDirection) {
        this.direction = newDirection;
    }

    @Override
    public BoardElement<TileType> copy() {
        return new Tile(tileType, direction);
    }

    @Override
    public String toString() {
        return StringUtil.addLeadingZeros(tileType.getTileTypeID(), 2) + ";" +
                StringUtil.addLeadingZeros(direction.getDirectionID(), 2);
    }
}
