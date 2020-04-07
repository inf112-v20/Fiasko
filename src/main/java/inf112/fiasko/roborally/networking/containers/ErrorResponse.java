package inf112.fiasko.roborally.networking.containers;

/**
 * This class represents a response saying that something went wrong with the request
 */
public class ErrorResponse {
    private String errorMessage;
    private Exception thrownException;

    /**
     * Constructs a new error response
     * @param errorMessage The error message describing the error
     */
    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Constructs a new error response
     * @param errorMessage The error message describing the error
     * @param thrownException The exception to throw
     */
    public ErrorResponse(String errorMessage, Exception thrownException) {
        this.errorMessage = errorMessage;
        this.thrownException = thrownException;
    }

    /**
     * Gets the error message attached to the error response
     * @return An error message
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * Gets the exception attached to the error response
     * @return An exception or null
     */
    public Exception getThrownException() {
        return this.thrownException;
    }
}
