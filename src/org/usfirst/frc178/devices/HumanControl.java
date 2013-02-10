/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//Main and Aux controllers, etc
package org.usfirst.frc178.devices;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Administrator
 */
public class HumanControl 
{
    public Joystick joystickMain, joystickAux; // add , joystickKiddie
    
    public HumanControl()
    {
        joystickMain = new Joystick(1);
        joystickAux = new Joystick(2);
        //joystickKiddie = new Joystick(3);
    }
}