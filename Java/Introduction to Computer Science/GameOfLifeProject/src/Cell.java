/**
 * @author Rachel Pierstorff
 * Project 1
 * January 15, 2017
 */
import edu.princeton.cs.introcs.StdDraw;

public class Cell {
	private boolean occupied;
	
	public Cell(boolean isOccupied){
		occupied = isOccupied;
		if (cellIsOccupied()){
			StdDraw.setPenColor(StdDraw.RED);
		}
	}
	
	public boolean cellIsOccupied(){
		return occupied;
	}
}
