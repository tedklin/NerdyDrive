package com.team687.frc2017.commands.auto;

import com.team687.frc2017.Constants;
import com.team687.frc2017.commands.DriveBezierRio;
import com.team687.frc2017.commands.DriveDistancePID;
import com.team687.frc2017.commands.DriveTime;
import com.team687.frc2017.commands.TurnToAngle;
import com.team687.frc2017.commands.WaitTime;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Blue far peg two gear auto
 * 
 * @author tedlin
 *
 */

public class BlueFarPegTwoGearAuto extends CommandGroup {

    public BlueFarPegTwoGearAuto() {
	addSequential(new DriveBezierRio(Constants.BluePathWallToFarPeg, -0.687, true, true));
	// addSequential(new DeployGear());
	addSequential(new DriveBezierRio(Constants.BluePathFarPegBackUp, 0.687, true, true));
	addSequential(new WaitTime(0.2));

	addSequential(new TurnToAngle(Constants.BlueWallFarToSecondGearAngle, 4, true));
	addSequential(new WaitTime(0.2));
	// addParallel(new IntakeGear());
	addSequential(new DriveDistancePID(Constants.BlueWallFarToSecondGearDistance,
		Constants.BlueWallFarToSecondGearDistance));
	addSequential(new WaitTime(0.2));
	addSequential(new DriveDistancePID(-Constants.BlueWallFarToSecondGearDistance,
		-Constants.BlueWallFarToSecondGearDistance));
	addSequential(new WaitTime(0.2));
	// addParallel(new GearManipUp());
	addSequential(new TurnToAngle(0, 4, true));
	addSequential(new WaitTime(0.2));

	addSequential(new DriveBezierRio(Constants.BluePathWallToFarPeg, -0.687, true, true));
	// addSequential(new DeployGear());
	addSequential(new DriveTime(0.5, 3, false));
    }

}
