package inf112.fiasko.roborally.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents a deck of cards
 */
public abstract class Deck<T> implements IDeck<T> {
    private ArrayList<T> cardDeck;


    /**
     * Initilazes the deck with cards
     * @param cardList list of cards
     */
    public Deck (ArrayList<T> cardList){
        this.cardDeck = cardList;
    }


    /**
     * This method shuffles the cards in the deck so that they are in a random order
     */

    @Override
    public void shuffle() {
        Random randomNumber = new Random();

        for (int i = cardDeck.size() - 1; i > 0; i--) {
            int randomIndex = randomNumber.nextInt(i);

            T CardRandomIndex = cardDeck.get(randomIndex);
            cardDeck.add(randomIndex, cardDeck.get(i));
            cardDeck.remove(randomIndex+1);
            cardDeck.add(i, CardRandomIndex);
            cardDeck.remove(i+1);

        }
    }

    /**
     * draws a card form the other deck and adds it to the current deck
     * @param other The deck to draw the card from
     */
    @Override
    public void draw(IDeck<T> other){
        Deck<T> otherDeck = (Deck) other;
        cardDeck.add(otherDeck.cardDeck.get(0));
        otherDeck.cardDeck.remove(0);
    }

    /**
     * draws n cards from the other deck and adds them to the current deck
     * @param other The other deck to draw from
     * @param n The number of cards to draw
     */
    @Override
    public void draw(IDeck<T> other, int n) {
        Deck<T> otherDeck = (Deck) other;
        if(n<1||n>otherDeck.size()){
            throw new IllegalArgumentException("n cant be below 1 or over the size of the other card deck");
        }
        else {
            for (int i=0; i<n;i++){
                cardDeck.add(otherDeck.cardDeck.get(0));
                otherDeck.cardDeck.remove(0);
            }
        }
    }

    /**
     * emptys the current deck of cards and adds the cards into the other card deck
     * @param other The deck to move this deck's cards into
     */
    @Override
    public void emptyInto(IDeck<T> other) {
        Deck<T> otherDeck = (Deck) other;
        int size = otherDeck.size();
        for (int i=0; i<size;i++){
            otherDeck.draw(this);
        }

    }

    /**
     * checks if the deck is empty
     * @return true if deck is empty false otherwise
     */
    @Override
    public boolean isEmpty() {
        return cardDeck.isEmpty();
    }

    /**
     * gets the size of the current deck
     * @return size of the deck
     */
    @Override
    public int size() {
        return cardDeck.size();
    }

    /**
     * gets the list of cards inn this deck
     * @return list of cards inn the deck
     */
    @Override
    public List<T> getCards() {

        ArrayList<T> returnDeck = new ArrayList<>();
        for (T card:cardDeck){
            returnDeck.add(card);
        }
        return returnDeck;

    }


}

