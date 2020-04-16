package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.elementproperties.GameState;

/**
 * This interface describes
 */
public interface IInteractableGame {
    /**
     * Gets the current state og the game
     * @return The state the game is currently in
     */
    GameState getGameState();

    /**
     * Sets the state of the game
     * @param gameState The new state of the game
     */
    void setGameState(GameState gameState);

    /**
     * Gets the name of the player who won
     * @return A string of the player name
     */
    String getWinningPlayerName();
}
