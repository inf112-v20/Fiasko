package inf112.fiasko.roborally.element_properties;

public class Wall {
    /**
     * This class is a representation of a wall
     */
    private WallType wall;
    private Direction direction;

    /**
     * Initializes the wall
     * @param wall gives the type of wall eks. wall normal or wall corner
     * @param direction gives the direction the wall is facing.
     */
    public Wall (WallType wall,Direction direction){
        this.wall = wall;
        this.direction = direction;
    }

    /**
     * Gets the type of the wall
     * @return the wall type
     */
    public WallType getWallType() {
        return wall;
    }

    /**
     * Gets the direction of the wall
     * @return the direction of the wall
     */
    public Direction getDirection(){
        return direction;
    }




}
