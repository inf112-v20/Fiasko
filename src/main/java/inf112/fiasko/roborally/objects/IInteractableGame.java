package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.elementproperties.GameState;
import inf112.fiasko.roborally.networking.containers.PowerdownContainer;
import inf112.fiasko.roborally.networking.containers.ProgamsContainer;

import java.util.List;

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

    void reciveAllProgrammes(ProgamsContainer programs) throws InterruptedException;

    void recivedStayInPowerdown(PowerdownContainer powerdowns);

    List<ProgrammingCard> getProgram();

    int getProgramSize();

    void setPlayerHand(ProgrammingCardDeck playerHand);

    ProgrammingCardDeck getPlayerHand();

    void setProgram(List<ProgrammingCard> program);



}
