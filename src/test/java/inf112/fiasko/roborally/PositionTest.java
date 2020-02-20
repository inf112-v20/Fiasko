package inf112.fiasko.roborally;

import inf112.fiasko.roborally.element_properties.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PositionTest {
    private Position testPosition;
    @Before
    public void setUp() {
        testPosition = new Position(3, 4);
    }
    @Test
    public void getXPosition(){
        assertEquals(3,testPosition.getXCoordinate());
    }
    @Test
    public void getYPosition(){
        assertEquals(4,testPosition.getYCoordinate());
    }
}
