package inf112.fiasko.roborally;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.fiasko.roborally.game.Game;
import inf112.fiasko.roborally.game.IDrawableGame;
import inf112.fiasko.roborally.objects.IDrawableObject;
import inf112.fiasko.roborally.utility.IOUtil;

import java.util.List;

/**
 * This class renders a game using libgdx
 */
public class GameLauncher extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private IDrawableGame game;

    private Texture robotTexture;
    private Texture textureSheet;

    private final int tileDimensions = 64;
    private float cameraZoom = 1;
    private int cameraX = 0;
    private int cameraY = 0;

    @Override
    public void create() {
        //Loads some textures
        robotTexture = new Texture(Gdx.files.internal("assets/Robot.png"));
        textureSheet = new Texture(Gdx.files.internal("assets/tiles.png"));

        game = new Game();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.getWidth() * tileDimensions,
                game.getHeight() * tileDimensions);
        batch = new SpriteBatch();
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
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Input.Keys.PLUS) && cameraZoom > 0) {
            cameraZoom -= 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Input.Keys.MINUS) && cameraZoom < 2) {
            cameraZoom += 0.1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            cameraX += 1;
        }
    }

    @Override
    public void dispose() {
        robotTexture.dispose();
        textureSheet.dispose();
        batch.dispose();
    }
}