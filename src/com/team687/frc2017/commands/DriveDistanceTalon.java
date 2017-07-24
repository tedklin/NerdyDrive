package com.team687.frc2017.commands;

import com.ctre.CANTalon;
import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.MotionProfileGenerator;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive a straight distance with built-in Talon motion profiling
 * 
 * @author tedfoodlin
 * 
 */

public class DriveDistanceTalon extends Command {

    private double m_distance;
    private MotionProfileGenerator m_motionProfile;

    class PeriodicRunnable implements java.lang.Runnable {
	public void run() {
	    Robot.drive.processMotionProfileBuffer();
	}
    }

    Notifier m_notifer = new Notifier(new PeriodicRunnable());

    /**
     * @param distance
     */
    public DriveDistanceTalon(double distance) {
	m_distance = distance;

	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "DriveDistanceTalon");
	Robot.drive.stopDrive();
	Robot.drive.resetEncoders();
	Robot.drive.shiftDown();
	Robot.drive.setValueMotionProfileOutput(CANTalon.SetValueMotionProfile.Disable);

	Robot.drive.changeMotionControlFramePeriod(5);
	m_notifer.startPeriodic(0.005);

	m_motionProfile = new MotionProfileGenerator(Constants.kMaxVelocity, Constants.kMaxAcceleration,
		-Constants.kMaxAcceleration);
	m_motionProfile.generateProfile(m_distance);

	Robot.drive.clearMotionProfileTrajectories();
	CANTalon.TrajectoryPoint point = new CANTalon.TrajectoryPoint();
	for (int i = 0; i < m_motionProfile.getTotalPoints(); i++) {
	    point.velocity = m_motionProfile.readVelocity(i);
	    point.timeDurMs = 10;
	    point.velocityOnly = true;

	    point.zeroPos = false;
	    if (i == 0) {
		point.zeroPos = true;
	    }

	    point.isLastPoint = false;
	    if ((i + 1) == m_motionProfile.getTotalPoints()) {
		point.isLastPoint = true;
	    }

	    Robot.drive.pushTrajectoryPoint(point);
	}
    }

    @Override
    protected void execute() {
	Robot.drive.setValueMotionProfileOutput(CANTalon.SetValueMotionProfile.Enable);
    }

    @Override
    protected boolean isFinished() {
	return Robot.drive.isMotionProfileFinished();
    }

    @Override
    protected void end() {
	resetMotionProfile();
	Robot.drive.stopDrive();
	Robot.drive.resetEncoders();
    }

    @Override
    protected void interrupted() {
	end();
    }

    public void resetMotionProfile() {
	Robot.drive.clearMotionProfileTrajectories();
	Robot.drive.setValueMotionProfileOutput(CANTalon.SetValueMotionProfile.Disable);
    }

}
