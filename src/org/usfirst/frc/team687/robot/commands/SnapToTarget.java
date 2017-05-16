package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Robot;
import org.usfirst.frc.team687.robot.utilities.NerdyMath;
import org.usfirst.frc.team687.robot.utilities.NerdyPID;
import org.usfirst.frc.team687.robot.Constants;

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
	private double m_error;
	private boolean m_isAligned;
	private NerdyPID m_rotPID;
	
	private double m_startTime;
	
	private double m_frameTimestamp;
	private double m_timeout = 5;

	public SnapToTarget() {
		m_timeout = 5; // default timeout is 5 seconds
		
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
		SmartDashboard.putString("Current Command", "SnapToTarget");
		
		visionUpdate();
		m_startTime = Timer.getFPGATimestamp();
		m_rotPID = new NerdyPID(Constants.kRotP, Constants.kRotI, Constants.kRotD);
		m_rotPID.setOutputRange(Constants.kMinRotPower, Constants.kMaxRotPower);
		m_rotPID.setDesired(m_error);
	}

	@Override
	protected void execute() {
		visionUpdate();
		m_rotPID.setDesired(m_error);
		double power = m_rotPID.calculate(Robot.drive.getCurrentYaw());
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
		SmartDashboard.putNumber("Angle To Turn from NerdyVision", m_angleToTurn);
		m_frameTimestamp = m_table.getDouble("FRAME_TIME");
		SmartDashboard.putNumber("Timestamp of frame captured", m_frameTimestamp);
		m_angleToTurn = NerdyMath.boundAngle(m_angleToTurn);
		m_error = m_angleToTurn - Robot.drive.getHistoricalYaw((long)m_frameTimestamp);
		SmartDashboard.putNumber("Error from Target", m_error);
		
		m_isAligned = m_table.getBoolean("IS_ALIGNED");
		SmartDashboard.putBoolean("Aligned to vision target", m_isAligned);
	}
	
}
