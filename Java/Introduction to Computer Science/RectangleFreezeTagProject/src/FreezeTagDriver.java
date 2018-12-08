/**
 * 
 * @author Rachel Pierstorff
 * Programming Project 5
 * November 18, 2016
 * This class creates an array of moving rectangles and facilitates the game with the user.
 *
 */
import edu.princeton.cs.introcs.StdDraw;
import java.util.Random;

public class FreezeTagDriver {

	public static final int CANVAS_SIZE = 500;
	public static void main(String[] args) {
		StdDraw.setCanvasSize(CANVAS_SIZE, CANVAS_SIZE);
		StdDraw.setXscale(0, CANVAS_SIZE);
		StdDraw.setYscale(0, CANVAS_SIZE);
		Random r = new Random();
		boolean gameOver;
		
		//creating and populating array
		MovingRectangle[] rectangles = new MovingRectangle[5];
		for (int i = 0; i < rectangles.length; i++)
		{
			int xCoord = r.nextInt(CANVAS_SIZE);
			int yCoord = r.nextInt(CANVAS_SIZE);
			int width = r.nextInt(50) + 49;
			int height = r.nextInt(50) + 49;
			int xVelocity = r.nextInt(4) + 1;
			int yVelocity = r.nextInt(4) + 1;
			int canvasSize = CANVAS_SIZE;
			rectangles[i] = new MovingRectangle(xCoord, yCoord, width, height, xVelocity, yVelocity, canvasSize);
		}
		
		//animation loop
		gameOver = false;
		while (!gameOver)
		{
			StdDraw.clear();
			for (int i = 0; i < rectangles.length; i++)
			{
				if (StdDraw.mousePressed()) //stops rectangle if clicked
				{
					double x = StdDraw.mouseX();
					double y = StdDraw.mouseY();
					if (rectangles[i].containsPoint(x, y))
					{
						rectangles[i].setColor(StdDraw.RED);
						rectangles[i].setFrozen(true);
					}
				}
				
				rectangles[i].move();
				rectangles[i].draw();
				
				//Part 2: comment out to rectangles Part 1
				if (rectangles[i].isFrozen())
				{
					for (int tester = 0; tester < rectangles.length; tester++)
					{
						if (tester != i)
						{
							if (rectangles[i].isIntersecting(rectangles[tester]))
							{
								rectangles[i].setFrozen(false);
								rectangles[i].setRandomColor();
							}
						}
					}
				}
				
				gameOver = true;
				for (int j = 0; j < rectangles.length; j++)
				{
					if (!rectangles[j].isFrozen())
					{
						gameOver = false;
					}
				}
			}
			StdDraw.show(25);
		}
		System.out.println("You won the game!");
	}
}