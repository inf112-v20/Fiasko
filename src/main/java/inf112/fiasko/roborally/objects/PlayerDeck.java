package inf112.fiasko.roborally.objects;

import java.util.ArrayList;

public class PlayerDeck<ProgrammingCard> extends Deck<ProgrammingCard> {

    /**
     * initalizes the PlayerDeck with a list of cards
     * @param cardlist list of programing cards
     */
    public PlayerDeck(ArrayList<ProgrammingCard> cardlist) {
        super(cardlist);
    }

}
