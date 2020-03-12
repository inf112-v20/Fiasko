package inf112.fiasko.roborally.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import inf112.fiasko.roborally.utility.NetworkUtil;

import java.io.IOException;

public class RoboRallyServer {
    private Server server;

    public RoboRallyServer() throws IOException {
        server = new Server();
        server.start();
        NetworkUtil.registerClasses(server.getKryo());
        server.bind(54555, 54777);
        server.addListener(new RoboRallyServerListener());
    }

    /**
     * Sends an object to all clients
     * @param object The object to send
     */
    public void sendToAllClients(Object object) {
        server.sendToAllTCP(object);
    }
}

class RoboRallyServerListener extends Listener {
    Connection host;

    public RoboRallyServerListener() {
        super();
    }

    @Override
    public void received (Connection connection, Object object) {
        if (object instanceof SomeRequest) {
            SomeRequest request = (SomeRequest)object;
            System.out.println(request.text);

            SomeResponse response = new SomeResponse();
            response.text = "Thanks";
            connection.sendTCP(response);
        }
    }

    @Override
    public void connected(Connection connection) {
        //The first client to connect is assumed to be the host
        if (host == null) {
            host = connection;
        }
        System.out.println(connection.getRemoteAddressTCP() + " connected");
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println(connection.getRemoteAddressTCP() + " disconnected");
    }
}