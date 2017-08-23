package com.team687.frc2017.commands.auto;

import com.team687.frc2017.Constants;
import com.team687.frc2017.commands.DriveBezierRio;
import com.team687.frc2017.commands.DriveDistancePID;
import com.team687.frc2017.commands.SnapToTarget;
import com.team687.frc2017.commands.TurnTime;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 2056 follow the wall red
 * 
 * @author tedlin
 *
 */

public class RedHopperShootAuto2056 extends CommandGroup {

    public RedHopperShootAuto2056() {
	// drive a little past hopper
	addSequential(new DriveBezierRio(Constants.RedPathWallToHopper2056, 0.687));

	// proc hopper by turning
	addSequential(new TurnTime(-0.687, 0.67, true));

	// drive align with goal
	addSequential(new SnapToTarget(true));

	// drive to perfectly align with hopper
	addSequential(new DriveDistancePID(Constants.RedPathDistanceAlignWithHopper,
		Constants.RedPathDistanceAlignWithHopper));

	// shoot
	// addParallel(new LiveVisionTracking());
	// addParallel(new Shoot());
    }

}
