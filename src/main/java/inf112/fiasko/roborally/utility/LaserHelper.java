package inf112.fiasko.roborally.utility;

import inf112.fiasko.roborally.objects.Particle;
import inf112.fiasko.roborally.objects.properties.Direction;
import inf112.fiasko.roborally.objects.properties.ParticleType;
import inf112.fiasko.roborally.objects.properties.WallType;

/**
 * Helps with displaying laser beams
 */
public final class LaserHelper {

    /**
     * Gets the correct particle type from a laser type
     *
     * @param laserType The type of laser firing
     * @return The particle representing the laser's beam
     */
    public static ParticleType getParticleFromLaserType(WallType laserType) {
        switch (laserType) {
            case WALL_LASER_SINGLE:
                return ParticleType.LASER_BEAM_SINGLE;
            case WALL_LASER_DOUBLE:
                return ParticleType.LASER_BEAM_DOUBLE;
            case WALL_LASER_TRIPLE:
                return ParticleType.LASER_BEAM_TRIPLE;
            default:
                throw new IllegalArgumentException("Invalid laser type encountered.");
        }
    }

    /**
     * Gets the new particle to use given the laser firing and the existing beam particle
     *
     * @param laserBeam    The laser beam which is fired
     * @param existingBeam The laser beam which already exists at a tile
     * @return The particle which is a combination of the two
     */
    public static Particle getNewLaserBeamParticle(Particle laserBeam, Particle existingBeam) {
        ParticleType laserBeamType = laserBeam.getType();
        ParticleType existingBeamType = existingBeam.getType();
        Direction laserDirection = laserBeam.getDirection();
        Direction existingDirection = existingBeam.getDirection();

        int forwardBeamsLaser = getNumberOfForwardBeams(laserBeamType);
        int crossingBeamsLaser = getNumberOfPerpendicularBeams(laserBeamType);
        int forwardBeamsExisting = getNumberOfForwardBeams(existingBeamType);
        int crossingBeamsExisting = getNumberOfPerpendicularBeams(existingBeamType);

        //Flip number of beams if beams are perpendicular
        if (Direction.arePerpendicular(laserDirection, existingDirection)) {
            int temp = forwardBeamsExisting;
            forwardBeamsExisting = crossingBeamsExisting;
            crossingBeamsExisting = temp;
        }
        //Calculates the new number of forward beams
        int forwardBeams = getNumberOfBeams(forwardBeamsLaser, forwardBeamsExisting);
        //Calculates the new number of crossing beams
        int crossingBeams = getNumberOfBeams(crossingBeamsLaser, crossingBeamsExisting);
        //The direction should be the direction with the least amount of beams
        Direction newDirection;
        if (forwardBeams > crossingBeams) {
            newDirection = existingDirection;
        } else {
            newDirection = laserDirection;
        }
        //If using the existing direction and the beams are perpendicular, the direction needs to be rotated
        if (newDirection.equals(existingDirection) &&
                Direction.arePerpendicular(laserDirection, existingDirection)) {
            newDirection = Direction.getLeftRotatedDirection(newDirection);
        }
        //If the particle does not exist, we have to rotate the beam to get the correct one
        ParticleType newParticleType = getNewParticleType(forwardBeams, crossingBeams);
        if (newParticleType == null) {
            newParticleType = getNewParticleType(crossingBeams, forwardBeams);
            newDirection = Direction.getLeftRotatedDirection(newDirection);
        }
        return new Particle(newParticleType, newDirection);
    }

    /**
     * Gets the correct number of beams given existing beams and the beams to add
     *
     * @param newBeams      The beam count of the new beam to add
     * @param existingBeams The beam count of the existing beam
     * @return The new number/thickness of beams/the beam
     */
    private static int getNumberOfBeams(int newBeams, int existingBeams) {
        if ((newBeams + existingBeams) != 0 && (newBeams + existingBeams) % 3 == 0) {
            return 3;
        } else {
            return Math.max(newBeams, existingBeams);
        }
    }

    /**
     * Gets a new particle type with the given number of beams
     *
     * @param forwardBeams       The number of beams in the direction of the laser
     * @param perpendicularBeams The number of beams in the perpendicular direction of the laser
     * @return The correct particle type to be displayed
     */
    private static ParticleType getNewParticleType(int forwardBeams, int perpendicularBeams) {
        return ParticleType.getParticleTypeFromID(10 * forwardBeams + perpendicularBeams);
    }

    /**
     * Gets the number of beams in the forward direction given a particle type
     *
     * @param particleType The type of particle to check
     * @return The number of beams in the forward direction of the laser beam
     */
    private static int getNumberOfForwardBeams(ParticleType particleType) {
        return particleType.getParticleTypeID() / 10;
    }

    /**
     * Gets the number of beams in the perpendicular direction given a particle type
     *
     * @param particleType The type of particle to check
     * @return The number of beams in the perpendicular direction of the laser beam
     */
    private static int getNumberOfPerpendicularBeams(ParticleType particleType) {
        return particleType.getParticleTypeID() % 10;
    }

}
