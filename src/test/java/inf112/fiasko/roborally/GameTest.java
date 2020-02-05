package inf112.fiasko.roborally;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import inf112.fiasko.roborally.game.Game;
import inf112.fiasko.roborally.game.IDrawableGame;
import org.junit.Before;
import org.junit.Test;

public class GameTest {
    private IDrawableGame game;

    @Before
    public void setUp() {
        game = new Game();
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
        assertTrue(game.getWidth() > 0);
    }

    @Test
    public void gameHeightIsMaximumFullHD() {
        assertTrue(game.getWidth() <= 1080);
    }

    @Test
    public void getObjectsToDrawReturnsNonemptyList() {
        assertFalse(game.getObjectsToDraw().isEmpty());
    }
}
