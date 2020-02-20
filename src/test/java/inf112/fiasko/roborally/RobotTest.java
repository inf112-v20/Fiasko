package inf112.fiasko.roborally;

import inf112.fiasko.roborally.element_properties.Position;
import inf112.fiasko.roborally.element_properties.Robot;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RobotTest {
    @Test
    public void testRobotGetDamageOnInitializedRobot(){
        Position robotPosition = new Position(3,6);
        Robot testRobotGetDamage = new Robot(6, robotPosition);
        assertEquals(0, testRobotGetDamage.getDamage());
    }
}
