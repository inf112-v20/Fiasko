package inf112.fiasko.roborally.objects;

/**
 * A class which can save a tuple with two values
 *
 * @param <T> The type of the first value
 * @param <K> The type of the second value
 */
public class TwoTuple<T, K> {
    public final T value1;
    public final K value2;

    /**
     * Instantiates a new 2-tuple
     *
     * @param value1 The first value of the tuple
     * @param value2 The second value of the tuple
     */
    public TwoTuple(T value1, K value2) {
        this.value1 = value1;
        this.value2 = value2;
    }
}
