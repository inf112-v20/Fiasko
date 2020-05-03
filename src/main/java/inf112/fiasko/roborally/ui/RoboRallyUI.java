package inf112.fiasko.roborally.ui;

import inf112.fiasko.roborally.networking.RoboRallyServer;
import inf112.fiasko.roborally.objects.RoboRallyGame;

/**
 * An interface describing necessary methods for a user interface
 */
public interface RoboRallyUI {
    /**
     * Gets the robo rally game being rendered by the UI
     *
     * @return The game used by the UI
     */
    RoboRallyGame getGame();

    /**
     * Sets the robo rally game being rendered by the UI
     *
     * @param game The new robo rally game being rendered
     */
    void setGame(RoboRallyGame game);

    /**
     * Quits the game in whatever way is appropriate
     *
     * @param message The message describing why the game quit
     */
    void quit(String message);

    /**
     * Gets the servers used for receiving objects from clients
     *
     * @return The server of the game
     */
    RoboRallyServer getServer();

    /**
     * Sets whether the client should hurry with whatever it's doing
     *
     * @param shouldHurry True if the user should hurry
     */
    void setShouldHurry(boolean shouldHurry);

    boolean isTesting();
}
