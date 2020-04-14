package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.elementproperties.RobotID;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a player
 */
public class Player {
    private RobotID robotID;
    private String name;
    private boolean powerDownNextRound = false;
    private ProgrammingCardDeck playerDeck;
    private ProgrammingCardDeck lockedPlayerDeck;
    private List <ProgrammingCard> program;

    /**
     * Instantiates a new player
     * @param robotID the global identifier of the robot
     * @param name the unique name of the player
     */
    public Player(RobotID robotID, String name) {
        this.playerDeck = new ProgrammingCardDeck(new ArrayList<>());
        this.robotID = robotID;
        this.name = name;
    }

    /**
     * Empty constructor required by kryo
     */
    public Player(){}

    /**
     * Sets the robot id of the robot
     * @param robotID The new id of the robot
     */
    public void setRobotID(RobotID robotID) {
        this.robotID = robotID;
    }

    /**
     * Sets the name of the robot
     * @param name The new name of the robot
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gives you the RobotID of a player
     * @return A RobotID
     */
    public RobotID getRobotID() {
        return robotID;
    }

    /**
     * Set the players deck to the given deck
     * @param playerDeck A deck of cards given to the player
     */
    public void setPlayerDeck(ProgrammingCardDeck playerDeck) {
        this.playerDeck = playerDeck;
    }

    /**
     * Gives you the Name of the player
     * @return A player Name
     */
    public String getName() {
        return name;
    }

    /**
     * Gives you the players program
     * @return A list of programming cards
     */
    public List<ProgrammingCard> getProgram() {
        return program;
    }

    /**
     * Gives you the player hand/deck
     * @return a deck
     */
    public ProgrammingCardDeck getPlayerDeck() {
        return playerDeck;
    }

    /**
     * Gives you the player deck with locked cards
     * @return a deck with locked cards
     */
    public ProgrammingCardDeck getLockedPlayerDeck() {
        return lockedPlayerDeck;
    }

    /**
     * Set the players locked deck to the given deck
     * @param lockedPlayerDeck A deck of locked cards kept by the player
     */
    public void setLockedPlayerDeck(ProgrammingCardDeck lockedPlayerDeck) {
        this.lockedPlayerDeck = lockedPlayerDeck;
    }

    /**
     * Gives you the players power down status
     * @return Whether the player is to power down
     */
    public boolean getPowerDownNextRound() {
        return powerDownNextRound;
    }

    /**
     * Sets the power down status
     * @param powerDownStatus Whether the player is to take power down next round
     */
    public void setPowerDownNextRound(boolean powerDownStatus) {
        this.powerDownNextRound = powerDownStatus;
    }

    /**
     * Sets the Players program to the given list of programing cards
     * @param cardList list the size of 5 with programing cards
     */
    public void setInProgram(List <ProgrammingCard> cardList) {
        if (cardList.size() != 5) {
            throw new IllegalArgumentException("list must contain 5 programing cards");
        } else {
            program = new ArrayList<>(cardList);
        }
    }

}
