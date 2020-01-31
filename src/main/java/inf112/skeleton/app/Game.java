package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.List;

/**
 * This class represent a game which is drawable using libgdx
 */
public class Game implements IDrawableGame {
    private final int TILE_SIZE = 64;
    private final int TILE_NUMBER = 12;
    private final int BOARD_WIDTH = TILE_SIZE * TILE_NUMBER;
    private final int BOARD_HEIGHT = TILE_SIZE * TILE_NUMBER;

    private Texture robotTexture;
    private Texture tileTexture;
    private Texture walledTileTexture;
    private Texture doublyWalledTileTexture;
    private Texture slowTransportBandTexture;

    /**
     * Instantiates a new Game object
     */
    public Game () {
        //Loads some textures
        robotTexture = new Texture(Gdx.files.internal("assets/Robot.png"));
        tileTexture = new Texture(Gdx.files.internal("assets/Tile.png"));
        walledTileTexture = new Texture(Gdx.files.internal("assets/WalledTile.png"));
        doublyWalledTileTexture = new Texture(Gdx.files.internal("assets/DoublyWalledTile.png"));
        slowTransportBandTexture = new Texture(Gdx.files.internal("assets/TransportBandSlow.png"));
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
