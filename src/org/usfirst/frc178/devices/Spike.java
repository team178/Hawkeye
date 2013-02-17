package org.usfirst.frc178.devices;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Compressor;
/**
 *
 * @author Administrator
 */
public class Spike 
{
    public Relay compressor;
	//public Compressor compressor;
    public Relay cameraLight;
    
    public Spike()
    {  
		cameraLight = new Relay(2,Relay.Direction.kForward);

        compressor = new Relay(4, Relay.Direction.kForward);
		//compressor = new Compressor(13, 3);
    }
}