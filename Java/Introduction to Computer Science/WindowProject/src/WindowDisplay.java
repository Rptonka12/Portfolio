/**
 * Rachel Pierstorff
 * Project 5
 * February 28, 2017
 */
import java.util.ArrayList;
import edu.princeton.cs.introcs.StdDraw;

public class WindowDisplay {

	ArrayList<Window> windows;
	private int width;
	private int height;
	
	public WindowDisplay(int w, int h){
		width = w;
		height = h;
		windows = new ArrayList<Window>();
		StdDraw.setCanvasSize();
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
	}
	
	public void addWindow(Window window){
		windows.add(window);
	}
	
	public void run(){
		//animation loop
		while (true) {
			StdDraw.clear();
			showWindows();
			windowClick();
			windowDrag();
			StdDraw.show(10);
		}
	}
	
	private void showWindows(){
		for (Window i : windows){
			i.display();
		}
	}
	
	private void windowClick(){
		if (StdDraw.mousePressed()) {
			int topSquare = 0;
			
			//cycles through windows to find the index of the top most window that overlaps the mouse click
			for (int i = windows.size()-1; i >= 0; i--){
				if (windows.get(i).isWithin(StdDraw.mouseX(), StdDraw.mouseY()) && (i > topSquare))
					topSquare = i;
			}
			
			//moves top square to the end of the arraylist, where it will be drawn on top of the others
			windows.add(windows.get(topSquare));
			windows.remove(windows.get(topSquare));
		}
	}
	
	private void windowDrag(){
		windowClick();
		
		double originalXCenter = windows.get(windows.size()-1).getxCenter();
		double originalYCenter = windows.get(windows.size()-1).getyCenter();
		double originalMouseX = StdDraw.mouseX();
		double originalMouseY = StdDraw.mouseY();
		
		//while window is being dragged, calculates change in mouse position to set new center coordinates
		while (StdDraw.mousePressed()) {
			double newMouseX = StdDraw.mouseX();
			double newMouseY = StdDraw.mouseY();
			double dX = newMouseX - originalMouseX;
			double dY = newMouseY - originalMouseY;
			
			windows.get(windows.size()-1).setxCenter(originalXCenter + dX);
			windows.get(windows.size()-1).setyCenter(originalYCenter + dY);
			
			StdDraw.clear();
			showWindows();
			StdDraw.show(10);
		}
	}
}