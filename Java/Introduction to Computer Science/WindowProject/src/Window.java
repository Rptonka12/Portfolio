/**
 * Rachel Pierstorff
 * Project 5
 * February 28, 2017
 */
import java.awt.Color;
import edu.princeton.cs.introcs.StdDraw;

public class Window {
	
	private double xCenter;
	private double yCenter;
	private double halfWidth;
	private double halfHeight;
	private Color color;
	
	public Window(double x, double y, double w, double h, Color c){
		xCenter = x;
		yCenter = y;
		halfWidth = w;
		halfHeight = h;
		color = c;
	}
	
	public void display(){
		StdDraw.setPenColor(color);
		StdDraw.filledRectangle(xCenter, yCenter, halfWidth, halfHeight);
	}
	
	public boolean isWithin(double x, double y){
		if (((x <= xCenter+halfWidth) && (y <= yCenter+halfHeight)) && ((x >= xCenter-halfWidth) && (y >= yCenter-halfHeight)))
			return true;
		return false;
	}

	public double getxCenter() { return xCenter; }

	public void setxCenter(double xCenter) { this.xCenter = xCenter; }

	public double getyCenter() { return yCenter; }

	public void setyCenter(double yCenter) { this.yCenter = yCenter; }
}