package com.team687.frc2017;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Interface to SmartDashboard
 *
 * @author tedfoodlin
 *
 */

public class SmartDashboardInteractions {
	
	public void initialize() {
		SmartDashboard.putNumber("Rot P", Constants.kRotP);
		SmartDashboard.putNumber("Rot I", Constants.kRotI);
		SmartDashboard.putNumber("Rot D", Constants.kRotD);
		SmartDashboard.putNumber("Min Rot Power", Constants.kMinRotPower);
		SmartDashboard.putNumber("Max Rot Power", Constants.kMaxRotPower);
	}
	
	@SuppressWarnings("deprecation")
	public void update() {
		Constants.kRotP = SmartDashboard.getNumber("Rot P");
		Constants.kRotI = SmartDashboard.getNumber("Rot I");
		Constants.kRotD = SmartDashboard.getNumber("Rot D");
		Constants.kMinRotPower = SmartDashboard.getNumber("Min Rot Power");
		Constants.kMaxRotPower = SmartDashboard.getNumber("Max Rot Power");
	}

}
