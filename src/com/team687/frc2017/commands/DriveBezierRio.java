package com.team687.frc2017.commands;

import java.util.ArrayList;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.BezierCurve;
import com.team687.frc2017.utilities.NerdyPID;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive a path generated from Bezier curve
 *
 * @author tedfoodlin
 *
 */

public class DriveBezierRio extends Command {
	
	private BezierCurve m_path;
	private ArrayList<Double> m_heading;
	private ArrayList<Double> m_arcLength;
	private int m_counter;
	
	private NerdyPID m_rotPID;
	private NerdyPID m_distPID;
	private boolean m_pathIsFinished;
	
	public DriveBezierRio(double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3) {
		m_path = new BezierCurve(x0, y0, x1, y1, x2, y2, x3, y3);
	}
	
	@Override
	protected void initialize() {
		SmartDashboard.putString("Current Command", "DriveBezierRio");
		Robot.drive.stopDrive();
		
		m_path.calculateBezier();
		m_heading = m_path.getHeading();
		m_arcLength = m_path.getArcLength();
		
		m_rotPID = new NerdyPID(Constants.kRotP, Constants.kRotI, Constants.kRotD);
		m_rotPID.setGyro(true);
		m_rotPID.setOutputRange(Constants.kMinRotPower, Constants.kMaxRotPower);
		
		m_distPID = new NerdyPID(Constants.kDistP, Constants.kDistI, Constants.kDistD);
		m_distPID.setGyro(false);
		m_distPID.setOutputRange(Constants.kBezierMinStraightPow, Constants.kBezierMaxStraightPow);
		m_distPID.setDesired(m_arcLength.get(m_arcLength.size() - 1));
		
		m_counter = 0;
		m_pathIsFinished = false;
	}

	@Override
	protected void execute() {
		double rotPow = 0;
		double straightPow = 0;
		if (m_counter < m_arcLength.size()) {
			if (Robot.drive.getDrivetrainPosition() < m_arcLength.get(m_counter)) {
				m_rotPID.setDesired(m_heading.get(m_counter));
				rotPow = m_rotPID.calculate(Robot.drive.getCurrentYaw());
				straightPow = m_distPID.calculate(Robot.drive.getDrivetrainPosition());
				
				double leftPow = straightPow + rotPow;
				double rightPow = straightPow - rotPow;
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
		return m_pathIsFinished || Robot.drive.getDrivetrainPosition() <= m_arcLength.get(m_arcLength.size() - 1);
	}

	@Override
	protected void end() {
		Robot.drive.stopDrive();
		m_path.clearAll();
	}

	@Override
	protected void interrupted() {
		end();
	}
	
}
