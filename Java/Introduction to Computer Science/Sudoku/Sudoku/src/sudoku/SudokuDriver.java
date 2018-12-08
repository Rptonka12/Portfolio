package sudoku;

import edu.princeton.cs.introcs.StdDraw;

public class SudokuDriver {

	public static void main(String[] args) {
		//load from the file
		Sudoku s = new Sudoku("sudoku1.txt");
		//draw the unsolved puzzle
		s.draw();
		//and wait a second
		StdDraw.show(1500);
		
		//solve the puzzle.  The first cell we need to fill in is
		//the first cell in the puzzle
		s.solve(0); //you might have draw calls in here
		//so clear everything
		StdDraw.clear();
		s.draw(); //and draw it once you've solved the puzzle
		StdDraw.show(0);
		

	}

}
