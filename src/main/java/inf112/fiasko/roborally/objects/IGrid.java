package inf112.fiasko.roborally.objects;

/**
 * This Interface describes a grid
 * @param <K> The type of element the grid is to store
 */
public interface IGrid<K> {

    /**
     * Gets the width of the grid
     * @return The width of the grid
     */
    int getWidth();

    /**
     * Gets height of the grid
     * @return The height of the grid
     */
    int getHeight();

    /**
     * Gets the element in a given x and y coordinate
     * @param x Coordinate in the grid
     * @param y Coordinate in the grid
     * @return Element in the x and y coordinate
     * @throws IllegalArgumentException Throws an exception if the coordinates are outside of the grid
     */
    K getElement(int x,int y) throws IllegalArgumentException;

    /**
     * Places the element on the given x and y coordinate
     * @param x Coordinate in the grid
     * @param y Coordinate in the grid
     * @param element The element to place in the grid
     */
    void setElement(int x, int y, K element) throws IllegalArgumentException;
}
