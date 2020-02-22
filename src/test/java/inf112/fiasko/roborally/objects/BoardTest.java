package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Direction;
import inf112.fiasko.roborally.element_properties.Position;
import inf112.fiasko.roborally.element_properties.RobotID;
import inf112.fiasko.roborally.element_properties.TileType;
import inf112.fiasko.roborally.element_properties.WallType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {
    private Grid<Tile> tileGrid;
    private Grid<Wall> wallGrid;
    private Position someValidPosition;
    private List<Robot> robotList;
    private Board board;

    @Before
    public void setUp() {
        tileGrid = new Grid<>(5, 5, new Tile(TileType.TILE, Direction.NORTH));
        wallGrid = new Grid<>(5, 5);
        someValidPosition = new Position(2, 2);
        robotList = new ArrayList<>();
        robotList.add(new Robot(RobotID.ROBOT_1, someValidPosition));
        wallGrid.setElement(2, 3, new Wall(WallType.WALL_NORMAL, Direction.SOUTH));
        wallGrid.setElement(2, 2, new Wall(WallType.WALL_NORMAL, Direction.EAST));
        wallGrid.setElement(1, 2, new Wall(WallType.WALL_CORNER, Direction.NORTH_EAST));
        board = new Board(tileGrid, wallGrid, robotList);
    }

    @Test
    public void robotCanMove() {
        assertTrue(board.moveRobot(RobotID.ROBOT_1, Direction.SOUTH));
    }

    @Test
    public void robotIsStoppedByWallOnSameTile() {
        assertFalse(board.moveRobot(RobotID.ROBOT_1, Direction.EAST));
    }

    @Test
    public void robotIsStoppedByWallOnAdjacentTile() {
        assertFalse(board.moveRobot(RobotID.ROBOT_1, Direction.NORTH));
    }

    @Test
    public void robotIsStoppedByCornerWall() {
        assertFalse(board.moveRobot(RobotID.ROBOT_1, Direction.WEST));
    }

    @Test (expected = IllegalArgumentException.class)
    public void gridsOfDifferentSizeThrowsError() {
        IGrid<Wall> wallGrid = new Grid<>(1, 1);
        new Board(tileGrid, wallGrid, robotList);
    }

    @Test (expected = IllegalArgumentException.class)
    public void multipleRobotsWithSameIDThrowsError() {
        Robot robot = new Robot(RobotID.ROBOT_1, someValidPosition);
        robotList.add(robot);
        new Board(tileGrid, wallGrid, robotList);
    }
}
