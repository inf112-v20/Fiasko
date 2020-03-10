package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.RobotID;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final RobotID robotID;
    private final String name;
    private boolean powerDownNextRound = false;
    private ProgrammingCardDeck playerDeck;
    private List <ProgrammingCard> program = new ArrayList();

    // Constructor for the player class, it get assigned a Robot, a player nam and a deck
    public Player(RobotID robotID, String name, ProgrammingCardDeck playerDeck) {
        this.robotID = robotID;
        this.name = name;
        this.playerDeck = playerDeck;
    }

    /**
     * Gives you the RobotID of a player
     * @return An RobotID
     */
    public RobotID getRobotID(){return robotID;}

    public String getName() {return name;}

    public List<ProgrammingCard> getProgram() {return program;}

    public ProgrammingCardDeck getPlayerDeck() {return playerDeck;}

    public boolean getPowerDownNextRound() { return powerDownNextRound;}

    public void setPowerDownNextRound(boolean powerDownStatus) { this.powerDownNextRound = powerDownStatus;}

    public void setCardInProgram(ProgrammingCard card) {
        for (int i = 0; i < 5; i++) {
            if (program.size() == 0) {
                program.add(i, card);
                return;
            }
            if (program.get(i) == null) {
                program.add(i, card);
                return;
            }
        }
        throw new IllegalArgumentException("Program deck is full,tried to add to many cards");
    }


    public ProgrammingCard removeProgramCard(int cardNr) {
        program.add(cardNr, null);
        return program.remove(cardNr+1);
    }

}
