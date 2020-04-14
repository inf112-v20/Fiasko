package inf112.fiasko.roborally.networking;

import com.esotericsoftware.kryonet.Client;
import inf112.fiasko.roborally.gamewrapper.RoboRallyWrapper;
import inf112.fiasko.roborally.utility.NetworkUtil;

import java.io.IOException;

/**
 * This class represents a client capable of connecting to a Robo Rally server
 */
public class RoboRallyClient {
    private final Client client;
    /**
     * Instantiates a new Robo Rally client
     * @param ipAddress The ip address of the server to connect to
     * @param wrapper The Robo Rally wrapper to be used
     * @throws IOException If the server cannot be reached
     */
    public RoboRallyClient(String ipAddress, RoboRallyWrapper wrapper) throws IOException {
        client = new Client();
        client.start();
        NetworkUtil.registerClasses(client.getKryo());
        client.connect(5000, ipAddress, 54555, 54777);

        client.addListener(new RoboRallyClientListener(wrapper));
    }

    /**
     * Sends something to the server
     * @param object The object to send to the server
     */
    public void sendElement(Object object) {
        client.sendTCP(object);
    }
}



