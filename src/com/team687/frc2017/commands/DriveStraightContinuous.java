package com.team687.frc2017.commands;

import com.team687.frc2017.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive straight without setting power to 0 when it reaches goal
 * 
 * @author tedlin
 *
 */

public class DriveStraightContinuous extends Command {

    private double m_distance;
    private double m_straightPower;
    private boolean m_isHighGear;

    /**
     * 
     * @param distance
     * @param straightPower
     */
    public DriveStraightContinuous(double distance, double straightPower, boolean isHighGear) {
	m_distance = distance;
	m_straightPower = straightPower;
	m_isHighGear = isHighGear;

	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "DriveStraightContinuous");

	if (m_isHighGear) {
	    Robot.drive.shiftUp();
	} else if (!m_isHighGear) {
	    Robot.drive.shiftDown();
	}
    }

    @Override
    protected void execute() {
	Robot.drive.setPower(m_straightPower, -m_straightPower);
    }

    @Override
    protected boolean isFinished() {
	return Robot.drive.getDrivetrainTicks() > m_distance;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
	end();
    }

}
