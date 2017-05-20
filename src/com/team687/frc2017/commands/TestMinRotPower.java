package com.team687.frc2017.commands;

import com.team687.frc2017.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A simple test to find the minimum power (0 to 1.0) it takes for the drivetrain to start rotating
 * This value will be then be used as the minimum output of any PID turning on the drivetrain
 *
 * @author tedfoodlin
 *
 */

public class TestMinRotPower extends Command {
	
	public TestMinRotPower() {
		// subsystem dependencies
		requires(Robot.drive);
	}
	
	@Override
	protected void initialize() {
		SmartDashboard.putString("Current Command", "TestMinRotPower");
		Robot.drive.stopDrive();
	}
	
	@Override
	protected void execute() {
		SmartDashboard.putNumber("Current Turning Power", Robot.oi.getThrottleR());
		Robot.drive.setPower(Robot.oi.getThrottleR(), -Robot.oi.getThrottleR());
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
