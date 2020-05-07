package inf112.fiasko.roborally.utility;

/**
 * This class helps with tasks related to string manipulation
 */
public final class StringUtil {

    private StringUtil() {
    }

    /**
     * Adds zeros to a number until it reaches a set length and converts it to a string
     *
     * @param number The number to add zeros to
     * @param zeros  The number of characters in the output
     * @return The number as a string with necessary leading zeros
     */
    public static String addLeadingZeros(int number, int zeros) {
        StringBuilder numberAsString = new StringBuilder(String.valueOf(number));
        for (int i = numberAsString.length(); i < zeros; i++) {
            numberAsString.insert(0, "0");
        }
        return numberAsString.toString();
    }

}
