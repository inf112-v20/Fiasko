package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Direction;
import inf112.fiasko.roborally.element_properties.Position;
import inf112.fiasko.roborally.element_properties.RobotID;
import inf112.fiasko.roborally.element_properties.TileType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a board
 */
public class Board {
    private int boardHeight;
    private int boardWidth;
    private IGrid<Wall> walls;
    private IGrid<Tile> tiles;
    private Map<RobotID, Robot> robots;
    private List<Robot> deadRobots;

    /**
     * Initializes the board
     * @param tiles A grid containing all tiles
     * @param walls A grid containing all walls
     * @param robots A list of all robots in the game
     */
    public Board(IGrid<Tile> tiles, IGrid<Wall> walls, List<Robot> robots) {
        if (walls.getWidth() != tiles.getWidth() || walls.getHeight() != tiles.getHeight()) {
            throw new IllegalArgumentException("The grids in the input don't have the same dimensions.");
        }
        this.robots = new HashMap<>();
        for (Robot robot : robots) {
            if (this.robots.get(robot.getRobotId()) != null) {
                throw new IllegalArgumentException("There can't be two robots with the same robot id.");
            }
            this.robots.put(robot.getRobotId(), robot);
        }
        this.deadRobots = new ArrayList<>();
        this.boardWidth = tiles.getWidth();
        this.boardHeight = tiles.getHeight();
        this.walls = walls;
        this.tiles = tiles;
    }

    /**
     * Gets the height of the board
     * @return The height of the board
     */
    public int getBoardHeight() {
        return boardHeight;
    }

    /**
     * Gets the width of the board
     * @return The width of the board
     */
    public int getBoardWidth() {
        return boardWidth;
    }

    /**
     * Moves all dead robots to their backups and makes them part of the board again
     */
    public void respawnRobots() {
        //TODO: Account for several robots re-spawning at same backup
        for (Robot robot : deadRobots) {
            robot.setPosition(robot.getBackupPosition());
            robots.put(robot.getRobotId(), robot);
        }
        deadRobots = new ArrayList<>();
    }

    /**
     * Gets all alive robots from the board
     * @return A list of alive robots
     */
    public List<Robot> getAliveRobots() {
        return new ArrayList<>(robots.values());
    }

    /**
     * Removes a dead robot from the board over to the dead robot list
     * @param robot the dead robot
     */
    public void removeDeadRobotFromBoard(Robot robot) {
        robots.remove(robot.getRobotId());
        deadRobots.add(robot);
    }

    /**
     * Rotates a robot to the right
     * @param robotID The id of the robot to rotate
     */
    public void rotateRobotLeft(RobotID robotID) {
        Robot robot = robots.get(robotID);
        Direction newDirection = Direction.getLeftRotatedDirection(robot.getFacingDirection());
        robot.setFacingDirection(newDirection);
    }

    /**
     * Rotates a robot to the left
     * @param robotID The id of the robot to rotate
     */
    public void rotateRobotRight(RobotID robotID) {
        Robot robot = robots.get(robotID);
        Direction newDirection = Direction.getRightRotatedDirection(robot.getFacingDirection());
        robot.setFacingDirection(newDirection);
    }

    /**
     * Moves a robot one unit in a specified direction
     * @param robotID ID of the robot to move
     * @param direction The direction to move the robot
     * @return True if the robot moved away from its old position
     */
    public boolean moveRobot(RobotID robotID, Direction direction) {
        Robot robot = robots.get(robotID);
        Position robotPosition = robot.getPosition();
        Position newPosition = getNewPosition(robotPosition, direction);
        //Robot tried to go outside of the map. Kill it.
        if (killRobotIfGoesOutsideMap(robot, newPosition)) {
            return true;
        }
        //There is a wall blocking the robot. It can't proceed.
        if (robotMoveIsStoppedByWall(robotPosition, newPosition, direction)) {
            return false;
        }
        //If another robot is blocking this robot's path, try to shove it.
        if (hasRobotOnPosition(newPosition)) {
            RobotID otherRobotID = getRobotOnPosition(newPosition);
            if (otherRobotID != null && !moveRobot(otherRobotID, direction)) {
                return false;
            }
        }
        //Some tiles may kill the robot if stepped on.
        if (killRobotIfStepsOnDangerousTile(robot, newPosition)) {
            return true;
        }
        robot.setPosition(newPosition);
        return true;
    }

    /**
     * Checks if a potential robot move would be blocked by a wall
     * @param robotPosition The current position of the robot
     * @param newPosition The position the robot is trying to move to
     * @param direction The direction the robot is going
     * @return True if a wall would stop its path
     */
    private boolean robotMoveIsStoppedByWall(Position robotPosition, Position newPosition, Direction direction) {
        return hasWallFacing(robotPosition, direction) ||
                hasWallFacing(newPosition, Direction.getReverseDirection(direction));
    }

    /**
     * Checks if the robot is about to step outside of the board, and kills it if it does
     * @param robot The robot attempting to move
     * @param newPosition The position the robot is attempting to move to
     * @return True if the robot was killed for leaving the board
     */
    private boolean killRobotIfGoesOutsideMap(Robot robot, Position newPosition) {
        if (newPosition.getXCoordinate() < 0
                || newPosition.getXCoordinate() >= boardWidth
                || newPosition.getYCoordinate() < 0
                || newPosition.getYCoordinate() >= boardHeight) {
            killRobot(robot);
            return true;
        }
        return false;
    }

    /**
     * Checks the tile the robot is about to step on and kills it if the tile is dangerous
     * @param robot The robot attempting to move
     * @param newPosition The position the robot is attempting to move to
     * @return True if the robot was killed by the tile
     */
    private boolean killRobotIfStepsOnDangerousTile(Robot robot, Position newPosition) {
        Tile tileRobotStepsOn = tiles.getElement(newPosition.getXCoordinate(), newPosition.getYCoordinate());
        if (tileRobotStepsOn == null) {
            throw new IllegalArgumentException("The game board is missing a tile. This should not happen.");
        }
        TileType tileTypeRobotStepsOn = tileRobotStepsOn.getTileType();
        if (tileTypeRobotStepsOn == TileType.HOLE || tileTypeRobotStepsOn == TileType.DEATH_TILE) {
            killRobot(robot);
            return true;
        }
        return false;
    }

    /**
     * Kills the robot
     *
     * If the robot steps outside of the board, steps on a hole or takes too much damage, this method should be used to
     * properly dispose of the robot until the next round.
     *
     * @param robot The robot to kill
     */
    private void killRobot(Robot robot) {
        //TODO: Must remove a life from the robot/player
        removeDeadRobotFromBoard(robot);
    }

    /**
     * Returns a robot id for a robot on a specific position if such a robot exists
     * @param position The position to check
     * @return The robot id of the robot on the position or null if there is no robot there
     */
    private RobotID getRobotOnPosition(Position position) {
        for (RobotID robotID : robots.keySet()) {
            Robot robot = robots.get(robotID);
            if (position.equals(robot.getPosition())) {
                return robotID;
            }
        }
        return null;
    }

    /**
     * Checks whether there exists a robot on a specific position
     * @param position The position to check
     * @return True if there is a robot on the specified position
     */
    private boolean hasRobotOnPosition(Position position) {
        return getRobotOnPosition(position) != null;
    }

    /**
     * Checks whether a position has a wall facing a specific direction
     * @param position The position to check
     * @param direction The direction of the wall to check for
     * @return True if there is a wall on the position facing the input direction
     */
    private boolean hasWallFacing(Position position, Direction direction) {
        Wall wall = walls.getElement(position.getXCoordinate(), position.getYCoordinate());
        if (wall == null) {
            return false;
        }
        int wallDirectionId = wall.getDirection().getDirectionID();
        if (wallDirectionId % 2 == 0) {
            return (wallDirectionId % 8) + 1 == direction.getDirectionID()
                    || (((wallDirectionId - 2) + 8) % 8) + 1 == direction.getDirectionID();
        } else {
            return wall.getDirection() == direction;
        }
    }

    /**
     * Gets the position 1 unit in a specific direction from another position
     * @param oldPosition The old/current position of the element
     * @param direction The direction to move the element
     * @return The new position of the element
     */
    private Position getNewPosition(Position oldPosition, Direction direction) {
        //TODO: Make sure we're accounting for the flipped y axis in libgdx
        switch (direction) {
            case NORTH:
                return new Position(oldPosition.getXCoordinate(), oldPosition.getYCoordinate() + 1);
            case SOUTH:
                return new Position(oldPosition.getXCoordinate(), oldPosition.getYCoordinate() - 1);
            case EAST:
                return new Position(oldPosition.getXCoordinate() + 1, oldPosition.getYCoordinate());
            case WEST:
                return new Position(oldPosition.getXCoordinate() - 1, oldPosition.getYCoordinate());
            default:
                throw new IllegalArgumentException("It's not possible to move in that direction.");
        }
    }
}
