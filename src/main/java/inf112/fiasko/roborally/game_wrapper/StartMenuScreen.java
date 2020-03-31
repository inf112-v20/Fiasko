package inf112.fiasko.roborally.game_wrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class StartMenuScreen implements Screen {
    private final RoboRallyWrapper roboRallyWrapper;

    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private final int applicationWidth = 600;
    private final int applicationHeight = 800;
    private Boolean press = false;
    public StartMenuScreen(final RoboRallyWrapper roboRallyWrapper) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(applicationWidth, applicationHeight, camera);
        stage = new Stage();

        TextButton serverButton = new SimpleButton("Create", roboRallyWrapper.font).getButton();
        stage.addActor(serverButton);
        serverButton.setY(applicationHeight/2f);
        serverButton.setX(applicationWidth/2f);
        this.roboRallyWrapper = roboRallyWrapper;
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        Gdx.input.setInputProcessor(stage);
        serverButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                press = true;
                return true;//her we do stuff
            }
        });

        TextButton clientButton = new SimpleButton("Join", roboRallyWrapper.font).getButton();
        stage.addActor(clientButton);
        clientButton.setY(applicationHeight/2f);
        clientButton.setX(applicationWidth/2f+serverButton.getWidth()+20);
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        Gdx.input.setInputProcessor(stage);
        clientButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                press = true;
                return true;//her we do stuff
            }
        });

        TextButton quitButton = new SimpleButton("Quit", roboRallyWrapper.font).getButton();
        stage.addActor(quitButton);
        quitButton.setY(applicationHeight/2f);
        quitButton.setX(applicationWidth/2f+serverButton.getWidth()+40+clientButton.getWidth());
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        Gdx.input.setInputProcessor(stage);
        quitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
                return true;//her we do stuff
            }
        });
    }

    @Override
    public void show() {
        //Nothing to do
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);

        roboRallyWrapper.batch.begin();
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "RoboRally",
                applicationWidth/2f-380/2f,applicationHeight/2f +100,380, 1, true);
        roboRallyWrapper.batch.end();
        stage.draw();

        if (press){
            roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getLoadingScreen(this.roboRallyWrapper));
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
