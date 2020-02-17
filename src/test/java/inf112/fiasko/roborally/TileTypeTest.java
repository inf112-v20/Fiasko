package inf112.fiasko.roborally;

import inf112.fiasko.roborally.abstractions.TileType;
import org.junit.Test;

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
}
