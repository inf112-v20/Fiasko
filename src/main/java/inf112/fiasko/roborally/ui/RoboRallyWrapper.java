package inf112.fiasko.roborally.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import inf112.fiasko.roborally.networking.RoboRallyClient;
import inf112.fiasko.roborally.networking.RoboRallyServer;
import inf112.fiasko.roborally.objects.InteractableGame;
import inf112.fiasko.roborally.objects.RoboRallyGame;
import inf112.fiasko.roborally.utility.TextureConverterUtil;

/**
 * This class acts as a wrapper around the different screens of the game
 */
public class RoboRallyWrapper extends Game implements RoboRallyUI {
    public int networkPort = 54555;
    public SpriteBatch batch;
    public BitmapFont font;
    public ScreenManager screenManager;
    public RoboRallyGame roboRallyGame;
    public RoboRallyServer server;
    public RoboRallyClient client;
    public boolean shouldHurry = false;
    public boolean isTesting = false;

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
        for (Disposable disposable : TextureConverterUtil.getDisposableElements()) {
            disposable.dispose();
        }
    }

    @Override
    public InteractableGame getGame() {
        return roboRallyGame;
    }

    @Override
    public void setGame(RoboRallyGame game) {
        this.roboRallyGame = game;
    }

    @Override
    public void quit(String string) {
        Gdx.app.error("Critical", string);
        quit();
    }

    @Override
    public RoboRallyServer getServer() {
        return server;
    }

    @Override
    public void setShouldHurry(boolean shouldHurry) {
        this.shouldHurry = shouldHurry;
    }

    /**
     * Quits the game
     */
    public void quit() {
        Gdx.app.exit();
    }

    @Override
    public boolean isTesting() {
        return this.isTesting;
    }
}
