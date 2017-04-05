package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Robot;
import org.usfirst.frc.team687.robot.utilities.NerdyMath;
import org.usfirst.frc.team687.robot.utilities.NerdyPID;
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
	private double m_angleToTurn;
	private NerdyPID m_rotPID;
	
	private double m_startTime;
	private double m_timeout = 3;
	private int m_counter = 0;

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
		m_rotPID = new NerdyPID(Constants.kRotP, Constants.kRotI, Constants.kRotD);
		m_rotPID.setDesired(m_angleToTurn);
	}

	@Override
	protected void execute() {
		double robotAngle = (360-Robot.drive.getYaw()) % 360;
		double error = m_angleToTurn - robotAngle;
		error = NerdyMath.boundAngle(error);
		double power = m_rotPID.calculate(Robot.drive.getYaw());
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
