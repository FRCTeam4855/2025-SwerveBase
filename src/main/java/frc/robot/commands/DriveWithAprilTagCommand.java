package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Limelight;

/**
 *
 */
public class DriveWithAprilTagCommand extends Command {

	private DriveSubsystem driveSubsystem;
	private Limelight limelight;
	private Joystick joystick;

	public static final double JOYSTICK_AXIS_THRESHOLD = 0.15;

	public DriveWithAprilTagCommand(DriveSubsystem driveSubsystem, Limelight limelight, Joystick joystick) {
		this.driveSubsystem = driveSubsystem;
		this.limelight = limelight;
		this.joystick = joystick;
		
		addRequirements(driveSubsystem);
		addRequirements(limelight);
	}

	// Called just before this Command runs the first time
	@Override
	public void initialize() {
		System.out.println("DriveWithAprilTagCommand Initialized");
	}


	// Called repeatedly when this Command is scheduled to run
	@Override
	public void execute() {

		//double angle = limelight.getAngleToTurnToTarget();

		driveSubsystem.drive(
			-MathUtil.applyDeadband(joystick.getY(), JOYSTICK_AXIS_THRESHOLD),
			-MathUtil.applyDeadband(joystick.getX(), JOYSTICK_AXIS_THRESHOLD),
			-limelight.xvalue/90.00,
			true, true);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	public boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	public void end(boolean interrupted) {
		System.out.println("driveSubsystemDriveUsingAprilTaglimelight: end");
		driveSubsystem.setStop();
	}
}