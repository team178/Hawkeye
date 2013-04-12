package org.usfirst.frc178.components;

import edu.wpi.first.wpilibj.Joystick;

import org.usfirst.frc178.devices.Sensors;

public class HumanControl {

	private Joystick joystick;
	private Joystick joystickAux;
	private Joystick joystickKiddy;

	private Drivetrain drivetrain;
	private Shooter shooter;
	private Sensors sensors;

	private boolean isPressedA;

	public HumanControl(Joystick joystick) {
		this.joystick = joystick;
	}

	public HumanControl(Joystick joystick, Joystick joystickAux) {
		this.joystick = joystick;
		this.joystickAux = joystickAux;
	}

	public HumanControl(Joystick joystick, Joystick joystickAux,
			Joystick joystickKiddy) {
		this.joystick = joystick;
		this.joystickAux = joystickAux;
		this.joystickKiddy = joystickKiddy;
	}

	public void setDrivetrain(Drivetrain drivetrain) {
		this.drivetrain = drivetrain;
	}

	public void setShooter(Shooter shooter) {
		this.shooter = shooter;
	}

	public void setSensors(Sensors sensors) {
		this.sensors = sensors;
	}

	public void run() {
		this.main();
		this.aux();
	}

	private void main() {
		this.drive();
		this.driveDiminished();
		this.gearShifting();

		this.shooterElevator();
	}

	private void aux() {
		this.shooter();
		this.breech();
		this.indexer();
		this.hopper();
	}

	/**
	 * Grab movement values from joystick controller
	 */
	private void drive() {
		double moveX = joystick.getX();
		double moveY = joystick.getY();
		double moveZ = joystick.getTwist() * 0.5;

		drivetrain.drive(moveX, moveY, moveZ);
	}

	/**
	 * Drive the robot with the kiddy controller. Not being used yet.
	 */
	private void driveKiddy() {
		double moveX = joystickKiddy.getX();
		double moveY = joystickKiddy.getY();
		double moveZ = joystickKiddy.getTwist() * 0.5;

		drivetrain.drive(moveX, moveY, moveZ);
	}

	/**
	 * Set a slower speed when slow-down button is pressed
	 */
	private void driveDiminished() {
		if (joystick.getRawButton(2)) {
			drivetrain.setSpeed(0.625);
		} else {
			drivetrain.setSpeed(1.0);
		}
	}

	/**
	 * Shift to high gear when pressing trigger
	 */
	private void gearShifting() {
		drivetrain.shiftTo( joystick.getTrigger() );
	}

	/**
	 * Adjust the shooter elevator. Going to floor 13.
	 */
	private void shooterElevator() {
		if (joystick.getRawButton(5)) {
			if (!sensors.elevationLowSwitch.getState()) {
				shooter.elevate(-1.0); // down
			}
		} else if (joystick.getRawButton(6)) {
			if (!sensors.elevationHighSwitch.getState()) {
				shooter.elevate(1.0); // up
			}
		} else {
			shooter.elevatorStop();
		}
	}

	private void shooter() {
		if (joystickAux.getRawButton(1)) { // A button
			if (this.isPressedA == false) {
				shooter.toggle();
			}

			this.isPressedA = true;
		} else {
			this.isPressedA = false;
		}
	}

	private void breech() {
		if (joystickAux.getRawButton(2)) { // B button
			shooter.engageBreech();
		} else {
			shooter.disengageBreech();
		}
	}

	private void indexer() {
		if (joystickAux.getRawButton(3)) { // X button
			shooter.runIndexer();
		} else {
			shooter.stopIndexer();
		}
	}

	private void autoAim() {
		if (joystickAux.getRawButton(4)) { // Y button
			// Auto-aim code here
		}
	}

	private void hopper() {
		if (joystickAux.getRawButton(5)) { // Left Bumper
			shooter.extendHopper(); // Extend
		}

		if (joystickAux.getRawButton(6)) { // Right Bumper
			shooter.retractHopper(); // Retract
		}
	}

}
