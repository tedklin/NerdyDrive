package com.team687.frc2017.commands;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.NerdyMath;
import com.team687.frc2017.utilities.NerdyPID;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Alignment based on vision and gyro
 * Used with NerdyVision
 * 
 * @author tedfoodlin
 * 
 */

public class SnapToTarget extends Command {
	
	private NetworkTable m_table;
	private double m_angleToTurn;
	private double m_historicalAngle;
	private double m_error;
	private boolean m_isAligned;
	private NerdyPID m_rotPID;
	
	private double m_startTime;
	private double m_processingTime;
	private double m_timeout = 6.87;

	public SnapToTarget() {
		m_timeout = 6.87; // default timeout is 5 seconds
		
		// subsystem dependencies
		requires(Robot.drive);
	}
	
	/**
	 * @param timeout
	 */
	public SnapToTarget(double timeout) {
		m_timeout = timeout;
		
		// subsystem dependencies
		requires(Robot.drive);
	}
	
	@Override
	protected void initialize() {
		SmartDashboard.putString("Current Command", "SnapToTarget");
		m_rotPID = new NerdyPID(Constants.kRotP, Constants.kRotI, Constants.kRotD);
		m_rotPID.setOutputRange(Constants.kMinRotPower, Constants.kMaxRotPower);
		
		Robot.drive.stopDrive();
		Robot.drive.shiftDown();
		
		m_startTime = Timer.getFPGATimestamp();
	}

	@Override
	protected void execute() {
		visionUpdate();
		m_rotPID.setDesired(m_error);
		double power = m_rotPID.calculate(Robot.drive.getCurrentYaw() - m_historicalAngle);
		Robot.drive.setPower(power, -power);
	}

	@Override
	protected boolean isFinished() {
		return Timer.getFPGATimestamp() - m_startTime > m_timeout || m_isAligned;
	}

	@Override
	protected void end() {
		Robot.drive.stopDrive();
	}

	@Override
	protected void interrupted() {
		end();
	}
	
	@SuppressWarnings("deprecation")
	private void visionUpdate() {
		m_angleToTurn = m_table.getDouble("ANGLE_TO_TURN");
		SmartDashboard.putNumber("Angle from NerdyVision", m_angleToTurn);
		m_processingTime = m_table.getDouble("PROCESSED_TIME");
		SmartDashboard.putNumber("Processing Time (seconds)", m_processingTime);
		
		m_angleToTurn = NerdyMath.boundAngle(m_angleToTurn);
		m_historicalAngle = Robot.drive.timeMachineYaw(m_processingTime);
		SmartDashboard.putNumber("Historical angle at timestamp of frame captured", m_historicalAngle);
		m_error = m_angleToTurn - m_historicalAngle;
		SmartDashboard.putNumber("Error from Target", m_error);
		
		m_isAligned = m_table.getBoolean("IS_ALIGNED");
		SmartDashboard.putBoolean("Aligned to vision target", m_isAligned);
	}
	
}
