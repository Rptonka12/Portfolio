
public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

	private static class AVLNode<T> extends Node<T>{
		int height;
		public AVLNode(T data, Node<T> parent){
			super(data, parent);
			height = 0; //every time a new node is made it's a leaf
		}
	}
	
	//no new data members needed for AVL tree
	public AVLTree(){
		super(); //root = null, size = 0;
	}
	
	@Override
	public Node<T> makeNode(T data, Node<T> parent){
		return new AVLNode<>(data, parent); //ok because AVLNode is a type of Node (inherits from Node)
	}
	
	private AVLNode<T> cast(Node<T> n){
		assert(n instanceof AVLNode<?>); //make sure it's really an AVL node
		return (AVLNode<T>) n;
	}
	
	private void relink(Node<T> parent, Node<T> child, boolean isLeft){
		if (child != null){
			child.parent = parent;
		}
		if (isLeft){
			parent.left = child;
		} else {
			parent.right = child;
		}
	}
	
	//rotate n up, and its parent down to appropriate side
	//n is either right or left children; have to consider n's children, p's other child, p's parents
	//have to update gp's child to be n unless gp is null (p is root)
	//update "middle" subtree (child of n)
	//update p, n
	private void rotate(Node<T> n){
		Node<T> p = n.parent; //know it has a parent because it needs to be rotated up
		Node<T> gp = p.parent;
		
		//fix grandparent
		if(gp==null){ //p is the root and n is new root
			root = n;
			n.parent = null;
		} else {
			//make n the child of gp, on the left it p was on the left
			relink(gp, n, p==gp.left);
		}
		
		if(n==p.left){
			//move n's right child to p's left
			relink(p, n.right, true);
			relink(n, p, false); //rotate n and p
		} else {
			relink(p, n.left, false);
			relink(n, p, true); 
		}
	}
	//fixes a broken node by rotating 1 or 2 times
	//return the new top node
	private Node<T> doubleRotate(Node<T> n){
		Node<T> p = n.parent;
		Node<T> gp = p.parent;
		//which side are n and p on?
		boolean nLeft = n==p.left;
		boolean pLeft = p==gp.left;
		
		if(nLeft==pLeft){ //easy case, single rotate
			rotate(p);
			return p;
		} else { //hard case, two rotates
			rotate(n);
			rotate(n);
			return n;
		}
	}
	//after doing a rotate, or checking tree, need to recompute height
	private void recomputeHeight(AVLNode<T> n){
		int lHeight = -1;
		int rHeight = -1;
		if(n.left!= null){
			lHeight = cast(n.left).height;
		}
		if(n.right != null){
			rHeight = cast(n.right).height;
		}
		n.height = Math.max(lHeight, rHeight) + 1;
	}
	
	private boolean isBalanced(AVLNode<T> n){
		//get height of children
		int lHeight = -1;
		int rHeight = -1;
		if(n.left!= null){
			lHeight = cast(n.left).height;
		}
		if(n.right != null){
			rHeight = cast(n.right).height;
		}
		//does the height differ by less than 2?
		return Math.abs(lHeight-rHeight)<2;
	}

	private AVLNode<T> tallerChild(AVLNode<T> n){
		if(n.left==null){ return cast(n.right); }
		if(n.right==null) { return cast(n.left); } 
		//left and right both exist if this has been reached
		
		//return the obviously teller child
		if(cast(n.left).height > cast(n.right).height){ return cast(n.left); }
		if(cast(n.right).height > cast(n.left).height){ return cast(n.right); }
		
		//left and right are the same height and not null
		//check if n's parent exists
		if(n.parent == null) {return cast(n.left); } //doesn't matter just pick left
		if(n == n.parent.left) { return cast(n.left); } //otherwise make sure grandchild is zig-zig or zag-zag
		else { return cast(n.right); }
	}
	
	@Override 
	protected void rebalance(Node<T> n){
		int oldHeight, newHeight;
		do{
			oldHeight = cast(n).height;
			if(!isBalanced(cast(n))){
				System.out.println("rebalancing  at " + n.data);
				n = doubleRotate(tallerChild(tallerChild(cast(n)))); //fix the tallest grandchild and set n to be new root of subtree
				recomputeHeight(cast(n.left));
				recomputeHeight(cast(n.right));
			}
			recomputeHeight(cast(n));
			newHeight = cast(n).height;
			n = n.parent;
		} while(oldHeight != newHeight && n != null);
	}
	
	@Override
	public String toString(){
		//in order traversal
		return inOrderPrint(root);
	}
	private String inOrderPrint(Node<T> n){
		if(n==null) { return ""; }
		String left = inOrderPrint(n.left);
		String right = inOrderPrint(n.right);
		
		return left + "( " + n.data + ", height: " + cast(n).height + ") " + right;
	}
}