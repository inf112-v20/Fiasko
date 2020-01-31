package inf112.skeleton.app;

/**
 * This interface describes an object drawable using libgdx
 */
public interface IDrawableObject {

    /**
     * Gets the texture to use for drawing the object
     * @return The texture of the object
     */
    GameTexture getTexture();

    /**
     * Gets the x position the object should be drawn on
     *
     * The x position should be in terms of the actual pixel position on the rendered game, not the position according
     * to the game tile. E.g. (128,64) not (2,1).
     *
     * @return An x position libgdx
     */
    int getXPosition();

    /**
     * Gets the y position the object should be drawn on
     *
     * The y position should be in terms of the actual pixel position on the rendered game, not the position according
     * to the game tile. E.g. (128,64) not (2,1).
     *
     * @return An x position libgdx
     */
    int getYPosition();

    /**
     * Gets the width of the object
     * @return A positive integer
     */
    int getWidth();

    /**
     * Gets the height of the object
     * @return A positive integer
     */
    int getHeight();

    /**
     * Gets the number of degrees to rotate the texture counterclockwise when rendering
     * @return An integer
     */
    int getRotation();

    /**
     * Whether to flip the texture on the x-axis when rendering
     * @return True if the texture is to be flipped. False otherwise
     */
    boolean flipX();

    /**
     * Whether to flip the texture on the y-axis when rendering
     * @return True if the texture is to be flipped. False otherwise
     */
    boolean flipY();

}
