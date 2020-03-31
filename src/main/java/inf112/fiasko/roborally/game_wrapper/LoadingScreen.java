package inf112.fiasko.roborally.game_wrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadingScreen implements Screen {
    private final RoboRallyWrapper roboRallyWrapper;

    private final OrthographicCamera camera;
    private final Viewport viewport;

    private long startTime;
    private final int applicationWidth = 600;
    private final int applicationHeight = 800;

    public LoadingScreen(final RoboRallyWrapper roboRallyWrapper) {
        this.roboRallyWrapper = roboRallyWrapper;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        viewport = new ExtendViewport(applicationWidth, applicationHeight, camera);
        startTime = System.currentTimeMillis();
    }

    @Override
    public void show() {
        //Nothing to do
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);

        roboRallyWrapper.batch.begin();
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "Loading...", applicationWidth/2f-380/2f,
                applicationHeight/2f,380, 1, true);
        roboRallyWrapper.batch.end();
        long time = System.currentTimeMillis();
        if (time-startTime>10000){
            roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getMainMenuScreen(this.roboRallyWrapper));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        //Nothing to do
    }

    @Override
    public void resume() {
        //Nothing to do
    }

    @Override
    public void hide() {
        //Nothing to do
    }

    @Override
    public void dispose() {
        //Nothing to do
    }
}
