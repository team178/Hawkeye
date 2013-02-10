package org.usfirst.frc178.components;
import org.usfirst.frc178.devices.*;
import org.usfirst.frc178.*;
/**
 *
 * @author FIRST TEAM 178
 */
public class Shooter 
{
    private Motors motors;
    private Sensors sensors;
    private HumanControl humanControl;
    
    public Shooter(Motors motors, Sensors sensors, HumanControl humanControl)
    {
        this.motors = motors;
        this.sensors = sensors;
        this.humanControl = humanControl;
    }
    
    public void shoot()
    {
        if(humanControl.joystickAux.getTrigger())
        {
            shooterStart();
        }
        else
        {
            shooterStop();
        }
    }
    
    public void shooterStart()
    {
        motors.shooterOne.set(1.0);
        motors.shooterTwo.set(1.0);
    }
    
    public void shooterStop()
    {
        motors.shooterOne.set(0.0);
        motors.shooterTwo.set(0.0);
    }
}