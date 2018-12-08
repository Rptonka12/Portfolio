import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class CarDriver {

	public static final int CANVAS_SIZE = 400;
	public static void main(String[] args) {

		StdDraw.setCanvasSize(CANVAS_SIZE, CANVAS_SIZE);
		StdDraw.setXscale(0, CANVAS_SIZE);
		StdDraw.setYscale(0, CANVAS_SIZE);
		Car car1 = new Car(CANVAS_SIZE);
		
		while (true)
		{	
			if (StdDraw.isKeyPressed(KeyEvent.VK_G))
			{	
				car1.gas();
			}
			else if (StdDraw.isKeyPressed(KeyEvent.VK_B))
			{
				car1.brake();
			}
			
			if (car1.getSpeed() > 8)
			{
				StdDraw.setPenColor(StdDraw.RED);
				StdDraw.filledRectangle(100, 350, 15, 5);
				StdDraw.show(25);
				StdDraw.setPenColor(StdDraw.BLUE);
				StdDraw.filledRectangle(130, 350, 15, 5);
				StdDraw.show(25);
			}
			StdDraw.clear();
			car1.move();
			car1.draw();
			StdDraw.show(25);
		}	
	}
}