/**----------------------------------------------------------------------------
 * Copyright (c) FIRST 2008. All Rights Reserved. 				
 * Open Source Software - may be modified and shared by FRC teams. The code   
 * must be accompanied by the FIRST BSD license file in the root directory of 
 * the project.									
 *----------------------------------------------------------------------------*/

package org.usfirst.frc178;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.NIVisionException;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Hawkeye extends IterativeRobot {
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

	// HID Controls
	private Joystick joystick;
	private Joystick joystickAux;
	private Joystick joystickKiddy;
	private EnhancedIOFHS enhancedIO;

	//Driverstation
	private DriverStation driverStation;
	private DashboardHigh dashboardHigh;

	//Movement
	private Drivetrain drivetrain;
	private Tower tower;

	//Sensors
	private Sensors sensors;
	//private CameraFHS camera;
	//private ImageAnalysis imageAnalysis;

	//Solenoid
	private Solenoid intakeSolenoid;

	//Watchdog
	private Watchdog watchdog;

	//Misc. Variables
	double rangeLast = 0;
	boolean first = true;
	boolean stopped = false;
	double rangeBeforeStop = 0;
	int luminosityMin;
	double numParticles;
	DriverStationLCD dsout;
	boolean autoFist = true;

	public void robotInit() {
		//Controls
		joystick = new Joystick(1); // joystick
		joystickAux = new Joystick(2); // xbox
		joystickKiddy = new Joystick(3); // Kiddy

		//DriverStation
		driverStation = DriverStation.getInstance();
		dashboardHigh = new DashboardHigh();
		enhancedIO = new EnhancedIOFHS(driverStation);

		//Movement
		drivetrain = new Drivetrain(8,4,9,5,joystick,1.0,joystickKiddy);
		tower = new Tower(driverStation,6,1,7,3,4,enhancedIO,joystickAux);

		//Solenoid
		//intakeSolenoid = new Solenoid(3);

		// DriveStation
		dsout = DriverStationLCD.getInstance();
		dsout.updateLCD();

		// Watchdog
		watchdog = Watchdog.getInstance();
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

		tower.runJoystick();
		tower.cameraLightOn();

		//dsout.println(DriverStationLCD.Line.kUser4, 2, "Slider: "+(int)(enhancedIO.getSlider()*100)+"%		");
		//dsout.updateLCD();

		//Dashboard
		//dashboardHigh.updateDashboardHigh(drivetrain, 0, sensors.getUltrasonicLeft().getRangeInches(), sensors.getUltrasonicRight().getRangeInches(), 0, luminosityMin, 0, joystick);

		//Watchdog
		watchdog.feed();
	}
	
}
