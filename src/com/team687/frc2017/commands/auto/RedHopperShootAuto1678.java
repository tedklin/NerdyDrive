package com.team687.frc2017.commands.auto;

import com.team687.frc2017.Constants;
import com.team687.frc2017.commands.DriveBezierRio;
import com.team687.frc2017.commands.LiveVisionTracking;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 1678 follow the wall red
 * 
 * @author tedlin
 *
 */

public class RedHopperShootAuto1678 extends CommandGroup {

    public RedHopperShootAuto1678() {
	// drive up next to hopper
	addSequential(new DriveBezierRio(Constants.RedPathWallToHopper1678, 0.4, false, false));

	// proc hopper
	// addSequential(new ExpandHopper());

	// aim and shoot
	addParallel(new LiveVisionTracking());
	// addParallel(new Shoot());
    }

}
