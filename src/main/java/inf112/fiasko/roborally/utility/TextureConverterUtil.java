package inf112.fiasko.roborally.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.fiasko.roborally.element_properties.Direction;
import inf112.fiasko.roborally.objects.Tile;

/**
 * This class can convert an element to an appropriate texture
 */
public final class TextureConverterUtil {
    private static final Texture textureSheet = new Texture(Gdx.files.internal("assets/tiles.png"));

    private TextureConverterUtil() {}

    /**
     * Gets the texture representing the tile
     * @param tile The tile to draw
     * @return The texture to draw
     */
    public static TextureRegion convertElement(Tile tile) {
        Direction direction = tile.getDirection();
        switch (tile.getTileType()) {
            case TILE:
                return getTextureOnSheet(4, 0);
            case HOLE:
                return getTextureOnSheet(5, 0);
            case COGWHEEL_RIGHT:
                return getTextureOnSheet(5, 6);
            case COGWHEEL_LEFT:
                return getTextureOnSheet(4, 6);
            case TRANSPORT_BAND_SLOW:
                return getDirectionalTextureRegion(direction, 0, 6, 3, 6, 1, 6, 2, 6);
            case TRANSPORT_BAND_SLOW_RIGHT:
                return getDirectionalTextureRegion(direction, 2, 5, 2, 4, 3, 4, 3, 5);
            case TRANSPORT_BAND_SLOW_LEFT:
                return getDirectionalTextureRegion(direction, 1, 5, 0, 5, 0, 4, 1, 4);
            case TRANSPORT_BAND_SLOW_SIDE_ENTRANCES:
                return getDirectionalTextureRegion(direction, 4, 8, 0, 5, 0, 4, 1, 4);
            case TRANSPORT_BAND_SLOW_SIDE_ENTRANCE_RIGHT:
                return getDirectionalTextureRegion(direction, 0, 8, 1, 8, 2, 8, 3, 8);
            case TRANSPORT_BAND_SLOW_SIDE_ENTRANCE_LEFT:
                return getDirectionalTextureRegion(direction, 0, 7, 1, 7, 2, 7, 3, 7);
            case TRANSPORT_BAND_FAST:
                return getDirectionalTextureRegion(direction, 4, 1, 5, 1, 4, 2, 5, 2);
            case TRANSPORT_BAND_FAST_RIGHT:
                return getDirectionalTextureRegion(direction, 2, 3, 2, 2, 3, 2, 3, 3);
            case TRANSPORT_BAND_FAST_LEFT:
                return getDirectionalTextureRegion(direction, 1, 3, 0, 3, 0, 2, 1, 2);
            case TRANSPORT_BAND_FAST_SIDE_ENTRANCES:
                return getDirectionalTextureRegion(direction, 3, 10, 0, 10, 1, 10, 2, 10);
            default:
                throw new IllegalArgumentException("Invalid or unimplemented tile type encountered");
        }
    }

    /**
     * Gets a texture region based on the direction of the tile
     * @param direction The direction the tile is facing
     * @param xNorth The relative x position on the texture sheet if the tile is facing north
     * @param yNorth The relative y position on the texture sheet if the tile is facing north
     * @param xEast The relative x position on the texture sheet if the tile is facing east
     * @param yEast The relative y position on the texture sheet if the tile is facing east
     * @param xSouth The relative x position on the texture sheet if the tile is facing south
     * @param ySouth The relative y position on the texture sheet if the tile is facing south
     * @param xWest The relative x position on the texture sheet if the tile is facing west
     * @param yWest The relative y position on the texture sheet if the tile is facing west
     * @return The texture region containing the tile's texture
     */
    private static TextureRegion getDirectionalTextureRegion(Direction direction, int xNorth, int yNorth, int xEast,
                                                             int yEast, int xSouth, int ySouth, int xWest, int yWest) {
        String INVALID_DIRECTION_MESSAGE = "Invalid direction for tile encountered";
        switch (direction) {
            case NORTH:
                return getTextureOnSheet(xNorth, yNorth);
            case EAST:
                return getTextureOnSheet(xEast, yEast);
            case SOUTH:
                return getTextureOnSheet(xSouth, ySouth);
            case WEST:
                return getTextureOnSheet(xWest, yWest);
            default:
                throw new IllegalArgumentException(INVALID_DIRECTION_MESSAGE);
        }
    }

    /**
     * Gets a texture on the main tiles texture sheet
     * @param x The relative x coordinate on the sheet
     * @param y The relative y coordinate on the sheet
     * @return The texture region containing the texture
     */
    private static TextureRegion getTextureOnSheet(int x, int y) {
        final int tileTextureHeight = 300;
        final int tileTextureWidth = 300;
        return new TextureRegion(textureSheet, x*tileTextureWidth, y*tileTextureHeight,
                tileTextureWidth, tileTextureHeight);
    }

    /**
     * Checks whether a tile has textures for different rotations
     *
     * For a tile without a rotation texture, the texture needs to be rotated when rendering.
     *
     * @param tile The tile to check
     * @return True if rotated versions of the texture exists. False otherwise
     */
    public static boolean hasRotatedTexture(Tile tile) {
        switch (tile.getTileType()) {
            case TILE:
            case HOLE:
            case COGWHEEL_RIGHT:
            case COGWHEEL_LEFT:
            case FLAG_1:
            case FLAG_2:
            case FLAG_3:
            case FLAG_4:
            case WRENCH:
            case WRENCH_AND_HAMMER:
            case DEATH_TILE:
                return false;
            case TRANSPORT_BAND_SLOW:
            case TRANSPORT_BAND_SLOW_RIGHT:
            case TRANSPORT_BAND_SLOW_LEFT:
            case TRANSPORT_BAND_SLOW_SIDE_ENTRANCES:
            case TRANSPORT_BAND_SLOW_SIDE_ENTRANCE_LEFT:
            case TRANSPORT_BAND_SLOW_SIDE_ENTRANCE_RIGHT:
            case TRANSPORT_BAND_FAST:
            case TRANSPORT_BAND_FAST_RIGHT:
            case TRANSPORT_BAND_FAST_LEFT:
            case TRANSPORT_BAND_FAST_SIDE_ENTRANCES:
            case TRANSPORT_BAND_FAST_SIDE_ENTRANCE_LEFT:
            case TRANSPORT_BAND_FAST_SIDE_ENTRANCE_RIGHT:
                return true;
            default:
                throw new IllegalArgumentException("Invalid tile type encountered");
        }
    }
}
