package inf112.fiasko.roborally.game;

import inf112.fiasko.roborally.abstractions.GameTexture;
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
    public List<IDrawableObject> objectsToRender() {
        List<IDrawableObject> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                DrawableObject tileObj = new DrawableObject(i * 64, j * 64, GameTexture.TILE);
                list.add(tileObj);
            }
        }
        DrawableObject roboObj = new DrawableObject(128,128, GameTexture.ROBOT);
        list.add(roboObj);

        return list;
    }
}
