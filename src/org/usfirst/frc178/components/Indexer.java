/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc178.components;

import edu.wpi.first.wpilibj.DriverStation;
import java.io.IOException;
import org.usfirst.frc178.custom.OculusClient;
import org.usfirst.frc178.devices.HumanControl;
import org.usfirst.frc178.devices.Motors;
import org.usfirst.frc178.devices.Sensors;

/**
 *
 * @author Enforcers
 */
public class Indexer implements Runnable{

	private Motors motors;
	private Sensors sensors;
	private Thread t;
	private boolean kill;

	public Indexer(Motors motors, Sensors sensors) {
		this.motors = motors;
		this.sensors = sensors;

		this.kill = false;
	}

	public void start() {
		t = new Thread(this);
		t.start();
	}

	public void run() {
		while (!this.kill) {
			autoLoad();
		}
	}

	public void autoLoad() {
		//sensors.printPhotocells();
		if (sensors.shooterPhoto.analog.getAverageValue() > sensors.PHOTOCELL_THRESHOLD) {
			motors.feederServo.set(0.00);
		} else {
			motors.feederServo.set(0.75);
		}
	}

	public void kill() {
		this.kill = true;
	}

}
