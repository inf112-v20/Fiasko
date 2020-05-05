package inf112.fiasko.roborally.utility;

import inf112.fiasko.roborally.objects.ProgrammingCard;
import inf112.fiasko.roborally.objects.ProgrammingCardDeck;
import inf112.fiasko.roborally.objects.properties.Action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for loading card decks
 */
public final class DeckLoaderUtil {

    /**
     * Returns a programming card deck containing all official programming cards
     *
     * @return A programming card deck with programming cards
     * @throws IOException If the programming cards file is invalid
     */
    public static ProgrammingCardDeck loadProgrammingCardsDeck() throws IOException {
        return loadProgrammingCardsDeck("programming_cards.txt");
    }

    /**
     * Returns a programming card deck containing nine cards to use for testing
     *
     * @return A programming card deck with nine programming cards
     * @throws IOException If the programming cards file is invalid
     */
    public static ProgrammingCardDeck loadProgrammingCardsTestDeck() throws IOException {
        return loadProgrammingCardsDeck("programming_cards_manual_testing.txt");
    }

    /**
     * Loads programming cards from a file
     *
     * @param cardFile The file containing the cards to load
     * @return A deck of programming cards
     * @throws IOException If the programming cards file is invalid
     */
    private static ProgrammingCardDeck loadProgrammingCardsDeck(String cardFile) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                ResourceUtil.getResourceAsInputStream(cardFile)));
        int numberOfCards = Integer.parseInt(reader.readLine());
        List<ProgrammingCard> programmingCardList = new ArrayList<>();
        for (int i = 0; i < numberOfCards; i++) {
            String cardLine = reader.readLine();
            String[] parts = cardLine.split(" ");
            int cardPriority = Integer.parseInt(parts[0]);
            Action cardAction = Action.valueOf(parts[1]);
            programmingCardList.add(new ProgrammingCard(cardPriority, cardAction));
        }
        return new ProgrammingCardDeck(programmingCardList);

    }
}
