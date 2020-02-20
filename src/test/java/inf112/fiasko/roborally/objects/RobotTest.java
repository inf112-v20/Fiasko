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
}