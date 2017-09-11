package com.team687.frc2017.commands.auto;

import com.team687.frc2017.Constants;
import com.team687.frc2017.commands.DriveDistancePID;
import com.team687.frc2017.commands.DriveTime;
import com.team687.frc2017.commands.SnapToTarget;
import com.team687.frc2017.commands.TurnToAngle;
import com.team687.frc2017.commands.WaitTime;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Red center peg two gear auto
 * 
 * @author tedlin
 *
 */

public class RedCenterPegTwoGearAuto extends CommandGroup {

    public RedCenterPegTwoGearAuto() {
	addSequential(new DriveDistancePID(Constants.RedWallToCenterPegDistance, Constants.RedWallToCenterPegDistance));
	// addSequential(new DeployGear());
	addSequential(new DriveDistancePID(Constants.RedCenterPegBackUpDistance, Constants.RedCenterPegBackUpDistance));

	addSequential(new WaitTime(0.3));
	addSequential(new TurnToAngle(Constants.RedWallToSecondGearAngle, 4, true));
	addSequential(new WaitTime(0.3));
	addSequential(
		new DriveDistancePID(Constants.RedWallToSecondGearDistance, Constants.RedWallToSecondGearDistance));
	// addParallel(new IntakeGear());

	addSequential(
		new DriveDistancePID(-Constants.RedWallToSecondGearDistance, -Constants.RedWallToSecondGearDistance));
	// addParallel(new GearManipUp());
	addSequential(new WaitTime(0.2));
	addSequential(new TurnToAngle(0, 4, true));
	addSequential(new WaitTime(0.1));
	addSequential(new SnapToTarget(true, 2));
	addSequential(new WaitTime(0.4));
	addSequential(
		new DriveDistancePID(-Constants.RedCenterPegBackUpDistance, -Constants.RedCenterPegBackUpDistance));
	// addSequential(new DeployGear());
	addSequential(new DriveTime(0.5, 3, false));
    }

}
