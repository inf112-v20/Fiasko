package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Action;
import inf112.fiasko.roborally.element_properties.Direction;
import inf112.fiasko.roborally.element_properties.Position;
import inf112.fiasko.roborally.element_properties.RobotID;
import inf112.fiasko.roborally.element_properties.TileType;
import inf112.fiasko.roborally.utility.BoardLoaderUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * This class represent a game which is drawable using libgdx
 */
public class RoboRallyGame implements IDrawableGame {
    private Board gameBoard;
    private List<BoardElementContainer<Tile>> cogwheels;
    private List<BoardElementContainer<Tile>> conveyorBelts;
    private List<BoardElementContainer<Tile>> fastConveyorBelts;

    public RoboRallyGame(boolean debug) {
        if (debug) {
            initializeDebugMode();
        } else {
            initializeGame();
        }
    }

    public RoboRallyGame() {
        initializeGame();
    }

    @Override
    public int getWidth() {
        return gameBoard.getBoardWidth();
    }

    @Override
    public int getHeight() {
        return gameBoard.getBoardHeight();
    }

    @Override
    public List<Tile> getTilesToDraw() {
        return gameBoard.getTiles();
    }

    @Override
    public List<Wall> getWallsToDraw() {
        return gameBoard.getWalls();
    }

    @Override
    public List<Particle> getParticlesToDraw() {
        return gameBoard.getParticles();
    }

    @Override
    public List<Robot> getRobotsToDraw() {
        return gameBoard.getAliveRobots();
    }

    /**
     * Makes the game thread wait a given time amount before continuing.
     * @throws InterruptedException If interrupted while trying to sleep.
     */
    private void sleep() throws InterruptedException {
        long cycleDelay = 600;
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
    }
    /**
     * Initializes the game with a debugging board
     */
    private void initializeDebugMode() {
        List<Robot> robots = new ArrayList<>();
        robots.add(new Robot(RobotID.ROBOT_1, new Position(0, 18)));
        robots.add(new Robot(RobotID.ROBOT_2, new Position(1, 18)));
        robots.add(new Robot(RobotID.ROBOT_3, new Position(2, 18)));
        robots.add(new Robot(RobotID.ROBOT_4, new Position(3, 18)));
        robots.add(new Robot(RobotID.ROBOT_5, new Position(4, 18)));
        robots.add(new Robot(RobotID.ROBOT_6, new Position(5, 18)));
        robots.add(new Robot(RobotID.ROBOT_7, new Position(6, 18)));
        robots.add(new Robot(RobotID.ROBOT_8, new Position(7, 18)));
        try {
            gameBoard = BoardLoaderUtil.loadBoard("boards/all_tiles_test_board.txt", robots);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the game with a playable board
     */
    private void initializeGame() {
        try {
            List<Robot> robots = new ArrayList<>();
            robots.add(new Robot(RobotID.ROBOT_1, new Position(1, 1)));
            robots.add(new Robot(RobotID.ROBOT_2, new Position(1, 2)));
            robots.add(new Robot(RobotID.ROBOT_3, new Position(1, 3)));
            robots.add(new Robot(RobotID.ROBOT_4, new Position(4, 8)));
            robots.add(new Robot(RobotID.ROBOT_5, new Position(6, 6)));
            robots.add(new Robot(RobotID.ROBOT_6, new Position(7, 7)));
            robots.add(new Robot(RobotID.ROBOT_7, new Position(6, 7)));
            robots.add(new Robot(RobotID.ROBOT_8, new Position(6, 8)));
            gameBoard = BoardLoaderUtil.loadBoard("boards/Dizzy_Dash.txt", robots);
            cogwheels = gameBoard.getPositionsOfTileOnBoard(TileType.COGWHEEL_RIGHT,
                    TileType.COGWHEEL_LEFT);
            fastConveyorBelts = gameBoard.getPositionsOfTileOnBoard(TileType.CONVEYOR_BELT_FAST,
                    TileType.CONVEYOR_BELT_FAST_RIGHT, TileType.CONVEYOR_BELT_FAST_LEFT,
                    TileType.CONVEYOR_BELT_FAST_SIDE_ENTRANCE_RIGHT,
                    TileType.CONVEYOR_BELT_FAST_SIDE_ENTRANCE_LEFT,
                    TileType.CONVEYOR_BELT_FAST_SIDE_ENTRANCES);
            conveyorBelts = new ArrayList<>();
            conveyorBelts.addAll(fastConveyorBelts);
            conveyorBelts.addAll(gameBoard.getPositionsOfTileOnBoard(TileType.CONVEYOR_BELT_SLOW,
                    TileType.CONVEYOR_BELT_SLOW_RIGHT, TileType.CONVEYOR_BELT_SLOW_LEFT,
                    TileType.CONVEYOR_BELT_SLOW_SIDE_ENTRANCE_RIGHT,
                    TileType.CONVEYOR_BELT_SLOW_SIDE_ENTRANCE_LEFT,
                    TileType.CONVEYOR_BELT_SLOW_SIDE_ENTRANCES));

            new Thread(() -> {
                try {
                    runGameLoop();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Does whatever the game wants to do
     * @throws InterruptedException If interrupted while trying to sleep
     */
    private void runGameLoop() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        makeMove(RobotID.ROBOT_1, Action.MOVE_1);
        makeMove(RobotID.ROBOT_1, Action.MOVE_2);
        fireAllLasers();
        makeMove(RobotID.ROBOT_1, Action.BACK_UP);
        makeMove(RobotID.ROBOT_1, Action.BACK_UP);
        makeMove(RobotID.ROBOT_1, Action.MOVE_3);
        makeMove(RobotID.ROBOT_1, Action.ROTATE_LEFT);
        makeMove(RobotID.ROBOT_1, Action.U_TURN);
        makeMove(RobotID.ROBOT_1, Action.ROTATE_RIGHT);
        makeMove(RobotID.ROBOT_2, Action.ROTATE_LEFT);
        makeMove(RobotID.ROBOT_2, Action.MOVE_3);
        makeMove(RobotID.ROBOT_2, Action.MOVE_3);
        makeMove(RobotID.ROBOT_2, Action.BACK_UP);
        makeMove(RobotID.ROBOT_2, Action.U_TURN);
        makeMove(RobotID.ROBOT_2, Action.BACK_UP);
        makeMove(RobotID.ROBOT_2, Action.BACK_UP);
        makeMove(RobotID.ROBOT_2, Action.BACK_UP);
        makeMove(RobotID.ROBOT_2, Action.MOVE_3);
        makeMove(RobotID.ROBOT_2, Action.BACK_UP);
        makeMove(RobotID.ROBOT_2, Action.BACK_UP);
        makeMove(RobotID.ROBOT_2, Action.ROTATE_LEFT);
        makeMove(RobotID.ROBOT_2, Action.U_TURN);
        makeMove(RobotID.ROBOT_2, Action.MOVE_1);
        moveAllConveyorBelts();
        checkAllFlags();
        rotateCogwheels();
        makeMove(RobotID.ROBOT_7, Action.MOVE_1);
    }

    /**
     * Makes the given robot move according to to the action input.
     * @param robotID The ID of the robot to move.
     * @param action The specific movement the robot is to take.
     * @throws InterruptedException If interrupted wile trying to sleep.
     */
    private void makeMove(RobotID robotID, Action action) throws InterruptedException {
        if (!gameBoard.isRobotAlive(robotID)) {
            return;
        }
        sleep();
        switch (action) {
            case MOVE_1:
                gameBoard.moveRobotForward(robotID);
                break;
            case MOVE_2:
                gameBoard.moveRobotForward(robotID);
                moveForward(robotID);
                break;
            case MOVE_3:
                gameBoard.moveRobotForward(robotID);
                moveForward(robotID);
                moveForward(robotID);
                break;
            case ROTATE_RIGHT:
                gameBoard.rotateRobotRight(robotID);
                break;
            case ROTATE_LEFT:
                gameBoard.rotateRobotLeft(robotID);
                break;
            case U_TURN:
                gameBoard.rotateRobotLeft(robotID);
                gameBoard.rotateRobotLeft(robotID);
                break;
            case BACK_UP:
                gameBoard.reverseRobot(robotID);
                break;
            default:
                throw new IllegalArgumentException("Not a recognized action.");
        }
    }

    /**
     * Helper method for makeMove. Takes care of movement forward of given robot.
     * @param robotID ID of the given robot.
     * @throws InterruptedException If interrupted wile sleeping.
     */
    private void moveForward(RobotID robotID) throws InterruptedException {
        if (!gameBoard.isRobotAlive(robotID)) {
            return;
        }
        sleep();
        gameBoard.moveRobotForward(robotID);
    }

    /**
     * Rotates all robots that are standing on cogWheel tiles on the board.
     * @throws InterruptedException If interrupted while sleeping.
     */
    private void rotateCogwheels() throws InterruptedException {
        for (BoardElementContainer<Tile> cogwheel : cogwheels) {
            if (!gameBoard.hasRobotOnPosition(cogwheel.getPosition())) {
                continue;
            }
            sleep();
            if (cogwheel.getElement().getTileType() == TileType.COGWHEEL_RIGHT) {
                gameBoard.rotateRobotRight(gameBoard.getRobotOnPosition(cogwheel.getPosition()));
            } else {
                gameBoard.rotateRobotLeft(gameBoard.getRobotOnPosition(cogwheel.getPosition()));
            }
        }
    }

    /**
     * Checks whether a given list has at least one element as defined by the predicate
     * @param list The list to check
     * @param predicate The predicate to test
     * @param <T> The type of the list
     * @return True if the list has at least one element passing the test of the predicate
     */
    private static <T> boolean testPredicate(List<T> list, Predicate<T> predicate) {
        for (T object : list) {
            if (predicate.test(object)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Moves robots standing on conveyor belts in the direction of the conveyor belt
     *
     * In addition, the function rotates appropriately when arriving at any non-straight conveyor belt
     *
     * @throws InterruptedException If disturbed during sleep
     */
    private void moveAllConveyorBelts() throws InterruptedException {
        sleep();
        moveConveyorBelts(fastConveyorBelts);
        sleep();
        moveConveyorBelts(conveyorBelts);
    }

    /**
     * Moves all conveyor belts in the input list
     * @param conveyorBelts A list of conveyor belts to move
     */
    private void moveConveyorBelts(List<BoardElementContainer<Tile>> conveyorBelts) {
        List<BoardElementContainer<Tile>> conveyorBeltsWithRobotsThatShouldMove =
                conveyorBeltsThatCanMoveWithoutConflict(conveyorBelts);
        for (BoardElementContainer<Tile> conveyorBelt : conveyorBeltsWithRobotsThatShouldMove) {
            System.out.println(gameBoard.getTileOnPosition(conveyorBelt.getPosition()).getTileType().toString());
            System.out.println(conveyorBelt.getPosition().toString());
            System.out.println(gameBoard.getRobotOnPosition(conveyorBelt.getPosition()));
            Direction currentDirection = conveyorBelt.getElement().getDirection();
            RobotID robot = gameBoard.getRobotOnPosition(conveyorBelt.getPosition());
            Position newPosition = gameBoard.getNewPosition(conveyorBelt.getPosition(), currentDirection);
            Tile nextTile = gameBoard.getTileOnPosition(newPosition);

            doConveyorBeltMovement(robot, currentDirection, nextTile);
        }
    }


    private List<BoardElementContainer<Tile>> blacklistedTiles = new ArrayList<>();
    private List<BoardElementContainer<Tile>> whitelistedTiles = new ArrayList<>();

    private List<BoardElementContainer<Tile>> conveyorBeltsThatCanMoveWithoutConflict(
            List<BoardElementContainer<Tile>> conveyorBelts) {

        List<BoardElementContainer<Tile>> conveyorBeltsWithRobotsOn = new ArrayList<>();
        whitelistedTiles.clear();
        blacklistedTiles.clear();

        for (BoardElementContainer<Tile> conveyorBelt : conveyorBelts) {
            if (gameBoard.hasRobotOnPosition(conveyorBelt.getPosition())) {
                conveyorBeltsWithRobotsOn.add(conveyorBelt);
            }
        }

        for (BoardElementContainer<Tile> conveyorBeltWithRobot : conveyorBeltsWithRobotsOn) {
            if (blacklistedTiles.contains(conveyorBeltWithRobot) ||
                    whitelistedTiles.contains((conveyorBeltWithRobot))) {
                continue;
            }

            Position conveyorBeltPosition = conveyorBeltWithRobot.getPosition();
            Tile conveyorBeltTile = conveyorBeltWithRobot.getElement();

            Position newPosition = gameBoard.getNewPosition(conveyorBeltPosition, conveyorBeltTile.getDirection());
            Tile nextTile = gameBoard.getTileOnPosition(newPosition);
            BoardElementContainer<Tile> nextConveyorBelt = new BoardElementContainer<>(nextTile, newPosition);

            BoardElementContainer<Tile> lastInRow = findLastRobotInRow (nextConveyorBelt, conveyorBeltsWithRobotsOn);
            List<BoardElementContainer<Tile>> listOfRow = new ArrayList<>();
            List<BoardElementContainer<Tile>> results = findFirstRobotInRow(lastInRow, conveyorBeltsWithRobotsOn,
                    listOfRow);

            for (BoardElementContainer<Tile> result : results) {
                if (!whitelistedTiles.contains(result)) {
                    whitelistedTiles.add(0, result);
                }
            }
        }
        return whitelistedTiles;
    }

    private List<BoardElementContainer<Tile>> findFirstRobotInRow(BoardElementContainer<Tile> currentConveyorBelt,
                                     List<BoardElementContainer<Tile>> conveyorBeltsWithRobotsOn,
                                     List<BoardElementContainer<Tile>> listOfRow) {
        Position nextPosition = gameBoard.getNewPosition(currentConveyorBelt.getPosition(),
                currentConveyorBelt.getElement().getDirection());
        Direction nextDirection = gameBoard.getTileOnPosition(nextPosition).getDirection();
        Tile nextTile = gameBoard.getTileOnPosition(nextPosition);
        BoardElementContainer<Tile> nextElementContainer = new BoardElementContainer<>(nextTile, nextPosition);
        List<BoardElementContainer<Tile>> pointingNeighbours = listOfConveyorBeltsWithRobotPointingAtTile(true,
                nextElementContainer, conveyorBeltsWithRobotsOn);

        listOfRow.add(currentConveyorBelt);

        if (blacklistedTiles.contains(nextElementContainer)) {
            blacklistedTiles.addAll(listOfRow);
            listOfRow.clear();
        } else if (currentConveyorBelt.getElement().getDirection() == Direction.getReverseDirection(nextDirection) &&
                conveyorBeltsWithRobotsOn.contains(nextElementContainer)) {
            blacklistedTiles.addAll(listOfRow);
            blacklistedTiles.add(nextElementContainer);
            listOfRow.clear();
        } else if ((!conveyorBelts.contains(nextElementContainer)) && gameBoard.hasRobotOnPosition(nextPosition)) {
            blacklistedTiles.addAll(listOfRow);
            listOfRow.clear();
        } else if (gameBoard.moveIsStoppedByWall(currentConveyorBelt.getPosition(), nextPosition,
                currentConveyorBelt.getElement().getDirection())) {
            blacklistedTiles.addAll(listOfRow);
            listOfRow.clear();
        } else if (pointingNeighbours.size() > 0) {
            blacklistedTiles.addAll(pointingNeighbours);
            blacklistedTiles.addAll(listOfRow);
            listOfRow.clear();
        } else if ((conveyorBeltsWithRobotsOn.contains(nextElementContainer))) {
            listOfRow = findFirstRobotInRow(nextElementContainer, conveyorBeltsWithRobotsOn, listOfRow);
        }
        return listOfRow;
    }

    private BoardElementContainer<Tile> findLastRobotInRow(BoardElementContainer<Tile> currentConveyorBelt,
                                    List<BoardElementContainer<Tile>> conveyorBeltsWithRobotsOn) {
        List<BoardElementContainer<Tile>> listOfConveyorBeltsWithRobotPointingAtTile =
                listOfConveyorBeltsWithRobotPointingAtTile(false, currentConveyorBelt,
                        conveyorBeltsWithRobotsOn);
        int sizeOfPointingList = listOfConveyorBeltsWithRobotPointingAtTile.size();
        if (sizeOfPointingList == 0) {
            return currentConveyorBelt;
        } else if (sizeOfPointingList == 1) {
            return findLastRobotInRow(listOfConveyorBeltsWithRobotPointingAtTile.get(0), conveyorBeltsWithRobotsOn);
        } else {
            blacklistedTiles.addAll(listOfConveyorBeltsWithRobotPointingAtTile);
            return currentConveyorBelt;
        }
    }



    private List<BoardElementContainer<Tile>> listOfConveyorBeltsWithRobotPointingAtTile(Boolean forward,
                                                                             BoardElementContainer<Tile> currentTile,
                                                            List<BoardElementContainer<Tile>> conveyorBeltsWithRobots) {
        List<BoardElementContainer<Tile>> possibleConflictConveyorBelts = new ArrayList<>();
        Tile conveyorBeltTile = currentTile.getElement();
        Position currentPosition = currentTile.getPosition();
        Direction currentDirection;
        
        if (forward) {
            currentDirection = conveyorBeltTile.getDirection();
        } else currentDirection = Direction.getReverseDirection(conveyorBeltTile.getDirection());
        
        Position nextPositionStraight = gameBoard.getNewPosition(currentPosition, currentDirection);
        Tile nextTileStraight = gameBoard.getTileOnPosition(nextPositionStraight);
        Position nextPositionLeft = gameBoard.getNewPosition(currentPosition,
                Direction.getLeftRotatedDirection(currentDirection));
        Tile nextTileLeft = gameBoard.getTileOnPosition(nextPositionLeft);
        Position nextPositionRight = gameBoard.getNewPosition(currentPosition,
                Direction.getRightRotatedDirection(currentDirection));
        Tile nextTileRight = gameBoard.getTileOnPosition(nextPositionRight);

        BoardElementContainer<Tile> rightOfCurrent = new BoardElementContainer<>(nextTileRight, nextPositionRight);
        BoardElementContainer<Tile> leftOfCurrent = new BoardElementContainer<>(nextTileLeft, nextPositionLeft);
        BoardElementContainer<Tile> inFrontOfCurrent = new BoardElementContainer<>(nextTileStraight, nextPositionStraight);
        
        if (currentDirection == Direction.getReverseDirection(
                nextTileStraight.getDirection()) && conveyorBeltsWithRobots.contains(inFrontOfCurrent)) {
            possibleConflictConveyorBelts.add(inFrontOfCurrent);
        }
        if (currentDirection == Direction.getLeftRotatedDirection(
                nextTileLeft.getDirection()) && conveyorBeltsWithRobots.contains(leftOfCurrent)) {
            possibleConflictConveyorBelts.add(leftOfCurrent);
        }
        if (currentDirection == Direction.getRightRotatedDirection(
                nextTileRight.getDirection()) && conveyorBeltsWithRobots.contains(rightOfCurrent)) {
            possibleConflictConveyorBelts.add(rightOfCurrent);
        }
        return possibleConflictConveyorBelts;
    }

    /**
     * Moves a robot standing on a conveyor belt
     * @param robot The id of the robot to move
     * @param currentDirection The direction of the conveyor belt the robot is standing on
     * @param nextTile The tile the robot is moving to
     */
    private void doConveyorBeltMovement(RobotID robot, Direction currentDirection, Tile nextTile) {
        Direction nextDirection = nextTile.getDirection();
        gameBoard.moveRobot(robot, currentDirection);
        if (testPredicate(conveyorBelts, (container) -> container.getElement() == nextTile)) {
            if (Direction.getRightRotatedDirection(nextDirection) == currentDirection) {
                gameBoard.rotateRobotLeft(robot);
            } else if (Direction.getLeftRotatedDirection(nextDirection) == currentDirection) {
                gameBoard.rotateRobotRight(robot);
            }
        }
    }

    /**
     * Checks all flags for robots. Tries to update the flag of the robot.
     */
    private void checkAllFlags() {
        List<BoardElementContainer<Tile>> listOfFlags = gameBoard.getPositionsOfTileOnBoard(TileType.FLAG_1,
                TileType.FLAG_2, TileType.FLAG_3, TileType.FLAG_4);
        for (BoardElementContainer<Tile> flag:listOfFlags) {
            Position flagPosition = flag.getPosition();
            if (gameBoard.hasRobotOnPosition(flagPosition)) {
                RobotID robot = gameBoard.getRobotOnPosition(flagPosition);
                gameBoard.updateFlagOnRobot(robot, flag.getElement().getTileType());
            }
        }
    }

    /**
     * Fires all lasers on the game board
     */
    private void fireAllLasers() throws InterruptedException {
        gameBoard.fireAllLasers();
        sleep();
        gameBoard.doLaserCleanup();
    }
}