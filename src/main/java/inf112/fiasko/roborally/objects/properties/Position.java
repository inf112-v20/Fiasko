package inf112.fiasko.roborally.objects.properties;

/**
 * This class represent a position on the board
 */
public class Position {

    private final int xCoordinate;
    private final int yCoordinate;

    /**
     * Initializes the position
     *
     * @param xCoordinate The x coordinate of the position
     * @param yCoordinate The y coordinate of the position
     */
    public Position(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * Gets the x coordinate of the position
     *
     * @return The x coordinate of the position
     */
    public int getXCoordinate() {
        return xCoordinate;
    }

    /**
     * Gets the y coordinate of the position
     *
     * @return The y coordinate of the position
     */
    public int getYCoordinate() {
        return yCoordinate;
    }

    @Override
    public String toString() {
        return String.format("X: %d, Y: %d", xCoordinate, yCoordinate);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Position.class) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return this.xCoordinate == ((Position) obj).xCoordinate &&
                this.yCoordinate == ((Position) obj).yCoordinate;
    }
}
