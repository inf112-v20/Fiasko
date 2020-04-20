package inf112.fiasko.roborally.utility;

import inf112.fiasko.roborally.elementproperties.Direction;
import inf112.fiasko.roborally.elementproperties.TileType;
import inf112.fiasko.roborally.elementproperties.WallType;
import inf112.fiasko.roborally.objects.Board;
import inf112.fiasko.roborally.objects.Grid;
import inf112.fiasko.roborally.objects.Robot;
import inf112.fiasko.roborally.objects.Tile;
import inf112.fiasko.roborally.objects.Wall;
import inf112.fiasko.roborally.objects.ListGrid;

import java.io.*;
import java.util.List;

/**
 * This class helps loading boards
 */
public final class BoardLoaderUtil {

    private BoardLoaderUtil() {
    }

    /**
     * Loads a board described in a file
     *
     * @param boardFile The file containing the board description
     * @param robotList A list of robots on the board
     * @return A board
     * @throws IOException If the board file cannot be loaded
     */
    public static Board loadBoard(String boardFile, List<Robot> robotList) throws IOException {
        InputStream fileStream = ResourceUtil.getResourceAsInputStream(boardFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));
        String infoLine = reader.readLine();
        String[] infoData = infoLine.split(" ");
        int gridWidth = Integer.parseInt(infoData[0]);
        int gridHeight = Integer.parseInt(infoData[1]);

        Grid<Tile> tileGrid = loadTileGrid(reader, gridWidth, gridHeight);
        Grid<Wall> wallGrid = loadWallGrid(reader, gridWidth, gridHeight);
        return new Board(tileGrid, wallGrid, robotList);
    }

    /**
     * Loads information about a tile grid from a buffered reader
     *
     * @param reader     A buffered reader ready to read information about the grid
     * @param gridWidth  The width of the grid to load
     * @param gridHeight The height of the grid to load
     * @return A grid containing the tiles described by the buffered reader
     * @throws IOException If the reader reads invalid grid information
     */
    private static Grid<Tile> loadTileGrid(BufferedReader reader, int gridWidth, int gridHeight) throws IOException {
        Grid<Tile> tileGrid = new ListGrid<>(gridWidth, gridHeight);
        for (int y = 0; y < gridHeight; y++) {
            String gridLine = reader.readLine();
            String[] tilesOnLine = gridLine.split(" ");
            for (int x = 0; x < gridWidth; x++) {
                String[] tileData = tilesOnLine[x].split(";");
                TileType tileType = TileType.getTileTypeFromID(Integer.parseInt(tileData[0]));
                Direction direction = Direction.getDirectionFromID(Integer.parseInt(tileData[1]));
                if (direction == null) {
                    throw new IllegalArgumentException("Invalid direction for tile encountered when loading board file.");
                }
                tileGrid.setElement(x, y, new Tile(tileType, direction));
            }
        }
        return tileGrid;
    }

    /**
     * Loads information about a wall grid from a buffered reader
     *
     * @param reader     A buffered reader ready to read information about the grid
     * @param gridWidth  The width of the grid to load
     * @param gridHeight The height of the grid to load
     * @return A grid containing the walls described by the buffered reader
     * @throws IOException If the reader reads invalid grid information
     */
    private static Grid<Wall> loadWallGrid(BufferedReader reader, int gridWidth, int gridHeight) throws IOException {
        Grid<Wall> wallGrid = new ListGrid<>(gridWidth, gridHeight);
        for (int y = 0; y < gridHeight; y++) {
            String gridLine = reader.readLine();
            String[] wallsOnLine = gridLine.split(" ");
            for (int x = 0; x < gridWidth; x++) {
                if (wallsOnLine[x].equals("0")) {
                    continue;
                }
                String[] wallData = wallsOnLine[x].split(";");
                WallType wallType = WallType.getWallTypeFromID(Integer.parseInt(wallData[0]));
                Direction direction = Direction.getDirectionFromID(Integer.parseInt(wallData[1]));
                if (direction == null) {
                    throw new IllegalArgumentException("Invalid direction for wall encountered when loading board file.");
                }
                wallGrid.setElement(x, y, new Wall(wallType, direction));
            }
        }
        return wallGrid;
    }
}
