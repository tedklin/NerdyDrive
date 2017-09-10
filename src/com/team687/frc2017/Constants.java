package com.team687.frc2017;

/**
 * Important constants
 * 
 * @author tedlin
 */

public class Constants {

    // Conversions
    public final static double kDriveFeetToEncoderUnitsR = 4.388 * 3 / (Math.PI);
    public final static double kDriveFeetToEncoderUnitsL = 4.487 * 3 / (Math.PI);
    public final static double kWheelDiameter = 0; // in inches
    public final static double kDrivetrainWidth = 36; // in inches

    // Distance PID
    public final static double kDistF = 0;
    public final static double kDistP = 0.001;
    public final static double kDistI = 0;
    public final static double kDistD = 0;
    public final static double kMinDistPowerLowGear = 0;
    public final static double kMaxDistPowerLowGear = 1.0;
    public final static double kDriveDistanceTolerance = 205.6;
    public final static double kDriveDistanceOscillationCount = 5;

    public final static double kDistPHighGear = 0.001;
    public final static double kMinDistPowerHighGear = 0;
    public final static double kMaxDistPowerHighGear = 1.0;

    // Rotation PID
    public final static double kRotPLowGear = 0.015;
    public final static double kRotI = 0;
    public final static double kRotD = 0;
    public final static double kMinRotPowerLowGear = 0.12;
    public final static double kMaxRotPowerLowGear = 1.0;
    public final static double kDriveRotationTolerance = 0.5;
    public final static double kDriveRotationDeadband = 0.5;
    public final static int kDriveRotationCounter = 3;

    public final static double kRotPHighGear = 0.05;
    public final static double kMinRotPowerHighGear = 0.254;
    public final static double kMaxRotPowerHighGear = 1.0;

    // Motion Profiling
    public final static double kMaxVelocity = 0;
    public final static double kMaxAcceleration = 0;
    public final static double kMaxJerk = 0;
    public final static double kV = 0;
    public final static double kA = 0;
    public final static double kDt = 0.01;
    public final static double kDtInMinutes = kDt / 60;
    public final static double kCruiseVelocity = 0;

    // Collision Detection
    public final static double kCollisionThreshold = 0;

    // Teleop
    public final static double kSensitivityHigh = 0.85;
    public final static double kSensitivityLow = 0.75;
    public final static double kDriveAlpha = 0.125; // Cheesy Drive
    public final static double kJoystickDeadband = 0.02;

    // Bezier Curves
    public final static double kBezierStep = 60;
    public final static double kRotPBezier = 0.03;
    public final static double kDistPBezier = 0.001; // look at TODO on how to tune this
						     // the higher this is, the less time to decelerate
    public final static double kMaxStraightPower = 0.75;
    public final static double kMinStraightPower = 0.25; // ensure that the robot gets to end point with softStops
    public final static double kStraightPowerAdjuster = 0.5; // the higher this is, the slower the robot will
							     // go during a sharp turn

    // Paths
    public final static double[] RedPathWallToPeg = { 0, 0, 0, 0, 0, 0, 0, 0 };
    public final static double[] RedPathPegToHopper = { 0, 0, 0, 0, 0, 0, 0, 0 };
    public final static double RedHopperToBoilerCorrectingAngle = 0;

    public final static double[] RedPathWallToHopper973 = { 0, 0, 0, 0, 0, 0, 0, 0 };
    public final static double RedWallToHopperInitialDistance = 0;
    public final static double RedWallToHopperArcTurnAngle = 0;
    public final static double RedHopperBackUpDistance = -0;
    public final static double RedHopperToBoilerAngle = 0;

    public final static double[] RedPathWallToHopper1678 = { 0, 0, 0, 0, 0, 0, 0, 0 };
    public final static double[] RedPathWallToHopper2056 = { 0, 0, 0, 0, 0, 0, 0, 0 };
    public final static double RedPathDistanceAlignWithHopper = 0;

    public final static double[] BluePathWallToPeg = { 0, 0, 0, 92278.44, 0, 92278.44, 74403.76, 134999.94 };
    public final static double[] BluePathPegToHopper = { 74403.7644, 134999.94, 0, 86582.24, 0, 198227.76, -33835.428,
	    117615.1376 };
    public final static double BlueHopperToBoilerCorrectingAngle = 0;

    public final static double[] BluePathWallToHopper973 = { 0, 0, 0, 100300, 0, 100300, -48000, 100900 };
    public final static double BlueWallToHopperInitialDistance = 0;
    public final static double BlueWallToHopperArcTurnAngle = 86;
    public final static double BlueHopperBackUpDistance = -20000;
    public final static double BlueHopperToBoilerAngle = 67;

    public final static double[] BluePathWallToHopper1678 = { 0, 0, 0, 0, 0, 0, 0, 0 };
    public final static double[] BluePathWallToHopper2056 = { 0, 0, -39000, 40000, -39000, 64000, -39000, 101000 };
    public final static double BluePathDistanceAlignWithHopper = 20000;

    public final static double BlueWallToCenterPegDistance = -0;
    public final static double BlueCenterPegBackUpDistance = 0;
    public final static double BlueWallToSecondGearAngle = 90;
    public final static double BlueWallToSecondGearDistance = -0;

}
