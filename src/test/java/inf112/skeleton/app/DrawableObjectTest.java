package inf112.skeleton.app;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DrawableObjectTest {

    DrawableObject testMinArg;
    DrawableObject testMaxArg;

    @Before
    public void setUp() throws Exception {
        testMinArg = new DrawableObject(5,5, GameTexture.SLOW_TRANSPORT_BAND);
        testMaxArg = new DrawableObject(6, 6, GameTexture.ROBOT, 90, 50, 50, true, true);
    }

    @Test
    public void getTextureMinArg() {
        assertTrue(GameTexture.SLOW_TRANSPORT_BAND == testMinArg.getTexture());
    }

    @Test
    public void getTextureMaxArg() {
        assertTrue(GameTexture.ROBOT == testMaxArg.getTexture());
    }

    @Test
    public void getXPositionMinArg() {
        assertTrue(5 == testMinArg.getXPosition());
    }

    @Test
    public void getXPositionMaxArg() {
        assertTrue(6 == testMaxArg.getXPosition());
    }

    @Test
    public void getYPositionMinArg() {
        assertTrue(5 == testMinArg.getYPosition());
    }

    @Test
    public void getYPositionMaxArg() {
        assertTrue(6 == testMaxArg.getYPosition());
    }


    @Test
    public void getWidthMinArg() {
        assertTrue(64 == testMinArg.getWidth());
    }

    @Test
    public void getWidthMaxArg() {
        assertEquals(50, testMaxArg.getWidth());
    }

    @Test
    public void getHeightMinArg() {
        assertTrue(64 == testMinArg.getHeight());
    }

    @Test
    public void getHeightMaxArg() {
        assertTrue(50 == testMaxArg.getHeight());
    }


    @Test
    public void getRotationMinArg() {
        assertTrue(0 == testMinArg.getRotation());
    }

    @Test
    public void getRotationMaxArg() {
        assertTrue(90 == testMaxArg.getRotation());
    }

    @Test
    public void flipXMinArg() {
        assertTrue(false == testMinArg.flipX());
    }

    @Test
    public void flipXMaxArg() {
        assertTrue(true == testMaxArg.flipX());
    }

    @Test
    public void flipYMinArg() {
        assertTrue(false == testMinArg.flipY());
    }

    @Test
    public void flipYMaxArg() {
        assertTrue(true == testMaxArg.flipY());
    }
}