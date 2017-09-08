package com.team687.frc2017.utilities;

import com.team687.frc2017.Constants;

/**
 * Skid steer drive kinematic calculations
 * 
 * @author tedlin
 *
 */

public class Kinematics {

    /**
     * @param origPose
     * @param rightVelocity
     * @param leftVelocity
     * @param deltaTime
     */
    public static double getNewX(Pose origPose, double rightVelocity, double leftVelocity, double deltaTime) {
	double angularVelocity = getAngularVelocity(rightVelocity, leftVelocity);
	double arcRadius = getCurvatureRadius(rightVelocity, leftVelocity);
	double newX;
	if (rightVelocity != leftVelocity) {
	    newX = (arcRadius * Math.cos(origPose.getTheta()) * Math.sin(angularVelocity * deltaTime))
		    + (arcRadius * Math.sin(origPose.getTheta()) * Math.cos(angularVelocity * deltaTime))
		    + origPose.getX() - (arcRadius * Math.sin(origPose.getTheta()));
	} else {
	    newX = (Math.sin(origPose.getTheta()) * rightVelocity) * deltaTime;
	}
	return newX;
    }

    /**
     * @param origPose
     * @param rightVelocity
     * @param leftVelocity
     * @param deltaTime
     */
    public static double getNewY(Pose origPose, double rightVelocity, double leftVelocity, double deltaTime) {
	double angularVelocity = getAngularVelocity(rightVelocity, leftVelocity);
	double arcRadius = getCurvatureRadius(rightVelocity, leftVelocity);
	double newY;
	if (rightVelocity != leftVelocity) {
	    newY = (arcRadius * Math.sin(origPose.getTheta()) * Math.sin(angularVelocity * deltaTime))
		    - (arcRadius * Math.cos(origPose.getTheta()) * Math.cos(angularVelocity * deltaTime))
		    + origPose.getY() + (arcRadius * Math.cos(origPose.getTheta()));
	} else {
	    newY = (Math.cos(origPose.getTheta()) * rightVelocity) * deltaTime;
	}
	return newY;
    }

    /**
     * @param origPose
     * @param rightVelocity
     * @param leftVelocity
     * @param deltaTime
     */
    public static double getNewTheta(Pose origPose, double rightVelocity, double leftVelocity, double deltaTime) {
	double angularVelocity = getAngularVelocity(rightVelocity, leftVelocity);
	return origPose.getTheta() + (angularVelocity * deltaTime);
    }

    /**
     * @param origPose
     * @param rightVelocity
     * @param leftVelocity
     * @param deltaTime
     */
    public static Pose getNewPose(Pose origPose, double rightVelocity, double leftVelocity, double deltaTime) {
	return new Pose(getNewX(origPose, rightVelocity, leftVelocity, deltaTime),
		getNewY(origPose, rightVelocity, leftVelocity, deltaTime),
		getNewTheta(origPose, rightVelocity, leftVelocity, deltaTime));
    }

    /**
     * @param rightVelocity
     * @param leftVelocity
     */
    public static double getAngularVelocity(double rightVelocity, double leftVelocity) {
	double diffVelocity = rightVelocity - leftVelocity;
	return diffVelocity / Constants.kDrivetrainWidth;
    }

    /**
     * @param rightVelocity
     * @param leftVelocity
     */
    public static double getCurvatureRadius(double rightVelocity, double leftVelocity) {
	double diffVelocity = rightVelocity - leftVelocity;
	double sigmaVelocity = rightVelocity + leftVelocity;
	if (diffVelocity == 0) {
	    return Double.POSITIVE_INFINITY;
	} else {
	    return (Constants.kDrivetrainWidth * sigmaVelocity) / (2 * diffVelocity);
	}
    }

    /**
     * @param rightVelocity
     * @param leftVelocity
     * @param deltaTime
     */
    public static double getDerivedDeltaYaw(double rightVelocity, double leftVelocity, double deltaTime) {
	double diffVelocity = rightVelocity - leftVelocity;
	return diffVelocity * deltaTime / Constants.kDrivetrainWidth;
    }

}
