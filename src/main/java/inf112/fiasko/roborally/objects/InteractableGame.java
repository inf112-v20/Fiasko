package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.networking.containers.PowerDownContainerResponse;
import inf112.fiasko.roborally.networking.containers.ProgramsContainerResponse;
import inf112.fiasko.roborally.objects.properties.GameState;

import java.util.List;

/**
 * This interface describes a game which can be interacted with
 */
public interface InteractableGame {
    /**
     * Gets the current state of the game
     *
     * @return The state the game is currently in
     */
    GameState getGameState();

    /**
     * Sets the state of the game
     *
     * @param gameState The new state of the game
     */
    void setGameState(GameState gameState);

    /**
     * Gets the name of the player who won
     *
     * @return A string of the player name
     */
    String getWinningPlayerName();

    /**
     * Sets the name of the player that won the game
     *
     * @param winningPlayerName The player winning the game
     */
    void setWinningPlayerName(String winningPlayerName);

    /**
     * Continues turn when programs for all players are received from the server
     *
     * @param programs The programs container received from the server
     * @throws InterruptedException If interrupted during sleep
     */
    void receiveAllPrograms(ProgramsContainerResponse programs) throws InterruptedException;

    /**
     * Continues turn when stay in power down is received from all players
     *
     * @param powerDowns The power down container received from the server
     */
    void receiveStayInPowerDown(PowerDownContainerResponse powerDowns);

    /**
     * Gets the hand of this player
     *
     * @return The hand of this player
     */
    ProgrammingCardDeck getPlayerHand();

    /**
     * Sets the hand of this player
     *
     * @param playerHand The new hand of this player
     */
    void setPlayerHand(ProgrammingCardDeck playerHand);

    /**
     * Gets extra cards necessary when the player has no program to lock
     *
     * @return A deck of extra cards
     */
    ProgrammingCardDeck getExtraCards();

    /**
     * Sets extra cards necessary when the player has no program to lock
     *
     * @param extraCards The extra cards to use
     */
    void setExtraCards(ProgrammingCardDeck extraCards);

    /**
     * Gets the amount of cards the player can choose for their program
     *
     * @return The size of the player's next program
     */
    int getProgramSize();

    /**
     * Gets the program of this player
     *
     * @return The program of this player
     */
    List<ProgrammingCard> getProgram();

    /**
     * Sets the program of this player
     *
     * @param program The program of this player
     */
    void setProgram(List<ProgrammingCard> program);

}
