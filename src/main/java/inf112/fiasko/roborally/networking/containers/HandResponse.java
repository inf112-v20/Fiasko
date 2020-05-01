package inf112.fiasko.roborally.networking.containers;

import inf112.fiasko.roborally.objects.ProgrammingCardDeck;

/**
 * A response containing the client's new hand
 */
public class HandResponse {
    private ProgrammingCardDeck newHand;
    private ProgrammingCardDeck extraCards;

    /**
     * Empty constructor required by KryoNet. DO NOT REMOVE THIS!!!
     */
    public HandResponse() {
    }

    /**
     * Instantiates a new programming card deck response
     *
     * @param newHand    The new hand of the client
     * @param extraCards Extra cards necessary if the robot has no program to lock
     */
    public HandResponse(ProgrammingCardDeck newHand, ProgrammingCardDeck extraCards) {
        this.newHand = newHand;
        this.extraCards = extraCards;
    }

    /**
     * Gets the hand contained within the response
     *
     * @return The client's new hand
     */
    public ProgrammingCardDeck getNewHand() {
        return this.newHand;
    }

    /**
     * Gets the extra cards contained within the response
     *
     * @return Extra cards necessary to lock registers
     */
    public ProgrammingCardDeck getExtraCards() {
        return this.extraCards;
    }
}
