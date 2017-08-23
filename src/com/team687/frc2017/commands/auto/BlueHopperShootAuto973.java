package com.team687.frc2017.commands.auto;

import com.team687.frc2017.Constants;
import com.team687.frc2017.commands.ArcTurn;
import com.team687.frc2017.commands.DriveStraightContinuous;
import com.team687.frc2017.commands.DriveUntilCollision;
import com.team687.frc2017.commands.LiveVisionTracking;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Blue hopper auto 973 style
 * 
 * @author tedlin
 *
 */

public class BlueHopperShootAuto973 extends CommandGroup {

    public BlueHopperShootAuto973() {
	// drive to hopper with Bezier curves
	// addSequential(new DriveBezierRio(Constants.BluePathWallToHopper, 0.687));

	// drive to hopper with continuous motion and arc turns
	addSequential(new DriveStraightContinuous(Constants.BluePathWallToHopperInitialDistance, 0.687, true));
	addSequential(new ArcTurn(Constants.BluePathWallToHopperArcTurnAngle, true, 0, true));
	addSequential(new DriveUntilCollision(0.971, true, 1.95));

	// back up in two motions
	// addSequential(new DriveDistancePID(Constants.BlueHopperBackUpDistance,
	// Constants.BlueHopperBackUpDistance));
	// addSequential(new TurnToAngle(Constants.BlueHopperAngleToShoot));

	// back up in one motion
	addSequential(new ArcTurn(Constants.BlueHopperAngleToShoot, false, 0, false));

	// aim and shoot
	addParallel(new LiveVisionTracking());
	// addParallel(new Shoot());
    }

}
