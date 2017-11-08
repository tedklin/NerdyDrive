package com.team687.frc2017.commands;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.PGains;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive at a specified heading. Loop is closed on heading but not on straight
 * power.
 * 
 * @author tedlin
 *
 */

public class DriveAtHeading extends Command {

    private double m_straightPower;
    private double m_heading, m_distance;
    private boolean m_isHighGear;
    private PGains m_rotPGains;

    /**
     * @param straightPower
     *            (determines direction and magnitude)
     * @param heading
     * @param distance
     *            (absolute value)
     * @param isHighGear
     */
    public DriveAtHeading(double straightPower, double heading, double distance, boolean isHighGear) {
	m_straightPower = straightPower;
	m_heading = heading;
	m_distance = distance;

	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "DriveAtHeading");

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
	double rotError = -m_heading - robotAngle;
	rotError = (rotError > 180) ? rotError - 360 : rotError;
	rotError = (rotError < -180) ? rotError + 360 : rotError;
	double rotPower = m_rotPGains.getP() * rotError;

	Robot.drive.setPower(rotPower + m_straightPower, rotPower - m_straightPower);
    }

    @Override
    protected boolean isFinished() {
	return Math.abs(Robot.drive.getDrivetrainTicks()) >= m_distance;
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