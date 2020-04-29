package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.elementproperties.Direction;

/**
 * Represents an element on the board
 *
 * @param <K> The type of the element
 */
public interface BoardElement<K> {

    /**
     * Gets the type of the element
     *
     * @return An enum value of type K
     */
    K getType();

    /**
     * Gets the direction of the element
     *
     * @return The element's direction
     */
    Direction getDirection();

    /**
     * Changes the direction of the element
     *
     * @param newDirection The element's new direction
     */
    void setDirection(Direction newDirection);

    /**
     * Makes a copy of the board element
     *
     * @return A copy of the element
     */
    BoardElement<K> copy();

}
