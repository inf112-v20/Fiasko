package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Position;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class RobotTest {
    private Position robotPosition;
    private Robot testRobot;
    @Before
    public void setUp(){
        robotPosition = new Position(3,6);
        testRobot = new Robot(6, robotPosition);
    }
    @Test
    public void testRobotGetDamageOnInitializedRobot(){
        assertEquals(0, testRobot.getDamage());
    }
    @Test
    public void testRobotSetDamage(){
        testRobot.setDamage(2);
        assertEquals(2, testRobot.getDamage());
    }
    @Test
    public void testRobotGetPositionOnInitializedRobot(){
        assertEquals(robotPosition, testRobot.getPosition());
    }
    @Test
    public void testRobotGetPositionOnRobotWithNewPosition(){
        Position newRobotPosition = new Position(8,12);
        testRobot.setPosition(newRobotPosition);
        assertEquals(newRobotPosition, testRobot.getPosition());
    }
    @Test
    public void testRobotIsInPowerDownOnInitializedRobot(){
        assertEquals(false, testRobot.isInPowerDown());
    }
    @Test
    public void testRobotSetPowerDown(){
        testRobot.setPowerDown(true);
        assertEquals(true, testRobot.isInPowerDown());
    }
    @Test
    public void testRobotGetNewFlag(){
        int nextFlag = 2;
        Position nextFlagPosition = new Position(3,4);
        testRobot.setLastFlagVisitedAndBackupPosition(nextFlag,nextFlagPosition);
        assertEquals(2, testRobot.getLastFlagVisited());
    }
    @Test
    public void testRobotGetNewBackup(){
        int nextFlag = 2;
        Position nextFlagPosition = new Position(3,4);
        testRobot.setLastFlagVisitedAndBackupPosition(nextFlag,nextFlagPosition);
        assertEquals(nextFlagPosition, testRobot.getBackupPosition());
    }


}
