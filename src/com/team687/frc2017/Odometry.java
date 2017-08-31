package com.team687.frc2017;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Odometry of drivebase
 * 
 * @author tedlin
 *
 */

public class Odometry {

    private static Odometry m_instance = null;

    private double m_x;
    private double m_y;

    private double m_absoluteX;
    private double m_absoluteY;

    private double m_gyroYaw;
    private double m_derivedYaw;
    private double m_angularVelocity;

    private double m_leftDistance;
    private double m_rightDistance;
    private double m_leftSpeed;
    private double m_rightSpeed;

    private double m_diffDistance;
    private double m_diffVelocity;

    private double m_hypotenuse;
    private double m_lastHypotenuse;

    public static Odometry getInstance() {
	if (m_instance == null) {
	    m_instance = new Odometry();
	}
	return m_instance;
    }

    protected Odometry() {
	m_absoluteX = 0;
	m_absoluteY = 0;
	m_gyroYaw = 0;

	m_lastHypotenuse = 0;
    }

    public void update() {
	// raw sensor readings
	m_leftDistance = Robot.drive.getLeftTicks();
	m_rightDistance = Robot.drive.getRightTicks();
	m_leftSpeed = Robot.drive.getLeftTicksSpeed();
	m_rightSpeed = Robot.drive.getRightTicksSpeed();
	m_hypotenuse = Robot.drive.getDrivetrainTicks() - m_lastHypotenuse;
	m_gyroYaw = Robot.drive.getCurrentYaw();

	// calculations
	m_x = Math.sin(m_gyroYaw) * m_hypotenuse;
	m_y = Math.cos(m_gyroYaw) * m_hypotenuse;

	m_absoluteX += m_x;
	m_absoluteY += m_y;

	SmartDashboard.putNumber("Absolute X", m_absoluteX);
	SmartDashboard.putNumber("Absolute Y", m_absoluteY);

	m_diffDistance = m_leftDistance - m_rightDistance;
	m_derivedYaw = 90 - (Math.acos(m_diffDistance / Constants.kDrivebaseWidth));
	SmartDashboard.putNumber("Yaw derived from encoders", m_derivedYaw);

	m_diffVelocity = m_leftSpeed - m_rightSpeed;
	m_angularVelocity = m_diffVelocity / Constants.kDrivebaseWidth;
	SmartDashboard.putNumber("Angular Velocity", m_angularVelocity);

	m_lastHypotenuse = m_hypotenuse;
    }

}
