package inf112.fiasko.roborally.networking.containers;

import java.util.Map;

public class PowerdownContainer {
    private Map<String,Boolean> powerdown;

    public PowerdownContainer(Map<String, Boolean> powerdown) {
        this.powerdown = powerdown;
    }

    public Map<String, Boolean> getPowerdown() {
        return powerdown;
    }
}
