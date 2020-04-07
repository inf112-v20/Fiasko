package inf112.fiasko.roborally.game_wrapper;

import inf112.fiasko.roborally.game_wrapper.screens.*;

/**
 * Keeps track of screen instances
 */
public class ScreenManager {
    private BoardActiveScreen boardActiveScreen;
    private CardChoiceScreen cardChoiceScreen;
    private PowerDownScreen powerDownScreen;
    private LoadingScreen loadingScreen;
    private UsernameScreen usernameScreen;
    private IPAddressScreen ipAddressScreen;
    private LobbyScreen lobbyScreen;

    /**
     * Gets an instance of the main menu screen
     * @param roboRallyWrapper The robo rally launcher instance to use
     * @return A main menu screen instance
     */
    public synchronized PowerDownScreen getPowerDownScreen(RoboRallyWrapper roboRallyWrapper) {
        if (this.powerDownScreen == null) {
            this.powerDownScreen = new PowerDownScreen(roboRallyWrapper);
        }
        return powerDownScreen;
    }
    public synchronized LobbyScreen getLobbyScreen(RoboRallyWrapper roboRallyWrapper) {
        if (this.lobbyScreen == null) {
            this.lobbyScreen = new LobbyScreen(roboRallyWrapper);
        }
        return lobbyScreen;
    }

    public synchronized IPAddressScreen getIPAddressScreen(RoboRallyWrapper roboRallyWrapper) {
        if (this.ipAddressScreen == null) {
            this.ipAddressScreen = new IPAddressScreen(roboRallyWrapper);
        }
        return ipAddressScreen;
    }

    public synchronized UsernameScreen getUsernameScreen(RoboRallyWrapper roboRallyWrapper) {
        if (this.usernameScreen == null) {
            this.usernameScreen = new UsernameScreen(roboRallyWrapper);
        }
        return usernameScreen;
    }

    synchronized StartMenuScreen getStartMenuScreen(RoboRallyWrapper roboRallyWrapper) {
        return new StartMenuScreen(roboRallyWrapper);
    }

    public synchronized LoadingScreen getLoadingScreen(RoboRallyWrapper roboRallyWrapper) {
        if (this.loadingScreen == null) {
            this.loadingScreen = new LoadingScreen(roboRallyWrapper);
        }
        return loadingScreen;
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
