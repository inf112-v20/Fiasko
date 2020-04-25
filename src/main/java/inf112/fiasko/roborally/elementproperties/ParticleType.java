package inf112.fiasko.roborally.elementproperties;

/**
 * This enum represents all possible particle types
 */
public enum ParticleType {
    /**
     * The beam emitting from a single laser
     */
    LASER_BEAM_SINGLE(10),
    /**
     * The beam emitting from a double laser
     */
    LASER_BEAM_DOUBLE(20),
    /**
     * The beam emitting if a single laser shoots in the opposite direction of a double laser
     */
    LASER_BEAM_TRIPLE(30),
    /**
     * The beam emitted where two single beams cross
     */
    LASER_BEAM_SINGLE_CROSS(11),
    /**
     * The beam emitted where two double beams cross
     */
    LASER_BEAM_DOUBLE_CROSS(22),
    /**
     * The beam emitted where two triple beams cross
     */
    LASER_BEAM_TRIPLE_CROSS(33),
    /**
     * The beam emitted where a single beam crosses a double beam
     */
    LASER_BEAM_SINGLE_DOUBLE_CROSS(12),
    /**
     * The beam emitted where a single beam crosses a triple beam
     */
    LASER_BEAM_SINGLE_TRIPLE_CROSS(13),
    /**
     * The beam emitted where a double beam crosses a triple beam
     */
    LASER_BEAM_DOUBLE_TRIPLE_CROSS(23);

    private final int particleTypeID;

    /**
     * Constructor to let a particle type be represented by a numerical identifier
     *
     * @param particleTypeID The numerical identifier assigned to the particle type
     */
    ParticleType(int particleTypeID) {
        this.particleTypeID = particleTypeID;
    }

    /**
     * Gets a particle type value from its numerical representation
     *
     * @param particleTypeID The numerical representation of a particle type
     * @return The enum value representing the particle type, or null if the id is invalid
     */
    public static ParticleType getParticleTypeFromID(int particleTypeID) {
        for (ParticleType type : ParticleType.values()) {
            if (type.particleTypeID == particleTypeID) {
                return type;
            }
        }
        return null;
    }

    /**
     * Gets the numerical id used for alternate identification of a tile type
     *
     * @return The numerical id of the tile type
     */
    public int getParticleTypeID() {
        return this.particleTypeID;
    }
}
