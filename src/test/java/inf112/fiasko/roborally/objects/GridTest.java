package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Direction;
import inf112.fiasko.roborally.element_properties.TileType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GridTest {
    private Grid<Tile> grid;
    private Grid<Tile> grid2;
    private Tile defaultTile;

    @Before
    public void setUp() {
        defaultTile = new Tile(TileType.TILE, Direction.NORTH);
        grid = new Grid<>(7, 4);
        grid2 = new Grid<>(5,8, defaultTile);
    }

    @Test
    public void getWidthFromGrid() {
        assertEquals(7, grid.getWidth());
    }

    @Test
    public void getWidthFromGrid2() {
        assertEquals(5, grid2.getWidth());
    }

    @Test
    public void getHeightFromGrid() {
        assertEquals(4, grid.getHeight());
    }

    @Test
    public void getHeightFromGrid2() {
        assertEquals(8, grid2.getHeight());
    }

    @Test
    public void getElementFromGrid2() {
        assertEquals(defaultTile, grid2.getElement(3,5));
    }

    @Test
    public void setElementInGrid2() {
        Tile hole = new Tile(TileType.HOLE, Direction.NORTH);
        grid2.setElement(2,1, hole);
        assertEquals(hole, grid2.getElement(2,1));
    }

    @Test (expected = IllegalArgumentException.class)
    public void invalidPositionThrowsErrorOnGet() {
        grid.getElement(7, 4);
    }

    @Test (expected = IllegalArgumentException.class)
    public void invalidPositionThrowsErrorOnSet() {
        grid2.setElement(5, 8, null);
    }
}