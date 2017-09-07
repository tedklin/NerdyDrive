package com.team687.frc2017.utilities;

/**
 * Skid steer drive kinematic calculations
 * 
 * @author tedlin
 *
 */

public class Kinematics {

    /**
     * @param origPose
     * @param angularVelocity
     * @param arcRadius
     * @param deltaTime
     */
    public static double getNewX(Pose origPose, double angularVelocity, double arcRadius, double deltaTime) {
	return (arcRadius * Math.cos(origPose.getTheta()) * Math.sin(angularVelocity * deltaTime))
		+ (arcRadius * Math.sin(origPose.getTheta()) * Math.cos(angularVelocity * deltaTime)) + origPose.getX()
		- (arcRadius * Math.sin(origPose.getTheta()));
    }

    /**
     * @param origPose
     * @param angularVelocity
     * @param arcRadius
     * @param deltaTime
     */
    public static double getNewY(Pose origPose, double angularVelocity, double arcRadius, double deltaTime) {
	return (arcRadius * Math.sin(origPose.getTheta()) * Math.sin(angularVelocity * deltaTime))
		- (arcRadius * Math.cos(origPose.getTheta()) * Math.cos(angularVelocity * deltaTime)) + origPose.getY()
		+ (arcRadius * Math.cos(origPose.getTheta()));
    }

    /**
     * @param origPose
     * @param angularVelocity
     * @param arcRadius
     * @param deltaTime
     */
    public static double getNewTheta(Pose origPose, double angularVelocity, double arcRadius, double deltaTime) {
	return origPose.getTheta() + (angularVelocity * deltaTime);
    }

    /**
     * @param origPose
     * @param angularVelocity
     * @param arcRadius
     * @param deltaTime
     */
    public static Pose getNewPose(Pose origPose, double angularVelocity, double arcRadius, double deltaTime) {
	return new Pose(getNewX(origPose, angularVelocity, arcRadius, deltaTime),
		getNewY(origPose, angularVelocity, arcRadius, deltaTime),
		getNewTheta(origPose, angularVelocity, arcRadius, deltaTime));
    }

}
