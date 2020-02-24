package inf112.fiasko.roborally;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
        /*MyTextInputListener listener = new MyTextInputListener();
        Gdx.input.getTextInput(listener, "Input name", "", "Name");*/
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
        //Draws all elements the game wants to draw
        List<IDrawableObject> elementsToDraw = IOUtil.getDrawableObjectsFromGame(game, tileDimensions, tileDimensions);
        for (IDrawableObject object : elementsToDraw) {
            TextureRegion objectTextureRegion = object.getTexture();
            /*System.out.println(object.getTexture() + " " + object.getXPosition() + " " + object.getYPosition() + " " + object.getWidth() + " " +
                    object.getHeight() + " " + object.getRotation() + " " + objectTextureRegion.getRegionX() + " " +
                    objectTextureRegion.getRegionY() + " " + objectTextureRegion.getRegionWidth() + " " + objectTextureRegion.getRegionHeight());*/
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