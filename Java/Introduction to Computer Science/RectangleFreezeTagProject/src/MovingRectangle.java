/**
 * 
 * @author Rachel Pierstorff
 * Programming Project 5
 * November 18, 2016
 * This class includes the methods and constructor used by the FreezeTagDriver class.
 *
 */
import java.awt.Color;
import java.util.Random;
import edu.princeton.cs.introcs.StdDraw;

public class MovingRectangle {
	private int xCoord;
	private int yCoord;
	private int width;
	private int height;
	private int xVelocity;
	private int yVelocity;
	private int canvasSize;
	private Color color;
	private boolean frozen;
	
	//constructor
	public MovingRectangle(int x, int y, int w, int h, int xVel, int yVel, int canvas)
	{
		xCoord = x;
		yCoord = y;
		width = w;
		height = h;
		xVelocity = xVel;
		yVelocity = yVel;
		canvasSize = canvas;
		frozen = false;
		
		//generating random rectangle color
		Random r = new Random();
		int randomColor = r.nextInt(7);
		switch (randomColor) {
			case 0:
				color = StdDraw.BLACK;
				break;
			case 1:
				color = StdDraw.ORANGE;
				break;
			case 2:
				color = StdDraw.YELLOW;
				break;
			case 3:
				color = StdDraw.GREEN;
				break;
			case 4:
				color = StdDraw.BLUE;
				break;
			case 5:
				color = StdDraw.PINK;
				break;
			case 6:
				color = StdDraw.CYAN;
				break;
		}	
	}
	
	//methods
	public void draw()
	{
		StdDraw.setPenColor(color);
		StdDraw.filledRectangle(xCoord, yCoord, width/2, height/2);
	}
	
	public void move()
	{	
		if (!(frozen))
		{
			xCoord = xCoord + xVelocity;
			yCoord = yCoord + yVelocity;
		
			//hitting walls
			if (((xCoord + (width/2)) >= canvasSize) || ((xCoord - (width/2)) <= 0))
			{
				xVelocity *= -1;
				this.setRandomColor();
			}
			if (((yCoord + (height/2)) >= canvasSize) || ((yCoord - (height/2)) <= 0))
			{
				yVelocity *= -1;
				this.setRandomColor();
			}
		}
		else
		{
			xCoord = xCoord + 0; //rectangle stops moving when frozen
			yCoord = yCoord + 0;
		}
	}
	
	public void setColor(Color c)
	{
		color = c;
	}
	
	public void setRandomColor()
	{
		Random r = new Random();
		int randomColor = r.nextInt(7);
		switch (randomColor) {
			case 0:
				color = StdDraw.BLACK;
				break;
			case 1:
				color = StdDraw.ORANGE;
				break;
			case 2:
				color = StdDraw.YELLOW;
				break;
			case 3:
				color = StdDraw.GREEN;
				break;
			case 4:
				color = StdDraw.BLUE;
				break;
			case 5:
				color = StdDraw.PINK;
				break;
			case 6:
				color = StdDraw.CYAN;
				break;
		}
	}
	
	public boolean containsPoint(double x, double y)
	{
		//rectangle edges
		double right = (double) xCoord + (width/2);
		double left = (double) xCoord - (width/2);
		double top = (double) yCoord + (height/2);
		double bottom = (double) yCoord - (height/2);
		
		if (((x > left) && (x < right)) && ((y > bottom) && (y < top)))
			return true;
		else
			return false;
	}
	
	public boolean isFrozen()
	{
		if (frozen)
			return true;
		else
			return false;
	}
	
	public void setFrozen(boolean val)
	{
		frozen = val;
		//if (val==true)
			//color = StdDraw.RED;
	}
	
	public boolean isIntersecting(MovingRectangle r)
	{
		//calling object edges
		double right = (double) xCoord + (width/2);
		double left = (double) xCoord - (width/2);
		double top = (double) yCoord + (height/2);
		double bottom = (double) yCoord - (height/2);
		
		//argument edges
		double right2 = (double) r.xCoord + (r.width/2);
		double left2 = (double) r.xCoord - (r.width/2);
		double top2 = (double) r.yCoord + (r.height/2);
		double bottom2 = (double) r.yCoord - (r.height/2);
		
		//conditions
		if (right < left2)
			return false;
		else if (right2 < left)
			return false;
		else if (bottom > top2)
			return false;
		else if (bottom2 > top)
			return false;
		else
			return true;
	}
}