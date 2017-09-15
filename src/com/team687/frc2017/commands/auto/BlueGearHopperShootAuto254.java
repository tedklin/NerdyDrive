package com.team687.frc2017.commands.auto;

import com.team687.frc2017.Constants;
import com.team687.frc2017.commands.ArcTurn;
import com.team687.frc2017.commands.DriveBezierRio;
import com.team687.frc2017.commands.TurnToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 254 style blue gear+hopper auto
 * 
 * @author tedlin
 * 
 */

public class BlueGearHopperShootAuto254 extends CommandGroup {

    public BlueGearHopperShootAuto254() {
	// deploy gear
	addSequential(new DriveBezierRio(Constants.BluePathWallToClosePeg, -0.687, true, true));
	addSequential(new TurnToAngle(60.126, 2, true)); // This is the first heading in the next path
							 // segment. This solves a problem where robot starts spinning
							 // to find correct heading between two path segments.
	// addSequential(new DeployGear());

	// drive to hopper
	// addParallel(new SetGearManipulatorUp());
	addSequential(new DriveBezierRio(Constants.BluePathPegToHopper, 0.5, true, false));
	addSequential(new ArcTurn(Constants.BlueHopperToBoilerCorrectingAngle, false, 0.33));

	// shoot
	// addParallel(new LiveVisionTracking());
	// addParallel(new Shoot());
    }
}
