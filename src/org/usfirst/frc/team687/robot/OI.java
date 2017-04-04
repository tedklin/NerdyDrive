package org.usfirst.frc.team687.robot;

import org.usfirst.frc.team687.robot.commands.ResetGyro;
import org.usfirst.frc.team687.robot.commands.ShiftUp;
import org.usfirst.frc.team687.robot.commands.ShiftDown;
import org.usfirst.frc.team687.robot.commands.SnapToTarget;
import org.usfirst.frc.team687.robot.commands.TankDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * @author tedfoodlin
 * 
 */

public class OI {
	
	public Joystick driveJoyLeft = new Joystick(0);
	public Joystick driveJoyRight = new Joystick(1);
	
	// buttons on driveJoyRight
	public JoystickButton zeroGyro_9;
	public JoystickButton clearAll_7;
	public JoystickButton quickTurn_1;
	
	public JoystickButton snapToTarget_2;
	public JoystickButton shiftUp_3;
	public JoystickButton shiftDown_4;
	
	public OI() {
		zeroGyro_9 = new JoystickButton(driveJoyRight, 9);
		zeroGyro_9.whenPressed(new ResetGyro());
		clearAll_7 = new JoystickButton(driveJoyRight, 7);
		clearAll_7.cancelWhenPressed(Robot.drive.getCurrentCommand());
		quickTurn_1 = new JoystickButton(driveJoyRight, 1);
		
		snapToTarget_2 = new JoystickButton(driveJoyRight, 2);
		snapToTarget_2.whenPressed(new SnapToTarget());
		shiftUp_3 = new JoystickButton(driveJoyRight, 3);
		shiftUp_3.whenPressed(new ShiftUp());
		shiftDown_4 = new JoystickButton(driveJoyRight, 4);
		shiftDown_4.whenPressed(new ShiftDown());
		
		// Smart Dashboard buttons
		SmartDashboard.putData("Tank Drive", new TankDrive());
		SmartDashboard.putData("Snap to Target", new SnapToTarget());
		SmartDashboard.putData("Shift Up", new ShiftUp());
		SmartDashboard.putData("Shift Down", new ShiftDown());
		SmartDashboard.putData("Zero Gyro", new ResetGyro());
		
	}
	
	/**
	 * @return input power from left drive joystick (-1.0 to +1.0)
	 */
	public double getDriveJoyL() {
		return driveJoyLeft.getY();
	}
	
	/**
	 * @return input power from right drive joystick (-1.0 to +1.0)
	 */
	public double getDriveJoyR() {
		return driveJoyRight.getY();
	}
	
	/**
	 * @return if quick turn state in cheesydrive mode
	 */
	public boolean getQuickTurn() {
		return quickTurn_1.get();
	}
}
