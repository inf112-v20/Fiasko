package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.objects.properties.Direction;
import inf112.fiasko.roborally.objects.properties.ParticleType;
import inf112.fiasko.roborally.objects.properties.Position;
import inf112.fiasko.roborally.objects.properties.RobotID;
import inf112.fiasko.roborally.objects.properties.TileType;
import inf112.fiasko.roborally.objects.properties.WallType;
import inf112.fiasko.roborally.utility.GridUtil;
import inf112.fiasko.roborally.utility.LaserHelper;

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
    private Grid<Wall> walls;
    private Grid<Tile> tiles;
    private Grid<Particle> particles;
    private Map<RobotID, Robot> robots;
    private List<Robot> deadRobots;
    private List<RobotID> realDeadRobots;
    private List<BoardElementContainer<Wall>> wallLasers;
    private List<BoardElementContainer<Tile>> repairTiles;

    /**
     * Initializes the board
     *
     * @param tiles  A grid containing all tiles
     * @param walls  A grid containing all walls
     * @param robots A list of all robots in the game
     */
    public Board(Grid<Tile> tiles, Grid<Wall> walls, List<Robot> robots) {
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
        this.particles = new ListGrid<>(tiles.getWidth(), tiles.getHeight());
        this.deadRobots = new ArrayList<>();
        this.realDeadRobots = new ArrayList<>();
        wallLasers = getPositionsOfWallsOnBoard(WallType.WALL_LASER_SINGLE,
                WallType.WALL_LASER_DOUBLE, WallType.WALL_LASER_TRIPLE);
        repairTiles = getPositionsOfTilesOnBoard(TileType.WRENCH,
                TileType.WRENCH_AND_HAMMER, TileType.FLAG_1, TileType.FLAG_2, TileType.FLAG_3, TileType.FLAG_4);
    }

    /**
     * Gets a list of robots no longer part of the game
     *
     * @return Robots no longer part of the game
     */
    public List<RobotID> getRealDeadRobots() {
        return realDeadRobots;
    }

    /**
     * Gets the height of the board
     *
     * @return The height of the board
     */
    public int getBoardHeight() {
        return boardHeight;
    }

    /**
     * Gets the width of the board
     *
     * @return The width of the board
     */
    public int getBoardWidth() {
        return boardWidth;
    }

    /**
     * Gets all alive robots from the board
     *
     * @return A list of alive robots
     */
    public List<Robot> getAliveRobots() {
        List<Robot> robotsCopy = new ArrayList<>(robots.values());
        robotsCopy.replaceAll(Robot::copy);
        return robotsCopy;
    }

    /**
     * Gets all robots from the board
     *
     * @return A list of robots
     */
    public List<Robot> getAllRobots() {
        List<Robot> robotsCopy = new ArrayList<>(deadRobots);
        robotsCopy.replaceAll(Robot::copy);
        robotsCopy.addAll(getAliveRobots());
        return robotsCopy;
    }

    /**
     * Gets all the tiles from the board
     *
     * @return A list of all tiles on the board
     */
    public List<Tile> getTiles() {
        return GridUtil.getAllElementsFromGrid(tiles);
    }

    /**
     * Gets all the walls from the board
     *
     * @return A list of all the walls on the board
     */
    public List<Wall> getWalls() {
        return GridUtil.getAllElementsFromGrid(walls);
    }

    /**
     * Gets all the particles from the board
     *
     * @return A list of all the particles on the board
     */
    public List<Particle> getParticles() {
        return GridUtil.getAllElementsFromGrid(particles);
    }

    /**
     * Rotates a robot to the right
     *
     * @param robotID The id of the robot to rotate
     */
    public void rotateRobotLeft(RobotID robotID) {
        Robot robot = robots.get(robotID);
        Direction newDirection = Direction.getLeftRotatedDirection(robot.getFacingDirection());
        robot.setFacingDirection(newDirection);
    }

    /**
     * Rotates a robot to the left
     *
     * @param robotID The id of the robot to rotate
     */
    public void rotateRobotRight(RobotID robotID) {
        Robot robot = robots.get(robotID);
        Direction newDirection = Direction.getRightRotatedDirection(robot.getFacingDirection());
        robot.setFacingDirection(newDirection);
    }

    /**
     * Moves a robot one unit forward according to the direction it's currently facing
     *
     * @param robotID The robot to move
     */
    public void moveRobotForward(RobotID robotID) {
        moveRobot(robotID, robots.get(robotID).getFacingDirection());
    }

    /**
     * Moves a robot one unit backwards according to the direction it's currently facing
     *
     * @param robotID The robot to move
     */
    public void reverseRobot(RobotID robotID) {
        moveRobot(robotID, Direction.getReverseDirection(robots.get(robotID).getFacingDirection()));
    }

    /**
     * Sets the power down status of the robot
     *
     * @param robotID   The robot id of the robot
     * @param powerDown The status of the power down
     */
    public void setPowerDown(RobotID robotID, Boolean powerDown) {
        Robot robot = getRobot(robotID);
        if (robot != null) {
            robot.setPowerDown(powerDown);
        }
    }

    /**
     * Sets the backup position of a given robot to a given position
     *
     * @param robotID  The robot to change backup position for
     * @param position The robot's new backup position
     */
    public void setBackupPositionOfRobot(RobotID robotID, Position position) {
        robots.get(robotID).setBackupPosition(position);
    }

    /**
     * Gets the power down status of the robot
     *
     * @param robotID The robot id of the robot
     * @return The power down status of the robot
     */
    public boolean getPowerDown(RobotID robotID) {
        Robot robot = getRobot(robotID);
        return robot != null && robot.isInPowerDown();
    }

    /**
     * Gets a robot from the list of dead robots
     *
     * @param robotID The id of the robot to get
     * @return The dead robot
     */
    private Robot getRobotFromDeadRobots(RobotID robotID) {
        for (Robot robot : deadRobots) {
            if (robot.getRobotId() == robotID) {
                return robot;
            }
        }
        return null;
    }

    /**
     * Removes one damage for a given robot given that it has taken som damage before
     *
     * @param robotID The ID of the robot
     */
    public void repairRobotOnTile(RobotID robotID) {
        Robot robot = robots.get(robotID);
        int newDamage = Math.max(robot.getDamageTaken() - 1, 0);
        robot.setDamageTaken(newDamage);
    }

    /**
     * Sets the damage taken of robots in power down to 0
     */
    public void executePowerDown() {
        for (Robot robot : robots.values()) {
            if (robot.isInPowerDown()) {
                robot.setDamageTaken(0);
            }
        }
    }

    /**
     * Get the damage of a specific robot
     *
     * @param robot The RobotID of a robot
     * @return The amount of damage the robot currently has
     */
    public int getRobotDamage(RobotID robot) {
        return robots.get(robot).getDamageTaken();
    }

    /**
     * Moves a robot one unit in a specified direction
     *
     * @param robotID   Id of the robot to move
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
        killRobotIfStepsInHole(robot, newPosition);
        return true;
    }

    /**
     * Checks whether a given tile is a conveyor belt
     *
     * @param tile The tile to check
     * @return True if the tile is a conveyor belt
     */
    public boolean isConveyorBelt(Tile tile) {
        if (tile == null) {
            return false;
        }
        int tileTypeId = tile.getType().getTileTypeID();
        return tileTypeId >= 5 && tileTypeId <= 16;
    }

    /**
     * Teleports a robot to some position without verification
     *
     * <p>Be quite careful about using this method. No validation will me done. The robot will magically disappear from
     * one position and appear on another, hence the name. This method should only be used when the new position has
     * been confirmed available.</p>
     *
     * @param robotID     The id of the robot to teleport
     * @param newPosition The position the robot should teleport to
     */
    public void teleportRobot(RobotID robotID, Position newPosition) {
        robots.get(robotID).setPosition(newPosition);
    }

    /**
     * Checks whether a given conveyor belt is able to move in its direction
     *
     * @param conveyorBelt The conveyor belt to move
     * @param iterations   The number of recursive calls already executed
     * @return True if nothing is blocking its movement
     */
    public boolean conveyorBeltCanMove(BoardElementContainer<Tile> conveyorBelt, int iterations) {
        if (!isConveyorBelt(conveyorBelt.getElement())) {
            throw new IllegalArgumentException("Input to function is of invalid tile type.");
        }
        //Prevents an infinite loop if robots are in a small conveyor belt loop
        if (iterations >= 8) {
            return true;
        }
        Position conveyorBeltPosition = conveyorBelt.getPosition();
        Direction conveyorBeltDirection = conveyorBelt.getElement().getDirection();
        //Ignore conveyor belts without a robot
        if (!hasRobotOnPosition(conveyorBeltPosition)) {
            return true;
        }
        Position positionInFront = getNewPosition(conveyorBeltPosition, conveyorBeltDirection);
        //The tile in front of the robot is not a conveyor belt and has something on it stopping the conveyor belt
        if (conveyorBeltIsStoppedByWallOrRobot(positionInFront, conveyorBeltPosition, conveyorBeltDirection)) {
            return false;
        }
        //If a conveyor belt will move the robot outside the map, the move is valid
        if (!isValidPosition(positionInFront)) {
            return true;
        }
        Tile tileInFront = getTileOnPosition(positionInFront);
        //There is another robot trying to enter the same crossing
        if (conveyorBeltHasCrossingConflict(positionInFront, conveyorBeltDirection)) {
            return false;
        }
        //The way forward seems clear
        if (!hasRobotOnPosition(positionInFront)) {
            return true;
        }
        return conveyorBeltCanMove(new BoardElementContainer<>(tileInFront, positionInFront), iterations + 1);
    }

    /**
     * Checks whether a conveyor belt movement is stopped by either a wall or a robot
     *
     * @param positionInFront       The position in front of the conveyor belt
     * @param conveyorBeltPosition  The position of the conveyor belt
     * @param conveyorBeltDirection The direction of the conveyor belt
     * @return True if the conveyor belt cannot move
     */
    private boolean conveyorBeltIsStoppedByWallOrRobot(Position positionInFront, Position conveyorBeltPosition,
                                                       Direction conveyorBeltDirection) {
        return ((!isValidPosition(positionInFront) && moveIsStoppedByWall(conveyorBeltPosition, positionInFront,
                conveyorBeltDirection)) || (isValidPosition(positionInFront) &&
                !isConveyorBelt(getTileOnPosition(positionInFront)) &&
                conveyorBeltHasFrontConflict(conveyorBeltPosition, positionInFront, conveyorBeltDirection)));
    }

    /**
     * Checks whether a conveyor belt has anything in front of it preventing it from moving forward
     *
     * @param conveyorBeltPosition  The position of the conveyor belt
     * @param positionInFront       The position in front of the conveyor belt
     * @param conveyorBeltDirection The direction of the conveyor belt
     * @return True if the conveyor belt cannot move forward
     */
    private boolean conveyorBeltHasFrontConflict(Position conveyorBeltPosition, Position positionInFront,
                                                 Direction conveyorBeltDirection) {
        return moveIsStoppedByWall(conveyorBeltPosition, positionInFront, conveyorBeltDirection) ||
                hasRobotOnPosition(positionInFront);
    }

    /**
     * Checks whether a conveyor belt has a conflict in a crossing
     *
     * @param crossingPosition      The position of the crossing
     * @param conveyorBeltDirection The direction of the conveyor belt
     * @return True if there is a conflict. False otherwise
     */
    private boolean conveyorBeltHasCrossingConflict(Position crossingPosition, Direction conveyorBeltDirection) {
        //Checks for conflict at a conveyor belt coming in from the left
        boolean frontLeftConflict = conveyorBeltHasConflict(getNewPosition(crossingPosition,
                Direction.getLeftRotatedDirection(conveyorBeltDirection)),
                Direction.getRightRotatedDirection(conveyorBeltDirection));
        //Checks for conflict at a conveyor belt coming in from the right
        boolean frontRightConflict = conveyorBeltHasConflict(getNewPosition(crossingPosition,
                Direction.getRightRotatedDirection(conveyorBeltDirection)),
                Direction.getLeftRotatedDirection(conveyorBeltDirection));
        //Checks for conflict at a conveyor belt at the opposite side of the crossing
        boolean frontConflict = conveyorBeltHasConflict(getNewPosition(crossingPosition, conveyorBeltDirection),
                Direction.getReverseDirection(conveyorBeltDirection));
        return frontLeftConflict || frontRightConflict || frontConflict;
    }

    /**
     * Checks whether a possible conflict position has a conflict
     *
     * @param conflictPosition  The position with a potential conflict
     * @param conflictDirection The direction necessary to count as a conflict
     * @return True if the position has a conflict
     */
    private boolean conveyorBeltHasConflict(Position conflictPosition, Direction conflictDirection) {
        if (!isValidPosition(conflictPosition)) {
            return false;
        }
        Tile conflictTile = getTileOnPosition(conflictPosition);
        return (isConveyorBelt(conflictTile) && conflictTile.getDirection() == conflictDirection &&
                hasRobotOnPosition(conflictPosition));
    }

    /**
     * Moves all dead robots to their backups and makes them part of the board again, and if a robot has no lives
     * it will be removed from the game
     */
    public void respawnRobots() {
        for (Robot robot : deadRobots) {
            if (robot.getAmountOfLives() > 0) {
                respawnRobot(robot);
                robot.setDamageTaken(2);
                robots.put(robot.getRobotId(), robot);
            } else {
                realDeadRobots.add(robot.getRobotId());
            }
        }
        deadRobots = new ArrayList<>();
    }

    /**
     * Re-spawns a robot at the first available position relative to its backup position
     *
     * @param robot The robot to re-spawn
     */
    private void respawnRobot(Robot robot) {
        Position backupPosition = robot.getBackupPosition();
        int startX = backupPosition.getXCoordinate();
        int startY = backupPosition.getYCoordinate();
        if (!hasRobotOnPosition(backupPosition)) {
            robot.setPosition(backupPosition);
            //Tries to set the robot's facing direction to the true up direction
            List<BoardElementContainer<Tile>> robotSpawn = getPositionsOfTilesOnBoard(TileType.ROBOT_SPAWN_1);
            if (robotSpawn.size() == 1) {
                robot.setFacingDirection(robotSpawn.get(0).getElement().getDirection());
            } else {
                robot.setFacingDirection(Direction.NORTH);
            }
            return;
        }
        int squareSize = 1;
        boolean hasRespawned = false;
        while (!hasRespawned) {
            hasRespawned = tryRobotRespawn(robot, squareSize, startX, startY, Direction.NORTH) ||
                    tryRobotRespawn(robot, squareSize, startX, startY, Direction.EAST) ||
                    tryRobotRespawn(robot, squareSize, startX, startY, Direction.SOUTH) ||
                    tryRobotRespawn(robot, squareSize, startX, startY, Direction.WEST);
            squareSize++;
        }
    }

    /**
     * Tries to re-spawn a robot on one of the positions described
     *
     * @param robot     The robot to re-spawn
     * @param size      The size of the square relative to the robot's spawn to try
     * @param startX    The x coordinate of the robot's backup position
     * @param startY    The y coordinate of the robot's backup position
     * @param direction The direction of the face of the square to check
     * @return Whether the robot was re-spawned
     */
    private boolean tryRobotRespawn(Robot robot, int size, int startX, int startY, Direction direction) {
        int axis;
        for (int i = 1; i <= size; i++) {
            if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                axis = startX;
            } else {
                axis = startY;
            }
            for (int j = axis - i; j < axis + i; j++) {
                Position potentialSpawn = calculatePotentialRobotSpawn(startX, startY, direction, j, size);
                if (isValidPosition(potentialSpawn) && !hasHole(potentialSpawn) && !hasRobotOnPosition(potentialSpawn)) {
                    robot.setFacingDirection(direction);
                    robot.setPosition(potentialSpawn);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Calculates a potential robot spawn from the input
     *
     * @param startX    The start position's x coordinate
     * @param startY    The start position's y coordinate
     * @param direction The direction which is checked
     * @param j         The offset from the start position
     * @param size      The size of the square around the start position
     * @return The position described by the input
     */
    private Position calculatePotentialRobotSpawn(int startX, int startY, Direction direction, int j, int size) {
        switch (direction) {
            case NORTH:
                return new Position(j, startY - size);
            case SOUTH:
                return new Position(j, startY + size);
            case WEST:
                return new Position(startX - size, j);
            case EAST:
                return new Position(startX + size, j);
            default:
                throw new IllegalArgumentException("invalid direction for tile");
        }
    }

    /**
     * Checks whether a position contains a hole
     *
     * @param position The position to check
     * @return True if the position has a hole
     */
    private boolean hasHole(Position position) {
        TileType type = getTileOnPosition(position).getType();
        return type == TileType.HOLE || type == TileType.PIT_CORNER || type == TileType.PIT_EMPTY
                || type == TileType.PIT_FULL || type == TileType.PIT_NORMAL || type == TileType.PIT_U;
    }

    /**
     * Updates backup position of all robots on a repair tile
     */
    public void updateRobotBackups() {
        for (BoardElementContainer<Tile> repairTile : repairTiles) {
            Position position = repairTile.getPosition();
            if (hasRobotOnPosition(position)) {
                getRobot(getRobotOnPosition(position)).setBackupPosition(position);
            }
        }
    }

    /**
     * Returns a robot id for a robot on a specific position if such a robot exists
     *
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
     *
     * @param robot the ID of the robot you want to check
     * @return True/False based on if the robot was found.
     */
    public boolean isRobotAlive(RobotID robot) {
        return robots.containsKey(robot);
    }

    /**
     * Updates the flag of the robot if it stands on the correct flag.
     *
     * @param robotID The RobotID of a robot
     * @param flag    BoardElementContainer of the flag we check
     */
    public void updateRobotFlag(RobotID robotID, BoardElementContainer<Tile> flag) {
        Robot robot = robots.get(robotID);
        int flagNumber = flag.getElement().getType().getTileTypeID() % 16;
        if (flagNumber - 1 == robot.getLastFlagVisited()) {
            robot.setLastFlagVisited(flagNumber);
            setHasTouchedFlagThisTurn(robotID, true);
        }
    }

    /**
     * Gets the position 1 unit in a specific direction from another position
     *
     * @param oldPosition The old/current position of the element
     * @param direction   The direction to move the element
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
     * Fires all lasers on the board and kills any robot that has taken to much damage after all lasers have fired.
     */
    public void fireAllLasers() {
        for (Robot robot : robots.values()) {
            fireRobotLaser(robot.getPosition(), robot.getFacingDirection());
        }
        for (BoardElementContainer<Wall> laser : wallLasers) {
            fireWallLaser(laser);
        }
    }

    /**
     * Does necessary cleanup after lasers have been fired
     */
    public void doLaserCleanup() {
        this.particles = new ListGrid<>(tiles.getWidth(), tiles.getHeight());
        killAllHeavilyDamagedRobots();
    }

    /**
     * Gets the tile on a specific position
     *
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
     *
     * @param tiles The tiles you want all positions for
     * @return A list of BoardElementContainers
     */
    public List<BoardElementContainer<Tile>> getPositionsOfTilesOnBoard(TileType... tiles) {
        List<BoardElementContainer<Tile>> combinedList = new ArrayList<>();
        for (TileType tile : tiles) {
            combinedList.addAll(GridUtil.getMatchingElements(tile, this.tiles));
        }
        return combinedList;
    }

    /**
     * Gets a list of BoardElementContainers, containing all tiles and positions of given wall types
     *
     * @param walls The walls you want all positions for
     * @return A list of BoardElementContainers
     */
    public List<BoardElementContainer<Wall>> getPositionsOfWallsOnBoard(WallType... walls) {
        List<BoardElementContainer<Wall>> combinedList = new ArrayList<>();
        for (WallType wall : walls) {
            combinedList.addAll(GridUtil.getMatchingElements(wall, this.walls));
        }
        return combinedList;
    }

    /**
     * Checks whether there exists a robot on a specific position
     *
     * @param position The position to check
     * @return True if there is a robot on the specified position
     */
    public boolean hasRobotOnPosition(Position position) {
        return getRobotOnPosition(position) != null;
    }

    /**
     * Checks if a potential move would be blocked by a wall
     *
     * @param robotPosition The current position of whatever is trying to move
     * @param newPosition   The position something is trying to move to
     * @param direction     The direction something is going
     * @return True if a wall would stop its path
     */
    private boolean moveIsStoppedByWall(Position robotPosition, Position newPosition, Direction direction) {
        return hasWallFacing(robotPosition, direction) || (isValidPosition(newPosition) &&
                hasWallFacing(newPosition, Direction.getReverseDirection(direction)));
    }

    /**
     * Checks whether a given position is valid
     *
     * @param position The position to test
     * @return True if the position is valid. False otherwise
     */
    public boolean isValidPosition(Position position) {
        return position.getXCoordinate() >= 0
                && position.getXCoordinate() < boardWidth
                && position.getYCoordinate() >= 0
                && position.getYCoordinate() < boardHeight;
    }

    /**
     * Checks if the robot is about to step outside of the board, and kills it if it does
     *
     * @param robot       The robot attempting to move
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
     *
     * @param robot       The robot attempting to move
     * @param newPosition The position the robot is attempting to move to
     */
    private void killRobotIfStepsInHole(Robot robot, Position newPosition) {
        if (hasHole(newPosition)) {
            killRobot(robot);
        }
    }

    /**
     * Kills the robot
     *
     * <p>If the robot steps outside of the board, steps on a hole or takes too much damage, this method should be used to
     * properly dispose of the robot until the next turn.</p>
     *
     * @param robot The robot to kill
     */
    private void killRobot(Robot robot) {
        robot.setAmountOfLives(robot.getAmountOfLives() - 1);
        robots.remove(robot.getRobotId());
        deadRobots.add(robot);
    }

    /**
     * Checks whether a position has a wall facing a specific direction
     *
     * @param position  The position to check
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
     * Kills all robots that have taken too much damage
     */
    private void killAllHeavilyDamagedRobots() {
        List<Robot> robotList = new ArrayList<>(robots.values());
        for (Robot robot : robotList) {
            if (robot.getDamageTaken() >= 10) {
                killRobot(robot);
            }
        }
    }

    /**
     * Fires one wall laser
     *
     * @param wallLaser The wall laser being fired
     */
    private void fireWallLaser(BoardElementContainer<Wall> wallLaser) {
        //Reverses direction since a laser points the opposite direction of the wall it's attached to
        Direction laserDirection = Direction.getReverseDirection(wallLaser.getElement().getDirection());
        List<Position> laserTargets = new ArrayList<>();
        //Stores all positions visited by the laser beam in laserTargets
        getLaserTarget(laserDirection, wallLaser.getPosition(), laserTargets);
        Position hitPosition = laserTargets.get(laserTargets.size() - 1);
        WallType laserType = wallLaser.getElement().getType();
        //Displays the laser beam in the particle grid
        updateLaserDisplay(laserTargets, laserDirection, laserType);
        //Applies damage if the laser stops because it hits a robot
        if (getRobotOnPosition(hitPosition) != null) {
            applyLaserDamage(laserType, robots.get(getRobotOnPosition(hitPosition)));
        }
    }

    /**
     * Fires one robot laser
     *
     * @param robotPosition  The position of the robot firing the laser
     * @param robotDirection The direction the robot is facing
     */
    private void fireRobotLaser(Position robotPosition, Direction robotDirection) {
        Position positionInFront = getNewPosition(robotPosition, robotDirection);
        if (!isValidPosition(positionInFront) || moveIsStoppedByWall(robotPosition, positionInFront, robotDirection)) {
            return;
        }
        List<Position> laserTargets = new ArrayList<>();
        getLaserTarget(robotDirection, positionInFront, laserTargets);
        Position hitPosition = laserTargets.get(laserTargets.size() - 1);
        WallType laserType = WallType.WALL_LASER_SINGLE;
        updateLaserDisplay(laserTargets, robotDirection, laserType);
        if (getRobotOnPosition(hitPosition) != null) {
            applyLaserDamage(laserType, robots.get(getRobotOnPosition(hitPosition)));
        }
    }

    /**
     * Applies the damage form the laser to the robot the laser hit
     *
     * @param laserType The type of laser that hit the robot
     * @param robot     The robot getting hit by the robot
     */
    private void applyLaserDamage(WallType laserType, Robot robot) {
        int laserDamage;
        switch (laserType) {
            case WALL_LASER_SINGLE:
                laserDamage = 1;
                break;
            case WALL_LASER_DOUBLE:
                laserDamage = 2;
                break;
            case WALL_LASER_TRIPLE:
                laserDamage = 3;
                break;
            default:
                throw new IllegalArgumentException("Invalid laser type encountered.");
        }
        robot.setDamageTaken(robot.getDamageTaken() + laserDamage);
    }

    /**
     * Gets all the positions the laser fires at
     *
     * @param direction     The direction of the laser
     * @param startPosition The start position of the laser
     * @param targets       The list to update with target positions
     */
    private void getLaserTarget(Direction direction, Position startPosition, List<Position> targets) {
        Position newPosition = getNewPosition(startPosition, direction);
        targets.add(startPosition);
        if (!isValidPosition(newPosition) || moveIsStoppedByWall(startPosition, newPosition, direction) ||
                getRobotOnPosition(startPosition) != null) {
            return;
        }
        if (getRobotOnPosition(newPosition) != null) {
            targets.add(newPosition);
        } else {
            getLaserTarget(direction, newPosition, targets);
        }
    }

    /**
     * Adds any lasers in the targets list to the grid displaying lasers
     *
     * @param laserTargets   The tiles the laser will hit
     * @param laserDirection The direction of the laser
     * @param laserType      The type of the laser
     */
    private void updateLaserDisplay(List<Position> laserTargets, Direction laserDirection, WallType laserType) {
        for (Position laserTarget : laserTargets) {
            updateLaserBeamOnParticleGrid(laserTarget, laserDirection, laserType);
        }
    }

    /**
     * Updates a laser beam on the particle grid
     *
     * @param addPosition    The position of the beam
     * @param laserDirection The direction of the beam
     * @param laserType      The type of the laser shooting
     */
    private void updateLaserBeamOnParticleGrid(Position addPosition, Direction laserDirection, WallType laserType) {
        ParticleType laserParticleType = LaserHelper.getParticleFromLaserType(laserType);
        Particle laserParticle = new Particle(laserParticleType, laserDirection);
        int positionX = addPosition.getXCoordinate();
        int positionY = addPosition.getYCoordinate();
        Particle particleAtPosition = particles.getElement(positionX, positionY);
        if (particleAtPosition == null) {
            particles.setElement(positionX, positionY, laserParticle);
        } else {
            particles.setElement(positionX, positionY,
                    LaserHelper.getNewLaserBeamParticle(laserParticle, particleAtPosition));
        }
    }

    /**
     * Gets the int corresponding to the flag a robot has last visited
     *
     * @param robotID The robot to be checked
     * @return The flag last visited in a number
     */
    public int getLastFlagVisited(RobotID robotID) {
        return robots.get(robotID).getLastFlagVisited();
    }

    /**
     * Sets a boolean for if the robot has touched a flag this turn
     *
     * @param robotID    The robot to be checked
     * @param hasTouched If the robot has touched a flag this turn
     */
    public void setHasTouchedFlagThisTurn(RobotID robotID, boolean hasTouched) {
        Robot robot = getRobot(robotID);
        if (robot != null) {
            robot.setHasTouchedFlagThisTurn(hasTouched);
        }
    }

    /**
     * Checks a boolean for if the robot has touched a flag this turn
     *
     * @param robotID The robot to be checked
     * @return If the robot has touched a flag this turn
     */
    public boolean hasTouchedFlagThisTurn(RobotID robotID) {
        return robots.get(robotID).hasTouchedFlagThisTurn();
    }

    /**
     * Gets the robot with the given robot id
     *
     * @param robotID The id of the robot to get
     * @return The robot with the id or null if it's not in the game
     */
    private Robot getRobot(RobotID robotID) {
        Robot aliveRobot = robots.get(robotID);
        if (aliveRobot == null) {
            return getRobotFromDeadRobots(robotID);
        } else {
            return aliveRobot;
        }
    }

}