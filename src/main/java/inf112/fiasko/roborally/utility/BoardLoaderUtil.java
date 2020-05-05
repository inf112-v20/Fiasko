package inf112.fiasko.roborally.utility;

import inf112.fiasko.roborally.objects.Board;
import inf112.fiasko.roborally.objects.BoardElement;
import inf112.fiasko.roborally.objects.BoardElementContainer;
import inf112.fiasko.roborally.objects.Grid;
import inf112.fiasko.roborally.objects.ListGrid;
import inf112.fiasko.roborally.objects.Robot;
import inf112.fiasko.roborally.objects.Tile;
import inf112.fiasko.roborally.objects.TwoTuple;
import inf112.fiasko.roborally.objects.Wall;
import inf112.fiasko.roborally.objects.properties.Direction;
import inf112.fiasko.roborally.objects.properties.TileType;
import inf112.fiasko.roborally.objects.properties.WallType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * This class helps loading boards
 */
public final class BoardLoaderUtil {

    private BoardLoaderUtil() {
    }

    /**
     * Gets the actual file name of a board given its human-readable name
     *
     * @param boardName The human-readable name of a board
     * @return The file name of the board
     */
    public static String getRealBoardName(String boardName) {
        return boardName.replace(" ", "_") + ".txt";
    }

    /**
     * Gets a list of all available boards with human-readable names
     *
     * @return A list of all available boards
     * @throws IOException If the board list cannot be read
     */
    public static String[] getBoardListHumanReadable() throws IOException {
        String[] boards = getBoardList();
        for (int i = 0; i < boards.length; i++) {
            boards[i] = boards[i].replace("_", " ").replace(".txt", "");
        }
        return boards;
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
        TwoTuple<Grid<Tile>, Grid<Wall>> grids = loadBoardGrids(boardFile);
        Grid<Tile> tileGrid = grids.value1;
        Grid<Wall> wallGrid = grids.value2;
        if (grids.value1.getHeight() < grids.value1.getWidth()) {
            tileGrid = rotateGrid(tileGrid, true);
            wallGrid = rotateGrid(wallGrid, true);
        }
        adjustRobotRotationToBoardRotation(tileGrid, robotList);
        return new Board(tileGrid, wallGrid, robotList);
    }

    /**
     * Gets a list of all available boards
     *
     * @return A list of all available boards
     * @throws IOException If the board list cannot be read
     */
    private static String[] getBoardList() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                ResourceUtil.getResourceAsInputStream("boards.txt")));
        int numberOfBoards = Integer.parseInt(reader.readLine());
        String[] boardList = new String[numberOfBoards];
        for (int i = 0; i < numberOfBoards; i++) {
            String board = reader.readLine();
            boardList[i] = board;
        }
        return boardList;
    }

    /**
     * Loads the grids necessary to create a board
     *
     * @param boardFile The board file to load
     * @return A tuple with the tile grid and the wall grid
     * @throws IOException If the board cannot be read
     */
    private static TwoTuple<Grid<Tile>, Grid<Wall>> loadBoardGrids(String boardFile) throws IOException {
        InputStream fileStream = ResourceUtil.getResourceAsInputStream(boardFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));
        String infoLine = reader.readLine();
        String[] infoData = infoLine.split(" ");
        int gridWidth = Integer.parseInt(infoData[0]);
        int gridHeight = Integer.parseInt(infoData[1]);
        Grid<Tile> tileGrid = loadTileGrid(reader, gridWidth, gridHeight);
        Grid<Wall> wallGrid = loadWallGrid(reader, gridWidth, gridHeight);
        return new TwoTuple<>(tileGrid, wallGrid);
    }

    /**
     * Rotates a grid clockwise or counterclockwise
     *
     * @param grid      The grid to rotate
     * @param clockwise Whether to rotate the grid clockwise
     * @param <K>       The type of the grid to rotate
     * @return The rotated board
     */
    @SuppressWarnings("unchecked")
    private static <K extends BoardElement> Grid<K> rotateGrid(Grid<K> grid, boolean clockwise) {
        int gridWidth = grid.getWidth();
        int gridHeight = grid.getHeight();
        Grid<K> newGrid = new ListGrid<>(grid.getHeight(), grid.getWidth());
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                K element = grid.getElement(i, j);
                K copy = null;
                if (element != null) {
                    copy = (K) element.copy();
                    if (clockwise) {
                        copy.setDirection(Direction.getRightRotatedDirection(copy.getDirection()));
                    } else {
                        copy.setDirection(Direction.getLeftRotatedDirection(copy.getDirection()));
                    }
                }
                if (clockwise) {
                    newGrid.setElement(gridHeight - j - 1, i, copy);
                } else {
                    newGrid.setElement(j, gridWidth - i - 1, copy);
                }
            }
        }
        return newGrid;
    }

    /**
     * Changes the direction of robots to the direction which is up
     *
     * @param tileGrid  The grid containing flags
     * @param robotList The list of robots on the board
     */
    private static void adjustRobotRotationToBoardRotation(Grid<Tile> tileGrid, List<Robot> robotList) {
        //The spawns are always in the up direction
        List<BoardElementContainer<Tile>> spawns = GridUtil.getMatchingElements(TileType.ROBOT_SPAWN_1, tileGrid);
        Direction boardDirection;
        if (spawns.size() == 0) {
            boardDirection = Direction.NORTH;
        } else {
            boardDirection = spawns.get(0).getElement().getDirection();
        }
        for (Robot robot : robotList) {
            robot.setFacingDirection(boardDirection);
        }
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
