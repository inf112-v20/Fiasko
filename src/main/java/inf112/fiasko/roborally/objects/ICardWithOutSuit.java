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

    /**
     * Sets the value of the card
     * @param value card value
     */
    void setValue(S value);

    /**
     * Sets the symbol of the card
     * @param symbol card symbol
     */
    void setSymbol(T symbol);

}
