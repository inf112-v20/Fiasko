package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Action;

/**
 * This class represents a programming card
 */
public class ProgrammingCard implements ICardWithoutSuit<Integer, Action> {

    private final Integer cardValue;
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

    @Override
    public Integer getValue() {
        return cardValue;
    }

    @Override
    public Action getSymbol() {
        return cardAction;
    }

    @Override
    public String toString() {
        return this.getValue() + " " + this.cardAction.toString();
    }
}
