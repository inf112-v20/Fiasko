package inf112.fiasko.roborally.objects;

/**
 * This Interface describes a card without a card suit
 * @param <S> The value type
 * @param <T> The symbol type
 */
public interface ICardWithoutSuit<S,T> {
    /**
     * Gets the value of the card
     * @return The card value
     */
    S getValue();

    /**
     * Gets the symbol of the card
     * @return The card symbol
     */
    T getSymbol();
}
