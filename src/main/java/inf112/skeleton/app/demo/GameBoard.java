package inf112.skeleton.app.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameBoard extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Texture robotTexture;
    private Texture tileTexture;

    private Rectangle robot;

    @Override
    public void create() {
        //Loads some textures
        robotTexture = new Texture(Gdx.files.internal("assets/Robot.png"));
        tileTexture = new Texture(Gdx.files.internal("assets/Tile.png"));

        robot = new Rectangle((float)768/2,(float)768/2,64,64);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 768, 768);
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
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                batch.draw(tileTexture, i * 64, j * 64);
            }
        }
        batch.draw(robotTexture, robot.x, robot.y);
        batch.end();
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && robot.x < 768-64) {
            robot.x += 64;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && robot.x > 0) {
            robot.x -= 64;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && robot.y < 768-64) {
            robot.y += 64;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && robot.y > 0) {
            robot.y -= 64;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}