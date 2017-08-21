package com.team687.frc2017.commands;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turn to a specified angle (no vision, absolute)
 * 
 * @author tedlin
 * 
 */

public class TurnToAngle extends Command {

    private double m_angleToTurn;
    private double m_startTime;
    private double m_timeout;
    private double error;
    private double m_kP;

    private boolean m_isHighGear;

    // private NerdyPID m_rotPID;

    /**
     * @param angle
     */
    public TurnToAngle(double angle) {
	m_angleToTurn = angle;
	m_timeout = 10; // default timeout is 10 seconds
	m_isHighGear = false;

	// subsystem dependencies
	requires(Robot.drive);
    }

    /**
     * @param angle
     * @param timeout
     */
    public TurnToAngle(double angle, double timeout) {
	m_angleToTurn = angle;
	m_timeout = timeout;
	m_isHighGear = false;

	// subsystem dependencies
	requires(Robot.drive);
    }

    public TurnToAngle(double angle, double timeout, boolean isHighGear) {
	m_angleToTurn = angle;
	m_timeout = timeout;
	m_isHighGear = isHighGear;

	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "TurnToAngle");
	m_startTime = Timer.getFPGATimestamp();
	// m_rotPID = new NerdyPID(Constants.kRotP, Constants.kRotI, Constants.kRotD);
	// m_rotPID.setOutputRange(Constants.kMinRotPower, Constants.kMaxRotPower);
	// m_rotPID.setDesired(m_angleToTurn);
	// m_rotPID.setGyro(true);

	if (m_isHighGear) {
	    Robot.drive.shiftUp();
	    m_kP = Constants.kRotPHighGear;
	} else if (!m_isHighGear) {
	    Robot.drive.shiftDown();
	    m_kP = Constants.kRotPLowGear;
	}
    }

    @Override
    protected void execute() {
	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	error = m_angleToTurn - robotAngle;
	SmartDashboard.putNumber("Angle Error", error);
	// double power = m_rotPID.calculate(Robot.drive.getCurrentYaw());
	double power = m_kP * error;
	Robot.drive.setPower(power, power);
    }

    @Override
    protected boolean isFinished() {
	return Math.abs(error) <= Constants.kDriveRotationTolerance
		|| Timer.getFPGATimestamp() - m_startTime > m_timeout;
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
