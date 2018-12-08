import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;

public class Car {
	private int width;
	private int xCoord;
	private int yCoord;
	private Color color;
	private int speed;
	
	//Constructor 1
	public Car(int canvasWidth)
	{
		width = canvasWidth;
		xCoord = 0;
		yCoord = width/2;
		color = StdDraw.BLACK;
		speed = 0;
	}
	
	//Constructor 2
	public Car(int canvasWidth, Color c)
	{
		width = canvasWidth;
		xCoord = 0;
		yCoord = width/2;
		color = c;
		speed = 0;
	}
	
	//methods
	public void gas()
	{
		speed = speed + 1;
	}
	
	public void brake()
	{
		if (speed > 0)
			speed = speed - 1;
	}
	
	public void move()
	{
		if (speed > 0)
			xCoord = xCoord + speed;
		if (xCoord>=width)
			xCoord = 0;
	}
	
	public void draw()
	{
		StdDraw.setPenColor(color);
		double x = xCoord;
		double y = yCoord;
		StdDraw.filledRectangle(x, y, 15, 5);
	}
	
	public int getSpeed()
	{
		return speed;
	}	
}