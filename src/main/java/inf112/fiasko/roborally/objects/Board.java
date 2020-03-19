package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Direction;
import inf112.fiasko.roborally.element_properties.Position;
import inf112.fiasko.roborally.element_properties.RobotID;
import inf112.fiasko.roborally.element_properties.TileType;
import inf112.fiasko.roborally.element_properties.WallType;

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
        this.boardWidth = tiles.getWidth();
        this.boardHeight = tiles.getHeight();
        this.walls = walls;
        this.tiles = tiles;
        this.deadRobots = new ArrayList<>();
    }

    /**
     * Fires all lasers on the board and kills any robot that has taken to much damage after all lasers have fired.
     */
    public void fireAllLasers(){
        List<BoardElementContainer<Wall>> listOfWallLasers = getPositionsOfWallOnBoard(WallType.WALL_LASER_SINGLE,
                WallType.WALL_LASER_DOUBLE);
        for (Robot robot:robots.values()) {
            fireOneRobotLaser(robot.getPosition(),robot.getFacingDirection());
        }
        for (BoardElementContainer<Wall> laser:listOfWallLasers) {
            fireOneWallLaser(laser);
        }
        killAllDeadRobot();
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
     * Gets all alive robots from the board
     * @return A list of alive robots
     */
    public List<Robot> getAliveRobots() {
        List<Robot> robotsCopy = new ArrayList<>(robots.values());
        robotsCopy.replaceAll(Robot::copy);
        return robotsCopy;
    }

    /**
     * Gets all the tiles from the board
     * @return A list of all tiles on the board
     */
    public List<Tile> getTiles() {
        return getAllElementsFromGrid(tiles);
    }

    /**
     * Gets all the walls from the board
     * @return A list of all the walls on the board
     */
    public List<Wall> getWalls() {
        return getAllElementsFromGrid(walls);
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
     * Moves a robot one unit forward according to the direction it's currently facing
     * @param robotID The robot to move
     */
    public void moveRobotForward(RobotID robotID) {
        moveRobot(robotID, robots.get(robotID).getFacingDirection());
    }

    /**
     * Moves a robot one unit backwards according to the direction it's currently facing
     * @param robotID The robot to move
     */
    public void reverseRobot(RobotID robotID) {
        moveRobot(robotID, Direction.getReverseDirection(robots.get(robotID).getFacingDirection()));
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
        //There is a wall blocking the robot. It can't proceed.
        if (moveIsStoppedByWall(robotPosition, newPosition, direction)) {
            return false;
        }
        //Robot tried to go outside of the map. Kill it.
        if (killRobotIfGoesOutsideMap(robot, newPosition)) {
            return true;
        }
        //If another robot is blocking this robot's path, try to shove it.
        if (hasRobotOnPosition(newPosition)) {
            RobotID otherRobotID = getRobotOnPosition(newPosition);
            if (otherRobotID != null && !moveRobot(otherRobotID, direction)) {
                //The other robot can't be shoved. Give up.
                return false;
            }
        }
        robot.setPosition(newPosition);
        //Some tiles may kill the robot if stepped on.
        killRobotIfStepsOnDangerousTile(robot, newPosition);
        return true;
    }

    /**
     * Moves all dead robots to their backups and makes them part of the board again, and if a robot has no lives
     * it will be removed from the game.
     */
    public void respawnRobots() {
        for (Robot robot : deadRobots) {
            if (robot.getAmountOfLives() > 0) {
                robot.setPosition(robot.getBackupPosition());
                robot.setFacingDirection(Direction.NORTH);
                robots.put(robot.getRobotId(), robot);
            }
        }
        deadRobots = new ArrayList<>();
    }

    /**
     * Returns a robot id for a robot on a specific position if such a robot exists
     * @param position The position to check
     * @return The robot id of the robot on the position or null if there is no robot there
     */
    public RobotID getRobotOnPosition(Position position) {
        for (RobotID robotID : robots.keySet()) {
            Robot robot = robots.get(robotID);
            if (position.equals(robot.getPosition())) {
                return robotID;
            }
        }
        return null;
    }

    /**
     * Checks if a specific robot is currently alive on the board
     * @param robot the ID of the robot you want to check
     * @return True/False based on if the robot was found.
     */
    public boolean isRobotAlive(RobotID robot) {
        return robots.containsKey(robot);
    }

    /**
     * Updates the flag of the robot if it stands on the correct flag.
     * @param robotID The RobotID of a robot
     * @param flagID TileType of the flag we check
     */
    public void updateFlagOnRobot(RobotID robotID, TileType flagID) {
        Robot robot = robots.get(robotID);
        int flagNr = flagID.getTileTypeID() % 16;
        if (flagNr - 1 == robot.getLastFlagVisited()) {
            robot.setLastFlagVisitedAndUpdateBackupPosition(flagNr);
        }
    }

    /**
     * Gets the position 1 unit in a specific direction from another position
     * @param oldPosition The old/current position of the element
     * @param direction The direction to move the element
     * @return The new position of the element
     */
    public Position getNewPosition(Position oldPosition, Direction direction) {
        switch (direction) {
            case NORTH:
                return new Position(oldPosition.getXCoordinate(), oldPosition.getYCoordinate() - 1);
            case SOUTH:
                return new Position(oldPosition.getXCoordinate(), oldPosition.getYCoordinate() + 1);
            case EAST:
                return new Position(oldPosition.getXCoordinate() + 1, oldPosition.getYCoordinate());
            case WEST:
                return new Position(oldPosition.getXCoordinate() - 1, oldPosition.getYCoordinate());
            default:
                throw new IllegalArgumentException("It's not possible to move in that direction.");
        }
    }

    /**
     * Gets the tile on a specific position
     * @param position The position to get a tile from
     * @return The tile on the given position
     */
    public Tile getTileOnPosition(Position position) {
        if (!isValidPosition(position)) {
            throw new IllegalArgumentException("Position is not on the board!");
        }
        return tiles.getElement(position.getXCoordinate(), position.getYCoordinate());
    }

    /**
     * Gets a list of BoardElementContainers, containing all tiles and positions of given tile types
     * @param tiles The tiles you want all positions for
     * @return A list of BoardElementContainers
     */
    public List<BoardElementContainer<Tile>> getPositionsOfTileOnBoard(TileType ... tiles) {
        List<BoardElementContainer<Tile>> combinedList = new ArrayList<>();
        for (TileType tile : tiles) {
            combinedList.addAll(makeTileList(tile, this.tiles));
        }
        return combinedList;
    }

    /**
     * Gets a list of BoardElementContainers, containing all tiles and positions of given wall types
     * @param walls The walls you want all positions for
     * @return A list of BoardElementContainers
     */
    public List<BoardElementContainer<Wall>> getPositionsOfWallOnBoard(WallType... walls) {
        List<BoardElementContainer<Wall>> combinedList = new ArrayList<>();
        for (WallType wall : walls) {
            combinedList.addAll(makeTileList(wall, this.walls));
        }
        return combinedList;
    }

    /**
     * Checks if a potential move would be blocked by a wall
     * @param robotPosition The current position of whatever is trying to move
     * @param newPosition The position something is trying to move to
     * @param direction The direction something is going
     * @return True if a wall would stop its path
     */
    public boolean moveIsStoppedByWall(Position robotPosition, Position newPosition, Direction direction) {
            return hasWallFacing(robotPosition, direction) || (isValidPosition(newPosition) &&
                    hasWallFacing(newPosition, Direction.getReverseDirection(direction)));
    }

    /**
     * Checks whether a given position is valid
     * @param position The position to test
     * @return True if the position is valid. False otherwise
     */
    private boolean isValidPosition(Position position) {
        return position.getXCoordinate() >= 0
                && position.getXCoordinate() < boardWidth
                && position.getYCoordinate() >= 0
                && position.getYCoordinate() < boardHeight;
    }

    /**
     * Checks if the robot is about to step outside of the board, and kills it if it does
     * @param robot The robot attempting to move
     * @param newPosition The position the robot is attempting to move to
     * @return True if the robot was killed for leaving the board
     */
    private boolean killRobotIfGoesOutsideMap(Robot robot, Position newPosition) {
        if (!isValidPosition(newPosition)) {
            killRobot(robot);
            return true;
        }
        return false;
    }

    /**
     * Checks the tile the robot is about to step on and kills it if the tile is dangerous
     * @param robot The robot attempting to move
     * @param newPosition The position the robot is attempting to move to
     */
    private void killRobotIfStepsOnDangerousTile(Robot robot, Position newPosition) {
        Tile tileRobotStepsOn = tiles.getElement(newPosition.getXCoordinate(), newPosition.getYCoordinate());
        if (tileRobotStepsOn == null) {
            throw new IllegalArgumentException("The game board is missing a tile. This should not happen.");
        }
        TileType tileTypeRobotStepsOn = tileRobotStepsOn.getTileType();
        List<TileType> dangerousTiles = new ArrayList<>();
        dangerousTiles.add(TileType.HOLE);
        dangerousTiles.add(TileType.PIT_CORNER);
        dangerousTiles.add(TileType.PIT_EMPTY);
        dangerousTiles.add(TileType.PIT_FULL);
        dangerousTiles.add(TileType.PIT_NORMAL);
        dangerousTiles.add(TileType.PIT_U);
        if (dangerousTiles.contains(tileTypeRobotStepsOn)) {
            killRobot(robot);
        }
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
        robot.setAmountOfLives(robot.getAmountOfLives() - 1);
        robots.remove(robot.getRobotId());
        deadRobots.add(robot);
    }

    /**
     * Checks whether there exists a robot on a specific position
     * @param position The position to check
     * @return True if there is a robot on the specified position
     */
    public boolean hasRobotOnPosition(Position position) {
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
     * Gets all elements on a grid
     * @param grid The grid to get elements from
     * @param <K> The type of the elements int the grid
     * @return A list containing all the elements in the grid
     */
    private <K> List<K> getAllElementsFromGrid(IGrid<K> grid) {
        List<K> elements = new ArrayList<>();
        for (int y = grid.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < grid.getWidth(); x++) {
                elements.add(grid.getElement(x, y));
            }
        }
        return elements;
    }

    /**
     * Finds all position of an obj and makes a list of BoardElementContainers
     * @param type Type of obj
     * @param grid Grid to search
     * @param <K> Type of type
     * @param <T> Type of grid
     * @return List of BoardElementContainers
     */
    private <K,T> List<BoardElementContainer<T>> makeTileList(K type, IGrid<T> grid) {
        List<BoardElementContainer<T>> objList = new ArrayList<>();

        for (int y = grid.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < grid.getWidth(); x++) {
                T gridElement = grid.getElement(x, y);
                if (gridElement != null) {
                    if (gridElement.getClass().isAssignableFrom(Tile.class)) {
                        Tile tile = (Tile) gridElement;
                        if (tile.getTileType() == type) {
                            objList.add(new BoardElementContainer<>(gridElement, new Position(x,y)));
                        }
                    } else if (gridElement.getClass().isAssignableFrom(Wall.class)) {
                        Wall wall = (Wall) gridElement;
                        if (wall.getWallType() == type) {
                            objList.add(new BoardElementContainer<>(gridElement, new Position(x,y)));
                        }
                    } else {
                        throw new IllegalArgumentException("Grid has unknown type.");
                    }
                }
            }
        }
        return objList;
    }

    /**
     * Kills all robots that has taken too much damage
     */
    private void killAllDeadRobot(){
        for (Robot robot:robots.values()) {
            if(robot.getDamageTaken()>=10){
                killRobot(robot);
            }
        }
    }

    /**
     * Fires one wall laser
     * @param laser the wall laser that is being fired
     */
    private void fireOneWallLaser(BoardElementContainer<Wall> laser){
        Position hitPosition = lineForTheLaser(Direction.getReverseDirection(laser.getElement().getDirection()),laser.getPosition());
        if(getRobotOnPosition(hitPosition)!=null){
            laserDamage(laser.getElement().getWallType(),robots.get(getRobotOnPosition(hitPosition)));
        }
    }

    /**
     * fires on robot laser
     * @param robotPosition the position of the robot firing the laser
     * @param robotDirection the direction the robot is facing
     */
    private void fireOneRobotLaser(Position robotPosition, Direction robotDirection){
        Position positionInFront = getNewPosition(robotPosition,robotDirection);

        if(!isValidPosition(positionInFront)||moveIsStoppedByWall(robotPosition,positionInFront,robotDirection)){
            return;
        }
        Position hitPosition = lineForTheLaser(robotDirection,positionInFront);
        if(getRobotOnPosition(hitPosition)!=null){
            laserDamage(WallType.WALL_LASER_SINGLE,robots.get(getRobotOnPosition(hitPosition)));
        }
    }

    /**
     * Applies the damage form the laser to the robot the laser hit
     * @param laserType the type of laser that hit the robot
     * @param robot the robot getting hit by the robot
     */
    private void laserDamage(WallType laserType, Robot robot){
        robot.setDamageTaken(robot.getDamageTaken()+laserType.getWallTypeID()-2);
    }

    /**
     * Gets the Position of where the laser hits something
     * @param direction the direction of the laser
     * @param startPosition the start positon of the laser
     * @return the position of the element that stopped the laser
     */
    private Position lineForTheLaser(Direction direction, Position startPosition){
        Position newPosition = getNewPosition(startPosition,direction);
        if(!isValidPosition(newPosition) || moveIsStoppedByWall(startPosition,newPosition,direction)){
            return startPosition;
        }
        else if(getRobotOnPosition(newPosition)!=null){
            return newPosition;
        }
        else{
            return lineForTheLaser(direction,newPosition);
        }
    }



}