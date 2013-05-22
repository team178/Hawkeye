package org.usfirst.frc178.components;

import org.usfirst.frc178.devices.Motors;
import org.usfirst.frc178.devices.Sensors;

import edu.wpi.first.wpilibj.PIDOutput;

public class Elevator implements PIDOutput {
	private Motors motors;
	private Sensors sensors;

	public Elevator(Motors motors, Sensors sensors) {
		this.motors = motors;
		this.sensors = sensors;
	}

	public void raise(double speed) {
		if (speed < 0) {
			throw new IllegalArgumentException();
		}

		if (!sensors.elevationHighSwitch.getState()) {
			motors.elevator.set(speed);
		} else {
			stop();
		}
	}

	public void lower(double speed) {
		if (speed < 0) {
			throw new IllegalArgumentException();
		}

		if (!sensors.elevationLowSwitch.getState()) {
			motors.elevator.set(-speed);
		} else {
			stop();
		}
	}

	public void stop() {
		motors.elevator.set(0.0);
	}

	public void pidWrite(double output) {
		System.out.println("Elevator: " + output);
		if (output < 0) {
			raise(output);
		} else {
			lower(-output);
		}
	}

}