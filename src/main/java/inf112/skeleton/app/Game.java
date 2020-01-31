package inf112.skeleton.app;

import java.util.List;

/**
 * This class represent a game which is drawable using libgdx
 */
public class Game implements IDrawableGame {
    private final int TILE_SIZE = 64;
    private final int TILE_NUMBER = 12;
    private final int BOARD_WIDTH = TILE_SIZE * TILE_NUMBER;
    private final int BOARD_HEIGHT = TILE_SIZE * TILE_NUMBER;



    /**
     * Instantiates a new Game object
     */
    public Game () {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public List<IDrawableObject> objectsToRender() {
        return null;
    }
}
