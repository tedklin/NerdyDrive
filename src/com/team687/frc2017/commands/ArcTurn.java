package com.team687.frc2017.commands;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.NerdyMath;
import com.team687.frc2017.utilities.NerdyPID;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArcTurn extends Command {
	
	private double m_angle;
	
	private double m_timeout;
	private double m_startTime;
	
	private double m_straightPower;
	private NerdyPID m_rotPID;
	
	/**
	 * @param angle
	 * @param straightPower
	 */
	public ArcTurn(double angle, double straightPower) {
		m_angle = angle;
		m_timeout = 6.87; //default
		m_straightPower = straightPower;

		requires(Robot.drive);
	}
	
	/**
	 * @param angle
	 * @param straightPower
	 * @param timeout
	 */
	public ArcTurn(double angle, double straightPower, double timeout) {
		m_angle = angle;
		m_timeout = timeout;
		m_straightPower = straightPower;
		
		requires(Robot.drive);
	}

	@Override
	protected void initialize() {
		SmartDashboard.putString("Current Command", "ArcTurn");
		
		m_rotPID = new NerdyPID();
		m_rotPID.setPID(Constants.kRotP, Constants.kRotI, Constants.kRotD);
		m_rotPID.setDesired(m_angle);
		m_rotPID.setOutputRange(Constants.kMinRotPower, Constants.kMaxRotPower);
		
		Robot.drive.stopDrive();
		m_startTime = Timer.getFPGATimestamp();
	}

	@Override
	protected void execute() {
		double rotPower = m_rotPID.calculate(Robot.drive.getCurrentYaw());
		double leftPower = m_straightPower + rotPower;
		leftPower = (leftPower < 0) ? leftPower : 0;
		double rightPower = m_straightPower - rotPower;
		rightPower = (rightPower < 0) ? rightPower : 0;
		Robot.drive.setPower(leftPower, rightPower);
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(NerdyMath.boundAngle(Robot.drive.getCurrentYaw()) - m_angle) <= Constants.kDriveRotationTolerance 
				|| Timer.getFPGATimestamp() - m_startTime >= m_timeout;
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
