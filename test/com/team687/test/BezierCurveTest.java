package com.team687.test;

import static org.junit.Assert.*;
import org.junit.Test;

import com.team687.frc2017.Constants;
import com.team687.frc2017.utilities.*;

/**
 * Bezier curve unit testing
 * 
 * @author tedlin
 *
 */

public class BezierCurveTest {

    private static final double kEpsilon = 1E-9;

    @Test
    public void testBezierInput() {
	BezierCurve bezierCurve = new BezierCurve(0, 0, 0, 10, 5, 0, 5, 10);
	double[] xVal = { 0, 0, 5, 5 };
	double[] yVal = { 0, 10, 0, 10 };
	assertArrayEquals(xVal, bezierCurve.getXParam(), 0);
	assertArrayEquals(yVal, bezierCurve.getYParam(), 0);
    }

    @Test
    public void testBezierSize() {
	BezierCurve bezierCurve = new BezierCurve(0, 0, 0, 10, 5, 0, 5, 10);
	bezierCurve.calculateBezier();
	assertEquals(Constants.kBezierStep, bezierCurve.getXPoints().size(), kEpsilon);
	assertEquals(Constants.kBezierStep, bezierCurve.getYPoints().size(), kEpsilon);
	assertEquals(Constants.kBezierStep, bezierCurve.getArcLength().size(), kEpsilon);
	assertEquals(Constants.kBezierStep, bezierCurve.getHeading().size(), kEpsilon);
    }

    @Test
    public void testEndPoints() {
	BezierCurve bezierCurve = new BezierCurve(0, 0, 0, 10, 5, 0, 5, 10);
	bezierCurve.calculateBezier();
	assertEquals(0, bezierCurve.getXPoints().get(0), kEpsilon);
	assertEquals(0, bezierCurve.getYPoints().get(0), kEpsilon);
	assertEquals(5, bezierCurve.getXPoints().get(bezierCurve.getXPoints().size() - 1), kEpsilon);
	assertEquals(10, bezierCurve.getYPoints().get(bezierCurve.getYPoints().size() - 1), kEpsilon);
    }

}
