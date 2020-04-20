package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.elementproperties.Position;
import inf112.fiasko.roborally.elementproperties.RobotID;
import inf112.fiasko.roborally.utility.BoardLoaderUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class PhaseTest {
    private Phase phase;
    private Board board;
    private Position robot1Position;

    @Before
    public void setUp() {
        List<Robot> robots = new ArrayList<>();
        robot1Position = new Position(2, 2);
        robots.add(new Robot(RobotID.ROBOT_1, robot1Position));
        robots.add(new Robot(RobotID.ROBOT_2, new Position(3, 2)));
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player(RobotID.ROBOT_1, "Player 1"));
        playerList.add(new Player(RobotID.ROBOT_2, "Player 2"));
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
        robot.add(new Robot(RobotID.ROBOT_1, new Position(0,0)));
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
}
