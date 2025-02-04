package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;


public class TimedRightStrafeCommand extends Command {
    DriveSubsystem drive;

    public TimedRightStrafeCommand(DriveSubsystem thisDrive) {
        drive = thisDrive;
    }
    @Override
    public void initialize() {
        new RunCommand(() -> drive.strafeRight())
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