package org.usfirst.frc178.components;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
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

	private boolean isShooterOn;
	private boolean isPressedA;
	private boolean isAutoLoading;

	private PIDController shooterOnePID;

	public Shooter(Motors motors, Sensors sensors, HumanControl humanControl, Pneumatics pneumatics) {
		this.pneumatics = pneumatics;
		this.motors = motors;
		this.sensors = sensors;
		this.humanControl = humanControl;

		this.isShooterOn = false;
		this.isPressedA = false;
		this.isAutoLoading = true;

		/*double Kp = 0.5;
		double Ki = 0;
		double Kd = 0;

		sensors.shooterOneEncoder.start();
		sensors.shooterOneEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);

		shooterOnePID = new PIDController(Kp, Ki, Kd, sensors.shooterOneEncoder, motors.shooterOne);
		shooterOnePID.enable();
		shooterOnePID.setInputRange(0, 100);*/
	}

	public boolean isShooterOn() {
		return this.isShooterOn;
	}

	public void shooterStart(){
		motors.shooterOne.set(-1.0);
		motors.shooterTwo.set(-1.0);
		sensors.printEncoders();
	}

	public void shooterStop(){
		motors.shooterOne.set(0.0);
		motors.shooterTwo.set(0.0);
	}

	public void run() {
		this.aux();
		// Move and prevent the shooter from moving past its limit
		if (humanControl.joystickMain.getRawButton(3) && !sensors.elevationLowSwitch.getState()) {
			motors.elevator.set(-1.0); // down
		} else if (humanControl.joystickMain.getRawButton(4) && !sensors.elevationHighSwitch.getState()) {
			motors.elevator.set(1.0); // up
		} else {
			if (humanControl.joystickMain.getRawButton(5) && !sensors.elevationLowSwitch.getState()) {
				motors.elevator.set(-0.5); // down
			} else if (humanControl.joystickMain.getRawButton(6) && !sensors.elevationHighSwitch.getState()) {
				motors.elevator.set(0.5); // up
			} else {
				motors.elevator.set(0.0);
			}
		}
		

			/*System.out.print(sensors.elevationHighSwitch.getState() + "\t");
			System.out.print(sensors.elevationLoadSwitch.getState() + "\t");
			System.out.println(sensors.elevationLowSwitch.getState());*/
	}
	/**
	 * aux controller
	 * receives button inputs
	 */
	public void aux() {
		if (isShooterOn) {
			shooterStart();
		} else {
			shooterStop();
		}

		
		
		if (humanControl.joystickAux.getRawButton(1)){	//button A
			if (isPressedA == false) {
				if (isShooterOn == true) {
					isShooterOn = false;
				} else if (isShooterOn == false) {
					isShooterOn = true;
				}
			}

			isPressedA = true;
		} else {
			isPressedA = false;
		}
		
		//Auto shooter speedupcf\
		if (humanControl.joystickAux.getRawButton(8)) {
			//Eric's Crap code
			/*double shooterPIDSpeed = sensors.proportaionalShooterControl(-26.0 * 10, sensors.shooterOneEncoder);
			System.out.println(sensors.shooterOneEncoder.getRate() + "\t" + shooterPIDSpeed);
			motors.shooterOne.set(-shooterPIDSpeed);
			motors.shooterTwo.set(-shooterPIDSpeed);*/


			//Brandon's less crap
			//shooterOnePID.setSetpoint(50);
		}

		//breech
		pneumatics.frisbeeLoader.set(humanControl.joystickAux.getRawButton(2));	//button B
		
		if(humanControl.joystickAux.getRawButton(3)){	//button X
			motors.feederServo.set(0.0);
		} else {
			motors.feederServo.set(0.5);			
		}
		
		if (humanControl.joystickAux.getRawButton(7)) {
			isAutoLoading = false;
		}
		
		if (isAutoLoading) {
			autoLoad();
		}
		
		if (humanControl.joystickAux.getRawButton(4) && !sensors.elevationLoadSwitch.getState()) { // button y  
		//auto aim
			motors.elevator.set(1.0);
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
			/*if(sensors.feederPhoto.analog.getVoltage() > 0.4){	//somewhere between 0.4 and 2.5
				shooterStart();
			}
			else if(!humanControl.joystickAux.getTrigger()){	//else, don't spin
				shooterStop();
			}
			else{}*/
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
	
	public void autoLoad() {
		//sensors.printPhotocells();
		if (sensors.shooterPhoto.analog.getAverageValue() > sensors.PHOTOCELL_THRESHOLD) {
			motors.feederServo.set(0.00);
		} else {
			motors.feederServo.set(0.75);
		}
	}
}