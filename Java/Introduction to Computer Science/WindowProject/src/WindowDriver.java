/**
 * Rachel Pierstorff
 * Project 5
 * February 28, 2017
 */
import edu.princeton.cs.introcs.StdDraw;

public class WindowDriver {

	public static void main(String[] args) {
		WindowDisplay w = new WindowDisplay(200, 200);
		
		Window w1 = new Window (50, 50, 30, 40, StdDraw.BLUE); w.addWindow(w1);
		Window w2 = new Window (100, 130, 40, 40, StdDraw.RED); w.addWindow(w2);
		Window w3 = new Window (80, 80, 30, 40, StdDraw.GREEN); w.addWindow(w3);
		Window w4 = new Window (120, 60, 50, 40, StdDraw.BLACK); w.addWindow(w4);

		w.run();
	}
}