package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Position;

/**
 * this class represents a robot
 */
public class Robot {
    private int robotDamageTaken = 0;
    private final int playerId;
    private boolean inPowerDown = false;
    private int lastFlagVisited = 0;
    private Position backupPosition;
    private Position currentPosition;

    /**
     * Instantiates a new robot
     * @param playerId gives the robot a identifier that links it too the correct player
     * @param spawnPosition gives the robot its starting position on the map
     */
    public Robot (int playerId, Position spawnPosition){
        this.playerId=playerId;
        this.backupPosition = spawnPosition;
        this.currentPosition = spawnPosition;
    }

    /**
     * Gets the damage the robot has taken
     * @return the amount of damage the robot has received
     */
    public int getDamage(){
        return robotDamageTaken;
    }

    /**
     *  Set the robot's damage too a given amount
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
        this.lastFlagVisited=currentFlag;
        this.backupPosition=newBackupPosition;
    }

    /**
     * Gets the correct flag the robot visited
     * @return last visited flag
     */
    public int getLastFlagVisited(){
        return lastFlagVisited;
    }

    /**
     * Gets the robots backup position
     * @return robots backup position
     */
    public Position getBackupPosition(){
        return backupPosition;
    }

}
