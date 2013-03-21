package org.usfirst.frc178.components;

import org.usfirst.frc178.devices.*;

public class Drivetrain  {

	private Motors motors;
	private HumanControl humanControl;
	private Pneumatics pneumatics;

	private double robotX, robotY, robotZ;

	public Drivetrain(Motors motors, HumanControl humanControl, Pneumatics pneumatics) {
		this.motors = motors;
		this.humanControl = humanControl;
		this.pneumatics = pneumatics;
	}

	/**
	 * Gets the front left motor speed
	 */
	public double getFrontLeft() {
		return motors.frontLeft.get();
	}

	/**
	 * Gets the front right motor speed
	 */
	public double getFrontRight() {
		return motors.frontRight.get();
	}

	/**
	 * Gets the back left motor speed
	 */
	public double getBackLeft() {
		return motors.backLeft.get();
	}

	/**
	 * Gets the back right motor speed
	 */
	public double getBackRight() {
		return motors.backRight.get();
	}

	/**
	 * Drives the robot
	 */
	public void drive() {
		// Shift to high gear when pressing trigger
		pneumatics.shiftTo( humanControl.joystickMain.getTrigger() );
		//pneumatics.shiftTo(humanControl.joystickMain.getRawButton(2));

		robotX = -humanControl.joystickMain.getX();
		robotY = -humanControl.joystickMain.getY();
		robotZ = -humanControl.joystickMain.getTwist() * 0.5; // Twist should be less sensitive

		// (-(Rotate) + (Forward Speed))
		motors.frontLeft.set(  (-(robotZ) + (robotY)) );
		motors.backLeft.set(   (-(robotZ) + (robotY)) );
		motors.frontRight.set( (-(robotZ) - (robotY)) );
		motors.backRight.set(  (-(robotZ) - (robotY)) );
		
		double halfSpeed = 0.625;
		if(humanControl.joystickMain.getRawButton(2))
		{
		// (-(Rotate) + (Forward Speed))
		motors.frontLeft.set(halfSpeed *  (-(robotZ) + (robotY)) );
		motors.backLeft.set(halfSpeed *   (-(robotZ) + (robotY)) );
		motors.frontRight.set(halfSpeed * (-(robotZ) - (robotY)) );
		motors.backRight.set(halfSpeed *  (-(robotZ) - (robotY)) );
		}
	}

	/**
	 * Sets the front left Victor
	 */
	public void frontLeftSet(double value) {
		motors.frontLeft.set(value);
	}

	/**
	 * Sets the back left Victor
	 */
	public void backLeftSet(double value) {
		motors.backLeft.set(value);
	}

	/**
	 * Sets the front right Victor
	 */
	public void frontRightSet(double value) {
		motors.frontRight.set(value);
	}

	/**
	 * Sets the back right Victor
	 */
	public void backRightSet(double value) {
		motors.backRight.set(value);
	}

	/**
	 * Sets the robot speed
	 */
	public void setDiminishedSpeed(double fraction) {
		motors.frontLeft.set(getFrontLeft() * fraction);
		motors.backLeft.set(getBackLeft() * fraction);
		motors.frontRight.set(getFrontRight() * fraction);
		motors.backRight.set(getBackRight() * fraction);
	}

	/**
	 * Turn the robot
	 */
	public void turn(double robotZ) {
		System.out.println(robotZ);
		motors.frontLeft.set(robotZ);
		motors.backLeft.set(robotZ);
		motors.frontRight.set(robotZ);
		motors.backRight.set(robotZ);
	}
}