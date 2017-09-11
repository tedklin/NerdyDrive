package com.team687.frc2017.commands.auto;

import com.team687.frc2017.Constants;
import com.team687.frc2017.commands.DriveBezierRio;
import com.team687.frc2017.commands.DriveDistancePID;
import com.team687.frc2017.commands.DriveTime;
import com.team687.frc2017.commands.TurnToAngle;
import com.team687.frc2017.commands.WaitTime;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Blue peg close to hopper two gear auto (similar to 1011 FOC auto)
 * 
 * @author tedlin
 * 
 */

public class BlueClosePegTwoGearAuto extends CommandGroup {

    public BlueClosePegTwoGearAuto() {
	addSequential(new DriveBezierRio(Constants.BluePathWallToClosePeg, -0.687, true, true));
	// addSequential(new DeployGear());
	addSequential(new DriveBezierRio(Constants.BluePathClosePegBackUp, 0.687, true, true));
	addSequential(new WaitTime(0.2));

	addSequential(new TurnToAngle(Constants.BlueWallCloseToSecondGearAngle, 4, true));
	addSequential(new WaitTime(0.2));
	// addParallel(new IntakeGear());
	addSequential(new DriveDistancePID(Constants.BlueWallCloseToSecondGearDistance,
		Constants.BlueWallCloseToSecondGearDistance));
	addSequential(new WaitTime(0.2));
	addSequential(new DriveDistancePID(-Constants.BlueWallCloseToSecondGearDistance,
		-Constants.BlueWallCloseToSecondGearDistance));
	addSequential(new WaitTime(0.2));
	// addParallel(new GearManipUp());
	addSequential(new TurnToAngle(0, 4, true));
	addSequential(new WaitTime(0.2));

	addSequential(new DriveBezierRio(Constants.BluePathWallToClosePeg, -0.687, true, true));
	// addSequential(new DeployGear());
	addSequential(new DriveTime(0.5, 3, false));
    }

}
