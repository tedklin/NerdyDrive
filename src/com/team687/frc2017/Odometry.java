package com.team687.frc2017;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Odometry of drive
 * 
 * @author tedlin
 *
 */

public class Odometry {

    private static Odometry m_instance = null;

    private double m_x;
    private double m_y;

    private double m_gyroYawDegrees;
    private double m_gyroYawRadians;
    private double m_derivedYaw;
    private double m_angularVelocity;
    private double m_arcRadius;

    private double m_leftDistance;
    private double m_rightDistance;
    private double m_leftSpeed;
    private double m_rightSpeed;

    private double m_diffDistance;
    private double m_diffVelocity;
    private double m_sigmaVelocity;

    private double m_currentTime;
    private double m_lastTime;
    private double m_deltaTime;

    public static Odometry getInstance() {
	if (m_instance == null) {
	    m_instance = new Odometry();
	}
	return m_instance;
    }

    protected Odometry() {
	m_lastTime = Timer.getFPGATimestamp();
    }

    public void update() {
	m_currentTime = Timer.getFPGATimestamp();
	m_deltaTime = m_currentTime - m_lastTime;
	m_lastTime = m_currentTime;

	// raw sensor readings
	m_leftDistance = Robot.drive.getLeftTicks();
	m_rightDistance = Robot.drive.getRightTicks();
	m_leftSpeed = Robot.drive.getLeftTicksSpeed();
	m_rightSpeed = Robot.drive.getRightTicksSpeed();
	m_gyroYawDegrees = Robot.drive.getCurrentYaw();
	m_gyroYawRadians = Robot.drive.getCurrentYawRadians();

	SmartDashboard.putNumber("Left Position Ticks", m_leftDistance);
	SmartDashboard.putNumber("Right Position Ticks", m_rightDistance);
	SmartDashboard.putNumber("Drivetrain Position Ticks", Robot.drive.getDrivetrainTicks());
	SmartDashboard.putNumber("Left Speed Ticks", m_leftSpeed);
	SmartDashboard.putNumber("Right Speed Ticks", m_rightSpeed);

	SmartDashboard.putNumber("Yaw (degrees)", m_gyroYawDegrees);
	SmartDashboard.putNumber("Yaw (radians)", m_gyroYawRadians);
	SmartDashboard.putNumber("Accel X", Robot.drive.getCurrentAccelX());
	SmartDashboard.putNumber("Accel Y", Robot.drive.getCurrentAccelY());
	SmartDashboard.putNumber("Accel Z", Robot.drive.getCurrentAccelZ());

	// calculations
	m_diffDistance = m_rightDistance - m_leftDistance;
	m_diffVelocity = m_rightSpeed - m_leftSpeed;
	m_sigmaVelocity = m_rightSpeed + m_leftSpeed;

	m_derivedYaw = (Math.PI / 2) - (Math.acos(m_diffDistance / Constants.kDrivetrainWidth));

	m_angularVelocity = m_diffVelocity / Constants.kDrivetrainWidth;
	if (m_diffVelocity == 0) {
	    m_arcRadius = Double.POSITIVE_INFINITY;
	} else {
	    m_arcRadius = (Constants.kDrivetrainWidth * m_sigmaVelocity) / (2 * m_diffVelocity);
	}

	SmartDashboard.putNumber("Yaw derived from encoders (radians)", m_derivedYaw);
	SmartDashboard.putNumber("Angular Velocity", m_angularVelocity);
	SmartDashboard.putNumber("Radius of Curvature", m_arcRadius);
    }

}
