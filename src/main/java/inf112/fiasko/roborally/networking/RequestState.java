package inf112.fiasko.roborally.networking;

/**
 * This enum represents states of a request sent to the server
 */
public enum RequestState {
    /**
     * The request has been send to the server, but no response has been received
     */
    SENT_NOT_RECEIVED,
    /**
     * The request has been sent to the server, and the server confirmed the receipt
     */
    SENT_OKAY,
    /**
     * The request has been sent to the server, but the server rejected the request
     */
    SENT_REJECTED,
    /**
     * No request has been sent to the server
     */
    NOT_SENT
}
