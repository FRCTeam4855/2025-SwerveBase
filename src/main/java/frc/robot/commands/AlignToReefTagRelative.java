// Copyright (c) FIRST and other WPILib contributors. 
// Open Source Software; you can modify and/or share it under the terms of 
// the WPILib BSD license file in the root directory of this project.
 
package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.DriveSubsystem;

public class AlignToReefTagRelative extends Command {
    private PIDController xController, yController, rotController;
    private boolean isRightScore;
    private Timer dontSeeTagTimer, stopTimer;
    private DriveSubsystem drivebase;

    public AlignToReefTagRelative(boolean isRightScore, DriveSubsystem drivebase) {
        xController = new PIDController(Constants.ReefAlignConstants.X_REEF_ALIGNMENT_P, 0, 0);  // Vertical movement
        yController = new PIDController(Constants.ReefAlignConstants.Y_REEF_ALIGNMENT_P, 0, 0);  // Horitontal movement
        rotController = new PIDController(Constants.ReefAlignConstants.ROT_REEF_ALIGNMENT_P, 0, 0);  // Rotation 
        this.isRightScore = isRightScore; 
        this.drivebase = drivebase; 
        addRequirements(drivebase); 
    }

    @Override 
    public void initialize() { 
        this.stopTimer = new Timer();   
        this.stopTimer.start();
        this.dontSeeTagTimer = new Timer();
        this.dontSeeTagTimer.start();

        rotController.setSetpoint(Constants.ReefAlignConstants.ROT_SETPOINT_REEF_ALIGNMENT);
        rotController.setTolerance(Constants.ReefAlignConstants.ROT_TOLERANCE_REEF_ALIGNMENT);

        xController.setSetpoint(Constants.ReefAlignConstants.X_SETPOINT_REEF_ALIGNMENT);
        xController.setTolerance(Constants.ReefAlignConstants.X_TOLERANCE_REEF_ALIGNMENT);

        yController.setSetpoint(isRightScore ? Constants.ReefAlignConstants.Y_SETPOINT_REEF_ALIGNMENT : -Constants.ReefAlignConstants.Y_SETPOINT_REEF_ALIGNMENT);
        yController.setTolerance(Constants.ReefAlignConstants.Y_TOLERANCE_REEF_ALIGNMENT);
    }

    @Override 
    public void execute() {
        

        double[] postions = LimelightHelpers.getBotPose_TargetSpace("limelight");
        SmartDashboard.putBoolean("Tv", LimelightHelpers.getTV("limelight"));
        SmartDashboard.putNumber("x", postions[2]);
        double xSpeed = xController.calculate(postions[2]);
        SmartDashboard.putNumber("xspeed", xSpeed);
        SmartDashboard.putNumber("y", postions[0]);
        double ySpeed = -yController.calculate(postions[0]);
        SmartDashboard.putNumber("yspeed", ySpeed);
        double rotValue = -rotController.calculate(postions[4]);

        if(LimelightHelpers.getTV("limelight")) {
            this.dontSeeTagTimer.reset();
        } else {
            drivebase.drive(new Translation2d(), 0, false);
        }
            
        drivebase.drive(new Translation2d(yController.getError() < Constants.ReefAlignConstants.Y_TOLERANCE_REEF_ALIGNMENT ? xSpeed : 0, ySpeed), rotValue, false);
        if (!rotController.atSetpoint())
            DataLogManager.log("not rotSetpoint");
        if (!yController.atSetpoint())
            DataLogManager.log("not ySetpoint");
        if ( !xController.atSetpoint())
            DataLogManager.log("not xSetpoint");

        if (!rotController.atSetpoint() ||
            !yController.atSetpoint() ||
            !xController.atSetpoint()) {
                stopTimer.reset();
        } else {
            drivebase.drive(new Translation2d(), 0, false);
        }
    }

    @Override
    public void end(boolean interrupted) {
        drivebase.drive(new Translation2d(), 0, false); 
    }
 
    @Override 
        public boolean isFinished() {
            // Requires the robot to stay in the correct position for 0.3 seconds, as long as it gets a tag in the camera
            return this.dontSeeTagTimer.hasElapsed(Constants.ReefAlignConstants.DONT_SEE_TAG_WAIT_TIME) ||
            stopTimer.hasElapsed(Constants.ReefAlignConstants.POSE_VALIDATION_TIME);
    }
}