package com.team687.frc2017.utilities;

import com.team687.frc2017.Constants;

/**
 * Useful math functions
 *
 * @author Wesley
 * @author tedfoodlin
 *
 */

public class NerdyMath {
	
    public static double radsToDeg(double deg)	{
    	// Native algorithm
    	return deg * 360 / (2 * Math.PI);
    }
    
    public static double degToRads(double rads)	{
    	// Native algorithm
    	return rads * (2 * Math.PI) / 360;
    }
    
	public static int feetToTicks(double feet) {
		return (int)(feet/(Math.PI * Math.pow(Constants.kWheelDiameter/2, 2)))*4096;
	}
    
    /**
     * Limits the given input to the given magnitude. 
     * 
     * @param value
     * @param absolute value limit
     * @return thresholded value
     */
    public static double limit(double v, double limit) {
        return (Math.abs(v) < limit) ? v : limit * (v < 0 ? -1 : 1);
    }
    
    /**
     * Normalizes the array to -1 or 1
     * 
     * @param values	The values
     * @param scaleUp	True makes the values scale no matter what
     * @return			The normalized values
     */
    public static double[] normalize(double[] values, boolean scaleUp){
        double[] normalizedValues = new double[values.length];
        double max = Math.max(Math.abs(values[0]), Math.abs(values[1]));
        for(int i = 2; i < values.length; i++){
            max = Math.max(Math.abs(values[i]), max);
        }
        if(max < 1 && scaleUp == false) {
            for(int i = 0; i < values.length; i++){
                normalizedValues[i] = values[i];
            }
        }   else    {
            for(int i = 0; i < values.length; i++){
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
	
}