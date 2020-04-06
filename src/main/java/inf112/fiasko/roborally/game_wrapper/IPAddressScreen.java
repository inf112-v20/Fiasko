package inf112.fiasko.roborally.game_wrapper;

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
import inf112.fiasko.roborally.networking.RoboRallyClient;


import javax.swing.*;
import java.io.IOException;

public class IPAddressScreen extends AbstractScreen {
    private final RoboRallyWrapper roboRallyWrapper;

    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private final int applicationWidth = 600;
    private final int applicationHeight = 800;
    private TextField txtinput;
    private TextButton loginbutton;
    public IPAddressScreen(final RoboRallyWrapper roboRallyWrapper) {

        stage = new Stage();

        Skin skin =new Skin(Gdx.files.internal("uiskin.json"));
        loginbutton = new TextButton("click",skin);
        loginbutton.setSize(300,60);
        loginbutton.setPosition(300,300);
        loginbutton.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button){
                try {
                    roboRallyWrapper.client = new RoboRallyClient(txtinput.getText());
                    roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getUsernameScreen(roboRallyWrapper));
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "A basic JOptionPane message dialog");
                }
            }
        });
        txtinput = new TextField("",skin);
        txtinput.setPosition(300,250);
        txtinput.setSize(150,40);
        stage.addActor(txtinput);
        stage.addActor(loginbutton);

        camera = new OrthographicCamera();
        viewport = new FitViewport(applicationWidth, applicationHeight, camera);
        this.roboRallyWrapper = roboRallyWrapper;
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        Gdx.input.setInputProcessor(stage);

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);

        roboRallyWrapper.batch.begin();
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "Click the button to enter IP to join server",
                applicationWidth/2f-380/2f,applicationHeight/2f + 100,380, 1, true);
        roboRallyWrapper.batch.end();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
