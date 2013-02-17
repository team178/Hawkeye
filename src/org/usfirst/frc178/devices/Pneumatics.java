package org.usfirst.frc178.devices;

import edu.wpi.first.wpilibj.Solenoid;
/**
 *
 * @author Administrator
 */
public class Pneumatics 
{
		public Solenoid frisbeeLoader, shifterHigh, shifterLow;

	public Pneumatics()
	{
		//module defaults to 3
		frisbeeLoader = new Solenoid(1);	//port 1
		shifterHigh = new Solenoid(2);		//port 2 //2 and 3 go to the same soleniod. do not send same signal to both sides
		shifterLow = new Solenoid(3);		//port 3
	}

	public void shiftBoth()
	{
		shiftLeft();
		shiftRight();
	}
	private void shiftLeft()
	{
		shifterHigh.set(!shifterHigh.get());
	}
	private void shiftRight()
	{
		shifterLow.set(!shifterLow.get());
	}
	private int getGear()
	{
		if(shifterHigh.get())
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
			shifterHigh.set(false);
			shifterLow.set(true);
		//	shifterLow.set(false); //comment out for old solenoids
			return getGear();
		}
		else	//gear == 2
		{
			shifterHigh.set(true);
		//	shifterHigh.set(false); //comment out for old solenoids
			shifterLow.set(false);
			return getGear();
		}
	}
	public int resetGears()	//resets both gears to first gear
	{
		return setBothGears(1);
	}
}