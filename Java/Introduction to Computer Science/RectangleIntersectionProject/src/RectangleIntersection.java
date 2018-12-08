/**
 * Rachel Pierstorff
 * Project 2
 * October 5, 2016
 */
import edu.princeton.cs.introcs.StdDraw;
import java.util.Scanner;
public class RectangleIntersection {
	public static final int CANVAS_SIZE = 500;
	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		StdDraw.setCanvasSize(CANVAS_SIZE, CANVAS_SIZE);
		StdDraw.setXscale(0, CANVAS_SIZE);
		StdDraw.setYscale(0, CANVAS_SIZE);
		StdDraw.setPenRadius(0.01);
		
		//rectangle 1
		System.out.print("Enter the x coordinate of the center of rectangle 1 (0-500): ");
		double centerX1 = keyboard.nextDouble();
		
		System.out.println("Enter the y coordinate of the center of rectangle 1 (0-500): ");
		double centerY1 = keyboard.nextDouble();
		
		System.out.println("Enter the width of rectangle 1: ");
		double height1 = keyboard.nextDouble();
		double halfHeight1 = height1 / 2;
		
		System.out.println("Enter the height of rectangle 1: ");
		double width1 = keyboard.nextDouble();
		double halfWidth1 = width1 / 2;
		
		//rectangle 2
		System.out.print("Enter the x coordinate of the center of rectangle 2 (0-500): ");
		double centerX2 = keyboard.nextDouble();
		
		System.out.println("Enter the y coordinate of the center of rectangle 2 (0-500): ");
		double centerY2 = keyboard.nextDouble();
		
		System.out.println("Enter the width of rectangle 2: ");
		double height2 = keyboard.nextDouble();
		double halfHeight2 = height2 / 2;
		
		System.out.println("Enter the height of rectangle 2: ");
		double width2 = keyboard.nextDouble();
		double halfWidth2 = width2 / 2;
		
		//determination of edges
		double edgeTop1 = centerY1 + halfHeight1;
		double edgeBottom1 = centerY1 - halfHeight1;
		double edgeRight1 = centerX1 + halfWidth1;
		double edgeLeft1 = centerX1 - halfWidth1;
		
		double edgeTop2 = centerY2 + halfHeight2;
		double edgeBottom2 = centerY2 - halfHeight2;
		double edgeRight2 = centerX2 + halfWidth2;
		double edgeLeft2 = centerX2 - halfWidth2;
		
		//conditionality
		if (edgeRight1 < edgeLeft2) {
			System.out.println("no intersection");
			StdDraw.setPenColor(StdDraw.GREEN);
		}
		else if (edgeRight2 < edgeLeft1) {
			System.out.println("no intersection");
			StdDraw.setPenColor(StdDraw.GREEN);
		}
		else if (edgeBottom1 > edgeTop2) {
			System.out.println("no intersection");
			StdDraw.setPenColor(StdDraw.GREEN);
		}
		else if (edgeBottom2 > edgeTop1) {
			System.out.println("no intersection");
			StdDraw.setPenColor(StdDraw.GREEN);
		}
		else {
			System.out.println("intersection");
			StdDraw.setPenColor(StdDraw.RED);
		}
		
		StdDraw.rectangle(centerX1, centerY1, halfWidth1, halfHeight1);
		StdDraw.rectangle(centerX2, centerY2, halfWidth2, halfHeight2);
			
	}
}