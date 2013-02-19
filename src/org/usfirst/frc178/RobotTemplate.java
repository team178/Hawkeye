package org.usfirst.frc178;

import org.usfirst.frc178.components.Shooter;
import org.usfirst.frc178.components.Drivetrain;
import org.usfirst.frc178.custom.*;
import org.usfirst.frc178.devices.*;
import org.usfirst.frc178.devices.Motors;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.IterativeRobot;

public class RobotTemplate extends IterativeRobot  {

	// Constants
	public static final String ip = "10.1.78.13";
	public static final String port = "178";

	// components
	private Drivetrain drivetrain;
	private Shooter shooter;

	// custom
	private OculusClient oculusClient;
	private VisionProcessing vision;

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
	private AnalogPressure analogPressure;

	private Thread visionThread;

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
		oculusClient = new OculusClient(ip, port);
		analogPressure = new AnalogPressure();

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
		// components
		drivetrain = new Drivetrain(motors,humanControl,pneumatics);
		shooter = new Shooter(motors,sensors,humanControl,pneumatics);
		vision = new VisionProcessing(drivetrain, shooter, humanControl, oculusClient);

		oculusClient.connect();
		visionThread = new Thread(vision, "Vision Thread");
		visionThread.start();
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

		//mdsout.println(DriverStationLCD.Line.kUser1, 1, "Position: " + oculusClient.request());
		dsout.println(DriverStationLCD.Line.kUser2, 1, "Volts: " + analogPressure.getVoltage());
		dsout.updateLCD();

		if(humanControl.joystickMain.getRawButton(5)){ //manual override for compressor
			System.out.println("on");
			spikes.compressorRelay.set(Relay.Value.kOn);
		} else	{
			spikes.compressorRelay.set(Relay.Value.kOff);
		}

		//System.out.println(sensors.pressureSwitch.getState());
		
		/*
		if(!sensors.pressureSwitch.getState()){ //runs contuniously, doesn't work
			spikes.compressorRelay.set(Relay.Value.kOff);
		}
		else{ //state == true 
			spikes.compressorRelay.set(Relay.Value.kOn);
		}
		*/
		
		
	}

}