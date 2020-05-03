package inf112.fiasko.roborally.objects.properties;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PositionTest {
    private Position testPosition1;
    private Position testPosition2;
    private Position testPosition3;
    private Position testPosition4;
    private Position testPosition5;
    private Position testPosition6;

    @Before
    public void setUp() {
        testPosition1 = new Position(3, 4);
        testPosition2 = new Position(2, 3);
        testPosition3 = new Position(3, 4);
        testPosition4 = new Position(3, 3);
        testPosition5 = new Position(1, 4);
        testPosition6 = new Position(3, 3);
    }

    @Test
    public void testGetXPosition() {
        assertEquals(3, testPosition1.getXCoordinate());
    }

    @Test
    public void testGetYPosition() {
        assertEquals(4, testPosition1.getYCoordinate());
    }

    @Test
    public void testSamePositionsAreEqual() {
        assertEquals(testPosition1, testPosition3);
    }

    @Test
    public void testDifferentPositionsAreNotEqual() {
        assertNotEquals(testPosition2, testPosition4);
    }

    @Test
    public void equalXandDifferentYIsNotEqual() {
        assertNotEquals(testPosition1, testPosition6);
    }

    @Test
    public void equalYandDifferentXIsNotEqual() {
        assertNotEquals(testPosition1, testPosition5);
    }
}
