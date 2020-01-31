package inf112.skeleton.app;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class GameTest {
    IDrawableGame game;

    @Before
    public void setUp() {
        game = new Game();
    }

    @Test
    public void gameWidthPositiveTest() {
        assertTrue(game.getWidth() > 0);
    }

    @Test
    public void gameWidthMaximumFullHDTest() {
        assertTrue(game.getWidth() <= 1920);
    }

    @Test
    public void gameHeightPositiveTest() {
        assertTrue(game.getWidth() > 0);
    }

    @Test
    public void gameHeightMaximumFullHDTest() {
        assertTrue(game.getWidth() <= 1080);
    }

    @Test
    public void getObjectsToRenderTest() {
        assertFalse(game.objectsToRender().isEmpty());
    }
}
