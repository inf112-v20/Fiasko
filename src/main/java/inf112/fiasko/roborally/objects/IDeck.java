package inf112.fiasko.roborally.objects;

import java.util.List;

/**
 * Describes a deck
 */
public interface IDeck {

    /**
     * Shuffles the order of the cards in the deck
     */
    void shuffle();

    /**
     * Draws one card from the top of another deck
     * @param other The deck to draw the card from
     */
    void draw(IDeck other);

    /**
     * Draws n cards from the top of another deck
     * @param other The other deck to draw from
     * @param n The number of cards to draw
     */
    void draw(IDeck other, int n);

    /**
     * Moves all cards in this deck into another deck
     * @param other The deck to move this deck's cards into
     */
    void emptyInto(IDeck other);

    /**
     * Whether this deck is empty
     * @return True if this deck is currently empty
     */
    boolean isEmpty();

    /**
     * Gets the number of cards currently in this deck
     * @return The number of cards in this deck
     */
    int size();

    /**
     * Gets a list of all cards in this deck
     *
     * The list should have the correct order according to the actual order within the deck. As an ICardWithoutSuit is
     * immutable, the object reference can be returned directly.
     *
     * @return A list of all cards in this deck
     */
    List<ICardWithoutSuit<Class<?>, Class<?>>> getCards();
}
