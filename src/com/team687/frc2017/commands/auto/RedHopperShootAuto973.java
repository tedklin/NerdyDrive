package com.team687.frc2017.commands.auto;

import com.team687.frc2017.Constants;
import com.team687.frc2017.commands.ArcTurn;
import com.team687.frc2017.commands.DriveStraightContinuous;
import com.team687.frc2017.commands.DriveUntilCollision;
import com.team687.frc2017.commands.LiveVisionTracking;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 973 style red hopper auto
 * 
 * @author tedlin
 *
 */

public class RedHopperShootAuto973 extends CommandGroup {

    public RedHopperShootAuto973() {
	// drive to hopper with Bezier curves
	// addSequential(new DriveBezierRio(Constants.RedPathWallToHopper, 1));

	// drive to hopper with continuous motion and arc turns
	addSequential(new DriveStraightContinuous(Constants.RedPathWallToHopperInitialDistance, 0.687, true));
	addSequential(new ArcTurn(Constants.RedPathWallToHopperArcTurnAngle, true, 0, true));
	addSequential(new DriveUntilCollision(0.971, true, 1.95));

	// back up in two motions
	// addSequential(new DriveDistancePID(Constants.BlueHopperBackUpDistance,
	// Constants.BlueHopperBackUpDistance));
	// addSequential(new TurnToAngle(Constants.BlueHopperAngleToShoot));

	// back up in one motion
	addSequential(new ArcTurn(Constants.RedHopperAngleToShoot, true, 0, false));

	// aim and shoot
	addParallel(new LiveVisionTracking());
	// addParallel(new Shoot());
    }

}
