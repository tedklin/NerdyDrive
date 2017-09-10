package com.team687.frc2017.commands.auto;

import com.team687.frc2017.Constants;
import com.team687.frc2017.commands.ArcTurn;
import com.team687.frc2017.commands.DriveBezierRio;

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
	addSequential(new DriveBezierRio(Constants.BluePathWallToPeg, -0.687, true, true));
	// addSequential(new SetGearManipulatorDown());

	// drive to hopper
	// addParallel(new SetGearManipulatorUp());
	addSequential(new DriveBezierRio(Constants.BluePathPegToHopper, 0.687, true, false));
	addSequential(new ArcTurn(Constants.BlueHopperToBoilerCorrectingAngle, false, 0.33));

	// shoot
	// addParallel(new LiveVisionTracking());
	// addParallel(new Shoot());
    }
}
