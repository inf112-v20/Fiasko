package inf112.fiasko.roborally.utility;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.esotericsoftware.kryonet.Connection;
import inf112.fiasko.roborally.objects.DrawableGame;
import inf112.fiasko.roborally.objects.Particle;
import inf112.fiasko.roborally.objects.Player;
import inf112.fiasko.roborally.objects.Robot;
import inf112.fiasko.roborally.objects.Tile;
import inf112.fiasko.roborally.objects.Wall;
import inf112.fiasko.roborally.objects.properties.Direction;
import inf112.fiasko.roborally.objects.properties.Position;
import inf112.fiasko.roborally.objects.properties.RobotID;
import inf112.fiasko.roborally.ui.DrawableObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class helps with tasks which mix primitive classes and classes from external libraries
 */
public final class IOUtil {

    private IOUtil() {
    }

    /**
     * Generates a list of players from a map of player names and a map of robot ids
     *
     * @param playerNames A map between connections and player names
     * @param robotIDs    A map between connections and robot ids
     * @return A list of players
     */
    public static List<Player> playerGenerator(Map<Connection, String> playerNames, Map<Connection, RobotID> robotIDs) {
        List<Player> playerList = new ArrayList<>();
        for (Connection connection : playerNames.keySet()) {
            Player player = new Player(robotIDs.get(connection), playerNames.get(connection));
            playerList.add(player);
        }
        return playerList;
    }

    /**
     * Gets a list of all elements which should be drawn from the game provided
     *
     * @param game       A game implementing DrawableGame
     * @param tileWidth  The with of all tiles to be drawn
     * @param tileHeight The height of all tiles to be drawn
     * @return A list of drawable objects
     */
    public static List<DrawableObject> getDrawableObjectsFromGame(DrawableGame game, int tileWidth, int tileHeight) {
        List<DrawableObject> drawableObjects = new ArrayList<>();
        List<Tile> tilesToDraw = game.getTilesToDraw();
        List<Wall> wallsToDraw = game.getWallsToDraw();
        List<Particle> particlesToDraw = game.getParticlesToDraw();
        List<Robot> robotsToDraw = game.getRobotsToDraw();
        int gameWidth = game.getWidth();
        int gameHeight = game.getHeight();
        drawableObjects.addAll(getDrawableObjectsFromElementList(tilesToDraw, gameWidth, tileWidth, tileHeight));
        drawableObjects.addAll(getDrawableObjectsFromElementList(particlesToDraw, gameWidth, tileWidth, tileHeight));
        drawableObjects.addAll(getDrawableObjectsFromElementList(wallsToDraw, gameWidth, tileWidth, tileHeight));
        drawableObjects.addAll(getDrawableRobots(robotsToDraw, gameHeight, tileWidth, tileHeight));
        return drawableObjects;
    }

    /**
     * Gets a list of all drawable robots on the board
     *
     * @param robots     A list of robots to draw
     * @param gameHeight The height of the game
     * @param tileWidth  The width of a tile
     * @param tileHeight The height of a tile
     * @return A list of drawable robots
     */
    private static List<DrawableObject> getDrawableRobots(List<Robot> robots, int gameHeight, int tileWidth,
                                                          int tileHeight) {
        List<DrawableObject> drawableObjects = new ArrayList<>();
        for (Robot robot : robots) {
            TextureRegion region = TextureConverterUtil.convertElement(robot);
            Position robotPosition = robot.getPosition();
            int rotation = getElementRotation(robot);
            DrawableObject drawableObject = new DrawableObject(region, robotPosition.getXCoordinate() * tileWidth,
                    (-robotPosition.getYCoordinate() + gameHeight - 1) * tileHeight, tileWidth, tileHeight, rotation);
            drawableObjects.add(drawableObject);
        }
        return drawableObjects;
    }

    /**
     * Gets a list of drawable objects with correct positions from a list of elements
     *
     * @param elementsToDraw A list of elements to draw
     * @param gameWidth      The width of the game board in tiles
     * @param tileWidth      The width of a tile
     * @param tileHeight     The height of a tile
     * @param <K>            Should be type Robot, Tile or Wall
     * @return A list of drawable objects
     */
    private static <K> List<DrawableObject> getDrawableObjectsFromElementList(List<K> elementsToDraw, int gameWidth,
                                                                              int tileWidth, int tileHeight) {
        List<DrawableObject> drawableObjects = new ArrayList<>();
        int y = 0;
        for (int i = 0; i < elementsToDraw.size(); i++) {
            K currentElement = elementsToDraw.get(i);
            int x = i % gameWidth;
            if (i > 0 && i % gameWidth == 0) {
                y++;
            }
            if (currentElement == null) {
                continue;
            }
            TextureRegion region;
            if (currentElement.getClass().isAssignableFrom(Tile.class)) {
                Tile tile = (Tile) currentElement;
                region = TextureConverterUtil.convertElement(tile);
            } else if (currentElement.getClass().isAssignableFrom(Wall.class)) {
                Wall wall = (Wall) currentElement;
                region = TextureConverterUtil.convertElement(wall);
            } else if (currentElement.getClass().isAssignableFrom(Particle.class)) {
                Particle particle = (Particle) currentElement;
                region = TextureConverterUtil.convertElement(particle);
            } else {
                throw new IllegalArgumentException("Unknown element type passed to function.");
            }
            int rotation = getElementRotation(currentElement);
            DrawableObject drawableObject = new DrawableObject(region,
                    x * tileWidth, y * tileHeight, tileWidth, tileHeight, rotation);
            drawableObjects.add(drawableObject);
        }
        return drawableObjects;
    }

    /**
     * Gets the amount of degrees to rotate an element
     *
     * @param element The element to rotate
     * @param <K>     Should be of type Tile, Robot or Wall
     * @return The amount of degrees the tile should be rotated to be properly displayed
     */
    private static <K> int getElementRotation(K element) {
        boolean hasRotatedTexture;
        Direction direction;
        if (element.getClass().isAssignableFrom(Robot.class)) {
            Robot robot = (Robot) element;
            hasRotatedTexture = false;
            direction = robot.getFacingDirection();
        } else if (element.getClass().isAssignableFrom(Tile.class)) {
            Tile tile = (Tile) element;
            hasRotatedTexture = TextureConverterUtil.hasRotatedTexture(tile);
            direction = tile.getDirection();
        } else if (element.getClass().isAssignableFrom(Wall.class)) {
            Wall wall = (Wall) element;
            hasRotatedTexture = TextureConverterUtil.hasRotatedTexture(wall);
            direction = wall.getDirection();
        } else if (element.getClass().isAssignableFrom(Particle.class)) {
            Particle particle = (Particle) element;
            hasRotatedTexture = TextureConverterUtil.hasRotatedTexture(particle);
            direction = particle.getDirection();
        } else {
            throw new IllegalArgumentException("Unknown element type passed to function.");
        }
        if (hasRotatedTexture) {
            return 0;
        }
        switch (direction) {
            case NORTH:
            case NORTH_EAST:
                return 0;
            case WEST:
            case NORTH_WEST:
                return 90;
            case SOUTH:
            case SOUTH_WEST:
                return 180;
            case EAST:
            case SOUTH_EAST:
                return 270;
            default:
                throw new IllegalArgumentException("Invalid element direction encountered.");
        }
    }
}
