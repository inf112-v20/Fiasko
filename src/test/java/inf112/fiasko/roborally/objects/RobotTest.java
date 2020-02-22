package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Position;
import inf112.fiasko.roborally.objects.Robot;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RobotTest {
    @Test
    public void testRobotGetDamageOnInitializedRobot(){
        Position robotPosition = new Position(3,6);
        Robot testRobotGetDamage = new Robot(6, robotPosition);
        assertEquals(0, testRobotGetDamage.getDamage());
    }
    @Test
    public void testRobotSetDamage(){
        Position robotPosition = new Position(3,6);
        Robot testRobotSetDamage = new Robot(6, robotPosition);
        testRobotSetDamage.setDamage(2);
        assertEquals(2, testRobotSetDamage.getDamage());
    }
    @Test
    public void testRobotGetPositionOnInitializedRobot(){
        Position robotPosition = new Position(3,6);
        Robot testRobotGetPosition = new Robot(6, robotPosition);
        assertEquals(robotPosition, testRobotGetPosition.getPosition());
    }
    @Test
    public void testRobotGetPositionOnNewPosition(){
        Position robotPosition = new Position(3,6);
        Robot testRobotGetPosition = new Robot(6, robotPosition);
        Position newRobotPosition = new Position(8,12);
        testRobotGetPosition.setPosition(newRobotPosition);
        assertEquals(newRobotPosition, testRobotGetPosition.getPosition());
    }
    @Test
    public void testRobotIsInPowerDownOnInitializedRobot(){
        Position robotPosition = new Position(3,6);
        Robot testRobotGetPosition = new Robot(6, robotPosition);
        assertEquals(false, testRobotGetPosition.isInPowerDown());
    }
    @Test
    public void testRobotSetPowerDown(){
        Position robotPosition = new Position(3,6);
        Robot testRobotGetPosition = new Robot(6, robotPosition);
        testRobotGetPosition.setPowerDown(true);
        assertEquals(true, testRobotGetPosition.isInPowerDown());
    }
    @Test
    public void testRobotGetNewFlag(){
        Position robotPosition = new Position(3,6);
        Robot testRobotGetPosition = new Robot(6, robotPosition);
        int nextFlag = 2;
        Position nextFlagPosition = new Position(3,4);
        testRobotGetPosition.setLastFlagVisitedAndBackupPosition(nextFlag,nextFlagPosition);
        assertEquals(2, testRobotGetPosition.getLastFlagVisited());
    }
    @Test
    public void testRobotGetNewBackup(){
        Position robotPosition = new Position(3,6);
        Robot testRobotGetPosition = new Robot(6, robotPosition);
        int nextFlag = 2;
        Position nextFlagPosition = new Position(3,4);
        testRobotGetPosition.setLastFlagVisitedAndBackupPosition(nextFlag,nextFlagPosition);
        assertEquals(nextFlagPosition, testRobotGetPosition.getBackupPosition());
    }


}
