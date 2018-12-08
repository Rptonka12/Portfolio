/**
 * Rachel Pierstorff
 * Project 4
 * Nov 3, 2016
 * This file creates the Turtle class and all of its associated constructors, methods, and variables.
 */
import edu.princeton.cs.introcs.StdDraw;

public class Turtle {
	public static final int CANVAS_SIZE = 500;
	
	//instance variables
	private double xCoord;
	private double yCoord;
	private double direction;
	private boolean penDown;
	
	//initializing constructor
	public Turtle()
	{
		StdDraw.setXscale(0, CANVAS_SIZE);
		StdDraw.setYscale(0, CANVAS_SIZE);
		xCoord = CANVAS_SIZE/2;
		yCoord = CANVAS_SIZE/2;
		direction = 90;
		StdDraw.setPenColor(StdDraw.BLACK);
		penDown = false;
		StdDraw.filledCircle(xCoord,yCoord, 5);
	}
	
	//methods
	public void forward(int steps)
	{
		int stepSize = 30;
		int stepNum = steps;
		if (!(penDown))
		{
			for (int i = 0; i < stepNum; i++) //draws each step
			{
				xCoord = xCoord + (Math.cos(Math.toRadians(direction))*stepSize);
				yCoord = yCoord + (Math.sin(Math.toRadians(direction))*stepSize);
				StdDraw.filledCircle(xCoord, yCoord, 5);
			}
		}
		else if (penDown)
		{
			for (int i = 0; i < stepNum; i++)
			{
				StdDraw.line(xCoord, yCoord, xCoord + (Math.cos(Math.toRadians(direction))*stepSize), 
							yCoord + (Math.sin(Math.toRadians(direction))*stepSize));
				xCoord = xCoord + (Math.cos(Math.toRadians(direction))*stepSize);
				yCoord = yCoord + (Math.sin(Math.toRadians(direction))*stepSize);
				StdDraw.filledCircle(xCoord, yCoord, 5);
			}
		}
	}
	
	public void right (double angle)
	{
		double change = angle;
		direction = direction - change;
	}
	
	public void left (double angle)
	{
		double change = angle;
		direction = direction + change;
	}
	
	public void penUp()
	{
		penDown = false;
	}
	
	public void penDown()
	{
		penDown = true;
	}
	
	public void setPenColor(String color)
	{
		String penColor = color;
		switch (penColor) 
		{
			case "black":
				StdDraw.setPenColor(StdDraw.BLACK);
				break;
			case "red":
				StdDraw.setPenColor(StdDraw.RED);
				break;
			case "green":
				StdDraw.setPenColor(StdDraw.GREEN);
				break;
			case "yellow":
				StdDraw.setPenColor(StdDraw.YELLOW);
				break;
			case "blue":
				StdDraw.setPenColor(StdDraw.BLUE);
				break;
			default:
				System.out.println("You entered an invalid color.");
				break;
		}
	}
}