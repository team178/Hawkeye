package org.usfirst.frc178.custom;

import edu.wpi.first.wpilibj.*;
import java.io.IOException;

import org.usfirst.frc178.components.*;

public class VisionProcessing implements Runnable {

	private Drivetrain drivetrain;
	private Elevator elevator;
	private Shooter shooter;
	private OculusClient oculusClient;
	private DriverStation driverStation;

	private PIDController verticalPID;

	private boolean started;
	private boolean isEnabled;
	private boolean isConnected;

	public VisionProcessing(Drivetrain drivetrain, Elevator elevator,
			Shooter shooter, OculusClient oculusClient) {
		this.drivetrain = drivetrain;
		this.elevator = elevator;
		this.shooter = shooter;
		this.oculusClient = oculusClient;
		this.driverStation = DriverStation.getInstance();

		this.verticalPID = new PIDController();

		this.isEnabled = false;
		this.isConnected = false;
	}

	public void start() {
		if (!this.started) {
			Thread t = new Thread(this);
			t.start();
			this.started = true;
		}
	}

	public void run() {
		while (true) {

			this.disableCheck();

			if (this.isConnected) {
				String packet = this.oculusClient.request();
				if (!packet.equals("")) {
					System.out.println(parseY(packet));
				}
			}

		}
	}

	/**
	 * Continuously check if the robot is disabled
	 */
	private void disableCheck() {
		if (this.isEnabled && this.driverStation.isDisabled()) {
			this.isEnabled = false;
			try {
				this.oculusClient.disconnect();
				this.isConnected = false;
				System.out.println("Disconnected from coprocessor");
			} catch (IOException e) {
				System.err.println("Disconnect failed.. what..?");
			}
		} else if (!this.isEnabled && this.driverStation.isEnabled()) {
			System.out.println("Connecting to coprocessor");
			this.isEnabled = true;
			if (!this.isConnected) {
				try {
					this.oculusClient.connect();
					this.isConnected = true;
					System.out.println("Connect success");
				} catch (IOException e) {
					System.err.println("Connect failed");
				}
			}
		}
	}

	public void turn() {
		String str_x = oculusClient.request().substring(0, 5);
		double x = Double.parseDouble(str_x);
		drivetrain.turn(x);
	}

	public String parseY(String oculusPoint) {
		return oculusPoint.substring(oculusPoint.indexOf(',') + 1, oculusPoint.indexOf(')') - 1); //Double.parseDouble(oculusPoint.substring(oculusPoint.indexOf(',') + 1, oculusPoint.indexOf(')') - 1));
	}

}