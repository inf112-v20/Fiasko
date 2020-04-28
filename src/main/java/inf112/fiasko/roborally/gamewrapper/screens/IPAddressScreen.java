package inf112.fiasko.roborally.gamewrapper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.fiasko.roborally.gamewrapper.RoboRallyWrapper;
import inf112.fiasko.roborally.networking.RoboRallyClient;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This screen allows the user to enter the ip address to connect to
 */
public class IPAddressScreen extends AbstractScreen {
    private final RoboRallyWrapper roboRallyWrapper;

    private TextField textInput;

    /**
     * Instantiates a new ip address screen
     *
     * @param roboRallyWrapper The Robo Rally wrapper which is parent of this screen
     */
    public IPAddressScreen(final RoboRallyWrapper roboRallyWrapper) {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        TextButton joinButton = new TextButton("Join", skin);
        joinButton.setSize(300, 60);
        joinButton.setPosition(applicationWidth / 2f - joinButton.getWidth() / 2f, 300);
        roboRallyWrapper.client = new RoboRallyClient(roboRallyWrapper);

        List<InetAddress> lanServers = roboRallyWrapper.client.getLanServers(roboRallyWrapper.networkPort);
        Set<String> validHosts = new HashSet<>();
        for (InetAddress address : lanServers) {
            validHosts.add(address.getHostAddress());
        }
        final SelectBox<String> selectBox = new SelectBox<>(skin);
        selectBox.setItems(validHosts.toArray(new String[0]));
        selectBox.setPosition(-80 + (applicationWidth - selectBox.getWidth()) / 2f, 180);
        selectBox.setSize(200, 50);

        stage.addActor(selectBox);


        joinButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                try {
                    String serverIp;
                    int serverPort = roboRallyWrapper.networkPort;
                    String writtenIP = textInput.getText();
                    if (writtenIP.isEmpty()) {
                        serverIp = selectBox.getSelected();
                    } else {
                        serverIp = writtenIP;
                        if (serverIp.contains(":")) {
                            String[] ipParts = serverIp.split(":");
                            serverIp = ipParts[0];
                            serverPort = Integer.parseInt(ipParts[1]);
                        }
                    }
                    roboRallyWrapper.client.connect(serverIp, serverPort);
                    roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getUsernameScreen(roboRallyWrapper));
                } catch (IOException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Could not connect to the server."
                            + " Please make sure the ip address you typed is correct, and that the server is online.");
                }
            }
        });
        textInput = new TextField("", skin);
        textInput.setPosition(applicationWidth / 2f - textInput.getWidth() / 2f, 250);
        textInput.setSize(150, 40);
        stage.addActor(textInput);
        stage.addActor(joinButton);

        viewport = new FitViewport(applicationWidth, applicationHeight, camera);
        this.roboRallyWrapper = roboRallyWrapper;
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        stage.setViewport(viewport);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);

        roboRallyWrapper.batch.begin();
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "Enter or select IP address and click the button to " +
                        "join a server",
                applicationWidth / 2f - 380 / 2f, applicationHeight / 2f + 100, 380, 1,
                true);
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "Specify IP",
                10, 280, 200, 1, true);
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "Local servers",
                10, 230, 200, 1, true);
        roboRallyWrapper.batch.end();
    }

}
