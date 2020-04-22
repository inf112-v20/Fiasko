package inf112.fiasko.roborally.networking.containers;

/**
 * This class represents a response saying that something went wrong with the request
 */
public class ErrorResponse {
    private String errorMessage;
    private boolean critical;

    /**
     * Constructs a new error response
     *
     * @param errorMessage The error message describing the error
     */
    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
        this.critical = false;
    }

    public ErrorResponse() {

    }

    /**
     * Constructs a new error response
     *
     * @param errorMessage The error message describing the error
     * @param critical     Whether the error is critical
     */
    public ErrorResponse(String errorMessage, boolean critical) {
        this.errorMessage = errorMessage;
        this.critical = critical;
    }

    /**
     * Gets the error message attached to the error response
     *
     * @return An error message
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * Gets whether the error is critical or not
     *
     * @return True if the error is critical. False otherwise
     */
    public boolean isCritical() {
        return this.critical;
    }
}
