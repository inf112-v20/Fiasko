package inf112.fiasko.roborally.element_properties;

public class Wall {
    private WallType wall;
    private Direction direction;

    public Wall (WallType wall,Direction direction){
        this.wall = wall;
        this.direction = direction;
    }

    public WallType getWallType() {
        return wall;
    }

    public Direction getDirection(){
        return direction;
    }




}
