package inf112.fiasko.roborally.objects;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private ArrayList<ProgrammingCard> cardDeck;

    public Deck(ArrayList<ProgrammingCard> cardDeck){
        this.cardDeck=cardDeck;
    }

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
    public ProgrammingCard drawCard(){
        ProgrammingCard draw = cardDeck.get(0);
        cardDeck.remove(0);
        return draw;
    }
    
    public void drawNCardsFromOtherDeck(int n, Deck otherDeck){
        if (n<1 || n>otherDeck.getCards().size()){
            throw new IllegalArgumentException("cant draw negativ cards or more cards then are in the deck");
        }
        else{
            for (int i=0;i<n;i++){
                cardDeck.add(otherDeck.drawCard());
            }
        }
    }

    public void drawCardOtherDeck(Deck otherCardDeck){
        cardDeck.add(otherCardDeck.drawCard());
    }

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

    public ArrayList<ProgrammingCard> getCards(){
            return cardDeck;
    }

    public Boolean isEmpty(){
        return cardDeck.isEmpty();
    }
    public int size(){
        return cardDeck.size();
    }

}

