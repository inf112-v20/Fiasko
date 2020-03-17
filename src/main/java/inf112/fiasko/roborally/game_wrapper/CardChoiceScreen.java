package inf112.fiasko.roborally.game_wrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.*;

import static com.badlogic.gdx.graphics.Color.GREEN;
import static com.badlogic.gdx.graphics.Color.RED;

public class CardChoiceScreen extends InputAdapter implements Screen {
    private final RoboRallyWrapper roboRallyWrapper;

    private final OrthographicCamera camera;
    private final CardRectangle cardRectangle;
    private final ShapeRenderer shapeRenderer;
    private final Viewport viewport;

    public CardChoiceScreen(final RoboRallyWrapper roboRallyWrapper) {
        this.roboRallyWrapper = roboRallyWrapper;
        camera = new OrthographicCamera();
        int applicationWidth = 600;
        int applicationHeight = 800;
        camera.setToOrtho(true, applicationWidth, applicationHeight);
        viewport = new FitViewport(applicationWidth, applicationHeight, camera);
        Rectangle card1 = new Rectangle();
        card1.x = 10;
        card1.y = 10;
        card1.width = 100;
        card1.height = 100;
        cardRectangle = new CardRectangle(card1);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {
        //Nothing to do
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (cardRectangle.selected) {
            shapeRenderer.setColor(RED);
            shapeRenderer.rect(cardRectangle.rectangle.x - 5, cardRectangle.rectangle.y - 5,
                    cardRectangle.rectangle.width + 10, cardRectangle.rectangle.height + 10);
        }
        shapeRenderer.setColor(GREEN);
        shapeRenderer.rect(cardRectangle.rectangle.x, cardRectangle.rectangle.y,
                cardRectangle.rectangle.width, cardRectangle.rectangle.height);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        //Nothing to do
    }

    @Override
    public void resume() {
        //Nothing to do
    }

    @Override
    public void hide() {
        //Nothing to do
    }

    @Override
    public void dispose() {
        //Nothing to do
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 transformed = viewport.unproject(new Vector3(screenX, screenY, 0));
        if (cardRectangle.rectangle.contains(transformed.x, transformed.y)) {
            cardRectangle.selected = !cardRectangle.selected;
            return true;
        }
        return false;
    }
}

class CardRectangle {
    protected final Rectangle rectangle;
    protected boolean selected = false;

    CardRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}