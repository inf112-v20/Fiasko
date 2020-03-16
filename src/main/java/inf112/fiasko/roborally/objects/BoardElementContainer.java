package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Position;

/**
 * This class represents a type of object and its position
 * @param <K> The type of object
 */
public class BoardElementContainer <K>{
    K obj;
    private Position pos;

    /**
     * Initializes the BoardElementContainer
     * @param obj The object
     * @param pos The position
     */
    BoardElementContainer(K obj, Position pos) {
        this.obj = obj;
        this.pos = pos;
    }

    /**
     * Gets the object
     * @return object
     */
    public K getObject() {
        return obj;
    }

    /**
     * Gets the position
     * @return position
     */
    public Position getPosition() {
        return pos;
    }
}
