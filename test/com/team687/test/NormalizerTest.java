package com.team687.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.team687.frc2017.utilities.*;

/**
 * Test for normalizer
 * 
 * @author tedlin
 *
 */

@RunWith(Parameterized.class)
public class NormalizerTest {

    @SuppressWarnings("rawtypes")
    @Parameters
    public static Collection testCases() {
	return Arrays.asList(new double[][] { { 0.971, 0.987 }, { 0.971, 1.678 }, { 1.678, 0.971 }, { -0.971, 1.678 },
		{ 0.971, -1.678 }, { 1.678, 2.54 }, { 2.54, 1.678 }, { 687, 254 } });
    }

    private double[] m_rawVal;

    public NormalizerTest(double[] rawVal) {
	m_rawVal = rawVal;
    }

    private static final double kEpsilon = 1E-9;

    @Test
    public void normalizeTest() {
	double[] normalizedVal = NerdyMath.normalize(m_rawVal, false);
	for (int i = 0; i < normalizedVal.length - 1; i++) {
	    assertTrue(Math.abs(normalizedVal[i]) <= 1.0);
	    assertTrue(isNegative(m_rawVal[i]) == isNegative(normalizedVal[i]));
	}
	assertEquals((m_rawVal[0] / m_rawVal[1]), (normalizedVal[0] / normalizedVal[1]), kEpsilon);
    }

    public static boolean isNegative(double d) {
	return Double.doubleToRawLongBits(d) < 0;
    }

}
