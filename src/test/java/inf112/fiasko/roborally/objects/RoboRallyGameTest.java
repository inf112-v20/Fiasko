package inf112.fiasko.roborally.objects;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class RoboRallyGameTest {
    private IDrawableGame game;

    @Before
    public void setUp() {
        game = new RoboRallyGame(new ArrayList<>(),"Checkmate.txt",false, "Player1");
    }

    @Test
    public void gameWidthIsPositive() {
        assertTrue(game.getWidth() > 0);
    }

    @Test
    public void gameWidthIsMaximumFullHD() {
        assertTrue(game.getWidth() <= 1920);
    }

    @Test
    public void gameHeightIsPositive() {
        assertTrue(game.getHeight() > 0);
    }

    @Test
    public void gameHeightIsMaximumFullHD() {
        assertTrue(game.getHeight() <= 1080);
    }
}
