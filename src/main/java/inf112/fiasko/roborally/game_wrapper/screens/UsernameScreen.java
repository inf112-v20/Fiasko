package inf112.fiasko.roborally.game_wrapper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.fiasko.roborally.game_wrapper.RoboRallyWrapper;

/**
 * This screen allows a user to choose their player name
 */
public class UsernameScreen extends AbstractScreen {
    private final RoboRallyWrapper roboRallyWrapper;

    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private TextField textInput;

    /**
     * Instantiates a new username screen
     * @param roboRallyWrapper The Robo Rally wrapper which is parent of this screen
     */
    public UsernameScreen(final RoboRallyWrapper roboRallyWrapper) {
        stage = new Stage();

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        TextButton confirm = new TextButton("Confirm", skin);
        confirm.setSize(300,60);
        confirm.setPosition(300,300);
        confirm.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                String userName = textInput.getText();
                if (nameInvalid(userName)) {
                    return;
                }
                roboRallyWrapper.client.sendElement(userName);
                if (roboRallyWrapper.server == null) {
                    roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getLoadingScreen(roboRallyWrapper));
                } else{
                    roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getLobbyScreen(roboRallyWrapper));
                }
            }
        });
        textInput = new TextField("",skin);
        textInput.setPosition(300,250);
        textInput.setSize(150,40);
        stage.addActor(textInput);
        stage.addActor(confirm);

        camera = new OrthographicCamera();
        viewport = new FitViewport(applicationWidth, applicationHeight, camera);
        this.roboRallyWrapper = roboRallyWrapper;
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        stage.setViewport(viewport);
    }

    /**
     * Checks whether the username is invalid
     * @param userName The username the user wants
     * @return False if the username can be used
     */
    private boolean nameInvalid(String userName) {
        //TODO: Find a way to ask the server if the name is taken
        return userName.equals("");
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
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "Click the button to enter username",
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
