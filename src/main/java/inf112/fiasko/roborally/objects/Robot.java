package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Position;

/**
 * this class represents a robot
 */
public class Robot {
    private int robotDamageTaken = 0;
    private final int playerId; //might not be needed
    private boolean inPowerDown = false;
    private int lastFlagVisited = 0;
    private Position backupPosition;
    private Position currentPosition;

    /**
     *
     * @param playerId gives the robot a identifier that links it too the correct player
     * @param spawnPosition
     */
    public Robot (int playerId, Position spawnPosition){
        this.playerId=playerId;
        this.backupPosition = spawnPosition;
        this.currentPosition = spawnPosition;
    }


    public int getDamage(){
        return robotDamageTaken;
    }
    public void setDamage (int damage){
        this.robotDamageTaken = damage;
    }
    public Position getPosition(){
        return currentPosition;
    }
    public void setPosition( Position newPosition ){
        this.currentPosition = newPosition;
    }
    public void setPowerDown(Boolean powerDownStatus){
        this.inPowerDown = powerDownStatus;
    }
    public Boolean isInPowerDown(){
        return inPowerDown;
    }

}
