package inf112.fiasko.roborally.networking.containers;

import inf112.fiasko.roborally.objects.Player;

import java.util.List;

/**
 * This class contains information about the game board to be used and the game's players
 */
public class GameStartInfoResponse {
    private String boardName;
    private List<Player> playerList;
    private String playerName;

    /**
     * Empty constructor required by KryoNet. DO NOT REMOVE THIS!!!
     */
    public GameStartInfoResponse() {
    }

    /**
     * Instantiates a new GameStartInfoResponse object
     *
     * @param boardName  The name of the board to be used, with extension
     * @param playerList List of players for the game
     * @param playerName The player name of the receiver
     */
    public GameStartInfoResponse(String boardName, List<Player> playerList, String playerName) {
        this.playerName = playerName;
        this.boardName = boardName;
        this.playerList = playerList;
    }

    /**
     * Gets the player name of the current player
     *
     * @return The player name of the current player
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Gets the list of players
     *
     * @return A list of players
     */
    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Gets the board name
     *
     * @return The board name
     */
    public String getBoardName() {
        return boardName;
    }

}
