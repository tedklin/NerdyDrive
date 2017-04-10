//package org.usfirst.frc.team687.robot.commands;
//
//import org.usfirst.frc.team687.robot.Constants;
//import org.usfirst.frc.team687.robot.Robot;
//import org.usfirst.frc.team687.robot.utilities.MotionProfileGenerator;
//
//import com.ctre.CANTalon;
//
//import edu.wpi.first.wpilibj.Notifier;
//import edu.wpi.first.wpilibj.command.Command;
//
///**
// * Drive a trajectory with built-in Talon motion profiling
// * 
// * @author tedfoodlin
// * 
// */
//
//public class DriveDistanceTalon extends Command {
//	
//	private double m_distance;
//	private MotionProfileGenerator m_motionProfile;
//	
//	class PeriodicRunnable implements java.lang.Runnable {
//	    public void run() {  
//	    	Robot.drive.processMotionProfileBuffer();  
//	    }
//	}
//	Notifier m_notifer = new Notifier(new PeriodicRunnable());
//
//    public DriveDistanceTalon(double distance) {
//    	m_distance = distance;
//    	
//    	// subsystem dependencies
//        requires(Robot.drive);
//    }
//
//	@Override
//	protected void initialize() {
//		Robot.drive.setValueMotionProfileOutput(CANTalon.SetValueMotionProfile.Disable);
//		Robot.drive.resetSensors();
//		Robot.drive.shiftDown();
//		
//		Robot.drive.changeMotionControlFramePeriod(5);
//		m_notifer.startPeriodic(0.005);
//		
//		m_motionProfile = new MotionProfileGenerator(Constants.kMaxVelocity, Constants.kMaxAcceleration, -Constants.kMaxAcceleration);
//		m_motionProfile.generateProfile(m_distance);
//		
//		CANTalon.TrajectoryPoint point = new CANTalon.TrajectoryPoint();
//		for (int i = 0; i < m_motionProfile.getTotalPoints(); i++) {
//			point.velocity = m_motionProfile.readVelocity(i);
//			point.timeDurMs = 10;
//			point.velocityOnly = true;
//			
//			point.zeroPos = false;
//			if (i == 0) {
//				point.zeroPos = true;
//			}
//			
//			point.isLastPoint = false;
//			if ((i + 1) == m_motionProfile.getTotalPoints()) {
//				point.isLastPoint = true;
//			}
//			
//			Robot.drive.pushTrajectoryPoint(point);
//		}
//	}
//
//	@Override
//	protected void execute() {
//		Robot.drive.setValueMotionProfileOutput(CANTalon.SetValueMotionProfile.Enable);
//	}
//
//	@Override
//	protected boolean isFinished() {
//		return Math.abs(Robot.drive.getDrivetrainPosition() - m_distance) <= 1;
//	}
//
//	@Override
//	protected void end() {
//		resetMotionProfile();
//	}
//
//	@Override
//	protected void interrupted() {
//		end();
//	}
//	
//    public void resetMotionProfile() {
//    	Robot.drive.clearMotionProfileTrajectories();
//		Robot.drive.setValueMotionProfileOutput(CANTalon.SetValueMotionProfile.Disable);
//    }
//
//}
