package inf112.fiasko.roborally.networking.containers;

import inf112.fiasko.roborally.objects.ProgrammingCard;

import java.util.List;
import java.util.Map;

public class ProgamsContainer {
    private Map<String, List<ProgrammingCard>> program;
    private Map<String, Boolean>  powerdown;

    public ProgamsContainer(){}
    public ProgamsContainer(Map<String, List<ProgrammingCard>> program, Map<String, Boolean>  powerdown) {
        this.program = program;
        this.powerdown = powerdown;
    }

    public Map<String, List<ProgrammingCard>> getProgram() {
        return program;
    }
    public Map<String, Boolean> getPowerdown() {
        return powerdown;
    }
}
