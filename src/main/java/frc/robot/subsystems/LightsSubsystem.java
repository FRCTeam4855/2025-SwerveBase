package frc.robot.subsystems;


import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LightsConstants;

public class LightsSubsystem extends SubsystemBase {
    
    public final Spark m_Blinkin;  //4855
    
    public LightsSubsystem() { 
        m_Blinkin = new Spark(0);
        setLEDs(LightsConstants.C1_AND_C2_SINELON);
    }

    public void setLEDs(double color) {
        m_Blinkin.set(color);
    }

}
