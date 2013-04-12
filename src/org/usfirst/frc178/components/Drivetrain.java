package org.usfirst.frc178.components;

import org.usfirst.frc178.devices.*;

public class Drivetrain  {

	private Motors motors;
	private Pneumatics pneumatics;

	private double speed;

	public Drivetrain(Motors motors, Pneumatics pneumatics) {
		this.motors = motors;
		this.pneumatics = pneumatics;

		this.speed = 1;
	}

	/**
	 * Set the driving speed
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * Get the driving speed
	 */
	public double getSpeed() {
		return this.speed;
	}

	/**
	 * Gets the front left motor speed
	 */
	public double getFrontLeft() {
		return motors.frontLeft.get();
	}

	/**
	 * Sets the front left Victor
	 */
	public void setFrontLeft(double value) {
		motors.frontLeft.set(value * this.speed);
	}

	/**
	 * Gets the front right motor speed
	 */
	public double getFrontRight() {
		return motors.frontRight.get();
	}

	/**
	 * Sets the front right Victor
	 */
	public void setFrontRight(double value) {
		motors.frontRight.set(value * this.speed);
	}

	/**
	 * Gets the back left motor speed
	 */
	public double getBackLeft() {
		return motors.backLeft.get();
	}

	/**
	 * Sets the back left Victor
	 */
	public void setBackLeft(double value) {
		motors.backLeft.set(value * this.speed);
	}

	/**
	 * Gets the back right motor speed
	 */
	public double getBackRight() {
		return motors.backRight.get();
	}

	/**
	 * Sets the back right Victor
	 */
	public void setBackRight(double value) {
		motors.backRight.set(value * this.speed);
	}

	/**
	 * Drives the robot
	 */
	public void drive(double moveX, double moveY, double moveZ) {
		// (-(Rotate) + (Forward Speed))
		setFrontLeft(  (moveZ - moveY) );
		setBackLeft(   (moveZ - moveY) );
		setFrontRight( (moveZ + moveY) );
		setBackRight(  (moveZ + moveY) );
	}

	public void shiftTo(boolean gear) {
		pneumatics.shiftTo(gear);
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
	public void turn(double moveZ) {
		setFrontLeft(moveZ);
		setBackLeft(moveZ);
		setFrontRight(moveZ);
		setBackRight(moveZ);
	}

	//Eric's auto-aim code
	public double proportaionalTurnControl(double setPoint) {
		return (setPoint * setPoint * setPoint);  //setPoint ^ 3
	}
}