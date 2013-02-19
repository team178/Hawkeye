package org.usfirst.frc178.devices;

import edu.wpi.first.wpilibj.Solenoid;
/**
 *
 * @author Administrator
 */
public class Pneumatics  {

	public Solenoid frisbeeLoader, shifter, shifterLow;

	public Pneumatics()
	{
		//module defaults to 3
		frisbeeLoader = new Solenoid(1);
		shifter = new Solenoid(2);
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

	public int setBothGears(int gear) {
		// Check bounds
		if (gear > 2 || gear < 0) {
			throw new IllegalArgumentException("Only gears of first and second can be set");
		}

		// Why is this not setting shifterLow?
		// because shifterLow is no longer used. the old solenoids (the only ones that work) have only one DIO input.

		if (gear == 1) {
			// Switch to first gear
			shifter.set(false);
		} else {
			// Switch to second gear
			shifter.set(true);
		}
		return getGear();
	}

	/**
	 * Resets both gears to first gear
	 */
	public int resetGears()
	{
		return setBothGears(1);
	}
}