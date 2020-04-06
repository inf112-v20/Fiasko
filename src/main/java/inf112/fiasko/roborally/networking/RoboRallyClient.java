package inf112.fiasko.roborally.networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.fiasko.roborally.utility.NetworkUtil;

import java.io.IOException;

public class RoboRallyClient {
    public RoboRallyClient(String IPaddresse) throws IOException {
        Client client = new Client();
        client.start();
        NetworkUtil.registerClasses(client.getKryo());
        client.connect(5000, IPaddresse, 54555, 54777);

        SomeRequest request = new SomeRequest();
        request.text = "Here is the request";
        client.sendTCP(request);

        client.addListener(new RoboRallyClientListener());
    }
}

class RoboRallyClientListener extends Listener {
    @Override
    public void received (Connection connection, Object object) {
        if (object instanceof SomeResponse) {
            SomeResponse response = (SomeResponse)object;
            System.out.println("Client received: " + response.text);
        } else if (object instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) object;
            System.out.println(errorResponse.getErrorMessage());
        }
    }
}