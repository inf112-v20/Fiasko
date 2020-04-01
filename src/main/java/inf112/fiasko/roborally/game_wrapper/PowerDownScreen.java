package inf112.fiasko.roborally.game_wrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PowerDownScreen extends AbstractScreen {
    private final RoboRallyWrapper roboRallyWrapper;

    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private long startTime;
    private final int applicationWidth = 600;
    private final int applicationHeight = 800;
    private Boolean buttonPressed = false;
    public PowerDownScreen(final RoboRallyWrapper roboRallyWrapper) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(applicationWidth, applicationHeight, camera);
        stage = new Stage();
        TextButton powerDownButton = new SimpleButton("PowerDown", roboRallyWrapper.font).getButton();
        stage.addActor(powerDownButton);
        powerDownButton.setY(applicationHeight / 2f);
        powerDownButton.setX(applicationWidth / 2f + powerDownButton.getWidth() / 4f);
        this.roboRallyWrapper = roboRallyWrapper;
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        Gdx.input.setInputProcessor(stage);
        startTime = System.currentTimeMillis();
        powerDownButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                buttonPressed = true;
                return true;//her we do stuff
            }
        });
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);

        int elapsedTime = (int)Math.floor((System.currentTimeMillis() - startTime) / 1000f);

        roboRallyWrapper.batch.begin();
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "Click the button to enter Power Down next round",
                applicationWidth/2f-380/2f,applicationHeight/2f + 100,380, 1, true);
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, String.valueOf(10 - elapsedTime),
                applicationWidth / 2f - 40 / 2f,applicationHeight/2f - 100,40, 1, true);
        roboRallyWrapper.batch.end();
        stage.draw();

        if (elapsedTime > 10) {
            roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getMainMenuScreen(this.roboRallyWrapper));
            dispose();
        } else if (buttonPressed) {
            roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getLoadingScreen(this.roboRallyWrapper));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

}
