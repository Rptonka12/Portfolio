/**
 * @author Rachel Pierstorff
 * Project 1
 * January 15, 2017
 */
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import edu.princeton.cs.introcs.StdDraw;

public class Environment {
	
	private int rows;
	private int cols;
	private Cell[][] grid;
	private int[][] neighborCount;
	
	public Environment(String filename){
		Scanner inputStream = null;
		try {
			inputStream = new Scanner(new FileInputStream(filename));
		}
		catch(FileNotFoundException e){
			System.out.println("Cannot open file: " + filename);
			System.exit(0);
		}
		
		//setting up initial environment
		rows = inputStream.nextInt();
		cols = inputStream.nextInt();
		int cellSize = 20;
		StdDraw.setCanvasSize(cellSize*cols, cellSize*rows);
		StdDraw.setXscale(0, cols);
		StdDraw.setYscale(0, rows);
		
		//filling grid from file data
		grid = new Cell[rows][cols];
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (inputStream.nextInt()==1) {
					grid[i][j] = new Cell(true);
				}
				else {
					grid[i][j] = new Cell(false);
				}
			}
		}
	}
	
	public void runSimulation(){
		for (int iterations = 0; iterations < 1500; iterations++){
			displayGrid();
			createNeighborCount();
			createNextGen();
		}
	}
	
	private void displayGrid(){
		StdDraw.clear();
		for (int i = 0; i < grid.length; i++){
			for (int j = 0; j < grid[i].length; j++){
				
				if (grid[i][j].cellIsOccupied()){
					StdDraw.filledSquare(i+0.5, j+0.5, 0.5);
				}
			}
		}
		StdDraw.show(1000);
	}
	
	
	private Cell[][] createNextGen(){
		Cell[][] nextGen = new Cell[rows][cols];
		
		//create new grid by running neighborCount method through a loop of all the cells
		for (int i = 0; i < grid.length; i++){
			for (int j = 0; j < grid[i].length; j++){
				
				if (neighborCount[i][j] < 2)
					nextGen[i][j] = new Cell(false);
				else if (neighborCount[i][j] >= 2 && neighborCount[i][j] <= 3)
					nextGen[i][j] = new Cell(true);
				else if (neighborCount[i][j] > 3)
					nextGen[i][j] = new Cell(false);
				
				if (!(grid[i][j].cellIsOccupied()) && neighborCount[i][j]==3)
					nextGen[i][j] = new Cell(true);
		
			}
		}
		grid = nextGen;
		return grid;
	}
	
	private void createNeighborCount(){
		neighborCount = new int[rows][cols];
		
		for (int i = 0; i < grid.length; i++){
			for (int j = 0; j < grid[i].length; j++){
				//upper left
				if (i>0 && j>0 && grid[i-1][j-1].cellIsOccupied()){
					neighborCount[i][j]++;
				}
				//upper middle
				if (i>0 && grid[i-1][j].cellIsOccupied()){
					neighborCount[i][j]++;
				}
				//upper right
				if (i>0 && j<(cols-1) && grid[i-1][j+1].cellIsOccupied()){
					neighborCount[i][j]++;
				}
				//left
				if (j>0 && grid[i][j-1].cellIsOccupied()){
					neighborCount[i][j]++;
				}
				//right
				if (j<(cols-1) && grid[i][j+1].cellIsOccupied()){
					neighborCount[i][j]++;
				}
				//bottom left
				if (i<(rows-1) && j>0 && grid[i+1][j-1].cellIsOccupied()){
					neighborCount[i][j]++;
				}
				//bottom middle
				if (i<(rows-1) && grid[i+1][j].cellIsOccupied()){
					neighborCount[i][j]++;
				}	
				//bottom right
				if (i<(rows-1) && j<(cols-1) && grid[i+1][j+1].cellIsOccupied()){
					neighborCount[i][j]++;
				}
			}
		}
	}	
}