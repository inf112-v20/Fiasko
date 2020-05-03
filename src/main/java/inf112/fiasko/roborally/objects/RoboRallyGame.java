package inf112.fiasko.roborally.objects;

import com.esotericsoftware.kryonet.Connection;
import inf112.fiasko.roborally.networking.RoboRallyServer;
import inf112.fiasko.roborally.networking.containers.HandResponse;
import inf112.fiasko.roborally.networking.containers.PowerDownContainerResponse;
import inf112.fiasko.roborally.networking.containers.ProgramsContainerResponse;
import inf112.fiasko.roborally.objects.properties.GameState;
import inf112.fiasko.roborally.objects.properties.Position;
import inf112.fiasko.roborally.objects.properties.RobotID;
import inf112.fiasko.roborally.objects.properties.TileType;
import inf112.fiasko.roborally.utility.BoardLoaderUtil;
import inf112.fiasko.roborally.utility.DeckLoaderUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class represent a game which is drawable using libgdx
 */
public class RoboRallyGame implements DrawableGame, InteractableGame {
    private final List<Player> playerList;
    private final boolean host;
    private final String playerName;
    private final RoboRallyServer server;
    private Phase phase;
    private Board gameBoard;
    private List<BoardElementContainer<Tile>> repairTiles;
    private Deck<ProgrammingCard> mainDeck;
    private GameState gameState = GameState.BEGINNING_OF_GAME;
    private String winningPlayerName;
    private List<ProgrammingCard> program;
    private ProgrammingCardDeck playerHand;
    private ProgrammingCardDeck extraCards;
    private Boolean testingMode;

    /**
     * Instantiates a new Robo Rally game
     *
     * @param playerList A list of all the players participating in the game
     * @param boardName  The playerName of the board to use
     * @param playerName The name of the player of this instance of the game
     * @param server     The server if this player is host. Should be null otherwise
     */
    public RoboRallyGame(List<Player> playerList, String boardName, String playerName,
                         RoboRallyServer server, Boolean testingMode) {
        this.testingMode = testingMode;
        this.playerName = playerName;
        this.host = server != null;
        this.playerList = playerList;
        this.server = server;
        initializeGame(boardName);
    }

    /**
     * Gets the power down status of the client playing this instance of the game
     *
     * @return Whether this player's robot is in power down
     */
    public Boolean getRobotPowerDown() {
        Player player = getPlayerFromName(this.playerName);
        if (player != null) {
            return gameBoard.getPowerDown(player.getRobotID());
        }
        return false;
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
    public List<Robot> getAllRobots() {
        return gameBoard.getAllRobots();
    }

    @Override
    public List<Player> getPlayers() {
        return new ArrayList<>(this.playerList);
    }

    @Override
    public synchronized GameState getGameState() {
        return gameState;
    }

    @Override
    public synchronized void setGameState(GameState gameState) {
        if (gameState == GameState.WAITING_FOR_CARDS_FROM_SERVER && this.gameState != GameState.BEGINNING_OF_GAME) {
            System.out.println("Unexpected game state " + gameState + " encountered.");
            return;
        }
        this.gameState = gameState;
    }

    @Override
    public ProgrammingCardDeck getPlayerHand() {
        return playerHand;
    }

    @Override
    public void setPlayerHand(ProgrammingCardDeck playerHand) {
        this.playerHand = playerHand;
    }

    @Override
    public ProgrammingCardDeck getExtraCards() {
        return extraCards;
    }

    @Override
    public void setExtraCards(ProgrammingCardDeck extraCards) {
        this.extraCards = extraCards;
    }

    @Override
    public List<ProgrammingCard> getProgram() {
        return program;
    }

    @Override
    public void setProgram(List<ProgrammingCard> program) {
        if (program.size() != 5 && program.size() != 0) {
            throw new IllegalArgumentException("Invalid program chosen.");
        }
        this.program = program;
    }

    @Override
    public int getProgramSize() {
        Player player = getPlayerFromName(playerName);
        if (player != null) {
            return Math.min(5, 5 - gameBoard.getRobotDamage(player.getRobotID()) + 4);
        }
        return -1;
    }

    @Override
    public void receiveAllPrograms(ProgramsContainerResponse programs) throws InterruptedException {
        //Reads data from server and updates player objects
        Map<String, List<ProgrammingCard>> programMap = programs.getProgramsMap();
        Map<String, Boolean> powerDown = programs.getPowerDownMap();
        String playerName;
        for (Player player : playerList) {
            playerName = player.getName();
            player.setProgram(programMap.get(playerName));
            player.setPowerDownNextRound(powerDown.get(playerName));
        }
        //Runs 5 phases
        setGameState(GameState.RUNNING_PROGRAMS);
        phase.runPhase(1);
        phase.runPhase(2);
        phase.runPhase(3);
        phase.runPhase(4);
        phase.runPhase(5);

        // Repair robots on repair tiles
        repairAllRobotsOnRepairTiles();
        //Updates the host's card deck

        if (host) {
            updateLockedProgrammingCardsForAllPlayers();
            removeNonLockedProgrammingCardsFromPlayers();
        }

        Player player = getPlayerFromName(this.playerName);
        if (player != null && gameBoard.getPowerDown(player.getRobotID())) {
            setGameState(GameState.CHOOSING_STAY_IN_POWER_DOWN);
        } else {
            setGameState(GameState.SKIP_STAY_IN_POWER_DOWN);
        }
    }

    @Override
    public void receiveStayInPowerDown(PowerDownContainerResponse powerDowns) {
        for (Player player : playerList) {
            if (gameBoard.getPowerDown(player.getRobotID())) {
                player.setPowerDownNextRound(powerDowns.getPowerDown().get(player.getName()));
            }
        }
        //Respawns robots and registers robots which are dead forever
        respawnRobots();
        //Sends list of dead players to server and removes dead players from the player list
        sendAllDeadPlayersToServer();
        //Resets hasTouchedFlagThisTurn
        resetHasTouchedFlagThisTurnForAllRobots();
        setGameState(GameState.BEGINNING_OF_GAME);
        runTurn();
    }

    @Override
    public String getWinningPlayerName() {
        return winningPlayerName;
    }

    @Override
    public void setWinningPlayerName(String winningPlayerName) {
        this.winningPlayerName = winningPlayerName;
    }

    /**
     * Initializes the game with a playable board
     */
    private void initializeGame(String boardName) {
        try {
            List<Robot> robots = new ArrayList<>();
            int posX = 1;
            for (Player player : playerList) {
                Position spawn = new Position(posX, 1);
                robots.add(new Robot(player.getRobotID(), spawn));
                posX++;
            }

            gameBoard = BoardLoaderUtil.loadBoard("boards/" + boardName, robots);
            moveRobotsToSpawn();

            repairTiles = gameBoard.getPositionsOfTilesOnBoard(TileType.FLAG_1, TileType.FLAG_2, TileType.FLAG_3,
                    TileType.FLAG_4, TileType.WRENCH, TileType.WRENCH_AND_HAMMER);

            if (host) {
                if (testingMode) {
                    mainDeck = DeckLoaderUtil.loadProgrammingCardsTestDeck();
                } else {
                    mainDeck = DeckLoaderUtil.loadProgrammingCardsDeck();
                }

            }

            phase = new Phase(gameBoard, playerList, 600, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Teleports all robots to their respective spawn positions
     */
    private void moveRobotsToSpawn() {
        for (Player player : playerList) {
            RobotID robotID = player.getRobotID();
            TileType robotSpawn = TileType.getTileTypeFromID(robotID.getRobotIDID() + 22);
            List<BoardElementContainer<Tile>> spawnTileContainerList = gameBoard.getPositionsOfTilesOnBoard(robotSpawn);
            if (spawnTileContainerList.size() < 1) {
                throw new IllegalArgumentException("The chosen board seems to be missing a robot spawn");
            }
            BoardElementContainer<Tile> spawnTileContainer = spawnTileContainerList.get(0);
            gameBoard.teleportRobot(robotID, spawnTileContainer.getPosition());
            gameBoard.setBackupPositionOfRobot(robotID, spawnTileContainer.getPosition());

        }
    }

    /**
     * Starts a turn in the game
     */
    public void runTurn() {
        // Sets the power down status to true on robots that have players who planned one this turn.
        // Resets players power down for next turn to false.
        updateRobotPowerDown();
        // Set damage of robots in power down to 0
        gameBoard.executePowerDown();
        //This check prevents the state from being overwritten if the client has already received the cards
        setGameState(GameState.WAITING_FOR_CARDS_FROM_SERVER);
        if (host) {
            //Distributes programming cards for all players, and sends a deck to each player
            distributeProgrammingCardsToPlayers();
            for (Connection connection : server.getPlayerNames().keySet()) {
                String playerName = server.getPlayerNames().get(connection);
                Player player = getPlayerFromName(playerName);
                if (player != null && player.getProgrammingCardDeck() != null) {
                    ProgrammingCardDeck extraCards = getExtraCardsDeck(player);
                    server.sendToClient(connection, new HandResponse(player.getProgrammingCardDeck(), extraCards));
                    extraCards.emptyInto(player.getLockedProgrammingCardDeck());
                } else {
                    throw new IllegalArgumentException("Player " + playerName + " is not part of the game.");
                }
            }
        }
    }

    /**
     * Gets a deck of extra cards to send to the player
     *
     * <p>These extra cards are cards which are locked, but not part of the player's program,</p>
     *
     * @param player The player to get extra cards for
     * @return The extra cards to send the player
     */
    private ProgrammingCardDeck getExtraCardsDeck(Player player) {
        //A player with an empty program and a robot not in power down may need extra cards
        if (player.getProgram() == null || !player.getProgram().isEmpty() || gameBoard.getPowerDown(player.getRobotID())) {
            return new ProgrammingCardDeck(new ArrayList<>());
        }
        int programSize = Math.min(5, 5 - gameBoard.getRobotDamage(player.getRobotID()) + 4);
        int lockedCards = 5 - programSize;
        ProgrammingCardDeck extraDeck = new ProgrammingCardDeck();
        for (int i = 0; i < lockedCards; i++) {
            extraDeck.draw(mainDeck);
        }
        return extraDeck;
    }

    /**
     * Sends information about players no longer part of the game to the server
     */
    private void sendAllDeadPlayersToServer() {
        //Removes dead players from playerList
        playerList.removeIf((player) -> gameBoard.getRealDeadRobots().contains(player.getRobotID()));
        if (playerList.isEmpty()) {
            setGameState(GameState.EXITED);
            try {
                Thread.sleep(100000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (host) {
            server.setDeadPlayers(gameBoard.getRealDeadRobots());
        }
    }

    /**
     * Resets the boolean for if the robot has touched a flag this turn, to set up the next turn.
     */
    private void resetHasTouchedFlagThisTurnForAllRobots() {
        for (RobotID robotID : RobotID.values()) {
            gameBoard.setHasTouchedFlagThisTurn(robotID, false);
        }
    }

    /**
     * Locks the players programming cards in relation to the robots damage
     */
    private void updateLockedProgrammingCardsForAllPlayers() {
        for (Player player : playerList) {
            List<ProgrammingCard> playerProgram = player.getProgram();
            ProgrammingCardDeck playerDeck = player.getProgrammingCardDeck();
            ProgrammingCardDeck lockedPlayerDeck = player.getLockedProgrammingCardDeck();
            int robotDamage;
            if (!gameBoard.isRobotAlive(player.getRobotID())) {
                robotDamage = 0;
            } else {
                robotDamage = gameBoard.getRobotDamage(player.getRobotID());
            }

            //The player has no locked cards. All previously locked cards should go into the free deck
            if (robotDamage <= 4 || playerProgram.size() == 0) {
                lockedPlayerDeck.emptyInto(player.getProgrammingCardDeck());
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
     *
     * @param card             The card to move to the locked deck
     * @param playerDeck       The deck containing the player's cards
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
            player.getProgrammingCardDeck().emptyInto(mainDeck);
        }
    }

    /**
     * Deals correct amount of cards to active players, based on their robots damage
     */
    private void distributeProgrammingCardsToPlayers() {
        mainDeck.shuffle();
        for (Player player : playerList) {
            RobotID robot = player.getRobotID();
            ProgrammingCardDeck playerDeck = player.getProgrammingCardDeck();
            int robotDamage = gameBoard.getRobotDamage(robot);
            //Powered down or heavily damaged robots don't get any cards
            if (gameBoard.getPowerDown(robot) || robotDamage >= 9) {
                continue;
            }
            if (!playerDeck.isEmpty()) {
                throw new IllegalStateException("Player deck must be empty when dealing new cards!");
            }
            //Gives the player the correct amount of cards
            playerDeck.draw(mainDeck, 9 - robotDamage);
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
     * Sets the robot's power down status to the player's "power down next turn" status and sets the players status to false
     */
    private void updateRobotPowerDown() {
        for (Player player : playerList) {
            setRobotPowerDown(player, player.getPowerDownNextRound());
            player.setPowerDownNextRound(false);
        }
    }

    /**
     * Sets the power down status of a robot
     *
     * @param player          The player that owns the robot
     * @param powerDownStatus The new power down status
     */
    private void setRobotPowerDown(Player player, Boolean powerDownStatus) {
        gameBoard.setPowerDown(player.getRobotID(), powerDownStatus);
    }

    /**
     * Gets a player object given a player name
     *
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
}