package com.team687.frc2017.commands;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.MotionProfileGenerator;
import com.team687.frc2017.utilities.NerdyMath;
import com.team687.frc2017.utilities.NerdyPID;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	private double m_timestamp;
	
	private NerdyPID m_rotPID;
	
	private double m_leftError;
	private double m_rightError;
	private double m_lastLeftError;
	private double m_lastRightError;
	
	/**
	 * @param distance
	 * @param isStraight
	 */
	public DriveDistanceRio(double distance, boolean straight) {
		m_distance = distance;
		m_isStraight = straight;
		
		// subsystem dependencies
		requires(Robot.drive);
	}
	
	@Override
	protected void initialize() {
		SmartDashboard.putString("Current Command", "DriveDistanceRio");
		m_leftError = 0;
		m_rightError = 0;
		
		m_motionProfile = new MotionProfileGenerator(Constants.kMaxVelocity, Constants.kMaxAcceleration, -Constants.kMaxAcceleration);
		m_motionProfile.generateProfile(m_distance);
		
		Robot.drive.stopDrive();
		Robot.drive.resetEncoders();
		Robot.drive.shiftDown();
		
		m_rotPID = new NerdyPID(Constants.kRotP, Constants.kRotI, Constants.kRotD);
		m_rotPID.setOutputRange(Constants.kMinRotPower, Constants.kMaxRotPower);
		m_rotPID.setGyro(true);
		m_rotPID.setDesired(Robot.drive.getCurrentYaw());
		
		SmartDashboard.putNumber("Desired Distance", m_distance);
		m_startTime = Timer.getFPGATimestamp();
	}
	
	@Override
	protected void execute() {
		m_lastLeftError = m_leftError;
		m_lastRightError = m_rightError;
		m_timestamp = Timer.getFPGATimestamp() - m_startTime;
		int index = (int)(m_timestamp/Constants.kDt);
		if (m_timestamp > m_motionProfile.getAccelTime() * 60) {
			index += 1;
		} else if (m_timestamp >= ((m_motionProfile.getAccelTime() + m_motionProfile.getCruiseTime()) * 60)) {
			index += 2;
		}
		double setpoint = m_motionProfile.readPosition(index);
		double goalVelocity = m_motionProfile.readVelocity(index);
		double goalAccel = m_motionProfile.readAcceleration(index);
		
		double feedforward = (Constants.kV * goalVelocity) + (Constants.kA * goalAccel);
		
		m_leftError = setpoint - Robot.drive.getLeftPosition();
		m_rightError = setpoint - Robot.drive.getRightPosition();
		SmartDashboard.putNumber("Left error from setpoint", m_leftError);
		SmartDashboard.putNumber("Right error from setpoint", m_rightError);
		
		double leftPow = (Constants.kDistP * m_leftError) + (Constants.kDistD * ((m_leftError - m_lastLeftError)/Constants.kDt - goalVelocity)) + feedforward;
		double rightPow = (Constants.kDistP * m_rightError) + (Constants.kDistD * ((m_rightError - m_lastRightError)/Constants.kDt - goalVelocity)) + feedforward;
		
		if (m_isStraight) {
			double angularPow = m_rotPID.calculate(Robot.drive.getCurrentYaw());
			leftPow += angularPow;
			rightPow -= angularPow;
		}
		
		double[] pow = {leftPow, rightPow};
		NerdyMath.normalize(pow, false);
		
		Robot.drive.setPower(pow[0], pow[1]);
	}

	@Override
	protected boolean isFinished() {
		return (Math.abs(Robot.drive.getLeftPosition() - m_distance) <= 1 
				&& Math.abs(Robot.drive.getRightPosition() - m_distance) <= 1) 
				|| m_timestamp >= m_motionProfile.getTotalTime() * 60;
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
