package inf112.fiasko.roborally.gamewrapper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.fiasko.roborally.networking.RoboRallyClient;
import inf112.fiasko.roborally.networking.RoboRallyServer;
import inf112.fiasko.roborally.objects.IRoboRallyGame;

public class RoboRallyWrapper extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public ScreenManager screenManager;
    public IRoboRallyGame roboRallyGame;
    public RoboRallyServer server;
    public RoboRallyClient client;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("assets/Montserrat-Regular.fnt"));
        this.screenManager = new ScreenManager();
        this.setScreen(screenManager.getStartMenuScreen(this));
    }

    public void dispose() {
        batch.dispose();
    }
}
