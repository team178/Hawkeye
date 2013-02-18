package org.usfirst.frc178.components;

import org.usfirst.frc178.devices.*;
import org.usfirst.frc178.*;
/**
 *
 * @author FIRST TEAM 178
 */
public class Shooter 
{
	private Pneumatics pneumatics;
	private Motors motors;
	private Sensors sensors;
	private HumanControl humanControl;

	public Shooter(Motors motors, Sensors sensors, HumanControl humanControl, Pneumatics pneumatics)
	{
		this.pneumatics = pneumatics;
		this.motors = motors;
		this.sensors = sensors;
		this.humanControl = humanControl;
	}
	
	public void run() {
		this.aux();
		if(humanControl.joystickAux.getTrigger()){
			shooterStart();
		}
		else{
			shooterStop();
		}
	}
	/**
	 * aux controller
	 * receives button inputs
	 */
	public void aux()
	{
		if(humanControl.joystickAux.getRawButton(1)){	//button A
		//auto fire
		}
		if(humanControl.joystickAux.getRawButton(2)){	//button B
		//breech
		}
		if(humanControl.joystickAux.getRawButton(3)){	//button X
		//auto aim
		}
		if(humanControl.joystickAux.getRawButton(4)){	//button Y
		//indexer
		}
		if(humanControl.joystickAux.getRawButton(5)){	//button LeftBumper
		//extend hopper
		}
		if(humanControl.joystickAux.getRawButton(6)){	//button RightBumper
		//autospin up
		}
		if(humanControl.joystickAux.getRawButton(7)){	//button Back
		//auto kill
		}
		if(humanControl.joystickAux.getRawButton(8)){	//button Start
		}
		if(humanControl.joystickAux.getRawButton(9)){	//button LeftAnalogStickButton
		}
		if(humanControl.joystickAux.getRawButton(10)){	//button RightAnalogStickButton
		}
	}
	 public void shooterStart() {
		motors.shooterOne.set(1.0);
		motors.shooterTwo.set(1.0);
	}
	 public void shooterStop()
	{
	 motors.shooterOne.set(0.0);
	 motors.shooterTwo.set(0.0);
	}
	
}