package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.elementproperties.Direction;
import inf112.fiasko.roborally.elementproperties.WallType;

/**
 * This class represents a wall
 */
public class Wall implements BoardElement<WallType> {
    private final WallType wallType;
    private Direction direction;

    /**
     * Initializes a wall
     *
     * @param wallType  The type of the wall
     * @param direction The direction of the wall
     */
    public Wall(WallType wallType, Direction direction) {
        if (direction.getDirectionID() % 2 == 0 && wallType != WallType.WALL_CORNER) {
            throw new IllegalArgumentException("Invalid direction for wall type submitted");
        }
        this.wallType = wallType;
        this.direction = direction;
    }

    @Override
    public WallType getType() {
        return wallType;
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
    public BoardElement<WallType> copy() {
        return new Wall(wallType, direction);
    }

    @Override
    public String toString() {
        return wallType.getWallTypeID() + ";" + direction.getDirectionID();
    }
}
