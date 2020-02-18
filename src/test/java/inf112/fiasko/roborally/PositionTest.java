package inf112.fiasko.roborally;

import inf112.fiasko.roborally.game.Position;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PositionTest {
    @Test
    public void TestGetXPosition(){
        Position testXPosition = new Position(3,4);
        assertEquals(3,testXPosition.getXCoordinate());
    }
}
