package inf112.fiasko.roborally.networking;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.fiasko.roborally.elementproperties.RobotID;
import inf112.fiasko.roborally.networking.containers.ErrorResponse;
import inf112.fiasko.roborally.networking.containers.PowerdownContainer;
import inf112.fiasko.roborally.networking.containers.ProgamsContainer;
import inf112.fiasko.roborally.networking.containers.ProgramAndPowerdownRequest;
import inf112.fiasko.roborally.objects.ProgrammingCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This listener handles all sending and responses for the server
 */
class RoboRallyServerListener extends Listener {
    private Connection host;
    private final Map<Connection, RobotID> clients;
    private final Map<Connection, String> playerNames;
    private final List<Connection> deadPlayers;
    private Map<Connection,Boolean> StayInPowerdown = new HashMap<>();
    private Map<Connection,ProgramAndPowerdownRequest> programs = new HashMap<>();
    private RoboRallyServer server;

    /**
     * Instantiates a new Robo Rally server listener
     */
    RoboRallyServerListener(RoboRallyServer server) {
        super();
        clients = new HashMap<>();
        playerNames = new HashMap<>();
        deadPlayers = new ArrayList<>();
        this.server = server;
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
            System.out.println((String) object);
            recivedString(connection,(String) object);
        }
        else if(object instanceof Boolean){
            recivedContinuePowerdown(connection,(Boolean) object);
        }
        else if(object instanceof ProgramAndPowerdownRequest){
            reciveProgamAndPowerdownRequest(connection,(ProgramAndPowerdownRequest) object);
        }

    }

    private void recivedString(Connection connection,String name){
        String playerName = name;
        if (playerNames.containsValue(playerName)) {
            String errorMessage = "The player name send is already taken.";
            connection.sendTCP(new ErrorResponse(errorMessage, new IllegalArgumentException(errorMessage)));
        } else {
            playerNames.put(connection, playerName);
        }

    }

    private void recivedContinuePowerdown(Connection connection,Boolean bool){
        StayInPowerdown.put(connection,bool);
        if(recivedDataFromAllConnections(StayInPowerdown)){
            Map<String,Boolean> powerdowns = new HashMap<>();
            for (Connection connected:StayInPowerdown.keySet()) {
                powerdowns.put(playerNames.get(connected),StayInPowerdown.get(connected));
            }
            server.sendToAllClients(new PowerdownContainer(powerdowns));
            StayInPowerdown = new HashMap<>();
        }
    }

    private void reciveProgamAndPowerdownRequest(Connection connection,ProgramAndPowerdownRequest request){
        programs.put(connection,request);
        if(recivedDataFromAllConnections(programs)){
            Map<String,Boolean> powerdown = new HashMap<>();
            Map<String,List<ProgrammingCard>> program = new HashMap<>();

            for (Connection connected:programs.keySet()) {
                powerdown.put(playerNames.get(connected),programs.get(connected).getPowerdown());
                program.put(playerNames.get(connected),programs.get(connected).getProgram());

            }
            server.sendToAllClients(new ProgamsContainer(program,powerdown));
            programs = new HashMap<>();

        }
    }
    private<K> boolean recivedDataFromAllConnections(Map<Connection,K> data){
        Set<Connection> connections = clients.keySet();
        connections.removeAll(deadPlayers);
        return connections.containsAll(data.keySet()) && data.keySet().containsAll(connections);
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