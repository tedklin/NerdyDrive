package com.team687.frc2017.commands.tests;

import com.team687.frc2017.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Test sensor values without actuating anything
 *
 * @author tedfoodlin
 *
 */

public class TestSensors extends Command {

    public TestSensors() {
	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "TestSensors");
	Robot.drive.stopDrive();
	Robot.drive.shiftUp();
	SmartDashboard.putNumber("Historical Yaw Timestamp (test, editable)", 0);
	SmartDashboard.putNumber("Desired Yaw (test, editable)", 0);
	SmartDashboard.putNumber("Desired Left Pos (test, editable)", 0);
	SmartDashboard.putNumber("Desired Right Pos (test, editable)", 0);
	SmartDashboard.putNumber("Historical Yaw Timestamp (test, editable)", 0);
	SmartDashboard.putNumber("NavX lookback time (test, editable)", 0);

    }

    @SuppressWarnings("deprecation")
    @Override
    protected void execute() {
	double desiredAngle = SmartDashboard.getNumber("Desired Yaw (test, editable)");
	double desiredLeftPos = SmartDashboard.getNumber("Desired Left Pos (test, editable)");
	double desiredRightPos = SmartDashboard.getNumber("Desired Right Pos (test, editable)");
	// double actualAngle = NerdyMath.boundAngle(Robot.drive.getCurrentYaw());
	double actualAngle = Robot.drive.getCurrentYaw();

	// SmartDashboard.putNumber("Actual Yaw (test)", actualAngle);
	// SmartDashboard.putNumber("Actual Left Pos (test)",
	// Robot.drive.getLeftPosition());
	// SmartDashboard.putNumber("Actual Right Pos (test)",
	// Robot.drive.getRightPosition());
	// SmartDashboard.putNumber("Actual Left Speed (test)",
	// Robot.drive.getLeftSpeed());
	// SmartDashboard.putNumber("Actual Right Speed (test)",
	// Robot.drive.getRightSpeed());
	// SmartDashboard.putNumber("Actual Left Pos Ticks (test)",
	// Robot.drive.getLeftTicks());
	// SmartDashboard.putNumber("Actual Right Pos Ticks (test)",
	// Robot.drive.getRightTicks());
	// SmartDashboard.putNumber("Actual Left Speed Ticks (test)",
	// Robot.drive.getLeftTicksSpeed());
	// SmartDashboard.putNumber("Actual Right Speed Ticks (test)",
	// Robot.drive.getRightTicksSpeed());

	double timestamp = SmartDashboard.getNumber("Historical Yaw Timestamp (test, editable)");
	long timestamp_ = (long) timestamp;
	SmartDashboard.putNumber("Historical Yaw Timestamp (test, noneditable (long))", timestamp_);
	SmartDashboard.putNumber("Historical Yaw (test)", Robot.drive.getHistoricalYaw(timestamp_));
	double lookbackTime = SmartDashboard.getNumber("NavX lookback time (test, editable)");
	long lookbackTime_ = (long) lookbackTime;
	SmartDashboard.putNumber("Time Machine Yaw (test)", Robot.drive.timeMachineYaw(lookbackTime_));

	double angleError = desiredAngle - actualAngle;
	double adjustedAngleError = desiredAngle - actualAngle;
	adjustedAngleError = (adjustedAngleError > 180) ? adjustedAngleError - 360 : adjustedAngleError;
	adjustedAngleError = (adjustedAngleError < -180) ? adjustedAngleError + 360 : adjustedAngleError;
	double leftPosError = desiredLeftPos - Robot.drive.getLeftPosition();
	double rightPosError = desiredRightPos - Robot.drive.getRightPosition();
	double leftTicksError = desiredLeftPos - Robot.drive.getLeftTicks();
	double rightTicksError = desiredRightPos - Robot.drive.getRightTicks();

	SmartDashboard.putNumber("Error Yaw (test)", angleError);
	SmartDashboard.putNumber("Adjusted Error Yaw (test)", adjustedAngleError);
	SmartDashboard.putNumber("Error Left Pos (test)", leftPosError);
	SmartDashboard.putNumber("Error Right Pos (test)", rightPosError);
	SmartDashboard.putNumber("Error Left Pos Ticks (test)", leftTicksError);
	SmartDashboard.putNumber("Error Right Pos Ticks (test)", rightTicksError);

	Robot.drive.setPower(0, 0);
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
