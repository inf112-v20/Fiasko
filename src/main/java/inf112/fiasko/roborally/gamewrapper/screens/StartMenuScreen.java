package inf112.fiasko.roborally.gamewrapper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.fiasko.roborally.gamewrapper.RoboRallyWrapper;
import inf112.fiasko.roborally.gamewrapper.SimpleButton;
import inf112.fiasko.roborally.networking.RoboRallyClient;
import inf112.fiasko.roborally.networking.RoboRallyServer;

import java.io.IOException;

/**
 * This screen is the first screen shown to a player
 */
public class StartMenuScreen extends AbstractScreen {
    private final RoboRallyWrapper roboRallyWrapper;

    /**
     * Instantiates a new start menu screen
     *
     * @param roboRallyWrapper The Robo Rally wrapper which is parent of this screen
     */
    public StartMenuScreen(final RoboRallyWrapper roboRallyWrapper) {
        viewport = new FitViewport(applicationWidth, applicationHeight, camera);
        stage.setViewport(viewport);
        TextButton serverButton = new SimpleButton("Create", roboRallyWrapper.font).getButton();
        stage.addActor(serverButton);
        serverButton.setY(applicationHeight / 2f);
        this.roboRallyWrapper = roboRallyWrapper;
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        serverButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    roboRallyWrapper.server = new RoboRallyServer(roboRallyWrapper.defaultTCPPort, roboRallyWrapper.discoverUDPPort);
                    roboRallyWrapper.client = new RoboRallyClient(roboRallyWrapper);
                    roboRallyWrapper.client.connect("127.0.0.1", roboRallyWrapper.defaultTCPPort, roboRallyWrapper.discoverUDPPort);
                    roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getUsernameScreen(roboRallyWrapper));
                } catch (IOException e) {
                    //Hard fail
                    roboRallyWrapper.quit("Server could not be started");
                }
            }
        });

        TextButton clientButton = new SimpleButton("Join", roboRallyWrapper.font).getButton();
        stage.addActor(clientButton);
        clientButton.setY(applicationHeight / 2f);
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        Gdx.input.setInputProcessor(stage);
        clientButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getIPAddressScreen(roboRallyWrapper));
            }
        });

        TextButton quitButton = new SimpleButton("Quit", roboRallyWrapper.font).getButton();
        stage.addActor(quitButton);
        quitButton.setY(applicationHeight / 2f);
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        quitButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                roboRallyWrapper.quit();
            }
        });
        serverButton.setX(applicationWidth / 2f - serverButton.getWidth() - clientButton.getWidth() / 2 - 10);
        clientButton.setX(applicationWidth / 2f - clientButton.getWidth() / 2);
        quitButton.setX(applicationWidth / 2f + clientButton.getWidth() / 2 + 10);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);
        roboRallyWrapper.batch.begin();
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "RoboRally",
                applicationWidth / 2f - 380 / 2f, applicationHeight / 2f + 100, 380, 1,
                true);
        roboRallyWrapper.batch.end();
    }
}
