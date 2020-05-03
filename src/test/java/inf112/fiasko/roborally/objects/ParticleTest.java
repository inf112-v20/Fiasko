package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.objects.properties.Direction;
import inf112.fiasko.roborally.objects.properties.ParticleType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ParticleTest {
    private Particle particle;
    private Particle particle2;

    @Before
    public void setUp() {
        particle = new Particle(ParticleType.LASER_BEAM_SINGLE, Direction.NORTH);
        particle2 = new Particle(ParticleType.LASER_BEAM_DOUBLE_CROSS, Direction.EAST);
    }

    @Test
    public void getParticleTypeFromParticle() {
        assertEquals(ParticleType.LASER_BEAM_SINGLE, particle.getType());
    }

    @Test
    public void getParticleTypeFromParticle2() {
        assertEquals(ParticleType.LASER_BEAM_DOUBLE_CROSS, particle2.getType());
    }


    @Test
    public void getDirectionFromParticle() {
        assertEquals(Direction.NORTH, particle.getDirection());
    }

    @Test
    public void getDirectionFromParticle2() {
        assertEquals(Direction.EAST, particle2.getDirection());
    }

    @Test
    public void invalidParticleTypeIDReturnsNull() {
        assertNull(ParticleType.getParticleTypeFromID(-1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidParticleDirectionThrowsError() {
        new Particle(ParticleType.LASER_BEAM_DOUBLE, Direction.NORTH_EAST);
    }
}
