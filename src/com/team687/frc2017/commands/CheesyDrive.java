package com.team687.frc2017.commands;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An implementation of 254's CheesyDrive
 */

public class CheesyDrive extends Command {

    private double m_quickStopAccumulator;

    public CheesyDrive() {
	// subsystem requirements
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "CheesyDrive");
	Robot.drive.stopDrive();
    }

    @Override
    protected void execute() {
	double rightPow, leftPow;
	boolean isQuickTurn = Robot.oi.getQuickTurn();

	double wheel = Robot.drive.handleDeadband(Robot.oi.driveJoyRight.getX(), Constants.kJoystickDeadband);
	double throttle = -Robot.drive.handleDeadband(Robot.oi.driveJoyLeft.getY(), Constants.kJoystickDeadband);

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
	    m_quickStopAccumulator = (1 - Constants.kDriveAlpha) * m_quickStopAccumulator
		    + Constants.kDriveAlpha * NerdyMath.limit(wheel, 1.0) * 2;
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

	double[] pow = { leftPow, rightPow };
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
