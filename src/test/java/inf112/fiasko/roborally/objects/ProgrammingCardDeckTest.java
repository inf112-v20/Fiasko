package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Action;
import inf112.fiasko.roborally.utility.DeckLoaderUtil;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgrammingCardDeckTest {
    private ProgrammingCard programmingCard1;
    private ProgrammingCard programmingCard3;
    private IDeck<ProgrammingCard> testDeck;
    private IDeck<ProgrammingCard> testDeck2;
    private IDeck<ProgrammingCard> fullDeck;

    @Before
    public void setUp() {
        try {
            fullDeck = DeckLoaderUtil.loadProgrammingCardsDeck();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<ProgrammingCard> cardList = new ArrayList<>();
        programmingCard1 = new ProgrammingCard(5, Action.MOVE_1);
        ProgrammingCard programmingCard2 = new ProgrammingCard(6, Action.MOVE_2);
        programmingCard3 = new ProgrammingCard(7, Action.MOVE_3);
        cardList.add(programmingCard1);
        cardList.add(programmingCard2);
        cardList.add(programmingCard3);
        testDeck = new ProgrammingCardDeck(cardList);
        testDeck2 = new ProgrammingCardDeck(cardList);
    }

    @Test
    public void testSize() {
        assertEquals(3,testDeck.size());
        testDeck.emptyInto(testDeck2);
        assertEquals(0,testDeck.size());
    }

    @Test
    public void testDrawCard() {
        assertEquals(3,testDeck.size());
        assertEquals(3,testDeck2.size());
        testDeck.draw(testDeck2);
        assertEquals(4,testDeck.size());
        assertEquals(2,testDeck2.size());
    }

    @Test
    public void testDrawMultipleCards() {
        assertEquals(3, testDeck.size());
        assertEquals(3, testDeck2.size());
        testDeck.draw(testDeck2, 3);
        assertEquals(6, testDeck.size());
        assertEquals(0, testDeck2.size());
    }

    @Test
    public void testEmptyInto() {
        assertEquals(3, testDeck.size());
        assertEquals(3, testDeck2.size());
        testDeck.emptyInto(testDeck2);
        assertEquals(0, testDeck.size());
        assertEquals(6, testDeck2.size());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(testDeck.isEmpty());
        testDeck.emptyInto(testDeck2);
        assertTrue(testDeck.isEmpty());
    }

    @Test
    public void testGetCards() {
        testDeck2.emptyInto(testDeck);
        assertEquals(programmingCard1, testDeck.getCards().get(0));
        assertEquals(programmingCard3, testDeck.getCards().get(2));
        assertEquals(programmingCard3, testDeck.getCards().get(5));
    }

    @Test
    public void testShuffle() {
        List<ProgrammingCard> beforeShuffle = testDeck.getCards();
        for (int i = 0; i < 10; i++) {
            testDeck.shuffle();
            List<ProgrammingCard> afterShuffle = testDeck.getCards();
            if (!beforeShuffle.equals(afterShuffle)) {
                return;
            }
        }
        fail();
    }

    @Test
    public void testShuffleIntegrity() {
        //Checks that no cards disappear or are duplicated during shuffle
        List<ProgrammingCard> cards = fullDeck.getCards();
        fullDeck.shuffle();
        assertEquals(cards.size(), fullDeck.size());
        assertTrue(cards.containsAll(fullDeck.getCards()));
        assertTrue(fullDeck.getCards().containsAll(cards));
    }

    @Test
    public void peekTop() {
        assertEquals(programmingCard1, testDeck.peekTop());
    }

    @Test
    public void peekBottom() {
        assertEquals(programmingCard3, testDeck.peekBottom());
    }
}
