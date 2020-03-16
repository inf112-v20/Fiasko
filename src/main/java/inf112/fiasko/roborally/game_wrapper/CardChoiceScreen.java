package inf112.fiasko.roborally.game_wrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

import static com.badlogic.gdx.graphics.Color.GREEN;
import static com.badlogic.gdx.graphics.Color.RED;

public class CardChoiceScreen extends InputAdapter implements Screen {
    private final RoboRallyWrapper roboRallyWrapper;

    private final OrthographicCamera camera;
    private final CardRectangle cardRectangle;
    private final ShapeRenderer shapeRenderer;

    public CardChoiceScreen(final RoboRallyWrapper roboRallyWrapper) {
        this.roboRallyWrapper = roboRallyWrapper;
        camera = new OrthographicCamera();
        camera.setToOrtho(true, 1200, 1200);
        Rectangle card1 = new Rectangle();
        card1.x = 1200/2;
        card1.y = 1200/2;
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
    public void resize(int i, int i1) {
        //Nothing to do
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
        System.out.println(screenX + " " + screenY);
        System.out.println(cardRectangle.rectangle.x + " " + cardRectangle.rectangle.y + " " +
                cardRectangle.rectangle.width + " " + cardRectangle.rectangle.height);
        if (cardRectangle.rectangle.contains(screenX, screenY)) {
            cardRectangle.selected = true;
            System.out.println("Card touched");
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