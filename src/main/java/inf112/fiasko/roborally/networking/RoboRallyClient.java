package inf112.fiasko.roborally.networking;

import com.esotericsoftware.kryonet.Client;
import inf112.fiasko.roborally.gamewrapper.RoboRallyWrapper;
import inf112.fiasko.roborally.utility.NetworkUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

/**
 * This class represents a client capable of connecting to a Robo Rally server
 */
public class RoboRallyClient {
    private final Client client;
    private final RoboRallyWrapper wrapper;
    private RoboRallyClientListener listener;

    /**
     * Instantiates a new Robo Rally client
     *
     * @param wrapper The Robo Rally wrapper to be used
     */
    public RoboRallyClient(RoboRallyWrapper wrapper) {
        this.wrapper = wrapper;
        client = new Client();
        client.start();
        NetworkUtil.registerClasses(client.getKryo());
        this.listener = new RoboRallyClientListener(wrapper);
        client.addListener(this.listener);
    }

    /**
     * Connects to a Robo Rally server
     *
     * @param ipAddress The ip address of the server to join
     * @param TCPPort   The TCP port to connect to
     * @param UDPPort   The UDP port to connect to
     * @throws IOException If the server cannot be connected to
     */
    public void connect(String ipAddress, int TCPPort, int UDPPort) throws IOException {
        client.connect(5000, ipAddress, TCPPort, UDPPort);
    }

    /**
     * Gets a list of addresses of local Robo Rally servers
     *
     * @return A list of server ip addresses
     */
    public List<InetAddress> getLanServers() {
        return client.discoverHosts(wrapper.discoverUDPPort, 1000);
    }

    /**
     * Gets the state of the lastly sent request
     *
     * @return The state of the lastly sent request
     */
    public RequestState getLastRequestState() {
        return this.listener.getLastRequestState();
    }

    /**
     * Sends something to the server
     *
     * @param object The object to send to the server
     */
    public void sendElement(Object object) {
        try {
            client.sendTCP(object);
            listener.resetLastRequestState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



