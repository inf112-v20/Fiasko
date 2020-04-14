package inf112.fiasko.roborally.gamewrapper;


import inf112.fiasko.roborally.gamewrapper.screens.BoardActiveScreen;
import inf112.fiasko.roborally.gamewrapper.screens.LoadingScreen;
import inf112.fiasko.roborally.gamewrapper.screens.PowerDownScreen;
import inf112.fiasko.roborally.gamewrapper.screens.UsernameScreen;
import inf112.fiasko.roborally.gamewrapper.screens.IPAddressScreen;
import inf112.fiasko.roborally.gamewrapper.screens.LobbyScreen;
import inf112.fiasko.roborally.gamewrapper.screens.StartMenuScreen;

/**
 * Keeps track of screen instances
 */
public class ScreenManager {
    private BoardActiveScreen boardActiveScreen;
    private PowerDownScreen powerDownScreen;
    private LoadingScreen loadingScreen;
    private UsernameScreen usernameScreen;
    private IPAddressScreen ipAddressScreen;
    private LobbyScreen lobbyScreen;

    /**
     * Gets an instance of the power down screen
     * @param roboRallyWrapper The Robo Rally launcher instance to use
     * @return A power down screen instance
     */
    public synchronized PowerDownScreen getPowerDownScreen(RoboRallyWrapper roboRallyWrapper) {
        if (this.powerDownScreen == null) {
            this.powerDownScreen = new PowerDownScreen(roboRallyWrapper);
        }
        return powerDownScreen;
    }

    /**
     * Gets an instance of the lobby screen
     * @param roboRallyWrapper The Robo Rally launcher instance to use
     * @return A lobby screen instance
     */
    public synchronized LobbyScreen getLobbyScreen(RoboRallyWrapper roboRallyWrapper) {
        if (this.lobbyScreen == null) {
            this.lobbyScreen = new LobbyScreen(roboRallyWrapper);
        }
        return lobbyScreen;
    }

    /**
     * Gets an instance of the ip address screen
     * @param roboRallyWrapper The Robo Rally launcher instance to use
     * @return An ip address screen instance
     */
    public synchronized IPAddressScreen getIPAddressScreen(RoboRallyWrapper roboRallyWrapper) {
        if (this.ipAddressScreen == null) {
            this.ipAddressScreen = new IPAddressScreen(roboRallyWrapper);
        }
        return ipAddressScreen;
    }

    /**
     * Gets an instance of the username screen
     * @param roboRallyWrapper The Robo Rally launcher instance to use
     * @return A username screen instance
     */
    public synchronized UsernameScreen getUsernameScreen(RoboRallyWrapper roboRallyWrapper) {
        if (this.usernameScreen == null) {
            this.usernameScreen = new UsernameScreen(roboRallyWrapper);
        }
        return usernameScreen;
    }

    /**
     * Gets an instance of the start menu screen
     * @param roboRallyWrapper The Robo Rally launcher instance to use
     * @return A start menu screen instance
     */
    public synchronized StartMenuScreen getStartMenuScreen(RoboRallyWrapper roboRallyWrapper) {
        return new StartMenuScreen(roboRallyWrapper);
    }

    /**
     * Gets an instance of the loading screen
     * @param roboRallyWrapper The Robo Rally launcher instance to use
     * @return A loading screen instance
     */
    public synchronized LoadingScreen getLoadingScreen(RoboRallyWrapper roboRallyWrapper) {
        if (this.loadingScreen == null) {
            this.loadingScreen = new LoadingScreen(roboRallyWrapper);
        }
        return loadingScreen;
    }

    /**
     * Gets an instance of the board active screen
     * @param roboRallyWrapper The Robo Rally launcher instance to use
     * @return A board active screen instance
     */
    public synchronized BoardActiveScreen getBoardActiveScreen(RoboRallyWrapper roboRallyWrapper) {
        if (this.boardActiveScreen == null) {
            this.boardActiveScreen = new BoardActiveScreen(roboRallyWrapper);
        }
        return boardActiveScreen;
    }


}