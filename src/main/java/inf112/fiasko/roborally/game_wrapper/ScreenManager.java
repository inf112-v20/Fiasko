package inf112.fiasko.roborally.game_wrapper;

/**
 * Keeps track of screen instances
 */
public class ScreenManager {
    private MainMenuScreen mainMenuScreen;
    private BoardActiveScreen boardActiveScreen;

    /**
     * Gets an instance of the main menu screen
     * @param roboRallyLauncher The robo rally launcher instance to use
     * @return A main menu screen instance
     */
    public synchronized MainMenuScreen getMainMenuScreen(RoboRallyLauncher roboRallyLauncher) {
        if (this.mainMenuScreen == null) {
            this.mainMenuScreen = new MainMenuScreen(roboRallyLauncher);
        }
        return mainMenuScreen;
    }

    /**
     * Gets an instance of the board active screen
     * @param roboRallyLauncher The robo rally launcher instance to use
     * @return A board active screen instance
     */
    public synchronized BoardActiveScreen getBoardActiveScreen(RoboRallyLauncher roboRallyLauncher) {
        if (this.boardActiveScreen == null) {
            this.boardActiveScreen = new BoardActiveScreen(roboRallyLauncher);
        }
        return boardActiveScreen;
    }
}
