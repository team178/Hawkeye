package org.usfirst.frc178.components;

import org.usfirst.frc178.custom.VisionProcessing;

import edu.wpi.first.wpilibj.Joystick;

public class HumanControl {

	private Joystick joystick;
	private Joystick joystickAux;
	private Joystick joystickKiddy;

	private Drivetrain drivetrain;
	private Shooter shooter;
	private Elevator elevator;
	private VisionProcessing vision;

	private boolean isElevatorMoving = false;
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

	public void setElevator(Elevator elevator) {
		this.elevator = elevator;
	}

	public void setShooter(Shooter shooter) {
		this.shooter = shooter;
	}

	public void setVision(VisionProcessing vision) {
		this.vision = vision;
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
		this.autoAim();
		this.hopper();
		this.bangbang();
	}

	/**
	 * Grab movement values from joystick controller
	 */
	private void drive(){
		boolean inKiddyMode = false;

		if (inKiddyMode == true){
			driveKiddy();
		} else {
			double moveX = joystick.getX();
			double moveY = joystick.getY();
			double moveZ = joystick.getTwist() * 0.5;
			drivetrain.drive(moveX, moveY, moveZ);
		}
	}

	/**
	 * Drive the robot with the kiddy controller.
	 */
	private void driveKiddy() {
		double moveX, moveY, moveZ;

		if (joystick.getTrigger()) {
			moveX = joystick.getX();
			moveY = joystick.getY();
			moveZ = joystick.getTwist() * 0.5;
		} else {
			moveX = joystickKiddy.getX() * 0.5;
			moveY = joystickKiddy.getY() * 0.5;
			moveZ = joystickKiddy.getTwist() * 0.4;
		}

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
		if (joystick.getRawButton(3)) {
			//this.isElevatorMoving = true;
			elevator.lower(1.0); // down
		} else if (joystick.getRawButton(4)) {
			//this.isElevatorMoving = true;
			elevator.raise(1.0); // up
		} else if (joystick.getRawButton(5)) {
			//this.isElevatorMoving = true;
			elevator.lower(0.5); // down
		} else if (joystick.getRawButton(6)) {
			//this.isElevatorMoving = true;
			elevator.raise(0.5); // up
		} else /*if (this.isElevatorMoving)*/ {
			//this.isElevatorMoving = false;
			elevator.stop();
		}
	
	}
	private void bangbang() {
		if (joystick.getRawButton(11)) {
			shooter.bangbang();
		} else {
			//shooter.stop();
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
			shooter.reverseIndexer();
		}
	}

	private void autoAim() {
		if (joystickAux.getRawButton(4)) { // Y button
			vision.aimElevator();
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
