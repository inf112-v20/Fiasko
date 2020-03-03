package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Action;

/**
 * This class represents a programming card
 */
public class ProgrammingCard implements Comparable<ProgrammingCard> {

    private final int cardPriority;
    private final Action cardAction;

    /**
     * Initializes the priority and the action of the card
     * @param cardPriority the priority of the card
     * @param cardAction the action of the card
     */
    public ProgrammingCard(int cardPriority, Action cardAction) {
        this.cardPriority = cardPriority;
        this.cardAction = cardAction;
    }

    /**
     * Gets the priority of the programming card
     * @return The programming card priority
     */
    public int getPriority() {
        return cardPriority;
    }

    /**
     * Gets the action of the programming card
     * @return The programming card action
     */
    public Action getAction() {
        return cardAction;
    }

    @Override
    public String toString() {
        return this.getPriority() + " " + this.cardAction.toString();
    }

    @Override
    public int compareTo(ProgrammingCard programmingCard) {
        return this.cardPriority - programmingCard.cardPriority;
    }
}
