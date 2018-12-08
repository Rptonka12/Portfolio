import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class ComponentsDriver {

	public static void main(String[] args) {
		//reading file and setting up output file
		String filename = args[0];
		String outputname = args[1];
		Scanner inputStream = null;
		PrintWriter pw;
		
		try { 
			inputStream = new Scanner(new FileInputStream(filename)); 
			pw = new PrintWriter(new FileOutputStream(outputname));
			
			int numVals = inputStream.nextInt();
			inputStream.nextLine();
			AdjacencyList graph = new AdjacencyList(numVals);
			
			//this loop looks at each input line to create arrays of edges
			//each edge contains two numbers, the two vertices included
			//the arraylist of the two vertices is then added to the adjacency list
			while (inputStream.hasNext()) {
				int origin = inputStream.nextInt();
				int destination = inputStream.nextInt();
				graph.addEdgeSet(origin, destination);
			}
			
			//finding strongly connected components
			graph.DFS(); //first run of DFW to find finish times
			AdjacencyList transposed = graph.graphTranspose(); //transpose the graph
			transposed.modifiedDFS(graph.getStack(), pw); //run DFS on transposed graph in order of finish times
			
			//sending everything to the specified file
			pw.flush();
			pw.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Cannot open file: " + filename);
			System.exit(0);
		}
	}
}