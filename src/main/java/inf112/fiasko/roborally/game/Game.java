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

    public Game() {
        try {
            List<Robot> robots = new ArrayList<>();
            robots.add(new Robot(RobotID.ROBOT_1, new Position(1, 1)));
            robots.add(new Robot(RobotID.ROBOT_2, new Position(1, 2)));
            gameBoard = BoardLoaderUtil.loadBoard("boards/Checkmate.txt", robots);
            new Thread(() -> {
                try {
                    runGameLoop();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
        TimeUnit.SECONDS.sleep(3);
        gameBoard.rotateRobotRight(RobotID.ROBOT_1);
        gameBoard.rotateRobotRight(RobotID.ROBOT_1);
        TimeUnit.SECONDS.sleep(1);
        gameBoard.moveRobotForward(RobotID.ROBOT_1);
        TimeUnit.SECONDS.sleep(1);
        gameBoard.rotateRobotLeft(RobotID.ROBOT_1);
        TimeUnit.SECONDS.sleep(1);
        gameBoard.moveRobotForward(RobotID.ROBOT_1);
        TimeUnit.SECONDS.sleep(1);
        gameBoard.moveRobotForward(RobotID.ROBOT_1);
        TimeUnit.SECONDS.sleep(1);
        gameBoard.rotateRobotRight(RobotID.ROBOT_1);
        TimeUnit.SECONDS.sleep(1);
        gameBoard.moveRobotForward(RobotID.ROBOT_1);
        TimeUnit.SECONDS.sleep(1);
        gameBoard.rotateRobotRight(RobotID.ROBOT_2);
        TimeUnit.SECONDS.sleep(1);
        gameBoard.moveRobotForward(RobotID.ROBOT_2);
        TimeUnit.SECONDS.sleep(1);
        gameBoard.rotateRobotRight(RobotID.ROBOT_2);
        gameBoard.rotateRobotRight(RobotID.ROBOT_2);
        TimeUnit.SECONDS.sleep(1);
        gameBoard.moveRobotForward(RobotID.ROBOT_2);
        TimeUnit.SECONDS.sleep(1);
        gameBoard.rotateRobotRight(RobotID.ROBOT_2);
        gameBoard.rotateRobotRight(RobotID.ROBOT_2);
        TimeUnit.SECONDS.sleep(1);
        gameBoard.moveRobotForward(RobotID.ROBOT_2);
        TimeUnit.SECONDS.sleep(1);
        gameBoard.moveRobotForward(RobotID.ROBOT_2);
        TimeUnit.SECONDS.sleep(1);
        gameBoard.rotateRobotRight(RobotID.ROBOT_2);
        gameBoard.rotateRobotRight(RobotID.ROBOT_2);
        TimeUnit.SECONDS.sleep(1);
        gameBoard.moveRobotForward(RobotID.ROBOT_2);
        TimeUnit.SECONDS.sleep(1);
        gameBoard.moveRobotForward(RobotID.ROBOT_2);
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
}
