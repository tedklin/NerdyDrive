package org.usfirst.frc.team687.robot;

import edu.wpi.first.wpilibj.I2C.Port;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

public class RobotMap {
	
	public static final int kShifterID1 = 0;
	public static final int kShifterID2 = 1;
	
	public static final int kRightMasterTalonID = 4;
	public static final int kRightSlaveTalon1ID = 5;
	public static final int kRightSlaveTalon2ID = 6;
	public static final int kLeftMasterTalonID = 1;
	public static final int kLeftSlaveTalon1ID = 2;
	public static final int kLeftSlaveTalon2ID = 3;
	
	public static final Port navID = Port.kMXP;
	
}