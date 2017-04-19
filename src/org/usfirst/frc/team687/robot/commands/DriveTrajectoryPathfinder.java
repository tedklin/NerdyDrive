//package org.usfirst.frc.team687.robot.commands;
//
//import org.usfirst.frc.team687.robot.Constants;
//import org.usfirst.frc.team687.robot.Robot;
//import org.usfirst.frc.team687.robot.utilities.NerdyMath;
//import org.usfirst.frc.team687.robot.utilities.NerdyPID;
//
//import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import jaci.pathfinder.*;
//
///**
// * Drive a trajectory from Pathfinder (https://github.com/JacisNonsense/Pathfinder)
// * Includes heading correction on top of encoder algorithm
// *
// * @author tedfoodlin
// *
// */
//
//public class DriveTrajectoryPathfinder extends Command {
//	
//	private Waypoint[] m_path;
//
//	private Trajectory m_trajectory;
//	private Trajectory m_leftTrajectory;
//	private Trajectory m_rightTrajectory;
//	
//	private EncoderFollower m_leftFollower;
//	private EncoderFollower m_rightFollower;
//	private NerdyPID m_headingCorrection;
//	
//	public DriveTrajectoryPathfinder(Waypoint[] path) {
//		m_path = path;
//		
//		// subsystem dependencies
//		requires(Robot.drive);
//	}
//	
//	@Override
//	protected void initialize() {
//		SmartDashboard.putString("Current Command", "DriveTrajectoryPathfinder");
//        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 
//        		Constants.kDt, Constants.kMaxVelocity, Constants.kMaxAcceleration, Constants.kMaxJerk);
//        m_trajectory = Pathfinder.generate(m_path, config);
//        TankModifier modifier = new TankModifier(m_trajectory).modify(Constants.kWheelbaseWidth);
//        
//        m_leftTrajectory = modifier.getLeftTrajectory();
//        m_rightTrajectory = modifier.getRightTrajectory();
//        
//        m_leftFollower = new EncoderFollower(m_leftTrajectory);
//        m_rightFollower = new EncoderFollower(m_rightTrajectory);
//        
//        m_leftFollower.configurePIDVA(Constants.kDistP, Constants.kDistI, Constants.kDistD, Constants.kV, Constants.kA);
//        m_rightFollower.configurePIDVA(Constants.kDistP, Constants.kDistI, Constants.kDistD, Constants.kV, Constants.kA);
//        
//        m_leftFollower.configureEncoder(Robot.drive.getLeftTicks(), Constants.kTicksPerRev, Constants.kWheelDiameter);
//        m_rightFollower.configureEncoder(Robot.drive.getRightTicks(), Constants.kTicksPerRev, Constants.kWheelDiameter);
//        
//        m_headingCorrection = new NerdyPID(Constants.kRotP, Constants.kRotI, Constants.kRotD);
//        
//        Robot.drive.stopDrive();
//	}
//	
//	@Override
//	protected void execute() {
//		double leftPow = m_leftFollower.calculate(Robot.drive.getLeftTicks());
//		double rightPow = m_rightFollower.calculate(Robot.drive.getRightTicks());
//		
//		m_headingCorrection.setDesired(m_leftFollower.getHeading());
//		double angularPow = m_headingCorrection.calculate(Robot.drive.getYaw());
//		leftPow += angularPow;
//		rightPow -= angularPow;
//		
//		double[] pow = {leftPow, rightPow};
//		NerdyMath.normalize(pow, false);
//		Robot.drive.setPower(pow[0], pow[1]);
//	}
//
//	@Override
//	protected boolean isFinished() {
//		return m_leftFollower.isFinished() && m_rightFollower.isFinished();
//	}
//	
//	@Override 
//	protected void end() {
//		reset();
//		Robot.drive.stopDrive();
//	}
//	
//	@Override
//	protected void interrupted() {
//		end();
//	}
//	
//	private void reset() {
//		m_leftFollower.reset();
//		m_rightFollower.reset();
//	}
//
//}
