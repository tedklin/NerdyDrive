package com.team687.frc2017.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.team687.frc2017.RobotMap;
import com.team687.frc2017.commands.teleop.TankDrive;
import com.team687.frc2017.utilities.NerdyMath;
import com.team687.lib.kauailabs.navx.frc.AHRS;
import com.team687.lib.kauailabs.sf2.frc.navXSensor;
import com.team687.lib.kauailabs.sf2.orientation.OrientationHistory;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive subsystem
 *
 * @author tedlin
 *
 */

public class Drive extends Subsystem {

    private final CANTalon m_leftMaster, m_leftSlave1, m_leftSlave2;
    private final CANTalon m_rightMaster, m_rightSlave1, m_rightSlave2;

    private final DoubleSolenoid m_shifter;

    private final AHRS m_nav;
    private final navXSensor m_navxsensor;
    private final OrientationHistory m_orientationHistory;

    private double m_initTime;
    private double m_currentTime;

    private boolean m_brakeModeOn;

    public Drive() {
	m_leftMaster = new CANTalon(RobotMap.kLeftMasterTalonID);
	m_leftSlave1 = new CANTalon(RobotMap.kLeftSlaveTalon1ID);
	m_leftSlave2 = new CANTalon(RobotMap.kLeftSlaveTalon2ID);
	m_rightMaster = new CANTalon(RobotMap.kRightMasterTalonID);
	m_rightSlave1 = new CANTalon(RobotMap.kRightSlaveTalon1ID);
	m_rightSlave2 = new CANTalon(RobotMap.kRightSlaveTalon2ID);

	m_leftMaster.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
	CANTalon.FeedbackDeviceStatus leftSensorPresent = m_leftMaster
		.isSensorPresent(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
	if (leftSensorPresent != CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent) {
	    DriverStation.reportError("Could not detect left encoder: " + leftSensorPresent, false);
	}
	m_leftMaster.reverseSensor(false);
	m_leftMaster.reverseOutput(false);
	m_leftSlave1.reverseOutput(false);
	m_leftSlave2.reverseOutput(false);
	m_leftMaster.enableBrakeMode(true);
	m_leftSlave1.enableBrakeMode(true);
	m_leftSlave2.enableBrakeMode(true);

	m_rightMaster.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
	CANTalon.FeedbackDeviceStatus rightSensorPresent = m_rightMaster
		.isSensorPresent(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
	if (rightSensorPresent != CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent) {
	    DriverStation.reportError("Could not detect right encoder: " + rightSensorPresent, false);
	}
	m_rightMaster.reverseSensor(true);
	m_rightMaster.reverseOutput(true);
	m_rightSlave1.reverseOutput(true);
	m_rightSlave2.reverseOutput(true);
	m_rightMaster.enableBrakeMode(true);
	m_rightSlave1.enableBrakeMode(true);
	m_rightSlave2.enableBrakeMode(true);

	m_brakeModeOn = true;

	m_shifter = new DoubleSolenoid(RobotMap.kShifterID1, RobotMap.kShifterID2);

	m_nav = new AHRS(SerialPort.Port.kMXP);
	m_navxsensor = new navXSensor(m_nav, "Drivetrain Orientation");
	m_orientationHistory = new OrientationHistory(m_navxsensor, m_nav.getRequestedUpdateRate() * 10);
    }

    @Override
    protected void initDefaultCommand() {
	setDefaultCommand(new TankDrive());
    }

    /**
     * Set drivetrain motor power to value between -1.0 and +1.0
     * 
     * @param leftPower
     * @param rightPower
     */
    public void setPower(double leftPower, double rightPower) {
	m_leftMaster.changeControlMode(TalonControlMode.PercentVbus);
	m_leftSlave1.changeControlMode(TalonControlMode.PercentVbus);
	m_leftSlave2.changeControlMode(TalonControlMode.PercentVbus);
	m_rightMaster.changeControlMode(TalonControlMode.PercentVbus);
	m_rightSlave1.changeControlMode(TalonControlMode.PercentVbus);
	m_rightSlave2.changeControlMode(TalonControlMode.PercentVbus);

	m_leftMaster.set(leftPower);
	m_leftSlave1.set(leftPower);
	m_leftSlave2.set(leftPower);

	m_rightMaster.set(rightPower);
	m_rightSlave1.set(rightPower);
	m_rightSlave2.set(rightPower);
    }

    public void setBrakeMode(boolean enabled) {
	m_leftMaster.enableBrakeMode(enabled);
	m_leftSlave1.enableBrakeMode(enabled);
	m_leftSlave2.enableBrakeMode(enabled);

	m_rightMaster.enableBrakeMode(enabled);
	m_rightSlave1.enableBrakeMode(enabled);
	m_rightSlave2.enableBrakeMode(enabled);

	m_brakeModeOn = enabled;
    }

    public boolean getBrakeMode() {
	return m_brakeModeOn;
    }

    public double squareInput(double input) {
	return Math.pow(input, 2) * (input / Math.abs(input));
    }

    // public double addLeftSensitivity(double input) {
    // return NerdyMath.addSensitivity(input, Robot.oi.getThrottleL());
    // }
    //
    // public double addRightSensitivity(double input) {
    // return NerdyMath.addSensitivity(input, Robot.oi.getThrottleR());
    // }

    /**
     * Handles when the joystick moves slightly when you actually don't want it to
     * move at all
     * 
     * @param value
     * @param deadband
     * @return value or 0 if within deadband
     */
    public double handleDeadband(double val, double deadband) {
	return (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;
    }

    public void shiftUp() {
	m_shifter.set(DoubleSolenoid.Value.kForward);
    }

    public void shiftDown() {
	m_shifter.set(DoubleSolenoid.Value.kReverse);
    }

    public boolean isHighGear() {
	return m_shifter.get() == DoubleSolenoid.Value.kForward;
    }

    public double getCurrentYaw() {
	return m_nav.getYaw();
    }

    public double getCurrentPitch() {
	return m_nav.getPitch();
    }

    public double getCurrentRoll() {
	return m_nav.getRoll();
    }

    public double getCurrentYawRadians() {
	return NerdyMath.degreesToRadians(m_nav.getYaw());
    }

    public double getCurrentPitchRadians() {
	return NerdyMath.degreesToRadians(m_nav.getPitch());
    }

    public double getCurrentRollRadians() {
	return NerdyMath.degreesToRadians(m_nav.getRoll());
    }

    public double getNavTimestamp() {
	return m_nav.getLastSensorTimestamp();
    }

    public double getHistoricalYaw(long timestamp) {
	return m_orientationHistory.getYawDegreesAtTime(timestamp);
    }

    public double timeMachineYaw(double processingTime) {
	long navxTimestamp = m_nav.getLastSensorTimestamp();
	navxTimestamp -= (1000 * processingTime); /* look backwards in time */
	return getHistoricalYaw(navxTimestamp);
    }

    public void resetGyro() {
	m_nav.reset();
    }

    public double getCurrentAccelX() {
	return m_nav.getWorldLinearAccelX();
    }

    public double getCurrentAccelY() {
	return m_nav.getWorldLinearAccelY();
    }

    public double getCurrentAccelZ() {
	return m_nav.getWorldLinearAccelZ();
    }

    public double getInitTime() {
	return m_initTime;
    }

    public double getCurrentTime() {
	return m_currentTime;
    }

    public double getLeftPosition() {
	return m_leftMaster.getPosition();
    }

    public double getRightPosition() {
	return m_rightMaster.getPosition();
    }

    public double getDrivetrainPosition() {
	return (getLeftPosition() + getRightPosition() / 2);
    }

    public double getLeftSpeed() {
	return m_leftMaster.getSpeed();
    }

    public double getRightSpeed() {
	return m_rightMaster.getSpeed();
    }

    public void resetEncoders() {
	// m_leftMaster.reset();
	// m_rightMaster.reset();

	m_leftMaster.setPosition(0);
	m_rightMaster.setPosition(0);

	// m_leftMaster.setEncPosition(0);
	// m_rightMaster.setEncPosition(0);
    }

    public void processMotionProfileBuffer() {
	processLeftMotionProfileBuffer();
	processRightMotionProfileBuffer();
    }

    public void processLeftMotionProfileBuffer() {
	m_leftMaster.processMotionProfileBuffer();
    }

    public void processRightMotionProfileBuffer() {
	m_rightMaster.processMotionProfileBuffer();
    }

    public void changeMotionControlFramePeriod(int time) {
	m_leftMaster.changeMotionControlFramePeriod(time);
	m_rightMaster.changeMotionControlFramePeriod(time);
    }

    public void pushTrajectoryPoint(CANTalon.TrajectoryPoint point) {
	pushLeftTrajectoryPoint(point);
	pushRightTrajectoryPoint(point);
    }

    public void pushLeftTrajectoryPoint(CANTalon.TrajectoryPoint point) {
	m_leftMaster.pushMotionProfileTrajectory(point);
    }

    public void pushRightTrajectoryPoint(CANTalon.TrajectoryPoint point) {
	m_rightMaster.pushMotionProfileTrajectory(point);
    }

    public void clearMotionProfileTrajectories() {
	m_leftMaster.clearMotionProfileTrajectories();
	m_rightMaster.clearMotionProfileTrajectories();
    }

    public void setValueMotionProfileOutput(CANTalon.SetValueMotionProfile output) {
	setLeftValueMotionProfileOutput(output);
	setRightValueMotionProfileOutput(output);
    }

    public void setLeftValueMotionProfileOutput(CANTalon.SetValueMotionProfile output) {
	m_leftMaster.set(output.value);
    }

    public void setRightValueMotionProfileOutput(CANTalon.SetValueMotionProfile output) {
	m_rightMaster.set(output.value);
    }

    public boolean isMotionProfileFinished() {
	return isLeftMotionProfileFinished() && isRightMotionProfileFinished();
    }

    public boolean isLeftMotionProfileFinished() {
	CANTalon.MotionProfileStatus status = new CANTalon.MotionProfileStatus();
	m_leftMaster.getMotionProfileStatus(status);
	return status.activePoint.isLastPoint;
    }

    public boolean isRightMotionProfileFinished() {
	CANTalon.MotionProfileStatus status = new CANTalon.MotionProfileStatus();
	m_rightMaster.getMotionProfileStatus(status);
	return status.activePoint.isLastPoint;
    }

    public void stopDrive() {
	setPower(0.0, 0.0);
    }
    
    public void testDriveSubsystem() {
	
    }

    public void reportToSmartDashboard() {
	if (isHighGear()) {
	    SmartDashboard.putString("Gear Shift", "High");
	} else if (!isHighGear()) {
	    SmartDashboard.putString("Gear Shift", "Low");
	}

	SmartDashboard.putBoolean("Brake Mode On", m_brakeModeOn);

	SmartDashboard.putNumber("Left Master PercentVbus", m_leftMaster.getOutputVoltage() / 12);
	SmartDashboard.putNumber("Left Slave 1 PercentVbus", m_leftSlave1.getOutputVoltage() / 12);
	SmartDashboard.putNumber("Left Slave 2 PercentVbus", m_leftSlave2.getOutputVoltage() / 12);
	SmartDashboard.putNumber("Right Master PercentVbus", m_rightMaster.getOutputVoltage() / 12);
	SmartDashboard.putNumber("Right Slave 1 PercentVbus", m_rightSlave1.getOutputVoltage() / 12);
	SmartDashboard.putNumber("Right Slave 2 PercentVbus", m_rightSlave2.getOutputVoltage() / 12);

	SmartDashboard.putNumber("Left Master Current", m_leftMaster.getOutputCurrent());
	SmartDashboard.putNumber("Left Slave 1 Current", m_leftSlave1.getOutputCurrent());
	SmartDashboard.putNumber("Left Slave 2 Current", m_leftSlave2.getOutputCurrent());
	SmartDashboard.putNumber("Right Master Current", m_rightMaster.getOutputCurrent());
	SmartDashboard.putNumber("Right Slave 1 Current", m_rightSlave1.getOutputCurrent());
	SmartDashboard.putNumber("Right Slave 2 Current", m_rightSlave2.getOutputCurrent());
    }

}