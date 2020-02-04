package inf112.fiasko.roborally;

import inf112.fiasko.roborally.objects.DrawableObject;
import inf112.fiasko.roborally.abstractions.GameTexture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DrawableObjectTest {

    private DrawableObject testMinArg;
    private DrawableObject testMaxArg;

    @Before
    public void setUp() {
        testMinArg = new DrawableObject(5,5, GameTexture.SLOW_TRANSPORT_BAND);
        testMaxArg = new DrawableObject(6, 6, GameTexture.ROBOT, 90, 50, 50, true, true);
    }

    @Test
    public void getTextureMinArg() {
        assertSame(GameTexture.SLOW_TRANSPORT_BAND, testMinArg.getTexture());
    }

    @Test
    public void getTextureMaxArg() {
        assertSame(GameTexture.ROBOT, testMaxArg.getTexture());
    }

    @Test
    public void getXPositionMinArg() {
        assertEquals(5, testMinArg.getXPosition());
    }

    @Test
    public void getXPositionMaxArg() {
        assertEquals(6, testMaxArg.getXPosition());
    }

    @Test
    public void getYPositionMinArg() {
        assertEquals(5, testMinArg.getYPosition());
    }

    @Test
    public void getYPositionMaxArg() {
        assertEquals(6, testMaxArg.getYPosition());
    }


    @Test
    public void getWidthMinArg() {
        assertEquals(64, testMinArg.getWidth());
    }

    @Test
    public void getWidthMaxArg() {
        assertEquals(50, testMaxArg.getWidth());
    }

    @Test
    public void getHeightMinArg() {
        assertEquals(64, testMinArg.getHeight());
    }

    @Test
    public void getHeightMaxArg() {
        assertEquals(50, testMaxArg.getHeight());
    }


    @Test
    public void getRotationMinArg() {
        assertEquals(0, testMinArg.getRotation());
    }

    @Test
    public void getRotationMaxArg() {
        assertEquals(90, testMaxArg.getRotation());
    }

    @Test
    public void flipXMinArg() {
        assertFalse(testMinArg.flipX());
    }

    @Test
    public void flipXMaxArg() {
        assertTrue(testMaxArg.flipX());
    }

    @Test
    public void flipYMinArg() {
        assertFalse(testMinArg.flipY());
    }

    @Test
    public void flipYMaxArg() {
        assertTrue(testMaxArg.flipY());
    }
}