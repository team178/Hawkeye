package org.usfirst.frc178;

import org.usfirst.frc178.components.Shooter;
import org.usfirst.frc178.components.Drivetrain;
import org.usfirst.frc178.devices.*;
import org.usfirst.frc178.devices.Motors;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.IterativeRobot;

public class RobotTemplate extends IterativeRobot  {
	DriverStationLCD dsout;
	private DriverStation driverStation;
	//private DashboardHigh dashboardHigh;

	private Drivetrain drivetrain;
	private Shooter shooter;

	private Pneumatics pneumatics;
	private Sensors sensors;
	private Motors motors;
	private HumanControl humanControl;

	private Watchdog watchdog;

	public void robotInit() {
		driverStation = DriverStation.getInstance();
		//dashboardHigh = new DashboardHigh();

		pneumatics = new Pneumatics();
		motors = new Motors();
		sensors = new Sensors();
		humanControl = new HumanControl();

		drivetrain = new Drivetrain(motors,humanControl,pneumatics);
		shooter = new Shooter(motors,sensors,humanControl,pneumatics);

		dsout = DriverStationLCD.getInstance();
		dsout.updateLCD();

		pneumatics.setBothGears(1);
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
		shooter.shoot();
		watchdog.feed();
	}

}