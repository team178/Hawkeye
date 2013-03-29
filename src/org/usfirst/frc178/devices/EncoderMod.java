/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc178.devices;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author Enforcers
 */
public class EncoderMod implements PIDSource {

	private int aSource;
	private int bSource;
	private Encoder encoder;
	private double lastPIDGet;

	EncoderMod(int aSource, int bSource) {
		this.aSource = aSource;
		this.bSource = bSource;

		this.encoder = new Encoder(aSource, bSource);
		this.lastPIDGet = 0;
	}

	public void start() {
		this.encoder.start();
	}

	public void reset() {
		this.encoder.reset();
	}

	public void setDistancePerPulse(double distancePerPulse) {
		this.encoder.setDistancePerPulse(distancePerPulse);
	}

	public void setPIDSourceParameter(Encoder.PIDSourceParameter pidSource) {
		this.encoder.setPIDSourceParameter(pidSource);
	}

	public void setReverseDirection(boolean reverseDirection) {
		this.encoder.setReverseDirection(reverseDirection);
	}

	public double getRate() {
		return this.encoder.getRate();
	}

	public double pidGet() {
		double currentPIDGet = encoder.pidGet();

		if (currentPIDGet == 0) {
			System.out.println("Skip: " + currentPIDGet);
			System.out.println("Returning " + this.lastPIDGet);
			return this.lastPIDGet;
		} else {
			this.lastPIDGet = currentPIDGet;
			return currentPIDGet;
		}
	}

}