package inf112.fiasko.roborally.gamewrapper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.fiasko.roborally.elementproperties.GameState;
import inf112.fiasko.roborally.gamewrapper.RoboRallyWrapper;

/**
 * This screen is used to wait for something
 */
public class LoadingScreen extends AbstractScreen {
    private final RoboRallyWrapper roboRallyWrapper;

    private final OrthographicCamera camera;
    private final Viewport viewport;

    /**
     * Instantiates a new loading screen
     * @param roboRallyWrapper The Robo Rally wrapper which is parent of this screen
     */
    public LoadingScreen(final RoboRallyWrapper roboRallyWrapper) {
        this.roboRallyWrapper = roboRallyWrapper;
        camera = new OrthographicCamera();
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
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "Loading...", applicationWidth / 2f - 380 / 2f,
                applicationHeight / 2f,380, 1, true);
        roboRallyWrapper.batch.end();

        if (roboRallyWrapper.roboRallyGame != null) {
            GameState gameState = roboRallyWrapper.roboRallyGame.getGameState();
            if (gameState != GameState.LOADING) {
                handleScreenChange(gameState);
            }
        }
    }

    /**
     * Changes to another screen depending on which state the game is in
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
                roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getCardChoiceScreen(this.roboRallyWrapper));
                break; }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

}
