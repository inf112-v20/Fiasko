package inf112.fiasko.roborally.objects;

import java.util.List;

/**
 * This interface describes a game drawable using libgdx
 */
public interface DrawableGame {

    /**
     * Gets the number of tiles in the x direction
     *
     * @return A positive integer
     */
    int getWidth();

    /**
     * Gets the number of tiles in the y direction
     *
     * @return A positive integer
     */
    int getHeight();

    /**
     * Gets a list of all the tiles to be drawn
     *
     * <p>Should return a list readable from top-left to top-right and so on. In other words, the first getWidth() tiles
     * should be drawn on the top row from left to right.</p>
     *
     * @return A list of tiles
     */
    List<Tile> getTilesToDraw();

    /**
     * Gets a list of all the walls to be drawn
     *
     * <p>Should return a list readable from top-left to top-right and so on. In other words, the first getWidth() walls
     * should be drawn on the top row from left to right.</p>
     *
     * @return A list of walls
     */
    List<Wall> getWallsToDraw();

    /**
     * Gets a list of all the particles to be drawn
     *
     * <p>Should return a list readable from top-left to top-right and so on. In other words, the first getWidth()
     * particles should be drawn on the top row from left to right.</p>
     *
     * @return A list of particles
     */
    List<Particle> getParticlesToDraw();

    /**
     * Gets a list of all robots to draw
     *
     * @return A list of all robots to draw
     */
    List<Robot> getRobotsToDraw();

    /**
     * Gets a list of all robots still participating
     *
     * @return A list of all robots
     */
    List<Robot> getAllRobots();

    /**
     * Gets a list of active players to receive information about player names
     *
     * @return A list of players
     */
    List<Player> getPlayers();
}
