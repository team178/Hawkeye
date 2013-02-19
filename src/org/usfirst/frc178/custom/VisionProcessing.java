package org.usfirst.frc178.custom;

import org.usfirst.frc178.components.*;
import org.usfirst.frc178.devices.*;

public class VisionProcessing implements Runnable {

	private Drivetrain drivetrain;
	private Shooter shooter;
	private HumanControl humanControl;
	private OculusClient oculusClient;

	public VisionProcessing(Drivetrain drivetrain, Shooter shooter, HumanControl humanControl, OculusClient oculusClient) {
		this.drivetrain = drivetrain;
		this.shooter = shooter;
		this.humanControl = humanControl;
		this.oculusClient = oculusClient;
	}

	public void run() {
		while(true) {
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