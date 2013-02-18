package org.usfirst.frc178.devices;

import edu.wpi.first.wpilibj.Solenoid;
/**
 *
 * @author Administrator
 */
public class Pneumatics 
{
		public Solenoid frisbeeLoader, shifter, shifterLow;

	public Pneumatics()
	{
		//module defaults to 3
		frisbeeLoader = new Solenoid(1);
		shifter = new Solenoid(2);
		shifterLow = new Solenoid(3);
	}

	public boolean shift()
	{
		shifter.set(!shifter.get());
		return shifter.get();

	}
	public boolean shiftTo(boolean shiftTo)
	{
		shifter.set(shiftTo);
		return shifter.get();
	}
	private int getGear()
	{
		if(shifter.get())
		{
			return 1;
		}
		else
		{
			return 2;
		}
	}
	public int setBothGears(int gear)
	{
		if(gear > 2 || gear < 0)
		{
			return -1;
		}
		else if(gear == 1)
		{
			shifter.set(false);
			return getGear();
		}
		else	//gear == 2
		{
			shifter.set(true);
			return getGear();
		}
	}
	public int resetGears()	//resets both gears to first gear
	{
		return setBothGears(1);
	}
}