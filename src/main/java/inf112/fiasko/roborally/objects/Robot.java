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
     * @param robotId gives the robot a identifier that links it too the correct player
     * @param spawnPosition gives the robot its starting position on the map
     */
    public Robot (RobotID robotId, Position spawnPosition) {
        this.robotId = robotId;
        this.backupPosition = spawnPosition;
        this.currentPosition = spawnPosition;
        this.facingDirection = Direction.NORTH;
    }

    /**
     * Gets the damage the robot has taken
     * @return the amount of damage the robot has received
     */
    public int getDamage(){
        return robotDamageTaken;
    }

    /**
     * Sets the robot's damage to a given amount
     * @param damage the amount of damage the robot has received
     */
    public void setDamage (int damage){
        this.robotDamageTaken = damage;
    }

    /**
     * Gets the robot's current position on the map
     * @return the robot's current position
     */
    public Position getPosition(){
        return currentPosition;
    }

    /**
     * places the robot on a new position
     * @param newPosition the new position for the robot
     */
    public void setPosition( Position newPosition ){
        this.currentPosition = newPosition;
    }

    /**
     * Places the status of the powerdown field
     * @param powerDownStatus True if robot is going to go to powerdown. False otherwise
     */
    public void setPowerDown(Boolean powerDownStatus){
        this.inPowerDown = powerDownStatus;
    }

    /**
     * Gets the status of the robot's powerdown field
     * @return robot's powerdown status
     */
    public Boolean isInPowerDown(){
        return inPowerDown;
    }

    /**
     * Set the robot's last visited flag too the new flag and places its backup on the flags position
     * @param currentFlag the flag the robot is standing on
     * @param newBackupPosition the position of the flag
     */
    public void setLastFlagVisitedAndBackupPosition(int currentFlag, Position newBackupPosition){
        this.lastFlagVisited = currentFlag;
        this.backupPosition = newBackupPosition;
    }

    /**
     * Gets the correct flag the robot visited
     * @return last visited flag
     */
    public int getLastFlagVisited(){
        return lastFlagVisited;
    }

    /**
     * Gets the robot's backup position
     * @return The robot's backup position
     */
    public Position getBackupPosition(){
        return backupPosition;
    }

    /**
     * Gets the identifier of the players controlling the robot
     * @return player identifier
     */
    public RobotID getRobotId(){
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
    public int getAmountOfLives() { return this.amountOfLives; }
}
