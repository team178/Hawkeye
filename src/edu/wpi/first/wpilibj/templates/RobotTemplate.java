/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;
//Imports
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.NIVisionException;
import org.usfirst.frc178.DashboardHigh;
import org.usfirst.frc178.Drivetrain;
import org.usfirst.frc178.EnhancedIOFHS;
import org.usfirst.frc178.Sensors;
import org.usfirst.frc178.Tower;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class RobotTemplate extends IterativeRobot
{
	//Controls
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
	private CameraFHS camera;
	private ImageAnalysis imageAnalysis;

	//Watchdog
	private Watchdog watchdog;

	//Solenoid
	private Solenoid intakeSolenoid;

	//Misc. Variables
	Timer timer, timer2;
	double rangeLast = 0;
	boolean first = true;
	boolean stopped = false;
	double rangeBeforeStop = 0;
	int luminosityMin;
	double numParticles;
	DriverStationLCD dsout;
	boolean autoFist = true;

	public void robotInit() {
		timer2 = new Timer();

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

		//Sensors
		sensors = new Sensors();

		//Solenoid
		//intakeSolenoid = new Solenoid(3);
		//Image
		imageAnalysis = new ImageAnalysis(AxisCamera.getInstance());
		//camera = new CameraFHS(drivetrain, imageAnalysis);

		//Watchdog
		watchdog = Watchdog.getInstance();
		dsout = DriverStationLCD.getInstance();
		dsout.updateLCD();
	}

	public void autonomousPeriodic()
	{

		/*
		double range = sensors.getUltrasonicRight().getRangeInches();
		System.out.println(range);
		double robo_speed_far = 0.3;
		double robo_speed_close = 0.10;


	if((first || (Math.abs(range-rangeLast) < 20)) && !stopped)
	{
				rangeBeforeStop = range;
		if(first)
		{
					first = false;
		}

		if(range < 150)
		{
					drivetrain.frontLeftSet(-robo_speed_far);
					drivetrain.frontRightSet(robo_speed_far);
					drivetrain.rearLeftSet(-robo_speed_far);
					drivetrain.rearRightSet(robo_speed_far);
		}
		else if(range >= 150 && range < 180)
		{
					drivetrain.frontLeftSet(-robo_speed_close);
					drivetrain.frontRightSet(robo_speed_close);
					drivetrain.rearLeftSet(-robo_speed_close);
					drivetrain.rearRightSet(robo_speed_close);
		}
		else
		{
					System.out.println("STOP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					drivetrain.frontLeftSet(0.0);
					drivetrain.frontRightSet(0.0);
					drivetrain.rearLeftSet(0.0);
					drivetrain.rearRightSet(0.0);
		}
		rangeLast = range;
	}
	else if(stopped)
	{
			timer.start();
			System.out.println(timer.get());
			drivetrain.frontLeftSet(0.0);
			drivetrain.frontRightSet(0.0);
			drivetrain.rearLeftSet(0.0);
			drivetrain.rearRightSet(0.0);

			if((Math.abs(range-rangeBeforeStop) < 5) && timer.get() < 5)
			{
				timer.stop();
				timer.reset();
			stopped = false;
			rangeLast = range;
			}
	}
	else
	{
			drivetrain.frontLeftSet(0.0);
			drivetrain.frontRightSet(0.0);
			drivetrain.rearLeftSet(0.0);
			drivetrain.rearRightSet(0.0);
	}
*/
		if(autoFist)
		{
			autoFist = false;
			timer2.start();
		}

		double time = timer2.get();

		// Wait 4 seconds for shooting motors to power up (more dense ball first)
		if(time <= 4)
		{
			tower.setShooterMotors(0.43);
		}
		// Start Elevator for 2 seconds (first + second ball)
		else if(time > 4 && time <= 5)
		{
			tower.setBallElevator(1.0);
		}
		// Stop Elevator for a second
		else if(time > 5 && time <= 6)
		{
			tower.setBallElevator(0.0);
		}//.35 for 3
		else if(time > 6 && time <= 7)
		{
			tower.setBallElevator(1.0);
		}
		// Start Elevator for 2 seconds
		else if(time > 7 && time <= 10)
		{
			tower.setShooterMotors(0.0);
			tower.setBallElevator(0.0);
			/*drivetrain.rearLeftSet(-0.35);
			drivetrain.rearRightSet(0.42);
			drivetrain.frontLeftSet(-0.35);
			drivetrain.frontRightSet(0.42);
			tower.bridgeSolenoid.set(true);*/
		}
		else if(time > 10)
		{
			/*drivetrain.rearLeftSet(0.0);
			drivetrain.rearRightSet(0.0);
			drivetrain.frontLeftSet(0.0);
			drivetrain.frontRightSet(0.0);*/
			timer2.stop();
		}

		// Update values on Dashboard
		dashboardHigh.updateDashboardHigh(drivetrain, 0, sensors.getUltrasonicLeft().getRangeInches(), sensors.getUltrasonicRight().getRangeInches(), 0, luminosityMin, 0, joystick);

		tower.startCompressor();
	}

	public void teleopPeriodic()
	{
		//Movement
		if(joystick.getRawButton(11))
		{
			/*try
			{
				imageAnalysis.updateImage(0);
				camera.centerOnTarget(0, 0);
			}
			catch (AxisCameraException ex){}
			catch (NIVisionException ex){}*/
		}

		/*else if (joystick.getRawButton(9)) {
			if (camera.isCenter()) {

			}
		}*/
		else
		{
			drivetrain.drive();
		}

		dsout.println(DriverStationLCD.Line.kUser4, 2, "Slider: "+(int)(enhancedIO.getSlider()*100)+"%		");

		tower.cameraLightOn();
		tower.runJoystick();

		//Dashboard
		dashboardHigh.updateDashboardHigh(drivetrain, 0, sensors.getUltrasonicLeft().getRangeInches(), sensors.getUltrasonicRight().getRangeInches(), 0, luminosityMin, 0, joystick);

		//Watchdog
		watchdog.feed();
		dsout.updateLCD();
	}
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
}