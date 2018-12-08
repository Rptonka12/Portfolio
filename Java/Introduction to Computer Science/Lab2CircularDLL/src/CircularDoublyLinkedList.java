public class CircularDoublyLinkedList <T>{

	private static class Node<T>{
		public T data;
		public Node<T> prev, next;
		public Node(T data, Node<T> prev, Node<T> next){ //don't need <T> because its a constructor--have already declared it as T
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}
	
	private Node<T> cursor;
	private int size;
	
	public CircularDoublyLinkedList(){
		cursor = null;
		size = 0;
	}
	
	public void addAfterCursor(T value){
		if (cursor==null){
			cursor = new Node<T>(value, null, null);
			cursor.prev = cursor;
			cursor.next = cursor;
		} else {
			Node<T> newNode = new Node<T>(value, cursor, cursor.next);
			cursor.next.prev = newNode;
			cursor.next = newNode;
		}
		size++;
	}
	
	public void deleteCursor(){
		Node<T> previous = cursor.prev;
		Node<T> successor = cursor.next;
		previous.next = successor;
		successor.prev = previous;
		//cursor.prev.next = cursor.next;
		//cursor.next.prev = cursor.prev;
		size--;
		
		if (size==0){
			cursor = null;
			System.out.println("List is now empty");
		} else {
			cursor = cursor.next;
		}
	}
	
	public void advanceCursor(int n){
		if (size==0) { return; }
		
		for(int i = 0; i < n; i++){
			cursor = cursor.next;
		}
	}
	
	public T getValue(){ return cursor.data; }
	public int getSize(){ return size; }
}