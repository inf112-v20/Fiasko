package inf112.fiasko.roborally.networking.containers;

import inf112.fiasko.roborally.objects.ProgrammingCard;

import java.util.List;

public class ProgramAndPowerdownRequest {
    private Boolean powerdown;
    private List<ProgrammingCard> program;

    public ProgramAndPowerdownRequest(){}
    public ProgramAndPowerdownRequest(Boolean powerdown, List<ProgrammingCard> program) {
        this.program=program;
        this.powerdown = powerdown;
    }

    public List<ProgrammingCard> getProgram() {
        return program;
    }

    public Boolean getPowerdown() {
        return powerdown;
    }
}
