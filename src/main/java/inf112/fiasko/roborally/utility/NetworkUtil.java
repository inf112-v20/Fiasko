package inf112.fiasko.roborally.utility;

import com.esotericsoftware.kryo.Kryo;
import inf112.fiasko.roborally.networking.containers.ErrorResponse;
import inf112.fiasko.roborally.networking.containers.GameStartInfoResponse;
import inf112.fiasko.roborally.networking.containers.HandResponse;
import inf112.fiasko.roborally.networking.containers.HurryResponse;
import inf112.fiasko.roborally.networking.containers.OkayResponse;
import inf112.fiasko.roborally.networking.containers.PowerDownContainerResponse;
import inf112.fiasko.roborally.networking.containers.ProgramAndPowerDownRequest;
import inf112.fiasko.roborally.networking.containers.ProgramsContainerResponse;
import inf112.fiasko.roborally.networking.containers.UsernameRequest;
import inf112.fiasko.roborally.objects.Deck;
import inf112.fiasko.roborally.objects.Player;
import inf112.fiasko.roborally.objects.ProgrammingCard;
import inf112.fiasko.roborally.objects.ProgrammingCardDeck;
import inf112.fiasko.roborally.objects.properties.Action;
import inf112.fiasko.roborally.objects.properties.RobotID;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class helps with networking tasks
 */
public final class NetworkUtil {

    private NetworkUtil() {
    }

    /**
     * Registers all classes which can be sent between a server and a client
     *
     * @param kryo The kryo object to register the classes to
     */
    public static void registerClasses(Kryo kryo) {
        kryo.register(ErrorResponse.class);
        kryo.register(Deck.class);
        kryo.register(ProgrammingCard.class);
        kryo.register(GameStartInfoResponse.class);
        kryo.register(ArrayList.class);
        kryo.register(Player.class);
        kryo.register(RobotID.class);
        kryo.register(ProgrammingCardDeck.class);
        kryo.register(Action.class);
        kryo.register(ProgramAndPowerDownRequest.class);
        kryo.register(ProgramsContainerResponse.class);
        kryo.register(PowerDownContainerResponse.class);
        kryo.register(HashMap.class);
        kryo.register(UsernameRequest.class);
        kryo.register(OkayResponse.class);
        kryo.register(HurryResponse.class);
        kryo.register(HandResponse.class);
    }
}
