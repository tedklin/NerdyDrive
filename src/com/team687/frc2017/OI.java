package com.team687.frc2017;

import com.team687.frc2017.commands.DriveUntilCollision;
import com.team687.frc2017.commands.LiveVisionTracking;
import com.team687.frc2017.commands.ResetGyro;
import com.team687.frc2017.commands.ShiftDown;
import com.team687.frc2017.commands.ShiftUp;
import com.team687.frc2017.commands.SnapToTarget;
import com.team687.frc2017.commands.TankDrive;
import com.team687.frc2017.commands.auto.BlueGearHopperShootAuto254;
import com.team687.frc2017.commands.auto.BlueHopperShootAuto1678;
import com.team687.frc2017.commands.auto.BlueHopperShootAuto2056;
import com.team687.frc2017.commands.auto.BlueHopperShootAuto973;
import com.team687.frc2017.commands.tests.TestCollisionDetection;

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

    // buttons on driveJoyLeft
    public JoystickButton testSensors_9;
    public JoystickButton testMinRotPower_7;
    public JoystickButton liveVisionTrack_1;

    public JoystickButton stopShooting_2;
    public JoystickButton wantToShoot_5;

    public JoystickButton tankDrive_10;
    public JoystickButton cheesyDrive_11;

    // buttons on driveJoyRight
    public JoystickButton pivotToAngle_9;
    public JoystickButton clearAll_8;
    public JoystickButton quickTurn_7;

    public JoystickButton snapToTarget_2;
    public JoystickButton turnToAngle_5;
    public JoystickButton shiftDown_4;

    public JoystickButton approachTarget_10;
    public JoystickButton arcTurn_11;

    public JoystickButton shiftUp_3;

    public OI() {
	// pivotToAngle_9 = new JoystickButton(driveJoyRight, 9);
	// pivotToAngle_9.whenPressed(new PivotToAngle(170, false, false));
	clearAll_8 = new JoystickButton(driveJoyRight, 8);
	clearAll_8.cancelWhenPressed(Robot.drive.getCurrentCommand());
	// quickTurn_7 = new JoystickButton(driveJoyRight, 7);

	// snapToTarget_2 = new JoystickButton(driveJoyRight, 2);
	// snapToTarget_2.whenPressed(new SnapToTarget(15));
	// turnToAngle_5 = new JoystickButton(driveJoyRight, 5);
	// turnToAngle_5.whenPressed(new TurnToAngle(90, 15));
	shiftDown_4 = new JoystickButton(driveJoyRight, 4);
	shiftDown_4.whenPressed(new ShiftDown());

	// approachTarget_10 = new JoystickButton(driveJoyRight, 10);
	// approachTarget_10.whenPressed(new ApproachTarget());
	// arcTurn_11 = new JoystickButton(driveJoyRight, 11);
	// arcTurn_11.whenPressed(new ArcTurn(90, 0.254));

	shiftUp_3 = new JoystickButton(driveJoyRight, 3);
	shiftUp_3.whenPressed(new ShiftUp());

	// testSensors_9 = new JoystickButton(driveJoyLeft, 9);
	// testSensors_9.whenPressed(new TestSensors());
	// testMinRotPower_7 = new JoystickButton(driveJoyLeft, 7);
	// testMinRotPower_7.whenPressed(new TestMinRotPower());
	liveVisionTrack_1 = new JoystickButton(driveJoyLeft, 1);
	liveVisionTrack_1.whenPressed(new LiveVisionTracking());

	// tankDrive_10 = new JoystickButton(driveJoyLeft, 10);
	// tankDrive_10.whenPressed(new TankDrive());
	// cheesyDrive_11 = new JoystickButton(driveJoyLeft, 11);
	// cheesyDrive_11.whenPressed(new CheesyDrive());

	// Smart Dashboard buttons
	SmartDashboard.putData("Tank Drive", new TankDrive());

	SmartDashboard.putData("Reset Gyro", new ResetGyro());

	SmartDashboard.putData("Shift Up", new ShiftUp());
	SmartDashboard.putData("Shift Down", new ShiftDown());

	SmartDashboard.putData("Test Collision Detection", new TestCollisionDetection());
	SmartDashboard.putData("Drive until Collision", new DriveUntilCollision(0.687, true));

	SmartDashboard.putData("Live Vision Tracking", new LiveVisionTracking());
	SmartDashboard.putData("Snap To Target Auto", new SnapToTarget(true));

	SmartDashboard.putData("Blue 973 Hopper Auto", new BlueHopperShootAuto973());
	SmartDashboard.putData("Blue 1678 Hopper Auto", new BlueHopperShootAuto1678());
	SmartDashboard.putData("Blue 2056 Hopper Auto", new BlueHopperShootAuto2056());
	SmartDashboard.putData("Blue 254 Gear + Hopper Auto", new BlueGearHopperShootAuto254());
	// SmartDashboard.putData("Red 973 Hopper Auto", new RedHopperShootAuto973());
	// SmartDashboard.putData("Red 1678 Hopper Auto", new RedHopperShootAuto1678());
	// SmartDashboard.putData("Red 2056 Hopper Auto", new RedHopperShootAuto2056());
	// SmartDashboard.putData("Red 254 Gear + Hopper Auto", new
	// RedGearHopperShootAuto254());

	// SmartDashboard.putData("Test Sensors", new TestSensors());
	// SmartDashboard.putData("Test Min Rot Power", new TestMinRotPower());
	// SmartDashboard.putData("Test Rot PID", new TestRotPID());
    }

    /**
     * @return input power from left drive joystick Y (-1.0 to +1.0)
     */
    public double getDriveJoyLeftY() {
	return driveJoyLeft.getY();
    }

    /**
     * @return input power from right drive joystick Y (-1.0 to +1.0)
     */
    public double getDriveJoyRightY() {
	return driveJoyRight.getY();
    }

    /**
     * @return input power from left drive joystick X (-1.0 to +1.0)
     */
    public double getDriveJoyLeftX() {
	return driveJoyLeft.getX();
    }

    /**
     * @return input power from right drive joystick X (-1.0 to +1.0)
     */
    public double getDriveJoyRightX() {
	return driveJoyRight.getX();
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
	return quickTurn_7.get();
    }

    /**
     * @return if want to shoot
     */
    public boolean wantToShoot() {
	return wantToShoot_5.get();
    }
}
