package inf112.fiasko.roborally.game;

public class Position {

    private int xPosition;
    private int yPosition;

    /**
     * initalises the x and y cooardinats.
     * @param xPosition sets the x position.
     * @param yPosition sets the y position.
     */
    public Position(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    /**
     * gets x coordinate of the position.
     * @return the x coordinate of the position.
     */
    public int getXCoordinate() {
        return xPosition;
    }

    /**
     * gets y coordinate of the position.
     * @return the y coordinate of the position.
     */
    public int getYCoordinate() {
        return yPosition;
    }
}
