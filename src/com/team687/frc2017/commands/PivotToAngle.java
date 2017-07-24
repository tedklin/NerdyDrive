package com.team687.frc2017.commands;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.NerdyPID;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Pivot on one side of the drivetrain to turn to an angle (absolute)
 *
 * @author tedfoodlin
 *
 */

public class PivotToAngle extends Command {

    private double m_angle;

    private double m_timeout;
    private double m_startTime;

    private NerdyPID m_rotPID;
    private boolean m_isRightTurn;
    private boolean m_isQuickPivot;
    private boolean m_isForward;

    /**
     * @param angle
     * @param isQuickPivot
     * @param isForward
     */
    public PivotToAngle(double angle, boolean isQuickPivot, boolean isForward) {
	m_angle = angle;
	m_timeout = 6.87; // default
	m_isQuickPivot = isQuickPivot;
	m_isForward = isForward;

	requires(Robot.drive);
    }

    /**
     * @param angle
     * @param timeout
     * @param isQuickPivot
     * @param isForward
     */
    public PivotToAngle(double angle, boolean isQuickPivot, boolean isForward, double timeout) {
	m_angle = angle;
	m_timeout = timeout;
	m_isQuickPivot = isQuickPivot;
	m_isForward = isForward;

	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	m_startTime = Timer.getFPGATimestamp();

	if (m_angle < 0) {
	    m_isRightTurn = false;
	} else if (m_angle > 0) {
	    m_isRightTurn = true;
	}

	m_rotPID = new NerdyPID();
	m_rotPID.setPID(Constants.kRotP, Constants.kRotI, Constants.kRotD);
	m_rotPID.setDesired(m_angle);
	m_rotPID.setOutputRange(Constants.kMinRotPower, Constants.kMaxRotPower);
	m_rotPID.setGyro(true);

	Robot.drive.stopDrive();
	Robot.drive.shiftDown();
    }

    @Override
    protected void execute() {
	double power = m_rotPID.calculate(Robot.drive.getCurrentYaw());

	if (m_isQuickPivot && Math.abs(power) > 0.1) {
	    power *= 2;
	}

	if (m_isRightTurn && m_isForward) {
	    Robot.drive.setPower(power, 0);
	} else if (!m_isRightTurn && m_isForward) {
	    Robot.drive.setPower(0, -power);
	} else if (m_isRightTurn && !m_isForward) {
	    Robot.drive.setPower(-power, 0);
	} else if (!m_isRightTurn && !m_isForward) {
	    Robot.drive.setPower(0, power);
	}
    }

    @Override
    protected boolean isFinished() {
	return Math.abs(Robot.drive.getCurrentYaw() - m_angle) <= Constants.kDriveRotationTolerance
		|| Timer.getFPGATimestamp() - m_startTime >= m_timeout;
    }

}
