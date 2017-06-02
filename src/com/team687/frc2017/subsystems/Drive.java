package com.team687.frc2017.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team687.frc2017.RobotMap;
import com.team687.frc2017.commands.*;
import com.team687.frc2017.utilities.NerdyMath;
import com.team687.lib.kauailabs.navx.frc.AHRS;
import com.team687.lib.kauailabs.sf2.frc.navXSensor;
import com.team687.lib.kauailabs.sf2.orientation.OrientationHistory;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive subsystem
 *
 * @author tedfoodlin
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
    
    public Drive() {
    	m_leftMaster = new CANTalon(RobotMap.kLeftMasterTalonID);
    	m_leftSlave1 = new CANTalon(RobotMap.kLeftSlaveTalon1ID);
    	m_leftSlave2 = new CANTalon(RobotMap.kLeftSlaveTalon2ID);
    	m_rightMaster = new CANTalon(RobotMap.kRightMasterTalonID);
    	m_rightSlave1 = new CANTalon(RobotMap.kRightSlaveTalon1ID);
    	m_rightSlave2 = new CANTalon(RobotMap.kRightSlaveTalon2ID);
    	
    	m_leftMaster.reverseSensor(false);
    	m_leftMaster.reverseOutput(false);
    	m_rightMaster.reverseSensor(true);
    	m_rightMaster.reverseOutput(true);
    	
    	m_shifter = new DoubleSolenoid(RobotMap.kShifterID1, RobotMap.kShifterID2);
        
    	m_nav = new AHRS(RobotMap.navID);
        m_navxsensor = new navXSensor(m_nav, "Drivetrain Orientation");
        m_orientationHistory = new OrientationHistory(m_navxsensor, m_nav.getRequestedUpdateRate() * 10);
    } 
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new TankDrive());
//		setDefaultCommand(new TestSensors());
	}
    
	public double squareInput(double input)	{
		return Math.pow(input, 2) * (input / Math.abs(input));
	}
    
    /**
     * Handles when the joystick moves slightly when you actually don't want it to move at all
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
    	return (m_shifter.get() == DoubleSolenoid.Value.kForward);
    }
    
    public double getCurrentYaw() {
		return m_nav.getYaw();
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
		m_nav.zeroYaw();
	}
	
	public double getInitTime() {
		return m_initTime;
	}
	
	public double getCurrentTime() {
		return m_currentTime;
	}

	/**
	 * Set drivetrain motor power to value between -1.0 and +1.0
	 * 
	 * @param lPow
	 * @param rPow
	 */
	public void setPower(double lPow, double rPow) {
		m_leftMaster.changeControlMode(TalonControlMode.PercentVbus);
		m_rightMaster.changeControlMode(TalonControlMode.PercentVbus);
	 	
	 	m_leftMaster.set(NerdyMath.limit(lPow, 1.0));
	 	m_leftSlave1.set(m_leftMaster.getDeviceID());
	 	m_leftSlave2.set(m_leftMaster.getDeviceID());
	 	
	 	m_rightMaster.set(NerdyMath.limit(rPow, 1.0));
	 	m_rightSlave1.set(m_rightMaster.getDeviceID());
	 	m_rightSlave2.set(m_rightMaster.getDeviceID());
	 }
	 
	public void processMotionProfileBuffer() {
	 	m_leftMaster.processMotionProfileBuffer();
	 	m_rightMaster.processMotionProfileBuffer();
	 }
	 
	public void changeMotionControlFramePeriod(int time) {
		m_leftMaster.changeMotionControlFramePeriod(time);
		m_rightMaster.changeMotionControlFramePeriod(time);
	}
	
	public void pushTrajectoryPoint(CANTalon.TrajectoryPoint point) {
		m_leftMaster.pushMotionProfileTrajectory(point);
		m_rightMaster.pushMotionProfileTrajectory(point);
	}
	 
	public void clearMotionProfileTrajectories() {
		m_leftMaster.clearMotionProfileTrajectories();
		m_rightMaster.clearMotionProfileTrajectories();
	}
	 
	public void setValueMotionProfileOutput(CANTalon.SetValueMotionProfile output) {
		m_leftMaster.set(output.value);
		m_rightMaster.set(output.value);
	 }

	public double getLeftPosition() {
		return m_leftMaster.getPosition();
	}
	
	public double getRightPosition() {
		return m_rightMaster.getPosition();
	}
	
	public int getLeftTicks() {
		return m_leftMaster.getEncPosition();
	}
	
	public int getRightTicks() {
		return m_rightMaster.getEncPosition();
	}
	
	public double getDrivetrainPosition() {
		return (getLeftPosition() + getRightPosition());
	}
	
	public int getDrivetrainTicks() {
		return (int)(getLeftTicks() + getRightTicks()/2);
	}
	
	public double getLeftSpeed() {
		return m_leftMaster.getSpeed();
	}
	
	public double getRightSpeed() {
		return m_rightMaster.getSpeed();
	}
	
	public double getLeftTicksSpeed() {
		return m_leftMaster.getEncVelocity();
	}
	
	public double getRightTicksSpeed() {
		return m_rightMaster.getEncVelocity();
	}
	
	public void resetEncoders() {
		m_leftMaster.reset();
		m_rightMaster.reset();
		
//		m_leftMaster.setPosition(0);
//		m_rightMaster.setPosition(0);
		
		m_leftMaster.setEncPosition(0);
		m_rightMaster.setEncPosition(0);
	}
	
    public void stopDrive() {
    	setPower(0, 0);
    }
	
	public void reportToSmartDashboard() {
		if (isHighGear()) {
			SmartDashboard.putString("Gear Shift", "High");
		} else if (!isHighGear()){
			SmartDashboard.putString("Gear Shift", "Low");
		}
		SmartDashboard.putNumber("Yaw", getCurrentYaw());
		
		SmartDashboard.putNumber("Left Position", getLeftPosition());
		SmartDashboard.putNumber("Right Position", getRightPosition());
		SmartDashboard.putNumber("Drivetrain Position", getDrivetrainPosition());
		SmartDashboard.putNumber("Left Speed", getLeftSpeed());
		SmartDashboard.putNumber("Right Speed", getRightSpeed());
		
		SmartDashboard.putNumber("Left Position Ticks", getLeftTicks());
		SmartDashboard.putNumber("Right Position Ticks", getRightTicks());
		SmartDashboard.putNumber("Drivetrain Position Ticks", getDrivetrainTicks());
		SmartDashboard.putNumber("Left Speed Ticks", getLeftTicksSpeed());
		SmartDashboard.putNumber("Right Speed Ticks", getRightTicksSpeed());
		
//		m_currentTime = Timer.getFPGATimestamp() - m_initTime;
//        m_table.putNumber("CURRENT_TIME", m_currentTime);
//        SmartDashboard.putNumber("Current Time", m_currentTime);
	}

}
