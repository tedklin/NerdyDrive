package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Robot;
import org.usfirst.frc.team687.robot.Constants;
import org.usfirst.frc.team687.robot.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.Command;

/**
 * An implementation of 254's CheesyDrive
 */

public class CheesyDrive extends Command{
	
	private boolean m_isQuickTurn;
	private double m_wheel, m_throttle;
	private double m_rPow, m_lPow;
	private double m_angularPow;
	private double m_sensitivity;
	private double m_quickStopAccumulator;
	
	public CheesyDrive() {
		//subsystem requirements
		requires(Robot.drive);
	}
	
	@Override
	protected void initialize() {
		Robot.drive.stopDrive();
	}

	@Override
	protected void execute() {
		m_isQuickTurn = Robot.oi.getQuickTurn();
		
	    m_wheel = Robot.drive.handleDeadband(Robot.oi.driveJoyRight.getX(), Constants.kWheelDeadband);
	    m_throttle = -Robot.drive.handleDeadband(Robot.oi.driveJoyLeft.getY(), Constants.kThrottleDeadband);
	    
	    m_rPow = m_lPow = m_throttle;
	    
	    if (Robot.drive.isHighGear()) {
	    	m_sensitivity = Constants.kSensitivityHigh;
	    } else {
	    	m_sensitivity = Constants.kSensitivityLow;
	    }
	    
	    if (m_isQuickTurn) {
	    	m_angularPow = m_wheel;
            m_quickStopAccumulator = (1 - Constants.kDriveAlpha) * m_quickStopAccumulator + Constants.kDriveAlpha * NerdyMath.limit(m_wheel, 1.0) * 2;
            m_throttle = 0;
	    } else {
	    	m_angularPow = Math.abs(m_throttle) * m_wheel * m_sensitivity - m_quickStopAccumulator;
            if (m_quickStopAccumulator > 1) {
            	m_quickStopAccumulator -= 1;
            } else if (m_quickStopAccumulator < -1) {
            	m_quickStopAccumulator += 1;
            } else {
            	m_quickStopAccumulator = 0;
            }
	    }
	    
	    m_lPow += m_angularPow;
	    m_rPow -= m_angularPow;
	    
	    double[] pow = {m_lPow, m_rPow};
	    pow = NerdyMath.normalize(pow, false);
		Robot.drive.setPower(pow[0], pow[1]);
	}

	@Override
	protected boolean isFinished() {
		return false;
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

