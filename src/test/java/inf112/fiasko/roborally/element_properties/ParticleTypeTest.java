package inf112.fiasko.roborally.element_properties;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ParticleTypeTest {
    @Test
    public void getTileTypeIDForLaserBeam() {
        assertEquals(1, ParticleType.LASER_BEAM_SINGLE.getParticleTypeID());
    }

    @Test
    public void getTileTypeFromLaserBeamID() {
        assertEquals(ParticleType.LASER_BEAM_SINGLE, ParticleType.getParticleTypeFromID(1));
    }

    @Test
    public void getTileTypeIDForLaserBeamDouble() {
        assertEquals(2, ParticleType.LASER_BEAM_DOUBLE.getParticleTypeID());
    }

    @Test
    public void getTileTypeFromLaserBeamDoubleID() {
        assertEquals(ParticleType.LASER_BEAM_DOUBLE, ParticleType.getParticleTypeFromID(2));
    }

    @Test
    public void allParticleTypesIDConversionToIDAndBack() {
        for (ParticleType type : ParticleType.values()) {
            assertEquals(type, ParticleType.getParticleTypeFromID(type.getParticleTypeID()));
        }
    }

    @Test
    public void allParticlesHaveUniqueId() {
        /* This test is also done implicitly by the allTileTypesIDConversionToIDAndBack test, but that test may fail
           even if this test passes, so this test is needed for clarity. */
        Set<Integer> set = new HashSet<>();
        List<Integer> list = new ArrayList<>();
        for (ParticleType type : ParticleType.values()) {
            set.add(type.getParticleTypeID());
            list.add(type.getParticleTypeID());
        }
        assertEquals(list.size(), set.size());
    }
}
