package org.usfirst.frc.team687.robot.subsystems;

import org.usfirst.frc.team687.robot.RobotMap;
import org.usfirst.frc.team687.robot.commands.*;
import org.usfirst.frc.team687.robot.utilities.NerdyMath;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive subsystem
 *
 * @author tedfoodlin
 *
 */

public class Drive extends Subsystem {
	
    private static Drive drive_instance = new Drive();
	
//	private final CANTalon m_leftMaster, m_leftSlave1, m_leftSlave2;
//	private final CANTalon m_rightMaster, m_rightSlave1, m_rightSlave2;
	
	private final VictorSP m_leftMaster, m_leftSlave1, m_leftSlave2;
	private final VictorSP m_rightMaster, m_rightSlave1, m_rightSlave2;
	
	private final DoubleSolenoid m_shifter;
	
	private final AHRS m_nav;
	
    public static Drive getInstance() {
    	if (drive_instance == null) {
    		drive_instance = new Drive();
    	}
        return drive_instance;
    }
    
    private Drive() {
    	super();
    	
//    	m_leftMaster = new CANTalon(RobotMap.kLeftMasterTalonID);
//    	m_leftSlave1 = new CANTalon(RobotMap.kLeftSlaveTalon1ID);
//    	m_leftSlave2 = new CANTalon(RobotMap.kLeftSlaveTalon2ID);
//    	m_rightMaster = new CANTalon(RobotMap.kRightMasterTalonID);
//    	m_rightSlave1 = new CANTalon(RobotMap.kRightSlaveTalon1ID);
//    	m_rightSlave2 = new CANTalon(RobotMap.kRightSlaveTalon2ID);
    	
    	m_leftMaster = new VictorSP(RobotMap.kLeftMasterTalonID);
    	m_leftSlave1 = new VictorSP(RobotMap.kLeftSlaveTalon1ID);
    	m_leftSlave2 = new VictorSP(RobotMap.kLeftSlaveTalon2ID);
    	m_rightMaster = new VictorSP(RobotMap.kRightMasterTalonID);
    	m_rightSlave1 = new VictorSP(RobotMap.kRightSlaveTalon1ID);
    	m_rightSlave2 = new VictorSP(RobotMap.kRightSlaveTalon2ID);
    	
    	m_shifter = new DoubleSolenoid(RobotMap.kShifterID1, RobotMap.kShifterID2);
    	
    	m_nav = new AHRS(RobotMap.navID);
    }
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new TestMinRotPower());
	}
	
	public void setPower(double lPow, double rPow) {
		m_leftMaster.set(NerdyMath.limit(lPow, 1.0));
		m_leftSlave1.set(NerdyMath.limit(lPow, 1.0));
		m_leftSlave2.set(NerdyMath.limit(lPow, 1.0));
		
		m_rightMaster.set(NerdyMath.limit(rPow, 1.0));
		m_rightSlave1.set(NerdyMath.limit(rPow, 1.0));
		m_rightSlave2.set(NerdyMath.limit(rPow, 1.0));
	}
	
//    /**
//     * Set drivetrain motor power to value between -1.0 and +1.0
//     * 
//     * @param left power
//     * @param right power
//     */
//    public void setPower(double lPow, double rPow) {
//    	m_leftMaster.changeControlMode(TalonControlMode.PercentVbus);
//    	m_rightMaster.changeControlMode(TalonControlMode.PercentVbus);
//    	
//    	m_leftMaster.set(NerdyMath.limit(lPow, 1.0));
//    	m_leftSlave1.set(m_leftMaster.getDeviceID());
//    	m_leftSlave2.set(m_leftMaster.getDeviceID());
//    	
//    	m_rightMaster.set(NerdyMath.limit(rPow, 1.0));
//    	m_rightSlave1.set(m_rightMaster.getDeviceID());
//    	m_rightSlave2.set(m_rightMaster.getDeviceID());
//    }
//    
//    public void processMotionProfileBuffer() {
//    	m_leftMaster.processMotionProfileBuffer();
//    	m_rightMaster.processMotionProfileBuffer();
//    }
//    
//    public void changeMotionControlFramePeriod(int time) {
//		m_leftMaster.changeMotionControlFramePeriod(time);
//		m_rightMaster.changeMotionControlFramePeriod(time);
//    }
//    
//    public void pushTrajectoryPoint(CANTalon.TrajectoryPoint point) {
//		m_leftMaster.pushMotionProfileTrajectory(point);
//		m_rightMaster.pushMotionProfileTrajectory(point);
//    }
//    
//    public void clearMotionProfileTrajectories() {
//    	m_leftMaster.clearMotionProfileTrajectories();
//    	m_rightMaster.clearMotionProfileTrajectories();
//    }
//    
//    public void setValueMotionProfileOutput(CANTalon.SetValueMotionProfile output) {
//    	m_leftMaster.set(output.value);
//    	m_rightMaster.set(output.value);
//    }
    
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
	
	public double getYaw() {
		return m_nav.getYaw();
	}
	
	public void resetGyro() {
		m_nav.zeroYaw();
	}
	
//	public double getLeftPosition() {
//		return m_leftMaster.getPosition();
//	}
//	
//	public double getRightPosition() {
//		return m_rightMaster.getPosition();
//	}
//	
//	public int getLeftTicks() {
//		return m_leftMaster.getEncPosition();
//	}
//	
//	public int getRightTicks() {
//		return m_rightMaster.getEncPosition();
//	}
//	
//	public double getDrivetrainPosition() {
//		return (getLeftPosition() + getRightPosition());
//	}
//	
//	public int getDrivetrainTicks() {
//		return (int)(getLeftTicks() + getRightTicks()/2);
//	}
//	
//	public double getLeftSpeed() {
//		return m_leftMaster.getSpeed();
//	}
//	
//	public double getRightSpeed() {
//		return m_rightMaster.getSpeed();
//	}
//	
//	public double getLeftTicksSpeed() {
//		return m_leftMaster.getEncVelocity();
//	}
//	
//	public double getRightTicksSpeed() {
//		return m_rightMaster.getEncVelocity();
//	}
//	
//	public void resetEncoders() {
//		m_leftMaster.reset();
//		m_rightMaster.reset();
//		
//		m_leftMaster.setPosition(0);
//		m_rightMaster.setPosition(0);
//		
//		m_leftMaster.setEncPosition(0);
//		m_rightMaster.setEncPosition(0);
//	}
	
	public void resetSensors() {
//		resetEncoders();
		resetGyro();
	}
	
    public void stopDrive() {
    	setPower(0, 0);
    	resetSensors();
    }
	
	public void reportToSmartDashboard() {
		SmartDashboard.putBoolean("High Gear", isHighGear());
		SmartDashboard.putNumber("Yaw", getYaw());
		
//		SmartDashboard.putNumber("Left Position", getLeftPosition());
//		SmartDashboard.putNumber("Right Position", getRightPosition());
//		SmartDashboard.putNumber("Drivetrain Position", getDrivetrainPosition());
//		SmartDashboard.putNumber("Left Speed", getLeftSpeed());
//		SmartDashboard.putNumber("Right Speed", getRightSpeed());
//		
//		SmartDashboard.putNumber("Left Position Ticks", getLeftTicks());
//		SmartDashboard.putNumber("Right Position Ticks", getRightTicks());
//		SmartDashboard.putNumber("Drivetrain Position Ticks", getDrivetrainTicks());
//		SmartDashboard.putNumber("Left Speed Ticks", getLeftTicksSpeed());
//		SmartDashboard.putNumber("Right Speed Ticks", getRightTicksSpeed());
	}

}
