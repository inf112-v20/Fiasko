package inf112.fiasko.roborally.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents a deck of cards
 */
public abstract class AbstractDeck<T> implements Deck<T> {
    private final List<T> cardList;

    public AbstractDeck() {
        this.cardList = new ArrayList<>();
    }

    /**
     * Initializes the deck with cards
     *
     * @param cardList list of cards
     */
    public AbstractDeck(List<T> cardList) {
        this.cardList = new ArrayList<>(cardList);
    }

    /**
     * Randomises the order of the deck
     */
    @Override
    public void shuffle() {
        Random randomGenerator = new Random();
        int deckSize = cardList.size();
        int halfDeckSize = deckSize / 2;
        int timesToShuffle = 30 * deckSize;
        for (int i = 0; i < timesToShuffle; i++) {
            int oldPosition = randomGenerator.nextInt(halfDeckSize);
            int newPosition = randomGenerator.nextInt(deckSize - halfDeckSize) + halfDeckSize;
            cardList.add(newPosition, cardList.remove(oldPosition));
        }
    }

    /**
     * Draws one card from the other deck
     *
     * @param other The deck to draw the card from
     */
    @Override
    public void draw(Deck<T> other) {
        AbstractDeck<T> otherDeck = (AbstractDeck<T>) other;
        cardList.add(otherDeck.cardList.remove(0));
    }

    /**
     * Draws multiple cards from the other deck
     *
     * @param other The other deck to draw from
     * @param n     The number of cards to draw
     */
    @Override
    public void draw(Deck<T> other, int n) {
        AbstractDeck<T> otherDeck = (AbstractDeck<T>) other;
        if (n < 0 || n > otherDeck.size()) {
            throw new IllegalArgumentException("n can't be below 0 or over the size of the other card deck");
        }
        for (int i = 0; i < n; i++) {
            draw(other);
        }
    }

    /**
     * Empty the entire deck into the other deck
     *
     * @param other The deck to move this deck's cards into
     */
    @Override
    public void emptyInto(Deck<T> other) {
        AbstractDeck<T> otherDeck = (AbstractDeck<T>) other;
        otherDeck.draw(this, this.size());
    }

    /**
     * Checks if the deck is empty
     *
     * @return Boolean for if the deck is empty
     */
    @Override
    public boolean isEmpty() {
        return cardList.isEmpty();
    }

    /**
     * Gets the size of the deck
     *
     * @return int size of the deck
     */
    @Override
    public int size() {
        return cardList.size();
    }

    /**
     * Gets a list of all the cards in the deck
     *
     * @return ArrayList of cards from the deck
     */
    @Override
    public List<T> getCards() {
        return new ArrayList<>(cardList);
    }

    /**
     * Gets the card from the deck in String format
     *
     * @return String the cards from the deck
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (T card : cardList) {
            builder.append(card.toString()).append("\n");
        }
        return builder.toString();
    }

    /**
     * Looks at the top card in the deck
     *
     * @return ProgrammingCard the first card in the deck
     */
    @Override
    public T peekTop() {
        return cardList.get(0);
    }

    /**
     * Looks at the bottom card of the deck
     *
     * @return ProgrammingCard the last card in the deck
     */
    @Override
    public T peekBottom() {
        return cardList.get(size() - 1);
    }
}

