package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.objects.properties.Action;
import inf112.fiasko.roborally.objects.properties.GameState;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RoboRallyGameTest {
    private RoboRallyGame game;

    @Before
    public void setUp() {
        game = new RoboRallyGame(new ArrayList<>(), "Checkmate.txt", "Player1",
                null, false);
    }

    @Test
    public void gameWidthIsPositive() {
        assertTrue(game.getWidth() > 0);
    }

    @Test
    public void gameHeightIsPositive() {
        assertTrue(game.getHeight() > 0);
    }

    @Test
    public void initialGameStateIsCorrect() {
        assertEquals(GameState.BEGINNING_OF_GAME, game.getGameState());
    }

    @Test
    public void gameStateIsChangeable() {
        game.setGameState(GameState.EXITED);
        assertEquals(GameState.EXITED, game.getGameState());
    }

    @Test
    public void gameWidthIsEqualToBoardWidth() {
        assertEquals(12, game.getWidth());
    }

    @Test
    public void gameHeightIsEqualToBoardHeight() {
        assertEquals(16, game.getHeight());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidProgramThrowsError() {
        List<ProgrammingCard> programmingCardList = new ArrayList<>();
        programmingCardList.add(new ProgrammingCard(10, Action.MOVE_1));
        game.setProgram(programmingCardList);
    }

    @Test
    public void canChangeGameProgram() {
        assertNull(game.getProgram());
        List<ProgrammingCard> programmingCardList = new ArrayList<>();
        programmingCardList.add(new ProgrammingCard(10, Action.MOVE_1));
        programmingCardList.add(new ProgrammingCard(30, Action.MOVE_2));
        programmingCardList.add(new ProgrammingCard(50, Action.MOVE_3));
        programmingCardList.add(new ProgrammingCard(340, Action.BACK_UP));
        programmingCardList.add(new ProgrammingCard(450, Action.U_TURN));
        game.setProgram(programmingCardList);
        assertEquals(programmingCardList, game.getProgram());
    }

    @Test
    public void canChangePlayerHand() {
        assertNull(game.getPlayerHand());
        ProgrammingCard card = new ProgrammingCard(10, Action.ROTATE_RIGHT);
        List<ProgrammingCard> cards = new ArrayList<>();
        cards.add(card);
        ProgrammingCardDeck deck = new ProgrammingCardDeck(cards);
        game.setPlayerHand(deck);
        assertEquals(card, game.getPlayerHand().peekTop());
    }

    @Test
    public void canChangeWinningPlayer() {
        assertNull(game.getWinningPlayerName());
        String winning = "Player 1";
        game.setWinningPlayerName(winning);
        assertEquals(winning, game.getWinningPlayerName());
    }

    @Test
    public void canGetPowerDown() {
        assertFalse(game.getRobotPowerDown());
    }

}
