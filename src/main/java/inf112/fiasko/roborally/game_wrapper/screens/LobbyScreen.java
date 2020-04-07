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
import inf112.fiasko.roborally.networking.containers.GameStartInfo;
import inf112.fiasko.roborally.objects.Player;
import inf112.fiasko.roborally.utility.IOUtil;

import java.util.List;

public class LobbyScreen extends AbstractScreen {
    private final RoboRallyWrapper roboRallyWrapper;

    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;

    public LobbyScreen(final RoboRallyWrapper roboRallyWrapper) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(applicationWidth, applicationHeight, camera);
        stage = new Stage();
        TextButton startGameButton= new SimpleButton("Start", roboRallyWrapper.font).getButton();
        stage.addActor(startGameButton);
        startGameButton.setY(applicationHeight / 2f);
        startGameButton.setX(applicationWidth / 2f + startGameButton.getWidth() / 4f);
        this.roboRallyWrapper = roboRallyWrapper;
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        Gdx.input.setInputProcessor(stage);
        startGameButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                List<Player> playerlist = IOUtil.playerGenerator(roboRallyWrapper.server.getPlayerNames(),
                        roboRallyWrapper.server.getRobotID());
                roboRallyWrapper.server.sendToAllClients(new GameStartInfo("Checkmate.txt",playerlist));
                roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getLoadingScreen(roboRallyWrapper));
                return true;//her we do stuff
            }
        });
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);

        roboRallyWrapper.batch.begin();
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "Click the button to start game",
                applicationWidth / 2f - 380 / 2f,applicationHeight / 2f + 100,380, 1,
                true);
        roboRallyWrapper.batch.end();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

}
