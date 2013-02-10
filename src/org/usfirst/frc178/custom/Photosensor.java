package org.usfirst.frc178.custom;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 *
 * @author Administrator
 */
public class Photosensor 
{
    private AnalogChannel analog;
    
    public Photosensor(int channel)
    {
        analog = new AnalogChannel(channel);
    }
    public Photosensor(int module, int channel)
    {
        analog = new AnalogChannel(module, channel);
    }
}