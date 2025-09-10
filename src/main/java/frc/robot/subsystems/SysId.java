package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ModuleConstants;
import edu.wpi.first.units.VoltageUnit;
import edu.wpi.first.units.measure.MutDistance;
import edu.wpi.first.units.measure.MutLinearVelocity;
import edu.wpi.first.units.measure.MutVoltage;
import edu.wpi.first.units.measure.Velocity;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volts;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotController;
import com.revrobotics.RelativeEncoder;
import frc.robot.Configs;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
//import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;


public class SysId extends SubsystemBase{
    public final SparkMax m_frontDriveRight = new SparkMax(DriveConstants.kFrontRightDrivingCanId, MotorType.kBrushless);
    public final SparkMax m_frontDriveLeft = new SparkMax(DriveConstants.kFrontLeftDrivingCanId, MotorType.kBrushless);
    public final SparkMax m_rearDriveRight = new SparkMax(DriveConstants.kRearRightDrivingCanId, MotorType.kBrushless);
    public final SparkMax m_rearDriveLeft = new SparkMax(DriveConstants.kRearLeftDrivingCanId, MotorType.kBrushless);

    public final SparkMax m_frontTurningRight = new SparkMax(DriveConstants.kFrontRightTurningCanId, MotorType.kBrushless);
    public final SparkMax m_frontTurningLeft = new SparkMax(DriveConstants.kFrontLeftTurningCanId, MotorType.kBrushless);
    public final SparkMax m_rearTurningRight = new SparkMax(DriveConstants.kRearRightTurningCanId, MotorType.kBrushless);
    public final SparkMax m_rearTurningLeft = new SparkMax(DriveConstants.kRearLeftTurningCanId, MotorType.kBrushless);

    public final SparkClosedLoopController m_frontRightTurningPIDController;
    public final SparkClosedLoopController m_frontLeftTurningPIDController;
    public final SparkClosedLoopController m_rearRightTurningPIDController;
    public final SparkClosedLoopController m_rearLeftTurningPIDController;

    public final RelativeEncoder m_frontLeftEncoder = m_frontDriveLeft.getEncoder();
    public final RelativeEncoder m_frontRightEncoder = m_frontDriveRight.getEncoder();
    public final RelativeEncoder m_rearLeftEncoder = m_rearDriveLeft.getEncoder();
    public final RelativeEncoder m_rearRightEncoder = m_rearDriveRight.getEncoder();

    public SysId() {
      m_frontRightTurningPIDController = m_frontTurningRight.getClosedLoopController();
      m_frontLeftTurningPIDController = m_frontTurningLeft.getClosedLoopController();
      m_rearRightTurningPIDController = m_rearTurningRight.getClosedLoopController();
      m_rearLeftTurningPIDController = m_rearTurningLeft.getClosedLoopController();

      m_frontTurningRight.configure(Configs.MAXSwerveModule.turnConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
      m_frontTurningLeft.configure(Configs.MAXSwerveModule.turnConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
      m_rearTurningRight.configure(Configs.MAXSwerveModule.turnConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
      m_rearTurningLeft.configure(Configs.MAXSwerveModule.turnConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);


    }
    
  private final MutVoltage m_appliedVoltage = Volts.mutable(0);
  private final MutDistance m_distance = Meters.mutable(0);
  private final MutLinearVelocity m_velocity = MetersPerSecond.mutable(0);

  public final Velocity<VoltageUnit> m_rampRate = Velocity.ofRelativeUnits(2.0, Volts.per(Second));
    private final SysIdRoutine m_sysIdRoutine =
      new SysIdRoutine(
          new SysIdRoutine.Config(m_rampRate, null, Seconds.of(5.0)),
          new SysIdRoutine.Mechanism(
              voltage -> {
                m_frontDriveRight.setVoltage(voltage);
                m_frontDriveLeft.setVoltage(voltage);
                m_rearDriveRight.setVoltage(voltage);
                m_rearDriveLeft.setVoltage(voltage);
              },
              log -> {

          log.motor("drive-front-left")
              .voltage(
                  m_appliedVoltage.mut_replace(
                      m_frontDriveLeft.get() * RobotController.getBatteryVoltage(), Volts))
              .linearPosition(m_distance.mut_replace((m_frontLeftEncoder.getPosition() * ModuleConstants.kDrivingEncoderPositionFactor), Meters))
              .linearVelocity(
                  m_velocity.mut_replace((m_frontLeftEncoder.getVelocity() * ModuleConstants.kDrivingEncoderVelocityFactor), MetersPerSecond));

          log.motor("drive-front-right")
              .voltage(
                  m_appliedVoltage.mut_replace(
                      m_frontDriveRight.get() * RobotController.getBatteryVoltage(), Volts))
              .linearPosition(m_distance.mut_replace((m_frontRightEncoder.getPosition()* ModuleConstants.kDrivingEncoderPositionFactor), Meters))
              .linearVelocity(
                  m_velocity.mut_replace((m_frontRightEncoder.getVelocity() * ModuleConstants.kDrivingEncoderVelocityFactor), MetersPerSecond));

          log.motor("drive-rear-left")
              .voltage(
                  m_appliedVoltage.mut_replace(
                      m_rearDriveRight.get() * RobotController.getBatteryVoltage(), Volts))
              .linearPosition(m_distance.mut_replace((m_rearRightEncoder.getPosition()* ModuleConstants.kDrivingEncoderPositionFactor), Meters))
              .linearVelocity(
                  m_velocity.mut_replace((m_rearRightEncoder.getVelocity() * ModuleConstants.kDrivingEncoderVelocityFactor), MetersPerSecond));

          log.motor("drive-rear-right")
              .voltage(
                  m_appliedVoltage.mut_replace(
                      m_rearDriveRight.get() * RobotController.getBatteryVoltage(), Volts))
              .linearPosition(m_distance.mut_replace((m_rearRightEncoder.getPosition()* ModuleConstants.kDrivingEncoderPositionFactor), Meters))
              .linearVelocity(
                  m_velocity.mut_replace((m_rearRightEncoder.getVelocity() * ModuleConstants.kDrivingEncoderVelocityFactor), MetersPerSecond));
        },
        this));

          public Command sysIdQuasistatic(SysIdRoutine.Direction direction) {
            return m_sysIdRoutine.quasistatic(direction);
          }
          public Command sysIdDynamic(SysIdRoutine.Direction direction) {
            return m_sysIdRoutine.dynamic(direction);
          }
    }
