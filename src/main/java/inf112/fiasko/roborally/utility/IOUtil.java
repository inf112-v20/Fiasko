package inf112.fiasko.roborally.utility;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.fiasko.roborally.element_properties.Direction;
import inf112.fiasko.roborally.element_properties.Position;
import inf112.fiasko.roborally.objects.IDrawableGame;
import inf112.fiasko.roborally.objects.DrawableObject;
import inf112.fiasko.roborally.objects.IDrawableObject;
import inf112.fiasko.roborally.objects.Robot;
import inf112.fiasko.roborally.objects.Tile;
import inf112.fiasko.roborally.objects.Wall;

import java.util.ArrayList;
import java.util.List;

public final class IOUtil {
    private IOUtil() {}

    /**
     * Gets a list of all elements which should be drawn from the game provided
     * @param game A game implementing IDrawableGame
     * @param tileWidth The with of all tiles to be drawn
     * @param tileHeight The height of all tiles to be drawn
     * @return A list of drawable objects
     */
    public static List<IDrawableObject> getDrawableObjectsFromGame(IDrawableGame game, int tileWidth, int tileHeight) {
        List<IDrawableObject> drawableObjects = new ArrayList<>();
        List<Tile> tilesToDraw = game.getTilesToDraw();
        List<Wall> wallsToDraw = game.getWallsToDraw();
        List<Robot> robotsToDraw = game.getRobotsToDraw();
        int gameWidth = game.getWidth();
        int gameHeight = game.getHeight();
        drawableObjects.addAll(getDrawableObjectsFromElementList(tilesToDraw, gameWidth, tileWidth, tileHeight));
        drawableObjects.addAll(getDrawableObjectsFromElementList(wallsToDraw, gameWidth, tileWidth, tileHeight));
        drawableObjects.addAll(getDrawableRobots(robotsToDraw, gameHeight, tileWidth, tileHeight));
        return drawableObjects;
    }

    /**
     * Gets a list of all drawable robots on the board
     * @param robots A list of robots to draw
     * @param gameHeight The height of the game
     * @param tileWidth The width of a tile
     * @param tileHeight The height of a tile
     * @return A list of drawable robots
     */
    private static List<IDrawableObject> getDrawableRobots(List<Robot> robots, int gameHeight, int tileWidth, int tileHeight) {
        List<IDrawableObject> drawableObjects = new ArrayList<>();
        for (Robot robot : robots) {
            TextureRegion region = TextureConverterUtil.convertElement(robot);
            Position robotPosition = robot.getPosition();
            int rotation = getElementRotation(robot);
            IDrawableObject drawableObject = new DrawableObject(region, robotPosition.getXCoordinate() * tileWidth,
                    (-robotPosition.getYCoordinate() + gameHeight - 1) * tileHeight, tileWidth, tileHeight, rotation);
            drawableObjects.add(drawableObject);
        }
        return drawableObjects;
    }

    /**
     * Gets a list of drawable objects with correct positions from a list of elements
     * @param elementsToDraw A list of elements to draw
     * @param gameWidth The width of the game board in tiles
     * @param tileWidth The width of a tile
     * @param tileHeight The height of a tile
     * @param <K> Should be type Robot, Tile or Wall
     * @return A list of drawable objects
     */
    private static <K> List<IDrawableObject> getDrawableObjectsFromElementList(List<K> elementsToDraw, int gameWidth, int tileWidth,
                                                                               int tileHeight) {
        List<IDrawableObject> drawableObjects = new ArrayList<>();
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
            } else {
                throw new IllegalArgumentException("Unknown element type passed to function.");
            }
            int rotation = getElementRotation(currentElement);
            IDrawableObject drawableObject = new DrawableObject(region,
                    x * tileWidth, y * tileHeight, tileWidth, tileHeight, rotation);
            drawableObjects.add(drawableObject);
        }
        return drawableObjects;
    }

    /**
     * Gets the amount of degrees to rotate an element
     * @param element The element to rotate
     * @param <K> Should be of type Tile, Robot or Wall
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
            hasRotatedTexture = true;
            direction = wall.getDirection();
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
