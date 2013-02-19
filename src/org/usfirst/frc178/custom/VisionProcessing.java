package org.usfirst.frc178.custom;

import org.usfirst.frc178.components.*;

public class VisionProcessing {

	private Drivetrain drivetrain;
	private Shooter shooter;
	private OculusClient oculusClient;

	public void VisionProcessing(Drivetrain drivetrain, Shooter shooter, OculusClient oculusClient) {
		this.drivetrain = drivetrain;
		this.shooter = shooter;
		this.oculusClient = oculusClient;
	}

	/**
	 * Automatically aim
	 */
	public void aim() {
		oculusClient.request();
		
	}

}