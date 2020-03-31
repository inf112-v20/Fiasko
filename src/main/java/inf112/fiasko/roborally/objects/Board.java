package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Direction;
import inf112.fiasko.roborally.element_properties.ParticleType;
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
    private IGrid<Particle> particles;
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
        this.particles = new Grid<>(tiles.getWidth(), tiles.getHeight());
        this.deadRobots = new ArrayList<>();
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
     * Gets all the particles from the board
     * @return A list of all the particles on the board
     */
    public List<Particle> getParticles() {
        return getAllElementsFromGrid(particles);
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
     * Sets the power down status of the robot
     * @param robotID The robot id of the robot
     * @param powerdown The status of the powerdown
     */
    public void setPowerDown(RobotID robotID,Boolean powerdown){
        robots.get(robotID).setPowerDown(powerdown);
    }

    /**
     * Gets the power down status of the robot
     * @param robotID The robot id of the robot
     */
    public void getPowerDown(RobotID robotID) {
        robots.get(robotID).isInPowerDown();
    }

    /**
     * removes one damage for a given robot given that it has taken som damage before
     * @param robotID the ID of the robot
     */
    public void repairRobotOnTile(RobotID robotID){
        Robot robot = robots.get(robotID);
        int newDamage =Math.max(robot.getDamageTaken()-1,0);
        robot.setDamageTaken(newDamage);
    }

    /**
     * sets the damage taken of robots in powerdown to 0
     */
    public void executePowerdown(){
        for (Robot robot:robots.values()) {
            if(robot.isInPowerDown()){
                robot.setDamageTaken(0);
            }
        }
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
     * Checks whether a given tile is a conveyor belt
     * @param tile The tile to check
     * @return True if the tile is a conveyor belt
     */
    public boolean isConveyorBelt(Tile tile) {
        if (tile == null) {
            return false;
        }
        switch (tile.getTileType()) {
            case CONVEYOR_BELT_SLOW:
            case CONVEYOR_BELT_FAST:
            case CONVEYOR_BELT_FAST_LEFT:
            case CONVEYOR_BELT_FAST_RIGHT:
            case CONVEYOR_BELT_FAST_SIDE_ENTRANCE_LEFT:
            case CONVEYOR_BELT_FAST_SIDE_ENTRANCE_RIGHT:
            case CONVEYOR_BELT_FAST_SIDE_ENTRANCES:
            case CONVEYOR_BELT_SLOW_LEFT:
            case CONVEYOR_BELT_SLOW_RIGHT:
            case CONVEYOR_BELT_SLOW_SIDE_ENTRANCE_LEFT:
            case CONVEYOR_BELT_SLOW_SIDE_ENTRANCE_RIGHT:
            case CONVEYOR_BELT_SLOW_SIDE_ENTRANCES:
                return true;
            default:
                return false;
        }
    }

    /**
     * Teleports a robot to some position without verification
     *
     * Be quite careful about using this method. No validation will me done. The robot will magically disappear from
     * one position and appear on another, hence the name. This method should only be used when the new position has
     * been confirmed available.
     *
     * @param robotID The id of the robot to teleport
     * @param newPosition The position the robot should teleport to
     */
    public void teleportRobot(RobotID robotID, Position newPosition) {
        robots.get(robotID).setPosition(newPosition);
    }

    /**
     * Checks whether a given conveyor belt is able to move in its direction
     * @param conveyorBelt The conveyor belt to move
     * @param iterations The number of recursive calls already executed
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
        Tile tileInFront = getTileOnPosition(positionInFront);
        //If a conveyor belt will move the robot outside the map, the move is valid
        if (!isValidPosition(positionInFront)) {
            return true;
        }
        //The tile in front of the robot is not a conveyor belt and has something on it stopping the conveyor belt
        if (!isConveyorBelt(tileInFront) &&
                hasFrontConflict(conveyorBeltPosition, positionInFront, conveyorBeltDirection)) {
                return false;
        }
        //There is another robot trying to enter the same crossing
        if (hasCrossingConflict(positionInFront, conveyorBeltDirection)) {
            return false;
        }
        //The way forward seems clear
        if (!hasRobotOnPosition(positionInFront)) {
            return true;
        }
        return conveyorBeltCanMove(new BoardElementContainer<>(tileInFront, positionInFront), iterations + 1);
    }

    /**
     * Checks whether a conveyor belt has anything in front of it preventing it from moving forward
     * @param conveyorBeltPosition The position of the conveyor belt
     * @param positionInFront The position in front of the conveyor belt
     * @param conveyorBeltDirection The direction of the conveyor belt
     * @return True if the conveyor belt cannot move forward
     */
    private boolean hasFrontConflict(Position conveyorBeltPosition, Position positionInFront,
                                     Direction conveyorBeltDirection) {
        return moveIsStoppedByWall(conveyorBeltPosition, positionInFront, conveyorBeltDirection) ||
                hasRobotOnPosition(positionInFront);
    }

    /**
     * Checks whether a conveyor belt has a conflict in a crossing
     * @param crossingPosition The position of the crossing
     * @param conveyorBeltDirection The direction of the conveyor belt
     * @return True if there is a conflict. False otherwise
     */
    private boolean hasCrossingConflict(Position crossingPosition, Direction conveyorBeltDirection) {
        Position frontLeftPosition = getNewPosition(crossingPosition,
                Direction.getLeftRotatedDirection(conveyorBeltDirection));
        Tile frontLeftTile = getTileOnPosition(frontLeftPosition);
        Position frontRightPosition = getNewPosition(crossingPosition,
                Direction.getRightRotatedDirection(conveyorBeltDirection));
        Tile frontRightTile = getTileOnPosition(frontRightPosition);
        Position twoForwardPosition = getNewPosition(crossingPosition, conveyorBeltDirection);
        Tile twoForwardTile = getTileOnPosition(twoForwardPosition);
        //If another robot is standing on a conveyor belt pointing to the tile in front, a conflict happens
        return (isValidPosition(frontLeftPosition) && isConveyorBelt(frontLeftTile) && frontLeftTile.getDirection() ==
                Direction.getRightRotatedDirection(conveyorBeltDirection) && hasRobotOnPosition(frontLeftPosition)) ||
                (isValidPosition(frontRightPosition) && isConveyorBelt(frontRightTile)
                        && frontRightTile.getDirection() == Direction.getLeftRotatedDirection(conveyorBeltDirection)
                        && hasRobotOnPosition(frontRightPosition)) ||
                (isValidPosition(twoForwardPosition) && isConveyorBelt(twoForwardTile)
                        && twoForwardTile.getDirection() == Direction.getReverseDirection(conveyorBeltDirection)
                        && hasRobotOnPosition(twoForwardPosition));
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
                robot.setDamageTaken(2);
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
     * Fires all lasers on the board and kills any robot that has taken to much damage after all lasers have fired.
     */
    public void fireAllLasers() {
        List<BoardElementContainer<Wall>> listOfWallLasers = getPositionsOfWallOnBoard(WallType.WALL_LASER_SINGLE,
                WallType.WALL_LASER_DOUBLE);
        for (Robot robot:robots.values()) {
            fireRobotLaser(robot.getPosition(),robot.getFacingDirection());
        }
        for (BoardElementContainer<Wall> laser : listOfWallLasers) {
            fireWallLaser(laser);
        }
    }

    /**
     * Does necessary cleanup after lasers have been fired
     */
    public void doLaserCleanup() {
        this.particles = new Grid<>(tiles.getWidth(), tiles.getHeight());
        killAllHeavilyDamagedRobots();
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
     * Checks whether there exists a robot on a specific position
     * @param position The position to check
     * @return True if there is a robot on the specified position
     */
    public boolean hasRobotOnPosition(Position position) {
        return getRobotOnPosition(position) != null;
    }

    /**
     * Checks if a potential move would be blocked by a wall
     * @param robotPosition The current position of whatever is trying to move
     * @param newPosition The position something is trying to move to
     * @param direction The direction something is going
     * @return True if a wall would stop its path
     */
    private boolean moveIsStoppedByWall(Position robotPosition, Position newPosition, Direction direction) {
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
     * Finds all tiles/walls with a certain type
     * @param type The type of tile/wall to look for
     * @param grid The grid to look through
     * @param <K> Type of the type to look for
     * @param <T> Type of the grid
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
                            objList.add(new BoardElementContainer<>(gridElement, new Position(x, y)));
                        }
                    } else if (gridElement.getClass().isAssignableFrom(Wall.class)) {
                        Wall wall = (Wall) gridElement;
                        if (wall.getWallType() == type) {
                            objList.add(new BoardElementContainer<>(gridElement, new Position(x, y)));
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
     * Kills all robots that have taken too much damage
     */
    private void killAllHeavilyDamagedRobots() {
        for (Robot robot:robots.values()) {
            if (robot.getDamageTaken() >= 10) {
                killRobot(robot);
            }
        }
    }

    /**
     * Fires one wall laser
     * @param wallLaser The wall laser being fired
     */
    private void fireWallLaser(BoardElementContainer<Wall> wallLaser) {
        Direction laserDirection = Direction.getReverseDirection(wallLaser.getElement().getDirection());
        List<Position> laserTargets = new ArrayList<>();
        getLaserTarget(laserDirection, wallLaser.getPosition(), laserTargets);
        Position hitPosition = laserTargets.get(laserTargets.size()-1);
        WallType laserType = wallLaser.getElement().getWallType();
        updateLaserDisplay(laserTargets, laserDirection, laserType);
        if (getRobotOnPosition(hitPosition) != null) {
            applyLaserDamage(laserType, robots.get(getRobotOnPosition(hitPosition)));
        }
    }

    /**
     * Fires one robot laser
     * @param robotPosition The position of the robot firing the laser
     * @param robotDirection The direction the robot is facing
     */
    private void fireRobotLaser(Position robotPosition, Direction robotDirection) {
        Position positionInFront = getNewPosition(robotPosition,robotDirection);
        if (!isValidPosition(positionInFront) || moveIsStoppedByWall(robotPosition, positionInFront, robotDirection)) {
            return;
        }
        List<Position> laserTargets = new ArrayList<>();
        getLaserTarget(robotDirection, positionInFront, laserTargets);
        Position hitPosition = laserTargets.get(laserTargets.size()-1);
        WallType laserType = WallType.WALL_LASER_SINGLE;
        updateLaserDisplay(laserTargets, robotDirection, laserType);
        if (getRobotOnPosition(hitPosition) != null) {
            applyLaserDamage(laserType, robots.get(getRobotOnPosition(hitPosition)));
        }
    }

    /**
     * Applies the damage form the laser to the robot the laser hit
     * @param laserType The type of laser that hit the robot
     * @param robot The robot getting hit by the robot
     */
    private void applyLaserDamage(WallType laserType, Robot robot) {
        robot.setDamageTaken(robot.getDamageTaken() + laserType.getWallTypeID() - 2);
    }

    /**
     * Gets all the positions the laser fires at
     * @param direction The direction of the laser
     * @param startPosition The start position of the laser
     * @param targets The list to update with target positions
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
     * @param laserTargets The tiles the laser will hit
     * @param laserDirection The direction of the laser
     * @param laserType The type of the laser
     */
    private void updateLaserDisplay(List<Position> laserTargets, Direction laserDirection, WallType laserType) {
        for (Position laserTarget : laserTargets) {
            updateLaserBeamOnParticleGrid(laserTarget, laserDirection, laserType);
        }
    }

    /**
     * Updates a laser beam on the particle grid
     * @param addPosition The position of the beam
     * @param laserDirection The direction of the beam
     * @param laserType The type of the laser shooting
     */
    private void updateLaserBeamOnParticleGrid(Position addPosition, Direction laserDirection, WallType laserType) {
        int positionX = addPosition.getXCoordinate();
        int positionY = addPosition.getYCoordinate();
        int numberOfLasers;
        switch (laserType) {
            case WALL_LASER_SINGLE:
                numberOfLasers = 1;
                break;
            case WALL_LASER_DOUBLE:
                numberOfLasers = 2;
                break;
            default:
                throw new IllegalArgumentException("Laser type submitted is not a laser.");
        }
        ParticleType type;
        Particle particleAtPosition = particles.getElement(positionX, positionY);
        if (particleAtPosition != null && Direction.arePerpendicular(particleAtPosition.getDirection(),
                laserDirection)) {
            type = numberOfLasers == 1 ? ParticleType.LASER_BEAM_SINGLE_CROSS :
                    ParticleType.LASER_BEAM_DOUBLE_CROSS;
        } else {
            type = numberOfLasers == 1 ? ParticleType.LASER_BEAM_SINGLE : ParticleType.LASER_BEAM_DOUBLE;
        }
        particles.setElement(positionX, positionY, new Particle(type, laserDirection));
    }

}