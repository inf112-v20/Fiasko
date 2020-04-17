package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.elementproperties.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * This class handles everything that should happen during a phase
 */
public class Phase {
    private final Board gameBoard;
    private final List<Player> playerList;
    private final int cycleDelay;
    private List<BoardElementContainer<Tile>> cogwheels;
    private List<BoardElementContainer<Tile>> conveyorBelts;
    private List<BoardElementContainer<Tile>> fastConveyorBelts;
    private List<BoardElementContainer<Tile>> flags;
    private RoboRallyGame game;

    /**
     * Instantiates a new phase
     * @param gameBoard The board to act on
     * @param playerList A list of players participating in the game
     * @param cycleDelay The amount of milliseconds to wait between moves
     * @param game The game which uses this object
     */
    public Phase(Board gameBoard, List<Player> playerList, int cycleDelay, RoboRallyGame game) {
        this.gameBoard = gameBoard;
        this.playerList = playerList;
        this.cycleDelay = cycleDelay;
        this.game = game;
        generateTileLists();
    }

    /**
     * Runs one phase as defined in the Robo Rally rulebook
     * @param phaseNumber The number of the phase to run
     * @throws InterruptedException If interrupted wile trying to sleep
     */
    public void runPhase(int phaseNumber) throws InterruptedException {
        runProgrammingCards(phaseNumber);

        moveAllConveyorBelts();
        rotateCogwheels();

        fireAllLasers();
        checkAllFlags();
    }

    /**
     * Checks all flags for robots. Tries to update the flag of the robot.
     */
    public void checkAllFlags() {
        for (BoardElementContainer<Tile> flag : flags) {
            Position flagPosition = flag.getPosition();
            if (!gameBoard.hasRobotOnPosition(flagPosition)) {
                continue;
            }
            RobotID robotID = gameBoard.getRobotOnPosition(flagPosition);
            if (gameBoard.isHasTouchedFlagThisTurnFromRobotID(robotID)) {
                continue;
            }
            gameBoard.updateFlagOnRobot(robotID, flag.getElement().getTileType());
            gameBoard.setHasTouchedFlagThisTurnFromRobotID(robotID,true);
            checkIfPlayerWon(robotID, flags.size());
        }
    }

    /**
     * Fires all lasers on the game board
     */
    public void fireAllLasers() throws InterruptedException {
        gameBoard.fireAllLasers();
        sleep();
        gameBoard.doLaserCleanup();
    }

    /**
     * Runs all programming cards for a phase
     * @param phase The number of the phase to run cards for
     * @throws InterruptedException If it gets interrupted while trying to sleep
     */
    public void runProgrammingCards(int phase) throws InterruptedException {
        List<RobotID> robotsToDoAction = new ArrayList<>();
        List<ProgrammingCard> programToBeRun = new ArrayList<>();
        List<Integer> originalPriority = new ArrayList<>();
        for (Player player : playerList) {
            List<ProgrammingCard> playerProgram = player.getProgram();
            if (!playerProgram.isEmpty()) {
                ProgrammingCard programmingCard = playerProgram.get(phase - 1);
                originalPriority.add(programmingCard.getPriority());
                robotsToDoAction.add(player.getRobotID());
                programToBeRun.add(programmingCard);
            }
        }
        Collections.sort(programToBeRun);
        for (ProgrammingCard card : programToBeRun) {
            int i = originalPriority.indexOf(card.getPriority());
            RobotID robot = robotsToDoAction.get(i);
            makeMove(robot, card.getAction());
        }
    }

    /**
     * Rotates all robots that are standing on cogWheel tiles on the board.
     * @throws InterruptedException If interrupted while sleeping.
     */
    public void rotateCogwheels() throws InterruptedException {
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
     * Moves robots standing on conveyor belts in the direction of the conveyor belt
     *
     * In addition, the function rotates appropriately when arriving at any non-straight conveyor belt
     *
     * @throws InterruptedException If disturbed during sleep
     */
    public void moveAllConveyorBelts() throws InterruptedException {
        sleep();
        moveConveyorBelts(fastConveyorBelts);
        sleep();
        moveConveyorBelts(conveyorBelts);
    }

    /**
     * Makes the given robot move according to to the action input.
     * @param robotID The ID of the robot to move.
     * @param action The specific movement the robot is to take.
     * @throws InterruptedException If interrupted wile trying to sleep.
     */
    public void makeMove(RobotID robotID, Action action) throws InterruptedException {
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
     * Checks if the player won, and shows the victory screen
     * @param robotID The robot to be checked
     * @param numberOfFlags The number of flags on the map
     */
    private void checkIfPlayerWon(RobotID robotID, int numberOfFlags) {
        if (victoryCheck(gameBoard.getLastFlagVisitedFromRobotID(robotID), numberOfFlags)) {
            for (Player player : playerList) {
                if (player.getRobotID() != robotID) {
                    continue;
                }
                if (game != null) {
                    game.setWinningPlayerName(player.getName());
                    game.setGameState(GameState.GAME_IS_WON);
                }
            }
        }
    }

    /**
     * Checks whether a player has won
     * @param lastFlagVisited The last flag the player visited
     * @param lastFlag The last flag of the board
     * @return True if the player has visited the last flag
     */
    private boolean victoryCheck(int lastFlagVisited, int lastFlag) {
        return (lastFlagVisited == lastFlag);
    }

    /**
     * Moves a list of conveyor belts
     * @param conveyorBelts A list of board element containers containing conveyor belts
     */
    private void moveConveyorBelts(List<BoardElementContainer<Tile>> conveyorBelts) {
        Map<RobotID, Position> newPositions = new HashMap<>();
        Map<RobotID, Boolean> moveNormally = new HashMap<>();
        for (Robot robot : gameBoard.getAliveRobots()) {
            newPositions.put(robot.getRobotId(), robot.getPosition());
        }
        //Updates hash maps containing robot move information
        for (BoardElementContainer<Tile> conveyorBelt : conveyorBelts) {
            Position conveyorBeltPosition = conveyorBelt.getPosition();
            Direction conveyorBeltDirection = conveyorBelt.getElement().getDirection();
            if (gameBoard.conveyorBeltCanMove(conveyorBelt, 0) &&
                    gameBoard.hasRobotOnPosition(conveyorBeltPosition)) {
                updateConveyorBeltMaps(conveyorBeltPosition, conveyorBeltDirection, newPositions, moveNormally);
            }
        }
        //Updates position for all robots affected by conveyor belts
        for (RobotID robotID : RobotID.values()) {
            if (newPositions.get(robotID) == null || moveNormally.get(robotID) == null) {
                continue;
            }
            if (moveNormally.get(robotID)) {
                gameBoard.moveRobot(robotID, gameBoard.getTileOnPosition(newPositions.get(robotID)).getDirection());
            } else {
                gameBoard.teleportRobot(robotID, newPositions.get(robotID));
            }
        }
    }

    /**
     * Updates maps containing information about what a robot on a conveyor belt should do
     * @param conveyorBeltPosition The position of the conveyor belt the robot stands on
     * @param conveyorBeltDirection The direction of the conveyor belt the robot stands on
     * @param newPositions The map containing new positions for robots
     * @param moveNormally The map containing whether a robot should move normally following normal rules
     */
    private void updateConveyorBeltMaps(Position conveyorBeltPosition, Direction conveyorBeltDirection,
                                        Map<RobotID, Position> newPositions, Map<RobotID, Boolean> moveNormally) {
        RobotID robotAtConveyorBelt = gameBoard.getRobotOnPosition(conveyorBeltPosition);
        Position newPosition = gameBoard.getNewPosition(conveyorBeltPosition, conveyorBeltDirection);
        if (gameBoard.isConveyorBelt(gameBoard.getTileOnPosition(newPosition))) {
            newPositions.put(robotAtConveyorBelt, newPosition);
            moveNormally.put(robotAtConveyorBelt, false);
            Direction newDirection = gameBoard.getTileOnPosition(newPosition).getDirection();
            if (Direction.getRightRotatedDirection(newDirection) == conveyorBeltDirection) {
                gameBoard.rotateRobotLeft(robotAtConveyorBelt);
            } else if (Direction.getLeftRotatedDirection(newDirection) == conveyorBeltDirection) {
                gameBoard.rotateRobotRight(robotAtConveyorBelt);
            }
        } else {
            newPositions.put(robotAtConveyorBelt, conveyorBeltPosition);
            moveNormally.put(robotAtConveyorBelt, true);
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
     * Makes the game thread wait a given time amount before continuing.
     * @throws InterruptedException If interrupted while trying to sleep.
     */
    private void sleep() throws InterruptedException {
        if (cycleDelay > 0) {
            TimeUnit.MILLISECONDS.sleep(cycleDelay);
        }
    }

    /**
     * Generates lists containing board element containers with all tiles of certain types
     */
    private void generateTileLists() {
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
        flags = gameBoard.getPositionsOfTileOnBoard(TileType.FLAG_1,
                TileType.FLAG_2, TileType.FLAG_3, TileType.FLAG_4);
    }
}
