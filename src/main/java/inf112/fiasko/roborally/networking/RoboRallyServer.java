package inf112.fiasko.roborally.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import inf112.fiasko.roborally.elementproperties.RobotID;
import inf112.fiasko.roborally.utility.NetworkUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This class represents a Robo Rally Server
 */
public class RoboRallyServer {
    private final Server server;
    private RoboRallyServerListener listener;

    /**
     * Instantiates a new Robo Rally server
     *
     * @throws IOException If the server cannot be started
     */
    public RoboRallyServer() throws IOException {
        server = new Server();
        server.start();
        NetworkUtil.registerClasses(server.getKryo());
        server.bind(54555, 54777);
        listener = new RoboRallyServerListener(this);
        server.addListener(listener);
    }

    /**
     * Makes notice of players which are dead and as such should not be expected to send anything
     *
     * @param deadRobotList A list of robot ids of robots which are permanently dead
     */
    public void setDeadPlayers(List<RobotID> deadRobotList) {
        listener.setDeadPlayers(deadRobotList);
    }

    /**
     * Gets a map between connections and their robot id
     *
     * @return A mapping between connections and robot ids
     */
    public Map<Connection, RobotID> getRobotID() {
        return listener.getRobotID();
    }

    /**
     * Gets a map between connections and their player name
     *
     * @return A mapping between connections and robot ids
     */
    public Map<Connection, String> getPlayerNames() {
        return listener.getPlayerNames();
    }

    /**
     * Sends an object to all clients
     *
     * @param object The object to send
     */
    public void sendToAllClients(Object object) {
        server.sendToAllTCP(object);
    }

    /**
     * Sends an object to a specific client
     *
     * @param connection The connection to send the object to
     * @param object     The object to send
     */
    public void sendToClient(Connection connection, Object object) {
        server.sendToTCP(connection.getID(), object);
    }
}

