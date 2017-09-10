package com.team687.frc2017.commands.auto;

import com.team687.frc2017.Constants;
import com.team687.frc2017.commands.DriveDistancePID;
import com.team687.frc2017.commands.DriveTime;
import com.team687.frc2017.commands.SnapToTarget;
import com.team687.frc2017.commands.TurnToAngle;
import com.team687.frc2017.commands.WaitTime;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Two gear center peg auto
 * 
 * @author tedlin
 *
 */

public class BlueCenterGearTwoGearAuto extends CommandGroup {

    public BlueCenterGearTwoGearAuto() {
	addSequential(
		new DriveDistancePID(Constants.BlueWallToCenterPegDistance, Constants.BlueWallToCenterPegDistance));
	// addSequential(new DeployGear());
	addSequential(
		new DriveDistancePID(Constants.BlueCenterPegBackUpDistance, Constants.BlueCenterPegBackUpDistance));

	addSequential(new WaitTime(0.4));
	addSequential(new TurnToAngle(Constants.BlueWallToSecondGearAngle, 4, true));
	addSequential(new WaitTime(0.4));
	addSequential(
		new DriveDistancePID(Constants.BlueWallToSecondGearDistance, Constants.BlueWallToSecondGearDistance));
	// addParallel(new IntakeGear());

	addSequential(
		new DriveDistancePID(-Constants.BlueWallToSecondGearDistance, -Constants.BlueWallToSecondGearDistance));
	// addParallel(new GearManipUp());
	addSequential(new WaitTime(0.2));
	addSequential(new TurnToAngle(0, 4, true));
	addSequential(new WaitTime(0.1));
	addSequential(new SnapToTarget(true, 2));
	addSequential(new WaitTime(0.4));
	addSequential(
		new DriveDistancePID(-Constants.BlueCenterPegBackUpDistance, -Constants.BlueCenterPegBackUpDistance));
	// addSequential(new DeployGear());
	addSequential(new DriveTime(0.5, 3, false));
    }

}
