package inf112.fiasko.roborally.utility;

import inf112.fiasko.roborally.element_properties.*;
import inf112.fiasko.roborally.objects.*;

import java.io.*;
import java.util.List;

/**
 * Loads a board
 */
public final class BoardLoaderUtil {
    private BoardLoaderUtil() {}

    /**
     * Loads a board described in a file
     * @param boardFile The file containing the board description
     * @param robotList A list of robots on the board
     * @return A board
     * @throws IOException If the board file cannot be loaded
     */
    public static Board loadBoard(String boardFile, List<Robot> robotList) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream fileStream = classloader.getResourceAsStream(boardFile);
        if (fileStream == null) {
            throw new IllegalArgumentException("Board file could not be loaded.");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));
        String infoLine = reader.readLine();
        String[] infoData = infoLine.split(" ");
        int gridWidth = Integer.parseInt(infoData[0]);
        int gridHeight = Integer.parseInt(infoData[1]);

        IGrid<Tile> tileGrid = loadTileGrid(reader, gridWidth, gridHeight);
        IGrid<Wall> wallGrid = loadWallGrid(reader, gridWidth, gridHeight);
        return new Board(tileGrid, wallGrid, robotList);
    }

    /**
     * Loads information about a tile grid from a buffered reader
     * @param reader A buffered reader ready to read information about the grid
     * @param gridWidth The width of the grid to load
     * @param gridHeight The height of the grid to load
     * @return A grid containing the tiles described by the buffered reader
     * @throws IOException If the reader reads invalid grid information
     */
    private static IGrid<Tile> loadTileGrid(BufferedReader reader, int gridWidth, int gridHeight) throws IOException {
        IGrid<Tile> tileGrid = new Grid<>(gridWidth, gridHeight);
        for (int i = 0; i < gridHeight; i++) {
            String gridLine = reader.readLine();
            String[] tilesOnLine = gridLine.split(" ");
            for (int j = 0; j < gridWidth; j++) {
                String[] tileData = tilesOnLine[j].split(";");
                TileType tileType = TileType.getTileTypeFromID(Integer.parseInt(tileData[0]));
                Direction direction = Direction.getDirectionFromID(Integer.parseInt(tileData[1]));
                if (direction == null) {
                    throw new IllegalArgumentException("Invalid direction for tile encountered when loading board file.");
                }
                tileGrid.setElement(i, j, new Tile(tileType, direction));
            }
        }
        return tileGrid;
    }

    /**
     * Loads information about a wall grid from a buffered reader
     * @param reader A buffered reader ready to read information about the grid
     * @param gridWidth The width of the grid to load
     * @param gridHeight The height of the grid to load
     * @return A grid containing the walls described by the buffered reader
     * @throws IOException If the reader reads invalid grid information
     */
    private static IGrid<Wall> loadWallGrid(BufferedReader reader, int gridWidth, int gridHeight) throws IOException {
        IGrid<Wall> wallGrid = new Grid<>(gridWidth, gridHeight);
        for (int i = 0; i < gridHeight; i++) {
            String gridLine = reader.readLine();
            String[] wallsOnLine = gridLine.split(" ");
            for (int j = 0; j < gridWidth; j++) {
                if (wallsOnLine[j].equals("0")) {
                    continue;
                }
                String[] wallData = wallsOnLine[j].split(";");
                WallType wallType = WallType.getWallTypeFromID(Integer.parseInt(wallData[0]));
                Direction direction = Direction.getDirectionFromID(Integer.parseInt(wallData[1]));
                if (direction == null) {
                    throw new IllegalArgumentException("Invalid direction for tile encountered when loading board file.");
                }
                wallGrid.setElement(i, j, new Wall(wallType, direction));
            }
        }
        return wallGrid;
    }
}
