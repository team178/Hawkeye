package org.usfirst.frc178.devices;

import edu.wpi.first.wpilibj.*;
import org.usfirst.frc178.custom.*;

public class Sensors
{
        public Encoder shooterOneEncoder, shooterTwoEncoder, elevationEncoder, driveEncoderLeft, driveEncoderRight;
        public LimitSwitch elevationLowSwitch, elevationHighSwitch, pressureSwitch;
        
	public Sensors()
	{
            //driveEncoderLeft = new Encoder(1,2);    //DIO ports 1,2
            //driveEncoderLeft = new Encoder(3,4);    //DIO ports 3,4
            //shooterOneEncoder = new Encoder(5,6);   //DIO ports 5,6
            //shooterTwoEncoder = new Encoder(7,8);   //DIO ports 7,8
            //elevationEncoder = new Encoder(9,10);   //DIO ports 9,10
            //elevationLowSwitch = new LimitSwitch(11,true);
            //elevationHighSwitch = new LimitSwitch(12,true);
            //pressureSwitch = new LimitSwitch(13,true); //Check Normally Open State******
	}

}