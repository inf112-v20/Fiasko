package inf112.fiasko.roborally.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a grid which can store anything
 * @param <K> The type of element the grid should store
 */
public class Grid<K> implements IGrid<K> {

    private int height;
    private int width;
    private List<ArrayList<K>> grid = new ArrayList<>();

    /**
     * Initializes an empty grid
     * @param width The width of the grid
     * @param height The height of the grid
     */
    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        for (int y = 0; y < height; y++) {
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
    public Grid(int width, int height, K tile) {
        this.width = width;
        this.height = height;
        for (int y = 0; y < height; y++) {
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
        makeSureCoordinatesAreWithinBounds(x, y);
        return grid.get(y).get(x);
    }

    @Override
    public void setElement(int x, int y, K element) throws IllegalArgumentException {
        makeSureCoordinatesAreWithinBounds(x, y);
        grid.get(y).set(x, element);
    }

    /**
     * Throws an exception if input coordinates are out of bounds
     * @param x The x coordinate to check
     * @param y The y coordinate to check
     */
    private void makeSureCoordinatesAreWithinBounds(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException();
        }
    }
}
