package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Action;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ProgrammingCardTest {

    private ProgrammingCard programmingCard1;
    private ProgrammingCard programmingCard2;
    private ProgrammingCard programmingCard3;

    @Before
    public void setUp() {
        programmingCard1 = new ProgrammingCard(5, Action.MOVE_1);
        programmingCard2 = new ProgrammingCard(234, Action.ROTATE_LEFT);
        programmingCard3 = new ProgrammingCard(2334, Action.ROTATE_LEFT);
    }
    @Test
    public void testGetProgrammingCardAction() {
        assertEquals(Action.MOVE_1, programmingCard1.getAction());
        assertEquals(Action.ROTATE_LEFT, programmingCard2.getAction());
    }
    @Test
    public void testGetProgrammingCardValue() {
        assertEquals(5, programmingCard1.getPriority());
        assertEquals(234, programmingCard2.getPriority());
        assertEquals(2334, programmingCard3.getPriority());
    }
}
