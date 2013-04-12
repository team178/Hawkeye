package org.usfirst.frc178.devices;

import com.sun.squawk.platform.posix.natives.LibC;
import edu.wpi.first.wpilibj.*;
import org.usfirst.frc178.custom.*;

public class Sensors {

	public final short PHOTOCELL_THRESHOLD = 200;

	public Encoder shooterOneEncoder, shooterTwoEncoder, driveEncoderLeft, driveEncoderRight;
	public LimitSwitch elevationLowSwitch, elevationHighSwitch, elevationLoadSwitch;
	public LimitSwitch pressureSwitch;
	public Photosensor shooterPhoto, feederPhoto;

	public Sensors() {
			//driveEncoderLeft = new Encoder(1,2);			//DIO ports 1,2
			//driveEncoderLeft = new Encoder(3,4);			//DIO ports 3,4
			//shooterOneEncoder = new Encoder(5,6);			//DIO ports 5,6
			shooterOneEncoder = new Encoder(5,6);
			//shooterTwoEncoder = new Encoder(7,8);			//DIO ports 7,8
			elevationHighSwitch = new LimitSwitch(9, false);
			elevationLoadSwitch = new LimitSwitch(10, false);
			elevationLowSwitch = new LimitSwitch(12, false);
			pressureSwitch = new LimitSwitch(13, false);		//DIO port 14, cutoff at 120 psi --Check Normally Open State******
			shooterPhoto = new Photosensor(3);
			//feederPhoto = new Photosensor(3);

			shooterOneEncoder.setDistancePerPulse(1/64d);
	}
	
	public void printEncoders(){
		System.out.println(shooterOneEncoder.getRate());
	}
	
	public void printPhotocells(){
		System.out.println(shooterPhoto.analog.getAverageValue());
	}

	public void printLimitSwitches() {
		System.out.print(elevationHighSwitch.getState() + "\t");
		System.out.print(elevationLoadSwitch.getState() + "\t");
		System.out.println(elevationLowSwitch.getState());
	}

	public void PID(){
		
	}
	
	public double proportaionalShooterControl(double setPoint, Encoder encoder){
		double currentRate = encoder.getRate();
		double error = setPoint - currentRate;

		double gain = 50;
		
		double output = error * gain + setPoint;
		double scaledOutput =(output/(setPoint * gain + setPoint));
		return (scaledOutput * scaledOutput * scaledOutput);  //scaledOutput ^ 3
	}

}