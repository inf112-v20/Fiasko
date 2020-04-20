package inf112.fiasko.roborally.gamewrapper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.fiasko.roborally.networking.RoboRallyClient;
import inf112.fiasko.roborally.networking.RoboRallyServer;
import inf112.fiasko.roborally.objects.RoboRallyGame;

/**
 * This class acts as a wrapper around the different screens of the game
 */
public class RoboRallyWrapper extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public ScreenManager screenManager;
    public RoboRallyGame roboRallyGame;
    public RoboRallyServer server;
    public RoboRallyClient client;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("assets/Montserrat-Regular.fnt"));
        this.screenManager = new ScreenManager();
        this.setScreen(screenManager.getStartMenuScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    /**
     * Quits the game after logging the input as an error
     *
     * @param string The error causing the game to quit
     */
    public void quit(String string) {
        Gdx.app.error("Critical", string);
        Gdx.app.exit();
    }

    /**
     * Quits the game
     */
    public void quit() {
        Gdx.app.exit();
    }
}
