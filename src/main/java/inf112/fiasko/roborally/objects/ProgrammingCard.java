package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Action;

/**
 * This class represents a programming card
 */
public class ProgrammingCard {

    private final int cardValue;
    private final Action cardAction;

    /**
     * Initializes the value and the action of the card
     * @param cardValue the value of the card
     * @param cardAction the action of the card
     */
    public ProgrammingCard(int cardValue, Action cardAction){
        this.cardValue = cardValue;
        this.cardAction = cardAction;
    }

    /**
     * Gets the value of the programming card
     * @return The programming card value
     */
    public int getValue() {
        return cardValue;
    }

    /**
     * Gets the symbol of the programming card
     * @return The programming card symbol
     */
    public Action getSymbol() {
        return cardAction;
    }

    @Override
    public String toString() {
        return this.getValue() + " " + this.cardAction.toString();
    }
}
