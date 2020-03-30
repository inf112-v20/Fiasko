package inf112.fiasko.roborally.game_wrapper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.fiasko.roborally.networking.RoboRallyClient;
import inf112.fiasko.roborally.networking.RoboRallyServer;
import inf112.fiasko.roborally.networking.SomeResponse;
import inf112.fiasko.roborally.objects.IDrawableGame;

import java.io.IOException;

public class RoboRallyWrapper extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public ScreenManager screenManager;
    public IDrawableGame roboRallyGame;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("assets/Montserrat-Regular.fnt"));
        this.screenManager = new ScreenManager();
        this.setScreen(screenManager.getMainMenuScreen(this));
        try {
            RoboRallyServer server = new RoboRallyServer();
            RoboRallyClient client = new RoboRallyClient();
            SomeResponse response = new SomeResponse();
            response.text = "ÆÆÆÆÆÆÆÆÆ";
            server.sendToAllClients(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dispose() {
        batch.dispose();
    }
}
