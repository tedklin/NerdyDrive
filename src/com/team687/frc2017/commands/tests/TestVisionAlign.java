package com.team687.frc2017.commands.tests;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.NerdyPID;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Testing vision alignment
 *
 * @author tedlin
 *
 */

public class TestVisionAlign extends Command {

    private NerdyPID m_rotPID;

    public TestVisionAlign() {
	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "SnapToTarget");
	m_rotPID = new NerdyPID(Constants.kRotPLowGear, Constants.kRotI, Constants.kRotD);
	m_rotPID.setOutputRange(Constants.kMinRotPower, Constants.kMaxRotPower);

	Robot.drive.stopDrive();
	Robot.drive.shiftUp();
    }

    @Override
    protected void execute() {
	double angleToTurn = Robot.visionAdapter.getAngleToTurn();
	double historicalAngle = Robot.drive.timeMachineYaw(Robot.visionAdapter.getProcessedTime());
	SmartDashboard.putNumber("Historical angle " + Robot.visionAdapter.getProcessedTime() + " seconds ago (test)",
		historicalAngle);
	double desiredAngle = angleToTurn + historicalAngle;
	SmartDashboard.putNumber("Desired angle (absolute) from vision (test)", desiredAngle);

	m_rotPID.setDesired(desiredAngle);
	double error = desiredAngle - Robot.drive.getCurrentYaw();
	SmartDashboard.putNumber("Angle error from vision (test)", error);

	double power = m_rotPID.calculate(Robot.drive.getCurrentYaw());
	SmartDashboard.putNumber("Vision Rotational PID output (test)", power);
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
