package inf112.fiasko.roborally;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.fiasko.roborally.abstractions.GameTexture;
import inf112.fiasko.roborally.game.Game;
import inf112.fiasko.roborally.game.IDrawableGame;
import inf112.fiasko.roborally.objects.IDrawableObject;

/**
 * This class renders a game using libgdx
 */
public class GameLauncher extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private IDrawableGame game;

    private Texture robotTexture;
    private Texture tileTexture;
    private Texture walledTileTexture;
    private Texture doublyWalledTileTexture;
    private Texture slowTransportBandTexture;

    @Override
    public void create() {
        //Loads some textures
        robotTexture = new Texture(Gdx.files.internal("assets/Robot.png"));
        tileTexture = new Texture(Gdx.files.internal("assets/Tile.png"));
        walledTileTexture = new Texture(Gdx.files.internal("assets/WalledTile.png"));
        doublyWalledTileTexture = new Texture(Gdx.files.internal("assets/DoublyWalledTile.png"));
        slowTransportBandTexture = new Texture(Gdx.files.internal("assets/TransportBandSlow.png"));

        game = new Game();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.getWidth(), game.getHeight());
        batch = new SpriteBatch();
    }

    /**
     * Renders all textures necessary to display a game
     */
    public void render() {
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //Renders all elements the game wants to render
        for (IDrawableObject object : game.objectsToRender()) {
            Texture objectTexture = gameTextureToTexture(object.getTexture());
            batch.draw(objectTexture, object.getXPosition(), object.getYPosition(),
                    (float)object.getWidth()/2, (float)object.getHeight()/2, object.getWidth(),
                    object.getHeight(), 1, 1, object.getRotation(),
                    0, 0, objectTexture.getWidth(), objectTexture.getHeight(), object.flipX(),
                    object.flipY());
        }
        batch.end();
    }

    @Override
    public void dispose() {
        robotTexture.dispose();
        tileTexture.dispose();
        walledTileTexture.dispose();
        doublyWalledTileTexture.dispose();
        slowTransportBandTexture.dispose();
        batch.dispose();
    }

    /**
     * Turns a GameTexture element into a Texture element
     *
     * This is necessary to keep all libgdx logic in this class only. Otherwise, testing would be painful.
     *
     * @param gameTexture A GameTexture enum
     * @return A Gdx Texture
     */
    private Texture gameTextureToTexture(GameTexture gameTexture) {
        switch (gameTexture) {
            case ROBOT:
                return robotTexture;
            case TILE:
                return tileTexture;
            case WALLED_TILE:
                return walledTileTexture;
            case DOUBLY_WALLED_TILE:
                return doublyWalledTileTexture;
            case SLOW_TRANSPORT_BAND:
                return slowTransportBandTexture;
            default:
                throw new IllegalArgumentException("Non existing texture encountered.");
        }
    }
}