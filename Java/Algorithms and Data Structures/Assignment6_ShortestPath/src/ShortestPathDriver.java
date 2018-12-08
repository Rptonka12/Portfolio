import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ShortestPathDriver {

	public static void main(String[] args) {
		//this array loops through all the specified file names provided as arguments
		for (int c = 0; c < args.length; c++) {
			//setting up file variables
			String filename = args[c];
			Scanner inputStream = null;
					
			try { 
				inputStream = new Scanner(new FileInputStream(filename));
				
				Graph graph = new Graph();
				
				//reading input file line by line
				while (inputStream.hasNext()) {
					//read input one line at a time and parse into parts
					String line = inputStream.nextLine();
					String[] info = line.trim().split("\\s+");
					
					//first string on the line is the origin vertex
					String origin = info[0];
					graph.addVertex(origin);
					
					// for the remaining line elements (pairs of vertices and 
					// edge weights) add new vertices and associated edge weights
					for (int i = 1; i < info.length; i+=2){
						String destination = info[i];
						graph.addVertex(destination);
						double weight = Double.parseDouble(info[i+1]);
						graph.addEdge(origin, destination, weight);
					}
				}
				
				//getter methods for print line output
				int vertCount = graph.getNumVertices();
				int edgeCount = graph.getNumEdges();
				
				// BELLMAN FORD ALGORITHM
				//set up iterations & timer (runs more times for smaller inputs)
				int iter1 = Math.max(1, (5000/vertCount));
				CpuTimer BF_Timer = new CpuTimer();
				
				//run algorithm iter1 times
				for (int a = 0; a < iter1; a++){
					graph.BellmanFord(graph.getStartVertex(), filename);
				}
				// calculate average time taken to run the algorithm and print
				// data to console
				double BF_Elapsed = BF_Timer.getElapsedCpuTime();
				float BF_Time = (float) BF_Elapsed/iter1;
				System.out.println(vertCount + "," + edgeCount + ",\"B\"," + BF_Time + ", \"" + filename + "\"");
				
				//DIJKSTRA'S ALGORITHM
				//set up iterations & timer (runs more times for smaller inputs)
				int iter2 = Math.max(1, (5000/vertCount));
				CpuTimer D_Timer = new CpuTimer();
				
				//run algorithm iter1 times
				for (int a = 0; a < iter2; a++){
					graph.Dijkstra(graph.getStartVertex(), filename);
				}
				// calculate average time taken to run the algorithm and print
				// data to console
				double D_Elapsed = D_Timer.getElapsedCpuTime();
				float D_Time = (float) D_Elapsed/iter2;
				System.out.println(vertCount + "," + edgeCount + ",\"D\"," + D_Time + ", \"" + filename + "\"");
			}
			catch(FileNotFoundException e) {
				System.out.println("Cannot open file: " + filename);
				System.exit(0);
			}
		}
	}
}