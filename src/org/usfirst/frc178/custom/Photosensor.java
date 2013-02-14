package org.usfirst.frc178.custom;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 *
 * @author Administrator
 */
public class Photosensor 
{
    private AnalogChannel analogInput;
	private double threshold;
    
    public Photosensor(int channel, double threshold)
    {
        analogInput = new AnalogChannel(channel);
		this.threshold = threshold;
    }
    public Photosensor(int module, int channel, double threshold)
    {
        analogInput = new AnalogChannel(module, channel);
		this.threshold = threshold;
    }
	
	public boolean sense()
	{
		if(analogInput.getVoltage() < this.threshold)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public double getVoltage()
	{
		return analogInput.getVoltage();
	}
	
	public int getValue()
	{
		return analogInput.getValue();
	}
}