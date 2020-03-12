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
    @Test
    public void addMultipuleCards(){
        playerTest.setCardInProgram(new ProgrammingCard(10,Action.MOVE_1));
        playerTest.setCardInProgram(new ProgrammingCard(30,Action.MOVE_2));
        playerTest.setCardInProgram(new ProgrammingCard(23452342,Action.MOVE_3));
        assertEquals(Action.MOVE_1,playerTest.getProgram().get(0).getAction());
        assertEquals(Action.MOVE_2,playerTest.getProgram().get(1).getAction());
        assertEquals(Action.MOVE_3,playerTest.getProgram().get(2).getAction());
    }
    @Test(expected = IllegalArgumentException.class)
    public void addTooManyCardsGetsAError() {
        playerTest.setCardInProgram(new ProgrammingCard(10,Action.MOVE_1));
        playerTest.setCardInProgram(new ProgrammingCard(30,Action.MOVE_2));
        playerTest.setCardInProgram(new ProgrammingCard(234523423,Action.MOVE_3));
        playerTest.setCardInProgram(new ProgrammingCard(2342342,Action.MOVE_3));
        playerTest.setCardInProgram(new ProgrammingCard(23432342,Action.MOVE_3));
        playerTest.setCardInProgram(new ProgrammingCard(234523242,Action.MOVE_3));
    }
    @Test
    public void removeCardsFromPlayerProgram() {
        playerTest.setCardInProgram(new ProgrammingCard(10,Action.MOVE_1));
        playerTest.setCardInProgram(new ProgrammingCard(30,Action.MOVE_2));
        playerTest.setCardInProgram(new ProgrammingCard(234523423,Action.MOVE_3));
        playerTest.setCardInProgram(new ProgrammingCard(2342342,Action.MOVE_3));
        playerTest.setCardInProgram(new ProgrammingCard(23432342,Action.MOVE_3));
        assertEquals(Action.MOVE_3,playerTest.getProgram().get(4).getAction());
        playerTest.removeProgramCard(4);
        assertEquals(null,playerTest.getProgram().get(4));
    }
    @Test
    public void removeAllCardsFromPlayerProgram() {
        playerTest.setCardInProgram(new ProgrammingCard(10,Action.MOVE_1));
        playerTest.setCardInProgram(new ProgrammingCard(30,Action.MOVE_2));
        playerTest.setCardInProgram(new ProgrammingCard(234523423,Action.MOVE_3));
        playerTest.setCardInProgram(new ProgrammingCard(2342342,Action.MOVE_3));
        playerTest.setCardInProgram(new ProgrammingCard(23432342,Action.MOVE_3));
        assertEquals(Action.MOVE_3,playerTest.getProgram().get(4).getAction());
        assertEquals(Action.MOVE_3,playerTest.getProgram().get(3).getAction());
        assertEquals(Action.MOVE_3,playerTest.getProgram().get(2).getAction());
        assertEquals(Action.MOVE_2,playerTest.getProgram().get(1).getAction());
        assertEquals(Action.MOVE_1,playerTest.getProgram().get(0).getAction());
        playerTest.removeProgramCard(4);
        playerTest.removeProgramCard(3);
        playerTest.removeProgramCard(2);
        playerTest.removeProgramCard(1);
        playerTest.removeProgramCard(0);
        assertEquals(null,playerTest.getProgram().get(4));
        assertEquals(null,playerTest.getProgram().get(3));
        assertEquals(null,playerTest.getProgram().get(2));
        assertEquals(null,playerTest.getProgram().get(1));
        assertEquals(null,playerTest.getProgram().get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorIfYouRemoveMoreThenIndexFive(){
        playerTest.setCardInProgram(new ProgrammingCard(10,Action.MOVE_1));
        playerTest.removeProgramCard(5);

    }
    @Test(expected = IllegalArgumentException.class)
    public void getErrorIfYouRemoveANegativIndex(){
        playerTest.setCardInProgram(new ProgrammingCard(10,Action.MOVE_1));
        playerTest.removeProgramCard(-1);

    }

}
