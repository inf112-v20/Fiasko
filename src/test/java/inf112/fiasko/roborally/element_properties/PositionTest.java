package inf112.fiasko.roborally.element_properties;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {
    private Position testPosition1;
    private Position testPosition2;
    private Position testPosition3;
    private Position testPosition4;
    @Before
    public void setUp() {
        testPosition1 = new Position(3, 4);
        testPosition2 = new Position(2, 3);
        testPosition3 = new Position(3, 4);
        testPosition4 = new Position(3, 3);
    }
    @Test
    public void testGetXPosition(){
        assertEquals(3,testPosition1.getXCoordinate());
    }

    @Test
    public void testGetYPosition(){
        assertEquals(4,testPosition1.getYCoordinate());
    }

    @Test
    public void testSamePositionsAreEqual() { assertEquals(testPosition1, testPosition3);}

    @Test
    public void testDifferentPositionsAreNotEqual() { assertNotEquals(testPosition2, testPosition4);}
}
