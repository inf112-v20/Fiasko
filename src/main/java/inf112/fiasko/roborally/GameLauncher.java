package inf112.fiasko.roborally;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import inf112.fiasko.roborally.game.Game;
import inf112.fiasko.roborally.game.IDrawableGame;
import inf112.fiasko.roborally.objects.IDrawableObject;
import inf112.fiasko.roborally.utility.IOUtil;

import java.util.List;

/**
 * This class renders a game using libgdx
 */
public class GameLauncher extends ApplicationAdapter implements InputProcessor {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private IDrawableGame game;

    private Texture robotTexture;
    private Texture textureSheet;

    private final int tileDimensions = 64;
    private float cameraZoom = 1;
    private int cameraX = 0;
    private int cameraY = 0;
    private Vector2 lastTouch;
    private final int viewPortWidth = 12 * tileDimensions;
    private final int viewPortHeight = 12 * tileDimensions;

    @Override
    public void create() {
        //Loads some textures
        robotTexture = new Texture(Gdx.files.internal("assets/Robot.png"));
        textureSheet = new Texture(Gdx.files.internal("assets/tiles.png"));

        game = new Game();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewPortWidth, viewPortHeight);
        camera.position.set(viewPortWidth/2f, viewPortHeight/2f, 0);
        batch = new SpriteBatch();
        /*MyTextInputListener listener = new MyTextInputListener();
        Gdx.input.getTextInput(listener, "Input name", "", "Name");*/
        Gdx.input.setInputProcessor(this);
        lastTouch = new Vector2();
    }

    /**
     * Renders all textures necessary to display a game
     */
    public void render() {
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        camera.translate(cameraX, cameraY);
        cameraX = 0;
        cameraY = 0;
        camera.zoom = cameraZoom;
        //Draws all elements the game wants to draw
        List<IDrawableObject> elementsToDraw = IOUtil.getDrawableObjectsFromGame(game, tileDimensions, tileDimensions);
        for (IDrawableObject object : elementsToDraw) {
            TextureRegion objectTextureRegion = object.getTexture();
            batch.draw(objectTextureRegion.getTexture(), object.getXPosition(), object.getYPosition(),
                    (float)object.getWidth()/2, (float)object.getHeight()/2,
                    object.getWidth(), object.getHeight(), 1, 1, object.getRotation(),
                    objectTextureRegion.getRegionX(), objectTextureRegion.getRegionY(),
                    objectTextureRegion.getRegionWidth(), objectTextureRegion.getRegionHeight(),
                    object.flipX(), object.flipY());
        }
        batch.end();
    }

    @Override
    public void dispose() {
        robotTexture.dispose();
        textureSheet.dispose();
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.PLUS:
                if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                    cameraZoom -= 0.1;
                    return true;
                }
            case Input.Keys.MINUS:
                if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                    cameraZoom += 0.1;
                    return true;
                }
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
            camera.up.x = 0;
            camera.up.y = 1;
            cameraZoom = 1;
            System.out.print((game.getHeight() * tileDimensions) - viewPortHeight/2f);
            camera.position.set(viewPortWidth/2f, viewPortHeight/2f, 0);
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
        int[] change = translateToDirection(diff.x, diff.y);
        cameraX = change[0];
        cameraY = change[1];
        return true;
    }

    /**
     * Translates x and y coordinates according to the camera's direction
     * @param x The x coordinate to translate
     * @param y The y coordinate to translate
     * @return A list containing the translated coordinates of x and y
     */
    private int[] translateToDirection(float x, float y) {
        int outX = 0;
        int outY = 0;
        int upX = Math.round(camera.up.x);
        int upY = Math.round(camera.up.y);
        if (upX == 0 && Math.round(camera.up.y) == 1) {
            outX = (int)-x;
            outY = (int)y;
        } else if (upX == 0 && upY == -1) {
            outX = (int)x;
            outY = (int)-y;
        } else if (upX == -1 && upY == 0) {
            outX = (int)-y;
            outY = (int)-x;
        } else if (upX == 1 && upY == 0) {
            outX = (int)y;
            outY = (int)x;
        }
        return new int[]{outX, outY};
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if (amount < 0 && cameraZoom > 0 || amount > 0 && cameraZoom < 2) {
            cameraZoom += amount / 10.0;
        }
        return true;
    }

    /*public static class MyTextInputListener implements Input.TextInputListener {
        @Override
        public void input (String text) {
            System.out.println(text);
        }

        @Override
        public void canceled () {
        }
    }*/
}