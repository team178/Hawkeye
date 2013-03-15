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

		// Move and prevent the shooter from moving past its limit
		humanControl.joystickMain.getRawButton(3);
		humanControl.joystickMain.getRawButton(4);
		if (humanControl.joystickMain.getRawButton(11) && !sensors.elevationLowSwitch.getState()) {
			motors.elevator.set(-1.0); // down
		} else if (humanControl.joystickMain.getRawButton(12) && !sensors.elevationHighSwitch.getState()) {
			motors.elevator.set(1.0); // up
		} else {
			motors.elevator.set(0.0);
		}

			/*System.out.print(sensors.elevationHighSwitch.getState() + "\t");
			System.out.print(sensors.elevationLoadSwitch.getState() + "\t");
			System.out.println(sensors.elevationLowSwitch.getState());*/

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
			motors.feederServo.set(0.0);
		} else {
			motors.feederServo.set(0.5);
		}
		

		if (humanControl.joystickAux.getRawButton(4)) { // button y  
		//auto aim
		}
		
		if(humanControl.joystickAux.getRawButton(5)){	//button LeftBumper
		//extend hopper
			pneumatics.hopper.set(true);
		}
		
		if(humanControl.joystickAux.getRawButton(6)){	//button RightBumper
			pneumatics.hopper.set(false);
		}
		else{
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
		
		//POV d-pad x-axis
		if (humanControl.joystickAux.getRawAxis(6)<0 && !sensors.elevationLowSwitch.getState()) {
			motors.elevator.set(-1.0); // down
		} else if (humanControl.joystickAux.getRawAxis(6)>0 && !sensors.elevationHighSwitch.getState()) {
			motors.elevator.set(1.0); // up
		} else {
			motors.elevator.set(0.0);
		}
		/**
		//POV d-pad y-axis
		if (humanControl.joystickAux.getRawAxis(5)<0 && !sensors.elevationLowSwitch.getState()) {
			motors.elevator.set(-1.0); // down
		} else if (humanControl.joystickAux.getRawAxis(5)>0 && !sensors.elevationHighSwitch.getState()) {
			motors.elevator.set(1.0); // up
		} else {
			motors.elevator.set(0.0);
		}
		*/
		
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