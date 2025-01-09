package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {


  ///********************************************************
  ///PLEASE KEEP ALL COMMENTED LINES FOR POSSIBLE FUTURE USE
  ///********************************************************
  public double xvalue;
  public double limelightTarget;
  public boolean onTarget;
  public boolean isLimelightLampOn(){
    if (NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").getDouble(0) == 1){ //inline way to pull a networktable entry as a double
      return true;
    } else { 
      return false;
    }
  }

  public void setLimelightLampOn() {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("limelight");
    NetworkTableEntry ledMode = table.getEntry("ledMode");
    ledMode.setNumber(3);
  }

  public void setLimelightLampOff() {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("limelight");
    NetworkTableEntry ledMode = table.getEntry("ledMode");
    ledMode.setNumber(1);
  }

  public boolean isLimelightOnAprilTagMode(){
    if (NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").getDouble(0) == 0){
      return true;
    } else {
      return false;
    }
  }
  public boolean doesLimelightHaveTarget(){
    if (NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1){
      return true;
    } else {
      return false;
    }
}

  public void setLimelightPipeToAprilTag() {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("limelight");
    NetworkTableEntry pipeline = table.getEntry("pipeline");
    pipeline.setNumber(0);
  }

  public void setLimelightPipeToRetroTape() {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("limelight");
    NetworkTableEntry pipeline = table.getEntry("pipeline");
    pipeline.setNumber(1);
  }
 
@Override
  public void periodic() {
    // ^^add this to robotPeriodic to run always
    // get the default instance of NetworkTables
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    // get a reference to the subtable called "datatable"
    NetworkTable table = inst.getTable("limelight");
    // NetworkTableEntry TeamEntry = table.getEntry("tx");
    
    NetworkTableEntry xEntry = table.getEntry("tx"); //Horizontal Offset From Crosshair To Target (LL1: -27 degrees to 27 degrees | LL2: -29.8 to 29.8 degrees)
    NetworkTableEntry yEntry = table.getEntry("ty"); //Vertical Offset From Crosshair To Target (LL1: -20.5 degrees to 20.5 degrees | LL2: -24.85 to 24.85 degrees)
    NetworkTableEntry aEntry = table.getEntry("ta"); //Target Area (0% of image to 100% of image)
    NetworkTableEntry tEntry = table.getEntry("tid");
    /*
    NetworkTableEntry lEntry = table.getEntry("tl"); //The pipeline’s latency contribution (ms). Add to “cl” to get total latency.
    */
    NetworkTableEntry vEntry = table.getEntry("tv"); //Whether the limelight has any valid targets (0 or 1)
    
   
    double tx = xEntry.getDouble(0.0); // Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
    double ty = yEntry.getDouble(0.0); // Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    double ta = aEntry.getDouble(0.0); // Target Area (0% of image to 100% of image)
    // double tl = lEntry.getDouble(0.0); // The pipeline’s latency contribution (ms) Add at least 11ms for image capture latency.
    double tv = vEntry.getDouble(0.0); // Whether the limelight has any valid targets (0 or 1)
    limelightTarget = tEntry.getDouble(-1);
    xvalue = xEntry.getDouble(0);

    if (xvalue <= 2 && xvalue >= -2 && doesLimelightHaveTarget()) {
      onTarget = true;
    } else {
      onTarget = false;
    }
    // ts Skew or rotation (-90 degrees to 0 degrees) CAN BE MULTIPLE TS (0-**)
    // cl	Capture pipeline latency (ms). Time between the end of the exposure of the middle row of the sensor to the beginning of the tracking pipeline.
    // tshort	Sidelength of shortest side of the fitted bounding box (pixels)
    // tlong	Sidelength of longest side of the fitted bounding box (pixels)
    // thor	Horizontal sidelength of the rough bounding box (0 - 320 pixels)
    // tvert	Vertical sidelength of the rough bounding box (0 - 320 pixels)
    // getpipe	True active pipeline index of the camera (0 .. 9)
    // json	Full JSON dump of targeting results
    // tclass	Class ID of primary neural detector result or neural classifier result

    // ledMode.setNumber(0); // use the LED Mode set in the current pipeline
    // ledMode.setNumber(1); // force off
    // ledMode.setNumber(2); // force blink
    // ledMode.setNumber(3); // force on

    // camMode.setNumber(1); cam mode 1 for "webcam", 0 for vision processor
    SmartDashboard.putBoolean("Limelight on Target", onTarget);
    // post to smart dashboard periodically
    SmartDashboard.putNumber("X Limelight", tx);
    SmartDashboard.putNumber("Limelight Y", ty);
    SmartDashboard.putNumber("Limelight Area", ta);
    //SmartDashboard.putNumber("Limelight Latency", tl);
    SmartDashboard.putNumber("Limelight Valid Target", tv);
    SmartDashboard.putBoolean("Limelight Has Target", doesLimelightHaveTarget());
    SmartDashboard.putBoolean("Limelight in AprilTag Mode", isLimelightOnAprilTagMode());
  }


  public class doesLimelightHaveTarget {
  }

  // public void pipelineButtons() {
  //   if (XboxController.getRawButtonPressed(button)) {
  //     turnONLamp
  //   } else if (XboxController.getRawButtonPressed(button)) {
  //     turnOffLampLight
  //   };
  //   if (XboxController.getRawButtonPressed(button)) {
  //     PipelinieTwo
  //   } else if (XboxController.getRawButtonPressed(button)) {
  //     PipelineOne
  //   };
  
}
