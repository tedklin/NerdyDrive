package com.team687.frc2017.utilities;

/**
 * Pose of robot (x, y, theta)
 * 
 * @author tedlin
 *
 */

public class Pose {

    private double m_x;
    private double m_y;
    private double m_theta;

    public Pose(double x, double y, double theta) {
	m_x = x;
	m_y = y;
	m_theta = theta;
    }

    public void setX(double x) {
	m_x = x;
    }

    public void setY(double y) {
	m_y = y;
    }

    public void setTheta(double theta) {
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

    /**
     * @return Pose in column matrix form
     */
    public Matrix getMatrix() {
	double[][] A = { { m_x }, { m_y }, { m_theta } };
	return new Matrix(A);
    }

}
