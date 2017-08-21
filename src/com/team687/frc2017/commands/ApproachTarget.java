package com.team687.frc2017.commands;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.VisionAdapter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Approach a target based on vision and gyro. Used with NerdyVision
 *
 * @author tedlin
 *
 */

public class ApproachTarget extends Command {

    private double m_distance;
    private double m_straightPower;
    private double m_startTime;
    private double m_timeout = 6.87;

    public ApproachTarget(double distance, double straightPower) {
	m_distance = distance;
	m_straightPower = straightPower;
	m_timeout = 6.87; // default timeout is 5 seconds

	// subsystem dependencies
	requires(Robot.drive);
    }

    /**
     * @param timeout
     */
    public ApproachTarget(double distance, double straightPower, double timeout) {
	m_distance = distance;
	m_straightPower = straightPower;
	m_timeout = timeout;

	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "ApproachTarget");

	Robot.drive.stopDrive();
	Robot.drive.shiftDown();

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
	}
	Robot.drive.setPower(rotPower + m_straightPower, rotPower - m_straightPower);
    }

    @Override
    protected boolean isFinished() {
	return Timer.getFPGATimestamp() - m_startTime > m_timeout || Robot.drive.getDrivetrainTicks() > m_distance;
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
