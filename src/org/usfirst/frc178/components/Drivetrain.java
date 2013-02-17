package org.usfirst.frc178.components;

import org.usfirst.frc178.devices.*;

public class Drivetrain  {

	private Motors motors;
	private HumanControl humanControl;
	private Pneumatics pneumatics;

	private double robotX, robotY, robotZ;

	public Drivetrain(Motors motors, HumanControl humanControl,Pneumatics pneumatics) {
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
		
		if (humanControl.joystickMain.getRawButton(3)) { //low gear
			//pneumatics.setBothGears(1);
			pneumatics.shifterHigh.set(false);
			pneumatics.shifterLow.set(true);
		}
		if (humanControl.joystickMain.getRawButton(4)) { //high gear
			//pneumatics.setBothGears(2);
			pneumatics.shifterHigh.set(true);
			pneumatics.shifterLow.set(false);
		}
		
		if (!humanControl.joystickMain.getRawButton(3) && !humanControl.joystickMain.getRawButton(4)) { //high gear
			//pneumatics.setBothGears(2);
			pneumatics.shifterHigh.set(false);
			pneumatics.shifterLow.set(false);
		}

		robotX = -humanControl.joystickMain.getX();
		robotY = -humanControl.joystickMain.getY();
		robotZ = -humanControl.joystickMain.getTwist() * 0.5;//made z-axis less sensesative
		//robotZ = -humanControl.joystickMain.getThrottle()*0.4;
		
		motors.frontLeft.set(  (-(robotZ) + (robotY))); // (-(Rotate) + (Forward Speed))
		motors.backLeft.set(   (-(robotZ) + (robotY))); // (-(Rotate) + (Forward Speed))
		motors.frontRight.set( (-(robotZ) - (robotY))); // (-(Rotate) + (Forward Speed))
		motors.backRight.set(  (-(robotZ) - (robotY))); // (-(Rotate) + (Forward Speed))
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