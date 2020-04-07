package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Direction;
import inf112.fiasko.roborally.element_properties.Position;
import inf112.fiasko.roborally.element_properties.RobotID;

/**
 * This class represents a robot
 */
public class Robot {
    private int amountOfLives = 3;
    private int robotDamageTaken = 0;
    private final RobotID robotId;
    private boolean inPowerDown = false;
    private int lastFlagVisited = 0;
    private Position backupPosition;
    private Position currentPosition;
    private Direction facingDirection;

    /**
     * Instantiates a new robot
     * @param robotId The global identifier of the robot
     * @param spawnPosition The starting position of the robot
     */
    public Robot (RobotID robotId, Position spawnPosition) {
        this.robotId = robotId;
        this.backupPosition = spawnPosition;
        this.currentPosition = spawnPosition;
        this.facingDirection = Direction.NORTH;
    }

    /**
     * Gets the damage the robot has taken
     * @return The amount of damage the robot has received
     */
    public int getDamageTaken() {
        return robotDamageTaken;
    }

    /**
     * Sets the robot's taken damage to a given amount
     * @param damage The amount of damage the robot has received
     */
    public void setDamageTaken(int damage) {
        this.robotDamageTaken = damage;
    }

    /**
     * Gets the robot's current position on the board
     * @return The robot's current position
     */
    public Position getPosition() {
        return currentPosition;
    }

    /**
     * Sets the robot's current position on the board
     * @param newPosition The new position of the robot
     */
    public void setPosition( Position newPosition ) {
        this.currentPosition = newPosition;
    }

    /**
     * Sets power-down status
     * @param powerDownStatus Whether the robot is currently in power-down
     */
    public void setPowerDown(Boolean powerDownStatus) {
        this.inPowerDown = powerDownStatus;
    }

    /**
     * Gets the robot's power-down status
     * @return Whether the robot is currently in power-down
     */
    public Boolean isInPowerDown() {
        return inPowerDown;
    }

    /**
     * Set the robot's last visited flag to the new flag and places its backup on the flag's position
     * @param currentFlag The flag the robot is standing on
     */
    public void setLastFlagVisitedAndUpdateBackupPosition(int currentFlag) {
        if (currentFlag - 1 != this.lastFlagVisited) {
            throw new IllegalArgumentException("The robot has not yet visited the previous flag!");
        }
        this.lastFlagVisited = currentFlag;
        this.backupPosition = currentPosition;
    }

    /**
     * Gets the last flag the robot visited
     * @return Last visited flag
     */
    public int getLastFlagVisited() {
        return lastFlagVisited;
    }

    /**
     * Gets the robot's backup position
     * @return The robot's backup position
     */
    public Position getBackupPosition() {
        return backupPosition;
    }

    /**
     * Gets the robot ID
     * @return Robot ID
     */
    public RobotID getRobotId() {
        return robotId;
    }

    /**
     * Gets the direction the robot is currently facing
     * @return The direction the robot is facing
     */
    public Direction getFacingDirection() {
        return this.facingDirection;
    }

    /**
     * Sets the direction the robot is currently facing
     * @param newFacingDirection The new direction the robot should be facing
     */
    public void setFacingDirection(Direction newFacingDirection) {
        if (newFacingDirection.getDirectionID() % 2 == 0) {
            throw new IllegalArgumentException("A robot is unable to face that direction.");
        }
        this.facingDirection = newFacingDirection;
    }

    /**
     * Sets the amount if life the robot has left
     * @param amountOfLives the new amount if lives the robot has left
     */
    public void setAmountOfLives(int amountOfLives) {
        this.amountOfLives = amountOfLives;
    }

    /**
     * Gets the amount of life a robot has left.
     * @return amount of life left
     */
    public int getAmountOfLives() {
        return this.amountOfLives;
    }

    /**
     * Makes a copy of this robot with the same properties as this robot
     * @return A copy of this robot
     */
    public Robot copy() {
        Robot copy = new Robot(this.robotId, this.currentPosition);
        copy.facingDirection = this.facingDirection;
        copy.lastFlagVisited = this.lastFlagVisited;
        copy.amountOfLives = this.amountOfLives;
        copy.backupPosition = this.backupPosition;
        copy.inPowerDown = this.inPowerDown;
        copy.robotDamageTaken = this.robotDamageTaken;
        return copy;
    }
}
