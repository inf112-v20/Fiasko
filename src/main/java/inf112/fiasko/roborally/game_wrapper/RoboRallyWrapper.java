package inf112.fiasko.roborally.game_wrapper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.fiasko.roborally.objects.IDrawableGame;

public class RoboRallyWrapper extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public ScreenManager screenManager;
    public IDrawableGame roboRallyGame;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.screenManager = new ScreenManager();
        this.setScreen(screenManager.getCardChoiceScreen(this));
    }

    public void dispose() {
        batch.dispose();
    }
}
