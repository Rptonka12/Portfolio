/**
 * @author Rachel Pierstorff
 * Project 3
 * April 21, 2017
 * @param <T>
 */
public interface Stack<T>{
	void push(T t);
	T pop();
	T peek();
	int size();
	boolean empty();
}