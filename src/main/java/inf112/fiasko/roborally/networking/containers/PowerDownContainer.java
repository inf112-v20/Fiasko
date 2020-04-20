package inf112.fiasko.roborally.networking.containers;

import java.util.Map;

/**
 * This class is used to contain power down status for all players
 */
public class PowerDownContainer {
    private Map<String, Boolean> powerDown;

    /**
     * IMPORTANT!!!
     * DO NOT USE!
     * Kryonet demands a no args constructor or else it throws a exception
     */
    public PowerDownContainer(){}
    /**
     * Instantiates a new power down container
     *
     * @param powerDown A map between player names and whether they should remain in power down
     */
    public PowerDownContainer(Map<String, Boolean> powerDown) {
        this.powerDown = powerDown;
    }

    /**
     * Gets the power down map stored in the container
     *
     * @return A map between player name and stay in power down
     */
    public Map<String, Boolean> getPowerDown() {
        return powerDown;
    }
}
