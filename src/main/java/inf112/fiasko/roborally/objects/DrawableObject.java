package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.abstractions.GameTexture;

/**
 * This class represents an object that can be drawn using libgdx
 */
public class DrawableObject implements IDrawableObject {
    private int xPos;
    private int yPos;
    private int width = 64;
    private int height = 64;
    private int rotation = 0;
    private GameTexture texture;
    private boolean flipX = false;
    private boolean flipY = false;

    /**
     * Initializes a drawable object
     * @param xPos The pixel to start drawing on for the x axis
     * @param yPos The pixel to start drawing on for the y axis
     * @param texture The texture to use for drawing the element
     * @param rotation The amount of degrees to rotate the element counterclockwise
     * @param width The width of the element
     * @param height The height of the element
     * @param flipX Whether to flip/mirror the element over the x axis
     * @param flipY Whether to flip/mirror the element over the y axis
     */
    public DrawableObject(int xPos, int yPos, GameTexture texture, int rotation, int width, int height, boolean flipX, boolean flipY) {
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
     * @param xPos The pixel to start drawing on for the x axis
     * @param yPos The pixel to start drawing on for the y axis
     * @param texture The texture to use for drawing the element
     */
    public DrawableObject(int xPos, int yPos, GameTexture texture) {
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
