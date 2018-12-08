import java.util.LinkedList;

public class Vertex {

	public int num; //actual numeric data
	public int discoveredTime; //time discovered
	public int finishedTime; //time finished - used for second DFS call
	public int parent; //parent - not really necessary for this simulation
	public LinkedList<Vertex> connectedVertices; //the destination vertices connected to this one by a directed edge
	
	//constructor: sets data variables
	public Vertex(int n){
		num = n;
		discoveredTime = -1;
		finishedTime = -1;
		parent = -1;
		connectedVertices = new LinkedList<Vertex>();
	}
	
	//sets time to given integer
	public void setDiscovered(int time){
		discoveredTime = time;
	}
	
	//sets time to given integer
	public void setFinished(int time){
		finishedTime = time;
	}
	
	//sets parent to the data associated with the parent - but not really needed here
	public void setParent(int p){
		parent = p;
	}
}