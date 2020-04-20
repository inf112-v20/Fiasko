package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.elementproperties.Direction;
import inf112.fiasko.roborally.elementproperties.ParticleType;

/**
 * This class represents a particle
 */
public class Particle {

    private ParticleType particleType;
    private Direction direction;

    /**
     * Instantiates a new particle
     *
     * @param particleType The type of the particle
     * @param direction    The direction of the particle
     */
    public Particle(ParticleType particleType, Direction direction) {
        if (direction.getDirectionID() % 2 == 0) {
            throw new IllegalArgumentException("Invalid direction for particle submitted");
        }
        this.particleType = particleType;
        this.direction = direction;
    }

    /**
     * Gets the particle type of the particle
     *
     * @return The particle's particle type
     */
    public ParticleType getParticleType() {
        return particleType;
    }

    /**
     * Gets the direction of the particle
     *
     * @return The particle's direction
     */
    public Direction getDirection() {
        return direction;
    }
}