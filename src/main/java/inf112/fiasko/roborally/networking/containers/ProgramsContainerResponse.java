package inf112.fiasko.roborally.networking.containers;

import inf112.fiasko.roborally.objects.ProgrammingCard;

import java.util.List;
import java.util.Map;

/**
 * This class contains information about all players' programs and whether they want to enter power down
 */
public class ProgramsContainerResponse {
    private Map<String, List<ProgrammingCard>> programsMap;
    private Map<String, Boolean> powerDownMap;

    /**
     * Empty constructor required by KryoNet. DO NOT REMOVE THIS!!!
     */
    public ProgramsContainerResponse() {
    }

    /**
     * Instantiates a new programs container
     *
     * @param programsMap  A map between a player name and a player's program
     * @param powerDownMap A map between a player name and power down
     */
    public ProgramsContainerResponse(Map<String, List<ProgrammingCard>> programsMap, Map<String, Boolean> powerDownMap) {
        this.programsMap = programsMap;
        this.powerDownMap = powerDownMap;
    }

    /**
     * Gets the map of programs
     *
     * @return A map between a player name and a player's program
     */
    public Map<String, List<ProgrammingCard>> getProgramsMap() {
        return programsMap;
    }

    /**
     * Gets the map of power down
     *
     * @return A map between a player name and power down
     */
    public Map<String, Boolean> getPowerDownMap() {
        return powerDownMap;
    }
}
