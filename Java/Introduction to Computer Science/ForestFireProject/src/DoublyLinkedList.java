//when you create a doubly linked list, you say what T is and it replaces T inside the class
//T must be an object type, not a primitive (use Integer class)
public class DoublyLinkedList <T>{

	private static class Node<T>{
		public T data;
		public Node<T> prev, next;
		public Node(T data, Node<T> prev, Node<T> next){ //don't need <T> because its a constructor--have already declared it as T
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}
	
	private Node<T> header;
	private Node<T> trailer;
	private int size;
	
	public DoublyLinkedList(){
		header = new Node<T>(null, null, null);
		trailer = new Node<T>(null, header, null);
		header.next = trailer;
		size = 0;
	}
	
	private void insertBetween(T data, Node<T> before, Node<T> after){
		Node<T> newNode = new Node<T>(data, before, after);
		before.next = newNode;
		after.prev = newNode;
		size++;
	}
	
	public void addToFront(T data){
		insertBetween(data, header, header.next);
	}
	
	public void addToBack(T data){
		insertBetween(data, trailer.prev, trailer);
	}
	
	private T removeNode(Node<T> node){
		assert(node != header && node!= trailer); //checks condition, if it's false, crashes the program
		node.prev.next = node.next;
		node.next.prev = node.prev;
		size--;
		assert(size >= 0);
		
		return node.data;
	}
	
	public T removeFromFront(){
		return removeNode(header.next);	
	}
	
	public T removeFromBack(){
		return removeNode(trailer.prev);
	}
	
	
	@Override
	public String toString(){
		String ret = "";
		for(Node<T> cursor = header.next; cursor != trailer; cursor = cursor.next){
			ret += cursor.data + " ";
		}
		return ret;
	}
	
	public int getSize(){ return size; }
	public boolean empty(){ return size==0; }
	
	public T front(){
		return header.next.data;
	}
	
	public T back(){
		return trailer.prev.data;
	}
}