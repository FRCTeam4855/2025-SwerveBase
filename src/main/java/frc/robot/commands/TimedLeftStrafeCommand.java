package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;


public class TimedLeftStrafeCommand extends Command {
    DriveSubsystem drive;

    public TimedLeftStrafeCommand(DriveSubsystem thisDrive) {
        drive = thisDrive;
    }
    @Override
    public void initialize() {
        new RunCommand(() -> drive.strafeLeft())
        .raceWith(new WaitCommand(.4))
        .schedule();
    }
    @Override
    public void execute() {  
    }
    
    @Override
    public boolean isFinished() {
            return true;
    }
}