package inf112.fiasko.roborally.gamewrapper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.fiasko.roborally.elementproperties.GameState;
import inf112.fiasko.roborally.gamewrapper.RoboRallyWrapper;
import inf112.fiasko.roborally.gamewrapper.SimpleButton;
import inf112.fiasko.roborally.networking.containers.ProgramAndPowerdownRequest;

/**
 * This screen is used for asking players whether they want to power down
 */
public class PowerDownScreen extends AbstractScreen {
    private final RoboRallyWrapper roboRallyWrapper;

    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private long startTime;

    /**
     * Instantiates a new power down screen
     *
     * @param roboRallyWrapper The Robo Rally wrapper which is parent of this screen
     */
    public PowerDownScreen(final RoboRallyWrapper roboRallyWrapper) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(applicationWidth, applicationHeight, camera);
        stage = new Stage();
        stage.setViewport(viewport);
        TextButton powerDownButton = new SimpleButton("PowerDown", roboRallyWrapper.font).getButton();
        stage.addActor(powerDownButton);
        powerDownButton.setY(applicationHeight / 2f - 50);
        powerDownButton.setX(applicationWidth / 2f - powerDownButton.getWidth() / 2f);
        this.roboRallyWrapper = roboRallyWrapper;
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        startTime = System.currentTimeMillis();
        powerDownButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sendPowerDownStatus(true);
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);

        int elapsedTime = (int) Math.floor((System.currentTimeMillis() - startTime) / 1000f);

        roboRallyWrapper.batch.begin();
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "Click the button to enter Power Down next round",
                applicationWidth / 2f - 380 / 2f, applicationHeight / 2f + 100, 380, 1,
                true);
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, String.valueOf(10 - elapsedTime),
                applicationWidth / 2f - 40 / 2f, applicationHeight / 2f - 100, 40, 1,
                true);
        roboRallyWrapper.batch.end();
        stage.draw();

        if (elapsedTime > 10) {
            sendPowerDownStatus(Boolean.FALSE);
        }
    }

    /**
     * Sends power down status to the server
     *
     * @param bool Whether the player wants to go/stay in power down
     */
    private void sendPowerDownStatus(boolean bool) {
        switch (roboRallyWrapper.roboRallyGame.getGameState()) {
            case CHOOSING_STAY_IN_POWER_DOWN:
                roboRallyWrapper.roboRallyGame.setGameState(GameState.TURN_CLEANUP);
                roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getLoadingScreen(this.roboRallyWrapper));
                roboRallyWrapper.client.sendElement(bool);
                break;
            case CHOOSING_POWER_DOWN:
                roboRallyWrapper.roboRallyGame.setGameState(GameState.LOADING);
                roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getLoadingScreen(this.roboRallyWrapper));
                roboRallyWrapper.client.sendElement(new ProgramAndPowerdownRequest(bool,
                        roboRallyWrapper.roboRallyGame.getProgram()));
                break;
            default:
                throw new IllegalStateException("The game is in an unexpected state. Cannot continue.");
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        startTime = System.currentTimeMillis();
    }

}
