package com.team687.frc2017.commands;

import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Test sensor values without actuating anything
 *
 * @author tedfoodlin
 *
 */

public class TestSensors extends Command {
	
	private double m_desiredAngle = 0;
	private double m_desiredLeftPos = 0;
	private double m_desiredRightPos = 0;

    public TestSensors() {
    	// subsystem dependencies
        requires(Robot.drive);
    }

	@Override
	protected void initialize() {
		SmartDashboard.putString("Current Command", "TestSensors");
		Robot.drive.stopDrive();
	}

	@Override
	protected void execute() {
		double actualAngle = NerdyMath.boundAngle(Robot.drive.getCurrentYaw());
		SmartDashboard.putNumber("Desired Angle (test)", m_desiredAngle);
		SmartDashboard.putNumber("Desired Left Pos (test)", m_desiredLeftPos);
		SmartDashboard.putNumber("Desired Right Pos (test)", m_desiredRightPos);
		SmartDashboard.putNumber("Actual Angle (test)", actualAngle);
		SmartDashboard.putNumber("Actual Left Pos (test)", Robot.drive.getLeftPosition());
		SmartDashboard.putNumber("Actual Right Pos (test)", Robot.drive.getRightPosition());
		SmartDashboard.putNumber("Actual Left Speed (test)", Robot.drive.getLeftSpeed());
		SmartDashboard.putNumber("Actual Right Speed (test)", Robot.drive.getRightSpeed());
		SmartDashboard.putNumber("Actual Left Pos Ticks (test)", Robot.drive.getLeftTicks());
		SmartDashboard.putNumber("Actual Right Pos Ticks (test)", Robot.drive.getRightTicks());
		SmartDashboard.putNumber("Actual Left Speed Ticks (test)", Robot.drive.getLeftTicksSpeed());
		SmartDashboard.putNumber("Actual Right Speed Ticks (test)", Robot.drive.getRightTicksSpeed());
		
		double angleError = m_desiredAngle - actualAngle;
		angleError = (angleError > 180) ? angleError-360 : angleError;
		angleError = (angleError < -180) ? angleError+360 : angleError;
		double leftPosError = m_desiredLeftPos - Robot.drive.getLeftPosition();
		double rightPosError = m_desiredRightPos - Robot.drive.getRightPosition();
		double leftTicksError = m_desiredLeftPos - Robot.drive.getLeftTicks();
		double rightTicksError = m_desiredRightPos - Robot.drive.getRightTicks();
		
		SmartDashboard.putNumber("Error Angle (test)", angleError);
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
	
	public void setDesiredAngle(double desiredAngle) {
		m_desiredAngle = desiredAngle;
	}
	
	public void setDesiredLeftDistance(double desiredLeft) {
		m_desiredLeftPos = desiredLeft;
	}
	
	public void setDesiredRightDistance(double desiredRight) {
		m_desiredRightPos = desiredRight;
	}
	
	public double getDesiredAngle() {
		return m_desiredAngle;
	}
	
	public double getDesiredLeftDistance() {
		return m_desiredLeftPos;
	}
	
	public double getDesiredRightDistance() {
		return m_desiredRightPos;
	}

}
