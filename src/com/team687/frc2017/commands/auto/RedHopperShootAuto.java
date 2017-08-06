package com.team687.frc2017.commands.auto;

import com.team687.frc2017.Constants;
import com.team687.frc2017.commands.DriveBezierRio;
import com.team687.frc2017.commands.DriveDistancePID;
import com.team687.frc2017.commands.LiveVisionTracking;
import com.team687.frc2017.commands.TurnToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RedHopperShootAuto extends CommandGroup {

    public RedHopperShootAuto() {
	addSequential(new DriveBezierRio(Constants.RedPathWallToHopper));
	addSequential(new DriveDistancePID(Constants.RedHopperBackUpDistance, Constants.RedHopperBackUpDistance));
	addSequential(new TurnToAngle(Constants.RedHopperAngleToShoot));
	addParallel(new LiveVisionTracking());
	// addParallel(new Shoot());
    }

}
