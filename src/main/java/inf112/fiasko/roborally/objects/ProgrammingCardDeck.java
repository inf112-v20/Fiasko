package inf112.fiasko.roborally.objects;

import java.util.ArrayList;

/**
 * This class represents a deck containing programming cards
 */
public class ProgrammingCardDeck extends Deck<ProgrammingCard> {

    /**
     * Initializes the PlayerDeck with a list of cards
     * @param cardList list of programing cards
     */
    public ProgrammingCardDeck(ArrayList<ProgrammingCard> cardList) {
        super(cardList);
    }
}
