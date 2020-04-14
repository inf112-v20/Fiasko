package inf112.fiasko.roborally.networking;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.fiasko.roborally.elementproperties.RobotID;
import inf112.fiasko.roborally.networking.containers.ErrorResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This listener handles all sending and responses for the server
 */
class RoboRallyServerListener extends Listener {
    private Connection host;
    private final Map<Connection, RobotID> clients;
    private final Map<Connection, String> playerNames;
    private List<Connection> deadPlayers;

    /**
     * Instantiates a new Robo Rally server listener
     */
    RoboRallyServerListener() {
        super();
        clients = new HashMap<>();
        playerNames = new HashMap<>();
        deadPlayers = new ArrayList<>();
    }

    /**
     * Lets the server know what players have lost this game.
     * @param deadRobots List of RobotID
     */
    public void setDeadPlayers(List<RobotID> deadRobots) {
        for (RobotID robotID:deadRobots) {
            for (Connection key:clients.keySet()) {
                if (clients.get(key) == robotID) {
                    deadPlayers.add(key);
                }
            }
        }
    }

    /**
     * Gets a map between connections and their player name
     * @return A mapping between connections and robot ids
     */
    public Map<Connection, String> getPlayerNames() {
        return playerNames;
    }

    /**
     * Gets a map between connections and their robot id
     * @return A mapping between connections and robot ids
     */
    public Map<Connection, RobotID> getRobotID() {
        return clients;
    }

    @Override
    public void received (Connection connection, Object object) {
        if (object instanceof String) {
            String playerName = (String) object;
            if (playerNames.containsValue(playerName)) {
                String errorMessage = "The player name send is already taken.";
                connection.sendTCP(new ErrorResponse(errorMessage, new IllegalArgumentException(errorMessage)));
            } else {
                playerNames.put(connection, playerName);
            }
        }
    }

    @Override
    public void connected(Connection connection) {
        //The first client to connect is assumed to be the host
        if (host == null) {
            host = connection;
        }
        //Prevents more than 8 players from connecting at once
        if (clients.size() >= 8) {
            String errorMessage = "The server already has 8 players. You cannot join.";
            connection.sendTCP(new ErrorResponse(errorMessage, new IOException(errorMessage)));
            connection.close();
            return;
        }
        clients.put(connection, RobotID.getRobotIDFromID(clients.size() + 1));
        System.out.println(connection.getRemoteAddressTCP() + " connected");
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println(connection.getRemoteAddressTCP() + " disconnected");
    }
}