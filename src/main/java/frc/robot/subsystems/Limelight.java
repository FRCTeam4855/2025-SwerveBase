package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.OIConstants;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;


public class Limelight extends SubsystemBase {
  //
  RobotContainer m_robotContainer; 
  //private final RobotContainer m_robotContainer;
  public double limelightTarget;
  public double[] tagPose;
  public boolean isLimelightOnTarget = false;
  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  NetworkTable table = inst.getTable("limelight");
  NetworkTableEntry ledMode = table.getEntry("ledMode");
  NetworkTableEntry pipeline = table.getEntry("pipeline");
  NetworkTableEntry vEntry = table.getEntry("tv"); //Whether the limelight has any valid targets (0 or 1)
  NetworkTableEntry aEntry = table.getEntry("ta"); //Target Area (0% of image to 100% of image)
  NetworkTableEntry tEntry = table.getEntry("tid");
  public NetworkTableEntry poseEntry = table.getEntry("targetpose_robotspace");

  public ChassisSpeeds limelightGetOffsetSpeeds(){
    if(doesLimelightHaveTarget()) {
       /*double[] pose = poseEntry.getDoubleArray(new double[6]);
        return(new ChassisSpeeds(0, -MathUtil.applyDeadband(pose[0] * .3, .1), -MathUtil.applyDeadband(pose[4] / 90.00, .1)));
        //return(new ChassisSpeeds(0, -MathUtil.applyDeadband(pose[0] * m_robotContainer.tuningValue, .1), -MathUtil.applyDeadband(pose[4] / 90.00, .1)));*/
        return(new ChassisSpeeds(0, 0, 0));  
    } else {
        return(new ChassisSpeeds(0, 0, 0));
    }
  }

  public boolean isLimelightLampOn(){
    if (inst.getTable("limelight").getEntry("ledMode").getDouble(0) == 1){
      return true;
    } else { 
      return false;
    }
  }

  public void setLimelightLampOn() {
    ledMode.setNumber(3);
  }

  public void setLimelightLampOff() {
    ledMode.setNumber(1);
  }

  public boolean isLimelightOnAprilTagMode(){
    if (table.getEntry("pipeline").getDouble(0) == 0){
      return true;
    } else {
      return false;
    }
  }
  public boolean doesLimelightHaveTarget(){
    if (vEntry.getDouble(0) == 1){
      return true;
    } else {
      return false;
    }
}

  public void setLimelightPipeToAprilTag() {
    pipeline.setNumber(0);
  }

  public void setLimelightPipeToRetroTape() {
    pipeline.setNumber(1);
  }

  public void limelightOnTarget() {
    isLimelightOnTarget = true;
  }

  public void limelightOffTarget() {
    isLimelightOnTarget = false;
  }

@Override
  public void periodic() {
    double tv = vEntry.getDouble(0); // Whether the limelight has any valid targets (0 or 1)
    double ta = aEntry.getDouble(0); // Target Area (0% of image to 100% of image)
    tagPose = poseEntry.getDoubleArray(new double[0]); //tx = [0] ty = [1] tz = [2] pitch = [3] yaw = [4] roll = [5]
    limelightTarget = tEntry.getDouble(-1);

    //SmartDashboard.putNumber("Limelight Tuning Value", m_robotContainer.tuningValue);

    SmartDashboard.putNumber("Limelight X", tagPose[0]);
    SmartDashboard.putNumber("Limelight Y", tagPose[1]);
    SmartDashboard.putNumber("Limelight Z", tagPose[2]);
    SmartDashboard.putNumber("Limelight Pitch", tagPose[3]);
    SmartDashboard.putNumber("Limelight Yaw", tagPose[4]);
    SmartDashboard.putNumber("Limelight Roll", tagPose[5]);
    SmartDashboard.putNumber("Limelight Area", ta);
    SmartDashboard.putNumber("Limelight Valid Target", tv);
    SmartDashboard.putBoolean("Limelight Has Target", doesLimelightHaveTarget());
    SmartDashboard.putBoolean("Limelight in AprilTag Mode", isLimelightOnAprilTagMode());
  }
  
}