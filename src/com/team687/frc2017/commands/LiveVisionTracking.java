package com.team687.frc2017.commands;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.VisionAdapter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Live vision tracking (follows target around, no end)
 * 
 * @author tedfoodlin
 *
 */

public class LiveVisionTracking extends Command {
	
	private double m_angleToTurn;
	private double m_startTime;
	private double m_timeout;
	
//	private NerdyPID m_rotPID;
	
	/**
	 * @param angle
	 */
	public LiveVisionTracking() {
		m_timeout = 10; // default timeout is 10 seconds
		
		// subsystem dependencies
		requires(Robot.drive);
	}

	@Override
	protected void initialize() {
		SmartDashboard.putString("Current Command", "TurnToAngle");
		m_startTime = Timer.getFPGATimestamp();
//		m_rotPID = new NerdyPID(Constants.kRotP, Constants.kRotI, Constants.kRotD);
//		m_rotPID.setOutputRange(Constants.kMinRotPower, Constants.kMaxRotPower);
//		m_rotPID.setDesired(m_angleToTurn);
//		m_rotPID.setGyro(true);
		
		Robot.drive.stopDrive();
		Robot.drive.shiftDown();
	}

	@Override
	protected void execute() {
		double robotAngle = (360-Robot.drive.getCurrentYaw()) % 360;
		double relativeAngleError = VisionAdapter.getInstance().getAngleToTurn();
		double processingTime = VisionAdapter.getInstance().getProcessedTime();
		double absoluteDesiredAngle = relativeAngleError + Robot.drive.timeMachineYaw(processingTime);
		double error = absoluteDesiredAngle - robotAngle;
		SmartDashboard.putNumber("Angle Error", error);
//		double power = m_rotPID.calculate(Robot.drive.getCurrentYaw());
		double power = Constants.kRotP * error;
		if(Math.abs(error) <= Constants.kDriveRotationDeadband) {
			power = 0;
		}
		Robot.drive.setPower(power, power);
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
