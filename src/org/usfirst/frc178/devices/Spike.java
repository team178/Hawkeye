package org.usfirst.frc178.devices;

import edu.wpi.first.wpilibj.Relay;

/**
 *
 * @author Administrator
 */
public class Spike 
{
    public Relay compressor;
    public Relay cameraLight;
    
    public Spike()
    {
        compressor = new Relay(1,Relay.Direction.kForward);
        cameraLight = new Relay(2,Relay.Direction.kForward);
    }
}