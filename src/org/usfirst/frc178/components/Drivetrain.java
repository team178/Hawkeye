package org.usfirst.frc178.components;

import org.usfirst.frc178.devices.*;

public class Drivetrain 
{
        private Motors motors;
        private HumanControl humanControl;

	double robotX, robotY, robotZ, speed;

	public Drivetrain(Motors motors, HumanControl humanControl) 
        {
		this.motors = motors;
                this.humanControl = humanControl;
	}
	/**
	 * Gets the front left motor speed
	 */
	public double getFrontLeft()
	{
		return motors.frontLeft.get();
	}

	public double getFrontRight() //Gets the front right motor speed
	{
		return motors.frontRight.get();
	}

	public double getBackLeft() //Gets the back left motor speed
	{
		return motors.backLeft.get();
	}

	public double getBackRight() //Gets the back right motor speed
	{
		return motors.backRight.get();
	}

	public void drive() //Drives the robot
	{
		robotX = -humanControl.joystickMain.getX();
		robotY = -humanControl.joystickMain.getY();
		robotZ = -humanControl.joystickMain.getTwist();


		motors.frontLeft.set(-(robotZ) + (robotY)); // (-(Rotate) + (Forward Speed))
		motors.backLeft.set(-(robotZ) + (robotY)); // (-(Rotate) + (Forward Speed))
		motors.frontRight.set(-(robotZ) - (robotY)); // (-(Rotate) + (Forward Speed))
		motors.backRight.set(-(robotZ) - (robotY)); // (-(Rotate) + (Forward Speed))
	}

	public void frontLeftSet(double value) //Sets the front left Victor
	{
		motors.frontLeft.set(value);
	}

	public void backLeftSet(double value) //Sets the back left Victor
	{
		motors.backLeft.set(value);
	}

	public void frontRightSet(double value) //Sets the front right Victor
	{
                motors.frontRight.set(value);
	}

	public void backRightSet(double value) //Sets the back right Victor
	{
                motors.backRight.set(value);
	}

	public void setDiminishedSpeed(double fraction) //Sets the robot speed
	{
		motors.frontLeft.set(getFrontLeft() * fraction);
		motors.backLeft.set(getBackLeft() * fraction);
		motors.frontRight.set(getFrontRight() * fraction);
		motors.backRight.set(getBackRight() * fraction);
	}
}