package org.usfirst.frc178;

import edu.wpi.first.wpilibj.*;

public class Sensors
{
	private final double DISTANCE_PER_PULSE = .196*Math.PI/1440; //1440 according to guy on Delphi stie in meters

	private UltrasonicFHS ultrasonicLeft, ultrasonicRight;
	private Gyro gyro;

	private Encoder encoderTop;
	private Encoder encoderBottom;

	public Sensors()
	{
	//gyro = new Gyro(1, 2);
	ultrasonicLeft = new UltrasonicFHS(3);
		ultrasonicRight = new UltrasonicFHS(4);
	//encoderBottom = new Encoder(1, 2);
		//encoderBottom.start();
	   // encoderBottom.setDistancePerPulse(DISTANCE_PER_PULSE);
	}

	public Gyro getGyro()
	{
	return gyro;
	}

	public Encoder getEncoder(int x)
	{
		if (x == 1)
		{
			return encoderTop;
	}
		else
		{
			return encoderBottom;
	}
	}

	public UltrasonicFHS getUltrasonicLeft()
	{
		return ultrasonicLeft;
	}

	public UltrasonicFHS getUltrasonicRight()
	{
		return ultrasonicRight;
	}
}
