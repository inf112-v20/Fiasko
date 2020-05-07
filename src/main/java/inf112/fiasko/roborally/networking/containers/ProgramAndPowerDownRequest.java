package inf112.fiasko.roborally.networking.containers;

import inf112.fiasko.roborally.objects.ProgrammingCard;

import java.util.List;

/**
 * A request containing a player's program and whether it wants to enter power down next round
 */
public class ProgramAndPowerDownRequest {
    private Boolean powerDown;
    private List<ProgrammingCard> program;

    /**
     * Empty constructor required by KryoNet. DO NOT REMOVE THIS!!!
     */
    public ProgramAndPowerDownRequest() {
    }

    /**
     * Instantiates a new program and power down request
     *
     * @param powerDown Whether the player wants to enter power down next round
     * @param program   The program the player has programmed
     */
    public ProgramAndPowerDownRequest(Boolean powerDown, List<ProgrammingCard> program) {
        this.program = program;
        this.powerDown = powerDown;
    }

    /**
     * Gets the program contained within this request
     *
     * @return The program sent by the player
     */
    public List<ProgrammingCard> getProgram() {
        return program;
    }

    /**
     * Gets the power down status contained within this request
     *
     * @return Whether the player wants to power down next round
     */
    public Boolean getPowerDown() {
        return powerDown;
    }
}
