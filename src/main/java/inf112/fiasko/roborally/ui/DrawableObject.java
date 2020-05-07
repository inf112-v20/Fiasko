package inf112.fiasko.roborally.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This class represents an object that can be drawn using libgdx
 */
public class DrawableObject {
    private final TextureRegion texture;
    private final int xPos;
    private final int yPos;
    private int width = 64;
    private int height = 64;
    private int rotation = 0;
    private boolean flipX = false;
    private boolean flipY = false;

    /**
     * Initializes a drawable object
     *
     * @param texture  The texture to use for drawing the element
     * @param xPos     The pixel to start drawing on for the x axis
     * @param yPos     The pixel to start drawing on for the y axis
     * @param width    The width of the element
     * @param height   The height of the element
     * @param rotation The amount of degrees to rotate the element counterclockwise
     * @param flipX    Whether to flip/mirror the element over the x axis
     * @param flipY    Whether to flip/mirror the element over the y axis
     */
    public DrawableObject(TextureRegion texture, int xPos, int yPos, int width, int height, int rotation, boolean flipX,
                          boolean flipY) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rotation = rotation;
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.flipX = flipX;
        this.flipY = flipY;
    }

    /**
     * Initializes a drawable object
     *
     * @param texture  The texture to use for drawing the element
     * @param xPos     The pixel to start drawing on for the x axis
     * @param yPos     The pixel to start drawing on for the y axis
     * @param width    The width of the element
     * @param height   The height of the element
     * @param rotation The amount of degrees to rotate the element counterclockwise
     */
    public DrawableObject(TextureRegion texture, int xPos, int yPos, int width, int height, int rotation) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rotation = rotation;
        this.texture = texture;
        this.width = width;
        this.height = height;
    }

    /**
     * Initializes a new drawable object
     *
     * @param texture The texture to use for drawing the element
     * @param xPos    The pixel to start drawing on for the x axis
     * @param yPos    The pixel to start drawing on for the y axis
     */
    public DrawableObject(TextureRegion texture, int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.texture = texture;
    }

    /**
     * Gets the texture to use for drawing the object
     *
     * @return The texture of the object
     */
    public TextureRegion getTexture() {
        return texture;
    }

    /**
     * Gets the x position the object should be drawn on
     *
     * <p>The x position should be in terms of the actual pixel position on the rendered game, not the position according
     * to the game tile. E.g. (128,64) not (2,1).</p>
     *
     * @return An x position libgdx
     */
    public int getXPosition() {
        return xPos;
    }

    /**
     * Gets the y position the object should be drawn on
     *
     * <p>The y position should be in terms of the actual pixel position on the rendered game, not the position according
     * to the game tile. E.g. (128,64) not (2,1).</p>
     *
     * @return An x position libgdx
     */
    public int getYPosition() {
        return yPos;
    }

    /**
     * Gets the width of the object
     *
     * @return A positive integer
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the object
     *
     * @return A positive integer
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the number of degrees to rotate the texture counterclockwise when rendering
     *
     * @return An integer
     */
    public int getRotation() {
        return rotation;
    }

    /**
     * Whether to flip the texture on the x-axis when rendering
     *
     * @return True if the texture is to be flipped. False otherwise
     */
    public boolean flipX() {
        return flipX;
    }

    /**
     * Whether to flip the texture on the y-axis when rendering
     *
     * @return True if the texture is to be flipped. False otherwise
     */
    public boolean flipY() {
        return flipY;
    }
}
