package inf112.fiasko.roborally.game_wrapper;

/**
 * Keeps track of screen instances
 */
public class ScreenManager {
    private MainMenuScreen mainMenuScreen;
    private BoardActiveScreen boardActiveScreen;
    private CardChoiceScreen cardChoiceScreen;

    /**
     * Gets an instance of the main menu screen
     * @param roboRallyWrapper The robo rally launcher instance to use
     * @return A main menu screen instance
     */
    public synchronized MainMenuScreen getMainMenuScreen(RoboRallyWrapper roboRallyWrapper) {
        if (this.mainMenuScreen == null) {
            this.mainMenuScreen = new MainMenuScreen(roboRallyWrapper);
        }
        return mainMenuScreen;
    }

    /**
     * Gets an instance of the board active screen
     * @param roboRallyWrapper The robo rally launcher instance to use
     * @return A board active screen instance
     */
    public synchronized BoardActiveScreen getBoardActiveScreen(RoboRallyWrapper roboRallyWrapper) {
        if (this.boardActiveScreen == null) {
            this.boardActiveScreen = new BoardActiveScreen(roboRallyWrapper);
        }
        return boardActiveScreen;
    }

    /**
     * Gets an instance of the board active screen
     * @param roboRallyWrapper The robo rally launcher instance to use
     * @return A board active screen instance
     */
    public synchronized CardChoiceScreen getCardChoiceScreen(RoboRallyWrapper roboRallyWrapper) {
        if (this.cardChoiceScreen == null) {
            this.cardChoiceScreen = new CardChoiceScreen(roboRallyWrapper);
        }
        return cardChoiceScreen;
    }
}
