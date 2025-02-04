package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Limelight;

/**
 *
 */
public class DriveWithAprilTagCommand extends Command {

	private DriveSubsystem driveSubsystem;
	private Limelight limelight;
	private Joystick joystickLeft, joystickRight;

	public static final double JOYSTICK_AXIS_THRESHOLD = 0.15;

	public DriveWithAprilTagCommand(DriveSubsystem driveSubsystem, Limelight limelight, Joystick joystickLeft, Joystick joystickRight) {
		this.driveSubsystem = driveSubsystem;
		this.limelight = limelight;
		this.joystickLeft = joystickLeft;
		this.joystickRight = joystickRight;
		
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
		// SmartDashboard.putNumber("Limelight Cmd Yaw", limelight.tagPose[4]/90);

		// driveSubsystem.drive(
		// 	-MathUtil.applyDeadband(joystick.getY() * OIConstants.kSpeedMultiplierPrecise, JOYSTICK_AXIS_THRESHOLD),
		// 	-MathUtil.applyDeadband(joystick.getX() * OIConstants.kSpeedMultiplierPrecise, JOYSTICK_AXIS_THRESHOLD),
		// 	-limelight.tagPose[4]/90.00,
		// 	true, true);
		driveSubsystem.drive(
			-MathUtil.applyDeadband(joystickLeft.getY() * OIConstants.kSpeedMultiplierPrecise, JOYSTICK_AXIS_THRESHOLD),
			-MathUtil.applyDeadband(limelight.tagPose[0] * .5 + joystickLeft.getX() * .5, .02),
			-MathUtil.applyDeadband(limelight.tagPose[4]/90.00 * 0.5 + joystickRight.getX() * 0.25, .05),
			false, true);
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