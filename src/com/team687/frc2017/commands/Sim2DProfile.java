package com.team687.frc2017.commands;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.MotionProfileGenerator;
import com.team687.frc2017.utilities.Waypoint;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Path following based on 1114
 * 
 * @author tedlin
 *
 */

public class Sim2DProfile extends Command {

    private MotionProfileGenerator m_motionProfile;
    private Waypoint[] m_path;
    private Waypoint point1;
    private Waypoint point2;
    private Waypoint point3;
    private Waypoint point4;

    private double m_lastError;
    private double m_straightError;
    private double m_rotError;
    private double m_timestamp;
    private double m_startTime;

    public Sim2DProfile(Waypoint[] path) {
	m_path = path;
	point1 = m_path[0];
	point2 = m_path[1];
	point3 = m_path[2];
	point4 = m_path[3];
    }

    @Override
    protected void initialize() {
	m_motionProfile = new MotionProfileGenerator(Constants.kCruiseVelocity, Constants.kMaxAcceleration,
		-Constants.kMaxAcceleration);
	m_motionProfile.generateProfile(getTotalPathDistance());
	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
	m_lastError = m_straightError;
	m_timestamp = Timer.getFPGATimestamp() - m_startTime;
	int index = (int) (m_timestamp / Constants.kDt);
	if (m_timestamp > m_motionProfile.getAccelTime() * 60) {
	    index += 1;
	} else if (m_timestamp >= ((m_motionProfile.getAccelTime() + m_motionProfile.getCruiseTime()) * 60)) {
	    index += 2;
	}
	double setpoint = m_motionProfile.readPosition(index);
	double goalVelocity = m_motionProfile.readVelocity(index);
	double goalAccel = m_motionProfile.readAcceleration(index);

	double feedforward = (Constants.kV * goalVelocity) + (Constants.kA * goalAccel);

	m_straightError = setpoint - Robot.drive.getDrivetrainTicks();

	double straightPower = (Constants.kDistP * m_straightError)
		+ (Constants.kDistD * ((m_straightError - m_lastError) / Constants.kDt - goalVelocity)) + feedforward;

	double heading = 0;
	if (Robot.drive.getDrivetrainTicks() < getFirstSegmentDistance()) {
	    heading = point2.getTheta();
	} else if (Robot.drive.getDrivetrainTicks() < getSecondSegmentDistance()) {
	    heading = point3.getTheta();
	} else if (Robot.drive.getDrivetrainTicks() < getTotalPathDistance()) {
	    heading = point4.getTheta();
	} else {
	    heading = point4.getTheta();
	}

	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	m_rotError = -heading - robotAngle;
	m_rotError = (m_rotError > 180) ? m_rotError - 360 : m_rotError;
	m_rotError = (m_rotError < -180) ? m_rotError + 360 : m_rotError;
	double rotPower = Constants.kRotP * m_rotError;
	Robot.drive.setPower(rotPower + straightPower, rotPower - straightPower);
    }

    @Override
    protected boolean isFinished() {
	return Robot.drive.getDrivetrainTicks() > getTotalPathDistance();
    }

    @Override
    protected void end() {
	Robot.drive.setPower(0, 0);
    }

    @Override
    protected void interrupted() {
	end();
    }

    private double getDistanceBetweenWaypoints(Waypoint point1, Waypoint point2) {
	double delta_x = point2.getX() - point1.getX();
	double delta_y = point2.getY() - point1.getY();

	return Math.sqrt(Math.pow(delta_x, 2) + Math.pow(delta_y, 2));
    }

    private double getFirstSegmentDistance() {
	return getDistanceBetweenWaypoints(point1, point2);
    }

    private double getSecondSegmentDistance() {
	return getFirstSegmentDistance() + getDistanceBetweenWaypoints(point2, point3);
    }

    private double getTotalPathDistance() {
	return getSecondSegmentDistance() + getDistanceBetweenWaypoints(point3, point4);
    }

}
