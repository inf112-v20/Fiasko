package inf112.fiasko.roborally.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.fiasko.roborally.networking.containers.ProgramAndPowerDownRequest;
import inf112.fiasko.roborally.objects.InteractableGame;
import inf112.fiasko.roborally.objects.ProgrammingCard;
import inf112.fiasko.roborally.objects.ProgrammingCardDeck;
import inf112.fiasko.roborally.objects.properties.GameState;
import inf112.fiasko.roborally.ui.RoboRallyWrapper;
import inf112.fiasko.roborally.ui.SimpleButton;
import inf112.fiasko.roborally.utility.TextureConverterUtil;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;
import static com.badlogic.gdx.graphics.Color.YELLOW;

/**
 * This screen is used to let the user choose their program
 */
public class CardChoiceScreen extends InteractiveScreen {
    private final RoboRallyWrapper roboRallyWrapper;

    private final List<CardRectangle> cardRectangles;
    private final ShapeRenderer shapeRenderer;
    private final List<CardRectangle> chosenCards;
    private final int maxCards;
    private final InteractableGame game;
    private long timerStarted;

    /**
     * Instantiates a new card choice screen
     *
     * @param roboRallyWrapper The Robo Rally wrapper which is parent of this screen
     */
    public CardChoiceScreen(final RoboRallyWrapper roboRallyWrapper) {
        game = roboRallyWrapper.getGame();
        ProgrammingCardDeck deck = game.getPlayerHand();
        this.roboRallyWrapper = roboRallyWrapper;
        maxCards = game.getProgramSize();
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
     * Confirm cards and send to server together with power down choice if all are chosen
     *
     * @param requestPowerDown Whether the user wants to enter power down
     */
    private void confirmCards(Boolean requestPowerDown) {
        if (chosenCards.size() == maxCards) {
            roboRallyWrapper.shouldHurry = false;
            List<ProgrammingCard> newProgram = getChosenAndLockedCards();
            //Save the program to get locked cards later
            game.setProgram(newProgram);
            game.setGameState(GameState.WAITING_FOR_OTHER_PLAYERS_PROGRAMS);
            roboRallyWrapper.setScreen(roboRallyWrapper.screenManager.getLoadingScreen(this.roboRallyWrapper));
            roboRallyWrapper.client.sendElement(new ProgramAndPowerDownRequest(requestPowerDown, newProgram));
        } else {
            JOptionPane.showMessageDialog(null, "You need to choose all your cards"
                    + " before confirming.");
        }
    }

    /**
     * Gets the number of locked cards
     *
     * @return The number of locked cards
     */
    private int getNumberOfLockedCards() {
        return 5 - maxCards;
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
        float cardWidth = viewport.getWorldWidth() / 3.2f;
        float cardHeight = (viewport.getWorldHeight() - 30) / 3.2f;
        for (int i = 0; i < cardList.size(); i++) {
            ProgrammingCard programmingCard = cardList.get(i);
            generateCardRectangle(i, cardWidth, cardHeight, programmingCard, true);
        }
        //Adds locked cards to the card list
        List<ProgrammingCard> oldProgram = getOldProgram();
        for (int i = cardList.size(); i < cardList.size() + getNumberOfLockedCards(); i++) {
            ProgrammingCard programmingCard = oldProgram.get(4 - (i - cardList.size()));
            generateCardRectangle(i, cardWidth, cardHeight, programmingCard, false);
        }
    }

    /**
     * Gets the old program of the player with extra cards added
     *
     * @return The player's old program
     */
    private List<ProgrammingCard> getOldProgram() {
        List<ProgrammingCard> oldProgram = game.getProgram();
        if (oldProgram != null && oldProgram.size() == 0) {
            oldProgram = game.getExtraCards().getCards();
            int nulls = 5 - oldProgram.size();
            for (int i = 0; i < nulls; i++) {
                oldProgram.add(0, null);
            }
        }
        return oldProgram;
    }

    /**
     * Adds a new card rectangle containing the appropriate data
     *
     * @param i               The index of the card rectangle relative to other card rectangles
     * @param cardWidth       The width of the card rectangle
     * @param cardHeight      The height of the card rectangle
     * @param programmingCard The programming card belonging to the card rectangle
     * @param selectable      Whether the card rectangle is selectable
     */
    private void generateCardRectangle(int i, float cardWidth, float cardHeight, ProgrammingCard programmingCard,
                                       boolean selectable) {
        int x = (int) (((i % 3) * cardWidth) + 10);
        int y = (int) (((i / 3) * cardHeight + 10));
        Rectangle card = new Rectangle();
        card.x = x;
        card.y = y;
        card.width = (int) cardWidth - 20;
        card.height = (int) cardHeight - 20;

        CardRectangle cardRectangle = new CardRectangle(card, programmingCard);
        cardRectangle.selectable = selectable;
        cardRectangle.selected = !selectable;
        cardRectangles.add(cardRectangle);
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
        roboRallyWrapper.font.draw(roboRallyWrapper.batch, "Press TAB to toggle the board", 10,
                viewport.getWorldHeight() - 50);
        int timerSize = 30;
        if (timerStarted != 0) {
            roboRallyWrapper.font.draw(roboRallyWrapper.batch, "Time left: " + String.valueOf(timerSize -
                            (System.currentTimeMillis() - timerStarted) / 1000), viewport.getWorldWidth() - 150,
                    viewport.getWorldHeight() - 50);
        }
        renderCardText();
        roboRallyWrapper.batch.end();
        stage.draw();
        stage.act();
        if (roboRallyWrapper.shouldHurry && timerStarted == 0) {
            timerStarted = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() > timerStarted + 1000 * timerSize && timerStarted > 0) {
            fillProgramWithRandomCards();
            confirmCards(false);
        }
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
        roboRallyWrapper.batch.begin();
        for (CardRectangle cardRectangle : cardRectangles) {
            if (cardRectangle.selected) {
                shapeRenderer.setColor(RED);
                shapeRenderer.rectLine(cardRectangle.rectangle.x, cardRectangle.rectangle.y,
                        cardRectangle.rectangle.x + cardRectangle.rectangle.width, cardRectangle.rectangle.y +
                                cardRectangle.rectangle.height, 2);
            }
            TextureRegion cardTexture = TextureConverterUtil.convertElement(cardRectangle.card);
            Rectangle rectangle = cardRectangle.rectangle;
            roboRallyWrapper.batch.draw(cardTexture.getTexture(), rectangle.getX(), rectangle.getY(),
                    rectangle.getWidth() / 2, rectangle.getHeight() / 2,
                    rectangle.getWidth(), rectangle.getHeight(), 1, 1, 0,
                    cardTexture.getRegionX(), cardTexture.getRegionY(),
                    cardTexture.getRegionWidth(), cardTexture.getRegionHeight(),
                    false, false);
        }
        roboRallyWrapper.batch.end();
    }

    /**
     * Fills the user's program with enough cards to complete the program
     */
    private void fillProgramWithRandomCards() {
        int missingCards = maxCards - getCards().size();
        for (int i = 0; i < missingCards; i++) {
            for (CardRectangle rectangle : cardRectangles) {
                if (!rectangle.selected) {
                    rectangle.selected = true;
                    rectangle.selectable = false;
                    chosenCards.add(rectangle);
                    break;
                }
            }
        }
    }

    /**
     * Renders the text displayed on cards
     */
    private void renderCardText() {
        roboRallyWrapper.font.setColor(YELLOW);
        for (CardRectangle cardRectangle : cardRectangles) {
            roboRallyWrapper.font.getData().setScale(0.7f);
            GlyphLayout layout = new GlyphLayout(roboRallyWrapper.font,
                    Integer.toString(cardRectangle.card.getPriority()));
            float fontX = (int) (cardRectangle.rectangle.x + (cardRectangle.rectangle.width - layout.width) - 28);
            float fontY = cardRectangle.rectangle.y + cardRectangle.rectangle.height - 21;
            roboRallyWrapper.font.draw(roboRallyWrapper.batch, layout, fontX, fontY);
            int chosenIndex = chosenCards.indexOf(cardRectangle);
            int totalIndex = getChosenAndLockedCards().indexOf(cardRectangle.card);
            if (chosenIndex == -1 && totalIndex != -1) {
                chosenIndex = totalIndex + maxCards - chosenCards.size();
            }
            if (chosenIndex != -1) {
                roboRallyWrapper.font.setColor(YELLOW);
                roboRallyWrapper.font.draw(roboRallyWrapper.batch, String.valueOf(chosenIndex + 1),
                        cardRectangle.rectangle.x + 30, cardRectangle.rectangle.y + cardRectangle.rectangle.height - 20);

            }
        }
        roboRallyWrapper.font.setColor(WHITE);
    }

    /**
     * Gets a list containing all chosen and locked programming cards
     *
     * @return A list of programming cards
     */
    private List<ProgrammingCard> getChosenAndLockedCards() {
        List<ProgrammingCard> oldProgram = getOldProgram();
        int lockedCardsInt = getNumberOfLockedCards();
        List<ProgrammingCard> newProgram = new ArrayList<>(getCards());
        for (int i = 4; i > (4 - lockedCardsInt); i--) {
            newProgram.add(oldProgram.get(i));
        }
        return newProgram;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 transformed = viewport.unproject(new Vector3(screenX, screenY, 0));
        for (CardRectangle cardRectangle : cardRectangles) {
            if (cardRectangle.rectangle.contains(transformed.x, transformed.y) && cardRectangle.selectable) {
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