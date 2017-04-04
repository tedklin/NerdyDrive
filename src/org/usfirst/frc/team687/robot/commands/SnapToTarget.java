package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Robot;
import org.usfirst.frc.team687.robot.Constants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Automatic alignment with a vision target
 * Used with NerdyVision
 * 
 * @author tedfoodlin
 * 
 */

public class SnapToTarget extends Command {
	
	private NetworkTable m_table;
	private double m_error;
	private double m_angleToTurn;
	private double m_robotAngle;
	private int m_counter = 0;
	private double m_startTime;
	private double m_timeout = 3;

	public SnapToTarget() {
		m_timeout = 3; // default timeout is 3 seconds
		
		// subsystem dependencies
		requires(Robot.drive);
	}
	
	public SnapToTarget(double timeout) {
		m_timeout = timeout;
		
		// subsystem dependencies
		requires(Robot.drive);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void initialize() {
		m_table = NetworkTable.getTable("NerdyVision");
		m_angleToTurn = m_table.getDouble("ANGLE_TO_TURN");
		m_startTime = Timer.getFPGATimestamp();
	}

	@Override
	protected void execute() {
		m_robotAngle = (360-Robot.drive.getYaw()) % 360;
		m_error = m_angleToTurn - m_robotAngle;
		m_error = (m_error > 180) ? m_error-360 : m_error;
		m_error = (m_error < -180) ? m_error+360 : m_error;
		double power = Constants.kRotP * m_error;
		if (Math.abs(m_error) <= Constants.kDriveRotationTolerance) {
			m_counter += 1;
		}	else	{
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
