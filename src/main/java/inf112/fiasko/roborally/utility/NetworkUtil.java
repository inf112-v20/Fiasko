package inf112.fiasko.roborally.utility;

import com.esotericsoftware.kryo.Kryo;
import inf112.fiasko.roborally.element_properties.RobotID;
import inf112.fiasko.roborally.networking.containers.ErrorResponse;
import inf112.fiasko.roborally.networking.containers.GameStartInfo;
import inf112.fiasko.roborally.objects.IDeck;
import inf112.fiasko.roborally.objects.Player;
import inf112.fiasko.roborally.objects.ProgrammingCard;
import inf112.fiasko.roborally.objects.ProgrammingCardDeck;

import java.util.ArrayList;

/**
 * This class helps with networking tasks
 */
public final class NetworkUtil {

    /**
     * Registers all classes which can be sent between a server and a client
     * @param kryo The kryo object to register the classes to
     */
    public static void registerClasses(Kryo kryo) {
        kryo.register(ErrorResponse.class);
        kryo.register(IDeck.class);
        kryo.register(ProgrammingCard.class);
        kryo.register(GameStartInfo.class);
        kryo.register(ArrayList.class);
        kryo.register(Player.class);
        kryo.register(RobotID.class);
        kryo.register(ProgrammingCardDeck.class);
    }
}
