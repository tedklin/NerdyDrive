package com.team687.frc2017.commands;

import java.util.ArrayList;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.BezierCurve;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive a path generated from Bezier curve
 *
 * @author tedlin
 *
 */

public class DriveBezierRio extends Command {

    private BezierCurve m_path;
    private double m_basePower = 1; // always equal to 1
    private double m_straightPower;
    private boolean m_straightPowerIsDynamic;
    private boolean m_softStop;

    private ArrayList<Double> m_heading;
    private double m_desiredHeading;
    private ArrayList<Double> m_arcLength;

    private int m_counter;
    private boolean m_pathIsFinished;
    private double m_direction;

    public DriveBezierRio(double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3,
	    double straightPower, boolean straightPowerIsDynamic, boolean softStop) {
	m_path = new BezierCurve(x0, y0, x1, y1, x2, y2, x3, y3);
	m_straightPower = straightPower;
	m_straightPowerIsDynamic = straightPowerIsDynamic;
	m_softStop = softStop;
    }

    /**
     * 
     * @param path
     * @param straightPower
     *            (postive if going forward (forward is side with climber), negative
     *            if going backwards)
     * @param straightPowerIsDynamic
     *            (true for paths with sharp turns)
     * @param softStop
     *            (if you want to slow down near end)
     */
    public DriveBezierRio(double[] path, double straightPower, boolean straightPowerIsDynamic, boolean softStop) {
	m_path = new BezierCurve(path[0], path[1], path[2], path[3], path[4], path[5], path[6], path[7]);
	m_straightPower = straightPower;
	m_straightPowerIsDynamic = straightPowerIsDynamic;
	m_softStop = softStop;
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "DriveBezierRio");
	Robot.drive.stopDrive();
	Robot.drive.resetEncoders();
	Robot.drive.shiftUp();

	m_path.calculateBezier();
	m_heading = m_path.getHeading();
	m_arcLength = m_path.getArcLength();

	m_counter = 0;
	m_pathIsFinished = false;
	m_direction = Math.signum(m_straightPower);
    }

    @Override
    protected void execute() {
	if (m_counter < m_arcLength.size()) {
	    if (Math.abs(Robot.drive.getDrivetrainTicks()) < m_arcLength.get(m_counter)) {
		double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
		m_desiredHeading = m_heading.get(m_counter); // TOOD: figure out if we have to modify this value when
							     // going reverse.
		// proposed solution for going reverse
		// if (m_direction < 0) {
		// m_desiredHeading += 180;
		// }
		m_desiredHeading = -m_desiredHeading; // This is always necessary because of how our rotational PID is
						      // structured.
		double error = m_desiredHeading - robotAngle;

		error = (error > 180) ? error - 360 : error;
		error = (error < -180) ? error + 360 : error;

		double rotPower = Constants.kRotPBezier * error;
		// default is specified straight power
		double straightPower = m_straightPower;

		// dynamic straight power
		if (m_straightPowerIsDynamic) {
		    straightPower = m_direction * m_basePower / (Math.abs(error) * Constants.kStraightPowerAdjuster);
		}

		double maxStraightPower = Math.abs(m_straightPower);
		if (m_softStop) {
		    double straightError = m_arcLength.get(m_arcLength.size() - 1)
			    - Math.abs(Robot.drive.getDrivetrainTicks());
		    double newMaxStraightPower = Constants.kDistPBezier * straightError;
		    maxStraightPower = Math.min(Math.abs(maxStraightPower), Math.abs(newMaxStraightPower));
		}

		// limit straight power to maintain rotPower to straightPower ratio
		// also for soft landings
		if (Math.abs(straightPower) > maxStraightPower) {
		    straightPower = maxStraightPower * m_direction;
		}

		// make sure robot reaches end point
		if (Math.abs(straightPower) < Constants.kMinStraightPower) {
		    straightPower = Constants.kMinStraightPower * m_direction;
		}

		double leftPow = rotPower + straightPower;
		double rightPow = rotPower - straightPower;
		Robot.drive.setPower(leftPow, rightPow);
	    } else {
		m_counter++;
	    }
	} else {
	    m_pathIsFinished = true;
	}
    }

    @Override
    protected boolean isFinished() {
	return m_pathIsFinished
		|| Math.abs(Robot.drive.getDrivetrainTicks()) >= m_arcLength.get(m_arcLength.size() - 1);
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
