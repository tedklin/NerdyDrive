package com.team687.frc2017.utilities;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.team687.frc2017.Constants;

/**
 * Bezier curve/path unit testing
 * 
 * @author tedlin
 *
 */

@RunWith(Parameterized.class)
public class BezierCurveTest {

    private final static double[] EmptyPath = { 0, 0, 0, 0, 0, 0, 0, 0 };
    private final static double[] ExamplePath1 = { 0, 0, -39000, 40000, -39000, 64000, -39000, 101000 };
    private final static double[] ExamplePath2 = { 0, 0, 0, 100300, 0, 100300, -48000, 100900 };
    private final static double[] ExamplePath3 = { 0, 0, 0, 92278.44, 0, 92278.44, 74403.76, 134999.94 };
    private final static double[] ExamplePath4 = { 74403.7644, 134999.94, 0, 86582.24, 0, 198227.76, -33835.428,
	    -117615.1376 };

    @SuppressWarnings("rawtypes")
    @Parameters
    public static Collection testCases() {
	return Arrays.asList(new double[][] { EmptyPath, ExamplePath1, ExamplePath2, ExamplePath3, ExamplePath4 });
    }

    private double[] m_path;

    public BezierCurveTest(double[] path) {
	m_path = path;
    }

    private static final double kEpsilon = 1E-9;

    @Test
    public void testBezierInput() {
	BezierCurve bezierCurve = new BezierCurve(0, 0, 0, 100300, 0, 100300, -48000, 100300);
	double[] xVal = { 0, 0, 0, -48000 };
	double[] yVal = { 0, 100300, 100300, 100300 };
	assertArrayEquals(xVal, bezierCurve.getXParam(), 0);
	assertArrayEquals(yVal, bezierCurve.getYParam(), 0);
    }

    @Test
    public void testBezierSize() {
	BezierCurve bezierCurve = new BezierCurve(m_path);
	bezierCurve.calculateBezier();
	assertEquals(Constants.kBezierStep + 1, bezierCurve.getXPoints().size(), 1);
	assertEquals(Constants.kBezierStep + 1, bezierCurve.getYPoints().size(), 1);
	assertEquals(Constants.kBezierStep + 1, bezierCurve.getArcLength().size(), 1);
	assertEquals(Constants.kBezierStep + 1, bezierCurve.getHeading().size(), 1);
    }

    @Test
    public void testEndPoints() {
	BezierCurve bezierCurve = new BezierCurve(0, 0, 0, 100300, 0, 100300, -48000, 100300);
	bezierCurve.calculateBezier();
	assertEquals(0, bezierCurve.getXPoints().get(0), kEpsilon);
	assertEquals(0, bezierCurve.getYPoints().get(0), kEpsilon);
	assertEquals(-48000, bezierCurve.getXPoints().get(bezierCurve.getXPoints().size() - 1), kEpsilon);
	assertEquals(100300, bezierCurve.getYPoints().get(bezierCurve.getYPoints().size() - 1), kEpsilon);
    }

    @Test
    public void testPathFollower() {
	BezierCurve bezierCurve = new BezierCurve(m_path);
	bezierCurve.calculateBezier();

	ArrayList<Double> heading = bezierCurve.getHeading();
	int counter = 1;
	double baseStraightPower = 1; // always equal to 1
	for (counter = 1; counter < heading.size(); counter++) {
	    double headingError = heading.get(counter) - heading.get(counter - 1);
	    double rotPower = Constants.kBezierRotHighGearPGains.getP() * headingError;
	    double straightPower = baseStraightPower / (Math.abs(headingError) * Constants.kStraightPowerAdjuster);

	    double sign = Math.signum(straightPower);
	    if (Math.abs(straightPower) > Constants.kBezierDistHighGearPGains.getMaxPower()) {
		straightPower = Constants.kBezierDistHighGearPGains.getMaxPower() * sign;
	    }

	    double leftPower = straightPower + rotPower;
	    double rightPower = straightPower - rotPower;

	    System.out.println("Left Power: " + leftPower);
	    System.out.println("Right Power: " + rightPower);

	    assertTrue(0 <= leftPower && leftPower <= 1.0);
	    assertTrue(0 <= rightPower && rightPower <= 1.0);
	}
    }

}
