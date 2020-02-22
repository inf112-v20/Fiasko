package inf112.fiasko.roborally.game;

import inf112.fiasko.roborally.element_properties.GameTexture;
import inf112.fiasko.roborally.objects.Board;
import inf112.fiasko.roborally.objects.DrawableObject;
import inf112.fiasko.roborally.objects.IDrawableObject;
import inf112.fiasko.roborally.objects.Robot;
import inf112.fiasko.roborally.utility.BoardLoaderUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a game which is drawable using libgdx
 */
public class Game implements IDrawableGame {
    private final int TILE_SIZE = 64;
    private Board gameBoard;

    public Game() {
        try {
            List<Robot> robots = new ArrayList<>();
            gameBoard = BoardLoaderUtil.loadBoard("boards/Checkmate.txt", robots);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getWidth() {
        return gameBoard.getBoardWidth() * TILE_SIZE;
    }

    @Override
    public int getHeight() {
        return gameBoard.getBoardHeight() * TILE_SIZE;
    }

    @Override
    public List<IDrawableObject> getObjectsToDraw() {
        List<IDrawableObject> list = new ArrayList<>();
        for (int i = 0; i < gameBoard.getBoardWidth(); i++) {
            for (int j = 0; j < gameBoard.getBoardHeight(); j++) {
                DrawableObject tile = new DrawableObject(GameTexture.TILE, i * TILE_SIZE, j * TILE_SIZE);
                list.add(tile);
            }
        }
        DrawableObject robot = new DrawableObject(GameTexture.ROBOT, TILE_SIZE, TILE_SIZE);
        list.add(robot);
        return list;
    }
}
