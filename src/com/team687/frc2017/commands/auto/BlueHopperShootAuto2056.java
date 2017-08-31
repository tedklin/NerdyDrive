package com.team687.frc2017.commands.auto;

import com.team687.frc2017.Constants;
import com.team687.frc2017.commands.DriveBezierRio;
import com.team687.frc2017.commands.DriveDistancePID;
import com.team687.frc2017.commands.SnapToTarget;
import com.team687.frc2017.commands.TurnTime;
import com.team687.frc2017.commands.WaitTime;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Blue hopper auto 2056 (similar to 1678). Starting configuration is aligned
 * with and touching the line that defines the key in front of the boiler.
 * 
 * @author tedlin
 *
 */

public class BlueHopperShootAuto2056 extends CommandGroup {

    public BlueHopperShootAuto2056() {
	// drive a little past hopper
	addSequential(new DriveBezierRio(Constants.BluePathWallToHopper2056, 0.4, false, false));
	addSequential(new WaitTime(0.3));

	// proc hopper by turning
	addSequential(new TurnTime(0.687, 0.35, true));
	addSequential(new WaitTime(0.1));

	// drive align with goal
	addSequential(new SnapToTarget(true));

	// drive to perfectly align with hopper
	addSequential(new DriveDistancePID(Constants.BluePathDistanceAlignWithHopper,
		Constants.BluePathDistanceAlignWithHopper));

	// shoot
	// addParallel(new LiveVisionTracking());
	// addParallel(new Shoot());
    }

}
