import java.util.Iterator;

public class BinarySearchTree<T extends Comparable<T> > implements Set<T>{ //returns -1 for less, 0 for equal, 1 for greater

	protected static class Node<T> {
		T data;
		Node<T> parent, left, right;
		
		Node(T data, Node<T> parent){
			this.data = data;
			this.parent = parent;
			left = null;
			right = null;
		}
	}
	
	protected Node<T> root;
	private int size;
	
	protected Node<T> makeNode(T data, Node<T> parent){
		return new Node<>(data, parent); //can override to return AVL node
	}
	
	protected void rebalance(Node<T> n){
		//does nothing in this class so it can be called and overridden in AVL tree
	}
	
	public BinarySearchTree(){
		root = null;
		size = 0;
	}
	
	private Node<T> search(T val){ //uses loop for efficiency to avoid new stack frame with each recursive call
		if (root == null) { return null; } //makes sure tree has at least one node
		Node<T> cursor = root; //start looking at root
		while(true){
			int c = val.compareTo(cursor.data); 
			if (c == 0) { 
				return cursor; 
			} else if (c < 0) {
				if (cursor.left == null) { return cursor; } //found parent node
				cursor = cursor.left; 
			} else {
				if (cursor.right == null) { return cursor; } //found parent node
				cursor = cursor.right;
			}
		}
	}
	
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(T val) {
		if (empty()) {
			size++;
			//root = new Node<>(val, null);
			root = makeNode(val, null);
			return true;
		}
		
		Node<T> n = search(val);
		int c = val.compareTo(n.data);
		if (c==0) { return false; } //value already there
		else if (c<0) {
			assert(n.left==null);
			//n.left = new Node<>(val,n); //have to make an AVL node; need different behavior from different classes
			n.left = makeNode(val, n);
			size++;
			rebalance(n);
			return true;
		} else {
			assert(n.right==null);
			n.right = makeNode(val, n);
			size++;
			rebalance(n);
			return true;
		}
	}

	//only call this if n definitely has a left child
	private Node<T> predecessor(Node<T> n) {
		n = n.left;
		while(n.right != null){
			n = n.right; //follow down to the right
		}
		return n;
	}
	
	@Override
	public boolean remove(T val) {
		Node<T> n = search(val);
		if (n==null || !val.equals(n.data)){
			return false;
		} 
		
		if (n.left != null && n.right != null){ //hard case
			Node<T> pred = predecessor(n);
			n.data = pred.data;
			n = pred; //let the code below delete pred for us
		}
		//at this point we're sure we have an easy case (1 child or none)
		//which node are we keeping?
		Node<T> survivor = n.left;
		if(survivor == null) { survivor = n.right; }
		
		//special case for removing the root
		if (n == root) {
			root = survivor;
			if (survivor != null){
				survivor.parent = null; //new root has no parent
			}
			size--;
			return true;
		}
		
		//remove n from its parent
		boolean isLeft = n==n.parent.left;
		if (isLeft){
			n.parent.left = survivor;
		} else {
			n.parent.right = survivor;
		}
		//if survivor exists, update its parent
		if(survivor != null){
			survivor.parent = n.parent;
		}
		
		size--;
		//if the removed node has a parent, rebalance starting there
		if (n.parent!=null){
			rebalance(n.parent);
		}
		return true;
	}

	@Override
	public boolean contains(T val) {
		if (empty()) { return false; }
		return val.equals(search(val).data); //does the node search return the value we want? if returns parent, doesn't equal	
	}
	
	@Override public String toString(){ 
		if (empty()) { return "empty set"; }
		StringBuilder sb = new StringBuilder();
		traverseHelper(root, sb);
		return sb.toString();
	}
	
	private void traverseHelper(Node<T> n, StringBuilder sb){
		if(n.left!=null) { traverseHelper(n.left, sb); }
		sb.append(n.data + ", ");
		if (n.right!=null) { traverseHelper(n.right, sb); }
	}

	@Override public int size() { return size; }
	@Override public boolean empty() { return (size==0); } 
}