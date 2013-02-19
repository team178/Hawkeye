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
			if(humanControl.joystickMain.getRawButton(11) && !humanControl.joystickMain.getRawButton(12) && !sensors.elevationLowSwitch.getState())
			{
				motors.elevator.set(-1.0);	//down
			}
			else if(humanControl.joystickMain.getRawButton(12) && !humanControl.joystickMain.getRawButton(11) && !sensors.elevationHighSwitch.getState())
			{
				motors.elevator.set(1.0);	//up
			}
			else
			{
				motors.elevator.set(0.0);
			}
		
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
		
		//breech
		pneumatics.frisbeeLoader.set(humanControl.joystickAux.getRawButton(2));	//button B
		
		if(humanControl.joystickAux.getRawButton(3)){	//button X
		//auto aim
		}
		
		if(humanControl.joystickAux.getRawButton(4)){	//button Y
		//indexer
			motors.feederServo.set(1.0);	//1.0 or -1.0
		}
		else{
			motors.feederServo.set(0.0);
		}
		
		if(humanControl.joystickAux.getRawButton(5)){	//button LeftBumper
		//extend hopper
			pneumatics.hopper.set(true);
		}
		else{
			pneumatics.hopper.set(false);
		}
		
		if(humanControl.joystickAux.getRawButton(6)){	//button RightBumper
		//autospin up
			//if there are frisbees, keep spinning
			if(sensors.feederPhoto.analog.getVoltage() > 0.4){	//somewhere between 0.4 and 2.5
				shooterStart();
			}
			else if(!humanControl.joystickAux.getTrigger()){	//else, don't spin
				shooterStop();
			}
			else{}
		}
		
		if(humanControl.joystickAux.getRawButton(7)){	//button Back
		//auto kill
		}
		
		if(humanControl.joystickAux.getRawButton(8) && !sensors.elevationLoadSwitch.getState()){	//button Start
			motors.elevator.set(1.0);
		}
		if(humanControl.joystickAux.getRawButton(9)){	//button LeftAnalogStickButton
		}
		if(humanControl.joystickAux.getRawButton(10)){	//button RightAnalogStickButton
		}
		//if(humanControl.joystickAux.)	//POV d-pad
	}
	
	public void shooterStart(){
		motors.shooterOne.set(-1.0);
		motors.shooterTwo.set(-1.0);
	}
	 public void shooterStop(){
		motors.shooterOne.set(0.0);
		motors.shooterTwo.set(0.0);
	}
	
}