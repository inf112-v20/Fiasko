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
    private List<BoardElementContainer<Tile>> repairTiles;
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
    private Phase phase;

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
        this.phase = new Phase(gameBoard, playerList, 600, this);
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
        this.phase = new Phase(gameBoard, playerList, 600, this);
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
            repairTiles = gameBoard.getPositionsOfTileOnBoard(TileType.FLAG_1, TileType.FLAG_2, TileType.FLAG_3,
                    TileType.FLAG_4, TileType.WRENCH, TileType.WRENCH_AND_HAMMER);

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
        phase.runPhase(1);
        phase.runPhase(2);
        phase.runPhase(3);
        phase.runPhase(4);
        phase.runPhase(5);

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
    protected void setWinningPlayerName(String winningPlayerName) {
        this.winningPlayerName = winningPlayerName;
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