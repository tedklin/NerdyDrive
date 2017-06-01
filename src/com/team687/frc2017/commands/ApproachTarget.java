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
 * Approach a target based on vision and gyro
 * Used with NerdyVision
 *
 * @author tedfoodlin
 *
 */

public class ApproachTarget extends Command {
	
	private NetworkTable m_table;
	private double m_angleToTurn;
	private double m_historicalAngle;
	private double m_error;
	private boolean m_isAligned;
	private NerdyPID m_rotPID;
	
	private double m_startTime;
	
	private double m_frameTimestamp;
	private double m_timeout = 6.87;
	
	public ApproachTarget() {
		m_timeout = 6.87; // default timeout is 5 seconds
		
		// subsystem dependencies
		requires(Robot.drive);
	}
	
	/**
	 * @param timeout
	 */
	public ApproachTarget(double timeout) {
		m_timeout = timeout;
		
		// subsystem dependencies
		requires(Robot.drive);
	}

	@Override
	protected void initialize() {
		SmartDashboard.putString("Current Command", "ApproachTarget");
		m_rotPID = new NerdyPID(Constants.kRotP, Constants.kRotI, Constants.kRotD);
		m_rotPID.setOutputRange(Constants.kMinRotPower, Constants.kMaxRotPower);
		
		m_startTime = Timer.getFPGATimestamp();
		
		Robot.drive.stopDrive();
		Robot.drive.shiftDown();
	}

	@Override
	protected void execute() {
		visionUpdate();
		m_rotPID.setDesired(m_error);
		double rotPower = m_rotPID.calculate(Robot.drive.getCurrentYaw() - m_historicalAngle);
		double straightPower = 0.330;
		Robot.drive.setPower(straightPower + rotPower, straightPower - rotPower);
	}

	@Override
	protected boolean isFinished() {
		return Timer.getFPGATimestamp() - m_startTime > m_timeout;
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
		m_frameTimestamp = m_table.getDouble("CAPTURE_TIME");
		SmartDashboard.putNumber("Timestamp of frame captured", m_frameTimestamp);
		
		m_angleToTurn = NerdyMath.boundAngle(m_angleToTurn);
		m_historicalAngle = Robot.drive.getHistoricalYaw((long)m_frameTimestamp);
		SmartDashboard.putNumber("Historical angle at timestamp of frame captured", m_historicalAngle);
		m_error = m_angleToTurn - m_historicalAngle;
		SmartDashboard.putNumber("Error from Target", m_error);
		
		m_isAligned = m_table.getBoolean("IS_ALIGNED");
		SmartDashboard.putBoolean("Aligned to vision target", m_isAligned);
	}

}
