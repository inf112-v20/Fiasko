package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Position;
import static org.junit.Assert.assertEquals;

import inf112.fiasko.roborally.element_properties.RobotID;
import org.junit.Before;
import org.junit.Test;

public class RobotTest {
    private Position robotPosition;
    private Robot testRobot;
    private int nextFlag = 1;

    @Before
    public void setUp() {
        robotPosition = new Position(3,6);
        testRobot = new Robot(RobotID.ROBOT_6, robotPosition);
    }

    @Test
    public void testRobotGetDamageOnInitializedRobot(){
        assertEquals(0, testRobot.getDamageTaken());
    }

    @Test
    public void testRobotGetPlayerId(){
        assertEquals(RobotID.ROBOT_6, testRobot.getRobotId());
    }

    @Test
    public void testRobotGetBackupOnInitializedRobot(){
        assertEquals(robotPosition, testRobot.getBackupPosition());
    }

    @Test
    public void testRobotSetDamage() {
        testRobot.setDamageTaken(2);
        assertEquals(2, testRobot.getDamageTaken());
    }

    @Test
    public void testRobotGetPositionOnInitializedRobot(){
        assertEquals(robotPosition, testRobot.getPosition());
    }

    @Test
    public void testRobotGetPositionOnRobotWithNewPosition() {
        Position newRobotPosition = new Position(8,12);
        testRobot.setPosition(newRobotPosition);
        assertEquals(newRobotPosition, testRobot.getPosition());
    }

    @Test
    public void testRobotIsInPowerDownOnInitializedRobot(){
        assertEquals(false, testRobot.isInPowerDown());
    }

    @Test
    public void testRobotSetPowerDown() {
        testRobot.setPowerDown(true);
        assertEquals(true, testRobot.isInPowerDown());
    }

    @Test
    public void testRobotGetNewFlag() {
        testRobot.setLastFlagVisitedAndUpdateBackupPosition(nextFlag);
        assertEquals(1, testRobot.getLastFlagVisited());
    }

    @Test
    public void testRobotGetNewBackup() {
        Position nextFlagPosition = new Position(3,4);
        testRobot.setPosition(nextFlagPosition);
        testRobot.setLastFlagVisitedAndUpdateBackupPosition(nextFlag);
        assertEquals(nextFlagPosition, testRobot.getBackupPosition());
    }
}
