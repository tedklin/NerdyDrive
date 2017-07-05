package com.team687.test;

import static org.junit.Assert.*;
import org.junit.Test;

import com.team687.frc2017.utilities.*;

/**
 * NerdyPID unit test
 *
 * @author tedfoodlin
 *
 */

public class NerdyPIDTest {
	
    private static final double kEpsilon = 1E-9;
	
	@Test
	public void testOutputRange() {
		NerdyPID nerdyTestPID = new NerdyPID(0.0687, 0, 0);
		nerdyTestPID.setOutputRange(0.254, 0.971);
		nerdyTestPID.setDesired(0);
		
		assertEquals(0.971, nerdyTestPID.calculate(-687), kEpsilon);
		assertEquals(-0.971, nerdyTestPID.calculate(687), kEpsilon);
		assertEquals(0.254, nerdyTestPID.calculate(-0.001), kEpsilon);
		assertEquals(-0.254, nerdyTestPID.calculate(0.001), kEpsilon);
	}

}
