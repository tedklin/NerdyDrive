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

    private double m_kP;
    private double m_straightPower;
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
     * @param straightPower
     */
    public ArcTurn(double desiredAngle, boolean isRightPowered, double straightPower) {
	m_desiredAngle = desiredAngle;
	m_isRightPowered = isRightPowered;
	m_straightPower = straightPower;
	m_timeout = 10;
	m_isHighGear = false;

	requires(Robot.drive);
    }

    /**
     * Arc Turn
     * 
     * @param desiredAngle
     * @param isRightPowered
     * @param straightPower
     * @param isHighGear
     */
    public ArcTurn(double desiredAngle, boolean isRightPowered, double straightPower, boolean isHighGear) {
	m_desiredAngle = desiredAngle;
	m_isRightPowered = isRightPowered;
	m_straightPower = straightPower;
	m_timeout = 10;
	m_isHighGear = isHighGear;

	requires(Robot.drive);
    }

    /**
     * Arc Turn
     * 
     * @param desiredAngle
     * @param isRightPowered
     * @param striaghtPower
     * @param isHighGear
     * @param timeout
     */
    public ArcTurn(double desiredAngle, boolean isRightPowered, double straightPower, boolean isHighGear,
	    double timeout) {
	m_desiredAngle = desiredAngle;
	m_isRightPowered = isRightPowered;
	m_straightPower = straightPower;
	m_timeout = timeout;
	m_isHighGear = isHighGear;

	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "ArcTurn");

	m_startTime = Timer.getFPGATimestamp();
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
	m_error = m_desiredAngle - robotAngle;
	SmartDashboard.putNumber("Angle Error", m_error);
	double power = m_kP * m_error * 1.95;
	if (m_isRightPowered) {
	    Robot.drive.setPower(0 + m_straightPower, power - m_straightPower);
	} else if (!m_isRightPowered) {
	    Robot.drive.setPower(power + m_straightPower, 0 - m_straightPower);
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
