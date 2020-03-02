package inf112.fiasko.roborally.game;

import inf112.fiasko.roborally.element_properties.Position;
import inf112.fiasko.roborally.element_properties.RobotID;
import inf112.fiasko.roborally.objects.Board;
import inf112.fiasko.roborally.objects.Robot;
import inf112.fiasko.roborally.objects.Tile;
import inf112.fiasko.roborally.objects.Wall;
import inf112.fiasko.roborally.utility.BoardLoaderUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This class represent a game which is drawable using libgdx
 */
public class Game implements IDrawableGame {
    private Board gameBoard;

    public Game(boolean debug) {
        if (debug) {
            initializeDebugMode();
        } else {
            initializeGame();
        }
    }

    public Game() {
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
        long cycleDelay = 600;
        TimeUnit.SECONDS.sleep(3);
        gameBoard.rotateRobotRight(RobotID.ROBOT_1);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.rotateRobotRight(RobotID.ROBOT_1);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.moveRobotForward(RobotID.ROBOT_1);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.rotateRobotLeft(RobotID.ROBOT_1);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.moveRobotForward(RobotID.ROBOT_1);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.moveRobotForward(RobotID.ROBOT_1);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.rotateRobotRight(RobotID.ROBOT_1);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.moveRobotForward(RobotID.ROBOT_1);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.rotateRobotRight(RobotID.ROBOT_2);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.moveRobotForward(RobotID.ROBOT_2);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.rotateRobotRight(RobotID.ROBOT_2);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.rotateRobotRight(RobotID.ROBOT_2);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.moveRobotForward(RobotID.ROBOT_2);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.rotateRobotRight(RobotID.ROBOT_2);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.rotateRobotRight(RobotID.ROBOT_2);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.moveRobotForward(RobotID.ROBOT_2);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.moveRobotForward(RobotID.ROBOT_2);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.rotateRobotRight(RobotID.ROBOT_2);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.rotateRobotRight(RobotID.ROBOT_2);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.moveRobotForward(RobotID.ROBOT_2);
        TimeUnit.MILLISECONDS.sleep(cycleDelay);
        gameBoard.moveRobotForward(RobotID.ROBOT_2);
    }
}
