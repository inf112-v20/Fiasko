package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Direction;
import inf112.fiasko.roborally.element_properties.Position;
import inf112.fiasko.roborally.element_properties.RobotID;
import inf112.fiasko.roborally.element_properties.TileType;
import inf112.fiasko.roborally.element_properties.WallType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class BoardTest {
    private Grid<Tile> tileGrid;
    private Grid<Wall> wallGrid;
    private static Position zeroPosition;
    private static Position someValidPosition1;
    private static Position someValidPosition2;
    private static Position someValidPosition3;
    private static Position someValidPosition4;
    private static Position someValidPosition5;
    private static Position someValidPosition6;
    private static Position someValidPosition7;
    private static Position someValidPosition8;
    private List<Robot> robotList;
    private Board board;

    @BeforeClass
    public static void globalSetUp() {
        zeroPosition = new Position(0, 0);
        someValidPosition1 = new Position(2, 2);
        someValidPosition2 = new Position(2, 1);
        someValidPosition3 = new Position(2, 3);
        someValidPosition4 = new Position(2, 4);
        someValidPosition5 = new Position(3, 1);
        someValidPosition6 = new Position(3, 2);
        someValidPosition7 = new Position(3, 3);
        someValidPosition8 = new Position(3, 4);
    }

    @Before
    public void setUp() {
        tileGrid = new Grid<>(5, 5, new Tile(TileType.TILE, Direction.NORTH));
        wallGrid = new Grid<>(5, 5);
        robotList = new ArrayList<>();
        robotList.add(new Robot(RobotID.ROBOT_1, someValidPosition1));
        robotList.add(new Robot(RobotID.ROBOT_2, someValidPosition2));
        robotList.add(new Robot(RobotID.ROBOT_3, someValidPosition3));
        robotList.add(new Robot(RobotID.ROBOT_4, someValidPosition4));
        robotList.add(new Robot(RobotID.ROBOT_5, someValidPosition5));
        robotList.add(new Robot(RobotID.ROBOT_6, someValidPosition6));
        robotList.add(new Robot(RobotID.ROBOT_7, someValidPosition7));
        robotList.add(new Robot(RobotID.ROBOT_8, someValidPosition8));
        wallGrid.setElement(2, 1, new Wall(WallType.WALL_NORMAL, Direction.SOUTH));
        wallGrid.setElement(2, 2, new Wall(WallType.WALL_NORMAL, Direction.EAST));
        wallGrid.setElement(1, 2, new Wall(WallType.WALL_CORNER, Direction.NORTH_EAST));
        board = new Board(tileGrid, wallGrid, robotList);
    }

    @Test
    public void robotCanPushRobots() {
        board.moveRobot(RobotID.ROBOT_5, Direction.SOUTH);
        assertNotEquals(someValidPosition5, robotList.get(4).getPosition());
        assertNotEquals(someValidPosition6, robotList.get(5).getPosition());
        assertNotEquals(someValidPosition7, robotList.get(6).getPosition());
        assertFalse(board.getAliveRobots().contains(robotList.get(7)));
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

    @Test
    public void robotCanBeRotatedLeft() {
        Robot robot = robotList.get(0);
        assertEquals(Direction.NORTH, robot.getFacingDirection());
        board.rotateRobotLeft(RobotID.ROBOT_1);
        assertEquals(Direction.WEST, robot.getFacingDirection());
        board.rotateRobotLeft(RobotID.ROBOT_1);
        assertEquals(Direction.SOUTH, robot.getFacingDirection());
        board.rotateRobotLeft(RobotID.ROBOT_1);
        assertEquals(Direction.EAST, robot.getFacingDirection());
        board.rotateRobotLeft(RobotID.ROBOT_1);
        assertEquals(Direction.NORTH, robot.getFacingDirection());
    }

    @Test
    public void robotCanBeRotatedRight() {
        Robot robot = robotList.get(0);
        assertEquals(Direction.NORTH, robot.getFacingDirection());
        board.rotateRobotRight(RobotID.ROBOT_1);
        assertEquals(Direction.EAST, robot.getFacingDirection());
        board.rotateRobotRight(RobotID.ROBOT_1);
        assertEquals(Direction.SOUTH, robot.getFacingDirection());
        board.rotateRobotRight(RobotID.ROBOT_1);
        assertEquals(Direction.WEST, robot.getFacingDirection());
        board.rotateRobotRight(RobotID.ROBOT_1);
        assertEquals(Direction.NORTH, robot.getFacingDirection());
    }

    @Test (expected = IllegalArgumentException.class)
    public void gridsOfDifferentSizeThrowsError() {
        IGrid<Wall> wallGrid = new Grid<>(1, 1);
        new Board(tileGrid, wallGrid, robotList);
    }

    @Test (expected = IllegalArgumentException.class)
    public void multipleRobotsWithSameIDThrowsError() {
        Robot robot = new Robot(RobotID.ROBOT_1, someValidPosition1);
        robotList.add(robot);
        new Board(tileGrid, wallGrid, robotList);
    }

    @Test
    public void killRobotReducesAmountOfLivesByOne() {
        Robot robot = robotList.get(1);
        assertEquals(3, robot.getAmountOfLives());
        robot.setPosition(zeroPosition);
        board.moveRobot(robot.getRobotId(), Direction.NORTH);
        assertEquals(2, robot.getAmountOfLives());
    }

    @Test
    public void respawnRobotAtBackupPosition() {
        Robot robot = robotList.get(0);
        robot.setPosition(zeroPosition);
        board.moveRobot(robot.getRobotId(), Direction.NORTH);
        board.respawnRobots();
        assertEquals(robot.getBackupPosition(), robot.getPosition());
    }

    @Test
    public void respawnRobotDoesNotRespawnARobotWithNoLives() {
        Robot robot = robotList.get(0);
        robot.setPosition(zeroPosition);
        robot.setAmountOfLives(1);
        board.moveRobot(robot.getRobotId(), Direction.NORTH);
        board.respawnRobots();
        assertFalse(board.isRobotAlive(robot.getRobotId()));
    }
}
