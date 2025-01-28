// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import com.pathplanner.lib.config.PIDConstants;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class DriveConstants {
    // Driving Parameters - Note that these are not the maximum capable speeds of
    // the robot, rather the allowed maximum speeds
    public static final double kMaxSpeedMetersPerSecond = 4.0;
    public static final double kMaxAngularSpeed = 2 * Math.PI; // radians per second

    public static final double kDirectionSlewRate = 1.2; // radians per second
    public static final double kMagnitudeSlewRate = 1.8; // percent per second (1 = 100%)
    public static final double kRotationalSlewRate = 2.0; // percent per second (1 = 100%)

    // Chassis configuration
    public static final double kTrackWidth = Units.inchesToMeters(23);
    // Distance between centers of right and left wheels on robot
    public static final double kWheelBase = Units.inchesToMeters(23);
    // Distance between front and back wheels on robot
    public static final double kDriveRadius = Units.inchesToMeters(kTrackWidth*.5*Math.sqrt(2));
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

    // Angular offsets of the modules relative to the chassis in radians
    public static final double kFrontLeftChassisAngularOffset = 0;
    public static final double kFrontRightChassisAngularOffset = Math.PI;
    public static final double kBackLeftChassisAngularOffset = 0;
    public static final double kBackRightChassisAngularOffset = -Math.PI;

    // SPARK MAX CAN IDs
    public static final int kFrontLeftDrivingCanId = 1;
    public static final int kRearLeftDrivingCanId = 3;
    public static final int kFrontRightDrivingCanId = 7;
    public static final int kRearRightDrivingCanId = 5;

    public static final int kFrontLeftTurningCanId = 2;
    public static final int kRearLeftTurningCanId = 4;
    public static final int kFrontRightTurningCanId = 8;
    public static final int kRearRightTurningCanId = 6;

    public static final boolean kGyroReversed = true;
  }

  public static final class ModuleConstants {
    // The MAXSwerve module can be configured with one of three pinion gears: 12T, 13T, or 14T.
    // This changes the drive speed of the module (a pinion gear with more teeth will result in a
    // robot that drives faster).
    public static final int kDrivingMotorPinionTeeth = 14;

    // Invert the turning encoder, since the output shaft rotates in the opposite direction of
    // the steering motor in the MAXSwerve Module.
    public static final boolean kTurningEncoderInverted = false;

    // Calculations required for driving motor conversion factors and feed forward
    public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
    public static final double kWheelDiameterMeters = .097;// 0.105; Original
    public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;
    // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15 teeth on the bevel pinion
    //public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
    public static final double kDrivingMotorReduction = 6.122; // previously (45.0 * 22) / (kDrivingMotorPinionTeeth *
                                                               // 15)
    public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters)
        / kDrivingMotorReduction;

    public static final double kDrivingEncoderPositionFactor = (kWheelDiameterMeters * Math.PI)
        / kDrivingMotorReduction; // meters
    public static final double kDrivingEncoderVelocityFactor = ((kWheelDiameterMeters * Math.PI)
        / kDrivingMotorReduction) / 60.0; // meters per second

    public static final double kTurningEncoderPositionFactor = (2 * Math.PI); // radians
    public static final double kTurningEncoderVelocityFactor = (2 * Math.PI) / 60.0; // radians per second

    public static final double kTurningEncoderPositionPIDMinInput = 0; // radians
    public static final double kTurningEncoderPositionPIDMaxInput = kTurningEncoderPositionFactor; // radians

    public static final double kDrivingP = 0.04;
    public static final double kDrivingI = 0;
    public static final double kDrivingD = 0;
    public static final double kDrivingFF = 1 / kDriveWheelFreeSpeedRps;
    public static final double kDrivingMinOutput = -1;
    public static final double kDrivingMaxOutput = 1;

    public static final double kTurningP = 1;
    public static final double kTurningI = 0;
    public static final double kTurningD = 0;
    public static final double kTurningFF = 0;
    public static final double kTurningMinOutput = -1;
    public static final double kTurningMaxOutput = 1;

    public static final PIDConstants translationConstants = new PIDConstants(0.04, 0.0, 0.0);
    public static final PIDConstants rotationConstants = new PIDConstants(1, 0.0, 0.0);

    public static final IdleMode kDrivingMotorIdleMode = IdleMode.kBrake;
    public static final IdleMode kTurningMotorIdleMode = IdleMode.kBrake;

    public static final int kDrivingMotorCurrentLimit = 50; // amps
    public static final int kTurningMotorCurrentLimit = 20; // amps
  }

  public static final class OIConstants {
    public static final int kLeftDriverControllerPort = 0;  
    public static final int kRightDriverControllerPort = 1;
    public static final int kOperatorControllerPort1 = 2;
    public static final double kDriveDeadband = 0.1;
    public static final double kRotateScale = 0.5;
    public static final int kJS_Trigger = 1;  //KJS = Konstants Joystick
    public static final int kJS_RB = 4;
    public static final int kJS_BB = 2;
    public static final int kJS_LB = 3;
    public static final double kSpeedMultiplierDefault = 1;   // the default speed when no accessory buttons are held down
    public static final double kSpeedMultiplierPrecise = 0.5; // the speed when the trigger is held down for precise movements

    //Constants for using the Joystick slider to modify tuning parameters in real-time
    public static final double kTuningMin = 0.1; // the minimum speed for tuning
    public static final double kTuningMax = 0.5; // the maximum speed for tuning

    //driver buttons
    public static final int kGyroReset_Start = 3;
    public static final int kFieldOrientedToggle_LB = 4;

    //operator buttons


    public static final String kAuton1 = "1. Front Speaker Two Note";
    public static final String kAuton2 = "2. Front Speaker Launch & Leave";
    public static final String kAuton3 = "3. Source Side Leave";
    public static final String kAuton4 = "4. Blue Amp Side Leave";
    public static final String kAuton5 = "5. Red Amp Side Leave";
    public static final String kAuton6 = "6. Test Sequential Command";
  }


  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    public static final double kPXController = 1;
    public static final double kPYController = 1;
    public static final double kPThetaController = 1;

    // Constraint for the motion profiled robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
        kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
  }

  public static final class NeoMotorConstants {
    public static final double kFreeSpeedRpm = 5676;
  }
  
  public static final class LightsConstants {
    public final static double RAINBOW_RAINBOWPALETTE = -.99;
    public final static double RAINBOW_PARTYPALETTE = -.97;
    public final static double RAINBOW_OCEANPALETTE = -.95;
    public final static double RAINBOW_LAVAPALETTE = -.93;
    public final static double RAINBOW_FORESTPALETTE = -.91;
    public final static double RAINBOW_GLITTER = -.89;
    public final static double CONFETTI = -.87;
    public final static double SHOT_RED = -.85;
    public final static double SHOT_BLUE = -.83;
    public final static double SHOT_WHITE = -.81;
    public final static double SINELON_RAINBOWPALETTE = -.79;
    public final static double SINELON_PARTYPALETTE = -.77;
    public final static double SINELON_OCEANPALETTE = -.75;
    public final static double SINELON_LAVAPALETTE = -.73;
    public final static double SINELON_FORESTPALETTE = -.71;
    public final static double BPM_RAINBOWPALETTE = -.69;
    public final static double BPM_PARTYPALETTE = -.67;
    public final static double BPM_OCEANPALETTE = -.65;
    public final static double BPM_LAVAPALETTE = -.63;
    public final static double BPM_FORESTPALETTE = -.61;
    public final static double FIRE_MEDIUM = -.59;
    public final static double FIRE_LARGE = -.57;
    public final static double TWINKLES_RAINBOWPALETTE = -.55;
    public final static double TWINKLES_PARTYPALETTE = -.53;
    public final static double TWINKLES_OCEANPALETTE = -.55;
    public final static double TWINKLES_LAVAPALETTE = -.53;
    public final static double TWINKLES_FORESTPALETTE = -.51;
    public final static double COLORWAVES_RAINBOWPALETTE = -.45;
    public final static double COLORWAVES_PARTYPALETTE = -.43;
    public final static double COLORWAVES_OCEANPALETTE = -.41;
    public final static double COLORWAVES_LAVAPALETTE = -.39;
    public final static double COLORWAVES_FORESTPALETTE = -.37;
    public final static double LARSONSCAN_RED = -.35;
    public final static double LARSONSCAN_GRAY = -.33;
    public final static double LIGHTCHASE_RED = -.31;
    public final static double LIGHTCHASE_BLUE = -.29;
    public final static double LIGHTCHASE_GRAY = -.27;
    public final static double HEARTBEAT_RED = -.25;
    public final static double HEARTBEAT_BLUE = -.23;
    public final static double HEARTBEAT_WHITE = -.21;
    public final static double HEARTBEAT_GRAY = -.19;
    public final static double BREATH_RED = -.17;
    public final static double BREATH_BLUE = -.15;
    public final static double BREATH_GRAY = -.13;
    public final static double STROBE_RED = -.11;
    public final static double STROBE_BLUE = -.09;
    public final static double STROBE_GOLD = -.07;
    public final static double STROBE_WHITE = -.05;
    public final static double C1_END_TO_END_BLEND_TO_BLACK = -.03;
    public final static double C1_LARSONSCAN = -.01;
    public final static double C1_LIGHTCHASE = .01;
    public final static double C1_HEARTBEAT_SLOW = .03;
    public final static double C1_HEARTBEAT_MEDIUM = .05;
    public final static double C1_HEARTBEAT_FAST = .07;
    public final static double C1_BREATH_SLOW = .09;
    public final static double C1_BREATH_FAST = .11;
    public final static double C1_SHOT = .13;
    public final static double C1_STROBE = .15;
    public final static double C2_END_TO_END_BLEND_TO_BLACK = .17;
    public final static double C2_LARSONSCAN = .19;
    public final static double C2_LIGHTCHASE = .21;
    public final static double C2_HEARTBEAT_SLOW = .23;
    public final static double C2_HEARTBEAT_MEDIUM = .25;
    public final static double C2_HEARTBEAT_FAST = .27;
    public final static double C2_BREATH_SLOW = .29;
    public final static double C2_BREATH_FAST = .31;
    public final static double C2_SHOT = .33;
    public final static double C2_STROBE = .35;
    public final static double SPARKLE_C1_ON_C2 = .37;
    public final static double SPARKLE_C2_ON_C1 = .39;
    public final static double C1_AND_C2_GRADIENT = .41;
    public final static double C1_AND_C2_BPM = .43;
    public final static double C1_AND_C2_END_TO_END_BLEND = .45;
    public final static double END_TO_END_BLEND = .47; 
    public final static double C1_AND_C2_NO_BLEND = .49;
    public final static double C1_AND_C2_TWINKLES = .51;
    public final static double C1_AND_C2_COLOR_WAVES = .53;
    public final static double C1_AND_C2_SINELON = .55;
    public final static double HOT_PINK = .57;
    public final static double DARK_RED = .59;
    public final static double RED = .61;
    public final static double RED_ORANGE = .63;
    public final static double ORANGE = .65;
    public final static double GOLD = .67;
    public final static double YELLOW = .69;
    public final static double LAWN_GREEN = .71;
    public final static double LIME = .73;
    public final static double DARK_GREEN = .75;
    public final static double GREEN = .77;
    public final static double BLUE_GREEN = .79;
    public final static double AQUA = .81;
    public final static double SKY_BLUE = .83;
    public final static double DARK_BLUE = .85;
    public final static double BLUE = .87;
    public final static double BLUE_VIOLET = .89;
    public final static double VIOLET = .91;
    public final static double WHITE = .93;
    public final static double GRAY = .95;
    public final static double DARK_GRAY = .97;
    public final static double BLACK = .99;
  }
}
