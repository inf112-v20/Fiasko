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
    private List <ProgrammingCard> program;

    /**
     * Instantiates a new player
     * @param robotID the global identifier of the robot
     * @param name the unique name of the player
     */
    public Player(RobotID robotID, String name) {
        this.robotID = robotID;
        this.name = name;
    }

    /**
     * Gives you the RobotID of a player
     * @return An RobotID
     */
    public RobotID getRobotID(){return robotID;}

    /**
     * Set the players deck to the given deck
     * @param playerDeck a deck of cards given to the player
     */
    public void setPlayerDeck(ProgrammingCardDeck playerDeck){
        this.playerDeck=playerDeck;
    }

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
     * Gets the program from the player
     * @return List of programing cards
     */
    public List <ProgrammingCard> getProgramFromPlayer(){
        return program;
    }

    /**
     * Sets the Players program to the given list of programing cards
     * @param cardList list the size of 5 with programing cards
     */
    public void setInProgram(List <ProgrammingCard> cardList){
        if(cardList.size() != 5){
            throw new IllegalArgumentException("list must contain 5 programing cards");
        }
        else {
            program = new ArrayList<>(cardList);
        }
    }

}
