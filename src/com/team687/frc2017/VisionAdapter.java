package com.team687.frc2017;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Vision adapter
 *
 * @author tedfoodlin
 *
 */

public class VisionAdapter {
	
	private static VisionAdapter m_instance = null;
	
	public static VisionAdapter getInstance() {
		if (m_instance == null) {
			m_instance = new VisionAdapter();
		}
		return m_instance;
	}
	
	private NetworkTable m_visionTable;
	
	protected VisionAdapter() {
		m_visionTable = NetworkTable.getTable("NerdyVision");
	}
	
	@SuppressWarnings("deprecation")
	public boolean isAligned() {
		return m_visionTable.getBoolean("IS_ALIGNED");
	}
	
	@SuppressWarnings("deprecation")
	public double getAngleToTurn() {
		return m_visionTable.getNumber("ANGLE_TO_TURN");
	}
	
	@SuppressWarnings("deprecation")
	public double getProcessedTime() {
		return m_visionTable.getNumber("PROCESSED_TIME");
	}
	
	public void reportToSmartDashboard() {
		SmartDashboard.putNumber("Angle to turn from NerdyVision", getAngleToTurn());
		SmartDashboard.putNumber("Image processing time (seconds)", getProcessedTime());
		SmartDashboard.putBoolean("Aligned to vision target", isAligned());
	}

}
