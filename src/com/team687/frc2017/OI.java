package com.team687.frc2017;

import com.team687.frc2017.commands.*;
import com.team687.frc2017.commands.tests.*;

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
	
	// buttons on driveJoyLeft
	public JoystickButton testSensors_9;
	public JoystickButton testMinRotPower_7;
	public JoystickButton testRotPID_1;
	
	// buttons on driveJoyRight
	public JoystickButton zeroGyro_9;
	public JoystickButton clearAll_8;
	public JoystickButton quickTurn_7;
	
	public JoystickButton snapToTarget_2;
	public JoystickButton turnToAngle_5;
	public JoystickButton shiftDown_4;
	
	public JoystickButton shiftUp_3;
	
	public OI() {
		zeroGyro_9 = new JoystickButton(driveJoyRight, 9);
		zeroGyro_9.whenPressed(new ResetGyro());
		clearAll_8 = new JoystickButton(driveJoyRight, 8);
		clearAll_8.cancelWhenPressed(Robot.drive.getCurrentCommand());
		quickTurn_7 = new JoystickButton(driveJoyRight, 7);
		
		snapToTarget_2 = new JoystickButton(driveJoyRight, 2);
		snapToTarget_2.whenPressed(new SnapToTarget(15));
		turnToAngle_5 = new JoystickButton(driveJoyRight, 5);
		turnToAngle_5.whenPressed(new TurnToAngle(45, 15));
		shiftDown_4 = new JoystickButton(driveJoyRight, 4);
		shiftDown_4.whenPressed(new ShiftDown());
		
		shiftUp_3 = new JoystickButton(driveJoyRight, 3);
		shiftUp_3.whenPressed(new ShiftUp());
		
		testSensors_9 = new JoystickButton(driveJoyLeft, 9);
		testSensors_9.whenPressed(new TestSensors());
		testMinRotPower_7 = new JoystickButton(driveJoyLeft, 7);
		testMinRotPower_7.whenPressed(new TestMinRotPower());
		testRotPID_1 = new JoystickButton(driveJoyLeft, 1);
		testRotPID_1.whenPressed(new TestRotPID());
		
		
		// Smart Dashboard buttons
		SmartDashboard.putData("Tank Drive", new TankDrive());
		SmartDashboard.putData("Snap to Target", new SnapToTarget(15));
		SmartDashboard.putData("Turn to 90", new TurnToAngle(90, 15));
		SmartDashboard.putData("Turn to 45", new TurnToAngle(45, 15));
		SmartDashboard.putData("Shift Up", new ShiftUp());
		SmartDashboard.putData("Shift Down", new ShiftDown());
		SmartDashboard.putData("Zero Gyro", new ResetGyro());
		
		SmartDashboard.putData("Test Sensors", new TestSensors());
		SmartDashboard.putData("Test Min Rot Power", new TestMinRotPower());
		SmartDashboard.putData("Test Rot PID", new TestRotPID());
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
	 * @return input throttle from right drive joystick (0 to +1.0)
	 */
	public double getThrottleR() {
		return (driveJoyRight.getThrottle() + 1) / 2;
	}
	
	/**
	 * @return if quick turn state in cheesydrive mode
	 */
	public boolean getQuickTurn() {
		return quickTurn_7.get();
	}
}
