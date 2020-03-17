package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.*;
import inf112.fiasko.roborally.utility.BoardLoaderUtil;

import java.io.IOException;
import java.util.ArrayList;
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
        robots.add(new Robot(RobotID.ROBOT_1, new Position(0, 16)));
        robots.add(new Robot(RobotID.ROBOT_2, new Position(1, 16)));
        robots.add(new Robot(RobotID.ROBOT_3, new Position(2, 16)));
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
            gameBoard = BoardLoaderUtil.loadBoard("boards/Checkmate.txt", robots);
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
        moveConveyorBelts(fastConveyorBelts);
        moveConveyorBelts(conveyorBelts);
    }

    /**
     * Moves all conveyor belts in the input list
     * @param conveyorBelts A list of conveyor belts to move
     * @throws InterruptedException If disturbed during sleep
     */
    private void moveConveyorBelts(List<BoardElementContainer<Tile>> conveyorBelts) throws InterruptedException {
        List<BoardElementContainer<Tile>> conveyorBeltsWithRobotsThatShouldMove =
                conveyorBeltsThatCanMoveWithoutConflict(conveyorBelts);
        for (BoardElementContainer<Tile> conveyorBelt : conveyorBeltsWithRobotsThatShouldMove) {
            Direction currentDirection = conveyorBelt.getElement().getDirection();
            RobotID robot = gameBoard.getRobotOnPosition(conveyorBelt.getPosition());
            Position newPosition = gameBoard.getNewPosition(conveyorBelt.getPosition(), currentDirection);
            Tile nextTile = gameBoard.getTileOnPosition(newPosition);

            doConveyorBeltMovement(robot, currentDirection, nextTile);
        }
    }

    private List<BoardElementContainer<Tile>> conveyorBeltsThatCanMoveWithoutConflict(
            List<BoardElementContainer<Tile>> conveyorBelts) {

        List<BoardElementContainer<Tile>> nonConflictConveyorBelts = new ArrayList<>();
        for (BoardElementContainer<Tile> conveyorBelt : conveyorBelts) {
            if (gameBoard.hasRobotOnPosition(conveyorBelt.getPosition())) {
                nonConflictConveyorBelts.add(conveyorBelt);
            }
        }
        for (BoardElementContainer<Tile> conveyorBeltWithRobot : nonConflictConveyorBelts) {
            Position conveyorBeltPosition = conveyorBeltWithRobot.getPosition();
            Tile conveyorBeltTile = conveyorBeltWithRobot.getElement();

            Position newPosition = gameBoard.getNewPosition(conveyorBeltPosition, conveyorBeltTile.getDirection());
            Tile nextTile = gameBoard.getTileOnPosition(newPosition);

            Position beyondNextPositionStraight = gameBoard.getNewPosition(newPosition, conveyorBeltTile.getDirection());
            Tile beyondNextTileStraight = gameBoard.getTileOnPosition(beyondNextPositionStraight);

            Position beyondNextPositionLeft = gameBoard.getNewPosition(newPosition,
                    Direction.getLeftRotatedDirection(conveyorBeltTile.getDirection()));
            Tile beyondNextTileLeft = gameBoard.getTileOnPosition(beyondNextPositionLeft);

            Position beyondNextPositionRight = gameBoard.getNewPosition(newPosition,
                    Direction.getRightRotatedDirection(conveyorBeltTile.getDirection()));
            Tile beyondNextTileRight = gameBoard.getTileOnPosition(beyondNextPositionRight);



            if (conveyorBeltTile.getDirection() == Direction.getReverseDirection(nextTile.getDirection()) &&
                nonConflictConveyorBelts.contains(new BoardElementContainer<>(nextTile, newPosition))) {
                nonConflictConveyorBelts.remove(conveyorBeltWithRobot);
            }
            else if (conveyorBeltTile.getDirection() == Direction.getReverseDirection(
                    beyondNextTileStraight.getDirection()) && nonConflictConveyorBelts.contains(
                            new BoardElementContainer<>(beyondNextTileStraight, beyondNextPositionStraight))) {
                nonConflictConveyorBelts.remove(conveyorBeltWithRobot);
            }
            else if (conveyorBeltTile.getDirection() == Direction.getLeftRotatedDirection(
                    beyondNextTileLeft.getDirection()) && nonConflictConveyorBelts.contains(
                    new BoardElementContainer<>(beyondNextTileLeft, beyondNextPositionLeft))) {
                nonConflictConveyorBelts.remove(conveyorBeltWithRobot);
            }
            else if (conveyorBeltTile.getDirection() == Direction.getRightRotatedDirection(
                    beyondNextTileRight.getDirection()) && nonConflictConveyorBelts.contains(
                    new BoardElementContainer<>(beyondNextTileRight, beyondNextPositionRight))) {
                nonConflictConveyorBelts.remove(conveyorBeltWithRobot);
            }
        }
        return nonConflictConveyorBelts;
    }

    /**
     * Moves a robot standing on a conveyor belt
     * @param robot The id of the robot to move
     * @param currentDirection The direction of the conveyor belt the robot is standing on
     * @param nextTile The tile the robot is moving to
     * @throws InterruptedException If disturbed during sleep
     */
    private void doConveyorBeltMovement(RobotID robot, Direction currentDirection, Tile nextTile)
            throws InterruptedException {
        Direction nextDirection = nextTile.getDirection();
        sleep();
        gameBoard.moveRobot(robot, currentDirection);
        if (testPredicate(conveyorBelts, (container) -> container.getElement() == nextTile)) {
            if (Direction.getRightRotatedDirection(nextDirection) == currentDirection) {
                sleep();
                gameBoard.rotateRobotLeft(robot);
            } else if (Direction.getLeftRotatedDirection(nextDirection) == currentDirection) {
                sleep();
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

    private void fireAllLasers(){
        gameBoard.fireAllLasers();
    }
}