package inf112.fiasko.roborally.game_wrapper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.fiasko.roborally.game_wrapper.RoboRallyWrapper;

/**
 * This screen is used to wait for something
 */
public class LoadingScreen extends AbstractScreen {
    private final RoboRallyWrapper roboRallyWrapper;

    private final OrthographicCamera camera;
    private final Viewport viewport;

    private long startTime;

    /**
     * Instantiates a new loading screen
     * @param roboRallyWrapper The Robo Rally wrapper which is parent of this screen
     */
    public LoadingScreen(final RoboRallyWrapper roboRallyWrapper) {
        this.roboRallyWrapper = roboRallyWrapper;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        viewport = new ExtendViewport(applicationWidth, applicationHeight, camera);
        startTime = System.currentTimeMillis();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);

        roboRallyWrapper.batch.begin();
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "Loading...", applicationWidth/2f-380/2f,
                applicationHeight / 2f,380, 1, true);
        roboRallyWrapper.batch.end();
        long time = System.currentTimeMillis();
        //TODO: Allow to set any condition and next screen
        if (time - startTime > 5000){
            roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getBoardActiveScreen(this.roboRallyWrapper));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show(){
        startTime = System.currentTimeMillis();
    }

}
