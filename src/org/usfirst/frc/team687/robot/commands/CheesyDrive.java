package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Robot;
import org.usfirst.frc.team687.robot.Constants;
import org.usfirst.frc.team687.robot.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.Command;

/**
 * An implementation of 254's CheesyDrive
 */

public class CheesyDrive extends Command{
	
	private double m_quickStopAccumulator;
	
	public CheesyDrive() {
		//subsystem requirements
		requires(Robot.drive);
	}
	
	@Override
	protected void initialize() {
		Robot.drive.stopDrive();
	}

	@Override
	protected void execute() {
		double rightPow, leftPow;
		boolean isQuickTurn = Robot.oi.getQuickTurn();
		
	    double wheel = Robot.drive.handleDeadband(Robot.oi.driveJoyRight.getX(), Constants.kWheelDeadband);
	    double throttle = -Robot.drive.handleDeadband(Robot.oi.driveJoyLeft.getY(), Constants.kThrottleDeadband);
	    
	    rightPow = leftPow = throttle;
	    
	    double sensitivity;
	    if (Robot.drive.isHighGear()) {
	    	sensitivity = Constants.kSensitivityHigh;
	    } else {
	    	sensitivity = Constants.kSensitivityLow;
	    }
	    
	    double angularPow;
	    if (isQuickTurn) {
	    	angularPow = wheel;
            m_quickStopAccumulator = (1 - Constants.kDriveAlpha) * m_quickStopAccumulator + Constants.kDriveAlpha * NerdyMath.limit(wheel, 1.0) * 2;
            throttle = 0;
	    } else {
	    	angularPow = Math.abs(throttle) * wheel * sensitivity - m_quickStopAccumulator;
            if (m_quickStopAccumulator > 1) {
            	m_quickStopAccumulator -= 1;
            } else if (m_quickStopAccumulator < -1) {
            	m_quickStopAccumulator += 1;
            } else {
            	m_quickStopAccumulator = 0;
            }
	    }
	    
	    leftPow += angularPow;
	    rightPow -= angularPow;
	    
	    double[] pow = {leftPow, rightPow};
	    pow = NerdyMath.normalize(pow, false);
		Robot.drive.setPower(pow[0], pow[1]);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.drive.stopDrive();
	}

	@Override
	protected void interrupted() {
		end();
	}

}

