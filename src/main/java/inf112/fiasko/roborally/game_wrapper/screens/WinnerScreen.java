package inf112.fiasko.roborally.game_wrapper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.fiasko.roborally.game_wrapper.RoboRallyWrapper;
import inf112.fiasko.roborally.game_wrapper.SimpleButton;

public class WinnerScreen extends AbstractScreen {
    private final RoboRallyWrapper roboRallyWrapper;

    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;

    /**
     * Instantiates a new winner screen
     * @param roboRallyWrapper The Robo Rally wrapper which is parent of this screen
     */
    public WinnerScreen(final RoboRallyWrapper roboRallyWrapper) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(applicationWidth, applicationHeight, camera);
        stage = new Stage();
        stage.setViewport(viewport);
        TextButton quitButton = new SimpleButton("Quit", roboRallyWrapper.font).getButton();
        stage.addActor(quitButton);
        quitButton.setY(applicationHeight / 2f);
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        quitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });
        quitButton.setX(applicationWidth / 2f + quitButton.getWidth()/2);
        this.roboRallyWrapper = roboRallyWrapper;
        camera.setToOrtho(false, applicationWidth, applicationHeight);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);

        roboRallyWrapper.batch.begin();
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "The winner is: " +
                        roboRallyWrapper.roboRallyGame.getWinningPlayerName(),applicationWidth / 2f - 380 / 2f,
                applicationHeight / 2f + 100,380, 1,
                true);
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "Click the button to exit the game",
                applicationWidth / 2f - 380 / 2f,applicationHeight / 2f + 100,380, 1,
                true);
        roboRallyWrapper.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

}