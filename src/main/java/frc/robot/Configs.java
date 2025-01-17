package frc.robot;

import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;

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
                .smartCurrentLimit(ModuleConstants.kDrivingMotorCurrentLimit);

            driveConfig.encoder
                .positionConversionFactor(drivingFactor)
                .velocityConversionFactor(drivingFactor / 60);

            driveConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .pid(ModuleConstants.kDrivingP, ModuleConstants.kDrivingI, ModuleConstants.kDrivingD)
                .velocityFF(drivingVelocityFeedForward)
                .outputRange(ModuleConstants.kDrivingMinOutput, ModuleConstants.kDrivingMaxOutput);

             //TURN CONFIG

            turnConfig
                .idleMode(ModuleConstants.kDrivingMotorIdleMode)
                .smartCurrentLimit(ModuleConstants.kTurningMotorCurrentLimit);

            turnConfig.absoluteEncoder
                .inverted(false)
                .positionConversionFactor(turningFactor)
                .velocityConversionFactor(turningFactor / 60);

            turnConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kAbsoluteEncoder)
                .pid(ModuleConstants.kTurningP, ModuleConstants.kTurningI, ModuleConstants.kTurningD)
                .outputRange(ModuleConstants.kTurningMinOutput, ModuleConstants.kTurningMaxOutput)
                .positionWrappingEnabled(true)
                .positionWrappingInputRange(0, turningFactor);

        }
    }
}
