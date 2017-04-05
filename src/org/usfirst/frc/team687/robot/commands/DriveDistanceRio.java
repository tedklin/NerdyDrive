package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Constants;
import org.usfirst.frc.team687.robot.Robot;
import org.usfirst.frc.team687.robot.utilities.MotionProfileGenerator;
import org.usfirst.frc.team687.robot.utilities.NerdyMath;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive a path with motion profiling with optional straight driving heading correction with a P-loop on NavX reading
 *
 * @author tedfoodlin
 *
 */

public class DriveDistanceRio extends Command {

	private double m_distance;
	private boolean m_isStraight;
	private MotionProfileGenerator m_motionProfile;
	
	private double m_startTime;
	private double m_timeStamp;
	private double m_dT = 0.01;
	private double m_dTInMinutes = m_dT * 60;
	
	private double m_setpoint;
	private double m_goalVelocity;
	private double m_goalAccel;
	private int m_index;
	private double m_feedforward;
	
	private double m_leftError;
	private double m_rightError;
	private double m_lastLeftError;
	private double m_lastRightError;
	
	private double m_leftPow;
	private double m_rightPow;
	
	public DriveDistanceRio(double distance, boolean straight) {
		m_distance = distance;
		m_isStraight = straight;
		
		// subsystem dependencies
		requires(Robot.drive);
	}
	
	@Override
	protected void initialize() {
		m_leftError = 0;
		m_rightError = 0;
		
		m_motionProfile = new MotionProfileGenerator(Constants.kMaxVelocity, Constants.kMaxAcceleration, -Constants.kMaxAcceleration);
		m_motionProfile.generateProfile(m_distance);
		
		Robot.drive.resetSensors();
		Robot.drive.shiftDown();
		m_startTime = Timer.getFPGATimestamp();
	}
	
	@Override
	protected void execute() {
		m_lastLeftError = m_leftError;
		m_lastRightError = m_rightError;
		m_timeStamp = Timer.getFPGATimestamp() - m_startTime;
		m_index = (int)(m_timeStamp/m_dTInMinutes);
		m_setpoint = m_motionProfile.readPosition(m_index);
		m_goalVelocity = m_motionProfile.readVelocity(m_index);
		m_goalAccel = m_motionProfile.readAcceleration(m_index);
		
		m_feedforward = (Constants.kV * m_goalVelocity) + (Constants.kA * m_goalAccel);
		
		m_leftError = m_setpoint - Robot.drive.getLeftPosition();
		m_rightError = m_setpoint - Robot.drive.getRightPosition();
		
		m_leftPow = (Constants.kDistP * m_leftError) + (Constants.kDistD * ((m_leftError - m_lastLeftError)/m_dT - m_goalVelocity)) + m_feedforward;
		m_rightPow = (Constants.kDistP * m_rightError) + (Constants.kDistD * ((m_rightError - m_lastRightError)/m_dT - m_goalVelocity)) + m_feedforward;
		
		if (m_isStraight) {
			double angularPow = Constants.kRotP * Robot.drive.getYaw();
			m_leftPow += angularPow;
			m_rightPow -= angularPow;
		}
		
		double[] pow = {m_leftPow, m_rightPow};
		NerdyMath.normalize(pow, false);
		
		Robot.drive.setPower(pow[0], pow[1]);
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.drive.getDrivetrainPosition() - m_distance) <= 1 && m_timeStamp >= m_motionProfile.getTotalTime();
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
