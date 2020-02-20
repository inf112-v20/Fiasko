package inf112.fiasko.roborally.objects;

/**
 * This Interface describes a grid
 * @param <K> type of element
 */
public interface IGrid<K> {

    /**
     * returns the width of the grid
     * @return the width
     */
    int getWidth();

    /**
     * returns the height of the grid
     * @return the height
     */
    int getHeight();

    /**
     * returns the element in a given x and y coordinate
     * @param x coordinate in the grid
     * @param y coordinate in the grid
     * @return element in the x and y coordinate
     * @throws IllegalArgumentException throws exception if coordinates are not in the grid
     */
    K getElement(int x,int y) throws IllegalArgumentException;

    /**
     * places the element in the given x and y coordinate
     * @param x coordinate
     * @param y coordinate
     * @param element that is being placed in the grid
     */
    void setElement(int x, int y, K element);
}
