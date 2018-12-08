import java.util.ArrayList;

public class Heap {

	//heap data variables: array to store elements and counter to keep track of progress
	private ArrayList<Aircraft> array;
	private int landingCounter;
	
	//constructor
	public Heap(){ 
		array = new ArrayList<Aircraft>(); 
		landingCounter = 0;
	}
	
	public int getLandingCounter(){ return landingCounter; }
	
	//indices of parent and child nodes, formulas adjusted from textbook pseudocode
	private int PARENT(int i){ return (i-1)/2; }
	private int LEFT(int i){ return (2*i) + 1; }  
	private int RIGHT(int i){ return (2*i) + 2; }
	
	//builds a min heap - runs after planes are added to heap to maintain heap property
	//runs through half of array (doesn't run from leaf nodes)
	void buildMinHeap() {
		for (int i = array.size()/2; i >= 0; i--){
			minHeapify(array, i);
		}
	}

	//min heapify, converted from textbook psuedocode for max heapify
	void minHeapify(ArrayList<Aircraft> list, int i){
		int l = LEFT(i);
		int r = RIGHT(i);
		int smallest;
		
		//checking left
		if ((l < list.size()) && (list.get(l).compareTo(list.get(i)))) {
			smallest = l;
		} else {
			smallest = i;
		}
		//checking right
		if ((r < list.size()) && (list.get(r).compareTo(list.get(smallest)))) {
			smallest = r;
		}
		
		//if the smallest node is not the current, switch positions of the 2 and keep calling minHeapify
		if (smallest != i){
			Aircraft temp = list.get(i);
			list.set(i, list.get(smallest));
			list.set(smallest, temp);
			minHeapify(list, smallest);
		}
	}

	//extract min aircraft from heap and reheapifies the list to ensure the right order
	public Aircraft heapExtractMin() {
		if (array.size() < 1) {
			System.out.println("error: heap underflow");
			return null;
		} else {
			//switches the place of the removed & last element to ensure non heap, then re-heapifies
			Aircraft min = array.get(0);
			array.set(0, array.get(array.size()-1));
			array.remove(array.size()-1);
			minHeapify(array, 0);
			landingCounter++;
			return min;
		}
	}
	
	//adds an aircraft element to the correct place in the tree
	void insert(Aircraft key){
		array.add(key);
		int index = array.size()-1;
		
		//because min-heapify checks down the tree, this loop checks 'up'
		//it compares the added place to its parent and switches their locations if necessary
		while ((index > 0) && (!array.get(PARENT(index)).compareTo(array.get(index)))){
			Aircraft temp = array.get(index);
			array.set(index, array.get(PARENT(index)));
			array.set(PARENT(index), temp);
			index = PARENT(index);
		}
	}
	
	//checks if the queue is empty (no planes have arrived at this time)
	public boolean isEmpty() {
		if (array.size()==0){
			return true;
		} else return false;
	}
	
	
/* not necessary for this simulation!
	void heapsort() {
		buildMinHeap();
		for (int i = array.size()-1; i >= 1; i--) {
			Aircraft temp = array.get(i);
			array.set(i, array.get(0));
			array.set(0, temp);
			minHeapify(array, i);
		}
	}
	public Aircraft minimum(){
		if (array.size() <= 0){
			return null;
		} else return array.get(0);
	}*/
}