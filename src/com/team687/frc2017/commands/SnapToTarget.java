package com.team687.frc2017.commands;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.NerdyPID;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Alignment based on vision and gyro
 * 
 * @author tedlin
 * 
 */

public class SnapToTarget extends Command {

    private NerdyPID m_rotPID;

    private double m_startTime;
    private double m_timeout = 6.87;

    public SnapToTarget() {
	m_timeout = 6.87; // default timeout is 6.87 seconds

	// subsystem dependencies
	requires(Robot.drive);
    }

    /**
     * @param timeout
     */
    public SnapToTarget(double timeout) {
	m_timeout = timeout;

	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "SnapToTarget");
	m_rotPID = new NerdyPID(Constants.kRotPLowGear, Constants.kRotI, Constants.kRotD);
	m_rotPID.setOutputRange(Constants.kMinRotPowerLowGear, Constants.kMaxRotPowerLowGear);
	m_rotPID.setGyro(true);

	Robot.drive.stopDrive();
	Robot.drive.shiftDown();

	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
	double angleToTurn = Robot.visionAdapter.getAngleToTurn();
	double historicalAngle = Robot.drive.timeMachineYaw(Robot.visionAdapter.getProcessedTime());
	double desiredAngle = angleToTurn + historicalAngle;

	m_rotPID.setDesired(desiredAngle);
	double error = desiredAngle - Robot.drive.getCurrentYaw();
	SmartDashboard.putNumber("Angle Error", error);

	double power = m_rotPID.calculate(Robot.drive.getCurrentYaw());
	Robot.drive.setPower(power, power);
    }

    @Override
    protected boolean isFinished() {
	return Timer.getFPGATimestamp() - m_startTime > m_timeout;
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
