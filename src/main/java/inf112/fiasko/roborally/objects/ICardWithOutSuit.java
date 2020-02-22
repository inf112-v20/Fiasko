package inf112.fiasko.roborally.objects;

/**
 * This Interface describes a card
 * @param <S> the type for the card value
 * @param <T> the type for the symbol
 */
public interface ICardWithOutSuit<S,T> {
    /**
     * Gets the value of the card
     * @return card value
     */
    S getValue();

    /**
     * Gets the symbol of the card
     * @return card symbol
     */
    T getSymbol();

}
