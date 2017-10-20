package com.team687.frc2017;

import com.team687.frc2017.commands.LiveTargetTracking;
import com.team687.frc2017.commands.ResetEncoders;
import com.team687.frc2017.commands.ResetGyro;
import com.team687.frc2017.commands.ShiftDown;
import com.team687.frc2017.commands.ShiftUp;
import com.team687.frc2017.commands.SnapToTarget;
import com.team687.frc2017.commands.teleop.CheesyDrive;
import com.team687.frc2017.commands.teleop.ClosedLoopDrive;
import com.team687.frc2017.commands.teleop.CulverDrive;
import com.team687.frc2017.commands.teleop.HaloDrive;
import com.team687.frc2017.commands.teleop.TankDrive;
import com.team687.frc2017.commands.tests.TestMinRotPower;
import com.team687.frc2017.commands.tests.TestRotPID;
import com.team687.frc2017.commands.tests.TestSensors;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * @author tedlin
 * 
 */

public class OI {

    public Joystick driveJoyLeft = new Joystick(0);
    public Joystick driveJoyRight = new Joystick(1);

    public Joystick gamepadJoy = new Joystick(0);

    public JoystickButton quickTurn_1;

    public OI() {
	// quickTurn_1 = new JoystickButton(driveJoyRight, 1);

	// Smart Dashboard buttons
	SmartDashboard.putData("Tank Drive", new TankDrive());
	SmartDashboard.putData("Halo Drive", new HaloDrive());
	SmartDashboard.putData("Culver Drive", new CulverDrive());
	SmartDashboard.putData("Cheesy Drive", new CheesyDrive());
	SmartDashboard.putData("Closed Loop Drive", new ClosedLoopDrive());

	SmartDashboard.putData("Reset Gyro", new ResetGyro());
	SmartDashboard.putData("Reset Encoders", new ResetEncoders());

	SmartDashboard.putData("Shift Up", new ShiftUp());
	SmartDashboard.putData("Shift Down", new ShiftDown());

	// SmartDashboard.putData("Test Collision Detection", new
	// TestCollisionDetection());
	// SmartDashboard.putData("Drive until Collision", new
	// DriveUntilCollision(0.687, true));

	SmartDashboard.putData("Live Target Tracking", new LiveTargetTracking(false));
	SmartDashboard.putData("Snap To Target", new SnapToTarget(false, 5));

	SmartDashboard.putData("Test Sensors", new TestSensors());
	SmartDashboard.putData("Test Min Rot Power", new TestMinRotPower());
	SmartDashboard.putData("Test Rot PID", new TestRotPID());
    }

    /**
     * @return input power from left drive joystick Y (-1.0 to +1.0)
     */
    public double getDriveJoyLeftY() {
	return gamepadJoy.getRawAxis(2);
	// return driveJoyLeft.getY();
    }

    /**
     * @return input power from right drive joystick Y (-1.0 to +1.0)
     */
    public double getDriveJoyRightY() {
	return gamepadJoy.getRawAxis(5);
	// return driveJoyRight.getY();
    }

    /**
     * @return input power from left drive joystick X (-1.0 to +1.0)
     */
    public double getDriveJoyLeftX() {
	return gamepadJoy.getRawAxis(1);
	// return driveJoyLeft.getX();
    }

    /**
     * @return input power from right drive joystick X (-1.0 to +1.0)
     */
    public double getDriveJoyRightX() {
	return gamepadJoy.getRawAxis(4);
	// return driveJoyRight.getX();
    }

    /**
     * @return input throttle from right drive joystick (0 to +1.0)
     */
    public double getThrottleR() {
	return (driveJoyRight.getThrottle() + 1) / 2;
    }

    /**
     * @return input throttle from left drive josytick
     */
    public double getThrottleL() {
	return (driveJoyLeft.getThrottle() + 1) / 2;
    }

    /**
     * @return if quick turn state in cheesydrive mode
     */
    public boolean getQuickTurn() {
	return quickTurn_1.get();
    }
}
