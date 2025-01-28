package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Limelight;

public class LimelightStrafeCommand extends Command {
    DriveSubsystem drive;
    Limelight limelight;

    public LimelightStrafeCommand(DriveSubsystem thisDrive, Limelight thisLimelight) {
        drive = thisDrive;
        limelight = thisLimelight;
    }

    public void initialize() {
    }

    public void execute() {
        if (limelight.tagPose[0] >= 2) {
            drive.strafeLeft();
        } else if (limelight.tagPose[0] <= -2) {
            drive.strafeRight();
        }
    }

    public boolean isFinished() {
        if (limelight.tagPose[0] < 2 && limelight.tagPose[0] > -2) {
            drive.setStop();
            return true;
        } else {
            return false;
        }
    }
}