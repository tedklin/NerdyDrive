package com.team687.frc2017.commands.auto;

import com.team687.frc2017.Constants;
import com.team687.frc2017.commands.DriveBezierRio;
import com.team687.frc2017.commands.LiveVisionTracking;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Blue hopper auto 1678 (follow the wall)
 * 
 * @author tedlin
 *
 */

public class BlueHopperShootAuto1678 extends CommandGroup {

    public BlueHopperShootAuto1678() {
	// drive up next to hopper
	addSequential(new DriveBezierRio(Constants.BluePathWallToHopper1678, 0.4, false, false));

	// proc hopper
	// addSequential(new ExpandHopper());

	// aim and shoot
	addParallel(new LiveVisionTracking());
	// addParallel(new Shoot());
    }

}
