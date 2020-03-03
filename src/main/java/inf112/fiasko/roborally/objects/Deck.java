package inf112.fiasko.roborally.objects;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents a deck of cards
 */
public class Deck {
    private ArrayList<ProgrammingCard> cardDeck;

    /**
     * Initalizes the card deck.
     * @param cardDeck a list of starting cards.
     */
    public Deck(ArrayList<ProgrammingCard> cardDeck){
        this.cardDeck=cardDeck;
    }

    /**
     * This method shuffles the cards in the deck so that they are in a random order
     */
    public void shuffle() {
        Random randomNumber = new Random();
        for (int i = cardDeck.size() - 1; i > 0; i--) {
            int index = randomNumber.nextInt(i);

            ProgrammingCard CardIndex = cardDeck.get(index);
            cardDeck.add(index, cardDeck.get(i));
            cardDeck.remove(index+1);
            cardDeck.add(i, CardIndex);
            cardDeck.remove(i+1);

        }
    }

    /**
     * draws the first card in the card deck
     * @return first card in the card deck list
     */
    public ProgrammingCard drawCard(){
        ProgrammingCard draw = cardDeck.get(0);
        cardDeck.remove(0);
        return draw;
    }

    /**
     * draws n cards for another card deck and adds it to this card deck
     * @param n number of cards you want to draw from the other deck
     * @param otherDeck the other card deck
     */
    public void drawNCardsFromOtherDeck(int n, Deck otherDeck){
        if (n<1 || n>otherDeck.getCard().size()){
            throw new IllegalArgumentException("cant draw negativ cards or more cards then are in the other deck");
        }
        else{
            for (int i=0;i<n;i++){
                cardDeck.add(otherDeck.drawCard());
            }
        }
    }

    /**
     * draws the first card from the other deck and adds it to this deck
     * @param otherCardDeck the other card deck
     */
    public void drawCardOtherDeck(Deck otherCardDeck){
        cardDeck.add(otherCardDeck.drawCard());
    }

    /**
     * draws all the cards from this card deck
     * @return returns a list of all the cards from this deck
     */
    public ArrayList<ProgrammingCard> drawAllCard(){
        ArrayList<ProgrammingCard> allCards= new ArrayList<>();
        int cardDeckSize = cardDeck.size();
        for (int i=0;i<cardDeckSize;i++){
            allCards.add(cardDeck.get((cardDeckSize-1)-i));
        }
        for (int i=0; i<cardDeckSize;i++){
            cardDeck.remove(0);
        }
        return allCards;
    }

    /**
     * gives a list of all the cards inn this deck
     * @return a list of all the cards inn this deck
     */
    public ArrayList<ProgrammingCard> getCard(){
            return cardDeck;
    }

    /**
     * checks if this deck is empty
     * @return true if empty false otherwise
     */
    public Boolean isEmpty(){
        return cardDeck.isEmpty();
    }

    /**
     * gets the size of this deck
     * @return size of the deck
     */
    public int size(){
        return cardDeck.size();
    }

}

