package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Action;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.*;


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
        assertEquals(false,testDeck.isEmpty());
        testDeck.emptyInto(testDeck2);
        assertEquals(true,testDeck.isEmpty());
    }

    @Test
    public void testGetCards(){
        testDeck2.emptyInto(testDeck);
        assertEquals(programmingCard1,testDeck.getCards().get(0));
        assertEquals(programmingCard3,testDeck.getCards().get(2));
        assertEquals(programmingCard6,testDeck.getCards().get(5));
    }

    @Test
    public void testShuffle(){
        Boolean atLeastOneShuffle = false;
        ArrayList<Boolean> resultList = new ArrayList<>();
        ArrayList<ProgrammingCard> beforeShuffle = (ArrayList<ProgrammingCard>)testDeck.getCards();

        for (int i = 0; i < 10; i++){ //Saves result of ten shuffles
            testDeck.shuffle();
            ArrayList<ProgrammingCard> afterShuffle = (ArrayList<ProgrammingCard>)testDeck.getCards();
            if (beforeShuffle != afterShuffle) {
                resultList.add(true);
            }
            else {
                resultList.add(false);
            }

        }
        //Looks to see if at least one shuffle is different from before
        for (int i = 0; i < resultList.size(); i++) {
            if (resultList.get(i)==true) {
                atLeastOneShuffle = true;
            }

        }
        assertTrue(atLeastOneShuffle);

    }


}
