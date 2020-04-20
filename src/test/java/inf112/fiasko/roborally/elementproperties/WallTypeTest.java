package inf112.fiasko.roborally.elementproperties;

import inf112.fiasko.roborally.objects.Wall;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class WallTypeTest {

    @Test
    public void getWallTypeIDForWallNormal() {
        assertEquals(1, WallType.WALL_NORMAL.getWallTypeID());
    }

    @Test
    public void getWallTypeFromWallNormalID() {
        assertEquals(WallType.WALL_NORMAL, WallType.getWallTypeFromID(1));
    }

    @Test
    public void getWallTypeIDForWallLaserSingle() {
        assertEquals(3, WallType.WALL_LASER_SINGLE.getWallTypeID());
    }

    @Test
    public void getWallTypeFromWallLaserSingleID() {
        assertEquals(WallType.WALL_LASER_SINGLE, WallType.getWallTypeFromID(3));
    }

    @Test
    public void allWallTypesIDConversionToIDAndBack() {
        for (WallType type : WallType.values()) {
            assertEquals(type, WallType.getWallTypeFromID(type.getWallTypeID()));
        }
    }

    @Test
    public void invalidWallTypeIDReturnsNull() {
        assertNull(TileType.getTileTypeFromID(-1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidWallDirectionThrowsError() {
        new Wall(WallType.WALL_NORMAL, Direction.NORTH_EAST);
    }

    @Test
    public void allWallsHaveUniqueId() {
        /* This test is also done implicitly by the allTileTypesIDConversionToIDAndBack test, but that test may fail
           even if this test passes, so this test is needed for clarity. */
        Set<Integer> set = new HashSet<>();
        List<Integer> list = new ArrayList<>();
        for (WallType type : WallType.values()) {
            set.add(type.getWallTypeID());
            list.add(type.getWallTypeID());
        }
        assertEquals(list.size(), set.size());
    }
}
