package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Limelight;

public class DriveWithAprilTagCommandOffset extends Command {

	private DriveSubsystem driveSubsystem;
	private Limelight limelight;
	private Joystick joystickLeft, joystickRight;
	private boolean leftBranch;
	public static final double JOYSTICK_AXIS_THRESHOLD = 0.15;

	public DriveWithAprilTagCommandOffset(DriveSubsystem driveSubsystem, Limelight limelight, Joystick joystickLeft, Joystick joystickRight, boolean leftBranch) {
		this.driveSubsystem = driveSubsystem;
		this.limelight = limelight;
		this.joystickLeft = joystickLeft;
		this.joystickRight = joystickRight;
		this.leftBranch = leftBranch;
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
		if (limelight.limelightHasTarget) {
			if (leftBranch) {
			driveSubsystem.drive(
				-MathUtil.applyDeadband(joystickLeft.getY() * OIConstants.kSpeedMultiplierPrecise, JOYSTICK_AXIS_THRESHOLD),
				-MathUtil.applyDeadband(limelight.tagPose[0] - .15, .01),
				-MathUtil.applyDeadband(limelight.tagPose[4]/90 + joystickRight.getX() * 0.25, .02),
				false, true);
			} else {
			driveSubsystem.drive(
				-MathUtil.applyDeadband(joystickLeft.getY() * OIConstants.kSpeedMultiplierPrecise, JOYSTICK_AXIS_THRESHOLD),
				-MathUtil.applyDeadband(limelight.tagPose[0] + .15, .01),
				-MathUtil.applyDeadband(limelight.tagPose[4]/90 + joystickRight.getX() * 0.25, .02),
				false, true);
			}
		} else {
			System.out.println("!! No Valid Limelight Target !!");
		}
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