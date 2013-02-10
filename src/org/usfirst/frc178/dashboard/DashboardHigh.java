package org.usfirst.frc178.dashboard;

import org.usfirst.frc178.components.Drivetrain;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class DashboardHigh {
	Dashboard dashboardHigh;

	public DashboardHigh() {

	}

	public void updateDashboardHigh(Drivetrain drivetrain, double gyro, double ultrasonic, double ultrasonic2, double numParticles, double luminosityMin, double compressorState, Joystick joystick) {
		// [frontLeftVictor],[frontRightVictor],[backLeftVictor],[backRightVictor],[Gyro],[Ultrasonic],[Joystick X],[Joystick Y],[Joystick Z]
		dashboardHigh = DriverStation.getInstance().getDashboardPackerHigh();

		dashboardHigh.addCluster();
		dashboardHigh.addCluster();
		dashboardHigh.addDouble(drivetrain.getFrontLeft());
		dashboardHigh.addDouble(drivetrain.getFrontRight());
		dashboardHigh.addDouble(drivetrain.getBackLeft());
		dashboardHigh.addDouble(drivetrain.getBackRight());
		dashboardHigh.addDouble(gyro);
		dashboardHigh.addDouble(ultrasonic);
		dashboardHigh.addDouble(ultrasonic2);
		dashboardHigh.addDouble(joystick.getX());
		dashboardHigh.addDouble(joystick.getY());
		dashboardHigh.addDouble(joystick.getZ());
		//dashboardHigh.addDouble(0.0);//remove pressure
		dashboardHigh.addDouble(compressorState);
		dashboardHigh.addDouble(numParticles);
		dashboardHigh.addDouble(luminosityMin);
		dashboardHigh.finalizeCluster();
		dashboardHigh.finalizeCluster();
		dashboardHigh.commit();
	}
}