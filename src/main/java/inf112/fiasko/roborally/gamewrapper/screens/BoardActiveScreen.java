package inf112.fiasko.roborally.gamewrapper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.fiasko.roborally.elementproperties.Direction;
import inf112.fiasko.roborally.elementproperties.GameState;
import inf112.fiasko.roborally.elementproperties.RobotID;
import inf112.fiasko.roborally.elementproperties.TileType;
import inf112.fiasko.roborally.gamewrapper.RoboRallyWrapper;
import inf112.fiasko.roborally.objects.DrawableObject;
import inf112.fiasko.roborally.objects.Player;
import inf112.fiasko.roborally.objects.Robot;
import inf112.fiasko.roborally.objects.Tile;
import inf112.fiasko.roborally.utility.IOUtil;
import inf112.fiasko.roborally.utility.TextureConverterUtil;

import java.util.List;

/**
 * This screen shows the game board in real time
 */
public class BoardActiveScreen extends AbstractScreen implements InputProcessor {
    private final RoboRallyWrapper roboRallyWrapper;
    private final OrthographicCamera camera;
    private final int tileDimensions = 64;
    private final int viewPortWidth = 12 * tileDimensions;
    private final int viewPortHeight = 12 * tileDimensions;
    private final Viewport viewport;
    private float cameraZoom = 1;
    private int cameraX = 0;
    private int cameraY = 0;
    private Vector2 lastTouch;

    /**
     * Instantiates a new board active screen
     *
     * @param roboRallyWrapper The Robo Rally wrapper which is parent of this screen
     */
    public BoardActiveScreen(final RoboRallyWrapper roboRallyWrapper) {
        this.roboRallyWrapper = roboRallyWrapper;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewPortWidth, viewPortHeight);
        camera.position.set(viewPortWidth / 2f, viewPortHeight / 2f, 0);
        viewport = new ExtendViewport(viewPortWidth, viewPortHeight, camera);

        lastTouch = new Vector2();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        resetCamera();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        updateCamera();
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);
        roboRallyWrapper.batch.begin();
        drawBoard(roboRallyWrapper.batch);
        roboRallyWrapper.batch.end();

        switch (roboRallyWrapper.roboRallyGame.getGameState()) {
            case GAME_IS_WON:
                roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getWinnerScreen(roboRallyWrapper));
                break;
            case CHOOSING_STAY_IN_POWER_DOWN:
                roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getPowerDownScreen(roboRallyWrapper));
                break;
            case SKIP_STAY_IN_POWER_DOWN:
                roboRallyWrapper.client.sendElement(false);
                roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getLoadingScreen(roboRallyWrapper));
                break;
            default:
                //Ignored
                break;
        }
    }

    @Override
    public void dispose() {
        for (Disposable disposable : TextureConverterUtil.getDisposableElements()) {
            disposable.dispose();
        }
    }

    @Override
    public boolean keyDown(int keyCode) {
        return false;
    }

    @Override
    public boolean keyUp(int keyCode) {
        if (keyCode == Input.Keys.TAB && roboRallyWrapper.roboRallyGame.getGameState() == GameState.CHOOSING_CARDS) {
            roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getCardChoiceScreen(roboRallyWrapper));
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if (character == 'r') {
            //camera.rotate(-90);
            camera.rotateAround(
                    new Vector3(viewPortWidth / 2f, viewPortHeight / 2f, 0),
                    new Vector3(0, 0, 1), 90);
            return true;
        } else if (character == 'q') {
            resetCamera();
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        lastTouch = new Vector2(screenX, screenY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        lastTouch = new Vector2(screenX, screenY);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 newTouch = new Vector2(screenX, screenY);
        Vector2 diff = newTouch.cpy().sub(lastTouch);
        lastTouch = newTouch;
        int[] positionChange = translateCoordinateAccountingForCameraRotation(diff.x, diff.y);
        cameraX = (int) (positionChange[0] * cameraZoom);
        cameraY = (int) (positionChange[1] * cameraZoom);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if (amount < 0 && cameraZoom > 0.3 || amount > 0 && cameraZoom < 3) {
            cameraZoom += 0.2 * amount;
        }
        return true;
    }

    /**
     * Resets the camera to its initial position
     */
    private void resetCamera() {
        camera.up.x = 0;
        camera.up.y = 1;
        cameraZoom = 1.4f;
        camera.position.set(viewPortWidth / 2f + 128, viewPortHeight / 2f + 128, 0);
    }

    /**
     * Renders all drawable objects on the board
     *
     * @param batch The sprite batch to use for drawing
     */
    private void drawBoard(SpriteBatch batch) {
        List<DrawableObject> elementsToDraw =
                IOUtil.getDrawableObjectsFromGame(roboRallyWrapper.roboRallyGame, tileDimensions, tileDimensions);
        for (DrawableObject object : elementsToDraw) {
            TextureRegion objectTextureRegion = object.getTexture();
            batch.draw(objectTextureRegion.getTexture(), object.getXPosition(), object.getYPosition(),
                    (float) object.getWidth() / 2, (float) object.getHeight() / 2,
                    object.getWidth(), object.getHeight(), 1, 1, object.getRotation(),
                    objectTextureRegion.getRegionX(), objectTextureRegion.getRegionY(),
                    objectTextureRegion.getRegionWidth(), objectTextureRegion.getRegionHeight(),
                    object.flipX(), object.flipY());
        }
        int index = 1;
        for (Player player : roboRallyWrapper.roboRallyGame.getPlayers()) {
            String playerName = player.getName();
            Robot robot = getPlayersRobot(player.getRobotID());
            if (robot == null) {
                throw new NullPointerException("Player's robot does not exist.");
            }
            roboRallyWrapper.font.draw(batch, playerName, viewPortWidth, 128 * index);
            roboRallyWrapper.font.draw(batch, "DMG: " + robot.getDamageTaken() + " LV: " + robot.getAmountOfLives(),
                    viewPortWidth, 96 + 128 * (index - 1));
            int lastFlagVisited = robot.getLastFlagVisited();
            if (lastFlagVisited > 0) {
                TileType flagType = TileType.getTileTypeFromID(robot.getLastFlagVisited() + 16);
                TextureRegion flagRegion = TextureConverterUtil.convertElement(new Tile(flagType, Direction.NORTH));
                batch.draw(flagRegion.getTexture(), viewPortWidth + 64, 128 * (index - 1), 64 / 2,
                        64 / 2, 64, 64, 1, 1, 0, flagRegion.getRegionX(),
                        flagRegion.getRegionY(), flagRegion.getRegionWidth(), flagRegion.getRegionWidth(),
                        false, false);
            }
            TextureRegion robotTexture = TextureConverterUtil.convertElement(player.getRobotID());
            batch.draw(robotTexture, viewPortWidth, 128 * (index - 1));
            index++;
        }

    }

    /**
     * Gets the robot with the corresponding robot id
     *
     * @param robotID The robot id to get robot for
     * @return The robot with the robot id
     */
    private Robot getPlayersRobot(RobotID robotID) {
        for (Robot robot : roboRallyWrapper.roboRallyGame.getAllRobots()) {
            if (robot.getRobotId() == robotID) {
                return robot;
            }
        }
        return null;
    }

    /**
     * Updates the camera according to any user input
     */
    private void updateCamera() {
        camera.translate(cameraX, cameraY);
        cameraX = 0;
        cameraY = 0;
        camera.zoom = cameraZoom;
        camera.update();
    }

    /**
     * Translates x and y coordinates according to the camera's direction
     *
     * @param x The x coordinate to translate
     * @param y The y coordinate to translate
     * @return A list containing the translated coordinates of x and y
     */
    private int[] translateCoordinateAccountingForCameraRotation(float x, float y) {
        int outX = 0;
        int outY = 0;
        int cameraUpX = Math.round(camera.up.x);
        int cameraUpY = Math.round(camera.up.y);
        if (cameraUpX == 0 && Math.round(camera.up.y) == 1) {
            outX = (int) -x;
            outY = (int) y;
        } else if (cameraUpX == 0 && cameraUpY == -1) {
            outX = (int) x;
            outY = (int) -y;
        } else if (cameraUpX == -1 && cameraUpY == 0) {
            outX = (int) -y;
            outY = (int) -x;
        } else if (cameraUpX == 1 && cameraUpY == 0) {
            outX = (int) y;
            outY = (int) x;
        }
        return new int[]{outX, outY};
    }
}
