package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.elementproperties.Position;

/**
 * This class represents a board element and its position
 *
 * @param <K> The type of element
 */
public class BoardElementContainer<K extends BoardElement> {
    private final K element;
    private final Position position;

    /**
     * Initializes the BoardElementContainer
     *
     * @param element  The element
     * @param position The position
     */
    public BoardElementContainer(K element, Position position) {
        this.element = element;
        this.position = position;
    }

    /**
     * Gets the element
     *
     * @return The element
     */
    public K getElement() {
        return element;
    }

    /**
     * Gets the position of the element
     *
     * @return The position of the element
     */
    public Position getPosition() {
        return position;
    }
}
