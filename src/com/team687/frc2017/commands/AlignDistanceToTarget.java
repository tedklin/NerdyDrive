package com.team687.frc2017.commands;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.NerdyMath;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Aligns the distance from target using vision
 * 
 * @author tedlin
 *
 */

public class AlignDistanceToTarget extends Command {

    private double m_error;
    private boolean m_isHighGear;

    private double m_kP;
    private double m_maxDistPower;
    private double m_minDistPower;

    private double m_startTime;
    private double m_timeout;

    public AlignDistanceToTarget() {
	m_timeout = 1.678;
	m_isHighGear = false;

	// subsystem dependencies
	requires(Robot.drive);
    }

    public AlignDistanceToTarget(double timeout) {
	m_timeout = timeout;
	m_isHighGear = true;

	// subsystem dependencies
	requires(Robot.drive);
    }

    public AlignDistanceToTarget(double timeout, boolean isHighGear) {
	m_timeout = timeout;
	m_isHighGear = isHighGear;

	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "AlignDistanceToTarget");

	Robot.drive.stopDrive();
	if (m_isHighGear) {
	    Robot.drive.shiftUp();
	    m_kP = Constants.kRotPHighGear;
	    m_maxDistPower = Constants.kMaxDistPowerHighGear;
	    m_minDistPower = Constants.kMinDistPowerHighGear;
	} else if (!m_isHighGear) {
	    Robot.drive.shiftDown();
	    m_kP = Constants.kRotPHighGear;
	    m_maxDistPower = Constants.kMaxDistPowerLowGear;
	    m_minDistPower = Constants.kMinDistPowerLowGear;
	}

	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
	double robotInchesFromTarget = Robot.visionAdapter.getDistanceFromTarget();
	m_error = NerdyMath.feetToInches(Constants.kShotDistanceFeet) - robotInchesFromTarget;

	double straightPower = m_kP * m_error;
	double sign = Math.signum(straightPower);
	if (Math.abs(straightPower) > m_maxDistPower) {
	    straightPower = m_maxDistPower * sign;
	}

	if (Math.abs(straightPower) < m_minDistPower) {
	    straightPower = m_minDistPower * sign;
	}

	Robot.drive.setPower(straightPower, -straightPower);
    }

    @Override
    protected boolean isFinished() {
	return Math.abs(m_error) < Constants.kDriveDistanceTolerance
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
