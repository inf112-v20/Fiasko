package inf112.fiasko.roborally.gamewrapper.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import inf112.fiasko.roborally.elementproperties.Direction;
import inf112.fiasko.roborally.elementproperties.GameState;
import inf112.fiasko.roborally.elementproperties.RobotID;
import inf112.fiasko.roborally.elementproperties.TileType;
import inf112.fiasko.roborally.gamewrapper.RoboRallyWrapper;
import inf112.fiasko.roborally.objects.DrawableGame;
import inf112.fiasko.roborally.objects.DrawableObject;
import inf112.fiasko.roborally.objects.InteractableGame;
import inf112.fiasko.roborally.objects.Player;
import inf112.fiasko.roborally.objects.Robot;
import inf112.fiasko.roborally.objects.Tile;
import inf112.fiasko.roborally.utility.IOUtil;
import inf112.fiasko.roborally.utility.TextureConverterUtil;

import java.util.List;

/**
 * This screen shows the game board in real time
 */
public class BoardActiveScreen extends InteractiveScreen {
    private final RoboRallyWrapper roboRallyWrapper;
    private final int tileDimensions = 64;
    private final int viewPortWidth;
    private final int viewPortHeight;
    private final DrawableGame drawableGame;
    private final InteractableGame interactableGame;
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
        this.drawableGame = this.roboRallyWrapper.roboRallyGame;
        this.interactableGame = this.roboRallyWrapper.roboRallyGame;

        viewPortWidth = drawableGame.getWidth() * tileDimensions;
        viewPortHeight = drawableGame.getHeight() * tileDimensions;

        camera.setToOrtho(false, viewPortWidth, viewPortHeight);
        camera.position.set(viewPortWidth / 2f, viewPortHeight / 2f, 0);
        viewport = new ExtendViewport(viewPortWidth, viewPortHeight, camera);

        lastTouch = new Vector2();
    }

    @Override
    public void show() {
        super.show();
        resetCamera();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        updateCamera();
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);
        roboRallyWrapper.batch.begin();
        drawBoard(roboRallyWrapper.batch);
        roboRallyWrapper.batch.end();

        switch (interactableGame.getGameState()) {
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
    public boolean keyUp(int keyCode) {
        if (keyCode == Input.Keys.TAB && interactableGame.getGameState() == GameState.CHOOSING_CARDS) {
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
    public boolean scrolled(int amount) {
        if (amount < 0 && cameraZoom > 0.3 || amount > 0 && cameraZoom < 3) {
            cameraZoom += 0.1 * amount;
        }
        return true;
    }

    /**
     * Resets the camera to its initial position
     */
    private void resetCamera() {
        camera.up.x = 0;
        camera.up.y = 1;
        cameraZoom = 1;
        camera.position.set(viewPortWidth / 1.5f, viewPortHeight / 2f, 0);
    }

    /**
     * Renders all drawable objects on the board
     *
     * @param batch The sprite batch to use for drawing
     */
    private void drawBoard(SpriteBatch batch) {
        List<DrawableObject> elementsToDraw =
                IOUtil.getDrawableObjectsFromGame(drawableGame, tileDimensions, tileDimensions);
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
        //Draws all participating players to the right of the board
        for (Player player : drawableGame.getPlayers()) {
            String playerName = player.getName();
            Robot robot = getPlayersRobot(player.getRobotID());
            if (robot == null) {
                throw new IllegalArgumentException("The robot belonging to player " + playerName +
                        " does not exist on the board.");
            }
            roboRallyWrapper.font.getData().setScale(tileDimensions / 44);
            roboRallyWrapper.font.draw(batch, playerName, viewPortWidth, 2 * tileDimensions * index);
            roboRallyWrapper.font.draw(batch, "DMG: " + robot.getDamageTaken() + " LV: " + robot.getAmountOfLives(),
                    viewPortWidth, 1.5f * tileDimensions + 2 * tileDimensions * (index - 1));
            int lastFlagVisited = robot.getLastFlagVisited();
            if (lastFlagVisited > 0) {
                TileType flagType = TileType.getTileTypeFromID(robot.getLastFlagVisited() + 16);
                TextureRegion flagRegion = TextureConverterUtil.convertElement(new Tile(flagType, Direction.NORTH));
                batch.draw(flagRegion.getTexture(), viewPortWidth + tileDimensions, 2 * tileDimensions *
                                (index - 1), tileDimensions / 2, tileDimensions / 2, tileDimensions,
                        tileDimensions, 1, 1, 0, flagRegion.getRegionX(),
                        flagRegion.getRegionY(), flagRegion.getRegionWidth(), flagRegion.getRegionWidth(),
                        false, false);
            }
            TextureRegion robotTexture = TextureConverterUtil.convertElement(player.getRobotID());
            batch.draw(robotTexture, viewPortWidth, 2 * tileDimensions * (index - 1), tileDimensions, tileDimensions);
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
        for (Robot robot : drawableGame.getAllRobots()) {
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
