package inf112.fiasko.roborally.objects;

import com.esotericsoftware.kryonet.Connection;
import inf112.fiasko.roborally.elementproperties.Action;
import inf112.fiasko.roborally.elementproperties.Direction;
import inf112.fiasko.roborally.elementproperties.GameState;
import inf112.fiasko.roborally.elementproperties.Position;
import inf112.fiasko.roborally.elementproperties.RobotID;
import inf112.fiasko.roborally.elementproperties.TileType;
import inf112.fiasko.roborally.networking.RoboRallyClient;
import inf112.fiasko.roborally.networking.RoboRallyServer;
import inf112.fiasko.roborally.networking.containers.PowerdownContainer;
import inf112.fiasko.roborally.networking.containers.ProgamsContainer;
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
public class RoboRallyGame implements IRoboRallyGame {
    private Board gameBoard;
    private List<BoardElementContainer<Tile>> cogwheels;
    private List<BoardElementContainer<Tile>> conveyorBelts;
    private List<BoardElementContainer<Tile>> fastConveyorBelts;
    private List<BoardElementContainer<Tile>> repairTiles;
    private List<BoardElementContainer<Tile>> flags;
    private final List<Player> playerList;
    private final boolean host;
    private Deck<ProgrammingCard> mainDeck;
    private GameState gameState = GameState.BEGINNING_OF_GAME;
    private String playerName;
    private final RoboRallyClient client;
    private RoboRallyServer server;
    private String winningPlayerName;
    private List<ProgrammingCard> program;
    private ProgrammingCardDeck playerHand;

    public ProgrammingCardDeck getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(ProgrammingCardDeck playerHand) {
        this.playerHand = playerHand;
    }

    public List<ProgrammingCard> getProgram() {
        return program;
    }

    public void setProgram(List<ProgrammingCard> program) {
        this.program = program;
    }

    /**
     * Instantiates a new Robo Rally game
     * @param playerList A list of all the players participating in the game
     * @param boardName The playerName of the board to use
     * @param host Whether this player is the host
     * @param playerName The name of the player of this instance of the game
     * @param client The client used to send data to the server
     * @param server The server if this player is host. Should be null otherwise
     * @param debug Whether this game is to use the debugging board
     */
    public RoboRallyGame(List<Player> playerList, String boardName, boolean host, String playerName,
                         RoboRallyClient client, RoboRallyServer server, boolean debug) {
        this.playerName = playerName;
        this.host = host;
        this.playerList = playerList;
        this.client = client;
        this.server = server;
        if (debug) {
            initializeDebugMode();
        } else {
            initializeGame(boardName);
        }
    }




    /**
     * Instantiates a new Robo Rally game
     * @param playerList A list of all the players participating in the game
     * @param boardName The playerName of the board to use
     * @param host Whether this player is the host
     * @param playerName The name of the player of this instance of the game
     * @param client The client used to send data to the server
     * @param server The server if this player is host. Should be null otherwise
     */
    public RoboRallyGame(List<Player> playerList, String boardName, boolean host, String playerName,
                         RoboRallyClient client, RoboRallyServer server) {
        this.playerName = playerName;
        this.host = host;
        this.playerList = playerList;
        this.client = client;
        this.server = server;
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

    @Override
    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Gets the name of the player playing this instance of the game
     * @return The name of this player
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the name of the player playing this instance of the game
     * @param playerName The new name of this player
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }


    /**
     * Gets the name of the player that won the game
     * @return The name of the winning player
     */
    public String getWinningPlayerName() {
        return winningPlayerName;
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
        flags = gameBoard.getPositionsOfTileOnBoard(TileType.FLAG_1,
                TileType.FLAG_2, TileType.FLAG_3, TileType.FLAG_4);
    }

    /**
     * Gets a player object given a player name
     * @param name The name of the player to get
     * @return The corresponding player object or null if no such object exists
     */
    private Player getPlayerFromName(String name) {
        for (Player player : playerList) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    public int getProgramSize(){
        Player player = getPlayerFromName(playerName);
        if (player != null) {
            return Math.min(5, 5 - gameBoard.getRobotDamage(player.getRobotID()) + 4);
        }
        return -1;
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
            if (server == null) {
                    System.out.println("Serveren er null");
            }
            for (Connection connection: server.getPlayerNames().keySet()) {
                String playerName  = server.getPlayerNames().get(connection);
                Player player = getPlayerFromName(playerName);
                if (player != null && player.getPlayerDeck() != null) {
                    server.sendToClient(connection, player.getPlayerDeck());
                }
            }
        }
        setGameState(GameState.JUST_BEFORE_CHOOSING_CARDS);

        // TODO: Make program for this player, if not in power down
        // TODO: Ask player for new power down
        // Run the phases of the game


        // TODO: If this player is in power down, ask if it shall continue
        // Respawn dead robots, as long as they have more lives left
    }

    public void recivedStayInPowerdown(PowerdownContainer powerdowns){
        for (Player player:playerList) {
            player.setPowerDownNextRound(powerdowns.getPowerdown().get(player.getName()));
        }
        respawnRobots();
        resetHasTouchedFlagThisTurnForAllRobots();
    }

    public void reciveAllProgrammes(ProgamsContainer programs) throws InterruptedException {
        Map<String,List<ProgrammingCard>> progs = programs.getProgram();
        Map<String,Boolean> powerdown = programs.getPowerdown();
        String playername;
        for (Player player:playerList) {
            playername = player.getName();
            player.setInProgram(progs.get(playername));
            player.setPowerDownNextRound(powerdown.get(playername));
        }
        setGameState(GameState.RUNNING_PROGRAMS);
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


    }

    /**
     * Sends information about players no longer part of the game to the server
     */
    private void sendAllDeadPlayersToServer() {
        if (host) {
            server.setDeadPlayers(gameBoard.getRealDeadRobots());
        }
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
     * Sets the name of the player that won the game
     * @param winningPlayerName The player winning the game
     */
    private void setWinningPlayerName(String winningPlayerName) {
        this.winningPlayerName = winningPlayerName;
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
                setWinningPlayerName(player.getName());
                setGameState(GameState.GAME_IS_WON);
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