package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Action;

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

    public void drawCardOtherDeck(Deck otherCardDeck){
        cardDeck.add(otherCardDeck.drawCard());
    }


    public ArrayList<ProgrammingCard> getDeck(){
            return cardDeck;
    }

    public ArrayList<ProgrammingCard> drawAllCard(){
        ArrayList<ProgrammingCard> allCards= new ArrayList<>();
        int cardDecksize = cardDeck.size();
        for (int i=0;i<cardDecksize;i++){
            allCards.add(cardDeck.get((cardDecksize-1)-i));
        }
        for (int i=0; i<cardDecksize;i++){
            cardDeck.remove(0);
        }
        return allCards;
    }



    public static void main(String args[]) //skal fjernes måtte bare se at shuffle virket
    {
        ArrayList<ProgrammingCard> cardDeck = new ArrayList<>();
        cardDeck.add(new ProgrammingCard(3, Action.MOVE_1));
        cardDeck.add(new ProgrammingCard(4, Action.MOVE_1));
        cardDeck.add(new ProgrammingCard(5, Action.MOVE_1));
        cardDeck.add(new ProgrammingCard(6, Action.MOVE_1));

        ArrayList<ProgrammingCard> cardDeck2 = new ArrayList<>();
        cardDeck2.add(new ProgrammingCard(8, Action.MOVE_1));
        cardDeck2.add(new ProgrammingCard(9, Action.MOVE_1));
        cardDeck2.add(new ProgrammingCard(99, Action.MOVE_1));
        cardDeck2.add(new ProgrammingCard(8989, Action.MOVE_1));

        Deck test66 = new Deck(cardDeck2);
        Deck test23 = new Deck(cardDeck);

        System.out.println("drawAllbefore "+test66.getDeck().size());
        System.out.println("drawAll "+test66.drawAllCard().size());
        System.out.println("drawAllafter "+test66.getDeck().size());



        }
        /*
        test.shuffle();
        ArrayList<ProgrammingCard> cardDeckShuffle = test.getDeck();
        System.out.println("size: "+cardDeckShuffle.size());
        for (int i = 0; i< cardDeckShuffle.size();i++ ){
            System.out.println(cardDeckShuffle.get(i).getValue());


        }
        */
}

