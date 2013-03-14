package org.usfirst.frc178.devices;

import edu.wpi.first.wpilibj.*;
import org.usfirst.frc178.custom.*;

public class Sensors
{
	public Encoder shooterOneEncoder, shooterTwoEncoder, driveEncoderLeft, driveEncoderRight;
	public LimitSwitch elevationLowSwitch, elevationHighSwitch, elevationLoadSwitch, pressureSwitch;
	public Photosensor shooterPhoto, feederPhoto;

	public Sensors()
	{
			driveEncoderLeft = new Encoder(1,2);			//DIO ports 1,2
			driveEncoderLeft = new Encoder(3,4);			//DIO ports 3,4
			//shooterOneEncoder = new Encoder(5,6);			//DIO ports 5,6
			//shooterTwoEncoder = new Encoder(7,8);			//DIO ports 7,8
			elevationHighSwitch = new LimitSwitch(9, false);
			elevationLoadSwitch = new LimitSwitch(10, false);
			elevationLowSwitch = new LimitSwitch(12, false);
			pressureSwitch = new LimitSwitch(13,false);		//DIO port 14, cutoff at 120 psi --Check Normally Open State******
			shooterPhoto = new Photosensor(2);
			feederPhoto = new Photosensor(3);
	}

}