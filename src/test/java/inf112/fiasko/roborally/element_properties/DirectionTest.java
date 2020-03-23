package inf112.fiasko.roborally.element_properties;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DirectionTest {

    @Test
    public void getDirectionIdForNorth() {
        assertEquals(1, Direction.NORTH.getDirectionID());
    }

    @Test
    public void getDirectionFromNorthID() {
        assertEquals(Direction.NORTH, Direction.getDirectionFromID(1));
    }

    @Test
    public void getDirectionIdForSouthWest() {
        assertEquals(6, Direction.SOUTH_WEST.getDirectionID());
    }

    @Test
    public void getDirectionFromSouthWestID() {
        assertEquals(Direction.SOUTH_WEST, Direction.getDirectionFromID(6));
    }

    @Test
    public void allDirectionsIDConversionToIDAndBack() {
        for (Direction direction : Direction.values()) {
            assertEquals(direction, Direction.getDirectionFromID(direction.getDirectionID()));
        }
    }

    @Test
    public void invalidDirectionIDReturnsNull() {
        assertNull(Direction.getDirectionFromID(-1));
    }

    @Test
    public void allDirectionsHaveUniqueId() {
        /* This test is also done implicitly by the allDirectionsIDConversionToIDAndBack test, but that test may fail
           even if this test passes, so this test is needed for clarity. */
        Set<Integer> set = new HashSet<>();
        List<Integer> list = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            set.add(direction.getDirectionID());
            list.add(direction.getDirectionID());
        }
        assertEquals(list.size(), set.size());
    }

    @Test
    public void getNorthOppositeDirection() {
        assertEquals(Direction.SOUTH, Direction.getReverseDirection(Direction.NORTH));
    }

    @Test
    public void getSouthOppositeDirection() {
        assertEquals(Direction.NORTH, Direction.getReverseDirection(Direction.SOUTH));
    }

    @Test
    public void getEastOppositeDirection() {
        assertEquals(Direction.WEST, Direction.getReverseDirection(Direction.EAST));
    }

    @Test
    public void getWestOppositeDirection() {
        assertEquals(Direction.EAST, Direction.getReverseDirection(Direction.WEST));
    }

    @Test
    public void getNorthWestOppositeDirection() {
        assertEquals(Direction.SOUTH_EAST, Direction.getReverseDirection(Direction.NORTH_WEST));
    }

    @Test
    public void getNorthEastOppositeDirection() {
        assertEquals(Direction.SOUTH_WEST, Direction.getReverseDirection(Direction.NORTH_EAST));
    }

    @Test
    public void getSouthEastOppositeDirection() {
        assertEquals(Direction.NORTH_WEST, Direction.getReverseDirection(Direction.SOUTH_EAST));
    }

    @Test
    public void getSouthWestOppositeDirection() {
        assertEquals(Direction.NORTH_EAST, Direction.getReverseDirection(Direction.SOUTH_WEST));
    }

    @Test
    public void getLeftRotatedDirectionFromNorth() {
        assertEquals(Direction.WEST, Direction.getLeftRotatedDirection(Direction.NORTH));
    }

    @Test
    public void getRightRotatedDirectionFromNorth() {
        assertEquals(Direction.EAST, Direction.getRightRotatedDirection(Direction.NORTH));
    }

    @Test
    public void getRightRotatedDirectionFromSouth() {
        assertEquals(Direction.WEST, Direction.getRightRotatedDirection(Direction.SOUTH));
    }

    @Test
    public void getLeftRotatedDirectionFromSouth() {
        assertEquals(Direction.EAST, Direction.getLeftRotatedDirection(Direction.SOUTH));
    }

    @Test
    public void getRightRotatedDirectionFromEast() {
        assertEquals(Direction.SOUTH, Direction.getRightRotatedDirection(Direction.EAST));
    }

    @Test
    public void getLeftRotatedDirectionFromEast() {
        assertEquals(Direction.NORTH, Direction.getLeftRotatedDirection(Direction.EAST));
    }

    @Test
    public void getRightRotatedDirectionFromWest() {
        assertEquals(Direction.NORTH, Direction.getRightRotatedDirection(Direction.WEST));
    }

    @Test
    public void getLeftRotatedDirectionFromWest() {
        assertEquals(Direction.SOUTH, Direction.getLeftRotatedDirection(Direction.WEST));
    }

    @Test
    public void getRightRotatedDirectionFromNorthEast() {
        assertEquals(Direction.SOUTH_EAST, Direction.getRightRotatedDirection(Direction.NORTH_EAST));
    }

    @Test
    public void getLeftRotatedDirectionFromNorthEast() {
        assertEquals(Direction.NORTH_WEST, Direction.getLeftRotatedDirection(Direction.NORTH_EAST));
    }

    @Test
    public void getRightRotatedDirectionFromNorthWest() {
        assertEquals(Direction.NORTH_EAST, Direction.getRightRotatedDirection(Direction.NORTH_WEST));
    }

    @Test
    public void getLeftRotatedDirectionFromNorthWest() {
        assertEquals(Direction.SOUTH_WEST, Direction.getLeftRotatedDirection(Direction.NORTH_WEST));
    }

    @Test
    public void getRightRotatedDirectionFromSouthEast() {
        assertEquals(Direction.SOUTH_WEST, Direction.getRightRotatedDirection(Direction.SOUTH_EAST));
    }

    @Test
    public void getLeftRotatedDirectionFromSouthEast() {
        assertEquals(Direction.NORTH_EAST, Direction.getLeftRotatedDirection(Direction.SOUTH_EAST));
    }

    @Test
    public void getRightRotatedDirectionFromSouthWest() {
        assertEquals(Direction.NORTH_WEST, Direction.getRightRotatedDirection(Direction.SOUTH_WEST));
    }

    @Test
    public void getLeftRotatedDirectionFromSouthWest() {
        assertEquals(Direction.SOUTH_EAST, Direction.getLeftRotatedDirection(Direction.SOUTH_WEST));
    }

    @Test
    public void eastAndWestArePerpendicular() {
        assertFalse(Direction.arePerpendicular(Direction.EAST, Direction.WEST));
    }

    @Test
    public void eastAndNorthArePerpendicular() {
        assertTrue(Direction.arePerpendicular(Direction.EAST, Direction.NORTH));
    }

    @Test
    public void eastAndSouthArePerpendicular() {
        assertTrue(Direction.arePerpendicular(Direction.EAST, Direction.SOUTH));
    }

    @Test
    public void northAndSouthArePerpendicular() {
        assertFalse(Direction.arePerpendicular(Direction.NORTH, Direction.SOUTH));
    }

    @Test
    public void northAndWestArePerpendicular() {
        assertTrue(Direction.arePerpendicular(Direction.NORTH, Direction.WEST));
    }

    @Test
    public void southAndWestArePerpendicular() {
        assertTrue(Direction.arePerpendicular(Direction.SOUTH, Direction.WEST));
    }
}
