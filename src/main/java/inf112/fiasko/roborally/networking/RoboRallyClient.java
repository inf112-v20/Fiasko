package inf112.fiasko.roborally.networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.fiasko.roborally.game_wrapper.RoboRallyWrapper;
import inf112.fiasko.roborally.networking.containers.ErrorResponse;
import inf112.fiasko.roborally.networking.containers.GameStartInfo;
import inf112.fiasko.roborally.objects.RoboRallyGame;
import inf112.fiasko.roborally.utility.NetworkUtil;

import java.io.IOException;

/**
 * This class represents a client capable of connecting to a Robo Rally server
 */
public class RoboRallyClient {
    private Client client;

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

/**
 * This listener handles all receiving from the server
 */
class RoboRallyClientListener extends Listener {
    private RoboRallyWrapper wrapper;

    RoboRallyClientListener(RoboRallyWrapper wrapper) {
        super();
        this.wrapper = wrapper;
    }
    @Override
    public void received (Connection connection, Object object) {
        if (object instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) object;
            System.out.println(errorResponse.getErrorMessage());
        } else if (object instanceof GameStartInfo) {
            GameStartInfo info = (GameStartInfo) object;
            wrapper.roboRallyGame = new RoboRallyGame(info.getPlayerlist(), info.getBoardname(),
                    wrapper.server != null);
        }
    }
}

