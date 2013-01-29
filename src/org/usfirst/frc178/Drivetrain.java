package org.usfirst.frc178;
//Mechnum drivetrain
import edu.wpi.first.wpilibj.*;

public class Drivetrain {
	private Victor frontLeft, frontRight, rearLeft, rearRight;

	private Joystick joystick;
	private Joystick joystickKiddy;

	double robotX, robotY, robotZ, speed;

	public Drivetrain(int frontLeftN, int frontRightN, int rearLeftN, int rearRightN, Joystick joystick, final double speed, Joystick joystickKiddy) {
		frontLeft = new Victor(frontLeftN)
		{
			public void set(double d)
			{
				super.set(d * speed);
			}
		};
		frontRight = new Victor(frontRightN)
		{
			public void set(double d)
			{
				super.set(d * speed);
			}
		};
		rearLeft = new Victor(rearLeftN)
		{
			public void set(double d)
			{
				super.set(d * speed);
			}
		};
		rearRight = new Victor(rearRightN)
		{
			public void set(double d)
			{
				super.set(d * speed);
			}
		};

		this.joystick = joystick;
		this.joystickKiddy = joystickKiddy;
	}

	/**
	 * Gets the front left motor speed
	 */
	public double getFrontLeft()
	{
		return this.frontLeft.get();
	}

	public double getFrontRight() //Gets the front right motor speed
	{
		return this.frontRight.get();
	}

	public double getRearLeft() //Gets the rear left motor speed
	{
		return this.rearLeft.get();
	}

	public double getRearRight() //Gets the rear right motor speed
	{
		return this.rearRight.get();
	}

	public void drive() //Drives the robot
	{
		if (joystick.getTrigger()) {
			robotX = -joystick.getX();
			robotY = -joystick.getY();
			robotZ = -joystick.getTwist();
			speed = 0.7;
		} else {
                        //defaults to kiddie code
			robotX = -joystickKiddy.getX();
			robotY = -joystickKiddy.getY();
			robotZ = -joystickKiddy.getTwist();
			speed = 0.35; //Kiddie code: 0.35
		}

		if (joystick.getRawButton(2)) {
			speed = speed / 2;
			System.out.println("Hi, Going at half-speed. Whoooo!"); //Nice, Pat :)
		}
                //  Omniwheel Drive
		frontLeft.set(-(robotZ * speed * 1/3) + (robotY * speed) - (robotX * speed));
		rearLeft.set(-(robotZ * speed * 1/3) + (robotY * speed) + (robotX * speed));
		frontRight.set(-(robotZ * speed * 1/3) - (robotY * speed) - (robotX * speed));
		rearRight.set(-(robotZ * speed *1/3) - (robotY * speed) + (robotX * speed));
                //  4-Wheel Tank Drive
            /*  frontLeft.set(-(robotZ * speed * 1/3) + (robotY * speed) - (robotX * speed));
                rearLeft.set(-(robotZ * speed * 1/3) + (robotY * speed) + (robotX * speed));
                frontRight.set(-(robotZ * speed * 1/3) - (robotY * speed) - (robotX * speed));
                rearRight.set(-(robotZ * speed *1/3) - (robotY * speed) + (robotX * speed));
            */
         }

	public void frontLeftSet(double value) //Sets the front left Victor
	{
		frontLeft.set(value);
	}

	public void rearLeftSet(double value) //Sets the rear left Victor
	{
		rearLeft.set(value);
	}

	public void frontRightSet(double value) //Sets the front right Victor
	{
	frontRight.set(value);
	}

	public void rearRightSet(double value) //Sets the rear right Victor
	{
	rearRight.set(value);
	}

	public void setDiminishedSpeed(double fraction) //Sets the robot speed
	{
		frontLeft.set(getFrontLeft() * fraction);
		rearLeft.set(getRearLeft() * fraction);
		frontRight.set(getFrontRight() * fraction);
		rearRight.set(getRearRight() * fraction);
	}
}