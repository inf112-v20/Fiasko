package inf112.fiasko.roborally.objects.properties;

/**
 * This class represents an id for marking specific robots
 */
public enum RobotID {
    /**
     * The id of the first robot (white)
     */
    ROBOT_1(1),
    /**
     * The id of the second robot (pink)
     */
    ROBOT_2(2),
    /**
     * The id of the third robot (light green)
     */
    ROBOT_3(3),
    /**
     * The id of the fourth robot (blue)
     */
    ROBOT_4(4),
    /**
     * The id of the fifth robot (yellow)
     */
    ROBOT_5(5),
    /**
     * The id of the sixth robot (dark green)
     */
    ROBOT_6(6),
    /**
     * The id of the seventh robot (orange)
     */
    ROBOT_7(7),
    /**
     * The id of the eight robot (red)
     */
    ROBOT_8(8);

    private final int robotID;

    /**
     * Constructor to let a robotID be represented by a numerical identifier
     *
     * @param robotID The numerical identifier assigned to the robot ID
     */
    RobotID(int robotID) {
        this.robotID = robotID;
    }

    /**
     * Gets a robot ID value from its numerical representation
     *
     * @param robotID The numerical representation of a robot id
     * @return The enum value representing the robot ID, or null if the id is invalid
     */
    public static RobotID getRobotIDFromID(int robotID) {
        for (RobotID type : RobotID.values()) {
            if (type.robotID == robotID) {
                return type;
            }
        }
        return null;
    }

    /**
     * Gets the numerical id used for identification of a robot id
     *
     * @return The numerical id of the robot id
     */
    public int getRobotIDID() {
        return this.robotID;
    }
}
