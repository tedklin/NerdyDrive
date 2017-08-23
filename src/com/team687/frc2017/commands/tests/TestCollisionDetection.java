package com.team687.frc2017.commands.tests;

import com.team687.frc2017.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Test collision detection threshold. Teleop tank driving.
 * 
 * @author tedlin
 *
 */

public class TestCollisionDetection extends Command {

    private double m_lastAccelX;
    private double m_lastAccelY;
    private double m_jerkX;
    private double m_jerkY;

    public TestCollisionDetection() {
	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "TestCollisionDetection");
	Robot.drive.stopDrive();
    }

    @Override
    protected void execute() {
	double leftPow = Robot.oi.getDriveJoyLeftY();
	double rightPow = Robot.oi.getDriveJoyRightY();
	Robot.drive.setPower(leftPow, -rightPow);

	double currentAccelX = Robot.drive.getCurrentAccelX();
	double currentAccelY = Robot.drive.getCurrentAccelY();

	m_jerkX = currentAccelX - m_lastAccelX;
	m_jerkY = currentAccelY - m_lastAccelY;

	m_lastAccelX = currentAccelX;
	m_lastAccelY = currentAccelY;

	SmartDashboard.putNumber("Jerk X", m_jerkX);
	SmartDashboard.putNumber("Jerk Y", m_jerkY);
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
