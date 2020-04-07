package inf112.fiasko.roborally.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import inf112.fiasko.roborally.element_properties.RobotID;
import inf112.fiasko.roborally.networking.containers.ErrorResponse;
import inf112.fiasko.roborally.utility.NetworkUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a Robo Rally Server
 */
public class RoboRallyServer {
    private final Server server;
    private RoboRallyServerListener listener;

    public RoboRallyServer() throws IOException {
        server = new Server();
        server.start();
        NetworkUtil.registerClasses(server.getKryo());
        server.bind(54555, 54777);
        listener = new RoboRallyServerListener();
        server.addListener(listener);
    }

    /**
     * Gets a map between connections and their robot id
     * @return A mapping between connections and robot ids
     */
    public Map<Connection, RobotID> getRobotID() {
        return listener.getRobotID();
    }

    /**
     * Gets a map between connections and their player name
     * @return A mapping between connections and robot ids
     */
    public Map<Connection, String> getPlayerNames() {
        return listener.getPlayerNames();
    }
    /**
     * Sends an object to all clients
     * @param object The object to send
     */
    public void sendToAllClients(Object object) {
        server.sendToAllTCP(object);
    }
}

/**
 * This listener handles all sending and responses for the server
 */
class RoboRallyServerListener extends Listener {
    private Connection host;
    private final Map<Connection, RobotID> clients;
    private final Map<Connection, String> playerNames;

    /**
     * Instantiates a new Robo Rally server listener
     */
    RoboRallyServerListener() {
        super();
        clients = new HashMap<>();
        playerNames = new HashMap<>();
    }

    /**
     * Gets a map between connections and their player name
     * @return A mapping between connections and robot ids
     */
    Map<Connection, String> getPlayerNames() {
        return playerNames;
    }

    /**
     * Gets a map between connections and their robot id
     * @return A mapping between connections and robot ids
     */
    Map<Connection, RobotID> getRobotID() {
        return clients;
    }

    @Override
    public void received (Connection connection, Object object) {
        if (object instanceof String) {
            String playerName = (String) object;
            if (playerNames.values().contains(playerName)) {
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