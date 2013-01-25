package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Kinect;
import org.usfirst.frc178.Drivetrain;

public class KinectFHS 
{
    private Kinect kinect;
    private Drivetrain drivetrain;
    private float rightHandZ, leftHandZ;
    
    public KinectFHS(Drivetrain drivetrain)
    {
	kinect = Kinect.getInstance();
	this.drivetrain = drivetrain;
    }
	    
    public void autonomousKinect()
    {
	if (kinect.getNumberOfPlayers() == 1) 
	{
	    rightHandZ = kinect.getSkeleton().GetHandRight().getZ();
            leftHandZ = kinect.getSkeleton().GetHandLeft().getZ();

            drivetrain.frontLeftSet(leftHandZ);
            drivetrain.rearLeftSet(leftHandZ);
            drivetrain.frontRightSet(-rightHandZ);
            drivetrain.rearRightSet(-rightHandZ);
        }
        else 
	{
            drivetrain.frontLeftSet(0.0);
            drivetrain.rearLeftSet(0.0);
            drivetrain.frontRightSet(0.0);
            drivetrain.rearRightSet(0.0);
        }
    }
}
