package inf112.fiasko.roborally;

import inf112.fiasko.roborally.abstractions.TileType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TileTypeTest {

    @Test
    public void getTileTypeIDForHole() {
        assertEquals(2, TileType.HOLE.getTileTypeID());
    }

    @Test
    public void getTileTypeFromHoleID() {
        assertEquals(TileType.HOLE, TileType.getTileTypeFromID(2));
    }

    @Test
    public void getTileTypeIDForWrench() {
        assertEquals(21, TileType.WRENCH.getTileTypeID());
    }

    @Test
    public void getTileTypeFromWrenchID() {
        assertEquals(TileType.WRENCH, TileType.getTileTypeFromID(21));
    }

    @Test
    public void allTileTypesIDConversionToIDAndBack() {
        for (TileType type : TileType.values()) {
            assertEquals(type, TileType.getTileTypeFromID(type.getTileTypeID()));
        }
    }

    @Test
    public void invalidTileTypeIDReturnsNull() {
        assertNull(TileType.getTileTypeFromID(-1));
    }

    @Test
    public void allTilesHaveUniqueId() {
        /* This test is also done implicitly by the allTileTypesIDConversionToIDAndBack test, but that test may fail
           even if this test passes, so this test is needed for clarity. */
        Set<Integer> set = new HashSet<>();
        List<Integer> list = new ArrayList<>();
        for (TileType type : TileType.values()) {
            set.add(type.getTileTypeID());
            list.add(type.getTileTypeID());
        }
        assertEquals(list.size(), set.size());
    }
}
