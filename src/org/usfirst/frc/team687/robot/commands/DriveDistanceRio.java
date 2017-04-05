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
	
	private double m_leftError;
	private double m_rightError;
	private double m_lastLeftError;
	private double m_lastRightError;
	
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
		int index = (int)(m_timeStamp/Constants.kDt);
		double setpoint = m_motionProfile.readPosition(index);
		double goalVelocity = m_motionProfile.readVelocity(index);
		double goalAccel = m_motionProfile.readAcceleration(index);
		
		double feedforward = (Constants.kV * goalVelocity) + (Constants.kA * goalAccel);
		
		m_leftError = setpoint - Robot.drive.getLeftPosition();
		m_rightError = setpoint - Robot.drive.getRightPosition();
		
		double leftPow = (Constants.kDistP * m_leftError) + (Constants.kDistD * ((m_leftError - m_lastLeftError)/Constants.kDt - goalVelocity)) + feedforward;
		double rightPow = (Constants.kDistP * m_rightError) + (Constants.kDistD * ((m_rightError - m_lastRightError)/Constants.kDt - goalVelocity)) + feedforward;
		
		if (m_isStraight) {
			double angularPow = Constants.kRotP * Robot.drive.getYaw();
			leftPow += angularPow;
			rightPow -= angularPow;
		}
		
		double[] pow = {leftPow, rightPow};
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
