package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import frc.robot.Constants.ModuleConstants;

public final class Configs {
    public static final class MAXSwerveModule{

        public static final SparkMaxConfig driveConfig = new SparkMaxConfig();
        public static final SparkMaxConfig turnConfig = new SparkMaxConfig();

        static{
            double drivingFactor = ModuleConstants.kWheelDiameterMeters * Math.PI
                    / ModuleConstants.kDrivingMotorReduction;
            double turningFactor = 2 * Math.PI;
            double drivingVelocityFeedForward = 1 / ModuleConstants.kDriveWheelFreeSpeedRps;

            //DRIVE CONFIG

            driveConfig
                .idleMode(ModuleConstants.kDrivingMotorIdleMode)
                .smartCurrentLimit(ModuleConstants.kTurningMotorCurrentLimit);

            driveConfig.encoder
                .positionConversionFactor(drivingFactor)
                .velocityConversionFactor(drivingFactor / 60);

            driveConfig.closedLoop
                .pid(ModuleConstants.kDrivingP, ModuleConstants.kDrivingI, ModuleConstants.kDrivingD)
                .velocityFF(drivingVelocityFeedForward)
                .outputRange(ModuleConstants.kDrivingMinOutput, ModuleConstants.kDrivingMaxOutput)
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder);

             //TURN CONFIG

            turnConfig
                .inverted(ModuleConstants.kTurningEncoderInverted)
                .idleMode(ModuleConstants.kDrivingMotorIdleMode)
                .smartCurrentLimit(ModuleConstants.kTurningMotorCurrentLimit);

            turnConfig.encoder
                .positionConversionFactor(turningFactor)
                .velocityConversionFactor(turningFactor / 60);

            turnConfig.closedLoop
                .pidf(ModuleConstants.kTurningP, ModuleConstants.kTurningI, ModuleConstants.kTurningD, ModuleConstants.kTurningFF)
                .outputRange(ModuleConstants.kTurningMinOutput, ModuleConstants.kTurningMinOutput)
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder); 

        }
    }
}
