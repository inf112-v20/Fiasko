package inf112.fiasko.roborally;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.fiasko.roborally.objects.Tile;

public class TextureConverter {
    private Texture textureSheet = new Texture(Gdx.files.internal("assets/tiles.png"));

    private TextureConverter() {}

    /**
     * Gets the texture representing the tile
     * @param tile The tile to draw
     * @return The texture to draw
     */
    public TextureRegion convertElement(Tile tile) {
        final int tileTextureHeight = 300, tileTextureWidth = 300;
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
                switch (tile.getDirection()) {
                    case NORTH:
                        return getTextureOnSheet(0, 6);
                    case EAST:
                        return getTextureOnSheet(3, 6);
                    case SOUTH:
                        return getTextureOnSheet(1, 6);
                    case WEST:
                        return getTextureOnSheet(2, 6);
                    default:
                        throw new IllegalArgumentException("Invalid direction for slow transport band encountered");
                }
            case TRANSPORT_BAND_SLOW_RIGHT:
                switch (tile.getDirection()) {
                    case NORTH:
                        return getTextureOnSheet(2, 5);
                    case EAST:
                        return getTextureOnSheet(2, 4);
                    case SOUTH:
                        return getTextureOnSheet(3, 4);
                    case WEST:
                        return getTextureOnSheet(3, 5);
                    default:
                        throw new IllegalArgumentException("Invalid direction for slow transport band encountered");
                }
            case TRANSPORT_BAND_SLOW_LEFT:
                switch (tile.getDirection()) {
                    case NORTH:
                        return getTextureOnSheet(1, 5);
                    case EAST:
                        return getTextureOnSheet(0, 5);
                    case SOUTH:
                        return getTextureOnSheet(0, 4);
                    case WEST:
                        return getTextureOnSheet(1, 4);
                    default:
                        throw new IllegalArgumentException("Invalid direction for slow transport band encountered");
                }
            case TRANSPORT_BAND_SLOW_SIDE_ENTRANCES:
                switch (tile.getDirection()) {
                    case NORTH:
                        return getTextureOnSheet(4, 8);
                    case EAST:
                        return getTextureOnSheet(0, 5);
                    case SOUTH:
                        return new TextureRegion(textureSheet, 0, 4*tileTextureHeight,
                                tileTextureWidth, tileTextureHeight);
                    case WEST:
                        return new TextureRegion(textureSheet, tileTextureWidth, 4*tileTextureHeight,
                                tileTextureWidth, tileTextureHeight);
                    default:
                        throw new IllegalArgumentException("Invalid direction for slow transport band encountered");
                }
            case TRANSPORT_BAND_SLOW_SIDE_ENTRANCE_RIGHT:
                switch (tile.getDirection()) {
                    case NORTH:
                        return getTextureOnSheet(0, 8);
                    case EAST:
                        return getTextureOnSheet(1, 8);
                    case SOUTH:
                        return getTextureOnSheet(2, 8);
                    case WEST:
                        return getTextureOnSheet(3, 8);
                    default:
                        throw new IllegalArgumentException("Invalid direction for slow transport band encountered");
                }
            case TRANSPORT_BAND_SLOW_SIDE_ENTRANCE_LEFT:
                switch (tile.getDirection()) {
                    case NORTH:
                        return getTextureOnSheet(0,7);
                    case EAST:
                        return getTextureOnSheet(1, 7);
                    case SOUTH:
                        return getTextureOnSheet(2, 7);
                    case WEST:
                        return getTextureOnSheet(3, 7);
                    default:
                        throw new IllegalArgumentException("Invalid direction for slow transport band encountered");
                }
            case TRANSPORT_BAND_FAST:
                switch (tile.getDirection()) {
                    case NORTH:
                        return getTextureOnSheet(4,1);
                    case EAST:
                        return getTextureOnSheet(5, 1);
                    case SOUTH:
                        return getTextureOnSheet(4, 2);
                    case WEST:
                        return getTextureOnSheet(5, 2);
                    default:
                        throw new IllegalArgumentException("Invalid direction for slow transport band encountered");
                }
            case TRANSPORT_BAND_FAST_RIGHT:
                switch (tile.getDirection()) {
                    case NORTH:
                        return getTextureOnSheet(2,3);
                    case EAST:
                        return getTextureOnSheet(2, 2);
                    case SOUTH:
                        return getTextureOnSheet(3, 2);
                    case WEST:
                        return getTextureOnSheet(3, 3);
                    default:
                        throw new IllegalArgumentException("Invalid direction for slow transport band encountered");
                }
            case TRANSPORT_BAND_FAST_LEFT:
                switch (tile.getDirection()) {
                    case NORTH:
                        return getTextureOnSheet(1,3);
                    case EAST:
                        return getTextureOnSheet(0, 3);
                    case SOUTH:
                        return getTextureOnSheet(3, 2);
                    case WEST:
                        return getTextureOnSheet(3, 3);
                    default:
                        throw new IllegalArgumentException("Invalid direction for slow transport band encountered");
                }
            default:
                throw new IllegalArgumentException("Invalid or unimplemented tile type encountered");
        }
    }

    /**
     * Gets a texture on the main tiles texture sheet
     * @param x The relative x coordinate on the sheet
     * @param y The relative y coordinate on the sheet
     * @return The texture region containing the texture
     */
    private TextureRegion getTextureOnSheet(int x, int y) {
        final int tileTextureHeight = 300, tileTextureWidth = 300;
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
    public boolean hasRotatedTexture(Tile tile) {
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
