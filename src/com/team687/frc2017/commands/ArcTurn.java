package com.team687.frc2017.commands;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Arc turning
 * 
 * @author tedlin
 *
 */

public class ArcTurn extends Command {

    private double m_desiredAngle;
    private boolean m_isRightPowered;
    private double m_timeout;
    private boolean m_isHighGear;
    private double m_startTime;
    private double m_error;

    /**
     * Arc Turn
     * 
     * @param desiredAngle
     * @param isRightPowered
     */
    public ArcTurn(double desiredAngle, boolean isRightPowered) {
	m_desiredAngle = desiredAngle;
	m_isRightPowered = isRightPowered;
	m_timeout = 10;
	m_isHighGear = false;

	requires(Robot.drive);
    }

    /**
     * Arc Turn
     * 
     * @param desiredAngle
     * @param isRightPowered
     * @param timeout
     */
    public ArcTurn(double desiredAngle, boolean isRightPowered, double timeout) {
	m_desiredAngle = desiredAngle;
	m_isRightPowered = isRightPowered;
	m_timeout = timeout;
	m_isHighGear = false;

	requires(Robot.drive);
    }

    /**
     * Arc Turn
     * 
     * @param desiredAngle
     * @param isRightPowered
     * @param timeout
     * @param isHighGear
     */
    public ArcTurn(double desiredAngle, boolean isRightPowered, double timeout, boolean isHighGear) {
	m_desiredAngle = desiredAngle;
	m_isRightPowered = isRightPowered;
	m_timeout = timeout;
	m_isHighGear = isHighGear;

	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "ArcTurn");

	m_startTime = Timer.getFPGATimestamp();
	Robot.drive.stopDrive();
	if (m_isHighGear) {
	    Robot.drive.shiftUp();
	} else {
	    Robot.drive.shiftDown();
	}
    }

    @Override
    protected void execute() {
	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	m_error = m_desiredAngle - robotAngle;
	SmartDashboard.putNumber("Angle Error", m_error);
	// double power = m_rotPID.calculate(Robot.drive.getCurrentYaw());
	double power = Constants.kRotP * m_error * 1.95;
	if (m_isRightPowered) {
	    Robot.drive.setPower(0, power);
	} else if (!m_isRightPowered) {
	    Robot.drive.setPower(power, 0);
	}
    }

    @Override
    protected boolean isFinished() {
	// TODO Auto-generated method stub
	return Math.abs(m_error) < Constants.kDriveRotationTolerance
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
