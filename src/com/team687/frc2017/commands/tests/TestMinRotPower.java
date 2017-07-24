package com.team687.frc2017.commands.tests;

import com.team687.frc2017.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A test to find the minimum power (0 to 1.0) it takes for the drivetrain to
 * start rotating This value can be then be used as the minimum output of any
 * PID turning on the drivetrain
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
	SmartDashboard.putNumber("Turning Power (test, editable)", 0);
	Robot.drive.stopDrive();
	Robot.drive.shiftDown();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void execute() {
	double power = SmartDashboard.getNumber("Turning Power (test, editable)");
	Robot.drive.setPower(power, -power);
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
