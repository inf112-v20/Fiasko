package inf112.fiasko.roborally.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.esotericsoftware.kryonet.Connection;
import inf112.fiasko.roborally.networking.containers.GameStartInfoResponse;
import inf112.fiasko.roborally.objects.Player;
import inf112.fiasko.roborally.ui.RoboRallyWrapper;
import inf112.fiasko.roborally.ui.SimpleButton;
import inf112.fiasko.roborally.utility.BoardLoaderUtil;
import inf112.fiasko.roborally.utility.IOUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This screen allows the host to wait for players to join
 */
public class LobbyScreen extends InteractiveScreen {
    private final SelectBox<String> selectBox;
    private final RoboRallyWrapper roboRallyWrapper;

    /**
     * Instantiates a new lobby screen
     *
     * @param roboRallyWrapper The Robo Rally wrapper which is parent of this screen
     */
    public LobbyScreen(final RoboRallyWrapper roboRallyWrapper) {
        viewport = new FitViewport(applicationWidth, applicationHeight, camera);
        TextButton startGameButton = new SimpleButton("Start", roboRallyWrapper.font).getButton();
        stage.addActor(startGameButton);
        startGameButton.setY(applicationHeight / 2f - 50);
        startGameButton.setX(applicationWidth / 2f - startGameButton.getWidth() / 2f);
        this.roboRallyWrapper = roboRallyWrapper;
        camera.setToOrtho(false, applicationWidth, applicationHeight);


        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        selectBox = new SelectBox<>(skin);
        try {
            selectBox.setItems(BoardLoaderUtil.getBoardListHumanReadable());
        } catch (IOException e) {
            e.printStackTrace();
        }
        selectBox.setSize(200, 50);
        selectBox.setPosition((applicationWidth - selectBox.getWidth()) / 2f, applicationHeight / 2f - 120);

        stage.addActor(selectBox);

        startGameButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                startGame();
            }
        });
        Gdx.input.setInputProcessor(stage);
        stage.setViewport(viewport);
    }

    /**
     * Makes the server send information to players about the selected map and connected players
     */
    private void startGame() {
        roboRallyWrapper.server.startGame();
        Map<Connection, String> playerNames = roboRallyWrapper.server.getPlayerNames();
        List<Player> playerList = IOUtil.playerGenerator(playerNames,
                roboRallyWrapper.server.getRobotID());
        for (Connection connection : playerNames.keySet()) {
            roboRallyWrapper.server.sendToClient(connection, new GameStartInfoResponse(
                    BoardLoaderUtil.getRealBoardName(selectBox.getSelected()), playerList, playerNames.get(connection)));
        }
        roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getLoadingScreen(roboRallyWrapper));
    }

    @Override
    public void show() {
        super.show();
        inputMultiplexer.addProcessor(this);
    }

    @Override
    public boolean keyUp(int keyCode) {
        if (keyCode == Input.Keys.HOME) {
            Array<String> items = selectBox.getItems();
            String testBoard = "all_tiles_test_board";
            if (!items.contains(testBoard, true)) {
                items.add(testBoard);
                selectBox.setItems(items);
                selectBox.setSelected(testBoard);
            }
            return true;
        } else if (keyCode == Input.Keys.T) {
            roboRallyWrapper.isTesting = true;
            String testBoard = "manual_testing";
            selectBox.getItems().add(testBoard);
            selectBox.setSelected(testBoard);
            startGame();
        }
        return false;
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
                applicationWidth / 2f - 380 / 2f, applicationHeight / 2f + 350, 380, 1,
                true);
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "Click the button to start game",
                applicationWidth / 2f - 380 / 2f, applicationHeight / 2f + 100, 380, 1,
                true);
        roboRallyWrapper.batch.end();
        stage.draw();
        stage.act();
    }

}
