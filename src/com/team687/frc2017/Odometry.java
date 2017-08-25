package com.team687.frc2017;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A simple trigonometric way of finding the x and y coordinates of a robot.
 * 
 * @author tedlin
 *
 */

public class Odometry {

    private double m_x;
    private double m_y;

    private double m_absoluteX;
    private double m_absoluteY;

    private double m_theta;
    private double m_hypotenuse;
    private double m_lastHypotenuse;

    public Odometry() {
	m_absoluteX = 0;
	m_absoluteY = 0;
	m_theta = 0;

	m_lastHypotenuse = 0;
    }

    public void update() {
	m_hypotenuse = Robot.drive.getDrivetrainTicks() - m_lastHypotenuse;
	m_theta = Robot.drive.getCurrentYaw();

	m_x = Math.sin(m_theta) * m_hypotenuse;
	m_y = Math.cos(m_theta) * m_hypotenuse;

	m_absoluteX += m_x;
	m_absoluteY += m_y;

	SmartDashboard.putNumber("Absolute X", m_absoluteX);
	SmartDashboard.putNumber("Absolute Y", m_absoluteY);

	m_lastHypotenuse = m_hypotenuse;
    }

}
