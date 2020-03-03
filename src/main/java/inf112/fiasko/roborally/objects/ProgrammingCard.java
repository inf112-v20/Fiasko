package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Action;

/**
 * This class represents a programming card
 */

public class ProgrammingCard implements ICardWithoutSuit<Integer, Action> {

    private Integer cardValue;
    private Action cardAction;

    /**
     * Initializes the value and the action of the card
     * @param cardValue the value of the card
     * @param cardAction the action of the card
     */
    public ProgrammingCard(int cardValue,Action cardAction){
        this.cardValue=cardValue;
        this.cardAction=cardAction;
    }

    /**
     * Returns the value of the card
     * @return the value of the card
     */
    @Override
    public Integer getValue() {
        return cardValue;
    }

    /**
     * Returns the action the card should perform
     * @return the action of the card
     */
    @Override
    public Action getSymbol() {
        return cardAction;
    }
}
