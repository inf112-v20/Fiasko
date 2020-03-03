package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Action;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class TestPlayerDeck {
    private ProgrammingCard programmingCard1 = new ProgrammingCard(5, Action.MOVE_1);
    private ProgrammingCard programmingCard2 = new ProgrammingCard(6, Action.MOVE_2);
    private ProgrammingCard programmingCard3 = new ProgrammingCard(7, Action.MOVE_3);
    private ProgrammingCard programmingCard4 = new ProgrammingCard(55, Action.MOVE_1);
    private ProgrammingCard programmingCard5 = new ProgrammingCard(66, Action.MOVE_2);
    private ProgrammingCard programmingCard6 = new ProgrammingCard(756, Action.MOVE_3);
    private ArrayList<ProgrammingCard> cardlist = new ArrayList();
    private ArrayList<ProgrammingCard> cardlist2 = new ArrayList();
    private PlayerDeck testDeck;
    private PlayerDeck testDeck2;
    @Before
    public void setUp() {
        cardlist.add(programmingCard1);
        cardlist.add(programmingCard2);
        cardlist.add(programmingCard3);
        cardlist2.add(programmingCard4);
        cardlist2.add(programmingCard5);
        cardlist2.add(programmingCard6);
        testDeck = new PlayerDeck(cardlist);
        testDeck2 = new PlayerDeck(cardlist2);
    }
    @Test
    public void testDrawCard(){
        assertEquals(3,testDeck.size());
        assertEquals(3,testDeck2.size());
        testDeck.draw(testDeck2);
        assertEquals(4,testDeck.size());
        assertEquals(2,testDeck2.size());
    }


}
