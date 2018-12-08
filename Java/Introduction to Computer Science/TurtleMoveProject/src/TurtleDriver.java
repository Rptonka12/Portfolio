/**
 * 
 * @author Rachel Pierstorff
 * Programming Project 4
 * November 3, 2016
 * This file performs the commands a user inputs in a switch statement format that runs until the user enters quit.
 *
 */
import java.util.Scanner;
public class TurtleDriver {

	public static void main(String[] args) {
		Turtle turtle1 = new Turtle();
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Welcome to Turtle Graphics.\n"
				+ "Choose from the following menu of commands: forward (include number of steps),right (include angle of turn),\n"
				+ "left (include angle of turn), pendown, penup, or pencolor (include black, red, green, yellow, or blue).\n"
				+ "Enter quit when you are done.");
        String response = keyboard.next();
        
        while (!(response.equals("quit"))) 
        {
        	switch (response) 
        	{
        		case "forward":
        			int steps = keyboard.nextInt();
        			turtle1.forward(steps);
        			break;
        		case "right":
        			double rDegrees = keyboard.nextDouble();
        			turtle1.right(rDegrees);
        			break;
        		case "left":
        			double lDegrees = keyboard.nextDouble();
        			turtle1.left(lDegrees);
        			break;
        		case "pendown":
        			turtle1.penDown();
        			break;
        		case "penup":
        			turtle1.penUp();
        			break;
        		case "pencolor":
        			String colorChoice = keyboard.next();
        			turtle1.setPenColor(colorChoice);
        			break;
        		default:
        			System.out.println("Invalid command.");
        			break;
        	}
        	System.out.println("Enter another command: ");
        	response = keyboard.next();
        }
        System.out.println("You've finished your drawing!");
	}
}
