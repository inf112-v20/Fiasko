package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Action;
import inf112.fiasko.roborally.element_properties.RobotID;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;


public class PlayerTest {
    private Player playerTest;
    @Before
    public void setUp() {
        List<ProgrammingCard> cards = new ArrayList();
        cards.add(new ProgrammingCard(10, Action.MOVE_1));
        cards.add(new ProgrammingCard(20, Action.MOVE_2));
        cards.add(new ProgrammingCard(30, Action.MOVE_3));
        cards.add(new ProgrammingCard(40, Action.BACK_UP));
        cards.add(new ProgrammingCard(50, Action.ROTATE_LEFT));
        ProgrammingCardDeck playerDeck = new ProgrammingCardDeck(cards);
        playerTest = new Player(RobotID.ROBOT_1, "TestPlayer" ,playerDeck);
    }

    @Test
    public void setPowerDownStatusToTrue() {
        playerTest.setPowerDownNextRound(true);
        assertEquals(true, playerTest.getPowerDownNextRound());
    }

    @Test
    public void setPowerDownStatusToFalse() {
        playerTest.setPowerDownNextRound(false);
        assertEquals(false, playerTest.getPowerDownNextRound());
    }

    @Test
    public void cardGetsInsertedIntoProgram() {
        playerTest.setCardInProgram(new ProgrammingCard(10,Action.MOVE_1));
        assertEquals(Action.MOVE_1,playerTest.getProgram().get(0).getAction());
    }
}
