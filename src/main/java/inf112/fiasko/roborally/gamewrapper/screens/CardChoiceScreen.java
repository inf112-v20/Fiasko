package inf112.fiasko.roborally.gamewrapper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.fiasko.roborally.elementproperties.GameState;
import inf112.fiasko.roborally.gamewrapper.RoboRallyWrapper;
import inf112.fiasko.roborally.gamewrapper.SimpleButton;
import inf112.fiasko.roborally.networking.containers.ProgramAndPowerdownRequest;
import inf112.fiasko.roborally.objects.ProgrammingCard;
import inf112.fiasko.roborally.objects.ProgrammingCardDeck;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.Color.BLACK;
import static com.badlogic.gdx.graphics.Color.GRAY;
import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

/**
 * This screen is used to let the user choose their program
 */
public class CardChoiceScreen extends InteractiveScreen implements Screen {
    private final RoboRallyWrapper roboRallyWrapper;

    private final List<CardRectangle> cardRectangles;
    private final ShapeRenderer shapeRenderer;
    private final List<CardRectangle> chosenCards;
    private final int maxCards;

    /**
     * Instantiates a new card choice screen
     *
     * @param roboRallyWrapper The Robo Rally wrapper which is parent of this screen
     */
    public CardChoiceScreen(final RoboRallyWrapper roboRallyWrapper) {
        ProgrammingCardDeck deck = roboRallyWrapper.roboRallyGame.getPlayerHand();
        this.roboRallyWrapper = roboRallyWrapper;
        maxCards = roboRallyWrapper.roboRallyGame.getProgramSize();
        if (maxCards == -1) {
            throw new IllegalArgumentException("This player should not be able to choose any cards at this point in " +
                    "time.");
        }
        camera.setToOrtho(false, applicationWidth, applicationHeight);
        viewport = new FitViewport(applicationWidth, applicationHeight, camera);
        cardRectangles = new ArrayList<>();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        generateCards(deck);
        this.chosenCards = new ArrayList<>();

        TextButton confirmCards = new SimpleButton("Confirm cards", roboRallyWrapper.font).getButton();
        stage.addActor(confirmCards);
        confirmCards.setY(viewport.getWorldHeight() - confirmCards.getHeight());
        confirmCards.setX(0);

        confirmCards.addListener(getConfirmListener());

        TextButton confirmCardsAndPowerDown = new SimpleButton("Confirm + PowerDown",
                roboRallyWrapper.font).getButton();
        stage.addActor(confirmCardsAndPowerDown);
        confirmCardsAndPowerDown.setY(viewport.getWorldHeight() - confirmCardsAndPowerDown.getHeight());
        confirmCardsAndPowerDown.setX(confirmCards.getWidth());

        confirmCardsAndPowerDown.addListener(getConfirmAndPowerDownListener());
        stage.setViewport(viewport);
    }

    /**
     * Generates a listener for confirming cards
     *
     * @return An input listener
     */
    private ClickListener getConfirmListener() {
        return new ClickListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                confirmCards(false);
            }
        };
    }

    /**
     * Generates a listener for confirming cards and entering power down
     *
     * @return An input listener
     */
    private ClickListener getConfirmAndPowerDownListener() {
        return new ClickListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                confirmCards(true);
            }
        };
    }

    /**
     * Confirm cards and send to server if all are chosen
     */
    private void confirmCards(Boolean requestPowerDown) {
        if (chosenCards.size() == maxCards) {
            List<ProgrammingCard> oldProgram = roboRallyWrapper.roboRallyGame.getProgram();
            int lockedCardsInt = 5 - maxCards;
            List<ProgrammingCard> newProgram = getCards();
            for (int i = 4; i > (4 - lockedCardsInt); i--) {
                newProgram.add(oldProgram.get(i));
            }
            //Save the program to get locked cards later
            roboRallyWrapper.roboRallyGame.setProgram(newProgram);
            roboRallyWrapper.roboRallyGame.setGameState(GameState.WAITING_FOR_OTHER_PLAYERS_PROGRAMS);
            roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getLoadingScreen(this.roboRallyWrapper));
            roboRallyWrapper.client.sendElement(new ProgramAndPowerdownRequest(requestPowerDown, newProgram));
        } else {
            JOptionPane.showMessageDialog(null, "You need to choose all your cards"
                    + " before confirming.");
        }
    }

    /**
     * Gets a list of programming cards from the player's chosen cards
     *
     * @return A list of programming cards
     */
    private List<ProgrammingCard> getCards() {
        List<ProgrammingCard> program = new ArrayList<>();
        chosenCards.forEach((cardRectangle -> program.add(cardRectangle.card)));
        return program;
    }

    /**
     * Calculates positions for cards in the given deck
     *
     * @param deck A deck containing cards which can be chosen
     */
    private void generateCards(ProgrammingCardDeck deck) {
        List<ProgrammingCard> cardList = deck.getCards();
        float cardWidth = viewport.getWorldWidth() / 3;
        float cardHeight = (viewport.getWorldHeight() - 30) / 3;
        for (int i = 0; i < cardList.size(); i++) {
            int x = (int) (((i % 3) * cardWidth) + 10);
            int y = (int) (((i / 3) * cardHeight + 10));
            Rectangle card = new Rectangle();
            card.x = x;
            card.y = y;
            card.width = (int) cardWidth - 20;
            card.height = (int) cardHeight - 20;
            ProgrammingCard programmingCard = cardList.get(i);
            CardRectangle cardRectangle = new CardRectangle(card, programmingCard);
            cardRectangles.add(cardRectangle);
        }
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        roboRallyWrapper.batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        renderCards();
        shapeRenderer.end();
        roboRallyWrapper.batch.begin();
        renderCardText();
        roboRallyWrapper.batch.end();
        stage.draw();
        stage.act();
    }

    @Override
    public boolean keyUp(int keyCode) {
        if (keyCode == Input.Keys.TAB) {
            roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getBoardActiveScreen(roboRallyWrapper));
        }
        return false;
    }

    /**
     * Renders the base shape of cards
     */
    private void renderCards() {
        for (CardRectangle cardRectangle : cardRectangles) {
            if (cardRectangle.selected) {
                shapeRenderer.setColor(RED);
                shapeRenderer.rect(cardRectangle.rectangle.x - 10, cardRectangle.rectangle.y - 10,
                        cardRectangle.rectangle.width + 20, cardRectangle.rectangle.height + 20);
            }
            shapeRenderer.setColor(GRAY);
            shapeRenderer.rect(cardRectangle.rectangle.x, cardRectangle.rectangle.y,
                    cardRectangle.rectangle.width, cardRectangle.rectangle.height);
        }
    }

    /**
     * Renders the text displayed on cards
     */
    private void renderCardText() {
        for (CardRectangle cardRectangle : cardRectangles) {
            GlyphLayout layout = new GlyphLayout(roboRallyWrapper.font,
                    Integer.toString(cardRectangle.card.getPriority()));
            float fontX = (int) (cardRectangle.rectangle.x + (cardRectangle.rectangle.width - layout.width) / 2.0);
            float fontY = cardRectangle.rectangle.y + cardRectangle.rectangle.height - 30;
            roboRallyWrapper.font.draw(roboRallyWrapper.batch, layout, fontX, fontY);
            drawCardSymbol(cardRectangle);
            int chosenIndex = chosenCards.indexOf(cardRectangle);
            if (chosenIndex != -1) {
                roboRallyWrapper.font.setColor(BLACK);
                roboRallyWrapper.font.draw(roboRallyWrapper.batch, String.valueOf(chosenIndex + 1),
                        cardRectangle.rectangle.x + cardRectangle.rectangle.width - 20, cardRectangle.rectangle.y + cardRectangle.rectangle.height - 5);
                roboRallyWrapper.font.setColor(WHITE);
            }
        }
    }

    /**
     * Draws the symbol on a card
     *
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
        stage.dispose();
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