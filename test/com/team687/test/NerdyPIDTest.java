package com.team687.test;

import static org.junit.Assert.*;
import org.junit.Test;

import com.team687.frc2017.utilities.*;

import edu.wpi.first.wpilibj.util.BoundaryException;

/**
 * NerdyPID unit testing
 *
 * @author tedfoodlin
 *
 */

public class NerdyPIDTest {
	
    private static final double kEpsilon = 1E-9;
    
    @Test
    public void testGyroMode() {
    		NerdyPID testRotPID = new NerdyPID(1, 0, 0);
    		testRotPID.setDesired(0);
    		assertEquals(0, testRotPID.getDesired(), kEpsilon);
    		assertFalse(testRotPID.isGyro());
    		
    		testRotPID.calculate(-270);
    		assertEquals(270, testRotPID.getError(), kEpsilon);
    		
    		testRotPID.setGyro(true);
    		assertTrue(testRotPID.isGyro());
    		
    		testRotPID.calculate(-270);
    		assertEquals(-90, testRotPID.getError(), kEpsilon);
    }
	
	@Test
	public void testOutputRange() {
		NerdyPID testPID = new NerdyPID(0.0687, 0, 0);
		testPID.setDesired(0);
		assertEquals(1.0, testPID.calculate(-687), kEpsilon);
		assertEquals(-1.0, testPID.calculate(687), kEpsilon);
		assertEquals(0, testPID.calculate(0), kEpsilon);
		
		testPID.setOutputRange(0.254, 0.971);
		assertEquals(0.971, testPID.calculate(-687), kEpsilon);
		assertEquals(-0.971, testPID.calculate(687), kEpsilon);
		assertEquals(0.254, testPID.calculate(-0.001), kEpsilon);
		assertEquals(-0.254, testPID.calculate(0.001), kEpsilon);
	}
	
	@Test(expected = BoundaryException.class)
	public void testOutputRangeException() {
		NerdyPID testPID = new NerdyPID(0, 0, 0);
		testPID.setOutputRange(0.971,  0.254);
	}

}
