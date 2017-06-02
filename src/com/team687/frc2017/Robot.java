package com.team687.frc2017;

import com.team687.frc2017.subsystems.Drive;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *                        
 * _._ _..._ .-',     _.._(`)
 *'-. `     '  /-._.-'    ',/
 *   )         \            '.
 *  / _    _    |             \
 * |  a    a    /              |
 * \   .-.                     ;  
 *  '-('' ).-'       ,'       ;
 *     '-;           |      .'
 *        \           \    /
 *        | 7  .__  _.-\   \
 *        | |  |  ``/  /`  /
 *       /,_|  |   /,_/   /
 *          /,_/      '`-'
 *          
 * @author tedfoodlin
 * 
 */

public class Robot extends IterativeRobot {

	public static Drive drive;
	public static PowerDistributionPanel pdp;
	public static Compressor compressor;
	public static OI oi;

	@Override
	public void robotInit() {
		pdp = new PowerDistributionPanel();
		compressor = new Compressor();
		compressor.start();
		
		drive = new Drive();
		drive.stopDrive();
		drive.shiftDown();
		drive.resetEncoders();
		drive.resetGyro();
		
		oi = new OI();

        SmartDashboard.putData(drive);
	}

	@Override
	public void disabledInit() {
		Scheduler.getInstance().removeAll();
        drive.reportToSmartDashboard();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
        drive.reportToSmartDashboard();
	}

	@Override
	public void autonomousInit() {
		Scheduler.getInstance().removeAll();
        drive.reportToSmartDashboard();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
        drive.reportToSmartDashboard();
        SmartDashboard.putData("PDP", pdp);
	}

	@Override
	public void teleopInit() {
		Scheduler.getInstance().removeAll();
        drive.reportToSmartDashboard();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
        drive.reportToSmartDashboard();
        SmartDashboard.putData("PDP", pdp);
	}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
        drive.reportToSmartDashboard();
        SmartDashboard.putData("PDP", pdp);
	}
}
