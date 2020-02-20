package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.TileType;
import inf112.fiasko.roborally.objects.Grid;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GridTest {
    private Grid<TileType> grid;
    private Grid<TileType> grid2;

    @Before
    public void setUp() {
        grid = new Grid<>(7, 4);
        grid2 = new Grid<>(5,8, TileType.TILE);
    }

    @Test
    public void getWidthFromGrid() {
        assertEquals(4, grid.getWidth());
    }

    @Test
    public void getWidthFromGrid2() {
        assertEquals(8, grid2.getWidth());
    }

    @Test
    public void getHeightFromGrid() {
        assertEquals(7,grid.getHeight());
    }

    @Test
    public void getHeightFromGrid2() {
        assertEquals(5,grid2.getHeight());
    }

    @Test
    public void getElementFromGrid2() {
        assertEquals(TileType.TILE, grid2.getElement(5,3));
    }

    @Test
    public void setElementInGrid2() {
        grid2.setElement(2,1, TileType.HOLE);
        assertEquals(TileType.HOLE, grid2.getElement(2,1));
    }
}