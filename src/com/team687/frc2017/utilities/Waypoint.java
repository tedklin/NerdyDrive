package com.team687.frc2017.utilities;

/**
 * Waypoint for use in 1114's paths
 */

public class Waypoint {

    private static double m_x;
    private static double m_y;
    private static double m_theta;

    public Waypoint(double x, double y, double theta) {
	m_x = x;
	m_y = y;
	m_theta = theta;
    }

    public void set(double x, double y, double theta) {
	m_x = x;
	m_y = y;
	m_theta = theta;
    }

    public double getX() {
	return m_x;
    }

    public double getY() {
	return m_y;
    }

    public double getTheta() {
	return m_theta;
    }

}
