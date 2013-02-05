package org.usfirst.frc178;

import edu.wpi.first.wpilibj.DriverStation;

public class EnhancedIOFHS {
	DriverStation ds;

	public EnhancedIOFHS(DriverStation driverStation)
	{
		ds = driverStation;
	}

	public double getSlider()
	{
		int var = (int)((Math.abs(ds.getAnalogIn(1) - 3.26) / 3.26)*100);
		double var2 = ((double)var/100)+0.05;
		if(var2 > 1.0)
		{
			return 1.0;
		}
		else
		{
			return var2;
		}
	}

	public boolean getFireButton()
	{
		return !ds.getDigitalIn(6);
	}

	public boolean[] getBridgeManipulatorSwitch()
	{
		boolean[] bool = new boolean[2];

		bool[0] = !ds.getDigitalIn(7);
		bool[1] = !ds.getDigitalIn(8);

		return bool;
	}

	public boolean[] getBallIntakeSwitch()
	{
		boolean[] bool = new boolean[2];

		bool[0] = !ds.getDigitalIn(3);//in
		bool[1] = !ds.getDigitalIn(4);//out

		return bool;
	}

	public boolean[] getBallElevatorSwitch()
	{
		boolean[] bool = new boolean[2];

		bool[0] = !ds.getDigitalIn(2);
		bool[1] = !ds.getDigitalIn(1);

		return bool;
	}
}
