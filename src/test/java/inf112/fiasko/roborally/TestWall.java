package inf112.fiasko.roborally;

import inf112.fiasko.roborally.element_properties.Direction;
import inf112.fiasko.roborally.element_properties.Wall;
import inf112.fiasko.roborally.element_properties.WallType;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestWall {
    @Test
    public void testWallGetWallTypeNormal(){
        Wall testGetWall = new Wall(WallType.WALL_NORMAL, Direction.NORTH);
        assertEquals(WallType.WALL_NORMAL, testGetWall.getWallType());
    }
    @Test
    public void testWallGetWallTypeCorner(){
        Wall testGetWall = new Wall(WallType.WALL_CORNER, Direction.NORTH);
        assertEquals(WallType.WALL_CORNER, testGetWall.getWallType());
    }
    @Test
    public void testWallGetDirectionNorth(){
        Wall testGetWall = new Wall(WallType.WALL_CORNER, Direction.NORTH);
        assertEquals(Direction.NORTH, testGetWall.getDirection());
    }
    @Test
    public void testWallGetDirectionEast(){
        Wall testGetWall = new Wall(WallType.WALL_CORNER, Direction.EAST);
        assertEquals(Direction.EAST, testGetWall.getDirection());
    }

}
