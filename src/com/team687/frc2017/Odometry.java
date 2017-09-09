package com.team687.frc2017;

import com.team687.frc2017.utilities.Kinematics;
import com.team687.frc2017.utilities.Pose;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Odometry of drivetrain. Keeps track of robot's pose (x, y, theta) over time
 * using pose transformations and kinematics.
 * 
 * @author tedlin
 *
 */

public class Odometry {

    private static Odometry m_instance = null;

    private double m_gyroYawDegrees;
    private double m_gyroYawRadians;
    private double m_derivedYaw;
    private double m_angularVelocity;
    private double m_arcRadius;

    private double m_leftDistance;
    private double m_rightDistance;
    private double m_leftVelocity;
    private double m_rightVelocity;

    private double m_currentTime;
    private double m_lastTime;
    private double m_deltaTime;

    private Pose m_lastPose;
    private Pose m_newPose;

    public static Odometry getInstance() {
	if (m_instance == null) {
	    m_instance = new Odometry();
	}
	return m_instance;
    }

    protected Odometry() {
	m_lastTime = Timer.getFPGATimestamp();

	// starting configuration
	m_lastPose = new Pose(0, 0, Robot.drive.getCurrentYawRadians());
	m_derivedYaw = Robot.drive.getCurrentYawRadians();
    }

    public void update() {
	// raw sensor readings
	m_leftDistance = Robot.drive.getLeftTicks();
	m_rightDistance = Robot.drive.getRightTicks();
	m_leftVelocity = Robot.drive.getLeftTicksSpeed();
	m_rightVelocity = Robot.drive.getRightTicksSpeed();
	m_gyroYawDegrees = Robot.drive.getCurrentYaw();
	m_gyroYawRadians = Robot.drive.getCurrentYawRadians();

	SmartDashboard.putNumber("Left Position Ticks", m_leftDistance);
	SmartDashboard.putNumber("Right Position Ticks", m_rightDistance);
	SmartDashboard.putNumber("Drivetrain Position Ticks", Robot.drive.getDrivetrainTicks());
	SmartDashboard.putNumber("Left Speed Ticks", m_leftVelocity);
	SmartDashboard.putNumber("Right Speed Ticks", m_rightVelocity);

	SmartDashboard.putNumber("Yaw (degrees)", m_gyroYawDegrees);
	SmartDashboard.putNumber("Yaw (radians)", m_gyroYawRadians);
	SmartDashboard.putNumber("Accel X", Robot.drive.getCurrentAccelX());
	SmartDashboard.putNumber("Accel Y", Robot.drive.getCurrentAccelY());
	SmartDashboard.putNumber("Accel Z", Robot.drive.getCurrentAccelZ());

	// calculations
	m_derivedYaw += Kinematics.getDerivedDeltaYaw(m_rightVelocity, m_leftVelocity, m_deltaTime);
	m_angularVelocity = Kinematics.getAngularVelocity(m_rightVelocity, m_leftVelocity);
	m_arcRadius = Kinematics.getCurvatureRadius(m_rightVelocity, m_leftVelocity);

	SmartDashboard.putNumber("Yaw derived from encoders (radians)", m_derivedYaw);
	SmartDashboard.putNumber("Angular Velocity", m_angularVelocity);
	SmartDashboard.putNumber("Radius of Curvature", m_arcRadius);

	m_currentTime = Timer.getFPGATimestamp();
	m_deltaTime = m_currentTime - m_lastTime;
	m_lastTime = m_currentTime;

	m_newPose = Kinematics.getNewPose(m_lastPose, m_rightVelocity, m_leftVelocity, m_deltaTime);
	m_lastPose = m_newPose;

	SmartDashboard.putNumber("X", m_newPose.getX());
	SmartDashboard.putNumber("Y", m_newPose.getY());
	SmartDashboard.putNumber("Theta (radians)", m_newPose.getTheta()); // this is the same as yaw in radians
    }

}
