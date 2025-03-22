package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.Constants.LightsConstants;

public class LightsSubsystem extends Subsystem {

    public final Spark m_Blinkin; // 4855

    @Override
    public void robotInit() {
        DataLogManager.log("LightsSubsystem in robotInit");
    }

    @Override
    public void autonomousInit() {
        DataLogManager.log("LightsSubsystem in autonomousInit");
    }

    @Override
    public void teleopInit() {
        DataLogManager.log("LightsSubsystem in teleopInit");
    }

    public LightsSubsystem() {
        m_Blinkin = new Spark(0);
        setLEDs(LightsConstants.C1_AND_C2_SINELON);
    }

    public void setLEDs(double color) {
        m_Blinkin.set(color);
    }

}
