import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Graph {

	private int vertices; //count of the vertices in  graph
	private int edges; //count of edges in  graph
	private HashMap<String, Vertex> adjlist; //main graph data structure
	private ArrayList<String> keys; //list of keys for easy access to starting element 
	
	//constructor - creates a new empty graph
	Graph(){
		adjlist = new HashMap<String, Vertex>();
		vertices = 0;
		edges = 0;
		keys = new ArrayList<String>();
	}
	
	//getter methods
	int getNumVertices() { return vertices; }
	int getNumEdges() { return edges; }
	Vertex getStartVertex() { return adjlist.get(keys.get(0)); }
	
	//adds vertex to graph after checking whether or not it exists already
	Vertex addVertex(String name){
		//if graph doesn't already have the key
		if (!adjlist.containsKey(name)) {
			keys.add(name); //places keys in list in order they're added
			vertices++; //increases vertex count variable
			return adjlist.put(name, new Vertex(name)); //adds to hashmap
		//if graph does already have the key
		} else {
			return adjlist.get(name);
		}
	}
	
	//adds an edge between the origin and destination vertices with a certain weight
	void addEdge(String origin, String destination, double weight){
		adjlist.get(origin).addEdge(adjlist.get(destination), weight);
		edges++;
	}
	
	//HELPER METHODS (initialize, relax, print)
	//initialize graph: sets all vertex distance variables to a max value and parent to null
	public void initializeSingleSource(Vertex start) {
		for (Vertex v : adjlist.values()){
			v.dist = Integer.MAX_VALUE;
			v.setParent(null);
		}
		//sets distance of the starter vertex to 0
		start.dist = 0;
	}
	
	//relax edge: lessens the distance of a vertex if a lower value exists through
	//a different vertex and the associated edge between the two
	public boolean relax(Vertex u, Vertex v, Edge e){
		if (v.dist > (u.dist + e.getWeight())) {
			v.dist = u.dist + e.getWeight(); 
			v.setParent(u);
			return true;
		}
		return false; //dijkstra's modifications
	}
	
	//print vertices with distances
	public void PrintDistances(boolean bellmanford, String filename){
		PrintWriter pw;
		
		//if statement writes to a different file depending on which algorithm just ran
		if (bellmanford) { //Bellman-Ford output file
			try {
				pw = new PrintWriter(new FileOutputStream(filename + ".bout"));
				
				for (Vertex v : adjlist.values()){
					//accounts for "Infinity" strings in answer key output files (so they match)
					if (v.dist == Integer.MAX_VALUE) {
						pw.println(v.name + " Infinity");
					} else {
						pw.println(v.name + " " + (float) v.dist);
					}
				}
				
				pw.flush();
				pw.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			try { //Dijkstra's output file
				pw = new PrintWriter(new FileOutputStream(filename + ".dout"));

				for (Vertex v : adjlist.values()){
					if (v.dist == Integer.MAX_VALUE) {
						pw.println(v.name + " Infinity");
					} else {
						pw.println(v.name + " " + (float) v.dist);
					}
				}
				
				pw.flush();
				pw.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	//ALGORITHMS 
	public boolean BellmanFord(Vertex start, String filename){
		initializeSingleSource(start);
		
		//goes through every edge vertices-1 times and relaxes them
		for (int i = 0; i < adjlist.size()-1; i++) {
			//for every vertex, look at every associated edge
			for (Vertex v : adjlist.values()){
				for (Edge e : v.connectedVertices) {
					relax(v, e.destVertex, e);
				}
			}
		}
		//for all edges, check if the vertex's distance can be lowered
		for (Vertex v : adjlist.values()) { 
			for (Edge e : v.connectedVertices) {
				if (v.dist > (e.destVertex.dist + e.getWeight())){
					PrintDistances(true, filename);
					return false;
				}
			}
		}
		PrintDistances(true, filename);
		return true;
	}
	
	public void Dijkstra(Vertex start, String filename){
		initializeSingleSource(start);
		//create priority queue with comparator to hold vertices sorted by
		//a key value (the vertex distance)
		PriorityQueue<Vertex> q = new PriorityQueue<>(vertices, vertComparator);
		q.add(start);//modification listed in assignment instructions
		
		while (!q.isEmpty()) {
			Vertex u = q.poll(); //pull minimum distance vertex
			//for each vertex, add connected vertices to the queue for each relaxed edge
			for (Edge e : u.connectedVertices){ 
				if (relax(u, e.destVertex, e)) { 
					if (q.contains(e.destVertex)){ //if queue contains destination vertex of relaxed edge, remove it from queue
						q.remove(e.destVertex);
					}
					q.add(e.destVertex);
				}
			}
		}
		PrintDistances(false, filename);
	}
	
	//comparator for the Dijkstra priority queue - finds minimum based on vertex distance
	public static Comparator<Vertex> vertComparator = new Comparator<Vertex>(){
		@Override
		public int compare(Vertex v1, Vertex v2) {
	        return (int) (v1.dist - v2.dist);
	    }
	};
}