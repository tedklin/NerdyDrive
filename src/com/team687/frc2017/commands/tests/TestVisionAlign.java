package com.team687.frc2017.commands.tests;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.NerdyMath;
import com.team687.frc2017.utilities.PGains;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Testing vision alignment
 *
 * @author tedlin
 *
 */

public class TestVisionAlign extends Command {

    private PGains m_rotPGains;

    public TestVisionAlign() {
	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "SnapToTarget");

	Robot.drive.stopDrive();
	Robot.drive.shiftUp();
	m_rotPGains = Constants.kRotHighGearPGains;
    }

    @Override
    protected void execute() {
	double angleToTurn = Robot.visionAdapter.getAngleToTurn();
	double historicalAngle = Robot.drive.timeMachineYaw(Robot.visionAdapter.getProcessedTime());
	SmartDashboard.putNumber("Historical angle " + Robot.visionAdapter.getProcessedTime() + " seconds ago (test)",
		historicalAngle);
	double desiredAngle = angleToTurn + historicalAngle;
	SmartDashboard.putNumber("Desired angle (absolute) from vision (test)", desiredAngle);

	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	double error = desiredAngle - robotAngle;
	error = (error > 180) ? error - 360 : error;
	error = (error < -180) ? error + 360 : error;

	double rotPower = m_rotPGains.getP() * error;
	rotPower = NerdyMath.threshold(rotPower, m_rotPGains.getMinPower(), m_rotPGains.getMaxPower());
	SmartDashboard.putNumber("Angle error from vision (test)", error);
	SmartDashboard.putNumber("Vision Rotational P output (test)", rotPower);
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
