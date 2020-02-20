package inf112.fiasko.roborally.objects;

import java.util.ArrayList;
import java.util.List;

public class Grid<K> implements IGrid<K> {

    private int height;
    private int width;
    private List<ArrayList<K>> grid = new ArrayList<>();

    /**
     * Initializes a empty grid
     * @param height sets the height of the grid
     * @param width sets the width of the grid
     */
    public Grid(int height, int width) {
        this.height = height;
        this.width = width;
        for(int y = 0; y < height; y++) {
            ArrayList<K> row = new ArrayList<>();
            for(int x = 0; x < width; x++) {
                row.add(null);
            }
            this.grid.add(row);
        }
    }

    /**
     * Initializes a grid filled with standard tiles.
     * @param height sets the height of the grid
     * @param width sets the width of the grid
     * @param tile gives the TileType the grid is to be filled with
     */
    public Grid(int height, int width, K tile) {
        this.height = height;
        this.width = width;
        for(int y = 0; y < height; y++) {
            ArrayList<K> row = new ArrayList<>();
            for(int x = 0; x < width; x++) {
                row.add(tile);
            }
            this.grid.add(row);
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public K getElement(int x, int y) throws IllegalArgumentException {
        if(x >= 0 && x <= width && y >= 0 && y <= height) {
            return grid.get(y).get(x);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void setElement(int x, int y, K element) {
        if(x >= 0 && x <= width && y >= 0 && y <= height) {
            grid.get(y).set(x, element);
        }
    }
}
