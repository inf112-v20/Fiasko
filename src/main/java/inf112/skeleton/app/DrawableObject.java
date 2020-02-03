package inf112.skeleton.app;

public class DrawableObject implements IDrawableObject {

    private int xPos;
    private int yPos;
    private int width = 64;
    private int height = 64;
    private int rotation = 0;
    private GameTexture texture;
    private boolean flipX = false;
    private boolean flipY = false;

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
