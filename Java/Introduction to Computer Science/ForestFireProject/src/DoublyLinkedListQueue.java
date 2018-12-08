/**
 * @author Rachel Pierstorff
 * Project 3
 * April 21, 2017
 * This generic DLL Queue class is the template for the breadth search in the Forest class.
 * @param <T>
 */
public class DoublyLinkedListQueue<T> implements Queue<T> {

	private DoublyLinkedList<T> list;
	
	public DoublyLinkedListQueue(){ list = new DoublyLinkedList<T>(); }
	
	@Override
	public void enqueue(T t) {
		list.addToBack(t);
	}

	@Override
	public T dequeue() {
		return list.removeFromFront();
	}

	@Override
	public T front() {
		return list.front();
	}

	@Override public int size() { return list.getSize(); }
	@Override public boolean empty() { return list.empty(); }
}