package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.RobotID;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class represents a player
 */

public class Player {
    private final RobotID robotID;
    private final String name;
    private boolean powerDownNextRound = false;
    private ProgrammingCardDeck playerDeck;
    private List <ProgrammingCard> program = new ArrayList();

    /**
     * Instantiates a new player
     * @param robotID the global identifier of the robot
     * @param name the unique name of the player
     * @param playerDeck the hand of cards dealt to the player
     */
    public Player(RobotID robotID, String name, ProgrammingCardDeck playerDeck) {
        this.robotID = robotID;
        this.name = name;
        this.playerDeck = playerDeck;
        program.add(0, null); //sets the initial values in program to null
        program.add(1, null);
        program.add(2, null);
        program.add(3, null);
        program.add(4, null);
    }

    /**
     * Gives you the RobotID of a player
     * @return An RobotID
     */
    public RobotID getRobotID(){return robotID;}

    /**
     * Gives you the Name of the player
     * @return a player Name
     */
    public String getName() {return name;}

    /**
     * Gives you the players program
     * @return a list<ProgrammingCard>
     */
    public List<ProgrammingCard> getProgram() {return program;}

    /**
     * Gives you the player hand/deck
     * @return a deck
     */
    public ProgrammingCardDeck getPlayerDeck() {return playerDeck;}

    /**
     * Gives you the players powerdown status
     * @return a boolean
     */
    public boolean getPowerDownNextRound() { return powerDownNextRound;}

    /**
     * Sets the prowerdown status
     * @param powerDownStatus the boolean that determines if it goes to a powerdown or not
     */
    public void setPowerDownNextRound(boolean powerDownStatus) { this.powerDownNextRound = powerDownStatus;}

    /**
     * Places a card in to the player program
     * @param card the card that is placed in to the player program
     */
    public void setCardInProgram(ProgrammingCard card) {
        for (int i = 0; i < 5; i++) {
            if (program.get(i) == null) {
                program.add(i, card);
                return;
            }
        }
        throw new IllegalArgumentException("Program deck is full,tried to add to many cards");
    }

    /**
     * Removes a card by the given index from the player program and returns it.
     * @param cardNr the index of the card that is being removed
     * @return the card that was removed from the program
     */
    public ProgrammingCard removeProgramCard(int cardNr) {
        if(cardNr<5 && cardNr>-1) {
            program.add(cardNr, null);
            return program.remove(cardNr + 1);
        }
        else{
            throw new IllegalArgumentException("cant remove more then index 4 or remove negatives");

        }
    }

}
