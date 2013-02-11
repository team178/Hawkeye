package org.usfirst.frc178.components;

import org.usfirst.frc178.devices.*;

public class Drivetrain  {

	private Motors motors;
	private HumanControl humanControl;

	double robotX, robotY, robotZ, speed;

	public Drivetrain(Motors motors, HumanControl humanControl) {
		this.motors = motors;
		this.humanControl = humanControl;
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
		robotX = -humanControl.joystickMain.getX();
		robotY = -humanControl.joystickMain.getY();
		robotZ = -humanControl.joystickMain.getTwist();

		motors.frontLeft.set(-(robotX) + (robotY)); // (-(Rotate) + (Forward Speed))
		motors.backLeft.set(-(robotX) + (robotY)); // (-(Rotate) + (Forward Speed))
		motors.frontRight.set(-(robotX) - (robotY)); // (-(Rotate) + (Forward Speed))
		motors.backRight.set(-(robotX) - (robotY)); // (-(Rotate) + (Forward Speed))
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
}