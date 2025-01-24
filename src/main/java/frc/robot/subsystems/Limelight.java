package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {

  public double limelightTarget;

  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  NetworkTable table = inst.getTable("limelight");
  NetworkTableEntry ledMode = table.getEntry("ledMode");
  NetworkTableEntry pipeline = table.getEntry("pipeline");
  NetworkTableEntry vEntry = table.getEntry("tv"); //Whether the limelight has any valid targets (0 or 1)
  NetworkTableEntry poseEntry = table.getEntry("botpose_targetspace");
  NetworkTableEntry aEntry = table.getEntry("ta"); //Target Area (0% of image to 100% of image)
  NetworkTableEntry tEntry = table.getEntry("tid");

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
 
@Override
  public void periodic() {
    double tv = vEntry.getDouble(0); // Whether the limelight has any valid targets (0 or 1)
    double ta = aEntry.getDouble(0); // Target Area (0% of image to 100% of image)
    double[] tx = poseEntry.getDoubleArray(new double[0]); 
    double[] ty = poseEntry.getDoubleArray(new double[1]); 
    double[] tz = poseEntry.getDoubleArray(new double[2]);
    double[] pitch = poseEntry.getDoubleArray(new double[3]);
    double[] yaw = poseEntry.getDoubleArray(new double[4]);
    double[] roll = poseEntry.getDoubleArray(new double[5]);
    limelightTarget = tEntry.getDouble(-1);

    SmartDashboard.putNumberArray("Limelight X", tx);
    SmartDashboard.putNumberArray("Limelight Y", ty);
    SmartDashboard.putNumberArray("Limelight Z", tz);
    SmartDashboard.putNumberArray("Limelight Pitch", pitch);
    SmartDashboard.putNumberArray("Limelight Yaw", yaw);
    SmartDashboard.putNumberArray("Limelight Roll", roll);
    SmartDashboard.putNumber("Limelight Area", ta);
    SmartDashboard.putNumber("Limelight Valid Target", tv);
    SmartDashboard.putBoolean("Limelight Has Target", doesLimelightHaveTarget());
    SmartDashboard.putBoolean("Limelight in AprilTag Mode", isLimelightOnAprilTagMode());
  }
  
}
