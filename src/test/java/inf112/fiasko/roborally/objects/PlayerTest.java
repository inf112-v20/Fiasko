package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Action;
import inf112.fiasko.roborally.element_properties.RobotID;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class PlayerTest {
    private Player playerTest;
    private final List<ProgrammingCard> cards = new ArrayList<>();

    @Before
    public void setUp() {
        cards.add(new ProgrammingCard(10, Action.MOVE_1));
        cards.add(new ProgrammingCard(20, Action.MOVE_2));
        cards.add(new ProgrammingCard(30, Action.MOVE_3));
        cards.add(new ProgrammingCard(40, Action.BACK_UP));
        cards.add(new ProgrammingCard(50, Action.ROTATE_LEFT));
        playerTest = new Player(RobotID.ROBOT_1, "TestPlayer" );
    }

    @Test
    public void setPowerDownStatusToTrue() {
        playerTest.setPowerDownNextRound(true);
        assertTrue(playerTest.getPowerDownNextRound());
    }

    @Test
    public void setPowerDownStatusToFalse() {
        playerTest.setPowerDownNextRound(false);
        assertFalse(playerTest.getPowerDownNextRound());
    }

    @Test
    public void testSetInProgram() {
        playerTest.setInProgram(cards);
        assertEquals(Action.MOVE_1, playerTest.getProgramFromPlayer().get(0).getAction());
        assertEquals(Action.MOVE_2, playerTest.getProgramFromPlayer().get(1).getAction());
        assertEquals(Action.MOVE_3, playerTest.getProgramFromPlayer().get(2).getAction());
        assertEquals(Action.BACK_UP, playerTest.getProgramFromPlayer().get(3).getAction());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetInProgramWithToManyCards() {
        cards.add(new ProgrammingCard(10,Action.ROTATE_LEFT));
        playerTest.setInProgram(cards);
    }

    @Test
    public void testSetInDeck() {
        cards.add(new ProgrammingCard(10,Action.ROTATE_LEFT));
        ProgrammingCardDeck playerDeck = new ProgrammingCardDeck(cards);
        playerTest.setPlayerDeck(playerDeck);
        assertEquals(playerDeck , playerTest.getPlayerDeck());
    }

    @Test
    public void getPlayerRobotId() {
        assertEquals(RobotID.ROBOT_1, playerTest.getRobotID());
    }

    @Test
    public void getPlayerName() {
        assertEquals("TestPlayer", playerTest.getName());
    }

    @Test
    public void getProgramFromPlayer() {
        playerTest.setInProgram(cards);
        assertEquals(cards,playerTest.getProgram());
    }

}
