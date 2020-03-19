package inf112.fiasko.roborally.utility;

import com.esotericsoftware.kryo.Kryo;
import inf112.fiasko.roborally.networking.ErrorResponse;
import inf112.fiasko.roborally.networking.SomeRequest;
import inf112.fiasko.roborally.networking.SomeResponse;
import inf112.fiasko.roborally.objects.IDeck;
import inf112.fiasko.roborally.objects.ProgrammingCard;

public final class NetworkUtil {

    /**
     * Registers all classes which can be sent between a server and a client
     * @param kryo The kryo object to register the classes to
     */
    public static void registerClasses(Kryo kryo) {
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
        kryo.register(ErrorResponse.class);
        kryo.register(IDeck.class);
        kryo.register(ProgrammingCard.class);
    }
}
