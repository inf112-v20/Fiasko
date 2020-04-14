package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.elementproperties.Direction;
import inf112.fiasko.roborally.elementproperties.Position;
import inf112.fiasko.roborally.elementproperties.TileType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardElementContainerTest {

    @Test
    public void getObjectTest() {
        Position pos = new Position(1,2);
        Tile tile = new Tile(TileType.TILE, Direction.NORTH);
        BoardElementContainer<Tile> element = new BoardElementContainer<>(tile, pos);
        assertEquals(tile, element.getElement());
    }

    @Test
    public void getPositionTest() {
        Position pos = new Position(1,2);
        Tile tile = new Tile(TileType.TILE, Direction.NORTH);
        BoardElementContainer<Tile> element = new BoardElementContainer<>(tile, pos);
        assertEquals(pos, element.getPosition());
    }
}