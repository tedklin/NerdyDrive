package com.team687.frc2017;

/**
 * Important constants
 */

public class Constants {

    // Conversions
    public final static double kDriveFeetToEncoderUnitsR = 4.388 * 3 / (Math.PI);
    public final static double kDriveFeetToEncoderUnitsL = 4.487 * 3 / (Math.PI);
    public final static double kWheelDiameter = 0;

    // Distance PID
    public final static double kDistF = 0;
    public final static double kDistP = 0.001;
    public final static double kDistI = 0;
    public final static double kDistD = 0;
    public final static double kMinDistPower = 0;
    public final static double kMaxDistPower = 1.0;
    public final static double kDriveDistanceTolerance = 205.6;
    public final static double kDriveDistanceOscillationCount = 5;

    // Rotation PID
    public final static double kRotP = 0.015;
    public final static double kRotI = 0;
    public final static double kRotD = 0;
    public final static double kMinRotPower = 0.12;
    public final static double kMaxRotPower = 1.0;
    public final static double kDriveRotationTolerance = 0.5;
    public final static double kDriveRotationDeadband = 0.25;

    // Motion Profiling
    public final static double kMaxVelocity = 0;
    public final static double kMaxAcceleration = 0;
    public final static double kMaxJerk = 0;
    public final static double kV = 0;
    public final static double kA = 0;
    public final static double kDt = 0.01;
    public final static double kDtInMinutes = kDt / 60;

    // Teleop
    public final static double kSensitivityHigh = 0.85;
    public final static double kSensitivityLow = 0.75;
    public final static double kDriveAlpha = 0.125; // Cheesy Drive
    public final static double kJoystickDeadband = 0.02;

    // Bezier Curves
    public final static double kBezierStep = 30;
    public final static double kBezierMinStraightPow = 0.1241;
    public final static double kBezierMaxStraightPow = 0.5172;

    // Paths
    public final static double[] RedPathWallToPeg = { 0, 0, 0, 0, 0, 0, 0, 0 };
    public final static double[] RedPathWallToHopper = { 0, 0, 0, 0, 0, 0, 0, 0 };
    public final static double RedHopperBackUpDistance = -0;
    public final static double RedHopperAngleToShoot = 0;
    public final static double[] RedPathPegToHopper = { 0, 0, 0, 0, 0, 0, 0, 0 };

}
