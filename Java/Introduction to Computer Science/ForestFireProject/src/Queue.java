/**
 * @author Rachel Pierstorff
 * Project 3
 * April 21, 2017
 * @param <T>
 */
public interface Queue<T> {
	void enqueue(T t);
	T dequeue();
	T front();
	int size();
	boolean empty();
}