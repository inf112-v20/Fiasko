package inf112.fiasko.roborally.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents a deck of cards
 */
public abstract class Deck<T> implements IDeck<T> {
    private final List<T> cardList;

    /**
     * Initializes the deck with cards
     * @param cardList list of cards
     */
    public Deck (List<T> cardList) {
        this.cardList = new ArrayList<>(cardList);
    }

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

    @Override
    public void draw(IDeck<T> other) {
        Deck<T> otherDeck = (Deck<T>) other;
        cardList.add(otherDeck.cardList.remove(0));
    }

    @Override
    public void draw(IDeck<T> other, int n) {
        Deck<T> otherDeck = (Deck<T>) other;
        if (n < 1 || n > otherDeck.size()) {
            throw new IllegalArgumentException("n can't be below 1 or over the size of the other card deck");
        }
        for (int i = 0; i < n; i++) {
            draw(other);
        }
    }

    @Override
    public void emptyInto(IDeck<T> other) {
        Deck<T> otherDeck = (Deck<T>) other;
        otherDeck.draw(this, this.size());
    }

    @Override
    public boolean isEmpty() {
        return cardList.isEmpty();
    }

    @Override
    public int size() {
        return cardList.size();
    }

    @Override
    public List<T> getCards() {
        return new ArrayList<>(cardList);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (T card : cardList) {
            builder.append(card.toString()).append("\n");
        }
        return builder.toString();
    }
}

