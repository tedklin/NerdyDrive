package com.team687.frc2017.utilities;

/**
 * Finds new pose of robot based on original pose, angular velocity, curvature
 * radius, and delta_time
 * 
 * @author tedlin
 *
 */

public class Transform {

    private Pose m_origPose;
    private double m_angularVelocity;
    private double m_radius;
    private double m_dt;

    public Transform(Pose origPose, double angularVelocity, double radius, double dt) {
	m_origPose = origPose;
	m_angularVelocity = angularVelocity;
	m_radius = radius;
	m_dt = dt;
    }

    public double getNewX() {
	return (m_radius * Math.cos(m_origPose.getTheta()) * Math.sin(m_angularVelocity * m_dt))
		+ (m_radius * Math.sin(m_origPose.getTheta()) * Math.cos(m_angularVelocity * m_dt)) + m_origPose.getX()
		- (m_radius * Math.sin(m_origPose.getTheta()));
    }

    public double getNewY() {
	return (m_radius * Math.sin(m_origPose.getTheta()) * Math.sin(m_angularVelocity * m_dt))
		- (m_radius * Math.cos(m_origPose.getTheta()) * Math.cos(m_angularVelocity * m_dt)) + m_origPose.getY()
		+ (m_radius * Math.cos(m_origPose.getTheta()));
    }

    public double getNewTheta() {
	return m_origPose.getTheta() + (m_angularVelocity * m_dt);
    }

    public Pose getNewPose() {
	return new Pose(getNewX(), getNewY(), getNewTheta());
    }
}
