package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.GameTexture;

/**
 * This class represents an object that can be drawn using libgdx
 */
public class DrawableObject implements IDrawableObject {
    private final GameTexture texture;
    private int xPos;
    private int yPos;
    private int width = 64;
    private int height = 64;
    private int rotation = 0;
    private boolean flipX = false;
    private boolean flipY = false;

    /**
     * Initializes a drawable object
     * @param texture The texture to use for drawing the element
     * @param xPos The pixel to start drawing on for the x axis
     * @param yPos The pixel to start drawing on for the y axis
     * @param width The width of the element
     * @param height The height of the element
     * @param rotation The amount of degrees to rotate the element counterclockwise
     * @param flipX Whether to flip/mirror the element over the x axis
     * @param flipY Whether to flip/mirror the element over the y axis
     */
    public DrawableObject(GameTexture texture, int xPos, int yPos, int width, int height, int rotation, boolean flipX,
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
     * Initializes a new drawable object
     * @param texture The texture to use for drawing the element
     * @param xPos The pixel to start drawing on for the x axis
     * @param yPos The pixel to start drawing on for the y axis
     */
    public DrawableObject(GameTexture texture, int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.texture = texture;
    }

    @Override
    public GameTexture getTexture() {
        return texture;
    }

    @Override
    public int getXPosition() {
        return xPos;
    }

    @Override
    public int getYPosition() {
        return yPos;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getRotation() {
        return rotation;
    }

    @Override
    public boolean flipX() {
        return flipX;
    }

    @Override
    public boolean flipY() {
        return flipY;
    }
}
