package com.team687.frc2017.commands;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.NerdyMath;
import com.team687.frc2017.utilities.NerdyPID;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turn to a specified angle (no vision, absolute)
 * 
 * @author tedfoodlin
 * 
 */

public class TurnToAngle extends Command {
	
	private double m_angleToTurn;
	private int m_counter = 0;
	private double m_startTime;
	private double m_timeout;
	
	private NerdyPID m_rotPID;
	
	/**
	 * @param angle
	 */
	public TurnToAngle(double angle) {
		m_angleToTurn = angle;
		m_timeout = 3; // default timeout is 3 seconds
		
		// subsystem dependencies
		requires(Robot.drive);
	}
	
	/**
	 * @param angle
	 * @param timeout
	 */
	public TurnToAngle(double angle, double timeout) {
		m_angleToTurn = angle;
		m_timeout = timeout;
		
		// subsystem dependencies
		requires(Robot.drive);
	}

	@Override
	protected void initialize() {
		SmartDashboard.putString("Current Command", "TurnToAngle");
		m_startTime = Timer.getFPGATimestamp();
		m_rotPID = new NerdyPID(Constants.kRotP, Constants.kRotI, Constants.kRotD);
		m_rotPID.setOutputRange(Constants.kMinRotPower, Constants.kMaxRotPower);
		m_rotPID.setDesired(m_angleToTurn);
		m_rotPID.setGyro(true);
		
		Robot.drive.stopDrive();
		Robot.drive.shiftDown();
	}

	@Override
	protected void execute() {
		double robotAngle = NerdyMath.boundAngle(Robot.drive.getCurrentYaw());
		double error = m_angleToTurn - robotAngle;
		SmartDashboard.putNumber("Angle Error", error);
		double power = m_rotPID.calculate(Robot.drive.getCurrentYaw());
		if (Math.abs(error) <= Constants.kDriveRotationTolerance) {
			m_counter += 1;
		} else {
			m_counter = 0;
		}
		Robot.drive.setPower(power, -power);
	}

	@Override
	protected boolean isFinished() {
		return m_counter > Constants.kDriveRotationOscillationCount || Timer.getFPGATimestamp() - m_startTime > m_timeout;
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
