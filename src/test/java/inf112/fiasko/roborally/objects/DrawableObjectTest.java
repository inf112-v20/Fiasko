package inf112.fiasko.roborally.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.fiasko.roborally.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertFalse;

@RunWith(GdxTestRunner.class)
public class DrawableObjectTest {

    private static final Texture textureSheet = new Texture(Gdx.files.internal("assets/tiles.png"));
    private static final Texture robotTexture = new Texture(Gdx.files.internal("assets/Robot.png"));
    public static final TextureRegion TEXTURE_MIN_ARG = new TextureRegion(textureSheet, 4*300, 0, 300, 300);
    public static final TextureRegion TEXTURE_MAX_ARG = new TextureRegion(robotTexture, 0, 0, 64, 64);
    public static final int X_POSITION_MIN_ARG = 5;
    public static final int Y_POSITION_MIN_ARG = 8;
    public static final int X_POSITION_MAX_ARG = 6;
    public static final int Y_POSITION_MAX_ARG = 7;
    private final int WIDTH_MAX_ARG = 50;
    private final int HEIGHT_MAX_ARG = 60;
    private final int ROTATION_MAX_ARG = 90;
    private final boolean FLIP_X_MAX_ARG = true;
    private final boolean FLIP_Y_MAX_ARG = true;
    private DrawableObject drawableObjectMinimumArguments;
    private DrawableObject drawableObjectMaximumArguments;

    @Before
    public void setUp() {
        drawableObjectMinimumArguments = new DrawableObject(TEXTURE_MIN_ARG, X_POSITION_MIN_ARG, Y_POSITION_MIN_ARG);
        drawableObjectMaximumArguments = new DrawableObject(TEXTURE_MAX_ARG, X_POSITION_MAX_ARG, Y_POSITION_MAX_ARG,
                WIDTH_MAX_ARG, HEIGHT_MAX_ARG, ROTATION_MAX_ARG, FLIP_X_MAX_ARG, FLIP_Y_MAX_ARG);
    }

    @Test
    public void getTextureMinArg() {
        assertSame(TEXTURE_MIN_ARG, drawableObjectMinimumArguments.getTexture());
    }

    @Test
    public void getTextureMaxArg() {
        assertSame(TEXTURE_MAX_ARG, drawableObjectMaximumArguments.getTexture());
    }

    @Test
    public void getXPositionMinArg() {
        assertEquals(X_POSITION_MIN_ARG, drawableObjectMinimumArguments.getXPosition());
    }

    @Test
    public void getXPositionMaxArg() {
        assertEquals(X_POSITION_MAX_ARG, drawableObjectMaximumArguments.getXPosition());
    }

    @Test
    public void getYPositionMinArg() {
        assertEquals(Y_POSITION_MIN_ARG, drawableObjectMinimumArguments.getYPosition());
    }

    @Test
    public void getYPositionMaxArg() {
        assertEquals(Y_POSITION_MAX_ARG, drawableObjectMaximumArguments.getYPosition());
    }


    @Test
    public void getWidthMinArg() {
        assertEquals(64, drawableObjectMinimumArguments.getWidth());
    }

    @Test
    public void getWidthMaxArg() {
        assertEquals(WIDTH_MAX_ARG, drawableObjectMaximumArguments.getWidth());
    }

    @Test
    public void getHeightMinArg() {
        assertEquals(64, drawableObjectMinimumArguments.getHeight());
    }

    @Test
    public void getHeightMaxArg() {
        assertEquals(HEIGHT_MAX_ARG, drawableObjectMaximumArguments.getHeight());
    }

    @Test
    public void getRotationMinArg() {
        assertEquals(0, drawableObjectMinimumArguments.getRotation());
    }

    @Test
    public void getRotationMaxArg() {
        assertEquals(ROTATION_MAX_ARG, drawableObjectMaximumArguments.getRotation());
    }

    @Test
    public void flipXMinArg() {
        assertFalse(drawableObjectMinimumArguments.flipX());
    }

    @Test
    public void flipXMaxArg() {
        assertEquals(FLIP_X_MAX_ARG, drawableObjectMaximumArguments.flipX());
    }

    @Test
    public void flipYMinArg() {
        assertFalse(drawableObjectMinimumArguments.flipY());
    }

    @Test
    public void flipYMaxArg() {
        assertEquals(FLIP_Y_MAX_ARG, drawableObjectMaximumArguments.flipY());
    }
}