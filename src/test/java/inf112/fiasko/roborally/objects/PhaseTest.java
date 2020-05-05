package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.objects.properties.Action;
import inf112.fiasko.roborally.objects.properties.Direction;
import inf112.fiasko.roborally.objects.properties.Position;
import inf112.fiasko.roborally.objects.properties.RobotID;
import inf112.fiasko.roborally.utility.BoardLoaderUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class PhaseTest {
    private List<Robot> robots = new ArrayList<>();
    private Phase phase;
    private Board board;
    private Position robot1Position = new Position(2, 2);
    private Position robot2Position = new Position(3, 2);
    private Position robot3Position = new Position(7, 2);
    private Position robot4Position = new Position(3, 8);
    private Position robot5Position = new Position(2, 14);
    private Position robot6Position = new Position(2, 15);
    private Robot robot1 = new Robot(RobotID.ROBOT_1, robot1Position);
    private Robot robot2 = new Robot(RobotID.ROBOT_2, robot2Position);
    private Robot robot3 = new Robot(RobotID.ROBOT_3, robot3Position);
    private Robot robot4 = new Robot(RobotID.ROBOT_4, robot4Position);
    private Robot robot5 = new Robot(RobotID.ROBOT_5, robot5Position);
    private Robot robot6 = new Robot(RobotID.ROBOT_6, robot6Position);
    private Player player1 = new Player(RobotID.ROBOT_1, "Player 1");
    private Player player2 = new Player(RobotID.ROBOT_2, "Player 2");
    private Player player3 = new Player(RobotID.ROBOT_3, "Player 3");
    private Player player4 = new Player(RobotID.ROBOT_4, "Player 4");
    private Player player5 = new Player(RobotID.ROBOT_5, "Player 5");
    private Player player6 = new Player(RobotID.ROBOT_6, "Player 6");
    private FakeGame testGame;

    @Before
    public void setUp() throws IOException {
        testGame = new FakeGame();
        robots.add(robot1);
        robots.add(robot2);
        robots.add(robot3);
        robots.add(robot4);
        robots.add(robot5);
        robots.add(robot6);

        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);
        playerList.add(player5);
        playerList.add(player6);
        board = BoardLoaderUtil.loadBoard("boards/Checkmate.txt", robots);
        this.phase = new Phase(board, playerList, 0, null);
    }

    @Test
    public void robotCannotLeaveConveyorBeltIfBlockedByRobot() throws InterruptedException {
        phase.moveAllConveyorBelts();
        assertEquals(RobotID.ROBOT_1, board.getRobotOnPosition(robot1Position));
    }

    @Test
    public void playerWinsAfterTouchingAllFlagsInCorrectOrder() throws IOException {
        List<Robot> robots = new ArrayList<>();
        List<Player> players = getPlayers(1);
        RobotID robotID = RobotID.ROBOT_1;
        Robot robot = new Robot(robotID, new Position(0, 0));
        robots.add(robot);
        Phase testPhase = createPhaseAndLoadBoard(players, robots, "boards/win_test_board.txt");

        //Should have registered 0 flags
        assertEquals(0, robot.getLastFlagVisited());
        assertFalse(robot.hasTouchedFlagThisTurn());
        testPhase.checkAllFlags();
        //Should have registered first flag
        assertEquals(1, robot.getLastFlagVisited());
        assertTrue(robot.hasTouchedFlagThisTurn());
        robot.setHasTouchedFlagThisTurn(false);
        assertNull(testGame.getWinningPlayerName());
        board.moveRobot(robotID, Direction.SOUTH);
        testPhase.checkAllFlags();
        //Should have registered second flag
        assertEquals(2, robot.getLastFlagVisited());
        assertTrue(robot.hasTouchedFlagThisTurn());
        assertNull(testGame.getWinningPlayerName());
        robot.setHasTouchedFlagThisTurn(false);
        board.moveRobot(robotID, Direction.SOUTH);
        testPhase.checkAllFlags();
        //Should have registered third flag
        assertEquals(3, robot.getLastFlagVisited());
        assertTrue(robot.hasTouchedFlagThisTurn());
        assertNull(testGame.getWinningPlayerName());
        robot.setHasTouchedFlagThisTurn(false);
        board.moveRobot(robotID, Direction.SOUTH);
        testPhase.checkAllFlags();
        //Should have registered fourth and last flag
        assertEquals(4, robot.getLastFlagVisited());
        assertTrue(robot.hasTouchedFlagThisTurn());
        assertEquals("Player 1", testGame.getWinningPlayerName());
    }

    @Test
    public void robotRegistersFlagWhenOnCorrectOne() {
        assertEquals(robot3.getLastFlagVisited(), 0);
        assertFalse(robot3.hasTouchedFlagThisTurn());
        phase.checkAllFlags();
        assertEquals(robot3.getLastFlagVisited(), 1);
        assertTrue(robot3.hasTouchedFlagThisTurn());
    }

    @Test
    public void robotDoesNotRegisterWrongFlag() {
        assertEquals(0, robot4.getLastFlagVisited());
        assertFalse(robot4.hasTouchedFlagThisTurn());
        phase.checkAllFlags();
        assertEquals(0, robot4.getLastFlagVisited());
        assertFalse(robot4.hasTouchedFlagThisTurn());
    }

    @Test
    public void actionDoesPerformAnAction() throws InterruptedException {
        assertEquals(robot4.getRobotId(), board.getRobotOnPosition(robot4Position));
        phase.makeMove(RobotID.ROBOT_4, Action.MOVE_1);
        assertEquals(robot4.getRobotId(), board.getRobotOnPosition(new Position(3, 7)));
    }

    @Test
    public void robotFiresLaserAndHitsAnotherRobot() throws InterruptedException, IOException {
        List<Robot> robots = new ArrayList<>();
        List<Player> players = getPlayers(2);
        Robot robot1 = new Robot(RobotID.ROBOT_1, new Position(9, 12));
        Robot robot2 = new Robot(RobotID.ROBOT_2, new Position(9, 13));
        robots.add(robot1);
        robots.add(robot2);

        Phase testPhase = createPhaseAndLoadBoard(players, robots, "boards/another_test_map.txt");
        assertEquals(0, robot2.getDamageTaken());
        assertEquals(0, robot1.getDamageTaken());
        testPhase.fireAllLasers();
        assertEquals(0, robot2.getDamageTaken());
        assertEquals(1, robot1.getDamageTaken());
    }

    @Test
    public void robotFiresLaserAndHitsAWallDoesNotDamageRobotOnOtherSide() throws InterruptedException, IOException {
        List<Robot> robots = new ArrayList<>();
        List<Player> players = getPlayers(2);
        Robot robot1 = new Robot(RobotID.ROBOT_1, new Position(9, 11));
        Robot robot2 = new Robot(RobotID.ROBOT_2, new Position(9, 13));
        robots.add(robot1);
        robots.add(robot2);

        Phase testPhase = createPhaseAndLoadBoard(players, robots, "boards/another_test_map.txt");
        assertEquals(0, robot2.getDamageTaken());
        assertEquals(0, robot1.getDamageTaken());
        testPhase.fireAllLasers();
        assertEquals(0, robot2.getDamageTaken());
        assertEquals(0, robot1.getDamageTaken());
    }

    @Test
    public void robotGetsHitBy2Lasers() throws InterruptedException, IOException {
        List<Robot> robots = new ArrayList<>();
        List<Player> players = getPlayers(3);
        Robot robot = new Robot(RobotID.ROBOT_1, new Position(9, 12));
        Robot robot3 = new Robot(RobotID.ROBOT_3, new Position(8, 12));
        robots.add(robot);
        robots.add(new Robot(RobotID.ROBOT_2, new Position(9, 13)));
        robots.add(robot3);
        Phase testPhase = createPhaseAndLoadBoard(players, robots, "boards/another_test_map.txt");
        robot3.setFacingDirection(Direction.EAST);

        assertEquals(0, robot.getDamageTaken());
        testPhase.fireAllLasers();
        assertEquals(2, robot.getDamageTaken());
    }


    @Test
    public void robotFiresLaserAndHitsARobotDoesNotDamageRobotOnOtherSide() throws InterruptedException, IOException {
        List<Robot> robots = new ArrayList<>();
        List<Player> players = getPlayers(3);
        Robot bot1 = new Robot(RobotID.ROBOT_1, new Position(9, 12));
        robots.add(bot1);
        robots.add(new Robot(RobotID.ROBOT_2, new Position(9, 13)));
        robots.add(new Robot(RobotID.ROBOT_3, new Position(9, 14)));

        Phase testPhase = createPhaseAndLoadBoard(players, robots, "boards/another_test_map.txt");
        assertEquals(0, bot1.getDamageTaken());
        testPhase.fireAllLasers();
        assertEquals(1, bot1.getDamageTaken());
    }

    @Test
    public void robotGetsRotatedByCog() throws InterruptedException, IOException {
        List<Robot> robots = new ArrayList<>();
        List<Player> players = getPlayers(2);
        Robot robot1 = new Robot(RobotID.ROBOT_1, new Position(0, 0));
        robots.add(robot1);
        robots.add(new Robot(RobotID.ROBOT_2, new Position(1, 0)));

        Phase testPhase = createPhaseAndLoadBoard(players, robots, "boards/another_test_map.txt");

        assertEquals(Direction.NORTH, robot1.getFacingDirection());
        testPhase.rotateCogwheels();
        assertEquals(Direction.EAST, robot1.getFacingDirection());
    }

    @Test
    public void programmingCardsGetsPlayedInOrder() throws InterruptedException {
        List<ProgrammingCard> testProgram1 = new ArrayList<>();
        List<ProgrammingCard> testProgram2 = new ArrayList<>();
        List<ProgrammingCard> testProgram3 = new ArrayList<>();
        testProgram1.add(new ProgrammingCard(10, Action.MOVE_1));
        testProgram2.add(new ProgrammingCard(20, Action.MOVE_1));
        testProgram3.add(new ProgrammingCard(30, Action.ROTATE_LEFT));
        for (int i = 0; i < 4; i++) {
            testProgram1.add(null);
            testProgram2.add(null);
            testProgram3.add(null);
        }
        player1.setProgram(testProgram3);
        player2.setProgram(testProgram3);
        player3.setProgram(testProgram3);
        player4.setProgram(testProgram3);
        player5.setProgram(testProgram1);
        player6.setProgram(testProgram2);
        assertEquals(robot5.getRobotId(), board.getRobotOnPosition(robot5Position));
        assertEquals(robot6.getRobotId(), board.getRobotOnPosition(robot6Position));
        phase.runProgrammingCards(1);
        assertEquals(robot5.getRobotId(), board.getRobotOnPosition(new Position(2, 12)));
        assertEquals(robot6.getRobotId(), board.getRobotOnPosition(new Position(2, 14)));
    }

    @Test
    public void robotsOnConveyorBeltsFacingTheSameEmptyTileDoesNotMove() throws InterruptedException, IOException {
        List<Robot> robots = new ArrayList<>();
        List<Player> players = getPlayers(2);
        robots.add(new Robot(RobotID.ROBOT_1, new Position(8, 11)));
        robots.add(new Robot(RobotID.ROBOT_2, new Position(7, 10)));

        Phase testPhase = createPhaseAndLoadBoard(players, robots);
        testPhase.moveAllConveyorBelts();

        assertEquals(RobotID.ROBOT_1, board.getRobotOnPosition(new Position(8, 11)));
        assertEquals(RobotID.ROBOT_2, board.getRobotOnPosition(new Position(7, 10)));
    }

    @Test
    public void robotsOnConveyorBeltsFacingTheSameHoleTileDoesNotMove() throws IOException, InterruptedException {
        List<Robot> robots = new ArrayList<>();
        List<Player> players = getPlayers(2);
        Position robot1Position = new Position(6, 7);
        Position robot2Position = new Position(7, 8);
        robots.add(new Robot(RobotID.ROBOT_1, robot1Position));
        robots.add(new Robot(RobotID.ROBOT_2, robot2Position));

        Phase testPhase = createPhaseAndLoadBoard(players, robots);
        testPhase.moveAllConveyorBelts();

        assertEquals(RobotID.ROBOT_1, board.getRobotOnPosition(robot1Position));
        assertEquals(RobotID.ROBOT_2, board.getRobotOnPosition(robot2Position));
    }

    @Test
    public void robotOnConveyorBeltsFacingWallDoesNotMove() throws IOException, InterruptedException {
        List<Robot> robots = new ArrayList<>();
        List<Player> players = getPlayers(1);
        Position robotPosition = new Position(1, 1);
        robots.add(new Robot(RobotID.ROBOT_1, robotPosition));

        Phase testPhase = createPhaseAndLoadBoard(players, robots);
        testPhase.moveAllConveyorBelts();

        assertEquals(RobotID.ROBOT_1, board.getRobotOnPosition(robotPosition));
    }

    @Test
    public void robotBehindAnotherRobotOnConveyorBeltsFacingWallDoesNotMove() throws IOException, InterruptedException {
        List<Robot> robots = new ArrayList<>();
        List<Player> players = getPlayers(2);
        Position robot1Position = new Position(1, 1);
        Position robot2Position = new Position(1, 2);
        robots.add(new Robot(RobotID.ROBOT_1, robot1Position));
        robots.add(new Robot(RobotID.ROBOT_2, robot2Position));

        Phase testPhase = createPhaseAndLoadBoard(players, robots);
        testPhase.moveAllConveyorBelts();

        assertEquals(RobotID.ROBOT_1, board.getRobotOnPosition(robot1Position));
        assertEquals(RobotID.ROBOT_2, board.getRobotOnPosition(robot2Position));
    }

    @Test
    public void robotBehindOtherRobotsOnSlowConveyorBeltsFacingEmptyTilesMoves() throws IOException, InterruptedException {
        List<Robot> robots = new ArrayList<>();
        List<Player> players = getPlayers(4);
        robots.add(new Robot(RobotID.ROBOT_1, new Position(5, 7)));
        robots.add(new Robot(RobotID.ROBOT_2, new Position(5, 8)));
        robots.add(new Robot(RobotID.ROBOT_3, new Position(5, 9)));
        robots.add(new Robot(RobotID.ROBOT_4, new Position(5, 10)));

        Phase testPhase = createPhaseAndLoadBoard(players, robots);
        testPhase.moveAllConveyorBelts();

        assertEquals(RobotID.ROBOT_1, board.getRobotOnPosition(new Position(5, 6)));
        assertEquals(RobotID.ROBOT_2, board.getRobotOnPosition(new Position(5, 7)));
        assertEquals(RobotID.ROBOT_3, board.getRobotOnPosition(new Position(5, 8)));
        assertEquals(RobotID.ROBOT_4, board.getRobotOnPosition(new Position(5, 9)));
    }

    @Test
    public void robotBehindOtherRobotsOnFastConveyorBeltsFacingEmptyTilesMoves() throws IOException, InterruptedException {
        List<Robot> robots = new ArrayList<>();
        List<Player> players = getPlayers(4);
        robots.add(new Robot(RobotID.ROBOT_1, new Position(4, 7)));
        robots.add(new Robot(RobotID.ROBOT_2, new Position(4, 8)));
        robots.add(new Robot(RobotID.ROBOT_3, new Position(4, 9)));
        robots.add(new Robot(RobotID.ROBOT_4, new Position(4, 10)));

        Phase testPhase = createPhaseAndLoadBoard(players, robots);
        testPhase.moveAllConveyorBelts();

        assertEquals(RobotID.ROBOT_1, board.getRobotOnPosition(new Position(4, 5)));
        assertEquals(RobotID.ROBOT_2, board.getRobotOnPosition(new Position(4, 6)));
        assertEquals(RobotID.ROBOT_3, board.getRobotOnPosition(new Position(4, 7)));
        assertEquals(RobotID.ROBOT_4, board.getRobotOnPosition(new Position(4, 8)));
    }

    @Test
    public void robotBehindOtherRobotsOnConveyorBeltsShapedAsARoundaboutMoves() throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();
        List<Robot> robots = new ArrayList<>();
        List<Player> players = getPlayers(4);
        robots.add(new Robot(RobotID.ROBOT_1, new Position(1, 8)));
        robots.add(new Robot(RobotID.ROBOT_2, new Position(2, 8)));
        robots.add(new Robot(RobotID.ROBOT_3, new Position(2, 9)));
        robots.add(new Robot(RobotID.ROBOT_4, new Position(1, 9)));

        Phase testPhase = createPhaseAndLoadBoard(players, robots);
        testPhase.moveAllConveyorBelts();

        assertEquals(RobotID.ROBOT_1, board.getRobotOnPosition(new Position(1, 9)));
        assertEquals(RobotID.ROBOT_2, board.getRobotOnPosition(new Position(1, 8)));
        assertEquals(RobotID.ROBOT_3, board.getRobotOnPosition(new Position(2, 8)));
        assertEquals(RobotID.ROBOT_4, board.getRobotOnPosition(new Position(2, 9)));
        int elapsedTime = (int) Math.floor((System.currentTimeMillis() - startTime) / 1000f);
        assertTrue(elapsedTime < 1);
    }

    @Test
    public void robotOnConveyorBeltFacingHoleMovesAndDies() throws IOException, InterruptedException {
        List<Robot> robots = new ArrayList<>();
        List<Player> players = getPlayers(1);
        robots.add(new Robot(RobotID.ROBOT_1, new Position(6, 7)));

        Phase testPhase = createPhaseAndLoadBoard(players, robots);
        testPhase.moveAllConveyorBelts();

        assertFalse(board.isRobotAlive(RobotID.ROBOT_1));
        assertNull(board.getRobotOnPosition(new Position(6, 7)));
        assertNull(board.getRobotOnPosition(new Position(7, 7)));
    }

    @Test
    public void robotOnConveyorBeltFacingOutOfMapMovesAndDies() throws IOException, InterruptedException {
        List<Robot> robots = new ArrayList<>();
        List<Player> players = getPlayers(1);
        robots.add(new Robot(RobotID.ROBOT_1, new Position(7, 0)));

        Phase testPhase = createPhaseAndLoadBoard(players, robots);
        testPhase.moveAllConveyorBelts();

        assertFalse(board.isRobotAlive(RobotID.ROBOT_1));
        assertNull(board.getRobotOnPosition(new Position(7, 0)));
    }

    @Test
    public void robotOnConveyorBeltFacingOutOfMapMovesIntoWallIsBlocked() throws IOException, InterruptedException {
        List<Robot> robots = new ArrayList<>();
        List<Player> players = getPlayers(1);
        robots.add(new Robot(RobotID.ROBOT_1, new Position(0, 0)));

        Phase testPhase = createPhaseAndLoadBoard(players, robots);
        testPhase.moveAllConveyorBelts();

        assertTrue(board.isRobotAlive(RobotID.ROBOT_1));
        assertEquals(RobotID.ROBOT_1, board.getRobotOnPosition(new Position(0, 0)));
    }

    @Test
    public void robotOnConveyorBeltFacingOutOfMapWithOneTileBetweenCanBeMoved() throws IOException, InterruptedException {
        List<Robot> robots = new ArrayList<>();
        List<Player> players = getPlayers(1);
        robots.add(new Robot(RobotID.ROBOT_1, new Position(10, 10)));

        Phase testPhase = createPhaseAndLoadBoard(players, robots);
        testPhase.moveAllConveyorBelts();

        assertTrue(board.isRobotAlive(RobotID.ROBOT_1));
        assertEquals(RobotID.ROBOT_1, board.getRobotOnPosition(new Position(10, 11)));
    }

    /**
     * Gets a list of a specific number of players
     *
     * @param numberOfPlayers The number of players to generate
     * @return A list of players
     */
    private List<Player> getPlayers(int numberOfPlayers) {
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            players.add(new Player(RobotID.getRobotIDFromID(i), "Player " + i));
        }
        return players;
    }

    /**
     * Loads a board to the variable and creates a new phase
     *
     * @param players   A list of players participating in the game
     * @param robots    A list of robots on the board
     * @param boardName The name of the board to load
     * @return A phase object
     */
    private Phase createPhaseAndLoadBoard(List<Player> players, List<Robot> robots, String boardName) throws IOException {
        board = BoardLoaderUtil.loadBoard(boardName, robots);
        return new Phase(board, players, 0, testGame);
    }

    /**
     * Loads the test board to the variable and creates a new phase
     *
     * @param players A list of players participating in the game
     * @param robots  A list of robots on the board
     * @return A phase object
     */
    private Phase createPhaseAndLoadBoard(List<Player> players, List<Robot> robots) throws IOException {
        return createPhaseAndLoadBoard(players, robots, "boards/test_board.txt");
    }
}
