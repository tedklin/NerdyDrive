package com.team687.frc2017;

import com.team687.frc2017.utilities.PGains;

/**
 * Important constants
 * 
 * @author tedlin
 * 
 */

public class Constants {

    // Conversions
    public final static double kDriveFeetToEncoderUnitsR = 4.388 * 3 / (Math.PI);
    public final static double kDriveFeetToEncoderUnitsL = 4.487 * 3 / (Math.PI);
    public final static double kWheelDiameter = 0; // in inches
    public final static double kDrivetrainWidth = 36; // in inches

    // Distance PID
    public final static PGains kDistLowGearRightPGains = new PGains(0.001, 0.25, 1.0);
    public final static PGains kDistLowGearLeftPGains = new PGains(0.001, 0.25, 1.0);
    public final static double kDistD = 0;

    public final static PGains kDistHighGearRightPGains = new PGains(0.001, 0, 1.0);
    public final static PGains kDistHighGearLeftPGains = new PGains(0.001, 0, 1.0);

    public final static double kDriveDistanceTolerance = 205.6;
    public final static double kDriveDistanceOscillationCount = 5;

    // Rotation PID
    public final static PGains kRotLowGearPGains = new PGains(0.014, 0.12, 1.0);
    public final static PGains kRotHighGearPGains = new PGains(0.05, 0.254, 0.971);

    public final static double kDriveRotationTolerance = 0.5;
    public final static double kDriveRotationDeadband = 0.5;
    public final static int kDriveRotationCounter = 3;

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
    public final static double kCollisionThreshold = 100000;

    // Teleop
    public final static double kSensitivityHigh = 0.85;
    public final static double kSensitivityLow = 0.75;
    public final static double kDriveAlpha = 0.125; // Cheesy Drive
    public final static double kJoystickDeadband = 0.02;

    // Bezier Curves
    public final static double kBezierStep = 60;
    public final static PGains kBezierRotLowGearPGains = new PGains(0, 0, 1.0);
    public final static PGains kBezierRotHighGearPGains = new PGains(0.04, 0, 1.0);

    public final static PGains kBezierDistLowGearPGains = new PGains(0, 0, 1.0);
    public final static PGains kBezierDistHighGearPGains = new PGains(0.00005, 0.25, 0.7); // look at TODO at how to
											   // tune this; the higher this
											   // is, the less time to
											   // declerate
    public final static double kStraightPowerAdjuster = 0.5; // the higher this is, the slower the robot will
							     // go during a sharp turn

    // Paths
    // Close peg is the peg closer to the hopper. Far peg is the peg farthest from
    // the hopper.
    public final static double[] RedPathWallToClosePeg = { 0, 0, 0, 0, 0, 0, 0, 0 };
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

    public final static double RedWallToCenterPegDistance = 0;
    public final static double RedCenterPegBackUpDistance = 0;
    public final static double RedWallToSecondGearAngle = 0;
    public final static double RedWallToSecondGearDistance = 0;

    public final static double[] BluePathWallToClosePeg = { 0, 0, 0, 92278.44, 0, 92278.44, 74403.76, 134999.94 };
    public final static double[] BluePathPegToHopper = { 74403.7644, 134999.94, 0, 86582.24, 0, 198227.76, -33835.428,
	    117615.1376 };
    public final static double BlueHopperToBoilerCorrectingAngle = 0;

    public final static double[] BluePathWallToHopper973 = { 0, 0, 0, 100300, 0, 100300, -48000, 100900 };
    public final static double BlueWallToHopperInitialDistance = 0;
    public final static double BlueWallToHopperArcTurnAngle = 90;
    public final static double BlueHopperBackUpDistance = -20000;
    public final static double BlueHopperToBoilerAngle = 67;

    public final static double[] BluePathWallToHopper1678 = { 0, 0, 0, 0, 0, 0, 0, 0 };
    public final static double[] BluePathWallToHopper2056 = { 0, 0, -39000, 40000, -39000, 64000, -39000, 101000 };
    public final static double BluePathDistanceAlignWithHopper = 20000;

    public final static double BlueWallToCenterPegDistance = -0;
    public final static double BlueCenterPegBackUpDistance = 0;
    public final static double BlueWallCenterToSecondGearAngle = 90;
    public final static double BlueWallCenterToSecondGearDistance = -0;

    public final static double[] BluePathWallToFarPeg = { 0, 0, 0, 0, 0, 0, 0, 0 };
    public final static double[] BluePathFarPegBackUp = { 0, 0, 0, 0, 0, 0, 0, 0 };
    public final static double BlueWallFarToSecondGearAngle = -90;
    public final static double BlueWallFarToSecondGearDistance = 0;

    public final static double[] BluePathClosePegBackUp = { 0, 0, 0, 0, 0, 0, 0, 0 };
    public final static double BlueWallCloseToSecondGearAngle = -90;
    public final static double BlueWallCloseToSecondGearDistance = 0;

    public final static double kShotDistanceFeet = 6.9028871; // in feet (direct from 1678's code)

}
