package org.usfirst.frc178;

// Components
import org.usfirst.frc178.components.Drivetrain;
import org.usfirst.frc178.components.Shooter;

// Custom
import org.usfirst.frc178.custom.AnalogPressure;
import org.usfirst.frc178.custom.LimitSwitch;
import org.usfirst.frc178.custom.OculusClient;
import org.usfirst.frc178.custom.Photosensor;
import org.usfirst.frc178.custom.VisionProcessing;

// Dashboard
import org.usfirst.frc178.dashboard.DashboardHigh;

// Devices
import org.usfirst.frc178.devices.HumanControl;
import org.usfirst.frc178.devices.Motors;
import org.usfirst.frc178.devices.Pneumatics;
import org.usfirst.frc178.devices.Sensors;
import org.usfirst.frc178.devices.Spike;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.IterativeRobot;

public class RobotTemplate extends IterativeRobot  {

	// Constants
	public static final String ip = "10.1.78.13";
	public static final String port = "1780";

	// components
	private Drivetrain drivetrain;
	private Shooter shooter;

	// custom
	private AnalogPressure analogPressure;
	private OculusClient oculusClient;
	private VisionProcessing vision;

	// dashboard
	private DriverStationLCD dsout;
	private DriverStation driverStation;
	//private DashboardHigh dashboardHigh;

	// devices
	private HumanControl humanControl;
	private Motors motors;
	private Pneumatics pneumatics;
	private Sensors sensors;
	private Spike spikes;

	// And finally, the watchdog
	private Watchdog watchdog;

	// Autonomous event timer
	private Timer autoTimer;
	private boolean timerStarted;

	// Off-load vision processing to another thread
	private Thread visionThread;

	public void robotInit() {
		// custom
		oculusClient = new OculusClient(ip, port);
		analogPressure = new AnalogPressure();

		// devices
		humanControl = new HumanControl();
		motors = new Motors();
		pneumatics = new Pneumatics();
		sensors = new Sensors();
		spikes = new Spike();

		// dashboard
		driverStation = DriverStation.getInstance();
		//dashboardHigh = new DashboardHigh();
		dsout = DriverStationLCD.getInstance();
		dsout.updateLCD();

		// Grab an instance of Watchdog to use
		watchdog = Watchdog.getInstance();

		// Start in low gear
		pneumatics.setBothGears(1);

		// Autonomous event timer
		autoTimer = new Timer();
		timerStarted = false;

		// components
		drivetrain = new Drivetrain(motors, humanControl, pneumatics);
		shooter = new Shooter(motors, sensors, humanControl, pneumatics);
		vision = new VisionProcessing(drivetrain, shooter, humanControl, oculusClient);

		/*oculusClient.connect();
		visionThread = new Thread(vision, "Vision Thread");
		visionThread.start();*/
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		
		if (!sensors.pressureSwitch.getState()) { //runs contuniously
			spikes.compressorRelay.set(Relay.Value.kOff);
		} else { //state == true 
			spikes.compressorRelay.set(Relay.Value.kOn);
		}

		if (this.timerStarted == false) {
			autoTimer.start();
			this.timerStarted = true;
		}

		System.out.println(autoTimer.get());

		if (autoTimer.get() < 5) {
			shooter.shooterStart();
			// nothing, wait for shooter to start
		} else if (autoTimer.get() < 5.5) {
			pneumatics.frisbeeLoader.set(true);
			System.out.println("1");
		} else if (autoTimer.get() < 6) {
			pneumatics.frisbeeLoader.set(false);
			System.out.println("0");
		} else if (autoTimer.get() < 6.5) {
			motors.feederServo.set(0.0);
		} else if (autoTimer.get() < 7.5) {
			motors.feederServo.set(0.5);
		} else if (autoTimer.get() < 10) {
			//wait for 2.5sec
		} else if (autoTimer.get() < 10.5) {
			pneumatics.frisbeeLoader.set(true);
		} else if (autoTimer.get() < 11) {
			pneumatics.frisbeeLoader.set(false);
		} else if (autoTimer.get() < 11.5) {
			motors.feederServo.set(0.0);
		} else if (autoTimer.get() < 12) {
			motors.feederServo.set(0.5);
		} else if (autoTimer.get() < 13.5) {
			//wait for 1.5sec
		} else if (autoTimer.get() < 14) {
			pneumatics.frisbeeLoader.set(true);
		} else if (autoTimer.get() < 14.5) {
			pneumatics.frisbeeLoader.set(false);
		} else {
			shooter.shooterStop();
		}

		/*
			shooter.shooterStart();
			Thread.sleep(5000);
			pneumatics.frisbeeLoader.set(true);
			Thread.sleep(500);
			pneumatics.frisbeeLoader.set(false);
			Thread.sleep(500);
			motors.feederServo.set(0.0);
			Thread.sleep(1000);
			motors.feederServo.set(0.5);
			Thread.sleep(3000);
			pneumatics.frisbeeLoader.set(true);
			Thread.sleep(500);
			pneumatics.frisbeeLoader.set(false);
			Thread.sleep(500);
			motors.feederServo.set(0.0);
			Thread.sleep(1000);
			motors.feederServo.set(0.5);
			Thread.sleep(2000);
			pneumatics.frisbeeLoader.set(true);
			Thread.sleep(500);
			pneumatics.frisbeeLoader.set(false);
			
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			shooter.shooterStop();
		}*/
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		drivetrain.drive();
		shooter.run();

		//mdsout.println(DriverStationLCD.Line.kUser1, 1, "Position: " + oculusClient.request());
		dsout.println(DriverStationLCD.Line.kUser2, 1, "Volts: " + analogPressure.getVoltage());
		dsout.println(DriverStationLCD.Line.kUser1, 1, "Pressure: " + sensors.pressureSwitch.getState());

		if (sensors.elevationLoadSwitch.getState())
			dsout.println(DriverStationLCD.Line.kUser3, 1, "Load switch: Presssed");
		else
			dsout.println(DriverStationLCD.Line.kUser3, 1, "Load switch: Off     ");

		if (shooter.isShooterOn())
			dsout.println(DriverStationLCD.Line.kUser4, 1, "Shooter: On ");
		else
			dsout.println(DriverStationLCD.Line.kUser4, 1, "Shooter: Off");

		dsout.updateLCD();

		if (humanControl.joystickMain.getRawButton(5)) { //manual override for compressor
			System.out.println("on");
			spikes.compressorRelay.set(Relay.Value.kOn);
		} else {
			spikes.compressorRelay.set(Relay.Value.kOff);
		}

		if (!sensors.pressureSwitch.getState()) { //runs contuniously
			spikes.compressorRelay.set(Relay.Value.kOff);
		} else { //state == true 
			spikes.compressorRelay.set(Relay.Value.kOn);
		}

		// We've survived another loop!
		watchdog.feed();
	}

	public void printLimitSwitches() {
		System.out.print(sensors.elevationHighSwitch.getState() + "\t");
		System.out.print(sensors.elevationLoadSwitch.getState() + "\t");
		System.out.println(sensors.elevationLowSwitch.getState());
	}

}