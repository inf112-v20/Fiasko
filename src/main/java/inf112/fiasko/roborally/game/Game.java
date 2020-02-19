package inf112.fiasko.roborally.game;

import inf112.fiasko.roborally.element_properties.GameTexture;
import inf112.fiasko.roborally.objects.DrawableObject;
import inf112.fiasko.roborally.objects.IDrawableObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a game which is drawable using libgdx
 */
public class Game implements IDrawableGame {
    private final int TILE_SIZE = 64;
    private final int TILE_NUMBER = 12;
    private final int BOARD_WIDTH = TILE_SIZE * TILE_NUMBER;
    private final int BOARD_HEIGHT = TILE_SIZE * TILE_NUMBER;

    @Override
    public int getWidth() {
        return BOARD_WIDTH;
    }

    @Override
    public int getHeight() {
        return BOARD_HEIGHT;
    }

    @Override
    public List<IDrawableObject> getObjectsToDraw() {
        List<IDrawableObject> list = new ArrayList<>();
        for (int i = 0; i < TILE_NUMBER; i++) {
            for (int j = 0; j < TILE_NUMBER; j++) {
                DrawableObject tile = new DrawableObject(GameTexture.TILE, i * TILE_SIZE, j * TILE_SIZE);
                list.add(tile);
            }
        }
        DrawableObject robot = new DrawableObject(GameTexture.ROBOT, TILE_SIZE, TILE_SIZE);
        list.add(robot);
        return list;
    }
}
