package inf112.fiasko.roborally.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.fiasko.roborally.element_properties.Direction;
import inf112.fiasko.roborally.element_properties.TileType;
import inf112.fiasko.roborally.objects.Tile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * This class can convert an element to an appropriate texture
 */
public final class TextureConverterUtil {
    private static final Texture textureSheet = new Texture(Gdx.files.internal("assets/tiles.png"));
    private static Map<TileType, TextureConverterContainer> tileSheetTileTextureMappings;
    private static Map<TileType, Boolean> tileSheetTileHasRotatedTextureMappings;

    private TextureConverterUtil() {}

    /**
     * Gets the texture representing the tile
     * @param tile The tile to draw
     * @return The texture to draw
     */
    public static TextureRegion convertElement(Tile tile) {
        if (tileSheetTileTextureMappings == null) {
            try {
                loadTileMappings();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Direction direction = tile.getDirection();
        TextureConverterContainer converterContainer = tileSheetTileTextureMappings.get(tile.getTileType());
        if (converterContainer != null) {
            return getDirectionalTextureRegion(direction, converterContainer.getXNorth(),
                    converterContainer.getYNorth(), converterContainer.getXEast(), converterContainer.getYEast(),
                    converterContainer.getXSouth(), converterContainer.getYSouth(), converterContainer.getXWest(),
                    converterContainer.getYWest());
        }
        throw new IllegalArgumentException("Invalid or unimplemented tile type encountered");
    }

    /**
     * Checks whether a tile has textures for different rotations
     *
     * For a tile without a rotated texture, the texture needs to be rotated when rendering.
     *
     * @param tile The tile to check
     * @return True if rotated versions of the texture exists. False otherwise
     */
    public static boolean hasRotatedTexture(Tile tile) {
        if (tileSheetTileTextureMappings == null) {
            try {
                loadTileMappings();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tileSheetTileHasRotatedTextureMappings.get(tile.getTileType());
    }

    /**
     * Loads mappings between a tile and texture
     *
     * Loads both information about mapping from a tile to a texture converter container and information about mapping
     * from a tile to whether the tile has a rotated version of each texture
     *
     * @throws IOException If the mapping file can't be properly read
     */
    private static synchronized void loadTileMappings() throws IOException {
        tileSheetTileTextureMappings = new HashMap<>();
        tileSheetTileHasRotatedTextureMappings = new HashMap<>();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream fileStream = classloader.getResourceAsStream("texture_sheet_tile_mapping.txt");
        if (fileStream == null) {
            throw new FileNotFoundException("Unable to load texture sheet mapping file.");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parameters = line.split(" ");
            TileType type = TileType.valueOf(parameters[0]);
            storeTextMappingInMap(parameters, type, tileSheetTileTextureMappings,
                    tileSheetTileHasRotatedTextureMappings);
        }
    }

    /**
     * Reads one line of texture mapping and puts it into the correct maps
     * @param parameters The parameters describing the texture mapping of the element
     * @param mapKey The key to store in the map
     * @param textureMapping The map containing texture mappings
     * @param hasRotatedTextureMapping The map containing whether an element has rotated textures or not
     * @param <K> The type of element that will be used for map keys
     */
    private static synchronized <K> void storeTextMappingInMap(String[] parameters, K mapKey,
                                                               Map<K,TextureConverterContainer> textureMapping,
                                                               Map<K,Boolean> hasRotatedTextureMapping) {
        TextureConverterContainer container;
        int xNorth = Integer.parseInt(parameters[1]);
        int yNorth = Integer.parseInt(parameters[2]);
        if (parameters.length == 3) {
            container = new TextureConverterContainer(xNorth, yNorth, xNorth, yNorth,
                    xNorth, yNorth, xNorth, yNorth);
            hasRotatedTextureMapping.put(mapKey, false);
        } else {
            int xEast = Integer.parseInt(parameters[3]);
            int yEast = Integer.parseInt(parameters[4]);
            int xSouth = Integer.parseInt(parameters[5]);
            int ySouth = Integer.parseInt(parameters[6]);
            int xWest = Integer.parseInt(parameters[7]);
            int yWest = Integer.parseInt(parameters[8]);
            container = new TextureConverterContainer(xNorth, yNorth, xEast, yEast,
                    xSouth, ySouth, xWest, yWest);
            hasRotatedTextureMapping.put(mapKey, true);
        }
        textureMapping.put(mapKey, container);
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
     * This class serves as a temporary container for texture region coordinates
     */
    private static class TextureConverterContainer {
        private final int xNorth;
        private final int yNorth;
        private final int xEast;
        private final int yEast;
        private final int xSouth;
        private final int ySouth;
        private final int xWest;
        private final int yWest;

        TextureConverterContainer(int xNorth, int yNorth, int xEast, int yEast, int xSouth, int ySouth,
                                  int xWest, int yWest) {
            this.xNorth = xNorth;
            this.yNorth = yNorth;
            this.xEast = xEast;
            this.yEast = yEast;
            this.xSouth = xSouth;
            this.ySouth = ySouth;
            this.xWest = xWest;
            this.yWest = yWest;
        }

        public int getXNorth() {
            return xNorth;
        }

        public int getYNorth() {
            return yNorth;
        }

        public int getXEast() {
            return xEast;
        }

        public int getYEast() {
            return yEast;
        }

        public int getXSouth() {
            return xSouth;
        }

        public int getYSouth() {
            return ySouth;
        }

        public int getXWest() {
            return xWest;
        }

        public int getYWest() {
            return yWest;
        }
    }
}
