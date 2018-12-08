//edge class
//edge objects stored in a list within each vertex
public class Edge {
	//stores weight data and the destination vertex which connects to 'this' vertex 
	private double weight; 
	Vertex destVertex;
	
	//edge constructor
	public Edge(double w, Vertex v) {
		weight = w;
		destVertex = v;
	}
	
	//getter method for edge weight
	public double getWeight() { return weight; }
}