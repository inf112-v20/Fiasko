package inf112.fiasko.roborally.utility;

import inf112.fiasko.roborally.elementproperties.Position;
import inf112.fiasko.roborally.objects.BoardElement;
import inf112.fiasko.roborally.objects.BoardElementContainer;
import inf112.fiasko.roborally.objects.Grid;

import java.util.ArrayList;
import java.util.List;

/**
 * A helper class containing helper methods fro a grid
 */
public final class GridUtil {

    /**
     * Gets all elements in a grid
     *
     * @param grid The grid to get elements from
     * @param <K>  The type of the elements int the grid
     * @return A list containing all the elements in the grid
     */
    public static <K> List<K> getAllElementsFromGrid(Grid<K> grid) {
        List<K> elements = new ArrayList<>();
        for (int y = grid.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < grid.getWidth(); x++) {
                elements.add(grid.getElement(x, y));
            }
        }
        return elements;
    }

    /**
     * Finds all tiles/walls with a certain type
     *
     * @param type The type of tile/wall to look for
     * @param grid The grid to look through
     * @param <K>  Type of the type to look for
     * @param <T>  Type of the grid
     * @return List of BoardElementContainers
     */
    public static <K, T extends BoardElement> List<BoardElementContainer<T>> getMatchingElements(K type, Grid<T> grid) {
        List<BoardElementContainer<T>> containerList = new ArrayList<>();
        for (int y = grid.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < grid.getWidth(); x++) {
                T gridElement = grid.getElement(x, y);
                if (gridElement != null && gridElement.getType() == type) {
                    containerList.add(new BoardElementContainer<>(gridElement, new Position(x, y)));
                }
            }
        }
        return containerList;
    }

}
