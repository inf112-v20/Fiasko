package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.elementproperties.Direction;
import inf112.fiasko.roborally.elementproperties.TileType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TileTest {
    private Tile tile;
    private Tile tile2;

    @Before
    public void setUp() {
        tile = new Tile(TileType.HOLE, Direction.NORTH);
        tile2 = new Tile(TileType.COGWHEEL_RIGHT, Direction.SOUTH);
    }

    @Test
    public void getTileTypeFromTile() {
        assertEquals(TileType.HOLE, tile.getTileType());
    }

    @Test
    public void getTileTypeFromTile2() {
        assertEquals(TileType.COGWHEEL_RIGHT, tile2.getTileType());
    }


    @Test
    public void getDirectionFromTile() {
        assertEquals(Direction.NORTH, tile.getDirection());
    }

    @Test
    public void getDirectionFromTile2() {
        assertEquals(Direction.SOUTH, tile2.getDirection());
    }

    @Test
    public void invalidTileTypeIDReturnsNull() {
        assertNull(TileType.getTileTypeFromID(-1));
    }

    @Test (expected = IllegalArgumentException.class)
    public void invalidTileDirectionThrowsError() {
        new Tile(TileType.TILE, Direction.NORTH_EAST);
    }
}