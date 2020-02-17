package inf112.fiasko.roborally;

import inf112.fiasko.roborally.abstractions.Direction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
}
