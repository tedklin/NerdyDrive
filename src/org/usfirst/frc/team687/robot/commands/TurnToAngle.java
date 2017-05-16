package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Robot;
import org.usfirst.frc.team687.robot.utilities.NerdyMath;
import org.usfirst.frc.team687.robot.utilities.NerdyPID;
import org.usfirst.frc.team687.robot.Constants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turn to a specified angle according to field (no vision)
 */

public class TurnToAngle extends Command {
	
	private double m_angleToTurn;
	private int m_counter = 0;
	private double m_startTime;
	private double m_timeout;
	
	private NerdyPID m_rotPID;
	
	public TurnToAngle(double angle) {
		m_angleToTurn = angle;
		m_timeout = 3; // default timeout is 3 seconds
		
		// subsystem dependencies
		requires(Robot.drive);
	}
	
	public TurnToAngle(double angle, double timeout) {
		m_angleToTurn = angle;
		m_timeout = timeout;
		
		// subsystem dependencies
		requires(Robot.drive);
	}

	@Override
	protected void initialize() {
		SmartDashboard.putString("Current Command", "TurnToAngle");
		m_startTime = Timer.getFPGATimestamp();
		m_rotPID = new NerdyPID(Constants.kRotP, Constants.kRotI, Constants.kRotD);
		m_rotPID.setOutputRange(Constants.kMinRotPower, Constants.kMaxRotPower);
		m_rotPID.setDesired(m_angleToTurn);
	}

	@Override
	protected void execute() {
		double robotAngle = (360-Robot.drive.getCurrentYaw()) % 360;
		double error = m_angleToTurn - robotAngle;
		error = NerdyMath.boundAngle(error);
		SmartDashboard.putNumber("Angle Error", error);
		double power = m_rotPID.calculate(Robot.drive.getCurrentYaw());
		if (Math.abs(error) <= Constants.kDriveRotationTolerance) {
			m_counter += 1;
		}	else	{
			m_counter = 0;
		}
		Robot.drive.setPower(power, -power);
	}

	@Override
	protected boolean isFinished() {
		return m_counter > Constants.kDriveRotationOscillationCount || Timer.getFPGATimestamp() - m_startTime > m_timeout;
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
