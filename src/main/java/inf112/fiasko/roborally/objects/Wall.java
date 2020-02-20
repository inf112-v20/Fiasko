package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Direction;
import inf112.fiasko.roborally.element_properties.WallType;

/**
 * This class represents a wall
 */
public class Wall {

    private final WallType wallType;
    private final Direction direction;

    /**
     * Initializes a wall
     * @param wallType The type of the wall
     * @param direction The direction of the wall
     */
    public Wall (WallType wallType, Direction direction) {
        this.wallType = wallType;
        this.direction = direction;
    }

    /**
     * Gets the type of the wall
     * @return The wall type
     */
    public WallType getWallType() {
        return wallType;
    }

    /**
     * Gets the direction of the wall
     * @return The direction of the wall
     */
    public Direction getDirection(){
        return direction;
    }
}
