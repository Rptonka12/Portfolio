/**
 * Rachel Pierstorff
 * Project 3
 * April 21, 2017
 * This generic class creates the ArrayList Stack for the depth search method in the Forest class.
 */
import java.util.ArrayList;

public class ArrayListStack<T> implements Stack<T> {

	private ArrayList<T> list;
	
	public ArrayListStack(){ list = new ArrayList<>(); }
	
	@Override
	public void push(T t) {
		list.add(t);
	}

	@Override
	public T pop() {
		return list.remove(list.size()-1);
	}

	@Override
	public T peek() {
		return list.get(list.size()-1);
	}

	@Override public int size() { return list.size(); }
	@Override public boolean empty() { return list.isEmpty(); }
}