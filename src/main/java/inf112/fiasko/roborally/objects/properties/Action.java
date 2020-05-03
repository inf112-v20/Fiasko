package inf112.fiasko.roborally.objects.properties;

/**
 * This enum represents an action on a programming card
 */
public enum Action {
    /**
     * The action of rotating a robot clockwise
     */
    ROTATE_RIGHT,
    /**
     * The action of rotating a robot counterclockwise
     */
    ROTATE_LEFT,
    /**
     * The action of rotating a robot 180 degrees
     */
    U_TURN,
    /**
     * The action of moving a robot one tile forward
     */
    MOVE_1,
    /**
     * The action of moving a robot two tiles forward
     */
    MOVE_2,
    /**
     * The action of moving a robot three tiles forward
     */
    MOVE_3,
    /**
     * The action of moving a robot one tile backward
     */
    BACK_UP
}