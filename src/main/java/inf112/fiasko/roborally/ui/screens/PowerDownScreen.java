package inf112.fiasko.roborally.ui.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.fiasko.roborally.objects.properties.GameState;
import inf112.fiasko.roborally.ui.RoboRallyWrapper;
import inf112.fiasko.roborally.ui.SimpleButton;

/**
 * This screen is used for asking players whether they want to power down
 */
public class PowerDownScreen extends AbstractScreen {
    private final RoboRallyWrapper roboRallyWrapper;

    private long startTime;

    /**
     * Instantiates a new power down screen
     *
     * @param roboRallyWrapper The Robo Rally wrapper which is parent of this screen
     */
    public PowerDownScreen(final RoboRallyWrapper roboRallyWrapper) {
        viewport = new FitViewport(applicationWidth, applicationHeight, camera);
        stage.setViewport(viewport);
        TextButton powerDownButton = new SimpleButton("PowerDown", roboRallyWrapper.font).getButton();
        stage.addActor(powerDownButton);
        powerDownButton.setY(applicationHeight / 2f - 50);
        powerDownButton.setX(applicationWidth / 2f - powerDownButton.getWidth() / 2f);
        this.roboRallyWrapper = roboRallyWrapper;
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        powerDownButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                sendPowerDownStatus(true);
            }
        });
    }

    @Override
    public void show() {
        super.show();
        startTime = System.currentTimeMillis();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);
        int elapsedTime = (int) Math.floor((System.currentTimeMillis() - startTime) / 1000f);

        roboRallyWrapper.batch.begin();

        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "Click the button to continue your power down the" +
                        " next turn",
                applicationWidth / 2f - 380 / 2f, applicationHeight / 2f + 100, 380, 1,
                true);
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, String.valueOf(5 - elapsedTime),
                applicationWidth / 2f - 40 / 2f, applicationHeight / 2f - 100, 40, 1,
                true);
        roboRallyWrapper.batch.end();

        if (elapsedTime > 5) {
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
            default:
                roboRallyWrapper.quit("The game is in an unexpected state. Cannot continue.");
                throw new IllegalStateException("The game is in an unexpected state. Cannot continue.");
        }
    }

}
