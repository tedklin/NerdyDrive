package com.team687.frc2017.utilities;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Matrix operations unit testing
 * 
 * @author tedlin
 *
 */

@RunWith(Parameterized.class)
public class TransformTest {

    private static final double kEpsilon = 1E-9;

    @SuppressWarnings("rawtypes")
    @Parameters
    public static Collection testCases() {
	// consists of {x, y, theta (in radians), angular velocity, and curvature radius
	return Arrays.asList(new double[][] {});
    }

    private double m_x;
    private double m_y;
    private double m_theta;
    private double m_angularVelocity;
    private double m_radius;
    private double m_dt;

    public TransformTest(double[] rawVal) {
	m_x = rawVal[0];
	m_y = rawVal[1];
	m_theta = rawVal[2];
	m_angularVelocity = rawVal[3];
	m_radius = rawVal[4];
    }

    @Test
    public void findNewPoseTest() {
	double[][] origPos = { { 1, 0, 0, m_x }, { 0, 1, 0, m_y }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
	double[][] currentPose = { { Math.cos(m_theta), -Math.sin(m_theta), 0, 0 },
		{ Math.sin(m_theta), Math.cos(m_theta), 0, 0 }, { 0, 0, 1, m_theta }, { 0, 0, 0, 1 } };
	// instantaneous center of curvature
	double[][] ICC = { { 1, 0, 0, 0 }, { 0, 1, 0, m_radius }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
	double[][] rotateICC = { { Math.cos(m_angularVelocity * m_dt), -Math.sin(m_angularVelocity * m_dt), 0, 0 },
		{ Math.sin(m_angularVelocity * m_dt), Math.cos(m_angularVelocity * m_dt), 0, 0 },
		{ 0, 0, 1, m_angularVelocity * m_dt }, { 0, 0, 0, 1 } };
	double[][] translateOut = { { 1, 0, 0, 0 }, { 0, 1, 0, -m_radius }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };

	Matrix RT_P = new Matrix(origPos);
	Matrix PT_P = new Matrix(currentPose);
	Matrix PT_ICC = new Matrix(ICC);
	Matrix ICCT_ICC = new Matrix(rotateICC);
	Matrix ICCT_N = new Matrix(translateOut);

	// find new pose
	Matrix RT_N = ICCT_N.multiplyBy(ICCT_ICC.multiplyBy(PT_ICC.multiplyBy((PT_P.multiplyBy(RT_P)))));

	double newX = (m_radius * Math.cos(m_theta) * Math.sin(m_angularVelocity * m_dt))
		+ (m_radius * Math.sin(m_theta) * Math.cos(m_angularVelocity * m_dt)) + m_x
		- (m_radius * Math.sin(m_theta));
	double newY = (m_radius * Math.sin(m_theta) * Math.sin(m_angularVelocity * m_dt))
		- (m_radius * Math.cos(m_theta) * Math.cos(m_angularVelocity * m_dt)) + m_y
		+ (m_radius * Math.cos(m_theta));
	double newTheta = m_theta + (m_angularVelocity * m_dt);

	assertEquals(RT_N.getData()[0][4], newX, kEpsilon);
	assertEquals(RT_N.getData()[1][4], newY, kEpsilon);
	assertEquals(RT_N.getData()[2][4], newTheta, kEpsilon);
    }

}
