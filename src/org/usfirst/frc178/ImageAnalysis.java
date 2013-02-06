package org.usfirst.frc178;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.Timer;
import java.util.Vector;
import org.usfirst.frc178.Drivetrain;

public class ImageAnalysis {

	private AxisCamera axis;
	private ParticleAnalysisReport[] report;
	private ParticleAnalysisReport[] rectangle;
	private double numParticles;
	private final double CAMERA_HEIGHT = 56.0 /12; // height of camera in feet
	private final double BOTTOM_HEIGHT = (28.0 + 15.75) /12; // 3.645833333333333 height of bottom hoop in feet
	private final double MIDDLE_HEIGHT = (61.0 + 15.75) /12; // 6.395833333333333 height of middle hoop in feet
	private final double TOP_HEIGHT = (98.0 + 15.75) /12; // 9.479166666666667 height of top hoop in feet
	private final double HEIGHT = 3.0/2; // height of backboard rectangle tape in feet
	private final double WIDTH = 2; // width of backboard rectangle tape in feet
	//view angle for the axis camera M1011-w
	private final double ANGLE = .7592;//actual angle is .8203 radians

	Timer timer;
	public ImageAnalysis(AxisCamera a)
	{
		axis = a;
		axis.writeResolution(AxisCamera.ResolutionT.k320x240);
		axis.writeCompression(30);
		axis.writeMaxFPS(15);
		timer = new Timer();
	}
	public void updateImage(int luminosityMin) throws AxisCameraException, NIVisionException
	{

		try
		{
			if (axis.freshImage())
			{
				//timer.start();
				ColorImage image = axis.getImage();
				//System.out.println("1:" + timer.get());
				BinaryImage image2 = image.thresholdHSL(60,255,220,255,140,180);//green light
				image.free();
				BinaryImage image3 = image2.removeSmallObjects(true, 1);
				image2.free();
				//System.out.println("2:" + timer.get());
				BinaryImage image4 = image3.convexHull(true);
				image3.free();
				//System.out.println("3:" + timer.get());
				numParticles = image4.getNumberParticles();
				report = image4.getOrderedParticleAnalysisReports();
				//System.out.println("4:" + timer.get());

				image4.free();
				//System.out.println("5:" + timer.get());
				rectangle = findRectangles();
				//System.out.println("6:" + timer.get());
				//timer.stop();
				//timer.reset();
				System.out.println("number:" + numParticles);
				for(int i = 0; i < rectangle.length; i++)
				{
					System.out.println("x: " + rectangle[i].center_mass_x + "y: " + rectangle[i].center_mass_y);
				}
			}
			else
			{
		rectangle = new ParticleAnalysisReport[0];
		numParticles = -1;
			}
		}
		catch (AxisCameraException ex)
		{
			rectangle = new ParticleAnalysisReport[0];
			numParticles = -1;
			ex.printStackTrace();
		}

	}
	public void updateImageTeleop(Drivetrain drive) throws AxisCameraException, NIVisionException
	{

		try
		{
			if (axis.freshImage())
			{
				ColorImage image = axis.getImage();
				BinaryImage image2 = image.thresholdHSL(0,255,0,50,165,255);
				drive.drive();
				BinaryImage image3 = image2.convexHull(true);
				drive.drive();
				report = image3.getOrderedParticleAnalysisReports(10);
				drive.drive();

				image.free(); image2.free(); image3.free();
				rectangle = findRectangles();
			}
			else
			{
		rectangle = new ParticleAnalysisReport[0];
			}
		}
		catch (AxisCameraException ex)
		{
			ex.printStackTrace();
		}

	}

		public ParticleAnalysisReport[] getRectangle()
		{
			return rectangle;
		}
	public void updateImageAuto(Drivetrain drive) throws AxisCameraException, NIVisionException
	{

		try
		{
			if (axis.freshImage())
			{
				ColorImage image = axis.getImage();
				BinaryImage image2 = image.thresholdHSL(0,255,0,50,165,255);
				drive.setDiminishedSpeed(.5);
				BinaryImage image3 = image2.convexHull(true);
				drive.setDiminishedSpeed(.7);
				report = image3.getOrderedParticleAnalysisReports(10);

				image.free(); image2.free(); image3.free();
				rectangle = findRectangles();
			}
			else
			{

		rectangle = new ParticleAnalysisReport[0];
			}
		}
		catch (AxisCameraException ex)
		{
			ex.printStackTrace();
		}

	}
	public double getRectangleScore(int particle)
	{
		//gives a score as to how similar a particle is to a rectangle. Perfect rectangle gives 1
		return report[particle].particleArea / (report[particle].boundingRectHeight * report[particle].boundingRectWidth) ;
	}
	public double getAspectRatio(int particle)
	{
		//gives the ratio of the width of rectangle over height. Ideal ratio is 4/3
		return report[particle].boundingRectWidth / report[particle].boundingRectHeight;
	}

	//finds particles that are close to the rectangles that we are looking for
	public ParticleAnalysisReport[] findRectangles()
	{

		Vector position = new Vector();

		for(int i = 0; i < report.length; i++)
		{
			if(report[i].particleArea > 100)
			{
				if(getRectangleScore(i) > .8)
				{
				position.addElement(Integer.toString(i));
				}
			}
			else
			{
				//System.out.println(report.length + ":" + i);
				break;
			}
		}
		ParticleAnalysisReport[] output = new ParticleAnalysisReport[position.size()];
		for(int i = 0; i < position.size(); i++)
		{
				output[i] = report[Integer.parseInt((String)position.elementAt(i))];
		}
		return output;

	}
	public ParticleAnalysisReport[] getParticles()
	{
		return report;
	}
	public ParticleAnalysisReport[] getRectangles()
	{
		return rectangle;
	}
	public double getNumParticles()
	{
		return numParticles;
	}
	public double getDistance(int particle) {
		//in feet
		//double thisvalue = HEIGHT / rectangle[particle].boundingRectHeight * rectangle[particle].boundingRectWidth;

		/*
		 * During testing
		 *  - find ratio of distance to size
		 */

		double particleSize = rectangle[particle].particleArea;

		double FieldOfVision = HEIGHT / rectangle[particle].boundingRectHeight * rectangle[particle].imageWidth;
		double distance = FieldOfVision / Math.tan(ANGLE);

		/*double FieldOfVision = HEIGHT / rectangle[particle].boundingRectHeight * rectangle[particle].imageWidth;
		double angle = MathUtils.acos(rectangle[particle].boundingRectWidth / axis.getResolution().width);
		double distance = Math.sqrt(MathUtils.pow((Math.sin(Math.PI/2-angle)/Math.sin(angle)*FieldOfVision),2)+MathUtils.pow(FieldOfVision, 2)); // Pythagorean Theorem/Distance Formula
		*/

		/*double a=(Math.sin(Math.PI/2-angle) / Math.sin(angle)*FieldOfVision);
		double b=FieldOfVision / 2;
		double angle_c = 911;
		double distance = Math.sqrt( MathUtils.pow(a,2) + MathUtils.pow(b,2) - 2 * a * b * Math.cos(angle_c) ); // Law of Cosines ^^ *better than above*
		*/
		return distance;
	}

	public double getHeight(int particle) {
		//in feet assuming camera points straight ahead at all times
		double FieldOfVision = HEIGHT/rectangle[particle].boundingRectHeight*rectangle[particle].imageHeight;
		double height = FieldOfVision/2*rectangle[particle].center_mass_y_normalized + CAMERA_HEIGHT;
		return height;
	}
	/*public String getWhichTarget(int particle)
	{
		if(Math.abs(getHeight(particle)-TOP_HEIGHT) < 1)
			return "top";
		if(Math.abs(getHeight(particle)-MIDDLE_HEIGHT) < 1)
			return "middle";
		if(Math.abs(getHeight(particle)-BOTTOM_HEIGHT) < 1)
			return "bottom";

		return "-1";
	}*/
	public ParticleAnalysisReport findTarget(int target)//0:bottom 1:middle(either) 2:top
	{
		double height = 0;
		if(target == 0)
		{
			height = BOTTOM_HEIGHT;
		}
		else
		if(target == 1)
		{
			height = MIDDLE_HEIGHT;
		}
		else
		if(target == 2)
		{
			height = TOP_HEIGHT;
		}
		else
		{
			return null;
		}
				//System.out.println(rectangle.length);
		for(int i = 0; i < rectangle.length; i++)
		{
			if(Math.abs(getHeight(i) - height) < 1)
			{
				return rectangle[i];
			}
		}
		return null;
	}
}