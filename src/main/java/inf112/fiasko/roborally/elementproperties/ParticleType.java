package inf112.fiasko.roborally.elementproperties;

public enum ParticleType {
    /**
     * The beam emitting from a single laser
     */
    LASER_BEAM_SINGLE(1),
    /**
     * The beam emitting from a double laser
     */
    LASER_BEAM_DOUBLE(2),
    /**
     * The beam emitted where two single beams cross
     */
    LASER_BEAM_SINGLE_CROSS(3),
    /**
     * The beam emitted where two double beams cross
     */
    LASER_BEAM_DOUBLE_CROSS(4);

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
