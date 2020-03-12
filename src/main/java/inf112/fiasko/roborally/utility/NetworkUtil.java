package inf112.fiasko.roborally.utility;

import com.esotericsoftware.kryo.Kryo;
import inf112.fiasko.roborally.networking.SomeRequest;
import inf112.fiasko.roborally.networking.SomeResponse;

public final class NetworkUtil {

    /**
     * Registers all classes which can be sent between a server and a client
     * @param kryo The kryo object to register the classes to
     */
    public static void registerClasses(Kryo kryo) {
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
    }
}
