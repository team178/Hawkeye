// Travis was here
package org.usfirst.frc178;

// Components
import org.usfirst.frc178.components.Drivetrain;
import org.usfirst.frc178.components.Elevator;
import org.usfirst.frc178.components.Indexer;
import org.usfirst.frc178.components.HumanControl;
import org.usfirst.frc178.components.Shooter;

// Custom
import org.usfirst.frc178.custom.*;
import org.usfirst.frc178.custom.AnalogPressure;
import org.usfirst.frc178.custom.LimitSwitch;
import org.usfirst.frc178.custom.OculusClient;
import org.usfirst.frc178.custom.Photosensor;
import org.usfirst.frc178.custom.VisionProcessing;

// Dashboard
import org.usfirst.frc178.dashboard.DashboardHigh;

// Devices
import org.usfirst.frc178.devices.Motors;
import org.usfirst.frc178.devices.Pneumatics;
import org.usfirst.frc178.devices.Sensors;
import org.usfirst.frc178.devices.Spike;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.IterativeRobot;

public class RobotTemplate extends IterativeRobot  {

	// Constants
	public static final String ip = "10.1.78.24";
	public static final String port = "1780";

	// Joysticks
	private Joystick joystick;
	private Joystick joystickAux;
	private Joystick joystickKiddy;

	// components
	private Drivetrain drivetrain;
	private Elevator elevator;
	private HumanControl humanControl;
	private Indexer indexer;
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
	private Motors motors;
	private Pneumatics pneumatics;
	private Sensors sensors;
	private Spike spikes;

	// And finally, the watchdog
	private Watchdog watchdog;

	public void robotInit() {
		// Joysticks
		joystick = new Joystick(1);
		joystickAux = new Joystick(2);
		joystickKiddy = new Joystick(3);

		// custom
		oculusClient = new OculusClient(ip, port);
		analogPressure = new AnalogPressure();

		// devices
		//humanControl = new HumanControl(joystick, joystickAux); // THIS IS PRODUCTION CODE
		humanControl = new HumanControl(joystick, joystickAux,joystickKiddy); 
		motors = new Motors();
		pneumatics = new Pneumatics();
		sensors = new Sensors();
		spikes = new Spike();

		// dashboard
		driverStation = DriverStation.getInstance();
		//dashboardHigh = new DashboardHigh();
		dsout = DriverStationLCD.getInstance();
		dsout.updateLCD();

		// Start in low gear
		pneumatics.setBothGears(1);

		// components
		drivetrain = new Drivetrain(motors, pneumatics);
		elevator = new Elevator(motors, sensors);
		shooter = new Shooter(motors, sensors, pneumatics);
		vision = new VisionProcessing(drivetrain, elevator, shooter, oculusClient);
		indexer = new Indexer(motors, sensors);

		// Add components to HumanControl
		humanControl.setDrivetrain(drivetrain);
		humanControl.setElevator(elevator);
		humanControl.setShooter(shooter);
		humanControl.setVision(vision);

		// Grab an instance of Watchdog to use
		watchdog = Watchdog.getInstance();
	}

	public void autonomousInit() {
		if (!sensors.pressureSwitch.getState()) { //runs contuniously
			spikes.compressorRelay.set(Relay.Value.kOff);
		} else { //state == true 
			spikes.compressorRelay.set(Relay.Value.kOn);
		}

		// Automatically load new frisbees
		indexer.start();

		// Start the shooter
		shooter.start();
		Timer.delay(8);

		// We're ready! Shot 1
		pneumatics.frisbeeLoader.set(true);
		Timer.delay(0.5);
		pneumatics.frisbeeLoader.set(false);

		Timer.delay(2);

		// Shot 2
		pneumatics.frisbeeLoader.set(true);
		Timer.delay(0.5);
		pneumatics.frisbeeLoader.set(false);

		Timer.delay(1.7);

		// Shot 3
		pneumatics.frisbeeLoader.set(true);
		Timer.delay(0.5);
		pneumatics.frisbeeLoader.set(false);

		// We're done. Stop everything
		shooter.stop();
		motors.feederServo.set(0.5);
		indexer.kill();
	}

	public void teleopInit() {
		vision.start();
		shooter.retractHopper();
		shooter.stop();
	}

	/**
	 * This function is called periodically during operator control
	 */
	
	public void teleopPeriodic() {

		humanControl.run();

		if (!sensors.pressureSwitch.getState()) { //runs contuniously
			spikes.compressorRelay.set(Relay.Value.kOff);
		} else { //state == true 
			spikes.compressorRelay.set(Relay.Value.kOn);
		}

		// We've survived another loop!
		watchdog.feed();
	}

}