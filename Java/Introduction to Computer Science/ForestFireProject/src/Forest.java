/**
 * Rachel Pierstorff
 * Project 3
 * April 21, 2017
 * This class creates Forest objects and runs the breadth and depth search methods.
 */
import java.util.Random;

public class Forest {
	private int[][] grid;
	private int rows;
	private int cols;
	private static final int DRY_LAND = 0;
	private static final int VEGETATION = 1;
	private static final int FIRE = 2;
	
	public Forest(int rows, int cols, float probability){
		Random r = new Random();
		this.rows = rows;
		this.cols = cols;
		grid = new int[rows][cols];
		
		for (int i = 0; i < grid.length; i++){
			for (int j = 0; j < grid[i].length; j++){
				double val = r.nextFloat();
				if (val < probability) { grid[i][j] = VEGETATION; }
				else { grid[i][j] = DRY_LAND; }
			}
		}
	}
	
	@Override
	public String toString(){
		StringBuilder forest = new StringBuilder();
		for (int i = 0; i < grid.length; i ++){
			for (int j = 0; j < grid[i].length; j++){
				forest.append(" " + grid[i][j]);
			}
			forest.append('\n');
		}
		return forest.toString();
	}
	
	public int getWidth(){ return cols; }
	public int getHeight() { return rows; }
	
	public class Cell{ //inner class to keep track of lists
		private int col, row;
		
		public Cell(int row, int col){
			this.row = row;
			this.col = col;
		}
	}
	
	public boolean depthFirstSearch(){
		ArrayListStack<Cell> cellsToExplore = new ArrayListStack<>();
		
		for (int j = 0; j < grid[0].length; j++){ //adds first row vegetation to stack
			if (grid[0][j]==VEGETATION){
				cellsToExplore.push(new Cell(0, j));
			}
		}
		
		Cell currentCell;
		while(!cellsToExplore.empty()){
			currentCell = cellsToExplore.pop();
			grid[currentCell.row][currentCell.col] = FIRE;
			
			if (currentCell.row==(rows-1)){ //if cell is in bottom row
				return true;
			}  
			if ((currentCell.row>0) && grid[(currentCell.row)-1][currentCell.col]==VEGETATION){ //cell above
				cellsToExplore.push(new Cell(((currentCell.row)-1), currentCell.col));
			} 
			if ((currentCell.col<cols-1) && grid[currentCell.row][(currentCell.col)+1]==VEGETATION){ //cell to the right
				cellsToExplore.push(new Cell(currentCell.row, ((currentCell.col)+1)));
			} 
			if ((currentCell.col>0) && grid[currentCell.row][(currentCell.col)-1]==VEGETATION){ //cell to the left
				cellsToExplore.push(new Cell(currentCell.row, ((currentCell.col)-1)));
			}  
			if ((currentCell.row<rows-1) && grid[(currentCell.row)+1][currentCell.col]==VEGETATION){ //cell below
				cellsToExplore.push(new Cell(((currentCell.row)+1), currentCell.col));
			} 
		}
		return false;
	}
	
	public boolean breadthFirstSearch(){
		DoublyLinkedListQueue<Cell> cellsToExplore = new DoublyLinkedListQueue<>();
		
		for (int j = 0; j < grid[0].length; j++){ //adds first row vegetation to queue
			if (grid[0][j]==VEGETATION){
				cellsToExplore.enqueue(new Cell(0, j));
			}
		}
		
		Cell currentCell;
		while (!cellsToExplore.empty()){
			currentCell = cellsToExplore.dequeue();
			grid[currentCell.row][currentCell.col] = FIRE;
			
			if (currentCell.row==(rows-1)){ //if cell is in bottom row
				return true;
			}  
			if ((currentCell.row>0) && grid[(currentCell.row)-1][currentCell.col]==VEGETATION){ //cell above
				cellsToExplore.enqueue(new Cell(((currentCell.row)-1), currentCell.col));
			} 
			if ((currentCell.col<cols-1) && grid[currentCell.row][(currentCell.col)+1]==VEGETATION){ //cell to the right
				cellsToExplore.enqueue(new Cell(currentCell.row, ((currentCell.col)+1)));
			} 
			if ((currentCell.col>0) && grid[currentCell.row][(currentCell.col)-1]==VEGETATION){ //cell to the left
				cellsToExplore.enqueue(new Cell(currentCell.row, ((currentCell.col)-1)));
			}  
			if ((currentCell.row<rows-1) && grid[(currentCell.row)+1][currentCell.col]==VEGETATION){ //cell below
				cellsToExplore.enqueue(new Cell(((currentCell.row)+1), currentCell.col));
			} 
		}
		return false;
	}
}