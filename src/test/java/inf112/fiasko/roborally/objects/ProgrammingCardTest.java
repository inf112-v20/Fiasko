package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.elementproperties.Action;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProgrammingCardTest {

    private ProgrammingCard programmingCard1;
    private ProgrammingCard programmingCard2;
    private ProgrammingCard programmingCard3;

    @Before
    public void setUp() {
        programmingCard1 = new ProgrammingCard(50, Action.MOVE_1);
        programmingCard2 = new ProgrammingCard(230, Action.ROTATE_LEFT);
        programmingCard3 = new ProgrammingCard(500, Action.ROTATE_LEFT);
    }

    @Test
    public void testGetProgrammingCardAction() {
        assertEquals(Action.MOVE_1, programmingCard1.getAction());
        assertEquals(Action.ROTATE_LEFT, programmingCard2.getAction());
    }

    @Test
    public void testGetProgrammingCardValue() {
        assertEquals(50, programmingCard1.getPriority());
        assertEquals(230, programmingCard2.getPriority());
        assertEquals(500, programmingCard3.getPriority());
    }
}
