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
import inf112.fiasko.roborally.gamewrapper.RoboRallyWrapper;
import inf112.fiasko.roborally.gamewrapper.SimpleButton;
import inf112.fiasko.roborally.networking.containers.GameStartInfo;
import inf112.fiasko.roborally.objects.Player;
import inf112.fiasko.roborally.utility.IOUtil;
import com.esotericsoftware.kryonet.Connection;

import java.util.List;
import java.util.Map;

/**
 * This screen allows the host to wait for players to join
 */
public class LobbyScreen extends AbstractScreen {
    private final RoboRallyWrapper roboRallyWrapper;

    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;

    /**
     * Instantiates a new lobby screen
     * @param roboRallyWrapper The Robo Rally wrapper which is parent of this screen
     */
    public LobbyScreen(final RoboRallyWrapper roboRallyWrapper) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(applicationWidth, applicationHeight, camera);
        stage = new Stage();
        TextButton startGameButton= new SimpleButton("Start", roboRallyWrapper.font).getButton();
        stage.addActor(startGameButton);
        startGameButton.setY(applicationHeight / 2f-50 );
        startGameButton.setX(applicationWidth / 2f - startGameButton.getWidth() / 2f);
        this.roboRallyWrapper = roboRallyWrapper;
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        startGameButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Map<Connection,String> playernames = roboRallyWrapper.server.getPlayerNames();
                List<Player> playerlist = IOUtil.playerGenerator(playernames,
                        roboRallyWrapper.server.getRobotID());
                for (Connection connection:playernames.keySet()) {
                    roboRallyWrapper.server.sendToClient(connection,new GameStartInfo("Checkmate.txt"
                            ,playerlist,playernames.get(connection)));
                }
                roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getLoadingScreen(roboRallyWrapper));
                return true;
            }
        });
        stage.setViewport(viewport);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);

        roboRallyWrapper.batch.begin();
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "Joined players: " +
                        roboRallyWrapper.server.getPlayerNames().values().toString(),
                applicationWidth / 2f - 380 / 2f,applicationHeight / 2f + 350,380, 1,
                true);
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
