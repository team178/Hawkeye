/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc178.devices;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author Administrator
 */
public class Motors 
{
    public Victor frontLeft, frontRight, backLeft, backRight, shooterOne, shooterTwo, elevator;
    public Servo feederServo;
    private double robotSpeed = 1.0;
    
    public Motors()
    {
        frontRight = new Victor(1){public void set(double d){super.set(d * robotSpeed);}}; //PWM port 1
        backRight = new Victor(2){public void set(double d){super.set(d * robotSpeed);}}; //PWM port 2
        frontLeft = new Victor(3){public void set(double d){super.set(d * robotSpeed);}}; //PWM port 3
		backLeft = new Victor(4){public void set(double d){super.set(d * robotSpeed);}}; //PWM port 4	
        shooterOne = new Victor(5);
        shooterTwo = new Victor(6);
        feederServo = new Servo(7);
        elevator = new Victor(8);
    }
}