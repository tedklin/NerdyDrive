package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Constants;
import org.usfirst.frc.team687.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Complete vision alignment action (multiple frames to maximize accuracy)
 *
 * @author tedfoodlin
 *
 */

public class AlignToTarget extends Command {
	
	private NetworkTable m_table;
	private double m_angleToTurn;
	private double m_lastAngleToTurn;
	private boolean m_isAligned;
	
	private int m_turnCount; 
	private int m_alignedCount;

	public AlignToTarget() {
		//subsystem requirements
		requires(Robot.drive);
	}
	
	protected void initialize() {
		SmartDashboard.putString("Current Command", "AlignToTarget");
		m_table = NetworkTable.getTable("NerdyVision");
	}
	
	@SuppressWarnings("deprecation")
	protected void execute() {
		m_lastAngleToTurn = m_angleToTurn;
		m_angleToTurn = m_table.getDouble("ANGLE_TO_TURN");
		SmartDashboard.putNumber("Angle To Turn from NerdyVision", m_angleToTurn);
		m_isAligned = m_table.getBoolean("IS_ALIGNED");
		SmartDashboard.putBoolean("Aligned to vision target", m_isAligned);
		
		if (m_lastAngleToTurn == m_angleToTurn) {
			m_turnCount ++;
		} else { 
			m_turnCount = 0;
		}
		if (m_turnCount >= Constants.kDriveStationaryCount) {
			Scheduler.getInstance().add(new TurnToAngle(m_angleToTurn));
		}
		
		if (m_isAligned) {
			m_alignedCount ++;
		} else {
			m_alignedCount = 0;
		}
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return m_alignedCount >= Constants.kDriveStationaryCount;
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
