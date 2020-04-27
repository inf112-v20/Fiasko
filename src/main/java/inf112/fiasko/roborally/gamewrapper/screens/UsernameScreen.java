package inf112.fiasko.roborally.gamewrapper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.fiasko.roborally.gamewrapper.RoboRallyWrapper;
import inf112.fiasko.roborally.networking.RequestState;
import inf112.fiasko.roborally.networking.containers.UsernameRequest;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

/**
 * This screen allows a user to choose their player name
 */
public class UsernameScreen extends AbstractScreen {
    private final RoboRallyWrapper roboRallyWrapper;

    private TextField textInput;

    /**
     * Instantiates a new username screen
     *
     * @param roboRallyWrapper The Robo Rally wrapper which is parent of this screen
     */
    public UsernameScreen(final RoboRallyWrapper roboRallyWrapper) {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        TextButton confirm = new TextButton("Confirm", skin);
        confirm.setSize(300, 60);
        confirm.setPosition(applicationWidth / 2f - confirm.getWidth() / 2, 300);
        confirm.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                if (nameInvalid(textInput.getText())) {
                    JOptionPane.showMessageDialog(null, "Username must be unique, not " +
                            "empty and less than 21 characters.");
                    return;
                }
                if (roboRallyWrapper.server == null) {
                    roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getLoadingScreen(roboRallyWrapper));
                } else {
                    roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getLobbyScreen(roboRallyWrapper));
                }

            }
        });
        textInput = new TextField("", skin);
        textInput.setPosition(applicationWidth / 2f - textInput.getWidth() / 2, 250);
        textInput.setSize(150, 40);
        stage.addActor(textInput);
        stage.addActor(confirm);

        viewport = new FitViewport(applicationWidth, applicationHeight, camera);
        this.roboRallyWrapper = roboRallyWrapper;
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        stage.setViewport(viewport);
    }

    /**
     * Checks whether the username is invalid
     *
     * @param userName The username the user wants
     * @return False if the username can be used
     */
    private boolean nameInvalid(String userName) {
        if ("".equals(userName) || userName.length() > 20) {
            return true;
        }
        UsernameRequest usernameRequest = new UsernameRequest(userName);
        roboRallyWrapper.client.sendElement(usernameRequest);
        while (roboRallyWrapper.client.getLastRequestState() == RequestState.SENT_NOT_RECEIVED) {
            //Wait for the server to respond
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return roboRallyWrapper.client.getLastRequestState() == RequestState.SENT_REJECTED;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);

        roboRallyWrapper.batch.begin();
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "Click the button to enter username",
                applicationWidth / 2f - 380 / 2f, applicationHeight / 2f + 100, 380, 1,
                true);
        roboRallyWrapper.batch.end();
    }

}
