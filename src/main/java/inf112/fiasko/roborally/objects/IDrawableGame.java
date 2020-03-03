package inf112.fiasko.roborally.objects;

import java.util.List;

/**
 * This interface describes a game drawable using libgdx
 */
public interface IDrawableGame {

    /**
     * Gets the number of tiles in the x direction
     * @return A positive integer
     */
    int getWidth();

    /**
     * Gets the number of tiles in the y direction
     * @return A positive integer
     */
    int getHeight();

    /**
     * Gets a list of all the tiles to be drawn
     *
     * Should return a list readable from top-left to top-right and so on. In other words, the first getWidth() tiles
     * should be drawn on the top row from left to right.
     *
     * @return A list of tiles
     */
    List<Tile> getTilesToDraw();

    /**
     * Gets a list of all the walls to be drawn
     *
     * Should return a list readable from top-left to top-right and so on. In other words, the first getWidth() walls
     * should be drawn on the top row from left to right.
     *
     * @return A list of walls
     */
    List<Wall> getWallsToDraw();

    /**
     * Gets a list of all robots to draw
     * @return A list of all robots to draw
     */
    List<Robot> getRobotsToDraw();

}