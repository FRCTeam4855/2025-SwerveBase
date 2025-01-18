// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.LightsConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.DriveWithAprilTagCommand;
import frc.robot.subsystems.DriveSubsystem;
<<<<<<< Updated upstream
import frc.robot.subsystems.LightsSubsystem;
=======
import frc.robot.subsystems.Limelight;
>>>>>>> Stashed changes
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.pathplanner.lib.auto.AutoBuilder;

//import frc.robot.subsystems.Limelight;
/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    // The robot's subsystems
    private final DriveSubsystem m_robotDrive = new DriveSubsystem();
<<<<<<< Updated upstream
    private final LightsSubsystem m_lights = new LightsSubsystem();
    //private final Limelight m_limelight = new Limelight();
=======
    private final Limelight m_limelight = new Limelight();
>>>>>>> Stashed changes
   

    // The driver's controller
    Joystick m_leftDriverController = new Joystick(OIConstants.kLeftDriverControllerPort);
    Joystick m_rightDriverController = new Joystick(OIConstants.kRightDriverControllerPort);
    // The Operator Controller
    CommandXboxController m_operatorController1 = new CommandXboxController(OIConstants.kOperatorControllerPort1);

    public static boolean fieldOriented = false;
    public double speedMultiplier = OIConstants.kSpeedMultiplierDefault;
    private final SendableChooser<Command> autoChooser;

    /**
    * The container for the robot. Contains subsystems, OI devices, and commands.
    */
    public RobotContainer() {
        //Register Named Commands
        // Configure the button bindings
        configureButtonBindings();

        // Configure default commands
        m_robotDrive.setDefaultCommand(
        // The left Joystick controls translation of the robot.
        // The right Joystick controls rotation of the robot.
        new RunCommand(
            () -> m_robotDrive.drive(
                -MathUtil.applyDeadband(m_leftDriverController.getRawAxis(1) * speedMultiplier, OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_leftDriverController.getRawAxis(0) * speedMultiplier, OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_rightDriverController.getRawAxis(0) * speedMultiplier, OIConstants.kDriveDeadband) * OIConstants.kRotateScale,
                fieldOriented, true),
            m_robotDrive));

            autoChooser = AutoBuilder.buildAutoChooser(); 
            SmartDashboard.putData("Auto Chooser", autoChooser);             
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
     * subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
     * passing it to a
     * {@link JoystickButton}.
     */
    private void configureButtonBindings() {
    
////    Driver Controls

       new JoystickButton(m_leftDriverController,OIConstants.kJS_BB)
            .whileTrue(new RunCommand(
                () -> m_robotDrive.setX(),
                m_robotDrive));

         new JoystickButton(m_rightDriverController,OIConstants.kJS_LB)
             .whileTrue(new RunCommand(
                 () -> m_robotDrive.strafeLeft(),
                 m_robotDrive));

         new JoystickButton(m_rightDriverController,OIConstants.kJS_RB)
             .whileTrue(new RunCommand(
                 () -> m_robotDrive.strafeRight(),
                 m_robotDrive));
       
        new JoystickButton(m_leftDriverController, OIConstants.kJS_RB).debounce(0.1)  //Gyro reset
            .whileTrue(new InstantCommand(
                () -> m_robotDrive.zeroHeading(),
                m_robotDrive)); 

        new JoystickButton(m_leftDriverController, OIConstants.kJS_LB)  //Field oriented toggle
            .whileTrue(new InstantCommand(
                () -> toggleFieldOriented()));
        
        new JoystickButton(m_leftDriverController, OIConstants.kJS_Trigger)  //Precise Driving Mode set
            .whileTrue(new InstantCommand(
                () -> speedMultiplier=OIConstants.kSpeedMultiplierPrecise));

        new JoystickButton(m_leftDriverController, OIConstants.kJS_Trigger)  //Precise Driving Mode clear
            .whileFalse(new InstantCommand(
                () -> speedMultiplier=OIConstants.kSpeedMultiplierDefault));
        
        // Operator Controls

<<<<<<< Updated upstream
        m_operatorController1.a()
            .whileTrue(new RunCommand(
                () -> m_lights.setLEDs(LightsConstants.GREEN),
                m_lights));
        m_operatorController1.x()
            .whileTrue(new RunCommand(
                () -> m_lights.setLEDs(LightsConstants.RED),
                m_lights));
        m_operatorController1.b()
            .whileTrue(new RunCommand(
                () -> m_lights.setLEDs(LightsConstants.VIOLET),
                m_lights));
        m_operatorController1.y()
            .whileTrue(new RunCommand(
                () -> m_lights.setLEDs(LightsConstants.GOLD),
                m_lights));
=======
        new JoystickButton(m_rightDriverController, OIConstants.kJS_Trigger)
            .whileTrue(new DriveWithAprilTagCommand(m_robotDrive, m_limelight, m_leftDriverController)
            );

>>>>>>> Stashed changes

////    Operator Controls 
 

          }
            /* TODO     
             * Auto Chooser DONE
             * Build paths and Autos DONE
             * Integrate LEDs to commands
             * Investigate loop overruns
             * Remove dead code blocks DONE
             * Find a cleaner way to declare these buttons and commands DONE
             * Look at switch statements for defining lists of things (buttons, autons, setpoints, etc.)
             * Program autons DONE
             * Autons with pathweaver DONE
             * Design a new drivestation that fits this laptop and the joysticks IN PROGRESS (depending on drake)
             * 
             * General Notes:
             * I think we're using commands for too simple of tasks. I think we can handle individual things 
             * (toggling states of things especially) with instant commands instead. I think the commands should be used when
             * we're grouping things together to keep the buik of the logic out of this file and in the command files.
             * Also learned that our smart dashboard calls should go in the periodic section of the subsystem. i agree
             */


    private void toggleFieldOriented () {
        fieldOriented = !fieldOriented;
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */


    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}
