import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

public class AdjacencyList {
	
	private int vertices; //count of the vertices in the graph
	private ArrayList<Vertex> adjlist; //main graph data structure
	private int time; //used to determine finishing times
	private Stack<Integer> orderedList; //used to ensure correct order for second DFS call
	
	//constructor: creates arraylist of correct size with each new vertex
	//and initializes instance variables
	AdjacencyList(int vCount){
		vertices = vCount;
		orderedList = new Stack<Integer>();
		adjlist = new ArrayList<Vertex>();
		
		for (int i = 0; i < vertices; i++){
			adjlist.add(new Vertex(i));
		}
	}
	
	//connects two existing vertices by adding the 'destination' vertex to the list
	//of connected vertices (stored in every vertex) of the 'origin' vertex
	void addEdgeSet(int origin, int destination){
		Vertex o = adjlist.get(origin);
		Vertex d = adjlist.get(destination);
		o.connectedVertices.add(d);
	}
	
	//the following Strongly Connected Components methods in order of being called
	
	//first depth search method - loops through each vertex and visits all vertices
	void DFS(){
		time = 0;
		for (int i = 0; i < adjlist.size(); i++){
			if (adjlist.get(i).discoveredTime < 0){
				DFS_Visit(adjlist.get(i), false, null);
			}
		}
	}
	
	//visit method goes through list of connected vertices at the current vertex and visits them
	//the print lines show strongly connected components by printing a new line after each 
	//connected vertex is visited - those on the same line are thus strongly connected
	void DFS_Visit(Vertex u, boolean print, PrintWriter pw){
		time++;
		u.setDiscovered(time);
		
		if (print) { pw.print(u.num + " "); }
		
		for (Vertex v : u.connectedVertices){
			if (v.discoveredTime < 0){
				v.setParent(u.num);
				DFS_Visit(v, print, pw);
			}
		}
		
		time++;
		u.setFinished(time);
		//vertex data added to a stack in the order they finish, so there is an 
		//established order to use for the second DFS call
		orderedList.push(u.num);
		
		if (print) { pw.println(); }
	}
	
	//transposes the existing graph by running through each list of connected vertices at each
	//vertex in the overall graph (results in nested for each loops), and reverses the edge
	//direction between each vertex - returns a transposed AdjacencyList 
	AdjacencyList graphTranspose(){
		AdjacencyList transposed = new AdjacencyList(vertices);
		for (Vertex v : adjlist){
			for (Vertex connected : v.connectedVertices){
				transposed.addEdgeSet(connected.num, v.num);
			}
		}
		return transposed;
	}
	
	//get method that returns the order vertices should be examined in second DFS call
	Stack<Integer> getStack(){ return orderedList; }
	
	//this DFS call uses a specific order in which to visit each node
	void modifiedDFS(Stack<Integer> order, PrintWriter pw){
		//resetting times to ensure copies from original graph didn't transfer
		for (int i = 0; i < vertices; i++){
			adjlist.get(i).setDiscovered(-1);
			adjlist.get(i).setFinished(-1);
		}
		
		//visits vertices in the order established by the stack
		while (!order.isEmpty()){
			Vertex v = adjlist.get(order.pop());
			if (v.discoveredTime < 0){
				DFS_Visit(v, true, pw);
			}
		}
	}
}