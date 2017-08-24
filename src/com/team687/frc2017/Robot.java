package com.team687.frc2017;

import com.team687.frc2017.subsystems.Drive;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Cosmos 2017
 * 
 * @author tedlin
 * 
 */

public class Robot extends IterativeRobot {

    public static Drive drive;
    public static PowerDistributionPanel pdp;
    public static Compressor compressor;
    public static OI oi;
    public static VisionAdapter visionAdapter;

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

	// displays which command is running on Drive
	SmartDashboard.putData(drive);

	visionAdapter = VisionAdapter.getInstance();
    }

    @Override
    public void disabledInit() {
	Scheduler.getInstance().removeAll();
	drive.reportToSmartDashboard();
	visionAdapter.reportToSmartDashboard();
    }

    @Override
    public void disabledPeriodic() {
	Scheduler.getInstance().run();
	drive.reportToSmartDashboard();
	visionAdapter.reportToSmartDashboard();
    }

    @Override
    public void autonomousInit() {
	Scheduler.getInstance().removeAll();
	drive.reportToSmartDashboard();
	visionAdapter.reportToSmartDashboard();
    }

    @Override
    public void autonomousPeriodic() {
	Scheduler.getInstance().run();

	drive.reportToSmartDashboard();
	visionAdapter.reportToSmartDashboard();
	SmartDashboard.putData("PDP", pdp);
    }

    @Override
    public void teleopInit() {
	Scheduler.getInstance().removeAll();
	visionAdapter.reportToSmartDashboard();
	drive.reportToSmartDashboard();
    }

    @Override
    public void teleopPeriodic() {
	Scheduler.getInstance().run();
	drive.reportToSmartDashboard();
	visionAdapter.reportToSmartDashboard();
	SmartDashboard.putData("PDP", pdp);
    }

    @Override
    public void testPeriodic() {
	LiveWindow.run();
	drive.reportToSmartDashboard();
	visionAdapter.reportToSmartDashboard();
	SmartDashboard.putData("PDP", pdp);
    }
}
