package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Constants;
import org.usfirst.frc.team687.robot.Robot;
import org.usfirst.frc.team687.robot.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.Command;

import jaci.pathfinder.*;

/**
 * Drive a trajectory from Pathfinder (https://github.com/JacisNonsense/Pathfinder)
 *
 * @author tedfoodlin
 *
 */

public class DriveTrajectoryPathfinder extends Command {
	
	private Waypoint[] m_path;
	private double m_dT = 0.01;

	private Trajectory m_trajectory;
	private Trajectory m_leftTrajectory;
	private Trajectory m_rightTrajectory;
	
	private EncoderFollower m_leftFollower;
	private EncoderFollower m_rightFollower;
	
	private double m_leftPow;
	private double m_rightPow;
	
	public DriveTrajectoryPathfinder(Waypoint[] path) {
		m_path = path;
		
		// subsystem dependencies
		requires(Robot.drive);
	}
	
	@Override
	protected void initialize() {
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 
        		m_dT, Constants.kMaxVelocity, Constants.kMaxAcceleration, Constants.kMaxJerk);
        m_trajectory = Pathfinder.generate(m_path, config);
        TankModifier modifier = new TankModifier(m_trajectory).modify(Constants.kWheelbaseWidth);
        
        m_leftTrajectory = modifier.getLeftTrajectory();
        m_rightTrajectory = modifier.getRightTrajectory();
        
        m_leftFollower = new EncoderFollower(m_leftTrajectory);
        m_rightFollower = new EncoderFollower(m_rightTrajectory);
        
        m_leftFollower.configurePIDVA(Constants.kDistP, Constants.kDistI, Constants.kDistD, Constants.kV, Constants.kA);
        m_rightFollower.configurePIDVA(Constants.kDistP, Constants.kDistI, Constants.kDistD, Constants.kV, Constants.kA);
        
        m_leftFollower.configureEncoder(0, Constants.kTicksPerRev, Constants.kWheelDiameter);
        m_rightFollower.configureEncoder(0, Constants.kTicksPerRev, Constants.kWheelDiameter);
	}
	
	@Override
	protected void execute() {
		m_leftPow = m_leftFollower.calculate(Robot.drive.getLeftTicks());
		m_rightPow = m_rightFollower.calculate(Robot.drive.getRightTicks());
		
		double[] pow = {m_leftPow, m_rightPow};
		NerdyMath.normalize(pow, false);
		
		Robot.drive.setPower(pow[0], pow[1]);
	}

	@Override
	protected boolean isFinished() {
		return m_leftFollower.isFinished() && m_rightFollower.isFinished();
	}
	
	@Override 
	protected void end() {
		reset();
	}
	
	@Override
	protected void interrupted() {
		end();
	}
	
	private void reset() {
		m_leftFollower.reset();
		m_rightFollower.reset();
		Robot.drive.stopDrive();
	}

}