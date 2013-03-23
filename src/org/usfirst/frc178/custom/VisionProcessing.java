package org.usfirst.frc178.custom;

import org.usfirst.frc178.components.*;
import org.usfirst.frc178.devices.*;

import edu.wpi.first.wpilibj.*;

public class VisionProcessing implements Runnable {

	private Drivetrain drivetrain;
	private Shooter shooter;
	private HumanControl humanControl;
	private OculusClient oculusClient;

	private Timer timer;

	private boolean connected;

	public VisionProcessing(Drivetrain drivetrain, Shooter shooter, HumanControl humanControl, OculusClient oculusClient) {
		this.drivetrain = drivetrain;
		this.shooter = shooter;
		this.humanControl = humanControl;
		this.oculusClient = oculusClient;

		this.timer = new Timer();

		this.connected = false;
	}

	public void start() {
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		this.timer.start();

		while (true) {
			if (!this.connected) {
				System.out.println("Connecting to coprocessor");
				boolean error = this.oculusClient.connect();
				if (error) {
					this.connected = true;
				} else {
					System.err.println("There was an issue connecting to the coprocessor");
				}
			}

			if (this.connected) {
				System.out.println(this.oculusClient.request());
			}

			if (humanControl.joystickMain.getTrigger()) { // X
				this.turn();
			}
		}
	}

	public void turn() {
		String str_x = oculusClient.request().substring(0, 5);
		double x = Double.parseDouble(str_x);
		drivetrain.turn(x);
	}

}