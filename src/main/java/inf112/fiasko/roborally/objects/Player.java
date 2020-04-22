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
    private List<ProgrammingCard> program;

    /**
     * Instantiates a new player
     *
     * @param robotID the global identifier of the robot
     * @param name    the unique name of the player
     */
    public Player(RobotID robotID, String name) {
        this.playerDeck = new ProgrammingCardDeck(new ArrayList<>());
        this.lockedPlayerDeck = new ProgrammingCardDeck(new ArrayList<>());
        this.robotID = robotID;
        this.name = name;
    }

    /**
     * Empty constructor required by kryo
     */
    public Player() {
    }

    /**
     * Gets the RobotID of a player
     *
     * @return A RobotID
     */
    public RobotID getRobotID() {
        return robotID;
    }

    /**
     * Gets the name of the player
     *
     * @return A player Name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the players program
     *
     * @return A list of programming cards
     */
    public List<ProgrammingCard> getProgram() {
        return program;
    }

    /**
     * Sets the Players program to the given list of programing cards
     *
     * @param cardList list the size of 5 with programing cards
     */
    public void setProgram(List<ProgrammingCard> cardList) {
        if (cardList.size() != 5 && !cardList.isEmpty()) {
            throw new IllegalArgumentException("The program must contain exactly 5 cards.");
        } else {
            program = new ArrayList<>(cardList);
        }
    }

    /**
     * Gets the player hand/deck
     *
     * @return A deck
     */
    public ProgrammingCardDeck getProgrammingCardDeck() {
        return playerDeck;
    }

    /**
     * Sets the players deck to the given deck
     *
     * @param playerDeck A deck of cards given to the player
     */
    public void setProgrammingCardDeck(ProgrammingCardDeck playerDeck) {
        this.playerDeck = playerDeck;
    }

    /**
     * Gets the player deck with locked cards
     *
     * @return a deck with locked cards
     */
    public ProgrammingCardDeck getLockedProgrammingCardDeck() {
        return lockedPlayerDeck;
    }

    /**
     * Gets the players power down status
     *
     * @return Whether the player is to power down
     */
    public boolean getPowerDownNextRound() {
        return powerDownNextRound;
    }

    /**
     * Sets the power down status
     *
     * @param powerDownStatus Whether the player is to take power down next round
     */
    public void setPowerDownNextRound(boolean powerDownStatus) {
        this.powerDownNextRound = powerDownStatus;
    }

}
