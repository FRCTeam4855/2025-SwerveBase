 package frc.robot.commands;

 import edu.wpi.first.wpilibj2.command.Command;
 import frc.robot.subsystems.DriveSubsystem;
 import edu.wpi.first.wpilibj.Timer;

public class PushAgainstElement extends Command {
    DriveSubsystem drive;
    double speed;
    double time;
    private Timer timer;

    public PushAgainstElement(DriveSubsystem Drive, double speed, double time) {
        this.drive = Drive;
        this.speed = speed;
        this.time = time;
        timer = new Timer();
    }

    public void initialize() {
        timer.reset();
        timer.start();

        drive.forAft(speed);
    }

    public boolean isFinished() {
        if(timer.get() >= time){
            drive.setStop();
            timer.stop();
            return true;
        } else {
            return false;
        }
    }
}