package com.team687.frc2017.commands;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.NerdyMath;
import com.team687.frc2017.utilities.PGains;

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

    private double m_straightPower;
    private double m_desiredAngle;
    private boolean m_isRightPowered;
    private boolean m_isHighGear;

    private PGains m_rotPGains;

    private double m_startTime, m_timeout;
    private double m_error;

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
	    m_rotPGains = Constants.kRotHighGearPGains;
	} else if (!m_isHighGear) {
	    Robot.drive.shiftDown();
	    m_rotPGains = Constants.kRotLowGearPGains;
	}
    }

    @Override
    protected void execute() {
	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	m_error = m_desiredAngle - robotAngle;
	m_error = (m_error > 180) ? m_error - 360 : m_error;
	m_error = (m_error < -180) ? m_error + 360 : m_error;
	double rotPower = m_rotPGains.getP() * m_error * 1.95; // multiplied by 2 because the rotational component is
							       // only added to one side of the drivetrain
	rotPower = NerdyMath.threshold(rotPower, m_rotPGains.getMinPower(), m_rotPGains.getMaxPower());

	if (m_isRightPowered) {
	    Robot.drive.setPower(0 + m_straightPower, rotPower - m_straightPower);
	} else if (!m_isRightPowered) {
	    Robot.drive.setPower(rotPower + m_straightPower, 0 - m_straightPower);
	}
    }

    @Override
    protected boolean isFinished() {
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
