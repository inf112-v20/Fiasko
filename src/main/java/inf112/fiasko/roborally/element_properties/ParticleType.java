package inf112.fiasko.roborally.element_properties;

public enum ParticleType {
    LASER_BEAM_SINGLE (1),
    LASER_BEAM_DOUBLE (2),
    LASER_BEAM_SINGLE_CROSS (3),
    LASER_BEAM_DOUBLE_CROSS (4);

    private final int particleTypeID;

    /**
     * Constructor to let a particle type be represented by a numerical identifier
     * @param particleTypeID <p>The numerical identifier assigned to the particle type</p>
     */
    ParticleType(int particleTypeID) {
        this.particleTypeID = particleTypeID;
    }

    /**
     * Gets the numerical id used for alternate identification of a tile type
     * @return <p>The numerical id of the tile type</p>
     */
    public int getParticleTypeID() {
        return this.particleTypeID;
    }

    /**
     * Gets a particle type value from its numerical representation
     * @param particleTypeID <p>The numerical representation of a particle type</p>
     * @return <p>The enum value representing the particle type, or null if the id is invalid</p>
     */
    public static ParticleType getParticleTypeFromID(int particleTypeID) {
        for (ParticleType type : ParticleType.values()) {
            if (type.particleTypeID == particleTypeID) {
                return type;
            }
        }
        return null;
    }
}
