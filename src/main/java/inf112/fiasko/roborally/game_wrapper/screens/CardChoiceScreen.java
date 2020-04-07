package inf112.fiasko.roborally.game_wrapper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.fiasko.roborally.game_wrapper.RoboRallyWrapper;
import inf112.fiasko.roborally.game_wrapper.SimpleButton;
import inf112.fiasko.roborally.objects.IDeck;
import inf112.fiasko.roborally.objects.ProgrammingCard;
import inf112.fiasko.roborally.utility.DeckLoaderUtil;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.Color.GREEN;
import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

/**
 * This screen is used to let the user choose their program
 */
public class CardChoiceScreen extends InputAdapter implements Screen {
    private final RoboRallyWrapper roboRallyWrapper;

    private final OrthographicCamera camera;
    private final List<CardRectangle> cardRectangles;
    private final ShapeRenderer shapeRenderer;
    private final Viewport viewport;
    private final List<CardRectangle> chosenCards;
    private final int maxCards;
    private final Stage stage;

    /**
     * Initializes a new card choice screen
     * @param roboRallyWrapper The robo rally wrapper which is its parent
     */
    public CardChoiceScreen(final RoboRallyWrapper roboRallyWrapper) {
        this.roboRallyWrapper = roboRallyWrapper;
        camera = new OrthographicCamera();
        int applicationWidth = 600;
        int applicationHeight = 800;
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        viewport = new FitViewport(applicationWidth, applicationHeight, camera);
        cardRectangles = new ArrayList<>();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);

        try {
            generateCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.chosenCards = new ArrayList<>();
        this.maxCards = 5;
        stage = new Stage();
        inputMultiplexer.addProcessor(stage);

        TextButton confirmCards = new SimpleButton("Confirm cards", roboRallyWrapper.font).getButton();
        stage.addActor(confirmCards);
        confirmCards.setY(viewport.getWorldHeight() + 60);
        confirmCards.setX(15);
        confirmCards.setTouchable(Touchable.enabled);
        confirmCards.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println(chosenCards.size());
                System.out.println(maxCards);
                if (chosenCards.size() == maxCards) {
                    System.out.println("Lock cards!");
                }
                return false;
            }
        });
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    /**
     * Generates some placeholder cards for testing
     * @throws IOException If programming cards cannot be loaded
     */
    private void generateCards() throws IOException {
        IDeck<ProgrammingCard> deck = DeckLoaderUtil.loadProgrammingCardsDeck();
        deck.shuffle();
        //Get 9 programming cards
        List<ProgrammingCard> cardList = deck.getCards().subList(0, 9);
        float cardWidth = viewport.getWorldWidth() / 3;
        float cardHeight = (viewport.getWorldHeight() - 30) / 3;
        for (int i = 0; i < 9; i++) {
            int x = (int)(((i % 3)*cardWidth) + 10);
            int y = (int)(((i / 3) * cardHeight + 10));
            Rectangle card = new Rectangle();
            card.x = x;
            card.y = y;
            card.width = (int)cardWidth - 20;
            card.height = (int)cardHeight - 20;
            ProgrammingCard programmingCard = cardList.get(i);
            CardRectangle cardRectangle = new CardRectangle(card, programmingCard);
            cardRectangles.add(cardRectangle);
        }
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
        for (CardRectangle cardRectangle : cardRectangles) {
            if (cardRectangle.selected) {
                shapeRenderer.setColor(RED);
                shapeRenderer.rect(cardRectangle.rectangle.x - 10, cardRectangle.rectangle.y - 10,
                        cardRectangle.rectangle.width + 20, cardRectangle.rectangle.height + 20);
            }
            shapeRenderer.setColor(GREEN);
            shapeRenderer.rect(cardRectangle.rectangle.x, cardRectangle.rectangle.y,
                    cardRectangle.rectangle.width, cardRectangle.rectangle.height);
        }

        shapeRenderer.end();
        roboRallyWrapper.batch.begin();
        for (CardRectangle cardRectangle : cardRectangles) {
            GlyphLayout layout = new GlyphLayout(roboRallyWrapper.font, Integer.toString(cardRectangle.card.getPriority()));
            float fontX = (int)(cardRectangle.rectangle.x + (cardRectangle.rectangle.width - layout.width) / 2.0);
            float fontY = cardRectangle.rectangle.y + cardRectangle.rectangle.height - 30;
            roboRallyWrapper.font.draw(roboRallyWrapper.batch, layout, fontX, fontY);
            drawCardSymbol(cardRectangle);
        }
        roboRallyWrapper.batch.end();
        stage.draw();
    }

    /**
     * Draws the symbol on a card
     * @param cardRectangle The card rectangle to draw
     */
    private void drawCardSymbol(CardRectangle cardRectangle) {
        String text;
        switch (cardRectangle.card.getAction()) {
            case MOVE_1:
                text = "Move 1 forward";
                break;
            case MOVE_2:
                text = "Move 2 forward";
                break;
            case MOVE_3:
                text = "Move 3 forward";
                break;
            case BACK_UP:
                text = "Back up";
                break;
            case ROTATE_LEFT:
                text = "Rotate left";
                break;
            case ROTATE_RIGHT:
                text = "Rotate right";
                break;
            case U_TURN:
                text = "U Turn";
                break;
            default:
                throw new IllegalArgumentException("Invalid action on CardRectangle.");
        }
        GlyphLayout layout = new GlyphLayout();
        layout.setText(roboRallyWrapper.font, text, WHITE, cardRectangle.rectangle.width - 20,
                1, true);
        float fontX = cardRectangle.rectangle.x;
        float fontY = cardRectangle.rectangle.y + cardRectangle.rectangle.height - 80;
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, layout, fontX, fontY);
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
        for (CardRectangle cardRectangle : cardRectangles) {
            if (cardRectangle.rectangle.contains(transformed.x, transformed.y)) {
                if (!cardRectangle.selected && chosenCards.size() < maxCards) {
                    chosenCards.add(cardRectangle);
                    cardRectangle.selected = true;
                } else if (cardRectangle.selected) {
                    chosenCards.remove(cardRectangle);
                    cardRectangle.selected = false;
                }
                return true;
            }
        }
        return false;
    }
}

/**
 * A helper class for keeping track of card information and a rectangle
 */
class CardRectangle {
    final Rectangle rectangle;
    boolean selected = false;
    final ProgrammingCard card;

    CardRectangle(Rectangle rectangle, ProgrammingCard card) {
        this.rectangle = rectangle;
        this.card = card;
    }

    @Override
    public String toString() {
        return card.toString();
    }
}