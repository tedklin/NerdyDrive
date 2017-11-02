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

    // the higher the distance kP is, the less time to declerate
    public final static PGains kBezierDistLowGearPGains = new PGains(0, 0, 1.0);
    public final static PGains kBezierDistHighGearPGains = new PGains(0.00005, 0.25, 0.7);

    // the higher the curvature function is, the slower the robot will go during a
    // sharp turn; keep this value under 148 to be safe; if it is zero, the straight
    // power will be static even if dynamic straight power is enabled
    public final static double kCurvatureFunction = 85;

    // in feet (direct from 1678's code)
    public final static double kShotDistanceFeet = 6.9028871;

}
