package com.team687.frc2017.commands;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.VisionAdapter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Alignment based on vision and gyro. Ends when the shoot button is released
 * 
 * @author tedlin
 * 
 */

public class SnapToTarget extends Command {

    private double m_startTime;
    private double m_timeout = 6.87;
    private int m_counter;
    private boolean m_isAuto;

    public SnapToTarget(boolean isAuto) {
	m_timeout = 3.3; // default timeout is 6.87 seconds
	m_isAuto = isAuto;

	// subsystem dependencies
	requires(Robot.drive);
    }

    /**
     * @param timeout
     */
    public SnapToTarget(double timeout, boolean isAuto) {
	m_timeout = timeout;
	m_isAuto = isAuto;

	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "SnapToTarget");

	Robot.drive.stopDrive();
	Robot.drive.shiftDown();
	m_counter = 0;

	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	double relativeAngleError = VisionAdapter.getInstance().getAngleToTurn();
	double processingTime = VisionAdapter.getInstance().getProcessedTime();
	double absoluteDesiredAngle = relativeAngleError + Robot.drive.timeMachineYaw(processingTime);
	double error = absoluteDesiredAngle - robotAngle;
	SmartDashboard.putNumber("Angle Error", error);
	double rotPower = Constants.kRotPLowGear * error;
	if (Math.abs(error) <= Constants.kDriveRotationDeadband) {
	    rotPower = 0;
	    m_counter++;
	} else {
	    m_counter = 0;
	}
	Robot.drive.setPower(rotPower, rotPower);
    }

    @Override
    protected boolean isFinished() {
	boolean isFinished = false;
	if (!m_isAuto) {
	    isFinished = Timer.getFPGATimestamp() - m_startTime > m_timeout || !Robot.oi.wantToShoot();
	} else if (m_isAuto) {
	    isFinished = Timer.getFPGATimestamp() - m_startTime > m_timeout
		    || m_counter > Constants.kDriveRotationCounter;
	}
	return isFinished;
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
