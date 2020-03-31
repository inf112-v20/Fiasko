package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Action;
import inf112.fiasko.roborally.element_properties.Direction;
import inf112.fiasko.roborally.element_properties.Position;
import inf112.fiasko.roborally.element_properties.RobotID;
import inf112.fiasko.roborally.element_properties.TileType;
import inf112.fiasko.roborally.utility.BoardLoaderUtil;
import inf112.fiasko.roborally.utility.DeckLoaderUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * This class represent a game which is drawable using libgdx
 */
public class RoboRallyGame implements IDrawableGame {
    private Board gameBoard;
    private List<BoardElementContainer<Tile>> cogwheels;
    private List<BoardElementContainer<Tile>> conveyorBelts;
    private List<BoardElementContainer<Tile>> fastConveyorBelts;
    private List<Player> playerList;
    private final boolean host;
    private Deck<ProgrammingCard> mainDeck;
    /**
     * Instantiates a new robo rally game
     * @param debug Whether to start the game in debugging mode
     */
    public RoboRallyGame(boolean debug) {
        this.host=false;
        if (debug) {
            initializeDebugMode();
        } else {
            initializeGame();
        }
    }

    /**
     * Instantiates a new robo rally game
     */
    public RoboRallyGame() {
        this.host=false;
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

            initializePlayers();
            gameBoard = BoardLoaderUtil.loadBoard("boards/Checkmate.txt", robots);
            generateTileLists();
            mainDeck = DeckLoaderUtil.loadProgrammingCardsDeck();

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
     * Initializes all players
     * @throws IOException If interrupted while trying to sleep
     */
    private void initializePlayers() throws IOException {
        playerList = new ArrayList<>();
        playerList.add(new Player(RobotID.ROBOT_1, "Player1"));
        playerList.add(new Player(RobotID.ROBOT_2, "Player2"));
        playerList.add(new Player(RobotID.ROBOT_3, "Player3"));
        playerList.add(new Player(RobotID.ROBOT_4, "Player4"));
        playerList.add(new Player(RobotID.ROBOT_5, "Player5"));
        playerList.add(new Player(RobotID.ROBOT_6, "Player6"));
        playerList.add(new Player(RobotID.ROBOT_7, "Player7"));
        playerList.add(new Player(RobotID.ROBOT_8, "Player8"));
        Deck<ProgrammingCard> cards =  DeckLoaderUtil.loadProgrammingCardsDeck();
        for (Player player : playerList) {
            cards.shuffle();
            List<ProgrammingCard> testProgram = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                cards.shuffle();
                testProgram.add(cards.peekTop());
            }
            player.setInProgram(testProgram);
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
    }

    /**
     * Does whatever the game wants to do
     * @throws InterruptedException If interrupted while trying to sleep
     */
    private void runGameLoop() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        runPhase(1);
        runPhase(2);
        runPhase(3);
        runPhase(4);
        runPhase(5);
        respawnRobots();
    }

    /**
     * Runs all the steps of one turn in the game
     * @throws InterruptedException If interrupted while trying to sleep
     */
    private void runTurn() throws InterruptedException {
        // The method should follow this sequence:
        /*
        Tilegne programeringskort

        Programmer roboten

        Gå i power down

        Kjør 5 faser

        Flagg + reprasjonstiles reparerer

        Fjerner ulåste programmeringskort

        Spør om de i power down skal fortsette i power down

        Respawn roboter
        */

        // Sets the power down status to true on robots that have players who planned one this turn.
        // Resets players power down for next turn to false.
        updateRobotPowerDown();
        // Set damage of robots in power down to 0
        gameBoard.executePowerdown();
        if (host) {
            // TODO: Distribute programming cards to players not in power down
            distributeProgrammingCardsToPlayers();
        }
        // TODO: Make program for this player, if not in power down
        // TODO: Ask player for new power down
        // Run the phases of the game
        runPhase(1);
        runPhase(2);
        runPhase(3);
        runPhase(4);
        runPhase(5);

        // Repair robots on repair tiles
        repairAllRobotsOnRepairTiles();
        // TODO: Update locked cards deck
        // TODO: Remove non-locked programming cards
        // TODO: If this player is in power down, ask if it shall continue
        // Respawn dead robots, as long as they have more lives left
        respawnRobots();
    }

    private void distributeProgrammingCardsToPlayers() {
        int robotDamage;
        ProgrammingCardDeck playerDeck;
        mainDeck.shuffle();

        for (Player player : playerList) {
            RobotID robot = player.getRobotID();
            playerDeck = player.getPlayerDeck();
            if (gameBoard.getPowerDown(robot)) {
                continue;
            }
            robotDamage = gameBoard.getRobotDamage(robot);
            if (robotDamage >= 9) {
                continue;
            }
            if (playerDeck.isEmpty()) {
                playerDeck.draw(mainDeck,9-robotDamage);
            } else throw new IllegalStateException("Player deck must be empty!");
        }
    }

    /**
     * Runs one phase as defined in the Robo Rally rulebook
     * @param phaseNumber The number of the phase to run
     * @throws InterruptedException If interrupted wile trying to sleep
     */
    private void runPhase(int phaseNumber) throws InterruptedException {
        runProgramCards(phaseNumber);

        moveAllConveyorBelts();
        rotateCogwheels();

        fireAllLasers();
        checkAllFlags();
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

    /**
     * Runs all programming cards for a phase
     * @param phase The number of the phase to run cards for
     * @throws InterruptedException If it gets interrupted while trying to sleep
     */
    private void runProgramCards(int phase) throws InterruptedException {
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
     * Respawn all the dead robots with more lives and places them on the game board
     */
    private void respawnRobots(){
        gameBoard.respawnRobots();
    }

    /**
     * repair all robots standing on a repair tile
     */
    private void repairAllRobotsOnRepairTiles(){
        List<BoardElementContainer<Tile>> listOfRepareTiles = gameBoard.getPositionsOfTileOnBoard(TileType.FLAG_1,
                TileType.FLAG_2, TileType.FLAG_3, TileType.FLAG_4, TileType.WRENCH, TileType.WRENCH_AND_HAMMER);
        for (BoardElementContainer<Tile> repareTile:listOfRepareTiles) {
            Position robotOnTilePosition = repareTile.getPosition();
            if (!gameBoard.hasRobotOnPosition(robotOnTilePosition)){
                continue;
            }
            gameBoard.repairRobotOnTile(gameBoard.getRobotOnPosition(robotOnTilePosition));
        }
    }

    /**
     * sets the robots powerdown status too the players powerdown next round status and sets the players status to false
     */
    private void updateRobotPowerDown(){
        for (Player player:playerList) {
            gameBoard.setPowerDown(player.getRobotID(),player.getPowerDownNextRound());
            player.setPowerDownNextRound(false);
        }
    }

    /**
     * sets the powerdown status of a robots
     * @param player the player that owns the robot
     * @param powerdownStatus the powerdown status
     */
    private void setRobotPowerDown(Player player,Boolean powerdownStatus){
        gameBoard.setPowerDown(player.getRobotID(),powerdownStatus);
    }
}