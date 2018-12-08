import java.util.LinkedList;

public class Vertex {
	public String name; //id variable of each vertex
	private Vertex parent; 
	public double dist; //distance from start vertex
	
	//list of connected vertices stored in edges 
	//edges hold the edge weight and the associated destination vertices
	public LinkedList<Edge> connectedVertices; 
	
	//vertex constructor: sets data variables
	public Vertex(String n){
		name = n;
		connectedVertices = new LinkedList<Edge>();
	}
	
	//set parent Vertex method
	public void setParent(Vertex p){
		parent = p;
	}
	
	//add edge between this vertex and vertex v, with the specified weight value
	public void addEdge(Vertex v, double weight){
		connectedVertices.add(new Edge(weight, v));
	}
}