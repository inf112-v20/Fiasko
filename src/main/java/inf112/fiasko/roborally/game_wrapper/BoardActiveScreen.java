package inf112.fiasko.roborally.game_wrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.fiasko.roborally.objects.IDrawableGame;
import inf112.fiasko.roborally.objects.IDrawableObject;
import inf112.fiasko.roborally.objects.RoboRallyGame;
import inf112.fiasko.roborally.utility.IOUtil;
import inf112.fiasko.roborally.utility.TextureConverterUtil;

import java.util.List;

public class BoardActiveScreen extends AbstractScreen implements InputProcessor {
    private final RoboRallyWrapper roboRallyWrapper;
    private final OrthographicCamera camera;
    private IDrawableGame debugGame;

    private final int tileDimensions = 64;
    private float cameraZoom = 1;
    private int cameraX = 0;
    private int cameraY = 0;
    private Vector2 lastTouch;
    private final int viewPortWidth = 12 * tileDimensions;
    private final int viewPortHeight = 12 * tileDimensions;
    private final Viewport viewport;

    public BoardActiveScreen(final RoboRallyWrapper roboRallyWrapper) {
        this.roboRallyWrapper = roboRallyWrapper;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewPortWidth, viewPortHeight);
        camera.position.set(viewPortWidth/2f, viewPortHeight/2f, 0);
        viewport = new ExtendViewport(viewPortWidth, viewPortHeight, camera);

        Gdx.input.setInputProcessor(this);
        lastTouch = new Vector2();
    }

    @Override
    public void show() {
        resetCamera();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        updateCamera();
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);
        roboRallyWrapper.batch.begin();
        drawBoard(roboRallyWrapper.batch);
        roboRallyWrapper.batch.end();
    }

    @Override
    public void dispose() {
        for (Disposable disposable : TextureConverterUtil.getDisposableElements()) {
            disposable.dispose();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.HOME) {
            IDrawableGame temp = roboRallyWrapper.roboRallyGame;
            roboRallyWrapper.roboRallyGame = debugGame;
            this.debugGame = temp;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if (character == 'r') {
            //camera.rotate(-90);
            camera.rotateAround(
                    new Vector3(viewPortWidth/2f, viewPortHeight/2f, 0),
                    new Vector3(0, 0, 1), 90);
            return true;
        } else if (character == 'q') {
            resetCamera();
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
        cameraX = (int)(positionChange[0] * cameraZoom);
        cameraY = (int)(positionChange[1] * cameraZoom);
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
        cameraZoom = 1;
        camera.position.set(viewPortWidth/2f, viewPortHeight/2f, 0);
    }

    /**
     * Renders all drawable objects on the board
     * @param batch The sprite batch to use for drawing
     */
    private void drawBoard(SpriteBatch batch) {
        List<IDrawableObject> elementsToDraw =
                IOUtil.getDrawableObjectsFromGame(roboRallyWrapper.roboRallyGame, tileDimensions, tileDimensions);
        for (IDrawableObject object : elementsToDraw) {
            TextureRegion objectTextureRegion = object.getTexture();
            batch.draw(objectTextureRegion.getTexture(), object.getXPosition(), object.getYPosition(),
                    (float)object.getWidth()/2, (float)object.getHeight()/2,
                    object.getWidth(), object.getHeight(), 1, 1, object.getRotation(),
                    objectTextureRegion.getRegionX(), objectTextureRegion.getRegionY(),
                    objectTextureRegion.getRegionWidth(), objectTextureRegion.getRegionHeight(),
                    object.flipX(), object.flipY());
        }
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
            outX = (int)-x;
            outY = (int)y;
        } else if (cameraUpX == 0 && cameraUpY == -1) {
            outX = (int)x;
            outY = (int)-y;
        } else if (cameraUpX == -1 && cameraUpY == 0) {
            outX = (int)-y;
            outY = (int)-x;
        } else if (cameraUpX == 1 && cameraUpY == 0) {
            outX = (int)y;
            outY = (int)x;
        }
        return new int[]{outX, outY};
    }
}
