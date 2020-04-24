package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.elementproperties.Action;

/**
 * This class represents a programming card
 */
public class ProgrammingCard implements Comparable<ProgrammingCard> {

    private int cardPriority;
    private Action cardAction;

    /**
     * Initializes the priority and the action of the card
     *
     * @param cardPriority the priority of the card
     * @param cardAction   the action of the card
     */
    public ProgrammingCard(int cardPriority, Action cardAction) {
        if (cardPriority < 10 || cardPriority > 840 || cardPriority % 10 != 0) {
            throw new IllegalArgumentException("You cannot create a programming card not part of the original game.");
        }
        this.cardPriority = cardPriority;
        this.cardAction = cardAction;
    }

    /**
     * Empty constructor required by KryoNet. DO NOT REMOVE THIS!!!
     */
    public ProgrammingCard() {
    }

    /**
     * Gets the priority of the programming card
     *
     * @return The programming card priority
     */
    public int getPriority() {
        return cardPriority;
    }

    /**
     * Gets the action of the programming card
     *
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
    public boolean equals(Object other) {
        if (!(other instanceof ProgrammingCard)) {
            return false;
        }
        ProgrammingCard otherCard = (ProgrammingCard) other;
        return otherCard.cardAction == this.cardAction && otherCard.cardPriority == this.cardPriority;
    }

    @Override
    public int compareTo(ProgrammingCard programmingCard) {
        return programmingCard.cardPriority - this.cardPriority;
    }
}
