package com.team687.frc2017.commands;

import java.util.ArrayList;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.BezierCurve;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive a path generated from Bezier curve based on 1241
 *
 * @author tedfoodlin
 *
 */

public class DriveBezierRio extends Command {

    private BezierCurve m_path;
    private ArrayList<Double> m_heading;
    private ArrayList<Double> m_arcLength;
    private int m_counter;

    private boolean m_pathIsFinished;

    public DriveBezierRio(double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3) {
	m_path = new BezierCurve(x0, y0, x1, y1, x2, y2, x3, y3);
    }

    public DriveBezierRio(double[] path) {
	m_path = new BezierCurve(path[0], path[1], path[2], path[3], path[4], path[5], path[6], path[7]);
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
    }

    @Override
    protected void execute() {
	if (m_counter < m_arcLength.size()) {
	    if (Robot.drive.getDrivetrainTicks() < m_arcLength.get(m_counter)) {
		double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
		double error = -m_heading.get(m_counter) - robotAngle;
		error = (error > 180) ? error - 360 : error;
		error = (error < -180) ? error + 360 : error;
		double straightPower = 0.8;
		double rotPower = Constants.kRotP * error;

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
	return m_pathIsFinished || Robot.drive.getDrivetrainTicks() >= m_arcLength.get(m_arcLength.size() - 1);
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
