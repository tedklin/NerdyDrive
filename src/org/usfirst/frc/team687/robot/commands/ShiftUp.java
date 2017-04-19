package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Shift to high gear
 */

public class ShiftUp extends Command {
	
	public ShiftUp() {
		// subsystem dependencies
		requires(Robot.drive);
	}

	@Override
	protected void initialize() {
		SmartDashboard.putString("Current Command", "ShiftUp");
		Robot.drive.shiftUp();
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
