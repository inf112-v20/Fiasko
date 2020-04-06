package inf112.fiasko.roborally.networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.fiasko.roborally.game_wrapper.RoboRallyWrapper;
import inf112.fiasko.roborally.objects.RoboRallyGame;
import inf112.fiasko.roborally.utility.NetworkUtil;

import java.io.IOException;

public class RoboRallyClient {
    Client client;
    RoboRallyWrapper wrapper;
    public RoboRallyClient(String IPaddresse,RoboRallyWrapper wrapper) throws IOException {
        client = new Client();
        this.wrapper=wrapper;
        client.start();
        NetworkUtil.registerClasses(client.getKryo());
        client.connect(5000, IPaddresse, 54555, 54777);

        SomeRequest request = new SomeRequest();
        request.text = "Here is the request";
        client.sendTCP(request);

        client.addListener(new RoboRallyClientListener(wrapper));
    }
    public void sendElement(Object obj) {
        client.sendTCP(obj);
    }
}

class RoboRallyClientListener extends Listener {
    RoboRallyWrapper wrapper;
    public RoboRallyClientListener( RoboRallyWrapper wrapper){
        super();
        this.wrapper=wrapper;
    }
    @Override
    public void received (Connection connection, Object object) {
        if (object instanceof SomeResponse) {
            SomeResponse response = (SomeResponse)object;
            System.out.println("Client received: " + response.text);
        } else if (object instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) object;
            System.out.println(errorResponse.getErrorMessage());
        }
        else if(object instanceof GameStartInfo){
            GameStartInfo info = (GameStartInfo) object;
            wrapper.roboRallyGame = new RoboRallyGame(info.getPlayerlist(),info.getBoardname(),
                    wrapper.server!=null);
        }
    }
}

