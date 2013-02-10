package org.usfirst.frc178.custom;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 * @author Administrator
 */
public class LimitSwitch 
{
    private int channel,module;
    private DigitalInput dIN;
    private boolean normallyOpen;
    
    public LimitSwitch(int channel, boolean normallyOpen)
    {
        this.channel = channel;
        this.normallyOpen = normallyOpen;
        dIN = new DigitalInput(this.channel);
    }
    
    public LimitSwitch(int module, int channel, boolean normallyOpen)
    {
        this.channel = channel;
        this.module = module;
        this.normallyOpen = normallyOpen;
        dIN = new DigitalInput(this.module,this.channel);
    }
    
    public boolean getState()
    {
        if(normallyOpen)
        {
            return dIN.get();
        }
        else
        {
            return !dIN.get();
        }
    }
}