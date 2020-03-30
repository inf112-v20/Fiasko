package inf112.fiasko.roborally.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import inf112.fiasko.roborally.element_properties.RobotID;
import inf112.fiasko.roborally.objects.IDeck;
import inf112.fiasko.roborally.objects.ProgrammingCard;
import inf112.fiasko.roborally.objects.ProgrammingCardDeck;
import inf112.fiasko.roborally.utility.DeckLoaderUtil;
import inf112.fiasko.roborally.utility.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoboRallyServer {
    private Server server;
    private IDeck<ProgrammingCard> programmingCardDeck;
    private RoboRallyServerListener listener;

    public RoboRallyServer() throws IOException {
        server = new Server();
        server.start();
        NetworkUtil.registerClasses(server.getKryo());
        server.bind(54555, 54777);
        listener = new RoboRallyServerListener();
        server.addListener(listener);
        programmingCardDeck = DeckLoaderUtil.loadProgrammingCardsDeck();
    }

    /**
     * Sends an object to all clients
     * @param object The object to send
     */
    public void sendToAllClients(Object object) {
        server.sendToAllTCP(object);
    }

    /**
     * Deals cards to all players
     */
    public void dealCards() {
        programmingCardDeck.shuffle();
        for (Connection connection : server.getConnections()) {
            IDeck<ProgrammingCard> hand = new ProgrammingCardDeck(new ArrayList<>());
            hand.draw(programmingCardDeck, 9);
            connection.sendTCP(hand);
        }
    }
}

class RoboRallyServerListener extends Listener {
    protected Connection host;
    protected Map<Connection, RobotID> clients;

    public RoboRallyServerListener() {
        super();
        clients = new HashMap<>();
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