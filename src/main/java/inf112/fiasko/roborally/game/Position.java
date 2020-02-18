package inf112.fiasko.roborally.game;

/**
 * This class represent a position on the board
 */
public class Position {

    private int xCoordinate;
    private int yCoordinate;

    /**
     * Initializes the position
     * @param xCoordinate The x coordinate of the position
     * @param yCoordinate The y coordinate of the position
     */
    public Position(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * Gets the x coordinate of the position
     * @return The x coordinate of the position
     */
    public int getXCoordinate() {
        return xCoordinate;
    }

    /**
     * Gets the y coordinate of the position
     * @return The y coordinate of the position
     */
    public int getYCoordinate() {
        return yCoordinate;
    }
}
