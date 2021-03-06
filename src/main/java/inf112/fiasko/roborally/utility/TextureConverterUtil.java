package inf112.fiasko.roborally.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import inf112.fiasko.roborally.objects.Particle;
import inf112.fiasko.roborally.objects.ProgrammingCard;
import inf112.fiasko.roborally.objects.Robot;
import inf112.fiasko.roborally.objects.Tile;
import inf112.fiasko.roborally.objects.Wall;
import inf112.fiasko.roborally.objects.properties.Direction;
import inf112.fiasko.roborally.objects.properties.ParticleType;
import inf112.fiasko.roborally.objects.properties.RobotID;
import inf112.fiasko.roborally.objects.properties.TileType;
import inf112.fiasko.roborally.objects.properties.WallType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class can convert an element to an appropriate texture
 */
public final class TextureConverterUtil {
    private static final Texture textureSheet = new Texture(Gdx.files.internal("assets/tiles.png"));
    private static final Texture robotsTexture = new Texture(Gdx.files.internal("assets/robots.png"));
    private static final Texture effectsTexture = new Texture(Gdx.files.internal("assets/effects.png"));
    private static final Texture programmingCardsTexture = new Texture(Gdx.files.internal("assets/programmingcards.png"));
    private static Map<TileType, TextureConverterContainer> tileSheetTileTextureMappings;
    private static Map<TileType, Boolean> tileSheetTileHasRotatedTextureMappings;
    private static Map<ParticleType, TextureConverterContainer> tileSheetParticleTextureMappings;
    private static Map<ParticleType, Boolean> tileSheetParticleHasRotatedTextureMappings;
    private static Map<WallType, TextureConverterContainer> tileSheetWallTextureMappings;
    private static Map<WallType, Boolean> tileSheetWallHasRotatedTextureMappings;

    private TextureConverterUtil() {
    }

    /**
     * Gets the texture representing the programming card
     *
     * @param card The card to draw
     * @return The texture to draw
     */
    public static TextureRegion convertElement(ProgrammingCard card) {
        switch (card.getAction()) {
            case MOVE_1:
                return new TextureRegion(programmingCardsTexture, 0, 0, 250, 400);
            case MOVE_2:
                return new TextureRegion(programmingCardsTexture, 250, 0, 250, 400);
            case MOVE_3:
                return new TextureRegion(programmingCardsTexture, 2 * 250, 0, 250, 400);
            case ROTATE_LEFT:
                return new TextureRegion(programmingCardsTexture, 3 * 250, 0, 250, 400);
            case ROTATE_RIGHT:
                return new TextureRegion(programmingCardsTexture, 4 * 250, 0, 250, 400);
            case BACK_UP:
                return new TextureRegion(programmingCardsTexture, 5 * 250, 0, 250, 400);
            case U_TURN:
                return new TextureRegion(programmingCardsTexture, 6 * 250, 0, 250, 400);
            default:
                throw new IllegalArgumentException("Invalid action encountered.");
        }
    }

    /**
     * Returns the texture to use to display damage tokens
     *
     * @return The damage token texture
     */
    public static TextureRegion getDamageTokenCriticalTexture() {
        return new TextureRegion(effectsTexture, 2 * 64, 0, 64, 64);
    }

    /**
     * Returns the texture to use to display damage tokens
     *
     * @return The damage token texture
     */
    public static TextureRegion getDamageTokenTexture() {
        return new TextureRegion(effectsTexture, 64, 0, 64, 64);
    }

    /**
     * Returns the texture to use to display lives
     *
     * @return The life texture
     */
    public static TextureRegion getLifeTexture() {
        return new TextureRegion(effectsTexture, 0, 0, 64, 64);
    }

    /**
     * Gets a list of all disposable elements which should be disposed when the software closes
     *
     * @return A list of disposable elements
     */
    public static List<Disposable> getDisposableElements() {
        List<Disposable> disposables = new ArrayList<>();
        disposables.add(textureSheet);
        disposables.add(robotsTexture);
        disposables.add(effectsTexture);
        return disposables;
    }

    /**
     * Gets the texture representing the tile
     *
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
        TextureConverterContainer converterContainer = tileSheetTileTextureMappings.get(tile.getType());
        if (converterContainer != null) {
            return getDirectionalTextureRegion(direction, converterContainer.getXNorth(),
                    converterContainer.getYNorth(), converterContainer.getXEast(), converterContainer.getYEast(),
                    converterContainer.getXSouth(), converterContainer.getYSouth(), converterContainer.getXWest(),
                    converterContainer.getYWest());
        }
        throw new IllegalArgumentException("Invalid or unimplemented tile type encountered");
    }

    /**
     * Gets the texture representing the particle
     *
     * @param particle The particle to draw
     * @return The texture to draw
     */
    public static TextureRegion convertElement(Particle particle) {
        if (tileSheetParticleTextureMappings == null) {
            try {
                loadParticleMappings();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Direction direction = particle.getDirection();
        TextureConverterContainer converterContainer = tileSheetParticleTextureMappings.get(particle.getType());
        if (converterContainer != null) {
            return getDirectionalTextureRegion(direction, converterContainer.getXNorth(),
                    converterContainer.getYNorth(), converterContainer.getXEast(), converterContainer.getYEast(),
                    converterContainer.getXSouth(), converterContainer.getYSouth(), converterContainer.getXWest(),
                    converterContainer.getYWest());
        }
        throw new IllegalArgumentException("Invalid or unimplemented particle type encountered");
    }

    /**
     * Gets the texture representing the tile
     *
     * @param wall The wall to draw
     * @return The texture to draw
     */
    public static TextureRegion convertElement(Wall wall) {
        if (tileSheetWallTextureMappings == null) {
            try {
                loadWallMappings();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (wall == null) {
            return null;
        }
        Direction direction = wall.getDirection();
        TextureConverterContainer converterContainer = tileSheetWallTextureMappings.get(wall.getType());
        if (converterContainer != null) {
            return getDirectionalTextureRegion(direction, converterContainer.getXNorth(),
                    converterContainer.getYNorth(), converterContainer.getXEast(), converterContainer.getYEast(),
                    converterContainer.getXSouth(), converterContainer.getYSouth(), converterContainer.getXWest(),
                    converterContainer.getYWest());
        }
        throw new IllegalArgumentException("Invalid or unimplemented tile type encountered");
    }

    /**
     * Gets the texture representing the robot
     *
     * @param robot The robot to draw
     * @return The texture to draw
     */
    public static TextureRegion convertElement(Robot robot) {
        return convertElement(robot.getRobotId());
    }

    /**
     * Gets the texture representing the robot id
     *
     * @param robotID The id of the robot to draw
     * @return The texture to draw
     */
    public static TextureRegion convertElement(RobotID robotID) {
        switch (robotID) {
            case ROBOT_1:
                return new TextureRegion(robotsTexture, 0, 0, 64, 64);
            case ROBOT_2:
                return new TextureRegion(robotsTexture, 64, 0, 64, 64);
            case ROBOT_3:
                return new TextureRegion(robotsTexture, 2 * 64, 0, 64, 64);
            case ROBOT_4:
                return new TextureRegion(robotsTexture, 3 * 64, 0, 64, 64);
            case ROBOT_5:
                return new TextureRegion(robotsTexture, 4 * 64, 0, 64, 64);
            case ROBOT_6:
                return new TextureRegion(robotsTexture, 5 * 64, 0, 64, 64);
            case ROBOT_7:
                return new TextureRegion(robotsTexture, 6 * 64, 0, 64, 64);
            case ROBOT_8:
                return new TextureRegion(robotsTexture, 7 * 64, 0, 64, 64);
            default:
                throw new IllegalArgumentException("Robot has no drawable texture.");
        }
    }

    /**
     * Checks whether a tile has textures for different rotations
     *
     * <p>For a tile without a rotated texture, the texture needs to be rotated when rendering.</p>
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
        return tileSheetTileHasRotatedTextureMappings.get(tile.getType());
    }

    /**
     * Checks whether a wall has textures for different rotations
     *
     * <p>For a wall without a rotated texture, the texture needs to be rotated when rendering.</p>
     *
     * @param wall The wall to check
     * @return True if rotated versions of the texture exists. False otherwise
     */
    public static boolean hasRotatedTexture(Wall wall) {
        if (tileSheetWallTextureMappings == null) {
            try {
                loadTileMappings();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tileSheetWallHasRotatedTextureMappings.get(wall.getType());
    }

    /**
     * Checks whether a particle has textures for different rotations
     *
     * <p>For a particle without a rotated texture, the texture needs to be rotated when rendering.</p>
     *
     * @param particle The particle to check
     * @return True if rotated versions of the texture exists. False otherwise
     */
    public static boolean hasRotatedTexture(Particle particle) {
        if (tileSheetTileTextureMappings == null) {
            try {
                loadParticleMappings();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tileSheetParticleHasRotatedTextureMappings.get(particle.getType());
    }

    /**
     * Loads mappings between a tile and texture
     *
     * <p>Loads both information about mapping from a tile to a texture converter container and information about mapping
     * from a tile to whether the tile has a rotated version of each texture</p>
     *
     * @throws IOException If the mapping file can't be properly read
     */
    private static synchronized void loadTileMappings() throws IOException {
        tileSheetTileTextureMappings = new HashMap<>();
        tileSheetTileHasRotatedTextureMappings = new HashMap<>();
        InputStream fileStream = ResourceUtil.getResourceAsInputStream("texture_sheet_tile_mapping.txt");
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
     * Loads mappings between a particle and a texture
     *
     * <p>Loads both information about mapping from a particle to a texture converter container and information about
     * mapping from a particle to whether the particle has a rotated version of each texture</p>
     *
     * @throws IOException If the mapping file can't be properly read
     */
    private static synchronized void loadParticleMappings() throws IOException {
        tileSheetParticleTextureMappings = new HashMap<>();
        tileSheetParticleHasRotatedTextureMappings = new HashMap<>();
        InputStream fileStream = ResourceUtil.getResourceAsInputStream("texture_sheet_particle_mapping.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parameters = line.split(" ");
            ParticleType type = ParticleType.valueOf(parameters[0]);
            storeTextMappingInMap(parameters, type, tileSheetParticleTextureMappings,
                    tileSheetParticleHasRotatedTextureMappings);
        }
    }

    /**
     * Loads mappings between a wall and texture
     *
     * @throws IOException If the mapping file can't be properly read
     */
    private static synchronized void loadWallMappings() throws IOException {
        tileSheetWallTextureMappings = new HashMap<>();
        tileSheetWallHasRotatedTextureMappings = new HashMap<>();
        InputStream fileStream = ResourceUtil.getResourceAsInputStream("texture_sheet_wall_mapping.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parameters = line.split(" ");
            WallType type = WallType.valueOf(parameters[0]);
            storeTextMappingInMap(parameters, type, tileSheetWallTextureMappings,
                    tileSheetWallHasRotatedTextureMappings);
        }
    }

    /**
     * Reads one line of texture mapping and puts it into the correct maps
     *
     * @param parameters               The parameters describing the texture mapping of the element
     * @param mapKey                   The key to store in the map
     * @param textureMapping           The map containing texture mappings
     * @param hasRotatedTextureMapping The map containing whether an element has rotated textures or not
     * @param <K>                      The type of element that will be used for map keys
     */
    private static synchronized <K> void storeTextMappingInMap(String[] parameters, K mapKey,
                                                               Map<K, TextureConverterContainer> textureMapping,
                                                               Map<K, Boolean> hasRotatedTextureMapping) {
        TextureConverterContainer container;
        int xNorth = Integer.parseInt(parameters[1]);
        int yNorth = Integer.parseInt(parameters[2]);
        if (parameters.length == 3) {
            container = new TextureConverterContainer(xNorth, yNorth, xNorth, yNorth,
                    xNorth, yNorth, xNorth, yNorth);
            if (hasRotatedTextureMapping != null) {
                hasRotatedTextureMapping.put(mapKey, false);
            }
        } else {
            int xEast = Integer.parseInt(parameters[3]);
            int yEast = Integer.parseInt(parameters[4]);
            int xSouth = Integer.parseInt(parameters[5]);
            int ySouth = Integer.parseInt(parameters[6]);
            int xWest = Integer.parseInt(parameters[7]);
            int yWest = Integer.parseInt(parameters[8]);
            container = new TextureConverterContainer(xNorth, yNorth, xEast, yEast,
                    xSouth, ySouth, xWest, yWest);
            if (hasRotatedTextureMapping != null) {
                hasRotatedTextureMapping.put(mapKey, true);
            }
        }
        textureMapping.put(mapKey, container);
    }

    /**
     * Gets a texture region based on the direction of the tile
     *
     * @param direction The direction the tile is facing
     * @param xNorth    The relative x position on the texture sheet if the tile is facing north
     * @param yNorth    The relative y position on the texture sheet if the tile is facing north
     * @param xEast     The relative x position on the texture sheet if the tile is facing east
     * @param yEast     The relative y position on the texture sheet if the tile is facing east
     * @param xSouth    The relative x position on the texture sheet if the tile is facing south
     * @param ySouth    The relative y position on the texture sheet if the tile is facing south
     * @param xWest     The relative x position on the texture sheet if the tile is facing west
     * @param yWest     The relative y position on the texture sheet if the tile is facing west
     * @return The texture region containing the tile's texture
     */
    private static TextureRegion getDirectionalTextureRegion(Direction direction, int xNorth, int yNorth, int xEast,
                                                             int yEast, int xSouth, int ySouth, int xWest, int yWest) {
        String INVALID_DIRECTION_MESSAGE = "Invalid direction for tile encountered";
        switch (direction) {
            case NORTH:
            case NORTH_EAST:
                return getTextureOnSheet(xNorth, yNorth);
            case EAST:
            case SOUTH_EAST:
                return getTextureOnSheet(xEast, yEast);
            case SOUTH:
            case SOUTH_WEST:
                return getTextureOnSheet(xSouth, ySouth);
            case WEST:
            case NORTH_WEST:
                return getTextureOnSheet(xWest, yWest);
            default:
                throw new IllegalArgumentException(INVALID_DIRECTION_MESSAGE);
        }
    }

    /**
     * Gets a texture on the main tiles texture sheet
     *
     * @param x The relative x coordinate on the sheet
     * @param y The relative y coordinate on the sheet
     * @return The texture region containing the texture
     */
    private static TextureRegion getTextureOnSheet(int x, int y) {
        final int tileTextureHeight = 300;
        final int tileTextureWidth = 300;
        return new TextureRegion(textureSheet, x * tileTextureWidth, y * tileTextureHeight,
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

        /**
         * Instantiates a new texture converter container
         *
         * @param xNorth The x position of the texture for a north-facing instance of the object
         * @param yNorth The y position of the texture for a north-facing instance of the object
         * @param xEast  The x position of the texture for a east-facing instance of the object
         * @param yEast  The y position of the texture for a east-facing instance of the object
         * @param xSouth The x position of the texture for a south-facing instance of the object
         * @param ySouth The y position of the texture for a south-facing instance of the object
         * @param xWest  The x position of the texture for a west-facing instance of the object
         * @param yWest  The y position of the texture for a west-facing instance of the object
         */
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

        /**
         * Gets the x position of the texture for a north-facing instance of the object
         *
         * @return The x position of the texture for a north-facing instance of the object
         */
        public int getXNorth() {
            return xNorth;
        }

        /**
         * Gets the y position of the texture for a north-facing instance of the object
         *
         * @return The y position of the texture for a north-facing instance of the object
         */
        public int getYNorth() {
            return yNorth;
        }

        /**
         * Gets the x position of the texture for a east-facing instance of the object
         *
         * @return The x position of the texture for a east-facing instance of the object
         */
        public int getXEast() {
            return xEast;
        }

        /**
         * Gets the y position of the texture for a east-facing instance of the object
         *
         * @return The y position of the texture for a east-facing instance of the object
         */
        public int getYEast() {
            return yEast;
        }

        /**
         * Gets the x position of the texture for a south-facing instance of the object
         *
         * @return The x position of the texture for a south-facing instance of the object
         */
        public int getXSouth() {
            return xSouth;
        }

        /**
         * Gets the y position of the texture for a south-facing instance of the object
         *
         * @return The y position of the texture for a south-facing instance of the object
         */
        public int getYSouth() {
            return ySouth;
        }

        /**
         * Gets the x position of the texture for a west-facing instance of the object
         *
         * @return The x position of the texture for a west-facing instance of the object
         */
        public int getXWest() {
            return xWest;
        }

        /**
         * Gets the y position of the texture for a west-facing instance of the object
         *
         * @return The y position of the texture for a west-facing instance of the object
         */
        public int getYWest() {
            return yWest;
        }
    }
}
