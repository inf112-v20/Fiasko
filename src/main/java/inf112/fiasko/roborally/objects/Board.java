package inf112.fiasko.roborally.objects;


import java.util.ArrayList;

/**
 * This class represents a board
 */

public class Board {
    private Grid walls;
    private Grid otherObjects;
    private ArrayList<Robot> deadRobots = new ArrayList<>();
    private ArrayList<Robot> aliveRobots;

    /**
     * Initializes the board
     * @param walls a grid containing all the walls
     * @param otherObjects a grid containing all the other Objects like flags and conveyor belts
     * @param aliveRobots a list of all the robots that are currently alive
     */
    public void Board(Grid walls, Grid otherObjects, ArrayList aliveRobots){
        this.walls=walls;
        this.otherObjects=otherObjects;
        this.aliveRobots=aliveRobots;
    }

    /**
     * Removes a dead robot from the board over to the dead robot list.
     * @param robot the dead robot
     */
    public void  removeDeadRobotFromBoard(Robot robot){
        aliveRobots.remove(robot);
        deadRobots.add(robot);
    }
}
