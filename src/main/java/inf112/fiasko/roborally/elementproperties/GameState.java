package inf112.fiasko.roborally.elementproperties;

/**
 * This enum represents the different states the game can be in
 */
public enum GameState {

    /**
     * At the absolute start of the game, before anything has happended
     */
    BEGINNING_OF_GAME,
    /**
     * Indicates that the users' input is being run. The board should be shown
     */
    RUNNING_PROGRAMS,
    /**
     * The game is in the process of setting up things which are needed before it's ready for user input
     */
    INITIAL_SETUP,
    /**
     * Indicates that the game is in the process of cleaning up after a turn
     */
    TURN_CLEANUP,
    /**
     * Indicates that the user is in the process of choosing cards
     */
    CHOOSING_CARDS,
    /**
     * Indicates that the user is in the process of choosing whether to power down
     */
    CHOOSING_POWER_DOWN,
    /**
     * Indicates that the user is in the process of choosing whether to stay in power down
     */
    CHOOSING_STAY_IN_POWER_DOWN,
    /**
     * Indicates that the game is won by a player
     */
    GAME_IS_WON,
    /**
     * Indicates that the game is currently waiting for something
     */
    LOADING,
    /**
     * Indicates that the game is no longer running
     */
    EXITED

}
