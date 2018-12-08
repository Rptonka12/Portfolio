package sudoku;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.princeton.cs.introcs.StdDraw;

public class Sudoku {

	private int[][] grid; //the 9x9 grid
	
	//load a puzzle from a text file
	public Sudoku(String filename){
		try{
			Scanner in = new Scanner(new FileInputStream(filename));
			
			grid = new int[9][9];
			for(int row = 0; row < 9; row++){
				for(int col = 0; col < 9; col++){
					grid[row][col] = in.nextInt();
				}
			}
			in.close();
		} catch (FileNotFoundException e){
			System.out.println("couldn't open file: " + e.getMessage());
			System.exit(1);
		}
	}
	
	
	public void draw(){
		double start = 1/18.0;
		double diff = 1/9.0;
		for(int row = 0; row < 9; row++){
			for(int col = 0; col < 9; col++){
				if(grid[row][col] > 0){
					StdDraw.text(start + col*diff, 1 - start - row*diff, 
							Integer.toString(grid[row][col]));
				}
			}
		}
		//draw the thin lines
		StdDraw.setPenRadius(0.01);
		for(int i = 1; i < 9; i++){
			StdDraw.line(0, i*diff, 1, i*diff);
			StdDraw.line(i*diff, 0, i*diff, 1);
		}
		//draw the thick lines
		StdDraw.setPenRadius(0.03);
		StdDraw.line(0, .333, 1, .333);
		StdDraw.line(0, .666, 1, .666);
		StdDraw.line(.333, 0, .333, 1);
		StdDraw.line(.666, 0, .666, 1);
		
	}
	
	//are all of the numbers full?
	//if there are any 0s, then, no, they aren't
	public boolean allFull(){
		for(int row = 0; row < 9; row++){
			for(int col = 0; col < 9; col++){
				if(grid[row][col] == 0){
					return false;
				}
			}
		}
		return true;
	}
	
	//are there any nonzero repeats in this row?
	private boolean rowValid(int row){
		for(int col = 0; col < 9; col++){
			for(int other = col + 1; other < 9; other++){
				if(grid[row][col] != 0 && grid[row][col] == grid[row][other]){
					return false;
				}
			}
		}
		return true;
	}
	
	//any nonzero repeats in this column?
	private boolean colValid(int col){
		for(int row = 0; row < 9; row++){
			for(int other = row + 1; other < 9; other++){
				if(grid[row][col] != 0 && grid[row][col] == grid[other][col]){
					return false;
				}
			}
		}
		return true;
	}
	
	//any nonzero repeats in one of the 3x3 blocks?
	private boolean checkBlock(int blockRow, int blockCol){
		for(int row = 3*blockRow; row < 3*blockRow + 3; row++){
			for(int col = 3*blockCol; col < 3*blockCol + 3; col++){
				for(int otherRow = 3*blockRow; otherRow < 3*blockRow + 3; otherRow++){
					for(int otherCol = 3*blockCol; otherCol < 3*blockCol + 3; otherCol++){
						if(row != otherRow && col != otherCol && 
								grid[row][col] != 0 &&
								grid[row][col] == grid[otherRow][otherCol]){
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	public boolean isValid(){
		//any repeats in the rows?
		for(int row = 0; row < 9; row++){
			if(!rowValid(row)){ return false;}
		}
		//repeats in cols?
		for(int col = 0; col < 9; col++){
			if(!colValid(col)){ return false;}
		}
		// repeats in 3x3 blocks?
		for(int br = 0; br < 3; br++){
			for(int bc = 0; bc < 3; bc++){
				if(!checkBlock(br, bc)){ return false;}
			}
		}
		//this might be a solution (might still contain 0's too though)
		return true;
	}
	
	
	//start is the "index" of the first cell that we're trying to fill in
	//the top left corner is cell 0, and the bottom right is cell 80
	//the first cell in row 1 (the second row) is cell 9
	public boolean solve(int start){
		//TODO: Follow the comments to write the solution
		//if the board is full, return whether or not it's a valid configuration
		if (this.allFull()) {
			return this.isValid();
		}
		//compute which row/column we're looking at
		int col = start%9;
		int row = start/9;
		
		//if that cell is occupied, there's no work to be done here,
		//solve, so move onto the next square
		if (!(grid[row][col] == 0)) 
			return solve(start+1);
		
		//try all 9 numbers in this square.  If putting in the number would be valid,
		//try solving the rest of the puzzle.  If it works, we've found the solution
		else if (grid[row][col] == 0) {
			for (int i = 1; i <= 9; i++){
				grid[row][col] = i;
				
				StdDraw.clear();
				this.draw();
				StdDraw.show(50);
				
				if (this.isValid()){
					if (solve(start+1)){
						System.out.println("Cell " + start + " solved!");
						return true;
					}
				}
			}
		}
		
		//if you want to get a more interactive view of what's happening,
		//call draw(), and StdDraw.show(0) to draw your guess as you go along.
		
		//if none of the 9 gave us a solution, reset the value to 0, 
		//indicate that we failed
		grid[row][col] = 0;
		System.out.println("Cell " + start + " solve failed");
		return false;
	}
}
