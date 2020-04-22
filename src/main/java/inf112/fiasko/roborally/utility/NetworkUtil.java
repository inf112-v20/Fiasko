package inf112.fiasko.roborally.utility;

import com.esotericsoftware.kryo.Kryo;
import inf112.fiasko.roborally.elementproperties.Action;
import inf112.fiasko.roborally.elementproperties.RobotID;
import inf112.fiasko.roborally.networking.containers.ErrorResponse;
import inf112.fiasko.roborally.networking.containers.GameStartInfoResponse;
import inf112.fiasko.roborally.networking.containers.PowerDownContainerResponse;
import inf112.fiasko.roborally.networking.containers.ProgramsContainerResponse;
import inf112.fiasko.roborally.networking.containers.ProgramAndPowerdownRequest;
import inf112.fiasko.roborally.objects.Deck;
import inf112.fiasko.roborally.objects.Player;
import inf112.fiasko.roborally.objects.ProgrammingCard;
import inf112.fiasko.roborally.objects.ProgrammingCardDeck;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class helps with networking tasks
 */
public final class NetworkUtil {

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
        kryo.register(ProgramAndPowerdownRequest.class);
        kryo.register(ProgramsContainerResponse.class);
        kryo.register(PowerDownContainerResponse.class);
        kryo.register(HashMap.class);
    }
}
