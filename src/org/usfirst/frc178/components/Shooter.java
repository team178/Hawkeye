package org.usfirst.frc178.components;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import org.usfirst.frc178.devices.*;
import org.usfirst.frc178.*;
/**
 *
 * @author FIRST TEAM 178
 */
public class Shooter {

	private Pneumatics pneumatics;
	private Motors motors;
	private Sensors sensors;

	private boolean isShooterOn;

	public Shooter(Motors motors, Sensors sensors, Pneumatics pneumatics) {
		this.pneumatics = pneumatics;
		this.motors = motors;
		this.sensors = sensors;
		
		this.sensors.shooterOneEncoder.start();
		this.isShooterOn = false;
	}

	public boolean isShooterOn() {
		return this.isShooterOn;
	}

	public void start() {
		motors.shooterOne.set(-1.0);
		motors.shooterTwo.set(-1.0);
		this.isShooterOn = true;
	}

	public void stop() {
		motors.shooterOne.set(0.0);
		motors.shooterTwo.set(0.0);
		this.isShooterOn = false;
	}

	public void toggle() {
		if (this.isShooterOn) {
			stop();
		} else {
			start();
		}
	}

	public void engageBreech() {
		pneumatics.frisbeeLoader.set(true);
	}

	public void disengageBreech() {
		pneumatics.frisbeeLoader.set(false);
	}

	public void runIndexer() {
		motors.feederServo.set(0.0);
	}

	public void reverseIndexer() {
		motors.feederServo.set(0.75);
	}

	public void extendHopper() {
		pneumatics.hopper.set(true);
	}

	public void retractHopper() {
		pneumatics.hopper.set(false);
	}

	public void bangbang() {
		double rate = sensors.shooterOneEncoder.getRate();
		System.out.println(rate);
		if (rate > -40) {
			motors.shooterOne.set(-1.0);
			motors.shooterTwo.set(-1.0);
		} else {
			motors.shooterOne.set(0.0);
			motors.shooterTwo.set(0.0);
		}
	}
	
	public void setExponentialElevator(double elevationSpeed) {
		double speed = 0;
		if (elevationSpeed > 0) {
			speed = MathUtils.pow(elevationSpeed, 2.5);
		}
		if (elevationSpeed < 0){
			speed = -MathUtils.pow(-elevationSpeed, 2.5);
		}
		motors.elevator.set(speed);
	}
}