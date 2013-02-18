package org.usfirst.frc178;

import org.usfirst.frc178.components.Shooter;
import org.usfirst.frc178.components.Drivetrain;
import org.usfirst.frc178.devices.*;
import org.usfirst.frc178.devices.Motors;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.IterativeRobot;

public class RobotTemplate extends IterativeRobot  {

	// components
	private Drivetrain drivetrain;
	private Shooter shooter;

	// custom
	private OculusClient oculusClient;

	// dashboard
	DriverStationLCD dsout;
	private DriverStation driverStation;
	//private DashboardHigh dashboardHigh;

	// devices
	private HumanControl humanControl;
	private Motors motors;
	private Pneumatics pneumatics;
	private Sensors sensors;
	private Spike spikes;

	private Watchdog watchdog;

	private boolean testMode = false;

	public void robotInit() {
		// components
		drivetrain = new Drivetrain(motors,humanControl,pneumatics);
		shooter = new Shooter(motors,sensors,humanControl,pneumatics);

		// dashboard
		driverStation = DriverStation.getInstance();
		//dashboardHigh = new DashboardHigh();

		// custom
		oculusClient = new OculusClient();

		// devices
		humanControl = new HumanControl();
		motors = new Motors();
		pneumatics = new Pneumatics();
		sensors = new Sensors();
		spikes = new Spike();

		dsout = DriverStationLCD.getInstance();
		dsout.updateLCD();

		watchdog = Watchdog.getInstance();

		pneumatics.setBothGears(1);

		if(testMode){
			pneumatics.setBothGears(2);
		}

		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		drivetrain.drive();
		shooter.run(); //if joystick trigger == true, fire
		watchdog.feed();

		if(humanControl.joystickMain.getRawButton(5)){ //manual override for compressor
			System.out.println("on");
			spikes.compressor.set(Relay.Value.kOn);
		} else	{
			spikes.compressor.set(Relay.Value.kOff);
		}

		System.out.println(sensors.pressureSwitch.getState());
		
		/*
		if(!sensors.pressureSwitch.getState()){ //runs contuniously, doesn't work
			spikes.compressor.set(Relay.Value.kOff);
		}
		else{ //state == true 
			spikes.compressor.set(Relay.Value.kOn);
		}
		*/
		
		
	}

}