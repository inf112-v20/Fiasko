package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Action;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

public class TestProgrammingCardDeck {
    private final ProgrammingCard programmingCard1 = new ProgrammingCard(5, Action.MOVE_1);
    private final ProgrammingCard programmingCard2 = new ProgrammingCard(6, Action.MOVE_2);
    private final ProgrammingCard programmingCard3 = new ProgrammingCard(7, Action.MOVE_3);
    private final ProgrammingCard programmingCard4 = new ProgrammingCard(55, Action.MOVE_1);
    private final ProgrammingCard programmingCard5 = new ProgrammingCard(66, Action.MOVE_2);
    private final ProgrammingCard programmingCard6 = new ProgrammingCard(756, Action.MOVE_3);
    private final ArrayList<ProgrammingCard> cardlist = new ArrayList<>();
    private final ArrayList<ProgrammingCard> cardlist2 = new ArrayList<>();
    private ProgrammingCardDeck testDeck;
    private ProgrammingCardDeck testDeck2;
    @Before
    public void setUp() {
        cardlist.add(programmingCard1);
        cardlist.add(programmingCard2);
        cardlist.add(programmingCard3);
        cardlist2.add(programmingCard4);
        cardlist2.add(programmingCard5);
        cardlist2.add(programmingCard6);
        testDeck = new ProgrammingCardDeck(cardlist);
        testDeck2 = new ProgrammingCardDeck(cardlist2);
    }
    @Test
    public void testSize(){
        assertEquals(3,testDeck.size());
        testDeck.emptyInto(testDeck2);
        assertEquals(0,testDeck.size());
    }
    @Test
    public void testDrawCard(){
        assertEquals(3,testDeck.size());
        assertEquals(3,testDeck2.size());
        testDeck.draw(testDeck2);
        assertEquals(4,testDeck.size());
        assertEquals(2,testDeck2.size());
    }
    @Test
    public void testDrawMultipulCards(){
        assertEquals(3,testDeck.size());
        assertEquals(3,testDeck2.size());
        testDeck.draw(testDeck2, 3);
        assertEquals(6,testDeck.size());
        assertEquals(0,testDeck2.size());
    }
    @Test
    public void testEmptyInto(){
        assertEquals(3,testDeck.size());
        assertEquals(3,testDeck2.size());
        testDeck.emptyInto(testDeck2);
        assertEquals(0,testDeck.size());
        assertEquals(6,testDeck2.size());
    }
    @Test
    public void testIsEmpty(){
        assertFalse(testDeck.isEmpty());
        testDeck.emptyInto(testDeck2);
        assertTrue(testDeck.isEmpty());
    }

    @Test
    public void testGetCards(){
        testDeck2.emptyInto(testDeck);
        assertEquals(programmingCard1,testDeck.getCards().get(0));
        assertEquals(programmingCard3,testDeck.getCards().get(2));
        assertEquals(programmingCard6,testDeck.getCards().get(5));
    }

    @Test
    public void testshuffle(){
        ProgrammingCard card1 = testDeck.getCards().get(0);
        int noe = card1.getValue();
        ProgrammingCard card2 = testDeck.getCards().get(1);
        int noe2 = card2.getValue();
        ProgrammingCard card3 = testDeck.getCards().get(2);
        int noe3 = card3.getValue();

        System.out.println(noe);
        System.out.println(noe2);
        System.out.println(noe3);

        testDeck.shuffle();

        ProgrammingCard scard1 = testDeck.getCards().get(0);
        int snoe = scard1.getValue();
        ProgrammingCard scard2 = testDeck.getCards().get(1);
        int snoe2 = scard2.getValue();
        ProgrammingCard scard3 = testDeck.getCards().get(2);
        int snoe3 = scard3.getValue();

        System.out.println(snoe);
        System.out.println(snoe2);
        System.out.println(snoe3);
    }
}
