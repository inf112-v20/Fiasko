package inf112.fiasko.roborally.utility;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.fiasko.roborally.GdxTestRunner;
import inf112.fiasko.roborally.elementproperties.Direction;
import inf112.fiasko.roborally.elementproperties.TileType;
import inf112.fiasko.roborally.objects.Tile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith (GdxTestRunner.class)
public class TextureConverterUtilTest {
    private Tile tileNorth;
    private Tile holeNorth;
    private Tile transportBandSlowEast;
    private TextureRegion tileTextureRegion;
    private TextureRegion holeTextureRegion;
    private TextureRegion transportBandSlowEastTextureRegion;

    @Before
    public void setUp() {
        tileNorth = new Tile(TileType.TILE, Direction.NORTH);
        holeNorth = new Tile(TileType.HOLE, Direction.NORTH);
        transportBandSlowEast = new Tile(TileType.CONVEYOR_BELT_SLOW, Direction.EAST);
        tileTextureRegion = TextureConverterUtil.convertElement(tileNorth);
        holeTextureRegion = TextureConverterUtil.convertElement(holeNorth);
        transportBandSlowEastTextureRegion = TextureConverterUtil.convertElement(transportBandSlowEast);
    }

    @Test
    public void tileTileConversion() {
        assertEquals(4*300, tileTextureRegion.getRegionX());
        assertEquals(0, tileTextureRegion.getRegionY());
    }

    @Test
    public void tileHoleConversion() {
        assertEquals(5*300, holeTextureRegion.getRegionX());
        assertEquals(0, holeTextureRegion.getRegionY());
    }

    @Test
    public void tileTransportBandSlowFacingEastConversion() {
        assertEquals(3*300, transportBandSlowEastTextureRegion.getRegionX());
        assertEquals(6*300, transportBandSlowEastTextureRegion.getRegionY());
    }

    @Test
    public void tileTileHasRotatedTextureTest() {
        assertFalse(TextureConverterUtil.hasRotatedTexture(tileNorth));
    }

    @Test
    public void tileHoleHasRotatedTextureTest() {
        assertFalse(TextureConverterUtil.hasRotatedTexture(holeNorth));
    }

    @Test
    public void tileTransportBandHasRotatedTextureTest() {
        assertTrue(TextureConverterUtil.hasRotatedTexture(transportBandSlowEast));
    }
}
