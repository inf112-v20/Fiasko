package inf112.fiasko.roborally.networking;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.fiasko.roborally.elementproperties.RobotID;
import inf112.fiasko.roborally.networking.containers.ErrorResponse;
import inf112.fiasko.roborally.networking.containers.HurryResponse;
import inf112.fiasko.roborally.networking.containers.OkayResponse;
import inf112.fiasko.roborally.networking.containers.PowerDownContainerResponse;
import inf112.fiasko.roborally.networking.containers.ProgramAndPowerdownRequest;
import inf112.fiasko.roborally.networking.containers.ProgramsContainerResponse;
import inf112.fiasko.roborally.networking.containers.UsernameRequest;
import inf112.fiasko.roborally.objects.ProgrammingCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This listener handles all sending and responses for the server
 */
class RoboRallyServerListener extends Listener {
    private final Map<Connection, RobotID> clients;
    private final Map<Connection, String> playerNames;
    private final List<Connection> deadPlayers;
    private final RoboRallyServer server;
    private Connection host;
    private Map<Connection, Boolean> stayInPowerDown;
    private Map<Connection, ProgramAndPowerdownRequest> programs;
    private boolean gameStarted = false;

    /**
     * Instantiates a new Robo Rally server listener
     *
     * @param server The Robo Rally server using the listener
     */
    RoboRallyServerListener(RoboRallyServer server) {
        super();
        clients = new HashMap<>();
        playerNames = new HashMap<>();
        deadPlayers = new ArrayList<>();
        stayInPowerDown = new HashMap<>();
        programs = new HashMap<>();
        this.server = server;
    }

    /**
     * Lets the server know which players have lost this game.
     *
     * @param deadRobots List of RobotID
     */
    public void setDeadPlayers(List<RobotID> deadRobots) {
        for (RobotID robotID : deadRobots) {
            for (Connection key : clients.keySet()) {
                if (clients.get(key) == robotID) {
                    deadPlayers.add(key);
                }
            }
        }
    }

    /**
     * Does necessary cleanup of dangling connections before game is started
     *
     * <p>Disconnects all connections which have not yet sent a player name.</p>
     */
    public void startGame() {
        List<Connection> connectionsToDrop = new ArrayList<>();
        for (Connection connection : clients.keySet()) {
            if (playerNames.get(connection) == null) {
                connectionsToDrop.add(connection);
            }
        }
        for (Connection connection : connectionsToDrop) {
            clients.remove(connection);
            deadPlayers.add(connection);
            connection.close();
        }
        this.gameStarted = true;
    }

    /**
     * Gets a map between connections and their player name
     *
     * @return A mapping between connections and robot ids
     */
    public Map<Connection, String> getPlayerNames() {
        Map<Connection, String> alivePlayers = new HashMap<>();
        for (Connection connection : playerNames.keySet()) {
            if (!deadPlayers.contains(connection)) {
                alivePlayers.put(connection, playerNames.get(connection));
            }
        }
        return alivePlayers;
    }

    /**
     * Gets a map between connections and their robot id
     *
     * @return A mapping between connections and robot ids
     */
    public Map<Connection, RobotID> getRobotID() {
        return clients;
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof UsernameRequest) {
            receivedUsername(connection, (UsernameRequest) object);
        } else if (object instanceof Boolean) {
            receiveContinuePowerDown(connection, (Boolean) object);
        } else if (object instanceof ProgramAndPowerdownRequest) {
            receiveProgramAndPowerDownRequest(connection, (ProgramAndPowerdownRequest) object);
        }
    }

    /**
     * Handles the receiving of a string, handled as a player name in this context
     *
     * @param connection The connection sending the string
     * @param request    The username request received
     */
    private void receivedUsername(Connection connection, UsernameRequest request) {
        String playerName = request.getUsername();
        if (playerNames.containsValue(playerName)) {
            connection.sendTCP(new ErrorResponse("The player playerName sent is already taken."));
        } else {
            connection.sendTCP(new OkayResponse());
            playerNames.put(connection, playerName);
        }
    }

    /**
     * Handles the receiving of continuing power down
     *
     * @param connection The connection sending the stay in power down value
     * @param bool       The stay in power down value received
     */
    private void receiveContinuePowerDown(Connection connection, Boolean bool) {
        stayInPowerDown.put(connection, bool);
        connection.sendTCP(new OkayResponse());
        if (receivedDataFromAllConnections(stayInPowerDown)) {
            Map<String, Boolean> powerDowns = new HashMap<>();
            for (Connection connected : stayInPowerDown.keySet()) {
                powerDowns.put(playerNames.get(connected), stayInPowerDown.get(connected));
            }
            server.sendToAllClients(new PowerDownContainerResponse(powerDowns));
            stayInPowerDown = new HashMap<>();
        }
    }

    /**
     * Handles the receiving of a player's program and whether they want to power down
     *
     * @param connection The connection sending the program and power down request
     * @param request    The program and power down request received
     */
    private void receiveProgramAndPowerDownRequest(Connection connection, ProgramAndPowerdownRequest request) {
        programs.put(connection, request);
        connection.sendTCP(new OkayResponse());
        if (receivedDataFromAllConnections(programs)) {
            Map<String, Boolean> powerDown = new HashMap<>();
            Map<String, List<ProgrammingCard>> program = new HashMap<>();
            for (Connection connected : programs.keySet()) {
                powerDown.put(playerNames.get(connected), programs.get(connected).getPowerdown());
                program.put(playerNames.get(connected), programs.get(connected).getProgram());
            }
            server.sendToAllClients(new ProgramsContainerResponse(program, powerDown));
            programs = new HashMap<>();
        } else {
            List<Connection> notReceivedFrom = playersNotYetReceivedFrom(programs);
            if (notReceivedFrom.size() != 1) {
                return;
            }
            Connection hurryUp = notReceivedFrom.get(0);
            hurryUp.sendTCP(new HurryResponse());
        }
    }

    /**
     * Checks whether the input map contains data received by all expected connections
     *
     * @param data A map between connections and some type of data
     * @param <K>  The type of the data contained in the map
     * @return True if information has been received by all alive players
     */
    private <K> boolean receivedDataFromAllConnections(Map<Connection, K> data) {
        Set<Connection> connections = clients.keySet();
        connections.removeAll(deadPlayers);
        return data.keySet().containsAll(connections);
    }

    /**
     * Gets a list containing all connections the data has not been received from
     *
     * @param data The data map to check
     * @param <K>  The type of data contained in the map
     * @return All active connections for which the map has no data
     */
    private <K> List<Connection> playersNotYetReceivedFrom(Map<Connection, K> data) {
        Set<Connection> connections = clients.keySet();
        connections.removeAll(deadPlayers);
        connections.removeAll(data.keySet());
        return new ArrayList<>(connections);
    }

    @Override
    public void connected(Connection connection) {
        //Prevent players from joining after the game has started
        if (gameStarted) {
            connection.close();
            return;
        }
        //The first client to connect is assumed to be the host
        if (host == null) {
            host = connection;
        }
        //Prevents more than 8 players from connecting at once
        if (clients.size() >= 8) {
            String errorMessage = "The server already has 8 players. You cannot join.";
            connection.sendTCP(new ErrorResponse(errorMessage));
            connection.close();
            return;
        }
        clients.put(connection, RobotID.getRobotIDFromID(clients.size() + 1));
        System.out.println(connection.getRemoteAddressTCP() + " connected");
    }

    @Override
    public void disconnected(Connection connection) {
        if (deadPlayers.contains(connection) || !clients.containsKey(connection) ||
                !playerNames.containsKey(connection)) {
            //Remove all traces of the player ever existing
            clients.remove(connection);
            playerNames.remove(connection);
            deadPlayers.remove(connection);
            stayInPowerDown.remove(connection);
            programs.remove(connection);
        } else {
            //Stop the game for all players
            String errorMessage = "An active player disconnected from the game. Cannot continue. Shutting down.";
            server.sendToAllClients(new ErrorResponse(errorMessage, true));
        }
        System.out.println(connection.getRemoteAddressTCP() + " disconnected");
    }
}