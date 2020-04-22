package inf112.fiasko.roborally.networking.containers;

import java.util.Map;

/**
 * This class is used to contain power down status for all players
 */
public class PowerDownContainerResponse {
    private Map<String, Boolean> powerDown;

    /**
     * Empty constructor required by KryoNet. DO NOT REMOVE THIS!!!
     */
    public PowerDownContainerResponse() {
    }

    /**
     * Instantiates a new power down container
     *
     * @param powerDown A map between player names and whether they should remain in power down
     */
    public PowerDownContainerResponse(Map<String, Boolean> powerDown) {
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
