package inf112.fiasko.roborally;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.fiasko.roborally.element_properties.GameTexture;
import inf112.fiasko.roborally.game.Game;
import inf112.fiasko.roborally.game.IDrawableGame;
import inf112.fiasko.roborally.objects.IDrawableObject;

/**
 * This class renders a game using libgdx
 */
public class GameLauncher extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private IDrawableGame game;

    private Texture robotTexture;
    private Texture textureSheet;

    @Override
    public void create() {
        //Loads some textures
        robotTexture = new Texture(Gdx.files.internal("assets/Robot.png"));
        textureSheet = new Texture(Gdx.files.internal("assets/tiles.png"));

        game = new Game();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.getWidth(), game.getHeight());
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
        //Draws all elements the game wants to draw
        for (IDrawableObject object : game.getObjectsToDraw()) {
            TextureRegion objectTextureRegion = gameTextureToTextureRegion(object.getTexture());
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

    /**
     * Turns a GameTexture element into a TextureRegion element
     *
     * This is necessary to keep all libgdx logic in this class only. Otherwise, testing would be painful.
     *
     * @param gameTexture A GameTexture enum
     * @return A Gdx TextureRegion
     */
    private TextureRegion gameTextureToTextureRegion(GameTexture gameTexture) {
        switch (gameTexture) {
            case ROBOT:
                return new TextureRegion(robotTexture, 0, 0, 64, 64);
            case TILE:
                return new TextureRegion(textureSheet, 4*300, 0, 300, 300);
            default:
                throw new IllegalArgumentException("Non existing texture encountered.");
        }
    }
}