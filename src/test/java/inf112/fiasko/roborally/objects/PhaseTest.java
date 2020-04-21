package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.elementproperties.Action;
import inf112.fiasko.roborally.elementproperties.Position;
import inf112.fiasko.roborally.elementproperties.RobotID;
import inf112.fiasko.roborally.utility.BoardLoaderUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

public class PhaseTest {
    List<Robot> robots = new ArrayList<>();
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

    @Before
    public void setUp() {
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
        try {
            board = BoardLoaderUtil.loadBoard("boards/Checkmate.txt", robots);
            this.phase = new Phase(board, playerList, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void robotCannotLeaveConveyorBeltIfBlockedByRobot() throws InterruptedException {
        phase.moveAllConveyorBelts();
        assertEquals(RobotID.ROBOT_1, board.getRobotOnPosition(robot1Position));
    }

    @Test
    public void playerWinsAfterTouchingAllFlagsInCorrectOrder() {
        FakeGame testGame = new FakeGame();
        List<Robot> robot = new ArrayList<>();
        List<Player> player = new ArrayList<>();
        robot.add(new Robot(RobotID.ROBOT_1, new Position(0, 0)));
        player.add(new Player(RobotID.ROBOT_1, "Player 1"));

        try {
            board = BoardLoaderUtil.loadBoard("boards/win_test_board.txt", robot);
            Phase testPhase = new Phase(board, player, 0, testGame);
            testPhase.checkAllFlags();
            assertEquals("Player 1", testGame.getWinningPlayerName());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void robotDoesNotRegistersWrongFlag() {
        assertEquals(robot4.getLastFlagVisited(), 0);
        assertFalse(robot4.hasTouchedFlagThisTurn());
        phase.checkAllFlags();
        assertEquals(robot4.getLastFlagVisited(), 0);
        assertFalse(robot4.hasTouchedFlagThisTurn());
    }

    @Test
    public void actionDoesPerformAnAction() throws InterruptedException {
        assertEquals(robot4.getRobotId(), board.getRobotOnPosition(robot4Position));
        phase.makeMove(RobotID.ROBOT_4, Action.MOVE_1);
        assertEquals(robot4.getRobotId(), board.getRobotOnPosition(new Position(3, 7)));
    }

    @Test
    public void programmingCardsGetsPlayedInOrder() throws InterruptedException {
        List<ProgrammingCard> testProgram1 = new ArrayList<>();
        List<ProgrammingCard> testProgram2 = new ArrayList<>();
        List<ProgrammingCard> testProgram3 = new ArrayList<>();
        ProgrammingCard card1 = new ProgrammingCard(1, Action.MOVE_1);
        ProgrammingCard card2 = new ProgrammingCard(2, Action.ROTATE_RIGHT);
        ProgrammingCard card3 = new ProgrammingCard(3, Action.MOVE_1);
        ProgrammingCard card4 = new ProgrammingCard(4, Action.MOVE_1);
        ProgrammingCard card5 = new ProgrammingCard(5, Action.MOVE_1);
        ProgrammingCard card6 = new ProgrammingCard(10, Action.MOVE_1);
        ProgrammingCard card7 = new ProgrammingCard(11, Action.ROTATE_RIGHT);
        ProgrammingCard card8 = new ProgrammingCard(12, Action.MOVE_1);
        ProgrammingCard card9 = new ProgrammingCard(13, Action.MOVE_1);
        ProgrammingCard card10 = new ProgrammingCard(14, Action.MOVE_1);
        ProgrammingCard card11 = new ProgrammingCard(100, Action.ROTATE_LEFT);
        ProgrammingCard card12 = new ProgrammingCard(200, Action.ROTATE_LEFT);
        ProgrammingCard card13 = new ProgrammingCard(300, Action.ROTATE_LEFT);
        ProgrammingCard card14 = new ProgrammingCard(400, Action.ROTATE_LEFT);
        ProgrammingCard card15 = new ProgrammingCard(500, Action.ROTATE_LEFT);
        testProgram1.add(card1);
        testProgram1.add(card2);
        testProgram1.add(card3);
        testProgram1.add(card4);
        testProgram1.add(card5);
        testProgram2.add(card6);
        testProgram2.add(card7);
        testProgram2.add(card8);
        testProgram2.add(card9);
        testProgram2.add(card10);
        testProgram3.add(card11);
        testProgram3.add(card12);
        testProgram3.add(card13);
        testProgram3.add(card14);
        testProgram3.add(card15);
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
    public void robotsOnConveyorBeltsFacingTheSameEmptyTileDoesNotMove() {
        List<Robot> robots = new ArrayList<>();
        List<Player> players = new ArrayList<>();
        robots.add(new Robot(RobotID.ROBOT_1, new Position(8, 11)));
        robots.add(new Robot(RobotID.ROBOT_2, new Position(7, 10)));
        players.add(new Player(RobotID.ROBOT_1, "Player 1"));
        players.add(new Player(RobotID.ROBOT_2, "Player 2"));

        try {
            board = BoardLoaderUtil.loadBoard("boards/test_board.txt", robots);
            Phase testPhase = new Phase(board, players, 0, null);
            testPhase.moveAllConveyorBelts();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(RobotID.ROBOT_1, board.getRobotOnPosition(new Position(8, 11)));
        assertEquals(RobotID.ROBOT_2, board.getRobotOnPosition(new Position(7, 10)));
    }

    @Test
    public void robotsOnConveyorBeltsFacingTheSameHoleTileDoesNotMove() {
        List<Robot> robots = new ArrayList<>();
        List<Player> players = new ArrayList<>();
        robots.add(new Robot(RobotID.ROBOT_1, new Position(6, 7)));
        robots.add(new Robot(RobotID.ROBOT_2, new Position(7, 8)));
        players.add(new Player(RobotID.ROBOT_1, "Player 1"));
        players.add(new Player(RobotID.ROBOT_2, "Player 2"));

        try {
            board = BoardLoaderUtil.loadBoard("boards/test_board.txt", robots);
            Phase testPhase = new Phase(board, players, 0, null);
            testPhase.moveAllConveyorBelts();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(RobotID.ROBOT_1, board.getRobotOnPosition(new Position(6, 7)));
        assertEquals(RobotID.ROBOT_2, board.getRobotOnPosition(new Position(7, 8)));
    }


}
