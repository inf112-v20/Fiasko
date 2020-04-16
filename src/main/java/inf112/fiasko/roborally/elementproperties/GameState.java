package inf112.fiasko.roborally.elementproperties;

public enum GameState {
    //Indicates that the server is waiting for something
    WAITING,
    //Gives the game Access to the client and server
    BEGINNING_OF_GAME,
    //Indicates that the users' input is being run. The board should be shown
    RUNNING_PROGRAMS,
    //Indicates that
    INITIAL_SETUP,
    //
    TURN_SETUP,
    //Indicates that the game is in the process of cleaning up after a turn
    TURN_CLEANUP,
    //Indicates that the user is in the process of choosing cards
    CHOOSING_CARDS,
    //Indicates that the user is in the process of choosing whether to power down
    CHOOSING_POWER_DOWN,
    //Indicates that the user is in the process of choosing whether to stay in power down
    CHOOSING_STAY_IN_POWER_DOWN,
    //Indicates that the user is in the process of sending their cards to the server
    SENDING_CARDS,
    //Indicates that the game is won by a player
    GAME_IS_WON,

    JUST_BEFORE_CHOOSING_CARDS,

    LOADING

}
