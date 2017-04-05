package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Tank drive with squared inputs
 */

public class TankDrive extends Command{

    public TankDrive() {
    	// subsystem dependencies
        requires(Robot.drive);
    }

	@Override
	protected void initialize() {
		Robot.drive.stopDrive();
	}

	@Override
	protected void execute() {
		double leftPow = Robot.drive.squareInput(Robot.oi.getDriveJoyL());
		double rightPow = Robot.drive.squareInput(Robot.oi.getDriveJoyR());
		Robot.drive.setPower(leftPow, rightPow);
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
