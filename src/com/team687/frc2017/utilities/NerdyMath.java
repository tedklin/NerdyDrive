package com.team687.frc2017.utilities;

import com.team687.frc2017.Constants;

/**
 * Useful math functions
 *
 * @author Wesley
 * @author tedlin
 *
 */

public class NerdyMath {

    public static double radsToDeg(double deg) {
	// Native algorithm
	return deg * 360 / (2 * Math.PI);
    }

    public static double degToRads(double rads) {
	// Native algorithm
	return rads * (2 * Math.PI) / 360;
    }

    public static double inchesToRotations(double inches) {
	return (int) (inches / (Math.PI * Constants.kWheelDiameter));
    }

    public static int rotationsToTicks(double rotations) {
	return (int) (rotations * 4096);
    }

    public static int inchesToTicks(double inches) {
	return (int) (rotationsToTicks(inchesToRotations(inches)));
    }

    public static double ticksToRotations(int ticks) {
	return ticks / rotationsToTicks(1);
    }

    public static double rotationsToInches(double rotations) {
	return rotations / inchesToRotations(1);
    }

    public static double ticksToInches(int ticks) {
	return rotationsToInches(ticksToRotations(ticks));
    }

    /**
     * Limits the given input to the given magnitude.
     * 
     * @param value
     * @param absolute
     *            value limit
     * @return thresholded value
     */
    public static double limit(double v, double limit) {
	return (Math.abs(v) < limit) ? v : limit * (v < 0 ? -1 : 1);
    }

    /**
     * Add joystick sensitivity (Ether method) If throttle is 0, joystick output is
     * linear If throttle is 1, joystick output is cubic You can adjust in between
     * 
     * @param input
     * @param throttle
     */
    public static double addSensitivity(double input, double throttle) {
	double b = Constants.kJoystickDeadband;
	double a = throttle;
	double output = 0;
	if (input >= 0) {
	    output = b + (1 - b) * (a * Math.pow(input, 3) + (1 - a) * input);
	} else if (input < 0) {
	    output = -b + (1 - b) * (a * Math.pow(input, 3) + (1 - a) * input);
	}
	return output;
    }

    /**
     * Normalizes the array to -1 or 1
     * 
     * @param values
     *            The values
     * @param scaleUp
     *            True makes the values scale no matter what
     * @return The normalized values
     */
    public static double[] normalize(double[] values, boolean scaleUp) {
	double[] normalizedValues = new double[values.length];
	double max = Math.max(Math.abs(values[0]), Math.abs(values[1]));
	for (int i = 2; i < values.length; i++) {
	    max = Math.max(Math.abs(values[i]), max);
	}
	if (max < 1 && scaleUp == false) {
	    for (int i = 0; i < values.length; i++) {
		normalizedValues[i] = values[i];
	    }
	} else {
	    for (int i = 0; i < values.length; i++) {
		normalizedValues[i] = values[i] / max;
	    }
	}

	return normalizedValues;
    }

    /**
     * Bound an angle (in degrees) to 360 degrees.
     */
    public static double boundAngle(double angleDegrees) {
	angleDegrees = (360 - angleDegrees) % 360;
	return angleDegrees;
    }

    /**
     * Bound angle error
     */
    public static double boundAngleError(double error) {
	error = (error > 180) ? error - 360 : error;
	error = (error < -180) ? error + 360 : error;
	return error;
    }

}