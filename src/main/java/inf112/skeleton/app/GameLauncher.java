package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This class renders a game using libgdx
 */
public class GameLauncher extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private IDrawableGame game;

    @Override
    public void create() {
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
        //Renders all elements the game wants to render
        for (IDrawableObject object : game.objectsToRender()) {
            batch.draw(object.getTexture(), object.getXPosition(), object.getYPosition(),
                    (float)object.getWidth()/2, (float)object.getHeight()/2, object.getWidth(),
                    object.getHeight(), 1, 1, object.getRotation(),
                    0, 0, object.getTexture().getWidth(), object.getTexture().getHeight(), object.flipX(),
                    object.flipY());
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}