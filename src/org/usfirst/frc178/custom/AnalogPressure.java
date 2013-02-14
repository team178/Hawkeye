package org.usfirst.frc178.custom;

import edu.wpi.first.wpilibj.AnalogChannel;
/**
 *
 * @author Administrator
 */
public class AnalogPressure 
{
	public AnalogChannel analogPressure;

	public AnalogPressure()
	{
		this.analogPressure = new AnalogChannel(4);	//Analog port 4
	}

	public double getVoltage()
	{
		return analogPressure.getVoltage();
	}
}