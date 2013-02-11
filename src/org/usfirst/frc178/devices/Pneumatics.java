package org.usfirst.frc178.devices;

import edu.wpi.first.wpilibj.Solenoid;
/**
 *
 * @author Administrator
 */
public class Pneumatics 
{
		public Solenoid frisbeeLoader, shifterLeft, shifterRight;

	public Pneumatics()
	{
		//module defaults to 3
		frisbeeLoader = new Solenoid(1);	//port 1
		shifterLeft = new Solenoid(2);		//port 2
		shifterRight = new Solenoid(3);		//port 3
	}

	public void shiftBoth()
	{
		shiftLeft();
		shiftRight();
	}
	private void shiftLeft()
	{
		shifterLeft.set(!shifterLeft.get());
	}
	private void shiftRight()
	{
		shifterRight.set(!shifterRight.get());
	}
	private int getGear()
	{
		if(shifterLeft.get())
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
			shifterLeft.set(false);
			shifterRight.set(false);
			return getGear();
		}
		else	//gear == 2
		{
			shifterLeft.set(true);
			shifterRight.set(true);
			return getGear();
		}
	}
	public int resetGears()	//resets both gears to first gear
	{
		return setBothGears(1);
	}
}