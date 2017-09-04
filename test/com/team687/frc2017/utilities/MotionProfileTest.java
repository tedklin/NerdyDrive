package com.team687.frc2017.utilities;

import static org.junit.Assert.*;
import org.junit.Test;

import com.team687.frc2017.Constants;
import com.team687.frc2017.utilities.MotionProfile;

/**
 * Tests for motion profile generation and integration
 * 
 * @author tedlin
 *
 */

public class MotionProfileTest {

    private static final double kEpsilon = 1E-9;

    private static double cruiseVelocity = 1241;
    private static double maxAccel = 51.56 * 3600;
    private static double maxDecel = -maxAccel;
    private static double targetDistance = 30.93;

    @Test
    public void generateProfileTest() {
	MotionProfile motionProfile = new MotionProfile(cruiseVelocity, maxAccel, maxDecel);
	motionProfile.generateProfile(targetDistance);
	double accelTime = cruiseVelocity / maxAccel;
	assertEquals(accelTime, motionProfile.getAccelTime(), kEpsilon);
	double cruiseTime = (targetDistance - (accelTime * cruiseVelocity)) / cruiseVelocity;
	assertEquals(cruiseTime, motionProfile.getCruiseTime(), kEpsilon);
	double decelTime = -cruiseVelocity / maxDecel;
	assertEquals(decelTime, motionProfile.getDecelTime(), kEpsilon);
	double totalTime = accelTime + cruiseTime + decelTime;
	assertEquals(totalTime, motionProfile.getTotalTime(), 0.001); // small tolerance of 0.001 minutes

	int totalIndex = (int) (totalTime / Constants.kDtInMinutes) + 3; // accounts for overlapped times in for loops
	assertEquals(totalIndex, motionProfile.getTotalPoints(), kEpsilon);

	for (double time = 0; time < accelTime; time += Constants.kDtInMinutes) {
	    int index = (int) (time / Constants.kDtInMinutes);
	    double position = motionProfile.readPosition(index);
	    double velocity = motionProfile.readVelocity(index);

	    assertTrue(position < targetDistance);
	    assertTrue(velocity < cruiseVelocity);
	    if (index > 0) {
		assertTrue(position > motionProfile.readPosition(index - 1));
		assertTrue(velocity > motionProfile.readVelocity(index - 1));
	    }
	}
	for (double time = accelTime; time < (accelTime + cruiseTime); time += Constants.kDtInMinutes) {
	    int index = (int) (time / Constants.kDtInMinutes) + 1;
	    double position = motionProfile.readPosition(index);
	    double velocity = motionProfile.readVelocity(index);

	    assertTrue(position < targetDistance);
	    assertEquals(cruiseVelocity, velocity, kEpsilon);
	    if (index > (int) (accelTime / Constants.kDtInMinutes) + 1) {
		assertTrue(position > motionProfile.readPosition(index - 1));
		assertEquals(velocity, motionProfile.readVelocity(index - 1), kEpsilon);
	    }
	}
	for (double time = (accelTime + cruiseTime); time < totalTime; time += Constants.kDtInMinutes) {
	    int index = (int) (time / Constants.kDtInMinutes) + 2;
	    double position = motionProfile.readPosition(index);
	    double velocity = motionProfile.readVelocity(index);

	    assertTrue(position < targetDistance);
	    assertTrue(velocity <= cruiseVelocity);
	    if (index > ((int) ((accelTime + cruiseTime) / Constants.kDtInMinutes)) + 2) {
		assertTrue(position > motionProfile.readPosition(index - 1));
		assertTrue(velocity < motionProfile.readVelocity(index - 1));
	    }
	}
    }

    @Test
    public void timeConversionTest() {
	double timestampInSeconds = 6.87;
	double timestampInMinutes = timestampInSeconds / 60;
	int indexForSeconds = (int) (timestampInSeconds / Constants.kDt);
	int indexForMinutes = (int) (timestampInMinutes / Constants.kDtInMinutes);
	assertEquals(indexForSeconds, indexForMinutes, kEpsilon);
    }

}
