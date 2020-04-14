package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Action;
import inf112.fiasko.roborally.element_properties.Direction;
import inf112.fiasko.roborally.element_properties.GameState;
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
    private List<BoardElementContainer<Tile>> repairTiles;
    private final List<Player> playerList;
    private final boolean host;
    private Deck<ProgrammingCard> mainDeck;
    private GameState gameState = GameState.INITIAL_SETUP;
    private String winningPlayerName;

    public String getWinningPlayerName() {
        return winningPlayerName;
    }

    public void setWinningPlayerName(String winningPlayerName) {
        this.winningPlayerName = winningPlayerName;
    }

    /**
     * Returns the gameState of the game
     * @return the gameState of the game
     */
    @Override
    public GameState getGameState(){
        return gameState;
    }

    /**
     * Sets the gameState of the game
     * @param gameState the gameState
     */
    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Instantiates a new robo rally game
     * @param debug Whether to start the game in debugging mode
     */
    public RoboRallyGame(List<Player> playerList, String boardName, boolean host, boolean debug) {
        this.host = host;
        this.playerList = playerList;
        if (debug) {
            initializeDebugMode();
        } else {
            initializeGame(boardName);
        }
    }

    /**
     * Instantiates a new robo rally game
     */
    public RoboRallyGame(List<Player> playerList, String boardName,boolean host) {
        this.host = host;
        this.playerList = playerList;
        initializeGame(boardName);
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
    private void initializeGame(String boardName) {
        try {
            List<Robot> robots = new ArrayList<>();
            //TODO: Find correct robot spawn positions
            int posX = 1;
            for (Player player : playerList) {
                Position spawn = new Position(posX,1);
                robots.add(new Robot(player.getRobotID(), spawn));
                posX++;
            }

            gameBoard = BoardLoaderUtil.loadBoard("boards/" + boardName, robots);
            generateTileLists();

            if (host) {
                mainDeck = DeckLoaderUtil.loadProgrammingCardsDeck();
            }

            new Thread(() -> {
                try {
                    runTurn();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
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
        repairTiles = gameBoard.getPositionsOfTileOnBoard(TileType.FLAG_1, TileType.FLAG_2, TileType.FLAG_3,
                TileType.FLAG_4, TileType.WRENCH, TileType.WRENCH_AND_HAMMER);
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
        if (host) {
            updateLockedProgrammingCardsForAllPlayers();
            removeNonLockedProgrammingCardsFromPlayers();
        }
        // TODO: If this player is in power down, ask if it shall continue
        // Respawn dead robots, as long as they have more lives left
        respawnRobots();
        resetHasTouchedFlagThisTurnForAllRobots();
    }

    /**
     * Resets the boolean for if the robot has touched a flag this turn, to set up the next turn.
     */
    private void resetHasTouchedFlagThisTurnForAllRobots() {
        for (Robot robot : gameBoard.getAliveRobots()) {
            robot.setHasTouchedFlagThisTurn(false);
        }
    }

    /**
     * Locks the players programming cards in relation to the robots damage
     */
    private void updateLockedProgrammingCardsForAllPlayers() {
        for (Player player : playerList) {
            List<ProgrammingCard> playerProgram = player.getProgram();
            ProgrammingCardDeck playerDeck = player.getPlayerDeck();
            ProgrammingCardDeck lockedPlayerDeck = player.getLockedPlayerDeck();
            int robotDamage = gameBoard.getRobotDamage(player.getRobotID());

            //The player has no locked cards. All previously locked cards should go into the free deck
            if (robotDamage <= 4) {
                lockedPlayerDeck.emptyInto(player.getPlayerDeck());
                continue;
            }

            //Goes through locked cards and moves them to the locked player deck
            for (int i = 1; i <= (robotDamage - 4); i++) {
                ProgrammingCard card = playerProgram.get(playerProgram.size() - i);
                moveProgrammingCardToLockedDeck(card, playerDeck, lockedPlayerDeck);
            }
        }
    }

    /**
     * Moves a card from the player's deck to the player's locked deck if found
     * @param card The card to move to the locked deck
     * @param playerDeck The deck containing the player's cards
     * @param lockedPlayerDeck The deck containing the player's locked cards
     */
    private void moveProgrammingCardToLockedDeck(ProgrammingCard card, ProgrammingCardDeck playerDeck,
                                                 ProgrammingCardDeck lockedPlayerDeck) {
        for (int i = 0; i < playerDeck.size(); i++) {
            if (card.equals(playerDeck.peekTop())) {
                //Found the card. Add to the locked deck
                lockedPlayerDeck.draw(playerDeck);
                break;
            } else {
                //Move the card to the bottom of the deck
                playerDeck.draw(playerDeck);
            }
        }

    }

    /**
     * Moves non-locked player programming cards from their hand back to the main deck
     */
    private void removeNonLockedProgrammingCardsFromPlayers() {
        for (Player player : playerList) {
            player.getPlayerDeck().emptyInto(mainDeck);
        }
    }

    /**
     * Deals correct amount of cards to active players, based on their robots damage
     */
    private void distributeProgrammingCardsToPlayers() {
        mainDeck.shuffle();
        for (Player player : playerList) {
            RobotID robot = player.getRobotID();
            ProgrammingCardDeck playerDeck = player.getPlayerDeck();
            int robotDamage = gameBoard.getRobotDamage(robot);
            //Powered down or heavily damaged robots don't get any cards
            if (gameBoard.getPowerDown(robot) || robotDamage >= 9) {
                continue;
            }
            if (!playerDeck.isEmpty()) {
                throw new IllegalStateException("Player deck must be empty when dealing new cards!");
            }
            //Gives the player the correct amount of cards
            playerDeck.draw(mainDeck,9 - robotDamage);
        }
    }

    /**
     * Runs one phase as defined in the Robo Rally rulebook
     * @param phaseNumber The number of the phase to run
     * @throws InterruptedException If interrupted wile trying to sleep
     */
    private void runPhase(int phaseNumber) throws InterruptedException {
        runProgrammingCards(phaseNumber);

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
                RobotID robotID = gameBoard.getRobotOnPosition(flagPosition);
                for (Robot robot : gameBoard.getAliveRobots()) {
                    if (robot.getRobotId() != robotID || robot.isHasTouchedFlagThisTurn()) {
                        continue;
                    }
                    gameBoard.updateFlagOnRobot(robotID, flag.getElement().getTileType());
                    robot.setHasTouchedFlagThisTurn(true);
                    if (victoryCheck(robot.getLastFlagVisited(), listOfFlags.size())) {
                        for (Player player : playerList) {
                            if (player.getRobotID() != robotID) {
                                continue;
                            }
                            setWinningPlayerName(player.getName());
                            setGameState(GameState.GAME_IS_WON);
                        }
                    }
                }
            }
        }
    }

    private boolean victoryCheck(int lastFlagVisited, int lastFlag) {
        return (lastFlagVisited == lastFlag);
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
    private void runProgrammingCards(int phase) throws InterruptedException {
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
    private void respawnRobots() {
        gameBoard.respawnRobots();
    }

    /**
     * repair all robots standing on a repair tile
     */
    private void repairAllRobotsOnRepairTiles() {
        for (BoardElementContainer<Tile> repairTile : repairTiles) {
            Position robotOnTilePosition = repairTile.getPosition();
            if (!gameBoard.hasRobotOnPosition(robotOnTilePosition)) {
                continue;
            }
            gameBoard.repairRobotOnTile(gameBoard.getRobotOnPosition(robotOnTilePosition));
        }
    }

    /**
     * Sets the robot's power down status to the player's "power down next round" status and sets the players status to false
     */
    private void updateRobotPowerDown() {
        for (Player player : playerList) {
            setRobotPowerDown(player, player.getPowerDownNextRound());
            player.setPowerDownNextRound(false);
        }
    }

    /**
     * Sets the power down status of a robot
     * @param player The player that owns the robot
     * @param powerDownStatus The new power down status
     */
    private void setRobotPowerDown(Player player, Boolean powerDownStatus) {
        gameBoard.setPowerDown(player.getRobotID(), powerDownStatus);
    }
}