package inf112.fiasko.roborally.networking.containers;

/**
 * A request for sending a username to the server
 */
public class UsernameRequest {
    private String username;

    /**
     * Empty constructor required by KryoNet. DO NOT REMOVE THIS!!!
     */
    public UsernameRequest() {

    }

    /**
     * Instantiates a new username request
     *
     * @param username The username the player wants to use
     */
    public UsernameRequest(String username) {
        this.username = username;
    }

    /**
     * Gets the username the user wants to use
     *
     * @return The username the user wants to use
     */
    public String getUsername() {
        return this.username;
    }
}
