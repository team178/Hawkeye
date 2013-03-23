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
System.out.println("hi");

	}

	public void turn() {
		String str_x = oculusClient.request().substring(0, 5);
		double x = Double.parseDouble(str_x);
		drivetrain.turn(x);
	}

}