package com.team687.frc2017.commands.tests;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A test for rotational PID output without actuating anything
 *
 * @author tedlin
 *
 */

public class TestRotPID extends Command {

    public TestRotPID() {
	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "TestRotPID");

	SmartDashboard.putNumber("Desired Yaw (test, editable)", 0);
	SmartDashboard.putNumber("Rot P (test, editable)", Constants.kRotLowGearPGains.getP());
	SmartDashboard.putNumber("Rot Min Power (test, editable)", Constants.kRotLowGearPGains.getMinPower());
	SmartDashboard.putNumber("Rot Max Power (test, editable)", Constants.kRotLowGearPGains.getMaxPower());

	Robot.drive.stopDrive();
	Robot.drive.shiftUp();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void execute() {
	double actualAngle = Robot.drive.getCurrentYaw();
	SmartDashboard.putNumber("Actual Yaw (test)", actualAngle);
	double desiredAngle = SmartDashboard.getNumber("Desired Yaw (test, editable)");

	double kP = SmartDashboard.getNumber("Rot P (test, editable)");
	double kMinRotPower = SmartDashboard.getNumber("Rot Min Power (test, editable)");
	double kMaxRotPower = SmartDashboard.getNumber("Rot Max Power (test, editable)");

	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	double error = desiredAngle - robotAngle;
	error = (error > 180) ? error - 360 : error;
	error = (error < -180) ? error + 360 : error;
	SmartDashboard.putNumber("Rot PID Error (test)", error);

	double power = kP * error;
	power = NerdyMath.threshold(power, kMinRotPower, kMaxRotPower);
	SmartDashboard.putNumber("Rot PID output (test)", power);

	Robot.drive.stopDrive();
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
