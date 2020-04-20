package inf112.fiasko.roborally.objects;

import java.util.List;

/**
 * Describes a deck
 *
 * <p>Any card stored in the deck is assumed to be immutable. If it's not, the integrity of the deck cannot be
 * guaranteed.</p>
 */
public interface Deck<T> {

    /**
     * Shuffles the order of the cards in the deck
     */
    void shuffle();

    /**
     * Draws one card from the top of another deck
     *
     * @param other The deck to draw the card from
     */
    void draw(Deck<T> other);

    /**
     * Draws n cards from the top of another deck
     *
     * @param other The other deck to draw from
     * @param n     The number of cards to draw
     */
    void draw(Deck<T> other, int n);

    /**
     * Moves all cards in this deck into another deck
     *
     * @param other The deck to move this deck's cards into
     */
    void emptyInto(Deck<T> other);

    /**
     * Whether this deck is empty
     *
     * @return True if this deck is currently empty
     */
    boolean isEmpty();

    /**
     * Gets the number of cards currently in this deck
     *
     * @return The number of cards in this deck
     */
    int size();

    /**
     * Takes a peek at the card currently at the top of the deck
     *
     * @return The card at the top of the deck
     */
    T peekTop();

    /**
     * Takes a peek at the card currently at the bottom of the deck
     *
     * @return The card at the bottom of the deck
     */
    T peekBottom();

    /**
     * Gets a list of all cards in this deck
     *
     * <p>The list should have the correct order according to the actual order within the deck.</p>
     *
     * @return A list of all cards in this deck
     */
    List<T> getCards();
}
