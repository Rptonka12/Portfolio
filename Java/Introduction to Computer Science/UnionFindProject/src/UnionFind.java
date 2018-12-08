/*
 * Rachel Pierstorff
 * Project 6
 * June 2, 2017
 * Part 3: UnionFind data structure
 */
import java.util.HashMap;

public class UnionFind<T> implements GroupCombine<T>{

	private class Node<T> {
		T data;
		Node<T> parent;
		int rank;
		
		public Node(T data){
			this.data = data;
			this.parent = this;
			this.rank = 0;
		}
	}
	
	private HashMap<T, Node<T>> tree;
	
	public UnionFind(){
		tree = new HashMap<T, Node<T>>();
	}
	
	@Override
	public void createGroup(T data) {
		Node<T> newNode = new Node<T>(data);
		tree.put(data, newNode);
	}
	
	private Node<T> findNode(Node<T> n){
		if (n.parent==n) { return n; }
		else {
			n.parent = findNode(n.parent);
			return n.parent;
		}
	}
	
	@Override
	public T find(T data) {
		Node<T> target = findNode(tree.get(data));
		return target.data;
	}
	
	@Override
	public void combineSet(T a, T b) {
		T repA = find(a);
		T repB = find(b);
		Node<T> nodeA = tree.get(repA);
		Node<T> nodeB = tree.get(repB);
		
		if (repA!=repB){
			if (nodeA.rank==nodeB.rank){
				nodeA.parent = nodeB;
				nodeB.rank++;
			} else if (nodeA.rank > nodeB.rank){ //greater rank = lower
				nodeA.parent = nodeB;
			} else {
				nodeB.parent = nodeA;
			}
		} else {
			System.out.println("Names are the same");
		}
	}
	
//	public void getGroup(T data){
//		Node<T> cursor = tree.get(data);
//		System.out.print(cursor.data + " "); //visit
//		
//		if (cursor!=cursor.parent) {
//			getGroup(cursor.parent.data); //recurse
//		} else {
//			System.out.println();
//		}
//	}
}