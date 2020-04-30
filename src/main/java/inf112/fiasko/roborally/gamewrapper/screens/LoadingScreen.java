package inf112.fiasko.roborally.gamewrapper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import inf112.fiasko.roborally.elementproperties.GameState;
import inf112.fiasko.roborally.gamewrapper.RoboRallyWrapper;
import inf112.fiasko.roborally.networking.containers.ProgramAndPowerdownRequest;

import java.util.ArrayList;

/**
 * This screen is used to wait for something
 */
public class LoadingScreen extends AbstractScreen {
    private final RoboRallyWrapper roboRallyWrapper;

    /**
     * Instantiates a new loading screen
     *
     * @param roboRallyWrapper The Robo Rally wrapper which is parent of this screen
     */
    public LoadingScreen(final RoboRallyWrapper roboRallyWrapper) {
        this.roboRallyWrapper = roboRallyWrapper;
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        viewport = new ExtendViewport(applicationWidth, applicationHeight, camera);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);

        roboRallyWrapper.batch.begin();
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, getLoadingText(), applicationWidth / 2f - 380 / 2f,
                applicationHeight / 2f, 380, 1, true);
        roboRallyWrapper.batch.end();

        if (roboRallyWrapper.roboRallyGame != null) {
            GameState gameState = roboRallyWrapper.roboRallyGame.getGameState();
            handleScreenChange(gameState);
        }
    }

    /**
     * Returns the correct loading text to display according to the game's state
     *
     * @return Text explaining what the game is waiting for
     */
    private String getLoadingText() {
        if (roboRallyWrapper.roboRallyGame == null) {
            if (roboRallyWrapper.server == null) {
                return "Waiting for host to start the game...";
            }
            return "Loading...";
        }
        System.out.println(roboRallyWrapper.roboRallyGame.getGameState());
        switch (roboRallyWrapper.roboRallyGame.getGameState()) {
            case WAITING_FOR_OTHER_PLAYERS_PROGRAMS:
                return "Waiting for other players to finish programming...";
            case WAITING_FOR_CARDS_FROM_SERVER:
                return "Waiting for new cards from the server...";
            case BEGINNING_OF_GAME:
                return "Initializing new turn...";
            case CHOOSING_CARDS:
                return "Waiting for screen change...";
            case SKIP_STAY_IN_POWER_DOWN:
                return "Waiting for players to choose whether to stay in power down...";
            default:
                return "Waiting for something...";
        }

    }

    /**
     * Changes to another screen depending on which state the game is in
     *
     * @param gameState The current state of the game
     */
    private void handleScreenChange(GameState gameState) {
        switch (gameState) {
            case RUNNING_PROGRAMS:
                roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getBoardActiveScreen(this.roboRallyWrapper));
                break;
            case INITIAL_SETUP:
                roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getLoadingScreen(this.roboRallyWrapper));
                break;
            case CHOOSING_CARDS:
                roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getNewCardChoiceScreen(this.roboRallyWrapper));
                break;
            case EXITED:
                roboRallyWrapper.quit("All players died. Cannot continue playing.");
                break;
            case SKIP_POWER_DOWN_SCREEN:
                roboRallyWrapper.roboRallyGame.setGameState(GameState.WAITING_FOR_OTHER_PLAYERS_PROGRAMS);
                roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getLoadingScreen(this.roboRallyWrapper));
                roboRallyWrapper.client.sendElement(new ProgramAndPowerdownRequest(false, new ArrayList<>()));
                break;
            default:
                //Ignored
                break;
        }
    }

}
